package com.sp2p.task;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_approve_notice_template;

/**
 * @ClassName: JobTaskDao.java
 * @Author: gang.lv
 * @Date: 2013-4-11 上午11:15:20
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 定时任务处理数据层
 */
public class JobTaskDao {

	/**
	 * @MethodName: queryUserBidParam
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-14 下午10:31:10
	 * @Return:
	 * @Descb: 查询用户的自动投标设置参数
	 * @Throws:
	 */
	public Map<String, String> queryUserBidParam(Connection conn, long userId) throws SQLException, DataException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		DataSet dataSet = t_automaticbid.open(conn, " * ", " userId= " + userId, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryAutoBidUser
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-14 下午10:29:36
	 * @Return:
	 * @Descb: 查询设置了自动投标的用户
	 * @Throws:
	 */
	public List<Map<String, Object>> queryAutoBidUser(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		DataSet dataSet = t_automaticbid.open(conn, " userId ", " bidSetTime is not null and bidStatus = 2 ", " bidSetTime asc", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

   /* public List<Map<String, Object>> queryWyfUser(Connection conn) throws SQLException, DataException {
        String sql="SELECT t1.* from t_automaticbid t1 join "
                + "(SELECT DISTINCT userId from t_activity_order where ordStatus=1 and ordType=0) t2 "
                + "on t1.userId=t2.userId  ";
        DataSet dataSet = MySQL.executeQuery(conn, sql);
        dataSet.tables.get(0).rows.genRowsMap();
        return dataSet.tables.get(0).rows.rowsMap;
    }*/
	/**
	 * @MethodName: queryAllBider
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-14 下午11:13:16
	 * @Return:
	 * @Descb: 查询所有的招标中进度小于95%的借款
	 * @Throws:
	 */
//	public List<Map<String, Object>> queryAllBider(Connection conn) throws SQLException, DataException {
//		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
//		StringBuffer command = new StringBuffer();
//		command.append("select `a`.`id` AS `id`,`a`.`deadline` AS `deadline`,`a`.`annualRate` AS `annualRate`,`a`.`borrowAmount` AS `borrowAmount`,`a`.`hasInvestAmount` AS `hasInvestAmount`,`f_credit_rating`(`b`.`creditrating`) AS `credit`,`a`.`borrowWay` AS `borrowWay`,`a`.`isDayThe` AS `isDayThe`,`a`.`autoBidEnableTime` AS `autoBidEnableTime` from `t_borrow` `a` left join `t_user` `b` on`a`.`publisher` = `b`.`id` where `a`.`borrowStatus` = 2 and a.hasInvestAmount=a.borrowAmount");
//		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
//		dataSet.tables.get(0).rows.genRowsMap();
//		return dataSet.tables.get(0).rows.rowsMap;
//	}
	public List<Map<String, Object>> queryAllBider(Connection conn)
			throws SQLException, DataException {
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		StringBuffer command = new StringBuffer();
		command
				.append("select id,borrowAmount,hasInvestAmount,borrowWay,isDayThe from v_t_autobid_borrow");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryUserBider
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-18 下午04:43:11
	 * @Return:
	 * @Descb: 查询用户已自动投标的标的
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserBider(Connection conn, long borrowId, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select id from t_automaticbid_user");
		command.append(" where borrowId=" + borrowId + " and userId=" + userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryBorrowOwer
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-18 下午04:59:32
	 * @Return:
	 * @Descb: 查询借款标的的拥有者
	 * @Throws:
	 */
	public Map<String, String> queryBorrowOwer(Connection conn, long borrowId, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select id from t_borrow where id=" + borrowId + " and publisher=" + userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryBiderByParam
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-14 下午11:04:12
	 * @Return:
	 * @Descb: 查询符合用户设置的自动投标参数的标的
	 * @Throws:
	 */
	public Map<String, String> queryBiderByParam(Connection conn, double rateStart, double rateEnd, int deadLineStart, int deadLineEnd, int creditStart, int creditEnd, long id,
			int isDayThe) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		if (isDayThe == IConstants.DAY_THE_2) {
			// 是天标时，需要将期限更改成天
			if (deadLineStart < 0) {
				// 用户设置了天标的条件
				deadLineStart = Math.abs(deadLineStart);
				deadLineEnd = Math.abs(deadLineEnd);
			}
		}
		// 月标
		command.append("select id from v_t_autobid_borrow where annualRate >= " + rateStart + " and annualRate <= " + rateEnd);
		command.append(" and deadline >= " + deadLineStart + " and deadline <=" + deadLineEnd + " and");
		command.append(" id = " + id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryUserAmount
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-15 下午01:42:32
	 * @Return:
	 * @Descb: 查询用户预留保留金额时的可用金额
	 * @Throws:
	 */
	public Map<String, String> queryUserAmount(Connection conn, double remandAmount, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select (usableSum-" + remandAmount + ") usableSum,usrCustId from t_user where (usableSum-" + remandAmount + ") > 0 and id = " + userId + " limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryBorrow
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-15 下午03:24:07
	 * @Return:
	 * @Descb: 查询符合投标的借款
	 * @Throws:
	 */
	public Map<String, String> queryBorrow(Connection conn, long borrowId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select * from v_t_autobid_borrow where id = " + borrowId + " limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: updateUserAutoBidTime
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-14 下午10:33:46
	 * @Return:
	 * @Descb: 修改用户自动投标时间
	 * @Throws:
	 */
	public long updateUserAutoBidTime(Connection conn, long userId) throws SQLException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		t_automaticbid.bidSetTime.setValue(new Date());
		return t_automaticbid.update(conn, " userId=" + userId);
	}

	/**
	 * @MethodName: queryBorrowInfo
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午10:57:24
	 * @Return:
	 * @Descb: 查询借款信息
	 * @Throws:
	 */
	public Map<String, String> queryBorrowInfo(Connection conn, int borrowId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT (b.annualRate/12) monthRate,b.deadline,b.borrowTitle,b.publisher,b.version,u.usrCustId,b.borrowGroup  FROM t_borrow b left join t_user u on b.publisher=u.id WHERE b.id= " + borrowId + " limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryVipOverTime
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午08:18:24
	 * @Return:
	 * @Descb: 查询过期的VIP
	 * @Throws:
	 */
	public List<Map<String, Object>> queryVipOverTime(Connection conn) throws SQLException, DataException {
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		StringBuffer command = new StringBuffer();
		command.append("select id,username from t_user where vipStatus =2 and DATE_FORMAT(vipCreateTime,'%Y-%m-%d') = ");
		command.append("DATE_FORMAT(date_sub('" + sf.format(new Date()) + "', interval 1 year),'%Y-%m-%d')");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryFirstVip
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午08:20:57
	 * @Return:
	 * @Descb: 查询首次成为VIP的用户
	 * @Throws:
	 */
	public List<Map<String, Object>> queryFirstVip(Connection conn) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select id,username from t_user where vipStatus =2 and feeStatus=1 and isFirstVip = 1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryVipFee
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午08:28:35
	 * @Return:
	 * @Descb: 查询VIP会费
	 * @Throws:
	 */
	public Map<String, String> queryVipFee(Connection conn) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT number FROM t_cost_manager WHERE TYPE = 4");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: updateUserVIPStatus
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午08:38:19
	 * @Return:
	 * @Descb: 更新用户的VIP续费状态
	 * @Throws:
	 */
	public long updateUserVIPStatus(Connection conn, long userId) throws SQLException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		t_user.vipStatus.setValue(3);
		t_user.feeStatus.setValue(1);
		return t_user.update(conn, " id=" + userId);
	}

	/**
	 * @MethodName: queryUserHasVipFee
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午08:44:17
	 * @Return:
	 * @Descb: 首次申请为VIP时,查看用户是否有钱
	 * @Throws:
	 */
	public Map<String, String> queryUserHasVipFee(Connection conn, long userId, double vipFee) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select usableSum ,username from t_user where id=" + userId + " and usableSum > " + vipFee);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: deductedUserVipFee
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午08:52:41
	 * @Return:
	 * @Descb: 扣除VIP会费
	 * @Throws:
	 */
	public long deductedUserVipFee(Connection conn, long userId, double vipFee) throws SQLException {
		StringBuffer command = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		command.append("update t_user set usableSum = usableSum - " + vipFee + ",feeStatus=2,vipStatus=2");
		command.append(",isFirstVip = 2,vipCreateTime = '" + sf.format(new Date()) + "' where id =" + userId);
		return MySQL.executeNonQuery(conn, command.toString());
	}

	/**
	 * @return
	 * @throws SQLException
	 * @MethodName: addVipFeeRecord
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午08:52:52
	 * @Return:
	 * @Descb: 添加VIP会费记录
	 * @Throws:
	 */
	public long addVipFeeRecord(Connection conn, long userId, double vipFee) throws SQLException {
		Dao.Tables.t_vipsum t_vipsum = new Dao().new Tables().new t_vipsum();
		t_vipsum.vipFee.setValue(vipFee);
		t_vipsum.userId.setValue(userId);
		return t_vipsum.insert(conn);
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryFriendsReward
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午09:12:26
	 * @Return:
	 * @Descb: 查询好友奖励
	 * @Throws:
	 */
	public List<Map<String, Object>> queryFriendsReward(Connection conn) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select a.id,a.money,a.userId,b.username from t_money a left join t_user b on a.userId = b.id where a.status =1");
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryRiskBalance
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-22 上午09:46:31
	 * @Return:
	 * @Descb: 查询风险保障金余额
	 * @Throws:
	 */
	public Map<String, String> queryRiskBalance(Connection conn) throws SQLException, DataException {
		DataSet dataSet = MySQL.executeQuery(conn, "select sum(riskInCome-riskSpending) riskBalance from t_risk_detail");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: spendingRiskAmount
	 * @Param: FrontMyPaymentDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午03:34:09
	 * @Return:
	 * @Descb: 支出风险保障金
	 * @Throws:
	 */
	public long spendingRiskAmount(Connection conn, double riskBalance, double riskAmount, long publisher, long id, String resource) throws SQLException {
		Dao.Tables.t_risk_detail t_risk_detail = new Dao().new Tables().new t_risk_detail();
		t_risk_detail.riskBalance.setValue(riskBalance);
		t_risk_detail.riskSpending.setValue(riskAmount);
		t_risk_detail.riskDate.setValue(new Date());
		t_risk_detail.riskType.setValue("支出");
		t_risk_detail.resource.setValue(resource);
		t_risk_detail.trader.setValue(publisher);
		t_risk_detail.borrowId.setValue(id);
		return t_risk_detail.insert(conn);
	}

	/**
	 * @MethodName: updateRewardStatus
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午09:25:45
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public long updateRewardStatus(Connection conn, long id, long userId) throws SQLException {
		Dao.Tables.t_money t_money = new Dao().new Tables().new t_money();
		t_money.status.setValue(2);
		t_money.userId.setValue(userId);
		t_money.endData.setValue(new Date());
		return t_money.update(conn, " id=" + id);
	}

	/**
	 * @MethodName: addUserAmount
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午09:29:16
	 * @Return:
	 * @Descb: 添加用户奖励
	 * @Throws:
	 */
	public long addUserAmount(Connection conn, long userId, double money) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_user set usableSum = usableSum + " + money + "  where id =" + userId);
		return MySQL.executeNonQuery(conn, command.toString());
	}

	/**
	 * @MethodName: queryOverDueRepayment
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午10:28:39
	 * @Return:
	 * @Descb: 查询逾期的还款 合和年3天内不算罚息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryOverDueRepayment(Connection conn, String date) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		// 合和年3天内不算罚息
		command.append("select id ,principalBalance ,borrowId ,DATEDIFF('" + date + "',repayDate) overdueDay ");
		command.append(" from t_repayment where repayStatus = 1 and repayDate < '" + date + "'");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @throws SQLException
	 * @MethodName: updateOverDueRepayment
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午10:36:31
	 * @Return:
	 * @Descb: 更新逾期的还款
	 * @Throws:
	 */
	public long updateOverDueRepayment(Connection conn, long id, double lateFee, int overdueDay, String executeTime) throws SQLException {
		long returnId = -1;
		String command = "update t_repayment set isLate = 2,lateDay = " + overdueDay + ",lateFI =" + lateFee + ",executeTime = '" + executeTime + "'  where id =" + id;
		returnId = Database.executeNonQuery(conn, command);
		command = null;
		return returnId;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryDeductedXLFee
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午10:44:54
	 * @Return:
	 * @Descb: 查询产生的学历认证费用
	 * @Throws:
	 */
	public List<Map<String, Object>> queryDeductedXLFee(Connection conn) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select a.id,a.userId,a.freeEducation,b.username,b.usableSum from t_education_cost a left join t_user b on a.userId=b.id where a.status = 1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: updateXLFeeStatus
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午10:49:54
	 * @Return:
	 * @Descb: 更新扣除学历认证费状态
	 * @Throws:
	 */
	public long updateXLFeeStatus(Connection conn, long costId) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_education_cost set status = 2 where id =" + costId);
		return MySQL.executeNonQuery(conn, command.toString());
	}

	/**
	 * @MethodName: deductedXLFee
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午10:50:12
	 * @Return:
	 * @Descb: 扣除用户学历认证产生的费用
	 * @Throws:
	 */
	public long deductedXLFee(Connection conn, long userId, double freeEducation) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_user set usableSum = usableSum -" + freeEducation + " where id =" + userId);
		return MySQL.executeNonQuery(conn, command.toString());
	}

	/**
	 * add by houli 查询某用户是否是首次成为vip
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryFirstVipById(Connection conn, Long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select id from t_user where vipStatus =2 and feeStatus=1 and isFirstVip = 1 " + " and id =" + userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * add by houli 查找邀请好友的用户名
	 * 
	 * @param conn
	 * @param mId
	 * @param muid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryFriendInfo(Connection conn, Long mId, Long muid) throws SQLException, DataException {
		Dao.Views.v_t_recommendfriend_list vlist = new Dao().new Views().new v_t_recommendfriend_list();
		DataSet dataSet = vlist.open(conn, " username ", " mId=" + mId + " and muid =" + muid, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: clearExceptionDate
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-5-12 上午10:01:08
	 * @Return:
	 * @Descb: 清除异常数据
	 * @Throws:
	 */
	public void clearExceptionDate(Connection conn) throws SQLException {
		Dao.Tables.t_money t_money = new Dao().new Tables().new t_money();
		t_money.delete(conn, " userId <=0");
	}

	// add by houli
	public long updateRewardStatus(Connection conn, long userId) throws SQLException {
		Dao.Tables.t_money t_money = new Dao().new Tables().new t_money();
		t_money.status.setValue(2);
		t_money.userId.setValue(userId);
		t_money.endData.setValue(new Date());
		return t_money.update(conn, " userId=" + userId + " AND status=1");
	}

	/*
	 * public List<Map<String, Object>> queryOverDueInvestRepayment( Connection
	 * conn, String date) throws SQLException, DataException { String command =
	 * "select a.id,a.recivedPrincipal,a.recivedInterest from t_invest_repayment a left join t_invest b "
	 * + " on a.invest_id = b.id left join t_borrow c  on b.borrowId=c.id " +
	 * " AND DATE_FORMAT(a.repayDate,'%Y-%m-%d') <= '" + date +
	 * "' and a.repayStatus = 1 and c.borrowShow=1 and c.borrowStatus=4" +
	 * " and a.isWebRepay = 1"; DataSet dataSet = MySQL.executeQuery(conn,
	 * command); dataSet.tables.get(0).rows.genRowsMap(); command = null; return
	 * dataSet.tables.get(0).rows.rowsMap; }
	 */
	/**
	 * @MethodName: queryOverDueInvestRepayment
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-6-2 下午10:33:01
	 * @Return:
	 * @Descb: 查询逾期的投资还款
	 * @Throws:
	 */
	public List<Map<String, Object>> queryOverDueInvestRepayment(Connection conn, String date) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select a.id as id,a.repayid as repayId,b.islate as isLate,b.lateday as lateDay, c.borrowWay, c.borrowStatus,b.stillPrincipal as principal,");
		command.append("a.recivedPrincipal as recivedPrincipal,a.recivedInterest as recivedInterest ,b.borrowId as borrowId,b.lateFI  from ");
		command.append("t_invest_repayment a LEFT JOIN t_repayment b ON a.repayId=b.id left join t_borrow c  on b.borrowId =c.id  ");
		command.append("  where   ");
		command.append(" b.repayDate <= '" + date + "' and b.repayStatus = 1 and b.isWebRepay=1");
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		command = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: updateOverDueInvestRepayment
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-6-2 下午10:36:50
	 * @Return:
	 * @Descb: 更新逾期的投资还款
	 * @Throws:
	 */
	public long updateOverDueInvestRepayment(Connection conn, long id, long repayId, double lateFee, int lateDay, int isLate) throws SQLException {
		long returnId = -1;
		String command = "update t_invest_repayment set isLate = " + isLate + ",lateDay = " + lateDay + ",recivedFI=" + lateFee + " where repayId =" + repayId + " and id=" + id;
		returnId = Database.executeNonQuery(conn, command);
		command = null;
		return returnId;
	}

	/**
	 * 查询所有模板信息
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryTmpleAll(Connection conn, PageBean<Map<String, Object>> pageBean) throws SQLException, DataException {
		Dao.Tables.t_notice_task t_notice_task = new Dao().new Tables().new t_notice_task();
		long c = t_notice_task.getCount(conn, "");
		boolean result = pageBean.setTotalNum(c);
		if (result) {
			DataSet ds = t_notice_task.open(conn, " * ", " ", " ", pageBean.getStartOfPage(), pageBean.getPageSize());
			ds.tables.get(0).rows.genRowsMap();// 将DataSet转换成map
			pageBean.setPage(ds.tables.get(0).rows.rowsMap);// 放入PageBean 类
		}
	}

	/**
	 * 根据编号查询ID
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> sendTemplate(Connection conn, int id) throws SQLException, DataException {
		Dao.Tables.t_notice_task t_notice_task = new Dao().new Tables().new t_notice_task();
		DataSet dataSet = t_notice_task.open(conn, " * ", " id = " + id, "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();

		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 删除
	 * 
	 * @param conn
	 * @param id
	 * @throws SQLException
	 */
	public void deleteTemple(Connection conn, long id) throws SQLException {
		Dao.Tables.t_notice_task t_notice_task = new Dao().new Tables().new t_notice_task();
		t_notice_task.delete(conn, " id = " + id);

	}

	/**
	 * 自动还款 更新逾期借款人还款记录
	 * 
	 * @param conn
	 * @param repayId
	 * @param repayAmount
	 * @throws SQLException
	 */
	public long updateOverDueRepayment(Connection conn, Long repayId) throws SQLException {
		Dao.Tables.t_repayment t_repayment = new Dao().new Tables().new t_repayment();
		t_repayment.isWebRepay.setValue(2);
		return t_repayment.update(conn, " id=" + repayId);
	}

	/**
	 * 自动还款 添加自动还款记录
	 * 
	 * @param conn
	 * @param repayId
	 * @param repayAmount
	 * @param oporator
	 * @throws SQLException
	 */
	public long addRepaymentRecord(Connection conn, Long repayId, Double repayAmount, int oporator) throws SQLException {
		Dao.Tables.t_repayment_record t_repayment_record = new Dao().new Tables().new t_repayment_record();
		t_repayment_record.createTime.setValue(new Date());
		t_repayment_record.oporator.setValue(oporator);
		t_repayment_record.repayType.setValue("自动还款");
		t_repayment_record.repayAmount.setValue(repayAmount);
		t_repayment_record.repayId.setValue(repayId);
		t_repayment_record.oporatorIp.setValue("192.168.0.1");
		return t_repayment_record.insert(conn);
	}

	public void sendShortMassege(Connection conn, long id) throws SQLException {
		String command = "update t_short_massege set status =1 where id =" + id;
		Database.executeNonQuery(conn, command);
		command = null;
	}

	public void updateBorrow(Connection conn) throws SQLException {
		String command = "update t_borrow set borrowStatus =2 where isTimes=0 and borrowStatus=8 and publishTime < SYSDATE()";
		Database.executeNonQuery(conn, command);
	}

	public int updateInvestRepayment(Connection conn, long borrowId, long payId, String repayPeriod, Double stillPrincipal, Double stillInterest, String basepath)
			throws SQLException, DataException {
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Dao.Procedures.p_invest_auto_webpay(conn, ds, outParameterValues, borrowId, payId, repayPeriod, new BigDecimal(stillPrincipal), new BigDecimal(stillInterest), basepath,
				-1, "");
		return Convert.strToInt(outParameterValues.get(0) + "", -1);
	}
	
	/**
	 * @MethodName: queryUserAmount
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-15 下午01:42:32
	 * @Return:
	 * @Descb: 查询用户预留保留金额时的可用金额
	 * @Throws:
	 */
	public Map<String, String> queryUserGroupAndAmount(Connection conn, double remandAmount, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select (usableSum-" + remandAmount + ") usableSum,usrCustId,userGroup from t_user where (usableSum-" + remandAmount + ") > 0 and id = " + userId + " limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * @MethodName: queryBiderByParam
	 * @Param: JobTaskDao
	 * @Author: lizg
	 * @Date: 2013-4-14 下午11:04:12
	 * @Return:
	 * @Descb: 查询符合用户设置的自动投标参数的标的
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBiderListByParam(Connection conn, double rateStart, double rateEnd, int deadLineStart, int deadLineEnd, int creditStart, int creditEnd, long id,
			int isDayThe) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		if (isDayThe == IConstants.DAY_THE_2) {
			// 是天标时，需要将期限更改成天
			if (deadLineStart < 0) {
				// 用户设置了天标的条件
				deadLineStart = Math.abs(deadLineStart);
				deadLineEnd = Math.abs(deadLineEnd);
			}
		}
		// 月标
		command.append("select * from v_t_autobid_borrow where annualRate >= " + rateStart + " and annualRate <= " + rateEnd);
		command.append(" and deadline >= " + deadLineStart + " and deadline <=" + deadLineEnd + " ");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

}
