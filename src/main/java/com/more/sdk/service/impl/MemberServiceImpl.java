package com.more.sdk.service.impl;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.more.sdk.entity.Member;
import com.more.sdk.entity.Permission;
import com.more.sdk.entity.api.Authorize;
import com.more.sdk.entity.api.Client;
import com.more.sdk.entity.api.Message;
import com.more.sdk.entity.api.User;
import com.more.sdk.entity.api.plus.UserPlus;
import com.more.sdk.entity.dao.MemberMapper;
import com.more.sdk.entity.dao.PermissionMapper;
import com.more.sdk.service.AdminService;
import com.more.sdk.service.CommonService;
import com.more.sdk.service.HttpService;
import com.more.sdk.service.MemberService;

@Service("memberService")
@Transactional("transactionManager")
public class MemberServiceImpl implements MemberService {

	@Value("${api.url.token.client.id}")
	private String apiUrlTokenClientId;

	@Value("${api.url.user.query}")
	private String apiUrlUserQuery;

	@Value("${api.url.user.check}")
	private String apiUrlUserCheck;

	@Value("${api.url.user.password}")
	private String apiUrlUserPassword;
	
	@Value("${api.url.user.reset.password}")
	private String apiUrlUserResetPassword;
	
	@Value("${api.url.user}")
	private String apiUrlUser;

	@Value("${hash.key}")
	private String hashKey;
	
	@Value("${api.url.user.list.deleted}")
	private String apiUrlUserListDeleted;
	
	@Value("${api.url.user.list.available}")
	private String apiUrlUserListAvailable;
	
	@Value("${api.url.token.validation.password.reset}")
	private String apiUrlTokenValidationPasswordReset;

