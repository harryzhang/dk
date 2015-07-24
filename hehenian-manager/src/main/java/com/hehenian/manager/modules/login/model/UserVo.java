package com.hehenian.manager.modules.login.model;

import java.sql.Timestamp;
import java.util.Date;

public class UserVo {

	private int userId;

	/**
	 * 性别
	 */
	private Integer gender;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 秘钥
	 */
	private String salt;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 邀请用户
	 */
	private Long inviteUserId;
	
	/**
	 * 第三方ID 
	 */
	private String thirdId;

	/**
	 * 第三方来源（QQ、微博）
	 */
	private LoginType loginType;
	/**
	 * 注册时间
	 */
	private Date registerTime;

	/**
	 * 注册ip
	 */
	private String registerIp;

	/**
	 * 登录次数
	 */
	private Integer loginCount;

	/**
	 * 上次登录时间
	 */
	private Date lastLogTime;

	/**
	 * 上次登录ip
	 */
	private String lastLogIP;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Timestamp modifyTime;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(Long inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Date getLastLogTime() {
		return lastLogTime;
	}

	public void setLastLogTime(Date lastLogTime) {
		this.lastLogTime = lastLogTime;
	}

	public String getLastLogIP() {
		return lastLogIP;
	}

	public void setLastLogIP(String lastLogIP) {
		this.lastLogIP = lastLogIP;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	
}
