package com.more.sdk.entity.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.more.sdk.entity.SdkAuthDuration;

public interface SdkAuthDurationMapper {
	public int deleteByPrimaryKey(Integer authId);
	public int insert(SdkAuthDuration record);
	public int insertSelective(SdkAuthDuration record);
	public SdkAuthDuration selectByPrimaryKey(Integer authId);
	public int updateByPrimaryKeySelective(SdkAuthDuration record);
	public int updateByPrimaryKey(SdkAuthDuration record);
	public SdkAuthDuration selectByAppIdSdkId(@Param("sdkId") Integer sdkId, @Param("appId") String appId);
	public int deleteByAppId(String appId);
	public List<SdkAuthDuration> selectByMemberIdDate(@Param("memberId") Integer memberId, @Param("sdkAuthDuration")SdkAuthDuration sdkAuthDuration);
	public List<SdkAuthDuration> selectByEffective(@Param("appId")String appId, @Param("date") Date date);
}