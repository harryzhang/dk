/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.settle
 * @Title: SettleCalculatorUtils.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:01:49
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.settle;

import java.util.Date;
import java.util.List;

import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.GatherWay;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.RuleType;
import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo.SettleWay;
import com.hehenian.biz.common.util.CalculateUtils;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:01:49
 */
public class SettleCalculatorUtils {
    private static final SettleCalculator fpicSettleCalculator  = new FPICSettleCalculator();
    private static final SettleCalculator lpSettleCalculator    = new LPSettleCalculator();
    private static final SettleCalculator iifpSettleCalculator  = new IIFPSettleCalculator();
    private static final SettleCalculator mifpSettleCalculator  = new MIFPSettleCalculator();
    private static final SettleCalculator piSettleCalculator    = new PISettleCalculator();
    private static final SettleCalculator hhn24SettleCalculator = new HHN24SettleCalculator(); // 合和贷24期
    private static final SettleCalculator hhn36SettleCalculator = new HHN36SettleCalculator(); // 合和贷36期
    private static final SettleCalculator elSettleCalculator    = new ELSettleCalculator();   // 精英贷

    /**
     * 根据还款方式获取还款结算计算器
     * 
     * @param schemeCode
     * @return
     */
    public static SettleCalculator createSettleCalculator(SettleWay repayWay) {
        if (repayWay.equals(SettleWay.FPIC)) {
            return fpicSettleCalculator;
        } else if (repayWay.equals(SettleWay.LP)) {
            return lpSettleCalculator;
        } else if (repayWay.equals(SettleWay.IIFP)) {
            return iifpSettleCalculator;
        } else if (repayWay.equals(SettleWay.MIFP)) {
            return mifpSettleCalculator;
        } else if (repayWay.equals(SettleWay.PI)) {
            return piSettleCalculator;
        } else if (repayWay.equals(SettleWay.HHD24)) {
            return hhn24SettleCalculator;
        } else if (repayWay.equals(SettleWay.HHD36)) {
            return hhn36SettleCalculator;
        } else if (repayWay.equals(SettleWay.EL)) {
            return elSettleCalculator;
        } else {
            throw new RuntimeException("未知还款类型!");
        }
    }

    /**
     * 计算借贷的结算明细信息
     * 
     * @param loanAmount
     *            借款金额
     * @param loanPeriod
     *            借款期限
     * @param loanAnnualRate
     *            借款年利率
     * @param investAnnualRate
     *            投资年利率
     * @param settSchemeDo
     *            结算方案对象
     * @return
     * @author: liuzgmf
     * @date: 2015年1月6日上午10:50:35
     */
    public static List<SettDetailDo> calSettDetail(Double loanAmount, Integer loanPeriod, Double loanAnnualRate,
            Double investAnnualRate, SettSchemeDo settSchemeDo) {
        SettleCalculator repaySettleCalculator = createSettleCalculator(settSchemeDo.getRepayWay());
        SettleCalculator receiptSettleCalculator = createSettleCalculator(settSchemeDo.getReceiptWay());

        List<SettDetailDo> detailDoList = repaySettleCalculator.calSettDetail(loanAmount, loanPeriod,
                getLoanAnnualRate(loanAnnualRate, investAnnualRate, settSchemeDo));
        List<SettDetailDo> retDetailDoList = receiptSettleCalculator.calSettDetail(loanAmount, loanPeriod,
                investAnnualRate);
        if (detailDoList.size() != retDetailDoList.size()) {
            throw new RuntimeException("结算方案[" + settSchemeDo.getSchemeId() + "]还款方式和回款方式不匹配!");
        }
        // 计算咨询费，咨询费=还款利息-回款利息
        //咨询费 = 还款总额-回款总额 2015-3-30修改
        for (SettDetailDo detailDo : retDetailDoList) {
            for (SettDetailDo repaySettDetailDo : detailDoList) {
                if (detailDo.getPeriod().intValue() == repaySettDetailDo.getPeriod().intValue()) {


                    detailDo.setConsultFee(CalculateUtils.sub(
                            CalculateUtils.add(repaySettDetailDo.getInterest(), repaySettDetailDo.getPrincipal()),
                            CalculateUtils.add(detailDo.getInterest(), detailDo.getPrincipal())));

                    RuleType[] ruleTypes = new RuleType[] { RuleType.CREDIT_FEE };
                    double creditFee = calSettleFee(detailDo, settSchemeDo, loanAmount, loanPeriod, ruleTypes);
                    detailDo.setConsultFee(CalculateUtils.add(detailDo.getConsultFee(), creditFee));// 咨询费=咨询费+征信费
                    break;
                }
            }
        }

        // 计算服务费
        for (SettDetailDo detailDo : retDetailDoList) {
            // 计入服务费的类型包括借款手续费，其他费用
            RuleType[] ruleTypes = new RuleType[] { RuleType.SERV_FEE, RuleType.OTHER };
            double servFee = calSettleFee(detailDo, settSchemeDo, loanAmount, loanPeriod, ruleTypes);
            detailDo.setServFee(servFee);
        }

        return retDetailDoList;
    }

