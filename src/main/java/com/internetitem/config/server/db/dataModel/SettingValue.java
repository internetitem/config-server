package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SettingValue")
public class SettingValue {

	@Id
	@GeneratedValue(generator = "seqValue")
	@SequenceGenerator(name = "seqValue", sequenceName = "SeqSettingValue")
	@Column(name = "SettingId")
	private long settingId;

	@Column(name = "SettingKey", nullable = false, length = 100)
	private String key;

	@Column(name = "SettingValue")
	@Lob
	private String value;

	@Convert(converter = SettingValueType.SettingValueTypeConverter.class)
	@Column(name = "ValueTypeId", nullable = false)
	private SettingValueType valueType;

	@Column(name = "SettingConstraints")
	@Lob
	private String constraints;

	@Column(name = "ValidFrom")
	@Temporal(TemporalType.TIMESTAMP)
	private Date validFrom;

	@Column(name = "ValidTo")
	@Temporal(TemporalType.TIMESTAMP)
	private Date validTo;

	@ManyToOne
	@JoinColumn(name = "VersionFrom", referencedColumnName = "VersionId")
	private SettingVersion versionFrom;

	@ManyToOne
	@JoinColumn(name = "VersionTo", referencedColumnName = "VersionId")
	private SettingVersion versionTo;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ApplicationGroupId", referencedColumnName = "ApplicationGroupId", nullable = false)
	private SettingApplicationGroup applicationGroup;

	@ManyToOne
	@JoinColumn(name = "ComponentId", referencedColumnName = "ComponentId")
	private SettingComponent component;

	@ManyToOne
	@JoinColumn(name = "ApplicationId", referencedColumnName = "ApplicationId")
	private SettingApplication application;

	@ManyToOne
	@JoinColumn(name = "EnvironmentId", referencedColumnName = "EnvironmentId")
	private SettingEnvironment environment;

	@Column(name = "CreatedTs", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTs;

	@Column(name = "DeletedTs")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedTs;

	protected SettingValue() {
	}

	public SettingValue(String key, String value, SettingValueType valueType, String constraints, Date validFrom, Date validTo, SettingVersion versionFrom, SettingVersion versionTo, SettingApplicationGroup applicationGroup, SettingComponent component, SettingApplication application, SettingEnvironment environment, Date createdTs) {
		this.key = key;
		this.value = value;
		this.valueType = valueType;
		this.constraints = constraints;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.versionFrom = versionFrom;
		this.versionTo = versionTo;
		this.applicationGroup = applicationGroup;
		this.component = component;
		this.application = application;
		this.environment = environment;
		this.createdTs = createdTs;
	}

	public long getSettingId() {
		return settingId;
	}

	public void setSettingId(long settingId) {
		this.settingId = settingId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SettingValueType getValueType() {
		return valueType;
	}

	public void setValueType(SettingValueType valueType) {
		this.valueType = valueType;
	}

	public String getConstraints() {
		return constraints;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public SettingVersion getVersionFrom() {
		return versionFrom;
	}

	public void setVersionFrom(SettingVersion versionFrom) {
		this.versionFrom = versionFrom;
	}

	public SettingVersion getVersionTo() {
		return versionTo;
	}

	public void setVersionTo(SettingVersion versionTo) {
		this.versionTo = versionTo;
	}

	public SettingApplicationGroup getApplicationGroup() {
		return applicationGroup;
	}

	public void setApplicationGroup(SettingApplicationGroup applicationGroup) {
		this.applicationGroup = applicationGroup;
	}

	public SettingComponent getComponent() {
		return component;
	}

	public void setComponent(SettingComponent component) {
		this.component = component;
	}

	public SettingApplication getApplication() {
		return application;
	}

	public void setApplication(SettingApplication application) {
		this.application = application;
	}

	public SettingEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(SettingEnvironment environment) {
		this.environment = environment;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Date getDeletedTs() {
		return deletedTs;
	}

	public void setDeletedTs(Date deletedTs) {
		this.deletedTs = deletedTs;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SettingValue)) return false;

		SettingValue that = (SettingValue) o;

		if (settingId != that.settingId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (settingId ^ (settingId >>> 32));
	}
}
