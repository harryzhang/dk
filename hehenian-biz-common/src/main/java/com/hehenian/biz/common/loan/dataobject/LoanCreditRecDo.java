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



public class LoanCreditRecDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long creditId;
	private java.lang.Long creditRecId;
	private String creditItem;
	private java.lang.String creditItemVal;
	private java.lang.Long creditAmt;
	private java.lang.String remark;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	//columns END
	public java.lang.Long getCreditId() {
		return this.creditId;
	}
	
	public void setCreditId(java.lang.Long value) {
		this.creditId = value;
	}
	public java.lang.Long getCreditRecId() {
		return this.creditRecId;
	}
	
	public void setCreditRecId(java.lang.Long value) {
		this.creditRecId = value;
	}
	
	public String getCreditItem() {
		return creditItem;
	}

	public void setCreditItem(String creditItem) {
		this.creditItem = creditItem;
	}

	public java.lang.String getCreditItemVal() {
		return this.creditItemVal;
	}
	
	public void setCreditItemVal(java.lang.String value) {
		this.creditItemVal = value;
	}
	public java.lang.Long getCreditAmt() {
		return this.creditAmt;
	}
	
	public void setCreditAmt(java.lang.Long value) {
		this.creditAmt = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("CreditId",getCreditId())
			.append("CreditRecId",getCreditRecId())
			.append("CreditItem",getCreditItem())
			.append("CreditItemVal",getCreditItemVal())
			.append("CreditAmt",getCreditAmt())
			.append("Remark",getRemark())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCreditRecId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanCreditRecDo == false) return false;
		if(this == obj) return true;
		LoanCreditRecDo other = (LoanCreditRecDo)obj;
		return new EqualsBuilder()
			.append(getCreditRecId(),other.getCreditRecId())
			.isEquals();
	}
}

