package com.internetitem.config.server.dao.rdbms.dataModel;

public class RawComponent {
	private long componentId;
	private String componentName;
	private int ordering;

	public long getComponentId() {
		return componentId;
	}

	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
}
