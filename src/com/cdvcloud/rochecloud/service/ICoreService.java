package com.cdvcloud.rochecloud.service;

import javax.servlet.http.HttpServletRequest;


/**
 * 业务描述：实现微信端请求业务逻辑
 * 
 * @author chenchang
 *
 */
public interface ICoreService {

	public  String processRequest(HttpServletRequest request, WxUserService userService, 
			UserMessageService messageService);
}
