CREATE TABLE SettingApplication (
	ApplicationId INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	ApplicationName VARCHAR(50) NOT NULL,
	CONSTRAINT unqApplicationName UNIQUE (ApplicationName)
);

CREATE TABLE SettingComponent (
	ComponentId INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	ComponentName VARCHAR(50) NOT NULL,
	CONSTRAINT unqComponentName UNIQUE (ComponentName)
);

CREATE TABLE SettingApplicationComponent (
	ApplicationId INTEGER NOT NULL,
	ComponentId INTEGER NOT NULL,
	Ordering INTEGER NOT NULL,
	CONSTRAINT pkApplicationComponent PRIMARY KEY (ApplicationId, ComponentId),
	CONSTRAINT fkApplicationComponentApplication FOREIGN KEY (ApplicationId) REFERENCES SettingApplication (ApplicationId),
	CONSTRAINT fkApplicationComponentComponent FOREIGN KEY (ComponentId) REFERENCES SettingComponent (ComponentId)
);

CREATE TABLE SettingVersion (
	VersionId INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	ApplicationId INTEGER NOT NULL,
	VersionString VARCHAR(50) NOT NULL,
	Ordering INTEGER NOT NULL,
	CONSTRAINT fkVersionApplication FOREIGN KEY (ApplicationId) REFERENCES SettingApplication (ApplicationId)
);

CREATE TABLE SettingEnvironment (
	EnvironmentId INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	EnvironmentName VARCHAR(50) NOT NULL,
	CONSTRAINT unqSettingEnvironment UNIQUE (EnvironmentName)
);

CREATE TABLE SettingValues (
	SettingId INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	SettingKey VARCHAR(100) NOT NULL,
	SettingValue LONG VARCHAR NOT NULL,
	ValidFrom TIMESTAMP NOT NULL,
	ValidTo TIMESTAMP NOT NULL,
	VersionFrom INTEGER,
	VersionTo INTEGER,
	ComponentId INTEGER,
	ApplicationId INTEGER,
	EnvironmentId INTEGER,
	LastModifiedTs TIMESTAMP NOT NULL,
	CONSTRAINT fkValueVersionFrom FOREIGN KEY (VersionFrom) REFERENCES SettingVersion (VersionId),
	CONSTRAINT fkValueVersionTo FOREIGN KEY (VersionTo) REFERENCES SettingVersion (VersionId),
	CONSTRAINT fkValueComponent FOREIGN KEY (ComponentId) REFERENCES SettingComponent (ComponentId),
	CONSTRAINT fkValueApplication FOREIGN KEY (ApplicationId) REFERENCES SettingApplication (ApplicationId),
	CONSTRAINT fkValueEnvironment FOREIGN KEY (EnvironmentId) REFERENCES SettingEnvironment (EnvironmentId)
);

CREATE TABLE SettingTag (
	TagId INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	SettingId INTEGER NOT NULL,
	TagType VARCHAR(50) NOT NULL,
	TagValue VARCHAR(100) NOT NULL,
	CONSTRAINT unqSettingTag UNIQUE (SettingId, TagType),
	CONSTRAINT fkTagSetting FOREIGN KEY (SettingId) REFERENCES SettingValues (SettingId)
);

CREATE TABLE SchemaVersion (
	SchemaType VARCHAR(50) NOT NULL PRIMARY KEY,
	CurrentVersion INTEGER NOT NULL
);

INSERT INTO SchemaVersion (SchemaType, CurrentVersion) VALUES ('Settings', 1);
