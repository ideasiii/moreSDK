package com.more.sdk.service;

import java.util.Date;
import java.util.List;

import com.more.sdk.entity.PermissionDefinition;
import com.more.sdk.entity.SdkAuthDuration;
import com.more.sdk.entity.api.User;
import com.more.sdk.entity.api.plus.UserPlus;
import com.more.sdk.entity.plus.SdkAuthDurationPlus;

public interface AdminService {
	/**
	 * 查詢已通過審核會員
	 * @return
	 */
	public List<User> getUserList();

	/**
	 * 查詢待審核會員
	 * @return
	 */
	public List<User> getUserAuditList();

	/**
	 * 查詢已刪除會員
	 * @return
	 */
	public List<User> getUserDelList();

	/**
	 * 刪除恢復會員
	 * @param user
	 * @param isDelete
	 */
	public void deleteRecoverUser(User user, boolean isDelete);

	/**
	 * 審核拒絕會員
	 * @param user
	 * @param isVerified
	 * @param updater
	 */
	public void verifiedRejectUser(User user, boolean isVerified, int updater);

	/**
	 * 查詢會員資料
	 * @param userId
	 * @return
	 */
	public UserPlus getUser(int userId);

	/**
	 * 修改會員資料
	 * @param userPlus
	 * @param updater
	 */
	public void modifyUser(UserPlus userPlus, int updater);

	/**
	 * 查詢會員SDK授權
	 * @param memberId
	 * @param sdkAuthDuration
	 * @return
	 */
	public List<SdkAuthDurationPlus> getUserSdkList(Integer memberId, SdkAuthDuration sdkAuthDuration);

	/**
	 * 修改使用者SDK授權時間
	 * @param sdkAuthDuration
	 * @param userId
	 */
	public void modifyUserSdkDate(SdkAuthDuration sdkAuthDuration, int updater);

	/**
	 * 修改app權限
	 * @param appIds
	 * @param permissionType
	 * @param updater
	 */
	public void modifyAppPermissionType(List<String> appIds, List<String> permissionType, int updater);


}
