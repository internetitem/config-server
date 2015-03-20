package com.internetitem.config.server.services;

import com.internetitem.config.server.db.dao.ActionDao;
import com.internetitem.config.server.db.dao.ApplicationGroupDao;
import com.internetitem.config.server.db.dao.EnvironmentDao;
import com.internetitem.config.server.db.dataModel.SettingAction;
import com.internetitem.config.server.db.dataModel.SettingActionType;
import com.internetitem.config.server.db.dataModel.SettingApplicationGroup;
import com.internetitem.config.server.db.dataModel.SettingEnvironment;
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
@Path("environment/{applicationGroup}")
public class EnvironmentService extends AbstractService {

	@Autowired
	private EnvironmentDao environmentDao;

	@Autowired
	private ApplicationGroupDao applicationGroupDao;

	@Autowired
	private ActionDao actionDao;

	@Autowired
	private PermissionSet permissionSet;

	@GET
	@Transactional
	public List<SettingEnvironment> getEnvironments(@PathParam("applicationGroup") String applicationGroupName) throws EntityNotFoundException, InsufficientPermissionsException {
		SettingApplicationGroup appGroup = applicationGroupDao.getApplicationGroupByName(applicationGroupName);
		if (appGroup == null) {
			return notFound("Unknown Application Group");
		}

		ensurePermission(permissionSet.canAdminAppGroup(appGroup));

		return environmentDao.getAllEnvironments(appGroup);
	}

	@PUT
	@Transactional
	public CreateResponse createEnvironment(@PathParam("applicationGroup") String applicationGroupName, @NotNull CreateRequest request) throws InsufficientPermissionsException {
		SettingApplicationGroup appGroup = applicationGroupDao.getApplicationGroupByName(applicationGroupName);
		if (appGroup == null) {
			return new CreateResponse("Unknown Application Group");
		}

		ensurePermission(permissionSet.canAdminAppGroup(appGroup));

		String environmentName = request.getName();

		SettingEnvironment oldEnvironment = environmentDao.getEnvironmentByName(appGroup, environmentName);
		if (oldEnvironment != null) {
			return new CreateResponse("Environment already exists");
		}

		SettingEnvironment env = environmentDao.createEnvironment(appGroup, environmentName);
		SettingAction action = actionDao.createAction(appGroup);
		actionDao.createStructureChange(action, env, SettingActionType.CREATE);

		return new CreateResponse("Created Environment " + environmentName, env.getEnvironmentId());
	}
}
