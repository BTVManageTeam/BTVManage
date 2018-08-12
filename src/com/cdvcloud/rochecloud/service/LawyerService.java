package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvLawyer;

import java.util.List;

/**
 * 用户管理业务处理类
 *
 * @author lyh
 */

public interface LawyerService {

	/**
	 * 查询所有律师数量支持分页和条件查询
	 *
	 * @param page
	 * @return
	 */
	int countFindAll(Pages<BtvLawyer> page);

	/**
	 * 查询所有用户信息支持分页和条件查询
	 *
	 * @param page
	 * @return
	 */
	List<BtvLawyer> selectFindAll(Pages<BtvLawyer> page);

	/**
	 * 根据用户ID获取律师信息
	 *
	 * @param userId
	 * @return
	 */
	BtvLawyer findLawyerByUserId(String userId);

	/**
	 * 根据律师id查询律师详情
	 * @param lawyerId
	 * @return
	 */
	BtvLawyer queryLawyerById(String lawyerId);

}
