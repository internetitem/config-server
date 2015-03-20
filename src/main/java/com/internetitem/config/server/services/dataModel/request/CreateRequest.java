package com.internetitem.config.server.services.dataModel.request;

import javax.validation.constraints.NotNull;

public class CreateRequest {

	@NotNull
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
