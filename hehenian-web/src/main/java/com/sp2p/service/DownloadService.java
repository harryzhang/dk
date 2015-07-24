package com.sp2p.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JSpinner.DateEditor;

import com.shove.base.BaseService;

import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;

import com.shove.vo.PageBean;
import com.sp2p.dao.DownloadDao;


import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DownloadService extends BaseService {
	public static Log log=LogFactory.getLog(DownloadService.class);
	
	private DownloadDao downloadDao;
	
	private ConnectionManager connectionManager;
	
	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public DownloadDao getDownloadDao() {
		return downloadDao;
	}

	public void setDownloadDao(DownloadDao downloadDao) {
		this.downloadDao = downloadDao;
	}
	
	/**
	 * 添加下载资料
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param publisher
	 * @param visits
	 * @param state
	 * @param seqNum
	 * @param attachment
	 * @return
	 * @throws SQLException
	 */
	public Long addDownload(String title,String content,String publishTime,Long userId
			,Integer visits,Integer state,Integer seqNum,String attachment)throws SQLException,DataException{
		
		Connection conn =connectionManager.getConnection();
		Long downloadId=0L;
		try {
			downloadId=downloadDao.addDownload(conn, title, content, publishTime, userId, visits, state, seqNum, attachment);
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return downloadId;
		
	}
	
	/**
	 * 更新下载资料
	 * @param id
	 * @param title
	 * @param publishTime
	 * @param state
	 * @param seqNum
	 * @param attachment
	 * @return
	 * @throws SQLException
	 */
	public Long updateDownload(Long id,String title,String content,Long userId,Integer visits,Integer state,
			Integer seqNum,String publishTime,String attachment)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Long downloadId=1L;
		try {
			downloadId=downloadDao.UpdateDownload(conn, id, title,content,userId,visits, state, seqNum,publishTime, attachment);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return downloadId;
	}
	
	public Long deleteDownload(Long id)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Long downloadId=0L;
		try {
			downloadId=downloadDao.deleteDownload(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			
		} catch(DataException e){
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return downloadId;
	}
	
	public Map<String,String> queryDownloadById(Long id)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Map<String ,String> map=null;
		try {
			map=downloadDao.getDownloadById(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
			
		catch(DataException e){
			log.error(e);
			e.printStackTrace();
		}finally{
			
		  conn.close();
		}
		return map;	
		
	}
	
	public Map<String,String> frontDownloadById(Long id)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Map<String ,String> map=null;
		try {
			map=downloadDao.frontGetDownloadById(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
			
		catch(DataException e){
			log.error(e);
			e.printStackTrace();
		}finally{
			
		  conn.close();
		}
		return map;	
	}
	
	
	public void queryDownloadPage(PageBean<Map<String,Object>> pageBean,String title)throws SQLException, DataException{
		StringBuffer buffer=new StringBuffer();
		
		if(StringUtils.isNotBlank(title)){
			buffer.append(" AND title LIKE \'%"+StringEscapeUtils.escapeSql(title)+"%\'");
		}
		Connection conn=connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_download_list", "*", "order by publishTime desc", buffer.toString());
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
