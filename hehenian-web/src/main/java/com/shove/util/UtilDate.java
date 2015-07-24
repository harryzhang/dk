package com.shove.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/* *
 *类名：UtilDate
 *功能：自定义订单类
 *详细：工具类，可以用作获取系统日期、订单编号等
 *版本：3.2
 *日期：2011-03-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class UtilDate {

	/** 年月日时分秒(无下划线) yyyyMMddHHmmss */
	public static final String dtLong = "yyyyMMddHHmmss";

	/** 完整时间 yyyy-MM-dd HH:mm:ss */
	public static final String simple = "yyyy-MM-dd HH:mm:ss";

	/** 年月日(无下划线) yyyyMMdd */
	public static final String dtShort = "yyyyMMdd";

	/** 年月日(下划线) yyyy-MM-dd */
	public static final String _dtShort = "yyyy-MM-dd";

	public static final String year ="yyyy";
	/**
	 * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
	 * 
	 * @return 以yyyyMMddHHmmss为格式的当前系统时间
	 */
	public static String getOrderNum() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtLong);
		return df.format(date);
	}

	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateFormatter() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(simple);
		return df.format(date);
	}

	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDate() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtShort);
		return df.format(date);
	}

	/**
	 * 产生随机的三位数
	 * 
	 * @return
	 */
	public static String getThree() {
		Random rad = new Random();
		return rad.nextInt(1000) + "";
	}

	
	/**   
	 * @MethodName: getMonthFirstDay  
	 * @Param: UtilDate  
	 * @Author: gang.lv
	 * @Date: 2013-3-22 下午07:14:34
	 * @Return:    
	 * @Descb: 获取当月的第一天
	 * @Throws:
	*/
	public static String getMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		Calendar f = (Calendar) cal.clone();
		f.clear();
		f.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		f.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		String firstday = new SimpleDateFormat("yyyy-MM-dd")
				.format(f.getTime());
		firstday = firstday +" 00:00:00";
		return firstday;

	}

	
	/**   
	 * @MethodName: getMonthLastDay  
	 * @Param: UtilDate  
	 * @Author: gang.lv
	 * @Date: 2013-3-22 下午07:14:41
	 * @Return:    
	 * @Descb: 获取当月的最后一天
	 * @Throws:
	*/
	public static String getMonthLastDay() {
		Calendar cal = Calendar.getInstance();
		Calendar l = (Calendar) cal.clone();
		l.clear();
		l.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		l.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		l.set(Calendar.MILLISECOND, -1);
		String lastday = new SimpleDateFormat("yyyy-MM-dd").format(l.getTime());
		lastday = lastday+" 23:59:59";
		return lastday;
	}
	 
	
	/**   
	 * @MethodName: getYesterDay  
	 * @Param: UtilDate  
	 * @Author: gang.lv
	 * @Date: 2013-4-14 上午01:22:46
	 * @Return:    
	 * @Descb: 获取昨天日期
	 * @Throws:
	*/
	public static Date getYesterDay(){
		 Date date = new Date();
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.add(Calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		 return calendar.getTime();
	}
}
