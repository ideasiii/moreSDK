package com.more.sdk.entity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.more.sdk.entity.SdkGroup;

public interface SdkGroupMapper {
	public int deleteByPrimaryKey(Integer id);
	public int insert(SdkGroup record);
	public int insertSelective(SdkGroup record);
	public SdkGroup selectByPrimaryKey(Integer id);
	public int updateByPrimaryKeySelective(SdkGroup record);
	public int updateByPrimaryKey(SdkGroup record);
	public List<SdkGroup> selectByGroupIdNodeType(@Param("groupId")Integer groupId, @Param("nodeType") Integer nodeType);
}