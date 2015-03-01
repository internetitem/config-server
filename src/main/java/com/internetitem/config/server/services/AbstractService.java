package com.internetitem.config.server.services;

import com.internetitem.config.server.services.exception.EntityNotFoundException;
import com.internetitem.config.server.services.exception.InsufficientPermissionsException;

public class AbstractService {
	protected void ensurePermission(boolean hasPermission) throws InsufficientPermissionsException {
		if (!hasPermission) {
			throw new InsufficientPermissionsException("Insufficient Permission");
		}
	}

	protected <T> T notFound(String message) throws EntityNotFoundException {
		throw new EntityNotFoundException(message);
	}
}
