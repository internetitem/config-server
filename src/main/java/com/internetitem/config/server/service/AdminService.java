package com.internetitem.config.server.service;

import com.internetitem.config.server.dao.DatabaseAccess;
import com.internetitem.config.server.dataModel.db.*;
import com.internetitem.config.server.service.dataModel.SetValueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Path("/admin")
public class AdminService {

	@Autowired
	private DatabaseAccess db;

	@GET
	@Path("applications")
	public List<Application> listApplications() {
		return db.listApplications();
	}

	@GET
	@Path("application/{application}")
	public Application getApplication(@PathParam("application") String application) throws SQLException {
		return db.getApplication(application);
	}

	@PUT
	@Path("application")
	public Application createApplication(Application application) {
		Application newApplication = db.createApplication(application.getApplicationName());
		return newApplication;
	}

	@GET
	@Path("environment/{environment}")
	public Environment getEnvironment(@PathParam("environment") String environment) throws SQLException {
		return db.getEnvironment(environment);
	}

	@PUT
	@Path("environment")
	public Environment createEnvironment(Environment environment) {
		Environment newEnvironment = db.createEnvironment(environment.getEnvironmentName());
		return newEnvironment;
	}

	@GET
	@Path("component/{component}")
	public Component getComponent(@PathParam("component") String name) {
		return db.getComponent(name);
	}

	@PUT
	@Path("component")
	public Component createComponent(Component component) {
		return db.createComponent(component.getName());
	}

	@POST
	@Path("mapComponent/{application}/{component}")
	public Application mapComponentToApplication(@PathParam("application") String applicationName, @PathParam("component") String componentName, @QueryParam("order") Integer order) throws SQLException {
		Application application = db.getApplication(applicationName);
		if (application == null) {
			throw new WebApplicationException("Application not found", Response.Status.NOT_FOUND);
		}

		Component component = db.getComponent(componentName);
		if (component == null) {
			throw new WebApplicationException("Component not found", Response.Status.NOT_FOUND);
		}

		return db.mapComponentToApplication(component, application, order);
	}

	@POST
	@Path("setValue")
	public Setting setValue(SetValueRequest request) throws SQLException {
		String appName = request.getApplication();
		Application app = null;
		if (appName != null) {
			app = db.getApplication(appName);
			if (app == null) {
				throw new WebApplicationException("Application not found", Response.Status.NOT_FOUND);
			}
		}

		String versionFromName = request.getVersionFrom();
		Version versionFrom = null;
		if (versionFromName != null) {
			if (app == null) {
				throw new WebApplicationException("Version requires Application to be specified", Response.Status.BAD_REQUEST);
			}
			versionFrom = db.getVersion(app, versionFromName);
			if (versionFrom == null) {
				throw new WebApplicationException("From Version not found", Response.Status.NOT_FOUND);
			}
		}

		String versionToName = request.getVersionTo();
		Version versionTo = null;
		if (versionToName != null) {
			if (app == null) {
				throw new WebApplicationException("Version requires Application to be specified", Response.Status.BAD_REQUEST);
			}
			versionTo = db.getVersion(app, versionToName);
			if (versionTo == null) {
				throw new WebApplicationException("To Version not found", Response.Status.NOT_FOUND);
			}
		}

		String key = request.getKey();
		if (key == null) {
			throw new WebApplicationException("Key not specified", Response.Status.BAD_REQUEST);
		}
		String value = request.getValue();
		if (value == null) {
			throw new WebApplicationException("Value not specified", Response.Status.BAD_REQUEST);
		}

		Date validFrom = request.getValidFrom();
		Date validTo = request.getValidTo();

		String componentName = request.getComponent();
		Component component = null;
		if (componentName != null) {
			component = db.getComponent(componentName);
			if (component == null) {
				throw new WebApplicationException("Component not found", Response.Status.NOT_FOUND);
			}
		}

		String environmentName = request.getEnvironment();
		Environment env = null;
		if (environmentName != null) {
			env = db.getEnvironment(environmentName);
			if (env == null) {
				throw new WebApplicationException("Environment not found", Response.Status.NOT_FOUND);
			}
		}

		Map<String, String> tags = request.getTags();
		if (tags == null) {
			tags = new HashMap<>();
		}

		return db.setValue(key, value, validFrom, validTo, versionFrom, versionTo, component, app, env, tags);
	}
}
