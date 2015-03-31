package com.internetitem.config.server.db.dao;

import com.internetitem.config.server.db.dataModel.SettingApplicationGroup;
import com.internetitem.config.server.db.dataModel.SettingComponent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ComponentDao extends AbstractDao {

	public SettingComponent getComponentByName(SettingApplicationGroup applicationGroup, String name) {
		return fetchZeroOrOne(
				entityManager.createNamedQuery("Component.fetchByName", SettingComponent.class)
						.setParameter("componentName", name)
						.setParameter("applicationGroup", applicationGroup)
		);
	}

	public SettingComponent createComponent(SettingApplicationGroup applicationGroup, String componentName) {
		SettingComponent component = new SettingComponent(componentName, applicationGroup, new Date());
		entityManager.persist(component);
		return component;
	}

	public List<SettingComponent> getAllComponents(SettingApplicationGroup applicationGroup) {
		return entityManager.createNamedQuery("Component.fetchAll", SettingComponent.class).setParameter("applicationGroup", applicationGroup).getResultList();
	}

}
