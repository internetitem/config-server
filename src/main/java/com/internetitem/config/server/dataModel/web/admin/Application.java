package com.internetitem.config.server.dataModel.web.admin;

import java.util.List;

public class Application {

	private long applicationId;
	private String applicationName;
	
	private List<Version> versions;
	private List<ApplicationComponent> components;

	public long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public List<ApplicationComponent> getComponents() {
		return components;
	}

	public void setComponents(List<ApplicationComponent> components) {
		this.components = components;
	}
}
