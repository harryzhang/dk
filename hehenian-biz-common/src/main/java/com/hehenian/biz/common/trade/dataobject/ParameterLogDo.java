/**
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade.dataobject
 * @Title: Parameter.java
 * @Description: 交易参数日志类
 * @author: liuzgmf
 * @date 2014年12月29日 下午4:27:08
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: liuzgmf
 * @date 2014年12月29日 下午4:27:08
 */
public class ParameterLogDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              parameterLogId;       // 参数ID
    private TradeType         tradeType;            // 交易类型
    private Long              ordId;                // 订单号
    private Date              ordDate;              // 订单日期
    private Long              trxId;                // 交易ID
    private String            other;                // 其他参数
    private ParameterType     parameterType;        // 参数类型（IN-请求参数OUT-响应参数）
    private Date              createTime;           // 创建时间

    /**
     * 交易类型 CASH-提现申请,USRFREEZEBG-资金（货款）冻结,USRUNFREEZE-资金（货款）解冻,CASHAUDIT-取现复核,
     * NETSAVE-网银充值
     * ,POSWHSAVE-商户无卡代扣充值,INITIATIVETENDER-主动投标,AUTOTENDER-自动投标,TENDERCANCLE
     * -投标撤销 ,AUTOTENDERPLAN-自动投标计划,AUTOTENDERPLANCLOSE-自动投标关闭,LOANS-自动扣款（放款）,
     * REPAYMENT-自动扣款（还款）
     * ,TRANSFER-转账（商户用）,USRACCTPAY-用户账户支付,MERCASH-商户代取现接口,USRTRANSFER
     * -前台用户间转账,CREDITASSIGN-债权转让
     * ,AUTOCREDITASSIGN-自动债权转让,FSSTRANS-生利宝交易,DIRECTRFAUTH-定向转账授权,DIRECTRF-定向转账
     */
    public enum TradeType {
        CASH, USRFREEZEBG, USRUNFREEZE, CASHAUDIT, NETSAVE, POSWHSAVE, INITIATIVETENDER, AUTOTENDER, TENDERCANCLE, AUTOTENDERPLAN, AUTOTENDERPLANCLOSE, LOANS, REPAYMENT, TRANSFER, USRACCTPAY, MERCASH, USRTRANSFER, CREDITASSIGN, AUTOCREDITASSIGN, FSSTRANS, DIRECTRFAUTH, DIRECTRF;
    }

    /**
     * 参数类型（REQU-请求参RESP-响应参数）
     */
    public enum ParameterType {
        REQU, RESP;
    }

    /**
     * @return parameterLogId
     */
    public Long getParameterLogId() {
        return parameterLogId;
    }

    /**
     * @param parameterLogId
     *            the parameterLogId to set
     */
    public void setParameterLogId(Long parameterLogId) {
        this.parameterLogId = parameterLogId;
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
     * @return trxId
     */
    public Long getTrxId() {
        return trxId;
    }

    /**
     * @param trxId
     *            the trxId to set
     */
    public void setTrxId(Long trxId) {
        this.trxId = trxId;
    }

    /**
     * @return other
     */
    public String getOther() {
        return other;
    }

    /**
     * @param other
     *            the other to set
     */
    public void setOther(String other) {
        this.other = other;
    }

    /**
     * @return parameterType
     */
    public ParameterType getParameterType() {
        return parameterType;
    }

    /**
     * @param parameterType
     *            the parameterType to set
     */
    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
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

}
