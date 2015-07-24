package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class BankCardDao {

	/**
	 * 添加银行卡信息
	 * */
	public Long addBankCard(Connection conn, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_bankcard table = new Dao().new Tables().new t_bankcard();
		table.bankName.setValue(paramMap.get("bankName"));
		table.branchBankName.setValue(paramMap.get("BRANCHBANKNAME"));
		table.cardNo.setValue(paramMap.get("bankNo"));
		table.cardUserName.setValue(paramMap.get("cardUserName"));
		table.userId.setValue(paramMap.get("USERID"));
		table.cardStatus.setValue(1);
		return table.insert(conn);

	}

	public Map<String, String> queryFirstBankCardById(Connection conn, long userId) throws SQLException, DataException {
		Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
		String condition = " userId='" + userId + "'";
		DataSet dataSet = t_info.open(conn, "*", condition, " ", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public long deleteBank(Connection conn, long id) throws SQLException {
		Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
		return t_info.delete(conn, " id=" + id);
	}
}
