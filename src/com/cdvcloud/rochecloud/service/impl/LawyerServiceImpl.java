package com.cdvcloud.rochecloud.service.impl;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvLawyer;
import com.cdvcloud.rochecloud.mapper.BtvLawyerMapper;
import com.cdvcloud.rochecloud.service.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:lyh
 * @Description:
 * @Date:Created in 2018/8/12 12:12
 */
@Service
public class LawyerServiceImpl implements LawyerService {

	@Autowired
	private BtvLawyerMapper btvLawyerMapper;

	/**
	 * 查询所有律师数量支持分页和条件查询
	 *
	 * @param page
	 * @return
	 */
	@Override
	public int countFindAll(Pages<BtvLawyer> page) {
//		return btvLawyerMapper.countFindAll(page);
		return 0;
	}

	/**
	 * 查询所有用户信息支持分页和条件查询
	 *
	 * @param page
	 * @return
	 */
	@Override
	public List<BtvLawyer> selectFindAll(Pages<BtvLawyer> page) {
//		return btvLawyerMapper.selectFindAll(page);
		return null;
	}

	/**
	 * 根据用户ID获取律师信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public BtvLawyer findLawyerByUserId(String userId) {
		return btvLawyerMapper.findLawyerByUserId(userId);
	}

	/**
	 * 根据律师id查询律师详情
	 * @param lawyerId
	 * @return
	 */
	@Override
	public BtvLawyer queryLawyerById(String lawyerId) {
		return btvLawyerMapper.selectByPrimaryKey(lawyerId);
	}

}
