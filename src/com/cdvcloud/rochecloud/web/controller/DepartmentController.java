package com.cdvcloud.rochecloud.web.controller;

import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.domain.BtvDepartment;
import com.cdvcloud.rochecloud.exception.MyDefineException;
import com.cdvcloud.rochecloud.service.DeparmentService;
import com.cdvcloud.rochecloud.util.DateUtil;
import com.cdvcloud.rochecloud.util.StringUtil;
import com.cdvcloud.rochecloud.util.UserUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 部门管理控制器
 * 
 * @author lyh
 * 
 */
@Controller
@RequestMapping(value = "department/")
public class DepartmentController {

	private static final Logger logger = Logger.getLogger(DepartmentController.class);

	@Autowired
	DeparmentService deparmentService;

	
	/**
	 * 跳转至部门页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "department/")
	public String toDepartment() {
		return "department/departmentList";
	}


	/**
	 * 新增部门
	 * 
	 * @param id
	 * @param pId
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "addDepartment/", produces = { "text/json;charset=UTF-8" })
	@ResponseBody
	public String addDepartment(HttpServletRequest request, @RequestParam(value = "id") String id, @RequestParam(value = "pId") String pId,
                                @RequestParam(value = "name") String name) {
		String strResult = "fail";
		try {
			logger.info("新增部门名称："+name+"parentId:"+pId+" deparmentId: "+id);
			String strUserId = UserUtil.getUserByRequest(request, Constants.CURRENT_USER_ID);
			BtvDepartment record = new BtvDepartment();
			record.setDepartmentId(pId);
			record.setDepartmentName(name);
			record.setCreateUserId(strUserId);
			record.setCreateTime(DateUtil.getCurrentDateTime());
			long num = deparmentService.addDepartment(record);
			if(num> 0){
				strResult = "success";
			}
		} catch (Exception e) {
			logger.error("新增部门信息异常！异常信息：" + e.getMessage());
		}
		return strResult;
	}


	/**
	 * 更新部门
	 * 
	 * @param id
	 * @param name
	 * @return
	 * @throws MyDefineException
	 */
	@RequestMapping(value = "updateDepartment/", produces = { "text/json;charset=UTF-8" })
	@ResponseBody
	public String updateDepartment(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name) throws MyDefineException {
		String strResult = "fail";
		try {
			BtvDepartment btvDepartment = new BtvDepartment();
			btvDepartment.setDepartmentId(id);
			btvDepartment.setDepartmentName(name);
			long num = deparmentService.updateDepartment(btvDepartment);
			if(num> 0){
				strResult = "success";
			}
		} catch (Exception e) {
			logger.error("更新部门信息异常！异常信息：" + e.getMessage());
			throw new MyDefineException(StringUtil.format("更新部门信息异常！用户ID {} 名称 {}", id,name));
		}
		return strResult;
	}

	/**
	 * 删除部门
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "removeDepartment/", produces = { "text/json;charset=UTF-8" })
	@ResponseBody
	public String removeDepartment(HttpServletRequest request, @RequestParam(value = "id") String id) {
		String strResult = "fail";
		try {
			long num = deparmentService.removeDepartment(id);
			if(num> 0){
				strResult = "success";
			}
		} catch (Exception e) {
			logger.error("删除部门信息异常！异常信息：" + e.getMessage());
		}
		return strResult;
	}


}
