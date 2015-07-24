package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class ShowShipinDao {
	/**
	 * 更新视频认证的审核状态
	 * 
	 * @param conn
	 * @param tmid
	 *            证件主表id
	 * @param status
	 *            审核状态
	 * @return
	 * @throws SQLException
	 */
	public Long updateMa(Connection conn, Long tmid, int status)
			throws SQLException {
		Dao.Tables.t_materialsauth tm = new Dao().new Tables().new t_materialsauth();
		tm.auditStatus.setValue(status);
		return tm.update(conn, " id = " + tmid);
	}

	/**
	 * 更新或者插入视频资料审核明细表中
	 * 
	 * @param conn
	 * @param tmid
	 *            证件主表id
	 * @param tmtype
	 *            证件类型
	 * @param status
	 *            审核状态
	 * @param flag
	 *            判断是否插入或者更新
	 * @param tmdid
	 *            证件主表下的明细表
	 * @return
	 * @throws SQLException
	 */
	public Long updateMade(Connection conn, Long tmid, Long tmtype, int status,
			boolean flag, Long tmdid) throws SQLException {
		Dao.Tables.t_materialimagedetal tmd = new Dao().new Tables().new t_materialimagedetal();
		tmd.uploadingTime.setValue(new Date());
		tmd.auditStatus.setValue(status);
		tmd.materialsauthid.setValue(tmid);
		if (flag) {
			return tmd.insert(conn);
		} else {
			return tmd.update(conn, " id =  " + tmdid);
		}
	}
	/**
	 * 查询视频资料审核明细表中
	 * @param tmid 资料主表id
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> queryMade(Connection conn,Long tmid) throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ");
		sql.append(" tmd.id as tmdid ");
		sql.append(" from  ");
		sql.append(" t_materialimagedetal tmd ");
		sql.append(" where tmd.materialsauthid =  "+tmid);
		sql.append(" LIMIT  0 , 1 ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql= null;
		return BeanMapUtils.dataSetToMap(dataSet);
		
	}
	
	
	

}
