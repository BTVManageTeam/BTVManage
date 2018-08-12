package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvLawyer;

import java.util.List;
import java.util.Map;

/**
 * 律师业务处理类
 *
 * @author lyh
 */

public interface LawyerService {

	/**
	 * 律师分页总数
	 *
	 * @param page
	 * @return
	 */
	int countFindAll(Pages<Map<String, Object>> page);

	/**
	 * 分页查询律师
	 *
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> selectFindAll(Pages<Map<String, Object>> page);

	/**
	 * 根据用户ID获取律师信息
	 *
	 * @param userId
	 * @return
	 */
	BtvLawyer findLawyerByUserId(String userId);

	/**
	 * 新增律师
	 * @param record
	 * @return
	 */
	int insertSelective(BtvLawyer record);

	/***
	 * 根据ID查询
	 * @param lawyerId
	 * @return
	 */
	BtvLawyer selectByPrimaryKey(String lawyerId);

	/**
	 * 根据ID更新律师
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(BtvLawyer record);

	/**
	 * 律师删除
	 * @param lawyerId
	 * @return
	 */
	int deleteByPrimaryKey(String lawyerId);


	/**
	 * 根据律所ID查询律师
	 * @param departmentId
	 * @return
	 */
	int selectByDeptId(String departmentId);

}
