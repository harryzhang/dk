package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.util.DBReflectUtil;

public class RechargeDao {

	/**
	 * 提现信息加载
	 * 
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> withdrawLoad(Connection conn, long userId, int limitStart, int limitCount) throws SQLException, DataException {
		Dao.Views.t_user_withdraw_info t_info = new Dao().new Views().new t_user_withdraw_info();
		// 加载已经绑定的银行卡信息，还在审核的不显示
		DataSet dataSet = t_info.open(conn, "*", " userId='" + userId + "' ", " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 查找提现申请记录表
	 * 
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryWithdrawList(Connection conn, long userId, int limitStart, int limitCount) throws SQLException,
			DataException {
		Dao.Tables.t_withdraw t_info = new Dao().new Tables().new t_withdraw();
		DataSet dataSet = t_info.open(conn, "*", " userId='" + userId + "'", " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 查询资金记录信息
	 * 
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryFundrecordList(Connection conn, long userId, int limitStart, int limitCount) throws SQLException,
			DataException {
		Dao.Tables.t_fundrecord t_info = new Dao().new Tables().new t_fundrecord();
		DataSet dataSet = t_info.open(conn, "*", " userId='" + userId + "'", " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	public List<Map<String, Object>> queryRechargeInfo(Connection conn, long userId, int limitStart, int limitCount) throws SQLException,
			DataException {
		Dao.Tables.t_recharge_info t_info = new Dao().new Tables().new t_recharge_info();
		DataSet dataSet = t_info.open(conn, "*", " userId='" + userId + "'", " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 添加提现申请信息
	 * 
	 * @param conn
	 * @param userId
	 * @param username
	 * @param cellphone
	 * @param account
	 * @param sum
	 * @param poundage
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public Long addWithdraw(Connection conn, Long userId, String username, String cellphone, String account, double sum, double poundage, int status,
			Long bankId, String ipAddress) throws SQLException {
		long result = -1;
		Dao.Tables.t_withdraw t_info = new Dao().new Tables().new t_withdraw();
		t_info._name.setValue(username);
		t_info.cellPhone.setValue(cellphone);
		t_info.acount.setValue(account);
		t_info.sum.setValue(sum);
		t_info.poundage.setValue(poundage);
		t_info.status.setValue(status);
		t_info.applyTime.setValue(new Date());
		t_info.userId.setValue(userId);
		t_info.bankId.setValue(bankId);
		t_info.ipAddress.setValue(ipAddress);
		result = t_info.insert(conn);
		return result;
	}

	/**
	 * 后台 进行扣费处理
	 * 
	 * @param conn
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public long addBackWithdraw(Connection conn, Long userId, Long adminId, Integer status, double sum, String remark, Date date) throws SQLException {
		Dao.Tables.t_withdraw t_info = new Dao().new Tables().new t_withdraw();
		t_info.userId.setValue(userId);
		t_info.status.setValue(status);
		t_info.sum.setValue(sum);
		t_info.remark.setValue(remark);
		return t_info.insert(conn);

	}

	/**
	 * 更新资金流动信息表
	 * 
	 * @param conn
	 * @param userId
	 * @param handleSum
	 * @param usableSum
	 * @return
	 * @throws SQLException
	 */
	public Long updateFundrecord(Connection conn, Long userId, double money, int type) throws SQLException {
		if (type == 0) {
			return MySQL.executeNonQuery(conn, " update t_user set `usableSum` = usableSum-" + money + ", `freezeSum`=freezeSum+" + money
					+ " where id=" + userId);
		} else if (type == 1) {
			return MySQL.executeNonQuery(conn, " update t_user set `usableSum` = usableSum+" + money + ", `freezeSum`=freezeSum-" + money
					+ " where id=" + userId);
		} else {// 充值
			return MySQL.executeNonQuery(conn, " update t_user set `usableSum`=usableSum+" + money + " where id=" + userId);
		}
	}

