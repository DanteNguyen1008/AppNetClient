package com.bigcatfamility.application.model;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseDBObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7743830455040278612L;

	private int id;
	private Date createdAt, updatedAt;

	public BaseDBObject(Date createdAt, Date updatedAt) {
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public BaseDBObject(int id, Date createdAt, Date updatedAt) {
		this(createdAt, updatedAt);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
