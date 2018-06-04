package com.more.sdk.entity.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class LatLon implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8735192837899835230L;
	@SerializedName("latitude")
	private double lat;
	@SerializedName("longitude")
	private double lng;
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
}
