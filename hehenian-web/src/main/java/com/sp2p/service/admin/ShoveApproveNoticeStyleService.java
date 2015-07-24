package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.ShoveApproveNoticeStyleDao;


/**
 * 提醒设置service
 * @author C_J
 *
 */
public class ShoveApproveNoticeStyleService extends  BaseService {
	
		private static Log log = LogFactory.getLog(ShoveApproveNoticeStyleService.class);
	
		private ShoveApproveNoticeStyleDao   shoveApproveNoticeStyleDao;

		

		/**
		 * 分页查询
		 * @param conn
		 * @param pageBean
		 * @throws SQLException 
		 * @throws DataException
		 */
		public void queryApproveNoticeStylePageAll(  PageBean<Map<String,Object>>  pageBean ,int notice_style) throws SQLException, DataException{
			Connection conn=MySQL.getConnection();
			try {
				shoveApproveNoticeStyleDao.queryApproveNoticeStylePageAll(conn, pageBean,notice_style);
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				throw e ;
			} catch (DataException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}finally{
				conn.close();
			}
		}

		/**
		 * 根据提醒方式查询
		 * @param conn
		 * @param notice_style
		 * @return
		 * @throws SQLException 
		 * @throws DataException 
		 */
		public List<Map<String,Object>> queryApproveNoticeStyleAll(int notice_style) throws SQLException, DataException{
			Connection conn=MySQL.getConnection();
			List<Map<String,Object>> map=null;
			try {
				map = shoveApproveNoticeStyleDao.queryApproveNoticeStyleAll(conn, notice_style);
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} catch (DataException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}finally{
				conn.close();
			}
			
			return map;
			
		}
		/**
		 * 根据ID 查询
		 * @param conn
		 * @param id
		 * @return
		 * @throws DataException 
		 * @throws SQLException 
		 */
		public Map<String,String> queryApproveNoticeStyleById(int id) throws DataException, SQLException {
			Connection  conn = MySQL.getConnection();
			Map<String,String> map=null;
			try {
				map=shoveApproveNoticeStyleDao.queryApproveNoticeStyleById(conn, id);
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} catch (DataException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}finally{
				conn.close();
			}
			
			return map;
		}
		
		/**
		 * 修改
		 * @param conn
		 * @param id
		 * @param notice_type
		 * @param nid
		 * @param sort
		 * @return
		 * @throws SQLException 
		 * @throws SQLException 
		 */
		public Long updateApproveNoticeStyle(int id,String title,int sort) throws SQLException {
			Connection  conn = MySQL.getConnection();
			long  result = -1L;
			try {
				result = shoveApproveNoticeStyleDao.updateApproveNoticeStyle(conn, id, title,  sort);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				throw e;
			}finally{
				conn.close();
			}
			
			return  result;
		}
		/**
		 * 增加
		 * @param conn
		 * @param id
		 * @param notic_type
		 * @param nid
		 * @param sort
		 * @return
		 * @throws SQLException 
		 * @throws SQLException 
		 */
		public Long addApproveNoticeStyle(int id,int notice_style,String title ,int notic_type,String nid,int sort) throws SQLException {
			Connection  conn = MySQL.getConnection();
			long  result = -1L;
			try {
				result = shoveApproveNoticeStyleDao.addApproveNoticeStyle(conn, id, notice_style, title, notic_type, nid, sort);
			} catch (SQLException e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				throw e;
			}finally{
				conn.close();
			}
			return  result;
		}
		
		
		
		
		
		public void setShoveApproveNoticeStyleDao(
				ShoveApproveNoticeStyleDao shoveApproveNoticeStyleDao) {
			this.shoveApproveNoticeStyleDao = shoveApproveNoticeStyleDao;
		}
		
}
