package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;

@Entity
@Table(name = "SettingPermissionGrant")
public class SettingPermissionGrant {

	private long permissionGrantId;

	private SettingUserGroup group;

	private SettingPermissionGrantType grantType;

	private SettingPermissionType permissionType;

	private SettingApplicationGroup applicationGroup;

	private SettingApplication application;

	private SettingComponent component;

	protected SettingPermissionGrant() {
	}

	public SettingPermissionGrant(SettingUserGroup group, SettingPermissionGrantType grantType, SettingPermissionType permissionType, SettingApplicationGroup applicationGroup, SettingApplication application, SettingComponent component) {
		this.group = group;
		this.grantType = grantType;
		this.permissionType = permissionType;
		this.applicationGroup = applicationGroup;
		this.application = application;
		this.component = component;
	}

	@Id
	@GeneratedValue(generator = "seqPermissionGrant")
	@SequenceGenerator(name = "seqPermissionGrant", sequenceName = "SeqSettingPermissiongrant")
	@Column(name = "PermissionGrantId")
	public long getPermissionGrantId() {
		return permissionGrantId;
	}

	public void setPermissionGrantId(long permissionGrantId) {
		this.permissionGrantId = permissionGrantId;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "UserGroupId", referencedColumnName = "UserGroupId", nullable = false)
	public SettingUserGroup getGroup() {
		return group;
	}

	public void setGroup(SettingUserGroup group) {
		this.group = group;
	}

	@Convert(converter = SettingPermissionGrantType.SettingPermissionGrantTypeConverter.class)
	@Column(name = "GrantTypeId", nullable = false)
	public SettingPermissionGrantType getGrantType() {
		return grantType;
	}

	public void setGrantType(SettingPermissionGrantType grantType) {
		this.grantType = grantType;
	}

	@Convert(converter = SettingPermissionType.SettingPermissionTypeConverter.class)
	@Column(name = "PermissionTypeId", nullable = false)
	public SettingPermissionType getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(SettingPermissionType permissionType) {
		this.permissionType = permissionType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ApplicationGroupId", referencedColumnName = "ApplicationGroupId")
	public SettingApplicationGroup getApplicationGroup() {
		return applicationGroup;
	}

	public void setApplicationGroup(SettingApplicationGroup applicationGroup) {
		this.applicationGroup = applicationGroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ApplicationId", referencedColumnName = "ApplicationId")
	public SettingApplication getApplication() {
		return application;
	}

	public void setApplication(SettingApplication application) {
		this.application = application;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ComponentId", referencedColumnName = "ComponentId")
	public SettingComponent getComponent() {
		return component;
	}

	public void setComponent(SettingComponent component) {
		this.component = component;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SettingPermissionGrant)) return false;

		SettingPermissionGrant that = (SettingPermissionGrant) o;

		if (permissionGrantId != that.permissionGrantId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (permissionGrantId ^ (permissionGrantId >>> 32));
	}
}
