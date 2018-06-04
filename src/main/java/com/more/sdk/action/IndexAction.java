package com.more.sdk.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.more.sdk.entity.tool.Constant;
import com.more.sdk.service.CommonService;
import com.more.sdk.service.MemberService;
@Component
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "super-default")
public class IndexAction extends RootAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7192184030830644253L;
	private String sdkHtml;
	@Action(value = "index", results = { @Result(name = "success",  location = "/jsp/index.jsp") })
	public String index(){
		sdkHtml = Constant.getInstance().getIndexSdkHtml();
		return SUCCESS;
	}
	public String getSdkHtml() {
		return sdkHtml;
	}
	public void setSdkHtml(String sdkHtml) {
		this.sdkHtml = sdkHtml;
	}
}