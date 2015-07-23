package com.hehenian.biz.common.wygj.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.ibm.icu.math.BigDecimal;

public class PropertyManagementFeeDo implements Serializable {
	
	private static final long serialVersionUID = 6102616132107267060L;
	
	private Integer id;
	private String theowner; // 业主,
	private Long mainaddressid;//'省/市/县/区/小区',
	private String building; //'楼栋',
	private String roomnum;//'房间号',
	private Integer roomtype;// '房屋类型：0，商铺；1住宅：2写字楼',
	private Double floor_area;//'房屋面积',
	private String mobilephone;//手机号',
	private Double property_fee;//'物业费',
	private Integer createuserid;//创建用户ID',
	private Integer updateuserid;//改用户ID',
	private Date createTime;//创建时间',
	private Date updateTime;

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
	public Long getMainaddressid() {
		return mainaddressid;
	}
	public void setMainaddressid(Long mainaddressid) {
		this.mainaddressid = mainaddressid;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getRoomnum() {
		return roomnum;
	}
	public void setRoomnum(String roomnum) {
		this.roomnum = roomnum;
	}
	public Integer getRoomtype() {
		return roomtype;
	}
	public void setRoomtype(Integer roomtype) {
		this.roomtype = roomtype;
	}
	public Double getFloor_area() {
		return floor_area;
	}
	public void setFloor_area(Double floor_area) {
		this.floor_area = floor_area;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public Double getProperty_fee() {
		return property_fee;
	}
	public void setProperty_fee(Double property_fee) {
		this.property_fee = property_fee;
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
