package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.security.Encrypt;
import com.shove.util.BeanMapUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_admin;

/**
 * 管理员
 * 
 */
public class AdminDao {

	/**
	 * 添加管理员
	 * 
	 * @param conn
	 * @param adminName管理员名称
	 * @param adminPassword管理员密码
	 * @param enable是否禁用
	 * @return
	 * @throws SQLException
	 */
	public Long addAdmin(Connection conn, String userName, String password, int enable, long roleId, String realName, String telphone, String qq,
			String email, String img, String isLeader, Integer sex, String card, String summary, Integer nativePlacePro, Integer nativePlaceCity,
			String address) throws SQLException {
		if ("1".equals(IConstants.ENABLED_PASS)) {
			password = com.shove.security.Encrypt.MD5(password.trim());// 对密码进行转码
		} else {
			password = com.shove.security.Encrypt.MD5(password.trim() + IConstants.PASS_KEY);// 对密码进行转码
		}
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
		t_admin.userName.setValue(StringEscapeUtils.escapeSql(userName));
		t_admin.password.setValue(StringEscapeUtils.escapeSql(password));
		t_admin.roleId.setValue(roleId);
		t_admin.enable.setValue(enable);
		t_admin.realName.setValue(realName);
		t_admin.telphone.setValue(telphone);
		t_admin.qq.setValue(qq);
		t_admin.email.setValue(email);
		t_admin.img.setValue(img);
		t_admin.isLeader.setValue(isLeader);

		// 团队长，经纪人所需要的字段
		t_admin.sex.setValue(sex);
		t_admin.card.setValue(card);
		t_admin.summary.setValue(summary);
		t_admin.nativePlacePro.setValue(nativePlacePro);
		t_admin.nativePlaceCity.setValue(nativePlaceCity);
		t_admin.address.setValue(address);
		t_admin.addDate.setValue(new Date());

		return t_admin.insert(conn);
	}

	public Long updateAdminUsrCustId(Connection conn, long adminId, String usrCustId) throws SQLException {
		Dao.Tables.t_admin admin = new Dao().new Tables().new t_admin();
		if (StringUtils.isNotBlank(usrCustId)) {
			admin.usrCustId.setValue(usrCustId);
		}
		Long returnId = admin.update(conn, " id =" + adminId);
		return returnId;
	}

