package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.ShoveBorrowStyleDao;
import com.sp2p.dao.admin.ShoveBorrowTypeDao;

/**
 * 还款方式 service
 * 
 * @author C_J
 * 
 */
public class ShoveBorrowStyleService extends BaseService {
	public static Log log = LogFactory.getLog(ShoveBorrowStyleService.class);

	private ShoveBorrowStyleDao shoveBorrowStyleDao;
	private ShoveBorrowTypeDao shoveBorrowTypeDao;

	/**
	 * 分页查询
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryBorrowStylePageAll(PageBean<Map<String, Object>> pageBean)
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		try {
			shoveBorrowStyleDao.queryBorrowStylePageAll(conn, pageBean);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close(); // 关闭连接
		}

	}

	/**
	 * 根据 ID 查询还款方式
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryShoveBorrowStyleById(int id)
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = shoveBorrowStyleDao.queryBorrowStylById(conn, id);
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
	 * 根据借款类型的标识符查询还款方式
	 * 
	 * @param nid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryShoveBorrowStyleByTypeNid(String nid)
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> mapList = null;
		try {
			String styles = shoveBorrowTypeDao.queryOneByNid(conn, "styles", nid);
			if(styles!=null && styles.length() > 0){
				mapList = shoveBorrowStyleDao.queryShoveBorrowStyleByTypeNid(conn,
						styles);
			}else{
				mapList = new ArrayList<Map<String,Object>>();
			}
			
		} catch (SQLException e) {
			log.error(e);
			throw e;
		} catch (DataException e) {
			log.error(e);
			throw e;
		} finally {
			conn.close();
		}

		return mapList;
	}
	/**
	 * 根据借款类型 查询 2机构担保   3和反担保方式  
	 * 
	 * @param nid
	 * @return
	 * @throws SQLException 
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String>  querySlectStyleByTypeNid(String nid,int types) throws SQLException{
		Map<String, String> mapList = null;
		String styles = "";
		Connection conn = MySQL.getConnection();
		try {
			if(types==3){
				//反担保方式
				styles = shoveBorrowTypeDao.queryOneByNid(conn, "counter_guarantee", nid);
			}
			if(types==2){
				//担保机构
				styles = shoveBorrowTypeDao.queryOneByNid(conn, "institution", nid);
			}
			if(styles!=null && styles.length() > 0){
				mapList = shoveBorrowStyleDao.querySelectDanbyTypenid(conn,
						styles,types);
			}else{
				mapList = new HashMap<String, String>();
			}
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return mapList;
	}
	
	/**
	 * 增加
	 * 
	 * @param nid
	 * @param status
	 * @param name
	 * @param title
	 * @param contents
	 * @param remark
	 * @param sort
	 * @return
	 * @throws SQLException
	 */
	public long addShoveBorrowStyle(String nid, int status, String name,
			String title, String contents, String remark, int sort)
			throws SQLException {
		Connection conn = MySQL.getConnection();

		long result = -1L;
		try {
			result = shoveBorrowStyleDao.addShoveBorrowStyle(conn, nid, status,
					name, title, contents, remark, sort);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 修改还款方式
	 * 
	 * @param conn
	 * @param id
	 * @param status
	 * @param title
	 * @param contents
	 * @param remark
	 * @param sort
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 */
	public long updateShoveBorrowStyle(int id, int status, String title,
			String contents, String remark, int sort) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = shoveBorrowStyleDao.updateShoveBorrowStyle(conn, id,
					status, title, contents, remark, sort);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 删除还款方式
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 */
	public long deleteShoveBorrowStyle(int id) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = shoveBorrowStyleDao.deleteShoveBorrowStyle(conn, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 查询所有还款方式
	 * 
	 * @param conn
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryBorrowAll() throws DataException,
			SQLException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> mapList = null;
		try {
			mapList = shoveBorrowStyleDao.queryBorrowAll(conn);
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

		return mapList;

	}

	public List<Map<String, Object>> queryBorrowAmountByIds(String ids)
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = null;
		try {
			list = shoveBorrowStyleDao.queryBorrowAmountNamesByIds(conn, ids);
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return list;

	}

	public void setShoveBorrowStyleDao(ShoveBorrowStyleDao shoveBorrowStyleDao) {
		this.shoveBorrowStyleDao = shoveBorrowStyleDao;
	}

	public void setShoveBorrowTypeDao(ShoveBorrowTypeDao shoveBorrowTypeDao) {
		this.shoveBorrowTypeDao = shoveBorrowTypeDao;
	}

}
