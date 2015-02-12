package com.internetitem.config.server.db.dao;

import com.internetitem.config.server.db.dataModel.SettingUser;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserDao extends AbstractDao {

	public SettingUser getUserWithPermissions(String username) {
		Set<SettingUser> users = new HashSet<>(entityManager.createNamedQuery("User.fetchWithPermissions", SettingUser.class)
				.setParameter("username", username)
				.getResultList());
		if (users.isEmpty()) {
			return null;
		}
		return users.iterator().next();
	}
}
