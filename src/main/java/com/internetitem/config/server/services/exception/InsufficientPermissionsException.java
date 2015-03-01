package com.internetitem.config.server.services.exception;

public class InsufficientPermissionsException extends SettingApplicationException {
    public InsufficientPermissionsException(String message) {
        super(message);
    }
}
