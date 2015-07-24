package com.hehenian.liumi.exchange;

public class Parameter {
	private final static String APPKET="caifubao";
//	private final static String APPSECRETKEY="caifubaotest";
	private final static String APPSECRETKEY="jgifn735&%$#";
	
	private String token;
	private String appkey=APPKET;
	private String appsecret=APPSECRETKEY;
	private String postpackage;
	private String mobile;
	private String extno;
	private String fixtime;
	private String appver;
	private String orderNo;
	
	
	
	private String sign;
	
	private String sqtime;
	private Integer num;
	private String status;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getAppsecret() {
		return MD5Util.string2MD5(appsecret);
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getPostpackage() {
		return postpackage;
	}
	public void setPostpackage(String postpackage) {
		this.postpackage = postpackage;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getExtno() {
		return extno;
	}
	public void setExtno(String extno) {
		this.extno = extno;
	}
	public String getFixtime() {
		return fixtime;
	}
	public void setFixtime(String fixtime) {
		this.fixtime = fixtime;
	}
	public String getAppver() {
		return appver;
	}
	public void setAppver(String appver) {
		this.appver = appver;
	}
	public String getSqtime() {
		return sqtime;
	}
	public void setSqtime(String sqtime) {
		this.sqtime = sqtime;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSign() {
		StringBuffer sb = new StringBuffer();
		sb.append("appkey").append(appkey);
		sb.append("appsecret").append(this.getAppsecret());
		if(appver!=null)
			sb.append("appver").append(appver);
		if(mobile!=null)
			sb.append("mobile").append(mobile);
		if(orderNo!=null)
			sb.append("orderNo").append(orderNo);
		if(postpackage!=null)
			sb.append("postpackage").append(postpackage);
		if(token!=null)
			sb.append("token").append(token);
		this.sign = SHA1.getSHA1(sb.toString());
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
