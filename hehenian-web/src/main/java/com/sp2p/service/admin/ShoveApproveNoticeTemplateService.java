package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.ShoveApproveNoticeTemplateDao;


/**
 *  提醒记录   service
 * @author C_J
 *
 */
public class ShoveApproveNoticeTemplateService extends BaseService {

	private static Log log = LogFactory.getLog(ShoveApproveNoticeTemplateService.class);
	
	private ShoveApproveNoticeTemplateDao  shoveApproveNoticeTemplateDao ;
	
	
	/**
	 * 增加记录
	 * @param conn
	 * @param notice_id
	 * @param name
	 * @param template
	 * @param nid
	 * @return
	 * @throws SQLException 
	 */
	public Long addApproveNoticeTemplate(int notice_id ,String name,String template,String nid  ) throws SQLException{
		Connection  conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = shoveApproveNoticeTemplateDao.addApproveNoticeTemplate(conn, notice_id, name, template, nid);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param conn
	 * @param id
	 * @param notice_id
	 * @param name
	 * @param template  模板
	 * @param nid
	 * @return
	 * @throws SQLException 
	 */
	public Long updateApproveNoticeTemplate(int id,int notice_id ,String name,String template,int sort ) throws SQLException {
		Connection  conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = shoveApproveNoticeTemplateDao.updateApproveNoticeTemplate(conn, id, notice_id, name, template,sort);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
		
	}
	
	/**
	 * 删除
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException 
	 * @throws SQLException 
	 */
	public Long deleteApproveNoticeTemplate(int id) throws SQLException{
		Connection  conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = shoveApproveNoticeTemplateDao.deleteApproveNoticeTemplate(conn, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}
	
	
	/**
	 * 分页查询
	 * @param conn
	 * @param pageBean
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public void queryApproveTemplatePageAll(PageBean<Map<String,Object>>  pageBean) throws DataException, SQLException {
		Connection  conn = MySQL.getConnection();
		try {
			shoveApproveNoticeTemplateDao.queryApproveTemplatePageAll(conn, pageBean);
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
	}
	
	/**
	 * 根据ID 查询
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException 
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws DataException 
	 */
	public Map<String,String> queryApproveTemplateById(int id) throws SQLException, DataException{
		Connection  conn= MySQL.getConnection();
		Map<String,String>  map= null;
		try {
			map = shoveApproveNoticeTemplateDao.queryApproveTemplateById(conn, id);
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
	
	/**'
	 *  根据类型查询所有提醒类型  
	 * @param pageBean
	 * @param notice_id
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryOrderRechargeRecords(PageBean<Map<String,Object>> pageBean,int notice_style,int id,int sid) throws SQLException, DataException{
		StringBuffer table = new StringBuffer();
		table.append(" t_approve_notice_template as t inner  join t_approve_notice_style as s ");
		table.append(" on t.notice_id = s.id  ");
		
		StringBuffer filed = new StringBuffer();
		filed.append(" and s.notice_style = " + notice_style);
		if(sid == 0){
			filed.append(" and s.id = "+id);
		}
		else{
			filed.append(" and t.id = "+id);
		}
		
		
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, table.toString(), " t.id,t.name,t.template,t.nid,t.sort,s.title,t.notice_id ,s.notice_style ,s.id as sid  ", "  order by  t.sort asc ", filed.toString() );
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
	}
	
	public List<Map<String, Object>> queryAllInformTemplate() throws SQLException, DataException{
		List<Map<String, Object>> list = null;
		Connection conn = Database.getConnection();
	    try{
	    	list =shoveApproveNoticeTemplateDao.queryAllInformTemplate(conn);
	    	conn.commit();
	    }finally{
	    	conn.close();
	    }	
	    return list;
	}
	

	public void setShoveApproveNoticeTemplateDao(
			ShoveApproveNoticeTemplateDao shoveApproveNoticeTemplateDao) {
		this.shoveApproveNoticeTemplateDao = shoveApproveNoticeTemplateDao;
	}

	public ShoveApproveNoticeTemplateDao getShoveApproveNoticeTemplateDao() {
		return shoveApproveNoticeTemplateDao;
	}
	
}
