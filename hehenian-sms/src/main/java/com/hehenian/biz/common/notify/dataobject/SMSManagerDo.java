package com.hehenian.biz.common.notify.dataobject;

public class SMSManagerDo {
	
	private int id;
	
	/**
	 * 短信营运注册机构号
	 */
	private String orgId;
	/**
	 * 短信营运商分配的短信发送的用户
	 */
	private String userId;
	
	/**
	 * 短信营运商分配的短信发送的密码
	 */
	private String pwd;
	
	/**
	 * 调用的URL
	 */
	private String wsdlUrl;
	
	/**
	 * 发送的方法
	 */
	private String sendMethod;
	/**
	 * 是否可用
	 */
	private String validate;
	
	/**
	 * wsdl的版本 axis ...
	 */
	private String wsdlType;
	
	/**
	 * 短信供应商
	 */
	private String smsSupplier;
	
	/**
	 * 根据短信类型选取短信供应商
	 */
	private String smsType;
	/**
	 * 群发短信的方法名
	 */
	private String groupSendMethod;
	/**
	 * 短信WEB管理url
	 */
	private String managerHomeUrl;

	public String getGroupSendMethod() {
		return groupSendMethod;
	}

	public void setGroupSendMethod(String groupSendMethod) {
		this.groupSendMethod = groupSendMethod;
	}

	public String getManagerHomeUrl() {
		return managerHomeUrl;
	}

	public void setManagerHomeUrl(String managerHomeUrl) {
		this.managerHomeUrl = managerHomeUrl;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getWsdlUrl() {
		return wsdlUrl;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	public String getSendMethod() {
		return sendMethod;
	}

	public void setSendMethod(String sendMethod) {
		this.sendMethod = sendMethod;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getWsdlType() {
		return wsdlType;
	}

	public void setWsdlType(String wsdlType) {
		this.wsdlType = wsdlType;
	}

	public String getSmsSupplier() {
		return smsSupplier;
	}

	public void setSmsSupplier(String smsSupplier) {
		this.smsSupplier = smsSupplier;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
