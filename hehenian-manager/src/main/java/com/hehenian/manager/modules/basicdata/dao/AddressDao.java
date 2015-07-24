/**
 * Project Name:hehenian-manager
 * File Name:AddressDao.java
 * Package Name:com.hehenian.manager.modules.basicdata.dao
 * Date:2015年5月6日上午10:54:04
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata.dao;

import java.sql.SQLException;

/**
 * 
 * @author   songxjmf
 * @date: 2015年5月6日 上午10:54:04 	 
 */
public interface AddressDao {

	/**
	 * 通过小区编码查询省市区小区完整地址
	 * @param communityCode 小区编码
	 * @author songxjmf
	 * @date: 2015年5月6日 上午10:44:49
	 */
	public String getByCommunityCode(String communityCode) throws SQLException;
}

