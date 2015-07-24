package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sp2p.dao.AwardMonthDao;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;

/**
 * 经济人提成 按经济人月流量计算提成
 * 
 * 
 */
public class AgentcommissionService extends BaseService {
	public static Log log = LogFactory.getLog(AgentcommissionService.class);
	private AwardMonthDao awardMonthDao;
	
	public void setAwardMonthDao(AwardMonthDao awardMonthDao) {
		this.awardMonthDao = awardMonthDao;
	}
	/**
	 * 每月调用一次去统t_award表中的数据
	 * @throws SQLException 
	 * @throws ParseException 
	 * 
	 */
	public void AddAgentcommission() throws Exception, ParseException{
		Connection 	conn = MySQL.getConnection();
		try {
			String month = getlastDateDate();
			//经济人统计
			DataSet dateset = MySQL.executeQuery(conn, " select sum(level2money) as moneys,level2userId from t_award where DATE_FORMAT(addDate,'%Y-%m-%d') >= '"
														+ getfirstDate()
														+ "' AND DATE_FORMAT(addDate,'%Y-%m-%d') <= '"
														+ getlastDateDate()
														+ "'  GROUP BY level2userId " );
			dateset.tables.get(0).rows.genRowsMap();
			List<Map<String,Object>> awardMaplist =   dateset.tables.get(0).rows.rowsMap;
			if(awardMaplist!=null){
				for(Map<String,Object> awardmap :awardMaplist ){
					double moneys  =   Convert.strToDouble(awardmap.get("moneys")+"", 0);
					long level2userId = Convert.strToLong(awardmap.get("level2userId")+"", -12); 
					if(moneys!=0||level2userId!=-12){
					awardMonthDao.addUserAwardMonth(conn, level2userId, moneys, 2, 1,month);//其中2表示 是经纪人，1 表示未月结
					}
				}
			}
			//团队长统计
			DataSet dateset1 = MySQL.executeQuery(conn, " select sum(level1money) as moneys,level1userId from t_award where DATE_FORMAT(addDate,'%Y-%m-%d') >= '"
														+ getfirstDate()
														+ "' AND DATE_FORMAT(addDate,'%Y-%m-%d') <= '"
														+ getlastDateDate()
														+ "' GROUP BY level1userId" );
			dateset1.tables.get(0).rows.genRowsMap();
			List<Map<String,Object>> awardMaplist1 =   dateset1.tables.get(0).rows.rowsMap;
			if(awardMaplist!=null){
				for(Map<String,Object> awardmap1 :awardMaplist1 ){
					double moneys  =   Convert.strToDouble(awardmap1.get("moneys")+"", 0);
					long level2userId = Convert.strToLong(awardmap1.get("level1userId")+"", -12); 
					if(moneys!=0||level2userId!=-12){
					awardMonthDao.addUserAwardMonth(conn, level2userId, moneys, 1, 1,month);//其中2表示 是经纪人，1 表示未月结
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			conn.close();
		}
		//查询表中的数据
	
		
		
	}

	/**
	 * 获取上一个月的最后一天
	 * 
	 * @return
	 */
	public String getlastDateDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDateDate = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(lastDateDate);
	}

	/**
	 * 获取上一个月的第一天
	 * 
	 * @return
	 */
	public String getfirstDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(firstDate);
	}
	
	public Date getfirstDate2() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return	 cal.getTime();
	}

	/**
	 * 获取本月第一天
	 * 
	 * @return 当前月第一天的日期
	 */
	public String getcurentMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		Calendar f = (Calendar) cal.clone();
		f.clear();
		f.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		f.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		String firstday = new SimpleDateFormat("yyyy-MM-dd").format(f
				.getTime());
		return firstday;

	}

	/**
	 * 获取本月最后一天
	 * 
	 * @return 当前月最后一天的日期
	 */
	public String getcurentMonthLastDay() {
		Calendar cal = Calendar.getInstance();
		Calendar l = (Calendar) cal.clone();
		l.clear();
		l.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		l.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		l.set(Calendar.MILLISECOND, -1);
		String lastday = new SimpleDateFormat("yyyy-MM-dd").format(l
				.getTime());
		return lastday;

	}

}
