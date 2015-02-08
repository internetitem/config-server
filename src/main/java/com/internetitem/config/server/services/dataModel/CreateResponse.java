package com.internetitem.config.server.services.dataModel;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CreateResponse {
	private boolean success;
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;

	public CreateResponse(boolean success, String message, Long id) {
		this.success = success;
		this.message = message;
		this.id = id;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