	@Resource(name = "httpService")
	private HttpService httpService;

	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "adminService")
	private AdminService adminService;
	
	@Resource
	private MemberMapper memberMapper;

	@Resource
	private PermissionMapper permissionMapper;
	
	@Value("${member.agree.version}")
	private String memberAgreeVersion;
	
	@Value("${reset.password.url}")
	private String resetPasswordUrl;
	@SuppressWarnings("unchecked")
	public UserPlus memberLogin(String email, String password) {
		if(email == null || password == null){
			return null;
		}
		try {
			Map<String, String> dataMap = new HashMap<String, String>();
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes());
			String tmpPassword = new BigInteger(1, messageDigest.digest()).toString(16);
			messageDigest.update((hashKey + tmpPassword).getBytes());
			dataMap.put("email", email);
			dataMap.put("hashedPassword", new BigInteger(1, messageDigest.digest()).toString(16));
			Client client = (Client) httpService.sendPost(true, apiUrlTokenClientId, dataMap, Client.class, false);

			if (client == null || client.getUserId() <= 0) {
				throw new RuntimeException("帳號密碼錯誤");
			} else {

				Authorize userAuthorize = commonService.getAuthorize(email, client.getClientId(), null);
				User user = adminService.getUser(client.getUserId());
				if (!"Y".equals(user.getVerified())) {
					throw new RuntimeException("帳號尚未審核通過");
				} else {
					Authorize adminAuthorize = commonService.getAdminAuthorize();
					List<User> userList = (List<User>) httpService.sendGet(true, apiUrlUserListDeleted + "?api_key=" + adminAuthorize.getAccessToken(), User.class, true);
					if(userList != null && userList.size() > 0){
						for (User checkUser : userList) {
							if(client.getUserEmail().equals(checkUser.getEmail())){
								throw new RuntimeException("帳號已被鎖定，請洽管理者");
							}
						}
					}
					UserPlus userPlus = new UserPlus();
					BeanUtils.copyProperties(user, userPlus);
					userPlus.setClientId(client.getClientId());
					Member member = memberMapper.selectByUserId(client.getUserId());
					if (member == null) {
						Date now  = new Date();
						member = new Member();
						member.setMemberEmail(client.getUserEmail());
						member.setUserId(client.getUserId());
						memberMapper.insertSelective(member);
						
						Permission permission = new Permission();
						permission.setMemberId(member.getMemberId());
						permission.setCreateDate(now);
						permission.setUpdateDate(now);
						permission.setMemberLevel(1);
						permission.setMemberState(0);
						permission.setUpdater(member.getMemberId());
						permissionMapper.insert(permission);
						userPlus.setMemberLevel(permission.getMemberLevel());
						
					}else{
						Permission permission = permissionMapper.selectByPrimaryKey(member.getMemberId());
						userPlus.setMemberLevel(permission.getMemberLevel());
					}
					userPlus.setMemberId(member.getMemberId());
					userPlus.setAuthorize(userAuthorize);
					return userPlus;
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("系統發生問題，請洽管理者:" + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public void memberForget(String email) {
		Authorize adminAuthorize = commonService.getAdminAuthorize();

		Message message = (Message) httpService.sendGet(true, apiUrlUserCheck + "?api_key=" + adminAuthorize.getAccessToken() + "&email=" + email, Message.class, false);
		if (message == null || message.getStatus() == null) {
			throw new RuntimeException("查無此email");
		} else {
			if (!"error".equals(message.getStatus()) || !"conflict email".equals(message.getMessage())) {
				throw new RuntimeException("查詢Email發生問題，請洽管理者");
			} else {
				List<User> userList = (List<User>) httpService.sendGet(true, apiUrlUserListAvailable + "?api_key=" + adminAuthorize.getAccessToken(), User.class, true);
				if(userList != null && userList.size() > 0){
					boolean flag = false;
					for (User checkUser : userList) {
						if(email.equals(checkUser.getEmail())){
							flag = true;
							break;
						}
					}
					if(!flag){
						throw new RuntimeException("帳號尚未被授權，請洽管理者");
					}else{
						int statusCode = httpService.sendPost(true, apiUrlUserResetPassword + "?api_key=" + adminAuthorize.getAccessToken()+"&email="+email+"&redirect-url="+resetPasswordUrl, null);
						if(HttpURLConnection.HTTP_OK != statusCode){
							throw new RuntimeException("送信失敗");
						}
					}
				}

				/*
				 * User user = httpService.sendGet(true,
				 * apiUrlUserQuery+client.getUserId()+"?api_key="+adminAuthorize
				 * .getAccessToken(), User.class);
				 * if(!"Y".equals(user.getVerified())){ throw new
				 * RuntimeException("帳號尚未審核通過"); }else{
				 * 
				 * }
				 */
			}
		}

	}

	public User memberQueryByToken(UserPlus loginUser) {
		try {
			Authorize authorize = commonService.getAuthorize(loginUser.getEmail(), loginUser.getClientId(), loginUser.getAuthorize());
			User user = (User) httpService.sendGet(true, apiUrlUser + "?api_key=" + authorize.getAccessToken(), User.class, false);
			loginUser.setAuthorize(authorize);
			if(user == null || user.getUserId() <= 0){
				throw new RuntimeException("查無會員資料");
			}
			return user;
		}catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("系統發生問題，請洽管理者:" + e.getMessage());
		}

	}

	public void modifyMember(User user, UserPlus loginUser) {
		Authorize adminAuthorize = commonService.getAdminAuthorize();
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			User olderUser = memberQueryByToken(loginUser);
			
			dataMap.put("displayName",user.getDisplayName());
			dataMap.put("displayImageUrl", olderUser.getDisplayImageUrl());
			dataMap.put("company", user.getCompany());
			dataMap.put("phone", user.getPhone());
			dataMap.put("purpose", user.getPurpose());
			dataMap.put("accessLevel", olderUser.getAccessLevel());
			dataMap.put("agreementVersion", olderUser.getAgreementVersion());
			
			int statusCode = httpService.sendPut(true, apiUrlUserQuery.replace("{userId}", olderUser.getUserId()+"") + "?api_key=" + adminAuthorize.getAccessToken(), dataMap);
			if(HttpURLConnection.HTTP_OK != statusCode){
				throw new RuntimeException("更新失敗");
			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("系統發生問題，請洽管理者:" + e.getMessage());
		}

	}

	public void modifyMemberPassword(int userId, String password) {
		Authorize adminAuthorize = commonService.getAdminAuthorize();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes());
			String tmpPassword = new BigInteger(1, messageDigest.digest()).toString(16);
			messageDigest.update((hashKey + tmpPassword).getBytes());
			int statusCode = httpService.sendPut(true, apiUrlUserPassword.replace("{userId}", userId+"")+"?password="+new BigInteger(1, messageDigest.digest()).toString(16)+"&api_key=" + adminAuthorize.getAccessToken(),null);
			if(HttpURLConnection.HTTP_OK != statusCode){
				throw new RuntimeException("修改密碼失敗");
			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("系統發生問題，請洽管理者:" + e.getMessage());
		}
		
	}

	public void registeredMember(User user, String password) {
		Date now = new Date();
		Authorize adminAuthorize = commonService.getAdminAuthorize();
		Message message = (Message) httpService.sendGet(true, apiUrlUserCheck + "?api_key=" + adminAuthorize.getAccessToken() + "&email="+user.getEmail(), Message.class, false);
		if (message != null && message.getStatus() != null) {
			throw new RuntimeException("該Email已存在，請換Email");
		} else {
			try {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				
				MessageDigest messageDigest = MessageDigest.getInstance("MD5");
				messageDigest.update(password.getBytes());
				String tmpPassword = new BigInteger(1, messageDigest.digest()).toString(16);
				messageDigest.update((hashKey + tmpPassword).getBytes());
				
				dataMap.put("email", user.getEmail());
				dataMap.put("password", new BigInteger(1, messageDigest.digest()).toString(16));
				dataMap.put("groupId", 3);
				dataMap.put("displayName", user.getDisplayName());
				dataMap.put("displayImageBase64", null);
				dataMap.put("company", user.getCompany());
				dataMap.put("phone", user.getPhone());
				dataMap.put("purpose", user.getPurpose());
				dataMap.put("agreementVersion", memberAgreeVersion);
				User registeredUser = (User) httpService.sendPost(true, apiUrlUser + "?api_key=" + adminAuthorize.getAccessToken(), dataMap ,User.class, false);
				
				if(registeredUser == null || registeredUser.getUserId()  <= 0){
					throw new RuntimeException("註冊失敗，請洽管理人員");
				}else{
					Member member = new Member();
					member.setMemberEmail(user.getEmail());
					member.setUserId(registeredUser.getUserId());
					memberMapper.insertSelective(member);
					
					Permission permission = new Permission();
					permission.setMemberId(member.getMemberId());
					permission.setCreateDate(now);
					permission.setUpdateDate(now);
					permission.setMemberLevel(0);
					permission.setMemberState(0);
					permission.setUpdater(member.getMemberId());
					permissionMapper.insert(permission);
				}
			} catch (RuntimeException e) {
				throw new RuntimeException(e.getMessage());
			} catch (Exception e) {
				throw new RuntimeException("系統發生問題，請洽管理者:" + e.getMessage());
			}
		}
	}

	public void resetPassword(int user, String token, String password) {
		
		int statusCode = httpService.sendGet(true, apiUrlTokenValidationPasswordReset + "?user=" + user + "&token="+token);
		if(HttpURLConnection.HTTP_OK != statusCode){
			throw new RuntimeException("token驗證錯誤");
		}else{
			modifyMemberPassword(user, password);
		}
		
	}

}
