package com.cdvcloud.rochecloud.mapper;

import com.cdvcloud.rochecloud.common.BaseMapper;
import com.cdvcloud.rochecloud.domain.BtvUser;

import java.util.List;
import java.util.Map;

public interface BtvUserMapper extends BaseMapper<BtvUser, String> {
	@Override
	int deleteByPrimaryKey(String userId);

	@Override
	int insert(BtvUser record);

	int insertSelective(BtvUser record);

	/**
	 * 根据ID查询
	 * @param userId
	 * @return
	 */
	@Override
	BtvUser selectByPrimaryKey(String userId);

	@Override
	int updateByPrimaryKeySelective(BtvUser record);

	@Override
	int updateByPrimaryKey(BtvUser record);

	/***
	 * 登录
	 *
	 * @param user
	 * @return
	 */
	BtvUser selectByloginIdAndPas(BtvUser user);

	/**
	 * 校验登录名唯一
	 *
	 * @param accountName
	 * @return
	 */
	List<BtvUser> selectByRepeatAccountName(String accountName);

}