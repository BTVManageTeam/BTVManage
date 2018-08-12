package com.cdvcloud.rochecloud.web.controller;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.domain.BtvOrder;
import com.cdvcloud.rochecloud.service.OrderService;
import com.cdvcloud.rochecloud.util.DateUtil;
import com.cdvcloud.rochecloud.util.StringUtil;
import com.cdvcloud.rochecloud.util.UserUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "api/")
public class ApiController {
	private static final Logger logger = Logger.getLogger(ApiController.class);

	@Autowired
	private OrderService orderService;

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

	/**
	 * 新增订单
	 *
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "insertOrder/", produces = { "text/json;charset=UTF-8" })
	@ResponseBody
	public String addDepartment(HttpServletRequest request,
								@RequestBody String strJson) {
		String strResult = "fail";
		try {
			logger.info("新增订单："+strJson);
			BtvOrder btvOrder = new BtvOrder();

			btvOrder.setCreateTime(DateUtil.getCurrentDateTime());
//			long num = orderService.addDepartment(record);
//			if(num> 0){
//				strResult = "success";
//			}
		} catch (Exception e) {
			logger.error("新增部门信息异常！异常信息：" + e.getMessage());
		}
		return strResult;
	}
}
