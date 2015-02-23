package com.internetitem.config.server.services;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class AbstractService {
	protected void ensurePermission(boolean hasPermission) {
		if (!hasPermission) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}

	protected <T> T notFound(String message) {
		throw new WebApplicationException(message, Response.Status.NOT_FOUND);
	}
}
