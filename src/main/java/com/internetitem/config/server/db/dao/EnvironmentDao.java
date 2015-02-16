package com.internetitem.config.server.db.dao;

import com.internetitem.config.server.db.dataModel.SettingApplicationGroup;
import com.internetitem.config.server.db.dataModel.SettingEnvironment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class EnvironmentDao extends AbstractDao {

	public SettingEnvironment getEnvironmentByName(SettingApplicationGroup applicationGroup, String name) {
		return fetchZeroOrOne(
				entityManager.createNamedQuery("Environment.fetchByName", SettingEnvironment.class)
						.setParameter("environmentName", name)
						.setParameter("applicationGroup", applicationGroup)
		);
	}

	public SettingEnvironment createEnvironment(SettingApplicationGroup applicationGroup, String environmentName) {
		SettingEnvironment env = new SettingEnvironment(environmentName, applicationGroup, new Date());
		entityManager.persist(env);
		return env;
	}

	public List<SettingEnvironment> getAllEnvironments(SettingApplicationGroup applicationGroup) {
		return entityManager.createNamedQuery("Environment.fetchAll", SettingEnvironment.class).setParameter("applicationGroup", applicationGroup).getResultList();
	}
}
