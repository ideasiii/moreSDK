package com.more.sdk.action;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.more.sdk.entity.api.User;
import com.more.sdk.entity.api.plus.UserPlus;
import com.more.sdk.entity.tool.CodeUtil;
import com.more.sdk.service.MemberService;
import com.opensymphony.xwork2.ActionContext;

@Component
@Scope("prototype")
@Namespace("/function")
@ParentPackage(value = "super-default")
@InterceptorRef(value = "authStack")
public class MemberAction extends RootAction {
	private static final long serialVersionUID = -8038918146614295774L;

	private String email;
	private String password;
	private String newPassword;
	private String confirmPassword;
	private String rememberMe;
	@Resource(name = "memberService")
	private MemberService memberService;

	private final int COOKIE_TIME = 15552000;

	@Value("${cookie.key}")
	private String cookieKey;

	private User user;

	/**
	 * 註冊頁面
	 * 
	 * @return
	 */
	@Action(value = "registered", results = { @Result(name = "success", type = "tiles", location = "function.registered") })
	public String registered() {
		return SUCCESS;
	}

	/**
	 * 會員註冊
	 * 
	 * @return
	 */
	@Action(value = "doRegistered", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String doRegistered() {
		try {
			if (user == null || StringUtils.isEmpty(user.getDisplayName()) || StringUtils.isEmpty(user.getCompany()) || StringUtils.isEmpty(user.getPhone()) 
					|| StringUtils.isEmpty(user.getPurpose()) || StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPassword) || !password.equals(confirmPassword)) {
				returnData.setMessage("輸入參數有誤");
			}else{
				memberService.registeredMember(user,password);
				returnData.setMessage("註冊成功，請等待管理人員審核");
			}
			returnData.setIsSuccess(true);
		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 登入頁面
	 * 
	 * @return
	 */
	@Action(value = "login", results = { @Result(name = "success", type = "tiles", location = "function.login") })
	public String login() {
		return SUCCESS;
	}

	/**
	 * 執行登入
	 * 
	 * @return
	 */
	@Action(value = "doLogin", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String doLogin() {
		try {
			UserPlus userPlus = memberService.memberLogin(email, password);
			ActionContext.getContext().setLocale(new Locale("en", "US"));
			if ("Y".equals(rememberMe)) {
				Cookie emailCookie = new Cookie("email", CodeUtil.encryptCode(cookieKey, email));
				Cookie passwordCookie = new Cookie("password", CodeUtil.encryptCode(cookieKey, password));
				emailCookie.setMaxAge(COOKIE_TIME);
				passwordCookie.setMaxAge(COOKIE_TIME);
				response.addCookie(emailCookie);
				response.addCookie(passwordCookie);
			}
			request.getSession().setAttribute("user", userPlus);
			returnData.setIsSuccess(true);
		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 執行登入
	 * 
	 * @return
	 */
	@Action(value = "doLogout", results = { @Result(name = "success", type = "redirectAction", location = "dashboard") })
	public String doLogout() {
		request.getSession().setAttribute("user", null);
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			for (int i = 0; i < cookies.length; i++) {
				cookies[i].setMaxAge(0);
	            response.addCookie(cookies[i]);
			}
		}
		return SUCCESS;
	}

	/**
	 * 忘記密碼頁面
	 * 
	 * @return
	 */
	@Action(value = "forget", results = { @Result(name = "success", type = "tiles", location = "function.forget") })
	public String forget() {
		return SUCCESS;
	}

	/**
	 * 執行忘記密碼
	 * 
	 * @return
	 */
	@Action(value = "doForget", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String doForget() {
		try {
			memberService.memberForget(email);
			returnData.setMessage("請至註冊Email收信");
			returnData.setIsSuccess(true);
		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}
		
		return SUCCESS;
	}

	/**
	 * 會員資料頁面
	 * 
	 * @return
	 */
	@Action(value = "member", results = { @Result(name = "success", type = "tiles", location = "function.member") })
	public String member() {
		setFunctionName(getText("member.memberInfo"));
		user = memberService.memberQueryByToken(loginUser);
		BeanUtils.copyProperties(user, loginUser);
		request.getSession().setAttribute("user", loginUser);
		return SUCCESS;
	}
	

	/**
	 * 修改會員資料
	 * 
	 * @return
	 */
	@Action(value = "modifyMember", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String modifyMember() {
		if (user == null || StringUtils.isEmpty(user.getDisplayName()) || StringUtils.isEmpty(user.getCompany()) || StringUtils.isEmpty(user.getPhone()) || StringUtils.isEmpty(user.getPurpose())) {

			returnData.setMessage("輸入參數有誤");
		} else {
			try {
				memberService.modifyMember(user, loginUser);
				BeanUtils.copyProperties(user, loginUser,new String[]{"userId"});
				request.getSession().setAttribute("user", loginUser);
				returnData.setIsSuccess(true);
				returnData.setMessage("會員資料已修改");
			} catch (RuntimeException e) {
				returnData.setMessage(e.getMessage());
			} catch (Exception e) {
				returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
			}

		}
		return SUCCESS;
	}

	/**
	 * 修改會員資料
	 * 
	 * @return
	 */
	@Action(value = "modifyMemberPassword", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String modifyMemberPassword() {
		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPassword) || !password.equals(confirmPassword)) {

			returnData.setMessage("輸入參數有誤");
		} else {
			try {
				memberService.modifyMemberPassword(loginUser.getUserId(), password);
				returnData.setIsSuccess(true);
				returnData.setMessage("會員密碼已修改");
				/*Cookie[] cookies = request.getCookies();
				if(cookies != null && cookies.length > 0){
					for (int i = 0; i < cookies.length; i++) {
						cookies[i].setMaxAge(0);
			            response.addCookie(cookies[i]);
					}
				}*/
				request.getSession().setAttribute("user", null);
			} catch (RuntimeException e) {
				returnData.setMessage(e.getMessage());
			} catch (Exception e) {
				returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
			}

		}
		return SUCCESS;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
