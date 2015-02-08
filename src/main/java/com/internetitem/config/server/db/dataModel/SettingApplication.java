package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SettingApplication")
public class SettingApplication {

	@Id
	@GeneratedValue(generator = "seqApplication")
	@SequenceGenerator(name = "seqApplication", sequenceName = "SeqSettingApplication")
	@Column(name = "ApplicationId")
	private long applicationId;

	@Column(name = "ApplicationName", unique = true, nullable = false, updatable = false, length = 50)
	private String applicationName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ApplicationGroupId", referencedColumnName = "ApplicationGroupId", nullable = false)
	private SettingApplicationGroup applicationGroup;

	@ManyToMany
	@JoinTable(name = "SettingApplicationComponent", joinColumns = @JoinColumn(name = "ApplicationId"), inverseJoinColumns = @JoinColumn(name = "ComponentId"))
	@OrderColumn(name = "Ordering")
	private List<SettingComponent> components = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ApplicationId", referencedColumnName = "ApplicationId")
	@OrderBy("ordering")
	private List<SettingVersion> versions = new ArrayList<>();

	protected SettingApplication() {
	}

	public SettingApplication(String applicationName, SettingApplicationGroup applicationGroup) {
		this.applicationName = applicationName;
		this.applicationGroup = applicationGroup;
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

	public List<SettingComponent> getComponents() {
		return components;
	}

	public void setComponents(List<SettingComponent> components) {
		this.components = components;
	}

	public List<SettingVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<SettingVersion> versions) {
		this.versions = versions;
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
