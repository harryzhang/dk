/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.settle
 * @Title: SettleCalculator.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:02:35
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.settle;

import java.util.Date;
import java.util.List;

import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.util.CalculateUtils;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午11:02:35
 */
public abstract class SettleCalculator {

    /**
     * 计算借贷结算明细信息
     * 
     * @param loanAmount
     * @param loanPeriod
     * @param annualRate
     * @return
     * @author: liuzgmf
     * @date: 2015年1月6日上午11:03:30
     */
    public List<SettDetailDo> calSettDetail(Double loanAmount, Integer loanPeriod, Double annualRate){
    	return calSettDetail(loanAmount,loanPeriod, annualRate, new Date());
    }
    
    public abstract List<SettDetailDo> calSettDetail(Double loanAmount,
			Integer loanPeriod, Double annualRate, Date startDate) ;

    /**
     * 计算月利率
     * 
     * @param annualRate
     * @return
     * @author: liuzgmf
     * @date: 2014年9月25日下午4:55:35
     */
    protected Double getMonthRate(Double annualRate) {
        return CalculateUtils.div(CalculateUtils.mul(annualRate, 0.01), 12);
    }
    
    
    public static void main(String args[]){
    	
    	System.out.println(CalculateUtils.div(CalculateUtils.mul(23.4, 0.01), 12));
    }

}
