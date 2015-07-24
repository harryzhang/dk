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
 * 后台信息管理
 * 
 * @author Administrator
 * 
 */
public class MessageDao {

	/**
	 * 添加信息管理
	 * 
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
	public Long addMessage(Connection conn, Integer sort, String columName, String content, String publishTime) throws SQLException, DataException {
		Dao.Tables.t_message message = new Dao().new Tables().new t_message();
		message.sort.setValue(sort);
		message.columName.setValue(columName);
		message.content.setValue(content);
		message.publishTime.setValue(publishTime);

		return message.insert(conn);

	}

	/**
	 * 删除信息管理
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteMessage(Connection conn, Long id) throws SQLException, DataException {
		Dao.Tables.t_message message = new Dao().new Tables().new t_message();

		return message.delete(conn, "id=" + id);
	}

	/**
	 * 更新信息管理
	 * 
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
	public Long updateMessage(Connection conn, Long id, Integer sort, String columName, String content, String publishTimee) throws SQLException,
			DataException {
		Dao.Tables.t_message message = new Dao().new Tables().new t_message();
		message.sort.setValue(sort);
		message.columName.setValue(columName);
		message.content.setValue(content);
		message.publishTime.setValue(publishTimee);

		return message.update(conn, "id=" + id);

	}

	/**
	 * 根据Id获取信息管理详情
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getMessageById(Connection conn, Long id) throws SQLException, DataException {
		Dao.Tables.t_message message = new Dao().new Tables().new t_message();
		DataSet dataSet = message.open(conn, "*", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 根据信息管理类型查询信息详情
	 * 
	 * @param conn
	 * @param typeId
	 *            类型
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getMessageByTypeId(Connection conn, Integer typeId) throws SQLException, DataException {
		Dao.Tables.t_message message = new Dao().new Tables().new t_message();
		DataSet dataSet = message.open(conn, "*", " typeId=" + typeId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 合和年 信息管理 查询信息详情
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> getMessageByHHN(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_message message = new Dao().new Tables().new t_message();
		DataSet dataSet = message.open(conn, "*", " id<9", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 获取信息管理列表
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryMessageList(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_message message = new Dao().new Tables().new t_message();
		DataSet dataSet = message.open(conn, "*", "", " sort asc ", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

}
