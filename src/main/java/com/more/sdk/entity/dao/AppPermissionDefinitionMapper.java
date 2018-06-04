package com.more.sdk.entity.dao;

import java.util.List;

import com.more.sdk.entity.AppPermissionDefinition;

public interface AppPermissionDefinitionMapper {
	public int deleteByPrimaryKey(Integer id);
	public int insert(AppPermissionDefinition record);
	public int insertSelective(AppPermissionDefinition record);
	public AppPermissionDefinition selectByPrimaryKey(Integer id);
	public int updateByPrimaryKeySelective(AppPermissionDefinition record);
	public int updateByPrimaryKey(AppPermissionDefinition record);
	public List<AppPermissionDefinition> selectAll();
}