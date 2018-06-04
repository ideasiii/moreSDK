package com.more.sdk.entity.plus;

import com.more.sdk.entity.SdkAuthDuration;

public class SdkAuthDurationPlus extends SdkAuthDuration{
	
	private String appName;
	private String sdkOs;
	private String sdkName;
	private String groupName;
	private String permissionDescription;
	private String appOs;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getSdkOs() {
		return sdkOs;
	}
	public void setSdkOs(String sdkOs) {
		this.sdkOs = sdkOs;
	}
	public String getSdkName() {
		return sdkName;
	}
	public void setSdkName(String sdkName) {
		this.sdkName = sdkName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getPermissionDescription() {
		return permissionDescription;
	}
	public void setPermissionDescription(String permissionDescription) {
		this.permissionDescription = permissionDescription;
	}
	public String getAppOs() {
		return appOs;
	}
	public void setAppOs(String appOs) {
		this.appOs = appOs;
	}

}
