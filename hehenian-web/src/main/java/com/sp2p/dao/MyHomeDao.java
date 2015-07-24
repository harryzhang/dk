package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_assignment_debt;
import com.sp2p.database.Dao.Tables.t_borrow;
import com.sp2p.database.Dao.Tables.t_invest;
import com.sp2p.database.Dao.Tables.t_user_recorelist;
import com.sp2p.util.DBReflectUtil;

public class MyHomeDao {

	/**
	 * @MethodName: queryBorrowRepaymentById
	 * @Param: MyHomeDao
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:51:39
	 * @Return:
	 * @Descb: 查询投资人回收中借款的还款详情
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBorrowForpayById(Connection conn, long id, long userId, long iid) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT a.repayPeriod as  repayPeriod ,DATE_FORMAT(a. repayDate,'%Y-%m-%d')  as  repayDate,round(a.recivedPrincipal,2) AS  forpayPrincipal , ");
		command.append("round(a.recivedInterest,2) AS  forpayInterest , round(a.principalBalance,2) AS  principalBalance , round(a.iManageFee,2)   AS  manage , ");
		command.append("a.isLate as  isLate ,a.lateDay as  lateDay ,round(a.recivedFI,2) AS  forFI , 0.00 AS  earn ,");
		command.append("d.username as  username ,a.isWebRepay as  isWebRepay  from t_invest_repayment a LEFT JOIN ");
		command.append("t_repayment b on a.repayId=b.id LEFT JOIN t_borrow c on b.borrowId=c.id LEFT JOIN t_user d on c.publisher=d.id");
		command.append(" where a.invest_id=" + iid + " and a.owner =" + userId + "  and a.repayStatus=1 AND a.isWebRepay=1 ORDER BY a.repayDate ");
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryBorrowHaspayById
	 * @Param: MyHomeDao
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:56:47
	 * @Return:
	 * @Descb: 查询已回收的借款还款详情
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBorrowHaspayById(Connection conn, long idLong, long userId, long iid) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT a.repayPeriod as  repayPeriod ,DATE_FORMAT(a. repayDate,'%Y-%m-%d')  as  repayDate,round(a.recivedPrincipal,2) AS  forpayPrincipal , ");
		command.append("round(a.recivedInterest,2) AS  forpayInterest , round(a.principalBalance,2) AS  principalBalance , round(a.iManageFee,2)   AS  manage , ");
		command.append("a.isLate as  isLate ,a.lateDay as  lateDay ,round(a.recivedFI,2) AS  forFI , 0.00 AS  earn ,");
		command.append("d.username as  username ,a.isWebRepay as  isWebRepay  from t_invest_repayment a LEFT JOIN ");
		command.append("t_repayment b on a.repayId=b.id LEFT JOIN t_borrow c on b.borrowId=c.id LEFT JOIN t_user d on c.publisher=d.id");
		command.append(" where a.invest_id=" + iid + " and a.owner =" + userId + "  and a.repayStatus=1 AND a.isWebRepay=1 ORDER BY a.repayDate ");
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryAutomaticBid
	 * @Param: MyHomeDao
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午03:09:20
	 * @Return:
	 * @Descb: 查询用户的自动投标设置
	 * @Throws:
	 */
	public Map<String, String> queryAutomaticBid(Connection conn, Long id) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT a.usableSum,b.* FROM t_user a LEFT JOIN t_automaticbid b ON b.userId = a.id");
		command.append(" where a.id = " + id + " limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: automaticBidSet
	 * @Param: MyHomeDao
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:29:20
	 * @Return:
	 * @Descb: 修改用户自动投标状态
	 * @Throws:
	 */
	public Long automaticBidSet(Connection conn, long status, long userId) throws SQLException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		if (status == 1) {
			t_automaticbid.bidStatus.setValue(2);
			t_automaticbid.bidSetTime.setValue(new Date());
		} else {
			t_automaticbid.bidStatus.setValue(1);
		}
		return t_automaticbid.update(conn, " userId = " + userId);
	}

