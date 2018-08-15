package com.cdvcloud.rochecloud.web.controller;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.common.PageParams;
import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.common.ParamsUtil;
import com.cdvcloud.rochecloud.domain.BtvDepartment;
import com.cdvcloud.rochecloud.domain.BtvLawyer;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:lyh
 * @Description:
 * @Date:Created in 2018/8/11 17:49
 */
@Controller
@RequestMapping(value = "lawyer/")
public class LawyerController {


	private static final Logger logger = Logger.getLogger(LawyerController.class);

	@Autowired
	LawyerService lawyerService;

	@Autowired
	UserService userService;
	@Autowired
	DepartmentService departmentService;

	@RequestMapping(value = "toPage/")
	public String toPage(@RequestParam(value = "page") String page, HttpServletRequest request) {
		if (!StringUtil.isEmpty(page)) {
			//新增律师页
			if (page.indexOf("addLawyer") != -1) {
				String roleCode = UserUtil.getUserByRequest(request, Constants.ROLE_CODE);
				String strUserId = null;
				//律师
				if (Constants.SZERO.equals(roleCode)) {
					strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
				}
				List<BtvDepartment> deptLists = departmentService.findAllDepartment(strUserId);
				request.setAttribute("deptLists", deptLists);
				//修改律师页
			} else if (page.indexOf("updateLawyer") != -1) {

			}
		}
		return page;
	}


	/**
	 * 分页展示用户信息
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
			page.setOrder("lawyer.createTime");
			String roleCode = UserUtil.getUserByRequest(request, Constants.ROLE_CODE);
			int code = Integer.valueOf(roleCode);
			page.setCondition(param);
			String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
			if (code == Constants.ZERO) {
				page.setTempParam(strUserId);
			}
			model.addAttribute("roleCode", roleCode);
			model.addAttribute("strUserId", strUserId);
			Integer totalNum = lawyerService.countFindAll(page);
			page.setTotalNum(totalNum);
			page.setList(lawyerService.selectFindAll(page));
		} catch (Exception e) {
			logger.error("查询信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("params", params);
		return "lawyer/lawyerList";
	}

	/**
	 * 分页展示律师信息-主要为律师对应的服务统计
	 *
	 * @param request
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "serviceManageList/")
	public String serviceManageList(HttpServletRequest request, Pages<Map<String, Object>> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;

		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getConditionByCAS(request, params);
			page.setOrder("lawyer.createTime");
			String roleCode = UserUtil.getUserByRequest(request, Constants.ROLE_CODE);
			int code = Integer.valueOf(roleCode);
			page.setCondition(param);
			String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
			if (code == Constants.ZERO) {
				page.setTempParam(strUserId);
			}
			model.addAttribute("roleCode", roleCode);
			model.addAttribute("strUserId", strUserId);
			Integer totalNum = lawyerService.countFindAll(page);
			page.setTotalNum(totalNum);
			page.setList(lawyerService.selectFindAll(page));
		} catch (Exception e) {
			logger.error("查询信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("params", params);
		return "serviceManage/serviceManageList";
	}


	/**
	 * 新增律师
	 *
	 * @return
	 */
	@RequestMapping(value = "addLawyer/")
	@ResponseBody
	public String addLawyer(HttpServletRequest request) {
		String strResult = "fail";
		try {
			//律师登录账号
			String accountName = request.getParameter("accountName");
			//律师登录密码
			String password = request.getParameter("password");
			//律师名称
			String lawyerName = request.getParameter("lawyerName");
			//职业年份
			String professionalYear = request.getParameter("professionalYear");
			//职业专长
			String speciality = request.getParameter("speciality");
			//职业地域
			String region = request.getParameter("region");
			//律师ID
			String departmentId = request.getParameter("departmentId");
			//律师介绍
			String introduce = request.getParameter("introduce");
			//头像
			String portrait = request.getParameter("portrait");

			logger.info("新增律师 accountName：" + accountName + "lawyerName:" + lawyerName + " departmentId: " + departmentId);
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
			user.setUserType(Constants.ZERO);
			userService.insertSelective(user);

			BtvLawyer lawyer = new BtvLawyer();
			String lawyerId = StringUtil.randomUUID();
			lawyer.setLawyerId(lawyerId);
			lawyer.setLawyerName(lawyerName);
			lawyer.setProfessionalYear(professionalYear);
			lawyer.setSpeciality(speciality);
			lawyer.setRegion(region);
			lawyer.setDepartmentId(departmentId);
			lawyer.setIntroduce(introduce);
			lawyer.setCreateUserId(strUserId);
			lawyer.setCreateTime(createTime);
			lawyer.setUserId(userId);
			lawyer.setAverage(0.0);
			lawyer.setCommentNum(0);
			lawyer.setServiceNum(0);
			lawyer.setPortrait(portrait);
			long num = lawyerService.insertSelective(lawyer);
			if (num > 0) {
				strResult = "success";
			}
		} catch (Exception e) {
			logger.error("新增律师信息异常！异常信息：" + e.getMessage());
		}
		return strResult;
	}

