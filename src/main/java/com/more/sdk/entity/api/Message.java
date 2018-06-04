package com.more.sdk.entity.api;

import java.io.Serializable;

public class Message implements Serializable{
	private static final long serialVersionUID = -5038813780519366530L;
	
	private String status;
	private String message;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
