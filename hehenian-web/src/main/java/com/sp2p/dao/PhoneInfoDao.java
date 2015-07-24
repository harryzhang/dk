package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Views;
import com.sp2p.database.Dao.Tables.bt_config;
import com.sp2p.database.Dao.Views.v_t_borrow_list;

/**
 * 工具箱
 * @author li.hou
 *
 */
public class PhoneInfoDao {

	/**
	 * 根据手机号码查询手机信息
	 * @param conn
	 * @param phoneNum
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPhoneInfoByNum(Connection conn, String phoneNum) throws SQLException, DataException {
		return null;
	}
	
	public List<Map<String,Object>> queryConfigList(Connection conn,int limitStart,int limitCount)
	throws SQLException, DataException {
		Dao.Tables.bt_config t_config = new Dao().new Tables().new bt_config();
		/**
		 * 类型为1
		 */
		DataSet dataSet = t_config.open(conn, "*", " type=2", "", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
}
