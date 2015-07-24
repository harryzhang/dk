package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.constants.IConstants;
import com.sp2p.dao.PersonDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.RelationDao;
import com.sp2p.dao.admin.ShoveBorrowAmountTypeDao;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;

/**
 * 手机号码注册
 * 
 * @author lw
 * 
 */
public class CellPhoneService extends BaseService {
	private UserDao userDao;
	private RelationDao relationDao;
	private PersonDao personDao;
	private ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao;
	
	public static Log log = LogFactory.getLog(CellPhoneService.class);

	/**
	 * 用户注册
	 * 
	 * @param email
	 * @param username
	 * @param password
	 * @param refferee
	 * @return
	 * @throws SQLException
	 * @throws DataException 
	 */
	public Long usercellRegister(String cellphone, String userName,
			String password, String refferee, Map<String, Object> userMap,
			int typeLen) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		String email = null;
		String dealpwd = null; // 交易密码
		String mobilePhone = cellphone;
		Integer rating = 0;// 网站积分
		Integer creditrating = 0;// 信用积分
		String lastIP = "";
		String lastDate = null;// 最后登录时间
		Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
		// DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String vipcreatetime = null;// VIP创建时间
		double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
		Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
		String headImg = "";// 用户头型
		Integer enable = 1; // 是否禁用 1、启用 2、禁用
		// 测试--跳过验证
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到信息额度类型
		Map<String, String> map = shoveBorrowAmountTypeDao
				.queryBorrowAmountByNid(conn, "credit");
		double creditLimit = Convert.strToDouble(map.get("init_credit"), 0);
		if (IConstants.ISDEMO.equals("1")) {
			authstep = 1;
			enable = 1;
			vipstatus = 2;
			vipcreatetime = df.format(new Date());
			creditlimit = creditLimit;
		}
		Long servicePersonId = null;
		long userId = -1L;
		long result = -1L;
		boolean flag = false;
		try {
			userId = userDao.addUser(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId,creditLimit);

			// 将手机号码同步到t_person表中

			result = personDao.addPerson(conn, null, cellphone, null,
					null, null, null, null, null,
					null, null, null, null, null,
					null, null, null,
					null, null, null, userId,
					null, null, null, null);

			if (result <= 0) {
				return -1L;
			}
			// ------
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				try {
					result = userDao.addMaterialsauth1(conn, userId, i);
					if (result <= 0) {
						return -1L;
					}
				} catch (Exception e) {
					return -1L;
				}
			}
			if (userMap != null) {
				relationDao.addRelation(conn, userId, (Long) userMap
						.get("parentId"), (Integer) userMap.get("level"), 1);
			}
			flag = true;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			if (flag) {
				conn.commit();
			} else {
				conn.rollback();
			}
			conn.close();
		}

		return userId;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}

	// 通过用户手机号码更改用户登录密码

	public Long updatepasswordBycellphone(String cellphone, String password)
			throws SQLException {
		Connection conn = connectionManager.getConnection();
		Long resultId = -1L;

		try {
			resultId = userDao.updatepasswordBycellphone(conn, cellphone,
					password);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return resultId;

	}
	
	/**
	 * 根据手机号码查询
	 * @param cellphone
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String,String>  queryCellPhone(String cellphone) throws DataException, SQLException{
		Connection  conn = connectionManager.getConnection();
		Map<String,String> map = new HashMap<String, String>();
		try {
			map = personDao.querCellPhone(conn, cellphone);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			 conn.close();
		}
		return  map;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public void setShoveBorrowAmountTypeDao(
			ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao) {
		this.shoveBorrowAmountTypeDao = shoveBorrowAmountTypeDao;
	}

}
