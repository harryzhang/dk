/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade
 * @Title: ICreditCalculatorService.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月15日 上午11:40:46
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade;

import java.util.Date;
import java.util.List;

import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo.SettleWay;

/**
 * 
 * @author: liuzgmf
 * @date 2014年12月15日 上午11:40:46
 */
public interface ISettleCalculatorService {
    /**
     * 计算每月还款明细信息
     * 
     * @param loanAmount
     *            借款金额
     * @param annualRate
     *            年利率
     * @param loanPeriod
     *            借款期数
     * @param schemeId
     *            结算方案ID
     * @return
     */
    List<SettDetailDo> calSettDetail(Double loanAmount, Double annualRate, Integer loanPeriod, Long schemeId);
    
    /**
     * 贷款申请的时候调用
     * @param loanAmount
     * @param annualRate
     * @param loanPeriod
     * @param schemeId
     * @return
     */
    List<SettDetailDo> calSettDetailForRepayPlanShow(Double loanAmount, Double annualRate, Integer loanPeriod, Long schemeId,Date startDate);

    /**
     * 贷款申请的时候调用， 展示还款的总金额， 本金，利息
     * @param loanAmount  借款金额
     * @param annualRate  借款年利率
     * @param loanPeriod  借款期限
     * @param fpic        还款方式
     * @return
     */
	List<SettDetailDo> calSettDetailForRepayPlanShow(Double loanAmount,
			Double annualRate, Integer loanPeriod, SettleWay fpic,Date startDate);

    /**
     * 计算每月还款明细信息
     * 
     * @param loanAmount
     *            借款金额
     * @param annualRate
     *            年利率
     * @param loanPeriod
     *            借款期数
     * @param settleWay
     *            结算方式
     * @return
     */
    List<SettDetailDo> calSettDetail(Double loanAmount, Double annualRate, Integer loanPeriod, SettleWay settleWay);


}
