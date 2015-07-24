package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.dao.admin.EmalAndMessageDao;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;

public class EmalAndMessageService  extends BaseService{
	public static Log log = LogFactory.getLog(EmalAndMessageService.class);
	@SuppressWarnings("unused")
	private EmalAndMessageDao emalAndMessageDao;
	public void setEmalAndMessageDao(EmalAndMessageDao emalAndMessageDao) {
		this.emalAndMessageDao = emalAndMessageDao;
	}
	/**
	 * 插入邮件设置表
	 * @param mailaddress
	 * @param mailpassword
	 * @param sendmail
	 * @param sendname
	 * @return
	 * @throws SQLException
	 * @throws DataException 
	 */
	public Long addUserWorkData(String mailaddress,String mailpassword,String sendmail,String sendname,String port,String host) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.addMailSet(conn, mailaddress, mailpassword, sendmail, sendname, port, host);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 添加或者修改短信设置
	 * @param id
	 * @param username
	 * @param password
	 * @param url
	 * @param enable
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addMessageSet(Long id ,String username,String password,String url,Integer enable) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.addMessageSet(conn, id, username, password, url, enable);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	
	
	
	public Long addTarage(String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.addTarage(conn, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 增加担保方式
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addDan(String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.addDan(conn, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 增加反担保机构
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addInver(String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.addInver(conn, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 添加系统头像
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addSysImage(String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.addSysImage(conn, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	
	
	
	
	public Long updateTage(Long id,String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.updateTage(conn, id, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 修改担保机构
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateAccount(Long id,String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.updateAccount(conn, id, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 更改系统头像
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateSysImage(Long id,String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.updateSysImage(conn, id, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	
	
	/**
	 * 修改投标金额
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateInvers(Long id,String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.updateAccount(conn, id, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 修改借款期限
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateDeadline(Long id,String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.updateDeadline(conn, id, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public Long deleteTage(Long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.deleteTage(conn, id);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 删除反担保fangsh
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteacc(Long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.deleteacc(conn, id);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 删除系统头像
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deletSImage(Long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.deletSImage(conn, id);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	
	
	/**
	 *  查询信息设置
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryMessageSet(long id) throws DataException,
	SQLException {
Connection conn = connectionManager.getConnection();

Map<String, String> map = new HashMap<String, String>();
try {
	map = emalAndMessageDao.queryMessageSet(conn, id);
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
	/**
	 * 得到当前最大的ID
	 * @param conn
	 * @return
	 * @throws SQLException 
	 */
	public  Map<String,String> queryMailsetMaxId() throws SQLException{
		Connection  conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map= emalAndMessageDao.queryMailsetMaxId(conn);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return map;
	}
	
	public Map<String, String> queryMailSet(long id) throws DataException,
	SQLException {
		Connection conn = connectionManager.getConnection();
		
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = emalAndMessageDao.queryMailSet(conn, id);
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
			
	/**
	 * 查询借款目的内容
	 * @param pageBean
	 * @param typeId
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>>  querySelectInfo( PageBean<Map<String,Object>> pageBean) throws SQLException{
		List<Map<String,Object>>  map = null;
		Connection conn = connectionManager.getConnection();
		StringBuffer condition  = new StringBuffer();
		condition.append(" AND ts.typeId = 1 AND ts.deleted = 1");
	/*	if(userId!=null&&userId>0){
			condition.append(" AND id = "+userId);
		}*/
		//=============================
		StringBuffer sqlresult = new StringBuffer();
		sqlresult.append(" ts.id as 'id' , ");
		sqlresult.append(" ts.selectName as 'name' ");
		//==========================
		StringBuffer sql = new StringBuffer();
		sql.append(" t_select ts ");
		//================================
		try {
			dataPage(conn, pageBean, sql.toString(), sqlresult.toString(), " order by id ", condition.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return map;
	}
	
	
	
	/**
	 * 查询反担保方式
	 * @param pageBean
	 * @param typeId
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>>  queryIversInof( PageBean<Map<String,Object>> pageBean) throws SQLException{
		List<Map<String,Object>>  map = null;
		Connection conn = connectionManager.getConnection();
		StringBuffer condition  = new StringBuffer();
		condition.append(" AND ts.typeId = 3");
		StringBuffer sql = new StringBuffer();
		sql.append(" t_select ts ");
		try {
			dataPage(conn, pageBean, sql.toString(), " * ", " order by id ", condition.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return map;
	}
	/**
	 * 查询系统头像
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>>  querySysImageInfo( PageBean<Map<String,Object>> pageBean) throws SQLException{
		List<Map<String,Object>>  map = null;
		Connection conn = connectionManager.getConnection();
		StringBuffer condition  = new StringBuffer();
		condition.append(" AND ts.enable = 1 ");
	/*	if(userId!=null&&userId>0){
			condition.append(" AND id = "+userId);
		}*/
		//=============================
		StringBuffer sqlresult = new StringBuffer();
		sqlresult.append(" ts.id as id , ");
		sqlresult.append(" ts.imagePath as imagePath ");
		//==========================
		StringBuffer sql = new StringBuffer();
		sql.append(" t_sysImages ts ");
		//================================
		try {
			dataPage(conn, pageBean, sql.toString(), sqlresult.toString(), " order by id ", condition.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return map;
	}
	
	
	
	
	
	
	
	
	/**
	 * 查询机构担保列表
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>>  queryAccountInfo( PageBean<Map<String,Object>> pageBean) throws SQLException{
		List<Map<String,Object>>  map = null;
		Connection conn = connectionManager.getConnection();
		StringBuffer condition  = new StringBuffer();
		condition.append(" AND typeId = 2 ");
		try {
			dataPage(conn, pageBean," t_select ", " * ", " order by id ", condition.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return map;
	}
	
	/**
	 *  修改
	 * @param type
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public long updateSelectType(int type,long id) throws SQLException {
		Connection  conn = MySQL.getConnection();
		long result = -1L;
		 try {
			result = emalAndMessageDao.updateSelectType(conn, type, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return result ;
	}
	
	/**
	 * 查询所有担保机构
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>>  queryinstitution() throws SQLException, DataException{
		List<Map<String,Object>>  map = null;
		Connection  conn  =connectionManager.getConnection();
		try {
			map = emalAndMessageDao.queryinstitution(conn);
		} catch (SQLException e) {
			log.equals(e);
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
	 * 查询所有反担保方式   
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>>  queryguarantee() throws SQLException, DataException{
		
		List<Map<String,Object>> map = null;
		
		Connection  conn = connectionManager.getConnection();
		try {
			map = emalAndMessageDao.queryguarantee(conn);
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
	 * 查询借款期限列表
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>>  queryDeadlineInfo( PageBean<Map<String,Object>> pageBean) throws SQLException{
		List<Map<String,Object>>  map = null;
		Connection conn = connectionManager.getConnection();
		StringBuffer condition  = new StringBuffer();
		condition.append(" AND typeId = 4 ");
		try {
			dataPage(conn, pageBean," t_select ", " * ", " order by selectValue ", condition.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return map;
	}
	
	/**
	 * 查询金额范围列表
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String,Object>>  queryMomeyInfo( PageBean<Map<String,Object>> pageBean) throws SQLException{
		List<Map<String,Object>>  map = null;
		Connection conn = connectionManager.getConnection();
		StringBuffer condition  = new StringBuffer();
		condition.append(" AND typeId = 5 ");
		try {
			dataPage(conn, pageBean," t_select ", " * ", " order by selectValue ", condition.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return map;
	}
	/**
	 * 修改金额范围  和借款期限
	 * @param id
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateMoney(Long id,String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
		 return  emalAndMessageDao.updateMoney(conn, id, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 增加借款期限和金额范围
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addDeadline(String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.addDeadline(conn, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 增加借款期限和金额范围
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addMoney(String name) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Long result = -1L;
		try {
		 return  emalAndMessageDao.addMomey(conn, name);
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
}