	/**
	 * @MethodName: queryAutomaticBid
	 * @Param: MyHomeDao
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午05:06:47
	 * @Return:
	 * @Descb: 查询用户是否已经创建自动投标内容
	 * @Throws:
	 */
	public Map<String, String> hasAutomaticBid(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select id from t_automaticbid");
		command.append(" where userId = " + userId + " limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @throws SQLException
	 * @MethodName: automaticBidAdd
	 * @Param: MyHomeDao
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:57:42
	 * @Return:
	 * @Descb: 添加自动投标内容
	 * @Throws:
	 */
	public Long automaticBidAdd(Connection conn,double maxAmt,  Long userId, double bidAmount, double rateStart, double rateEnd, int deadlineStart, int deadlineEnd) throws SQLException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		t_automaticbid.bidAmount.setValue(bidAmount);
		t_automaticbid.rateStart.setValue(rateStart);
		t_automaticbid.rateEnd.setValue(rateEnd);
		t_automaticbid.deadlineStart.setValue(deadlineStart);
		t_automaticbid.deadlineEnd.setValue(deadlineEnd);
//		t_automaticbid.creditStart.setValue(creditStart);
//		t_automaticbid.creditEnd.setValue(creditEnd);
		t_automaticbid.remandAmount.setValue(maxAmt);
		t_automaticbid.userId.setValue(userId);
//		t_automaticbid.borrowWay.setValue(borrowWay);
		return t_automaticbid.insert(conn);
	}

	/**
	 * @param maxAmt 
	 * @throws SQLException
	 * @MethodName: automaticBidUpdate
	 * @Param: MyHomeDao
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:57:28
	 * @Return:
	 * @Descb: 更新自动投标内容
	 * @Throws:
	 */
	public Long automaticBidUpdate(Connection conn, double remandAmount, Long userId, double bidAmount, double rateStart, double rateEnd, int deadlineStart, int deadlineEnd) throws SQLException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		t_automaticbid.bidAmount.setValue(bidAmount);
		t_automaticbid.rateStart.setValue(rateStart);
		t_automaticbid.rateEnd.setValue(rateEnd);
		t_automaticbid.deadlineStart.setValue(deadlineStart);
		t_automaticbid.deadlineEnd.setValue(deadlineEnd);
//		t_automaticbid.creditStart.setValue(creditStart);
//		t_automaticbid.creditEnd.setValue(creditEnd);
		t_automaticbid.remandAmount.setValue(remandAmount);
//		t_automaticbid.borrowWay.setValue(borrowWay);
		return t_automaticbid.update(conn, " userId = " + userId);
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryRepaymentByOwner
	 * @Param: MyHomeDao
	 * @Author:
	 * @Date:
	 * @Return:
	 * @Descb: 查询最近还款日及金额
	 * @Throws:
	 */

	public Map<String, String> queryRepaymentByOwner(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT DATE_FORMAT(tr.repayDate ,'%Y-%m-%d') AS minRepayDate,SUM(recivedPrincipal + recivedInterest) AS totalSum FROM t_invest_repayment tr ");
		command.append("WHERE tr.repayDate IN (SELECT MIN(repayDate) FROM t_invest_repayment ");
		command.append("WHERE owner = " + userId + " AND DATE_FORMAT(repayDate ,'%Y-%m-%d') >= DATE_FORMAT(NOW() ,'%Y-%m-%d') AND repayStatus = 1) ");
		command.append("AND tr.`owner` = " + userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/** 
     * 查询成功充值总金额
     * @param conn
     * @param userId
     * @return
     * @throws Exception [参数说明]
     * 
     * @return Map<String,String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public Map<String,String> queryRechargeInfoOk(Connection conn,long userId) throws Exception {
        String sql = "select sum(rechargeMoney) from t_recharge_detail where userId=" + userId + " and result=1";
        DataSet ds = MySQL.executeQuery(conn, sql);
        sql = null;
        return BeanMapUtils.dataSetToMap(ds);
    }

	/**
	 * 查询最近一个月流水记录
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryFundRecord(Connection conn, Long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select income ,spending as expend ,recordTime as add_time ,remarks as remark,cost from t_fundrecord where " +
				"userId=" + userId + " and fundMode <> '冻结投标金额' order by recordTime desc limit 5");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	@SuppressWarnings("unchecked")
    public void queryBorrowneedpayById(Connection conn, long borrowId, HttpServletRequest request) throws SQLException, DataException {

		// 历史还款记录
		List<Map<String, Object>> record = new ArrayList<Map<String, Object>>();
		String sql = "select repayPeriod,repayDate, stillPrincipal+stillInterest+repayFee+consultFee as stillPI,stillInterest,lateFI,isLate,realRepayDate,repayStatus,isWebRepay  from t_repayment";
		sql += " where borrowId=" + borrowId + " order by substring_index(ifnull(repayPeriod,0),'/',1)+0 ";
		DataSet dataSet = MySQL.executeQuery(conn, sql);
		dataSet.tables.get(0).rows.genRowsMap();
		record = dataSet.tables.get(0).rows.rowsMap;

		// 当前的还款记录
		Map<String, Object> curr = new HashMap<String, Object>();
		sql = " SELECT b.id as borrowId,IFNULL(tpr.consultFee,'--')consultFee, c.username AS username, d.realName AS realName, b.borrowTitle AS borrowTitle, b.borrowAmount borrowAmount,";
		sql += " b.deadline deadline, b.annualRate annualRate, IFNULL(a.repayPeriod,'--') repayPeriod, IFNULL(a.repayDate,'--') repayDate, IFNULL(a.stillInterest + a.stillPrincipal,'--') stillPI,";
		sql += " IFNULL(a.stillInterest,'--') stillInterest, IFNULL(a.lateFI,'--') lateFI, IFNULL(a.lateDay,'--') lateDay, IFNULL(a.realRepayDate,'--') realRepayDate, IFNULL(a.repayStatus,3) repayStatus,a.id payId ";
		sql += " FROM  t_borrow b LEFT JOIN (select * from t_repayment where repayStatus=1) a ON  a.borrowId = b.id LEFT JOIN t_user c ON b.publisher = c.id ";
		sql += " LEFT JOIN t_person d ON c.id = d.userId left join t_repayment tpr on tpr.borrowId = b.id and tpr.repayStatus=1 where b.id=" + borrowId + " LIMIT 0,1";
		dataSet = MySQL.executeQuery(conn, sql);
		dataSet.tables.get(0).rows.genRowsMap();
		List<Map<String, Object>> temp = dataSet.tables.get(0).rows.rowsMap;
		if (temp != null && temp.size() > 0)
			curr = temp.get(0);

		request.setAttribute("curr", curr);
		request.setAttribute("record", record);
	}

	// 删除已合并的债权
	public long deleteHasCombineInvest(Connection conn, String ids) throws SQLException {
		Dao.Tables.t_invest invest = new Dao().new Tables().new t_invest();
		return invest.delete(conn, "id in (" + ids + ")");
	}

	// 合并并删除(多项拆分中的操作)
	public long deleteAndInsert(Connection conn, Dao.Tables.t_invest invests, String ids) throws SQLException {
		long result = invests.insert(conn);
		if (result < 0) {
			return result;
		}
		result = deleteHasCombineInvest(conn, ids);
		return result;

	}

	/**
	 * 根据ID查询债权信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryInvestById(Connection conn, String id) throws SQLException, DataException {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select tp.realName as buserName,tub.username as username ");
		sbf.append(" from t_borrow tb");
		sbf.append(" left join t_user tub on tub.id = tb.publisher");
		sbf.append(" left join t_person tp on tp.userId = tub.id");
		sbf.append(" where tb.id=" + id);
		DataSet dataSet = MySQL.executeQuery(conn, sbf.toString());
		return BeanMapUtils.dataSetToMap(dataSet);

	}

	/**
	 * 根据ID查询出转让人的各项信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @throws DataException
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryAlienatorById(Connection conn, String alienatorId) throws SQLException, DataException {
		String sql = "select * from t_invest where id =" + alienatorId + " group by investor";
		DataSet dataSet = MySQL.executeQuery(conn, sql);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String, String> queryAssignmentById(Connection conn, String id) throws SQLException, DataException {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select tb.borrowTitle,tb.borrowWay,tb.borrowStatus,tb.annualRate,tb.deadline as deadlineTime,tb.borrowAmount,"
				+ "tb.moneyPurposes,tb.minTenderedSum,tb.maxTenderedSum,tb.excitationType,tb.paymentMode,tub.username as buserName,"
				+ "tb.publishTime,tb.publishIP,tp.realName,tub.source,tub.username,tb.publisher,tb.excitationSum,ti.*");
		sbf.append(" from t_invest ti left join t_borrow tb on tb.id = ti.borrowId");
		sbf.append(" left join t_user tub on tub.id = tb.publisher");
		sbf.append(" left join t_person tp on tp.userId = tub.id");
		sbf.append(" where ti.id=" + id);
		DataSet dataSet = MySQL.executeQuery(conn, sbf.toString());
		return BeanMapUtils.dataSetToMap(dataSet);

	}

	/**
	 * 拆分
	 * 
	 * @param conn
	 * @param map
	 * @param debtSum
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long insertAssignmentDebt(Connection conn, Map<String, String> map, double realAmount) throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.investAmount.setValue(map.get("investAmount"));
		t_invest.monthRate.setValue(map.get("monthRate"));
		t_invest.investor.setValue(map.get("investor"));
		t_invest.borrowId.setValue(map.get("borrowId"));
		t_invest.investTime.setValue(map.get("investTime"));
		t_invest.oriInvestor.setValue(map.get("oriInvestor"));
		t_invest.realAmount.setValue(realAmount);
		t_invest.hasPI.setValue(map.get("hasPI"));
		t_invest.deadline.setValue(map.get("deadline"));
		t_invest.hasDeadline.setValue(map.get("hasDeadline"));
		t_invest.recivedPrincipal.setValue(map.get("recivedPrincipal"));
		t_invest.recievedInterest.setValue(map.get("recievedInterest"));
		t_invest.hasPrincipal.setValue(map.get("hasPrincipal"));
		t_invest.hasInterest.setValue(map.get("hasInterest"));
		t_invest.recivedFI.setValue(map.get("recivedFI"));
		t_invest.hasFI.setValue(map.get("hasFI"));
		t_invest.manageFee.setValue(map.get("manageFee"));
		t_invest.reward.setValue(map.get("rewards"));
		t_invest.repayStatus.setValue(map.get("repayStatus"));
		t_invest.flag.setValue(map.get("flag"));
		t_invest.isAutoBid.setValue(map.get("isAutoBid"));
		t_invest.isDebt.setValue(map.get("isDebt"));
		t_invest.circulationInterest.setValue(map.get("circulationInterest"));
		t_invest.circulationForpayStatus.setValue(map.get("circulationForpayStatus"));
		t_invest.reason.setValue(map.get("reason"));
		if (null != map.get("repayDate") && map.get("repayDate").length() < 0) {
			t_invest.repayDate.setValue(map.get("repayDate"));
		}
		t_invest.check_principal.setValue(map.get("check_principal"));
		t_invest.check_interest.setValue(map.get("check_interest"));
		if (null != map.get("min_invest_id") && map.get("min_invest_id").length() < 0) {
			t_invest.min_invest_id.setValue(Integer.valueOf(map.get("min_invest_id")));
		}
		if (null != map.get("max_invest_id") && map.get("max_invest_id").length() < 0) {
			t_invest.max_invest_id.setValue(Integer.valueOf(map.get("max_invest_id")));
		}
		t_invest.adjust_principal.setValue(map.get("adjust_principal"));

		return t_invest.insert(conn);
	}

	/**
	 * 拆分--根据ID删除原始债权
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long deleteAssignmentDebt(Connection conn, String id) throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		return t_invest.delete(conn, "id=" + id);
	}

	/**
	 * 判断是否可以债权转让
	 * 
	 * @param strToLong
	 * @param strToLong2
	 * @return 返回true则不可以转让，否则可以
	 * @throws DataException
	 * @throws SQLException
	 */
	public boolean isHaveAssignmentDebt(Connection conn, long investId, long userId) throws SQLException, DataException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		StringBuilder condition = new StringBuilder(" 1=1 ");
		condition.append(" and id = ");
		condition.append(investId);
		condition.append(" and investor = ");
		condition.append(userId);
		condition.append(" and isDebt = 1");
		DataSet ds = t_invest.open(conn, "count(1) as counts", condition.toString(), "", -1, -1);
		long count = Convert.strToLong(BeanMapUtils.dataSetToMap(ds).get("counts"), -1);
		condition = null;
		return count > 0;
	}

	/**
	 * 添加债权转让
	 * 
	 * @param conn
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long addAssignmentDebt(Connection conn, Map<String, String> paramMap) throws SQLException {
		// 在t_assignment_debt中插入转让记录
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		DBReflectUtil.mapToTableValue(t_assignment_debt, paramMap);
		long result = t_assignment_debt.insert(conn);

		// 修改t_invest表中的信息，转让状态、前后台转让
		StringBuffer condition = new StringBuffer();
		condition.append(" id = " + Convert.strToLong(paramMap.get("investId"), -1));
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.isDebt.setValue(3);// 转让中
		t_invest.distinguish_debt.setValue(0);// 前台转让
		t_invest.invest_number.setValue(result);// 设置债权编号
		t_invest.update(conn, condition.toString());

		return result;

	}

	/**
	 * 获取借款标题
	 * 
	 * @param strToLong
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String getBorrowTitle(Connection conn, long debtId) throws SQLException, DataException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		DataSet ds = t_borrow.open(conn, "borrowTitle", " id = (select borrowId from t_assignment_debt t where t.id=" + debtId + ")", "", -1, -1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		String borrowTitle = null;
		if (map != null) {
			borrowTitle = map.get("borrowTitle");
		}
		map = null;
		return borrowTitle;
	}

	/**
	 * @MethodName: addUserDynamic
	 * @Param: BorrowDao
	 * @Author: gang.lv
	 * @Date: 2013-4-21 上午10:28:50
	 * @Return:
	 * @Descb: 添加用户动态
	 * @Throws:
	 */
	public long addUserDynamic(Connection conn, long userId, String url) throws SQLException {
		Dao.Tables.t_user_recorelist t_user_recorelist = new Dao().new Tables().new t_user_recorelist();
		t_user_recorelist.userId.setValue(userId);
		t_user_recorelist.url.setValue(url);
		t_user_recorelist.time.setValue(new Date());
		return t_user_recorelist.insert(conn);
	}

	/**
	 * 会员中心 当前投资 还款详情
	 */
	public List<Map<String, Object>> queryBorrowForpayHHN(Connection conn, Long userId, long investId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT * from v_t_hhn_pay_detail where investId=" + investId + " and userId=" + userId);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 债权合并
	 * 
	 * @param ids
	 */
	public void debtCombine(String ids) {

	}
}
