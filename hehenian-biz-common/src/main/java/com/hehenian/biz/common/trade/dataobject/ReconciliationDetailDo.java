/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade.dataobject
 * @Title: ReconciliationDetailDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月5日 下午2:56:58
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 对账明细信息
 * 
 * @author: liuzgmf
 * @date 2015年1月5日 下午2:56:58
 */
public class ReconciliationDetailDo implements Serializable {
    private static final long    serialVersionUID = 1L;
    private Long                 detailId;             // 对账明细ID
    private Long                 userId;               // 用户ID
    private Long                 tradeId;              // 交易ID
    private Long                 reconciliationId;     // 对账ID
    private TradeType            tradeType;            // 交易类型

    private ReconciliationStatus reconciliationStatus; // 对账状态
    private String               reconciliationDesc;   // 对账失败描叙
    private Date                 createTime;           // 创建时间
    private Date                 updateTime;           // 修改时间

    /** 对账类型（RECHARGE-充值，CASH-提现，DEBT-债权，LOANS-放款,REPAYMENT-还款，TRANSFER-商户扣款） */
    public enum TradeType {
        RECHARGE, CASH, DEBT, LOANS, REPAYMENT, TRANSFER;
    }

    /** 对账状态（SUCCESS-对账成功，FAILURE-对账失败，IGNORE-对账失败忽略） */
    public enum ReconciliationStatus {
        SUCCESS, FAILURE, IGNORE;
    }

    /**
     * @return detailId
     */
    public Long getDetailId() {
        return detailId;
    }

    /**
     * @param detailId
     *            the detailId to set
     */
    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    /**
     * @return userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return tradeId
     */
    public Long getTradeId() {
        return tradeId;
    }

    /**
     * @param tradeId
     *            the tradeId to set
     */
    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
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
     * @return tradeType
     */
    public TradeType getTradeType() {
        return tradeType;
    }

    /**
     * @param tradeType
     *            the tradeType to set
     */
    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
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

}
