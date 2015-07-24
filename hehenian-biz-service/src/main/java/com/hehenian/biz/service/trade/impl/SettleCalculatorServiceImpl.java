/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.trade.impl
 * @Title: CreditCalculatorServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月15日 上午11:41:55
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.trade.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.hehenian.biz.common.settle.SettleCalculatorUtils;
import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo.SettleWay;
import com.hehenian.biz.common.trade.ISettleCalculatorService;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.system.ISettSchemeComponent;

/**
 * 
 * @author: liuzgmf
 * @date 2014年12月15日 上午11:41:55
 */
@Service("settleCalculatorService")
public class SettleCalculatorServiceImpl implements ISettleCalculatorService {
    @Autowired
    private ISettSchemeComponent settSchemeComponent;

    @Override
    public List<SettDetailDo> calSettDetail(Double loanAmount, Double annualRate, Integer loanPeriod, Long schemeId) {
        SettSchemeDo settSchemeDo = settSchemeComponent.getBySchemeId(schemeId);
        return SettleCalculatorUtils.calSettDetail(loanAmount, loanPeriod, null, annualRate, settSchemeDo);
    }
    
    /**
     * 贷款申请的时候调用， 展示还款的总金额， 本金，利息
     * @param loanAmount  借款金额
     * @param annualRate  借款年利率
     * @param loanPeriod  借款期限
     * @param fpic        还款方式
     * @return
     */
    @Override
	public List<SettDetailDo> calSettDetailForRepayPlanShow(Double loanAmount,
			Double annualRate, Integer loanPeriod, SettleWay fpic,Date startDate) {
    	SettSchemeDo settSchemeDo = new SettSchemeDo();
    	settSchemeDo.setRepayWay(fpic);    	
        return SettleCalculatorUtils.calSettDetailForRepayPlanShow(loanAmount, loanPeriod, annualRate, settSchemeDo,startDate);
	}

    @Override
    public List<SettDetailDo> calSettDetail(Double loanAmount, Double annualRate, Integer loanPeriod,
            SettleWay settleWay) {
        return SettleCalculatorUtils.createSettleCalculator(settleWay)
                .calSettDetail(loanAmount, loanPeriod, annualRate);
    }

    public static void main(String[] args) throws Exception {
        List<String> datas = new ArrayList<String>();
        datas.add("30000.00;12.00;24;733");
        datas.add("50000.00;12.00;24;734");
        datas.add("60000.00;14.00;36;735");
        datas.add("60000.00;14.00;36;736");
        datas.add("25000.00;10.00;12;737");
        datas.add("40000.00;14.00;36;740");
        datas.add("20000.00;10.00;12;741");
        datas.add("50000.00;12.00;24;744");
        datas.add("30000.00;12.00;24;745");
        datas.add("30000.00;12.00;24;746");
        datas.add("60000.00;12.00;24;747");
        datas.add("50000.00;11.00;18;748");
        datas.add("40000.00;11.00;18;749");
        datas.add("50000.00;12.00;24;753");
        datas.add("30000.00;12.00;24;754");
        datas.add("25000.00;10.00;12;755");
        datas.add("40000.00;11.00;18;756");
        datas.add("40000.00;11.00;18;757");
        datas.add("60000.00;10.00;12;758");
        datas.add("25000.00;10.00;12;759");
        datas.add("30000.00;14.00;36;760");
        datas.add("40000.00;14.00;36;761");
        datas.add("40000.00;12.00;24;765");
        datas.add("40000.00;14.00;36;766");
        datas.add("40000.00;12.00;24;767");
        datas.add("30000.00;12.00;24;768");
        datas.add("30000.00;12.00;24;769");
        datas.add("30000.00;12.00;24;770");
        datas.add("50000.00;14.00;36;771");
        datas.add("40000.00;14.00;36;772");
        datas.add("40000.00;11.00;18;773");
        datas.add("30000.00;12.00;24;774");
        datas.add("40000.00;14.00;36;775");
        datas.add("50000.00;14.00;36;776");
        datas.add("20000.00;12.00;24;777");
        datas.add("30000.00;11.00;18;778");
        datas.add("50000.00;14.00;36;779");
        datas.add("30000.00;12.00;24;780");
        datas.add("50000.00;12.00;24;781");
        datas.add("30000.00;12.00;24;782");
        datas.add("30000.00;14.00;36;783");
        datas.add("20000.00;14.00;36;784");
        datas.add("35000.00;12.00;24;785");
        datas.add("50000.00;14.00;36;786");
        datas.add("30000.00;14.00;36;787");
        String url = "http://10.111.0.203:9150/hehenian-service/services/settleCalculatorService";
        HessianProxyFactory factory = new HessianProxyFactory();
        ISettleCalculatorService settleCalculatorService = (ISettleCalculatorService) factory.create(
                ISettleCalculatorService.class, url);
        DecimalFormat df = new DecimalFormat("##0.00");
        try {
            for (String data : datas) {
                args = data.split(";");
                double loanAmount = Double.parseDouble(args[0]);
                double annualRate = Double.parseDouble(args[1]);
                int loanPeriod = Integer.parseInt(args[2]);
                String loanId = args[3];

                List<SettDetailDo> settDetailDoList = settleCalculatorService.calSettDetail(loanAmount, annualRate,
                        loanPeriod, 1l);
                for (SettDetailDo settDetailDo : settDetailDoList) {
                    String sql = "UPDATE td_fund_pre_repayment SET capital=0.00,interest=0.00,fee_amount=0.00, pre_capital = #{pre_capital},pre_interest = #{pre_interest},pre_fee_charge=#{pre_fee_charge},pre_repay_amount=#{pre_repay_amount} where loan_id = #{loan_id} and repay_times=#{repay_times};";

                    sql = sql.replace("#{pre_capital}", df.format(settDetailDo.getPrincipal()));
                    sql = sql.replace("#{pre_interest}", df.format(settDetailDo.getInterest()));
                    double feeAmount = CalculateUtils.add(settDetailDo.getConsultFee(), settDetailDo.getServFee());
                    sql = sql.replace("#{pre_fee_charge}", df.format(feeAmount));
                    double repayAmount = CalculateUtils.add(
                            CalculateUtils.add(settDetailDo.getPrincipal(), settDetailDo.getInterest()), feeAmount);
                    sql = sql.replace("#{pre_repay_amount}", df.format(repayAmount));
                    sql = sql.replace("#{loan_id}", loanId);
                    sql = sql.replace("#{repay_times}", settDetailDo.getPeriod() + "");
                    System.out.println(sql);
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * E  personal center call
     */
	@Override
	public List<SettDetailDo> calSettDetailForRepayPlanShow(Double loanAmount,
			Double annualRate, Integer loanPeriod, Long schemeId, Date startDate) {
		SettSchemeDo settSchemeDo = settSchemeComponent.getBySchemeId(schemeId);
        return SettleCalculatorUtils.calSettDetailForRepayPlanShow(loanAmount, loanPeriod, annualRate, settSchemeDo,startDate);
	}

}
