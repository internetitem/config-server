package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;

@Entity
@Table(name = "SettingVersion")
public class SettingVersion {

	@Id
	@GeneratedValue(generator = "seqVersion")
	@SequenceGenerator(name = "seqVersion", sequenceName = "SeqSettingVersion")
	@Column(name = "VersionId")
	private long versionId;

	@ManyToOne(optional = false)
	private SettingApplication application;

	@Column(name = "VersionString", length = 50, nullable = false)
	private String label;

	@Column(name = "Ordering", nullable = false)
	private int ordering;

	protected SettingVersion() {
	}

	public SettingVersion(SettingApplication application, String label, int ordering) {
		this.application = application;
		this.label = label;
		this.ordering = ordering;
	}

	public SettingApplication getApplication() {
		return application;
	}

	public void setApplication(SettingApplication application) {
		this.application = application;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public long getVersionId() {
		return versionId;
	}

	public void setVersionId(long versionId) {
		this.versionId = versionId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SettingVersion)) return false;

		SettingVersion that = (SettingVersion) o;

		if (versionId != that.versionId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (versionId ^ (versionId >>> 32));
	}
}
