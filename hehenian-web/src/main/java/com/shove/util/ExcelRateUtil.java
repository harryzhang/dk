package com.shove.util;

import java.text.DecimalFormat;

public class ExcelRateUtil {

	/**
	 * 按月还款
	 * excel 中rate()函数
	 * @param a 现值
	 * @param b 年金
	 * @param c 期数
	 * @param cnt 循环次数
	 * @param ina 精确到小数点后10位
	 * @return
	 */
	public static double excelRate(double a,double b,double c,int cnt,int ina){  
	    double rate = 1,x,jd = 0.1,side = 0.1,i = 1;  
	    do{  
	        x = a/b - (Math.pow(1+rate, c)-1)/(Math.pow(rate+1, c)*rate);  
	        if(x*side>0){side = -side;jd *=10;}  
	        rate += side/jd;  
	    }while(i++<cnt&&Math.abs(x)>=1/Math.pow(10, ina));  
	    if(i>cnt)return Double.NaN;  
	    return rate;  
	}  
	
	/**
	 * 每月付息，到期还本
	 * @param planTotal
	 * @param putIn
	 * @param time 月份
	 * 年平均收益率 = (预计到期本金收益和 / 计划投资金额) ^ (1 / 投资年限) - 1
	 * @return
	 */
	public static double rateTotal(double planTotal,double putIn,int time){  
		float year = time*1.0f / 12;//月份转换成年
	    
		double rate = Math.pow(planTotal/putIn, 1/year) - 1;
	    return rate;  
	}  
}
