package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;

public class InvestDo implements Serializable {
    private static final long  serialVersionUID = 1L;
    private Long               id;                     // 编号
    private Double             investAmount;           // 投资金额
    private Double             monthRate;              // 月利率
    private Long               investor;               // 投资人
    private Long               borrowId;               // 借款ID

    private Date               investTime;             // 投资时间
    private Long               oriInvestor;            // 原始投资人
    private Double             realAmount;             // 实际投资金额（用于债权转让是的投资金额
    private Double             hasPI;                  // 已收本息
    private Integer            deadline;               // 期数

    private Integer            hasDeadline;            // 已还款期数
    private Double             recivedPrincipal;       // 应收本金
    private Double             recievedInterest;       // 应收利息
    private Double             hasPrincipal;           // 已收本金
    private Double             hasInterest;            // 已收利息

    private Double             recivedFI;              // 应收罚金
    private Double             hasFI;                  // 已收罚金
    private Double             manageFee;              // 管理费
    private Double             reward;                 // 奖励
    private Integer            repayStatus;            // 还款状态（1 默认未偿还 2 已偿还 3
                                                        // 还款中 )

    private String             flag;                   // 标识
    private Integer            isAutoBid;              // 自动投标( 默认 1 手动 2 自动)
    private Integer            isDebt;                 // 是否转让(1,没有转让，2转让，3转让中)
    private Double             circulationInterest;    // 流转标利息
    private Integer            circulationForpayStatus; // 流转标收款状态(默认 -1 不受理 1
                                                        // 待收
                                                        // 2 已收)

    private String             reason;                 // 购买理由
    private Date               repayDate;              // 还款日期
    private Double             checkPrincipal;         // 校验本金
    private Double             checkInterest;          // 校验利息
    private Long               minInvestId;            // 最小投资id

    private Long               maxInvestId;            // 最大投资id
    private Double             adjustPrincipal;        // 调整本金
    private Double             isCancel;               // 是否取消投资(默认1未取消 2:取消)
    private Date               cancelDate;             //
    private Integer            distinguishDebt;        // 债权区分(0，前台申请，1，后台债权)

    private String             investNumber;           // 债权编号
    private Long               trxId;                  // 汇付生成的交易唯一标识
    private Integer            sourceFrom;             // 投标来源

    private AccountUserDo      userDo;                 // 投资人

    /**
     * 债券转让对象， 根据invest_id, 机票
     *
     */
    protected AssignmentDebtDo assignDebtDo;

    /**
     * 汇付交易的时候取subordid ，如果有债券转让用债券转让的ID
     *
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午9:04:01
     */
    public long getSubOrdId() {
        if (null != assignDebtDo && assignDebtDo.getDebtStatus().intValue() == 3) {
            return assignDebtDo.getAuctionDebtDo().getId();
        } else {
            return this.id;
        }
    }

    /**
     * 汇付交易的时候取subordDate ，如果有债券转让用债券转让的交易时间
     *
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午9:05:51
     */
    public Date getSubOrdDate() {
        if (null != assignDebtDo && assignDebtDo.getDebtStatus().intValue() == 3) {
            return assignDebtDo.getAuctionDebtDo().getAuctionTime();
        } else {
            return this.investTime;
        }
    }

    public Integer getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(Integer sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(Double investAmount) {
        this.investAmount = investAmount;
    }

    public Double getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(Double monthRate) {
        this.monthRate = monthRate;
    }

    public Long getInvestor() {
        return investor;
    }

    public void setInvestor(Long investor) {
        this.investor = investor;
    }

    public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public Date getInvestTime() {
        return investTime;
    }

    public void setInvestTime(Date investTime) {
        this.investTime = investTime;
    }

    public Long getOriInvestor() {
        return oriInvestor;
    }

    public void setOriInvestor(Long oriInvestor) {
        this.oriInvestor = oriInvestor;
    }

    public Double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Double realAmount) {
        this.realAmount = realAmount;
    }

    public Double getHasPI() {
        return hasPI;
    }

