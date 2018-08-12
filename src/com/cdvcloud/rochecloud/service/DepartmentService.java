package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvDepartment;

import java.util.List;
import java.util.Map;


public interface DepartmentService {


	/**
	 * 根据Id查询当前部门
	 *
	 * @param id 部门Id
	 * @return
	 */
	BtvDepartment findDeparmentById(String id);


	/**
	 * 新增部门
	 *
	 * @param record
	 * @return
	 */
	long addDepartment(BtvDepartment record);

	/**
	 * 更新部门
	 *
	 * @param record
	 * @return
	 */
	long updateDepartment(BtvDepartment record);

	/**
	 * 删除部门
	 *
	 * @param id
	 * @return
	 */
	long removeDepartment(String id);


	/**
	 * 根据用户ID查询部门信息
	 *
	 * @param userId
	 * @return
	 */
	BtvDepartment findDeparmentByUserId(String userId);

	/**
	 * 分页总数
	 *
	 * @param page
	 * @return
	 */
	int countFindAllDepartment(Pages<Map<String, Object>> page);

	/**
	 * 分页数据
	 *
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> selectFindAllDepartment(Pages<Map<String, Object>> page);

	/**
	 * 根据userID查询部门，不传参则查询所有
	 * @param userId
	 * @return
	 */
	List<BtvDepartment> findAllDepartment(String userId);

}
