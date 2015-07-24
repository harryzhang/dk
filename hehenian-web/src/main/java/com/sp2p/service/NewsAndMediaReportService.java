package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.MediaReportDao;
import com.sp2p.dao.admin.NewsDao;

/**
 * 网站公告 和 媒体报道Service
 */
public class NewsAndMediaReportService extends BaseService {
	public static Log log =LogFactory.getLog(NewsAndMediaReportService.class);
	
	private NewsDao newsDao;
	
	private MediaReportDao mediaReportDao;
	
	private ConnectionManager connectionManager;
	
	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public MediaReportDao getMediaReportDao() {
		return mediaReportDao;
	}

	public void setMediaReportDao(MediaReportDao mediaReportDao) {
		this.mediaReportDao = mediaReportDao;
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	
	/**
	 * 添加网站公告信息
	 * @param sort
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param userId
	 * @param visits
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addNews(Integer sort,String title,String content,Long userId,String visits,String publishTime,int type)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Long result=0L;
		try {
			result=newsDao.addNews(conn, sort, title, content, publishTime, userId, visits,type);
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
	 * 修改网站公告信息
	 * @param id
	 * @param sort
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param userId
	 * @param visits
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateNews(Long id,Integer sort,String title,String content,Long userId,Integer visits,String publishTime)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Long result=0L;
		try {
			  result=newsDao.updateNews(conn, id, sort, title, content, publishTime, userId, visits);
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
	 * 删除网站公告 信息
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteNews(Long id)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Long result=0L;
		try {
			result=newsDao.deleteNews(conn, id);
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
	* 删除网站公告介绍（团度介绍）的数据
	* @param commonIds id拼接字符串
	* @param delimiter 分割符
	* @throws DataException
	* @throws SQLException
	* @return int
	*/
	public int deleteNews(String commonIds, String delimiter) throws DataException, SQLException{
		Connection conn = connectionManager.getConnection();
		int result = -1;
		try {
			result =newsDao.deleteNews(conn, commonIds, delimiter);
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
	/**
	 * 根据Id获取团队信息详情
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getNewsById(Long id)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Map<String, String> map=null;
		try {
			map=newsDao.getNewsById(conn, id);
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
	
	public void queryNewsPage(PageBean<Map<String,Object>> pageBean)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_news_list", "*", "order by  publishTime  desc ", "AND state=1");
			
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
	public void frontQueryNewsPage(PageBean<Map<String,Object>> pageBean)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		try {
			//modify by houli  按照后台排序顺序显示
			dataPage(conn, pageBean, "v_t_news_list", "*", " order by publishTime desc ", " AND state=1 ");
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
	 * add by houli 查找表里最大的排列序号
	 * @return
	 * @throws SQLException 
	 * @throws DataException 
	 */
	public Map<String, String> getMaxSerial() throws SQLException, DataException{
		Connection conn=connectionManager.getConnection();
		try {
			return newsDao.getMaxSerial(conn);
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
	 * add by houli 排序处理
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public Long updateNewsIndex(String ids) throws SQLException{
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = newsDao.updateNewsIndex(conn, ids);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	
	/**
	 * add by houli 查看sortid是否存在
	 * @param sortId
	 * @return
	 * @throws SQLException
	 * @throws DataException 
	 */
	public Long isExistSortId(int sortId) throws SQLException, DataException{
		Connection conn=connectionManager.getConnection();
		try {
			Map<String,String> map =  newsDao.isExistSortId(conn, sortId);
			if(map == null || map.get("sort") == null)
				return 1L;
			else
				return -1L;
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	public Long isExistToUpdate(int sortId ,int originalSortId) throws SQLException, DataException{
		Connection conn=connectionManager.getConnection();
		try {
			Map<String,String> map =  newsDao.isExistToUpdate(conn, sortId,originalSortId);
			if(map == null || map.get("sort") == null)
				return 1L;
			else
				return -1L;
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	//以上是网站公告的模块（增删改）
	//以下是媒体报道的模块
	/**
	 * 添加媒体报道信息
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addMediaReport(Integer sort,String title,String source,String url,
			           String imgPath,String content,String publishTime,int state,int stick)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Long result=0L;
		try {
			result=mediaReportDao.addReport(conn, sort, title, source, url, imgPath, content, publishTime,state,stick);
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
	 * 更新媒体报道信息
	 * @param id
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateMediaReport(Long id,Integer sort,String title,String source,String url,
	                       String imgPath,String content,String publishTime,int state,int stick)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Long result=0L;
		try {
			  result=mediaReportDao.updateReport(conn, id, sort, title, source, url, imgPath, content, publishTime,state,stick);
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
	 * 删除媒体报道信息
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteMediaReprot(Long id)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Long result=0L;
		try {
			result=mediaReportDao.deleteReport(conn, id);
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
	* 删除媒体报道的数据
	* @param commonIds id拼接字符串
	* @param delimiter 分割符
	* @throws DataException
	* @throws SQLException
	* @return int
	*/
	public int deleteMediaReport(String commonIds, String delimiter) throws DataException, SQLException{
		Connection conn = connectionManager.getConnection();
		int result = -1;
		try {
			result = mediaReportDao.deleteReport(conn, commonIds, delimiter);
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
	
	/**
	 * 根据Id获取媒体报道详情
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getMediaReportById(Long id)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Map<String, String> map=null;
		try {
			map=mediaReportDao.getReportById(conn, id);
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
	 * 媒体报道分页
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryMediaReportPage(PageBean<Map<String,Object>> pageBean)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " t_mediareport  ", " * ", " order by stick desc,sort desc ", "");
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
	 * 查询媒体报道置顶数据（查询是否顶置）
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryMediaReportPage(PageBean<Map<String,Object>> pageBean,int stick)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		StringBuffer  condition = new StringBuffer();
		if(stick ==2){
			condition.append("  and stick = 2  and state =2 ");
		}
		if(stick ==1){
			condition.append("  and stick = 1  and state =2 ");
		}if(stick!=1 && stick !=2){
			condition.append("");
		}
		try {
			dataPage(conn, pageBean, " t_mediareport  ", " * ", " order by stick desc,sort desc ", condition+"");
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
	 * 修改媒体报道顶置状态，是否顶置
	 * @param id
	 * @param stick
	 * @return 
	 * @throws SQLException
	 * @throws DataException
	 */
	public long  updateReportStick(int id,int stick) throws SQLException{
		Connection  conn = MySQL.getConnection();
		long result = -1L;
		 try {
			result =mediaReportDao.updateReportStick(conn, id, stick);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		
		return result;
	}
	/**
	 * 查询首页滚动图片（页面上显示）是前台还是后台？？？
	 * @return
	 * @author Administrator
	 */
	public List<Map<String, Object>> queryIndexRollImg() throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = mediaReportDao.queryIndexRollImg(conn);
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
		if (list.size()==0) {
			return null;
		}
		return list;
	}
	public List<Map<String, Object>> queryNewsType() throws Exception{
		Connection conn  = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = newsDao.queryNewsType(conn);
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
		if (list.size()==0) {
			return null;
		}
		return list;
	}
}
