package com.more.sdk.entity.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.more.sdk.entity.AppPermissionDefinition;
import com.more.sdk.entity.PermissionDefinition;
import com.more.sdk.entity.SdkGroupDefinition;
import com.more.sdk.entity.api.Authorize;

public class Constant {
	private static Constant instance;
	
	private Authorize adminAuthorize;
	
	private String moreApiUrl;
	private String moreTcUrl;
	private Map<Integer, AppPermissionDefinition> appPermissionDefintionMap;
	private Map<Integer, PermissionDefinition> permissionDefintionMap;
	private List<AppPermissionDefinition> appPermissionDefinitionList;
	private List<PermissionDefinition> permissionDefinitionList;
	private Map<Integer, SdkGroupDefinition> sdkGroupDefinitionMap; 
	private String menuHtml;
	private String indexSdkHtml;
	private Map<String, Boolean> excludeMap;
	
	public static Constant getInstance(){
		if (instance == null) {
			instance = new Constant();
		}
		return instance;
	}

	public Authorize getAdminAuthorize() {
		return adminAuthorize;
	}

	public void setAdminAuthorize(Authorize adminAuthorize) {
		this.adminAuthorize = adminAuthorize;
	}
	
	public String getMoreApiUrl() {
		return moreApiUrl;
	}

	public void setMoreApiUrl(String moreApiUrl) {
		this.moreApiUrl = moreApiUrl;
	}

	public String getMoreTcUrl() {
		return moreTcUrl;
	}

	public void setMoreTcUrl(String moreTcUrl) {
		this.moreTcUrl = moreTcUrl;
	}

	public void setExcludeMap(Map<String, Boolean> excludeMap) {
		this.excludeMap = excludeMap;
		
	}
	
	public boolean checkExcludeName(String name){
		if(excludeMap == null || StringUtils.isEmpty(name)){
			return false;
		}else{
			return excludeMap.get(name) != null && excludeMap.get(name) == true;
		}
	}

	public void setPermissionDefinitionList(List<PermissionDefinition> permissionDefinitionList) {
		this.permissionDefintionMap = new HashMap<Integer, PermissionDefinition>();
		if (permissionDefinitionList != null && permissionDefinitionList.size() > 0){
			this.permissionDefinitionList = permissionDefinitionList;
			for(PermissionDefinition permissionDefinition : permissionDefinitionList){
				this.permissionDefintionMap.put(permissionDefinition.getMemberLevel(), permissionDefinition);
			}
		}else{
			this.permissionDefinitionList = new ArrayList<PermissionDefinition>();
			
		}
	}
	public void setAppPermissionDefinitionList(List<AppPermissionDefinition> appPermissionDefinitionList) {
		this.appPermissionDefintionMap = new HashMap<Integer, AppPermissionDefinition>();
		if (appPermissionDefinitionList != null && appPermissionDefinitionList.size() > 0){
			this.appPermissionDefinitionList = appPermissionDefinitionList;
			for(AppPermissionDefinition appPermissionDefinition : appPermissionDefinitionList){
				this.appPermissionDefintionMap.put(appPermissionDefinition.getPermissionType(), appPermissionDefinition);
			}
		}else{
			this.appPermissionDefinitionList = new ArrayList<AppPermissionDefinition>();
			
		}
	}

	public List<AppPermissionDefinition> getAppPermissionDefinitionList() {
		return appPermissionDefinitionList;
	}

	public List<PermissionDefinition> getPermissionDefinitionList() {
		return permissionDefinitionList;
	}

	public AppPermissionDefinition getAppPermissionDefintion(Integer permissionType) {
		return appPermissionDefintionMap.get(permissionType);
	}

	public PermissionDefinition getPermissionDefintion(Integer memberLevel) {
		return permissionDefintionMap.get(memberLevel);
	}

	public String getMenuHtml() {
		return menuHtml;
	}

	public void setMenuHtml(String menuHtml) {
		this.menuHtml = menuHtml;
	}

	public Map<Integer, SdkGroupDefinition> getSdkGroupDefinitionMap() {
		return sdkGroupDefinitionMap;
	}

	public void setSdkGroupDefinitionMap(Map<Integer, SdkGroupDefinition> sdkGroupDefinitionMap) {
		this.sdkGroupDefinitionMap = sdkGroupDefinitionMap;
	}

	public String getIndexSdkHtml() {
		return indexSdkHtml;
	}

	public void setIndexSdkHtml(String indexSdkHtml) {
		this.indexSdkHtml = indexSdkHtml;
	}
	
}
