package com.internetitem.config.server.security;

import com.internetitem.config.server.db.dataModel.*;

import java.util.HashSet;
import java.util.Set;

public class PermissionSet {

	private SettingUser user;

	private String hostname;

	// SUPERUSER
	private boolean globalAdmin;

	// GET_SETTINGS
	private Set<Long> readApplications;

	// ADMIN_APP_GROUP
	private Set<Long> adminAppGroups;

	// SET_VALUE
	private boolean svGlobal;
	private Set<Long> svAppGroup;

	public PermissionSet(LoggedInUserDetails permissions, String hostname) {
		this.user = permissions.getUser();
		this.hostname = hostname;

		this.readApplications = new HashSet<>();
		this.adminAppGroups = new HashSet<>();
		this.svAppGroup = new HashSet<>();

		for (SettingPermissionGrant grant : permissions.getGrants()) {
			SettingPermissionType permissionType = grant.getPermissionType();
			SettingPermissionGrantType grantType = grant.getGrantType();

			switch (permissionType) {
				case SUPERUSER: globalAdmin = true; break;
				case GET_SETTINGS: readApplications.add(Long.valueOf(grant.getApplication().getApplicationId())); break;
				case ADMIN_APP_GROUP: adminAppGroups.add(Long.valueOf(grant.getApplicationGroup().getApplicationGroupId())); break;
				case SET_VALUE:
					switch (grantType) {
						case GLOBAL: svGlobal = true; break;
						case APPLICATION_GROUP: svAppGroup.add(Long.valueOf(grant.getApplicationGroup().getApplicationGroupId())); break;
					}
					break;
			}
		}
	}

	public boolean isGlobalAdmin() {
		return globalAdmin;
	}

	public boolean canAdminAppGroup(SettingApplicationGroup appGroup) {
		return isGlobalAdmin() || adminAppGroups.contains(Long.valueOf(appGroup.getApplicationGroupId()));
	}

	public boolean canSetValue(SettingApplicationGroup appGroup) {
		return canAdminAppGroup(appGroup) || svAppGroup.contains(Long.valueOf(appGroup.getApplicationGroupId()));
	}

	public boolean canReadApplication(SettingApplicationGroup appGroup, SettingApplication app) {
		return canSetValue(appGroup) || readApplications.contains(Long.valueOf(app.getApplicationId()));
	}

	public SettingUser getUser() {
		return user;
	}

	public String getHostname() {
		return hostname;
	}
}
