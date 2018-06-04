package com.more.sdk.entity.dao;

import com.more.sdk.entity.AppPermission;

public interface AppPermissionMapper {
	public int deleteByPrimaryKey(Integer id);
	public int insert(AppPermission record);
	public int insertSelective(AppPermission record);
	public AppPermission selectByPrimaryKey(Integer id);
	public int updateByPrimaryKeySelective(AppPermission record);
	public int updateByPrimaryKey(AppPermission record);
	public AppPermission selectByAppId(String appId);
	public void deleteByAppId(String appId);
}