	/**
	 * 修改管理员
	 * 
	 * @param conn数据库连接
	 * @param adminId管理员编号
	 * @param password密码
	 * @param enable状态
	 * @param lastIP最后登录时间
	 * @return
	 * @throws SQLException
	 */
	public Long updateAdmin(Connection conn, long adminId, String password, Integer enable, String lastIP, Long roleId, String realName,
			String telphone, String qq, String email, String img, String isLeader, Integer sex, String card, String summary, Integer nativePlacePro,
			Integer nativePlaceCity, String address) throws SQLException {
		Dao.Tables.t_admin admin = new Dao().new Tables().new t_admin();
		if (enable != null && enable >= 0) {// 状态值满足，如果不为空，并且大于等于0则对此属性进行修改
			admin.enable.setValue(enable);
		}
		if (StringUtils.isNotBlank(password)) {// 密码不为空

			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());// 对密码进行转码
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim() + IConstants.PASS_KEY);// 对密码进行转码
			}
			admin.password.setValue(password);
		}
		if (StringUtils.isNotBlank(lastIP)) {// 最后登录IP不为空
			admin.lastIP.setValue(lastIP);
			admin.lastTime.setValue(new Date());
		}
		if (roleId != null && roleId > 0) {
			admin.roleId.setValue(roleId);
		}
		if (StringUtils.isNotBlank(realName)) {
			admin.realName.setValue(realName);
		}
		if (StringUtils.isNotBlank(telphone)) {
			admin.telphone.setValue(telphone);
		}
		if (StringUtils.isNotBlank(qq)) {
			admin.qq.setValue(qq);
		}
		if (StringUtils.isNotBlank(email)) {
			admin.email.setValue(email);
		}
		if (StringUtils.isNotBlank(img)) {
			admin.img.setValue(img);
		}
		if (StringUtils.isNotBlank(isLeader)) {
			admin.isLeader.setValue(isLeader);
		}
		// 团队长，经纪人所需要的字段
		if (sex != null && sex >= 0) {
			admin.sex.setValue(sex);
		}
		if (nativePlacePro != null && nativePlacePro >= 0) {
			admin.nativePlacePro.setValue(nativePlacePro);
		}
		if (nativePlaceCity != null && nativePlaceCity >= 0) {
			admin.nativePlaceCity.setValue(nativePlaceCity);
		}
		if (StringUtils.isNotBlank(card)) {
			admin.card.setValue(card);
		}
		if (StringUtils.isNotBlank(summary)) {
			admin.summary.setValue(summary);
		}
		if (StringUtils.isNotBlank(address)) {
			admin.address.setValue(address);
		}
		Long returnId = admin.update(conn, " id =" + adminId);
		return returnId;
	}

	/**
	 * 删除管理员
	 * 
	 * @param adminIds管理员编号拼接起来的字符串
	 * @return
	 * @throws SQLException
	 */
	public void deleteAdmins(Connection conn, String adminIds) throws SQLException {
		String idStr = StringEscapeUtils.escapeSql("'" + adminIds + "'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String[] array = idStr.split(",");
		for (int n = 0; n <= array.length - 1; n++) {
			idSQL += "," + array[n];
		}
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
		t_admin.delete(conn, " id in (" + idSQL + ")");// 对管理员进行删除，删除条数返回到returnId上面
	}

	/**
	 * 按条件查询管理员
	 * 
	 * @param conn
	 * @param adminId管理员编号
	 * @param userName用户名
	 * @param enable状态
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAdminList(Connection conn, String userName, Integer enable) throws SQLException, DataException {
		StringBuffer condition = new StringBuffer();
		List<Map<String, Object>> list = null;
		condition.append("1 = 1");
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName = '" + StringEscapeUtils.escapeSql(userName.trim()) + "'");
		}
		if (enable != null && enable >= 0) {
			condition.append(" AND enable = " + enable);
		}
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
		DataSet ds = t_admin.open(conn, "", condition.toString(), " id DESC  ", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		list = ds.tables.get(0).rows.rowsMap;
		condition = null;
		return list;
	}

	/**
	 * 根据用户名和密码查询管理员
	 * 
	 * @param conn
	 * @param userName用户名
	 * @param password密码
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryAdminByNamePass(Connection conn, String userName, String password) throws SQLException, DataException {
		StringBuffer condition = new StringBuffer();
		condition.append(" userName = '" + StringEscapeUtils.escapeSql(userName.trim()) + "'");
		condition.append(" AND password = '" + StringEscapeUtils.escapeSql(com.shove.security.Encrypt.MD5(password.trim())) + "'");
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
		DataSet ds = t_admin.open(conn, "", condition.toString(), " ", -1, -1);
		condition = null;
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 根据用户名查用户id
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryIdByUser(Connection conn, String userName) throws SQLException, DataException {
		Dao.Tables.t_admin admin = new Dao().new Tables().new t_admin();
		DataSet dataSet = admin.open(conn, "id", " userName='" + userName + "'", "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 根据Id查询管理员
	 * 
	 * @param conn
	 * @param adminId管理员编号
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryAdminById(Connection conn, long adminId) throws SQLException, DataException {
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();// 获得管理员对象
		DataSet ds = t_admin.open(conn, "", " id = " + adminId, "", -1, -1);// 对数据库进行访问，查询数据，DateSet装载数据结果
		return BeanMapUtils.dataSetToMap(ds);

	}

	/**
	 * 根据角色编号查询角色信息
	 * 
	 * @param conn
	 * @param roleId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAdminByRoleId(Connection conn, long roleId) throws SQLException, DataException {
		Dao.Tables.t_admin admin = new Dao().new Tables().new t_admin();
		DataSet dataSet = admin.open(conn, " id,userName ", " roleId = " + roleId, "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 查询是否存在该用户名
	 * 
	 * @param conn
	 * @param userName
	 * @return 存在返回true
	 * @throws SQLException
	 * @throws DataException
	 */
	public boolean isExistUserName(Connection conn, String userName) throws SQLException, DataException {
		Dao.Tables.t_admin admin = new Dao().new Tables().new t_admin();
		DataSet dataSet = admin.open(conn, " count(1) as counts ", " userName = '" + StringEscapeUtils.escapeSql(userName) + "'", "", -1, -1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
		int count = Convert.strToInt(map.get("counts"), 0);
		return count > 0;
	}

	/**
	 * 修改用户权限 禁用 或者开启
	 * 
	 * @param conn
	 * @param id
	 * @param enable
	 * @return
	 * @author C_J
	 * @throws SQLException
	 */
	public long isenableAdmin(Connection conn, long id, int enable) throws SQLException {
		Dao.Tables.t_admin admin = new Dao().new Tables().new t_admin();
		admin.enable.setValue(enable);
		return admin.update(conn, " id =  " + id);
	}

	public long updateAdminMoney(Connection conn, String subAcct, String subAcctMoney) {
		Dao.Tables.t_admin admin = new Dao().new Tables().new t_admin();
		admin.subAcctMoney.setValue(subAcctMoney);
		return admin.update(conn, " subAcct = '" + subAcct + "'");
	}

	public String queryAdminByAcctId(Connection conn, String subAcct) throws DataException {
		Dao.Tables.t_admin admin = new Dao().new Tables().new t_admin();
		DataSet dataSet = admin.open(conn, " subAcctMoney ", " subAcct = '" + StringEscapeUtils.escapeSql(subAcct) + "'", "", -1, -1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
		return map == null ? null : map.get("subAcctMoney");
	}

	/**
	 * 
	 * 更新管理员余额
	 * 
	 * @param conn
	 * @param outAvlBal
	 * @param object
	 *            [参数说明]
	 * @return
	 * 
	 */
	public long updateSubAcct(Connection conn, String avlBal, String subAcct) {
		Dao.Tables.t_admin t_admin = new Dao().new Tables().new t_admin();
		t_admin.subAcctMoney.setValue(Double.valueOf(avlBal));
		return t_admin.update(conn, " subAcct='" + subAcct + "'");
	}
}
