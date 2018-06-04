package com.more.sdk.entity.api;

import java.io.Serializable;

public class Authorize implements Serializable{
	
	private static final long serialVersionUID = -7410382715408082489L;
	
	private int userId;
	private String accessToken;
	private String refreshToken;
	private String expiresIn;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
}
