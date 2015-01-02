package com.internetitem.config.server.dao.derby;

import com.internetitem.config.server.FileUtility;
import com.internetitem.config.server.dao.DatabaseAccess;
import com.internetitem.config.server.dao.derby.dataModel.RawComponent;
import com.internetitem.config.server.dao.derby.dataModel.RawSetting;
import com.internetitem.config.server.dao.derby.dataModel.RawTag;
import com.internetitem.config.server.dataModel.db.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Component
@ConditionalOnProperty(name = "derby.url")
public class DerbyDatabase implements DatabaseAccess {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Override
	public void upgrade() throws Exception {
		int version = getVersion();
		if (version < 1) {
			runSql("00-tables");
		}
	}

	private String loadSql(String type, String name) throws IOException {
		return FileUtility.slurp("src/sql/derby/" + type + "/" + name + ".sql");
	}

	private void runSql(String filename) throws Exception {
		String sql = loadSql("schema", filename);

		for (String query : sql.split(";")) {
			query = query.trim();
			if (query.isEmpty()) {
				continue;
			}
			template.getJdbcOperations().execute(query);
		}
	}

	private int getVersion() {
		try {
			Integer version = queryForInteger("SELECT CurrentVersion FROM SchemaVersion WHERE SchemaType = 'Settings'", new MapSqlParameterSource());
			if (version != null) {
				return version.intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Ignore
		}
		return 0;
	}

	private String loadQuery(String name) throws SQLException {
		try {
			return loadSql("query", name);
		} catch (IOException e) {
			throw new SQLException("Unable to load " + name + " query: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Setting> getSettings(Date timestamp, Version version, Application application, Environment environment) throws SQLException {
		String settingsQuery = loadQuery("get-settings");
		String tagQuery = loadQuery("get-tags");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("applicationId", Long.valueOf(application.getApplicationId()));
		params.addValue("environmentId", Long.valueOf(environment.getEnvironmentId()));
		params.addValue("validAt", timestamp);
		params.addValue("versionId", version == null ? null : Long.valueOf(version.getVersionId()));

		List<RawSetting> settings = template.query(settingsQuery, params, new BeanPropertyRowMapper<>(RawSetting.class));
		Map<String, List<RawSetting>> settingMap = toMap(settings);
		Collection<List<RawSetting>> valueLists = settingMap.values();

		filterComponents(valueLists);
		filterVersions(version, valueLists);
		Map<Long, RawSetting> settingsById = filterDupes(valueLists);
		List<RawTag> tags = template.query(tagQuery, params, new BeanPropertyRowMapper<>(RawTag.class));
		return buildSettings(application, environment, settingsById, tags);
	}

	private List<Setting> buildSettings(Application application, Environment environment, Map<Long, RawSetting> settingsById, List<RawTag> tags) {
		for (RawTag tag : tags) {
			Long settingId = Long.valueOf(tag.getSettingId());
			RawSetting setting = settingsById.get(settingId);
			if (setting == null) {
				continue;
			}
			setting.putTag(tag.getTagType(), tag.getTagValue());
		}
		List<Setting> settings = new ArrayList<>(settingsById.size());
		for (RawSetting raw : settingsById.values()) {
			Setting setting = new Setting();
			setting.setSettingId(raw.getSettingId());
			setting.setKey(raw.getSettingKey());
			setting.setValue(raw.getSettingValue());
			setting.setValidFrom(raw.getValidFrom());
			setting.setValidTo(raw.getValidTo());
			setting.setMinVersion(buildVersion(raw.getFromVersionId(), raw.getFromVersion(), raw.getFromVersionOrder(), application));
			setting.setMaxVersion(buildVersion(raw.getToVersionId(), raw.getToVersion(), raw.getToVersionOrder(), application));
			setting.setComponent(buildComponent(raw));
			setting.setApplication(raw.getApplicationName() == null ? null : application);
			setting.setEnvironment(raw.getEnvironmentName() == null ? null : environment);
			setting.setTags(raw.getTags());
			settings.add(setting);
		}
		return settings;
	}

	private com.internetitem.config.server.dataModel.db.Component buildComponent(RawSetting raw) {
		String name = raw.getComponentName();
		if (name == null) {
			return null;
		}

		com.internetitem.config.server.dataModel.db.Component c = new com.internetitem.config.server.dataModel.db.Component();
		c.setName(name);
		c.setComponentId(raw.getComponentId().longValue());
		return c;
	}

	private Version buildVersion(Long id, String name, Integer order, Application application) {
		if (id == null) {
			return null;
		}
		Version version = new Version();
		version.setVersionId(id.longValue());
		version.setVersionString(name);
		version.setOrdering(order.intValue());
		version.setApplication(application);
		return version;
	}

	private Map<String, List<RawSetting>> toMap(List<RawSetting> settings) {
		Map<String, List<RawSetting>> map = new HashMap<>();
		for (RawSetting setting : settings) {
			String key = setting.getSettingKey().toLowerCase();
			List<RawSetting> settingList = map.get(key);
			if (settingList == null) {
				settingList = new ArrayList<>();
				map.put(key, settingList);
			}
			settingList.add(setting);
		}
		return map;
	}

	private Map<Long, RawSetting> filterDupes(Collection<List<RawSetting>> valueLists) {
		Map<Long, RawSetting> map = new HashMap<>();
		for (List<RawSetting> settings : valueLists) {
			if (settings.size() == 1) {
				RawSetting setting = settings.iterator().next();
				map.put(Long.valueOf(setting.getSettingId()), setting);
				continue;
			}
			RawSetting setting = filterDupes(settings);
			map.put(Long.valueOf(setting.getSettingId()), setting);
		}
		return map;
	}

	private RawSetting filterDupes(List<RawSetting> settings) {
		settings.sort(Comparator.comparing(RawSetting::getLastModifiedTs).thenComparingLong(RawSetting::getSettingId).reversed());
		return settings.iterator().next();
	}

	private void filterComponents(Collection<List<RawSetting>> valueLists) {
		for (List<RawSetting> settings : valueLists) {
			if (settings.size() == 1) {
				continue;
			}
			filterComponents(settings);
		}
	}

	private void filterComponents(List<RawSetting> settings) {
		settings.sort(Comparator.comparing(RawSetting::getComponentOrder).reversed());

		boolean first = true;
		int firstCmp = 0;
		Iterator<RawSetting> i = settings.iterator();
		while (i.hasNext()) {
			RawSetting setting = i.next();
			int cmpVal = setting.getComponentOrder().intValue();
			if (first) {
				firstCmp = cmpVal;
				first = false;
			} else {
				if (cmpVal > firstCmp) {
					i.remove();
				}
			}
		}
	}

	private void filterVersions(Version requestedVersion, Collection<List<RawSetting>> valueLists) {
		if (requestedVersion == null) {
			return;
		}
		for (List<RawSetting> settings : valueLists) {
			if (settings.size() == 1) {
				continue;
			}
			filterVersions(requestedVersion, settings);
		}
	}

	private void filterVersions(Version requestedVersion, List<RawSetting> settings) {
		VersionSortComparator cmp = new VersionSortComparator(requestedVersion.getOrdering());
		settings.sort(cmp);

		boolean first = true;
		int firstCmp = 0;
		Iterator<RawSetting> i = settings.iterator();
		while (i.hasNext()) {
			RawSetting setting = i.next();
			int cmpVal = cmp.versionDiff(setting);
			if (first) {
				firstCmp = cmpVal;
				first = false;
			} else {
				if (cmpVal > firstCmp) {
					i.remove();
				}
			}
		}
	}

	@Override
	public Application getApplication(String name) throws SQLException {
		Application application = queryForObject("SELECT ApplicationId, ApplicationName FROM SettingApplication WHERE ApplicationName = :applicationName", new MapSqlParameterSource("applicationName", name), Application.class);
		if (application == null) {
			return null;
		}

		application.setVersions(template.query("SELECT VersionId, VersionString, Ordering FROM SettingVersion WHERE ApplicationId = :applicationId", new MapSqlParameterSource("applicationId", Long.valueOf(application.getApplicationId())), new BeanPropertyRowMapper<>(Version.class)));
		for (Version version : application.getVersions()) {
			version.setApplication(application);
		}

		List<RawComponent> rcs = template.query("SELECT c.ComponentId, c.ComponentName, sac.Ordering FROM SettingApplicationComponent sac JOIN SettingComponent c on sac.ComponentId = c.ComponentId WHERE sac.ApplicationId = :applicationId ORDER BY sac.Ordering", new MapSqlParameterSource("applicationId", Long.valueOf(application.getApplicationId())), new BeanPropertyRowMapper<>(RawComponent.class));
		List<ApplicationComponent> acs = new ArrayList<>(rcs.size());
		for (RawComponent rc : rcs) {
			com.internetitem.config.server.dataModel.db.Component c = new com.internetitem.config.server.dataModel.db.Component();
			c.setComponentId(rc.getComponentId());
			c.setName(rc.getComponentName());
			ApplicationComponent ac = new ApplicationComponent();
			ac.setApplication(application);
			ac.setComponent(c);
			ac.setOrdering(rc.getOrdering());
			acs.add(ac);
		}
		application.setComponents(acs);

		return application;
	}

	private Integer queryForInteger(String query, MapSqlParameterSource params) {
		List<Integer> l = template.queryForList(query, params, Integer.class);
		if (l.isEmpty()) {
			return null;
		}
		return l.iterator().next();
	}

	private <T> T queryForObject(String query, MapSqlParameterSource params, Class<T> clazz) {
		List<T> l = template.query(query, params, new BeanPropertyRowMapper<>(clazz));
		if (l.isEmpty()) {
			return null;
		}
		return l.iterator().next();
	}

	@Override
	public Environment getEnvironment(String name) throws SQLException {
		return queryForObject("SELECT EnvironmentId, EnvironmentName FROM SettingEnvironment WHERE EnvironmentName = :environmentName", new MapSqlParameterSource("environmentName", name), Environment.class);
	}

	@Override
	public Version getVersion(Application application, String name) throws SQLException {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("applicationId", Long.valueOf(application.getApplicationId()));
		params.addValue("versionString", name);
		Version version = queryForObject("SELECT VersionId, VersionString, Ordering FROM SettingVersion WHERE VersionString = :versionString AND ApplicationId = :applicationId", params, Version.class);
		if (version == null) {
			return null;
		}
		version.setApplication(application);
		return version;
	}

	@Override
	public List<Application> listApplications() {
		return template.query("SELECT ApplicationId, ApplicationName FROM SettingApplication ORDER BY ApplicationName", new MapSqlParameterSource(), new BeanPropertyRowMapper<>(Application.class));
	}

	@Override
	public Application createApplication(String name) {
		Application app = new Application();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ApplicationName", name);
		Number id = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations()).withTableName("SettingApplication").usingGeneratedKeyColumns("ApplicationId").executeAndReturnKey(params);
		app.setApplicationId(id.longValue());
		return app;
	}

	@Override
	public Environment createEnvironment(String name) {
		Environment env = new Environment();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("EnvironmentName", name);
		Number id = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations()).withTableName("SettingEnvironment").usingGeneratedKeyColumns("EnvironmentId").executeAndReturnKey(params);
		env.setEnvironmentId(id.longValue());
		return env;
	}

	@Override
	public com.internetitem.config.server.dataModel.db.Component getComponent(String name) {
		return queryForObject("SELECT ComponentId, ComponentName FROM SettingComponent WHERE ComponentName = :componentName", new MapSqlParameterSource("componentName", name), com.internetitem.config.server.dataModel.db.Component.class);
	}

	@Override
	public com.internetitem.config.server.dataModel.db.Component createComponent(String name) {
		com.internetitem.config.server.dataModel.db.Component comp = new com.internetitem.config.server.dataModel.db.Component();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ComponentName", name);
		Number id = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations()).withTableName("SettingComponent").usingGeneratedKeyColumns("ComponentId").executeAndReturnKey(params);
		comp.setComponentId(id.longValue());
		return comp;
	}

