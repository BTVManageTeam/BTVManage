package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.domain.BtvDepartment;
import com.cdvcloud.rochecloud.mapper.BtvDepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeparmentService {

	@Autowired
	private BtvDepartmentMapper btvDepartmentMapper;

	/**
	 * 根据Id查询当前部门
	 *
	 * @param id 部门Id
	 * @return
	 */
	public BtvDepartment findDeparmentById(String id) {
		return btvDepartmentMapper.selectByPrimaryKey(id);
	}


	/**
	 * 新增部门
	 *
	 * @param record
	 * @return
	 */
	public long addDepartment(BtvDepartment record) {
		return btvDepartmentMapper.insertSelective(record);
	}

	/**
	 * 更新部门
	 *
	 * @param record
	 * @return
	 */
	public long updateDepartment(BtvDepartment record) {
		return btvDepartmentMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 删除部门
	 *
	 * @param id
	 * @return
	 */
	public long removeDepartment(String id) {
		return btvDepartmentMapper.deleteByPrimaryKey(id);
	}


	/**
	 * 根据用户ID查询部门信息
	 * @param userId
	 * @return
	 */
	public BtvDepartment findDeparmentByUserId(String userId) {
		return btvDepartmentMapper.findDeparmentByUserId(userId);
	}
}
