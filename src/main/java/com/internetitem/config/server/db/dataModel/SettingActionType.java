package com.internetitem.config.server.db.dataModel;

import com.internetitem.config.server.db.jpa.IdableEnum;
import com.internetitem.config.server.db.jpa.JpaEnumConverter;

import javax.persistence.AttributeConverter;

public enum SettingActionType implements IdableEnum {
	CREATE(1),
	DELETE(2);

	private int id;

	SettingActionType(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	public static class SettingActionTypeConverter extends JpaEnumConverter<SettingActionType> implements AttributeConverter<SettingActionType, Integer> {
		@Override
		protected Class<? extends SettingActionType> getUnderlyingClass() {
			return SettingActionType.class;
		}
	}
}
