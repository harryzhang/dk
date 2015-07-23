/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.common.trade.dataobject;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class RepaymentRecordDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long id;
	private java.lang.Long repayId;
	private java.lang.Double repayAmount;
	private java.lang.Long oporator;
	private java.lang.String remark;
	private java.util.Date createTime;
	private java.lang.String oporatorIp;
	private java.lang.String repayType;
	private java.lang.String processStatus;
	//columns END
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.Long getRepayId() {
		return this.repayId;
	}
	
	public void setRepayId(java.lang.Long value) {
		this.repayId = value;
	}
	public java.lang.Double getRepayAmount() {
		return this.repayAmount;
	}
	
	public void setRepayAmount(java.lang.Double value) {
		this.repayAmount = value;
	}
	public java.lang.Long getOporator() {
		return this.oporator;
	}
	
	public void setOporator(java.lang.Long value) {
		this.oporator = value;
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
	public java.lang.String getOporatorIp() {
		return this.oporatorIp;
	}
	
	public void setOporatorIp(java.lang.String value) {
		this.oporatorIp = value;
	}
	public java.lang.String getRepayType() {
		return this.repayType;
	}
	
	public void setRepayType(java.lang.String value) {
		this.repayType = value;
	}
	public java.lang.String getProcessStatus() {
		return this.processStatus;
	}
	
	public void setProcessStatus(java.lang.String value) {
		this.processStatus = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("RepayId",getRepayId())
			.append("RepayAmount",getRepayAmount())
			.append("Oporator",getOporator())
			.append("Remark",getRemark())
			.append("CreateTime",getCreateTime())
			.append("OporatorIp",getOporatorIp())
			.append("RepayType",getRepayType())
			.append("ProcessStatus",getProcessStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RepaymentRecordDo == false) return false;
		if(this == obj) return true;
		RepaymentRecordDo other = (RepaymentRecordDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

