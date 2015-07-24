package com.sp2p.dao.admin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Functions;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.util.DBReflectUtil;

/**
 * 债权管理数据实现类
 * 
 * @author xiemin
 * @version [版本号, 2013-9-30]
 */
public class AssignmentDebtDao {

	/**
	 * 根据ID查询债权信息
	 */
	public Map<String, String> queryAssignmentById(Connection conn, String id) throws SQLException, DataException {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select tb.borrowTitle,tb.borrowWay,tb.borrowStatus,tb.annualRate,tb.deadline as deadlineTime,tb.borrowAmount,"
				+ "tb.moneyPurposes,tb.minTenderedSum,tb.maxTenderedSum,tb.excitationType,tb.paymentMode,tub.username as buserName,"
				+ "tb.publishTime,tb.publishIP,tp.realName,tu.source,tu.username,tb.publisher,tb.excitationSum,ti.*");
		sbf.append(" from t_invest ti left join t_borrow tb on tb.id = ti.borrowId");
		sbf.append(" left join t_assignment_debt tad on tad.alienatorId = ti.investor");
		sbf.append(" left join t_person tp on tp.userId = tb.publisher");
		sbf.append(" left join t_user tu on tu.id = tad.alienatorId");
		sbf.append(" left join t_user tub on tub.id = tb.publisher");
		sbf.append(" where ti.id=" + id);
		DataSet dataSet = MySQL.executeQuery(conn, sbf.toString());
		return BeanMapUtils.dataSetToMap(dataSet);

	}

	/**
	 * 根据还款ID和投资ID查询出还款表信息
	 */
	public Map<String, String> queryInvestRepayment(Connection conn, String id, String borrowId) throws SQLException, DataException {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select * from t_invest_repayment tr where 1=1");
		sbf.append(" tr.borrow_id =" + id + "and tr.invest_id = " + borrowId);
		DataSet dataSet = MySQL.executeQuery(conn, sbf.toString());
		return BeanMapUtils.dataSetToMap(dataSet);

	}

	/**
	 * 审核
	 */
	public long updateAssginment(Connection conn, String id, String status, String dsc) throws SQLException {
		Dao.Tables.t_msgboard t_msgboard = new Dao().new Tables().new t_msgboard();
		t_msgboard.status.setValue(Integer.valueOf(status));
		t_msgboard.dsc.setValue(dsc);
		return t_msgboard.update(conn, " id in (" + id + ")");
	}

	/**
	 * 债权转让
	 */
	public long assginment(Connection conn, String id, String auctionDays, String auctionBasePrice, String details, String deadline,
			Map<String, String> map, String auctionEndTime) throws SQLException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		t_assignment_debt.auctionDays.setValue(auctionDays);
		t_assignment_debt.auctionBasePrice.setValue(auctionBasePrice);
		t_assignment_debt.details.setValue(details);
		t_assignment_debt.debtStatus.setValue(2);
		t_assignment_debt.debtLimit.setValue(deadline);
		t_assignment_debt.borrowId.setValue(map.get("borrowId"));
		t_assignment_debt.alienatorId.setValue(map.get("investor"));
		t_assignment_debt.investId.setValue(map.get("id"));
		t_assignment_debt.debtSum.setValue(map.get("realAmount"));
		t_assignment_debt.publishTime.setValue(new Date());
		t_assignment_debt.applyTime.setValue(map.get("publishTime"));
		t_assignment_debt.auctionEndTime.setValue(auctionEndTime);
		return t_assignment_debt.insert(conn);
	}

	/**
	 * 根据ID查询转让中的债权信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryTransferById(Connection conn, String id) throws SQLException, DataException {
		StringBuffer sbf = new StringBuffer();
		sbf.append("select tb.borrowTitle,tb.annualRate,tb.minTenderedSum,tb.maxTenderedSum,tub.username as buserName,"
				+ "ti.investAmount,tu.username,tad.id,tad.debtLimit,tad.debtSum,tad.auctionBasePrice,"
				+ "date_format(SYSDATE()-tad.auctionEndTime,'%Y-%m-%d %H:%i:%s') as auctionEndTime,tad.investId");
		sbf.append(" from t_assignment_debt tad left join t_borrow tb on tb.id = tad.borrowId");
		sbf.append(" left join t_person tp on tp.userId = tad.alienatorId");
		sbf.append(" left join t_user tu on tu.id = tad.alienatorId");
		sbf.append(" left join t_user tub on tub.id = tb.publisher");
		sbf.append(" left join t_invest ti on ti.id = tad.investId");
		sbf.append(" where tad.debtStatus = 2 and tad.id=" + id);
		DataSet dataSet = MySQL.executeQuery(conn, sbf.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 更新转让中债权状态--成交，撤回
	 * 
	 * @param conn
	 * @param id
	 * @param debtStatus
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long udadateDebtStatus(Connection conn, String id, String debtStatus) throws SQLException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		t_assignment_debt.debtStatus.setValue(Integer.valueOf(debtStatus));
		return t_assignment_debt.update(conn, "id=" + Integer.valueOf(id));
	}

	// ====
	/**
	 * 根据ID获债权转让信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getAssignmentDebt(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		DataSet ds = t_assignment_debt.open(conn, "*", "id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 查询借款信息
	 * 
	 * @param conn
	 * @param idLong
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryBorrowInfo(Connection conn, long idLong) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select  a.borrowShow, a.borrowAmount ,a.deadline,a.isDayThe,a.borrowWay,a.publisher,a.auditTime as auditTime, ");
		command.append(" a.annualRate,a.borrowTitle,b.username ,a.frozen_margin,a.feestate,a.feelog ,d.realName as realName, d.idNo as idNo");
		command.append(" from t_borrow a left join t_user b on a.publisher=b.id LEFT JOIN t_person d on d.userId = b.id  where a.id=" + idLong);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 更新投资还款记录是债权转让的状态
	 * 
	 * @param conn
	 * @param investId
	 * @param owner
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long updateInvestDebtStatus(Connection conn, long investId, long owner) throws SQLException {
		String command = "update t_invest_repayment SET owner = " + owner + ",ownerlist=concat(ownerlist,'," + owner + "'),isDebt=2 where invest_id="
				+ investId + " and repayStatus=1";
		return MySQL.executeNonQuery(conn, command);
	}

	/**
	 * 添加投资历史表
	 * 
	 * @param conn
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public long addInvestHistory(Connection conn, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_invest_history t_invest_history = new Dao().new Tables().new t_invest_history();
		DBReflectUtil.mapToTableValue(t_invest_history, paramMap);
		return t_invest_history.insert(conn);
	}

	/**
	 * 修改投资表
	 * 
	 * @param conn
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public long updateInvest(Connection conn, long id, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		DBReflectUtil.mapToTableValue(t_invest, paramMap);
		return t_invest.update(conn, " id=" + id);
	}

	/**
	 * 得到认购者信息
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> getUserById(Connection conn, long userId) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet ds = t_user.open(conn, "id,username,dealpwd,usableSum,freezeSum,dueinSum", "id=" + userId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 修改用户金额
	 * 
	 * @param conn
	 * @param userId
	 * @param userMap
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long updateUser(Connection conn, long userId, Map<String, String> userMap) throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DBReflectUtil.mapToTableValue(user, userMap);

		return user.update(conn, "id=" + userId);
	}

	/**
	 * 查询用户操作后的资金记录
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryUserAmountAfterHander(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select ifnull(a.usableSum,0) usableSum,ifnull(a.freezeSum,0) freezeSum,ifnull(sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest),0.0) forPI ,a.lastIP as lastIP ");
		command.append(" from t_user a left join t_invest b on a.id = b.investor where a.id=" + userId + " group by a.id");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 获取借款标题
	 * 
	 * @param conn
	 * @param debtId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
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
	 * 添加资金记录表
	 * 
	 * @param conn
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long addFundRecord(Connection conn, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_fundrecord t_fundrecord = new Dao().new Tables().new t_fundrecord();
		DBReflectUtil.mapToTableValue(t_fundrecord, paramMap);
		return t_fundrecord.insert(conn);
	}

	/**
	 * 添加用户资金
	 * 
	 * @param conn
	 * @param fundType
	 * @param uId
	 * @param money
	 * @param traderId
	 * @param remark
	 * @param addIP
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return int [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public int addAccountUsers(Connection conn, String fundType, Long uId, BigDecimal money, Long traderId, String remark, String addIP)
			throws SQLException, DataException {
		int moneyStyle = Functions.f_moneyEncode(conn, fundType);
		if (moneyStyle == -1)
			return -1;
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		return Procedures.pr_modifyUserAmount(conn, ds, outParameterValues, money, moneyStyle, remark, uId, traderId, new Date(), addIP);

	}

	/**
	 * 获取用户名称
	 * 
	 * @param userId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String queryUserNameById(Connection conn, long userId) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet ds = t_user.open(conn, "username", " id =" + userId, "", -1, -1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		String username = null;
		if (map != null) {
			username = map.get("username");
		}
		map = null;
		return username;
	}

	/**
	 * @MethodName: addRisk
	 * @Param: RiskManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午03:02:24
	 * @Return:
	 * @Descb: 手动添加风险保障金
	 * @Throws:
	 */
	public long addRisk(Connection conn, double amountDouble, long adminId, String remark, double riskBalanceDouble, Date riskDate, String riskType,
			String resource) throws SQLException {
		Dao.Tables.t_risk_detail t_risk_detail = new Dao().new Tables().new t_risk_detail();
		t_risk_detail.riskInCome.setValue(amountDouble);
		t_risk_detail.operator.setValue(adminId);
		t_risk_detail.remark.setValue(remark);
		t_risk_detail.riskBalance.setValue(riskBalanceDouble);
		t_risk_detail.riskDate.setValue(riskDate);
		t_risk_detail.riskType.setValue(riskType);
		t_risk_detail.resource.setValue(resource);
		return t_risk_detail.insert(conn);
	}

	/**
	 * @MethodName: queryRiskBalance
	 * @Param: RiskManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午02:51:57
	 * @Return:
	 * @Descb: 查询风险保障金余额
	 * @Throws:
	 */
	public Map<String, String> queryRiskBalance(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_risk_detail t_risk_detail = new Dao().new Tables().new t_risk_detail();
		DataSet ds = t_risk_detail.open(conn, " sum(riskInCome-riskSpending) riskBalance", "", "", 0, 1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	// =====

	/**
	 * 转让成功则向投资表插入一条新的数据--新的投资人债权产生
	 * 
	 * @param conn
	 * @param map
	 * @param alienatorId
	 * @param auctionerId
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long insertInvest(Connection conn, Map<String, String> map, String alienatorId, String auctionerId, String auctionBasePrice)
			throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.investAmount.setValue(Double.valueOf(auctionBasePrice));
		t_invest.monthRate.setValue(map.get("monthRate"));
		t_invest.investor.setValue(auctionerId);
		t_invest.borrowId.setValue(map.get("borrowId"));
		t_invest.investTime.setValue(map.get("investTime"));
		t_invest.oriInvestor.setValue(alienatorId);
		t_invest.realAmount.setValue(auctionBasePrice);
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
		if (null == map.get("reward") || map.get("reward") == "") {
			t_invest.reward.setValue(0);
		} else {
			t_invest.reward.setValue(map.get("reward"));
		}
		t_invest.repayStatus.setValue(map.get("repayStatus"));
		t_invest.flag.setValue(map.get("flag"));
		t_invest.isAutoBid.setValue(map.get("isAutoBid"));
		t_invest.isDebt.setValue(1);
		t_invest.circulationInterest.setValue(map.get("circulationInterest"));
		t_invest.circulationForpayStatus.setValue(map.get("circulationForpayStatus"));
		t_invest.reason.setValue(map.get("reason"));
		if (null == map.get("repayDate") || map.get("repayDate") == "") {
			t_invest.repayDate.setValue(new Date());
		} else {
			t_invest.repayDate.setValue(map.get("repayDate"));
		}
		t_invest.check_principal.setValue(map.get("check_principal"));
		t_invest.check_interest.setValue(map.get("check_interest"));
		// t_invest.min_invest_id.setValue(map.get("min_invest_id"));
		t_invest.max_invest_id.setValue(alienatorId);
		t_invest.adjust_principal.setValue(map.get("adjust_principal"));
		t_invest.isCancel.setValue(map.get("isCancel"));
		if (null == map.get("cancelDate") || map.get("cancelDate") == "") {
			t_invest.cancelDate.setValue(new Date());
		} else {
			t_invest.cancelDate.setValue(map.get("cancelDate"));
		}
		return t_invest.insert(conn);
	}

	/**
	 * 根据ID修改原始投资人
	 * 
	 * @param conn
	 * @param id
	 * @param debtStatus
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long udadateOriInvestor(Connection conn, String id, Map<String, String> map) throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.oriInvestor.setValue(Integer.valueOf(map.get("investor")));
		return t_invest.update(conn, "id=" + Integer.valueOf(id));
	}

	/**
	 * 根据ID更新还款表的信息
	 * 
	 * @param conn
	 * @param id
	 * @param map
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long udadateRepayment(Connection conn, String id, Map<String, String> map, long invest) throws SQLException {
		Dao.Tables.t_invest_repayment t_invest_repayment = new Dao().new Tables().new t_invest_repayment();
		t_invest_repayment.invest_id.setValue(Integer.valueOf(map.get("invest")));
		t_invest_repayment.invest_id.setValue(Integer.valueOf(map.get("owner")));
		return t_invest_repayment.update(conn, "borrowId=" + map.get("borrowId"));
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
	public Map<String, String> queryAlienatorById(Connection conn, String id) throws SQLException, DataException {
		String sql = "select * from t_invest where id =" + id + " group by investor";
		DataSet dataSet = MySQL.executeQuery(conn, sql);
		return BeanMapUtils.dataSetToMap(dataSet);
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
	 * 债权转让过程中修改投资表债权的状态
	 * 
	 * @param conn
	 * @param d
	 * @param debtStatus
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long udadateInvest(Connection conn, String id, Integer status) throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.isDebt.setValue(status);
		return t_invest.update(conn, "id = " + Integer.valueOf(id));
	}

	// /**
	// * 删除债权转让表中的原始记录--撤回
	// * @param conn
	// * @param id
	// * @param debtStatus
	// * @return
	// * @throws SQLException [参数说明]
	// *
	// * @return long [返回类型说明]
	// * @exception throws [违例类型] [违例说明]
	// * @see [类、类#方法、类#成员]
	// */
	// public long deleteAssignment(Connection conn, String id)
	// throws SQLException
	// {
	// Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new
	// Tables().new t_assignment_debt();
	// return t_assignment_debt.delete(conn, "id = " + id);
	// }

	/**
	 * 债权成交过程中修改债权转让表的状态--撤回
	 * 
	 * @param conn
	 * @param id
	 * @param debtStatus
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long udadeteAssingmentDebt(Connection conn, String id, String debtStatus) throws SQLException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		t_assignment_debt.debtStatus.setValue(Integer.valueOf(debtStatus));
		return t_assignment_debt.update(conn, "id = " + id);
	}

	public long udadeteAssingmentDebts(Connection conn, String id, String debtStatus, String remark, String time) throws SQLException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		t_assignment_debt.debtStatus.setValue(Integer.valueOf(debtStatus));
		t_assignment_debt.details.setValue(remark);
		t_assignment_debt.publishTime.setValue(time);
		t_assignment_debt.auctionDays.setValue(3);
		return t_assignment_debt.update(conn, "id = " + id);
	}

	/**
	 * 根据债权ID修改转让表已经插入的转让信息状态
	 * 
	 * @param conn
	 * @param debtId
	 * @param map
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long udadeteAssingment(Connection conn, long debtId, Map<String, String> map) throws SQLException {
		Dao.Tables.t_assignment_debt t_assignment_debt = new Dao().new Tables().new t_assignment_debt();
		DBReflectUtil.mapToTableValue(t_assignment_debt, map);
		return t_assignment_debt.update(conn, "id = " + debtId);
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
	 * 拆分投资还款表
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
	public long insertInvestRepayment(Connection conn, Map<String, String> mapRepayment, double realAmount) throws SQLException {
		Dao.Tables.t_invest_repayment t_invest_repayment = new Dao().new Tables().new t_invest_repayment();
		t_invest_repayment.repayId.setValue(mapRepayment.get("repayId"));
		t_invest_repayment.repayPeriod.setValue(mapRepayment.get("repayPeriod"));
		t_invest_repayment.repayDate.setValue(mapRepayment.get("repayDate"));
		t_invest_repayment.realRepayDate.setValue(mapRepayment.get("realRepayDate"));
		t_invest_repayment.recivedPrincipal.setValue(mapRepayment.get("recivedPrincipal"));
		t_invest_repayment.recivedInterest.setValue(mapRepayment.get("recivedInterest"));
		t_invest_repayment.hasPrincipal.setValue(mapRepayment.get("hasPrincipal"));
		t_invest_repayment.hasInterest.setValue(mapRepayment.get("hasInterest"));
		t_invest_repayment.interestOwner.setValue(mapRepayment.get("interestOwner"));
		t_invest_repayment.recivedFI.setValue(mapRepayment.get("recivedFI"));
		t_invest_repayment.isLate.setValue(mapRepayment.get("isLate"));
		t_invest_repayment.lateDay.setValue(mapRepayment.get("lateDay"));
		t_invest_repayment.isWebRepay.setValue(mapRepayment.get("isWebRepay"));
		t_invest_repayment.principalBalance.setValue(mapRepayment.get("principalBalance"));
		t_invest_repayment.interestBalance.setValue(mapRepayment.get("interestBalance"));
		t_invest_repayment.invest_id.setValue(mapRepayment.get("invest_id"));
		t_invest_repayment.owner.setValue(mapRepayment.get("owner"));
		t_invest_repayment.ownerlist.setValue(mapRepayment.get("ownerlist"));
		t_invest_repayment.repayStatus.setValue(mapRepayment.get("repayStatus"));
		t_invest_repayment.iManageFee.setValue(mapRepayment.get("iManageFee"));
		t_invest_repayment.isDebt.setValue(mapRepayment.get("isDebt"));
		t_invest_repayment.iManageFeeRate.setValue(mapRepayment.get("iManageFeeRate"));
		t_invest_repayment.isDebt.setValue(mapRepayment.get("isDebt"));
		t_invest_repayment.circulationForpayStatus.setValue(mapRepayment.get("circulationForpayStatus"));
		return t_invest_repayment.insert(conn);
	}

}
