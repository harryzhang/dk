package com.hehenian.biz.common.account.dataobject;

import java.io.Serializable;

public class UserThirdPartyDo implements Serializable {
	private Integer id; // 用户ID
	private String thethirdusername; // 第三方用户账号
	private Integer userId; // 用户ID
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getThethirdusername() {
		return thethirdusername;
	}
	public void setThethirdusername(String thethirdusername) {
		this.thethirdusername = thethirdusername;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
