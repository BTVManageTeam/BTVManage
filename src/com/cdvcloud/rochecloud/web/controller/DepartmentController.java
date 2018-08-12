package com.cdvcloud.rochecloud.web.controller;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.common.PageParams;
import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.common.ParamsUtil;
import com.cdvcloud.rochecloud.domain.BtvDepartment;
import com.cdvcloud.rochecloud.domain.BtvUser;
import com.cdvcloud.rochecloud.exception.MyDefineException;
import com.cdvcloud.rochecloud.service.DepartmentService;
import com.cdvcloud.rochecloud.service.UserService;
import com.cdvcloud.rochecloud.util.DateUtil;
import com.cdvcloud.rochecloud.util.MD5Util;
import com.cdvcloud.rochecloud.util.StringUtil;
import com.cdvcloud.rochecloud.util.UserUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 律所管理控制器
 *
 * @author lyh
 */
@Controller
@RequestMapping(value = "department/")
public class DepartmentController {

	private static final Logger logger = Logger.getLogger(DepartmentController.class);

	@Autowired
	DepartmentService deparmentService;

	@Autowired
	UserService userService;


	@RequestMapping(value = "toPage/")
	public String toPage(@RequestParam(value = "page") String page, HttpServletRequest request) {

		return page;
	}

	/**
	 * 跳转至律所页面
	 *
	 * @return
	 */
	@RequestMapping(value = "toDepartment/")
	public String toDepartment() {
		return "department/departmentList";
	}


	@RequestMapping(value = "findall/")
	public String findall(HttpServletRequest request, Pages<Map<String,Object>> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getConditionByCAS(request, params);
			page.setOrder("dept.createTime");
			page.setCondition(param);
			Integer totalNum = deparmentService.countFindAllDepartment(page);
			page.setTotalNum(totalNum);

			page.setList(deparmentService.selectFindAllDepartment(page));
		} catch (Exception e) {
			logger.error("查询律所信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("params", params);

		return "department/departmentList";
	}

	/**
	 * 校验登录名
	 *
	 * @param request
	 * @param loginId
	 * @return
	 */
	@RequestMapping("checkAccountName/")
	@ResponseBody
	public String checkAccountName(HttpServletRequest request, @RequestParam(value = "param") String loginId) {
		// 操作类型
		String operate = request.getParameter("operate");
		List<BtvUser> userLists = userService.selectByRepeatAccountName(loginId);
		// add:增加
		if ("add".equals(operate)) {
			try {
				if (userLists == null || 0 == userLists.size()) {
					return Constants.VALIDATE_YES;
				} else {
					return "该登录名已经存在！";
				}
			} catch (Exception e) {
				logger.error("根据登录名查询用户信息异常！异常信息：[" + e.getMessage() + "]");
				return Constants.VALIDATE_YES;
			}
			// update:修改
		} else {
			// 用户id
			String userId = request.getParameter("userId");
			if (null == userLists || userLists.size() == 0 || userLists.get(0).getUserId().equals(userId)) {
				return Constants.VALIDATE_YES;
			} else {
				return "该登录名已经存在！";
			}
		}
	}

	/**
	 * 新增律所
	 *
	 * @return
	 */
	@RequestMapping(value = "addDepartment/")
	@ResponseBody
	public String addDepartment(HttpServletRequest request) {
		String strResult = "fail";
		try {
			String accountName = request.getParameter("accountName");
			String password = request.getParameter("password");
			String departmentName = request.getParameter("departmentName");
			String regionName = request.getParameter("regionName");
			String departmentDescribe = request.getParameter("departmentDescribe");
			logger.info("新增律所 departmentName：" + departmentName + "regionName:" + regionName + " accountName: " + accountName);
			String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);

			Date createTime = DateUtil.getCurrentDateTime();
			BtvUser user = new BtvUser();
			String userId = StringUtil.randomUUID();
			user.setUserId(userId);
			user.setAccountName(accountName);
			user.setPassword(MD5Util.getMd5ToLower(password));
			user.setCreateTime(createTime);
			user.setCreateUserId(strUserId);
			user.setDeleteStatus(Constants.ZERO);
			user.setUserType(Constants.ONE);
			userService.insertSelective(user);

			BtvDepartment record = new BtvDepartment();
			String departmentId = StringUtil.randomUUID();
			record.setDepartmentId(departmentId);
			record.setDepartmentName(departmentName);
			record.setCreateUserId(strUserId);
			record.setCreateTime(createTime);
			record.setUserId(userId);
			record.setRegionName(regionName);
			long num = deparmentService.addDepartment(record);
			if (num > 0) {
				strResult = "success";
			}
		} catch (Exception e) {
			logger.error("新增律所信息异常！异常信息：" + e.getMessage());
		}
		return strResult;
	}


	/**
	 * 更新律所
	 *
	 * @param id
	 * @param name
	 * @return
	 * @throws MyDefineException
	 */
	@RequestMapping(value = "updateDepartment/", produces = {"text/json;charset=UTF-8"})
	@ResponseBody
	public String updateDepartment(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name) throws MyDefineException {
		String strResult = "fail";
		try {
			BtvDepartment btvDepartment = new BtvDepartment();
			btvDepartment.setDepartmentId(id);
			btvDepartment.setDepartmentName(name);
			long num = deparmentService.updateDepartment(btvDepartment);
			if (num > 0) {
				strResult = "success";
			}
		} catch (Exception e) {
			logger.error("更新律所信息异常！异常信息：" + e.getMessage());
			throw new MyDefineException(StringUtil.format("更新律所信息异常！用户ID {} 名称 {}", id, name));
		}
		return strResult;
	}

	/**
	 * 删除律所
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "removeDepartment/", produces = {"text/json;charset=UTF-8"})
	@ResponseBody
	public String removeDepartment(HttpServletRequest request, @RequestParam(value = "id") String id) {
		String strResult = "fail";
		try {
			long num = deparmentService.removeDepartment(id);
			if (num > 0) {
				strResult = "success";
			}
		} catch (Exception e) {
			logger.error("删除律所信息异常！异常信息：" + e.getMessage());
		}
		return strResult;
	}


}
