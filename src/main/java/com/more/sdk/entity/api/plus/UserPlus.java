package com.more.sdk.entity.api.plus;

import com.more.sdk.entity.api.Authorize;
import com.more.sdk.entity.api.User;

public class UserPlus extends User{
	private static final long serialVersionUID = -1034599774511965149L;
	
	private String clientId;
	private Integer memberId;
	private Authorize authorize;
	private int memberLevel;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Authorize getAuthorize() {
		return authorize;
	}
	public void setAuthorize(Authorize authorize) {
		this.authorize = authorize;
	}
	public int getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}
}
