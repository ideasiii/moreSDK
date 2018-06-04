package com.more.sdk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.more.sdk.entity.Sdk;
import com.more.sdk.entity.dao.SdkMapper;
import com.more.sdk.service.SdkService;

@Service("sdkService")
@Transactional("transactionManager")
public class SdkServiceImpl implements SdkService{
	
	@Resource
	private SdkMapper sdkMapper;

	public List<Sdk> getSdkByGroup(int groupId) {
		return sdkMapper.selectByGroupId(groupId);
	}

	public Sdk getSdk(int sdkId) {
		return sdkMapper.selectByPrimaryKey(sdkId);
	}

}
