/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.activity;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.activity.dataobject.ActivityLockDo;

public interface IActivityLockComponent {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public ActivityLockDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<ActivityLockDo> selectActivityLock(Map<String, Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateActivityLockById(ActivityLockDo newActivityLockDo);
	
	/**
	 * 新增
	 */
	public int addActivityLock(ActivityLockDo newActivityLockDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
