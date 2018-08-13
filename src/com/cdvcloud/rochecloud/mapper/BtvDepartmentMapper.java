package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvDepartment;

import java.util.List;
import java.util.Map;

public interface BtvDepartmentMapper {
	int deleteByPrimaryKey(String departmentId);

	int insert(BtvDepartment record);

	int insertSelective(BtvDepartment record);

	BtvDepartment selectByPrimaryKey(String departmentId);

	BtvDepartment findDeparmentByUserId(String userId);

	int updateByPrimaryKeySelective(BtvDepartment record);

	int updateByPrimaryKey(BtvDepartment record);

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
	 * @param mapUser
	 * @return
	 */
	List<BtvDepartment> findAllDepartment(Map<String,Object> mapUser);
}