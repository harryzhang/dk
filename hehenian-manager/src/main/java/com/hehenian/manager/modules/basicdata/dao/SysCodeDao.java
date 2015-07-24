/**
 * Project Name:hehenian-manager
 * File Name:getSubjectByNameAndType.java
 * Package Name:com.hehenian.manager.modules.basicdata.dao
 * Date:2015年5月5日下午2:35:18
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata.dao;

import com.hehenian.manager.modules.basicdata.model.SysCode;

/**
 * 
 * @author   songxjmf
 * @date: 2015年5月5日 下午2:35:18 	 
 */
public interface SysCodeDao {

	/**
	 * 根据省/市/区/小区、级别查找编码
	 */
	public Long getSubjectByNameAndType(String parentSubjectName,String subjectName,int sysCodeType);
	
	/**
	 * 找区县下面最大的小区编号
	 *
	 * @author songxjmf
	 * @date: 2015年5月5日 下午6:00:47
	 */
	public Long getMaxCommunityId(Long districtId);
	
	/**
	 * 新增省/市/区/小区
	 * @author songxjmf
	 * @date: 2015年5月5日 下午3:20:51
	 */
	public void insertSubject(SysCode sysCode);
}

