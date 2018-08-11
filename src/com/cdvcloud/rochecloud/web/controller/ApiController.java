package com.cdvcloud.rochecloud.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.util.StringUtil;
import com.cdvcloud.rochecloud.util.UserUtil;

@Controller
@RequestMapping(value = "api/")
public class ApiController {
	private static final Logger logger = Logger.getLogger(ApiController.class);

	/**
	 * oss上传回调方法
	 */
	@RequestMapping(value = "ossCallBack/")
	@ResponseBody
	public String ossCallBack(@RequestBody String resource, HttpServletRequest request) {
		logger.info("oss上传回调！");
		return "ok";
	}

	@RequestMapping(value = "checkLogin/")
	@ResponseBody
	public String checkLogin(HttpServletRequest request) {
		String strLoginId = UserUtil.getUserByRequest(request, Constants.LOGIN_ID);
		if (StringUtil.isEmpty(strLoginId)) {
			return "error";
		}
		return "ok";
	}
}
