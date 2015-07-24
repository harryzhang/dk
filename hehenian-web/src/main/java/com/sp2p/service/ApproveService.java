package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.sp2p.dao.ApproveDao;

public class ApproveService extends BaseService {
	public static Log log = LogFactory.getLog(ApproveService.class);
	private ApproveDao approveDao;

	public void setApproveDao(ApproveDao approveDao) {
		this.approveDao = approveDao;
	}

	/**
	 * 找回交易密码
	 * 
	 * @param email
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> querytrancePassword(String email)
			throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = approveDao.querytrancePassword(conn, email);
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
	 * 修改用户交易密码
	 * 
	 * @param id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserTrancePassword(Long id, String password)
			throws SQLException {
		Connection conn = connectionManager.getConnection();
		long userId = -1;

		try {
			userId = approveDao.updateUserTrancePassword(conn, id, password);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return userId;
	}

}