    /**
     * 还款计划表展示
     * @param loanAmount 借款金额
     * @param loanPeriod 借款期限
     * @param loanAnnualRate 借款年利率
     * @param settSchemeDo   结算方案对象
     * @return
     */
    public static List<SettDetailDo> calSettDetailForRepayPlanShow(Double loanAmount, Integer loanPeriod, Double loanAnnualRate,SettSchemeDo settSchemeDo,Date startDate){
    	SettleCalculator repaySettleCalculator = createSettleCalculator(settSchemeDo.getRepayWay());
    	List<SettDetailDo> detailDoList = repaySettleCalculator.calSettDetail(loanAmount, loanPeriod,loanAnnualRate,startDate);
    	for(SettDetailDo detailDo : detailDoList){
    		double servFee = calSettleFeeForRepayPlanShow(detailDo, settSchemeDo, loanAmount, loanPeriod);
    		detailDo.setInterest(CalculateUtils.add(detailDo.getInterest(),servFee));
    	}
    	return detailDoList;
    }
    
    /**
     * 放款后生成还款计划表 
     * @param loanAmount
     * @param loanPeriod
     * @param loanAnnualRate
     * @param investAnnualRate
     * @param settSchemeDo
     * @return
     */
    public static List<SettDetailDo> calSettDetailForRepayPlanRecord(Double loanAmount, Integer loanPeriod, Double loanAnnualRate,Double investAnnualRate,SettSchemeDo settSchemeDo){
    	SettleCalculator repaySettleCalculator = createSettleCalculator(settSchemeDo.getRepayWay());
    	SettleCalculator receiptSettleCalculator = createSettleCalculator(settSchemeDo.getReceiptWay());
    	
    	List<SettDetailDo> detailDoList = repaySettleCalculator.calSettDetail(loanAmount, loanPeriod,loanAnnualRate);
    	List<SettDetailDo> retDetailDoList = receiptSettleCalculator.calSettDetail(loanAmount, loanPeriod,investAnnualRate);
    	
    	 for(SettDetailDo detailDo : detailDoList) {
            for(SettDetailDo repaySettDetailDo : retDetailDoList) {
            	if(detailDo.getPeriod().intValue() == repaySettDetailDo.getPeriod().intValue()){
            		 detailDo.setConsultFee(CalculateUtils.sub(CalculateUtils.add(detailDo.getInterest(),detailDo.getPrincipal()), CalculateUtils.add(repaySettDetailDo.getInterest(),repaySettDetailDo.getPrincipal())));
            		 double servFee = calSettleFeeForRepayPlanRecord(detailDo, settSchemeDo, loanAmount, loanPeriod);
             		 detailDo.setInterest(CalculateUtils.add(detailDo.getInterest(),servFee));
            	}
            }
    	 }    
    	
    	for(SettDetailDo detailDo : detailDoList){
    		double servFee = calSettleFeeForRepayPlanRecord(detailDo, settSchemeDo, loanAmount, loanPeriod);
    		detailDo.setInterest(CalculateUtils.add(detailDo.getInterest(),servFee));
    	}
    	return detailDoList;
    }
    
