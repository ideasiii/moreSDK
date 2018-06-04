package com.more.sdk.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.more.sdk.entity.App;
import com.more.sdk.entity.PermissionDefinition;
import com.more.sdk.entity.SdkAuthDuration;
import com.more.sdk.entity.api.User;
import com.more.sdk.entity.api.plus.UserPlus;
import com.more.sdk.entity.plus.AppDataPlus;
import com.more.sdk.entity.plus.SdkAuthDurationPlus;
import com.more.sdk.entity.tool.Constant;
import com.more.sdk.service.AdminService;
import com.more.sdk.service.AppService;

@Component
@Scope("prototype")
@Namespace("/function")
@ParentPackage(value = "super-default")
@InterceptorRef(value="authStack")
public class AdminAction extends RootAction {

	private static final long serialVersionUID = 1396625580457962837L;
	@Resource(name = "adminService")
	private AdminService adminService;
	private UserPlus userPlus;
	private List<PermissionDefinition> permissionDefinitionList;
	private SdkAuthDuration sdkAuthDuration;
	private String startDate;
	private String endDate;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private List<AppDataPlus> appDataPlusList;
	@Resource(name = "appService")
	private AppService appService;
	private String isAdmin;
	private String appIds;
	private String permissionTypes;
	
	private List<App> appList;
	/**
	 * 已註冊使用者頁面
	 * 
	 * @return
	 */
	@Action(value = "user", results = { @Result(name = "success", type = "tiles", location = "function.user") })
	public String user() {
		setFunctionName(getText("sidebar.registeredUsers"));
		return SUCCESS;
	}

