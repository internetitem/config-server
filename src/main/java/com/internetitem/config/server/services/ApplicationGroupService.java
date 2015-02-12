package com.internetitem.config.server.services;

import com.internetitem.config.server.db.dao.ApplicationGroupDao;
import com.internetitem.config.server.db.dao.EnvironmentDao;
import com.internetitem.config.server.db.dataModel.*;
import com.internetitem.config.server.security.LoggedInUserDetails;
import com.internetitem.config.server.services.dataModel.CreateResponse;
import com.internetitem.config.server.services.dataModel.PermissionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Path("applicationGroup")
public class ApplicationGroupService {

	@Autowired
	private ApplicationGroupDao applicationGroupDao;

	@GET
	@Transactional
	public List<SettingApplicationGroup> getApplicationGroups() {
		return applicationGroupDao.getAllApplicationGroups();
	}

	@POST
	@Path("{applicationGroup}")
	@Transactional
	public CreateResponse createApplicationGroup(@PathParam("applicationGroup") String applicationGroupName) {
		SettingApplicationGroup appGroup = applicationGroupDao.getApplicationGroupByName(applicationGroupName);
		if (appGroup != null) {
			return new CreateResponse(false, "Application Group already exists", null);
		}

		appGroup = applicationGroupDao.createApplicationGroup(applicationGroupName);
		return new CreateResponse(true, "Created Application Group " + applicationGroupName, Long.valueOf(appGroup.getApplicationGroupId()));
	}

	@GET
	@Path("grants")
	public List<PermissionItem> getGrants(@Context SecurityContext securityContext) {
		LoggedInUserDetails details = (LoggedInUserDetails)((Authentication) securityContext.getUserPrincipal()).getPrincipal();
		List<PermissionItem> permissions = new ArrayList<>();
		for (SettingPermissionGrant grant : details.getGrants()) {
			SettingPermissionType permissionType = grant.getPermissionType();
			SettingPermissionGrantType grantType = grant.getGrantType();

			SettingApplicationGroup applicationGroup = grant.getApplicationGroup();
			Long applicationGroupId = null;
			if (applicationGroup != null) {
				applicationGroupId = Long.valueOf(applicationGroup.getApplicationGroupId());
			}
			SettingApplication application = grant.getApplication();
			Long applicationId = null;
			if (application != null) {
				applicationId = Long.valueOf(application.getApplicationId());
			}
			SettingComponent component = grant.getComponent();
			Long componentId = null;
			if (component != null) {
				componentId = Long.valueOf(component.getComponentId());
			}

			PermissionItem pi = new PermissionItem(grantType, permissionType, applicationGroupId, applicationId, componentId);
			permissions.add(pi);
		}
		return permissions;
	}
}
