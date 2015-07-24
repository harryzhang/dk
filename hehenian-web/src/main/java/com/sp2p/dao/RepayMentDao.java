package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * @param:还款记录表
 * @ClassName: RepayMentDao.java
 * @Author: gang.lv
 * @Date: 2013-5-24 下午02:50:42
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb:
 */
public class RepayMentDao {

	/**
	 * @MethodName: updateRepayMentSum
	 * @Param: RepayMentDao
	 * @Author: gang.lv
	 * @Date: 2013-5-24 下午03:11:24
	 * @Return:
	 * @Descb: 更新还款本金利息
	 * @Throws:
	 */
	public long updateRepayMentSum(Connection conn, double recivedPrincipal, double recivedInterest, long repayId) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append(" update t_repayment set stillPrincipal= stillPrincipal+" + recivedPrincipal);
		command.append(" ,stillInterest=stillInterest+" + recivedInterest + " where id=" + repayId + " and repayStatus=1");
		return Database.executeNonQuery(conn, command.toString());
	}

	/**
	 * 更新还款状态
	 * 
	 * @param conn
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 */
	public long updateRepaymentStatus(Connection conn, long borrowId) throws SQLException {
		long returnId = -1;
		StringBuffer sb = new StringBuffer();
		sb.append("update t_repayment set  isWebRepay =2 , version = version +1  where borrowId = " + borrowId);
		returnId = MySQL.executeNonQuery(conn, sb.toString());
		sb = null;
		return returnId;
	}

	/**
	 * 查询已还金额 和 待收金额
	 * 
	 * @param conn
	 * @param borroid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryHasPIAndStillPi(Connection conn, long borroid) throws SQLException, DataException {
		String command = "select a.hasPI as hasPI,b.borrowAmount as borrowAmount ,b.annualRate as annualRate , (a.stillPrincipal+a.stillInterest) as isp,";
		command += " a.stillPrincipal as stillPrincipal ,a.stillInterest as stillInterest,a.repayPeriod as repayPeriod ,a.repayDate as repayDate ";
		command += " from t_repayment a left join t_borrow b on a.borrowId = b.id  where a.borrowId =" + borroid;
		DataSet dataSet = MySQL.executeQuery(conn, command);
		dataSet.tables.get(0).rows.genRowsMap();

		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @Author: yijun
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryRepaymentInfo
	 * @Descb: 自动代偿还款 查询需要还款的信息
	 */
	public List<Map<String, Object>> queryNeedRepaymentInfo(Connection conn) throws SQLException, DataException {
		Dao.Views.v_t_auto_repay_list auto = new Dao().new Views().new v_t_auto_repay_list();
		DataSet dataSet = auto.open(conn, " * ", " autoRepayCount<2 ", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryRepaymentInfo
	 * @Param: RepayMentDao
	 * @Author: gang.lv
	 * @Date: 2013-5-24 下午03:16:47
	 * @Return:
	 * @Descb: 查询还款信息
	 * @Throws:
	 */
	public Map<String, String> queryRepaymentInfo(Connection conn, long id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select a.id,a.stillPrincipal,a.stillInterest,a.lateFI from t_repayment a left join t_borrow b on a.borrowId=b.id ");
		command.append(" where a.borrowId =" + id + " limit 0,1");
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryRepaymentById
	 * @Param: RepayMentDao
	 * @Author: gang.lv
	 * @Date: 2013-6-2 下午08:35:34
	 * @Return:
	 * @Descb: 根据还款id查询还款信息
	 * @Throws:
	 */
	public Map<String, String> queryRepaymentById(Connection conn, long id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select a.stillPrincipal,a.stillInterest,a.lateFI,a.borrowId from t_repayment a ");
		command.append(" where a.id =" + id);
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(ds);
	}
}
