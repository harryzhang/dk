package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 自动投标
 * @author zhuchao
 *
 */
public class AutomaticBidDao {
	
	/**
	 * 根据id查询自动投标记录
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryBorrowStylById(Connection conn, int id) throws SQLException, DataException {
		Dao.Views.v_t_automaticbid  v_t_automaticbid = new Dao().new Views().new v_t_automaticbid();
		DataSet ds = v_t_automaticbid.open(conn, " * ", " id ="+id, "", -1,-1);
		ds.tables.get(0).rows.genRowsMap();
		 
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 关闭用户自动投标
	 * @param conn
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public long closeAutomaticBid(Connection conn, String ids) throws SQLException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		t_automaticbid.bidStatus.setValue(1);
        return t_automaticbid.update(conn, "id in (" + ids + ")");
	}
}

