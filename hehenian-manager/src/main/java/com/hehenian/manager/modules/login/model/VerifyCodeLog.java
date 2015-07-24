package com.hehenian.manager.modules.login.model;

import java.util.Date;

public class VerifyCodeLog {
	
	private long id;
	
	private long userId;
	
	private int type;
	
	private String verifyCode;
	
	private Date verifyCreateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Date getVerifyCreateTime() {
		return verifyCreateTime;
	}

	public void setVerifyCreateTime(Date verifyCreateTime) {
		this.verifyCreateTime = verifyCreateTime;
	}
	
}
