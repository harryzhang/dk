package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 关闭网站
 * @author Administrator
 *
 */
public class CloseNetWorkDao {



	/**
	 * 更新网站状态（开启或禁用）
	 * @param conn
	 * @param id
	 * @param sort
	 * @param columName
	 * @param content
	 * @param publishTimee
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateNetWork(Connection conn,Integer status,String content)
	throws SQLException,DataException{
	      Dao.Tables.t_closenetwork netWork=new Dao().new Tables().new t_closenetwork();
	      netWork.status.setValue(status);
	      netWork.content.setValue(content);
		return netWork.update(conn, "id=1");
		
	}
	
	/**
	 * 获取网站信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getNetWorkById(Connection conn)
	throws SQLException,DataException{
		Dao.Tables.t_closenetwork netWork=new Dao().new Tables().new t_closenetwork();
		   
		DataSet dataSet=netWork.open(conn, "*", " id=1 ", "", -1, -1);
	    return BeanMapUtils.dataSetToMap(dataSet);		
	}
	
	
	
	

}
