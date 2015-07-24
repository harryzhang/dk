package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class BecomeToFinanceDao {

	/**
	 * 查询用户信息
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryFinancer(Connection conn,long userId,int limitStart,int limitCount )
		throws SQLException, DataException{
		Dao.Tables.t_user_finance t_info = new Dao().new Tables().new t_user_finance();
		
		String condition = " userId='"+userId+"'";
		DataSet dataSet = t_info.open(conn, "status", condition, " ", limitStart, limitCount);
		condition=null;
		return BeanMapUtils.dataSetToMap(dataSet);
		
	}
	/**
	 * 添加用户信息
	 * @param conn
	 * @param userId
	 * @param realName
	 * @param cellPhone
	 * @param idNo
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public long addBecomeFinancer(Connection conn,long userId,
			String realName,String cellPhone,String idNo,int status) throws SQLException{
		Dao.Tables.t_user_finance t_info = new Dao().new Tables().new t_user_finance();
		t_info.userId.setValue(userId);
		t_info.realName.setValue(realName);
		t_info.cellPhone.setValue(cellPhone);
		t_info.idNo.setValue(idNo);
		t_info.status.setValue(status);
		return t_info.insert(conn);
	}
}
