package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;

@Entity
@Table(name = "SettingPermissionGrant")
public class SettingPermissionGrant {

	@Id
	@GeneratedValue(generator = "seqPermissionGrant")
	@SequenceGenerator(name = "seqPermissionGrant", sequenceName = "SeqSettingPermissiongrant")
	@Column(name = "PermissionGrantId")
	private long permissionGrantId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "UserGroupId", referencedColumnName = "UserGroupId", nullable = false)
	private SettingUserGroup group;

	@Convert(converter = SettingPermissionGrantType.SettingPermissionGrantTypeConverter.class)
	@Column(name = "GrantTypeId", nullable = false)
	private SettingPermissionGrantType grantType;

	@Convert(converter = SettingPermissionType.SettingPermissionTypeConverter.class)
	@Column(name = "PermissionTypeId", nullable = false)
	private SettingPermissionType permissionType;

	@ManyToOne
	@JoinColumn(name = "ApplicationGroupId", referencedColumnName = "ApplicationGroupId")
	private SettingApplicationGroup applicationGroup;

	@ManyToOne
	@JoinColumn(name = "ApplicationId", referencedColumnName = "ApplicationId")
	private SettingApplication application;

	@ManyToOne
	@JoinColumn(name = "ComponentId", referencedColumnName = "ComponentId")
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

	public long getPermissionGrantId() {
		return permissionGrantId;
	}

	public void setPermissionGrantId(long permissionGrantId) {
		this.permissionGrantId = permissionGrantId;
	}

	public SettingUserGroup getGroup() {
		return group;
	}

	public void setGroup(SettingUserGroup group) {
		this.group = group;
	}

	public SettingPermissionGrantType getGrantType() {
		return grantType;
	}

	public void setGrantType(SettingPermissionGrantType grantType) {
		this.grantType = grantType;
	}

	public SettingPermissionType getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(SettingPermissionType permissionType) {
		this.permissionType = permissionType;
	}

	public SettingApplicationGroup getApplicationGroup() {
		return applicationGroup;
	}

	public void setApplicationGroup(SettingApplicationGroup applicationGroup) {
		this.applicationGroup = applicationGroup;
	}

	public SettingApplication getApplication() {
		return application;
	}

	public void setApplication(SettingApplication application) {
		this.application = application;
	}

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
