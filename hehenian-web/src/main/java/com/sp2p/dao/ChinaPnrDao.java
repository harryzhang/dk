package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.sp2p.database.Dao;

public class ChinaPnrDao {

	/**
	 * 汇付 更新用户冻结资金
	 */
	public long updateUserFreezeSum(Connection conn, String usrCustId, String transAmt) throws SQLException, DataException {
		double freeSum = Convert.strToDouble(transAmt, 0);
		String command = "update t_user set usableSum = usableSum - " + freeSum;
		command += ", freezeSum = freezeSum + " + freeSum + " where id = in_uid";
		return MySQL.executeNonQuery(conn, command);
	}

	/**
	 * 提现失败的时候删除记录
	 */
	public long deleteWithDraw(Connection conn, String ordId) throws SQLException {
		Dao.Tables.t_withdraw with = new Dao().new Tables().new t_withdraw();
		return with.delete(conn, " id=" + ordId+" and (trxId is null or trxId='' )");
	}

	/**
	 * 投资失败的时候删除记录
	 */
	public long deleteBorrowInvest(Connection conn, String ordId) throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		return t_invest.delete(conn, " id=" + ordId);
	}

	/**
	 * 更新提现trxId
	 */
	public long updateCashTrxId(Connection conn, String ordId, String trxId) throws SQLException {
		Dao.Tables.t_withdraw withdraw = new Dao().new Tables().new t_withdraw();
		withdraw.trxId.setValue(trxId);
		withdraw.status.setValue(1);
		return withdraw.update(conn, " id=" + ordId);
	}

	/**
     * 插入提现资金记录
     */
	 public long insertMoney(Connection conn, String fundMode,String remarks, String income,String spending ,String type,String userId) throws SQLException {
        Dao.Tables.t_fundrecord t_fundrecord = new Dao().new Tables().new t_fundrecord();
        t_fundrecord.fundMode.setValue(fundMode);
        t_fundrecord.remarks.setValue(remarks);
        t_fundrecord.income.setValue(income);
        t_fundrecord.spending.setValue(spending);
        t_fundrecord.type.setValue(type);
        t_fundrecord.userId.setValue(userId);
        return t_fundrecord.insert(conn);
    }
	
	/**
	 * 更新投资trxId
	 * 
	 * @return
	 * @throws SQLException
	 */
	public long updatInvestTrxId(Connection conn, String ordId, String trxId) throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.trxId.setValue(trxId);
		return t_invest.update(conn, " id=" + ordId);
	}

}
