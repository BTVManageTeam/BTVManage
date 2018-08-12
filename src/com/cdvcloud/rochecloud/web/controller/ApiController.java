package com.cdvcloud.rochecloud.web.controller;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.common.ResponseObject;
import com.cdvcloud.rochecloud.common.ReturnState;
import com.cdvcloud.rochecloud.domain.BtvLawyer;
import com.cdvcloud.rochecloud.domain.BtvOrder;
import com.cdvcloud.rochecloud.service.LawyerService;
import com.cdvcloud.rochecloud.service.OrderService;
import com.cdvcloud.rochecloud.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "api/")
public class ApiController {
	private static final Logger logger = Logger.getLogger(ApiController.class);

	@Autowired
	private OrderService orderService;
	@Autowired
	private LawyerService lawyerService;

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
	@RequestMapping(value = "insertOrder")
	@ResponseBody
	public ResponseObject insertOrder(HttpServletRequest request,
								@RequestBody String strJson) {
		ResponseObject responseObject = new ResponseObject(ReturnState.failure.status,ReturnState.failure.detail,ReturnState.failure.enDetail);
		try {
			logger.info("新增订单入参："+strJson);
			Map<String,Object> strMap = JsonUtil.readJSON2Map(strJson);
			BtvOrder btvOrder = new BtvOrder();
			String orderId = UUIDUtil.randomUUID();
			btvOrder.setOrderId(orderId);
			btvOrder.setUserName(String.valueOf(strMap.get("userName")));
			btvOrder.setPhone(String.valueOf(strMap.get("phone")));
			btvOrder.setPieceName(String.valueOf(strMap.get("piceName")));
			btvOrder.setPieceUrls(String.valueOf(strMap.get("piceUrls")));
			btvOrder.setServiceType(String.valueOf(strMap.get("serviceType")));
			btvOrder.setScore(0.0);
			btvOrder.setCommentStatus(Constants.ZERO);
			btvOrder.setServiceStatus(Constants.ZERO);
			btvOrder.setOpenId(String.valueOf(strMap.get("openId")));
			btvOrder.setCreateTime(DateUtil.getCurrentDateTime());
			btvOrder.setCreateUserId(String.valueOf(strMap.get("lawyerId")));
			long num =orderService.insertOrder(btvOrder);
			if(num> 0){
				responseObject.setCode(ReturnState.success.status);
				responseObject.setMessage(ReturnState.success.detail);
				responseObject.setData(orderId);
			}
		} catch (Exception e) {
			logger.error("新增订单信息异常！异常信息：" + e.getMessage());
		}
		return responseObject;
	}

	/**
	 * 更新订单
	 *
	 * @param strJson
	 * @return
	 */
	@RequestMapping(value = "updateOrder")
	@ResponseBody
	public ResponseObject updateOrder(HttpServletRequest request,
									  @RequestParam(value = "id") String id,
									  @RequestBody String strJson) {
		ResponseObject responseObject = new ResponseObject(ReturnState.failure.status,ReturnState.failure.detail,ReturnState.failure.enDetail);
		try {
			logger.info("更新订单入参："+strJson+"[id="+id+"]");
			Map<String,Object> strMap = JsonUtil.readJSON2Map(strJson);
			BtvOrder btvOrder = new BtvOrder();
			btvOrder.setOrderId(id);
			if(strMap.containsKey("userName")){
				btvOrder.setUserName(String.valueOf(strMap.get("userName")));
			}
			if(strMap.containsKey("phone")){
				btvOrder.setPhone(String.valueOf(strMap.get("phone")));
			}

			if(strMap.containsKey("userName")){
				btvOrder.setPieceName(String.valueOf(strMap.get("piceName")));
			}

			if(strMap.containsKey("userName")){
				btvOrder.setPieceUrls(String.valueOf(strMap.get("piceUrls")));
			}

			if(strMap.containsKey("userName")){
				btvOrder.setServiceType(String.valueOf(strMap.get("serviceType")));
			}

			if(strMap.containsKey("score")){
				btvOrder.setScore(Double.valueOf(String.valueOf(strMap.get("score"))));
				btvOrder.setCommentStatus(Constants.ONE);
			}
			if(strMap.containsKey("openId")){
				btvOrder.setOpenId(String.valueOf(strMap.get("openId")));
			}
			btvOrder.setUpdateTime(DateUtil.getCurrentDateTime());
			String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
			btvOrder.setUpdateUserId(strUserId);
			long num =orderService.updateOrder(btvOrder);
			if(num> 0){
				responseObject.setCode(ReturnState.success.status);
				responseObject.setMessage(ReturnState.success.detail);
				responseObject.setData(ReturnState.success.enDetail);
			}
		} catch (Exception e) {
			logger.error("更新订单信息异常！异常信息：" + e.getMessage());
		}
		return responseObject;
	}

