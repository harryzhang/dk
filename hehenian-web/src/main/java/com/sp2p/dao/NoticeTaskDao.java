package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
/**
 * 短消息 (模板)Dao
 * @author C_J
 *
 */
public class NoticeTaskDao {
	/**
	 *  根据用户Id 和操作ID  查询 短消息
	 * @param conn
	 * @param userId
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @author  C_J
	 */
	public Map<String,String> queryNoticeTask(Connection  conn,long userId,long borrowId) throws SQLException, DataException{
		Dao.Tables.t_notice_task  t_notice_task = new Dao().new Tables().new t_notice_task();
		DataSet  ds = t_notice_task.open(conn, " *  ", " user_id = "+userId+"  and  operate_id = "+borrowId+"  and  operate_identify = 'first_auth'  " , " id desc", 0, 1);
		
		return BeanMapUtils.dataSetToMap(ds);
	}
	public Map<String,String> queryNoticeTasklog(Connection  conn,long userId,long borrowId) throws SQLException, DataException{
		Dao.Tables.t_notice_task_log  t_notice_task = new Dao().new Tables().new t_notice_task_log();
		DataSet  ds = t_notice_task.open(conn, " *  ", " user_id = "+userId+"  and  operate_id = "+borrowId+" and  operate_identify = 'first_auth' " , " id desc ", 0, 1);
		
		return BeanMapUtils.dataSetToMap(ds);
	}
}