    /**
     * 获取借款年利率
     * 
     * @param loanAnnualRate
     * @param investAnnualRate
     * @param settSchemeDo
     * @return
     * @author: liuzgmf
     * @date: 2015年1月8日上午10:40:33
     */
    private static Double getLoanAnnualRate(Double loanAnnualRate, Double investAnnualRate, SettSchemeDo settSchemeDo) {
        if (loanAnnualRate == null || CalculateUtils.lt(loanAnnualRate, investAnnualRate)) {
            return settSchemeDo.getDefaultAnnualRate();
        }
        return loanAnnualRate;
    }

    /**
     * 计算结算费用
     * 
     * @param settSchemeDo
     * @return
     * @author: liuzgmf
     * @date: 2015年1月6日下午1:58:44
     */
    private static double calSettleFee(SettDetailDo detailDo, SettSchemeDo settSchemeDo, Double loanAmount,
            Integer loanPeriod, RuleType[] ruleTypes) {
        double servFee = 0.00;
        if (settSchemeDo.getFeeRuleDoList() == null || settSchemeDo.getFeeRuleDoList().size() == 0) {
            return servFee;
        }
        for (FeeRuleDo feeRuleDo : settSchemeDo.getFeeRuleDoList()) {
            for (RuleType ruleType : ruleTypes) {
                if (!ruleType.equals(feeRuleDo.getRuleType())) {
                    continue;
                }
                
                //确定乘数， 是用剩余本金，还是借款金额
                double baseAmount = loanAmount;                
                if("2".equals(feeRuleDo.getBaseAmountType())){
                	baseAmount = detailDo.getRemainingPrincipal();
                }
                
                

                // 一次性按比例收取
                if (feeRuleDo.getGatherWay().equals(GatherWay.ONCE_RATIO) && detailDo.getPeriod().intValue() == 1) {
                    double gatherRate = CalculateUtils.div(feeRuleDo.getGatherRate(), 100);
                    servFee = CalculateUtils.add(servFee, CalculateUtils.mul(baseAmount, gatherRate));
                    continue;
                }
                // 一次性固定收取
                if (feeRuleDo.getGatherWay().equals(GatherWay.ONCE_FIXED) && detailDo.getPeriod().intValue() == 1) {
                    servFee = CalculateUtils.add(servFee, feeRuleDo.getFeeAmount());
                    continue;
                }
                // 每期按比例收取
                if (feeRuleDo.getGatherWay().equals(GatherWay.EACH_RATIO)) {
                    double gatherRate = CalculateUtils.div(feeRuleDo.getGatherRate(), 100);
                    servFee = CalculateUtils.add(servFee, CalculateUtils.mul(baseAmount, gatherRate));
                    continue;
                }
                // 每期固定收取
                if (feeRuleDo.getGatherWay().equals(GatherWay.EACH_FIXED)) {
                    servFee = CalculateUtils.add(servFee, feeRuleDo.getFeeAmount());
                    continue;
                }
                // 一次性比例收取（月度比例）
                if (feeRuleDo.getGatherWay().equals(GatherWay.ONCE_MONRATIO) && detailDo.getPeriod().intValue() == 1) {
                    double monthGatherRate = CalculateUtils.div(feeRuleDo.getGatherRate(), 100);

                    servFee = CalculateUtils.add(servFee,CalculateUtils.mul(CalculateUtils.mul(baseAmount, monthGatherRate), loanPeriod));

                    continue;
                }
            }
            
            if(feeRuleDo.getIsInclude().equalsIgnoreCase("T")){
            	double consultFee = detailDo.getConsultFee();
            	//咨询-手续费， 原来咨询费= 利率差，
            	//新的咨询费 = 利率差-0.2%手续费
            	double newConsultFee = CalculateUtils.sub(consultFee, servFee);
            	detailDo.setConsultFee(newConsultFee);
            }
            
        }
        return CalculateUtils.round(servFee, 2);
    }
    
