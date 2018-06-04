package com.more.sdk.entity.api;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.more.sdk.entity.model.Coordinate;
import com.more.sdk.entity.model.Data;
import com.more.sdk.entity.model.LatLon;

public class AppPoiLatLonUsers implements Serializable{
	private static final long serialVersionUID = -2284282354272199031L;
	
	private boolean success;
	private int count;
	private String error;
	private String message;
	private List<Coordinate> result;
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Coordinate> getResult() {
		return result;
	}
	public void setResult(List<Coordinate> result) {
		this.result = result;
	}
}
