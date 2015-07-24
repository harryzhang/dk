/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.settle
 * @Title: ELSettleCalculator.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年4月21日 下午5:27:15
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.settle;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.util.CalculateUtils;

/**
 * 
 * @author: liuzgmf
 * @date 2015年4月21日 下午5:27:15
 */
public class ELSettleCalculator extends SettleCalculator {

    @Override
    public List<SettDetailDo> calSettDetail(Double loanAmount, Integer loanPeriod, Double annualRate,Date startDate) {
        // 第一期，管理费
        SettDetailDo settDetailDo = new SettDetailDo();
        settDetailDo.setPeriod(0);// 期数
        settDetailDo.setPrincipal(0d);// 本金
        settDetailDo.setInterest(0d);// 利息
        settDetailDo.setServFee(0d);
        settDetailDo.setRemainingPrincipal(loanAmount);// 剩余本金
        // 管理费=借款金额*管理费利率*期数，24期为1.2%，36期为0.8%
        if (loanPeriod == 24) {
            settDetailDo.setConsultFee(CalculateUtils.round(
                    CalculateUtils.mul(CalculateUtils.mul(loanAmount, 0.012), loanPeriod), 2));
        } else {
            settDetailDo.setConsultFee(CalculateUtils.round(
                    CalculateUtils.mul(CalculateUtils.mul(loanAmount, 0.008), loanPeriod), 2));
        }
        settDetailDo.setRepayDate(startDate);// 还款日期
        List<SettDetailDo> settDetailDoList = new ArrayList<SettDetailDo>();
        settDetailDoList.add(settDetailDo);

        // 本金和利息
        Double remainPrincipal = loanAmount;// 剩余待还本金
        for (int i = 1; i <= loanPeriod; i++) {
            settDetailDo = new SettDetailDo();
            settDetailDo.setServFee(0d);
            settDetailDo.setConsultFee(0d);
            settDetailDo.setPeriod(i);// 期数
            Double principal = CalculateUtils.div(loanAmount, loanPeriod, 2, BigDecimal.ROUND_DOWN);
            if (i >= loanPeriod) {
                principal = remainPrincipal;
            }
            settDetailDo.setPrincipal(principal);
            settDetailDo.setInterest(CalculateUtils.round(CalculateUtils.mul(loanAmount, getMonthRate(annualRate)), 2));
            settDetailDo.setRemainingPrincipal(remainPrincipal);// 剩余本金
            remainPrincipal = CalculateUtils.sub(remainPrincipal, principal);
            settDetailDo.setRepayDate(DateUtils.addMonths(startDate, i));// 还款日期

            // 第一期附加上50元的咨询费
            if (i == 1) {
                settDetailDo.setServFee(CalculateUtils.add(settDetailDo.getServFee(), 50));
            }
            settDetailDoList.add(settDetailDo);
        }
        return settDetailDoList;
    }

    public static void main(String[] args) {
        // ELSettleCalculator elSettleCalculator = new ELSettleCalculator();
        // List<SettDetailDo> settDetailDoList =
        // elSettleCalculator.calSettDetail(57000d, 36, 9.96d);
        // for (SettDetailDo settDetailDo : settDetailDoList) {
        // System.out.println(settDetailDo);
        // }
        //
        // System.out.println();
        //
        // elSettleCalculator = new ELSettleCalculator();
        // settDetailDoList = elSettleCalculator.calSettDetail(29000d, 24,
        // 5.16d);
        // for (SettDetailDo settDetailDo : settDetailDoList) {
        // System.out.println(settDetailDo);
        // }

        ELSettleCalculator elSettleCalculator = new ELSettleCalculator();
        List<SettDetailDo> settDetailDoList = elSettleCalculator.calSettDetail(43000d, 24, 5.16d);
        //List<SettDetailDo> settDetailDoList = elSettleCalculator.calSettDetail(36000d, 36, 9.96d);
        DecimalFormat df = new DecimalFormat("##0.00");
        for (SettDetailDo settDetailDo : settDetailDoList) {
            String sql = "UPDATE td_fund_pre_repayment SET pre_capital = #pre_capital#,pre_interest=#pre_interest#,pre_service_fee=#pre_service_fee#,pre_consult_fee=#pre_consult_fee#,pre_repay_amount=#pre_repay_amount# WHERE loan_id = #loan_id# AND repay_times = #repay_times#;";
            sql = sql.replace("#pre_capital#", df.format(settDetailDo.getPrincipal()));
            sql = sql.replace("#pre_interest#", df.format(settDetailDo.getInterest()));
            sql = sql.replace("#pre_consult_fee#", df.format(settDetailDo.getConsultFee()));
            sql = sql.replace("#pre_service_fee#", df.format(settDetailDo.getServFee()));
            double totalRepayAmt = CalculateUtils.add(CalculateUtils.add(
                    CalculateUtils.add(settDetailDo.getPrincipal(), settDetailDo.getInterest()),
                    settDetailDo.getConsultFee()), settDetailDo.getServFee());
            sql = sql.replace("#pre_repay_amount#", df.format(totalRepayAmt));
            sql = sql.replace("#loan_id#", "837");
            sql = sql.replace("#repay_times#", "'" + settDetailDo.getPeriod() + "'");
            System.out.println(sql);
        }

    }
}