    /**
     * 还款计划表展示 费用计算
     * @param detailDo
     * @param settSchemeDo
     * @param loanAmount
     * @param loanPeriod
     * @return
     */
    private static double calSettleFeeForRepayPlanShow(SettDetailDo detailDo, SettSchemeDo settSchemeDo, Double loanAmount,
            Integer loanPeriod) {
    	double servFee = 0.00;
        if (settSchemeDo.getFeeRuleDoList() == null || settSchemeDo.getFeeRuleDoList().size() == 0) {
            return servFee;
        }
        for (FeeRuleDo feeRuleDo : settSchemeDo.getFeeRuleDoList()){
        	if(feeRuleDo.getIsInitRepayPlanUse().equalsIgnoreCase("F")){ //不是初始还款计划表使用
        		continue;
        	}
        	if(feeRuleDo.getIsInclude().equalsIgnoreCase("T")){ //包含在年利率内
        		continue;
        	}
        	//确定乘数， 是用剩余本金，还是借款金额
            double baseAmount = loanAmount;                
            if("2".equals(feeRuleDo.getBaseAmountType())){
            	baseAmount = detailDo.getRemainingPrincipal();
            }
            
            double fee = calFee(detailDo,feeRuleDo,baseAmount,loanPeriod);
            servFee = CalculateUtils.add(servFee, fee);
           
        }
        return CalculateUtils.round(servFee, 2);
    }

    /**
     * 放款后 还款计划表 费用计算
     * @param detailDo
     * @param settSchemeDo
     * @param loanAmount
     * @param loanPeriod
     * @return
     */
    private static double calSettleFeeForRepayPlanRecord(SettDetailDo detailDo, SettSchemeDo settSchemeDo, Double loanAmount,
            Integer loanPeriod) {
    	double servFee = 0.00;
        if (settSchemeDo.getFeeRuleDoList() == null || settSchemeDo.getFeeRuleDoList().size() == 0) {
            return servFee;
        }
        for (FeeRuleDo feeRuleDo : settSchemeDo.getFeeRuleDoList()){
        	if(feeRuleDo.getIsInitRepayPlanUse().equalsIgnoreCase("F")){ //不是初始还款计划表使用
        		continue;
        	}
        	//确定乘数， 是用剩余本金，还是借款金额
            double baseAmount = loanAmount;                
            if("2".equals(feeRuleDo.getBaseAmountType())){
            	baseAmount = detailDo.getRemainingPrincipal();
            }
            double fee = calFee(detailDo,feeRuleDo,baseAmount,loanPeriod);
        	if(feeRuleDo.getIsInclude().equalsIgnoreCase("T")){ //包含在年利率内
        		detailDo.setConsultFee(CalculateUtils.sub(detailDo.getConsultFee(), servFee));
        	}else{
        		servFee = CalculateUtils.add(servFee, fee);
        	}
        	LoanRepaymentFeeDo loanRepaymentFeeDo = new LoanRepaymentFeeDo();
        	loanRepaymentFeeDo.setFeeAmount(fee);
        	loanRepaymentFeeDo.setFeeType(feeRuleDo.getRuleType().toString());
        	loanRepaymentFeeDo.setFeeName(feeRuleDo.getRuleName());
        	detailDo.getRfList().add(loanRepaymentFeeDo);
        }
        LoanRepaymentFeeDo loanRepaymentFeeDo = new LoanRepaymentFeeDo();
    	loanRepaymentFeeDo.setFeeAmount(detailDo.getConsultFee());
    	loanRepaymentFeeDo.setFeeType(FeeRuleDo.RuleType.CONSULT_FEE.toString());
    	loanRepaymentFeeDo.setFeeName("咨询费");
    	detailDo.getRfList().add(loanRepaymentFeeDo);
        return CalculateUtils.round(servFee, 2);
    }
    
    
    private static double calFee(SettDetailDo detailDo,FeeRuleDo feeRuleDo, double baseAmount,Integer loanPeriod){
    	double fee = 0d;
    	// 一次性按比例收取
        if(feeRuleDo.getGatherWay().equals(GatherWay.ONCE_RATIO) && detailDo.getPeriod().intValue() == 1) {
            double gatherRate = CalculateUtils.div(feeRuleDo.getGatherRate(), 100);
            fee = CalculateUtils.mul(baseAmount, gatherRate);
            return fee;
        }
        // 一次性固定收取
        if(feeRuleDo.getGatherWay().equals(GatherWay.ONCE_FIXED) && detailDo.getPeriod().intValue() == 1) {
        	fee = feeRuleDo.getFeeAmount();
        	return fee;
        }
        // 每期按比例收取
        if(feeRuleDo.getGatherWay().equals(GatherWay.EACH_RATIO)) {
            double gatherRate = CalculateUtils.div(feeRuleDo.getGatherRate(), 100);
            fee = CalculateUtils.mul(baseAmount, gatherRate);
            return fee;
        }
        // 每期固定收取
        if(feeRuleDo.getGatherWay().equals(GatherWay.EACH_FIXED)) {
        	fee = feeRuleDo.getFeeAmount();
        	return fee;
        }
        // 一次性比例收取（月度比例）
        if(feeRuleDo.getGatherWay().equals(GatherWay.ONCE_MONRATIO) && detailDo.getPeriod().intValue() == 1) {
            double monthGatherRate = CalculateUtils.div(feeRuleDo.getGatherRate(), 100);
            fee = CalculateUtils.mul(CalculateUtils.mul(baseAmount, monthGatherRate), loanPeriod);
            return fee;
        }
        return fee;
    }
    
