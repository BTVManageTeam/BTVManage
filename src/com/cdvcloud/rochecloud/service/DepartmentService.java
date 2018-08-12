package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.domain.BtvDepartment;


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
}