	@Override
	public Application mapComponentToApplication(com.internetitem.config.server.dataModel.db.Component comp, Application app, Integer order) throws SQLException {

		// Deal with the case where the component is already mapped to the application
		MapSqlParameterSource existingParams = new MapSqlParameterSource();
		existingParams.addValue("applicationId", Long.valueOf(app.getApplicationId()));
		existingParams.addValue("componentId", Long.valueOf(comp.getComponentId()));
		Integer existingOrder = queryForInteger("SELECT Ordering FROM SettingApplicationComponent WHERE ApplicationId = :applicationId AND ComponentId = :componentId", existingParams);
		if (existingOrder != null) {
			if (existingOrder.equals(order)) {
				// Haha, no-op
				return getApplication(app.getApplicationName());
			} else {
				template.update("DELETE FROM SettingApplicationComponent WHERE ApplicationId = :applicationId AND ComponentId = :componentId", existingParams);
				existingParams.addValue("order", existingOrder);
				template.update("UPDATE SettingApplicationComponent SET Ordering = Ordering - 1 WHERE ApplicationId = :applicationId AND Ordering > :order", existingParams);
			}
		}

		if (order != null) {
			// An explicit ordering was requested
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("order", order);
			params.addValue("applicationId", Long.valueOf(app.getApplicationId()));
			template.update("UPDATE SettingApplicationComponent SET Ordering = Ordering + 1 WHERE Ordering >= :order AND ApplicationId = :applicationId", params);
		} else {
			// Shove at the end
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("applicationId", Long.valueOf(app.getApplicationId()));
			Integer maxOrder = queryForInteger("SELECT MAX(Ordering) FROM SettingApplicationComponent WHERE ApplicationId = :applicationId", params);
			if (maxOrder != null) {
				order = Integer.valueOf(maxOrder.intValue() + 1);
			} else {
				order = Integer.valueOf(1);
			}
		}

		// Actual insert
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("order", order);
		params.addValue("applicationId", Long.valueOf(app.getApplicationId()));
		params.addValue("componentId", Long.valueOf(comp.getComponentId()));
		template.update("INSERT INTO SettingApplicationComponent (ApplicationId, ComponentId, Ordering) VALUES (:applicationId, :componentId, :ordering)", params);

		return getApplication(app.getApplicationName());
	}

