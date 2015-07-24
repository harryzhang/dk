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
import com.sp2p.dao.admin.ShoveBorrowAmountTypeDao;

/**
 * 信用额度 service
 * @author C_J
 *
 */
public class ShoveBorrowAmountTypeService extends  BaseService {
	
	public static Log log=LogFactory.getLog(ShoveBorrowAmountTypeService.class);
	
	private ShoveBorrowAmountTypeDao  shoveBorrowAmountTypeDao;
	
	/**
	 * 分页查询所有额度类型
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void  queryBorrowAmountPageAll(PageBean<Map<String,Object>> pageBean) throws SQLException, DataException{
		Connection  conn=MySQL.getConnection();
		try {
			shoveBorrowAmountTypeDao.queryBorrowAmountPageAll(conn, pageBean);
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
	 * 查询所有额度信息
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String,Object>>  queryBorrowAmountAll() throws DataException, SQLException {
		Connection conn=MySQL.getConnection();
		List<Map<String,Object>>  mapList= null;
		try {
			mapList   = shoveBorrowAmountTypeDao.queryBorrowAmountAll(conn);
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
		
		return mapList;
	}
	

	/**
	 * 根据 ID 查询额度信息
	 * @param id
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String>  queryBorrowAmountById(int id ) throws SQLException, DataException {
		 Connection  conn=MySQL.getConnection();
		 Map<String,String> map =null;
		 try {
			map = shoveBorrowAmountTypeDao.queryBorrowAmountById(conn, id);
		} catch (SQLException e) {
			conn.rollback();
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
	 * 增加信用额度
	 * @param conn
	 * @param name
	 * @param nid
	 * @param once_status
	 * @param title
	 * @param descriptionm
	 * @param status
	 * @param remark
	 * @param init_credit
	 * @return
	 * @throws SQLException
	 */
	public Long  addBorrowAmount(String name,String nid,int once_status,String title,String descriptionm,
								int status,String remark,String init_credit) throws SQLException{
			Connection  conn=MySQL.getConnection();
			long result = -1L;
			try {
				result = shoveBorrowAmountTypeDao.addBorrowAmount(conn, name, nid, once_status, title, descriptionm, status, remark, init_credit);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				log.error(e);
				e.printStackTrace();
				throw e;
			}finally{
				conn.close();
			}
			
			return result;
	 }
	
	/**
	 * 修改额度类型
	 * @param conn
	 * @param id
	 * @param name
	 * @param nid
	 * @param once_status
	 * @param title
	 * @param descriptionm
	 * @param status
	 * @param remark
	 * @param init_credit
	 * @return
	 * @throws SQLException 
	 * @throws SQLException
	 */
	public Long  updateBorrowAmount(int id,String title,String descriptionm,int status,String remark,double init_credit) throws SQLException {
			Connection  conn= MySQL.getConnection();
			long result = -1L;
			try {
				result = shoveBorrowAmountTypeDao.updateBorrowAmount(conn, id,title, descriptionm, status, remark,init_credit);
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				log.error(e);
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
	public  Long deleteBorrowAmount(int id) throws SQLException {
		Connection  conn= MySQL.getConnection();
		long  result = -1L;
		try {
			result = shoveBorrowAmountTypeDao.deleteBorrowAmount(conn, id);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return result;
	}

	public void setShoveBorrowAmountTypeDao(
			ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao) {
		this.shoveBorrowAmountTypeDao = shoveBorrowAmountTypeDao;
	}

	
	
	
}
