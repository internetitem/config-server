package com.internetitem.config.server.services;

import com.internetitem.config.server.db.dao.ActionDao;
import com.internetitem.config.server.db.dao.ApplicationGroupDao;
import com.internetitem.config.server.db.dao.ComponentDao;
import com.internetitem.config.server.db.dataModel.SettingAction;
import com.internetitem.config.server.db.dataModel.SettingActionType;
import com.internetitem.config.server.db.dataModel.SettingApplicationGroup;
import com.internetitem.config.server.db.dataModel.SettingComponent;
import com.internetitem.config.server.security.PermissionSet;
import com.internetitem.config.server.services.dataModel.request.CreateRequest;
import com.internetitem.config.server.services.dataModel.response.CreateResponse;
import com.internetitem.config.server.services.exception.EntityNotFoundException;
import com.internetitem.config.server.services.exception.InsufficientPermissionsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Service
@Path("component/{applicationGroup}")
public class ComponentService extends AbstractService {

	@Autowired
	private ComponentDao componentDao;

	@Autowired
	private ApplicationGroupDao applicationGroupDao;

	@Autowired
	private ActionDao actionDao;

	@Autowired
	private PermissionSet permissionSet;

	@GET
	@Transactional
	public List<SettingComponent> getComponents(@PathParam("applicationGroup") String applicationGroupName) throws EntityNotFoundException, InsufficientPermissionsException {
		SettingApplicationGroup appGroup = applicationGroupDao.getApplicationGroupByName(applicationGroupName);
		if (appGroup == null) {
			return notFound("Unknown Application Group");
		}

		ensurePermission(permissionSet.canAdminAppGroup(appGroup));

		return componentDao.getAllComponents(appGroup);
	}

	@PUT
	@Transactional
	public CreateResponse createComponent(@PathParam("applicationGroup") String applicationGroupName, @NotNull CreateRequest request) throws InsufficientPermissionsException {
		SettingApplicationGroup appGroup = applicationGroupDao.getApplicationGroupByName(applicationGroupName);
		if (appGroup == null) {
			return new CreateResponse("Unknown Application Group");
		}

		ensurePermission(permissionSet.canAdminAppGroup(appGroup));

		String componentName = request.getName();

		SettingComponent oldComponent = componentDao.getComponentByName(appGroup, componentName);
		if (oldComponent != null) {
			return new CreateResponse("Component already exists");
		}

		SettingComponent component = componentDao.createComponent(appGroup, componentName);
		SettingAction action = actionDao.createAction(appGroup);
		actionDao.createStructureChange(action, component, SettingActionType.CREATE);

		return new CreateResponse("Created Component " + componentName, component.getComponentId());
	}

}
