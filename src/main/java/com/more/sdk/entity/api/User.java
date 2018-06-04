package com.more.sdk.entity.api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User  implements Serializable{

	private static final long serialVersionUID = 3518917940288783872L;
	private int userId;
	private String email;
	private String displayName;
	private String displayImageUrl;
	private String company;
	private String phone;
	private String purpose;
	private int accessLevel;
	private Date createTime;
	private String verified;
	private String agreementVersion;
	private Date lastAccessTime;
	private String lastAccessIp;
	private List<Group> groups;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDisplayImageUrl() {
		return displayImageUrl;
	}
	public void setDisplayImageUrl(String displayImageUrl) {
		this.displayImageUrl = displayImageUrl;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public int getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getVerified() {
		return verified;
	}
	public void setVerified(String verified) {
		this.verified = verified;
	}
	public String getAgreementVersion() {
		return agreementVersion;
	}
	public void setAgreementVersion(String agreementVersion) {
		this.agreementVersion = agreementVersion;
	}
	public Date getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	public String getLastAccessIp() {
		return lastAccessIp;
	}
	public void setLastAccessIp(String lastAccessIp) {
		this.lastAccessIp = lastAccessIp;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
}
