package com.internetitem.config.server.services.dataModel.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CreateResponse extends AbstractResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;

	public CreateResponse(String message, long id) {
		this(message, Long.valueOf(id));
	}

	public CreateResponse(String message, Long id) {
		super(message, true);
		this.id = id;
	}

	public CreateResponse(String message) {
		super(message, false);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
