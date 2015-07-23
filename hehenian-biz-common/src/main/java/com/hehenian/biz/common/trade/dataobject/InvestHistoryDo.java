/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade.dataobject
 * @Title: InvestHistoryDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月8日 上午9:55:49
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 投资历史记录
 * 
 * @author: liuzgmf
 * @date 2014年10月8日 上午9:55:49
 */
public class InvestHistoryDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              id;                   // 编号
    private Double            investAmount;         // 投资金额
    private Double            monthRate;            // 月利率
    private Long              investor;             // 投资人
    private Long              borrowId;             // 标的ID

    private Date              investTime;           // 投资时间
    private Long              oriInvestor;          // 原始投资人
    private Double            realAmount;           // 实际投资金额
    private Double            hasPI;                // 已收本息
    private Integer           deadline;             // 期数

    private Integer           hasDeadline;          // 已还款期数
    private Double            recivedPrincipal;     // 应收本金
    private Double            recievedInterest;     // 应收利息
    private Double            hasPrincipal;         // 已收本金
    private Double            hasInterest;          // 已收本金

    private Double            recivedFI;            // 应收罚金
    private Double            hasFI;                // 已收罚金
    private Double            manageFee;            // 管理费
    private Double            reward;               // 奖励
    private Integer           repayStatus;          // 还款状态（1 默认未偿还 2 已偿还）

    private String            flag;                 // 标识
    private Integer           isAutoBid;            // 自动投标( 默认 1 手动 2 自动)
    private Integer           isDebt;               // 是否转让(1,没有转让，2转让)
    private Double            checkPrincipal;       // 校验本金
    private Double            checkInterest;        // 校验利息

    private Long              minInvestId;          // 最小投资id
    private Long              maxInvestId;          // 最大投资id
    private Double            adjustPrincipal;      // 调整本金

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return investAmount
     */
    public Double getInvestAmount() {
        return investAmount;
    }

    /**
     * @param investAmount
     *            the investAmount to set
     */
    public void setInvestAmount(Double investAmount) {
        this.investAmount = investAmount;
    }

    /**
     * @return monthRate
     */
    public Double getMonthRate() {
        return monthRate;
    }

    /**
     * @param monthRate
     *            the monthRate to set
     */
    public void setMonthRate(Double monthRate) {
        this.monthRate = monthRate;
    }

    /**
     * @return investor
     */
    public Long getInvestor() {
        return investor;
    }

    /**
     * @param investor
     *            the investor to set
     */
    public void setInvestor(Long investor) {
        this.investor = investor;
    }

    /**
     * @return borrowId
     */
    public Long getBorrowId() {
        return borrowId;
    }

    /**
     * @param borrowId
     *            the borrowId to set
     */
    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    /**
     * @return investTime
     */
    public Date getInvestTime() {
        return investTime;
    }

    /**
     * @param investTime
     *            the investTime to set
     */
    public void setInvestTime(Date investTime) {
        this.investTime = investTime;
    }

    /**
     * @return oriInvestor
     */
    public Long getOriInvestor() {
        return oriInvestor;
    }

    /**
     * @param oriInvestor
     *            the oriInvestor to set
     */
    public void setOriInvestor(Long oriInvestor) {
        this.oriInvestor = oriInvestor;
    }

    /**
     * @return realAmount
     */
    public Double getRealAmount() {
        return realAmount;
    }

    /**
     * @param realAmount
     *            the realAmount to set
     */
    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    /**
     * @return hasPI
     */
    public Double getHasPI() {
        return hasPI;
    }

    /**
     * @param hasPI
     *            the hasPI to set
     */
    public void setHasPI(Double hasPI) {
        this.hasPI = hasPI;
    }

    /**
     * @return deadline
     */
    public Integer getDeadline() {
        return deadline;
    }

    /**
     * @param deadline
     *            the deadline to set
     */
    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    /**
     * @return hasDeadline
     */
    public Integer getHasDeadline() {
        return hasDeadline;
    }

    /**
     * @param hasDeadline
     *            the hasDeadline to set
     */
    public void setHasDeadline(Integer hasDeadline) {
        this.hasDeadline = hasDeadline;
    }

    /**
     * @return recivedPrincipal
     */
    public Double getRecivedPrincipal() {
        return recivedPrincipal;
    }

    /**
     * @param recivedPrincipal
     *            the recivedPrincipal to set
     */
    public void setRecivedPrincipal(Double recivedPrincipal) {
        this.recivedPrincipal = recivedPrincipal;
    }

    /**
     * @return recievedInterest
     */
    public Double getRecievedInterest() {
        return recievedInterest;
    }

    /**
     * @param recievedInterest
     *            the recievedInterest to set
     */
    public void setRecievedInterest(Double recievedInterest) {
        this.recievedInterest = recievedInterest;
    }

    /**
     * @return hasPrincipal
     */
    public Double getHasPrincipal() {
        return hasPrincipal;
    }

    /**
     * @param hasPrincipal
     *            the hasPrincipal to set
     */
    public void setHasPrincipal(Double hasPrincipal) {
        this.hasPrincipal = hasPrincipal;
    }

    /**
     * @return hasInterest
     */
    public Double getHasInterest() {
        return hasInterest;
    }

    /**
     * @param hasInterest
     *            the hasInterest to set
     */
    public void setHasInterest(Double hasInterest) {
        this.hasInterest = hasInterest;
    }

    /**
     * @return recivedFI
     */
    public Double getRecivedFI() {
        return recivedFI;
    }

    /**
     * @param recivedFI
     *            the recivedFI to set
     */
    public void setRecivedFI(Double recivedFI) {
        this.recivedFI = recivedFI;
    }

    /**
     * @return hasFI
     */
    public Double getHasFI() {
        return hasFI;
    }

    /**
     * @param hasFI
     *            the hasFI to set
     */
    public void setHasFI(Double hasFI) {
        this.hasFI = hasFI;
    }

    /**
     * @return manageFee
     */
    public Double getManageFee() {
        return manageFee;
    }

    /**
     * @param manageFee
     *            the manageFee to set
     */
    public void setManageFee(Double manageFee) {
        this.manageFee = manageFee;
    }

    /**
     * @return reward
     */
    public Double getReward() {
        return reward;
    }

    /**
     * @param reward
     *            the reward to set
     */
    public void setReward(Double reward) {
        this.reward = reward;
    }

    /**
     * @return repayStatus
     */
    public Integer getRepayStatus() {
        return repayStatus;
    }

    /**
     * @param repayStatus
     *            the repayStatus to set
     */
    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    /**
     * @return flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag
     *            the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return isAutoBid
     */
    public Integer getIsAutoBid() {
        return isAutoBid;
    }

    /**
     * @param isAutoBid
     *            the isAutoBid to set
     */
    public void setIsAutoBid(Integer isAutoBid) {
        this.isAutoBid = isAutoBid;
    }

    /**
     * @return isDebt
     */
    public Integer getIsDebt() {
        return isDebt;
    }

    /**
     * @param isDebt
     *            the isDebt to set
     */
    public void setIsDebt(Integer isDebt) {
        this.isDebt = isDebt;
    }

    /**
     * @return checkPrincipal
     */
    public Double getCheckPrincipal() {
        return checkPrincipal;
    }

    /**
     * @param checkPrincipal
     *            the checkPrincipal to set
     */
    public void setCheckPrincipal(Double checkPrincipal) {
        this.checkPrincipal = checkPrincipal;
    }

    /**
     * @return checkInterest
     */
    public Double getCheckInterest() {
        return checkInterest;
    }

    /**
     * @param checkInterest
     *            the checkInterest to set
     */
    public void setCheckInterest(Double checkInterest) {
        this.checkInterest = checkInterest;
    }

    /**
     * @return minInvestId
     */
    public Long getMinInvestId() {
        return minInvestId;
    }

    /**
     * @param minInvestId
     *            the minInvestId to set
     */
    public void setMinInvestId(Long minInvestId) {
        this.minInvestId = minInvestId;
    }

    /**
     * @return maxInvestId
     */
    public Long getMaxInvestId() {
        return maxInvestId;
    }

    /**
     * @param maxInvestId
     *            the maxInvestId to set
     */
    public void setMaxInvestId(Long maxInvestId) {
        this.maxInvestId = maxInvestId;
    }

    /**
     * @return adjustPrincipal
     */
    public Double getAdjustPrincipal() {
        return adjustPrincipal;
    }

    /**
     * @param adjustPrincipal
     *            the adjustPrincipal to set
     */
    public void setAdjustPrincipal(Double adjustPrincipal) {
        this.adjustPrincipal = adjustPrincipal;
    }

}
