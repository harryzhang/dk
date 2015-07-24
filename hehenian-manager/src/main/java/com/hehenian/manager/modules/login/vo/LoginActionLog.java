package com.hehenian.manager.modules.login.vo;

import java.util.Date;

/**
 * 登录日志类
 * 
 * @author vipsong
 * 
 */
public class LoginActionLog {

	private Integer userId;

	private Date loginTime;

	private int loginType;

	private String ip;

	//0为普通登录，1为自动登录
	private int isAutoLogin;

	public LoginActionLog(Integer userId, Date loginTime, int loginType,
			String ip, int isAutoLogin) {
		super();
		this.userId = userId;
		this.loginTime = loginTime;
		this.loginType = loginType;
		this.ip = ip;
		this.isAutoLogin = isAutoLogin;
	}

	public void setIsAutoLogin(int isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public void setLoginType(Short loginType) {
		this.loginType = loginType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setIsAutoLogin(short isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public int getIsAutoLogin() {
		return isAutoLogin;
	}

}
