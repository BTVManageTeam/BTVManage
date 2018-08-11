package com.cdvcloud.rochecloud.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.common.PageParams;
import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.common.ParamsUtil;
import com.cdvcloud.rochecloud.domain.OpRmsDeparment;
import com.cdvcloud.rochecloud.domain.OpRmsOwnerBusiness;
import com.cdvcloud.rochecloud.domain.OpRmsUser;
import com.cdvcloud.rochecloud.domain.OpRmsUserDepartment;
import com.cdvcloud.rochecloud.domain.OpRmsUserDepartmentTemp;
import com.cdvcloud.rochecloud.domain.OpRmsUserGroup;
import com.cdvcloud.rochecloud.domain.OpRmsUserOwnerBusiness;
import com.cdvcloud.rochecloud.domain.OpRmsUserTemp;
import com.cdvcloud.rochecloud.domain.OpRmsYunkey;
import com.cdvcloud.rochecloud.service.DeparmentService;
import com.cdvcloud.rochecloud.service.OwnerBusinssService;
import com.cdvcloud.rochecloud.service.UserDeparmentService;
import com.cdvcloud.rochecloud.service.UserGroupService;
import com.cdvcloud.rochecloud.service.UserOwnerBusinessService;
import com.cdvcloud.rochecloud.service.UserService;
import com.cdvcloud.rochecloud.service.UserTempService;
import com.cdvcloud.rochecloud.service.YunKeyService;
import com.cdvcloud.rochecloud.util.MD5Util;
import com.cdvcloud.rochecloud.util.StringUtil;
import com.cdvcloud.rochecloud.util.UserUtil;
import com.cdvcloud.rochecloud.vo.ResObject;

import dom.UserDom4j;

/**
 * 用户管理控制器
 * 
 * @author lyh
 * 
 */
