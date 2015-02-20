package com.internetitem.config.server.security;

import com.internetitem.config.server.services.dataModel.PermissionItem;

import java.util.List;

public class PermissionSet {

	private List<PermissionItem> permissions;

	public PermissionSet(List<PermissionItem> permissions) {
		this.permissions = permissions;
	}

	public List<PermissionItem> getPermissions() {
		return permissions;
	}
}
