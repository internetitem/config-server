package com.internetitem.config.server.service.dataModel;

import java.util.Date;
import java.util.Map;

public class SetValueRequest {

	private String key;
	private String value;
	private Date validFrom;
	private Date validTo;
	private String versionFrom;
	private String versionTo;
	private String application;
	private String component;
	private String environment;
	private Map<String, String> tags;

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

	public String getVersionFrom() {
		return versionFrom;
	}

	public void setVersionFrom(String versionFrom) {
		this.versionFrom = versionFrom;
	}

	public String getVersionTo() {
		return versionTo;
	}

	public void setVersionTo(String versionTo) {
		this.versionTo = versionTo;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}
}
