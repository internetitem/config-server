package com.internetitem.config.server.services;

import com.internetitem.config.server.db.dao.ApplicationGroupDao;
import com.internetitem.config.server.db.dao.EnvironmentDao;
import com.internetitem.config.server.db.dataModel.SettingApplicationGroup;
import com.internetitem.config.server.db.dataModel.SettingEnvironment;
import com.internetitem.config.server.services.dataModel.CreateResponse;
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
}
