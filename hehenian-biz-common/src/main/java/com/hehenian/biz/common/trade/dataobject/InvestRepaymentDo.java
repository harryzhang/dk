package com.hehenian.biz.common.trade.dataobject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.util.CalculateUtils;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

public class InvestRepaymentDo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    // columns START
    private java.lang.Long    id;
    private java.lang.Long    repayId;
    private java.lang.String  repayPeriod;
    private java.util.Date    repayDate;
    private java.util.Date    realRepayDate;
    private Double            recivedPrincipal;
    private Double            recivedInterest;
    private Double            hasPrincipal;
    private Double            hasInterest;
    private java.lang.Long    interestOwner;
    private Double            recivedFi;
    private java.lang.Integer isLate;
    private java.lang.Integer lateDay;
    private java.lang.Integer isWebRepay;
    private Double            principalBalance;
    private Double            interestBalance;
    private java.lang.Long    investId;
    private java.lang.Long    owner;
    private java.lang.String  ownerlist;
    private java.lang.Integer repayStatus;
    private Double            imanageFee;
    private Double            imanageFeeRate;
    private java.lang.Integer isDebt;
    private java.lang.Long    borrowId;
    private java.lang.Integer circulationForpayStatus;
    private Long              parentId;
    
    /**
     * 回款上invest_id对应的投资对象
     */
    private InvestDo investDo = null;
    
    /**
     * owner 对应的用户对象
     */
    private AccountUserDo ownerUser = null;
    
    /**
     * 应收总金额
     * @return
     */
    public double getRecivedTotalAmount(){

        double ramount = CalculateUtils.add(this.recivedInterest == null ? 0 : this.recivedInterest,
                this.recivedPrincipal == null ? 0 : this.recivedPrincipal);
        return CalculateUtils.add(ramount, this.recivedFi == null ? 0 : this.recivedFi);
    }

    /**
     * 应收本金和利息
     * @return
     */
    public double getRecivedPI(){
        double ramount = this.recivedInterest + this.recivedPrincipal;
        return CalculateUtils.round(ramount, 2);
    }
    
    // columns END
    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long value) {
        this.id = value;
    }

    public java.lang.Long getRepayId() {
        return this.repayId;
    }

    public void setRepayId(java.lang.Long value) {
        this.repayId = value;
    }

    public java.lang.String getRepayPeriod() {
        return this.repayPeriod;
    }

    public void setRepayPeriod(java.lang.String value) {
        this.repayPeriod = value;
    }

    public java.util.Date getRepayDate() {
        return this.repayDate;
    }

    public void setRepayDate(java.util.Date value) {
        this.repayDate = value;
    }

    public java.util.Date getRealRepayDate() {
        return this.realRepayDate;
    }

    public void setRealRepayDate(java.util.Date value) {
        this.realRepayDate = value;
    }

    public Double getRecivedPrincipal() {
        return this.recivedPrincipal;
    }

    public void setRecivedPrincipal(Double value) {
        this.recivedPrincipal = value;
    }

    public Double getRecivedInterest() {
        return this.recivedInterest;
    }

    public void setRecivedInterest(Double value) {
        this.recivedInterest = value;
    }

    public Double getHasPrincipal() {
        return this.hasPrincipal;
    }

    public void setHasPrincipal(Double value) {
        this.hasPrincipal = value;
    }

    public Double getHasInterest() {
        return this.hasInterest;
    }

    public void setHasInterest(Double value) {
        this.hasInterest = value;
    }

    public java.lang.Long getInterestOwner() {
        return interestOwner;
    }

    public void setInterestOwner(java.lang.Long interestOwner) {
        this.interestOwner = interestOwner;
    }

    public Double getRecivedFi() {
        return this.recivedFi;
    }

    public void setRecivedFi(Double value) {
        this.recivedFi = value;
    }

    public java.lang.Integer getIsLate() {
        return this.isLate;
    }

    public void setIsLate(java.lang.Integer value) {
        this.isLate = value;
    }

    public java.lang.Integer getLateDay() {
        return this.lateDay;
    }

    public void setLateDay(java.lang.Integer value) {
        this.lateDay = value;
    }

    public java.lang.Integer getIsWebRepay() {
        return this.isWebRepay;
    }

    public void setIsWebRepay(java.lang.Integer value) {
        this.isWebRepay = value;
    }

    public Double getPrincipalBalance() {
        return this.principalBalance;
    }

    public void setPrincipalBalance(Double value) {
        this.principalBalance = value;
    }

    public Double getInterestBalance() {
        return this.interestBalance;
    }

    public void setInterestBalance(Double value) {
        this.interestBalance = value;
    }

    public java.lang.Long getInvestId() {
        return this.investId;
    }

    public void setInvestId(java.lang.Long value) {
        this.investId = value;
    }

    public java.lang.Long getOwner() {
        return this.owner;
    }

    public void setOwner(java.lang.Long value) {
        this.owner = value;
    }

    public java.lang.String getOwnerlist() {
        return this.ownerlist;
    }

    public void setOwnerlist(java.lang.String value) {
        this.ownerlist = value;
    }

    public java.lang.Integer getRepayStatus() {
        return this.repayStatus;
    }

    public void setRepayStatus(java.lang.Integer value) {
        this.repayStatus = value;
    }

    public Double getImanageFee() {
        return this.imanageFee;
    }

    public void setImanageFee(Double value) {
        this.imanageFee = value;
    }

    public Double getImanageFeeRate() {
        return this.imanageFeeRate;
    }

    public void setImanageFeeRate(Double value) {
        this.imanageFeeRate = value;
    }

    public java.lang.Integer getIsDebt() {
        return this.isDebt;
    }

    public void setIsDebt(java.lang.Integer value) {
        this.isDebt = value;
    }

    public java.lang.Long getBorrowId() {
        return this.borrowId;
    }

    public void setBorrowId(java.lang.Long value) {
        this.borrowId = value;
    }

    public java.lang.Integer getCirculationForpayStatus() {
        return this.circulationForpayStatus;
    }

    public void setCirculationForpayStatus(java.lang.Integer value) {
        this.circulationForpayStatus = value;
    }

    public InvestDo getInvestDo() {
		return investDo;
	}

	public void setInvestDo(InvestDo investDo) {
		this.investDo = investDo;
	}

	public AccountUserDo getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(AccountUserDo ownerUser) {
		this.ownerUser = ownerUser;
	}

	/**
     * @return parentId
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId())
                .append("RepayId", getRepayId()).append("RepayPeriod", getRepayPeriod())
                .append("RepayDate", getRepayDate()).append("RealRepayDate", getRealRepayDate())
                .append("RecivedPrincipal", getRecivedPrincipal()).append("RecivedInterest", getRecivedInterest())
                .append("HasPrincipal", getHasPrincipal()).append("HasInterest", getHasInterest())
                .append("InterestOwner", getInterestOwner()).append("RecivedFi", getRecivedFi())
                .append("IsLate", getIsLate()).append("LateDay", getLateDay()).append("IsWebRepay", getIsWebRepay())
                .append("PrincipalBalance", getPrincipalBalance()).append("InterestBalance", getInterestBalance())
                .append("InvestId", getInvestId()).append("Owner", getOwner()).append("Ownerlist", getOwnerlist())
                .append("RepayStatus", getRepayStatus()).append("ImanageFee", getImanageFee())
                .append("ImanageFeeRate", getImanageFeeRate()).append("IsDebt", getIsDebt())
                .append("BorrowId", getBorrowId()).append("CirculationForpayStatus", getCirculationForpayStatus())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof InvestRepaymentDo == false)
            return false;
        if (this == obj)
            return true;
        InvestRepaymentDo other = (InvestRepaymentDo) obj;
        return new EqualsBuilder().append(getId(), other.getId()).isEquals();
    }
}
