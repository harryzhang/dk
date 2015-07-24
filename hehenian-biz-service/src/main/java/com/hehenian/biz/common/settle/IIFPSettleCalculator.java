/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.settle
 * @Title: IIFPSettleCalculator.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:15:21
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
 * 先息后本结算方式，放款日扣除利息和服务费
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:15:21
 */
public class IIFPSettleCalculator extends SettleCalculator {

    @Override
    public List<SettDetailDo> calSettDetail(Double loanAmount, Integer loanPeriod, Double annualRate,Date StartDate) {
        // 当期，第一期
        SettDetailDo settDetailDo = new SettDetailDo();
        settDetailDo.setPeriod(1);// 期数
        settDetailDo.setPrincipal(0.00);// 本金
        Double interestAmount = calTotalInterestAmount(loanAmount, annualRate, loanPeriod);// 总还利息总额
        settDetailDo.setInterest(interestAmount);// 利息
        settDetailDo.setRemainingPrincipal(loanAmount);// 剩余本金
        settDetailDo.setRepayDate(StartDate);// 还款日期

        List<SettDetailDo> settDetailDoList = new ArrayList<SettDetailDo>();
        settDetailDoList.add(settDetailDo);

        // 最后一期
        settDetailDo = new SettDetailDo();
        settDetailDo.setPeriod(2);// 期数
        settDetailDo.setPrincipal(loanAmount);// 本金
        settDetailDo.setInterest(0.00);// 利息
        settDetailDo.setRemainingPrincipal(loanAmount);// 剩余本金
        settDetailDo.setRepayDate(DateUtils.addMonths(StartDate, loanPeriod));// 还款日期
        settDetailDoList.add(settDetailDo);

        return settDetailDoList;
    }

    /**
     * 计算总还利息总额
     * 
     * @param loanAmount
     * @param annualRate
     * @param loanPeriod
     * @return
     * @author: liuzgmf
     * @date: 2014年10月10日上午10:40:51
     */
    private Double calTotalInterestAmount(Double loanAmount, Double annualRate, Integer loanPeriod) {
        Double totalInterestAmount = CalculateUtils.mul(CalculateUtils.mul(loanAmount, getMonthRate(annualRate)),
                loanPeriod);
        return CalculateUtils.round(totalInterestAmount, 2);
    }

}
