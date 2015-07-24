package com.hehenian.manager.modules.login.model;

import java.util.Date;

public class LoginDayRecord {

	private Long seqId;
	private Long userId;
	private Date loginTime;
	private Integer loginDay;

	public Long getSeqId() {
		return seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getLoginDay() {
		return loginDay;
	}

	public void setLoginDay(Integer loginDay) {
		this.loginDay = loginDay;
	}

}
