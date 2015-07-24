/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.common.bank.dataobject;



import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class BankBingDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long id;
	private java.lang.Long userId;
	private java.lang.String bankCode;
    private BigDecimal       amount;
	private java.lang.String sendFlag;
    private java.lang.Short  checkNumber;
	private java.lang.String checkFlag;
	private java.util.Date sendTime;
	private java.util.Date checkTime;
	private java.lang.String recordStatus;
	private java.lang.String businessRecordId;
	//columns END
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	public java.lang.String getBankCode() {
		return this.bankCode;
	}
	
	public void setBankCode(java.lang.String value) {
		this.bankCode = value;
	}
	
    /**
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public java.lang.String getSendFlag() {
		return this.sendFlag;
	}
	
	public void setSendFlag(java.lang.String value) {
		this.sendFlag = value;
	}
	
    /**
     * @return checkNumber
     */
    public java.lang.Short getCheckNumber() {
        return checkNumber;
    }

    /**
     * @param checkNumber
     *            the checkNumber to set
     */
    public void setCheckNumber(java.lang.Short checkNumber) {
        this.checkNumber = checkNumber;
    }

    public java.lang.String getCheckFlag() {
		return this.checkFlag;
	}
	
	public void setCheckFlag(java.lang.String value) {
		this.checkFlag = value;
	}
	public java.util.Date getSendTime() {
		return this.sendTime;
	}
	
	public void setSendTime(java.util.Date value) {
		this.sendTime = value;
	}
	public java.util.Date getCheckTime() {
		return this.checkTime;
	}
	
	public void setCheckTime(java.util.Date value) {
		this.checkTime = value;
	}
	public java.lang.String getRecordStatus() {
		return this.recordStatus;
	}
	
	public void setRecordStatus(java.lang.String value) {
		this.recordStatus = value;
	}
	public java.lang.String getBusinessRecordId() {
		return this.businessRecordId;
	}
	
	public void setBusinessRecordId(java.lang.String value) {
		this.businessRecordId = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("BankCode",getBankCode())
			.append("Amount",getAmount())
			.append("SendFlag",getSendFlag())
			.append("CheckNumber",getCheckNumber())
			.append("CheckFlag",getCheckFlag())
			.append("SendTime",getSendTime())
			.append("CheckTime",getCheckTime())
			.append("RecordStatus",getRecordStatus())
			.append("BusinessRecordId",getBusinessRecordId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BankBingDo == false) return false;
		if(this == obj) return true;
		BankBingDo other = (BankBingDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

