package com.more.sdk.interceptor;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;

import com.more.sdk.entity.api.plus.UserPlus;
import com.more.sdk.entity.tool.CodeUtil;
import com.more.sdk.entity.tool.Constant;
import com.more.sdk.service.MemberService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthInterceptor implements Interceptor {
	private static final long serialVersionUID = -6394821443782299530L;
	@Resource(name = "memberService")
	private MemberService memberService;
	@Value("${cookie.key}")
	private String cookieKey;
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		String resultName = "";
		ActionContext actionContext = invocation.getInvocationContext();
		String url = ServletActionContext.getActionMapping().getNamespace() + "/" + ServletActionContext.getActionMapping().getName();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String language =  (String) request.getSession().getAttribute("language");
		if("US".equals(language)){
			ActionContext.getContext().setLocale(new Locale("en", "US"));
		}else if("TW".equals(language)){
			ActionContext.getContext().setLocale(new Locale("zh", "TW"));
		}else{
			ActionContext.getContext().setLocale(new Locale("en", "US"));
			language="US";
		}
		
		request.getSession().setAttribute("language", language);
		if (Constant.getInstance().checkExcludeName(url)) {
			if("/function/login".equals(url)){
				UserPlus userPlus = (UserPlus) request.getSession().getAttribute("user");
				if (userPlus != null && userPlus.getUserId() >0) {
					resultName = "dashboard";
				}
			}
			resultName = invocation.invoke();
		} else {
			UserPlus userPlus = (UserPlus) request.getSession().getAttribute("user");
			if (userPlus == null || userPlus.getUserId() == 0) {
				Cookie[] cookies = request.getCookies();
				if(cookies != null && cookies.length > 0){
					String email = null;
					String password = null;
					for (int i = 0; i < cookies.length; i++) {
						if("email".equals(cookies[i].getName())){
							email = CodeUtil.decryptCode(cookieKey, cookies[i].getValue());
						}else if("password".equals(cookies[i].getName())){
							password = CodeUtil.decryptCode(cookieKey, cookies[i].getValue());
						}
					}
					userPlus = memberService.memberLogin(email, password);
					if (userPlus != null && userPlus.getUserId() > 0) {
						request.getSession().setAttribute("user", userPlus);
					}
				}
			} 
			if (userPlus == null || userPlus.getUserId() == 0) {
				resultName = "sessionOut";
			}else{
				if (userPlus.getMemberLevel() != 1 && userPlus.getMemberLevel() != 2 && userPlus.getMemberLevel() != 8 && userPlus.getMemberLevel() != 9) {
					resultName = "dashboard";
				}else{
					resultName = invocation.invoke();
				}
			}
		}

		return resultName;
	}

}
