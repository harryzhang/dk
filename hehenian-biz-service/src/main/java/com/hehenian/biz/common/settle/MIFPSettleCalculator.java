/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.settle
 * @Title: MIFPSettleCalculator.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:15:33
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.settle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.util.CalculateUtils;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:15:33
 */
public class MIFPSettleCalculator extends SettleCalculator {

    @Override
    public List<SettDetailDo> calSettDetail(Double loanAmount, Integer loanPeriod, Double annualRate,Date startDate){
        List<SettDetailDo> settDetailDoList = new ArrayList<SettDetailDo>();
        for (int i = 1; i <= loanPeriod.intValue(); i++) {
            SettDetailDo settDetailDo = new SettDetailDo();
            settDetailDo.setPeriod(i);// 期数
            Double principal = (i >= loanPeriod.intValue()) ? loanAmount : 0.00;
            settDetailDo.setPrincipal(principal);// 本金
            Double interest = calMonthRepayInterest(loanAmount, annualRate);// 月还利息
            if (i == loanPeriod.intValue()) {
                double totalRepayInterest = calTotalRepayInterest(loanAmount, annualRate, loanPeriod);
                interest = CalculateUtils.sub(totalRepayInterest, CalculateUtils.mul(interest, (loanPeriod - 1)));
            }
            settDetailDo.setInterest(interest);// 利息
            settDetailDo.setRemainingPrincipal(loanAmount);// 剩余本金
            settDetailDo.setRepayDate(DateUtils.addMonths(startDate, i));// 还款日期
            settDetailDoList.add(settDetailDo);
        }
        return settDetailDoList;
    }

    /**
     * 计算月还利息金额
     * 
     * @param remainingPrincipal
     *            剩余本金
     * @param annualRate
     *            年利率
     * @return
     * @author: liuzgmf
     * @date: 2014年9月24日上午9:33:45
     */
    private Double calMonthRepayInterest(Double loanAmount, Double annualRate) {
        return CalculateUtils.round(CalculateUtils.mul(loanAmount, getMonthRate(annualRate)), 2);
    }

    /**
     * 计算总还利息金额
     * 
     * @param remainingPrincipal
     *            剩余本金
     * @param annualRate
     *            年利率
     * @return
     * @author: liuzgmf
     * @date: 2014年9月24日上午9:33:45
     */
    private Double calTotalRepayInterest(Double loanAmount, Double annualRate, Integer loanPeriod) {
        return CalculateUtils.round(
                CalculateUtils.mul(CalculateUtils.mul(loanAmount, getMonthRate(annualRate)), loanPeriod), 2);
    }

}
