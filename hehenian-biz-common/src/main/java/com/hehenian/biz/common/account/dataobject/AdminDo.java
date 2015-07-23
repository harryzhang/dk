/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.common.account.dataobject;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class AdminDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long id;
	private java.lang.String userName;
	private java.lang.String password;
	private java.lang.Integer enable;
	private java.util.Date lastTime;
	private java.lang.String lastIp;
	private java.lang.Long roleId;
	private java.lang.String realName;
	private java.lang.String telphone;
	private java.lang.String qq;
	private java.lang.String email;
	private java.lang.String img;
	private java.lang.String isLeader;
	private java.lang.Integer sex;
	private java.lang.String card;
	private java.lang.String summary;
	private java.lang.Integer nativePlacePro;
	private java.lang.Integer nativePlaceCity;
	private java.lang.String address;
	private java.util.Date addDate;
	private java.lang.Double moneys;
	private java.lang.Long usrCustId;
	private java.lang.String subAcct;
	private java.lang.Double subAcctMoney;
	//columns END
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.String getUserName() {
		return this.userName;
	}
	
	public void setUserName(java.lang.String value) {
		this.userName = value;
	}
	public java.lang.String getPassword() {
		return this.password;
	}
	
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	public java.lang.Integer getEnable() {
		return this.enable;
	}
	
	public void setEnable(java.lang.Integer value) {
		this.enable = value;
	}
	public java.util.Date getLastTime() {
		return this.lastTime;
	}
	
	public void setLastTime(java.util.Date value) {
		this.lastTime = value;
	}
	public java.lang.String getLastIp() {
		return this.lastIp;
	}
	
	public void setLastIp(java.lang.String value) {
		this.lastIp = value;
	}
	public java.lang.Long getRoleId() {
		return this.roleId;
	}
	
	public void setRoleId(java.lang.Long value) {
		this.roleId = value;
	}
	public java.lang.String getRealName() {
		return this.realName;
	}
	
	public void setRealName(java.lang.String value) {
		this.realName = value;
	}
	public java.lang.String getTelphone() {
		return this.telphone;
	}
	
	public void setTelphone(java.lang.String value) {
		this.telphone = value;
	}
	public java.lang.String getQq() {
		return this.qq;
	}
	
	public void setQq(java.lang.String value) {
		this.qq = value;
	}
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	public java.lang.String getImg() {
		return this.img;
	}
	
	public void setImg(java.lang.String value) {
		this.img = value;
	}
	public java.lang.String getIsLeader() {
		return this.isLeader;
	}
	
	public void setIsLeader(java.lang.String value) {
		this.isLeader = value;
	}
	public java.lang.Integer getSex() {
		return this.sex;
	}
	
	public void setSex(java.lang.Integer value) {
		this.sex = value;
	}
	public java.lang.String getCard() {
		return this.card;
	}
	
	public void setCard(java.lang.String value) {
		this.card = value;
	}
	public java.lang.String getSummary() {
		return this.summary;
	}
	
	public void setSummary(java.lang.String value) {
		this.summary = value;
	}
	public java.lang.Integer getNativePlacePro() {
		return this.nativePlacePro;
	}
	
	public void setNativePlacePro(java.lang.Integer value) {
		this.nativePlacePro = value;
	}
	public java.lang.Integer getNativePlaceCity() {
		return this.nativePlaceCity;
	}
	
	public void setNativePlaceCity(java.lang.Integer value) {
		this.nativePlaceCity = value;
	}
	public java.lang.String getAddress() {
		return this.address;
	}
	
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	public java.util.Date getAddDate() {
		return this.addDate;
	}
	
	public void setAddDate(java.util.Date value) {
		this.addDate = value;
	}
	public java.lang.Double getMoneys() {
		return this.moneys;
	}
	
	public void setMoneys(java.lang.Double value) {
		this.moneys = value;
	}
	public java.lang.Long getUsrCustId() {
		return this.usrCustId;
	}
	
	public void setUsrCustId(java.lang.Long value) {
		this.usrCustId = value;
	}
	public java.lang.String getSubAcct() {
		return this.subAcct;
	}
	
	public void setSubAcct(java.lang.String value) {
		this.subAcct = value;
	}
	public java.lang.Double getSubAcctMoney() {
		return this.subAcctMoney;
	}
	
	public void setSubAcctMoney(java.lang.Double value) {
		this.subAcctMoney = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserName",getUserName())
			.append("Password",getPassword())
			.append("Enable",getEnable())
			.append("LastTime",getLastTime())
			.append("LastIp",getLastIp())
			.append("RoleId",getRoleId())
			.append("RealName",getRealName())
			.append("Telphone",getTelphone())
			.append("Qq",getQq())
			.append("Email",getEmail())
			.append("Img",getImg())
			.append("IsLeader",getIsLeader())
			.append("Sex",getSex())
			.append("Card",getCard())
			.append("Summary",getSummary())
			.append("NativePlacePro",getNativePlacePro())
			.append("NativePlaceCity",getNativePlaceCity())
			.append("Address",getAddress())
			.append("AddDate",getAddDate())
			.append("Moneys",getMoneys())
			.append("UsrCustId",getUsrCustId())
			.append("SubAcct",getSubAcct())
			.append("SubAcctMoney",getSubAcctMoney())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof AdminDo == false) return false;
		if(this == obj) return true;
		AdminDo other = (AdminDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

