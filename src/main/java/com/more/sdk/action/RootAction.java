package com.more.sdk.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.more.sdk.entity.api.plus.UserPlus;
import com.more.sdk.entity.model.ReturnData;
import com.more.sdk.entity.tool.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RootAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8326584413394771585L;
	protected HttpServletRequest request = ServletActionContext.getRequest();
	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected ReturnData returnData = new ReturnData();
	
	protected UserPlus loginUser;
	private String contextPath;
	private String functionName;
	private String moreApiUrl = Constant.getInstance().getMoreApiUrl();
	private String moreTcUrl = Constant.getInstance().getMoreTcUrl();
	private String menuHtml;
	private String language;
	
	
	public RootAction(){
		ActionContext actionContext = ActionContext.getContext();  
	    ServletContext servletContext = (ServletContext)actionContext.get(ServletActionContext.SERVLET_CONTEXT);  
	    String contextPath = servletContext.getContextPath();
		this.contextPath = contextPath;
		menuHtml = Constant.getInstance().getMenuHtml();
		menuHtml = menuHtml.replaceAll("\\{contextPath\\}", contextPath);
		loginUser = (UserPlus) request.getSession().getAttribute("user");
		language = (String) request.getSession().getAttribute("language");
	}
	
	
	public String getContextPath() {
		
		return contextPath;
	}


	public ReturnData getReturnData() {
		return returnData;
	}


	public String getFunctionName() {
		return functionName;
	}


	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}


	public UserPlus getLoginUser() {
		return loginUser;
	}


	public void setLoginUser(UserPlus loginUser) {
		this.loginUser = loginUser;
	}


	public String getMoreApiUrl() {
		return moreApiUrl;
	}


	public String getMoreTcUrl() {
		return moreTcUrl;
	}


	public String getMenuHtml() {
		return menuHtml;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}



}
