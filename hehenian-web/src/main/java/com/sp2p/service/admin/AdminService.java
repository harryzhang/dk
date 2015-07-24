package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataRow;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.admin.AdminDao;
import com.sp2p.dao.admin.RelationDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.entity.Admin;
import com.sp2p.util.ChinaPnRInterface;

public class AdminService extends BaseService {

	public static Log log = LogFactory.getLog(AdminService.class);

	private AdminDao adminDao;
	private RelationDao relationDao;

	/**
	 * 添加管理员
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param enable
	 *            状态
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addAdmin(String userName, String password, int enable,
			long roleId, String realName, String telphone, String qq,
			String email, String img, String isLeader) throws SQLException,
			DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = adminDao.queryAdminList(conn,
				userName, null);
		if (list != null && list.size() > 0) {
			return -2L;
		}
		Long adminId = -1L;
		try {
			adminId = addAdminInfo(conn, userName, password, enable, roleId,
					realName, telphone, qq, email, img, isLeader);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return adminId;
	}

	/**
	 * 修改管理员客户号
	 * 
	 * @param adminID
	 * @param usrCustId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return Long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Long updateAdmin(long adminID, String usrCustId)
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Long adminId = -1L;
		try {
			adminId = adminDao.updateAdminUsrCustId(conn, adminID, usrCustId);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return adminId;
	}

	/**
	 * 修改管理员
	 * 
	 * @param adminId
	 *            管理员编号
	 * @param password
	 *            密码
	 * @param enable
	 *            状态
	 * @param lastIP
	 *            最后登录IP
	 * @return Long
	 * @throws SQLException
	 */
	public Long updateAdmin(long adminId, String password, Integer enable,
			String lastIP, Long roleId, String realName, String telphone,
			String qq, String email, String img, String isLeader)
			throws SQLException {
		Connection conn = connectionManager.getConnection();
		Long returnId = -1L;
		try {
			returnId = updateAdminInfo(conn, adminId, password, enable, lastIP,
					roleId, realName, telphone, qq, email, img, isLeader);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return returnId;
	}

	/**
	 * 删除管理员
	 * 
	 * @param adminIds
	 *            管理员编号拼接成的字符串
	 * @throws SQLException
	 */
	public void deleteAdmin(String adminIds) throws SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			adminDao.deleteAdmins(conn, adminIds);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 根据管理员编号查询管理员信息
	 * 
	 * @param id
	 *            管理员编号
	 * @return Map<String,String>
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryAdminById(long id) throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = adminDao.queryAdminById(conn, id);
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
	 * 更加用户名获取id
	 * 
	 * @param userName
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryIdByUser(String userName)
			throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = adminDao.queryIdByUser(conn, userName);
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
	 * 根据用户名，密码进行查询（登录功能）
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return Map<String,String>
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryAdminByNamePass(String userName,
			String password) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = adminDao.queryAdminByNamePass(conn, userName, password);
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
	 * 根据条件查询管理员信息
	 * 
	 * @param userName
	 *            用户名
	 * @param enable
	 *            状态
	 * @return List<Map<String,Object>>
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAdminList(String userName,
			Integer enable) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = adminDao.queryAdminList(conn, userName, enable);
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
		return list;
	}

