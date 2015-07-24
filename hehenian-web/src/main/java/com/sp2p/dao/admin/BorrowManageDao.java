package com.sp2p.dao.admin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_user;

/**
 * @ClassName: BorrowManageDao.java
 * @Author: gang.lv
 * @Date: 2013-3-10 下午10:06:38
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 后台借款管理数据处理
 */
public class BorrowManageDao {

	/**
	 * @MethodName: queryBorrowFistAuditDetailById
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:19:43
	 * @Return:
	 * @Descb: 后台借款管理中的初审借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFistAuditDetailById(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_h_firstaudit_detail t_borrow_h_firstaudit_detail = new Dao().new Views().new v_t_borrow_h_firstaudit_detail();
		DataSet dataSet = t_borrow_h_firstaudit_detail.open(conn, " * ", "  id=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	// 借款管理中，等待资料初审借款总额

	/**
	 * @MethodName: queryBorrowTenderInDetailById
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:19:43
	 * @Return:
	 * @Descb: 后台借款管理中的招标中借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowTenderInDetailById(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_h_tenderin_detail v_t_borrow_h_tenderin_detail = new Dao().new Views().new v_t_borrow_h_tenderin_detail();
		DataSet dataSet = v_t_borrow_h_tenderin_detail.open(conn, " * ", " id=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryBorrowFullScaleDetailById
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:19:43
	 * @Return:
	 * @Descb: 后台借款管理中的满标借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFullScaleDetailById(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_h_fullscale_detail v_t_borrow_h_fullscale_detail = new Dao().new Views().new v_t_borrow_h_fullscale_detail();
		DataSet dataSet = v_t_borrow_h_fullscale_detail.open(conn, " * ", " id=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryBorrowFlowMarkDetailById
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:19:43
	 * @Return:
	 * @Descb: 后台借款管理中的流标借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFlowMarkDetailById(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_h_flowmark_detail v_t_borrow_h_flowmark_detail = new Dao().new Views().new v_t_borrow_h_flowmark_detail();
		DataSet dataSet = v_t_borrow_h_flowmark_detail.open(conn, " * ", " id=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryBorrowAllDetailById
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:19:43
	 * @Return:
	 * @Descb: 后台借款管理中的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowAllDetailById(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_h_detail t_borrow_h_detail = new Dao().new Views().new v_t_borrow_h_detail();
		DataSet dataSet = t_borrow_h_detail.open(conn, " * ", " id=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: updateBorrowFistAuditStatus
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午05:34:17
	 * @Return:
	 * @Descb: 更新初审中的借款状态
	 * @Throws:
	 */
	public Long updateBorrowFistAuditStatus(Connection conn, long id, long status, String auditOpinion, int sorts) throws SQLException {
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		Date date = new Date();
		long returnId = -1L;
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE t_borrow SET  borrowStatus =" + status);
		sql.append(", sort  =" + sorts);
		sql.append(",auditOpinion ='" + StringEscapeUtils.escapeSql(auditOpinion) + "'");
		if (IConstants.BORROW_STATUS_2 == status) {
			sql.append(",remainTimeStart='" + sf.format(date));
			sql.append("',remainTimeEnd = DATE_ADD('" + sf.format(date) + "',INTERVAL raiseTerm DAY)");
			sql.append(",autoBidEnableTime = DATE_ADD('" + sf.format(date) + "',INTERVAL 15 MINUTE)");
		}
		sql.append(" where id=" + id);
		returnId = MySQL.executeNonQuery(conn, sql.toString());
		sql = null;
		return returnId;
	}

	/**
	 * @MethodName: updateBorrowTenderInStatus
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:18:19
	 * @Return:
	 * @Descb: 更新招标中的借款状态
	 * @Throws:
	 */
	public Long updateBorrowTenderInStatus(Connection conn, long id, String auditOpinion) throws SQLException {
		long returnId = -1L;
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		StringBuffer command = new StringBuffer();
		command.append("update t_borrow set remainTimeStart = '" + sf.format(new Date()) + "',remainTimeEnd = date_add('" + sf.format(new Date())
				+ "',INTERVAL raiseTerm day) where id =" + id);
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.auditOpinion.setValue(StringEscapeUtils.escapeSql(auditOpinion));
		returnId = t_borrow.update(conn, " id =" + id);
		returnId = MySQL.executeNonQuery(conn, command.toString());
		command = null;
		sf = null;
		return returnId;
	}

