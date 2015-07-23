package com.hehenian.biz.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * 数字格式化
 * NumberUtil
 * 
 * harry
 * 2014年9月22日 下午3:58:45
 * 
 * @version 1.0.0
 *
 */
public class NumberUtil {
	
	/**
	 * 默认两位小数
	 */
	private final static String PATTERN_TWO = "##0.00";
	
	private static DecimalFormat  df =  new DecimalFormat(PATTERN_TWO);
	
	/**
	 * 
	 * formatDouble 格式化小数，默认保留两位小数
	 * @param v1 需要被格式化的数字
	 * @return  格式后的字符串
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String formatDouble(double v1) {
		return formatDouble(v1,PATTERN_TWO);
	}
	
	/**
	 * 
	 * formatDouble 格式化小数，默认保留两位小数
	 * @param v1 需要被格式化的数字
	 * @return  格式后的字符串
	 *String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String formatDouble(double v1,String pattern) {
		df.applyPattern(pattern);
		return df.format(v1);
	}

	
	/**
     * 
     * formatDouble 格式化小数，默认保留两位小数
     * @param v1 需要被格式化的数字
     * @return  格式后的字符串
     *String
     * @exception 
     * @since  1.0.0
     */
    public static String formatBigDecimal(BigDecimal v1) {
        return formatDouble(v1,PATTERN_TWO);
    }
    
    /**
     * 
     * formatDouble 格式化小数，默认保留两位小数
     * @param v1 需要被格式化的数字
     * @return  格式后的字符串
     *String
     * @exception 
     * @since  1.0.0
     */
    public static String formatDouble(BigDecimal v1,String pattern) {
        df.applyPattern(pattern);
        return df.format(v1);
    }
    
    /** 
     * @param object
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年9月23日下午3:19:49
     */
    public static String formatObject(Object object) {
        if(null == object){return "";}
        if(object instanceof BigDecimal){
            return formatBigDecimal((BigDecimal)object);
        }else if(object instanceof Double){
            return formatDouble((Double)object);
        }else if(object instanceof Float){
            return formatDouble((Float)object);
        }else if(object instanceof Integer){
            return  ((Integer)(object)).toString();
        }else{
            return object.toString();
        }
    }

    /** 
     * 字符转double
     * @param string
     * @param i
     * @return  
     * @author: zhangyunhmf
     * @date: 2014年9月23日下午7:40:38
     */
    public static double strToDouble(String v1, double defaultValue) {
        double Result = defaultValue;

        try {
            Result = Double.parseDouble(v1);
        } catch (Exception e) {
        }

        return Result;
    }

}
