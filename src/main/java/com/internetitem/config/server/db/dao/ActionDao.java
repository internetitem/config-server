package com.internetitem.config.server.db.dao;

import com.internetitem.config.server.db.dataModel.*;
import com.internetitem.config.server.security.PermissionSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ActionDao extends AbstractDao {

	@Autowired
	private PermissionSet permissionSet;

	public SettingAction createAction(SettingApplicationGroup appGroup) {
		SettingAction action = new SettingAction(permissionSet.getUser(), permissionSet.getHostname(), "API", null, appGroup, new Date());
		entityManager.persist(action);
		return action;
	}

	public SettingActionStructureChange createStructureChange(SettingAction action, SettingApplication application, SettingActionType actionType) {
		SettingActionStructureChange change = new SettingActionStructureChange(action, SettingActionStructureChangeType.APPLICATION, actionType, application, null, null, null);
		entityManager.persist(change);
		return change;
	}

	public SettingActionStructureChange createStructureChange(SettingAction action, SettingComponent component, SettingActionType actionType) {
		SettingActionStructureChange change = new SettingActionStructureChange(action, SettingActionStructureChangeType.COMPONENT, actionType, null, component, null, null);
		entityManager.persist(change);
		return change;
	}

	public SettingActionStructureChange createStructureChange(SettingAction action, SettingEnvironment environment, SettingActionType actionType) {
		SettingActionStructureChange change = new SettingActionStructureChange(action, SettingActionStructureChangeType.ENVIRONMENT, actionType, null, null, null, environment);
		entityManager.persist(change);
		return change;
	}

	public SettingActionStructureChange createStructureChange(SettingAction action, SettingVersion version, SettingActionType actionType) {
		SettingActionStructureChange change = new SettingActionStructureChange(action, SettingActionStructureChangeType.VERSION, actionType, null, null, version, null);
		entityManager.persist(change);
		return change;
	}

}
