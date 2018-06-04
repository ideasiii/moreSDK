package com.more.sdk.entity.model;

import java.io.Serializable;

public class Data implements Serializable{
	private static final long serialVersionUID = -5880682975786396112L;
	private String date;
	private int count;
	private String category;
	private String poi;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPoi() {
		return poi;
	}
	public void setPoi(String poi) {
		this.poi = poi;
	}
}
