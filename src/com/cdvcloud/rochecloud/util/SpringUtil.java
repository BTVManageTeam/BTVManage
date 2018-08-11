package com.cdvcloud.rochecloud.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		 synchronized(SpringUtil.class) {
			 applicationContext = arg0;
		 }
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getInstance(String name) {
		return applicationContext.getBean(name);
	}

}
