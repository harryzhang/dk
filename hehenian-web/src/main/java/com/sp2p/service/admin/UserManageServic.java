package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.UserIntegralDao;
import com.sp2p.dao.admin.UserManageDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_user;

/**
 * 后台用户管理
 * 
 * @author lw
 * 
 */
public class UserManageServic extends BaseService {
	public static Log log = LogFactory.getLog(UserManageServic.class);
	private UserManageDao userManageDao;
	private UserIntegralDao userIntegralDao;

	public UserIntegralDao getUserIntegralDao() {
		return userIntegralDao;
	}

	public void setUserIntegralDao(UserIntegralDao userIntegralDao) {
		this.userIntegralDao = userIntegralDao;
	}

	private List<Map<String, Object>> paymentMode;
	private List<Map<String, Object>> deadline;

	public void setPaymentMode(List<Map<String, Object>> paymentMode) {
		this.paymentMode = paymentMode;
	}

	public void setDeadline(List<Map<String, Object>> deadline) {
		this.deadline = deadline;
	}

	public void setUserManageDao(UserManageDao userManageDao) {
		this.userManageDao = userManageDao;
	}

	/**
	 * 用户基本信息管理
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserManageBaseInfo(PageBean<Map<String, Object>> pageBean, String userName, String id, String idNo, String cellPhone, int source) throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(userName))
				condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");

			if (StringUtils.isNotBlank(id))
				condition.append(" and id like '%" + StringEscapeUtils.escapeSql(id.trim()) + "%' ");

			if (StringUtils.isNotBlank(cellPhone))
				condition.append(" and cellPhone like '%" + StringEscapeUtils.escapeSql(cellPhone.trim()) + "%' ");

			if (StringUtils.isNotBlank(idNo))
				condition.append(" and idNo like '%" + StringEscapeUtils.escapeSql(idNo.trim()) + "%' ");

			if (source >= 0)
				condition.append(" and source=" + source);

			dataPage(conn, pageBean, "v_t_usermanage_baseinfo", "*", " order by id ", condition.toString());
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
	 * 根据用户id查询用户基本信息
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserManageBaseInfo_id(PageBean<Map<String, Object>> pageBean, String ids) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		try {

			if (StringUtils.isNotBlank(ids)) {
				String idStr = StringEscapeUtils.escapeSql("'" + ids + "'");
				String idSQL = "-2";
				idStr = idStr.replaceAll("'", "");
				String[] array = idStr.split(",");
				for (int n = 0; n <= array.length - 1; n++) {
					idSQL += "," + array[n];
				}
				condition.append("and id in(");
				condition.append(idSQL);
				condition.append(")");
			}
			dataPage(conn, pageBean, "v_t_usermanage_baseinfo", "*", " order by id ", condition.toString());
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
	 * 用户基本信息管理
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserManageBaseInfo(PageBean<Map<String, Object>> pageBean) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_usermanage_baseinfo", "*", "", "");
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

	// 用户基本信息列表查看
	public void queryUserManageInfo(PageBean<Map<String, Object>> pageBean, String userName, String realName) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(userName)) {
				condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
			}
			if (StringUtils.isNotBlank(realName)) {
				condition.append(" and realName like '%" + StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
			}

			dataPage(conn, pageBean, "v_t_usermanage_info", "*", " order by id ", condition.toString());
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
	 * 用户积分管理
	 * 
	 * @param pageBean
	 * @param username
	 * @param viprecode
	 * @param creditcode
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserManageintegralinfo(PageBean<Map<String, Object>> pageBean, String username, int viprecode, int creditcode) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(username)) {
			condition.append(" and  username  like '%" + StringEscapeUtils.escapeSql(username.trim()) + "%' ");
		}
		StringBuffer ordercondition = new StringBuffer();
		if (viprecode != -1 && viprecode == 1) {
			ordercondition.append(" ORDER BY   rating ");
		}
		if (viprecode != -1 && viprecode == 2) {
			ordercondition.append(" ORDER BY  rating  DESC");
		}
		if (creditcode != -1 && creditcode == 1 && viprecode == -1) {
			ordercondition.append(" ORDER BY   creditrating ");
		}
		if (creditcode != -1 && creditcode == 2 && viprecode == -1) {
			ordercondition.append("  ORDER BY   creditrating  DESC");// 大到小
		}
		if (creditcode != -1 && creditcode == 1 && viprecode != -1) {
			ordercondition.append("  , creditrating ");
		}
		if (creditcode != -1 && creditcode == 2 && viprecode != -1) {// 大到小
			ordercondition.append("  , creditrating DESC ");
		}

		try {
			dataPage(conn, pageBean, "v_t_usermanage_integralinfo", "*", ordercondition.toString(), condition.toString());
			// dataPage(conn, pageBean, "v_t_usermanage_integralinfo", "*", "",
			// "");
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
	 * vip记录表
	 * 
	 * @param pageBean
	 * @param username
	 * @param apptime
	 * @param lasttime
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUservipRecoderinfo(PageBean<Map<String, Object>> pageBean, String username, String apptime, String lasttime) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(username)) {
			condition.append(" and  username  like '%" + StringEscapeUtils.escapeSql(username.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(apptime)) {
			condition.append(" and vipCreateTime >= '" + StringEscapeUtils.escapeSql(apptime.trim()) + "'");
		}
		if (StringUtils.isNotBlank(lasttime)) {
			condition.append(" and vip <= '" + StringEscapeUtils.escapeSql(lasttime.trim()) + "'");

		}
		try {
			dataPage(conn, pageBean, "v_t_usermanage_viprecordinfo", "*", "", condition.toString());
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
	 * 用户基本信息里面的查看用户的基本信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryUserManageInnerMsg(Long userId) throws SQLException {
		Map<String, String> map = null;

		Connection conn = connectionManager.getConnection();
		try {
			map = userManageDao.queryUserManageInnerMsg(conn, userId);

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

	public void queryUserCashList(PageBean<Map<String, Object>> pageBean, Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String command = "";
		if (userId != null) {
			command += " and id =" + userId;
		}
		StringBuffer cmd = new StringBuffer();
		cmd.append("(select a.id as userId,a.username,IFNULL(f.forRePaySum,0) as dueoutSum,a.usableSum,a.freezeSum,");
		cmd.append("round(sum(IFNULL(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest,0)),2) as dueinSum,d.realName realName from t_user a left join t_invest b on a.id = b.investor ");
		cmd.append(" left join t_person d on d.userId=a.id left join ");
		cmd.append("(select forRePaySum,publisher from (select sum(IFNULL((c.stillPrincipal+c.stillInterest-c.hasPI+c.lateFI-c.hasFI),0)) as forRePaySum,d.publisher  from t_repayment c left join t_borrow d on c.borrowId = d.id where c.repayStatus = 1 GROUP BY d.publisher) t) f");
		cmd.append(" on f.publisher = a.id  group by a.ID,a.usableSum,a.freezeSum,f.forRePaySum,d.realName,a.username) u");
		try {
			dataPage(conn, pageBean, cmd.toString(), "*", "", command);
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
	 * 修改用户信息
	 * 
	 * @param id
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserAllInfo(Long id, String realName, String highestEdu, String idNo, String address, String sex, String birthday, String cellPhone) {
		Long long1 = -1l;
		Connection conn = null;
		try {
			conn = connectionManager.getConnection();
			Dao.Tables.t_person person = new Dao().new Tables().new t_person();
			person.realName.setValue(realName);
			person.highestEdu.setValue(highestEdu);
			person.idNo.setValue(idNo);
			person.address.setValue(address);
			person.sex.setValue(sex);
			// email
			person.birthday.setValue(birthday);
			person.cellPhone.setValue(cellPhone);

			long1 = person.update(conn, " userid=" + id);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return long1;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param id
	 * @param
	 * @return
	 * @throws SQLException
	 */
	public Long updUserWorkAllInfo(Long work_id, Long id, String job, String monthlyIncome, String orgName, String companyTel, String companyType, String workEmail,
			String companyScale, String companyAddress, String workYear) {
		Long long1 = -1l;
		Connection conn = null;
		try {
			conn = connectionManager.getConnection();
			Dao.Tables.t_workauth workauth = new Dao().new Tables().new t_workauth();
			workauth.job.setValue(job);
			workauth.monthlyIncome.setValue(monthlyIncome);
			workauth.orgName.setValue(orgName);
			workauth.companyTel.setValue(companyTel);
			workauth.companyType.setValue(companyType);
			workauth.workEmail.setValue(workEmail);
			workauth.companyScale.setValue(companyScale);
			workauth.companyAddress.setValue(companyAddress);
			workauth.workYear.setValue(workYear);
			if (work_id == 0) {
				workauth.userId.setValue(id);
				long1 = workauth.insert(conn);
			} else
				long1 = workauth.update(conn, " userid=" + id + " and id=" + work_id);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return long1;
	}

	/**
	 * 弹出框显示信息初始化
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */

	public Map<String, String> queryUserManageaddInteral(Long userId) throws SQLException {
		Map<String, String> map = null;

		Connection conn = connectionManager.getConnection();
		try {
			map = userManageDao.queryUserManageaddInteral(conn, userId);

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
	 * 查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, String> queryUserInfo(long userId) {
		Connection conn = connectionManager.getConnection();
		try {
			return userManageDao.queryUserInfo(conn, userId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public long updateUserqq(long id, String qq) {
		long result = -1L;
		Connection conn = connectionManager.getConnection();
		try {
			result = userManageDao.updateUserqq(conn, id, qq);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/**
	 * 跳转到会员分明细info
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public void userintegralcreditinfo(PageBean<Map<String, Object>> pageBean, Long userid, Integer type) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (userid != -1L) {
				condition.append(" AND id = " + userid + " AND type = " + type);
				dataPage(conn, pageBean, "v_t_userManage_integralinner", "*", "", condition.toString());
			}
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
	 * 查询用投资管理
	 * 
	 * @param pageBean
	 * @param userid
	 * @throws SQLException
	 */
	public void queryUserManageInvest(PageBean<Map<String, Object>> pageBean, Long userid, String createtimeStart, String createtimeEnd) throws SQLException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (userid != -1L) {
				condition.append(" AND investor = " + userid);
			}
			if (StringUtils.isNotBlank(createtimeStart)) {
				condition.append(" and investTime >'" + StringEscapeUtils.escapeSql(createtimeStart.trim()) + "'");
			}
			if (StringUtils.isNotBlank(createtimeEnd)) {
				condition.append(" and investTime <'" + StringEscapeUtils.escapeSql(createtimeEnd.trim()) + "'");
			}
			dataPage(conn, pageBean, "v_t_userManage_invest", "*", "", condition.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

	/**
	 * 查询用户积分详情
	 * 
	 * @param userId
	 * @param score
	 * @param type
	 * @param typeStr
	 * @param remark
	 * @param time
	 * @param changetype
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addIntervalDelt(Long userId, Integer score, Integer type, String typeStr, String remark, String changetype) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		Long result1 = -1L;
		try {
			// 向t_user表增加积分
			result = userManageDao.addUserManageaddInteral(conn, userId, score, type);
			if (result < 0) {
				conn.rollback();
				return -1L;
			}

			if (type == 1) { // 向积分明细添加信用积分明细
				result1 = userManageDao.addserintegraldetail(conn, userId, score, typeStr, type, remark, changetype);
			}// 向积分明细添加会员积分明细
			else {
				Map<String, String> map = userIntegralDao.queryUserIntegral2(conn, userId, 2, typeStr);
				if (map == null) {
					result1 = userManageDao.addserintegraldetail(conn, userId, score, typeStr, type, remark, changetype);
				} else {

					long changerecore = Convert.strToInt((String) map.get("changerecore"), 1);
					long minId = Convert.strToInt(map.get("minId"), 1);
					// result1=userIntegralDao.updateUserIntegral(conn,changerecore,score,minId);

					result1 = userManageDao.addserintegraldetail(conn, userId, score, typeStr, type, remark, changetype);
				}
			}
			if (result1 < 0) {
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

	/**
	 * 查询用户资金信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserCashInfo(Long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return userManageDao.queryUserCashInfo(conn, userId);

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}

	public void changeFigure(PageBean pageBean) {
		List<Map<String, Object>> ll = pageBean.getPage();
		if (ll != null && ll.size() > 0) {// result rechargeType 中文显示
			for (Map<String, Object> mp : ll) {
				if (mp.get("paymentMode") != null) {
					String typeId = mp.get("paymentMode").toString();
					for (Map<String, Object> cc : this.getpaymentMode()) {
						if (cc.get("typeId").toString().equals(typeId)) {
							mp.put("paymentMode", cc.get("typeValue"));
							break;
						}
					}
				}
				if (mp.get("deadline") != null && mp.get("isDayThe") != null) {
					if (mp.get("isDayThe").equals(1)) {
						mp.put("deadline", mp.get("deadline") + "个月");
					} else
						mp.put("deadline", mp.get("deadline") + "天");

				}
			}
		}
	}

	public List<Map<String, Object>> getpaymentMode() {
		if (paymentMode == null) {
			paymentMode = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("typeValue", " 按月等额本息还款");
			paymentMode.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("typeValue", "按先息后本还款");
			paymentMode.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("typeValue", "秒还");
			paymentMode.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 4);
			mp.put("typeValue", "一次性还款");
			paymentMode.add(mp);

		}
		return paymentMode;
	}

	/**
	 * 用户注册管理
	 * 
	 * @param pageBean
	 * @param userName
	 * @param phone
	 * @param startTime
	 * @param endTiem
	 * @param userSource
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void findUserRegister(PageBean<Map<String, Object>> pageBean, String userName, String phone, String startTime, String endTiem, String userSource) throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(userName)) {
				condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
			}
			if (StringUtils.isNotBlank(phone)) {
				condition.append(" and mobilePhone like '%" + StringEscapeUtils.escapeSql(phone.trim()) + "%' ");
			}
			if (StringUtils.isNotBlank(startTime)) {
				condition.append(" and createTime >= '" + StringEscapeUtils.escapeSql(startTime.trim()) + "' ");
			}
			if (StringUtils.isNotBlank(endTiem)) {
				condition.append(" and createTime <= '" + StringEscapeUtils.escapeSql(endTiem.trim()) + "' ");
			}
			if (StringUtils.isNotBlank(userSource)) {
				condition.append(" and source = '" + StringEscapeUtils.escapeSql(userSource.trim()) + "' ");
			}

			dataPage(conn, pageBean, "t_user", "*", " order by id ", condition.toString());
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
	 * 根据选中ID获取用户列表
	 * 
	 * @param pageBean
	 * @param ids
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void queryUserList(PageBean<Map<String, Object>> pageBean, String ids) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(ids)) {
			String idStr = StringEscapeUtils.escapeSql("'" + ids + "'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String[] array = idStr.split(",");
			for (int n = 0; n <= array.length - 1; n++) {
				idSQL += "," + array[n];
			}
			condition.append("and id in(");
			condition.append(idSQL);
			condition.append(")");
		}

		try {
			dataPage(conn, pageBean, "t_user", "*", "", condition.toString());
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

	public List<Map<String, Object>> queryUserList() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = userManageDao.queryUserList(conn);
		conn.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	public void referencerList(String username,PageBean pageBean) throws SQLException {
		Connection conn = MySQL.getConnection();
		StringBuilder sql = new StringBuilder();
		String result = " a.id,a.username,a.email,a.usableSum,a.lastIP,a.lastDate, ifnull(count,0) as count ";
		String table = " t_user as a left join (select refferee,count(refferee) as count from t_user group by id) as b on a.id = b.refferee ";
		if (!StringUtils.isBlank(username)) {
			sql .append( " and username like '%" + username + "%'");
		}
		try {
			dataPage(conn, pageBean, table, result, " order by id asc ",  sql.toString());
		} catch (Exception e) {
			log.info(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 更新user代理人账户
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserAccountType(Long userId) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = userManageDao.updateUserAccountType(conn, userId);
			if (result < 0) {
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
	/**
	 * 查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, String> queryUserAccountType(long userId) {
		Connection conn = connectionManager.getConnection();
		try {
			return userManageDao.queryUserAccountType(conn, userId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