    public void setHasPI(Double hasPI) {
        this.hasPI = hasPI;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public Integer getHasDeadline() {
        return hasDeadline;
    }

    public void setHasDeadline(Integer hasDeadline) {
        this.hasDeadline = hasDeadline;
    }

    public Double getRecivedPrincipal() {
        return recivedPrincipal;
    }

    public void setRecivedPrincipal(Double recivedPrincipal) {
        this.recivedPrincipal = recivedPrincipal;
    }

    public Double getRecievedInterest() {
        return recievedInterest;
    }

    public void setRecievedInterest(Double recievedInterest) {
        this.recievedInterest = recievedInterest;
    }

    public Double getHasPrincipal() {
        return hasPrincipal;
    }

    public void setHasPrincipal(Double hasPrincipal) {
        this.hasPrincipal = hasPrincipal;
    }

    public Double getHasInterest() {
        return hasInterest;
    }

    public void setHasInterest(Double hasInterest) {
        this.hasInterest = hasInterest;
    }

    public Double getRecivedFI() {
        return recivedFI;
    }

    public void setRecivedFI(Double recivedFI) {
        this.recivedFI = recivedFI;
    }

    public Double getHasFI() {
        return hasFI;
    }

    public void setHasFI(Double hasFI) {
        this.hasFI = hasFI;
    }

    public Double getManageFee() {
        return manageFee;
    }

    public void setManageFee(Double manageFee) {
        this.manageFee = manageFee;
    }

    public Double getReward() {
        return reward;
    }

    public void setReward(Double reward) {
        this.reward = reward;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getIsAutoBid() {
        return isAutoBid;
    }

    public void setIsAutoBid(Integer isAutoBid) {
        this.isAutoBid = isAutoBid;
    }

    public Integer getIsDebt() {
        return isDebt;
    }

    public void setIsDebt(Integer isDebt) {
        this.isDebt = isDebt;
    }

    public Double getCirculationInterest() {
        return circulationInterest;
    }

    public void setCirculationInterest(Double circulationInterest) {
        this.circulationInterest = circulationInterest;
    }

    public Integer getCirculationForpayStatus() {
        return circulationForpayStatus;
    }

    public void setCirculationForpayStatus(Integer circulationForpayStatus) {
        this.circulationForpayStatus = circulationForpayStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public Double getCheckPrincipal() {
        return checkPrincipal;
    }

    public void setCheckPrincipal(Double checkPrincipal) {
        this.checkPrincipal = checkPrincipal;
    }

    public Double getCheckInterest() {
        return checkInterest;
    }

    public void setCheckInterest(Double checkInterest) {
        this.checkInterest = checkInterest;
    }

    public Long getMinInvestId() {
        return minInvestId;
    }

    public void setMinInvestId(Long minInvestId) {
        this.minInvestId = minInvestId;
    }

    public Long getMaxInvestId() {
        return maxInvestId;
    }

    public void setMaxInvestId(Long maxInvestId) {
        this.maxInvestId = maxInvestId;
    }

    public Double getAdjustPrincipal() {
        return adjustPrincipal;
    }

    public void setAdjustPrincipal(Double adjustPrincipal) {
        this.adjustPrincipal = adjustPrincipal;
    }

    public Double getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Double isCancel) {
        this.isCancel = isCancel;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Integer getDistinguishDebt() {
        return distinguishDebt;
    }

    public void setDistinguishDebt(Integer distinguishDebt) {
        this.distinguishDebt = distinguishDebt;
    }

    public String getInvestNumber() {
        return investNumber;
    }

    public void setInvestNumber(String investNumber) {
        this.investNumber = investNumber;
    }

    public Long getTrxId() {
        return trxId;
    }

    public void setTrxId(Long trxId) {
        this.trxId = trxId;
    }

    /**
     * @return userDo
     */
    public AccountUserDo getUserDo() {
        return userDo;
    }

    /**
     * @param userDo
     *            the userDo to set
     */
    public void setUserDo(AccountUserDo userDo) {
        this.userDo = userDo;
    }

    public AssignmentDebtDo getAssignDebtDo() {
        return assignDebtDo;
    }

    public void setAssignDebtDo(AssignmentDebtDo assignDebtDo) {
        this.assignDebtDo = assignDebtDo;
    }

}
