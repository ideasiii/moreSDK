package com.more.sdk.service;

import com.more.sdk.entity.api.Authorize;

public interface CommonService {
	
	public Authorize getAdminAuthorize();
	
	public Authorize getAuthorize(String email, String clientId, Authorize authorize);
	
	public void resetMenu();
	
	public void resetConstant();
	
	public void resetSdk();
}
