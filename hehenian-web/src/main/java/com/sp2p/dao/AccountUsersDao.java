package com.sp2p.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.sp2p.database.Dao.Functions;
import java.util.ArrayList;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.sp2p.database.Dao.Procedures;

public class AccountUsersDao {

	/**
	 * 添加用户资金
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
	 */
	public int addAccountUsers(Connection conn,String fundType,Long uId,BigDecimal money,Long traderId,String remark,String addIP) throws SQLException, DataException{
		int moneyStyle = Functions.f_moneyEncode(conn,fundType);
		if(moneyStyle == -1)
			return -1;
	    DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		return Procedures.pr_modifyUserAmount(conn, ds, outParameterValues, money,moneyStyle,remark,uId,traderId,new Date(),addIP);
		
	}
}