	public Map<String, String> getRechargeDetail(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_recharge_detail t_recharge_detail = new Dao().new Tables().new t_recharge_detail();
		DataSet ds = t_recharge_detail.open(conn, "*", "id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 添加充值记录信息
	 * 
	 * @param conn
	 * @param userId
	 * @param money
	 * @param rechargeType
	 * @param bankName
	 * @param poundage
	 * @param account
	 * @param rechargeTime
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public Long addRechargeInfo(Connection conn, Long userId, float money, int rechargeType, String bankName, float poundage, float account,
			int status) throws SQLException {
		long result = -1;
		Dao.Tables.t_recharge_info t_info = new Dao().new Tables().new t_recharge_info();
		t_info.userId.setValue(userId);
		t_info.rechargeMoney.setValue(money);
		t_info.rechargeType.setValue(rechargeType);
		t_info.bankName.setValue(bankName);
		t_info.poundage.setValue(poundage);
		t_info.account.setValue(account);
		t_info.rechargeTime.setValue(new Date());
		t_info.status.setValue(status);
		result = t_info.insert(conn);
		return result;
	}

	//
	public long addRecharge(Connection conn, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_recharge t_recharge = new Dao().new Tables().new t_recharge();
		DBReflectUtil.mapToTableValue(t_recharge, paramMap);
		return t_recharge.insert(conn);

	}

	public long addRechargeDetail(Connection conn, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_recharge_detail t_recharge_detail = new Dao().new Tables().new t_recharge_detail();
		DBReflectUtil.mapToTableValue(t_recharge_detail, paramMap);
		return t_recharge_detail.insert(conn);

	}

	public Long deleteWithdraw(Connection conn, Long userId, long wid) throws SQLException {
		Dao.Tables.t_withdraw t_info = new Dao().new Tables().new t_withdraw();
		String condition = " userId=" + userId + " and id=" + wid;
		return t_info.delete(conn, condition);
	}

	public Map<String, String> queryFundrecordSum(Connection conn, Long userId, int limitStart, int limitCount) throws SQLException, DataException {
		Dao.Tables.t_fundrecord t_info = new Dao().new Tables().new t_fundrecord();
		String condition = " userId=" + userId;
		DataSet dataSet = t_info.open(conn, "*", condition, " ", limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String, String> queryFundrecordSum(Connection conn, Long userId, int limitStart, int limitCount, String cmd) throws SQLException,
			DataException {
		Dao.Tables.t_fundrecord t_info = new Dao().new Tables().new t_fundrecord();
		String condition = " userId=" + userId;
		condition += cmd;
		DataSet dataSet = t_info.open(conn, "*", condition, " ", limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public long updateRechargeDetail(Connection conn, long id, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_recharge_detail t_recharge_detail = new Dao().new Tables().new t_recharge_detail();
		DBReflectUtil.mapToTableValue(t_recharge_detail, paramMap);
		return t_recharge_detail.update(conn, "id=" + id);

	}

	public long updateRecharge(Connection conn, long id, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_recharge t_recharge = new Dao().new Tables().new t_recharge();
		DBReflectUtil.mapToTableValue(t_recharge, paramMap);
		return t_recharge.update(conn, "id=" + id);

	}

	/**
	 * 查看指定时间内成功充值的金额(当前时间往前推15天)
	 * 
	 * @param conn
	 * @param userId
	 * @param startTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryRechargeInTime(Connection conn, long userId, String startTime) throws SQLException, DataException {
		String command = "SELECT sum(rechargeMoney) as rechargeMoney from t_recharge_detail " + "where result=" + IConstants.RECHARGE_SUCCESS
				+ " and userId=" + userId + " and rechargeTime>='" + StringEscapeUtils.escapeSql(startTime) + "'";
		DataSet ds = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 获得指定时间内用户投标收到的还款值(当前时间往前推15天)
	 * 
	 * @param conn
	 * @param userId
	 * @param startTime
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryTradeInTime(Connection conn, long userId, String startTime) throws SQLException, DataException {
		String command = "select sum(a.hasPrincipal+a.hasInterest) hasPI," + "a.investor from t_invest a left join t_borrow b on  a.borrowId = b.id "
				+ "left join t_repayment c on c.borrowId = b.id " + " where  c.realRepayDate >= '" + StringEscapeUtils.escapeSql(startTime) + "' "
				+ " and a.investor = " + userId + "  and b.id is not null group by a.investor";
		DataSet ds = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(ds);
	}

	public Map<String, String> queryLastRecharge(Connection conn, Long userId) throws SQLException, DataException {
		String command = "SELECT max(rechargeTime) from t_recharge_detail where userId=" + userId;
		DataSet ds = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(ds);
	}

	/** 
	 * 根据ID查询用户信息
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException [参数说明]
	 * 
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryUser(Connection conn, Long id) throws Exception {
        String command = "SELECT * from t_user where id=" + id;
        DataSet ds = MySQL.executeQuery(conn, command);
        command = null;
        return BeanMapUtils.dataSetToMap(ds);
    }
	
	/**
	 * 查找用户资金信息
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryFund(Connection conn, Long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select a.usableSum as usableSum,a.freezeSum as freezeSum,sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest) as forPI ");
		command.append(" from t_user a left join t_invest b on a.ID = b.investor where a.id=" + userId + " group by a.ID,a.usableSum,a.freezeSum");

		DataSet ds = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(ds);
	}

	public Map<String, String> queryMoneyRecords(Connection conn, long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select a.usableSum,a.freezeSum,sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest) as forPI");
		command.append(" from t_user a left join t_invest b on a.id = b.investor where a.id=" + userId + " group by a.ID,a.usableSum,a.freezeSum");

		DataSet dds = Database.executeQuery(conn, command.toString());

		command = null;
		return BeanMapUtils.dataSetToMap(dds);
	}

	public Map<String, String> getWithdrawById(Connection conn, long wid) throws SQLException, DataException {
		Dao.Tables.t_withdraw t_info = new Dao().new Tables().new t_withdraw();
		DataSet ds = t_info.open(conn, "", "id=" + wid, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	public Long deleteWithdraw(Connection conn, long wid) throws SQLException {
		Dao.Tables.t_withdraw t_info = new Dao().new Tables().new t_withdraw();
		return t_info.delete(conn, " id=" + wid);
	}

	/**
	 * 查询资金流水中的所有类型
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryTypeFund(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_fundrecord t_fundrecord = new Dao().new Tables().new t_fundrecord();
		DataSet ds = t_fundrecord.open(conn, " DISTINCT fundMode,operateType ", "", "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();

		return ds.tables.get(0).rows.rowsMap;
	}
}
