package com.internetitem.config.server.db.dao;

import com.internetitem.config.server.db.dataModel.SettingEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnvironmentDao extends AbstractDao {

	public SettingEnvironment getEnvironmentByName(String name) {
		return fetchZeroOrOne(entityManager.createNamedQuery("Environment.fetchByName", SettingEnvironment.class).setParameter("environmentName", name));
	}

	public SettingEnvironment createEnvironment(String environmentName) {
		SettingEnvironment env = new SettingEnvironment(environmentName, null);
		entityManager.persist(env);
		return env;
	}

	public List<SettingEnvironment> getAllEnvironments() {
		return entityManager.createNamedQuery("Environment.fetchAll", SettingEnvironment.class).getResultList();
	}
}