	/**
	 * 查询订单详情
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "queryOrderById")
	@ResponseBody
	public ResponseObject queryOrderById(HttpServletRequest request,
									  @RequestParam(value = "id") String id) {
		ResponseObject responseObject = new ResponseObject(ReturnState.failure.status,ReturnState.failure.detail,ReturnState.failure.enDetail);
		try {
			logger.info("更新订单入参：[id="+id+"]");
			BtvOrder btvOrder1 =orderService.queryOrder(id);
			responseObject.setCode(ReturnState.success.status);
			responseObject.setMessage(ReturnState.success.detail);
			responseObject.setData(btvOrder1);
		} catch (Exception e) {
			logger.error("根据id查询订单信息异常！异常信息：" + e.getMessage());
		}
		return responseObject;
	}

	/**
	 * 查询订单列表
	 * @param request
	 * @param openId
	 * @return
	 */
	@RequestMapping(value = "queryOrderPage")
	@ResponseBody
	public ResponseObject queryOrderPage(HttpServletRequest request,
										 @RequestParam(value = "openId") String openId,
										@RequestBody String strJson) {
		ResponseObject responseObject = new ResponseObject(ReturnState.failure.status,ReturnState.failure.detail,ReturnState.failure.enDetail);
		try {
			logger.info("分页查询订单入参：[openId="+openId+"],"+strJson);
			Map<String,Object> strMap = new HashMap<String,Object>();
			Pages<BtvOrder> page = new Pages<BtvOrder>();
			page.setNumPerPage(10);
			page.setCurrentPage(1);
			String condition = " openId = '"+openId+"'";
			if(strMap.containsKey("commentStatus")){
				condition += "and commentStatus = "+Integer.valueOf(String.valueOf(strMap.get("commentStatus")));
			}
			if(strMap.containsKey("pageNum")){
				page.setNumPerPage(Integer.valueOf(String.valueOf(strMap.get("pageNum"))));
			}
			if(strMap.containsKey("currentPage")){
				page.setCurrentPage(Integer.valueOf(String.valueOf(strMap.get("currentPage"))));
			}
			page.setCondition(condition);
			Integer totalNum = orderService.countFindAll(page);
			page.setTotalNum(totalNum);
			List<BtvOrder> btvOrderList =orderService.selectFindAll(page);
			page.setList(btvOrderList);
			responseObject.setCode(ReturnState.success.status);
			responseObject.setMessage(ReturnState.success.detail);
			responseObject.setData(page);
		} catch (Exception e) {
			logger.error("分页查询订单信息异常！异常信息：" + e.getMessage());
		}
		return responseObject;
	}

	/**
	 * 查询律师详情
	 *
	 * @param lawyerId
	 * @return
	 */
	@RequestMapping(value = "queryLawyerById")
	@ResponseBody
	public ResponseObject queryLawyerById(HttpServletRequest request,
										 @RequestParam(value = "lawyerId") String lawyerId) {
		ResponseObject responseObject = new ResponseObject(ReturnState.failure.status,ReturnState.failure.detail,ReturnState.failure.enDetail);
		try {
			logger.info("更新订单入参：[lawyerId="+lawyerId+"]");
			BtvLawyer btvLawyer =lawyerService.queryLawyerById(lawyerId);
			responseObject.setCode(ReturnState.success.status);
			responseObject.setMessage(ReturnState.success.detail);
			responseObject.setData(btvLawyer);
		} catch (Exception e) {
			logger.error("根据id查询订单信息异常！异常信息：" + e.getMessage());
		}
		return responseObject;
	}

}
