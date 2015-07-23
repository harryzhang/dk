/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.common.activity.dataobject;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class ActivityAuthDo  implements java.io.Serializable{	
	
	//columns START
	private Long authId;
	private Long ordId;
	private Long fromUserId;
	private Long toUserId;
	private Double authAmount;
	private Integer authType;
	private java.util.Date authTime;
	private Integer authStatus;
	private String remark;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	//columns END
	public Long getAuthId() {
		return this.authId;
	}
	
	public void setAuthId(Long value) {
		this.authId = value;
	}
	public Long getOrdId() {
		return this.ordId;
	}
	
	public void setOrdId(Long value) {
		this.ordId = value;
	}
	public Long getFromUserId() {
		return this.fromUserId;
	}
	
	public void setFromUserId(Long value) {
		this.fromUserId = value;
	}
	public Long getToUserId() {
		return this.toUserId;
	}
	
	public void setToUserId(Long value) {
		this.toUserId = value;
	}
	public Double getAuthAmount() {
		return this.authAmount;
	}
	
	public void setAuthAmount(Double value) {
		this.authAmount = value;
	}
	public Integer getAuthType() {
		return this.authType;
	}
	
	public void setAuthType(Integer value) {
		this.authType = value;
	}
	public java.util.Date getAuthTime() {
		return this.authTime;
	}
	
	public void setAuthTime(java.util.Date value) {
		this.authTime = value;
	}
	public Integer getAuthStatus() {
		return this.authStatus;
	}
	
	public void setAuthStatus(Integer value) {
		this.authStatus = value;
	}
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String value) {
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
			.append("AuthId",getAuthId())
			.append("OrdId",getOrdId())
			.append("FromUserId",getFromUserId())
			.append("ToUserId",getToUserId())
			.append("AuthAmount",getAuthAmount())
			.append("AuthType",getAuthType())
			.append("AuthTime",getAuthTime())
			.append("AuthStatus",getAuthStatus())
			.append("Remark",getRemark())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getAuthId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ActivityAuthDo == false) return false;
		if(this == obj) return true;
		ActivityAuthDo other = (ActivityAuthDo)obj;
		return new EqualsBuilder()
			.append(getAuthId(),other.getAuthId())
			.isEquals();
	}
}

