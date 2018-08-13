package com.cdvcloud.rochecloud.service;

import org.apache.log4j.Logger;

public class CoreBeanFactory {
	private static Logger logger = Logger.getLogger(CoreBeanFactory.class);

	public static ICoreService getInstance(String productId) {
		try {
			logger.info("create productId=["+productId+"] bean factory.");
			String classBean = InitConfigService.getWechatKeyByProductId(productId).getClassBean();
			if(null == classBean || "".equals(classBean)) {
				classBean = "com.cdvcloud.wx.service.wechat.coreImpl.CoreServiceImpl"; //走公共的类
			}
			return (ICoreService)Class.forName(classBean).newInstance();
		} catch (Exception e) {
			logger.error("create productId=["+productId+"] coreBean factory exception, errMsg={" +e+ "}.");
			return null;
		}
	}
}
