package com.more.sdk.service.impl;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.more.sdk.entity.AppPermissionDefinition;
import com.more.sdk.entity.PermissionDefinition;
import com.more.sdk.entity.Sdk;
import com.more.sdk.entity.SdkGroup;
import com.more.sdk.entity.SdkGroupDefinition;
import com.more.sdk.entity.api.Authorize;
import com.more.sdk.entity.dao.AppPermissionDefinitionMapper;
import com.more.sdk.entity.dao.PermissionDefinitionMapper;
import com.more.sdk.entity.dao.SdkGroupDefinitionMapper;
import com.more.sdk.entity.dao.SdkGroupMapper;
import com.more.sdk.entity.dao.SdkMapper;
import com.more.sdk.entity.tool.Constant;
import com.more.sdk.service.CommonService;
import com.more.sdk.service.HttpService;

@Service("commonService")
@Transactional("transactionManager")
public class CommonServiceImpl implements CommonService {

	@Value("${admin.email}")
	private String adminEmail;

	@Value("${admin.client.id}")
	private String adminClientId;

	@Value("${api.url.token.authorize}")
	private String apiUrlTokenAuthorize;

	@Value("${api.url.token.validation}")
	private String apiUrlTokenValidation;

	@Value("${more.api.url}")
	private String moreApiUrl;

	@Value("${more.tc.url}")
	private String moreTcUrl;

	@Value("${interceptor.exclude.name}")
	private String interceptorExcludeName;

	@Resource(name = "httpService")
	private HttpService httpService;

	@Resource
	private SdkGroupDefinitionMapper sdkGroupDefinitionMapper;

	@Resource
	private SdkGroupMapper sdkGroupMapper;

	@Resource
	private AppPermissionDefinitionMapper appPermissionDefinitionMapper;

	@Resource
	private PermissionDefinitionMapper permissionDefinitionMapper;

	@Resource
	private SdkMapper sdkMapper;

	public Authorize getAdminAuthorize() {
		Authorize adminAuthorize = Constant.getInstance().getAdminAuthorize();
		adminAuthorize = getAuthorize(adminEmail, adminClientId, adminAuthorize);
		Constant.getInstance().setAdminAuthorize(adminAuthorize);
		return adminAuthorize;
	}

	public boolean checkToken(String token) {
		String params = "?token=" + token;
		int statusCode = httpService.sendGet(true, apiUrlTokenValidation + params);
		return HttpURLConnection.HTTP_OK == statusCode;
	}

	public Authorize getAuthorize(String email, String clientId, Authorize authorize) {

		try {

			if (authorize != null && checkToken(authorize.getAccessToken())) {
				return authorize;
			} else {
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put("email", email);
				dataMap.put("clientId", clientId);
				authorize = (Authorize) httpService.sendPost(true, apiUrlTokenAuthorize, dataMap, Authorize.class, false);
				return authorize;
			}

		} catch (Exception e) {
			throw new RuntimeException("API權限錯誤");
		}
	}

	public void resetMenu() {
		List<SdkGroupDefinition> rootDefinitionList = sdkGroupDefinitionMapper.selectByGroupLevel(1);
		List<SdkGroupDefinition> nodeDefinitionList = sdkGroupDefinitionMapper.selectByGroupLevel(2);
		Map<Integer, SdkGroupDefinition> definitionMap = new HashMap<Integer, SdkGroupDefinition>();
		if (nodeDefinitionList != null && nodeDefinitionList.size() > 0) {
			for (SdkGroupDefinition sdkGroupDefinition : nodeDefinitionList) {
				definitionMap.put(sdkGroupDefinition.getGroupId(), sdkGroupDefinition);
			}
		}

		StringBuilder htmlBuilder = new StringBuilder();
		if (rootDefinitionList != null && rootDefinitionList.size() > 0) {
			htmlBuilder.append("<ul class=\"nav nav-second-level\">");
			for (SdkGroupDefinition sdkGroupDefinition : rootDefinitionList) {
				definitionMap.put(sdkGroupDefinition.getGroupId(), sdkGroupDefinition);
				htmlBuilder.append(loopMenuHtml(sdkGroupDefinition, definitionMap));
			}
			htmlBuilder.append("</ul>");
		}
		Constant.getInstance().setSdkGroupDefinitionMap(definitionMap);
		Constant.getInstance().setMenuHtml(htmlBuilder.toString());
	}

