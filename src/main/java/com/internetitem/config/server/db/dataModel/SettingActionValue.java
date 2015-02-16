package com.internetitem.config.server.db.dataModel;

import javax.persistence.*;

@Entity
@Table(name="SettingActionValue")
public class SettingActionValue {

	@Id
	@GeneratedValue(generator = "seqActionValue")
	@SequenceGenerator(name = "seqActionValue", sequenceName = "SeqSettingActionValue")
	@Column(name = "ActionValueId")
	private long actionValueId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ActionId", referencedColumnName = "ActionId", nullable = false)
	private SettingAction action;

	@Column(name="ActionTypeId", nullable = false)
	@Convert(converter = SettingActionType.SettingActionTypeConverter.class)
	private SettingActionType actionType;

	@ManyToOne
	@JoinColumn(name = "SettingId", referencedColumnName = "SettingId")
	private SettingValue value;

	protected SettingActionValue() {
	}

	public long getActionValueId() {
		return actionValueId;
	}

	public void setActionValueId(long actionValueId) {
		this.actionValueId = actionValueId;
	}

	public SettingAction getAction() {
		return action;
	}

	public void setAction(SettingAction action) {
		this.action = action;
	}

	public SettingActionType getActionType() {
		return actionType;
	}

	public void setActionType(SettingActionType actionType) {
		this.actionType = actionType;
	}

	public SettingValue getValue() {
		return value;
	}

	public void setValue(SettingValue value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SettingActionValue)) return false;

		SettingActionValue that = (SettingActionValue) o;

		if (actionValueId != that.actionValueId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (int) (actionValueId ^ (actionValueId >>> 32));
	}
}
