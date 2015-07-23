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



public class LoanCheckItemDo  implements java.io.Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2692079790549437688L;
	//columns START
	private java.lang.Long checkId;
	private java.lang.String checkType;
	private java.lang.String checkItemName;
	private java.lang.String checkItemCode;
	private java.lang.String status;
	private java.lang.Integer version;
	private java.lang.String remark;
	private java.util.Date createtime;
	private java.util.Date updatetime;
	private java.lang.Long createby;
	//columns END
	public java.lang.Long getCheckId() {
		return this.checkId;
	}
	
	public void setCheckId(java.lang.Long value) {
		this.checkId = value;
	}
	public java.lang.String getCheckType() {
		return this.checkType;
	}
	
	public void setCheckType(java.lang.String value) {
		this.checkType = value;
	}
	public java.lang.String getCheckItemName() {
		return this.checkItemName;
	}
	
	public void setCheckItemName(java.lang.String value) {
		this.checkItemName = value;
	}
	public java.lang.String getCheckItemCode() {
		return this.checkItemCode;
	}
	
	public void setCheckItemCode(java.lang.String value) {
		this.checkItemCode = value;
	}
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	public java.lang.Integer getVersion() {
		return this.version;
	}
	
	public void setVersion(java.lang.Integer value) {
		this.version = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	public void setCreatetime(java.util.Date value) {
		this.createtime = value;
	}
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	public void setUpdatetime(java.util.Date value) {
		this.updatetime = value;
	}
	public java.lang.Long getCreateby() {
		return this.createby;
	}
	
	public void setCreateby(java.lang.Long value) {
		this.createby = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("CheckId",getCheckId())
			.append("CheckType",getCheckType())
			.append("CheckItemName",getCheckItemName())
			.append("CheckItemCode",getCheckItemCode())
			.append("Status",getStatus())
			.append("Version",getVersion())
			.append("Remark",getRemark())
			.append("Createtime",getCreatetime())
			.append("Updatetime",getUpdatetime())
			.append("Createby",getCreateby())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCheckId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanCheckItemDo == false) return false;
		if(this == obj) return true;
		LoanCheckItemDo other = (LoanCheckItemDo)obj;
		return new EqualsBuilder()
			.append(getCheckId(),other.getCheckId())
			.isEquals();
	}
}

