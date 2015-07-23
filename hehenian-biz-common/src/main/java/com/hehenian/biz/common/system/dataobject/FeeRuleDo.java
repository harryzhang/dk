/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.system.dataobject
 * @Title: FeeRuleDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:13:28
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.system.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:13:28
 */
public class FeeRuleDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              ruleId;               // 规则ID
    private Long              schemeId;             // 方案ID
    private String            ruleName;             // 规则名称
    private RuleType          ruleType;             // 规则类型（CONSULT_FEE-咨询费,SERV_FEE-手续费,SETTLE_FEE-提现结清手续费,CREDIT_FEE-征信费,OVERDUE_FEE-逾期罚息,OTHER-其他）
    private GatherWay         gatherWay;            // 收取方式（ONCE_RATIO-一次性比例收取,ONCE_FIXED-一次性固定收取,EACH_RATIO-每期比例收取,EACH_FIXED-每期固定收取）
    
    
    private String isInclude;// 该费用是否包含在借款利率里， T 包含， F 不包含
    private String baseAmountType; //乘数， 1： 借款金额， 2：剩余本金

    private Double            gatherRate;           // 收取比率
    private Double            feeAmount;            // 费用金额
    private Long              createUserId;         // 创建用户ID
    private Long              updateUserId;         // 修改用户ID
    private Date              createTime;           // 创建时间
    private Date              updateTime;           // 修改时间
    private String 			isInitRepayPlanUse ;  //是否在生存还款计划表的时候 用

    /**
     * 规则类型（CONSULT_FEE-咨询费,SERV_FEE-手续费,SETTLE_FEE-提现结清手续费,CREDIT_FEE-征信费,
     * OVERDUE_FEE-逾期罚息,OTHER-其他）
     */
    public enum RuleType {
        CONSULT_FEE, SERV_FEE, SETTLE_FEE, CREDIT_FEE, OVERDUE_FEE, OTHER;
    }

    /**
     * 收取方式（ONCE_RATIO-一次性比例收取,ONCE_FIXED-一次性固定收取,EACH_RATIO-每期比例收取,EACH_FIXED-
     * 每期固定收取，ONCE_MONRATIO-一次性比例收取（月度比例））
     */
    public enum GatherWay {
        ONCE_RATIO, ONCE_FIXED, EACH_RATIO, EACH_FIXED, ONCE_MONRATIO;
    }

    /**
     * @return ruleId
     */
    public Long getRuleId() {
        return ruleId;
    }

    /**
     * @param ruleId
     *            the ruleId to set
     */
    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
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
     * @return ruleName
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * @param ruleName
     *            the ruleName to set
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * @return ruleType
     */
    public RuleType getRuleType() {
        return ruleType;
    }

    /**
     * @param ruleType
     *            the ruleType to set
     */
    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    /**
     * @return gatherWay
     */
    public GatherWay getGatherWay() {
        return gatherWay;
    }

    /**
     * @param gatherWay
     *            the gatherWay to set
     */
    public void setGatherWay(GatherWay gatherWay) {
        this.gatherWay = gatherWay;
    }

    /**
     * @return gatherRate
     */
    public Double getGatherRate() {
        return gatherRate;
    }

    /**
     * @param gatherRate
     *            the gatherRate to set
     */
    public void setGatherRate(Double gatherRate) {
        this.gatherRate = gatherRate;
    }

    /**
     * @return feeAmount
     */
    public Double getFeeAmount() {
        return feeAmount;
    }

    /**
     * @param feeAmount
     *            the feeAmount to set
     */
    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
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

	public String getIsInclude() {
		return isInclude;
	}

	public void setIsInclude(String isInclude) {
		this.isInclude = isInclude;
	}

	public String getBaseAmountType() {
		return baseAmountType;
	}

	public void setBaseAmountType(String baseAmountType) {
		this.baseAmountType = baseAmountType;
	}

	public String getIsInitRepayPlanUse() {
		return isInitRepayPlanUse;
	}

	public void setIsInitRepayPlanUse(String isInitRepayPlanUse) {
		this.isInitRepayPlanUse = isInitRepayPlanUse;
	}
}
