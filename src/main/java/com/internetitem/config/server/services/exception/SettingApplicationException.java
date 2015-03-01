package com.internetitem.config.server.services.exception;

public class SettingApplicationException extends Exception {
    
    public SettingApplicationException(String message) {
        super(message);
    }

    public SettingApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
