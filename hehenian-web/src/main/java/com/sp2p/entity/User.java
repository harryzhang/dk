package com.sp2p.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String userName;
	private String password;
	private Integer enable;
	private Date lastTime;
	private String lastIP;
	private BigDecimal balances;
	private Integer vipStatus;
	private String email;
	private String servicePerson;
	private String realName;
	private Integer authStep;// 用户基本资料验证
	private Integer source;// 用户来源
	private Integer creditrating;// 用户积分
	private Integer creditLevel;// 用户积分等级
	private String kefuname;
	private String idNo;
	private String headImage;
	private String personalHead;
	private String creditLimit;
	private String imgHead;
	private Integer kefuid;
	private String encodeP; // 加密后的密码,用来登录论坛
	private Long usrCustId;// 汇付天下客户号
	private String mobilePhone; // 手机号码
	private String usableSum ;
	private String refferee;
	
	
	public String getRefferee() {
		return refferee;
	}
	public void setRefferee(String refferee) {
		this.refferee = refferee;
	}

	/*
	 * 20140610 by 刘文韬
	 * 增加用户群组字段
	 */
	private int userGroup;
	public int getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(int userGroup) {
		this.userGroup = userGroup;
	}

	public String getUsableSum() {
		return usableSum;
	}

	public void setUsableSum(String usableSum) {
		this.usableSum = usableSum;
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

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
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

	public BigDecimal getBalances() {
		return balances;
	}

	public void setBalances(BigDecimal balances) {
		this.balances = balances;
	}

	public Integer getVipStatus() {
		return vipStatus;
	}

	public void setVipStatus(Integer vipStatus) {
		this.vipStatus = vipStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getServicePerson() {
		return servicePerson;
	}

	public void setServicePerson(String servicePerson) {
		this.servicePerson = servicePerson;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getAuthStep() {
		return authStep;
	}

	public void setAuthStep(Integer authStep) {
		this.authStep = authStep;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getCreditrating() {
		return creditrating;
	}

	public void setCreditrating(Integer creditrating) {
		this.creditrating = creditrating;
	}

	public Integer getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(Integer creditLevel) {
		this.creditLevel = creditLevel;
	}

	public String getKefuname() {
		return kefuname;
	}

	public void setKefuname(String kefuname) {
		this.kefuname = kefuname;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getPersonalHead() {
		return personalHead;
	}

	public void setPersonalHead(String personalHead) {
		this.personalHead = personalHead;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getImgHead() {
		return imgHead;
	}

	public void setImgHead(String imgHead) {
		this.imgHead = imgHead;
	}

	public Integer getKefuid() {
		return kefuid;
	}

	public void setKefuid(Integer kefuid) {
		this.kefuid = kefuid;
	}

	public String getEncodeP() {
		return encodeP;
	}

	public void setEncodeP(String encodeP) {
		this.encodeP = encodeP;
	}

	public Long getUsrCustId() {
		return usrCustId;
	}

	public void setUsrCustId(Long usrCustId) {
		this.usrCustId = usrCustId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
}