    /**
     * 计算计算费用
     * 
     * @param transAmt
     *            交易金额
     * @param loanPeriod
     *            借款期限
     * @param feeRuleDo
     *            费用对象
     * @return
     * @author: liuzgmf
     * @date: 2015年1月12日下午5:59:28
     */
    public static double calSettleFee(Double transAmt, Integer loanPeriod, FeeRuleDo feeRuleDo) {
        // 一次性按比例收取
        if (feeRuleDo.getGatherWay().equals(GatherWay.ONCE_RATIO)) {
            double gatherRate = CalculateUtils.div(feeRuleDo.getGatherRate(), 100);
            return CalculateUtils.mul(transAmt, gatherRate);
        }
        // 一次性固定收取
        if (feeRuleDo.getGatherWay().equals(GatherWay.ONCE_FIXED)) {
            return feeRuleDo.getFeeAmount();
        }
        // 每期按比例收取
        if (feeRuleDo.getGatherWay().equals(GatherWay.EACH_RATIO)) {
            double gatherRate = CalculateUtils.div(feeRuleDo.getGatherRate(), 100);
            return CalculateUtils.mul(transAmt, gatherRate);
        }
        // 每期固定收取
        if (feeRuleDo.getGatherWay().equals(GatherWay.EACH_FIXED)) {
            return feeRuleDo.getFeeAmount();
        }
        // 一次性比例收取（月度比例）
        if (feeRuleDo.getGatherWay().equals(GatherWay.ONCE_MONRATIO)) {
            double monthGatherRate = CalculateUtils.div(feeRuleDo.getGatherRate(), 100);
            return CalculateUtils.mul(CalculateUtils.mul(transAmt, monthGatherRate), loanPeriod);
        }
        return 0d;
    }

	public static List<SettDetailDo> displayRepayDetail(Double loanAmount,
			Integer loanPeriod, Double loanAnnualRate, Double investAnnualRate,
			SettSchemeDo settSchemeDo) {
        SettleCalculator repaySettleCalculator = createSettleCalculator(settSchemeDo.getRepayWay());
        return repaySettleCalculator.calSettDetail(loanAmount, loanPeriod,
                getLoanAnnualRate(loanAnnualRate, investAnnualRate, settSchemeDo));
    }
	
	
	public static void main(String[] args) {
		SettSchemeDo settSchemeDo = new SettSchemeDo();
    	settSchemeDo.setRepayWay(SettSchemeDo.SettleWay.FPIC);    	
    	List<SettDetailDo> l = SettleCalculatorUtils.calSettDetailForRepayPlanShow(1000d, 12, 0.1, settSchemeDo,new Date());
    	for(SettDetailDo sd : l)
    	System.out.println(sd);
	}


}
