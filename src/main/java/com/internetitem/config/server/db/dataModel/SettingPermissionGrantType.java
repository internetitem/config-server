package com.internetitem.config.server.db.dataModel;

import com.internetitem.config.server.db.jpa.IdableEnum;
import com.internetitem.config.server.db.jpa.JpaEnumConverter;

import javax.persistence.AttributeConverter;

public enum SettingPermissionGrantType implements IdableEnum {
	GLOBAL(1),
	APPLICATION_GROUP(2),
	APPLICATION(3),
	COMPONENT(4);

	public static SettingPermissionGrantType[] ALL_TYPES = {GLOBAL, APPLICATION_GROUP, APPLICATION, COMPONENT};

	private int grantTypeId;

	SettingPermissionGrantType(int grantTypeId) {
		this.grantTypeId = grantTypeId;
	}

	public int getId() {
		return grantTypeId;
	}

	public static class SettingPermissionGrantTypeConverter extends JpaEnumConverter<SettingPermissionGrantType> implements AttributeConverter<SettingPermissionGrantType, Integer> {
		@Override
		protected Class<? extends SettingPermissionGrantType> getUnderlyingClass() {
			return SettingPermissionGrantType.class;
		}
	}

}
