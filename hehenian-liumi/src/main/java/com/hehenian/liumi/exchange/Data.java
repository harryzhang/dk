package com.hehenian.liumi.exchange;

public class Data {

	private String token;
	private String orderNO;
	private String extNO;
	
	private String orderNo;
	private String extNo;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getOrderNO() {
		if(this.orderNO==null || "".equals(this.orderNO))
		    return orderNo;
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	public String getExtNO() {
		if(this.extNO==null || "".equals(this.extNO))
		    return extNo;
		return extNO;
	}
	public void setExtNO(String extNO) {
		this.extNO = extNO;
	}
	public String getOrderNo() {
		if(this.orderNo==null || "".equals(this.orderNo))
		    return orderNO;
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getExtNo() {
		if(this.extNo==null || "".equals(this.extNo))
		    return extNO;
		return extNo;
	}
	public void setExtNo(String extNo) {
		this.extNo = extNo;
	}
	
	
}
