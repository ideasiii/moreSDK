package com.more.sdk.entity;

import java.util.Date;

public class AppPermission {
    private Integer id;
    private String appId;
    private Integer permissionType;
    private Date createDate;
    private Date updateDate;
    private Integer updater;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }
    public Integer getPermissionType() {
        return permissionType;
    }
    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }
}