	/**
	 * 更新lawyer
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toUpdateLawyer/")
	public String toUpdateLawyer(HttpServletRequest request, @RequestParam(value = "id") String id, Model model) {
		BtvLawyer lawyer = lawyerService.selectByPrimaryKey(id);
		String userId = lawyer.getUserId();
		BtvUser user = userService.selectByPrimaryKey(userId);
		model.addAttribute("lawyer", lawyer);
		model.addAttribute("user", user);
		String roleCode = UserUtil.getUserByRequest(request, Constants.ROLE_CODE);
		String strUserId = null;
		//律师
		if (Constants.SZERO.equals(roleCode)) {
			strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
		}
		List<BtvDepartment> deptLists = departmentService.findAllDepartment(strUserId);
		request.setAttribute("deptLists", deptLists);
		return "lawyer/updateLawyer";
	}

	/**
	 * 更新律师
	 *
	 * @param request
	 * @return
	 * @throws MyDefineException
	 */
	@RequestMapping(value = "updateLawyer/")
	@ResponseBody
	public String updateLawyer(HttpServletRequest request) throws MyDefineException {
		String strResult = "fail";
		try {
			//律师登录账号
			String accountName = request.getParameter("accountName");
			//律师登录密码
			String password = request.getParameter("password");
			//律师名称
			String lawyerName = request.getParameter("lawyerName");
			//职业年份
			String professionalYear = request.getParameter("professionalYear");
			//职业专长
			String speciality = request.getParameter("speciality");
			//职业地域
			String region = request.getParameter("region");
			//律师ID
			String departmentId = request.getParameter("departmentId");
			//律师介绍
			String introduce = request.getParameter("introduce");
			//用户ID
			String userId = request.getParameter("userId");
			//律师ID
			String lawyerId = request.getParameter("lawyerId");

			logger.info("更新律师 accountName：" + accountName + "lawyerName:" + lawyerName + " departmentId: " + departmentId);
			String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);

			Date updateTime = DateUtil.getCurrentDateTime();
			if (!StringUtil.isEmpty(password)) {
				BtvUser user = new BtvUser();
				user.setUserId(userId);
				user.setPassword(MD5Util.getMd5ToLower(password));
				user.setUpdateUserId(strUserId);
				userService.updateByPrimaryKeySelective(user);
			}

			BtvLawyer lawyer = new BtvLawyer();
			lawyer.setLawyerId(lawyerId);
			lawyer.setLawyerName(lawyerName);
			lawyer.setProfessionalYear(professionalYear);
			lawyer.setSpeciality(speciality);
			lawyer.setRegion(region);
			lawyer.setDepartmentId(departmentId);
			lawyer.setIntroduce(introduce);
			lawyer.setUpdateUserId(strUserId);
			lawyer.setUpdateTime(updateTime);
			int num = lawyerService.updateByPrimaryKeySelective(lawyer);
			if (num > 0) {
				strResult = "success";
			}
		} catch (Exception e) {
			logger.error("更新律师信息异常！异常信息：" + e.getMessage());
		}
		return strResult;
	}

	/**
	 * 删除律师
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteLawyer/")
	@ResponseBody
	public String deleteLawyer(HttpServletRequest request, @RequestParam(value = "id") String id) {
		String strResult = "fail";
		try {
			BtvLawyer lawyer = lawyerService.selectByPrimaryKey(id);
			String userId = lawyer.getUserId();
			userService.deleteByPrimaryKey(userId);
			long num = lawyerService.deleteByPrimaryKey(id);
			if (num > 0) {
				strResult = "success";
			}
		} catch (Exception e) {
			logger.error("删除律师信息异常！异常信息：" + e.getMessage());
		}
		return strResult;
	}

}
