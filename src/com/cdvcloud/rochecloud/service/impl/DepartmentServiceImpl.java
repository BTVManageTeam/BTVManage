package com.cdvcloud.rochecloud.service.impl;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvDepartment;
import com.cdvcloud.rochecloud.mapper.BtvDepartmentMapper;
import com.cdvcloud.rochecloud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:lyh
 * @Description:
 * @Date:Created in 2018/8/12 12:07
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private BtvDepartmentMapper btvDepartmentMapper;


	/**
	 * 根据Id查询当前部门
	 *
	 * @param id 部门Id
	 * @return
	 */
	@Override
	public BtvDepartment findDeparmentById(String id) {
		return btvDepartmentMapper.selectByPrimaryKey(id);
	}


	/**
	 * 新增部门
	 *
	 * @param record
	 * @return
	 */
	@Override
	public long addDepartment(BtvDepartment record) {
		return btvDepartmentMapper.insertSelective(record);
	}

	/**
	 * 更新部门
	 *
	 * @param record
	 * @return
	 */
	@Override
	public long updateDepartment(BtvDepartment record) {
		return btvDepartmentMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 删除部门
	 *
	 * @param id
	 * @return
	 */
	@Override
	public long removeDepartment(String id) {
		return btvDepartmentMapper.deleteByPrimaryKey(id);
	}


	/**
	 * 根据用户ID查询部门信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public BtvDepartment findDeparmentByUserId(String userId) {
		return btvDepartmentMapper.findDeparmentByUserId(userId);
	}

	@Override
	public int countFindAllDepartment(Pages<Map<String, Object>> page) {
		return btvDepartmentMapper.countFindAllDepartment(page);
	}

	@Override
	public List<Map<String, Object>> selectFindAllDepartment(Pages<Map<String, Object>> page) {
		return btvDepartmentMapper.selectFindAllDepartment(page);
	}

}