	@Override
	public Version createVersion(Application application, String name, Integer order, boolean shift) throws SQLException {
		Version existingVersion = getVersion(application, name);
		if (existingVersion != null) {
			throw new RuntimeException("Version already exists");
		}

		if (shift && order != null) {
			template.update("UPDATE SettingVersion SET Ordering = Ordering + 1 WHERE ApplicationId = :applicationId AND Ordering >= :order", new MapSqlParameterSource("applicationId", Long.valueOf(application.getApplicationId())).addValue("order", order));
		}
		if (order == null) {
			order = queryForInteger("SELECT MAX(Ordering) FROM SettingVersion WHERE ApplicationId = :applicationId", new MapSqlParameterSource("applicationId", Long.valueOf(application.getApplicationId())));
			if (order == null) {
				order = Integer.valueOf(1);
			}
		}

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("VersionString", name);
		params.addValue("ApplicationId", Long.valueOf(application.getApplicationId()));
		params.addValue("Ordering", order);
		Number id = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations()).withTableName("SettingVersion").usingGeneratedKeyColumns("VersionId").executeAndReturnKey(params);

		Version version = new Version();
		version.setVersionId(id.longValue());
		version.setApplication(application);
		version.setOrdering(order.intValue());
		version.setVersionString(name);

		return version;
	}

	@Override
	public Setting setValue(String key, String value, Date validFrom, Date validTo, Version versionFrom, Version versionTo, com.internetitem.config.server.dataModel.db.Component component, Application application, Environment environment, Map<String, String> tags) {
		Date now = new Date();

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("SettingKey", key);
		params.addValue("SettingValue", value);

		if (validFrom == null) {
			validFrom = now;
		}
		params.addValue("ValidFrom", validFrom);
		if (validTo == null) {
			validTo = getEndOfTime();
		}
		params.addValue("ValidTo", validTo);

		if (versionFrom != null) {
			params.addValue("VersionFrom", Long.valueOf(versionFrom.getVersionId()));
		}
		if (versionTo != null) {
			params.addValue("VersionTo", Long.valueOf(versionTo.getVersionId()));
		}

		if (component != null) {
			params.addValue("ComponentId", Long.valueOf(component.getComponentId()));
		}
		if (application != null) {
			params.addValue("ApplicationId", Long.valueOf(application.getApplicationId()));
		}
		if (environment != null) {
			params.addValue("EnvironmentId", Long.valueOf(environment.getEnvironmentId()));
		}

		params.addValue("LastModifiedTs", now);

		Number id = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations()).withTableName("SettingValues").usingGeneratedKeyColumns("SettingId").executeAndReturnKey(params);
		long settingId = id.longValue();

		for (Map.Entry<String, String> entry : tags.entrySet()) {
			template.update("INSERT INTO SettingTag (SettingId, TagType, TagValue) VALUES (:settingId, :key, :value)", new MapSqlParameterSource("settingId", Long.valueOf(settingId)).addValue("key", entry.getKey()).addValue("value", entry.getValue()));
		}

		Setting setting = new Setting();
		setting.setSettingId(settingId);
		setting.setKey(key);
		setting.setValue(value);
		setting.setValidFrom(validFrom);
		setting.setValidTo(validTo);
		setting.setMinVersion(versionFrom);
		setting.setMaxVersion(versionTo);
		setting.setComponent(component);
		setting.setApplication(application);
		setting.setEnvironment(environment);
		setting.setTags(tags);

		return setting;
	}

	private Date getEndOfTime() {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, 9999);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return cal.getTime();
	}
}
