package com.more.sdk.entity.api;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.more.sdk.entity.model.Data;

public class AppUsers implements Serializable{
	private static final long serialVersionUID = -2284282354272199031L;
	
	private boolean success;
	private int count;
	private String error;
	private String message;
	private List<Data> result;
	
	@SerializedName("morning_count")
	private int morningCount;
	@SerializedName("noon_count")
	private int noonCount;
	@SerializedName("night_count")
	private int nightCount;
	@SerializedName("mid_count")
	private int midCount;
	
	@SerializedName("north_count")
	private int northCount;
	@SerializedName("west_count")
	private int westCount;
	@SerializedName("east_count")
	private int eastCount;
	@SerializedName("south_count")
	private int southCount;
	
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
	public List<Data> getResult() {
		return result;
	}
	public void setResult(List<Data> result) {
		this.result = result;
	}
	public int getMorningCount() {
		return morningCount;
	}
	public void setMorningCount(int morningCount) {
		this.morningCount = morningCount;
	}
	public int getNoonCount() {
		return noonCount;
	}
	public void setNoonCount(int noonCount) {
		this.noonCount = noonCount;
	}
	public int getNightCount() {
		return nightCount;
	}
	public void setNightCount(int nightCount) {
		this.nightCount = nightCount;
	}
	public int getMidCount() {
		return midCount;
	}
	public void setMidCount(int midCount) {
		this.midCount = midCount;
	}
	public int getNorthCount() {
		return northCount;
	}
	public void setNorthCount(int northCount) {
		this.northCount = northCount;
	}
	public int getWestCount() {
		return westCount;
	}
	public void setWestCount(int westCount) {
		this.westCount = westCount;
	}
	public int getEastCount() {
		return eastCount;
	}
	public void setEastCount(int eastCount) {
		this.eastCount = eastCount;
	}
	public int getSouthCount() {
		return southCount;
	}
	public void setSouthCount(int southCount) {
		this.southCount = southCount;
	}
}
