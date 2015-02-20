package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;

@Entity
@Table(name = "SettingActionStructureChange")
public class SettingActionStructureChange {

	@Id
	@GeneratedValue(generator = "seqActionStructureChange")
	@SequenceGenerator(name = "seqActionStructureChange", sequenceName = "SeqSettingActionStructureChange")
	@Column(name = "ActionStructureChangeId")
	private long actionStructureChangeId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ActionId", referencedColumnName = "ActionId", nullable = false)
	private SettingAction action;

	@Column(name="ActionStructureChangeTypeId", nullable = false)
	@Convert(converter = SettingActionStructureChangeType.SettingActionStructureChangeTypeConverter.class)
	private SettingActionStructureChangeType structureChangeType;

	@Column(name="ActionTypeId", nullable = false)
	@Convert(converter = SettingActionType.SettingActionTypeConverter.class)
	private SettingActionType actionType;

	@ManyToOne
	@JoinColumn(name="ApplicationId", referencedColumnName = "ApplicationId")
	private SettingApplication application;

	@ManyToOne
	@JoinColumn(name="ComponentId", referencedColumnName = "ComponentId")
	private SettingComponent component;

	@ManyToOne
	@JoinColumn(name="VersionId", referencedColumnName = "VersionId")
	private SettingVersion version;

	@ManyToOne
	@JoinColumn(name="EnvironmentId", referencedColumnName = "EnvironmentId")
	private SettingEnvironment environment;

	protected SettingActionStructureChange() {
	}

	public SettingActionStructureChange(SettingAction action, SettingActionStructureChangeType structureChangeType, SettingActionType actionType, SettingApplication application, SettingComponent component, SettingVersion version, SettingEnvironment environment) {
		this.action = action;
		this.structureChangeType = structureChangeType;
		this.actionType = actionType;
		this.application = application;
		this.component = component;
		this.version = version;
		this.environment = environment;
	}

	public long getActionStructureChangeId() {
		return actionStructureChangeId;
	}

	public void setActionStructureChangeId(long actionStructureChangeId) {
		this.actionStructureChangeId = actionStructureChangeId;
	}

	public SettingAction getAction() {
		return action;
	}

	public void setAction(SettingAction action) {
		this.action = action;
	}

	public SettingActionStructureChangeType getStructureChangeType() {
		return structureChangeType;
	}

	public void setStructureChangeType(SettingActionStructureChangeType structureChangeType) {
		this.structureChangeType = structureChangeType;
	}

	public SettingActionType getActionType() {
		return actionType;
	}

	public void setActionType(SettingActionType actionType) {
		this.actionType = actionType;
	}

	public SettingApplication getApplication() {
		return application;
	}

	public void setApplication(SettingApplication application) {
		this.application = application;
	}

	public SettingComponent getComponent() {
		return component;
	}

	public void setComponent(SettingComponent component) {
		this.component = component;
	}

	public SettingVersion getVersion() {
		return version;
	}

	public void setVersion(SettingVersion version) {
		this.version = version;
	}

	public SettingEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(SettingEnvironment environment) {
		this.environment = environment;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SettingActionStructureChange)) return false;

		SettingActionStructureChange that = (SettingActionStructureChange) o;

		if (actionStructureChangeId != that.actionStructureChangeId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (actionStructureChangeId ^ (actionStructureChangeId >>> 32));
	}
}
