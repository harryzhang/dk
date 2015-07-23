/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade.dataobject
 * @Title: RepaymentFee.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月22日 下午3:33:17
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.hehenian.biz.common.util.CalculateUtils;

/**
 * 还款费用子表
 * 
 * @author: zhangyunhmf
 * @date 2014年10月22日 下午3:33:17
 */
public class RepaymentFeeDo implements java.io.Serializable {

    public RepaymentFeeDo() {

    }

    public RepaymentFeeDo(Long id, String feeCode, Double stillAmount, Double hasAmount) {
        this.id = id;
        this.feeCode = feeCode;
        this.stillAmount = stillAmount;
        this.hasAmount = hasAmount;
    }

    public RepaymentFeeDo(Long id, String feeCode, Double stillAmount, Double hasAmount, String operationNum,
            Long lastUpdateUser, Long investRepaymentId, Long repaymentId) {
        this.id = id;
        this.feeCode = feeCode;
        this.stillAmount = stillAmount;
        this.hasAmount = hasAmount;
        this.repaymentId = repaymentId;
        this.investRepaymentId = investRepaymentId;
        this.lastUpdateUser = lastUpdateUser;
    }

    /**
     * id
     */
    private Long   id;
    /**
     * 费用编码
     */
    private String feeCode;

    /**
     * 还款ID
     */
    private Long   repaymentId;

    /**
     * 应金额额
     */
    private Double stillAmount;

    /**
     * 已收金额
     */
    private Double hasAmount;

    /**
     * 备注: 1 还款， 2 减免
     */
    private String remark = "1";

    /**
     * 最后修改日期
     */
    private Date   lastUpdateDate = new Date();

    private Long   investRepaymentId; // 回款ID

    private Long   lastUpdateUser;   // 减免操作人

    private String operationNum;     // 还款操作批次,生产规则当前还款id+hh+mi+ss(两位时分秒)

    /**
     * 计算剩余未收费用金额
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年11月26日下午2:48:07
     */
    public Double getRemainAmount() {
        return CalculateUtils.sub(this.stillAmount == null ? 0 : this.stillAmount, this.hasAmount == null ? 0
                : this.hasAmount);
    }

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
     * @return feeCode
     */
    public String getFeeCode() {
        return feeCode;
    }

    /**
     * @param feeCode
     *            the feeCode to set
     */
    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    /**
     * @return repaymentId
     */
    public Long getRepaymentId() {
        return repaymentId;
    }

    /**
     * @param repaymentId
     *            the repaymentId to set
     */
    public void setRepaymentId(Long repaymentId) {
        this.repaymentId = repaymentId;
    }

    /**
     * @return stillAmount
     */
    public Double getStillAmount() {
        return stillAmount == null ? 0 : stillAmount;
    }

    /**
     * @param stillAmount
     *            the stillAmount to set
     */
    public void setStillAmount(Double stillAmount) {
        this.stillAmount = stillAmount;
    }

    /**
     * @return hasAmount
     */
    public Double getHasAmount() {
        return this.hasAmount == null ? 0 : this.hasAmount;
    }

    /**
     * @param hasAmount
     *            the hasAmount to set
     */
    public void setHasAmount(Double hasAmount) {
        this.hasAmount = hasAmount;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return lastUpdateDate
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param lastUpdateDate
     *            the lastUpdateDate to set
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * @return investRepaymentId
     */
    public Long getInvestRepaymentId() {
        return investRepaymentId;
    }

    /**
     * @param investRepaymentId
     *            the investRepaymentId to set
     */
    public void setInvestRepaymentId(Long investRepaymentId) {
        this.investRepaymentId = investRepaymentId;
    }

    /**
     * @return lastUpdateUser
     */
    public Long getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * @param lastUpdateUser
     *            the lastUpdateUser to set
     */
    public void setLastUpdateUser(Long lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    /**
     * @return operationNum
     */
    public String getOperationNum() {
        return operationNum;
    }

    /**
     * @param operationNum
     *            the operationNum to set
     */
    public void setOperationNum(String operationNum) {
        this.operationNum = operationNum;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId())
                .append("FeeCode", getFeeCode()).append("RepaymentId", getRepaymentId())
                .append("StillAmount", getStillAmount()).append("HasAmount", getHasAmount())
                .append("LastUpdateDate", getLastUpdateDate()).append("Remark", getRemark())
                .append("lastUpdateUser", this.getLastUpdateUser()).append("operationNum", this.getOperationNum())
                .append("investRepaymentId", this.getInvestRepaymentId()).toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof RepaymentFeeDo == false)
            return false;
        if (this == obj)
            return true;
        RepaymentFeeDo other = (RepaymentFeeDo) obj;
        return new EqualsBuilder().append(getId(), other.getId()).isEquals();
    }

}
