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
  CreatedTs TIMESTAMPTZ NOT NULL,
  DeletedTs TIMESTAMPTZ,
  CONSTRAINT fkApplicationApplicationGroup FOREIGN KEY (ApplicationGroupId) REFERENCES SettingApplicationGroup (ApplicationGroupId)
);
ALTER SEQUENCE SeqSettingApplication OWNED BY SettingApplication.ApplicationId;

CREATE SEQUENCE SeqSettingComponent;
CREATE TABLE SettingComponent (
	ComponentId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingComponent'),
	ComponentName VARCHAR(50) NOT NULL,
  ApplicationGroupId INTEGER NOT NULL,
  CreatedTs TIMESTAMPTZ NOT NULL,
  DeletedTs TIMESTAMPTZ,
  CONSTRAINT fkComponentApplicationGroup FOREIGN KEY (ApplicationGroupId) REFERENCES SettingApplicationGroup (ApplicationGroupId)
);
ALTER SEQUENCE SeqSettingComponent OWNED BY SettingComponent.ComponentId;

CREATE SEQUENCE SeqSettingApplicationComponent;
CREATE TABLE SettingApplicationComponent (
  ApplicationComponentId INTEGER PRIMARY KEY,
	ApplicationId INTEGER NOT NULL,
	ComponentId INTEGER NOT NULL,
	Ordering INTEGER NOT NULL,
  CreatedTs TIMESTAMPTZ NOT NULL,
  DeletedTs TIMESTAMPTZ,
	CONSTRAINT fkApplicationComponentApplication FOREIGN KEY (ApplicationId) REFERENCES SettingApplication (ApplicationId),
	CONSTRAINT fkApplicationComponentComponent FOREIGN KEY (ComponentId) REFERENCES SettingComponent (ComponentId)
);
ALTER SEQUENCE SeqSettingApplicationComponent OWNED BY SettingApplicationComponent.ApplicationComponentId;

CREATE SEQUENCE SeqSettingVersion;
CREATE TABLE SettingVersion (
	VersionId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingVersion'),
	ApplicationId INTEGER NOT NULL,
	VersionString VARCHAR(50) NOT NULL,
	Ordering INTEGER NOT NULL,
  CreatedTs TIMESTAMPTZ NOT NULL,
  DeletedTs TIMESTAMPTZ,
  CONSTRAINT fkVersionApplication FOREIGN KEY (ApplicationId) REFERENCES SettingApplication (ApplicationId)
);
ALTER SEQUENCE SeqSettingVersion OWNED BY SettingVersion.VersionId;

CREATE SEQUENCE SeqSettingEnvironment;
CREATE TABLE SettingEnvironment (
	EnvironmentId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingEnvironment'),
	EnvironmentName VARCHAR(50) NOT NULL,
  ApplicationGroupId INTEGER NOT NULL,
  CreatedTs TIMESTAMPTZ NOT NULL,
  DeletedTs TIMESTAMPTZ,  CONSTRAINT fkEnvironmentApplicationGroup
  FOREIGN KEY (ApplicationGroupId) REFERENCES SettingApplicationGroup (ApplicationGroupId)
);
ALTER SEQUENCE SeqSettingEnvironment OWNED BY SettingEnvironment.EnvironmentId;

CREATE TABLE SettingValueType (
  ValueTypeId INTEGER PRIMARY KEY,
  ValueTypeName VARCHAR(50) NOT NULL,
  CONSTRAINT unqValueTypeName UNIQUE (ValueTypeName)
);

INSERT INTO SettingValueType (ValueTypeId, ValueTypeName) VALUES (1, 'Deleted');
INSERT INTO SettingValueType (ValueTypeId, ValueTypeName) VALUES (2, 'String');
INSERT INTO SettingValueType (ValueTypeId, ValueTypeName) VALUES (3, 'JSON');

