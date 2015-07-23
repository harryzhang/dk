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



public class LoanModifyLogDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long modifyLogId;
	private java.lang.String field;
	private java.lang.String oldVal;
	private java.lang.String newVal;
	private java.util.Date createTime;
	private java.lang.Long loanId;
	private java.lang.Long modifyBy;
	//columns END
	public java.lang.Long getModifyLogId() {
		return this.modifyLogId;
	}
	
	public void setModifyLogId(java.lang.Long value) {
		this.modifyLogId = value;
	}
	public java.lang.String getField() {
		return this.field;
	}
	
	public void setField(java.lang.String value) {
		this.field = value;
	}
	public java.lang.String getOldVal() {
		return this.oldVal;
	}
	
	public void setOldVal(java.lang.String value) {
		this.oldVal = value;
	}
	public java.lang.String getNewVal() {
		return this.newVal;
	}
	
	public void setNewVal(java.lang.String value) {
		this.newVal = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.lang.Long getLoanId() {
		return this.loanId;
	}
	
	public void setLoanId(java.lang.Long value) {
		this.loanId = value;
	}
	public java.lang.Long getModifyBy() {
		return this.modifyBy;
	}
	
	public void setModifyBy(java.lang.Long value) {
		this.modifyBy = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("ModifyLogId",getModifyLogId())
			.append("Field",getField())
			.append("OldVal",getOldVal())
			.append("NewVal",getNewVal())
			.append("CreateTime",getCreateTime())
			.append("LoanId",getLoanId())
			.append("ModifyBy",getModifyBy())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getModifyLogId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanModifyLogDo == false) return false;
		if(this == obj) return true;
		LoanModifyLogDo other = (LoanModifyLogDo)obj;
		return new EqualsBuilder()
			.append(getModifyLogId(),other.getModifyLogId())
			.isEquals();
	}
}

