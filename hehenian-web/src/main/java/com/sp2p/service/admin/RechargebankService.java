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
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.RechargebankDao;
/**
 * 线下充值银行编辑
 * @author Administrator
 *
 */
public class RechargebankService extends BaseService{
  private RechargebankDao rechargebankDao;
  public static Log log = LogFactory.getLog(RechargebankService.class);

  
  
  
	public Map<String, String> queryrechargeBankById(Long id)throws SQLException {
			Map<String, String> map = new HashMap<String, String>();
			Connection conn = connectionManager.getConnection();
			try {
				map = rechargebankDao.queryrechargeBankById(conn, id);
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
			} catch (DataException e) {
				log.error(e);
				e.printStackTrace();
			} finally {
				conn.close();
			}
			return map;
	}
	
	public Map<String, String> queryFundRecordTypeAmount(long userId,String startTime,
			String endTime,Map<String,String> typeMap)throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = connectionManager.getConnection();
		try {
			map = rechargebankDao.queryFundRecordTypeAmount(conn, userId, startTime, endTime, typeMap);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
}
	
	
	
	public Map<String, String> queryrechargeBank()throws SQLException {
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = connectionManager.getConnection();
		try {
			map = rechargebankDao.queryrechargeBank(conn);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
}
	
	public long updaterechargeBankById(long id,String bankname,String Account,String accountbank,String bankimage,String accountname)throws SQLException {
		long result = -1;
		Connection conn = connectionManager.getConnection();
		try {
			result = rechargebankDao.updaterechargeBankById(conn, id, bankname, Account, accountbank, bankimage,accountname);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
}
	
	public long addRechargeBankInit(String bankname,String Account,String accountbank,String bankimage,String accountname)throws SQLException {
		long result = -1;
		Connection conn = connectionManager.getConnection();
		try {
			result = rechargebankDao.addRechargeBankInit(conn, bankname, Account, accountbank, bankimage,accountname);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
	
	
	
	public List<Map<String, Object>> queryFundRecordType()throws SQLException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			try {
				list = rechargebankDao.queryFundRecordType(conn);
			} catch (DataException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return list;
}
	public List<Map<String, Object>> queryrechargeBanklist()throws SQLException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			try {
				list = rechargebankDao.queryrechargeBanklist(conn);
			} catch (DataException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return list;
}
	
	
	
	
  
  
	public void queryRechargebanklist(PageBean<Map<String, Object>> pageBean) throws SQLException {
		StringBuffer condition = new StringBuffer();
/*
		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND uername  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(username.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(starttime)) {
			condition.append(" and applyTime >= '" + StringEscapeUtils.escapeSql(starttime.trim()) + "'");
		}
		if (StringUtils.isNotBlank(endTime)) {
			condition.append(" and applyTime <= '" + StringEscapeUtils.escapeSql(endTime.trim()) + "'");
		}
		if (autiStatus != null && autiStatus != -1) {
			condition.append(" AND applystatus = " + autiStatus);
		}
		*/
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "t_rechargebank", "*",
					"", condition
							.toString());
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}
  
  
  
  
  
public void setRechargebankDao(RechargebankDao rechargebankDao) {
	this.rechargebankDao = rechargebankDao;
}
}
