package com.cdvcloud.rochecloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rochecloud.domain.wechar.AccessTokenM;
import com.cdvcloud.rochecloud.mapper.AccessTokenMapper;
import com.cdvcloud.rochecloud.service.AccessTokenService;
@Service
public class AccessTokenServiceImpl implements AccessTokenService {
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;

	@Override
	public int addAccessTokenM(AccessTokenM params) {
		return accessTokenMapper.addAccessTokenM(params);
	}

	@Override
	public int updateAccessTokenM(AccessTokenM setParams) {
		return accessTokenMapper.updateAccessTokenM(setParams);
	}

	@Override
	public AccessTokenM findAccessTokenM(AccessTokenM whereParams) {
		return accessTokenMapper.findAccessTokenM(whereParams);
	}

}
