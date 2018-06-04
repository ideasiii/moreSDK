package com.more.sdk.entity.model;

import java.io.Serializable;

public class ReturnData implements Serializable{
	private static final long serialVersionUID = 9028984928106229790L;
	
	private boolean isSuccess;
	private String message;
	private Object data;
	
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
