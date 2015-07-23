/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade.dataobject
 * @Title: ReconciliationDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年11月24日 上午9:56:30
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;

/**
 * 对账信息
 * 
 * @author: liuzgmf
 * @date 2014年11月24日 上午9:56:30
 */
public class ReconciliationDo implements Serializable {
    private static final long    serialVersionUID = 1L;
    private Long                 reconciliationId;     // 对账ID
    private ReconciliationType   reconciliationType;   // 对账类型
    private ReconciliationStatus reconciliationStatus; // 对账状态
    private String               reconciliationDesc;   // 对账描述
    private Long                 ordId;                // 订单号
    private String               merCustId;            // 商户客户号

    private String               usrCustId;            // 用户客户号
    private String               cardId;               // 开户银行账号
    private Double               transAmt;             // 交易金额
    private String               transStat;            // 汇付交易状态（S 成功,F 失败,I
                                                        // 初始,P 部分成功,H 经办,R 拒绝）
    private Date                 pnrDate;              // 汇付交易日期

    private String               pnrSeqId;             // 汇付交易流水
    private Date                 ordDate;              // 订单日期
    private String               investCustId;         // 投资人客户号
    private String               borrCustId;           // 借款人客户号
    private String               gateBusiId;           // 支付网关业务代号

    private String               openBankId;           // 开户银行代号
    private String               openAcctId;           // 开户银行账号2
    private Double               feeAmt;               // 手续费金额
    private String               feeCustId;            // 手续费扣款客户号
    private String               feeAcctId;            // 手续费扣款子账户号

    private String               sellCustId;           // 转让人客户号
    private Double               creditAmt;            // 转让金额
    private Double               creditDealAmt;        // 承接金额
    private Double               fee;                  // 扣款手续费
    private String               buyCustId;            // 承接人客户号
    private Date                 createTime;           // 创建时间
    private Date                 updateTime;           // 修改时间
    
    private AccountUserDo     userDo;               // 借款人信息

    /** 对账类型（RECHARGE-充值，CASH-提现，DEBT-债权，LOANS-放款,REPAYMENT-还款，TRANSFER-商户扣款） */
    public enum ReconciliationType {
        RECHARGE, CASH, DEBT, LOANS, REPAYMENT, TRANSFER;
    }

    /** 对账状态（UNRECONCILIATION-未对账，SUCCESS-对账成功，FAILURE-对账失败） */
    public enum ReconciliationStatus {
        UNRECONCILIATION, SUCCESS, FAILURE;
    }

    /**
     * @return reconciliationId
     */
    public Long getReconciliationId() {
        return reconciliationId;
    }

    /**
     * @param reconciliationId
     *            the reconciliationId to set
     */
    public void setReconciliationId(Long reconciliationId) {
        this.reconciliationId = reconciliationId;
    }

    /**
     * @return reconciliationType
     */
    public ReconciliationType getReconciliationType() {
        return reconciliationType;
    }

    /**
     * @param reconciliationType
     *            the reconciliationType to set
     */
    public void setReconciliationType(ReconciliationType reconciliationType) {
        this.reconciliationType = reconciliationType;
    }

    /**
     * @return reconciliationStatus
     */
    public ReconciliationStatus getReconciliationStatus() {
        return reconciliationStatus;
    }

    /**
     * @param reconciliationStatus
     *            the reconciliationStatus to set
     */
    public void setReconciliationStatus(ReconciliationStatus reconciliationStatus) {
        this.reconciliationStatus = reconciliationStatus;
    }

    /**
     * @return reconciliationDesc
     */
    public String getReconciliationDesc() {
        return reconciliationDesc;
    }

    /**
     * @param reconciliationDesc
     *            the reconciliationDesc to set
     */
    public void setReconciliationDesc(String reconciliationDesc) {
        this.reconciliationDesc = reconciliationDesc;
    }

    /**
     * @return ordId
     */
    public Long getOrdId() {
        return ordId;
    }

    /**
     * @param ordId
     *            the ordId to set
     */
    public void setOrdId(Long ordId) {
        this.ordId = ordId;
    }

    /**
     * @return merCustId
     */
    public String getMerCustId() {
        return merCustId;
    }

    /**
     * @param merCustId
     *            the merCustId to set
     */
    public void setMerCustId(String merCustId) {
        this.merCustId = merCustId;
    }

    /**
     * @return usrCustId
     */
    public String getUsrCustId() {
        return usrCustId;
    }

    /**
     * @param usrCustId
     *            the usrCustId to set
     */
    public void setUsrCustId(String usrCustId) {
        this.usrCustId = usrCustId;
    }

    /**
     * @return cardId
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * @param cardId
     *            the cardId to set
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * @return transAmt
     */
    public Double getTransAmt() {
        return transAmt;
    }

    /**
     * @param transAmt
     *            the transAmt to set
     */
    public void setTransAmt(Double transAmt) {
        this.transAmt = transAmt;
    }

