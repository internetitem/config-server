package com.internetitem.config.server.db.dataModel;

import com.internetitem.config.server.db.jpa.IdableEnum;
import com.internetitem.config.server.db.jpa.JpaEnumConverter;

import javax.persistence.AttributeConverter;

public enum SettingValueType implements IdableEnum {
	DELETED(1),
	STRING(2),
	JSON(3);

	private int id;

	private SettingValueType(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	public static class SettingValueTypeConverter extends JpaEnumConverter<SettingValueType> implements AttributeConverter<SettingValueType, Integer> {
		@Override
		protected Class<? extends SettingValueType> getUnderlyingClass() {
			return SettingValueType.class;
		}
	}
}
