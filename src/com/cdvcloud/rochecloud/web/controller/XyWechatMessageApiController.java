package com.cdvcloud.rochecloud.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.common.ParamsUtil;
import com.cdvcloud.rochecloud.common.ResponseObject;
import com.cdvcloud.rochecloud.service.XyWechatMessageService;
import com.cdvcloud.rochecloud.util.JsonUtil;

/**
 * 公众号消息管理
 *
 * @Auther: zhangyuelong
 * @Date: 2018/7/9 18:38
 * @Description:
 */
@Controller
@RequestMapping("api/xy/wechatMessage")
public class XyWechatMessageApiController {


	private static final Logger logger = Logger.getLogger(XyWechatMessageApiController.class);

	private final XyWechatMessageService xyWechatMessageService;

	/**
	 * @Description: spring注入
	 */
	@Autowired
	public XyWechatMessageApiController(XyWechatMessageService xyWechatMessageService) {
		this.xyWechatMessageService = xyWechatMessageService;
	}

	/**
	 * 条件分页查询消息
	 *
	 * @param instanceId
	 * @param requestId
	 * @param ip
	 * @param uid
	 * @param commonParameters 公共参数
	 * @param str              请求参数
	 * @param request
	 * @return
	 */
	@RequestMapping("v1/queryWechatMessage4page")
	public String queryWechatMessage4page(HttpServletRequest request, Pages<Map<String, Object>> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			List<Map<String, Object>> list = xyWechatMessageService.queryWechatMessage4page(params,page);
			
			page.setList(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 回复微信公众号信息
	 *
	 * @param instanceId
	 * @param requestId
	 * @param ip
	 * @param uid
	 * @param commonParameters
	 * @param str
	 * @return
	 */
	@RequestMapping("v1/replyWechatMessage")
	@ResponseBody
	public ResponseObject replyWechatMessage(HttpServletRequest request, @RequestBody String str) {

		ResponseObject resObj = new ResponseObject();

		try {
			logger.info(";json:" + str);
			Map<String, Object> mapJson = JsonUtil.readJSON2Map(str);

			long influenceNum  = xyWechatMessageService.replyWechatMessage( mapJson,request);
			Map<String, Object> result = new ConcurrentHashMap<>(16);
			result.put("influenceNum", influenceNum);
			resObj.setCode(0);
			resObj.setData(result);
			return resObj;
		} catch (Exception e) {
			resObj.setCode(-1);
			e.printStackTrace();
			logger.error("context", e);
			return resObj;
		}
	}
}