CREATE SEQUENCE SeqSettingValue;
CREATE TABLE SettingValue (
	SettingId BIGINT PRIMARY KEY DEFAULT nextval('SeqSettingValue'),
	SettingKey VARCHAR(100) NOT NULL,
	SettingValue TEXT,
  ValueTypeId INTEGER NOT NULL,
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
  DeletedTs TIMESTAMPTZ,
  CONSTRAINT fkValueType FOREIGN KEY (ValueTypeId) REFERENCES SettingValueType (ValueTypeId),
	CONSTRAINT fkValueVersionFrom FOREIGN KEY (VersionFrom) REFERENCES SettingVersion (VersionId),
	CONSTRAINT fkValueVersionTo FOREIGN KEY (VersionTo) REFERENCES SettingVersion (VersionId),
	CONSTRAINT fkValueComponent FOREIGN KEY (ComponentId) REFERENCES SettingComponent (ComponentId),
	CONSTRAINT fkValueApplication FOREIGN KEY (ApplicationId) REFERENCES SettingApplication (ApplicationId),
	CONSTRAINT fkValueEnvironment FOREIGN KEY (EnvironmentId) REFERENCES SettingEnvironment (EnvironmentId),
  CONSTRAINT fkValueApplicationGroup FOREIGN KEY (ApplicationGroupId) REFERENCES SettingApplicationGroup (ApplicationGroupId)
);
ALTER SEQUENCE SeqSettingValue OWNED BY SettingValue.SettingId;

CREATE SEQUENCE SeqSettingUser;
CREATE TABLE SettingUser (
  UserId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingUser'),
  Username VARCHAR(50) NOT NULL,
  Password VARCHAR(100),
  Active BIT NOT NULL DEFAULT 1::BIT,
  CONSTRAINT unqUserUsername UNIQUE (Username)
);
ALTER SEQUENCE SeqSettingUser OWNED BY SettingUser.UserId;

CREATE SEQUENCE SeqSettingUserGroup;
CREATE TABLE SettingUserGroup (
  UserGroupId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingUserGroup'),
  GroupName VARCHAR(50) NOT NULL,
  CONSTRAINT unqUserGroupName UNIQUE (GroupName)
);
ALTER SEQUENCE SeqSettingUserGroup OWNED BY SettingUserGroup.UserGroupId;

CREATE TABLE SettingUserGroupMember (
  UserGroupId INTEGER NOT NULL,
  UserId INTEGER NOT NULL,
  CONSTRAINT pkUserGroupMember PRIMARY KEY (UserGroupId, UserId),
  CONSTRAINT fkUserGroupMemberGroup FOREIGN KEY (UserGroupId) REFERENCES SettingUserGroup (UserGroupId),
  CONSTRAINT fkUserGroupMemberUser FOREIGN KEY (UserId) REFERENCES SettingUser (UserId)
);

CREATE TABLE SettingPermissionType (
  PermissionTypeId INTEGER PRIMARY KEY,
  PermissionTypeName VARCHAR(50) NOT NULL,
  CONSTRAINT unqPermissionTypeName UNIQUE (PermissionTypeName)
);
INSERT INTO SettingPermissionType (PermissionTypeId, PermissionTypeName) VALUES (1, 'Super User');
INSERT INTO SettingPermissionType (PermissionTypeId, PermissionTypeName) VALUES (2, 'Get Application Settings');
INSERT INTO SettingPermissionType (PermissionTypeId, PermissionTypeName) VALUES (3, 'Set Value');
INSERT INTO SettingPermissionType (PermissionTypeId, PermissionTypeName) VALUES (4, 'Administer Application Group');

CREATE TABLE SettingPermissionGrantType (
  PermissionGrantTypeId INTEGER PRIMARY KEY,
  PermissionGrantTypeName VARCHAR(50) NOT NULL,
  CONSTRAINT unqPermissionGrantTypeName UNIQUE (PermissionGrantTypeName)
);
INSERT INTO SettingPermissionGrantType (PermissionGrantTypeId, PermissionGrantTypeName) VALUES (1, 'Global');
INSERT INTO SettingPermissionGrantType (PermissionGrantTypeId, PermissionGrantTypeName) VALUES (2, 'ApplicationGroup');
INSERT INTO SettingPermissionGrantType (PermissionGrantTypeId, PermissionGrantTypeName) VALUES (3, 'Application');
INSERT INTO SettingPermissionGrantType (PermissionGrantTypeId, PermissionGrantTypeName) VALUES (4, 'Component');

