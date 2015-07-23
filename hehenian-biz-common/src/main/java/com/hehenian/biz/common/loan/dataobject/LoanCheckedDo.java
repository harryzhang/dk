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



public class LoanCheckedDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long id;
	private java.lang.Long loanId;
	private java.lang.String opinion;
	private java.lang.Double loanAmount;
	private java.lang.String checkResult;
	private java.lang.String checkUser;
	private java.util.Date checkDate;
	private java.lang.String checkType;
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
	public java.lang.String getOpinion() {
		return this.opinion;
	}
	
	public void setOpinion(java.lang.String value) {
		this.opinion = value;
	}
	public java.lang.Double getLoanAmount() {
		return this.loanAmount;
	}
	
	public void setLoanAmount(java.lang.Double value) {
		this.loanAmount = value;
	}
	public java.lang.String getCheckResult() {
		return this.checkResult;
	}
	
	public void setCheckResult(java.lang.String value) {
		this.checkResult = value;
	}
	public java.lang.String getCheckUser() {
		return this.checkUser;
	}
	
	public void setCheckUser(java.lang.String value) {
		this.checkUser = value;
	}
	public java.util.Date getCheckDate() {
		return this.checkDate;
	}
	
	public void setCheckDate(java.util.Date value) {
		this.checkDate = value;
	}
	public java.lang.String getCheckType() {
		return this.checkType;
	}
	
	public void setCheckType(java.lang.String value) {
		this.checkType = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("LoanId",getLoanId())
			.append("Opinion",getOpinion())
			.append("LoanAmount",getLoanAmount())
			.append("CheckResult",getCheckResult())
			.append("CheckUser",getCheckUser())
			.append("CheckDate",getCheckDate())
			.append("checkType",getCheckType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanCheckedDo == false) return false;
		if(this == obj) return true;
		LoanCheckedDo other = (LoanCheckedDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

