package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;

@Entity
@Table(name="SettingApplicationGroup")
public class SettingApplicationGroup {

	@Id
	@GeneratedValue(generator = "seqApplicationGroupId")
	@SequenceGenerator(name = "seqApplicationGroupId", sequenceName = "SeqApplicationGroup")
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