	/**
	 * 根据条件分页查询管理员信息
	 * 
	 * @param userName
	 *            用户名
	 * @param enable
	 *            状态
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryAdminPage(String userName, Integer enable, Long roleId,
			PageBean<Map<String, Object>> pageBean) throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		try {
			StringBuffer condition = new StringBuffer();
			if (StringUtils.isNotBlank(userName)) {
				condition.append(" and  userName  like '%"
						+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
			}
			if (enable != null && enable >= 0) {
				condition.append(" AND enable = " + enable);
			}
			if (roleId != null && roleId >= -1) {
				condition.append(" AND roleId = " + roleId);
			}
			dataPage(conn, pageBean, " v_t_admin", " * ", "", condition
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
	 * 管理员登陆
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param IP
	 * @return Admin
	 * @throws Exception
	 */
	public Admin adminLogin(String userName, String password, String IP)
			throws Exception {
		Connection conn = connectionManager.getConnection();
		Admin admin = null;
		try {
			password = StringEscapeUtils.escapeSql(password.trim());
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}
			Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
			DataSet ds = t_admin.open(conn, "", " userName ='"
					+ StringEscapeUtils.escapeSql(userName.trim())
					+ "' and password = '" + password + "'", "", -1, -1);
			int returnId = ds.tables.get(0).rows.getCount();
			if (returnId <= 0) {
				conn.close();
				return null;
			} else {
				admin = new Admin();
				DataRow dr = ds.tables.get(0).rows.get(0);
				admin.setId((Long) dr.get("id"));
				admin.setUserName(dr.get("userName") + "");
				admin.setPassword(dr.get("password") + "");
				admin.setLastIP(dr.get("lastIP") + "");
				admin.setLastTime((Date) dr.get("lastTime"));
				admin.setEnable(Integer.parseInt(dr.get("enable") + ""));
				admin.setRoleId(Convert.strToLong(dr.get("roleId") + "", 0));

				admin.setCard(dr.get("card") == null ? "" : dr.get("card")
						.toString());
				admin.setEmail(dr.get("email") == null ? "" : dr.get("email")
						.toString());
				admin.setTelphone(dr.get("telphone") == null ? "" : dr.get(
						"telphone").toString());
				admin.setRealName(dr.get("realName") == null ? "" : dr.get(
						"realName").toString());
				admin.setUsrCustId(dr.get("usrCustId") == null ? "" : dr.get(
						"usrCustId").toString());
				admin.setSubAcctMoney(dr.get("subAcctMoney") == null ? "" : dr
						.get("subAcctMoney").toString());
			}
			JSONObject json = null;
			if (null != admin.getUsrCustId()) {
				DataRow dr = ds.tables.get(0).rows.get(0);
				String jsonStr = ChinaPnRInterface.queryAccts();// 汇付查()询专属账户余额
				if(null != jsonStr && !jsonStr.equals("签名错误"))
				{
					json = JSONObject.fromObject(jsonStr);
					int RespCode = json.getInt("RespCode");
					if (RespCode == 0) {
						JSONArray array = json.getJSONArray("AcctDetails");
						for(int i = 0;i<array.size();i++){
						JSONObject  jjj = array.getJSONObject(i);
							if(jjj.getString("SubAcctId").equals(dr.get("subAcct"))){
								String avlBal = jjj.getString("AvlBal");
								avlBal = avlBal.replace(",", "");
								// 更新出账账户余额--汇付结果
								if ((dr.get("subAcctMoney") != avlBal)
										|| (!dr.get("subAcctMoney").equals(avlBal))) {
									adminDao.updateSubAcct(conn, avlBal, dr.get("subAcct")
											.toString());
									admin.setSubAcctMoney(avlBal);
								}
							}
						}
					}
				}
			}

			if (StringUtils.isNotBlank(IP)) {
				t_admin.lastTime.setValue(new Date());
				t_admin.lastIP.setValue(IP);
				t_admin.update(conn, " id=" + admin.getId());
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return admin;
	}

	/**
	 * 获得管理员权限
	 * 
	 * @param role
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryAdminRoleName(long id) throws Exception {
		Connection conn = connectionManager.getConnection();
		String roleName = "";
		try {
			Dao.Tables.t_role t_role = new Dao().new Tables().new t_role();
			DataSet ds1 = t_role.open(conn, "", " id=" + id, "", -1, -1);
			DataRow dr1 = ds1.tables.get(0).rows.get(0);
			roleName = dr1.get("name") + "";
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return roleName;
	}

	/**
	 * 获得管理员列表
	 * 
	 * @param enable
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAdministors(Integer enable)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		String command = "SELECT DISTINCT userName,id from t_admin";

		try {
			DataSet dataSet = MySQL.executeQuery(conn, command);
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		} finally {
			conn.close();
		}

	}

	public Long addAdminGroup(String userName, String password, int enable,
			long roleId, String realName, String telphone, String qq,
			String email, String img, String isLeader, Integer sex,
			String card, String summary, Integer nativePlacePro,
			Integer nativePlaceCity, String address, long parentId, int level)
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = adminDao.queryAdminList(conn,
				userName, null);
		if (list != null && list.size() > 0) {
			return -2L;
		}
		boolean flag = false;
		Long adminId = -1L;
		try {
			adminId = addAdminGroupInfo(conn, userName, password, enable,
					roleId, realName, telphone, qq, email, img, isLeader, sex,
					card, summary, nativePlacePro, nativePlaceCity, address);
			if (adminId <= 0) {
				return adminId;
			}
			long returnId = relationDao.addRelation(conn, adminId, parentId,
					level, 1);
			if (returnId <= 0) {
				return -1L;
			}
			flag = true;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			if (flag) {
				conn.commit();
			} else {
				conn.rollback();
			}
			conn.close();
		}
		return adminId;
	}

	public Long updateAdminGroup(long adminId, String userName,
			String password, Integer enable, long roleId, String realName,
			String telphone, String qq, String email, String img,
			String isLeader, Integer sex, String card, String summary,
			Integer nativePlacePro, Integer nativePlaceCity, String address,
			Long relationId, Long parentId) throws SQLException {
		long returnId = -1;
		Connection conn = MySQL.getConnection();
		boolean flag = false;
		try {
			returnId = updateAdminGroupInfo(conn, adminId, password, enable,
					roleId, realName, telphone, qq, email, img, isLeader, sex,
					card, summary, nativePlacePro, nativePlaceCity, address);
			if (returnId <= 0) {
				return returnId;
			}
			if (relationId != null && relationId > 0) {
				// returnId = relationDao.updateRelation(conn, relationId, null,
				// parentId, null, null);
			}
			if (returnId <= 0) {
				return returnId;
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
		return returnId;
	}

	/**
	 * 查询团队长信息
	 * 
	 * @param userName
	 * @param realName
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	public void queryGroupCaptain(String userName, String realName,
			String startDate, String endDate,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		StringBuffer condition = new StringBuffer();
		condition.append(" AND roleId=1 ");
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(startDate)) {
			condition.append(" AND addDate >= '"
					+ StringEscapeUtils.escapeSql(startDate) + "'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			condition.append(" AND addDate <= '"
					+ StringEscapeUtils.escapeSql(endDate) + "'");
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" t_admin ",
					" id,userName,realName,card,telphone,summary,date_format(addDate,'%Y-%m-%d %H:%i:%s' ) as addDate ,enable ",
					" order by id desc ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<Map<String, Object>> queryAdminByRoleId(long roleId)
			throws Exception {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = adminDao.queryAdminByRoleId(conn, roleId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	public long addrelation(long peopleId, long parentId, int level, int enable)
			throws SQLException {
		Connection conn = connectionManager.getConnection();
		// conn.setAutoCommit(false);
		long returnId = 0;
		for (int i = 0; i < 10; i++) {
			// returnId = relationDao.addRelation(conn, peopleId, parentId,
			// level, enable);
			relationDao.updateRelation(conn, 282 + i, peopleId + 2,
					parentId + 1, level + 1, enable + 1);
		}
		conn.commit();
		conn.close();
		return returnId;
	}

	/**
	 * 添加管理人
	 * 
	 * @param conn
	 * @param userName
	 * @param password
	 * @param enable
	 * @param roleId
	 * @param realName
	 * @param telphone
	 * @param qq
	 * @param email
	 * @param img
	 * @param isLeader
	 * @return
	 * @throws SQLException
	 */
	private Long addAdminInfo(Connection conn, String userName,
			String password, int enable, long roleId, String realName,
			String telphone, String qq, String email, String img,
			String isLeader) throws SQLException {
		return adminDao.addAdmin(conn, userName, password, enable, roleId,
				realName, telphone, qq, email, img, isLeader, null, null, null,
				null, null, null);
	}

	/**
	 * 添加军团长/经济人
	 * 
	 * @return
	 * @throws SQLException
	 */
	private Long addAdminGroupInfo(Connection conn, String userName,
			String password, int enable, long roleId, String realName,
			String telphone, String qq, String email, String img,
			String isLeader, Integer sex, String card, String summary,
			Integer nativePlacePro, Integer nativePlaceCity, String address)
			throws SQLException {
		return adminDao.addAdmin(conn, userName, password, enable, roleId,
				realName, telphone, qq, email, img, isLeader, sex, card,
				summary, nativePlacePro, nativePlaceCity, address);
	}

	/**
	 * 修改管理员
	 * 
	 * @return
	 * @throws SQLException
	 */
	private long updateAdminInfo(Connection conn, long adminId,
			String password, Integer enable, String lastIP, Long roleId,
			String realName, String telphone, String qq, String email,
			String img, String isLeader) throws SQLException {
		return adminDao.updateAdmin(conn, adminId, password, enable, lastIP,
				roleId, realName, telphone, qq, email, img, isLeader, null,
				null, null, null, null, null);
	}

	private long updateAdminGroupInfo(Connection conn, long adminId,
			String password, Integer enable, Long roleId, String realName,
			String telphone, String qq, String email, String img,
			String isLeader, Integer sex, String card, String summary,
			Integer nativePlacePro, Integer nativePlaceCity, String address)
			throws SQLException {
		return adminDao.updateAdmin(conn, adminId, password, enable, null,
				roleId, realName, telphone, qq, email, img, isLeader, sex,
				card, summary, nativePlacePro, nativePlaceCity, address);
	}

	public Map<String, String> queryCheckCount(long adminId)
			throws SQLException, DataException {
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			Procedures.pr_examine(conn, ds, outParameterValues, adminId);
			map = BeanMapUtils.dataSetToMap(ds);
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
	 * 查询是否存在该用户名
	 * 
	 * @param conn
	 * @param userName
	 * @return 存在返回true
	 * @throws SQLException
	 * @throws SQLException
	 * @throws DataException
	 * @throws DataException
	 */
	public boolean isExistUserName(String userName) throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		boolean result = false;
		try {
			result = adminDao.isExistUserName(conn, userName);
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
		return result;
	}

	/**
	 * 根据状态禁用 或开启 管理员权限
	 * 
	 * @param id
	 * @param enable
	 * @return
	 * @throws SQLException
	 */
	public long isenableAdmin(long id, int enable) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = adminDao.isenableAdmin(conn, id, enable);
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
	 * 查询经纪人信息
	 * 
	 * @param userName
	 * @param realName
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	public void queryrelationeconomicInfo(String userName, String realName,
			String startDate, String endDate,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		StringBuffer condition = new StringBuffer();
		condition.append(" AND roleId=2 ");
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(startDate)) {
			condition.append(" AND addDate >= '"
					+ StringEscapeUtils.escapeSql(startDate.trim()) + "'");
		}
		if (StringUtils.isNotBlank(endDate)) {
			condition.append(" AND addDate <= '"
					+ StringEscapeUtils.escapeSql(endDate.trim()) + "'");
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" t_admin ",
					" id,userName,realName,card,telphone,summary,addDate,enable ",
					" order by id desc ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryEconomyAward(String userName,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_economy_award ", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryEconomyList(String userName,
			PageBean<Map<String, Object>> pageBean, String starttime,
			String endTime) throws Exception {
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND username like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(starttime)) {
			condition.append(" AND createTime >= '"
					+ StringEscapeUtils.escapeSql(starttime.trim()) + "'");
		}
		if (StringUtils.isNotBlank(endTime)) {
			condition.append(" AND createTime <= '"
					+ StringEscapeUtils.escapeSql(endTime.trim()) + "'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " t_economy_list ", " * ", " ", condition
					.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}
}
