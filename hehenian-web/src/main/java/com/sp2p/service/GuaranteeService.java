package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.GuaranteeDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.UserDao;

public class GuaranteeService extends BaseService {
	public static Log log = LogFactory.getLog(GuaranteeService.class);
	private GuaranteeDao guaranteeDao;
	private UserDao userDao;
	private OperationLogDao operationLogDao;

	public void setGuaranteeDao(GuaranteeDao guaranteeDao) {
		this.guaranteeDao = guaranteeDao;
	}

	/**
	 * 查询个人的详细信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryUserInformation(Long userId) throws SQLException {
		Map<String, String> map = null;
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId >= -1)
			condition.append(" AND id = " + userId);
		Connection conn = connectionManager.getConnection();
		try {
			map = guaranteeDao.queryUserInformation(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询用户的信用分数详情
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryPerUserCreditfornt(Long userId) throws SQLException {
		Map<String, String> map = null;
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId >= -1)
			condition.append(" AND id = " + userId);
		Connection conn = connectionManager.getConnection();
		try {
			map = guaranteeDao.queryPerUserCreditfornt(conn, userId);
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

	/**
	 * 查询个人的信用总分
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryUserCredit(Long userId) throws SQLException {
		Map<String, String> map = null;
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId >= -1) {
			condition.append(" AND id = " + userId);
		}

		Connection conn = connectionManager.getConnection();
		try {
			map = guaranteeDao.queryUserCredit(conn, userId);

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

	/**
	 * 查询用户的vip图片
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryUserVipPicture(Long userId) throws SQLException {
		Map<String, String> map = null;
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId >= -1) {
			condition.append(" AND id = " + userId);
		}

		Connection conn = connectionManager.getConnection();
		try {
			map = guaranteeDao.queryUserVipPicture(conn, userId);

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

	/**
	 * 动态显示用的信用图标
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryUserCriditPicture(Long userId) throws SQLException {
		Map<String, String> map = null;
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId >= -1) {
			condition.append(" AND id = " + userId);
		}

		Connection conn = connectionManager.getConnection();
		try {
			map = guaranteeDao.queryUserCriditPicture(conn, userId);

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

	/**
	 * 查询用户的好友列表
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public void queryUserFriends(PageBean<Map<String, Object>> pageBean, Long userId) throws SQLException, Exception {
		Connection conn = connectionManager.getConnection();
		StringBuffer sqlresult = new StringBuffer();
		sqlresult.append(" tc.moduleId as tcmoduleId, ");
		sqlresult.append(" tuser.username as username, ");
		sqlresult.append(" tuser1.username as aausername, ");
		sqlresult.append(" tc.userId as id, ");
		sqlresult.append(" tpp.personalHead as personalHead ");

		// ====================
		StringBuffer sql = new StringBuffer();
		sql.append(" t_concern tc ");
		sql.append(" left join t_user tuser on tuser.id = tc.userId ");
		sql.append(" left join t_user tuser1 on tc.moduleId = tuser1.id  ");
		sql.append(" left join t_person tp on tp.userId = tc.userId  ");
		sql.append(" left join t_person tpp on tc.moduleId = tpp.userId  ");
		// ====================
		try {
			dataPage(conn, pageBean, sql.toString(), sqlresult.toString(), "", " AND tc.moduleType = 1 AND tc.userId = " + userId);

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
	}

	/**
	 * 删除好友关注
	 * 
	 * @param attentionUserId
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long DeteleUserFriends(Long attentionUserId, Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long reuslt = -1L;
		try {
			reuslt = guaranteeDao.DeteleUserFriends(conn, attentionUserId, userId);
			userMap = userDao.queryUserById(conn, userId);
			// 添加操作日志
			operationLogDao.addOperationLog(conn, "t_concern", Convert.strToStr(userMap.get("username"), ""), IConstants.DELETE,
					Convert.strToStr(userMap.get("lastIP"), ""), 0, "删除关注好友", 1);
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

		return reuslt;

	}

	/**
	 * 查询个人统计 借款记录
	 */
	public Map<String, String> queryUserBorrowRecode(Long userId) throws SQLException, Exception {
		Connection conn = connectionManager.getConnection();

		try {
			return guaranteeDao.queryUserBorrowRecode(conn, userId);

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
	}

	/**
	 * 查询个人统计 投标记录
	 */
	public Map<String, String> queryUserInerseRecode(Long userId) throws SQLException, Exception {
		Connection conn = connectionManager.getConnection();
		try {
			return guaranteeDao.queryUserInerseRecode(conn, userId);

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
	}

	/**
	 * 查询用户的提前还款的统计15内 和分数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver15(Long userId) throws SQLException, Exception {
		Connection conn = connectionManager.getConnection();
		try {
			return guaranteeDao.queryUserBorrowAndInver15(conn, userId);

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
	}

	/**
	 * 查询用户的提前还款的统计 和分数16天以上
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver16(Long userId) throws SQLException, Exception {
		Connection conn = connectionManager.getConnection();
		try {
			return guaranteeDao.queryUserBorrowAndInver16(conn, userId);

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
	}

	/**
	 * 查询用户的逾期还款的统计 和分数1-10天以上
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver10(Long userId) throws SQLException, Exception {
		Connection conn = connectionManager.getConnection();
		try {
			return guaranteeDao.queryUserBorrowAndInver10(conn, userId);

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
	}

	/**
	 * 查询用户的逾期还款的统计 和分数 大于11-30天的还款统计和分数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver30(Long userId) throws SQLException, Exception {
		Connection conn = connectionManager.getConnection();
		try {
			return guaranteeDao.queryUserBorrowAndInver30(conn, userId);

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
	}

	/**
	 * 查询用户的逾期还款的统计 和分数 大于31-90天的还款统计和分数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver90(Long userId) throws SQLException, Exception {
		Connection conn = connectionManager.getConnection();
		try {
			return guaranteeDao.queryUserBorrowAndInver90(conn, userId);

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
	}

	/**
	 * 查询用户的逾期还款的统计 和分数 大于90天以上的还款统计和分数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserBorrowAndInver90up(Long userId) throws SQLException, Exception {
		Connection conn = connectionManager.getConnection();
		try {
			return guaranteeDao.queryUserBorrowAndInver90up(conn, userId);

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
	}

	/**
	 * 查询个人的借款列表
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryMyBorrowList(PageBean<Map<String, Object>> pageBean, Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_user_myborrowlist", "*", "", "  AND uid = " + userId);
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
	}

	/**
	 * 查询个人的投资记录
	 * 
	 * @param pageBean
	 * @param userId
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryMyborrowRecorde(PageBean<Map<String, Object>> pageBean, Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_user_myborrowrecorde", "*", "", "  AND uid = " + userId);
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
	}

	/**
	 * 查询用户的动态
	 * 
	 * @param pageBean
	 * @param userId
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserRecore(PageBean<Map<String, Object>> pageBean, Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId != -1) {
			condition.append("  AND userId = " + userId);
		}
		try {
			dataPage(conn, pageBean, "t_user_recorelist", "*", " order by id", condition.toString());
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
	}

	/**
	 * 查询用户的好友的动态
	 * 
	 * @param pageBean
	 * @param userId
	 *            用户id
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryfrendsRecore(PageBean<Map<String, Object>> pageBean, Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId != -1) {
			condition.append("  AND tc.userId = " + userId);
		}
		// ======
		StringBuffer resulSQl = new StringBuffer();
		resulSQl.append(" tur.title as title,");
		resulSQl.append(" tur.time as time, ");
		resulSQl.append(" tur.url as url ");
		// ======
		StringBuffer sql = new StringBuffer();
		sql.append(" t_user_recorelist tur ");
		sql.append(" left join  t_concern tc on tur.userId = tc.moduleId ");
		// =--=-=-=

		try {
			dataPage(conn, pageBean, sql.toString(), resulSQl.toString(), " order by tur.time", condition.toString());
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
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	/**
	 * 合和年查询 信用积分
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryPerUserCreditforntHHN(Long userId) throws SQLException {
		
		List<Map<String, Object>> map = null;
		StringBuffer condition = new StringBuffer();
		if (userId != null && userId >= -1)
			condition.append(" AND id = " + userId);
		Connection conn = MySQL.getConnection();
		try {
			map = guaranteeDao.queryPerUserCreditforntHHN(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}
}
