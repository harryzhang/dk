package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.GroupDao;
import com.sp2p.dao.admin.GroupUserDao;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;

public class GroupService extends BaseService {
	public static Log log = LogFactory.getLog(GroupService.class);

	private GroupDao groupDao;
	private GroupUserDao groupUserDao;
	private UserDao userDao;

	/**
	 * 添加用户组
	 * 
	 * @param adminId
	 *            添加人
	 * @param groupName
	 *            用户组名
	 * @param groupRemark
	 *            用户组备注
	 * @return
	 * @throws SQLException
	 */
	public long addGroup(long adminId, List<Long> userIds, String groupName,
			String groupRemark,int cashStatus) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = groupDao.addGroup(conn, adminId, groupName, groupRemark,cashStatus);
			for (Long userId : userIds) {
				groupUserDao.addGroupUser(conn, userId, result);
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 修改用户组
	 * 
	 * @param id
	 *            用户组ID
	 * @param adminId
	 *            添加人
	 * @param groupName
	 *            用户组名
	 * @param groupRemark
	 *            用户组备注
	 * @param cashStatus
	 *            提现状态
	 * @return
	 * @throws SQLException
	 */
	public long updateGroup(long id, long adminId, String groupName,
			String groupRemark, int cashStatus) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = groupDao.updateGroup(conn, id, adminId, groupName,
					groupRemark, cashStatus);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 删除用户组
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public long deleteGroup(String id) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			groupUserDao.deleteGroupUserByGroupId(conn, id);
			result = groupDao.deleteGroup(conn, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 删除用户跟用户组的关系
	 * 
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public long deleteGroupUsersByIds(String ids) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = groupUserDao.deleteGroupUserById(conn, ids);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 添加用户到用户组
	 * 
	 * @param userIds
	 * @param groupId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long addGroupUser(List<Long> userIds, long groupId)
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			if (userIds != null) {
				// 去除已经添加过的用户
				List<Long> list = groupUserDao.queryUserIdByGroupId(conn,
						groupId);
				userIds.removeAll(list);
				for (Long userId : userIds) {
					result = groupUserDao.addGroupUser(conn, userId, groupId);
				}
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 根据Id获取用户组信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> getGroup(long id) throws SQLException,
			DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = groupDao.getGroup(conn, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setGroupUserDao(GroupUserDao groupUserDao) {
		this.groupUserDao = groupUserDao;
	}

	/**
	 * 分页查询用户组信息
	 * -----modify by houli  添加一个groupName参数，用来进行用户组搜索
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryGroupForPage(PageBean pageBean,String groupName) throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		StringBuilder condition = new StringBuilder();

		try {
			//--------------add by houli
			if(groupName != null){
				condition.append(" and groupName like '%"+StringEscapeUtils.escapeSql(groupName)+"%'");
			}
			//-------------
			dataPage(conn, pageBean, "v_t_group_admin", "*", "", condition
					.toString());
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
	 * 根据用户名查询用户ID
	 * 
	 * @param userNames
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryUserIdByUserName(String userNames)
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = userDao.queryUserIdByUserName(conn, userNames);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return result;

	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 查询用户信息
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryGroupUserForPage(String userName, String realName,
			double startAllSum, double endAllSum, double startUseableSum,
			double endUseableSum, long cashStatus, PageBean pageBean)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND username like '%");
			condition.append(StringEscapeUtils.escapeSql(userName));
			condition.append("%'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName like '%");
			condition.append(StringEscapeUtils.escapeSql(realName));
			condition.append("%'");
		}
		if (startAllSum > 0) {
			condition.append(" AND allSum >=");
			condition.append(startAllSum);
		}
		if (endAllSum > 0) {
			condition.append(" AND allSum <=");
			condition.append(endAllSum);
		}
		if (startUseableSum > 0) {
			condition.append(" AND usableSum >=");
			condition.append(startUseableSum);
		}
		if (endUseableSum > 0) {
			condition.append(" AND usableSum <=");
			condition.append(endUseableSum);
		}
		if (cashStatus > 0) {
			condition.append(" AND cashStatus=");
			condition.append(cashStatus);
		}
		try {
			dataPage(conn, pageBean, "v_t_user_person_work", "*", "", condition
					.toString());
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
	 * 修改用户的提现状态
	 * 
	 * @param userId
	 * @param cashStatus
	 * @throws SQLException
	 */
	public long updateUserCashStatus(String userId, String cashStatus)
			throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = userDao
					.updateUserCashStatus(conn, userId + "", cashStatus);

			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 查询用户组成员
	 * 
	 * @param userName
	 * @param realName
	 * @param startAllSum
	 * @param endAllSum
	 * @param startUseableSum
	 * @param endUseableSum
	 * @param groupId
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryGroupUsersForPage(String userName, String realName,
			double startAllSum, double endAllSum, double startUseableSum,
			double endUseableSum, long groupId, PageBean pageBean)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND username like '%");
			condition.append(StringEscapeUtils.escapeSql(userName));
			condition.append("%'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName like '%");
			condition.append(StringEscapeUtils.escapeSql(realName));
			condition.append("%'");
		}
		if (startAllSum > 0) {
			condition.append(" AND allSum >=");
			condition.append(startAllSum);
		}
		if (endAllSum > 0) {
			condition.append(" AND allSum <=");
			condition.append(endAllSum);
		}
		if (startUseableSum > 0) {
			condition.append(" AND usableSum >=");
			condition.append(startUseableSum);
		}
		if (endUseableSum > 0) {
			condition.append(" AND usableSum <=");
			condition.append(endUseableSum);
		}
		if (groupId > 0) {
			condition.append(" AND groupId=");
			condition.append(groupId);
		}
		try {
			dataPage(conn, pageBean, "v_t_groupuser_user_person", "*", "",
					condition.toString());
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
	 * 查询所有的用户组
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAllGroup() throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();

		try {
			return groupDao.queryAllGroup(conn);
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
	 * 查询用户组成员
	 * 
	 * @param userName
	 * @param realName
	 * @param startAllSum
	 * @param endAllSum
	 * @param startUseableSum
	 * @param endUseableSum
	 * @param groupId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryGroupUsers(String userName,
			String realName, double startAllSum, double endAllSum,
			double startUseableSum, double endUseableSum, long groupId)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		try {
			return groupUserDao.queryGroupUsers(conn, userName, realName,
					startAllSum, endAllSum, startUseableSum, endUseableSum,
					groupId);
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
	 * 查询组用户的电话
	 * 
	 * @param groupId
	 * @return
	 * @throws SQLException 
	 * @throws DataException 
	 */
	public List<Map<String,Object>> queryUserPhoneByGroupId(long groupId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return groupUserDao.queryUserPhoneByGroupId(conn, groupId);
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
	 * 查询邮箱号
	 * @param groupId
	 * @return
	 * @throws SQLException 
	 * @throws DataException 
	 */
	public List<Map<String, Object>> queryUserEmailByGroupId(long groupId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return groupUserDao.queryUserEmailByGroupId(conn, groupId);
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
	 * 根据Id集合查询用户组资料
	 * @param ids
	 * @return
	 * @throws SQLException 
	 * @throws DataException 
	 */
	public List<Map<String, Object>> queryGroupUsersByIds(String ids) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		try {
			return groupUserDao.queryGroupUsersByIds(conn, ids);
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
	 * houli
	 * @param groupId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryGroupById(Long groupId) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();

		try {
			Map<String,String> map =  groupUserDao.queryGroupById(conn, groupId);
			List<Map<String,Object>> listUsers = groupUserDao.queryUsersByGroupId(conn, groupId);
			StringBuffer sb = new StringBuffer();
			StringBuffer sb1 = new StringBuffer();
			Map<String,Object> mp = null;
			if(listUsers !=null && listUsers.size() >0){
				for(int i=0,n=listUsers.size();i<n;i++){
					mp = listUsers.get(i);
					sb.append(mp.get("username")+",");
					sb1.append(mp.get("userId")+",");
					
				}
			}
			map.put("users", sb.toString());
			map.put("userIds", sb1.toString());
			return map;
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
	
	public long deleteGroupUserByGroupId(long groupId) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = groupUserDao.deleteGroupUserByGroupId(conn, String.valueOf(groupId));
			if(result <= 0){
				conn.rollback();
				return result;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	public long addGroupUser(List<Long> userIds) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			int count = 0;
			for (Long userId : userIds) {
				result = groupUserDao.addGroupUser(conn, userId, result);
				if(result >0 )
					count ++;
			}
			if(count != userIds.size()){
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	public long modifyGroup(long groupId,long adminId, List<Long> userIds, String groupName,
			String groupRemark,int cashStatus) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = groupDao.modifyGroup(conn, groupId,adminId, groupName, groupRemark,cashStatus);
			if(result <= 0){
				conn.rollback();
				return result;
			}
			if(userIds != null && userIds.size() >0 ){
				groupUserDao.deleteGroupUserByGroupId(conn, String.valueOf(groupId));
				for (Long userId : userIds) {
					groupUserDao.addGroupUser(conn, userId, groupId);
				}
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

}
