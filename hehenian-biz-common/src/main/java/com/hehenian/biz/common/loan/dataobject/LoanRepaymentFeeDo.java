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



public class LoanRepaymentFeeDo  implements java.io.Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3959423154443899532L;
	//columns START
	private java.lang.Long feeId;
	private java.lang.Long loanId;
	private java.lang.Long repaymentId;
	private Double feeAmount;
	private String feeType ;
	private String feeName;
	
	private String orderCode;
	private Integer repayType = 0; //状态：0-计划，1-实际
	private Double  earlySettlementFee;   //提前结清手续费
	//columns END
	public java.lang.Long getFeeId() {
		return this.feeId;
	}
	
	public void setFeeId(java.lang.Long value) {
		this.feeId = value;
	}
	public java.lang.Long getLoanId() {
		return this.loanId;
	}
	
	public void setLoanId(java.lang.Long value) {
		this.loanId = value;
	}
	public java.lang.Long getRepaymentId() {
		return this.repaymentId;
	}
	
	public void setRepaymentId(java.lang.Long value) {
		this.repaymentId = value;
	}
	
	public Double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getRepayType() {
		return repayType;
	}

	public void setRepayType(Integer repayType) {
		this.repayType = repayType;
	}

	public Double getEarlySettlementFee() {
		return earlySettlementFee;
	}

	public void setEarlySettlementFee(Double earlySettlementFee) {
		this.earlySettlementFee = earlySettlementFee;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("FeeId",getFeeId())
			.append("LoanId",getLoanId())
			.append("RepaymentId",getRepaymentId())
			.append("FeeAmount",getFeeAmount())
			.append("feeType",getFeeType())
			.append("feeName",getFeeName())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getFeeId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanRepaymentFeeDo == false) return false;
		if(this == obj) return true;
		LoanRepaymentFeeDo other = (LoanRepaymentFeeDo)obj;
		return new EqualsBuilder()
			.append(getFeeId(),other.getFeeId())
			.isEquals();
	}
}

