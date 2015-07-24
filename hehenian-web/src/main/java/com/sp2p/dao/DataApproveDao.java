package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class DataApproveDao {

	public Map<String,String> querySauthId(Connection conn, long userId,long typeId,
			int limitStart,int limitCount) 
	    throws SQLException, DataException{
		Dao.Tables.t_materialsauth t_materialsauth = new Dao().new Tables().new t_materialsauth();
		DataSet dataSet = t_materialsauth.open(conn, " id,auditStatus ", " userId=" + userId+" and materAuthTypeId="+typeId, 
				"", limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询认证资料审核状态
	 * @param conn
	 * @param sauthId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryApproveStatus(Connection conn, long sauthId,int limitStart,int limitCount)
	    throws SQLException, DataException{
	    	Dao.Tables.t_materialimagedetal t_materialImagedetal = 
	    		new Dao().new Tables().new t_materialimagedetal();
			DataSet dataSet = t_materialImagedetal.open(conn, " auditStatus ", " materialsauthid=" + sauthId, 
					"", limitStart, limitCount);
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 查询个人信息资料审核状态
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryPersonInfo(Connection conn, long userId,
			int limitStart,int limitCount) 
	    throws SQLException, DataException{
		Dao.Tables.t_person t_person = new Dao().new Tables().new t_person();
		DataSet dataSet = t_person.open(conn, " id,auditStatus ", " userId=" + userId, 
				"", limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询所有通过个人信息认证的用户
	 * @param conn
	 * @param passStatus
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String,Object>> queryAllPassPerson(Connection conn,int passStatus,
			int limitStart,int limitCount) throws SQLException, DataException{
		Dao.Tables.t_person t_person = new Dao().new Tables().new t_person();
		DataSet dataSet = t_person.open(conn, " userId ", " auditStatus=" + passStatus, 
				"", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public Map<String,String> queryWorkInfo(Connection conn, long userId,
			int limitStart,int limitCount) 
	    throws SQLException, DataException{
		Dao.Tables.t_workauth t_workauth = new Dao().new Tables().new t_workauth();
		DataSet dataSet = t_workauth.open(conn, " id,auditStatus," +
				"directedStatus,otherStatus,moredStatus ", " userId=" + userId, 
				"", limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public List<Map<String,Object>> queryAllPassWork(Connection conn,int passStatus,
			int limitStart,int limitCount) throws SQLException, DataException{
		Dao.Tables.t_workauth t_workauth = new Dao().new Tables().new t_workauth();
		String command = " auditStatus="+passStatus+" and directedStatus="+passStatus+
		" and otherStatus="+passStatus+" and moredStatus="+passStatus;
		DataSet dataSet = t_workauth.open(conn, " userId ", command, 
				"", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		command=null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
}
