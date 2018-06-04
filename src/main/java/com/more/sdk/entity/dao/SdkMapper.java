package com.more.sdk.entity.dao;

import java.util.List;

import com.more.sdk.entity.Sdk;

public interface SdkMapper {
	public int deleteByPrimaryKey(Integer sdkId);
	public int insert(Sdk record);
	public int insertSelective(Sdk record);
	public Sdk selectByPrimaryKey(Integer sdkId);
	public int updateByPrimaryKeySelective(Sdk record);
	public int updateByPrimaryKey(Sdk record);
	public List<Sdk> selectAll();
	public List<Sdk> selectByGroupId(Integer groupId);
	public List<Sdk> selectBySdkState(int sdkState);
}