package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SettingEnvironment")
@NamedQueries({
		@NamedQuery(name = "Environment.fetchByName", query = "SELECT e FROM SettingEnvironment e WHERE e.environmentName = :environmentName and e.applicationGroup = :applicationGroup"),
		@NamedQuery(name = "Environment.fetchAll", query = "SELECT e FROM SettingEnvironment e WHERE e.applicationGroup = :applicationGroup")
})
public class SettingEnvironment {

	@Id
	@GeneratedValue(generator="seqEnvironment")
	@SequenceGenerator(name="seqEnvironment", sequenceName = "SeqSettingEnvironment")
	@Column(name = "EnvironmentId")
	private long environmentId;

	@Column(name = "EnvironmentName", nullable = false, updatable = false, length = 50)
	private String environmentName;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ApplicationGroupId", referencedColumnName = "ApplicationGroupId", nullable = false)
	private SettingApplicationGroup applicationGroup;

	@Column(name = "CreatedTs", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTimestamp;

	@Column(name = "DeletedTs")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedTimestamp;

	protected SettingEnvironment() {
	}

	public SettingEnvironment(String environmentName, SettingApplicationGroup applicationGroup, Date createdTimestamp) {
		this.environmentName = environmentName;
		this.applicationGroup = applicationGroup;
		this.createdTimestamp = createdTimestamp;
	}

	public long getEnvironmentId() {
		return environmentId;
	}

	public void setEnvironmentId(long environmentId) {
		this.environmentId = environmentId;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
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
		if (!(o instanceof SettingEnvironment)) return false;

		SettingEnvironment that = (SettingEnvironment) o;

		if (environmentId != that.environmentId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (environmentId ^ (environmentId >>> 32));
	}
}
