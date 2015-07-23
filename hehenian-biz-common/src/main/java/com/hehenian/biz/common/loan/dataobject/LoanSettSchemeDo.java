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
package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hehenian.biz.common.system.dataobject.FeeRuleDo;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月5日 下午9:07:04
 */
public class LoanSettSchemeDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              id;             // 方案ID
    private Long              prodId;        //产品ID
    private String            code;           // 方案代码
    private String            name;           // 方案名称
    private String         	  repayWay;             // 还款方式
    private Double            defaultAnnualRate;    // 默认借款年利率

    private String         	 receiptWay;           // 回款方式
    private Integer           aheadSettlePeriod;    // 提前结清顺延期限
    private String      	 status;         // 方案状态（ENABLED-启用，DISABLED-禁用，PUBLISHED-已发布）
    private Long              createUserId;         // 创建用户ID
    private Long              updateUserId;         // 修改用户ID

    private Date              createTime;           // 创建时间
    private Date              updateTime;           // 修改时间
    private List<LoanFeeRuleDo>   loanFeeRuleDoList;        // 费用规则
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRepayWay() {
		return repayWay;
	}
	public void setRepayWay(String repayWay) {
		this.repayWay = repayWay;
	}
	public Double getDefaultAnnualRate() {
		return defaultAnnualRate;
	}
	public void setDefaultAnnualRate(Double defaultAnnualRate) {
		this.defaultAnnualRate = defaultAnnualRate;
	}
	public String getReceiptWay() {
		return receiptWay;
	}
	public void setReceiptWay(String receiptWay) {
		this.receiptWay = receiptWay;
	}
	public Integer getAheadSettlePeriod() {
		return aheadSettlePeriod;
	}
	public void setAheadSettlePeriod(Integer aheadSettlePeriod) {
		this.aheadSettlePeriod = aheadSettlePeriod;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public List<LoanFeeRuleDo> getLoanFeeRuleDoList() {
		return loanFeeRuleDoList;
	}
	public void setLoanFeeRuleDoList(List<LoanFeeRuleDo> loanFeeRuleDoList) {
		this.loanFeeRuleDoList = loanFeeRuleDoList;
	}
	
}
