package com.hehenian.biz.common.loan.dataobject;

import java.math.BigDecimal;
import java.util.Date;

public class FundProductDo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Long productId;
	private Long loanId;
	private Long userId;
	private String productName;
	private BigDecimal investAmount;
	private BigDecimal investedAmount;
	private String productUsage;
	private Short loanPeriod;
	private BigDecimal annualRate;
	private Short repayPeriod;
	private Byte repayType;
	private Short tenderDay;
	private String remark;
	private Byte productStatus;
	private Byte loanType;
	private Byte securityType;
	private String binLevel;
	private Date createTime;
	private Date updateTime;
	private Date publishTime;
	private BigDecimal loanRate;
	private Integer locked;
	private String withdrawFlag;
	private BigDecimal withdrawAmount;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getInvestAmount() {
		return investAmount;
	}
	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}
	public BigDecimal getInvestedAmount() {
		return investedAmount;
	}
	public void setInvestedAmount(BigDecimal investedAmount) {
		this.investedAmount = investedAmount;
	}
	public String getProductUsage() {
		return productUsage;
	}
	public void setProductUsage(String productUsage) {
		this.productUsage = productUsage;
	}
	public Short getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(Short loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public BigDecimal getAnnualRate() {
		return annualRate;
	}
	public void setAnnualRate(BigDecimal annualRate) {
		this.annualRate = annualRate;
	}
	public Short getRepayPeriod() {
		return repayPeriod;
	}
	public void setRepayPeriod(Short repayPeriod) {
		this.repayPeriod = repayPeriod;
	}
	public Byte getRepayType() {
		return repayType;
	}
	public void setRepayType(Byte repayType) {
		this.repayType = repayType;
	}
	public Short getTenderDay() {
		return tenderDay;
	}
	public void setTenderDay(Short tenderDay) {
		this.tenderDay = tenderDay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Byte getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(Byte productStatus) {
		this.productStatus = productStatus;
	}
	public Byte getLoanType() {
		return loanType;
	}
	public void setLoanType(Byte loanType) {
		this.loanType = loanType;
	}
	public Byte getSecurityType() {
		return securityType;
	}
	public void setSecurityType(Byte securityType) {
		this.securityType = securityType;
	}
	public String getBinLevel() {
		return binLevel;
	}
	public void setBinLevel(String binLevel) {
		this.binLevel = binLevel;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public BigDecimal getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public String getWithdrawFlag() {
		return withdrawFlag;
	}
	public void setWithdrawFlag(String withdrawFlag) {
		this.withdrawFlag = withdrawFlag;
	}
	public BigDecimal getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(BigDecimal withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
}

