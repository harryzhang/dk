package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sp2p.dao.admin.MailBoxManagerDao;

import com.shove.base.BaseService;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.vo.PageBean;

/**
 * 站内信息管理
 * @author zhongchuiqing
 *
 */
public class MailBoxManagerService extends BaseService {

public static Log log =LogFactory.getLog(MailBoxManagerService.class);
	
	
	
	private ConnectionManager connectionManager;
	
	private MailBoxManagerDao mailBoxManagerDao;

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	
	
	
	public MailBoxManagerDao getMailBoxManagerDao() {
		return mailBoxManagerDao;
	}

	public void setMailBoxManagerDao(MailBoxManagerDao mailBoxManagerDao) {
		this.mailBoxManagerDao = mailBoxManagerDao;
	}

	/**
	 * 根据条件查询站内信（管理员与用户，用户与用户）
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryMailByCondition(PageBean pageBean,Integer mailType,String sender,String beginTime,String endTime)throws SQLException,DataException{
		Connection conn = connectionManager.getConnection();
		StringBuffer condition =new StringBuffer("and backgroundStatus!=2 ");
		if(mailType!=null){
			condition.append("and mailType=");
			condition.append(mailType);
		}
		if(StringUtils.isNotBlank(sender)){
			condition.append(" and sender LIKE '%");
			condition.append(StringEscapeUtils.escapeSql(sender));
			condition.append("%'");
		}
		if(StringUtils.isNotBlank(beginTime)){
			condition.append(" and sendTime >= '");
			condition.append(StringEscapeUtils.escapeSql(beginTime));
			condition.append("'");
		}
		if(StringUtils.isNotBlank(endTime)){
			condition.append(" and sendTime <= '");
			condition.append(StringEscapeUtils.escapeSql(endTime));
			condition.append("'");
		}
		try {
			dataPage(conn, pageBean, "t_mail", "*", " order by sendTime desc ", condition.toString());
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
	}
	
	
	
	/**
	* 删除团队介绍的数据
	* @param commonIds id拼接字符串
	* @param delimiter 分割符
	* @throws DataException
	* @throws SQLException
	* @return int
	*/
	public int deleteMailBox(String commonIds, String delimiter) throws DataException, SQLException{
		Connection conn = connectionManager.getConnection();
		int result = -1;
		try {
			result =mailBoxManagerDao.deleteMailBox(conn, commonIds, delimiter);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	public Long updateMailBoxById(Long id,String title,String content)throws SQLException,DataException
	{
		Connection conn = connectionManager.getConnection();
		Long result=-1L;
		try {
			result=mailBoxManagerDao.updateMailBoxById(conn, id, title, content);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
  
	public  Map<String, String> getMailById(Long id)
	throws SQLException,DataException{
		Connection conn = connectionManager.getConnection();
		Map<String, String> map=null;
		try {
			map=mailBoxManagerDao.getMailById(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
		
		
	}


	
}
