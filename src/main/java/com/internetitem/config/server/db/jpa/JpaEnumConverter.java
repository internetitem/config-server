package com.internetitem.config.server.db.jpa;

import javax.persistence.AttributeConverter;


public abstract class JpaEnumConverter<EnumClass extends IdableEnum> implements AttributeConverter<EnumClass, Integer> {

	protected abstract Class<? extends EnumClass> getUnderlyingClass();

	@Override
	public Integer convertToDatabaseColumn(IdableEnum attribute) {
		if (attribute != null) {
			return Integer.valueOf(attribute.getId());
		}
		return null;
	}

	@Override
	public EnumClass convertToEntityAttribute(Integer dbData) {
		if (dbData != null) {
			int iVal = dbData.intValue();
			Class<? extends EnumClass> underlyingClass = getUnderlyingClass();
			for (EnumClass value : getUnderlyingClass().getEnumConstants()) {
				if (value.getId() == iVal) {
					return value;
				}
			}
		}
		return null;
	}
}
