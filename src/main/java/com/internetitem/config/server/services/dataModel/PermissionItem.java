package com.internetitem.config.server.services.dataModel;

import com.internetitem.config.server.db.dataModel.SettingPermissionGrantType;
import com.internetitem.config.server.db.dataModel.SettingPermissionType;

public class PermissionItem {
	private SettingPermissionGrantType grantType;
	private SettingPermissionType permissionType;
	
	private Long applicationGroupId;
	private Long applicationId;
	private Long componentId;

	public PermissionItem(SettingPermissionGrantType grantType, SettingPermissionType permissionType, Long applicationGroupId, Long applicationId, Long componentId) {
		this.grantType = grantType;
		this.permissionType = permissionType;
		this.applicationGroupId = applicationGroupId;
		this.applicationId = applicationId;
		this.componentId = componentId;
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

	public Long getApplicationGroupId() {
		return applicationGroupId;
	}

	public void setApplicationGroupId(Long applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getComponentId() {
		return componentId;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}
}
