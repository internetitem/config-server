package com.internetitem.config.server.services;

import com.internetitem.config.server.db.dao.ApplicationGroupDao;
import com.internetitem.config.server.db.dataModel.SettingApplicationGroup;
import com.internetitem.config.server.security.PermissionSet;
import com.internetitem.config.server.services.dataModel.request.CreateRequest;
import com.internetitem.config.server.services.dataModel.response.CreateResponse;
import com.internetitem.config.server.services.exception.InsufficientPermissionsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;

@Service
@Path("applicationGroup")
public class ApplicationGroupService extends AbstractService {

	@Autowired
	private ApplicationGroupDao applicationGroupDao;

	@Autowired
	private PermissionSet permissionSet;

	@GET
	@Transactional
	public List<SettingApplicationGroup> getApplicationGroups() throws InsufficientPermissionsException {
		ensurePermission(permissionSet.isGlobalAdmin());

		return applicationGroupDao.getAllApplicationGroups();
	}

	@PUT
	@Transactional
	public CreateResponse createApplicationGroup(@NotNull CreateRequest request) throws InsufficientPermissionsException {
		ensurePermission(permissionSet.isGlobalAdmin());

		String applicationGroupName = request.getName();

		SettingApplicationGroup appGroup = applicationGroupDao.getApplicationGroupByName(applicationGroupName);
		if (appGroup != null) {
			return new CreateResponse("Application Group already exists");
		}

		appGroup = applicationGroupDao.createApplicationGroup(applicationGroupName);
		return new CreateResponse("Created Application Group " + applicationGroupName, appGroup.getApplicationGroupId());
	}

}
