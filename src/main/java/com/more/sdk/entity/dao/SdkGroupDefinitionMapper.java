package com.more.sdk.entity.dao;

import java.util.List;

import com.more.sdk.entity.SdkGroupDefinition;

public interface SdkGroupDefinitionMapper {
    public int deleteByPrimaryKey(Integer groupId);
    public int insert(SdkGroupDefinition record);
    public int insertSelective(SdkGroupDefinition record);
    public SdkGroupDefinition selectByPrimaryKey(Integer groupId);
    public int updateByPrimaryKeySelective(SdkGroupDefinition record);
    public int updateByPrimaryKey(SdkGroupDefinition record);
	public List<SdkGroupDefinition> selectByGroupLevel(Integer groupLevel);
}