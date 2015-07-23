package com.hehenian.biz.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 * 
 * @author liuzgmf
 *
 */
public class DateUtils {

	/**
	 * 常用时间格式
	 */
	public static final String Format_Date = "yyyy-MM-dd";
	public static final String Format_Date_back_slant = "yyyy/MM/dd";
	public static final String Format_Time = "HH:mm:ss";
	public static final String Format_DateTime = "yyyy-MM-dd HH:mm:ss";

	/** 默认的日期格式化器，格式为yyyy-MM-dd */
	private final static SimpleDateFormat DEFAULT_DATE_FORMATER = new SimpleDateFormat(
			"yyyy-MM-dd");

	/** 默认的时间格式化器，格式为yyyy-MM-dd HH:mm:ss */
	private final static SimpleDateFormat DEFAULT_DATETIME_FORMATER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 取得当前月份和偏离月份
	 * @author jiangwmf
	 * @return eg:2015/4~2015/6
	 * @throws ParseException
	 */
	 public static String getFutureMonth(int diverge){
		 return  getYearAfter(Calendar.MONTH,diverge)+ "-" + getMonthAfter(Calendar.MONTH,diverge);
	 }
	/**
	 * 取得当前日期（只有日期，没有时间，或者可以说是时间为0点0分0秒）
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getCurrentDate() throws ParseException {
		Date date = new Date();
		date = DEFAULT_DATE_FORMATER.parse(DEFAULT_DATE_FORMATER.format(date));//
		return date;
	}

	/**
	 * 取得当前时间（包括日期和时间）
	 * 
	 * @return 当前时间
	 */
	public static Date getCurrentDateTime() {
		Date date = new Date();
		return date;
	}

	/**
	 * 获取指定格式的当前系统日期时间
	 * 
	 * @param format
	 *            自定义日期格式器
	 * @return 前系统日期时间
	 */
	public static String getCurrentDateTime(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	/**
	 * 用默认的日期格式，格式化一个Date对象
	 * 
	 * @param date
	 *            待被格式化日期
	 * @return “yyyy-MM-dd”格式的日期字符串
	 */
	public static String formatDate(Date date) {
		return date == null ? "" : DEFAULT_DATE_FORMATER.format(date);
	}

	/**
	 * 根据传入的格式，将日期对象格式化为日期字符串
	 * 
	 * @param date
	 *            待被格式化日期
	 * @param format
	 *            自定义日期格式器
	 * @return 格式后的日期字符串
	 */
	public static String formatDate(Date date, String format) {
		String s = "";

		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			s = sdf.format(date);
		}

		return s;
	}

	/**
	 * 用默认的日期时间格式，格式化一个Date对象
	 * 
	 * @param date
	 *            待被格式化日期
	 * @return “yyyy-MM-dd HH:mm:ss”格式的日期时间字符串
	 */
	public static String formatTime(Date date) {
		return date == null ? "" : DEFAULT_DATETIME_FORMATER.format(date);
	}

	/**
	 * 根据传入的格式，将日期对象格式化为时间字符串
	 * 
	 * @param date
	 *            待被格式化日期
	 * @param format
	 *            自定义日期格式器
	 * @return 格式后的日期时间字符串
	 */
	public static String formatTime(Date date, String format) {
		String s = "";
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			s = sdf.format(date);
		}

