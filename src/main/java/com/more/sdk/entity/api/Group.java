package com.more.sdk.entity.api;

import java.io.Serializable;

public class Group implements Serializable{
	
	private static final long serialVersionUID = -8807577052676837658L;
	private int userId;
	private int groupId;
	private String groupName;
	private boolean isOwner;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public boolean isOwner() {
		return isOwner;
	}
	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}
}
