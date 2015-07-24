/**
 * Project Name:hehenian-manager
 * File Name:getSubjectByNameAndType.java
 * Package Name:com.hehenian.manager.modules.basicdata.dao
 * Date:2015年5月5日下午2:35:18
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata.dao;

import com.hehenian.manager.modules.basicdata.model.Parking;
import com.hehenian.manager.modules.basicdata.model.Property;

/**
 * 
 * @author   songxjmf
 * @date: 2015年5月5日 下午2:35:18 	 
 */
public interface PropertyDao {

	/**
	 * 插入多宝物业数据
	 *
	 * @author songxjmf
	 * @date: 2015年5月5日 下午5:49:01
	 */
	public void insertPropertyData(Property property);
	
	/**
	 * 插入车宝物业数据
	 * @author songxjmf
	 * @date: 2015年5月5日 下午3:20:51
	 */
	public void insertParkingData(Parking parking);
}