		return s;
	}

	/**
	 * 获取指定天数后的日期
	 * 
	 * @param baseDate
	 *            基准日期
	 * @param day
	 *            后推天数
	 * @return 后推后的天数
	 */
	public static Date getDateAfter(Date baseDate, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(baseDate);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
	
	public static Date getMonthAfter(Date baseDate, int month) {
		Calendar now = Calendar.getInstance();
		now.setTime(baseDate);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
		return now.getTime();
	}

	/**
	 * 利用默认的格式（yyyy-MM-dd）将一个表示日期的字符串解析为日期对象
	 * 
	 * @param dateStr
	 *            待格式化日期字符串
	 * @return 格式化后日期对象
	 * @throws RuntimeException
	 */
	public static Date parseDate(String dateStr) {
		Date date = null;
		try {
			date = DEFAULT_DATE_FORMATER.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		return date;
	}
	
	public static Date parseDateOrNull(String dateStr,String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(dateStr);
		} catch (ParseException e) {
			return null;
		}

		return date;
	}

	/**
	 * 利用默认的格式（yyyy-MM-dd HH:mm:ss）将一个表示时间的字符串解析为日期对象
	 * 
	 * @param timeStr
	 *            时间字符串
	 * @return 格式化后的日期对象
	 * @throws ParseException
	 */
	public static Date parseTime(String timeStr) throws ParseException {
		return DEFAULT_DATETIME_FORMATER.parse(timeStr);
	}

	/**
	 * 将一个字符串，按照特定格式，解析为日期对象
	 * 
	 * @param datetimeStr
	 *            日期、时间、日期时间字符串
	 * @param format
	 *            自定义日期格式器
	 * @return 格式化后的日期对象
	 * @throws ParseException
	 */
	public static Date parseDateTime(String datetimeStr, String format)
			throws ParseException {
		Date date = null;
		try {
			date = (new SimpleDateFormat(format)).parse(datetimeStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		return date;
	}

	/**
	 * 得到当前年份
	 * 
	 * @return 当前年份
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 得到年份
	 * field - the calendar field.
	 * amount - the amount of date or time to be added to the field
	 * @return 
	 */
	public static int getYearAfter(int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.add(field, amount);
		return cal.get(Calendar.YEAR);
	}
	/**
	 * 得到当前月份（1至12）
	 * 
	 * @return 当前月份（1至12）
	 */
	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}
	/**
	 * 得到年份
	 * field - the calendar field.
	 * amount - the amount of date or time to be added to the field
	 * @return 
	 */
	public static int getMonthAfter(int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.add(field, amount);
		return cal.get(Calendar.MONTH)+1;
	}
	/**
	 * 获取yyyy-MM-dd格式的当前系统日期
	 * 
	 * @return 当前系统日期
	 */
	public static String getCurrentDateAsString() {
		return new SimpleDateFormat(Format_Date).format(new Date());
	}
	
	public static String getCurrentDateAsStringByBackSlant() {
		return new SimpleDateFormat(Format_Date_back_slant).format(new Date());
	}

	/**
	 * 获取指定格式的当前系统日期
	 * 
	 * @param format
	 *            自定义日期格式器
	 * @return 当前系统日期
	 */
	public static String getCurrentDateAsString(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	/**
	 * 获取HH:mm:ss格式的当前系统时间
	 * 
	 * @return 当前系统时间
	 */
	public static String getCurrentTimeAsString() {
		return new SimpleDateFormat(Format_Time).format(new Date());
	}

	/**
	 * 获取指定格式的当前系统时间
	 * 
	 * @param format
	 *            自定义日期格式器
	 * @return 当前系统时间
	 */
	public static String getCurrentTimeAsString(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	/**
	 * 获取格式为yyyy-MM-dd HH:mm:ss的当前系统日期时间
	 * 
	 * @return 当前系统日期时间
	 */
	public static String getCurrentDateTimeAsString() {
		return getCurrentDateTime(Format_DateTime);
	}

	/**
	 * 获取当前为星期几,从星期日~星期六对应的值是1~7
	 * 
	 * @return 星期几
	 * @date: 2013年12月31日下午3:35:08
	 */
	public static int getDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取指定日期为星期几,从星期日~星期六对应的值是1~7
	 * 
	 * @param date
	 *            指定日期
	 * @return 星期几
	 * @date: 2013年12月31日下午3:45:35
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取星期几的中文名称
	 * 
	 * @param date
	 *            指定日期
	 * @return 星期几
	 */
	public static String getChineseDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		return getChineseDayOfWeek(cal.getTime());
	}

	/**
	 * 获取星期几的中文名称
	 * 
	 * @param date
	 *            指定日期
	 * @return 星期几
	 */
	public static String getChineseDayOfWeek(String date) {
		return getChineseDayOfWeek(parseDate(date));
	}

	/**
	 * 获取星期几的中文名称
	 * 
	 * @param date
	 *            指定日期
	 * @return 星期几
	 */
	public static String getChineseDayOfWeek(Date date) {
		int dateOfWeek = getDayOfWeek(date);
		if (dateOfWeek == Calendar.MONDAY) {
			return "星期一";
		} else if (dateOfWeek == Calendar.TUESDAY) {
			return "星期二";
		} else if (dateOfWeek == Calendar.WEDNESDAY) {
			return "星期三";
		} else if (dateOfWeek == Calendar.THURSDAY) {
			return "星期四";
		} else if (dateOfWeek == Calendar.FRIDAY) {
			return "星期五";
		} else if (dateOfWeek == Calendar.SATURDAY) {
			return "星期六";
		} else if (dateOfWeek == Calendar.SUNDAY) {
			return "星期日";
		}
		return null;
	}

	/**
	 * 获取当天为几号
	 * 
	 * @return 几号
	 * @date: 2013年12月31日下午3:50:11
	 */
	public static int getDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期为几号
	 * 
	 * @param date
	 *            指定的日期
	 * @return 几号
	 * @date: 2013年12月31日下午3:50:40
	 */
	public static int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期所在月份的最后一天是几号
	 * 
	 * @param date
	 *            指定日期
	 * @return 指定日期所在月份的最后一天是几号
	 * @date: 2013年12月31日下午3:51:07
	 */
	public static int getMaxDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期所在月份的第一天
	 * 
	 * @param date
	 *            指定日期
	 * @return 指定日期所在月份的第一天
	 * @date: 2013年12月31日下午4:16:56
	 */
	public static String getFirstDayOfMonth(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat(Format_Date).format(cal.getTime());
	}
	
	
	/**
     * 获取指定日期所在月份的第一天
     * 
     * @param date
     *            指定日期
     * @return 指定日期所在月份的最后一天
     * @date: 2013年12月31日下午4:16:56
     */
    public static String getLastDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate(date));
        int dayOfMonth = getMaxDayOfMonth(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return new SimpleDateFormat(Format_Date).format(cal.getTime());
    }

	/**
	 * 获取当天为一年中第几天
	 * 
	 * @return 一年中第几天
	 * @date: 2013年12月31日下午4:03:57
	 */
	public static int getDayOfYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取指定日期为一年中第几天
	 * 
	 * @param date
	 *            指定日期
	 * @return 一年中第几天
	 * @date: 2013年12月31日下午4:04:21
	 */
	public static int getDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取指定日期为星期几,从星期日~星期六对应的值是1~7
	 * 
	 * @param date
	 *            指定日期
	 * @return 星期几
	 * @date: 2013年12月31日下午3:45:35
	 */
	public static int getDayOfWeek(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取指定日期为几号
	 * 
	 * @param date
	 *            指定的日期
	 * @return 几号
	 * @date: 2013年12月31日下午3:50:40
	 */
	public static int getDayOfMonth(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * * 根据传过来的时间和时间格式<br>
	 * 以及传过来对应当前传过来时间的前后n天来做处理<br>
	 * 返回一个你想要的天数时间<br>
	 * 例如：String generatedTime = getEveryTime("yyyy-MM-dd HH:mm:ss",new Date(),-30);<br>
	 * Date = 2013-09-12 17:07:37<br>
	 * simpleDateFormat = yyyy-MM-dd HH:mm:ss<br>
	 * 想取的前30天的这个时间   2013-08-13 17:07:37<br>
     * @return String 
     * @auther liminglmf
	 * @date 2015年6月9日
	 * @param simpleDateFormat 时间格式
	 * @param date 比较的时间
	 * @param dateCount   相差的天数
     * @throws
     */
	
	public static String getEveryTime(String simpleDateFormat,Date date,int dateCount ){
        Date beforeDate = new Date();
        //得到日历
        Calendar calendar = Calendar.getInstance(); 
        //把当前时间赋给日历
        calendar.setTime(date);
        //设置为前后n天
        calendar.add(Calendar.DAY_OF_MONTH, dateCount);  
        //得到前后n天的时间
        beforeDate = calendar.getTime();   
        //设置时间格式
        SimpleDateFormat sdf = new SimpleDateFormat(simpleDateFormat); 
        //格式化前后n天时间
        String generatedTime = sdf.format(beforeDate);    
        //格式化当前时间
        //String nowDate = sdf.format(date); 
        //System.out.println("当天的时间是：" + nowDate); 
        //System.out.println("生成的时间是：" + generatedTime);
        return generatedTime;
        
	}
	
	/**
	 * 获取指定日期为一年中第几天
	 * 
	 * @param date
	 *            指定日期
	 * @return 一年中第几天
	 * @date: 2013年12月31日下午4:04:21
	 */
	public static int getDayOfYear(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 时间戳转换:把距离GMT时间的毫秒数转为日期，中国处在东八区，所以是：GMT时间+8小时
	 * 
	 * @param time
	 *            距离GTM时刻的毫秒数
	 * @return 获取到的北京时区日期时间字符串
	 */
	public static String longTimeToDateTimeString(Long time) {
		SimpleDateFormat format = new SimpleDateFormat(Format_DateTime);
		String d = format.format(time);
		return d;
	}

	/**
	 * 时间戳转换:把距离GMT时间的毫秒数转为日期，中国处在东八区，所以是：GMT时间+8小时
	 * 
	 * @param time
	 *            距离GTM时刻的毫秒数
	 * @return 获取到的北京时区日期时间对象
	 */
	public static Date longTimeToDateTime(Long time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(Format_DateTime);
		String d = format.format(time);
		return parseTime(d);
	}
}
