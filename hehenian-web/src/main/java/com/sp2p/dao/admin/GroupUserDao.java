package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_group;

/**
 * 用户组与用户关系表
 * @author Administrator
 *
 */
public class GroupUserDao {
	
	/**
	 * 添加用户组与用户关系
	 * @param conn
	 * @param userId 用户Id
	 * @param groupId 用户组ID
	 * @return
	 * @throws SQLException
	 */
	public long addGroupUser(Connection conn,long userId,long groupId) throws SQLException{
		Dao.Tables.t_group_user t_group_user = new Dao().new Tables().new t_group_user();
		t_group_user.groupId.setValue(groupId);
		t_group_user.userId.setValue(userId);
		return t_group_user.insert(conn);
	}
	
	/**
	 * 根据ID删除用户关系
	 * @param conn
	 * @param id 用户关系ID
	 * @return
	 * @throws SQLException
	 */
	public long deleteGroupUserById(Connection conn,String id) throws SQLException{
		String idStr = StringEscapeUtils.escapeSql("'"+id+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_group_user t_group_user = new Dao().new Tables().new t_group_user();
		return t_group_user.delete(conn, " id in ("+idSQL+")");
	}
	
	/**
	 * 根据用户组ID删除用户关系
	 * @param conn
	 * @param id 用户关系ID
	 * @return
	 * @throws SQLException
	 */
	public long deleteGroupUserByGroupId(Connection conn,String groupId) throws SQLException{
		String idStr = StringEscapeUtils.escapeSql("'"+groupId+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_group_user t_group_user = new Dao().new Tables().new t_group_user();
		return t_group_user.delete(conn, " groupId in ("+idSQL+ ")");
	}

	/**
	 * 根据用户组ID查询用户ID
	 * @param conn
	 * @param groupId
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Long> queryUserIdByGroupId(Connection conn,
			long groupId) throws SQLException, DataException {
		Dao.Tables.t_group_user t_group_user = new Dao().new Tables().new t_group_user();
		DataSet ds = t_group_user.open(conn, "userId", " groupId="+groupId, "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		List<Map<String,Object>> userMapList = ds.tables.get(0).rows.rowsMap;
		List<Long> userIdList = new ArrayList<Long>();
		if(userMapList != null){
			for(Map<String,Object> map : userMapList){
				userIdList.add((Long)map.get("userId"));
			}
		}
		userMapList=null;
		return userIdList;
	}

	public List<Map<String, Object>> queryGroupUsers(Connection conn,
			String userName, String realName, double startAllSum,
			double endAllSum, double startUseableSum, double endUseableSum,
			long groupId) throws SQLException, DataException {
		StringBuilder condition = new StringBuilder(" 1=1 ");
		if(StringUtils.isNotBlank(userName)){
			condition.append(" AND username like '%");
			condition.append(StringEscapeUtils.escapeSql(userName));
			condition.append("%'");
		}
		if(StringUtils.isNotBlank(realName)){
			condition.append(" AND realName like '%");
			condition.append(StringEscapeUtils.escapeSql(realName));
			condition.append("%'");
		}
		if(startAllSum > 0){
			condition.append(" AND allSum >=");
			condition.append(startAllSum);
		}
		if(endAllSum > 0){
			condition.append(" AND allSum <=");
			condition.append(endAllSum);
		}
		if(startUseableSum > 0){
			condition.append(" AND usableSum >=");
			condition.append(startUseableSum);
		}
		if(endUseableSum > 0){
			condition.append(" AND usableSum <=");
			condition.append(endUseableSum);
		}
		if(groupId > 0){
			condition.append(" AND groupId=");
			condition.append(groupId);
		}
		
		Dao.Views.v_t_groupuser_user_person v_t_groupuser_user_person = new Dao().new Views().new v_t_groupuser_user_person();
		DataSet dataSet=v_t_groupuser_user_person.open(conn, "*", condition.toString() + " ", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition=null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 查询用户组的电话
	 * @param conn
	 * @param groupId
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String,Object>> queryUserPhoneByGroupId(Connection conn, long groupId) throws SQLException, DataException {
		Dao.Tables.t_person t_person = new Dao().new Tables().new t_person();
		DataSet dataSet=t_person.open(conn, "cellPhone", " userId in ( select userId from t_group_user where groupId ="+groupId+")", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 查询用户组的邮箱号
	 * @param conn
	 * @param groupId
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> queryUserEmailByGroupId(Connection conn,
			long groupId) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet dataSet=t_user.open(conn, "email,username", " id in ( select userId from t_group_user where groupId ="+groupId+")", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String, Object>> queryGroupUsersByIds(Connection conn,
			String ids) throws SQLException, DataException {
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Views.v_t_groupuser_user_person v_t_groupuser_user_person = new Dao().new Views().new v_t_groupuser_user_person();
		DataSet dataSet=v_t_groupuser_user_person.open(conn, "*", " id in (" + idSQL+ ")", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public Map<String,String> queryGroupById(Connection conn,Long groupId) throws SQLException, DataException{
		Dao.Tables.t_group t_group = new Dao().new Tables().new t_group();
		DataSet dataSet = t_group.open(conn, " id as groupId,groupName,groupRemark,cashStatus,adminId", " id="+groupId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public List<Map<String,Object>> queryUsersByGroupId(Connection conn,Long groupId) throws SQLException, DataException{
		String sqlStr = "SELECT a.id as id,a.groupId as groupId,a.userId as userId,b.username as username from " +
				"t_group_user a LEFT JOIN t_user b on a.userId=b.id WHERE a.groupId="+groupId;
		DataSet dataSet = MySQL.executeQuery(conn, sqlStr);
		dataSet.tables.get(0).rows.genRowsMap();
		sqlStr=null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
}
