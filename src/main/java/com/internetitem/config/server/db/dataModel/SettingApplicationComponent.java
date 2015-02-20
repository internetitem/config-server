package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SettingApplicationComponent")
public class SettingApplicationComponent {

	@Id
	@GeneratedValue(generator = "seqApplicationComponent")
	@SequenceGenerator(name = "seqApplicationComponent", sequenceName = "SeqSettingApplicationComponent")
	@Column(name = "ApplicationComponentId")
	private long applicationComponentId;

	@ManyToOne
	@JoinColumn(name = "ApplicationId", referencedColumnName = "ApplicationId")
	private SettingApplication application;

	@ManyToOne
	@JoinColumn(name = "ComponentId", referencedColumnName = "ComponentId")
	private SettingComponent component;

	@Column(name = "Ordering", nullable = false)
	private int ordering;

	@Column(name = "CreatedTs", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTimestamp;

	@Column(name = "DeletedTs")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deletedTimestamp;

	protected SettingApplicationComponent() {
	}

	public SettingApplicationComponent(SettingApplication application, SettingComponent component, int ordering, Date createdTimestamp) {
		this.application = application;
		this.component = component;
		this.ordering = ordering;
		this.createdTimestamp = createdTimestamp;
	}

	public long getApplicationComponentId() {
		return applicationComponentId;
	}

	public void setApplicationComponentId(long applicationComponentId) {
		this.applicationComponentId = applicationComponentId;
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

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Date getDeletedTimestamp() {
		return deletedTimestamp;
	}

	public void setDeletedTimestamp(Date deletedTimestamp) {
		this.deletedTimestamp = deletedTimestamp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SettingApplicationComponent)) return false;

		SettingApplicationComponent that = (SettingApplicationComponent) o;

		if (applicationComponentId != that.applicationComponentId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (applicationComponentId ^ (applicationComponentId >>> 32));
	}
}
