/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.settle
 * @Title: EISettleCalculator.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:05:27
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
 * 等额本金结算方法，Level Principal（等额本金）
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:05:27
 */
public class LPSettleCalculator extends SettleCalculator {

    public static void main(String[] args) {
        SettleCalculator eiSettleCalculator = new LPSettleCalculator();
        List<SettDetailDo> settDetailDoList = eiSettleCalculator.calSettDetail(100000.0, 12, 22.5);
        for (SettDetailDo settDetailDo : settDetailDoList) {
            System.out.println(settDetailDo);
        }
    }

    @Override
    public List<SettDetailDo> calSettDetail(Double loanAmount, Integer loanPeriod, Double annualRate,Date startDate) {
        Double remainingPrincipal = loanAmount;// 剩余本金
        List<SettDetailDo> settDetailDoList = new ArrayList<SettDetailDo>();
        for (int i = 1; i <= loanPeriod.intValue(); i++) {
            Double principal = 0.00;// 月还本金
            if (i >= loanPeriod.intValue()) {
                principal = remainingPrincipal;
            } else {
                principal = calMonthRepayPrincipal(loanAmount, loanPeriod);
            }

            // 每月借款费用
            SettDetailDo settDetailDo = new SettDetailDo();
            settDetailDo.setPeriod(i);
            settDetailDo.setPrincipal(principal);
            Double interest = calMonthRepayInterest(remainingPrincipal, annualRate);// 月还利息
            settDetailDo.setInterest(interest);
            settDetailDo.setRemainingPrincipal(remainingPrincipal);
            settDetailDo.setRepayDate(DateUtils.addMonths(startDate, i));// 还款日期
            remainingPrincipal = CalculateUtils.sub(remainingPrincipal, principal);
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
    private Double calMonthRepayInterest(Double remainingPrincipal, Double annualRate) {
        return CalculateUtils.round(CalculateUtils.mul(remainingPrincipal, getMonthRate(annualRate)), 2);
    }

    /**
     * 计算月还本金金额
     * 
     * @param loanAmount
     * @param loanPeriod
     * @return
     * @author: liuzgmf
     * @date: 2014年9月24日上午9:28:51
     */
    private Double calMonthRepayPrincipal(Double loanAmount, Integer loanPeriod) {
        return CalculateUtils.div(loanAmount, loanPeriod, 2);
    }

}
