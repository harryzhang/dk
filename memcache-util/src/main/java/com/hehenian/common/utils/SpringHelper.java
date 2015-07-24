package com.hehenian.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring 辅助类，用于获取容器中的bean
 * 
 * @author tuoqiantu
 * @date 2013-8-12 上午11:12:37
 * 
 */
public class SpringHelper implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringHelper.applicationContext = arg0;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		if(applicationContext == null){
			return null;
		}
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		if(applicationContext == null){
			return null;
		}
		return applicationContext.getBean(clazz);
	}

}
