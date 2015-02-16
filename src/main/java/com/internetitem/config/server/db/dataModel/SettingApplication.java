package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "SettingApplication")
public class SettingApplication {

	@Id
	@GeneratedValue(generator = "seqApplication")
	@SequenceGenerator(name = "seqApplication", sequenceName = "SeqSettingApplication")
	@Column(name = "ApplicationId")
	private long applicationId;

	@Column(name = "ApplicationName", nullable = false, updatable = false, length = 50)
	private String applicationName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ApplicationGroupId", referencedColumnName = "ApplicationGroupId", nullable = false)
	private SettingApplicationGroup applicationGroup;

	@OneToMany(mappedBy = "application")
	private Set<SettingApplicationComponent> components = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ApplicationId", referencedColumnName = "ApplicationId")
	private Set<SettingVersion> versions = new HashSet<>();

	@Column(name = "CreatedTs", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTimestamp;

	@Column(name = "DeletedTs")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedTimestamp;

	protected SettingApplication() {
	}

	public SettingApplication(String applicationName, SettingApplicationGroup applicationGroup, Date createdTimestamp) {
		this.applicationName = applicationName;
		this.applicationGroup = applicationGroup;
		this.createdTimestamp = createdTimestamp;
	}

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

	public SettingApplicationGroup getApplicationGroup() {
		return applicationGroup;
	}

	public void setApplicationGroup(SettingApplicationGroup applicationGroup) {
		this.applicationGroup = applicationGroup;
	}

	public Set<SettingApplicationComponent> getComponents() {
		return components;
	}

	public void setComponents(Set<SettingApplicationComponent> components) {
		this.components = components;
	}

	public Set<SettingVersion> getVersions() {
		return versions;
	}

	public void setVersions(Set<SettingVersion> versions) {
		this.versions = versions;
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
		if (!(o instanceof SettingApplication)) return false;

		SettingApplication that = (SettingApplication) o;

		if (applicationId != that.applicationId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (applicationId ^ (applicationId >>> 32));
	}
}
