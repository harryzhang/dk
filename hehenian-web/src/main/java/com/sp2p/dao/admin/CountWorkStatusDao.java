package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_materialsauth;


public class CountWorkStatusDao {
	/**
	 * 返回今本信息的4个状态值
	 * @param id 用户id
	 * @return
	 */
	public boolean queryWorkStatus(Connection conn,Long id) throws SQLException{
		Dao.Tables.t_workauth workauth = new Dao().new Tables().new t_workauth();
		Map<String, String> wormap = null;
		Integer dirauditStatus = -1;
		Integer otauditStatus = -1;
		Integer moauditStatus = -1;
		Integer perauditStatus = -1;
		Integer allStatus = -1;
		boolean flag = false;
		try {
			DataSet workds = workauth.open(conn,
					"auditStatus,directedStatus,otherStatus,moredStatus",
					" userId = " + id, "", -1, -1);
			wormap = BeanMapUtils.dataSetToMap(workds);
		} catch (DataException e) {
			e.printStackTrace();
			return flag;
		}
		if (wormap != null && wormap.size() > 0 ) {
			// 获取工作信息中的4个状态值
			dirauditStatus = Convert.strToInt(wormap.get("directedStatus"), -1);
			otauditStatus = Convert.strToInt(wormap.get("otherStatus"), -1);
			moauditStatus = Convert.strToInt(wormap.get("moredStatus"), -1);
			perauditStatus = Convert.strToInt(wormap.get("auditStatus"), -1);// 获取person中状态值
			if(dirauditStatus!=-1&&otauditStatus!=-1&&moauditStatus!=-1&&perauditStatus!=-1){
		        if(dirauditStatus==1&&otauditStatus==1&&moauditStatus==1&&perauditStatus==3){
		        	flag = true;
		        }
			}
		}
		return flag;
	}
	/**
	 * 判断工作信息中是否审核失败
	 * @param id  用户id
	 */
	public boolean isFailWorkstatus(Connection conn,Long id) throws SQLException{
		Dao.Tables.t_workauth workauth = new Dao().new Tables().new t_workauth();
		Map<String, String> wormap = null;
		Integer dirauditStatus = -1;
		Integer otauditStatus = -1;
		Integer moauditStatus = -1;
		Integer perauditStatus = -1;
		Integer allStatus = -1;
		boolean flag = false;
		try {
			DataSet workds = workauth.open(conn,
					"auditStatus,directedStatus,otherStatus,moredStatus",
					" userId = " + id, "", -1, -1);
			wormap = BeanMapUtils.dataSetToMap(workds);
		} catch (DataException e) {
			e.printStackTrace();
			return flag;
		}
		if (wormap != null && wormap.size() > 0 ) {
			// 获取工作信息中的4个状态值
			dirauditStatus = Convert.strToInt(wormap.get("directedStatus"), -1);
			otauditStatus = Convert.strToInt(wormap.get("otherStatus"), -1);
			moauditStatus = Convert.strToInt(wormap.get("moredStatus"), -1);
			perauditStatus = Convert.strToInt(wormap.get("auditStatus"), -1);// 获取person中状态值
			if(dirauditStatus!=-1&&otauditStatus!=-1&&moauditStatus!=-1&&perauditStatus!=-1){
		        if(dirauditStatus==2&&otauditStatus==2&&moauditStatus==2&&perauditStatus==2){
		        	flag = true;
		        }
			}
		}
		return flag;
	}
	/**
	 * 查询审核证件之前的信用分数
	 * @param userId
	 * @param type
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> queryC(Connection conn,Long userId,Integer type) throws SQLException, DataException{
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
		DataSet mads  = materialsauth.open(conn, "criditing,auditStatus", " userId = "
					+ userId + " AND materAuthTypeId = " + type, "", 0, -1);
		 return  BeanMapUtils.dataSetToMap(mads);
	} 
	
	
	
	
   
	
	
}
