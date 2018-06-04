package com.more.sdk.entity.dao;

import org.apache.ibatis.annotations.Param;

import com.more.sdk.entity.Member;

public interface MemberMapper {
    public int deleteByPrimaryKey(Integer memberId);
    public int insert(Member record);
    public int insertSelective(Member record);
    public Member selectByPrimaryKey(Integer memberId);
    public int updateByPrimaryKeySelective(Member record);
    public int updateByPrimaryKey(Member record);
	public Member selectByUserId(Integer userId);
}