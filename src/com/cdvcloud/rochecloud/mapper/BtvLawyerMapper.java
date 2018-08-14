package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvLawyer;

import java.util.List;
import java.util.Map;

public interface BtvLawyerMapper {
	int deleteByPrimaryKey(String lawyerId);

	int insert(BtvLawyer record);

	int insertSelective(BtvLawyer record);

	BtvLawyer selectByPrimaryKey(String lawyerId);

	int updateByPrimaryKeySelective(BtvLawyer record);

	int updateByPrimaryKey(BtvLawyer record);


	/**
	 * 根据用户ID查询律师信息
	 *
	 * @param userId
	 * @return
	 */
	BtvLawyer findLawyerByUserId(String userId);


	/**
	 * 分页总数
	 *
	 * @param page
	 * @return
	 */
	int countFindAll(Pages<Map<String, Object>> page);

	/**
	 * 分页数据
	 *
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> selectFindAll(Pages<Map<String, Object>> page);


	/**
	 * 根据律所ID查询律师
	 * @param departmentId
	 * @return
	 */
	int selectByDeptId(String departmentId);

	/**
	 * 根据律师id查询律师详情以及律所名称
	 * @param lawyerId
	 * @return
	 */
	BtvLawyer queryLawyerAndDepartmentById(String lawyerId);

}