/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
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

public class CapitalAccountDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long accountId;
	private java.lang.Double rechargeMoney;
	private java.lang.Double investAmount;
	private java.lang.Double tradeAmount;
	private java.lang.Double hbBuyMoney;
	private java.lang.Double jxbBuyMoney;
	private java.lang.Double jzbBuyMoney;
	private java.lang.Double platformInvestAmount;
	private java.lang.Double platformTradeAmount;
	private java.lang.Double borrowAmount;
	private java.lang.Double withdrawal;
	private java.lang.Double tlAvailableMoney;
	private java.lang.Double hfAvailableMoney;
	private java.util.Date createTime;
	//columns END
	public java.lang.Long getAccountId() {
		return this.accountId;
	}
	
	public void setAccountId(java.lang.Long value) {
		this.accountId = value;
	}
	public java.lang.Double getRechargeMoney() {
		return this.rechargeMoney;
	}
	
	public void setRechargeMoney(java.lang.Double value) {
		this.rechargeMoney = value;
	}
	public java.lang.Double getInvestAmount() {
		return this.investAmount;
	}
	
	public void setInvestAmount(java.lang.Double value) {
		this.investAmount = value;
	}
	public java.lang.Double getTradeAmount() {
		return this.tradeAmount;
	}
	
	public void setTradeAmount(java.lang.Double value) {
		this.tradeAmount = value;
	}
	public java.lang.Double getPlatformInvestAmount() {
		return this.platformInvestAmount;
	}
	
	public void setPlatformInvestAmount(java.lang.Double value) {
		this.platformInvestAmount = value;
	}
	public java.lang.Double getPlatformTradeAmount() {
		return this.platformTradeAmount;
	}
	
	public void setPlatformTradeAmount(java.lang.Double value) {
		this.platformTradeAmount = value;
	}
	public java.lang.Double getBorrowAmount() {
		return this.borrowAmount;
	}
	
	public void setBorrowAmount(java.lang.Double value) {
		this.borrowAmount = value;
	}
	public java.lang.Double getWithdrawal() {
		return this.withdrawal;
	}
	
	public void setWithdrawal(java.lang.Double value) {
		this.withdrawal = value;
	}
	public java.lang.Double getTlAvailableMoney() {
		return this.tlAvailableMoney;
	}
	
	public void setTlAvailableMoney(java.lang.Double value) {
		this.tlAvailableMoney = value;
	}
	public java.lang.Double getHfAvailableMoney() {
		return this.hfAvailableMoney;
	}
	
	public void setHfAvailableMoney(java.lang.Double value) {
		this.hfAvailableMoney = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("AccountId",getAccountId())
			.append("RechargeMoney",getRechargeMoney())
			.append("InvestAmount",getInvestAmount())
			.append("TradeAmount",getTradeAmount())
			.append("PlatformInvestAmount",getPlatformInvestAmount())
			.append("PlatformTradeAmount",getPlatformTradeAmount())
			.append("BorrowAmount",getBorrowAmount())
			.append("Withdrawal",getWithdrawal())
			.append("TlAvailableMoney",getTlAvailableMoney())
			.append("HfAvailableMoney",getHfAvailableMoney())
			.append("CreateTime",getCreateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getAccountId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CapitalAccountDo == false) return false;
		if(this == obj) return true;
		CapitalAccountDo other = (CapitalAccountDo)obj;
		return new EqualsBuilder()
			.append(getAccountId(),other.getAccountId())
			.isEquals();
	}

	public java.lang.Double getHbBuyMoney() {
		return hbBuyMoney;
	}

	public void setHbBuyMoney(java.lang.Double hbBuyMoney) {
		this.hbBuyMoney = hbBuyMoney;
	}

	public java.lang.Double getJxbBuyMoney() {
		return jxbBuyMoney;
	}

	public void setJxbBuyMoney(java.lang.Double jxbBuyMoney) {
		this.jxbBuyMoney = jxbBuyMoney;
	}

	public java.lang.Double getJzbBuyMoney() {
		return jzbBuyMoney;
	}

	public void setJzbBuyMoney(java.lang.Double jzbBuyMoney) {
		this.jzbBuyMoney = jzbBuyMoney;
	}
}

