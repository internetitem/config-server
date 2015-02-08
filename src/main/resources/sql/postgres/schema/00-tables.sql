CREATE SEQUENCE SeqSettingApplicationGroup;
CREATE TABLE SettingApplicationGroup (
  ApplicationGroupId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingApplicationGroup'),
  ApplicationGroupName VARCHAR(50) NOT NULL,
  CONSTRAINT unqApplicationGroupName UNIQUE (ApplicationGroupName)
);
ALTER SEQUENCE SeqSettingApplicationGroup OWNED BY SettingApplicationGroup.ApplicationGroupId;

CREATE SEQUENCE SeqSettingApplication;
CREATE TABLE SettingApplication (
	ApplicationId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingApplication'),
	ApplicationName VARCHAR(50) NOT NULL,
  ApplicationGroupId INTEGER NOT NULL,
  CONSTRAINT fkApplicationApplicationGroup FOREIGN KEY (ApplicationGroupId) REFERENCES SettingApplicationGroup (ApplicationGroupId),
	CONSTRAINT unqApplicationName UNIQUE (ApplicationName)
);
ALTER SEQUENCE SeqSettingApplication OWNED BY SettingApplication.ApplicationId;

CREATE SEQUENCE SeqSettingComponent;
CREATE TABLE SettingComponent (
	ComponentId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingComponent'),
	ComponentName VARCHAR(50) NOT NULL,
  ApplicationGroupId INTEGER NOT NULL,
  CONSTRAINT fkComponentApplicationGroup FOREIGN KEY (ApplicationGroupId) REFERENCES SettingApplicationGroup (ApplicationGroupId),
	CONSTRAINT unqComponentName UNIQUE (ComponentName)
);
ALTER SEQUENCE SeqSettingComponent OWNED BY SettingComponent.ComponentId;

CREATE TABLE SettingApplicationComponent (
	ApplicationId INTEGER NOT NULL,
	ComponentId INTEGER NOT NULL,
	Ordering INTEGER NOT NULL,
	CONSTRAINT pkApplicationComponent PRIMARY KEY (ApplicationId, ComponentId),
	CONSTRAINT fkApplicationComponentApplication FOREIGN KEY (ApplicationId) REFERENCES SettingApplication (ApplicationId),
	CONSTRAINT fkApplicationComponentComponent FOREIGN KEY (ComponentId) REFERENCES SettingComponent (ComponentId)
);

CREATE SEQUENCE SeqSettingVersion;
CREATE TABLE SettingVersion (
	VersionId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingVersion'),
	ApplicationId INTEGER NOT NULL,
	VersionString VARCHAR(50) NOT NULL,
	Ordering INTEGER NOT NULL,
	CONSTRAINT fkVersionApplication FOREIGN KEY (ApplicationId) REFERENCES SettingApplication (ApplicationId)
);
ALTER SEQUENCE SeqSettingVersion OWNED BY SettingVersion.VersionId;

CREATE SEQUENCE SeqSettingEnvironment;
CREATE TABLE SettingEnvironment (
	EnvironmentId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingEnvironment'),
	EnvironmentName VARCHAR(50) NOT NULL,
  ApplicationGroupId INTEGER NOT NULL,
  CONSTRAINT fkEnvironmentApplicationGroup FOREIGN KEY (ApplicationGroupId) REFERENCES SettingApplicationGroup (ApplicationGroupId),
  CONSTRAINT unqSettingEnvironment UNIQUE (EnvironmentName)
);
ALTER SEQUENCE SeqSettingEnvironment OWNED BY SettingEnvironment.EnvironmentId;

CREATE SEQUENCE SeqSettingValue;
CREATE TABLE SettingValue (
	SettingId BIGINT PRIMARY KEY DEFAULT nextval('SeqSettingValue'),
	SettingKey VARCHAR(100) NOT NULL,
	SettingValue TEXT NOT NULL,
  SettingConstraints TEXT NULL,
	ValidFrom TIMESTAMPTZ,
	ValidTo TIMESTAMPTZ,
	VersionFrom INTEGER,
	VersionTo INTEGER,
  ApplicationGroupId INTEGER NOT NULL,
  ComponentId INTEGER,
	ApplicationId INTEGER,
	EnvironmentId INTEGER,
	CreatedTs TIMESTAMPTZ NOT NULL,
  DeletedTs TIMESTAMPTZ NOT NULL,
	CONSTRAINT fkValueVersionFrom FOREIGN KEY (VersionFrom) REFERENCES SettingVersion (VersionId),
	CONSTRAINT fkValueVersionTo FOREIGN KEY (VersionTo) REFERENCES SettingVersion (VersionId),
	CONSTRAINT fkValueComponent FOREIGN KEY (ComponentId) REFERENCES SettingComponent (ComponentId),
	CONSTRAINT fkValueApplication FOREIGN KEY (ApplicationId) REFERENCES SettingApplication (ApplicationId),
	CONSTRAINT fkValueEnvironment FOREIGN KEY (EnvironmentId) REFERENCES SettingEnvironment (EnvironmentId),
  CONSTRAINT fkValueApplicationGroup FOREIGN KEY (ApplicationGroupId) REFERENCES SettingApplicationGroup (ApplicationGroupId)
);
ALTER SEQUENCE SeqSettingValue OWNED BY SettingValue.SettingId;

CREATE SEQUENCE SeqSettingAction;
CREATE TABLE SettingAction (
  ActionId BIGINT PRIMARY KEY DEFAULT nextval('SeqSettingAction'),
  Username VARCHAR(50),
  Hostname VARCHAR(50),
  SettingSource VARCHAR(100),
  Comments TEXT,
  ApplicationGroupId INTEGER NOT NULL,
  EventTs TIMESTAMPTZ NOT NULL,
  CONSTRAINT fkActionApplicationGroup FOREIGN KEY (ApplicationGroupId) REFERENCES SettingApplicationGroup (ApplicationGroupId)
);
ALTER SEQUENCE SeqSettingAction OWNED BY SettingAction.ActionId;

CREATE TABLE SettingActionType (
  ActionTypeId INTEGER PRIMARY KEY,
  ActionTypeName VARCHAR(50) NOT NULL,
  CONSTRAINT unqActionTypeName UNIQUE (ActionTypeName)
);
INSERT INTO SettingActionType (ActionTypeId, ActionTypeName) VALUES (1, 'Create');
INSERT INTO SettingActionType (ActionTypeId, ActionTypeName) VALUES (2, 'Delete');

CREATE SEQUENCE SeqSettingActionValue;
CREATE TABLE SettingActionValue (
  ActionValueId BIGINT PRIMARY KEY DEFAULT nextval('SeqSettingActionValue'),
  ActionId BIGINT NOT NULL,
  SettingId BIGINT NOT NULL,
  ActionTypeId INTEGER NOT NULL,
  CONSTRAINT fkActionValueAction FOREIGN KEY (ActionId) REFERENCES SettingAction (ActionId),
  CONSTRAINT fkActionValueSetting FOREIGN KEY (SettingId) REFERENCES SettingValue (SettingId),
  CONSTRAINT fkActionValueType FOREIGN KEY (ActionTypeId) REFERENCES SettingActionType (ActionTypeId)
);
ALTER SEQUENCE SeqSettingActionValue OWNED BY SettingActionValue.ActionValueId;

CREATE TABLE SchemaVersion (
	SchemaType VARCHAR(50) NOT NULL PRIMARY KEY,
	CurrentVersion INTEGER NOT NULL
);

INSERT INTO SchemaVersion (SchemaType, CurrentVersion) VALUES ('Settings', 1);
