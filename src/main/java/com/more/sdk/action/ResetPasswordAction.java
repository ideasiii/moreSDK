package com.more.sdk.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.more.sdk.service.MemberService;

@Component
@Scope("prototype")
@Namespace("/function")
@ParentPackage(value = "super-default")
@InterceptorRef(value = "authStack")
public class ResetPasswordAction extends RootAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2683749900097988647L;
	private int user;
	private String token;
	private String isVaild; 
	private String password;
	private String confirmPassword;
	@Resource(name="memberService")
	private MemberService memberService;
	
	/**
	 * 重設密碼頁面
	 * 
	 * @return
	 */
	@Action(value = "resetPassword", results = { @Result(name = "success", type = "tiles", location = "function.reset.password") })
	public String resetPassword() {
		setFunctionName("重設密碼");
		if(user <= 0 || StringUtils.isBlank(token)){
			isVaild = "N";
		}else{
			isVaild = "Y";
		}
		return SUCCESS;
	}
	
	/**
	 * 修改會員資料
	 * 
	 * @return
	 */
	@Action(value = "doResetPassword", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String doResetPassword() {
		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPassword) || !password.equals(confirmPassword) || user <= 0 || StringUtils.isBlank(token)) {
			returnData.setMessage("輸入參數有誤");
		} else {
			try {
				memberService.resetPassword(user,token,password);
				returnData.setIsSuccess(true);
				returnData.setMessage("會員密碼已修改");
			} catch (RuntimeException e) {
				returnData.setMessage(e.getMessage());
			} catch (Exception e) {
				returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
			}

		}
		return SUCCESS;
	}
	
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getIsVaild() {
		return isVaild;
	}

	public void setIsVaild(String isVaild) {
		this.isVaild = isVaild;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
