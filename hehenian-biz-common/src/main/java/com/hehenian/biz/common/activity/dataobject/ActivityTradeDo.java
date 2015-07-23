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



public class ActivityTradeDo  implements java.io.Serializable{	
	
	//columns START
	private Long tradeId;
	private Long ordId;
	private Long fromUserId;
	private Long toUserId;
	private Double amount;
	private Double realAmount;
	private java.util.Date tradeTime;
	private java.util.Date realTradeTime;
	private Integer tradeStatus;
	private String remark;
	private java.util.Date createTime;
	private java.util.Date updateTime;
	//columns END
	public Long getTradeId() {
		return this.tradeId;
	}
	
	public void setTradeId(Long value) {
		this.tradeId = value;
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
	public Double getAmount() {
		return this.amount;
	}
	
	public void setAmount(Double value) {
		this.amount = value;
	}
	public Double getRealAmount() {
		return this.realAmount;
	}
	
	public void setRealAmount(Double value) {
		this.realAmount = value;
	}
	public java.util.Date getTradeTime() {
		return this.tradeTime;
	}
	
	public void setTradeTime(java.util.Date value) {
		this.tradeTime = value;
	}
	public java.util.Date getRealTradeTime() {
		return this.realTradeTime;
	}
	
	public void setRealTradeTime(java.util.Date value) {
		this.realTradeTime = value;
	}
	public Integer getTradeStatus() {
		return this.tradeStatus;
	}
	
	public void setTradeStatus(Integer value) {
		this.tradeStatus = value;
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
			.append("TradeId",getTradeId())
			.append("OrdId",getOrdId())
			.append("FromUserId",getFromUserId())
			.append("ToUserId",getToUserId())
			.append("Amount",getAmount())
			.append("RealAmount",getRealAmount())
			.append("TradeTime",getTradeTime())
			.append("RealTradeTime",getRealTradeTime())
			.append("TradeStatus",getTradeStatus())
			.append("Remark",getRemark())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getTradeId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ActivityTradeDo == false) return false;
		if(this == obj) return true;
		ActivityTradeDo other = (ActivityTradeDo)obj;
		return new EqualsBuilder()
			.append(getTradeId(),other.getTradeId())
			.isEquals();
	}
}

