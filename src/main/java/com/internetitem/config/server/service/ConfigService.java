package com.internetitem.config.server.service;

import com.internetitem.config.server.dao.DatabaseAccess;
import com.internetitem.config.server.dataModel.db.Application;
import com.internetitem.config.server.dataModel.db.Environment;
import com.internetitem.config.server.dataModel.db.Setting;
import com.internetitem.config.server.dataModel.db.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
@Path("/config")
public class ConfigService {

	@Autowired
	private DatabaseAccess db;

	@GET
	@Path("{application}/{environment}")
	public List<Setting> getSettings(@PathParam("application") String applicationName, @PathParam("environment") String environmentName, @QueryParam("version") String versionName) throws SQLException {
		Application application = db.getApplication(applicationName);
		if (application == null) {
			throw new WebApplicationException("Application not found", Response.Status.NOT_FOUND);
		}

		Version version = null;
		if (versionName != null) {
			version = db.getVersion(application, versionName);
		}

		Environment environment = db.getEnvironment(environmentName);
		if (environment == null) {
			throw new WebApplicationException("Environment not found", Response.Status.NOT_FOUND);
		}

		return db.getSettings(new Date(), version, application, environment);
	}


}
