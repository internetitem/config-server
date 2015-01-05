package com.internetitem.config.server.dao.rdbms.dataModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RawSetting {
	private long settingId;
	private String settingKey;
	private String settingValue;

	private Date validFrom;
	private Date validTo;

	private String fromVersion;
	private Integer fromVersionOrder;
	private Long fromVersionId;

	private String toVersion;
	private Integer toVersionOrder;
	private Long toVersionId;

	private String componentName;
	private Long componentId;
	private Integer componentOrder;

	private String applicationName;
	private Long applicationId;

	private String environmentName;
	private Long environmentId;

	private Date lastModifiedTs;

	private Map<String, String> tags;

	public long getSettingId() {
		return settingId;
	}

	public void setSettingId(long settingId) {
		this.settingId = settingId;
	}

	public String getSettingKey() {
		return settingKey;
	}

	public void setSettingKey(String settingKey) {
		this.settingKey = settingKey;
	}

	public String getSettingValue() {
		return settingValue;
	}

	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
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

	public String getFromVersion() {
		return fromVersion;
	}

	public void setFromVersion(String fromVersion) {
		this.fromVersion = fromVersion;
	}

	public Integer getFromVersionOrder() {
		return fromVersionOrder;
	}

	public void setFromVersionOrder(Integer fromVersionOrder) {
		this.fromVersionOrder = fromVersionOrder;
	}

	public Long getFromVersionId() {
		return fromVersionId;
	}

	public void setFromVersionId(Long fromVersionId) {
		this.fromVersionId = fromVersionId;
	}

	public String getToVersion() {
		return toVersion;
	}

	public void setToVersion(String toVersion) {
		this.toVersion = toVersion;
	}

	public Integer getToVersionOrder() {
		return toVersionOrder;
	}

	public void setToVersionOrder(Integer toVersionOrder) {
		this.toVersionOrder = toVersionOrder;
	}

	public Long getToVersionId() {
		return toVersionId;
	}

	public void setToVersionId(Long toVersionId) {
		this.toVersionId = toVersionId;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public Long getComponentId() {
		return componentId;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public Integer getComponentOrder() {
		return componentOrder;
	}

	public void setComponentOrder(Integer componentOrder) {
		this.componentOrder = componentOrder;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public Long getEnvironmentId() {
		return environmentId;
	}

	public void setEnvironmentId(Long environmentId) {
		this.environmentId = environmentId;
	}

	public Date getLastModifiedTs() {
		return lastModifiedTs;
	}

	public void setLastModifiedTs(Date lastModifiedTs) {
		this.lastModifiedTs = lastModifiedTs;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void putTag(String key, String value) {
		if (this.tags == null) {
			tags = new HashMap<>();
		}
		tags.put(key, value);
	}

}
