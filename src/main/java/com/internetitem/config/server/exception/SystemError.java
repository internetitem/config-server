package com.internetitem.config.server.exception;

public class SystemError extends RuntimeException {

	public SystemError(String message) {
		super(message);
	}

	public SystemError(String message, Throwable cause) {
		super(message, cause);
	}
}
