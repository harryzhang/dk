/*package com.hehenian.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


*//**
 * 日期操作类
 * @author zhouziyang
 *
 *//*
public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	
	public static final String[] DATE_PATTERNS = new String[]{"yyyy-MM","yyyyMM","yyyy/MM",   
            "yyyyMMdd","yyyy-MM-dd","yyyy/MM/dd",   
            "yyyyMMddHHmmss",   
                        "yyyy-MM-dd HH:mm:ss",   
                        "yyyy/MM/dd HH:mm:ss","yyyyMMddHHmmssSSS"};

	*//**
	 * yyyy-MM-dd
	 *//*
	public static final String DATE_FORMAT = DATE_PATTERNS[4];
	
	*//**
	 * yyyyMMdd
	 *//*
	public static final String DATE_FORMAT_2 = DATE_PATTERNS[3];
	
	*//**
	 * yyyy-MM-dd HH:mm:ss
	 *//*
	public static final String DATE_TIME_FORMAT = DATE_PATTERNS[7];
	
	public static final String DATE_FORMAT_9 = DATE_PATTERNS[9];
	
	public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛",
		"虎", "兔", "龙", "蛇", "马", "羊" };

	public static final String[] constellationArr = { "水瓶座", "双鱼座", "白羊座",
			"金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };
	
	public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22,
			23, 23, 23, 23, 22, 22 };
	
	*//**
	 * 根据日期获取生肖
	 * 
	 * @return
	 *//*
	public static String date2Zodica(Calendar time) {
		return zodiacArr[time.get(Calendar.YEAR) % 12];
	}
	
	*//**
	 * 根据日期获取星座
	 * 
	 * @param time
	 * @return
	 *//*
	public static String date2Constellation(Calendar time) {
		int month = time.get(Calendar.MONTH);
		int day = time.get(Calendar.DAY_OF_MONTH);
		if (day < constellationEdgeDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArr[month];
		}
		// default to return 魔羯
		return constellationArr[11];
	}
	
	*//**
	 * 通过出生日期计算出一个人的年龄
	 * 
	 * @param date
	 * @return
	 *//*
	public static int getAge(Date memberBirthday) {
		if (memberBirthday == null)
			return -1;
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(memberBirthday.getTime());

		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
		birthday.add(Calendar.YEAR, age);

		if (today.before(birthday)) {
			age--;
		}
		//未满18岁的当18岁
        return age<18 ? 18 : age;
	}
	
	*//**
	 * 判断两个日期是否为同一天
	 * @param loginDate
	 * @param lastLoginDate
	 * @return
	 *//*
	public static boolean isSameDay(Date loginDate,Date lastLoginDate){
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		//System.out.println(df.format(loginDate)+","+(df.format(lastLoginDate)));
		if(df.format(loginDate).equals(df.format(lastLoginDate))){
			return true;
		}
		return false;
	}
	
	*//**  
     * 计算两个日期之间相差的天数  
     * @param date1  
     * @param date2  
     * @return  
     *//*  
    public static int daysBetween(Date date1,Date date2){   
        Calendar cal = Calendar.getInstance();   
        cal.setTime(date1);   
        long time1 = cal.getTimeInMillis();                
        cal.setTime(date2);   
        long time2 = cal.getTimeInMillis();        
        long between_days=(time1-time2)/(1000*3600*24);   
        return Integer.parseInt(String.valueOf(between_days));          
    }  

    public static boolean is1970(Date date){
    	DateFormat df = new SimpleDateFormat("yyyyMMdd");
    	if(df.format(date).equals("19700101")){
    		return true;
    	}
    	return false;
    }
	
    *//**
     * 返回当前时间
     *
     * @return 返回当前时间
     *//*
    public static Date getCurrentDateTime() {
            java.util.Calendar calNow = java.util.Calendar.getInstance();
            java.util.Date dtNow = calNow.getTime();
            return dtNow;
    }
   
	*//**
	 * @return 返回今天日期，时间部分为0。例如：2006-4-8 00:00:00
	 *//*
	public static Date getTodayStart() {
	    return truncate(new Date(), Calendar.DATE);
	}
	
	*//**
	 * @return 返回今天日期，时间部分为23:59:59.999。例如：2006-4-19 23:59:59.999
	 *//*
	public static Date getTodayEnd() {
	    return getDayEnd(new Date());
	}
	
	*//**
	 * 将字符串转换为日期（不包括时间）
	 * @param dateString "yyyy-MM-dd"格式的日期字符串
	 * @return 日期
	 *//*
	public static Date getDateFromStr(String dateString) {
		try {
			return parseDate(dateString,DATE_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getDateFormStr_yyyy_MM_dd(String dateString){
		try {
			return parseDate(dateString,DATE_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	*//**
	 * 检查字符串是否为日期格式yyyy-MM-dd
	 * @param dateString
	 * @return true=是；false=否
	 *//*
	public static boolean checkDateString(String dateString) {
	    return (getDateFromStr(dateString)!=null);
	}
	
	*//**
	 * 将字符串转换为日期（包括时间）
	 * @param dateString "yyyy-MM-dd HH:mm:ss"格式的日期字符串
	 * @return 日期时间
	 *//*
	public static Date getDateTimeFromStr(String dateTimeString) {
		try {
			return parseDate(dateTimeString,DATE_TIME_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	*//**
	 * 检查字符串是否为日期时间格式yyyy-MM-dd HH:mm:ss
	 * @param dateString
	 * @return true=是；false=否
	 *//*
	public static boolean checkDateTimeString(String dateTimeString) {
	    return (getDateTimeFromStr(dateTimeString)!=null);
	}
	
	*//**
	 * 将日期转换为指定的pattern
	 * @param when
	 * @param pattern
	 * @return
	 *//*
	public static String formatDate(Date when, String pattern){
		return DateFormatUtils.format(when, pattern);
	}

	*//**
	 * 将日期转换成yyyy-MM-dd格式
	 * @param when
	 * @return
	 *//*
	public static String formatDate_1(Date when){
		return DateFormatUtils.format(when, DATE_FORMAT);
	}
	public static String formatDate_yyyy_MM_dd(Date when){
		return DateFormatUtils.format(when, DATE_FORMAT);
	}
	*//**
	 * 将日期转换成yyyyMMdd格式
	 * @param when
	 * @return
	 *//*
	public static String formatDate_2(Date when){
		return DateFormatUtils.format(when, DATE_FORMAT_2);
	}
	
	*//**
	 * 将日期转换成yyyy-MM-dd HH:mm:ss格式
	 * @param when
	 * @return
	 *//*
	public static String formatDateTime(Date when){
		return DateFormatUtils.format(when, DATE_TIME_FORMAT);
	}
	public static String formatDateTime_YYYY_MM_DD_HH_MM_SS_SSS(Date when){
		return DateFormatUtils.format(when, DATE_FORMAT_9);
	}
	*//**
	 * 获取月底的时间
	 * @param year 年 4位年度
	 * @param month 月 1~12
	 * @return 月底的Date对象。例如：2006-3-31 23:59:59.999
	 *//*
	public static Date getMonthEnd(int year, int month) {
	    StringBuffer sb = new StringBuffer(10);
	    Date date;
	    if (month<12) {
	        sb.append(Integer.toString(year));
	        sb.append("-");
	        sb.append(month+1);
	        sb.append("-1");
	        date = getDateFromStr(sb.toString());
	    }else{
	        sb.append(Integer.toString(year+1));
	        sb.append("-1-1");
	        date = getDateFromStr(sb.toString());
	    }
	    date.setTime(date.getTime() - 1);
	    return date;
	}
	
	*//**
	 * 获取月底
	 * @param when 要计算月底的日期
	 * @return 月底的Date对象。例如：2006-3-31 23:59:59.999
	 *//*
	public static Date getMonthEnd(Date when) {
	    //Assert.notNull(when,"date must not be null !");
		if(when==null){
			return null;
		}
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(when);
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH)+1;
	    return getMonthEnd(year,month);
	}
	
	*//**
	 * 获取给定日的最后一刻。
	 * @param when 给定日
	 * @return 最后一刻。例如：2006-4-19 23:59:59.999
	 *//*
	public static Date getDayEnd(Date when) {
	    Date date = truncate(when,Calendar.DATE);
	    date = addDays(date,1);
	    date.setTime(date.getTime() - 1);
	    return date;
	}
	
	*//**
	 * 获取给定日的第0刻。
	 * @param when 给定日
	 * @return 第0刻。例如：2006-4-19 00:00:00
	 *//*
	public static Date getDay(Date when) {
	    Date date = truncate(when,Calendar.DATE);
	    //date = addDays(date,-1);
	    //date.setTime(date.getTime()+1);
	    return date;
	}
	
	*//**
	 * 日期加法
	 * @param when 被计算的日期
	 * @param field the time field. 在Calendar中定义的常数，例如Calendar.DATE
	 * @param amount 加数
	 * @return 计算后的日期
	 *//*
	public static Date add(Date when, int field, int amount) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(when);
	    calendar.add(field,amount);
	    return calendar.getTime();
	}
	
	*//**
	 * 计算给定的日期加上给定的天数。
	 * @param when 给定的日期
	 * @param amount 给定的天数
	 * @return 计算后的日期
	 *//*
	public static Date addDays(Date when, int amount) {
	    return add(when,Calendar.DAY_OF_YEAR,amount);
	}
	
	*//**
	 * 计算给定的日期加上给定的月数。
	 * @param when 给定的日期
	 * @param amount 给定的月数
	 * @return 计算后的日期
	 *//*
	public static Date addMonths(Date when, int amount) {
	    return add(when,Calendar.MONTH,amount);
	}
	
	*//**
	 * 计算给定的日期加上给定的年数。
	 * @param when 给定的日期
	 * @param amount 给定的年数
	 * @return 计算后的日期
	 *//*
	public static Date addYears(Date when, int amount) {
		return add(when,Calendar.YEAR,amount);
	}
	
	*//**
	 * 计算两个日期之间的差
	 * @param date1
	 * @param date2
	 * @return
	 * date1 - date2
	 *//*
	public static int getSeconds(Date date1, Date date2){
		if(date1==null || date2==null){
			try {
				throw new Exception("DateUtils.java --> getSeconds()'s args is null");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		return (int) ((time1-time2)/1000);
	}
	
    *//**
     * 当月第一天
     * @return
     *//*
    public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }
    
    *//**
     * 当月最后一天
     * @return
     *//*
    public static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();

    }
    //当前的日期是多少
    public static int getNowDate(){
    	Calendar calendar = Calendar.getInstance();
    	int date = calendar.get(Calendar.DATE);
    	return date;
    }
    
    public static long timeDiff(Date date1,Date date2){
    	Calendar cal = Calendar.getInstance();   
        cal.setTime(date1);   
        long time1 = cal.getTimeInMillis();                
        cal.setTime(date2);   
        long time2 = cal.getTimeInMillis();
        return time1-time2;
    }
    public static int getNowDays(){
    	Date date = new Date();
    	return date.getDate();
    }
    public static void main(String args[]){
    	
    	//System.out.println(getDay(new Date()));
    	Calendar calendar = Calendar.getInstance();
    	int date = calendar.get(Calendar.DATE);
    	System.out.println(getNowDays());
    }
	
//	public static void main(String args[]){
////		Date date1 = getDateTimeFromStr("2014-01-20 21:00:01");
////		Date date2 = getDateTimeFromStr("2014-01-20 20:00:00");
////		System.out.println(getSeconds(date1, date2));
//		
//		System.out.println(getDayEnd(new Date()));
//	}
}
*/