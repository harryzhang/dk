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

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class LoanPersonCheckDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long id;
	private java.lang.Long loanId;
	private java.lang.String name;
	private java.lang.String idno;
	private java.lang.String cname;
	private java.lang.String caddress;
	private java.lang.String houseAddress;
	private java.lang.String mngfee;
	//columns END
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.Long getLoanId() {
		return this.loanId;
	}
	
	public void setLoanId(java.lang.Long value) {
		this.loanId = value;
	}
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	public java.lang.String getIdno() {
		return this.idno;
	}
	
	public void setIdno(java.lang.String value) {
		this.idno = value;
	}
	public java.lang.String getCname() {
		return this.cname;
	}
	
	public void setCname(java.lang.String value) {
		this.cname = value;
	}
	public java.lang.String getCaddress() {
		return this.caddress;
	}
	
	public void setCaddress(java.lang.String value) {
		this.caddress = value;
	}
	public java.lang.String getHouseAddress() {
		return this.houseAddress;
	}
	
	public void setHouseAddress(java.lang.String value) {
		this.houseAddress = value;
	}
	public java.lang.String getMngfee() {
		return this.mngfee;
	}
	
	public void setMngfee(java.lang.String value) {
		this.mngfee = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("LoanId",getLoanId())
			.append("Name",getName())
			.append("Idno",getIdno())
			.append("Cname",getCname())
			.append("Caddress",getCaddress())
			.append("HouseAddress",getHouseAddress())
			.append("Mngfee",getMngfee())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanPersonCheckDo == false) return false;
		if(this == obj) return true;
		LoanPersonCheckDo other = (LoanPersonCheckDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

