package com.bigcatfamily.networkhandler.db;

public class APIError {
	private int error;
	private String description;

	public APIError(int error, String description) {
		this.error = error;
		this.description = description;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
