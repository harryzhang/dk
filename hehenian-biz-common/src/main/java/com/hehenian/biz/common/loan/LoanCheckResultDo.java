/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.common.loan;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class LoanCheckResultDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long loanId;
	private java.lang.Integer id;
	private java.lang.Long itemCheckId;
	private java.lang.String status;
	private java.lang.Double checkResult;
	private java.util.Date createTime;
	//columns END
	public java.lang.Long getLoanId() {
		return this.loanId;
	}
	
	public void setLoanId(java.lang.Long value) {
		this.loanId = value;
	}
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	public java.lang.Long getItemCheckId() {
		return this.itemCheckId;
	}
	
	public void setItemCheckId(java.lang.Long value) {
		this.itemCheckId = value;
	}
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	public java.lang.Double getCheckResult() {
		return this.checkResult;
	}
	
	public void setCheckResult(java.lang.Double value) {
		this.checkResult = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("LoanId",getLoanId())
			.append("Id",getId())
			.append("ItemCheckId",getItemCheckId())
			.append("Status",getStatus())
			.append("CheckResult",getCheckResult())
			.append("CreateTime",getCreateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanCheckResultDo == false) return false;
		if(this == obj) return true;
		LoanCheckResultDo other = (LoanCheckResultDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

