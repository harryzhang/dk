package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DepositLoanDetailDo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long loanId;
	private Long userId;
	private String loanTitle;
	private BigDecimal loanAmount;
	private String loanUsage;
	private Short loanPeriod;
	private BigDecimal annualRate;
	private Short repayPeriod;
	private Byte repayType;
	private Short tenderDay;
	private String loanDesc;
	private Byte loanStatus;
	private Byte borrowerType;
	private Date createTime;
	private Date updateTime;
	private String remark;
	private BigDecimal loanRate;
	private String businessNum;
	private String issuerBrunch;
	private String department;
	
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
	public String getLoanTitle() {
		return loanTitle;
	}
	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanUsage() {
		return loanUsage;
	}
	public void setLoanUsage(String loanUsage) {
		this.loanUsage = loanUsage;
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
	public String getLoanDesc() {
		return loanDesc;
	}
	public void setLoanDesc(String loanDesc) {
		this.loanDesc = loanDesc;
	}
	public Byte getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(Byte loanStatus) {
		this.loanStatus = loanStatus;
	}
	public Byte getBorrowerType() {
		return borrowerType;
	}
	public void setBorrowerType(Byte borrowerType) {
		this.borrowerType = borrowerType;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}
	public String getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}
	public String getIssuerBrunch() {
		return issuerBrunch;
	}
	public void setIssuerBrunch(String issuerBrunch) {
		this.issuerBrunch = issuerBrunch;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
}
