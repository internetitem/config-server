package com.internetitem.config.server.security;

import com.internetitem.config.server.db.dao.UserDao;
import com.internetitem.config.server.db.dataModel.SettingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class SettingsUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SettingUser user = fetchUser(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user found");
		}
		if (!user.isActive()) {
			throw new UsernameNotFoundException("User not active");
		}
		return new LoggedInUserDetails(user);
	}

	@Transactional
	private SettingUser fetchUser(String username) {
		return userDao.getUserWithPermissions(username);
	}
}
