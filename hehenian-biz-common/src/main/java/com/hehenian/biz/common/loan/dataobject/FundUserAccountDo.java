package com.hehenian.biz.common.loan.dataobject;

// Generated 2014-12-2 15:40:14 by Hibernate Tools 3.4.0.CR1


import java.math.BigDecimal;
import java.util.Date;

public class FundUserAccountDo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long userAccountId;
	private Long userId;
	private BigDecimal balanceAmount;
	private BigDecimal freezeAmount;
	private BigDecimal investAmount;
	private BigDecimal totalInvestAmount;
	private BigDecimal loanAmount;
	private BigDecimal totalLoanAmount;
	private BigDecimal totalIncome;
	private BigDecimal totalRechargeAmount;
	private BigDecimal totalWithdrawAmount;
	private String tradePassword;
	private Date updateTime;
	private Date createTime;
	public Long getUserAccountId() {
		return userAccountId;
	}
	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public BigDecimal getFreezeAmount() {
		return freezeAmount;
	}
	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}
	public BigDecimal getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}
	public BigDecimal getTotalInvestAmount() {
		return totalInvestAmount;
	}
	public void setTotalInvestAmount(BigDecimal totalInvestAmount) {
		this.totalInvestAmount = totalInvestAmount;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public BigDecimal getTotalLoanAmount() {
		return totalLoanAmount;
	}
	public void setTotalLoanAmount(BigDecimal totalLoanAmount) {
		this.totalLoanAmount = totalLoanAmount;
	}
	public BigDecimal getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}
	public BigDecimal getTotalRechargeAmount() {
		return totalRechargeAmount;
	}
	public void setTotalRechargeAmount(BigDecimal totalRechargeAmount) {
		this.totalRechargeAmount = totalRechargeAmount;
	}
	public BigDecimal getTotalWithdrawAmount() {
		return totalWithdrawAmount;
	}
	public void setTotalWithdrawAmount(BigDecimal totalWithdrawAmount) {
		this.totalWithdrawAmount = totalWithdrawAmount;
	}
	public String getTradePassword() {
		return tradePassword;
	}
	public void setTradePassword(String tradePassword) {
		this.tradePassword = tradePassword;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
