package com.cdvcloud.rochecloud.web.controller;

import com.cdvcloud.rochecloud.common.*;
import com.cdvcloud.rochecloud.domain.BtvLawyer;
import com.cdvcloud.rochecloud.domain.BtvOrder;
import com.cdvcloud.rochecloud.service.LawyerService;
import com.cdvcloud.rochecloud.service.OrderService;
import com.cdvcloud.rochecloud.util.DateUtil;
import com.cdvcloud.rochecloud.util.UserUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:lyh
 * @Description:
 * @Date:Created in 2018/8/11 17:49
 */
@Controller
@RequestMapping(value = "order/")
public class OrderController {

	private static final Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;
	@Autowired
	private LawyerService lawyerService;

	/**
	 * 分页查询订单信息-服务管理页面根据律师查询的订单列表
	 * @param request
	 * @param page
	 * @param model
	 * @param lawyerId
	 * @return
	 */
	@RequestMapping(value = "queryOrderPage")
	public String queryOrderPage(HttpServletRequest request, Pages<BtvOrder> page, Model model,
							@RequestParam(value = "lawyerId") String lawyerId) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getConditionByCAS(request, params);
			param += "createUserId = '"+ lawyerId +"'";
			page.setCondition(param);
			String roleCode = UserUtil.getUserByRequest(request, Constants.ROLE_CODE);
			int code = Integer.valueOf(roleCode);
			String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
			if (code == Constants.ZERO) {
				page.setTempParam(strUserId);
			}
			model.addAttribute("roleCode", roleCode);
			model.addAttribute("strUserId", strUserId);
			Integer totalNum = orderService.countFindAll(page);
			page.setTotalNum(totalNum);
			page.setList(orderService.selectFindAll(page));
			//查询律师信息相关内容并渲染页面
			BtvLawyer btvLawyer = lawyerService.selectByPrimaryKey(lawyerId);
			model.addAttribute("serviceNum", btvLawyer.getServiceNum());
			model.addAttribute("commentNum", btvLawyer.getCommentNum());
			model.addAttribute("average", btvLawyer.getAverage());
			model.addAttribute("lawyerName", btvLawyer.getLawyerName());
		} catch (Exception e) {
			logger.error("查询信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("params", params);
		return "order/orderList";
	}

	/**
	 * 更新订单
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateOrder")
	@ResponseBody
	public ResponseObject updateOrder(HttpServletRequest request,
									  @RequestParam(value = "id") String id) {
		ResponseObject responseObject = new ResponseObject(ReturnState.failure.status,ReturnState.failure.detail,ReturnState.failure.enDetail);
		try {
			logger.info("更新订单入参：[id="+id+"]");
			BtvOrder btvOrder = new BtvOrder();
			btvOrder.setOrderId(id);
			btvOrder.setServiceStatus(Constants.ONE);
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
	 *
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "queryPieceUrls")
	public String queryPieceUrls(HttpServletRequest request, Model model,
								 @RequestParam(value = "id") String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;
		try {
			//查询律师信息相关内容并渲染页面
			BtvOrder btvOrder = orderService.queryOrder(id);
			//将图片按照，切分放到数组中进行传递
			String pieceUrls = btvOrder.getPieceUrls();
			String[] pieceUrlList = pieceUrls.split(",");
			model.addAttribute("lawyerId", btvOrder.getCreateUserId());
			model.addAttribute("pieceUrls", pieceUrlList);
		} catch (Exception e) {
			logger.error("查询信息异常！[" + e.getMessage() + "]");
		}
		return "order/orderPieceUrls";
	}

}
