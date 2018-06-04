package com.more.sdk.entity.dao;

import java.util.List;

import com.more.sdk.entity.App;

public interface AppMapper {
    public int deleteByPrimaryKey(String appId);
    public int insert(App record);
    public int insertSelective(App record);
    public App selectByPrimaryKey(String appId);
    public int updateByPrimaryKeySelective(App record);
    public int updateByPrimaryKey(App record);
	public List<App> selectByMemberId(Integer memberId);
}