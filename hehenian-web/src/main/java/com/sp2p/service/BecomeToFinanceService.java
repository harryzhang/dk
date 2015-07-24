package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.sp2p.dao.BecomeToFinanceDao;

public class BecomeToFinanceService extends BaseService{

	public static Log log = LogFactory.getLog(FinanceService.class);
	
	private BecomeToFinanceDao becomeFinanceDao;

	
	public BecomeToFinanceDao getBecomeFinanceDao() {
		return becomeFinanceDao;
	}

	public void setBecomeFinanceDao(BecomeToFinanceDao becomeFinanceDao) {
		this.becomeFinanceDao = becomeFinanceDao;
	}
	/**
	 * 通过用户ID查询用户信息邮箱，电话
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryFinancer(long userId) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = becomeFinanceDao.queryFinancer(conn, userId, -1, -1);
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
	 * 添加用户信息（用户名、电话等）
	 * @param userId
	 * @param realName
	 * @param cellPhone
	 * @param idNo
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public long addBecomeFinancer(long userId,String realName,String cellPhone,String idNo,int status) throws SQLException{
		Connection conn = MySQL.getConnection();
		
		Long result = -1L;
		try {
			result = becomeFinanceDao.addBecomeFinancer(conn, userId, realName, cellPhone, idNo, status);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
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
}