@Controller
@RequestMapping(value = "users/")
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	UserDeparmentService userDeparmentService;

	@Autowired
	UserTempService userTempService;

	@Autowired
	DeparmentService deparmentService;

	@Autowired
	YunKeyService yunKeyService;

	@Autowired
	UserGroupService userGroupService;

	@Autowired
	OwnerBusinssService ownerBusinssService;

	@Autowired
	UserOwnerBusinessService userOwnerBusinessService;

	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserDeparmentService getUserDeparmentService() {
		return userDeparmentService;
	}

	public void setUserDeparmentService(UserDeparmentService userDeparmentService) {
		this.userDeparmentService = userDeparmentService;
	}

	public UserTempService getUserTempService() {
		return userTempService;
	}

	public void setUserTempService(UserTempService userTempService) {
		this.userTempService = userTempService;
	}

	public DeparmentService getDeparmentService() {
		return deparmentService;
	}

	public void setDeparmentService(DeparmentService deparmentService) {
		this.deparmentService = deparmentService;
	}

	public YunKeyService getYunKeyService() {
		return yunKeyService;
	}

	public void setYunKeyService(YunKeyService yunKeyService) {
		this.yunKeyService = yunKeyService;
	}

	public UserGroupService getUserGroupService() {
		return userGroupService;
	}

	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	public OwnerBusinssService getOwnerBusinssService() {
		return ownerBusinssService;
	}

	public void setOwnerBusinssService(OwnerBusinssService ownerBusinssService) {
		this.ownerBusinssService = ownerBusinssService;
	}

	public UserOwnerBusinessService getUserOwnerBusinessService() {
		return userOwnerBusinessService;
	}

	public void setUserOwnerBusinessService(UserOwnerBusinessService userOwnerBusinessService) {
		this.userOwnerBusinessService = userOwnerBusinessService;
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
	public String findall(HttpServletRequest request, Pages<OpRmsUser> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;
		String strOperateFlag = "no";//是否允许用户继续创建用户
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getConditionByCAS(request, params);
			param = param.replace("ownerBusCode", "u.ownerBusCode");
			page.setCondition(param);
			Integer totalNum = userService.countFindAll(page);
			page.setCondition(param);
			page.setTotalNum(totalNum);
			page.setList(userService.selectFindAll(page));

			/**根据当前所属商标识获取所属商允许最大创建用户数量，超出则不允许继续新增用户 (by lyh 2018年3月22日20:25:31) */
			String companyName = UserUtil.getUserByRequest(request, Constants.COMPANY_NAME);
			int maxNum = 0;// 所属商允许创建的最大用户数量
			List<OpRmsOwnerBusiness> ownerBus = ownerBusinssService.selectByOwnerBusCode(companyName);
			if (null != ownerBus && 0 < ownerBus.size()) {
				OpRmsOwnerBusiness ownerBusiness = ownerBus.get(0);
				if (null != ownerBusiness) {
					maxNum = ownerBusiness.getMaxUserNum();
				}
			}
			if (page.getTotalNum() < maxNum) {
				strOperateFlag = "yes";
			}
		} catch (Exception e) {
			logger.error("查询用户信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("params", params);
		model.addAttribute("strOperateFlag", strOperateFlag);
		return "user/users";
	}

	/**
	 * @Title: loadComp
	 * @Description: TODO(加载所属商列表)
	 * @param @param request
	 * @param @param model
	 * @param @return
	 * @throws
	 * @author lyh
	 * @date 2016年11月25日 下午2:20:29
	 */
	@RequestMapping(value = "loadComp/")
	@ResponseBody
	public Map<String, Object> loadComp(HttpServletRequest request, Model model) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		// 获取所属商列表
		List<OpRmsOwnerBusiness> ownerBusiness = new ArrayList<OpRmsOwnerBusiness>();
		String strLoginId = UserUtil.getUserByRequest(request, Constants.LOGIN_ID);
		if ("administrator".equals(strLoginId)||"root".equals(strLoginId)) {
			ownerBusiness = ownerBusinssService.selectAllOwnerBus();
		}
		mapResult.put("ownerBusiness", ownerBusiness);
		return mapResult;
	}

	/**
	 * @Title: initCompany
	 * @Description: TODO(更改所属商)
	 * @param @param request
	 * @param @param companyCode
	 * @param @return
	 * @throws
	 * @author lyh
	 * @date 2016年11月25日 上午11:36:17
	 */
	@RequestMapping(value = "initCompany/")
	@ResponseBody
	public String initCompany(HttpServletRequest request, @RequestParam(value = "companyCode") String companyCode) {
		request.getSession().removeAttribute(Constants.COMPANY_NAME);
		request.getSession().setAttribute(Constants.COMPANY_NAME, companyCode);
		return Constants.SERVICE_SUCCESS;
	}

	/**
	 * 列表展示未/已分配用户信息-栏目
	 * 
	 * @param request
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findSectionUsers/")
	public String findSectionUsers(@RequestParam(value = "sectionId") String sectionId, HttpServletRequest request, Pages<OpRmsUser> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String disflag = request.getParameter("disflag");
		String sectionName = request.getParameter("sectionName");
		String param = null;
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getConditionByCAS(request, params);
			param = param.replace("ownerBusCode", "op_rms_user.ownerBusCode");
			page.setCondition(param);
			if (null == disflag || "".equals(disflag) || "0".equals(disflag)) {
				page.setTempParam(sectionId);// 条件：未分配给改栏目的用户
				Integer totalNum = userService.countFindAllDistUser(page);
				page.setTotalNum(totalNum);
				page.setList(userService.selectFindDistUser(page));
				model.addAttribute("disflag", "0");
			} else {
				page.setTempParamtwo(sectionId);// 条件：已分配给改栏目的用户
				Integer totalNum = userService.countDistAlready(page);
				page.setTotalNum(totalNum);
				page.setList(userService.selectFindDistAlready(page));
				model.addAttribute("disflag", "1");
			}

		} catch (Exception e) {
			logger.error("列表展示分配用户信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("param", param);
		model.addAttribute("sectionId", sectionId);
		model.addAttribute("sectionName", sectionName);
		return "section/sectionusers";
	}

	/**
	 * 列表展示未/已分配用户信息-栏目
	 * 
	 * @param request
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findSectionUserList/")
	public String findSectionUserList(@RequestParam(value = "sectionId") String sectionId,
			@RequestParam(value = "partId", required = false) String partId, HttpServletRequest request, Pages<OpRmsUser> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String disflag = request.getParameter("disflag");

		String param = null;
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getConditionByCAS(request, params);
			param = param.replace("ownerBusCode", "op_rms_user.ownerBusCode");
			page.setOrder("username");
			page.setCondition(param);
			if (null == partId || "".equals(partId)) {
				if (null == disflag || "".equals(disflag) || "0".equals(disflag)) {
					page.setTempParam(sectionId);// 条件：未分配给改栏目的用户
					Integer totalNum = userService.countFindAllDistUser(page);
					page.setTotalNum(totalNum);
					page.setList(userService.selectFindDistUser(page));
					model.addAttribute("disflag", "0");
				} else {
					page.setTempParamtwo(sectionId);// 条件：已分配给改栏目的用户
					Integer totalNum = userService.countDistAlready(page);
					page.setTotalNum(totalNum);
					page.setList(userService.selectFindDistAlready(page));
					model.addAttribute("disflag", "1");
				}
			} else {
				if (null == disflag || "".equals(disflag) || "0".equals(disflag)) {
					page.setTempParam(sectionId);// 条件：未分配给改栏目的用户
					page.setTempParamtwo(partId);// 部门id
					Integer totalNum = userService.countFindAllDistUser(page);
					page.setTotalNum(totalNum);
					page.setList(userService.selectFindDistUser(page));
					model.addAttribute("disflag", "0");
				} else {
					page.setTempParamtwo(sectionId);// 条件：已分配给改栏目的用户
					page.setTempParam(partId);// 部门id
					Integer totalNum = userService.countDistAlready(page);
					page.setTotalNum(totalNum);
					page.setList(userService.selectFindDistAlready(page));
					model.addAttribute("disflag", "1");
				}
				model.addAttribute("partId", partId);

			}

		} catch (Exception e) {
			logger.error("列表展示分配用户信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("param", param);
		model.addAttribute("sectionId", sectionId);
		return "section/sectionUserList";
	}

	/**
	 * 查看已分配用户信息-栏目
	 * 
	 * @param request
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findDistSelSection/")
	public String findDistSelSection(@RequestParam(value = "sectionId") String sectionId, HttpServletRequest request, Pages<OpRmsUser> page,
			Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getCondition(params);
			page.setCondition(param);
			page.setTempParamtwo(sectionId);// 条件：已分配给改栏目的用户
			Integer totalNum = userService.countDistAlready(page);
			page.setTotalNum(totalNum);
			page.setList(userService.selectFindDistAlready(page));
		} catch (Exception e) {
			logger.error("列表展示分配用户信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("param", param);
		model.addAttribute("sectionId", sectionId);
		return "section/sectionreadyusers";
	}

	/**
	 * 根据用户id删除指定用户
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteByid/")
	@ResponseBody
	public String deleteByid(@RequestParam(value = "id") String id, HttpServletRequest request) {
		OpRmsUser opRmsUser = userService.findUserById(id);
		opRmsUser.setUserState(1);
		int num = userService.updateUser(opRmsUser, request);
		if (0 < num) {
			Map<String, Object> mapDel = new HashMap<String, Object>();
			mapDel.put("userId", id);
			userGroupService.deleteGroupUser(mapDel, request);
			return Constants.SERVICE_SUCCESS;
		}
		return Constants.SERVICE_ERROR;
	}

	/**
	 * 根据用户id查询要修改的用户信息
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toupdateuser/")
	public String toupdateuser(@RequestParam(value = "id") String id, HttpServletRequest request, Model model) {
		OpRmsUser opRmsUser = userService.selectByUserId(id, request);

		List<OpRmsUserDepartment> depList = userDeparmentService.selectDepByUserId(id);
		if (null != depList && depList.size() > 0) {
			String departId = depList.get(0).getDepartmentId();
			OpRmsDeparment opRmsDeparment = deparmentService.findDeparmentById(departId,UserUtil.getUserByRequest(request, Constants.COMPANY_NAME));
			model.addAttribute("departId", departId);
			model.addAttribute("departName", opRmsDeparment.getDeparmentName());
		} else {
			model.addAttribute("departId", "");
			model.addAttribute("departName", "");
		}

		List<OpRmsOwnerBusiness> listOwnerBus = ownerBusinssService.selectAllOwnerBus();
		if (null != listOwnerBus && 0 < listOwnerBus.size()) {
			request.setAttribute("listOwnerBus", listOwnerBus);
		}
		List<Map<String, Object>> listMapUserBus = userOwnerBusinessService.selectByUserId(opRmsUser.getUserId());
		if (null != listMapUserBus && 0 < listMapUserBus.size()) {
			String strOwnerBusId = String.valueOf(listMapUserBus.get(0).get("ownerBusId"));
			model.addAttribute("userId", strOwnerBusId);
		}
		model.addAttribute("opRmsUser", opRmsUser);
		return "user/updateuser";
	}

	/**
	 * 修改指定用户信息
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateUser/")
	@ResponseBody
	public String updateUser(HttpServletRequest request, OpRmsUser opRmsUser) {
		int num = userService.updateUser(opRmsUser, request);
		String departId = request.getParameter("departId");
		String strOwnerBusId = request.getParameter("ownerBusId");
		String strUserId = opRmsUser.getUserId();// 用户Id
		logger.info("userId: " + strUserId + " departId: " + departId);
		if (0 < num) {
			userDeparmentService.deleteByUserId(strUserId);
			OpRmsUserDepartment opRmsUserDepartment = new OpRmsUserDepartment();
			opRmsUserDepartment.setUserId(strUserId);
			opRmsUserDepartment.setDepartmentId(departId);
			userDeparmentService.insertSelective(opRmsUserDepartment);

			userOwnerBusinessService.deleteByUserId(strUserId);
			/** 用户和所属商创建关联 */
			OpRmsUserOwnerBusiness opRmsUserOwnerBusiness = new OpRmsUserOwnerBusiness();
			opRmsUserOwnerBusiness.setOwnerBusId(strOwnerBusId);
			opRmsUserOwnerBusiness.setUserId(strUserId);
			userOwnerBusinessService.insertSelective(opRmsUserOwnerBusiness);

			return Constants.SERVICE_SUCCESS;
		}
		return Constants.SERVICE_ERROR;
	}

	/**
	 * 根据用户id查询要修改的用户密码
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toupdatepwd/")
	public String toupdatepwd(@RequestParam(value = "id") String id, HttpServletRequest request, Model model) {
		OpRmsUser opRmsUser = userService.selectByUserId(id, request);
		model.addAttribute("opRmsUser", opRmsUser);
		return "user/updateUserPwd";
	}

	/**
	 * 修改的用户密码
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updatepwd/")
	@ResponseBody
	public String updatepwd(@RequestParam(value = "userId") String userId, @RequestParam(value = "password") String password,
			HttpServletRequest request, Model model) {
		OpRmsUser opRmsUser = userService.findUserById(userId);
		opRmsUser.setUpdateTime(new Date());
		opRmsUser.setUpdateUserName(UserUtil.getUserName(request));
		opRmsUser.setPassword(MD5Util.getMd5ToLower(password));
		int num = userService.updateUser(opRmsUser, request);
		if (0 < num) {
			return Constants.SERVICE_SUCCESS;
		}
		return Constants.SERVICE_ERROR;
	}

	/**
	 * 跳转至用户组分配用户页面
	 * 
	 * @param sectionId
	 * @param request
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findGroupUsers/")
	public String findGroupUsers(@RequestParam(value = "groupId") String groupId, HttpServletRequest request, Pages<OpRmsUser> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String disflag = request.getParameter("disflag");
		String groupName = request.getParameter("groupName");
		String callbackUrl = request.getParameter("callbackUrl");
		String param = null;
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getConditionByCAS(request, params);
			param = param.replace("ownerBusCode", "op_rms_user.ownerBusCode");
			page.setCondition(param);
			if (null == disflag || "".equals(disflag) || "0".equals(disflag)) {
				page.setTempParam(groupId);// 条件：未分配给用户组的用户
				Integer totalNum = userService.countGroupUser(page, 0);
				page.setTotalNum(totalNum);
				page.setList(userService.selectAllGroupUser(page, 0));
				model.addAttribute("disflag", "0");
			} else {
				page.setTempParam(groupId);// 条件：已分配给用户组的用户
				Integer totalNum = userService.countGroupUser(page, 1);
				page.setTotalNum(totalNum);
				page.setList(userService.selectAllGroupUser(page, 1));
				model.addAttribute("disflag", "1");
			}

		} catch (Exception e) {
			logger.error("列表展示用户组分配用户信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("param", param);
		model.addAttribute("groupId", groupId);
		model.addAttribute("groupName", groupName);
		if(StringUtil.isEmpty(callbackUrl)){
			callbackUrl="/group/findall/";
		}
		model.addAttribute("callbackUrl", callbackUrl);
		return "group/groupusers";
	}

	/**
	 * 列表展示未/已分配用户信息-栏目
	 * 
	 * @param request
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findGroupUserList/")
	public String findGroupUserList(@RequestParam(value = "groupId") String groupId, @RequestParam(value = "partId", required = false) String partId,
			HttpServletRequest request, Pages<OpRmsUser> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String disflag = request.getParameter("disflag");// 标识(0:未分配，1：已分配)

		String param = null;
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getConditionByCAS(request, params);
			param = param.replace("ownerBusCode", "op_rms_user.ownerBusCode");
			page.setCondition(param);
			if (null == partId || "".equals(partId)) {// 部门id,为空则查询所有
				if (null == disflag || "".equals(disflag) || "0".equals(disflag)) {
					page.setTempParam(groupId);// 条件：未分配给用户组的用户
					Integer totalNum = userService.countGroupUser(page, 0);
					page.setTotalNum(totalNum);
					page.setList(userService.selectAllGroupUser(page, 0));
					model.addAttribute("disflag", "0");
				} else {
					page.setTempParam(groupId);// 条件：已分配给用户组的用户
					Integer totalNum = userService.countGroupUser(page, 1);
					page.setTotalNum(totalNum);
					page.setList(userService.selectAllGroupUser(page, 1));
					model.addAttribute("disflag", "1");
				}

			} else {// 查询指定部门下用户组信息
				if (null == disflag || "".equals(disflag) || "0".equals(disflag)) {
					page.setTempParam(groupId);// 条件：未分配给用户组的用户
					page.setTempParamtwo(partId);// 部门id
					Integer totalNum = userService.countGroupUser(page, 0);
					page.setTotalNum(totalNum);
					page.setList(userService.selectAllGroupUser(page, 0));
					model.addAttribute("disflag", "0");
				} else {
					page.setTempParam(groupId);// 条件：已分配给用户组的用户
					page.setTempParamtwo(partId);// 部门id
					Integer totalNum = userService.countGroupUser(page, 1);
					page.setTotalNum(totalNum);
					page.setList(userService.selectAllGroupUser(page, 1));
					model.addAttribute("disflag", "1");
				}
				model.addAttribute("partId", partId);

			}

		} catch (Exception e) {
			logger.error("列表展示用户组分配用户信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("param", param);
		model.addAttribute("groupId", groupId);
		return "group/groupUserList";
	}

	/**
	 * 导入xml用户到临时表
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "importUser/")
	public ResObject importUser(String filePath, HttpServletRequest request) {
		UserDom4j deptDom = new UserDom4j();
		Map<String, Object> map = deptDom.getXMLData(filePath, request);
		List<OpRmsUserTemp> listUser = (List<OpRmsUserTemp>) map.get("listUser");
		List<OpRmsUserDepartmentTemp> listDept = (List<OpRmsUserDepartmentTemp>) map.get("listDept");
		for (int i = 0; i < listDept.size(); i++) {
			if (listDept.get(i).getDepartmentId() == null || "".equals(listDept.get(i).getDepartmentId())) {
				listDept.remove(listDept.get(i));
				i--;
			}
		}
		userService.saveTempUser(listUser);
		userDeparmentService.saveTempUserDeparment(listDept);
		ResObject resObject = new ResObject();
		resObject.setStatus(200);
		return resObject;
	}

	/**
	 * 比较本地用户和导入用户
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("compareUser/")
	public String compareUser(HttpServletRequest request, Pages<OpRmsUserTemp> page, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;
		Integer totalNum = 0;
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			param = PageParams.getCondition(params);
			page.setOrder("createTime");
			totalNum = userService.countFindNewTempUser(page);
			page.setTotalNum(totalNum);
			page.setList(userService.selectNewTempUser(page));
			page.setTotalNum(totalNum);
		} catch (Exception e) {
			logger.error("查询临时用户信息异常！[" + e.getMessage() + "]");
		}
		model.addAttribute("page", page);
		model.addAttribute("params", params);
		return "user/compareUser";
	}

	/**
	 * 比较用户控制器
	 * 
	 * @param request
	 * @param page
	 * @param model
	 * @param flag
	 *            add:增加的用户,update:更新的用户,delete:删除的用户
	 * @return
	 */
	@RequestMapping("compareUserList/")
	public String compareUserList(HttpServletRequest request, Pages<OpRmsUserTemp> page, Model model, @RequestParam("flag") String flag) {
		Map<String, Object> params = new HashMap<String, Object>();
		String param = null;
		Integer totalNum = 0;
		try {
			params = ParamsUtil.getParamsMapWithTrim(request);
			if (flag.equals("add")) {
				param = PageParams.getConditionByCAS(request, params);
				String companyName = UserUtil.getUserByRequest(request, Constants.COMPANY_NAME);
				page.setTempParam(companyName);
				page.setCondition(param);
				totalNum = userService.countFindNewTempUser(page);
				page.setTotalNum(totalNum);
				page.setList(userService.selectNewTempUser(page));
			} else if (flag.equals("update")) {
				param = PageParams.getConditionByCAS(request, params);
				param = param.replace("ownerBusCode", "op_rms_user_temp.ownerBusCode");
				page.setCondition(param);
				totalNum = userService.countFindUpdateTempUser(page);
				page.setTotalNum(totalNum);
				page.setList(userService.selectUpdateTempUser(page));
			} else if (flag.equals("delete")) {
				String name = (String) params.get("op_rms_user_temp.usernameLIKE");
				params.remove("op_rms_user_temp.usernameLIKE");
				params.put("op_rms_user.usernameLIKE", name);
				param = PageParams.getConditionByCAS(request, params);
				param = param.replace("ownerBusCode", "op_rms_user.ownerBusCode");
				String companyName = UserUtil.getUserByRequest(request, Constants.COMPANY_NAME);
				page.setTempParam(companyName);
				page.setCondition(param);
				totalNum = userService.countFindDeleteTempUser(page);
				page.setTotalNum(totalNum);
				page.setList(userService.selectDeleteTempUser(page));
			}
			model.addAttribute("flag", flag);
			model.addAttribute("page", page);
			model.addAttribute("params", params);
		} catch (Exception e) {
			logger.error("查询临时用户信息异常！[" + e.getMessage() + "]");
		}
		return "user/compareUserList";
	}

	/**
	 * 从临时表导入到本地数据库
	 * 
	 * @param request
	 * @param userIds
	 * @return
	 */
	@RequestMapping(value = "tempToLocal/")
	@ResponseBody
	public Map<String, Object> addImportUser(HttpServletRequest request, @RequestParam("type") String type, @RequestParam("userIds") String userIds) {
		Pages<OpRmsUserTemp> pages = new Pages<OpRmsUserTemp>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			if (type.equals("add")) {
				
				//添加用户
				addUserToDataBase(request, userIds, resultMap);
				
			} else if (type.equals("update")) {
				
				//修改用户
				updateUserToDataBase(request, userIds, resultMap, pages);
				
			} else if (type.equals("delete")) {
				
				//删除用户
				deleteUserToDataBase(request, userIds, resultMap, pages);
			}
		} catch (Exception e) {
			logger.error("临时表导入到本地表失败![" + e.getMessage() + "]");
			resultMap.put("status", Constants.SERVICE_ERROR);
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * @Title: addUserToDataBase
	 * @Description: (添加用户)
	 * @param request
	 * @param userIds
	 * @param resultMap
	 * @author lyh
	 * @date 2018-6-6 下午4:06:04
	 */
	private void addUserToDataBase(HttpServletRequest request,String userIds,Map<String,Object> resultMap){
		// 添加用户
		int successInt = 0;// 添加数据成功数量
		int failInt = 0;// 添加数据失败数量
		String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
		String strCompanyName = UserUtil.getUserByRequest(request, Constants.COMPANY_NAME);
		List<String> delUserIdList = new ArrayList<String>();// 需要彻底删除的用户id
		List<OpRmsUserTemp> userTempList = new ArrayList<OpRmsUserTemp>();// 要添加的用户集合
		int totalNum = 0;// 总数
		if (!StringUtil.isEmpty(userIds) && userIds.indexOf(",") !=-1) {// 根据选中的用户进行添加
			String[] userIdArray = userIds.split(",");
			if (0 < userIdArray.length) {
				for (int a = 0; a < userIdArray.length; a++) {
					// 根据用户id去用户临时表查找此用户是否为新增用户
					OpRmsUserTemp opRmsUserTemp = userService.selectUserTempByUserId(userIdArray[a]);
					if (null != opRmsUserTemp) {
						opRmsUserTemp.setCreateUserId(strUserId);
						opRmsUserTemp.setOwnerBusCode(strCompanyName);
						userTempList.add(opRmsUserTemp);
						delUserIdList.add(opRmsUserTemp.getUserId());
					}
				}
				totalNum = userTempList.size();// 设置添加用户的总数量
				logger.info("添加临时数据至本地户表结果：" + successInt);
			}
		} else {// 添加所有【添加用户】列表中的所有用户
			userTempList = userService.selectAllUserTemp(strCompanyName);// 获取全部增加的用户
			totalNum = userTempList.size();// 总数
			if (0 < userTempList.size()) {
				for (OpRmsUserTemp oprmsUserTemp : userTempList) {
					delUserIdList.add(oprmsUserTemp.getUserId());
					oprmsUserTemp.setCreateUserId(strUserId);
					oprmsUserTemp.setOwnerBusCode(strCompanyName);
				}
			}
			logger.info("添加临时数据至本地户表结果：" + successInt);
		}

		if (0 < userTempList.size()) {
			int result = userService.batchCompDeleteUser(delUserIdList);// 将本地用户状态为1的彻底删除
			logger.info("删除重复数据：" + result);
			successInt = userService.saveTempNewBatchLocalUser(userTempList);// 批量添加至本地用户表
		}
		userService.operatRepeatLoginIdUser();// 处理重复logid数据
		int result = userService.saveTempOperatUserDepart();// 建立用户和部门之间的关系
		logger.info("用户部门中间表添加结果：" + result);
		failInt = totalNum - successInt;
		resultMap.put("status", Constants.SERVICE_SUCCESS);
		resultMap.put("successInt", successInt);
		resultMap.put("failInt", failInt);
	
	}
	
	/**
	 * @Title: updateUserToDataBase
	 * @Description: (更新用户信息)
	 * @param request
	 * @param userIds
	 * @param resultMap
	 * @param pages
	 * @author lyh
	 * @date 2018-6-6 下午4:09:23
	 */
	private void updateUserToDataBase(HttpServletRequest request,String userIds,Map<String,Object> resultMap,Pages<OpRmsUserTemp> pages){
		// 修改用户
		int successInt = 0;// 添加数据成功数量
		int failInt = 0;// 添加数据失败数量
		String strUserName = UserUtil.getUserName(request);
		String strCompanyName = UserUtil.getUserByRequest(request, Constants.COMPANY_NAME);
		String strUserEmail = UserUtil.getUserByRequest(request, "userEmail");
		if (!StringUtil.isEmpty(userIds) && userIds.indexOf(",") !=-1) {// 根据选中的用户进行修改
			String[] userIdArray = userIds.split(",");
			if (0 < userIdArray.length) {
				//更新选中的用户信息
				successInt = updateChoiceUsers(request, successInt, strUserName, strCompanyName, strUserEmail, userIdArray);

			}
		} else {// 修改【修改用户】列表所有用户
			// 获取全部更新的用户
			successInt = updateAllUsers(request, pages, successInt, strUserName, strCompanyName, strUserEmail);
		}

		logger.info("去重复操作！。。。。。。。。。。。。。。。。。");
		userService.operatRepeatLoginIdUser();// 处理重复logid数据
	
		
		resultMap.put("status", Constants.SERVICE_SUCCESS);
		resultMap.put("successInt", successInt);
		resultMap.put("failInt", failInt);
	}

	/**
	 * @Title: updateChoiceUsers
	 * @Description: (更新选中的用户信息)
	 * @param request
	 * @param successInt
	 * @param strUserName
	 * @param strCompanyName
	 * @param strUserEmail
	 * @param userIdArray
	 * @return
	 * @author lyh
	 * @date 2018-6-6 下午6:27:34
	 */
	private int updateChoiceUsers(HttpServletRequest request, int successInt, String strUserName, String strCompanyName, String strUserEmail,
			String[] userIdArray) {
		for (int i = 0; i < userIdArray.length; i++) {
			OpRmsUserTemp userTemp = userService.selectUpdateTempUserById(userIdArray[i]);
			if (null != userTemp) {
				OpRmsUser opRmsUser = tempToLocal(userTemp, strUserName, strCompanyName, strUserEmail);
				// 先去临时表中查看当前用户属于那个部门
				if (existDep(userTemp.getUserId(),strCompanyName)) {
					successInt += userService.update4ColumByPrimaryKey(opRmsUser, request);
					// 先清除该用户之前的关系
					int resultInt = userDeparmentService.deleteByUserId(opRmsUser.getUserId());
					logger.info("更新用户之前，删除该用户之前和部门的关系数为[" + resultInt + "]");
					if (resultInt > 0) {
						// 查询出该用户新的所属部门
						List<OpRmsUserDepartmentTemp> opRmsUserDepartmentTemps = userDeparmentService.selectByUserId(opRmsUser
								.getUserId());
						for (OpRmsUserDepartmentTemp opRmsUserDepartmentTemp : opRmsUserDepartmentTemps) {
							OpRmsUserDepartment opRmsUserDepartment = new OpRmsUserDepartment();
							opRmsUserDepartment.setUserId(opRmsUserDepartmentTemp.getUserId());
							opRmsUserDepartment.setDepartmentId(opRmsUserDepartmentTemp.getDepartmentId());
							// 有关系，不建立
							if (userDeparmentService.selectOpRmsUserDepartment(opRmsUserDepartment) == null) {
								userDeparmentService.saveUserDepartment(opRmsUserDepartment);
							}
						}
					}

				} else {
					opRmsUser.setImportState(1);
					opRmsUser.setImportDesc("该用户无所属部门!");
					successInt += userService.update4ColumByPrimaryKey(opRmsUser, request);
				}

			}
		}
		return successInt;
	}

	/**
	 * @Title: updateAllUsers
	 * @Description: (修改【修改用户】列表所有用户)
	 * @param request
	 * @param pages
	 * @param successInt
	 * @param strUserName
	 * @param strCompanyName
	 * @param strUserEmail
	 * @return
	 * @author lyh
	 * @date 2018-6-6 下午6:26:59
	 */
	private int updateAllUsers(HttpServletRequest request, Pages<OpRmsUserTemp> pages, int successInt, String strUserName, String strCompanyName,
			String strUserEmail) {
		StringBuffer sqlcount = new StringBuffer();
		String companyName = UserUtil.getUserByRequest(request, Constants.COMPANY_NAME);
		sqlcount.append(" and op_rms_user_temp.ownerBusCode = '").append(companyName).append("'");
		pages.setCondition(sqlcount.toString());
		Integer totalNum = userService.countFindUpdateTempUser(pages);
		pages.setNumPerPage(totalNum);
		pages.setTotalNum(totalNum);
		List<OpRmsUserTemp> userTemps = userService.selectUpdateTempUser(pages);
		for (OpRmsUserTemp opRmsUserTemp : userTemps) {
			OpRmsUser opRmsUser = tempToLocal(opRmsUserTemp, strUserName, strCompanyName, strUserEmail);
			// 先去临时表中查看当前用户属于那个部门
			if (existDep(opRmsUserTemp.getUserId(),strCompanyName)) {
				successInt += userService.update4ColumByPrimaryKey(opRmsUser, request);
				// 先清除该用户之前的关系
				int resultInt = userDeparmentService.deleteByUserId(opRmsUser.getUserId());
				logger.info("更新用户之前，删除该用户之前和部门的关系数为[" + resultInt + "]");
				if (resultInt > 0) {
					// 查询出该用户新的所属部门
					List<OpRmsUserDepartmentTemp> opRmsUserDepartmentTemps = userDeparmentService.selectByUserId(opRmsUser.getUserId());
					for (OpRmsUserDepartmentTemp opRmsUserDepartmentTemp : opRmsUserDepartmentTemps) {
						OpRmsUserDepartment opRmsUserDepartment = new OpRmsUserDepartment();
						opRmsUserDepartment.setUserId(opRmsUserDepartmentTemp.getUserId());
						opRmsUserDepartment.setDepartmentId(opRmsUserDepartmentTemp.getDepartmentId());
						// 有关系，不建立
						if (userDeparmentService.selectOpRmsUserDepartment(opRmsUserDepartment) == null) {
							userDeparmentService.saveUserDepartment(opRmsUserDepartment);
						}
					}
				}
			} else {
				opRmsUser.setImportState(1);
				opRmsUser.setImportDesc("该用户无所属部门!");
				successInt += userService.update4ColumByPrimaryKey(opRmsUser, request);
			}
		}
		return successInt;
	}
	
	/**
	 * @Title: deleteUserToDataBase
	 * @Description: (删除用户)
	 * @param request
	 * @param userIds
	 * @param resultMap
	 * @param pages
	 * @author lyh
	 * @date 2018-6-6 下午4:11:04
	 */
	private void deleteUserToDataBase(HttpServletRequest request,String userIds,Map<String,Object> resultMap,Pages<OpRmsUserTemp> pages){
		// 删除用户
		int successInt = 0;// 添加数据成功数量
		int failInt = 0;// 添加数据失败数量
		List<String> userIdList = new ArrayList<String>();// 封装要删除的用户id
		if (!StringUtil.isEmpty(userIds) && userIds.indexOf(",") !=-1) {// 根据选中的用户进行删除
			String[] userIdArray = userIds.split(",");
			if (0 < userIdArray.length) {
				for (int d = 0; d < userIdArray.length; d++) {
					userIdList.add(userIdArray[d]);
				}
				if (0 < userIdList.size()) {
					successInt = userService.batchDeleteUser(userIdList);
				}
				failInt = userIdArray.length - successInt;
			}
		} else {// 删除【删除用户】列表中所有用户
			// 获取全部删除的用户
			StringBuffer sqlcount = new StringBuffer();
			String companyName = UserUtil.getUserByRequest(request, Constants.COMPANY_NAME);
			sqlcount.append(" and op_rms_user.ownerBusCode = '").append(companyName).append("'");
			pages.setTempParam(companyName);
			pages.setCondition(sqlcount.toString());
			Integer totalNum = userService.countFindDeleteTempUser(pages);
			pages.setNumPerPage(totalNum);
			pages.setTotalNum(totalNum);
			List<OpRmsUserTemp> userTemps = userService.selectDeleteTempUser(pages);
			for (OpRmsUserTemp opRmsUserTemp : userTemps) {
				userIdList.add(opRmsUserTemp.getUserId());
			}
			if (0 < userIdList.size()) {
				successInt = userService.batchDeleteUser(userIdList);
			}
			failInt = totalNum - successInt;

		}

		resultMap.put("status", Constants.SERVICE_SUCCESS);
		resultMap.put("successInt", successInt);
		resultMap.put("failInt", failInt);
	}
	
	
	/**
	 * 查询要绑定的云key并跳转至绑定页面
	 * 
	 * @param userId
	 *            用户id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toBindYunKey/")
	public String toBindYunKey(@RequestParam("userId") String userId, Model model) {
		List<OpRmsYunkey> yunkeyList = yunKeyService.selectAllYunKey();
		model.addAttribute("yunkeylist", yunkeyList);
		model.addAttribute("userId", userId);
		return "user/bindyunkey";
	}

	/**
	 * 给指定用户绑定云key
	 * 
	 * @param yunkeyId
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "bindYunKey/")
	@ResponseBody
	public String bindYunKey(@RequestParam("yunkeyId") String yunkeyId, @RequestParam("userId") String userId, HttpServletRequest request) {
		if (!StringUtil.isEmpty(userId) && !StringUtil.isEmpty(yunkeyId)) {
			OpRmsUser opRmsUser = userService.findUserById(userId);// 根据用户id查询用户信息
			if (null != opRmsUser) {
				OpRmsYunkey opRmsYunkey = yunKeyService.selectByPrimaryKey(yunkeyId);// 根据云keyid查询云key信息

				String userYunKeyId = opRmsUser.getYunkeyId();// 获取用户当前云key
				if (!StringUtil.isEmpty(userYunKeyId)) {// 此用户已绑定一个云key,需先解绑，然后再绑定新云key

					OpRmsYunkey userYunkey = yunKeyService.selectByPrimaryKey(userYunKeyId);
					userYunkey.setYunkeyState(Constants.SZERO);// 修改云key状态为可用
					yunKeyService.updateByPrimaryKeySelective(userYunkey);

				}

				opRmsYunkey.setYunkeyState(Constants.ONE);// 修改云key状态为不可用
				opRmsUser.setYunkeyId(yunkeyId);
				userService.updateUser(opRmsUser, request);// 将云key与用户绑定
				yunKeyService.updateByPrimaryKeySelective(opRmsYunkey);// 修改云key状态为已绑定

				return Constants.SERVICE_SUCCESS;

			}

		}
		return Constants.SERVICE_ERROR;
	}

	/**
	 * 将临时用户更改为本地用户
	 * 
	 * @param opRmsUserTemp
	 * @return
	 */
	private OpRmsUser tempToLocal(OpRmsUserTemp opRmsUserTemp, String strUserName,String strCompanyName,String strUserEmail) {
		OpRmsUser opRmsUser = new OpRmsUser();
		opRmsUser.setUserId(opRmsUserTemp.getUserId());
		opRmsUser.setUserName(opRmsUserTemp.getUserName());
		opRmsUser.setLoginId(opRmsUserTemp.getLoginId());
		opRmsUser.setCreateTime(new Date());
		opRmsUser.setUserState(opRmsUserTemp.getUserState());
		opRmsUser.setImportType(opRmsUserTemp.getImportType());
		opRmsUser.setPassword(opRmsUserTemp.getPassword());
		opRmsUser.setCreateUserName(strUserName);
		opRmsUser.setUserEmail(strUserEmail);
		opRmsUser.setOwnerBusCode(strCompanyName);
		return opRmsUser;
	}

	/**
	 * 判断部门是否存在
	 * 
	 * @param userId
	 * @return
	 */
	private boolean existDep(String userId,String strCompanyName) {
		// 从临时用户部门关系表中获取,该用户的关系
		List<OpRmsUserDepartmentTemp> departmentTemps = userDeparmentService.selectByUserId(userId);
		if (departmentTemps.size() != 0) {
			// 本地部门表中获取该部门
			for (OpRmsUserDepartmentTemp opRmsUserDepartmentTemp : departmentTemps) {
				OpRmsDeparment localDep = deparmentService.findDeparmentById(opRmsUserDepartmentTemp.getDepartmentId(),strCompanyName);
				if (localDep == null) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 跳转至添加用户页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "toAddUser/")
	public String toAddUser(HttpServletRequest request) {
		List<OpRmsOwnerBusiness> listOwnerBus = new ArrayList<OpRmsOwnerBusiness>();
		// String loginId = UserUtil.getUserByRequest(request, "loginId");
		// if (!"root".equals(loginId)) {
		String companyName = UserUtil.getUserByRequest(request, Constants.COMPANY_NAME);
		listOwnerBus = ownerBusinssService.selectByOwnerBusCode(companyName);
		// } else {
		// listOwnerBus = ownerBusinssService.selectAllOwnerBus();
		// }
		if (null != listOwnerBus && 0 < listOwnerBus.size()) {
			request.setAttribute("listOwnerBus", listOwnerBus);
		}
		return "user/adduser";
	}

	/**
	 * 添加栏目
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addUser/")
	@ResponseBody
	public String addUser(OpRmsUser opRmsUser, HttpServletRequest request) {
		String departId = request.getParameter("departId");
		String ownerBusId = request.getParameter("ownerBusId");
		if (StringUtil.isEmpty(departId)) {
			departId = "P00000115";
		}
		String strUserId = StringUtil.randomUUID();
		opRmsUser.setUserId(strUserId);
		opRmsUser.setCreateTime(new Date());
		opRmsUser.setImportType(1);
		opRmsUser.setImportState(1);
		opRmsUser.setCreateUserId(UserUtil.getUserByRequest(request, "userId"));
		opRmsUser.setCreateUserName(UserUtil.getUserName(request));
		opRmsUser.setOwnerBusCode(UserUtil.getUserByRequest(request, Constants.COMPANY_NAME));
		logger.info("添加用户参数：createUserId: " + opRmsUser.getCreateUserId() + " createUserName: " + opRmsUser.getCreateUserName() + " loginId: "
				+ opRmsUser.getLoginId() + " 所属部门id: " + departId);

		int num = userService.insertSelective(opRmsUser, request);
		if (num > 0) {
			OpRmsUserDepartment opRmsUserDepartment = new OpRmsUserDepartment();
			opRmsUserDepartment.setUserId(opRmsUser.getUserId());
			opRmsUserDepartment.setDepartmentId(departId);
			int result = userDeparmentService.insertSelective(opRmsUserDepartment);
			/** 用户和所属商创建关联 */
			OpRmsUserOwnerBusiness opRmsUserOwnerBusiness = new OpRmsUserOwnerBusiness();
			opRmsUserOwnerBusiness.setOwnerBusId(ownerBusId);
			opRmsUserOwnerBusiness.setUserId(strUserId);
			userOwnerBusinessService.insertSelective(opRmsUserOwnerBusiness);
			if (result > 0) {
				return Constants.SERVICE_SUCCESS;
			}

		}
		return Constants.SERVICE_ERROR;
	}

	/**
	 * 校验是否存在loginId相同的用户
	 * 
	 * @param request
	 * @param loginId
	 * @return
	 */
	@RequestMapping("checkLoginId/")
	@ResponseBody
	public String checkLoginId(HttpServletRequest request, @RequestParam(value = "param") String loginId) {
		String operate = request.getParameter("operate");// 操作类型
		List<OpRmsUser> userLists = userService.selectByRepeatLoginId(loginId);
		if ("add".equals(operate)) {// add:增加
			try {
				if (userLists == null || 0 == userLists.size()) {
					return Constants.VALIDATE_YES;
				} else {
					return "该登录名已经存在！";
				}
			} catch (Exception e) {
				logger.error("根据loginId查询用户信息异常！异常信息：[" + e.getMessage() + "]");
				return Constants.VALIDATE_YES;
			}
		} else {// update:修改
			String userId = request.getParameter("userId");// 用户id
			if (null == userLists || userLists.size() == 0 || userLists.get(0).getUserId().equals(userId)) {
				return Constants.VALIDATE_YES;
			} else {
				return "该登录名已经存在！";
			}
		}
	}

	/**
	 * @Title: checkUserPhone
	 * @Description: (校验是否存在userPhone相同的用户)
	 * @param request
	 * @param userPhone
	 * @return
	 * @author lyh
	 * @date 2018-3-28 下午1:38:27
	 */
	@RequestMapping("checkUserPhone/")
	@ResponseBody
	public String checkUserPhone(HttpServletRequest request, @RequestParam(value = "param") String userPhone) {
		String operate = request.getParameter("operate");// 操作类型
		List<OpRmsUser> userLists = userService.selectByRepeatUserPhone(userPhone);
		if ("add".equals(operate)) {// add:增加
			try {
				if (userLists == null || 0 == userLists.size()) {
					return Constants.VALIDATE_YES;
				} else {
					return "该手机号已经存在！";
				}
			} catch (Exception e) {
				logger.error("根据userPhone查询用户信息异常！异常信息：[" + e.getMessage() + "]");
				return Constants.VALIDATE_YES;
			}
		} else {// update:修改
			String userId = request.getParameter("userId");// 用户id
			if (null == userLists || userLists.size() == 0 || userLists.get(0).getUserId().equals(userId)) {
				return Constants.VALIDATE_YES;
			} else {
				return "该手机号已经存在！";
			}
		}
	}

	/**
	 * @Title: toDistUserGroup
	 * @Description: TODO(跳转至分配用户组页面)
	 * @param @param userId
	 * @param @return
	 * @throws
	 * @author lyh
	 * @date 2015年11月23日 下午1:57:22
	 */
	@RequestMapping("toDistUserGroup/")
	public String toDistUserGroup(HttpServletRequest request, @RequestParam(value = "userId") String userId, Model model) {
		// 查询用户所属用户组和非所属用户组
		List<Map<String, Object>> userGroupLists = userGroupService.selectDistUserGroup(userId);
		List<Map<String, Object>> distUserGroupLists = userGroupService.selectNotDistUserGroup(request, userId);
		model.addAttribute("ugList", userGroupLists);
		model.addAttribute("distugList", distUserGroupLists);
		model.addAttribute("userId", userId);
		return "user/distUser";
	}

	/**
	 * @Title: bathAddGroupToUser
	 * @Description: TODO(批量分配用户组给指定用户)
	 * @param @param groupIds 用户组
	 * @param @param userId 用户
	 * @param @param request
	 * @param @return
	 * @throws
	 * @author lyh
	 * @date 2015年11月23日 下午5:28:14
	 */
	@RequestMapping(value = "bathAddGroupToUser/")
	@ResponseBody
	public String bathAddGroupToUser(@RequestParam(value = "groupIds") String groupIds, @RequestParam(value = "userId") String userId,
			HttpServletRequest request) {
		logger.info("用户组groupIds: " + groupIds + " 用户userId: " + userId);
		String[] groupIdArray = groupIds.split(",");
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("userId", userId);
		List<OpRmsUserGroup> groupLists = new ArrayList<OpRmsUserGroup>();// 用户组与用户集合
		for (int i = 0; i < groupIdArray.length; i++) {
			String groupId = groupIdArray[i];
			whereMap.put("groupId", groupId);
			int existFlag = userGroupService.selectGrouptUser(whereMap, request);// 查询用户组与用户是否已存在关系
			if (existFlag <= 0) {// 将未建立关系的用户组与用户封装
				OpRmsUserGroup opRmsUserGroup = new OpRmsUserGroup();
				opRmsUserGroup.setGroupId(groupId);
				opRmsUserGroup.setUserId(userId);
				groupLists.add(opRmsUserGroup);
			}

		}
		if (groupLists.size() > 0) {// 批量建立用户组与用户的关系
			int result = userGroupService.batchInsertSelective(groupLists, request);
			if (0 >= result) {
				return Constants.SERVICE_ERROR;
			}
		}
		return Constants.SERVICE_SUCCESS;
	}

	/**
	 * @Title: cancelAddGroupToUser
	 * @Description: TODO(批量取消用户已分配的用户组)
	 * @param @param groupIds
	 * @param @param userId
	 * @param @param request
	 * @param @return
	 * @throws
	 * @author lyh
	 * @date 2015年11月23日 下午5:57:36
	 */
	@RequestMapping(value = "cancelAddGroupToUser/")
	@ResponseBody
	public String cancelAddGroupToUser(@RequestParam(value = "groupIds") String groupIds, @RequestParam(value = "userId") String userId,
			HttpServletRequest request) {
		logger.info("用户组groupIds: " + groupIds + " 用户userId: " + userId);
		String[] groupIdArray = groupIds.split(",");
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("userId", userId);
		for (int i = 0; i < groupIdArray.length; i++) {
			String groupId = groupIdArray[i];
			whereMap.put("groupId", groupId);
			int existFlag = userGroupService.selectGrouptUser(whereMap, request);// 查询用户组与用户是否已存在关系
			if (existFlag > 0) {// 取消用户组与用户的关联关系
				userGroupService.deleteGroupUser(whereMap, request);
			}

		}
		return Constants.SERVICE_SUCCESS;
	}

}
