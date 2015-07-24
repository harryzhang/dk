package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
/**
 * 后台团队管理
 * @author Administrator
 *
 */
public class TeamDao {
	
	/**
	 * 添加团队信息
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
	public Long addTeam(Connection conn,Integer sort,String userName,String imgPath,String intro,String publishTime)throws SQLException,DataException{
		Dao.Tables.t_team team=new Dao().new Tables().new t_team();
		team.sort.setValue(sort);
		team.userName.setValue(userName);
		team.imgPath.setValue(imgPath);
		team.intro.setValue(intro);
		team.publishTime.setValue(publishTime);
		
		return team.insert(conn);
		
	}
	/**
	 * 删除团队信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteTeam(Connection conn,Long id)throws SQLException,DataException{
		Dao.Tables.t_team team=new Dao().new Tables().new t_team();
		team.state.setValue(2);
		 return team.update(conn, "id="+id);	 
	}
	
	/**
	* 删除团队信息
	* @param conn
	* @param ids  id字符串拼接
	* @param delimiter  拼接符号
	* @return long
	* @throws DataException 
	* @throws SQLException 
	*/
	public int deleteTeam(Connection conn,String commonIds, String delimiter) throws SQLException, DataException{
		DataSet dataSet = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		return Dao.Procedures.p_deleteTeam(conn, dataSet, outParameterValues, commonIds, delimiter);
	}
	
	public Long updateTeam(Connection conn,Long id,Integer sort,String userName,String imgPath,String intro,String publishTime)throws SQLException,DataException{
		Dao.Tables.t_team team=new Dao().new Tables().new t_team();
		team.sort.setValue(sort);
		team.userName.setValue(userName);
		team.imgPath.setValue(imgPath);
		team.intro.setValue(intro);
		team.publishTime.setValue(publishTime);
		
		return team.update(conn, "id="+id);
		
	}
	
	public Map<String,String> getTeamById(Connection conn,Long id)throws SQLException,DataException{
		Dao.Tables.t_team team=new Dao().new Tables().new t_team();
		DataSet dataSet=team.open(conn, "*", " id="+id+" AND state=1", "", -1, -1);
	     return BeanMapUtils.dataSetToMap(dataSet);		
	}
	
	public List<Map<String,Object>> queryTeamList(Connection conn)throws SQLException,DataException{
		Dao.Tables.t_team team=new Dao().new Tables().new t_team();
		//modify by houli  添加按照sort进行排序的条件
		DataSet dataSet=team.open(conn, "*", "state=1", " sort ",-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * add by houli 查找表里最大的排列序号
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getMaxSerial(Connection conn) throws SQLException, DataException{
		Dao.Tables.t_team team = new Dao().new Tables().new t_team();
		DataSet dataSet = team.open(conn, "max(sort) as sortId", "",
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * add by houli 判断sort是否存在
	 * @param conn
	 * @param sortId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> isExistSortId(Connection conn,int sortId) throws SQLException, DataException{
		Dao.Tables.t_team team = new Dao().new Tables().new t_team();
		DataSet dataSet = team.open(conn, " sort", " sort="+sortId,
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * add by houli 判断修改后的sort是否存在
	 * @param conn
	 * @param sortId
	 * @param originalSortId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> isExistToUpdate(Connection conn,int sortId,int originalSortId) throws SQLException, DataException{
		String command = "SELECT id,sort from (select id,sort from t_team " +
				" where sort != "+originalSortId+" ) b where sort="+sortId;
		DataSet dataSet = MySQL.executeQuery(conn, command); 
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	 
}
