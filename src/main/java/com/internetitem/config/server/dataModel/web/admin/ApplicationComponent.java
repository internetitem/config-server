package com.internetitem.config.server.dataModel.web.admin;

public class ApplicationComponent {

	private Application application;
	private Component component;
	private int ordering;

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
}
