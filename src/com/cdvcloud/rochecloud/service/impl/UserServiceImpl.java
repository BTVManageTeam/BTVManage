package com.cdvcloud.rochecloud.service.impl;

import com.cdvcloud.rochecloud.domain.BtvUser;
import com.cdvcloud.rochecloud.mapper.BtvUserMapper;
import com.cdvcloud.rochecloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:lyh
 * @Description:
 * @Date:Created in 2018/8/12 12:33
 */
@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private BtvUserMapper btvUserMapper;

	@Override
	public BtvUser login(BtvUser user) {
		return btvUserMapper.selectByloginIdAndPas(user);
	}

	@Override
	public List<BtvUser> selectByRepeatAccountName(String accountName) {
		return btvUserMapper.selectByRepeatAccountName(accountName);
	}

	@Override
	public int insertSelective(BtvUser record) {
		return btvUserMapper.insertSelective(record);
	}

}
