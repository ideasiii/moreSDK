package com.more.sdk.service;

import com.more.sdk.entity.api.User;
import com.more.sdk.entity.api.plus.UserPlus;

public interface MemberService {

	/**
	 * 會員登入
	 * @param email
	 * @param password
	 * @return
	 */
	public UserPlus memberLogin(String email, String password);

	/**
	 * 忘記密碼
	 * @param email
	 */
	public void memberForget(String email);

	/**
	 * 查詢會員資料(Token)
	 * @param token
	 * @return 
	 */
	public User memberQueryByToken(UserPlus loginUser);

	/**
	 * 修改會員資料
	 * @param user 
	 * @param user
	 * @param token
	 */
	public void modifyMember(User user, UserPlus loginUser);

	/**
	 * 修改會員密碼
	 * @param userId
	 * @param password
	 */
	public void modifyMemberPassword(int userId, String password);

	/**
	 * 會員註冊
	 * @param user
	 * @param password
	 */
	public void registeredMember(User user, String password);

	/**
	 * 重置密碼
	 * @param user
	 * @param token
	 * @param password
	 */
	public void resetPassword(int user, String token, String password);


}
