package com.more.sdk.entity.model;

import java.io.Serializable;
import java.util.List;

public class MapData implements Serializable{

	private static final long serialVersionUID = 3280505904432238605L;
	private List<LatLon> morningNowMap;
	private List<LatLon> noonNowMap;
	private List<LatLon> nightNowMap;
	private List<LatLon> midNowMap;
	
	private List<LatLon> morningThisMap;
	private List<LatLon> noonThisMap;
	private List<LatLon> nightThisMap;
	private List<LatLon> midThisMap;
	
	private List<LatLon> morningPreMap;
	private List<LatLon> noonPreMap;
	private List<LatLon> nightPreMap;
	private List<LatLon> midPreMap;
	private List<LatLon> preferMap;
	public List<LatLon> getNoonNowMap() {
		return noonNowMap;
	}
	public void setNoonNowMap(List<LatLon> noonNowMap) {
		this.noonNowMap = noonNowMap;
	}
	public List<LatLon> getNightNowMap() {
		return nightNowMap;
	}
	public void setNightNowMap(List<LatLon> nightNowMap) {
		this.nightNowMap = nightNowMap;
	}
	public List<LatLon> getMidNowMap() {
		return midNowMap;
	}
	public void setMidNowMap(List<LatLon> midNowMap) {
		this.midNowMap = midNowMap;
	}
	public List<LatLon> getMorningNowMap() {
		return morningNowMap;
	}
	public void setMorningNowMap(List<LatLon> morningNowMap) {
		this.morningNowMap = morningNowMap;
	}
	public List<LatLon> getMorningThisMap() {
		return morningThisMap;
	}
	public void setMorningThisMap(List<LatLon> morningThisMap) {
		this.morningThisMap = morningThisMap;
	}
	public List<LatLon> getNoonThisMap() {
		return noonThisMap;
	}
	public void setNoonThisMap(List<LatLon> noonThisMap) {
		this.noonThisMap = noonThisMap;
	}
	public List<LatLon> getNightThisMap() {
		return nightThisMap;
	}
	public void setNightThisMap(List<LatLon> nightThisMap) {
		this.nightThisMap = nightThisMap;
	}
	public List<LatLon> getMidThisMap() {
		return midThisMap;
	}
	public void setMidThisMap(List<LatLon> midThisMap) {
		this.midThisMap = midThisMap;
	}
	public List<LatLon> getMorningPreMap() {
		return morningPreMap;
	}
	public void setMorningPreMap(List<LatLon> morningPreMap) {
		this.morningPreMap = morningPreMap;
	}
	public List<LatLon> getNoonPreMap() {
		return noonPreMap;
	}
	public void setNoonPreMap(List<LatLon> noonPreMap) {
		this.noonPreMap = noonPreMap;
	}
	public List<LatLon> getNightPreMap() {
		return nightPreMap;
	}
	public void setNightPreMap(List<LatLon> nightPreMap) {
		this.nightPreMap = nightPreMap;
	}
	public List<LatLon> getMidPreMap() {
		return midPreMap;
	}
	public void setMidPreMap(List<LatLon> midPreMap) {
		this.midPreMap = midPreMap;
	}
	public List<LatLon> getPreferMap() {
		return preferMap;
	}
	public void setPreferMap(List<LatLon> preferMap) {
		this.preferMap = preferMap;
	}

}
