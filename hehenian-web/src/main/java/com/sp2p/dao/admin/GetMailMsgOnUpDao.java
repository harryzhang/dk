package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class GetMailMsgOnUpDao {
	/**
	 * 从t_mailset表读取配置数据
	 */
	public Map<String, String> getMailSet(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_mailset mailset = new Dao().new Tables().new t_mailset();
		DataSet dataSet = mailset.open(conn, "*", "", "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
}
