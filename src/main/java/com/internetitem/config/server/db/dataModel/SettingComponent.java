package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SettingComponent")
@NamedQueries({
		@NamedQuery(name = "Component.fetchByName", query = "SELECT c FROM SettingComponent c WHERE c.componentName = :componentName and c.applicationGroup = :applicationGroup"),
		@NamedQuery(name = "Component.fetchAll", query = "SELECT c FROM SettingComponent c WHERE c.applicationGroup = :applicationGroup")
})
public class SettingComponent {

	@Id
	@GeneratedValue(generator = "seqComponent")
	@SequenceGenerator(name = "seqComponent", sequenceName = "SeqSettingComponent")
	@Column(name = "ComponentId")
	private long componentId;

	@Column(name = "ComponentName", nullable = false, updatable = false, length = 50)
	private String componentName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ApplicationGroupId", referencedColumnName = "ApplicationGroupId", nullable = false)
	private SettingApplicationGroup applicationGroup;

	@Column(name = "CreatedTs", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTimestamp;

	@Column(name = "DeletedTs")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedTimestamp;

	protected SettingComponent() {
	}

	public SettingComponent(String componentName, SettingApplicationGroup applicationGroup, Date createdTimestamp) {
		this.componentName = componentName;
		this.applicationGroup = applicationGroup;
		this.createdTimestamp = createdTimestamp;
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

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Date getDeletedTimestamp() {
		return deletedTimestamp;
	}

	public void setDeletedTimestamp(Date deletedTimestamp) {
		this.deletedTimestamp = deletedTimestamp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SettingComponent)) return false;

		SettingComponent that = (SettingComponent) o;

		return componentId == that.componentId;
	}

	@Override
	public int hashCode() {
		return (int) (componentId ^ (componentId >>> 32));
	}
}
