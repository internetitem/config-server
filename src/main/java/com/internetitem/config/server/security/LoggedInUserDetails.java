package com.internetitem.config.server.security;

import com.internetitem.config.server.db.dataModel.SettingPermissionGrant;
import com.internetitem.config.server.db.dataModel.SettingUser;
import com.internetitem.config.server.db.dataModel.SettingUserGroup;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LoggedInUserDetails extends User {

	private Set<SettingPermissionGrant> grants;

	public LoggedInUserDetails(SettingUser user) {
		super(user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("User")));
		grants = new HashSet<>();
		for (SettingUserGroup group : user.getGroups()) {
			grants.addAll(group.getGrants());
		}
	}

	public Set<SettingPermissionGrant> getGrants() {
		return grants;
	}
}
