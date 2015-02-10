package com.internetitem.config.server.db.dao;

import com.internetitem.config.server.db.dataModel.SettingApplicationGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationGroupDao extends AbstractDao {

	public SettingApplicationGroup getApplicationGroupByName(String name) {
		return fetchZeroOrOne(
				entityManager.createNamedQuery("ApplicationGroup.fetchByName", SettingApplicationGroup.class)
						.setParameter("applicationGroupName", name)
		);
	}

	public SettingApplicationGroup createApplicationGroup(String name) {
		SettingApplicationGroup ag = new SettingApplicationGroup(name);
		entityManager.persist(ag);
		return ag;
	}

	public List<SettingApplicationGroup> getAllApplicationGroups() {
		return entityManager.createNamedQuery("ApplicationGroup.fetchAll", SettingApplicationGroup.class).getResultList();
	}
}
