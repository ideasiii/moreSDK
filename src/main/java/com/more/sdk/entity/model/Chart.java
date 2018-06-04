package com.more.sdk.entity.model;

import java.io.Serializable;

public class Chart implements Serializable{

	private static final long serialVersionUID = -6159562035302084093L;
	private String label;
	private String value;
	private String html="";
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

}
