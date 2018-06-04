package com.more.sdk.entity.api;

import java.util.List;

import com.more.sdk.entity.model.Coordinate;

public class AppTitle {
	private boolean success;
	private List<String> result;
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<String> getResult() {
		return result;
	}
	public void setResult(List<String> result) {
		this.result = result;
	}
}
