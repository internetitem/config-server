package com.internetitem.config.server.services;

import com.internetitem.config.server.db.dao.ApplicationGroupDao;
import com.internetitem.config.server.db.dataModel.SettingApplicationGroup;
import com.internetitem.config.server.security.PermissionSet;
import com.internetitem.config.server.services.dataModel.CreateResponse;
import com.internetitem.config.server.services.exception.InsufficientPermissionsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

	@POST
	@Path("{applicationGroup}")
	@Transactional
	public CreateResponse createApplicationGroup(@PathParam("applicationGroup") String applicationGroupName) throws InsufficientPermissionsException {
		ensurePermission(permissionSet.isGlobalAdmin());

		SettingApplicationGroup appGroup = applicationGroupDao.getApplicationGroupByName(applicationGroupName);
		if (appGroup != null) {
			return new CreateResponse(false, "Application Group already exists", null);
		}

		appGroup = applicationGroupDao.createApplicationGroup(applicationGroupName);
		return new CreateResponse(true, "Created Application Group " + applicationGroupName, Long.valueOf(appGroup.getApplicationGroupId()));
	}

}
