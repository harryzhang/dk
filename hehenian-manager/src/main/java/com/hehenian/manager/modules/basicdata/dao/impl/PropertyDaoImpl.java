/**
 * Project Name:hehenian-manager
 * File Name:PropertyDaoImpl.java
 * Package Name:com.hehenian.manager.modules.basicdata.service.impl
 * Date:2015年5月5日下午2:36:49
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata.dao.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hehenian.manager.modules.basicdata.dao.PropertyDao;
import com.hehenian.manager.modules.basicdata.model.Parking;
import com.hehenian.manager.modules.basicdata.model.Property;

/**
 * 
 * @author   songxjmf
 * @date: 2015年5月5日 下午2:36:49 	 
 */
@Repository("propertyDao")
public class PropertyDaoImpl implements PropertyDao {

	@Resource
	protected NamedParameterJdbcTemplate sp2pNameJdbcTemplate;
	
	/**
	 * 插入多宝物业数据
	 */
	@Override
	public void insertPropertyData(Property property) {
		String sql = "INSERT INTO t_property_management_fee(theowner,mainaddressid,building,roomnum,roomtype,floor_area,property_fee,createTime)VALUES (?,?,?,?,?,?,?,?)";
		sp2pNameJdbcTemplate.getJdbcOperations().update(sql, property.getTheowner(),property.getMainAddressId(),property.getBuilding(),property.getRoomNum(),property.getRoomType(),property.getFloorArea(),property.getPropertyFee(),property.getCreateTime());
	}
	
	/**
	 * 插入车宝物业数据
	 * @author songxjmf
	 * @date: 2015年5月5日 下午3:20:51
	 */
	@Override
	public void insertParkingData(Parking parking){
		String sql = "INSERT INTO t_parking_fee (mainaddressid,plate_number,car_emissions,the_arage_type,parking_fee,createTime)VALUES(?, ?, ?, ?, ?, ?)";
		sp2pNameJdbcTemplate.getJdbcOperations().update(sql, parking.getMainAddressId(),parking.getPlateNumber(),parking.getCarEmissions(),parking.getTheArageType(),parking.getParkingFee(),parking.getCreateTime());
	}
	
}

