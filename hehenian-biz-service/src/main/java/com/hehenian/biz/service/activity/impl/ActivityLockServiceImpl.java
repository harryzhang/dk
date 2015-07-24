/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.service.activity.impl;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.activity.dataobject.ActivityLockDo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.component.activity.IActivityLockComponent;
import com.hehenian.biz.common.activity.IActivityLockService;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("activityLockService")
public class ActivityLockServiceImpl implements IActivityLockService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private IActivityLockComponent  activityLockComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public ActivityLockDo getById(int id){
	  return activityLockComponent.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<ActivityLockDo> selectActivityLock(Map<String,Object> parameterMap){
		return activityLockComponent.selectActivityLock(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateActivityLockById(ActivityLockDo newActivityLockDo){
		return activityLockComponent.updateActivityLockById(newActivityLockDo);
	}
	
	/**
	 * 新增
	 */
	public int addActivityLock(ActivityLockDo newActivityLockDo){
		return activityLockComponent.addActivityLock(newActivityLockDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return activityLockComponent.deleteById(id);
	}

}
