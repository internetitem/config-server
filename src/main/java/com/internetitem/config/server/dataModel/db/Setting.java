package com.internetitem.config.server.dataModel.db;

import java.util.Date;
import java.util.Map;

public class Setting {

	private long settingId;

	private String key;
	private String value;

	private Date validFrom;
	private Date validTo;

	private Version minVersion;
	private Version maxVersion;

	private Component component;
	private Application application;
	private Environment environment;

	private Map<String, String> tags;

	public long getSettingId() {
		return settingId;
	}

	public void setSettingId(long settingId) {
		this.settingId = settingId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public Version getMinVersion() {
		return minVersion;
	}

	public void setMinVersion(Version minVersion) {
		this.minVersion = minVersion;
	}

	public Version getMaxVersion() {
		return maxVersion;
	}

	public void setMaxVersion(Version maxVersion) {
		this.maxVersion = maxVersion;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}
}
