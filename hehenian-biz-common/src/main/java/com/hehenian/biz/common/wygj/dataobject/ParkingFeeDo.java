package com.hehenian.biz.common.wygj.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.ibm.icu.math.BigDecimal;

public class ParkingFeeDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private String theowner;// 业主
	private String mainaddressid;// '省/市/县/区/小区'
	private String mobilephone;//手机号
	private String plate_number;//停车卡号
	private String car_emissions;//排量
	private Integer the_arage_type;//车库类型
	private Double  parking_fee;//停车费
	private Integer createuserid;// 创建人

	private Integer updateuserid;// 更新人
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTheowner() {
		return theowner;
	}
	public void setTheowner(String theowner) {
		this.theowner = theowner;
	}
	public String getMainaddressid() {
		return mainaddressid;
	}
	public void setMainaddressid(String mainaddressid) {
		this.mainaddressid = mainaddressid;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getPlate_number() {
		return plate_number;
	}
	public void setPlate_number(String plate_number) {
		this.plate_number = plate_number;
	}
	public String getCar_emissions() {
		return car_emissions;
	}
	public void setCar_emissions(String car_emissions) {
		this.car_emissions = car_emissions;
	}
	public Integer getThe_arage_type() {
		return the_arage_type;
	}
	public void setThe_arage_type(Integer the_arage_type) {
		this.the_arage_type = the_arage_type;
	}
	public Double getParking_fee() {
		return parking_fee;
	}
	public void setParking_fee(Double parking_fee) {
		this.parking_fee = parking_fee;
	}
	public Integer getCreateuserid() {
		return createuserid;
	}
	public void setCreateuserid(Integer createuserid) {
		this.createuserid = createuserid;
	}
	public Integer getUpdateuserid() {
		return updateuserid;
	}
	public void setUpdateuserid(Integer updateuserid) {
		this.updateuserid = updateuserid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
