package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.dao.AwardMonthDao;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;

public class AwardMonthService extends  BaseService {
	public static Log log=LogFactory.getLog(AwardMonthService.class);
	private AwardMonthDao awardMonthDao;
	
	/**
	 * 团队长,经纪人结算
	 * @param pageBean
	 * @param startTime
	 * @param endTime
	 * @param tyle
	 * @param username
	 * @param realName
	 * @throws SQLException
	 */
	public void quereyGroupCloseMoneyInfo(PageBean<Map<String, Object>> pageBean,String startTime,String endTime,int tyle,String username,String realName) throws SQLException {
		StringBuffer condition = new StringBuffer();
		condition.append("  and type = "+tyle);
		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND userName  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(username.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName = '" + StringEscapeUtils.escapeSql(realName.trim()) + "'");
		}
		if (StringUtils.isNotBlank(startTime)) {
			condition.append(" AND moth >= '" + StringEscapeUtils.escapeSql(startTime.trim()) + "'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND moth >= '" + StringEscapeUtils.escapeSql(endTime.trim()) + "'");
		}
		
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_award_month", "*", "", condition.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}
	
	/**
	 * 团队长月结算 统计
	 * @param pageBean
	 * @param startTime
	 * @param endTime
	 * @param tyle
	 * @param username
	 * @param realName
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> quereyGroupCloseMoneySum(String startTime,String endTime,int tyle,String username,String realName) throws DataException, Exception {
		StringBuffer condition = new StringBuffer();
		condition.append("  and type = "+tyle);
		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND userName  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(username.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName = '" + StringEscapeUtils.escapeSql(realName.trim()) + "'");
		}
		if (StringUtils.isNotBlank(startTime)) {
			condition.append(" AND moth >= '" + StringEscapeUtils.escapeSql(startTime.trim()) + "'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND moth >= '" + StringEscapeUtils.escapeSql(endTime.trim()) + "'");
		}
		
		Connection conn = MySQL.getConnection();
			DataSet ds;
			try {
				ds = MySQL.executeQuery(conn, " select sum(moneys ) as moneySum  from  v_t_award_month where 1=1 " + condition);
				return BeanMapUtils.dataSetToMap(ds);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				throw e ;
			}finally {
				conn.close();
			}
			
		
		

	}
	
	
	public long  updateMoneyInfo(long id,int applystatus) throws SQLException {
		Connection conn = connectionManager.getConnection();
		long result = -1;
		try {
			result = 	awardMonthDao.updateMoneyInfo(conn, id, applystatus);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}  finally {
			conn.close();
		}
		return result;

	}
	
	
	public void setAwardMonthDao(AwardMonthDao awardMonthDao) {
		this.awardMonthDao = awardMonthDao;
	}
	

}
