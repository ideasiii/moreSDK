package com.more.sdk.entity.model;

import java.io.Serializable;
import java.util.List;

public class Coordinate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6894671005080623741L;
	private String category;
	private int count;
	private List<LatLon> coordinate;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<LatLon> getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(List<LatLon> coordinate) {
		this.coordinate = coordinate;
	}

}
