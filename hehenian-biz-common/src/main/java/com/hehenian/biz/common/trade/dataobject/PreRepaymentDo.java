/*
 * Powered By liuzgmf
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.common.trade.dataobject;

/**
 * 
 * 
 * @author: liuzgmf
 * @date 2014年9月24日 上午11:04:18
 */
public class PreRepaymentDo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private java.lang.Long    id;
    private java.lang.String  repayPeriod;
    private java.lang.String  repayDate;
    private java.lang.Double  stillPrincipal;
    private java.lang.Double  stillInterest;
    private java.lang.Double  principalBalance;
    private java.lang.Double  interestBalance;
    private java.lang.Double  consultFee;
    private java.lang.Double  mrate;
    private java.lang.Double  totalSum;
    private java.lang.Double  totalAmount;
    private java.lang.Long    borrowId;
    private java.lang.String  identify;
    private java.lang.Integer sort;
    private java.lang.Double  repayFee;

    /**
     * @return id
     */
    public java.lang.Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(java.lang.Long id) {
        this.id = id;
    }

    /**
     * @return repayPeriod
     */
    public java.lang.String getRepayPeriod() {
        return repayPeriod;
    }

    /**
     * @param repayPeriod
     *            the repayPeriod to set
     */
    public void setRepayPeriod(java.lang.String repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    /**
     * @return repayDate
     */
    public java.lang.String getRepayDate() {
        return repayDate;
    }

    /**
     * @param repayDate
     *            the repayDate to set
     */
    public void setRepayDate(java.lang.String repayDate) {
        this.repayDate = repayDate;
    }

    /**
     * @return stillPrincipal
     */
    public java.lang.Double getStillPrincipal() {
        return stillPrincipal;
    }

    /**
     * @param stillPrincipal
     *            the stillPrincipal to set
     */
    public void setStillPrincipal(java.lang.Double stillPrincipal) {
        this.stillPrincipal = stillPrincipal;
    }

    /**
     * @return stillInterest
     */
    public java.lang.Double getStillInterest() {
        return stillInterest;
    }

    /**
     * @param stillInterest
     *            the stillInterest to set
     */
    public void setStillInterest(java.lang.Double stillInterest) {
        this.stillInterest = stillInterest;
    }

    /**
     * @return principalBalance
     */
    public java.lang.Double getPrincipalBalance() {
        return principalBalance;
    }

    /**
     * @param principalBalance
     *            the principalBalance to set
     */
    public void setPrincipalBalance(java.lang.Double principalBalance) {
        this.principalBalance = principalBalance;
    }

    /**
     * @return interestBalance
     */
    public java.lang.Double getInterestBalance() {
        return interestBalance;
    }

    /**
     * @param interestBalance
     *            the interestBalance to set
     */
    public void setInterestBalance(java.lang.Double interestBalance) {
        this.interestBalance = interestBalance;
    }

    /**
     * @return consultFee
     */
    public java.lang.Double getConsultFee() {
        return consultFee;
    }

    /**
     * @param consultFee
     *            the consultFee to set
     */
    public void setConsultFee(java.lang.Double consultFee) {
        this.consultFee = consultFee;
    }

    /**
     * @return mrate
     */
    public java.lang.Double getMrate() {
        return mrate;
    }

    /**
     * @param mrate
     *            the mrate to set
     */
    public void setMrate(java.lang.Double mrate) {
        this.mrate = mrate;
    }

    /**
     * @return totalSum
     */
    public java.lang.Double getTotalSum() {
        return totalSum;
    }

    /**
     * @param totalSum
     *            the totalSum to set
     */
    public void setTotalSum(java.lang.Double totalSum) {
        this.totalSum = totalSum;
    }

    /**
     * @return totalAmount
     */
    public java.lang.Double getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount
     *            the totalAmount to set
     */
    public void setTotalAmount(java.lang.Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return borrowId
     */
    public java.lang.Long getBorrowId() {
        return borrowId;
    }

    /**
     * @param borrowId
     *            the borrowId to set
     */
    public void setBorrowId(java.lang.Long borrowId) {
        this.borrowId = borrowId;
    }

    /**
     * @return identify
     */
    public java.lang.String getIdentify() {
        return identify;
    }

    /**
     * @param identify
     *            the identify to set
     */
    public void setIdentify(java.lang.String identify) {
        this.identify = identify;
    }

    /**
     * @return sort
     */
    public java.lang.Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     *            the sort to set
     */
    public void setSort(java.lang.Integer sort) {
        this.sort = sort;
    }

    /**
     * @return repayFee
     */
    public java.lang.Double getRepayFee() {
        return repayFee;
    }

    /**
     * @param repayFee
     *            the repayFee to set
     */
    public void setRepayFee(java.lang.Double repayFee) {
        this.repayFee = repayFee;
    }

}
