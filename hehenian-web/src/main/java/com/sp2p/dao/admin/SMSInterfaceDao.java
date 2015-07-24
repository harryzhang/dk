package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 后台短信接口
 * @author Administrator
 *
 */
public class SMSInterfaceDao {
	

	
	
	
	/**
	 * 更新短信接口信息
	 * @param conn
	 * @param id
	 * @param UserID
	 * @param Account
	 * @param Password
	 * @param status
	 * @param url
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateSMS(Connection conn,Integer id,String UserID,String Account,String Password,String status,String url)throws SQLException,DataException{
		Dao.Tables.t_sms sms=new Dao().new Tables().new t_sms();
		sms.UserID.setValue(UserID);
		sms.Account.setValue(Account);
		sms.Password.setValue(Password);
		sms.status.setValue(status);
		sms.url.setValue(url);
		
		return sms.update(conn, "id="+id);
		
	}
	
	/**
	 * 根据Id获取短信接口信息详情
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getSMSById(Connection conn,Integer id)throws SQLException,DataException{
		Dao.Tables.t_sms sms=new Dao().new Tables().new t_sms();
		DataSet dataSet=sms.open(conn, "*", " id="+id, "", -1, -1);
	     return BeanMapUtils.dataSetToMap(dataSet);		
	}
	
	
	public List<Map<String, Object>> findBySMS(Connection conn)throws SQLException,DataException{
		Dao.Tables.t_sms sms=new Dao().new Tables().new t_sms();
		DataSet dataSet = sms.open(conn, "*", "", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	

}
