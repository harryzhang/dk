package com.hehenian.biz.common.wygj.dataobject;

public class OffsetDetailVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private Integer user_id;// 用户ID
	private Long mainaddressid;// '省/市/县/区/小区'
	
	private String building;// 楼栋
	private String roomnum;//房间号
	private String theowner;// 业主
	
	private String plate_number;//停车卡号
	private String car_emissions;//排量
	private Integer the_arage_type;//车库类型
	
	private Integer  defaultset;//是否为默认：0，不是；1默认
	private Integer infotype;// 是否冲抵
	

	
	private String addressDesc;

	public String getAddressDesc() {
		return addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

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

	public String getTheowner() {
		return theowner;
	}

	public void setTheowner(String theowner) {
		this.theowner = theowner;
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
