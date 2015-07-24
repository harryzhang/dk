package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;

/**
 * @ClassName: AfterCreditManageDao.java
 * @Author: gang.lv
 * @Date: 2013-3-19 上午10:16:48
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 贷后管理数据处理层
 */
public class AfterCreditManageDao {

	/**
	 * @MethodName: queryRepaymentAmount
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午11:31:28
	 * @Return:
	 * @Descb: 根据条件统计最近还款总额
	 * @Throws:
	 */
	@SuppressWarnings("static-access")
	public Map<String, String> queryRepaymentAmount(Connection conn,
			String userName, int borrowWay, String realName, String title,
			int status, String type) throws SQLException, DataException {
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		Calendar calendar = Calendar.getInstance();
		StringBuffer command = new StringBuffer();
		command
				.append("SELECT SUM(totalSum) amount FROM v_t_repayment_h where 1=1");
		if (StringUtils.isNotBlank(userName)) {
			// command.append(" and username ='"+StringEscapeUtils.escapeSql(userName)+"'");
			command.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			// command.append(" and realName ='"+StringEscapeUtils.escapeSql(realName)+"'");
			command.append(" and realName  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(title)) {
			/*
			 * command.append(" and borrowTitle  LIKE CONCAT('%','" +
			 * StringEscapeUtils.escapeSql(title.trim()) + "','%')");
			 */
			command.append(" and borrowTitle  like '%"
					+ StringEscapeUtils.escapeSql(title.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			command.append(" and borrowWay =" + borrowWay);
		}
		if (IConstants.DEFAULT_NUMERIC != status) {
			command.append(" and repayStatus =" + status);
		}
		if ("".equals(type)) {
			Date date = calendar.getTime();
			command.append(" and repayDate ='"
					+ StringEscapeUtils.escapeSql(sf.format(date)) + "'");
		} else if ("1".equals(type)) {
			calendar.add(calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			command.append(" and repayDate ='"
					+ StringEscapeUtils.escapeSql(sf.format(date)) + "'");
		} else if ("2".equals(type)) {
			calendar.add(calendar.DAY_OF_YEAR, 2);
			Date date = calendar.getTime();
			command.append(" and repayDate ='"+ StringEscapeUtils.escapeSql(sf.format(date)) + "'");
		}
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		sf = null;
		calendar = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: addRepayMentNotice
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-19 下午07:01:47
	 * @Return:
	 * @Descb: 添加还款沟通记录
	 * @Throws:
	 */
	public Long addRepayMentNotice(Connection conn, long idLong, String content)
			throws SQLException {
		Dao.Tables.t_repayment_service t_repayment_service = new Dao().new Tables().new t_repayment_service();
		t_repayment_service.repayId.setValue(idLong);
		t_repayment_service.serviceContent.setValue(content);
		t_repayment_service.serviceTime.setValue(new Date());
		return t_repayment_service.insert(conn);
	}

	/**
	 * @MethodName: queryForPaymentAmount
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午06:33:33
	 * @Return:
	 * @Descb: 根据条件查询待收款统计
	 * @Throws:
	 */
	public Map<String, String> queryForPaymentAmount(Connection conn,
			String investor, String timeStart, String timeEnd, String title,
			int borrowWayInt, int groupInt, boolean inverse)
			throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command
				.append("SELECT round(SUM(forTotalSum),2) amount FROM v_t_forpayment_h where 1=1");
		if (StringUtils.isNotBlank(investor)) {
			command.append(" and  investor  like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			command.append(" and repayDate >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			command.append(" and repayDate <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (StringUtils.isNotBlank(title)) {
			command.append(" and  borrowTitle  like '%"
					+ StringEscapeUtils.escapeSql(title.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			command.append(" and borrowWay =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != groupInt) {
			if (inverse) {// 如果是反选
				command.append(" AND   ( groupId !=" + groupInt
						+ "  or groupId  is null )");
			} else {
				command.append(" AND  groupId =" + groupInt);
			}
		}
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @throws SQLException
	 * @MethodName: queryForPaymentTotalAmount
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午10:10:49
	 * @Return:
	 * @Descb: 查询代收款总计统计
	 * @Throws:
	 */
	public Map<String, String> queryForPaymentTotalAmount(Connection conn,
			String investor, String timeStart, String timeEnd, int deadlineInt,
			int groupInt, boolean inverse) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command
				.append("SELECT round(SUM(b.forTotalSum),2) amount FROM v_t_forpayment_h b LEFT JOIN (");
		;
		command
				.append(" SELECT a.id,a.borrowId,SUM(a.forTotalSum) AS forPI FROM (SELECT id,borrowId,");
		command
				.append(" forTotalSum FROM v_t_forpayment_h) a GROUP BY a.id,a.borrowId) c ON");
		command.append(" b.borrowId=c.borrowId AND b.id=c.id");
		command.append(" where 1=1");
		if (StringUtils.isNotBlank(investor)) {
			command.append(" and  investor  like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			command.append(" and repayDate >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			command.append(" and repayDate <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (IConstants.DEFAULT_NUMERIC != deadlineInt) {
			command.append(" and deadline =" + deadlineInt);
		}
		if (IConstants.DEFAULT_NUMERIC != groupInt) {
			if (inverse) {// 如果是反选
				command.append(" AND   ( groupId !=" + groupInt
						+ "  or groupId  is null )");
			} else {
				command.append(" AND  groupId =" + groupInt);
			}
		}
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryHasRePayAmount
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午02:36:43
	 * @Return:
	 * @Descb: 已还款统计
	 * @Throws:
	 */
	public Map<String, String> queryHasRePayAmount(Connection conn,
			String userName, String realName, String timeStart, String timeEnd,
			int borrowWayInt, int deadlineInt, int repayStatusInt,
			String timeStart1, String timeEnd1) throws SQLException,
			DataException {
		StringBuffer command = new StringBuffer();
		command
				.append("SELECT SUM(hasPI) amount FROM v_t_hasrepay_h where 1=1");
		if (StringUtils.isNotBlank(userName)) {
			command.append(" and username   like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			command.append(" and realName   like '%"
					+ StringEscapeUtils.escapeSql(realName) + "%' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			command.append(" and realRepayDate >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			command.append(" and realRepayDate <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			command.append(" and borrowWay =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != deadlineInt) {
			command.append(" and deadline =" + deadlineInt);
		}
		if (IConstants.DEFAULT_NUMERIC != repayStatusInt) {
			command.append(" and repayStatus =" + repayStatusInt);
		}

		// ---------add by houli
		if (StringUtils.isNotBlank(timeStart1)) {
			command.append(" and repayDate >= '"
					+ StringEscapeUtils.escapeSql(timeStart1) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd1)) {
			command.append(" and repayDate <= '"
					+ StringEscapeUtils.escapeSql(timeEnd1) + "'");
		}
		// --------

		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryLateRepayAmount
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:18:25
	 * @Return:
	 * @Descb: 逾期的借款统计
	 * @Throws:
	 */
	public Map<String, String> queryLateRepayAmount(Connection conn,
			String userName, int borrowWayInt, int statusInt)
			throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command .append("SELECT SUM(repaySum) as amount,sum(totalSum) as totalSumm,sum(lateFI) as totallateFI FROM v_t_laterepay_h where 1=1");
		if (StringUtils.isNotBlank(userName)) {
			command.append(" and userName  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			command.append(" and borrowWay =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != statusInt) {
			command.append(" and repayStatus =" + statusInt);
		}
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 根据ID查询逾期还款信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryLateRepayById(Connection conn, long id)
			throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT *  FROM  v_t_overduepayment_h  where id=" + id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryOverduePaymentAmount
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:43:36
	 * @Return:
	 * @Descb: 逾期垫付的借款统计
	 * @Throws:
	 */
	public Map<String, String> queryOverduePaymentAmount(Connection conn,
			String userName, int borrowWayInt, int statusInt)
			throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT SUM(repaySum) as amount,sum(totalSum) as totalSumm,sum(lateFI) as totallateFI FROM v_t_overduepayment_h where 1=1");
		if (StringUtils.isNotBlank(userName)) {
			command.append(" and userName  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			command.append(" and borrowWay =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != statusInt) {
			command.append(" and repayStatus =" + statusInt);
		}
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryRepaymentDetail
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午02:07:39
	 * @Return:
	 * @Descb: 查询还款记录详情
	 * @Throws:
	 */
	public Map<String, String> queryRepaymentDetail(Connection conn, long id)
			throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT * FROM (SELECT a.lateDay,(a.stillPrincipal+a.stillInterest) stillPI,a.lateFI,(a.stillPrincipal+a.stillInterest+a.lateFI) totalSum");
		command.append(" ,b.borrowTitle,b.id,b.publisher,c.username borrower,a.id repayId FROM t_repayment a LEFT JOIN t_borrow b ON a.borrowId=b.id LEFT JOIN t_user c ON b.publisher=c.id) t");
		command.append(" LEFT JOIN (SELECT SUM(a.stillPrincipal+a.stillInterest-a.hasPI+a.investorForpayFI-a.investorHaspayFI) forSum,a.borrowId FROM t_repayment a GROUP BY a.borrowId) t2");
		command.append(" ON t.id=t2.borrowId WHERE t.repayId = " + id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryRepaymentService
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午01:52:48
	 * @Return:
	 * @Descb: 借款催收记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryRepaymentCollection(Connection conn,
			long id) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql
				.append("SELECT id,remark,DATE_FORMAT(collectionDate,'%Y-%m-%d %T') collectionDate,colResult FROM t_collection WHERE repayId = "
						+ id);
		sql.append(" order by id desc");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;

	}

	/**
	 * @MethodName: queryRepaymentColectoin
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午01:52:38
	 * @Return:
	 * @Descb: 借款客服沟通记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryRepaymentService(Connection conn,
			long id) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql
				.append("SELECT id,serviceContent,DATE_FORMAT(serviceTime,'%Y-%m-%d %T') serviceTime FROM t_repayment_service WHERE repayId = "
						+ id);
		sql.append(" order by id desc");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;

	}

	/**
	 * @MethodName: addCollection
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午04:17:43
	 * @Return:
	 * @Descb: 添加催收记录
	 * @Throws:
	 */
	public Long addCollection(Connection conn, long idLong, String colResult,
			String remark) throws SQLException {
		Dao.Tables.t_collection t_collection = new Dao().new Tables().new t_collection();
		t_collection.colResult.setValue(colResult);
		t_collection.remark.setValue(remark);
		t_collection.repayId.setValue(idLong);
		t_collection.collectionDate.setValue(new Date());
		return t_collection.insert(conn);
	}

	/**
	 * @throws SQLException
	 * @MethodName: delCollection
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午04:35:55
	 * @Return:
	 * @Descb: 删除催收记录
	 * @Throws:
	 */
	public Long delCollection(Connection conn, long idLong) throws SQLException {
		Dao.Tables.t_collection t_collection = new Dao().new Tables().new t_collection();
		return t_collection.delete(conn, " id=" + idLong);
	}

	/**
	 * @MethodName: queryLateBorrow
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 上午10:52:36
	 * @Return:
	 * @Descb: 查询处于逾期的借款
	 * @Throws:
	 */
	public Map<String, String> queryLateBorrow(Connection conn, long id)
			throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command
				.append("SELECT id FROM t_repayment WHERE isLate = 2 and repayStatus = 1 and id ="
						+ id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryRepaymentSum
	 * @Param: AfterCreditManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 上午10:57:00
	 * @Return:
	 * @Descb: 查询代还金额
	 * @Throws:
	 */
	public Map<String, String> queryRepaymentSum(Connection conn, long id)
			throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command
				.append("SELECT (stillPrincipal+stillInterest-hasPI) needSum FROM t_repayment WHERE id ="
						+ id + " AND repayStatus =1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

}
