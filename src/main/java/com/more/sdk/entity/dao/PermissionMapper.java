package com.more.sdk.entity.dao;

import com.more.sdk.entity.Permission;

public interface PermissionMapper {
	public int deleteByPrimaryKey(Integer memberId);
	public int insert(Permission record);
	public int insertSelective(Permission record);
	public Permission selectByPrimaryKey(Integer memberId);
	public int updateByPrimaryKeySelective(Permission record);
	public int updateByPrimaryKey(Permission record);
}