package com.more.sdk.service;

import java.util.List;

import com.more.sdk.entity.Sdk;

public interface SdkService {

	/**
	 * 查詢該類別的SDK
	 * @param groupId
	 * @return
	 */
	public List<Sdk> getSdkByGroup(int groupId);

	/**
	 * 查詢SDK
	 * @param sdkId
	 * @return
	 */
	public Sdk getSdk(int sdkId);

}
