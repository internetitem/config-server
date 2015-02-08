package com.internetitem.config.server.db.dataModel;

import com.internetitem.config.server.db.jpa.IdableEnum;
import com.internetitem.config.server.db.jpa.JpaEnumConverter;

import javax.persistence.AttributeConverter;
import java.util.HashSet;
import java.util.Set;

public enum SettingPermissionType implements IdableEnum {
	SUPERUSER(1, "Super User", SettingPermissionGrantType.GLOBAL),
	GET_SETTINGS(2, "Get Application Settings", SettingPermissionGrantType.APPLICATION),
	SET_VALUE(3, "Set Value", SettingPermissionGrantType.ALL_TYPES),
	ADMIN_APP_GROUP(4, "Administer Application Group", SettingPermissionGrantType.APPLICATION_GROUP)
	;

	private int typeId;
	private String name;
	private Set<SettingPermissionGrantType> grantTypes = new HashSet<>();

	SettingPermissionType(int typeId, String name, SettingPermissionGrantType...grantTypes) {
		this.typeId = typeId;
		this.name = name;
		for (SettingPermissionGrantType type : grantTypes) {
			this.grantTypes.add(type);
		}
	}

	public int getId() {
		return typeId;
	}

	public String getName() {
		return name;
	}

	public Set<SettingPermissionGrantType> getGrantTypes() {
		return grantTypes;
	}

	public static class SettingPermissionTypeConverter extends JpaEnumConverter<SettingPermissionType> implements AttributeConverter<SettingPermissionType, Integer> {
		@Override
		protected Class<? extends SettingPermissionType> getUnderlyingClass() {
			return SettingPermissionType.class;
		}
	}

}
