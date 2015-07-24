package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.dao.admin.CountWorkStatusDao;
import com.shove.base.BaseService;
import com.shove.data.DataException;

public class CountWorkStatusService extends BaseService{
	public static Log log =LogFactory.getLog(CountWorkStatusService.class);
	private CountWorkStatusDao countWorkStatusDao;
	public void setCountWorkStatusDao(CountWorkStatusDao countWorkStatusDao) {
		this.countWorkStatusDao = countWorkStatusDao;
	}
	
 public Map<String, String> queryC(Long userId,Integer type) throws SQLException, DataException {
	Connection conn = connectionManager.getConnection();

	Map<String, String> map = new HashMap<String, String>();
	try {
		map = countWorkStatusDao.queryC(conn, userId, type);
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
	
	
}
