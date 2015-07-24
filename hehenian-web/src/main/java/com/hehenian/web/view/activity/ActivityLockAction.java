/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */


package com.hehenian.web.view.activity;

import java.util.Map;

import org.apache.log4j.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.opensymphony.xwork2.ActionSupport;

import com.hehenian.biz.common.activity.IActivityLockService;

@Scope("prototype")
@Component("activityLockAction")
public class ActivityLockAction extends ActionSupport implements RequestAware {
	
	private final Logger logger = Logger.getLogger(this.getClass());	
	@Autowired
	private IActivityLockService activityLockService;
	
	private Map<String, Object> request;
	
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;		
	}
	

	/**
	 * 新增或修改
	 * 
	 */
	public String saveActivityLock(){
		return "";
	}
	

	
	
	/**
	 * 删除
	 * 
	 */
	public String deleteActivityLock(){
	    return "";
	}
	
	/**
	 * 查找
	 * 
	 * @return
	 */
	public String findById(){
		return "";
	}
	
	/**
	 * 根据条件查找
	 * 
	 * @return
	 */
	public String query(){
		return "";
	}


}
