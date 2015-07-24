/**
 * Project Name:hehenian-manager
 * File Name:Community.java
 * Package Name:com.hehenian.manager.modules.basicdata.model
 * Date:2015年5月5日上午8:51:13
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
 */

package com.hehenian.manager.modules.basicdata.model;

import com.hehenian.manager.modules.basicdata.BaseModel;

/**
 * 物业数据
 * @author songxjmf
 * @date: 2015年5月5日 上午8:51:13
 */
public class Property extends BaseModel {

	/**
	 * 商铺
	 */
	public final static int ROOMTYPE_STORE = 0;
	/**
	 * 住宅
	 */
	public final static int ROOMTYPE_HOUSING = 1;
	/**
	 * 写字楼
	 */
	public final static int ROOMTYPE_OFFICE = 2;
	
	/**
	 * 业主姓名
	 */
	private String theowner;
	/**
	 * 地址ID
	 */
	private Long mainAddressId;
	/**
	 * 楼栋
	 */
	private String building;
	/**
	 * 房间号
	 */
	private String roomNum;
	/**
	 * 房屋类型：0，商铺；1住宅：2写字楼
	 */
	private Integer roomType;
	/**
	 * 房屋面积
	 */
	private Double floorArea;
	/**
	 * 手机号码
	 */
	private String mobilePhone;

	/**
	 * 物业费
	 */
	private Double propertyFee;

	public String getTheowner() {
		return theowner;
	}

	public void setTheowner(String theowner) {
		this.theowner = theowner;
	}

	public Long getMainAddressId() {
		return mainAddressId;
	}

	public void setMainAddressId(Long mainAddressId) {
		this.mainAddressId = mainAddressId;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	public Double getFloorArea() {
		return floorArea;
	}

	public void setFloorArea(Double floorArea) {
		this.floorArea = floorArea;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Double getPropertyFee() {
		return propertyFee;
	}

	public void setPropertyFee(Double propertyFee) {
		this.propertyFee = propertyFee;
	}
	
	public static int getRommType(String roomType){
		if("商铺".equals(roomType)){
			return 0;
		}else if("住宅".equals(roomType)){
			return 1;
		}else if("写字楼".equals(roomType)){
			return 2;
		}else{
			return 3;
		}
	}

}
