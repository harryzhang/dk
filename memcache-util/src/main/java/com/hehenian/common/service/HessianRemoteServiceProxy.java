package com.hehenian.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.InitializingBean;


/**
 * hessian 分布式远程调用
 * @author Calvin
 */
public abstract class HessianRemoteServiceProxy<T> implements InitializingBean {

	/**
	 * 服务url列表
	 */
    private String[] serviceUrl;
    
    /**
     * 服务权重
     */
    private Integer[] priority;
    
    /**
     * 服务名称 如 com.zhenai.jiami.service.EncryptService
     */
    private String serviceInterface;
    
    /**
     * 超时时间
     */
    protected int readTimeout;
    
    /**
     * 是否支持重载
     */
    private boolean overloadEnabled;
    
    /**
     * 服务排序
     */
    private List<Integer> list;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(priority != null) {
			list = new ArrayList<Integer>();
			for(Integer i = 0; i < priority.length; i++) {
				for(int j = 0; j <= Math.abs(priority[i]); j++) {
					list.add(Integer.valueOf(i));
				}
			}
			Collections.shuffle(list);
		}
	}
	
    /**
     * 获取服务URL
     * @return
     */
    protected String getSerivceUrl()
    {
		if(priority == null) {
			return serviceUrl[RandomUtils.nextInt(serviceUrl.length)];
		}
		
		return serviceUrl[list.get(RandomUtils.nextInt(list.size()))];
    }
    
    /**
     * 获取服务
     * @return
     */
    protected abstract T getService();
    
	public String[] getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String[] serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public Integer[] getPriority() {
		return priority;
	}

	public void setPriority(Integer[] priority) {
		this.priority = priority;
	}

	public String getServiceInterface() {
		return serviceInterface;
	}

	public void setServiceInterface(String serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public boolean getOverloadEnabled() {
		return overloadEnabled;
	}

	public void setOverloadEnabled(boolean overloadEnabled) {
		this.overloadEnabled = overloadEnabled;
	}
}
