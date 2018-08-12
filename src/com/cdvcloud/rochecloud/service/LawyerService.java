package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.common.Pages;
import com.cdvcloud.rochecloud.domain.BtvLawyer;
import com.cdvcloud.rochecloud.mapper.BtvLawyerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户管理业务处理类
 *
 * @author lyh
 */
@Service
public class LawyerService {

	@Autowired
	private BtvLawyerMapper btvLawyerMapper;

	/**
	 * 查询所有律师数量支持分页和条件查询
	 *
	 * @param page
	 * @return
	 */
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
	public List<BtvLawyer> selectFindAll(Pages<BtvLawyer> page) {
//		return btvLawyerMapper.selectFindAll(page);
		return null;
	}

	/**
	 * 根据用户ID获取律师信息
	 * @param userId
	 * @return
	 */
	public BtvLawyer findLawyerByUserId(String userId){
		return btvLawyerMapper.findLawyerByUserId(userId);
	}


}
