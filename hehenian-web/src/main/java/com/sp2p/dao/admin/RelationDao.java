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

public class RelationDao {

	/**
	 * 添加角色之间的关系
	 * @param conn
	 * @param peopleId
	 * @param parentId
	 * @param level
	 * @param enable
	 * @return
	 * @throws SQLException
	 */
	public long addRelation(Connection conn,long peopleId,long parentId,int level,int enable) throws SQLException{
		Dao.Tables.t_relation relation = new Dao().new Tables().new t_relation();
		relation.peopleId.setValue(peopleId);
		relation.parentId.setValue(parentId);
		relation.level.setValue(level);
		relation.enable.setValue(enable);
		return relation.insert(conn);
	}
	
	/**
	 * 修改角色之间的关系
	 * @param conn
	 * @param id
	 * @param peopleId
	 * @param parentId
	 * @param level
	 * @param enable
	 * @return
	 * @throws SQLException
	 */
	public long updateRelation(Connection conn,long id,Long peopleId,Long parentId,Integer level,Integer enable) throws SQLException{
		Dao.Tables.t_relation relation = new Dao().new Tables().new t_relation();
		if(peopleId!=null&&peopleId>0){
			relation.peopleId.setValue(peopleId);
		}
		if(parentId!=null&&parentId>0){
			relation.parentId.setValue(parentId);
		}
		if(level!=null&&level>0){
			relation.level.setValue(level);
		}
		if(enable!=null&&enable>0){
			relation.enable.setValue(enable);
		}
		return relation.update(conn, " id =  "+id);
	}
	
	/**
	 * 根据编号查询编号信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String,String> queryRelationById(Connection conn,long id) throws DataException, SQLException{
		Dao.Tables.t_relation relation = new Dao().new Tables().new t_relation();
		DataSet dataSet = relation.open(conn, "  ", " id= "+id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 根据角色编号查询信息
	 * @param conn
	 * @param peopleId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryRelationByPeopleId(Connection conn,long peopleId) throws SQLException, DataException{
		Dao.Tables.t_relation relation = new Dao().new Tables().new t_relation();
		DataSet dataSet = relation.open(conn, "  ", " peopleId= "+peopleId, "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	

	public List<Map<String,Object>> queryRelationByUserId(Connection conn,long peopleId) throws SQLException, DataException{
	Dao.Tables.t_relation relation = new Dao().new Tables().new t_relation();
	DataSet dataSet = relation.open(conn, " * ", " level in(3,4) and peopleId= "+peopleId, "", -1, -1);
	dataSet.tables.get(0).rows.genRowsMap();
	return dataSet.tables.get(0).rows.rowsMap;
	}

	
	public List<Map<String,Object>> queryRelationStatus(Connection conn,Long level2userId,Long level1userId) throws SQLException, DataException{
		StringBuffer condition = new StringBuffer();
		if(level2userId!=null&&level2userId>0){
			condition.append(" level2userId = "+level2userId);
		}
		if(level1userId!=null&&level1userId>0){
			condition.append(" level1userId = "+level1userId);
		}
		Dao.Views.v_t_relation_enable enable = new Dao().new Views().new v_t_relation_enable();
		DataSet dataSet = enable.open(conn, "", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition=null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
    /** 
     * 判断是否是网站会员
     * @param conn
     * @param userId
     * @return
     * @throws SQLException
     * @throws DataException [参数说明]
     * 
     * @return List<Map<String,Object>> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public Map<String, String> queryUserById(Connection conn, String userId)
        throws SQLException, DataException
    {
        Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
        DataSet dataSet = t_user.open(conn, "*", " id= "+userId, "", -1, -1);
        return BeanMapUtils.dataSetToMap(dataSet);
    }
	
	
	public List<Map<String, Object>> queryUserByRelation3(Connection conn,
			String userName) throws SQLException, DataException {
		StringBuffer condition = new StringBuffer();
		condition.append(" level = 3 ");
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND cid = '"+StringEscapeUtils.escapeSql(userName.trim()) + "'");
		}
		Dao.Views.v_t_relation_level3 user = new Dao().new Views().new v_t_relation_level3();
		DataSet dataSet = user
				.open(conn, " peopleId as id,cid ", condition.toString(), " ", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition=null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String, Object>> queryUserByRelation2(Connection conn,
			String userId) throws SQLException, DataException {
		StringBuffer condition = new StringBuffer();
		condition.append(" level = 2 ");
		if (StringUtils.isNotBlank(userId)) {
			condition.append(" AND bid = '"+StringEscapeUtils.escapeSql(userId.trim()) + "'");
		}
		Dao.Views.v_t_relation_level2 user = new Dao().new Views().new v_t_relation_level2();
		DataSet dataSet = user
				.open(conn, " peopleId as id,bid ", condition.toString(), " ", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition=null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
}