	/**
	 * @MethodName: reBackBorrow
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午04:04:33
	 * @Return:
	 * @Descb: 撤销借款轮为流标
	 * @Throws:
	 */
	public Long reBackBorrow(Connection conn, long id, int borrowStatus, int sorts) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowStatus.setValue(borrowStatus);
		t_borrow.sort.setValue(sorts);
		return t_borrow.update(conn, " id =" + id + " and borrowStatus in(2,3)");
	}

	/**
	 * @throws DataException
	 * @MethodName: updateBorrowFullScaleStatus
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:18:07
	 * @Return:
	 * @Descb: 更新满标借款状态
	 * @Throws:
	 */
	public int updateBorrowFullScaleStatus(Connection conn, long id, int status, String auditOpinion, long userId, int sorts) throws SQLException,
			DataException {
		int returnId = -1;
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.auditOpinion.setValue(auditOpinion);
		t_borrow.borrowStatus.setValue(status);
		t_borrow.auditTime.setValue(new Date());
		t_borrow.sort.setValue(sorts);
		returnId = (int) t_borrow.update(conn, " id =" + id + " and borrowStatus = 3");
		return returnId;
	}

	/**
	 * @MethodName: updateBorrowStatus
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:17:56
	 * @Return:
	 * @Descb: 更新借款状态
	 * @Throws:
	 */
	public Long updateBorrowStatus(Connection conn, long id, long status, String auditOpinion) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowStatus.setValue(status);
		t_borrow.auditOpinion.setValue(auditOpinion);
		return t_borrow.update(conn, " id =" + id);
	}

	/**
	 * @MethodName: sendBorrowMail
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午03:51:27
	 * @Return:
	 * @Descb: 发送借款审核站内信
	 * @Throws:
	 */
	public Long sendBorrowMail(Connection conn, long borrowId, long reciver, String mailTitle, String content, int mailType, long sender)
			throws SQLException {
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		t_mail.reciver.setValue(reciver);
		t_mail.sender.setValue(sender);
		t_mail.mailContent.setValue(content);
		t_mail.mailTitle.setValue(mailTitle);
		// 系统站内信
		t_mail.mailType.setValue(mailType);
		t_mail.sendTime.setValue(new Date());
		t_mail.borrowId.setValue(borrowId);
		return t_mail.insert(conn);
	}

	/**
	 * @MethodName: queryUserAssociatedBorrow
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午04:59:30
	 * @Return:
	 * @Descb: 查询借款记录的发布者和投标者
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserAssociatedBorrow(Connection conn, long id) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append("(SELECT a.investor as reciver,a.realAmount as borrowSum,b.id,b.borrowTitle FROM t_invest a LEFT JOIN t_borrow b ON a.borrowId=b.id WHERE b.id ="
				+ id);
		sql.append(") UNION ALL (SELECT c.publisher AS investor,NULL AS borrowSum,c.id,c.borrowTitle FROM t_borrow c WHERE c.id =" + id);
		sql.append(")");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;

	}

	/**
	 * @MethodName: rebackInvestAmount
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午06:34:33
	 * @Return:
	 * @Descb: 返还投资人投资的金额
	 * @Throws:
	 */
	public Long rebackInvestAmount(Connection conn, long userId, BigDecimal borrowAmount) throws SQLException {
		long returnId = -1;
		returnId = MySQL.executeNonQuery(conn, " update t_user set usableSum = usableSum+" + borrowAmount + ",freezeSum=freezeSum-" + borrowAmount
				+ " where id = " + userId);
		return returnId;
	}


	/**
	 * @MethodName: queryBorrowInfo
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-24 下午08:46:41
	 * @Return:
	 * @Descb: 查询根据还款ID 查询借款ID
	 * @Throws:
	 */
	public Map<String, String> queryBorrowByrepayId(Connection conn, long idLong) throws SQLException, DataException {
		String command = "SELECT id ,borrowId FROM t_repayment WHERE   id =" + idLong + " limit 0,1";
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: updateFistAudit
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-24 下午08:57:41
	 * @Return:
	 * @Descb: 更新撤消初审未通过的借款标
	 * @Throws:
	 */
	public long updateFistAudit(Connection conn, long idLong, String auditOpinion, int sorts) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowStatus.setValue(IConstants.BORROW_STATUS_6);
		if (StringUtils.isNotBlank(auditOpinion)) {
			t_borrow.auditOpinion.setValue(auditOpinion);
		}
		t_borrow.sort.setValue(sorts);
		return t_borrow.update(conn, " borrowStatus =1 and id = " + idLong);
	}

	/**
	 * @MethodName: rebackUserAmount
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-24 下午09:01:37
	 * @Return:
	 * @Descb: 返还用户冻结资金
	 * @Throws:
	 */
	public long rebackUserAmount(Connection conn, double fee, long publisher) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append(" update t_user set usableSum = usableSum+" + fee + ",freezeSum=freezeSum-" + fee);
		command.append(" where id=" + publisher);
		long result = MySQL.executeNonQuery(conn, command.toString());
		command = null;
		return result;
	}

	/**
	 * @throws SQLException
	 * @MethodName: rebackUserCreditLimit
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-24 下午09:01:50
	 * @Return:
	 * @Descb: 返还用户信用额度
	 * @Throws:
	 */
	public long rebackUserCreditLimit(Connection conn, double borrowAmount, int publisher) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append(" update t_user set usableCreditLimit = usableCreditLimit+" + borrowAmount);
		command.append(" where id=" + publisher);
		long result = MySQL.executeNonQuery(conn, command.toString());
		command = null;
		return result;
	}

	/**
	 * @MethodName: queryInvestBorrow
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午08:59:07
	 * @Return:
	 * @Descb: 查询投资和借款信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryInvestBorrow(Connection conn, long id) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,b.id as borrowId,a.investor as investor,c.username as investorName,d.username as borrowerName,a.realAmount,b.borrowTitle,b.publisher,b.borrowAmount,b.paymentMode,b.isDayThe,");
		sql.append("b.annualRate,b.deadline, b.excitationType,b.excitationSum from t_invest a left join t_borrow b on a.borrowId=b.id");
		sql.append(" left join t_user c on a.investor=c.id left join t_user d on b.publisher = d.id");
		sql.append(" where  b.id is not null and b.id =" + id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryUserAmountAfterHander
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午09:11:22
	 * @Return:
	 * @Descb: 查询用户 的资金
	 * @Throws:
	 */
	public Map<String, String> queryUserAmountAfterHander(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select a.usableSum,a.freezeSum,a.lastIP as lastIP,sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest) forPI");
		command.append(" from t_user a left join t_invest b on a.id = b.investor where a.id=" + userId + " group by a.id");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	

	/**
	 * @MethodName: queryBorrowInfo
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-24 下午08:46:41
	 * @Return:
	 * @Descb: 查询借款信息
	 * @Throws:
	 */
	public Map<String, String> queryBorrowInfo(Connection conn, long idLong) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select  a.borrowShow, a.borrowAmount ,a.deadline,a.isDayThe,a.borrowWay,a.publisher,a.auditTime as auditTime,b.source,");
		command.append(" a.annualRate,a.borrowTitle,b.username ,a.frozen_margin,a.feestate,a.feelog ,d.realName as realName, d.idNo as idNo");
		command.append(" from t_borrow a left join t_user b on a.publisher=b.id LEFT JOIN t_person d on d.userId = b.id  where a.id=" + idLong);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String, String> queBorrowInfo(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		DataSet dataSet = t_borrow.open(conn, " borrowTitle,publisher ,feestate,feelog ,borrowWay", " id=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: updateUserAmount
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午10:59:14
	 * @Return:
	 * @Descb: 更新用户的资金
	 * @Throws:
	 */
	public long updateUserAmount(Connection conn, long investor, double realAmount, double excitationSum) throws SQLException {
		long returnId = -1;
		returnId = MySQL.executeNonQuery(conn, " update t_user set usableSum = usableSum+" + excitationSum + ",freezeSum=freezeSum-" + realAmount
				+ " where id =" + investor);
		return returnId;
	}

	/**
	 * @MethodName: addUserAmount
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-30 下午01:49:09
	 * @Return:
	 * @Descb: 添加用户资金
	 * @Throws:
	 */
	public long addUserAmount(Connection conn, long userId, double amount) throws SQLException {
		long returnId = -1;
		returnId = MySQL.executeNonQuery(conn, " update t_user set usableSum = usableSum+" + amount + " where id =" + userId);
		return returnId;
	}

	/**
	 * @throws SQLException
	 * @MethodName: deducatedUserFreezeAmount
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-30 下午01:50:38
	 * @Return:
	 * @Descb: 扣除冻结资金
	 * @Throws:
	 */
	public long deducatedUserFreezeAmount(Connection conn, long userId, double amount) throws SQLException {
		long returnId = -1;
		returnId = MySQL.executeNonQuery(conn, " update t_user set freezeSum=freezeSum-" + amount + " where id =" + userId);
		return returnId;
	}

	/**
	 * @MethodName: deducatedUserAmount
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-30 下午01:59:21
	 * @Return:
	 * @Descb: 扣除可用资金
	 * @Throws:
	 */
	public long deducatedUserAmount(Connection conn, long userId, double amount) throws SQLException {
		long returnId = -1;
		returnId = MySQL.executeNonQuery(conn, " update t_user set usableSum=usableSum-" + amount + " where id =" + userId);
		return returnId;
	}

	/**
	 * @throws SQLException
	 * @MethodName: updateInvestAmount
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午11:09:23
	 * @Return:
	 * @Descb: 更新投资收益资金
	 * @Throws:
	 */
	public long updateInvestAmount(Connection conn, long investId, double realAmount, double totalInterest, double excitationSum) throws SQLException {
		long returnId = -1;
		returnId = MySQL.executeNonQuery(conn, " update t_invest set recivedPrincipal = " + realAmount + ",recievedInterest=" + totalInterest
				+ ",reward=" + excitationSum + " where id =" + investId);
		return returnId;
	}

	/**
	 * @MethodName: queryBorrowDetail
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-22 上午09:32:00
	 * @Return:
	 * @Descb: 查询借款的详细内容
	 * @Throws:
	 */
	public Map<String, String> queryBorrowDetail(Connection conn, long id) throws DataException, SQLException {
		// Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		// //-----modify by houli 添加天标字段(isDayThe)
		// DataSet dataSet = t_borrow.open(conn,
		// " publisher,borrowTitle,annualRate,paymentMode,borrowAmount,deadline,borrowWay,excitationType,excitationSum,isDayThe ",
		// "  id="+id, "", 0, 1);
		// ---------------
		StringBuffer command = new StringBuffer();
		command.append(" select a.id as borrowId, a.publisher,a.borrowTitle,");
		command.append(" a.annualRate,a.paymentMode,a.borrowAmount,");
		command.append(" a.deadline,a.borrowWay,a.excitationType,");
		command.append(" a.excitationSum,a.isDayThe,b.username,a.frozen_margin   from t_borrow a left join t_user b on a.publisher=b.id where a.id="
				+ id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
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
	 * @MethodName: addRiskAmount
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-22 上午10:35:37
	 * @Return:
	 * @Descb: 累加风险保障金
	 * @Throws:
	 */
	public long addRiskAmount(Connection conn, double riskBalance, double riskAmount, long publisher, long id) throws SQLException {
		Dao.Tables.t_risk_detail t_risk_detail = new Dao().new Tables().new t_risk_detail();
		t_risk_detail.riskBalance.setValue(riskBalance);
		t_risk_detail.riskInCome.setValue(riskAmount);
		t_risk_detail.riskDate.setValue(new Date());
		t_risk_detail.riskType.setValue("收入");
		t_risk_detail.resource.setValue("借款成功累加风险保障金");
		t_risk_detail.trader.setValue(publisher);
		t_risk_detail.borrowId.setValue(id);
		return t_risk_detail.insert(conn);
	}

	/**
	 * @throws SQLException
	 * @MethodName: updateBorrowerAmount
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-22 上午11:09:23
	 * @Return:
	 * @Descb: 更新借款人的资金
	 * @Throws:
	 */
	public long updateBorrowerAmount(Connection conn, long publisher, double realAmount) throws SQLException {
		long returnId = -1;
		returnId = MySQL.executeNonQuery(conn, " update t_user set usableSum = usableSum+" + realAmount + " where id =" + publisher);
		return returnId;
	}

	/**
	 * @throws SQLException
	 * @MethodName: addRepayRecord
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-22 上午11:22:22
	 * @Return:
	 * @Descb: 添加还款记录
	 * @Throws:
	 */
	public long addRepayRecord(Connection conn, String repayPeriod, double stillPrincipal, double stillInterest, long id, double principalBalance,
			double interestBalance, String repayDate) throws SQLException {
		Dao.Tables.t_repayment t_repayment = new Dao().new Tables().new t_repayment();
		t_repayment.repayPeriod.setValue(repayPeriod);
		t_repayment.stillPrincipal.setValue(stillPrincipal);
		t_repayment.stillInterest.setValue(stillInterest);
		t_repayment.borrowId.setValue(id);
		t_repayment.principalBalance.setValue(principalBalance);
		t_repayment.interestBalance.setValue(interestBalance);
		t_repayment.repayDate.setValue(repayDate);
		return t_repayment.insert(conn);
	}

	/**
	 * @MethodName: rebackInvestAmount
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午06:34:33
	 * @Return:
	 * @Descb: 返还投资人投资的金额
	 * @Throws:
	 */
	public long rebackInvestAmount(Connection conn, long userId, double borrowAmount) throws SQLException {
		long returnId = -1;
		returnId = MySQL.executeNonQuery(conn, " update t_user set usableSum = usableSum+" + borrowAmount + ",freezeSum=freezeSum-" + borrowAmount
				+ " where id = " + userId);
		return returnId;
	}

	/**
	 * @MethodName: updateInvestInfo
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-25 下午04:39:51
	 * @Return:
	 * @Descb: 更新生意贷投资记录
	 * @Throws:
	 */
	public long updateInvestInfo(Connection conn, long investId, double realAmount, double totalInterest, double iManageFee) throws SQLException {
		long returnId = -1;
		returnId = Database.executeNonQuery(conn, " update t_invest set hasPrincipal = " + realAmount + ",hasInterest=" + totalInterest
				+ ",manageFee=" + iManageFee + ",repayStatus=2 where id = " + investId);
		return returnId;
	}

	/**
	 * @MethodName: updateEarnAmount
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-25 下午04:41:22
	 * @Return:
	 * @Descb: 更新用户生意贷收益金额
	 * @Throws:
	 */
	public long updateEarnAmount(Connection conn, long userId, double earnAmount) throws SQLException {
		long returnId = -1;
		returnId = MySQL.executeNonQuery(conn, " update t_user set usableSum =usableSum+" + earnAmount + " where id = " + userId);
		return returnId;
	}

	/**
	 * @MethodName: updateBorrowRepayment
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-25 下午05:31:15
	 * @Return:
	 * @Descb: 更新借款还款记录
	 * @Throws:
	 */
	public long updateBorrowRepayment(Connection conn, long id, double totalAmount) throws SQLException {
		Dao.Tables.t_repayment t_repayment = new Dao().new Tables().new t_repayment();
		t_repayment.hasPI.setValue(totalAmount);
		t_repayment.repayStatus.setValue(2);
		t_repayment.realRepayDate.setValue(new Date());
		return t_repayment.update(conn, " id =" + id);
	}

	/**
	 * @MethodName: updateBorrowerPay
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-25 下午05:47:37
	 * @Return:
	 * @Descb: 更新借款还款
	 * @Throws:
	 */
	public long updateBorrowerPay(Connection conn, long publisher, double totalAmount) throws SQLException {
		long returnId = -1;
		returnId = MySQL.executeNonQuery(conn, " update t_user set usableSum =usableSum-" + totalAmount + " where id = " + publisher);
		return returnId;
	}

	/**
	 * @MethodName: updateBorrowerStatus
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-25 下午05:53:43
	 * @Return:
	 * @Descb: 更新借款状态
	 * @Throws:
	 */
	public long updateBorrowerStatus(Connection conn, long id, int sorts) throws SQLException {
		long returnId = -1;
		returnId = MySQL.executeNonQuery(conn, " update t_borrow set borrowStatus = 5,hasInvestAmount = borrowAmount , sort = " + sorts
				+ "  where id =" + id);
		return returnId;
	}

	/**
	 * add by houli 查询所有的等待资料审核的借款
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAllWaitingBorrow(Connection conn) throws SQLException, DataException {
		String sqlStr = "select  a.borrowShow,a. id ,a. userId ,a. username ,a. realName ," + "b.counts as  counts ,a. borrowWay ,a. borrowTitle ,"
				+ "a. borrowAmount ,a. annualRate ,a. deadline ,a. raiseTerm   "
				+ "from v_t_borrow_h_firstaudit a LEFT JOIN (SELECT userid,COUNT(1) AS counts "
				+ "FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a. userId =b.userid "
				+ " INNER JOIN v_t_user_un_approve_lists c on a. userId  = c. uid  where a. borrowWay  >2 "
				+ "UNION ALL SELECT  a.borrowShow,a. id ,a. userId ,a. username ,a. realName ,b.counts "
				+ "as  counts ,a. borrowWay ,a. borrowTitle ,a. borrowAmount ,a. annualRate ,"
				+ "a. deadline ,a. raiseTerm   from v_t_borrow_h_firstaudit a LEFT JOIN "
				+ "(SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3  "
				+ "GROUP BY userid) b ON a. userId =b.userid   INNER JOIN v_t_user_base_approve_lists d"
				+ "  on a. userId =d. uuid  where a. borrowWay  <3 and d. auditStatus !=3";

		DataSet dataSet = Database.executeQuery(conn, sqlStr);
		dataSet.tables.get(0).rows.genRowsMap();
		sqlStr = null;
		return dataSet.tables.get(0).rows.rowsMap;

	}

	/**
	 * @MethodName: updateBorrowManageFee
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-5-7 下午08:45:53
	 * @Return:
	 * @Descb: 更新借款管理费
	 * @Throws:
	 */
	public long updateBorrowManageFee(Connection conn, long id, double bManageFee) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.manageFee.setValue(bManageFee);
		return t_borrow.update(conn, " id=" + id);
	}

	/**
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryCirculationRepayRecord
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午07:35:26
	 * @Return:
	 * @Descb: 查询流转标还款记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryCirculationRepayRecord(Connection conn, long borrowId) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,b.* from v_t_repayment_record_list a left join (select borrowId as bId, sum(repayAmount) as repaySum ");
		sql.append(" from v_t_repayment_record_list where borrowId=" + borrowId + " group by borrowId) b on a.borrowId=b.bId");
		sql.append(" where a.borrowId=" + borrowId + " order by a.id desc");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryBorrowCirculationDetailById
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午02:24:15
	 * @Return:
	 * @Descb: 查询流转标详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowCirculationDetailById(Connection conn, long id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select a.excitationType as excitationType,a.excitationSum as excitationSum,a.id,b.id as userId,b.username,c.realName,a.borrowTitle,a.borrowAmount,a.annualRate,a.smallestFlowUnit,");
		command.append(" a.deadline,a.hasCirculationNumber,(a.circulationNumber-a.hasCirculationNumber) as remainCirculationNumber,");
		command.append(" a.publishTime,a.publishIP,a.businessDetail,a.assets,a.moneyPurposes from t_borrow a left join t_user b on");
		command.append(" a.publisher=b.id left join t_person c on c.userId=b.id where a.id=" + id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: updateBorrowCirculationStatus
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午03:14:40
	 * @Return:
	 * @Descb: 更新流转标状态
	 * @Throws:
	 */
	public Long updateBorrowCirculationStatus(Connection conn, Long id, long statusLong, String auditOpinion,/*
																											 * double
																											 * rewardDouble
																											 * ,
																											 */
			int circulationStatus, int sort) throws SQLException {
		StringBuffer command = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		auditOpinion = StringEscapeUtils.escapeSql(auditOpinion.trim());
		command.append("update t_borrow set borrowStatus =" + statusLong + ",auditOpinion='" + auditOpinion + "',circulationStatus="
				+ circulationStatus + ",sort=" + sort + ",auditTime='" + sf.format(new Date()) + "',autoBidEnableTime = DATE_ADD('"
				+ sf.format(new Date()) + "',INTERVAL 15 MINUTE)" + " where borrowStatus=1 and id=" + id);
		return MySQL.executeNonQuery(conn, command.toString());
	}

	/**
	 * @MethodName: isCirculationBorrow
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午08:41:53
	 * @Return:
	 * @Descb: 查询是否为流转标并是认购中或回购中的状态
	 * @Throws:
	 */
	public Map<String, String> isCirculationBorrow(Connection conn, long repayId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select a.id,a.borrowId,b.borrowTitle,a.stillPrincipal as stillPrincipal,a.stillInterest as stillInterest,a.hasPI as hasPI,a.lateFI");
		command.append(" ,c.username,b.publisher,a.version from t_repayment a left join t_borrow b on a.borrowId=b.id left join t_user c ");
		command.append(" on b.publisher=c.id");
		command.append(" where b.borrowShow = 2 and b.borrowStatus in (2,4) and a.id=" + repayId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: markBorrow
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午08:53:17
	 * @Return:
	 * @Descb: 标记为已还款
	 * @Throws:
	 */
	public long markBorrow(Connection conn, long repayId, int version) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append(" update t_repayment set hasPI=stillPrincipal+stillInterest");
		command.append(" ,repayStatus=2,version=version+1");
		command.append(" where id=" + repayId + " and repayStatus=1 and version=" + version);
		return MySQL.executeNonQuery(conn, command.toString());
	}

	/**
	 * @MethodName: updateBorrow
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午08:53:02
	 * @Return:
	 * @Descb: 更新借款状态
	 * @Throws:
	 */
	public long updateBorrow(Connection conn, long borrowId) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append(" update t_borrow set sort=2,borrowStatus=5 where id=" + borrowId);
		return MySQL.executeNonQuery(conn, command.toString());
	}

	// 借款管理模块，查询各个报表中的总额
	public Map<String, String> queryBorrowTotalFistAuditDetail(Connection conn) throws SQLException, DataException {
		Dao.Views.v_t_borrow_h_firstaudit t_borrow_h_firstaudit = new Dao().new Views().new v_t_borrow_h_firstaudit();
		DataSet dataSet = t_borrow_h_firstaudit.open(conn, "sum(borrowAmount) as totalFirstBorrowAmount", "", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String, String> queryBorrowTotalWait(Connection conn) throws SQLException, DataException {
		Dao.Views.v_t_borrow_h_firstaudit t_borrow_h_firstaudit = new Dao().new Views().new v_t_borrow_h_firstaudit();
		DataSet dataSet = t_borrow_h_firstaudit.open(conn, "sum(borrowAmount) as totalWaitBorrowAmount", "auditStatus=1", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String, String> queryBorrowTotalFullScaleDetail(Connection conn) throws SQLException, DataException {
		Dao.Views.v_t_borrow_h_fullscale t_borrow_h_fullscale = new Dao().new Views().new v_t_borrow_h_fullscale();
		DataSet dataSet = t_borrow_h_fullscale.open(conn, "sum(borrowAmount) as fullScaleTotalAmount", "", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String, String> queryBorrowFullList(Connection conn, long borrowId) throws SQLException, DataException {
		Dao.Tables.t_invest invest = new Dao().new Tables().new t_invest();
		DataSet dataSet = invest.open(conn, " * ", " borrowId=" + borrowId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String, String> queryBorrowTotalTenderDetail(Connection conn) throws SQLException, DataException {
		Dao.Views.v_t_borrow_h_tenderin t_borrow_h_tenderin = new Dao().new Views().new v_t_borrow_h_tenderin();
		DataSet dataSet = t_borrow_h_tenderin.open(conn, "sum(borrowAmount) as tenderBorrowAmount", "", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String, String> queryBorrowFlowMarkDetail(Connection conn) throws SQLException, DataException {
		Dao.Views.v_t_borrow_h_flowmark t_borrow_h_flowmark = new Dao().new Views().new v_t_borrow_h_flowmark();
		DataSet dataSet = t_borrow_h_flowmark.open(conn, "sum(borrowAmount) as flowmarkBorrowAmount", "", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 根据ID查询借款信息--初审
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryFirsttrialById(Connection conn, String id) throws SQLException, DataException {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select tb.id,tb.borrowWay,tb.borrowTitle,tb.borrowAmount,tb.deadline,tb.annualRate,tb.borrowStatus,tu.username,tu.source,tp.realName,"
				+ "tb.minTenderedSum,tb.maxTenderedSum,tb.excitationSum,tb.paymentMode,tb.moneyPurposes,tb.publishIP,tb.publishTime,tb.windControl,tb.publisher");
		sbf.append(" from t_borrow tb left join t_user tu on tb.publisher = tu.id");
		sbf.append(" left join t_person tp on tp.userid = tu.id ");
		sbf.append(" where 1=1 and tb.id=" + id);
		DataSet dataSet = MySQL.executeQuery(conn, sbf.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询投资信息
	 * 
	 * @param conn
	 * @param borrowId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryInvestByBorrowId(Connection conn, long borrowId) throws Exception {
		String sql = "select * from v_t_full_invest where subOrdId = " + borrowId;
		DataSet ds = MySQL.executeQuery(conn, sql);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}

	/**
	 * 初审
	 * 
	 * @param conn
	 * @param ids
	 * @param status
	 * @param windControl
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long updateFirsttrial(Connection conn, String ids, String borrowStatus, String windControl, String firstTrial) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowStatus.setValue(Integer.valueOf(borrowStatus));
		t_borrow.windControl.setValue(windControl);
		t_borrow.firstTrial.setValue(firstTrial);
		return t_borrow.update(conn, "id in (" + ids + ")");
	}

	/**
	 * 根据ID查询借款信息--复审、待发布、撮合
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryRecheckById(Connection conn, String id) throws SQLException, DataException {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select tb.id,tu.id userId,tb.borrowWay,tb.raiseTerm,tb.borrowTitle,tb.borrowAmount,tb.deadline,tb.annualRate,tb.borrowStatus,tu.username,tu.source,tp.realName,tb.firstTrial,"
				+ "tb.minTenderedSum,tb.maxTenderedSum,tb.excitationSum,tb.paymentMode,tb.moneyPurposes,tb.publishIP,tb.publishTime,tb.windControl,tb.publisher,tb.recheck,");
		sbf.append(" (tb.hasInvestAmount/tb.borrowAmount) collagen,tb.borrowGroup from t_borrow tb left join t_user tu on tb.publisher = tu.id");
		sbf.append(" left join t_person tp on tp.userid = tu.id ");
		sbf.append(" where 1=1 and tb.id=" + id);
		DataSet dataSet = MySQL.executeQuery(conn, sbf.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 复审有最高金额限制
	 */
	public long updateRecheck(Connection conn, String ids, String borrowStatus, String windControl, String recheck, String minTenderedSums,
			String maxTenderedSums, double borrowAmount, double annualRate, int deadline, int raiseTerm) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowStatus.setValue(Integer.valueOf(borrowStatus));
		t_borrow.windControl.setValue(windControl);
		t_borrow.recheck.setValue(recheck);
		t_borrow.minTenderedSum.setValue(minTenderedSums);
		t_borrow.maxTenderedSum.setValue(maxTenderedSums);
		t_borrow.borrowAmount.setValue(borrowAmount);
		t_borrow.annualRate.setValue(annualRate);
		t_borrow.deadline.setValue(deadline);
		t_borrow.raiseTerm.setValue(raiseTerm);
		return t_borrow.update(conn, "id in(" + Integer.valueOf(ids) + ")");
	}

	/**
	 * 复审没有最高金额限制
	 * 
	 * @param conn
	 * @param id
	 * @param status
	 * @param windControl
	 * @param firstTrial
	 * @param recheck
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long updateRechecks(Connection conn, String ids, String borrowStatus, String windControl, String recheck, String minTenderedSums)
			throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowStatus.setValue(Integer.valueOf(borrowStatus));
		t_borrow.windControl.setValue(windControl);
		t_borrow.recheck.setValue(recheck);
		t_borrow.minTenderedSum.setValue(minTenderedSums);
		return t_borrow.update(conn, "id in(" + Integer.valueOf(ids) + ")");
	}

	/**
	 * 新增站内信
	 * 
	 * @param ids
	 *            借款ID
	 * @param mailTitle
	 *            站内信标题
	 * @param mailContent
	 *            站内信内容
	 * @param sender
	 *            发件人ID
	 * @param reciver
	 *            收件人ID
	 */
	public long insertEmail(Connection conn, String ids, String mailTitle, String mailContent, long sender, String reciver) throws SQLException {
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		t_mail.mailTitle.setValue(mailTitle);
		t_mail.mailContent.setValue(mailContent);
		t_mail.sendTime.setValue(new Date());
		t_mail.sender.setValue(sender);
		t_mail.reciver.setValue(Integer.valueOf(reciver));
		t_mail.mailType.setValue(2);
		t_mail.mailStatus.setValue(1);
		t_mail.borrowId.setValue(ids);
		t_mail.mailMode.setValue(3);
		t_mail.backgroundStatus.setValue(1);
		return t_mail.insert(conn);
	}

	/**
	 * 重新初审
	 * 
	 * @param raiseTerm
	 */
	public long updateFirsttrialAgain(Connection conn, String id, String borrowStatus, String borrowAmount, String annualRate, String deadline,
			String raiseTerm) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowStatus.setValue(Integer.valueOf(borrowStatus));
		t_borrow.borrowAmount.setValue(borrowAmount);
		t_borrow.annualRate.setValue(annualRate);
		t_borrow.deadline.setValue(deadline);
		t_borrow.raiseTerm.setValue(raiseTerm);
		return t_borrow.update(conn, "id in(" + Integer.valueOf(id) + ")");
	}

	/**
	 * 发布
	 */
	public long updateReleased(Connection conn, String ids) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowStatus.setValue(2); // 发布成功，正在招标中
		t_borrow.remainTimeStart.setValue(new Date()); // 剩余时间开始为当前时间
		t_borrow.remainTimeEnd.setValue(null);
		
		return t_borrow.update(conn, "id in(" + Integer.valueOf(ids) + ")");
	}

	/**
	 * 撮合失败
	 */
	public long updateBring(Connection conn, String ids) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowStatus.setValue(6); // 流标
		return t_borrow.update(conn, "id in(" + Integer.valueOf(ids) + ")");
	}

	/**
	 * 定时
	 */
	public long updateTimer(Connection conn, String ids, String times) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.publishTime.setValue(times);
		t_borrow.isTimes.setValue(0);
		return t_borrow.update(conn, "id in(" + Integer.valueOf(ids) + ")");
	}

	/**
	 * 定时发布借款--更新借款状态
	 */
	public long updateTimerSend(Connection conn, Object id, Integer isTimes) throws SQLException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		t_borrow.borrowStatus.setValue(2);
		t_borrow.remainTimeStart.setValue(new Date());
		return t_borrow.update(conn, "id in(" + id + ") and isTimes = " + isTimes);
	}

	// 借款失败中多项删除
	public Long deleteFailedBorrow(Connection conn, String ids) throws SQLException {
		Dao.Tables.t_borrow borrow = new Dao().new Tables().new t_borrow();
		return borrow.delete(conn, "id in (" + ids + ")");
	}

	public Map<String, String> queryFailedBorrowById(Connection conn, String id) throws SQLException, DataException {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select tb.id,tb.borrowWay,tb.borrowTitle,tb.borrowAmount,tb.deadline,tb.annualRate,tb.borrowStatus,tu.username,tu.source,tp.realName,tb.publisher,"
				+ "tb.minTenderedSum,tb.maxTenderedSum,tb.excitationSum,tb.paymentMode,tb.moneyPurposes,tb.publishIP,tb.publishTime,tb.windControl,tm.mailContent");
		sbf.append(" from t_mail tm right join t_borrow tb on tm.borrowId = tb.id");
		sbf.append(" left join t_user tu on tb.publisher = tu.id");
		sbf.append(" left join t_person tp on tp.userid = tu.id ");
		sbf.append(" where 1=1 and tb.id=" + id);
		DataSet dataSet = MySQL.executeQuery(conn, sbf.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public List<Map<String, Object>> queryBringByIds(Connection conn, String id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_bring brings = new Dao().new Views().new v_t_borrow_bring();
		DataSet dataSet = brings.open(conn, "*", " id in (" + id + ")", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 汇付回调 更新用户 usrCustId
	 */
	public long updateUser(Connection conn, long userId, long usrCustId) throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.usrCustId.setValue(usrCustId);
		return user.update(conn, " id=" + userId);
	}

	public long updatePerson(Connection conn, String userId, String realName, String sex, String age, String maritalStatus, String hasHourse,
			String highestEdu, String creditNum, String creditSum, String hasCar) {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.realName.setValue(realName);
		person.sex.setValue(sex);
		person.age.setValue(age);
		person.maritalStatus.setValue(maritalStatus);
		person.hasHourse.setValue(hasHourse);
		person.highestEdu.setValue(highestEdu);
		person.creditNum.setValue(creditNum);
		person.creditSum.setValue(creditSum);
		person.hasCar.setValue(hasCar);
		return person.update(conn, " userId=" + userId);
	}

	public long updateWork(Connection conn, String userId, String orgName, String companyAddress, String job, String companyType, String workYear,
			String companyLine, String monthlyIncome, String monthlyOutcome) {
		Dao.Tables.t_workauth work = new Dao().new Tables().new t_workauth();
		work.orgName.setValue(orgName);
		work.companyAddress.setValue(companyAddress);
		work.job.setValue(job);
		work.companyType.setValue(companyType);
		work.workYear.setValue(workYear);
		work.companyLine.setValue(companyLine);
		work.monthlyIncome.setValue(monthlyIncome);
		work.monthlyOutcome.setValue(monthlyOutcome);
		return work.update(conn, " userId=" + userId);
	}

	/**
	 * 
	 * <一句话功能简述><功能详细描述>
	 * @param conn
	 * @param map
	 * @return [参数说明]
	 * 
	 */
    public long insertFundrecord(Connection conn,Map<String, Object> map)
    {
        Dao.Tables.t_fundrecord t_fundrecord = new Dao().new Tables().new t_fundrecord();
        t_fundrecord.userId.setValue(map.get("investor"));
        t_fundrecord.fundMode.setValue("投标失败解冻金额");
        t_fundrecord.handleSum.setValue(map.get("transAmt"));
        t_fundrecord.usableSum.setValue(map.get("transAmt"));
        t_fundrecord.freezeSum.setValue(map.get("transAmt"));
        t_fundrecord.dueinSum.setValue(map.get("transAmt"));
        t_fundrecord.trader.setValue(map.get("id"));
        t_fundrecord.recordTime.setValue(UtilDate.getDateFormatter());
        t_fundrecord.dueoutSum.setValue(map.get("transAmt"));
        t_fundrecord.remarks.setValue("投标失败解冻投标金额"+map.get("transAmt"));
        t_fundrecord.income.setValue(map.get("transAmt"));
        t_fundrecord.borrow_id.setValue(map.get("id"));
        return t_fundrecord.insert(conn);
    }

}
