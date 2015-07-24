package com.sp2p.service.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import com.sp2p.dao.admin.SendSMSDao;

import com.shove.base.BaseService;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.vo.PageBean;

/**
 *短信发送
 * @author zhongchuiqing
 *
 */
public class SendSMSService extends BaseService {

public static Log log =LogFactory.getLog(SendSMSService.class);
	
	private SendSMSDao sendSMSDao;

	
	private ConnectionManager connectionManager;
	

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	public SendSMSDao getSendSMSDao() {
		return sendSMSDao;
	}

	public void setSendSMSDao(SendSMSDao sendSMSDao) {
		this.sendSMSDao = sendSMSDao;
	}



	/**
	 * 添加短信内容
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addMessageSMS(String message)
	throws SQLException, DataException {
		Connection conn=connectionManager.getConnection();
		Long result=0L;
		try {
			result=sendSMSDao.addMessageSMS(conn, message);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	
	/**
	 * 更新网短信内容
	 * 
	 * @param conn
	 * @param id
	 * @param sort
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param publisher
	 * @param visits
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateMessageSMS( Long id,String content) throws SQLException, DataException {
		Connection conn=connectionManager.getConnection();
		Long result=0L;
		try {
			  result=sendSMSDao.updateMessageSMS(conn, id, content);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	
	/**
	 * 删除团队信息
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteMessageSMS( Long id) throws SQLException,
	DataException {
		Connection conn=connectionManager.getConnection();
		Long result=0L;
		try {
			result=sendSMSDao.deleteMessageSMS(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return result;
	}
	
	/**
	 * 发送短信，保存短信
	 * @param conn
	 * @param id
	 * @param content
	 * @param cellPhones
	 * @param sendUrl
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws DocumentException 
	 */
	public Long SendSMSs(String content,String splitID,String cellPhones)
	throws SQLException,DataException,IOException, DocumentException{
		Connection conn=connectionManager.getConnection();
		Long result=-1L;
		try {
			result=sendSMSDao.SendSMSs(conn,  content,  splitID, cellPhones);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return result;
	}
	

	
	/**
	 * 获取发送短信详情
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getSendSMSByDetail(Long id)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Map<String, String> map=null;
		try {
		    map=sendSMSDao.getSendSMSByDetail(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return map;
	}
	
	/**
	 * 获取用户列表
	 * @param pageBean
	 * @param userName
	 * @param realName
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserPage(PageBean<Map<String,Object>> pageBean,String userName,String realName,String ids)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		StringBuffer condition=new StringBuffer();
		if(StringUtils.isNotBlank(userName)){
			condition.append("and username LIKE '%");
			condition.append(StringEscapeUtils.escapeSql(userName));
			condition.append("%'");
			
		}
		if(StringUtils.isNotBlank(realName)){
			condition.append("and realname LIKE '%");
			condition.append(StringEscapeUtils.escapeSql(realName));
			condition.append("%'");
		}
		if(StringUtils.isNotBlank(ids)){
			String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String [] array = idStr.split(",");
			for(int n=0;n<=array.length-1;n++){
			   idSQL += ","+array[n];
			}
			condition.append("and id in(");
			condition.append(idSQL);
			condition.append(")");
		}
		
		try {
			dataPage(conn, pageBean, "v_t_user_amountofrecords", "*", "", condition.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	
	/**
	 * 获取短信列表
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void querySendSMSPage(PageBean<Map<String,Object>> pageBean,String beginTime,String endTime)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		StringBuffer condition=new StringBuffer();
		if(StringUtils.isNotBlank(beginTime)){
			condition.append("and sendTime between \'");
			condition.append(StringEscapeUtils.escapeSql(beginTime));
			condition.append("\'  and \'");
			condition.append(StringEscapeUtils.escapeSql(endTime));
			condition.append("\'");
		}
		

		try {
			dataPage(conn, pageBean, "t_sendSMS", "*", "order by sendTime desc", condition.toString());
		} catch (SQLException e) {
			
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	 
}
