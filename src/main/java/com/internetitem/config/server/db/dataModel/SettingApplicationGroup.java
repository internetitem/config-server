package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;

@Entity
@Table(name="SettingApplicationGroup")
@NamedQueries({
		@NamedQuery(name = "ApplicationGroup.fetchByName", query = "SELECT ag FROM SettingApplicationGroup ag WHERE ag.applicationGroupName = :applicationGroupName"),
		@NamedQuery(name = "ApplicationGroup.fetchAll", query = "SELECT ag FROM SettingApplicationGroup ag")
})
public class SettingApplicationGroup {

	@Id
	@GeneratedValue(generator = "seqApplicationGroupId")
	@SequenceGenerator(name = "seqApplicationGroupId", sequenceName = "SeqSettingApplicationGroup")
	@Column(name = "ApplicationGroupId")
	private long applicationGroupId;

	@Column(name="ApplicationGroupName", nullable = false, unique = true)
	private String applicationGroupName;

	protected SettingApplicationGroup() {
	}

	public SettingApplicationGroup(String applicationGroupName) {
		this.applicationGroupName = applicationGroupName;
	}

	public long getApplicationGroupId() {
		return applicationGroupId;
	}

	public void setApplicationGroupId(long applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}

	public String getApplicationGroupName() {
		return applicationGroupName;
	}

	public void setApplicationGroupName(String applicationGroupName) {
		this.applicationGroupName = applicationGroupName;
	}
}
