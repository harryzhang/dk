package com.hehenian.biz.dal.individualCenter.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;


import com.hehenian.biz.common.individualCenter.BeanMapUtils;
import com.hehenian.biz.common.individualCenter.Dao;
import com.shove.data.DataException;
import com.shove.data.DataSet;

public class UserDao {

	//private UserManageDao userManageDao;
	
	/**
	 * 用户基本信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPersonById(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		DataSet dataSet = person.open(conn, " * ", "userId = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param conn
	 * @param id
	 *            用户编号
	 * @throws SQLException
	 * @throws DataException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserById(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet dataSet = user.open(conn, "*", " id=" + id, " id desc ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	


}
