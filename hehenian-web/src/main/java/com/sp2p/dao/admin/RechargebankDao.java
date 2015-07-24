package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;

public class RechargebankDao {
	
	public Map<String,String> queryrechargeBankById(Connection conn, long id) throws DataException, SQLException{
			Dao.Tables.t_rechargebank t_rechargebank = new Dao().new Tables().new t_rechargebank();
			DataSet dataSet = t_rechargebank.open(conn, "", " id = " + id, "", -1, -1);
			return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 添加充值银行
	 * @param conn
	 * @param bankname
	 * @param Account
	 * @param accountbank
	 * @param bankimage
	 * @return
	 * @throws SQLException
	 */
	public long addRechargeBankInit(Connection conn,String bankname,String Account,String accountbank,String bankimage,String accountname) throws SQLException{
		Dao.Tables.t_rechargebank t_rechargebank = new Dao().new Tables().new t_rechargebank();
		t_rechargebank.bankname.setValue(bankname);
		t_rechargebank.Account.setValue(Account);
		t_rechargebank.accountbank.setValue(accountbank);
		t_rechargebank.bankimage.setValue(bankimage);
		t_rechargebank.accountname.setValue(accountname);
		return t_rechargebank.insert(conn);
	}
	
	
	
	
	
	public long updaterechargeBankById(Connection conn,long id,String bankname,String Account,String accountbank,String bankimage,String accountname) throws SQLException{
		Dao.Tables.t_rechargebank t_rechargebank = new Dao().new Tables().new t_rechargebank();
		t_rechargebank.bankname.setValue(bankname);
		t_rechargebank.Account.setValue(Account);
		t_rechargebank.accountbank.setValue(accountbank);
		if(StringUtils.isNotBlank(bankimage)){
			t_rechargebank.bankimage.setValue(bankimage);
		}
		t_rechargebank.accountname.setValue(accountname);
		return t_rechargebank.update(conn, " id = "+id);
		
	}
	
	public Map<String,String> queryrechargeBank(Connection conn) throws DataException, SQLException{
		Dao.Tables.t_rechargebank t_rechargebank = new Dao().new Tables().new t_rechargebank();
		DataSet dataSet = t_rechargebank.open(conn, "*", "","", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
}
	/**
	 * 查询后台充值银行
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryrechargeBanklist(Connection conn) throws SQLException, DataException{
		DataSet ds = 	MySQL.executeQuery(conn," select * from t_rechargebank ");
	  	ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	
	
	/**
	 * 查询资金流向类型
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String,Object>> queryFundRecordType(Connection conn) throws SQLException, DataException{
		DataSet ds = 	MySQL.executeQuery(conn," select DISTINCT  fundMode as  fundMode from t_fundrecord where fundMode <> '虚拟充值'");
	  	ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 统计合计金额
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> queryFundRecordTypeAmount(Connection conn,long userId,String startTime,
			String endTime,Map<String,String> typeMap) throws SQLException, DataException{
		String condition = "";
		if(startTime == null || endTime == null){//没有时间就查询所有记录
			if(typeMap==null){
				condition = "and userId="+userId;
			}else{
				condition = "and userId="+userId+typeMap.get("conditionSQL")+"";
			}
		}else{
			if(typeMap==null){
				condition = "and userId="+userId+" and recordTime<='"+StringEscapeUtils.escapeSql(endTime.trim())+"' and recordTime >= '"+StringEscapeUtils.escapeSql(startTime.trim())+"'";
			}else{
				condition = "and userId="+userId+" and recordTime<='"+StringEscapeUtils.escapeSql(endTime.trim())+"' and recordTime >= '"+StringEscapeUtils.escapeSql(startTime.trim())+typeMap.get("conditionSQL")+"";
			}
		}
		DataSet ds = MySQL.executeQuery(conn,"select sum(handleSum) as allAmount from t_fundrecord  where 1 = 1 "+condition);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	
	
	
	
	
}
