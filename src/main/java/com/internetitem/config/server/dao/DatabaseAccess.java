package com.internetitem.config.server.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.internetitem.config.server.dataModel.db.*;

public interface DatabaseAccess {

	void upgrade() throws Exception;

	List<Setting> getSettings(Date timestamp, Version version, Application application, Environment environment) throws SQLException;

	Application getApplication(String name) throws SQLException;

	Environment getEnvironment(String name) throws SQLException;

	Version getVersion(Application application, String name) throws SQLException;

	List<Application> listApplications();

	Application createApplication(String name);

	Environment createEnvironment(String name);

	com.internetitem.config.server.dataModel.db.Component getComponent(String name);

	com.internetitem.config.server.dataModel.db.Component createComponent(String name);

	Application mapComponentToApplication(com.internetitem.config.server.dataModel.db.Component comp, Application app, Integer order) throws SQLException;

	Version createVersion(Application application, String name, Integer order, boolean shift) throws SQLException;

	Setting setValue(String key, String value, Date validFrom, Date validTo, Version versionFrom, Version versionTo, Component component, Application application, Environment environment, Map<String, String> tags);
}
