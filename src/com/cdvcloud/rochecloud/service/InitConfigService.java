package com.cdvcloud.rochecloud.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdvcloud.rochecloud.domain.wechar.WechatKeyVo;
import com.cdvcloud.rochecloud.mongodao.BasicDao;

@Service
public class InitConfigService {
	private static Logger logger = Logger.getLogger(InitConfigService.class);
	
	public static Map<String, Object> wechatKeyMap = new ConcurrentHashMap<String, Object>();
	
	public static Map<String, Object> mapGlobal = new ConcurrentHashMap<String, Object>();
	
	@Autowired
	private BasicDao baseDao;

	public synchronized static WechatKeyVo getWechatKeyByProductId(String productId) {
		WechatKeyVo vo = null;
		return vo;
	}
	
	public static String getConfigKey(String key) {
		if(mapGlobal.containsKey(key)) {
			return String.valueOf(mapGlobal.get(key));
		} else {
			return "";
		}
	}
	
}
