/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.common.loan.dataobject;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.hehenian.biz.common.util.StringUtil;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class LoanUserDo  implements java.io.Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7891399758648625541L;
	//columns START
	private java.lang.Long loanUserId;
	private java.lang.String name;
	private java.lang.String idNo;
	private String idNoShort;
	private java.lang.String mobile;
	private String mobileShort;
	private java.lang.Long cid;
	private java.lang.String cname;
	private java.lang.String caddress;
	private java.lang.Integer level;
	private java.lang.Integer birthday;
	private java.lang.Integer userType;
	//columns END
	public java.lang.Long getLoanUserId() {
		return this.loanUserId;
	}
	
	public void setLoanUserId(java.lang.Long value) {
		this.loanUserId = value;
	}
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = StringUtil.filterDangerString(value);
	}
	
	public java.lang.String getIdNo() {
		return idNo;
	}

	public void setIdNo(java.lang.String idNo) {
		this.idNo = StringUtil.filterDangerString(idNo); 
	}

	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(java.lang.String value) {
		this.mobile = StringUtil.filterDangerString(value);
	}
	public java.lang.Long getCid() {
		return this.cid;
	}
	
	public void setCid(java.lang.Long value) {
		this.cid = value;
	}
	public java.lang.String getCname() {
		return this.cname;
	}
	
	public void setCname(java.lang.String value) {
		this.cname = StringUtil.filterDangerString(value);;
	}
	public java.lang.String getCaddress() {
		return this.caddress;
	}
	
	public void setCaddress(java.lang.String value) {
		this.caddress = StringUtil.filterDangerString(value);;
	}
	public java.lang.Integer getLevel() {
		return this.level;
	}
	
	public void setLevel(java.lang.Integer value) {
		this.level = value;
	}
	public java.lang.Integer getBirthday() {
		return this.birthday;
	}
	
	public void setBirthday(java.lang.Integer value) {
		this.birthday = value;
	}
	public java.lang.Integer getUserType() {
		return this.userType;
	}
	
	public void setUserType(java.lang.Integer value) {
		this.userType = value;
	}

	public String getIdNoShort() {
		return idNoShort;
	}

	public void setIdNoShort(String idNoShort) {
		this.idNoShort = StringUtil.filterDangerString(idNoShort);
	}

	public String getMobileShort() {
		return mobileShort;
	}

	public void setMobileShort(String mobileShort) {
		this.mobileShort = StringUtil.filterDangerString(mobileShort);
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("LoanUserId",getLoanUserId())
			.append("Name",getName())
			.append("Idno",getIdNo())
			.append("Mobile",getMobile())
			.append("Cid",getCid())
			.append("Cname",getCname())
			.append("Caddress",getCaddress())
			.append("Level",getLevel())
			.append("Birthday",getBirthday())
			.append("UserType",getUserType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getLoanUserId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanUserDo == false) return false;
		if(this == obj) return true;
		LoanUserDo other = (LoanUserDo)obj;
		return new EqualsBuilder()
			.append(getLoanUserId(),other.getLoanUserId())
			.isEquals();
	}
}

