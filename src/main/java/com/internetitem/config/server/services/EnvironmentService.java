package com.internetitem.config.server.services;

import com.internetitem.config.server.db.dao.EnvironmentDao;
import com.internetitem.config.server.db.dataModel.SettingEnvironment;
import com.internetitem.config.server.services.dataModel.CreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Service
@Path("environment")
public class EnvironmentService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EnvironmentDao environmentDao;

	@GET
	@Transactional
	public List<SettingEnvironment> getEnvironments() {
		return environmentDao.getAllEnvironments();
	}

	@POST
	@Path("environment/{environmentName}")
	@Transactional
	public CreateResponse createEnvironment(@PathParam("environmentName") String environmentName) {
		SettingEnvironment oldEnvironment = environmentDao.getEnvironmentByName(environmentName);
		if (oldEnvironment != null) {
			return new CreateResponse(false, "Environment already exists", null);
		}

		SettingEnvironment env = environmentDao.createEnvironment(environmentName);
		return new CreateResponse(true, "Created Environment " + environmentName, Long.valueOf(env.getEnvironmentId()));
	}
}