CREATE SEQUENCE SeqSettingPermissionGrant;
CREATE TABLE SettingPermissionGrant (
  PermissionGrantId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingPermissionGrant'),
  UserGroupId INTEGER NOT NULL,
  GrantTypeId INTEGER NOT NULL,
  PermissionTypeId INTEGER NOT NULL,
  ApplicationGroupId INTEGER,
  ApplicationId INTEGER,
  ComponentId INTEGER,
  CONSTRAINT fkPermissionGrantGroup FOREIGN KEY (UserGroupId) REFERENCES SettingUserGroup (UserGroupId),
  CONSTRAINT fkPermissionGrantGrantType FOREIGN KEY (GrantTypeId) REFERENCES SettingPermissionGrantType (PermissionGrantTypeId),
  CONSTRAINT fkPermissionGrantPermissionType FOREIGN KEY (PermissionTypeId) REFERENCES SettingPermissionType (PermissionTypeId),
  CONSTRAINT fkPermissionGrantApplicationGroup FOREIGN KEY (ApplicationGroupId) REFERENCES SettingApplicationGroup (ApplicationGroupId),
  CONSTRAINT fkPermissionGrantApplication FOREIGN KEY (ApplicationId) REFERENCES SettingApplication (ApplicationId),
  CONSTRAINT fkPermissionGrantComponent FOREIGN KEY (ComponentId) REFERENCES SettingComponent (ComponentId)
);

CREATE SEQUENCE SeqSettingAction;
CREATE TABLE SettingAction (
  ActionId BIGINT PRIMARY KEY DEFAULT nextval('SeqSettingAction'),
  UserId INTEGER NOT NULL,
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

CREATE TABLE SettingActionStructureChangeType (
  ActionStructureChangeTypeId INTEGER PRIMARY KEY,
  ActionStructureChangeTypeName VARCHAR(50) NOT NULL,
  CONSTRAINT unqActionStructureChangeTypeName UNIQUE (ActionStructureChangeTypeName)
);
INSERT INTO SettingActionStructureChangeType (ActionStructureChangeTypeId, ActionStructureChangeTypeName) VALUES (1, 'Application');
INSERT INTO SettingActionStructureChangeType (ActionStructureChangeTypeId, ActionStructureChangeTypeName) VALUES (2, 'Component');
INSERT INTO SettingActionStructureChangeType (ActionStructureChangeTypeId, ActionStructureChangeTypeName) VALUES (3, 'Version');
INSERT INTO SettingActionStructureChangeType (ActionStructureChangeTypeId, ActionStructureChangeTypeName) VALUES (4, 'Environment');

CREATE SEQUENCE SeqSettingActionStructureChange;
CREATE TABLE SettingActionStructureChange (
  ActionStructureChangeId INTEGER PRIMARY KEY DEFAULT nextval('SeqSettingActionStructureChange'),
  ActionId INTEGER NOT NULL,
  ActionStructureChangeTypeId INTEGER NOT NULL,
  ActionTypeId INTEGER NOT NULL,
  ApplicationId INTEGER,
  ComponentId INTEGER,
  VersionId INTEGER,
  EnvironmentId INTEGER,
  CONSTRAINT fkActionStructureChangeAction FOREIGN KEY (ActionId) REFERENCES SettingAction (ActionId),
  CONSTRAINT fkActionStructureChangeActionType FOREIGN KEY (ActionTypeId) REFERENCES SettingActionType (ActionTypeId),
  CONSTRAINT fkActionStructureChangeType FOREIGN KEY (ActionStructureChangeTypeId) REFERENCES SettingActionStructureChangeType (ActionStructureChangeTypeId),
  CONSTRAINT fkActionStructureChangeApplication FOREIGN KEY (ApplicationId) REFERENCES SettingApplication (ApplicationId),
  CONSTRAINT fkActionStructureChangeComponent FOREIGN KEY (ComponentId) REFERENCES SettingComponent (ComponentId),
  CONSTRAINT fkActionStructureChangeVersion FOREIGN KEY (VersionId) REFERENCES SettingVersion (VersionId),
  CONSTRAINT fkActionStructureChangeEnvironment FOREIGN KEY (EnvironmentId) REFERENCES settingenvironment (EnvironmentId)
);
ALTER SEQUENCE SeqSettingActionStructureChange OWNED BY SettingActionStructureChange.ActionStructureChangeId;

CREATE TABLE SchemaVersion (
	SchemaType VARCHAR(50) NOT NULL PRIMARY KEY,
	CurrentVersion INTEGER NOT NULL
);

INSERT INTO SchemaVersion (SchemaType, CurrentVersion) VALUES ('Settings', 1);



insert into settinguser (username, password, active) values ('user', 'password', 1::bit);

