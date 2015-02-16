package com.internetitem.config.server.db.dataModel;

import com.internetitem.config.server.db.jpa.IdableEnum;
import com.internetitem.config.server.db.jpa.JpaEnumConverter;

import javax.persistence.AttributeConverter;

public enum SettingActionStructureChangeType implements IdableEnum {
	APPLICATION(1),
	COMPONENT(2),
	VERSION(3),
	ENVIRONMENT(4);

	private int id;

	SettingActionStructureChangeType(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	public static class SettingActionStructureChangeTypeConverter extends JpaEnumConverter<SettingActionStructureChangeType> implements AttributeConverter<SettingActionStructureChangeType, Integer> {
		@Override
		protected Class<? extends SettingActionStructureChangeType> getUnderlyingClass() {
			return SettingActionStructureChangeType.class;
		}
	}

}
