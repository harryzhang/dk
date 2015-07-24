package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Views;
import com.sp2p.database.Dao.Tables.t_help_question;
import com.sp2p.database.Dao.Views.v_t_borrow_list;

/**
 * 友情链接列表处理
 * @author li.hou
 *
 */
public class ClauseDao {

	/**
	 * 添加条款编辑信息
	 * @return
	 * @throws SQLException
	 */
	public Long addClause(Connection conn,String columnName,String content,int typeId) throws SQLException{
		Dao.Tables.t_message t_message = new Dao().new Tables().new t_message();
		if(columnName != null)
			t_message.columName.setValue(columnName);
		if(content != null)
			t_message.content.setValue(content);
		t_message.typeId.setValue(typeId);
		return t_message.insert(conn);
	}
	
	/**
	 * 删除条款信息
	 * @param conn
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public Long deleteClause(Connection conn,String ids) throws SQLException{
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_message t_links = new Dao().new Tables().new t_message();
		return t_links.delete(conn, " id in("+idSQL+")");
	}
	
	/**
	 * 根据条款id找条款信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queyClauseInfoById(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_message t_message = new Dao().new Tables().new t_message();
		DataSet dataSet = t_message.open(conn, "", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 根据条款id更新对应的条款信息
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long updateClause(Connection conn,String columnName,String content,int id) throws SQLException{
		Dao.Tables.t_message t_message = new Dao().new Tables().new t_message();
		if(columnName!=null){
			t_message.columName.setValue(columnName);
		}
		if(content!=null){
			t_message.content.setValue(content);
		}
		return t_message.update(conn, " id="+id);
	}
	
	public Map<String,String> getMaxSerial(Connection conn)throws SQLException, DataException{
		Dao.Tables.t_links tLinks = new Dao().new Tables().new t_links();
		DataSet dataSet = tLinks.open(conn, "max(serialCount)", " ", "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryBorrowClause(Connection conn, String condition) throws SQLException, DataException {
		Dao.Tables.t_message t_message = new Dao().new Tables().new t_message();
		DataSet dataSet = t_message.open(conn, "", condition, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
}
