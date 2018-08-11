package com.cdvcloud.rochecloud.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.domain.BtvUser;
import com.cdvcloud.rochecloud.service.UserService;
import com.cdvcloud.rochecloud.util.MD5Util;
import com.cdvcloud.rochecloud.util.UserUtil;

/**
 * 登录，注销用户控制器
 * @author Dong
 *
 */
@Controller
@RequestMapping(value = "lizhiyun/")
public class LoginController {
	private static final Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	UserService userService;

	/**
	 * 登录
	 *
	 * @param request
	 * @param loginId
	 * @param password
	 * @return
	 */
	@RequestMapping("loginV2/")
	@ResponseBody
	public String loginV2(HttpServletRequest request, @RequestParam("loginId") String loginId,
			@RequestParam("password") String password) {
		try {
			BtvUser user = new BtvUser();
			user.setAccountName(loginId);
			password = MD5Util.getMd5ToLower(password);
			user.setPassword(password);
			user = userService.login(user);
			if (user == null) {
				return Constants.SERVICE_ERROR;
			}
			UserUtil.saveUser2SessionV2(request, user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("登录出现错误![" + e.getMessage() + "]");
			return Constants.SERVICE_ERROR;
		}
		return Constants.SERVICE_SUCCESS;
	}

	/**
	 * 注销用户
	 * @param request
	 * @return
	 */
	@RequestMapping("doLogout/")
	@ResponseBody
	public String doLogout(HttpServletRequest request) {
		UserUtil.removeUserSession(request);
		return Constants.SERVICE_SUCCESS;
	}
}
