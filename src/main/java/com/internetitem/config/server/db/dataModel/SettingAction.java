package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SettingAction")
public class SettingAction {

	@Id
	@GeneratedValue(generator = "seqAction")
	@SequenceGenerator(name = "seqAction", sequenceName = "SeqSettingAction")
	@Column(name = "ActionId")
	private long actionId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "UserId", referencedColumnName = "UserId", nullable = false)
	private SettingUser user;

	@Column(name = "Hostname", length = 50)
	private String hostname;

	@Column(name = "SettingSource", length = 100)
	private String source;

	@Column(name = "Comments")
	@Lob
	private String comments;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ApplicationGroupId", referencedColumnName = "ApplicationGroupId", nullable = false)
	private SettingApplicationGroup applicationGroup;

	@Column(name = "EventTs", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date eventTimestamp;

	@OneToMany(mappedBy = "action")
	private Set<SettingActionValue> values = new HashSet<>();

	protected SettingAction() {
	}

	public SettingAction(SettingUser user, String hostname, String source, String comments, SettingApplicationGroup applicationGroup, Date eventTimestamp) {
		this.user = user;
		this.hostname = hostname;
		this.source = source;
		this.comments = comments;
		this.applicationGroup = applicationGroup;
		this.eventTimestamp = eventTimestamp;
	}

	public long getActionId() {
		return actionId;
	}

	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

	public SettingUser getUser() {
		return user;
	}

	public void setUser(SettingUser user) {
		this.user = user;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public SettingApplicationGroup getApplicationGroup() {
		return applicationGroup;
	}

	public void setApplicationGroup(SettingApplicationGroup applicationGroup) {
		this.applicationGroup = applicationGroup;
	}

	public Date getEventTimestamp() {
		return eventTimestamp;
	}

	public void setEventTimestamp(Date eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}

	public Set<SettingActionValue> getValues() {
		return values;
	}

	public void setValues(Set<SettingActionValue> values) {
		this.values = values;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SettingAction)) return false;

		SettingAction that = (SettingAction) o;

		if (actionId != that.actionId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (actionId ^ (actionId >>> 32));
	}
}
