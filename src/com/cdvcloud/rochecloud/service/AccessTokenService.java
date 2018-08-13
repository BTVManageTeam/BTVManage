package com.cdvcloud.rochecloud.service;

import com.cdvcloud.rochecloud.domain.wechar.AccessTokenM;


/**
 * 业务描述：微信AccessToken相关操作
 * 
 * @author chenchang
 *
 */
public interface AccessTokenService{
	
	int addAccessTokenM(AccessTokenM params);

	int updateAccessTokenM(AccessTokenM setParams);
	
	AccessTokenM findAccessTokenM(AccessTokenM whereParams);
	
	

}
