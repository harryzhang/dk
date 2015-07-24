package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.admin.AutomaticBidDao;

/**
 * 自动投标
 * @author zhuchao
 *
 */
public class AutomaticBidService extends BaseService {
	public static Log log = LogFactory.getLog(AutomaticBidService.class);
	private AutomaticBidDao automaticBidDao;
	private OperationLogDao operationLogDao;
	
	/**
	 * 自动投标列表展示
	 * @param pageBean
	 * @param username
	 * @param bidSetTime
	 * @throws SQLException
	 */
	public void automaticBidList(PageBean<Map<String, Object>> pageBean, String username, String bidSetTime)
	throws SQLException {
		StringBuffer condition = new StringBuffer();
		
		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND username LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(username.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(bidSetTime)) {
			condition.append(" and bidSetTime >= '" + StringEscapeUtils.escapeSql(bidSetTime) + "'");
		}
		
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_automaticbid", "*", " ORDER BY bidSetTime", condition.toString());
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

	/**
	 * 根据id查询自动投标记录
	 * @param id
	 * @return
	 * @throws SQLException 
	 * @throws DataException 
	 */
	public Map<String, String> queryAutomaticBidById(int id) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = automaticBidDao.queryBorrowStylById(conn, id);
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
	 * 关闭用户自动投标
	 * @param username
	 * @param lastIp
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public long closeAutomaticBid(String username, String lastIp, String ids) throws Exception {
		Connection conn=MySQL.getConnection();
		Long result = -1L;
		try {
			result=automaticBidDao.closeAutomaticBid(conn,ids);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}else{
				operationLogDao.addOperationLog(conn, "t_automaticbid", username, IConstants.UPDATE, lastIp, 0, "关闭自动投标", 2);
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		
		return result;
	}
	
	public void setAutomaticBidDao(AutomaticBidDao automaticBidDao) {
		this.automaticBidDao = automaticBidDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}
}
