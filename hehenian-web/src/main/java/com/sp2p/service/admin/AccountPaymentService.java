package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.AccountPaymentDao;


public class AccountPaymentService extends BaseService {
	public  static Log log = LogFactory.getLog(AccountPaymentService.class);
	
	private AccountPaymentDao  accountPaymentDao;
	/**
	 * 增加支付方式
	 * @param conn
	 * @param name
	 * @param nid
	 * @param status
	 * @param litpic
	 * @param style
	 * @param config
	 * @param description
	 * @param order
	 * @return
	 * @throws SQLException 
	 * @throws SQLException 
	 */
		public long  addAccountPayment(String name,String nid ,long status,
						String litpic,int style,String config,String description,int order) throws SQLException{
			Connection  conn  = MySQL.getConnection();
			long result = -1L;
			 try {
				result = accountPaymentDao.addAccountPayment(conn, name, nid, status, litpic, style, config, description, order);
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
		 * 分页查询所有
		 * @param conn
		 * @param pageBean
		 * @throws DataException
		 * @throws SQLException 
		 */
		public void  queryAccountPaymentPage(PageBean<Map<String,Object>>  pageBean) throws DataException, SQLException{
			Connection  conn = MySQL.getConnection();
			try {
				accountPaymentDao.queryAccountPaymentPage(conn, pageBean);
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
		 * 修改
		 * @param conn
		 * @param id
		 * @param name
		 * @param nid
		 * @param status
		 * @param litpic
		 * @param style
		 * @param config
		 * @param description
		 * @param order
		 * @return
		 * @throws SQLException 
		 */
		public long updateAccountPaymentPage(long id,String name,
				String litpic,String config,String description,int order) throws SQLException{
			Connection  conn = MySQL.getConnection();
			long result = -1L;
			  try {
				result = accountPaymentDao.updateAccountPaymentPage(conn, id, name,  litpic,  config, description, order);
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
		public  long deleteAccountPaymentPage(long id,long status) throws SQLException{
			Connection  conn = MySQL.getConnection();
			long result = -1L;
			try {
				result = accountPaymentDao.deleteAccountPaymentPage(conn, id,status);
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
		 * 根据ID 查询
		 * @param conn
		 * @param id
		 * @return
		 * @throws DataException
		 * @throws SQLException 
		 */
		public Map<String,String>  queryAccountPaymentById( String nid) throws DataException, SQLException {
			Connection  conn = MySQL.getConnection();
			Map<String,String>  map = null;
			try {
				map = accountPaymentDao.queryAccountPaymentById(conn,nid);
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
		 * 查询所有支付信息
		 * @return
		 * @throws SQLException
		 * @throws DataException
		 */
		public List<Map<String,Object>>  queryAccountPaymentList() throws SQLException, DataException{
			Connection  conn = MySQL.getConnection();
			List<Map<String,Object>>  mapList =  new ArrayList<Map<String,Object>>();
			try {
				mapList = accountPaymentDao.queryAccountPaymentList(conn);
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

		public void setAccountPaymentDao(AccountPaymentDao accountPaymentDao) {
			this.accountPaymentDao = accountPaymentDao;
		} 
		
		
}
