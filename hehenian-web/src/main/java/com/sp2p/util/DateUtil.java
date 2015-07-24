/**
 * 
 */
package com.sp2p.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.shove.Convert;

/**
 * 时间工具类
 * 
 * @author Administrator
 * 
 */
public class DateUtil {

	public final static DateFormat YYYY_MM_DD_MM_HH_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public final static DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

	public final static DateFormat YYYYMMDDMMHHSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public final static DateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

	public final static DateFormat HHMMssSSS = new SimpleDateFormat("HHmmssSSS");

	public static final DateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	
	public static final DateFormat YYMMDD = new SimpleDateFormat("yyMMdd");

	/**
	 * 时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return YYYY_MM_DD_MM_HH_SS.format(date);
	}

	public static Date strToDate(String dateString) {
		Date date = null;
		try {
			date = YYYY_MM_DD_MM_HH_SS.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	* 时间转换为时间戳
	* 
	* @param format
	* @param date
	* @return
	* @throws ParseException
	*/
	public static String getTimeCurS(String format, Date date) throws ParseException {
	        SimpleDateFormat sf = new SimpleDateFormat(format);
	return Convert.strToStr(sf.parse(sf.format(date)).getTime() + "", "");
	}

	/**
	 * 获取当前时间 HHmmssSSS加一个随机数的10位数字符串
	 */
	public static String getTrxNumber() {
		return HHMMssSSS.format(new Date()) + (int) (Math.random() * 10);
	}

	public static Date strToYYMMDDDate(String dateString) {
		Date date = null;
		try {
			date = YYYY_MM_DD.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 计算两个时间之间相差的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long diffDays(Date startDate, Date endDate) {
		long days = 0;
		long start = startDate.getTime();
		long end = endDate.getTime();
		// 一天的毫秒数1000 * 60 * 60 * 24=86400000
		days = (end - start) / 86400000;
		return days;
	}

	/**
	 * 日期加上月数的时间
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateAddMonth(Date date, int month) {
		return add(date, Calendar.MONTH, month);
	}

	/**
	 * 日期加上天数的时间
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date dateAddDay(Date date, int day) {
		return add(date, Calendar.DAY_OF_YEAR, day);
	}

	/**
	 * 日期加上年数的时间
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date dateAddYear(Date date, int year) {
		return add(date, Calendar.YEAR, year);
	}

	/**
	 * 计算剩余时间 (多少天多少时多少分)
	 * 
	 * @param startDateStr
	 * @param endDateStr
	 * @return
	 */
	public static String remainDateToString(Date startDate, Date endDate) {
		StringBuilder result = new StringBuilder();
		if (endDate == null) {
			return "过期";
		}
		long times = endDate.getTime() - startDate.getTime();
		if (times < -1) {
			result.append("过期");
		} else {
			long temp = 1000 * 60 * 60 * 24;
			// 天数
			long d = times / temp;

			// 小时数
			times %= temp;
			temp /= 24;
			long m = times / temp;
			// 分钟数
			times %= temp;
			temp /= 60;
			long s = times / temp;

			result.append(d);
			result.append("天");
			result.append(m);
			result.append("小时");
			result.append(s);
			result.append("分");
		}
		return result.toString();
	}

	private static Date add(Date date, int type, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return calendar.getTime();
	}

	/**
	 * @MethodName: getLinkUrl
	 * @Param: DateUtil flag ： true 转换 false 不转换
	 * @Author: gang.lv
	 * @Date: 2013-5-8 下午02:52:44
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public static String getLinkUrl(boolean flag, String content, String id) {
		if (flag) {
			content = "<a href='finance.do?id=" + id + "'>" + content + "</a>";
		}
		return content;
	}

	/**
	 * 时间转换为时间戳
	 * 
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long getTimeCur(String format, String date) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.parse(sf.format(date)).getTime();
	}

	/**
	 * 时间转换为时间戳
	 * 
	 * @param format
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long getTimeCur(String format, Date date) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.parse(sf.format(date)).getTime();
	}

	/**
	 * 获取当前年份
	 */
	public static String getTimeYear() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return df.format(new Date()).substring(0, 4);// new Date()为获取当前系统时间
	}

	/**
	 * 将时间戳转为字符串
	 * 
	 * @param cc_time
	 * @return
	 */
	public static String getStrTime(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	/**
	 * 获取某个时间距离当前时间的秒数
	 * 
	 * @param date
	 * @return
	 */
	public static Long getMsecondsDiff(Date date) {
		Long secend = date.getTime();
		secend -= System.currentTimeMillis();
		return secend / 1000;
	}

	/**
	 * 将时间转化为yyyyMMdd格式
	 */
	public static String dateToYMD(Date date) {
		return YYYYMMDD.format(date);
	}
	
	public static String dateFormartYMD(Date date) {
		return YYYY_MM_DD.format(date);
	}
	
	/**
	 * 获取这周星期一
	 * @return
	 * @throws ParseException
	 */
    public static String getMonDay() throws ParseException {
    	Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return YYYY_MM_DD.format(cal.getTime());
    }
    
    /**
     * 通过星期一获取其他的星期几
     * @param currentDate
     * @return
     * @throws ParseException
     */
    public static String getWeekDay(String currentDate,int days) throws ParseException {
    	Calendar cal = Calendar.getInstance();
		cal.setTime(YYYY_MM_DD.parse(currentDate));
		cal.add(cal.DATE, days);
    	return YYYY_MM_DD.format(cal.getTime());
    }
    
    /**
     * 一个月的第一天
     * @return
     */
    public static String getMonthFirstDay() {
    	  Calendar cal = Calendar.getInstance();
    	  cal.set(Calendar.DAY_OF_MONTH, 1);
    	  return YYYY_MM_DD.format(cal.getTime());
    }
    
    /**
     * 一个月的最后一天
     * @return
     */
    public static String getMonthLastDay() {
  	  Calendar cal = Calendar.getInstance();
  	  cal.set(Calendar.DAY_OF_MONTH, 1);
  	  cal.add(Calendar.MONTH, 1);    //加一个月
  	  cal.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
  	  return YYYY_MM_DD.format(cal.getTime());
  }
    
   
  public static int getCurrentMonth() {
	   Calendar cal = Calendar.getInstance();
	   return cal.get(Calendar.MONTH) + 1;
  }
    
   

    
}
