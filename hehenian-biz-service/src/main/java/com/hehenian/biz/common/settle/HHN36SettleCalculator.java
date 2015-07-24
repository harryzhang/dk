/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.settle
 * @Title: HHN36SettleCalculator.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年4月21日 下午3:55:48
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
 * @date 2015年4月21日 下午3:55:48
 */
public class HHN36SettleCalculator extends SettleCalculator {

    @Override
    public List<SettDetailDo> calSettDetail(Double loanAmount, Integer loanPeriod, Double annualRate,Date StartDate) {
        List<SettDetailDo> settDetailDoList = new ArrayList<SettDetailDo>(36);
        Double remainPrincipal = loanAmount;// 剩余待还本金
        for (int i = 1; i <= 36; i++) {
            // 每月借款费用
            SettDetailDo settDetailDo = new SettDetailDo();
            settDetailDo.setPeriod(i);
            settDetailDo.setServFee(0d);
            settDetailDo.setConsultFee(0d);
            Double principal = CalculateUtils.div(loanAmount, 36, 2, BigDecimal.ROUND_DOWN);
            if (i >= 36) {
                principal = remainPrincipal;
            }
            settDetailDo.setPrincipal(principal);
            settDetailDo.setRemainingPrincipal(remainPrincipal);// 剩余本金
            remainPrincipal = CalculateUtils.sub(remainPrincipal, principal);
            settDetailDo.setRepayDate(DateUtils.addMonths(StartDate, i));// 还款日期

            // 1~6期，利息:1.30%，管理费:0.80%
            if (i <= 6) {
                settDetailDo.setInterest(CalculateUtils.round(CalculateUtils.mul(loanAmount, 0.013), 2));
                settDetailDo.setConsultFee(CalculateUtils.round(CalculateUtils.mul(loanAmount, 0.008), 2));
            }

            // 7~12期，利息:1.10% ，管理费:0.80%
            if (i >= 7 && i <= 12) {
                settDetailDo.setInterest(CalculateUtils.round(CalculateUtils.mul(loanAmount, 0.011), 2));
                settDetailDo.setConsultFee(CalculateUtils.round(CalculateUtils.mul(loanAmount, 0.008), 2));
            }

            // 13~18期，利息:0.70%，管理费:0.80%
            if (i >= 13 && i <= 18) {
                settDetailDo.setInterest(CalculateUtils.round(CalculateUtils.mul(loanAmount, 0.007), 2));
                settDetailDo.setConsultFee(CalculateUtils.round(CalculateUtils.mul(loanAmount, 0.008), 2));
            }

            // 19~33期，利息:0.0%，管理费:0.80%
            if (i >= 19 && i <= 33) {
                settDetailDo.setInterest(0d);
                settDetailDo.setConsultFee(CalculateUtils.round(CalculateUtils.mul(loanAmount, 0.008), 2));
            }

            // 34~36期，利息:0.0%，管理费:0.0%
            if (i >= 34) {
                settDetailDo.setInterest(0d);
                settDetailDo.setConsultFee(0d);
            }

            // 第一期附加上50元的咨询费
            if (i == 1) {
                settDetailDo.setServFee(CalculateUtils.add(settDetailDo.getServFee(), 50));
            }

            settDetailDoList.add(settDetailDo);
        }
        return settDetailDoList;
    }

    public static void main(String[] args) {
        // HHN36SettleCalculator hhn36SettleCalculator = new
        // HHN36SettleCalculator();
        // List<SettDetailDo> settDetailDoList =
        // hhn36SettleCalculator.calSettDetail(60000d, 36, null);
        // for (SettDetailDo settDetailDo : settDetailDoList) {
        // System.out.println(settDetailDo);
        // }

        HHN36SettleCalculator hhn36SettleCalculator = new HHN36SettleCalculator();
        List<SettDetailDo> settDetailDoList = hhn36SettleCalculator.calSettDetail(50000d, 36, null);
        DecimalFormat df = new DecimalFormat("###.00");
        for (SettDetailDo settDetailDo : settDetailDoList) {
            String sql = "UPDATE t_repayment SET stillPrincipal=#stillPrincipal#,stillInterest=#stillInterest#,consultFee=#consultFee#,repayFee=#repayFee#,principalBalance=#principalBalance# WHERE borrowId=#borrowId# AND repayPeriod=#repayPeriod#;";
            sql = sql.replace("#stillPrincipal#", df.format(settDetailDo.getPrincipal()));
            sql = sql.replace("#stillInterest#", df.format(settDetailDo.getInterest()));
            sql = sql.replace("#consultFee#", df.format(settDetailDo.getConsultFee()));
            sql = sql.replace("#repayFee#", df.format(settDetailDo.getServFee()));
            sql = sql.replace("#principalBalance#", df.format(settDetailDo.getRemainingPrincipal()));
            sql = sql.replace("#borrowId#", "5971");
            sql = sql.replace("#repayPeriod#", "'" + settDetailDo.getPeriod() + "/" + settDetailDoList.size() + "'");
            System.out.println(sql);
        }

    }

}
