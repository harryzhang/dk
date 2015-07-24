/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.settle
 * @Title: HHN24SettleCalculator.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年4月21日 下午3:55:20
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
 * @date 2015年4月21日 下午3:55:20
 */
public class HHN24SettleCalculator extends SettleCalculator {

    @Override
    public List<SettDetailDo> calSettDetail(Double loanAmount, Integer loanPeriod, Double annualRate,Date StartDate)  {
        List<SettDetailDo> settDetailDoList = new ArrayList<SettDetailDo>(24);
        Double remainPrincipal = loanAmount;// 剩余待还本金
        for (int i = 1; i <= 24; i++) {
            // 每月借款费用
            SettDetailDo settDetailDo = new SettDetailDo();
            settDetailDo.setPeriod(i);
            settDetailDo.setServFee(0d);
            settDetailDo.setConsultFee(0d);
            Double principal = CalculateUtils.div(loanAmount, 24, 2, BigDecimal.ROUND_DOWN);
            if (i >= 24) {
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

            // 19~24期，利息:0.0%，管理费:0.80%
            if (i >= 19) {
                settDetailDo.setInterest(0d);
                settDetailDo.setConsultFee(CalculateUtils.round(CalculateUtils.mul(loanAmount, 0.008), 2));
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
//        HHN24SettleCalculator hhn24SettleCalculator = new HHN24SettleCalculator();
//        List<SettDetailDo> settDetailDoList = hhn24SettleCalculator.calSettDetail(50000d, 24, null);
//        for (SettDetailDo settDetailDo : settDetailDoList) {
//            System.out.println(settDetailDo);
//        }
        
        HHN24SettleCalculator hhn24SettleCalculator = new HHN24SettleCalculator();
        List<SettDetailDo> settDetailDoList = hhn24SettleCalculator.calSettDetail(40000d, 24, null);
        DecimalFormat df = new DecimalFormat("###.00");
        for (SettDetailDo settDetailDo : settDetailDoList) {
            String sql = "UPDATE t_repayment SET stillPrincipal=#stillPrincipal#,stillInterest=#stillInterest#,consultFee=#consultFee#,repayFee=#repayFee#,principalBalance=#principalBalance# WHERE borrowId=#borrowId# AND repayPeriod=#repayPeriod#;";
            sql = sql.replace("#stillPrincipal#", df.format(settDetailDo.getPrincipal()));
            sql = sql.replace("#stillInterest#", df.format(settDetailDo.getInterest()));
            sql = sql.replace("#consultFee#", df.format(settDetailDo.getConsultFee()));
            sql = sql.replace("#repayFee#", df.format(settDetailDo.getServFee()));
            sql = sql.replace("#principalBalance#", df.format(settDetailDo.getRemainingPrincipal()));
            sql = sql.replace("#borrowId#", "5970");
            sql = sql.replace("#repayPeriod#", "'" + settDetailDo.getPeriod() + "/" + settDetailDoList.size() + "'");
            System.out.println(sql);
        }
    }
}