	private String loopMenuHtml(SdkGroupDefinition rootDefinition, Map<Integer, SdkGroupDefinition> definitionMap) {
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<li>");
		List<SdkGroup> sdKGroupList = sdkGroupMapper.selectByGroupIdNodeType(rootDefinition.getGroupId(), 2);
		if (sdKGroupList == null || sdKGroupList.size() == 0) {
			htmlBuilder.append("<a href='{contextPath}/function/sdk.action?groupId=" + rootDefinition.getGroupId() + "'>" + rootDefinition.getCategory() + "</a>");
		} else {
			htmlBuilder.append("<a href='#'>" + rootDefinition.getCategory() + "<span class='fa '></span></a>");
			htmlBuilder.append("<ul class=\"nav nav-second-level\">");
			for (SdkGroup sdkGroup : sdKGroupList) {
				SdkGroupDefinition subRootDefinition = definitionMap.get(sdkGroup.getNodeId());
				if (subRootDefinition != null) {
					htmlBuilder.append(loopMenuHtml(subRootDefinition, definitionMap));
				}
			}

			htmlBuilder.append("</ul>");
		}
		htmlBuilder.append("</li>");
		return htmlBuilder.toString();
	}

	@PostConstruct
	public void resetConstant() {
		Constant constant = Constant.getInstance();
		constant.setMoreApiUrl(moreApiUrl);
		constant.setMoreTcUrl(moreTcUrl);

		String[] excludeName = interceptorExcludeName.split(",");
		Map<String, Boolean> excludeMap = new HashMap<String, Boolean>();
		if (excludeName != null && excludeName.length > 0) {
			for (String name : excludeName) {
				excludeMap.put(name, true);
			}
		}
		constant.setExcludeMap(excludeMap);

		List<PermissionDefinition> permissionDefinitionList = permissionDefinitionMapper.selectAll();
		List<AppPermissionDefinition> appPermissionDefinitionList = appPermissionDefinitionMapper.selectAll();

		constant.setPermissionDefinitionList(permissionDefinitionList);
		constant.setAppPermissionDefinitionList(appPermissionDefinitionList);

		resetMenu();
		resetSdk();
	}

	public void resetSdk() {
		List<Sdk> sdkList = sdkMapper.selectBySdkState(1);
		StringBuffer html = new StringBuffer();
		if (sdkList != null && sdkList.size() > 0) {
			for (int i = 0; i < sdkList.size(); i++) {
				Sdk sdk = sdkList.get(i);
				if ((i + 1) % 4 == 1) {
					if ((i + 1) != 1) {
						html.append("</div>");
					}
					html.append("<div class='row row-bottom-padded-md'>");
				}
				html.append("<div class='col-md-3 col-sm-6 col-padding animate-box' data-animate-effect='fadeInLeft'>");
				html.append("<div class='blog-entry'>");
				html.append("<a href='#' class='blog-img'><img src='"+sdk.getSdkIcon()+"' class='img-responsive'></a>");
				html.append("<div class='desc' style='font-family:微軟正黑體, 'Open Sans', sans-serif;'>");
				html.append("<h3><a href='#'>"+sdk.getSdkName()+"</a></h3>");
				html.append("<span><small>應用模組化</small></span>");
				html.append("<p>"+sdk.getSdkDesc()+"</p>");
				html.append("</div>");
				html.append("</div>");
				html.append("</div>");
			}
			html.append("</div>");
			html.append("<div class='clearfix'> </div>");
		}
		Constant.getInstance().setIndexSdkHtml(html.toString());

	}

}
