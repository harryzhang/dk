package com.hehenian.biz.common.wygj.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.ibm.icu.math.BigDecimal;

public class PropertyManagementDetailDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private Integer user_id;// 用户ID
	private String theowner;// 业主
	private Long mainaddressid;// '省/市/县/区/小区'
	private String building;// 楼栋
	private String roomnum;//房间号
	private Integer  defaultset;//是否为默认：0，不是；1默认
	private Integer infotype;// 创建人
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getTheowner() {
		return theowner;
	}
	public void setTheowner(String theowner) {
		this.theowner = theowner;
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
	public Long getMainaddressid() {
		return mainaddressid;
	}
	public void setMainaddressid(Long mainaddressid) {
		this.mainaddressid = mainaddressid;
	}
	public Integer getDefaultset() {
		return defaultset;
	}
	public void setDefaultset(Integer defaultset) {
		this.defaultset = defaultset;
	}
	public Integer getInfotype() {
		return infotype;
	}
	public void setInfotype(Integer infotype) {
		this.infotype = infotype;
	}

	
}
