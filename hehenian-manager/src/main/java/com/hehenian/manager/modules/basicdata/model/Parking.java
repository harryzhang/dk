/**
 * Project Name:hehenian-manager
 * File Name:Parking.java
 * Package Name:com.hehenian.manager.modules.basicdata.model
 * Date:2015年5月5日上午9:26:10
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
 */

package com.hehenian.manager.modules.basicdata.model;

import com.hehenian.manager.modules.basicdata.BaseModel;

/**
 * 车位
 * @author songxjmf
 * @date: 2015年5月5日 上午9:26:10
 */
public class Parking extends BaseModel {
	/**
	 * 地址ID
	 */
	private Long mainAddressId;
	/**
	 * 手机号码
	 */
	private String mobilePhone;
	/**
	 * 停车卡号
	 */

	private String plateNumber;
	/**
	 * 汽车排量
	 */
	private String carEmissions;
	/**
	 * 车库类型：0，室内；1室外
	 */
	private Integer theArageType;
	/**
	 * 停车费
	 */
	private Double parkingFee;

	public Long getMainAddressId() {
		return mainAddressId;
	}

	public void setMainAddressId(Long mainAddressId) {
		this.mainAddressId = mainAddressId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getCarEmissions() {
		return carEmissions;
	}

	public void setCarEmissions(String carEmissions) {
		this.carEmissions = carEmissions;
	}

	public Integer getTheArageType() {
		return theArageType;
	}

	public void setTheArageType(Integer theArageType) {
		this.theArageType = theArageType;
	}

	public Double getParkingFee() {
		return parkingFee;
	}

	public void setParkingFee(Double parkingFee) {
		this.parkingFee = parkingFee;
	}
	
	public static int getGarageType(String garageType){
		if("室内".equals(garageType)){
			return 0;
		}else if("室外".equals(garageType)){
			return 1;
		}else{
			return 2;
		}
		
	}

}
