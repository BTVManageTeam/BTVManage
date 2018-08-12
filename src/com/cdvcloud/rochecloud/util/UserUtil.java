package com.cdvcloud.rochecloud.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cdvcloud.rochecloud.common.LoginData;
import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.domain.BtvUser;

/**
 * session用户Util类
 * 
 * @author Dong
 * 
 */
public class UserUtil {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(UserUtil.class);


	/**
	 * 获取用户信息
	 *
	 * @param request
	 * @param params
	 *            根据用户字段获取用户信息
	 * @return
	 * @throws IOException
	 */
	public static String getUserByRequest(HttpServletRequest request, String params) {
		HttpSession session = request.getSession();
		String result = String.valueOf(session.getAttribute(params));
		return result;
	}

	/**
	 * 获取用户名称
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getUserNameOld(HttpServletRequest request) {
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		Map<String, Object> mapUserInfo = principal.getAttributes();
		String name = String.valueOf(mapUserInfo.get(Constants.CURRENT_USER_NAME));
		String userName = StringUtil.decodeString(name);
		return userName;
	}

	/**
	 * 获取用户名称
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getUserName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userName = String.valueOf(session.getAttribute(Constants.CURRENT_USER_NAME));
		return userName;
	}

	/**
	 * 保存用户名称和用户ID到session
	 * 
	 * @param request
	 * @param loginData
	 */
	public static void saveUser2Session(HttpServletRequest request, LoginData loginData) {
		HttpSession session = request.getSession();
		session.setAttribute(Constants.CURRENT_USER_ID,loginData.getUserId());
		session.setAttribute(Constants.CURRENT_USER_NAME,  loginData.getUserName());
	}

	/**
	 * 保存用户名称和用户ID到session
	 *
	 * @param request
	 * @param loginData
	 */
	public static void saveUser2SessionV2(HttpServletRequest request, LoginData loginData) {
		HttpSession session = request.getSession();
		session.setAttribute(Constants.CURRENT_USER_ID, loginData.getUserId());
		session.setAttribute(Constants.CURRENT_USER_NAME, loginData.getUserName());
		session.setAttribute(Constants.LOGIN_ID,loginData.getLoginId());
	}

	/**
	 * 清空用户名称和用户ID到session
	 * 
	 * @param request
	 */
	public static void removeUserSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}

}
