package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_seoconfig;

public class GetSEOConfigOnUpDao {
	/**
	 * 获取SEO配置信息
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String>  getSEOConfig(Connection conn) 
			throws SQLException, DataException{
		Dao.Tables.t_seoconfig seo = new Dao().new Tables().new t_seoconfig();
		DataSet dataSet = seo.open(conn, "*", "",
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);	
	}
}
