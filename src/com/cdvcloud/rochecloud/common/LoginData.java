package com.cdvcloud.rochecloud.common;

/**
 * @Author:lyh
 * @Description:登录信息实体类
 * @Date:Created in 2018/8/11 19:38
 */
public class LoginData {

	/**
	 * 用户主键
	 */
	private String userId;
	/**
	 * 登录名
	 */
	private String loginId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 类型：0 ：律师 1：律所 2：超管
	 */
	private int userType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
}
