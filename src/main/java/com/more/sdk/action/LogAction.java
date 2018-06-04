package com.more.sdk.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
@Namespace("/function")
@ParentPackage(value = "super-default")
@InterceptorRef(value = "authStack")
public class LogAction extends RootAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7192184030830644253L;
	@Action(value = "log", results = { @Result(name = "success", type = "tiles", location = "function.log") })
	public String log(){
		return SUCCESS;
	}
}
