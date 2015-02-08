package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;

@Entity
@Table(name = "SettingComponent")
public class SettingComponent {

	@Id
	@GeneratedValue(generator = "seqComponent")
	@SequenceGenerator(name = "seqComponent", sequenceName = "SeqSettingComponent")
	@Column(name = "ComponentId")
	private long componentId;

	@Column(name = "ComponentName", unique = true, nullable = false, updatable = false, length = 50)
	private String componentName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ApplicationGroupId", referencedColumnName = "ApplicationGroupId", nullable = false)
	private SettingApplicationGroup applicationGroup;

	protected SettingComponent() {
	}

	public SettingComponent(String componentName, SettingApplicationGroup applicationGroup) {
		this.componentName = componentName;
		this.applicationGroup = applicationGroup;
	}

	public SettingComponent(String componentNameName) {
		this.componentName = componentNameName;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public long getComponentId() {
		return componentId;
	}

	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}

	public SettingApplicationGroup getApplicationGroup() {
		return applicationGroup;
	}

	public void setApplicationGroup(SettingApplicationGroup applicationGroup) {
		this.applicationGroup = applicationGroup;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SettingComponent)) return false;

		SettingComponent that = (SettingComponent) o;

		if (componentId != that.componentId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (componentId ^ (componentId >>> 32));
	}
}
