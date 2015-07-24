package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.sp2p.database.Dao;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;

/**
 * 客服管理
 * @author Administrator
 *
 */
public class KefuDao {
	
	/**
	 * 添加客服信息
	 * @param conn
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addKefu(Connection conn,String kefuName,String imgPath,String qq,String remark)throws SQLException,DataException{
		Dao.Tables.t_user_kefu kefu=new Dao().new Tables().new t_user_kefu();
	    kefu._name.setValue(kefuName);
	    kefu.kefuImage.setValue(imgPath);
	    kefu.QQ.setValue(qq);
	    kefu.remark.setValue(remark);
		
		return kefu.insert(conn);
		
	}
	
	/**
	* 删除客服信息
	* @param conn
	* @param ids  id字符串拼接
	* @param delimiter  拼接符号
	* @return long
	* @throws DataException 
	* @throws SQLException 
	*/
	public int deleteKefu(Connection conn,String commonIds, String delimiter) throws SQLException, DataException{
		DataSet dataSet = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		return Dao.Procedures.p_deleteKefu(conn, dataSet, outParameterValues, commonIds, delimiter);
	}
	
	public Long updateKefu(Connection conn,Long id,String kefuName,String imgPath,String qq,String remark)throws SQLException,DataException{
		Dao.Tables.t_user_kefu kefu=new Dao().new Tables().new t_user_kefu();
	    kefu._name.setValue(kefuName);
	    kefu.kefuImage.setValue(imgPath);
	    kefu.QQ.setValue(qq);
	    kefu.remark.setValue(remark);
		
		return kefu.update(conn, "id="+id);
		
	}
	
	public Map<String,String> getKefuById(Connection conn,Long id)throws SQLException,DataException{
		Dao.Tables.t_user_kefu kefu=new Dao().new Tables().new t_user_kefu();
		DataSet dataSet=kefu.open(conn, "*", " id="+id, "", -1, -1);
	     return BeanMapUtils.dataSetToMap(dataSet);		
	}
	public long updateKefuByid(Connection conn,long userid,long kefuid) throws SQLException{
		Dao.Tables.t_user kefu=new Dao().new Tables().new t_user();
		kefu.kefuId.setValue(kefuid);
		return kefu.update(conn, " id = "+userid);
		
	}
	
	/**
	 * 查找客服信息
	 * @param conn
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryKefuList(Connection conn,int limitStart,int limitCount) throws SQLException, DataException{
		Dao.Tables.t_user_kefu t_info = new Dao().new Tables().new t_user_kefu();
		DataSet dataSet = t_info.open(conn, "*", " ", " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

}
