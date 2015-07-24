package com.sp2p.entity;

import java.io.Serializable;
import java.util.Date;

public class Admin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private	String userName;
	private String password;
	private int enable ;
	private Date lastTime;
	private String lastIP;
	private long roleId;
	private String realName;
	private String card;
	private String email;
	private String telphone;
	private String usrCustId;
	private String subAcctMoney;
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getLastIP() {
		return lastIP;
	}
	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
    public String getUsrCustId()
    {
        return usrCustId;
    }
    public void setUsrCustId(String usrCustId)
    {
        this.usrCustId = usrCustId;
    }
    public String getSubAcctMoney()
    {
        return subAcctMoney;
    }
    public void setSubAcctMoney(String subAcctMoney)
    {
        this.subAcctMoney = subAcctMoney;
    }
	
}
