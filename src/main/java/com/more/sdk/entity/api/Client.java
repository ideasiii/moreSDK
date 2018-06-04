package com.more.sdk.entity.api;

import java.io.Serializable;

public class Client implements Serializable{

	private static final long serialVersionUID = -7669337767072223675L;
	
	private int userId;
	private String userEmail;
	private String clientId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
