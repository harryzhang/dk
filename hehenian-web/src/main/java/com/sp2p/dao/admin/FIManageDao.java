package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;

public class FIManageDao {

	public Map<String, String> queryOneFirstChargeDetails(Connection conn, long rechargeId) throws SQLException, DataException {
		Dao.Views.v_t_user_rechargefirst_lists t_recharge_detail = new Dao().new Views().new v_t_user_rechargefirst_lists();
		DataSet ds = t_recharge_detail.open(conn, "*", "id=" + rechargeId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	public Map<String, String> queryOneChargeDetails(Connection conn, long rechargeId) throws SQLException, DataException {
		Dao.Views.v_t_user_rechargedetails_list t_recharge_detail = new Dao().new Views().new v_t_user_rechargedetails_list();
		DataSet ds = t_recharge_detail.open(conn, "*", "id=" + rechargeId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	public Map<String, String> queryOneWithdraw(Connection conn, long wid) throws SQLException, DataException {
		Dao.Views.v_t_user_withdraw_lists t_list = new Dao().new Views().new v_t_user_withdraw_lists();
		DataSet ds = t_list.open(conn, "*", "id=" + wid, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 后台 进行充值扣费信息添加处理
	 * 
	 * @param conn
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public long addBackR_W(Connection conn, Long userId, Long adminId, Integer type, double money, String remark, Date date) throws SQLException {
		Dao.Tables.t_recharge_withdraw_info t_info = new Dao().new Tables().new t_recharge_withdraw_info();
		t_info.userId.setValue(userId);
		t_info.type.setValue(type);
		t_info.dealMoney.setValue(money);
		t_info.remark.setValue(remark);
		t_info.checkUser.setValue(adminId);
		t_info.checkTime.setValue(date);
		return t_info.insert(conn);

	}

	// 后台查询还款记录
	@SuppressWarnings("unchecked")
	public void queryBorrowneedpayByIdAdmin(Connection conn, long borrowId, HttpServletRequest request) throws SQLException, DataException {

		// 历史还款记录
		List<Map<String, Object>> record = new ArrayList<Map<String, Object>>();
		String sql = "select repayPeriod,repayDate, stillPrincipal+stillInterest as stillPI,stillInterest,lateFI,lateDay,realRepayDate  from t_repayment";
		sql += " where repayStatus=2 and borrowId=" + borrowId + " order by repayPeriod desc";
		DataSet dataSet = MySQL.executeQuery(conn, sql);
		dataSet.tables.get(0).rows.genRowsMap();
		record = dataSet.tables.get(0).rows.rowsMap;

		// 当前的还款记录
		Map<String, Object> curr = new HashMap<String, Object>();
		sql = " SELECT b.id as borrowId,IFNULL(tpr.consultFee,'--')consultFee,tpr.repayFee, c.username AS username, d.realName AS realName, b.borrowTitle AS borrowTitle, b.borrowAmount borrowAmount,";
		sql += " b.deadline deadline, b.annualRate annualRate, IFNULL(a.repayPeriod,'--') repayPeriod, IFNULL(a.repayDate,'--') repayDate, IFNULL(a.stillInterest + a.stillPrincipal,'--') stillPI,";
		sql += " IFNULL(a.stillInterest,'--') stillInterest, IFNULL(a.lateFI,'--') lateFI, IFNULL(a.lateDay,'--') lateDay, IFNULL(a.realRepayDate,'--') realRepayDate, IFNULL(a.repayStatus,3) repayStatus,a.id payId ";
		sql += " FROM  t_borrow b LEFT JOIN (select * from t_repayment where repayStatus=1) a ON  a.borrowId = b.id LEFT JOIN t_user c ON b.publisher = c.id ";
		sql += " LEFT JOIN t_person d ON c.id = d.userId left join t_repayment tpr on tpr.borrowId = b.id and tpr.repayStatus=1 where b.id="
				+ borrowId + " LIMIT 0,1";
		dataSet = MySQL.executeQuery(conn, sql);
		dataSet.tables.get(0).rows.genRowsMap();
		List<Map<String, Object>> temp = dataSet.tables.get(0).rows.rowsMap;
		if (temp != null && temp.size() > 0)
			curr = temp.get(0);

		request.setAttribute("curr", curr);
		request.setAttribute("record", record);
	}

	/**
	 * 更新资金流动信息表
	 */
	public Long updateFundrecord(Connection conn, Long userId, double money, int type) throws SQLException {
		if (type == IConstants.WITHDRAW) {// 扣费
			ResultSet result = conn.prepareStatement(" select usableSum from t_user where id=" + userId).executeQuery();
			if (result.next()) {
				Double usableSum = result.getDouble("usableSum");
				if (usableSum < money)
					return -99L;
			}
			return MySQL.executeNonQuery(conn, " update t_user set `usableSum` = usableSum-" + money + " where id=" + userId);
		} else if (type == IConstants.RECHARAGE) {// 充值
			return MySQL.executeNonQuery(conn, " update t_user set `usableSum` = usableSum+" + money + " where id=" + userId);
		} else if (type == IConstants.WITHDRAW_FAIL) {// 审核或者转账失败（将提现的金额变成可用余额）
			return MySQL.executeNonQuery(conn, " update t_user set `usableSum` = usableSum+" + money + ", `freezeSum`=freezeSum-" + money
					+ " where id=" + userId);
		} else if (type == 100) {// 转账成功（将冻结余额扣除）IConstants.WITHDRAW_SUCCESS =
			return MySQL.executeNonQuery(conn, " update t_user set  `freezeSum`=freezeSum-" + money + " where id=" + userId);
		} else {
			return -1L;
		}
	}

	/**
	 * 某用户充值成功总额
	 * 
	 * @param conn
	 * @param userId
	 * @param result
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querySuccessRecharge(Connection conn, Long userId, int result) throws SQLException, DataException {
		String command = "SELECT sum(rechargeMoney) as r_total from t_recharge_detail where userId=" + userId + " and result=" + result;
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 某用户2个月提现成功总额
	 * 
	 * @param conn
	 * @param userId
	 * @param result
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querySuccessWithdraw(Connection conn, Long userId, int result) throws SQLException, DataException {
		String command = "SELECT sum(sum) as w_total from t_withdraw where userId=" + userId + " and status=" + result +" and applyTime>=DATE_SUB(CURDATE(),INTERVAL 2 MONTH )";
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 投标成功总额
	 * 
	 * @throws SQLException
	 */
	public Map<String, String> querySuccessBid(Connection conn, Long userId) throws SQLException, DataException {
		Dao.Views.v_t_user_successtotalbid_lists lists = new Dao().new Views().new v_t_user_successtotalbid_lists();
		DataSet dataSet = lists.open(conn, "*", "investor=" + userId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	// 查询状态，如果已经转账过了，就不再转账
	public Map<String, String> queryTransStatus(Connection conn, Long wid) throws SQLException, DataException {
		Dao.Tables.t_withdraw t_info = new Dao().new Tables().new t_withdraw();
		DataSet dataSet = t_info.open(conn, "*", "id=" + wid, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Long updateWithdraw(Connection conn, Long wid, double money, float poundage, int status, String remark, Long adminId, int oldStatus)
			throws SQLException {
		Dao.Tables.t_withdraw t_info = new Dao().new Tables().new t_withdraw();
		if (status >= 0)
			t_info.status.setValue(status);
		if (money >= 0)
			t_info.sum.setValue(money);
		if (remark != null)
			t_info.remark.setValue(remark);
		if (poundage >= 0)
			t_info.poundage.setValue(poundage);
		if (adminId != -100)
			t_info.checkId.setValue(adminId);
		t_info.checkTime.setValue(new Date());
		return t_info.update(conn, " id=" + wid + " and status=" + oldStatus);
	}

	public Long updateWithdraws(Connection conn, String wids, int status, Long adminId) throws SQLException {
		String idStr = StringEscapeUtils.escapeSql("'" + wids + "'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String[] array = idStr.split(",");
		for (int n = 0; n <= array.length - 1; n++) {
			idSQL += "," + array[n];
		}
		Dao.Tables.t_withdraw t_info = new Dao().new Tables().new t_withdraw();
		t_info.checkId.setValue(adminId);
		t_info.status.setValue(status);

		return t_info.update(conn, " id in(" + idSQL + ")");
	}

	public Map<String, String> queryR_WInfo(Connection conn, Long rwId) throws SQLException, DataException {
		Dao.Views.v_t_user_backrw_lists t_list = new Dao().new Views().new v_t_user_backrw_lists();

		DataSet ds = t_list.open(conn, "*", "id=" + rwId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	public Map<String, String> queryDueInSum(Connection conn, Long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select a.usableSum as usableSum ,a.freezeSum as freezeSum,sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest) forPI");
		command.append(" from t_user a left join t_invest b on a.id = b.investor where a.id=" + userId + " group by a.id");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);

	}

	public List<Map<String, Object>> queryLastRecord(Connection conn) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT * from (select * from t_fundrecord  ORDER BY recordTime desc ) a GROUP BY userId");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		command = null;
		return dataSet.tables.get(0).rows.rowsMap;

	}

	//提现成功，更新当期提现金额为0.00
	public Long updateWithdrawMoney(Connection conn,long wid) throws SQLException {
        Dao.Tables.t_withdraw t_withdraw = new Dao().new Tables().new t_withdraw();
        t_withdraw.sum.setValue(0);
        return t_withdraw.update(conn, " id in  (" + wid + ")");
    }
	
	
	/**
	 * 
	 * 更新还款状态
	 * 
	 * @param conn
	 * @param repaymentId
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return Long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Long updateRepaymentById(Connection conn, String repaymentId) throws SQLException {
		String idStr = StringEscapeUtils.escapeSql(repaymentId);
		Dao.Tables.t_repayment t_repayment = new Dao().new Tables().new t_repayment();
		t_repayment.repayStatus.setValue(2);
		t_repayment.realRepayDate.setValue(new Date());
		return t_repayment.update(conn, " id in  (" + idStr + ")");
	}

	public Long updateBorrowById(Connection conn, String borrowId, String hasDeadline) throws SQLException {
		String[] bid = borrowId.split(",");
		String[] hasline = hasDeadline.split(",");
		Dao.Tables.t_borrow t_borrow;

		for (int i = 0, length = hasline.length; i < length; i++) {
			String idStr = StringEscapeUtils.escapeSql(bid[i]);
			String lineStr = StringEscapeUtils.escapeSql(hasline[i]);
			t_borrow = new Dao().new Tables().new t_borrow();
			t_borrow.hasDeadline.setValue(Integer.parseInt(lineStr) + 1);
			t_borrow.update(conn, " id=" + idStr);
		}
		return 1L;
	}

	public Long insertRepaymentRecord(Connection conn, String repaymentId, String repayAmount, Long adminId, String addIP) throws SQLException {
		String[] repayid = repaymentId.split(",");
		String[] repayMount = repayAmount.split(",");
		Date now = new Date();
		Dao.Tables.t_repayment_record t_repayment_record;
		for (int i = 0, length = repayMount.length; i < length; i++) {
			String repayidStr = StringEscapeUtils.escapeSql(repayid[i]);
			String amountStr = StringEscapeUtils.escapeSql(repayMount[i]);
			t_repayment_record = new Dao().new Tables().new t_repayment_record();
			t_repayment_record.repayId.setValue(Long.parseLong(repayidStr));
			t_repayment_record.repayAmount.setValue(Double.parseDouble(amountStr));
			t_repayment_record.oporator.setValue(adminId == null ? -1 : adminId);
			t_repayment_record.repayType.setValue("手动还款");
			t_repayment_record.createTime.setValue(now);
			t_repayment_record.oporatorIp.setValue(addIP);
			t_repayment_record.insert(conn);
		}
		return 1L;
	}

	/**
	 * 合和年 投资曲线收益 根据ID查询投资数据
	 * 
	 * @param conn
	 * @param id
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryInvestForYear(Connection conn, long userid) throws Exception {
		StringBuffer command = new StringBuffer();
		command.append(" SELECT sum(realAmount) amount ,DATE_FORMAT(investTime,'%m') months from t_invest ");
		command.append(" WHERE year(investTime)=year(SYSDATE()) and investor=" + userid + " or oriInvestor=" + userid);
		command.append(" GROUP BY months ORDER BY months ");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 合和年 投资曲线收益 根据ID查询收益数据
	 */
	public List<Map<String, Object>> queryIncomeForYear(Connection conn, long userid) throws Exception {
		StringBuffer command = new StringBuffer();
		command.append("SELECT sum(hasPrincipal+hasInterest+recivedFI) amount ,DATE_FORMAT(repayDate,'%m') months from t_invest_repayment ");
		command.append("  WHERE year(repayDate)=year(SYSDATE())  and owner=" + userid + " ");
		command.append(" GROUP BY months ORDER BY months ");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	public long deleteRechargeRecode(Connection conn, long id) throws SQLException {
		Dao.Tables.t_recharge_withdraw_info t_info = new Dao().new Tables().new t_recharge_withdraw_info();
		return t_info.delete(conn, " id=" + id);
	}

	public long addMerCash(Connection conn, String userId, double transAmt, Long adminId) {
		Dao.Tables.t_merCash info = new Dao().new Tables().new t_merCash();
		info.adminId.setValue(adminId);
		info.userId.setValue(userId);
		info.sum.setValue(transAmt);
		info.applyTime.setValue(new Date());
		return info.insert(conn);
	}

	public long updateMerCash(Connection conn, long id, String cardNo, String poundage) {
		Dao.Tables.t_merCash info = new Dao().new Tables().new t_merCash();
		info.cardNo.setValue(cardNo);
		info.poundage.setValue(poundage);
		info.status.setValue(1);
		return info.update(conn, "id=" + id);
	}
	
	/**
	 * 
	 * 添加商户取现记录
	 * @param conn
	 * @param userId
	 * @param transAmt
	 * @param adminId
	 * @return 
	 * @return [参数说明]
	 * 
	 */
	public long addWithdraw(Connection conn, String username, String acount, String sum, String poundage, String applyTime, String bankId,String userId){
        Dao.Tables.t_withdraw info = new Dao().new Tables().new t_withdraw();
        info._name.setValue(username);
        info.acount.setValue(acount);
        info.sum.setValue(sum);
        info.poundage.setValue(poundage);
        info.applyTime.setValue(applyTime);
        info.bankId.setValue(bankId);
        info.userId.setValue(userId);
        info.status.setValue(2);
        return info.insert(conn);
    }

	/**
	 * 
	 * @param userId
	 * @fundMode 资金流水类型
	 * @transAmt 金额
	 * @date 时间
	 * @remarks 备注
	 * @cost 手续费
	 * @income 收入
	 * @spending 支出
	 */
	public long addFundRecode(Connection conn, String userId, String fundMode, double transAmt, Date date, String remarks, double cost,
			double income, double spending) {
		Dao.Tables.t_fundrecord info = new Dao().new Tables().new t_fundrecord();
		info.userId.setValue(userId);
		info.fundMode.setValue(fundMode);
		info.handleSum.setValue(transAmt);
		info.recordTime.setValue(date);
		info.remarks.setValue(remarks);
		info.cost.setValue(cost);
		info.income.setValue(income);
		info.spending.setValue(spending);
		return info.insert(conn);

	}

	public Map<String, String> queryReturnMoney(Connection conn, long userId) throws DataException {
		String sql = " select sum(hasPrincipal+hasInterest+recivedFI) as retSum from t_invest_repayment where repayStatus=2 and realRepayDate>=DATE_SUB(CURDATE(),INTERVAL 2 MONTH ) ";
		DataSet dataSet = MySQL.executeQuery(conn, sql);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

}
