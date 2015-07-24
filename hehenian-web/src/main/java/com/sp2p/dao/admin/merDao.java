package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Views;
import com.sp2p.database.Dao.Tables.t_recharge_withdraw_info;
import com.sp2p.database.Dao.Views.v_t_borrow_collection;

public class merDao {

	public long addRecharge(Connection conn) {
		Dao.Tables.t_recharge_detail t = new Dao().new Tables().new t_recharge_detail();
		t.cost.setValue(0);
		return t.insert(conn);
	}

	public long deleteRechargeDetails(Connection conn, long id) {
		Dao.Tables.t_recharge_detail t = new Dao().new Tables().new t_recharge_detail();
		return t.delete(conn, " id=" + id);
	}

	public long addMerRecharge(Connection conn, long id, double amount, String subAcct, Date now) {
		Dao.Tables.t_merRecharge t = new Dao().new Tables().new t_merRecharge();
		t.id.setValue(id);
		t.money.setValue(amount);
		t.subAcct.setValue(subAcct);
		t.rechargeTime.setValue(now);
		return t.insert(conn);
	}

	/**
	 * 
	 * 查询出账账户余额
	 * 
	 * @param conn
	 * @param outSubAcct
	 * @return [参数说明]
	 * 
	 */
	public Map<String, String> queryOutSubAcct(Connection conn, String outSubAcct) throws Exception {
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
		DataSet dataSet = t_admin.open(conn, "*", " subAcct='" + outSubAcct + "'", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 
	 * 查询入账账户余额
	 * 
	 * @param conn
	 * @param outSubAcct
	 * @return [参数说明]
	 * @throws Exception
	 * 
	 */
	public Map<String, String> queryInSubAcct(Connection conn, String inSubAcct) throws Exception {
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
		DataSet dataSet = t_admin.open(conn, "*", " subAcct='" + inSubAcct + "'", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 
	 * 更新出账账户金额
	 * 
	 * @param conn
	 * @param amount
	 * @param outSubAcct
	 */
	public long updateOutSubAcct(Connection conn, String outAvlBal, String outSubAcct) {
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
		t_admin.subAcctMoney.setValue(Double.valueOf(outAvlBal));
		return t_admin.update(conn, " subAcct='" + outSubAcct + "'");
	}

	/**
	 * 
	 * 更新入账账户金额
	 * 
	 * @param conn
	 * @param amount
	 * @param intSubAcct
	 */
	public long updateInSubAcct(Connection conn, String inAvlBal, String intSubAcct) {
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
		t_admin.subAcctMoney.setValue(Double.valueOf(inAvlBal));
		return t_admin.update(conn, " subAcct ='" + intSubAcct + "'");
	}

	/**
	 * 
	 * 防止ID冲突，插入还款表测试数据
	 * 
	 * @param conn
	 * @param amount
	 * @param intSubAcct
	 */
	public long addRepayment(Connection conn, Date date) {
		Dao.Tables.t_repayment t_repayment = new Dao().new Tables().new t_repayment();
		t_repayment.repayDate.setValue(date);
		return t_repayment.insert(conn);
	}

	/**
	 * 
	 * 删除插入还款表测试数据
	 * 
	 * @param conn
	 * @param amount
	 * @param intSubAcct
	 */
	public long deleteRepayment(Connection conn, long ordId) {
		Dao.Tables.t_repayment t_repayment = new Dao().new Tables().new t_repayment();
		return t_repayment.delete(conn, "id=" + ordId);
	}

	/** 成功 */
	public long updateMerRechargeSuccess(Connection conn, String id, String trxId, String fee, String usableSum) {
		Dao.Tables.t_merRecharge t = new Dao().new Tables().new t_merRecharge();
		t.result.setValue(1);
		t.trxId.setValue(trxId);
		t.fee.setValue(fee);
		if (!StringUtils.isBlank(usableSum)) {
			t.usableSum.setValue(usableSum);
		}
		return t.update(conn, " id=" + id);
	}

	/** 失败 */
	public long netSaveFail(Connection conn, long id, String trxId, String fee, String usableSum) {
		Dao.Tables.t_merRecharge t = new Dao().new Tables().new t_merRecharge();
		t.result.setValue(0);
		t.trxId.setValue(trxId);
		t.fee.setValue(fee);
		if (!StringUtils.isBlank(usableSum)) {
			t.usableSum.setValue(usableSum);
		}
		return t.update(conn, " id=" + id);
	}

	/**
     * 
     * 查询充值ID
     * @param conn
     * @param id
     * @return
     * @throws SQLException
     */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryTrechargeDetail(Connection conn, String userName,String startTime,String endTime) throws Exception {
        String sql = "select t.id ordId,tu.username userName,t.rechargeMoney money,date(t.rechargeTime) time from t_recharge_detail t left join " +
        		" t_user tu on t.userId = tu.id where tu.username like ('" + userName +"') and " +
        		" rechargeTime >= '" + startTime + "' and rechargeTime <= '" + endTime +"'";
        DataSet dataSet = MySQL.executeQuery(conn, sql);
        dataSet.tables.get(0).rows.genRowsMap();
        return dataSet.tables.get(0).rows.rowsMap;
//        return BeanMapUtils.dataSetToMap(dataSet);
    }

	/**
	 * 
	 * 查询放款ID
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryLoans(Connection conn, String userName,String startTime,String endTime) throws SQLException, DataException {
	     String sql = "select t.id ordId,tu.username userName,date(t.investTime) time,t.realAmount money from t_invest t left join" +
	     		" t_user tu on t.investor = tu.id where tu.username in ('" + userName +"') and " +
         "investTime >= '" + startTime + "' and investTime <= '" + endTime +"'";
         DataSet dataSet = MySQL.executeQuery(conn, sql);
         dataSet.tables.get(0).rows.genRowsMap();
         return dataSet.tables.get(0).rows.rowsMap;
    }
    
    /**
     * 
     * 查询还款ID
     * @param conn
     * @param id
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryRepayment(Connection conn, String userName, String startTime, String endTime)
        throws SQLException, DataException
    {
        String sql =
            "SELECT t.id ordId,tu.username userName,date(tr.realRepayDate) time,"
                + " t.recivedPrincipal money FROM t_invest_repayment t LEFT JOIN t_repayment tr ON t.repayId = tr.id"
                + " LEFT JOIN t_borrow b ON b.id = tr.borrowId LEFT JOIN t_user tu ON b.publisher = tu.id"
                + " WHERE tu.username ='"+ userName + "' AND tr.realRepayDate >= '"+startTime+"' AND tr.realRepayDate <= '"+endTime+"'";
         DataSet dataSet = MySQL.executeQuery(conn, sql);
         dataSet.tables.get(0).rows.genRowsMap();
         return dataSet.tables.get(0).rows.rowsMap;
    }
    
    /**
     * 
     * 查询取现ID
     * @param conn
     * @param id
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryCash(Connection conn, String userName,String startTime,String endTime) throws SQLException, DataException {
         String sql = "select t.id ordId,tu.username userName,t.sum money,date(t.applyTime) time from t_withdraw t left join t_user tu on t.userId = tu.id where tu.username in ('" + userName +"') and " +
         "applyTime >= '" + startTime + "' and applyTime <= '" + endTime +"'";
         DataSet dataSet = MySQL.executeQuery(conn, sql);
         dataSet.tables.get(0).rows.genRowsMap();
         return dataSet.tables.get(0).rows.rowsMap;
    }
    
    /**
     * 
     * 查询取现复核ID
     * @param conn
     * @param id
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryCashAudit(Connection conn, String userName,String startTime,String endTime) throws SQLException, DataException {
         String sql = "select t.id ordId,tu.username userName,date(t.realRepayDate) time,(t.recivedPrincipal+t.recivedInterest) money from t_invest_repayment t left join t_user tu on t.invest_id = tu.id where tu.username in ('" + userName +"') and " +
         "realRepayDate >= '" + startTime + "' and realRepayDate <= '" + endTime +"'";
         DataSet dataSet = MySQL.executeQuery(conn, sql);
         dataSet.tables.get(0).rows.genRowsMap();
         return dataSet.tables.get(0).rows.rowsMap;
    }
    
    /**
     * 
     * 查询投标ID
     * @param conn
     * @param id
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryInitiativeTender(Connection conn, String userName,String startTime,String endTime) throws SQLException, DataException {
         String sql = "select t.id ordId,tu.username userName,date(t.investTime) time,t.realAmount money from t_invest t left join t_user tu on t.investor = tu.id where tu.username in ('" + userName +"') and " +
         " investTime >= '" + startTime + "' and investTime <= '" + endTime +"'";
         DataSet dataSet = MySQL.executeQuery(conn, sql);
         dataSet.tables.get(0).rows.genRowsMap();
         return dataSet.tables.get(0).rows.rowsMap;
    }
    
    /**
     * 
     * 查询代取现ID
     * @param conn
     * @param id
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryMerCash(Connection conn, String userName,String startTime,String endTime) throws SQLException, DataException {
         String sql = "select t.id ordId,tu.username userName,date(t.applyTime) time,t.sum money from t_merCash t left join t_user tu on t.userId = tu.id where tu.username in ('" + userName +"') and " +
         "applyTime >= '" + startTime + "' and applyTime <= '" + endTime +"'";
         DataSet dataSet = MySQL.executeQuery(conn, sql);
         dataSet.tables.get(0).rows.genRowsMap();
         return dataSet.tables.get(0).rows.rowsMap;
    }

	public long addBack_w(Connection conn) {
		Dao.Tables.t_recharge_withdraw_info t_info = new Dao().new Tables().new t_recharge_withdraw_info();
		t_info.checkTime.setValue(new Date());
		return t_info.insert(conn);
	}

	public long deleteBack_w(Connection conn, long ordId) {
		Dao.Tables.t_recharge_withdraw_info t_info = new Dao().new Tables().new t_recharge_withdraw_info();
		return t_info.delete(conn, " where id="+ordId);
	}
	
	 /** 
	 * 查询子账户取现记录
	 * @param conn
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryCfbMerCashList(Connection conn, long userId) throws Exception {
        String sql = "select *, ta.subAcctMoney from t_mercash t left join t_admin ta on ta.id = t.userId where userId = " + userId;
        DataSet dataSet = MySQL.executeQuery(conn, sql);
        dataSet.tables.get(0).rows.genRowsMap();
        return dataSet.tables.get(0).rows.rowsMap;
    }
}
