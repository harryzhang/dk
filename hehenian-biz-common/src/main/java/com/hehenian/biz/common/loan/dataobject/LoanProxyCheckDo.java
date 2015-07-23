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



public class LoanProxyCheckDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long id;
	private java.lang.Long loanId;
	private java.lang.String tableCode;
	private java.lang.Long recordId;
	private java.lang.String nameCode;
	private java.lang.String fieldName;
	private java.lang.Integer status;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	private java.lang.Long auditUserId;
	private java.lang.String auditUserName;
	private java.util.Date auditTime;
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
	public java.lang.String getTableCode() {
		return this.tableCode;
	}
	
	public void setTableCode(java.lang.String value) {
		this.tableCode = value;
	}
	public java.lang.Long getRecordId() {
		return this.recordId;
	}
	
	public void setRecordId(java.lang.Long value) {
		this.recordId = value;
	}
	public java.lang.String getNameCode() {
		return this.nameCode;
	}
	
	public void setNameCode(java.lang.String value) {
		this.nameCode = value;
	}
	public java.lang.String getFieldName() {
		return this.fieldName;
	}
	
	public void setFieldName(java.lang.String value) {
		this.fieldName = value;
	}
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	public java.lang.Long getAuditUserId() {
		return this.auditUserId;
	}
	
	public void setAuditUserId(java.lang.Long value) {
		this.auditUserId = value;
	}
	
	public java.lang.String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(java.lang.String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public java.util.Date getAuditTime() {
		return this.auditTime;
	}
	
	public void setAuditTime(java.util.Date value) {
		this.auditTime = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("LoanId",getLoanId())
			.append("TableCode",getTableCode())
			.append("RecordId",getRecordId())
			.append("NameCode",getNameCode())
			.append("FieldName",getFieldName())
			.append("Status",getStatus())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.append("AuditUserId",getAuditUserId())
			.append("AuditUserName",getAuditUserName())
			.append("AuditTime",getAuditTime())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanProxyCheckDo == false) return false;
		if(this == obj) return true;
		LoanProxyCheckDo other = (LoanProxyCheckDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

