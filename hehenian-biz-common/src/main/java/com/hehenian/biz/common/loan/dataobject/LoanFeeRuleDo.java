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
package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:13:28
 */
public class LoanFeeRuleDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              id;               // 规则ID
    private Long              schemeId;             // 方案ID
    private String            name;             // 规则名称
    private String          type;             // 规则类型（CONSULT_FEE-咨询费,SERV_FEE-手续费,SETTLE_FEE-提现结清手续费,CREDIT_FEE-征信费,OVERDUE_FEE-逾期罚息,OTHER-其他）
    private String         gatherWay;            // 收取方式（ONCE_RATIO-一次性比例收取,ONCE_FIXED-一次性固定收取,EACH_RATIO-每期比例收取,EACH_FIXED-每期固定收取）
    
    
    private String isInclude;// 该费用是否包含在借款利率里， T 包含， F 不包含
    private String baseAmountType; //乘数， 1： 借款金额， 2：剩余本金

    private Double            gatherRate;           // 收取比率
    private Double            feeAmount;            // 费用金额
    private Long              createUserId;         // 创建用户ID
    private Long              updateUserId;         // 修改用户ID
    private Date              createTime;           // 创建时间
    private Date              updateTime;           // 修改时间
    
    private String 			isInitRepayPlanUse ;  //是否在生存还款计划表的时候 用
    
    //管理费,PARKING_FEE停车费,REG_FEE登记费,REPAY_FEE手续费
    public enum feeType{
    	CONSULT_FEE,SERV_FEE,SETTLE_FEE,CREDIT_FEE,OVERDUE_FEE,OTHER,PARKING_FEE,REG_FEE,REPAY_FEE
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGatherWay() {
		return gatherWay;
	}

	public void setGatherWay(String gatherWay) {
		this.gatherWay = gatherWay;
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

	public Double getGatherRate() {
		return gatherRate;
	}

	public void setGatherRate(Double gatherRate) {
		this.gatherRate = gatherRate;
	}

	public Double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsInitRepayPlanUse() {
		return isInitRepayPlanUse;
	}

	public void setIsInitRepayPlanUse(String isInitRepayPlanUse) {
		this.isInitRepayPlanUse = isInitRepayPlanUse;
	}
    
}
