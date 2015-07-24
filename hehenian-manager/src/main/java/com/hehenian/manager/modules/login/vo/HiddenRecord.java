package com.hehenian.manager.modules.login.vo;

import java.util.Date;

/**
 * 隐藏记录
 * 
 *
 */
public class HiddenRecord {

	
	private int userId;
	
	private int reason;
	
	private Date createTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getReason() {
		return reason;
	}

	public void setReason(int reason) {
		this.reason = reason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
