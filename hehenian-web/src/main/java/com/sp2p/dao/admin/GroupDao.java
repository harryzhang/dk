package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 用户组
 * @author Administrator
 *
 */
public class GroupDao {
	/**
	 * 添加用户组
	 * @param conn
	 * @param adminId 添加人
	 * @param groupName 用户组名
	 * @param groupRemark  用户组备注
	 * @param cashStatus  提现状态(add by houli ,添加用户组的时候设置提现状态)
	 * @return
	 * @throws SQLException
	 */
	public long addGroup(Connection conn,long adminId,String groupName,String groupRemark,
			int cashStatus) throws SQLException{
		Dao.Tables.t_group t_group = new Dao().new Tables().new t_group();
		t_group.adminId.setValue(adminId);
		t_group.groupName.setValue(StringEscapeUtils.escapeSql(groupName));
		t_group.groupRemark.setValue(StringEscapeUtils.escapeSql(groupRemark));
		//-------add by houli
		t_group.cashStatus.setValue(cashStatus);
		//------------
		return t_group.insert(conn);
	}
	
	/**
	 * 修改用户组
	 * @param conn
	 * @param id 用户组ID
	  @param adminId 添加人
	 * @param groupName 用户组名
	 * @param groupRemark  用户组备注
	 * @param cashStatus 提现状态
	 * @return
	 * @throws SQLException
	 */
	public long updateGroup(Connection conn,long id,long adminId,String groupName,String groupRemark,int cashStatus) throws SQLException{
		Dao.Tables.t_group t_group = new Dao().new Tables().new t_group();
		if(adminId > 0){
			t_group.adminId.setValue(adminId);
		}
		if(StringUtils.isNotBlank(groupName)){
			t_group.groupName.setValue(StringEscapeUtils.escapeSql(groupName));
		}
		
		if(StringUtils.isNotBlank(groupRemark)){
			t_group.groupRemark.setValue(StringEscapeUtils.escapeSql(groupRemark));
		}
		if(cashStatus > 0){
			t_group.cashStatus.setValue(cashStatus);
		}
		return t_group.update(conn, " id="+id);
	}
	
	/**
	 * 删除用户组
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public long deleteGroup(Connection conn,String id) throws SQLException{
		String idStr = StringEscapeUtils.escapeSql("'"+id+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_group t_group = new Dao().new Tables().new t_group();
		return t_group.delete(conn, " id in (" +idSQL+")");
	}
	
	/**
	 * 根据Id获取用户组信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> getGroup(Connection conn,long id) throws SQLException, DataException{
		Dao.Tables.t_group t_group = new Dao().new Tables().new t_group();
		DataSet dataSet=t_group.open(conn, "*", " id="+id, "", -1, -1);
	     return BeanMapUtils.dataSetToMap(dataSet);	
	}

	/**
	 * 查询所有的用户组
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> queryAllGroup(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_group t_group = new Dao().new Tables().new t_group();
		DataSet dataSet=t_group.open(conn, "id,groupName", " ", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public long modifyGroup(Connection conn,long groupId,long adminId,String groupName,String groupRemark,
			int cashStatus) throws SQLException{
		Dao.Tables.t_group t_group = new Dao().new Tables().new t_group();
		t_group.adminId.setValue(adminId);
		t_group.groupName.setValue(StringEscapeUtils.escapeSql(groupName));
		t_group.groupRemark.setValue(StringEscapeUtils.escapeSql(groupRemark));
		t_group.cashStatus.setValue(cashStatus);
		return t_group.update(conn, " id= "+groupId);
	}
	
}
