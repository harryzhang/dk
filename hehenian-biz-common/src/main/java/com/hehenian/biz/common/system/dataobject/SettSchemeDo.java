/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.system.dataobject
 * @Title: SettSchemeDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月5日 下午9:07:04
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.system.dataobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月5日 下午9:07:04
 */
public class SettSchemeDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              schemeId;             // 方案ID
    private String            schemeCode;           // 方案代码
    private String            schemeName;           // 方案名称
    private SettleWay         repayWay;             // 还款方式
    private Double            defaultAnnualRate;    // 默认借款年利率

    private SettleWay         receiptWay;           // 回款方式
    private Integer           aheadSettlePeriod;    // 提前结清顺延期限
    private SettleType        settleType;           // 借款类型
    private SchemeStatus      schemeStatus;         // 方案状态（ENABLED-启用，DISABLED-禁用，PUBLISHED-已发布）
    private Long              createUserId;         // 创建用户ID
    private Long              updateUserId;         // 修改用户ID
    
    private String 	productCode;//产品代码

    private Date              createTime;           // 创建时间
    private Date              updateTime;           // 修改时间
    private List<FeeRuleDo>   feeRuleDoList;        // 费用规则

    /**
     * 结算方式（FPIC-平息,LP-等额本金,IIFP-一次付息到期还款,MIFP-按月付息到期还本,PI-等额本息,HHD24-合和贷24期,
     * HHD36-合和贷36期,EL-精英贷）
     */
    public enum SettleWay {
        FPIC, LP, IIFP, MIFP, PI, HHD24, HHD36, EL;
    }

    /** 结算类型（还款和回款是否分开计算：SEPARATE-分开，MERGE-合并计算） */
    public enum SettleType {
        SEPARATE, MERGE;
    }

    /** 方案状态（ENABLED-启用，DISABLED-禁用，PUBLISHED-已发布） */
    public enum SchemeStatus {
        ENABLED, DISABLED, PUBLISHED;
    }

    /**
     * @return schemeId
     */
    public Long getSchemeId() {
        return schemeId;
    }

    /**
     * @param schemeId
     *            the schemeId to set
     */
    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    /**
     * @return schemeCode
     */
    public String getSchemeCode() {
        return schemeCode;
    }

    /**
     * @param schemeCode
     *            the schemeCode to set
     */
    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    /**
     * @return schemeName
     */
    public String getSchemeName() {
        return schemeName;
    }

    /**
     * @param schemeName
     *            the schemeName to set
     */
    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    /**
     * @return repayWay
     */
    public SettleWay getRepayWay() {
        return repayWay;
    }

    /**
     * @param repayWay
     *            the repayWay to set
     */
    public void setRepayWay(SettleWay repayWay) {
        this.repayWay = repayWay;
    }

    /**
     * @return defaultAnnualRate
     */
    public Double getDefaultAnnualRate() {
        return defaultAnnualRate;
    }

    /**
     * @param defaultAnnualRate
     *            the defaultAnnualRate to set
     */
    public void setDefaultAnnualRate(Double defaultAnnualRate) {
        this.defaultAnnualRate = defaultAnnualRate;
    }

    /**
     * @return receiptWay
     */
    public SettleWay getReceiptWay() {
        return receiptWay;
    }

    /**
     * @param receiptWay
     *            the receiptWay to set
     */
    public void setReceiptWay(SettleWay receiptWay) {
        this.receiptWay = receiptWay;
    }

    /**
     * @return aheadSettlePeriod
     */
    public Integer getAheadSettlePeriod() {
        return aheadSettlePeriod;
    }

    /**
     * @param aheadSettlePeriod
     *            the aheadSettlePeriod to set
     */
    public void setAheadSettlePeriod(Integer aheadSettlePeriod) {
        this.aheadSettlePeriod = aheadSettlePeriod;
    }

    /**
     * @return settleType
     */
    public SettleType getSettleType() {
        return settleType;
    }

    /**
     * @param settleType
     *            the settleType to set
     */
    public void setSettleType(SettleType settleType) {
        this.settleType = settleType;
    }

    /**
     * @return schemeStatus
     */
    public SchemeStatus getSchemeStatus() {
        return schemeStatus;
    }

    /**
     * @param schemeStatus
     *            the schemeStatus to set
     */
    public void setSchemeStatus(SchemeStatus schemeStatus) {
        this.schemeStatus = schemeStatus;
    }

    /**
     * @return createUserId
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId
     *            the createUserId to set
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return updateUserId
     */
    public Long getUpdateUserId() {
        return updateUserId;
    }

    /**
     * @param updateUserId
     *            the updateUserId to set
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
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
     * @return feeRuleDoList
     */
    public List<FeeRuleDo> getFeeRuleDoList() {
        return feeRuleDoList;
    }

    /**
     * @param feeRuleDoList
     *            the feeRuleDoList to set
     */
    public void setFeeRuleDoList(List<FeeRuleDo> feeRuleDoList) {
        this.feeRuleDoList = feeRuleDoList;
    }

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