    /**
     * @return transStat
     */
    public String getTransStat() {
        return transStat;
    }

    /**
     * @param transStat
     *            the transStat to set
     */
    public void setTransStat(String transStat) {
        this.transStat = transStat;
    }

    /**
     * @return pnrDate
     */
    public Date getPnrDate() {
        return pnrDate;
    }

    /**
     * @param pnrDate
     *            the pnrDate to set
     */
    public void setPnrDate(Date pnrDate) {
        this.pnrDate = pnrDate;
    }

    /**
     * @return pnrSeqId
     */
    public String getPnrSeqId() {
        return pnrSeqId;
    }

    /**
     * @param pnrSeqId
     *            the pnrSeqId to set
     */
    public void setPnrSeqId(String pnrSeqId) {
        this.pnrSeqId = pnrSeqId;
    }

    /**
     * @return ordDate
     */
    public Date getOrdDate() {
        return ordDate;
    }

    /**
     * @param ordDate
     *            the ordDate to set
     */
    public void setOrdDate(Date ordDate) {
        this.ordDate = ordDate;
    }

    /**
     * @return investCustId
     */
    public String getInvestCustId() {
        return investCustId;
    }

    /**
     * @param investCustId
     *            the investCustId to set
     */
    public void setInvestCustId(String investCustId) {
        this.investCustId = investCustId;
    }

    /**
     * @return borrCustId
     */
    public String getBorrCustId() {
        return borrCustId;
    }

    /**
     * @param borrCustId
     *            the borrCustId to set
     */
    public void setBorrCustId(String borrCustId) {
        this.borrCustId = borrCustId;
    }

    /**
     * @return gateBusiId
     */
    public String getGateBusiId() {
        return gateBusiId;
    }

    /**
     * @param gateBusiId
     *            the gateBusiId to set
     */
    public void setGateBusiId(String gateBusiId) {
        this.gateBusiId = gateBusiId;
    }

    /**
     * @return openBankId
     */
    public String getOpenBankId() {
        return openBankId;
    }

    /**
     * @param openBankId
     *            the openBankId to set
     */
    public void setOpenBankId(String openBankId) {
        this.openBankId = openBankId;
    }

    /**
     * @return openAcctId
     */
    public String getOpenAcctId() {
        return openAcctId;
    }

    /**
     * @param openAcctId
     *            the openAcctId to set
     */
    public void setOpenAcctId(String openAcctId) {
        this.openAcctId = openAcctId;
    }

    /**
     * @return feeAmt
     */
    public Double getFeeAmt() {
        return feeAmt;
    }

    /**
     * @param feeAmt
     *            the feeAmt to set
     */
    public void setFeeAmt(Double feeAmt) {
        this.feeAmt = feeAmt;
    }

    /**
     * @return feeCustId
     */
    public String getFeeCustId() {
        return feeCustId;
    }

    /**
     * @param feeCustId
     *            the feeCustId to set
     */
    public void setFeeCustId(String feeCustId) {
        this.feeCustId = feeCustId;
    }

    /**
     * @return feeAcctId
     */
    public String getFeeAcctId() {
        return feeAcctId;
    }

    /**
     * @param feeAcctId
     *            the feeAcctId to set
     */
    public void setFeeAcctId(String feeAcctId) {
        this.feeAcctId = feeAcctId;
    }

    /**
     * @return sellCustId
     */
    public String getSellCustId() {
        return sellCustId;
    }

    /**
     * @param sellCustId
     *            the sellCustId to set
     */
    public void setSellCustId(String sellCustId) {
        this.sellCustId = sellCustId;
    }

    /**
     * @return creditAmt
     */
    public Double getCreditAmt() {
        return creditAmt;
    }

    /**
     * @param creditAmt
     *            the creditAmt to set
     */
    public void setCreditAmt(Double creditAmt) {
        this.creditAmt = creditAmt;
    }

    /**
     * @return creditDealAmt
     */
    public Double getCreditDealAmt() {
        return creditDealAmt;
    }

    /**
     * @param creditDealAmt
     *            the creditDealAmt to set
     */
    public void setCreditDealAmt(Double creditDealAmt) {
        this.creditDealAmt = creditDealAmt;
    }

    /**
     * @return fee
     */
    public Double getFee() {
        return fee;
    }

    /**
     * @param fee
     *            the fee to set
     */
    public void setFee(Double fee) {
        this.fee = fee;
    }

    /**
     * @return buyCustId
     */
    public String getBuyCustId() {
        return buyCustId;
    }

    /**
     * @param buyCustId
     *            the buyCustId to set
     */
    public void setBuyCustId(String buyCustId) {
        this.buyCustId = buyCustId;
    }

    /**
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     *            the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /** 
     * @return userDo 
     */
    public AccountUserDo getUserDo() {
        return userDo;
    }

    /**
     * @param userDo the userDo to set
     */
    public void setUserDo(AccountUserDo userDo) {
        this.userDo = userDo;
    }

}
