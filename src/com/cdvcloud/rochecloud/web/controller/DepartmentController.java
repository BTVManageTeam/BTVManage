package com.cdvcloud.rochecloud.web.controller;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.common.PageParams;
import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.common.ParamsUtil;
import com.cdvcloud.rochecloud.domain.BtvDepartment;
import com.cdvcloud.rochecloud.domain.BtvUser;
import com.cdvcloud.rochecloud.exception.MyDefineException;
import com.cdvcloud.rochecloud.service.DepartmentService;
import com.cdvcloud.rochecloud.service.LawyerService;
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
import java.util.*;

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

	@Autowired
	LawyerService lawyerService;


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


	/**
	 * 律所列表
	 *
	 * @param request
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findall/")
	public String findall(HttpServletRequest request, Pages<Map<String, Object>> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getConditionByCAS(request, params);
			page.setOrder("dept.createTime");
			String roleCode = UserUtil.getUserByRequest(request, Constants.ROLE_CODE);
			int code = Integer.valueOf(roleCode);
			page.setCondition(param);
			String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
			if (code == Constants.ONE) {
				page.setTempParam(strUserId);
			}
			model.addAttribute("roleCode", roleCode);
			model.addAttribute("strUserId", strUserId);
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
			record.setDepartmentDescribe(departmentDescribe);
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
	 * 跳转至更新律所页面
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toUpdateDepartment/")
	public String toUpdateDepartment(@RequestParam(value = "id") String id, Model model) {
		BtvDepartment department = deparmentService.findDeparmentById(id);
		String userId = department.getUserId();
		BtvUser user = userService.selectByPrimaryKey(userId);
		model.addAttribute("department", department);
		List<String> listStr = new ArrayList<String>();
		listStr.add("东城区");
		listStr.add("西城区");
		listStr.add("朝阳区");
		listStr.add("崇文区");
		listStr.add("海淀区");
		listStr.add("宣武区");
		listStr.add("丰台区");
		listStr.add("房山区");
		listStr.add("大兴区");
		listStr.add("通州区");
		listStr.add("顺义区");
		listStr.add("平谷区");
		listStr.add("昌平区");
		listStr.add("怀柔区");
		listStr.add("延庆县");
		listStr.add("密云县");
		listStr.add("石景山区");
		listStr.add("门头沟区");
		model.addAttribute("regions", listStr);
		model.addAttribute("user", user);
		return "department/updateDepartment";
	}

	/**
	 * 更新律所
	 *
	 * @return
	 * @throws MyDefineException
	 */
	@RequestMapping(value = "updateDepartment/")
	@ResponseBody
	public String updateDepartment(HttpServletRequest request) throws MyDefineException {
		String strResult = "fail";
		String departmentId = "";
		try {
			departmentId = request.getParameter("departmentId");
			String userId = request.getParameter("userId");
			String password = request.getParameter("password");
			String regionName = request.getParameter("regionName");
			String departmentDescribe = request.getParameter("departmentDescribe");

			String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
			if (!StringUtil.isEmpty(password)) {
				BtvUser user = new BtvUser();
				user.setUserId(userId);
				user.setPassword(MD5Util.getMd5ToLower(password));
				user.setUpdateUserId(strUserId);
				userService.updateByPrimaryKeySelective(user);
			}

			BtvDepartment btvDepartment = new BtvDepartment();
			btvDepartment.setRegionName(regionName);
			btvDepartment.setDepartmentDescribe(departmentDescribe);
			btvDepartment.setUpdateTime(DateUtil.getCurrentDateTime());
			btvDepartment.setUpdateUserId(strUserId);
			btvDepartment.setDepartmentId(departmentId);
			long num = deparmentService.updateDepartment(btvDepartment);
			if (num > 0) {
				strResult = "success";
			}
		} catch (Exception e) {
			logger.error("更新律所信息异常！异常信息：" + e.getMessage());
			throw new MyDefineException(StringUtil.format("更新律所信息异常！律所ID {} ", departmentId));
		}
		return strResult;
	}

	/**
	 * 删除律所
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteDepartment/")
	@ResponseBody
	public String removeDepartment(HttpServletRequest request, @RequestParam(value = "id") String id) {
		String strResult = "fail";
		try {
			int count = lawyerService.selectByDeptId(id);
			if (count > 0) {
				return "no";
			}
			BtvDepartment dept = deparmentService.findDeparmentById(id);
			String userId = dept.getUserId();
			userService.deleteByPrimaryKey(userId);
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
