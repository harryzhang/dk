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



public class LoanRepaymentDo  implements java.io.Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4389810980709978824L;
	//columns START
	private java.lang.Long repaymentId;
	private java.lang.Long loanId;
	private java.lang.Integer repayPeriod;
	private Double stillPrincipal;
	private Double stillInterest;
	private Double lateFi;
	private Double realPrincipal;
	private Double realInterest;
	private Double realFi;
	private Double principalBalance;
	private Double interestBalance;
	private java.util.Date repayDate;
	private java.util.Date realRepayDate;
	private java.lang.Integer lateDay;
	private java.lang.Integer repayStatus = 0; //还款状态（0 默认未偿还 1已偿还）
	private java.lang.Integer repayStyle = 1; //还款方式(1:默认正常还款，2：代偿，3：提前结清)
	private java.util.Date createDate;
	private java.util.Date lastUpdateDate;
	
	private String            orderCode;			//订单号
	private Double 			  stillRepayAll;        //应还款总额
	private Double            realRepayAll;         //实际还款总额
//	private Double            earlySettlementFee;   //提前结清手续费
	
	public java.lang.Long getRepaymentId() {
		return repaymentId;
	}

	public void setRepaymentId(java.lang.Long repaymentId) {
		this.repaymentId = repaymentId;
	}

	public java.lang.Long getLoanId() {
		return loanId;
	}

	public void setLoanId(java.lang.Long loanId) {
		this.loanId = loanId;
	}

	public java.lang.Integer getRepayPeriod() {
		return repayPeriod;
	}

	public void setRepayPeriod(java.lang.Integer repayPeriod) {
		this.repayPeriod = repayPeriod;
	}

	public Double getStillPrincipal() {
		return stillPrincipal;
	}

	public void setStillPrincipal(Double stillPrincipal) {
		this.stillPrincipal = stillPrincipal;
	}

	public Double getStillInterest() {
		return stillInterest;
	}

	public void setStillInterest(Double stillInterest) {
		this.stillInterest = stillInterest;
	}

	public Double getLateFi() {
		return lateFi;
	}

	public void setLateFi(Double lateFi) {
		this.lateFi = lateFi;
	}

	public Double getRealPrincipal() {
		return realPrincipal;
	}

	public void setRealPrincipal(Double realPrincipal) {
		this.realPrincipal = realPrincipal;
	}

	public Double getRealInterest() {
		return realInterest;
	}

	public void setRealInterest(Double realInterest) {
		this.realInterest = realInterest;
	}

	public Double getRealFi() {
		return realFi;
	}

	public void setRealFi(Double realFi) {
		this.realFi = realFi;
	}

	public Double getPrincipalBalance() {
		return principalBalance;
	}

	public void setPrincipalBalance(Double principalBalance) {
		this.principalBalance = principalBalance;
	}

	public Double getInterestBalance() {
		return interestBalance;
	}

	public void setInterestBalance(Double interestBalance) {
		this.interestBalance = interestBalance;
	}

	public java.util.Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(java.util.Date repayDate) {
		this.repayDate = repayDate;
	}

	public java.util.Date getRealRepayDate() {
		return realRepayDate;
	}

	public void setRealRepayDate(java.util.Date realRepayDate) {
		this.realRepayDate = realRepayDate;
	}

	public java.lang.Integer getLateDay() {
		return lateDay;
	}

	public void setLateDay(java.lang.Integer lateDay) {
		this.lateDay = lateDay;
	}

	public java.lang.Integer getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(java.lang.Integer repayStatus) {
		this.repayStatus = repayStatus;
	}

	public java.lang.Integer getRepayStyle() {
		return repayStyle;
	}

	public void setRepayStyle(java.lang.Integer repayStyle) {
		this.repayStyle = repayStyle;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.util.Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(java.util.Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	public Double getStillRepayAll() {
		return stillRepayAll;
	}

	public void setStillRepayAll(Double stillRepayAll) {
		this.stillRepayAll = stillRepayAll;
	}

	public Double getRealRepayAll() {
		return realRepayAll;
	}

	public void setRealRepayAll(Double realRepayAll) {
		this.realRepayAll = realRepayAll;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("RepaymentId",getRepaymentId())
			.append("LoanId",getLoanId())
			.append("RepayPeriod",getRepayPeriod())
			.append("StillPrincipal",getStillPrincipal())
			.append("StillInterest",getStillInterest())
			.append("LateFi",getLateFi())
			.append("RealPrincipal",getRealPrincipal())
			.append("RealInterest",getRealInterest())
			.append("RealFi",getRealFi())
			.append("PrincipalBalance",getPrincipalBalance())
			.append("InterestBalance",getInterestBalance())
			.append("RepayDate",getRepayDate())
			.append("RealRepayDate",getRealRepayDate())
			.append("LateDay",getLateDay())
			.append("RepayStatus",getRepayStatus())
			.append("RepayStyle",getRepayStyle())
			.append("CreateDate",getCreateDate())
			.append("LastUpdateDate",getLastUpdateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRepaymentId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoanRepaymentDo == false) return false;
		if(this == obj) return true;
		LoanRepaymentDo other = (LoanRepaymentDo)obj;
		return new EqualsBuilder()
			.append(getRepaymentId(),other.getRepaymentId())
			.isEquals();
	}
}

