package com.more.sdk.entity.dao;

import java.util.List;

import com.more.sdk.entity.PermissionDefinition;

public interface PermissionDefinitionMapper {
	public int deleteByPrimaryKey(Integer memberLevel);
	public int insert(PermissionDefinition record);
	public int insertSelective(PermissionDefinition record);
	public PermissionDefinition selectByPrimaryKey(Integer memberLevel);
	public int updateByPrimaryKeySelective(PermissionDefinition record);
	public int updateByPrimaryKey(PermissionDefinition record);
	public List<PermissionDefinition> selectAll();
}