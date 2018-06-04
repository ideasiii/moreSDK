package com.more.sdk.service.impl;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.more.sdk.entity.App;
import com.more.sdk.entity.AppPermission;
import com.more.sdk.entity.Member;
import com.more.sdk.entity.Permission;
import com.more.sdk.entity.Sdk;
import com.more.sdk.entity.SdkAuthDuration;
import com.more.sdk.entity.api.Authorize;
import com.more.sdk.entity.api.Message;
import com.more.sdk.entity.api.User;
import com.more.sdk.entity.api.plus.UserPlus;
import com.more.sdk.entity.dao.AppMapper;
import com.more.sdk.entity.dao.AppPermissionMapper;
import com.more.sdk.entity.dao.MemberMapper;
import com.more.sdk.entity.dao.PermissionMapper;
import com.more.sdk.entity.dao.SdkAuthDurationMapper;
import com.more.sdk.entity.dao.SdkMapper;
import com.more.sdk.entity.plus.SdkAuthDurationPlus;
import com.more.sdk.entity.tool.Constant;
import com.more.sdk.service.AdminService;
import com.more.sdk.service.CommonService;
import com.more.sdk.service.HttpService;

@Service("adminService")
@Transactional("transactionManager")
public class AdminServiceImpl implements AdminService {
	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "httpService")
	private HttpService httpService;

	@Value("${api.url.user.list.available}")
	private String apiUrlUserListAvailable;
	
	@Value("${api.url.user.list.pending}")
	private String apiUrlUserListPending;
	
	@Value("${api.url.user.list.deleted}")
	private String apiUrlUserListDeleted;
	
	@Value("${api.url.user.check}")
	private String apiUrlUserCheck;
	
	@Value("${api.url.user.query}")
	private String apiUrlUserQuery;
	
	@Value("${api.url.user.deleted}")
	private String apiUrlUserDelete;
	
	@Value("${api.url.user.verify}")
	private String apiUrlUserVerify;
	
	@Resource
	private MemberMapper memberMapper;

	@Resource
	private PermissionMapper permissionMapper;
	
	@Resource
	private AppPermissionMapper appPermissionMapper;
	
	@Resource
	private SdkAuthDurationMapper sdkAuthDurationMapper;
	
	@Resource
	private SdkMapper sdkMapper;
	
	@Resource
	private AppMapper appMapper;
	
	@SuppressWarnings("unchecked")
	public List<User> getUserList() {
		Authorize adminAuthorize = commonService.getAdminAuthorize();
		List<User> userList = (List<User>) httpService.sendGet(true, apiUrlUserListAvailable + "?api_key=" + adminAuthorize.getAccessToken(), User.class, true);
		return userList;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUserAuditList() {
		Authorize adminAuthorize = commonService.getAdminAuthorize();
		List<User> userList = (List<User>) httpService.sendGet(true, apiUrlUserListPending + "?api_key=" + adminAuthorize.getAccessToken(), User.class, true);
		return userList;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUserDelList() {
		Authorize adminAuthorize = commonService.getAdminAuthorize();
		List<User> userList = (List<User>) httpService.sendGet(true, apiUrlUserListDeleted + "?api_key=" + adminAuthorize.getAccessToken(), User.class, true);
		return userList;
	}

	public void deleteRecoverUser(User user, boolean isDelete) {
		Authorize adminAuthorize = commonService.getAdminAuthorize();
		Message message = (Message) httpService.sendGet(true, apiUrlUserCheck + "?api_key=" + adminAuthorize.getAccessToken() + "&email="+user.getEmail(), Message.class, false);
		if (message == null || message.getStatus() == null) {
			throw new RuntimeException("查無此email");
		} else {
			if (!"error".equals(message.getStatus()) || !"conflict email".equals(message.getMessage())) {
				throw new RuntimeException("查詢Email發生問題，請洽管理者");
			} else {
				int statusCode = httpService.sendPut(true, apiUrlUserDelete.replace("{userId}", user.getUserId()+"")+"?deleted="+isDelete+"&api_key=" + adminAuthorize.getAccessToken(), null);
				if(HttpURLConnection.HTTP_OK != statusCode){
					throw new RuntimeException((isDelete?"刪除":"恢復")+"使用者失敗");
				}
			}
		}
		
	}

	public void verifiedRejectUser(User user, boolean isVerified, int updater) {
		Authorize adminAuthorize = commonService.getAdminAuthorize();
		Message message = (Message) httpService.sendGet(true, apiUrlUserCheck + "?api_key=" + adminAuthorize.getAccessToken() + "&email="+user.getEmail(), Message.class, false);
		if (message == null || message.getStatus() == null) {
			throw new RuntimeException("查無此email");
		} else {
			if (!"error".equals(message.getStatus()) || !"conflict email".equals(message.getMessage())) {
				throw new RuntimeException("查詢Email發生問題，請洽管理者");
			} else {
				Member member = memberMapper.selectByUserId(user.getUserId());
				if(!isVerified){
					if(member != null){
						permissionMapper.deleteByPrimaryKey(member.getMemberId());
						memberMapper.deleteByPrimaryKey(member.getMemberId());
					}
					
				}else{
					if(member != null){
						Permission permission = permissionMapper.selectByPrimaryKey(member.getMemberId());
						permission.setMemberLevel(1);
						permission.setUpdateDate(new Date());
						permission.setUpdater(updater);
						permissionMapper.updateByPrimaryKey(permission);
					}
				}
				int statusCode = httpService.sendPut(true, apiUrlUserVerify.replace("{userId}", user.getUserId()+"")+"?verified="+isVerified+"&api_key=" + adminAuthorize.getAccessToken(), null);
				if(HttpURLConnection.HTTP_OK != statusCode){
					throw new RuntimeException((isVerified?"審核":"拒絕")+"使用者失敗");
				}
			}
		}
		
	}

	public UserPlus getUser(int userId) {
		Authorize adminAuthorize = commonService.getAdminAuthorize();
		try{
			UserPlus userPlus = (UserPlus) httpService.sendGet(true, apiUrlUserQuery.replace("{userId}", userId+"")  + "?api_key=" + adminAuthorize.getAccessToken(), UserPlus.class, false);
			if(userPlus != null && userPlus.getUserId() > 0){
				Member member = memberMapper.selectByUserId(userPlus.getUserId());
				if(member != null){
					userPlus.setMemberId(member.getMemberId());
					Permission permission = permissionMapper.selectByPrimaryKey(member.getMemberId());
					if(permission != null){
						userPlus.setMemberLevel(permission.getMemberLevel());
					}
					
				}
			}else{
				throw new RuntimeException("查詢無用戶資料");
			}
			
			return userPlus;
		}catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}catch (Exception e) {
			throw new RuntimeException("查詢用戶發生問題，請洽管理者");
		}
		
	}

	public void modifyUser(UserPlus userPlus, int updater) {
		Authorize adminAuthorize = commonService.getAdminAuthorize();
		User olderUser = getUser(userPlus.getUserId());
		if(olderUser == null){
			throw new RuntimeException("查詢用戶發生問題，請洽管理者");
		}else{
			Member member = memberMapper.selectByUserId(userPlus.getUserId());
			if(member != null){
				Permission permission = permissionMapper.selectByPrimaryKey(member.getMemberId());
				if(userPlus.getMemberLevel() != permission.getMemberLevel()){
					permission.setMemberLevel(userPlus.getMemberLevel());
					permission.setUpdateDate(new Date());
					permission.setUpdater(updater);
					permissionMapper.updateByPrimaryKey(permission);
					
					if(!"Y".equals(olderUser.getVerified()) && userPlus.getMemberLevel() > 0){
						verifiedRejectUser(olderUser, true, updater);
					}
				}
				
			}
			try {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				
				dataMap.put("displayName",userPlus.getDisplayName());
				dataMap.put("displayImageUrl", olderUser.getDisplayImageUrl());
				dataMap.put("company", userPlus.getCompany());
				dataMap.put("phone", userPlus.getPhone());
				dataMap.put("purpose", userPlus.getPurpose());
				dataMap.put("accessLevel", olderUser.getAccessLevel());
				dataMap.put("agreementVersion", olderUser.getAgreementVersion());
				
				int statusCode = httpService.sendPut(true, apiUrlUserQuery.replace("{userId}", userPlus.getUserId()+"") + "?api_key=" + adminAuthorize.getAccessToken(), dataMap);
				if(HttpURLConnection.HTTP_OK != statusCode){
					throw new RuntimeException("更新失敗");
				}
			} catch (RuntimeException e) {
				throw new RuntimeException(e.getMessage());
			} catch (Exception e) {
				throw new RuntimeException("系統發生問題，請洽管理者:" + e.getMessage());
			}
		}
		
		
	}


	public List<SdkAuthDurationPlus> getUserSdkList(Integer memberId, SdkAuthDuration querySdkAuthDuration) {
		List<SdkAuthDurationPlus> sdkAuthDurationPlusList = new ArrayList<SdkAuthDurationPlus>();
		
		if(querySdkAuthDuration.getStartDate() != null){
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(querySdkAuthDuration.getStartDate());
			startCalendar.set(Calendar.HOUR, 0);
			startCalendar.set(Calendar.MINUTE, 0);
			startCalendar.set(Calendar.SECOND, 0);
			startCalendar.set(Calendar.MILLISECOND, 0);
			querySdkAuthDuration.setStartDate(startCalendar.getTime());
		}
		
		if(querySdkAuthDuration.getEndDate() != null){
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(querySdkAuthDuration.getEndDate());
			endCalendar.set(Calendar.HOUR, 23);
			endCalendar.set(Calendar.MINUTE, 59);
			endCalendar.set(Calendar.SECOND, 59);
			endCalendar.set(Calendar.MILLISECOND, 999);
			querySdkAuthDuration.setEndDate(endCalendar.getTime());
		}
		
		List<SdkAuthDuration> tmpDataList = sdkAuthDurationMapper.selectByMemberIdDate(memberId, querySdkAuthDuration);
		Map<String, App> appMap = new HashMap<String, App>();
		Map<Integer, Sdk> sdkMap = new HashMap<Integer, Sdk>();
		Map<String, AppPermission> appPermissionMap = new HashMap<String, AppPermission>();
		if(tmpDataList != null && tmpDataList.size() > 0){
			for (SdkAuthDuration sdkAuthDuration : tmpDataList) {
				SdkAuthDurationPlus sdkAuthDurationPlus = new SdkAuthDurationPlus();
				BeanUtils.copyProperties(sdkAuthDuration, sdkAuthDurationPlus);
				App app = appMap.get(sdkAuthDurationPlus.getAppId());
				Sdk sdk = sdkMap.get(sdkAuthDurationPlus.getSdkId());
				AppPermission appPermission;
				if(app == null){
					app = appMapper.selectByPrimaryKey(sdkAuthDurationPlus.getAppId());
					appMap.put(sdkAuthDurationPlus.getAppId(), app);
					appPermission = appPermissionMapper.selectByAppId(app.getAppId());
					appPermissionMap.put(app.getAppId(), appPermission);
				}else{
					appPermission = appPermissionMap.get(app.getAppId());
				}
				
				if(sdk == null){
					sdk = sdkMapper.selectByPrimaryKey(sdkAuthDurationPlus.getSdkId());
					sdkMap.put(sdkAuthDurationPlus.getSdkId(), sdk);
				}
				
				sdkAuthDurationPlus.setAppName(app.getAppName());
				sdkAuthDurationPlus.setAppOs(app.getAppOs());
				sdkAuthDurationPlus.setSdkOs(sdk.getSdkOs());
				sdkAuthDurationPlus.setSdkName(sdk.getSdkName());
				sdkAuthDurationPlus.setPermissionDescription(Constant.getInstance().getAppPermissionDefintion(appPermission.getPermissionType()).getDescription());
				sdkAuthDurationPlusList.add(sdkAuthDurationPlus);
			}
		}
		return sdkAuthDurationPlusList;
	}

	public void modifyUserSdkDate(SdkAuthDuration sdkAuthDuration, int updater) {
		SdkAuthDuration olderSdkAuthDuration = sdkAuthDurationMapper.selectByAppIdSdkId(sdkAuthDuration.getSdkId(), sdkAuthDuration.getAppId());
		
		try{
			if(olderSdkAuthDuration == null){
				throw new RuntimeException("查無授權資料");
			}else{
				Date endDate = sdkAuthDuration.getEndDate();
				Date startDate = sdkAuthDuration.getStartDate();
				Calendar calendar = Calendar.getInstance();
				

				calendar.setTime(startDate);
				calendar.set(Calendar.HOUR, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				olderSdkAuthDuration.setStartDate(calendar.getTime());
				
				calendar.setTime(endDate);
				calendar.set(Calendar.HOUR, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				olderSdkAuthDuration.setEndDate(calendar.getTime());
				
				olderSdkAuthDuration.setUpdateDate(new Date());
				olderSdkAuthDuration.setUpdater(updater);
				sdkAuthDurationMapper.updateByPrimaryKeySelective(olderSdkAuthDuration);
			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("系統發生問題，請洽管理者:" + e.getMessage());
		}
		
	}

	public void modifyAppPermissionType(List<String> appIds, List<String> permissionType, int updater) {
		for (int i = 0; i < appIds.size(); i++) {
			AppPermission appPermission = appPermissionMapper.selectByAppId(appIds.get(i));
			appPermission.setPermissionType(Integer.valueOf(permissionType.get(i)));
			appPermissionMapper.updateByPrimaryKey(appPermission);
		}
	}

}
