/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.settle
 * @Title: PISettleCalculator.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:15:48
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
 * 等额本息结算方法，Principal & Interest（等额本息）
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:15:48
 */
public class PISettleCalculator extends SettleCalculator {

    @Override
    public List<SettDetailDo> calSettDetail(Double loanAmount, Integer loanPeriod, Double annualRate,Date startDate) {
        Double monthRepayAmount = CalculateUtils.round(getMonthRepayAmount(loanAmount, annualRate, loanPeriod), 2);// 月还款金额
        Double remainPrincipal = loanAmount;// 剩余待还本金
        Double monthRate = getMonthRate(annualRate);

        List<SettDetailDo> settDetailDoList = new ArrayList<SettDetailDo>();
        for (int i = 1; i <= loanPeriod; i++) {
            Double interest = CalculateUtils.round(CalculateUtils.mul(remainPrincipal, monthRate), 2);
            Double principal = 0.00;
            if (i < loanPeriod) {
                principal = CalculateUtils.sub(monthRepayAmount, interest);
            } else {
                principal = remainPrincipal;
            }

            // 每月借款费用
            SettDetailDo settDetailDo = new SettDetailDo();
            settDetailDo.setPeriod(i);
            settDetailDo.setPrincipal(principal);
            settDetailDo.setInterest(interest);
            settDetailDo.setRemainingPrincipal(remainPrincipal);// 剩余本金
            remainPrincipal = CalculateUtils.sub(remainPrincipal, principal);
            settDetailDo.setRepayDate(DateUtils.addMonths(startDate, i));// 还款日期
            settDetailDoList.add(settDetailDo);
        }
        return settDetailDoList;
    }

    /**
     * 计算每月的还款金额
     * 
     * @param loanAmount
     * @param annualRate
     * @param loanPeriod
     * @return
     * @author: liuzgmf
     * @date: 2014年11月27日上午10:02:46
     */
    private Double getMonthRepayAmount(Double loanAmount, Double annualRate, Integer loanPeriod) {
        Double monthRate = getMonthRate(annualRate);
        // [贷款本金×月利率×（1+月利率）^还款月数]÷[（1+月利率）^还款月数－1]
        return (CalculateUtils.div(
                CalculateUtils.mul(CalculateUtils.mul(loanAmount, monthRate), Math.pow((1 + monthRate), loanPeriod)),
                (Math.pow((1 + monthRate), loanPeriod) - 1)));
    }

}