	/**
	 * 已註冊使用者列表
	 * 
	 * @return
	 */
	@Action(value = "getUserList", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getUserList() {
		List<User> userList = adminService.getUserList();
		returnData.setData(userList);
		return SUCCESS;
	}

	/**
	 * 待審核使用者頁面
	 * 
	 * @return
	 */
	@Action(value = "userAudit", results = { @Result(name = "success", type = "tiles", location = "function.user.audit") })
	public String userAudit() {
		setFunctionName(getText("sidebar.unanthorizedUsers"));
		return SUCCESS;
	}

	/**
	 * 待審核使用者列表
	 * 
	 * @return
	 */
	@Action(value = "getUserAuditList", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getUserAuditList() {
		List<User> userList = adminService.getUserAuditList();
		returnData.setData(userList);
		return SUCCESS;
	}

	/**
	 * 已刪除帳號頁面
	 * 
	 * @return
	 */
	@Action(value = "userDel", results = { @Result(name = "success", type = "tiles", location = "function.user.del") })
	public String userDel() {
		setFunctionName(getText("sidebar.blackList"));
		return SUCCESS;
	}

	/**
	 * 已刪除帳號列表
	 * 
	 * @return
	 */
	@Action(value = "getUserDelList", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getUserDelList() {
		List<User> userList = adminService.getUserDelList();
		returnData.setData(userList);
		return SUCCESS;
	}

	/**
	 * 刪除使用者
	 * 
	 * @return
	 */

	@Action(value = "deleteUser", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String deleteUser() {
		try {
			adminService.deleteRecoverUser(userPlus, true);
			returnData.setIsSuccess(true);
			returnData.setMessage("該用戶已刪除");
		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * 恢復使用者
	 * 
	 * @return
	 */

	@Action(value = "recoverUser", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String recoverUser() {
		try {
			adminService.deleteRecoverUser(userPlus, false);
			returnData.setIsSuccess(true);
			returnData.setMessage("該用戶已恢復");
		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * 通過審核使用者
	 * 
	 * @return
	 */

	@Action(value = "verifiedUser", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String verifiedUser() {
		try {
			adminService.verifiedRejectUser(userPlus, true, loginUser.getUserId());
			returnData.setIsSuccess(true);
			returnData.setMessage("該用戶已審核通過");
		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * 拒絕使用者
	 * 
	 * @return
	 */

	@Action(value = "rejectUser", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String rejectUser() {
		try {
			adminService.verifiedRejectUser(userPlus, false, loginUser.getUserId());
			returnData.setIsSuccess(true);
			returnData.setMessage("該用戶已拒絕");
		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * 編輯使用者頁面
	 * 
	 * @return
	 */
	@Action(value = "userModify", results = { @Result(name = "success", type = "tiles", location = "function.user.modify") })
	public String userModify() {
		setFunctionName("使用者資料管理");
		userPlus = adminService.getUser(userPlus.getUserId());
		permissionDefinitionList = Constant.getInstance().getPermissionDefinitionList();
		return SUCCESS;
	}

	/**
	 * 編輯使用者資料
	 * 
	 * @return
	 */
	@Action(value = "modifyUser", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String modifyUser() {
		if (userPlus == null || StringUtils.isEmpty(userPlus.getDisplayName()) || StringUtils.isEmpty(userPlus.getCompany()) || 
				StringUtils.isEmpty(userPlus.getPhone()) || StringUtils.isEmpty(userPlus.getPurpose()) || userPlus.getUserId() <= 0 ||
				userPlus.getMemberLevel() < 0) {
			returnData.setMessage("輸入參數有誤");
		} else {
			try {
				adminService.modifyUser(userPlus, loginUser.getUserId());
				returnData.setMessage("用戶資料已修改");
				returnData.setIsSuccess(true);
			} catch (RuntimeException e) {
				returnData.setMessage(e.getMessage());
			} catch (Exception e) {
				returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
			}

		}
		return SUCCESS;
	}
	
	/**
	 * 使用者SDK授權頁面
	 * 
	 * @return
	 */
	@Action(value = "userSdk", results = { @Result(name = "success", type = "tiles", location = "function.user.sdk") })
	public String userSdk() {
		setFunctionName("SDK 授權管理");
		userPlus = adminService.getUser(userPlus.getUserId());
		appList = appService.getAppList(userPlus.getMemberId());
		
		return SUCCESS;
	}
	
	/**
	 * 使用者SDK授權列表
	 * 
	 * @return
	 */
	@Action(value = "getUserSdkList", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getUserSdkList() {
		try {
			Date startDate = null;
			Date endDate = null;
			if(this.startDate != null){
				startDate = dateFormat.parse(this.startDate);
			}
			if(this.endDate != null){
				endDate = dateFormat.parse(this.endDate);
			}
			if(sdkAuthDuration == null){
				sdkAuthDuration = new SdkAuthDuration();
			}
			sdkAuthDuration.setStartDate(startDate);
			sdkAuthDuration.setEndDate(endDate);
			List<SdkAuthDurationPlus> sdkAuthDurationPlusList = adminService.getUserSdkList(userPlus.getMemberId(), sdkAuthDuration);
			returnData.setData(sdkAuthDurationPlusList);
		} catch (ParseException e) {
			throw new RuntimeException("參數輸入有誤");
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 編輯使用者SDK授權時間
	 * 
	 * @return
	 */
	@Action(value = "modifyUserSdkDate", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String modifyUserSdkDate() {
		try {
			sdkAuthDuration.setStartDate(dateFormat.parse(startDate));
			sdkAuthDuration.setEndDate(dateFormat.parse(endDate));
			adminService.modifyUserSdkDate(sdkAuthDuration, loginUser.getUserId());
			returnData.setMessage("授權時間已修改");
			returnData.setIsSuccess(true);
		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * App Dashboard
	 * 
	 * @return
	 */
	@Action(value = "adminAppDashboard", results = { @Result(name = "success", type = "tiles", location = "function.app.dashboard") })
	public String appDashboard() {
		setFunctionName("APP Dashboard");
		userPlus  = adminService.getUser(userPlus.getUserId());
		appDataPlusList = appService.appDashboard(userPlus.getMemberId());
		isAdmin = "Y";
		return SUCCESS;
	}
	
	/**
	 * 編輯使用者資料
	 * 
	 * @return
	 */
	@Action(value = "modifyAppPermissionType", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String modifyAppPermissionType() {
		if (appIds == null || permissionTypes == null) {
			returnData.setMessage("輸入參數有誤");
		} else {
			try {
				adminService.modifyAppPermissionType(Arrays.asList(appIds.split(",")), Arrays.asList(permissionTypes.split(",")), loginUser.getUserId());
				returnData.setMessage("用戶資料已修改");
				returnData.setIsSuccess(true);
			} catch (RuntimeException e) {
				returnData.setMessage(e.getMessage());
			} catch (Exception e) {
				returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
			}

		}
		return SUCCESS;
	}
	
	public List<PermissionDefinition> getPermissionDefinitionList() {
		return permissionDefinitionList;
	}

	public void setPermissionDefinitionList(List<PermissionDefinition> permissionDefinitionList) {
		this.permissionDefinitionList = permissionDefinitionList;
	}


	public UserPlus getUserPlus() {
		return userPlus;
	}

	public void setUserPlus(UserPlus userPlus) {
		this.userPlus = userPlus;
	}

	public SdkAuthDuration getSdkAuthDuration() {
		return sdkAuthDuration;
	}

	public void setSdkAuthDuration(SdkAuthDuration sdkAuthDuration) {
		this.sdkAuthDuration = sdkAuthDuration;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public List<AppDataPlus> getAppDataPlusList() {
		return appDataPlusList;
	}

	public void setAppDataPlusList(List<AppDataPlus> appDataPlusList) {
		this.appDataPlusList = appDataPlusList;
	}

	public String isAdmin() {
		return isAdmin;
	}

	public void setAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getAppIds() {
		return appIds;
	}

	public void setAppIds(String appIds) {
		this.appIds = appIds;
	}

	public String getPermissionTypes() {
		return permissionTypes;
	}

	public void setPermissionTypes(String permissionTypes) {
		this.permissionTypes = permissionTypes;
	}

	public List<App> getAppList() {
		return appList;
	}

	public void setAppList(List<App> appList) {
		this.appList = appList;
	}
}
