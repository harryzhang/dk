package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 站内信管理
 * 
 * @author Administrator
 * 
 */
public class MailBoxManagerDao {



	/**
	 * 删除站内信
	 * 
	 * @param conn
	 * @param ids
	 *            id字符串拼接
	 * @param delimiter
	 *            拼接符号
	 * @return long
	 * @throws DataException
	 * @throws SQLException
	 */
	public int deleteMailBox(Connection conn, String commonIds, String delimiter)
			throws SQLException, DataException {
		DataSet dataSet = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		return Dao.Procedures.p_deleteMailBox(conn, dataSet, outParameterValues,
				commonIds, delimiter);
	}
	
	
	/**
	 * 更新站内信
	 * @param conn
	 * @param id
	 * @param title
	 * @param content
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateMailBoxById(Connection conn,Long id,String title,String content)throws SQLException,DataException
	{
		Dao.Tables.t_mail mail=new Dao().new Tables().new t_mail();
		mail.mailTitle.setValue(title);
		mail.mailContent.setValue(content);
		return mail.update(conn, "id="+id);
	}
	
	
	/**
	 * 获取站内信详情
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public  Map<String, String> getMailById(Connection conn,Long id)throws SQLException,DataException{
		Dao.Tables.t_mail mail=new Dao().new Tables().new t_mail();
		DataSet dataSet = mail.open(conn, "*", " id="+id, " ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	

	

}
