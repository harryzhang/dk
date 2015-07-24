package com.sp2p.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.dao.admin.UserManageDao;
import com.sp2p.database.Dao;

public class VidateDao {
	public static Log log = LogFactory.getLog(VidateDao.class);
	private UserManageDao userManageDao;

	/**
	 * 查找图片资料验证情况
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querymaterialsauth(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();

		DataSet dataSet = materialsauth.open(conn, "", "userId = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查找图片资料验证情况
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */

	public Map<String, String> querymaterialsauth(Connection conn, long id, Integer type) throws SQLException, DataException {
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();

		DataSet dataSet = materialsauth.open(conn, "", "userId = " + id + " AND materAuthTypeId = " + type, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询个人的额度申请详情
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryrequestCredit(Connection conn, long tcid) throws SQLException, DataException {
		// Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new
		// t_materialsauth();
		Dao.Views.v_t_user_credit_apply_msgas msg = new Dao().new Views().new v_t_user_credit_apply_msgas();
		DataSet dataSet = msg.open(conn, "", " tcid = " + tcid, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询客服表的所有名字
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryServiceName(Connection conn) throws SQLException, DataException {
		return null;
	}

	/**
	 * 额度申请审核处理
	 * 
	 * @param conn
	 * @param userId
	 *            用户id
	 * @param status
	 *            审核状态
	 * @param applyAmount
	 *            申请金额
	 * @param checkMsg
	 *            审核信息
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Long updateUserCreditLimit(Connection conn, Long userId, Integer Creditstatus, BigDecimal applyAmount, String checkMsg, Long adminId,
			Long ti) throws SQLException, DataException {
		Long resul = -1L;
		Dao.Tables.t_user user = null;
		BigDecimal preCreditLimit = null;
		BigDecimal usableCreditLimit = null;// 可用信用额度
		Map<String, String> map = new HashMap<String, String>();
		try {
			user = new Dao().new Tables().new t_user();
			DataSet dataSet = user.open(conn, "creditLimit,usableCreditLimit", " id = " + userId, "", -1, -1);
			map = BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		if (map != null && map.size() > 0) {
			preCreditLimit = new BigDecimal(map.get("creditLimit"));
			usableCreditLimit = new BigDecimal(map.get("usableCreditLimit"));
		}
		if (preCreditLimit != null && usableCreditLimit != null) {
			user.creditLimit.setValue(preCreditLimit.add(applyAmount));
			user.usableCreditLimit.setValue(usableCreditLimit.add(applyAmount));
			resul = user.update(conn, " id = " + userId);
			if (resul > 0) {
				Dao.Tables.t_crediting crediting = new Dao().new Tables().new t_crediting();
				crediting.status.setValue(Creditstatus);
				crediting.checkMsg.setValue(checkMsg);
				crediting.reviewer.setValue(adminId);
				crediting.reviewTime.setValue(new Date());
				crediting.agreeAmount.setValue(applyAmount);// 设置同意申请金额
				resul = crediting.update(conn, " id = " + ti);
				if (resul < 0) {
					return -1L;
				} else {
					return resul;
				}
			} else {
				return -1L;
			}
		}
		return resul;
	}

	/**
	 * 更新用t_crediting这种表里面的数据
	 * 
	 * @throws SQLException
	 */
	public Long upTCREDITING(Connection conn, Long userId, Integer Creditstatus, BigDecimal applyAmount, String checkMsg, Long adminId, Long ti)
			throws SQLException {
		Dao.Tables.t_crediting crediting = new Dao().new Tables().new t_crediting();
		crediting.status.setValue(Creditstatus);
		crediting.checkMsg.setValue(checkMsg);
		crediting.reviewer.setValue(adminId);
		crediting.reviewTime.setValue(new Date());
		crediting.agreeAmount.setValue(applyAmount);// 设置同意申请金额
		return crediting.update(conn, " id  = " + ti);
	}

	/**
	 * 根据用户id 查询证件类型集合
	 * 
	 * @param conn
	 * @param ids
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryMeterAuthTypeListByIds(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
		DataSet dataSet = materialsauth.open(conn, "", "userId = " + id, "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 根据用户id 查询证件类型集合
	 * 
	 * @param conn
	 * @param ids
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryServiceNameByI(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_admin admin = new Dao().new Tables().new t_admin();
		DataSet dataSet = admin.open(conn, "", "", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;

	}

	/**
	 * 查询个人信息信息
	 */
	public Map<String, String> queryPerUserCredit(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_user_credit_msg msg = new Dao().new Views().new v_t_user_credit_msg();
		DataSet dataSet = msg.open(conn, "", " id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * * 查询用户认证信息
	 */
	public Map<String, String> queryPerUserrez(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_base_check msg = new Dao().new Views().new v_t_base_check();
		DataSet dataSet = msg.open(conn, "", " id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询个人的单个证件信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPerPictruMsgCallBack(Connection conn, long id, Integer materAuthTypeId, Long tmtId) throws SQLException,
			DataException {
		Dao.Views.v_t_per_picture msg = new Dao().new Views().new v_t_per_picture();
		DataSet dataSet = msg.open(conn, "", " id = " + id + " AND materAuthTypeId = " + materAuthTypeId + " AND tmdid = " + tmtId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * //后台统计待审核的用户数量为 X 个。总的待审核的认证数量为XX个
	 * 
	 * @param conn
	 * @param username
	 *            用户名
	 * @param realName
	 *            真实姓名
	 * @param auditStatus
	 *            认证状态
	 * @param serviceManName
	 *            跟踪人
	 * @param certificateName
	 *            认证类型
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryUserCreditCount(Connection conn, String username, String realName, Integer auditStatus,
			String serviceManName, Integer certificateName) throws SQLException, DataException {
		List<Map<String, Object>> lists = null;
		StringBuffer condition = new StringBuffer();
		condition.append(" 1 = 1 ");
		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND usrename  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(username.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(realName.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(serviceManName)) {
			condition.append(" AND serviceManName  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(serviceManName.trim()) + "','%')");
		}

		if (null != auditStatus && auditStatus > -1) {
			// ========
			if (certificateName != null && certificateName > -1 && certificateName == 1) {
				condition.append(" AND tmIdentityauditStatus = " + auditStatus);
			}
			// ========
			if (certificateName != null && certificateName > -1 && certificateName == 2) {
				condition.append(" AND tmworkauditStatus = " + auditStatus);
			}
			// ===========
			if (certificateName != null && certificateName > -1 && certificateName == 3) {
				condition.append(" AND tmaddressauditStatus = " + auditStatus);
			}
			// =============================
			if (certificateName != null && certificateName > -1 && certificateName == 4) {
				condition.append(" AND tmresponseauditStatus = " + auditStatus);
			}
			// ============================
			if (certificateName != null && certificateName > -1 && certificateName == 5) {
				condition.append(" AND tmincomeeauditStatus = " + auditStatus);
			}
			if (certificateName == -1) {
				condition.append(" and tmIdentityauditStatus = " + auditStatus + " or  tmworkauditStatus = " + auditStatus
						+ " or tmaddressauditStatus = " + auditStatus + "  or tmresponseauditStatus  = " + auditStatus
						+ " or tmincomeeauditStatus = " + auditStatus + " ");
			}

		}
		try {
			Dao.Views.v_t_user_auth vtcm = new Dao().new Views().new v_t_user_auth();
			// 得出结果集
			DataSet ds = vtcm.open(conn, "tmIdentityauditStatus,tmworkauditStatus,tmaddressauditStatus,tmresponseauditStatus,tmincomeeauditStatus",
					condition.toString(), "", -1, -1);
			ds.tables.get(0).rows.genRowsMap();
			lists = ds.tables.get(0).rows.rowsMap;
			condition = null;
		} finally {
			conn.close();
		}
		return lists;
	}

	/**
	 * 删除单个证件
	 * 
	 * @param tmdid
	 *            单个证件id
	 */
	public void deletecertificate(Connection conn, Long tmdid) throws SQLException {
		Dao.Tables.t_materialimagedetal materialImagedetal = new Dao().new Tables().new t_materialimagedetal();
		materialImagedetal.delete(conn, " id = " + tmdid);
	}

	/**
	 * 查询个人的图片情况 （5大证件）
	 * 
	 * @param conn
	 * @param id
	 *            用户id
	 * @return Map<String,String>
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryPerUserPicturMsg(Connection conn, Long id, Integer materAuthTypeId) throws SQLException, DataException {
		Dao.Views.v_t_user_picture_base msg = new Dao().new Views().new v_t_user_picture_base();
		StringBuffer condition = new StringBuffer();
		condition.append(" 1 = 1 ");
		if (id != null) {
			condition.append(" AND id =  " + id);
		}
		if (materAuthTypeId != -1 && materAuthTypeId != null) {
			condition.append(" AND materAuthTypeId =  " + materAuthTypeId);
		}
		DataSet dataSet = msg.open(conn, "", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;

	}

	/**
	 * 查询可选证件资料
	 * 
	 * @param conn
	 * @param id
	 * @param materAuthTypeId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> querySelectPictureDate(Connection conn, Long id, Integer materAuthTypeId) throws SQLException, DataException {
		Dao.Views.v_t_user_picture_select msg = new Dao().new Views().new v_t_user_picture_select();
		StringBuffer condition = new StringBuffer();
		condition.append(" 1 = 1 ");
		if (id != null) {
			condition.append(" AND id =  " + id);
		}
		if (materAuthTypeId != -1 && materAuthTypeId != null) {
			condition.append(" AND materAuthTypeId =  " + materAuthTypeId);
		}
		DataSet dataSet = msg.open(conn, "", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;

	}

	/**
	 * 查询用户的图片资料的审核
	 * 
	 * @param conn
	 * @param id
	 * @param materAuthTypeId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */

	/**
	 * 查询一个人的基本资料认证的情况
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryBaseDataById(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		DataSet dataSet = person.open(conn, "", " userId=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询用户的用户名
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserNameById(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet dataSet = user.open(conn, "", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询个人的工作认证情况
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryWorkDataById(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_workauth workauth = new Dao().new Tables().new t_workauth();
		DataSet dataSet = workauth.open(conn, "", " userId=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询个人上传资料时间
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryWork(Connection conn, long id) throws SQLException, DataException {
		Dao.Tables.t_workauth workauth = new Dao().new Tables().new t_workauth();
		DataSet dataSet = workauth.open(conn, "", " userId=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查看可选页面中的通过审核数据
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryselectpicture(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_user_selectpicture selectpicture = new Dao().new Views().new v_t_user_selectpicture();
		DataSet dataSet = selectpicture.open(conn, "", " id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询可选认证的积分信用情况
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querySelectCledit(Connection conn, long id) throws SQLException, DataException {
		// Dao.Tables.t_workauth workauth = new Dao().new Tables().new
		// t_workauth();
		// DataSet dataSet = workauth.open(conn, "", " userId=" + id, "", -1,
		// -1);
		Dao.Views.v_t_user_select_credit credit = new Dao().new Views().new v_t_user_select_credit();
		DataSet dataSet = credit.open(conn, "", " id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询用户的工作和信息联系认证状态
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryAllWorkStatus(Connection conn, Long userId) throws SQLException, DataException {
		DataSet dataSet = MySQL.executeQuery(conn, "select  tw.auditStatus as 'audi' from t_workauth tw where tw.userId = " + userId);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 统计新用户未分配的的个数
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querydistribute(Connection conn) throws SQLException, DataException {
		DataSet dataSet = MySQL.executeQuery(conn, "select IFNULL(COUNT(*) ,0) as cou from t_user tuser where tuser.adminId is  null");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询可选认证的list集合
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> querySelectCleditList(Connection conn, Long id) throws SQLException, DataException {
		Dao.Views.v_t_user_select_credit_last select = new Dao().new Views().new v_t_user_select_credit_last();
		StringBuffer condition = new StringBuffer();
		condition.append(" 1 = 1 ");
		if (id != null) {
			condition.append(" AND id =  " + id);
		}
		DataSet dataSet = select.open(conn, "", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;

	}

	/**
	 * 统计基本资料通过的数量
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryTotaPass(Connection conn, Long userId) throws SQLException, DataException {
		DataSet dataSet = MySQL.executeQuery(conn, "select COUNT(*) as 'total'  from t_materialsauth tm where tm.userId = " + userId
				+ " AND tm.auditStatus = 3  AND tm.materAuthTypeId <=5");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 统计可选认证的通过数量
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querySelectPassTotal(Connection conn, Long userId) throws SQLException, DataException {
		DataSet dataSet = MySQL.executeQuery(conn, "select COUNT(*) as 'total'  from t_materialsauth tm where tm.userId = " + userId
				+ " AND tm.auditStatus = 3  AND tm.materAuthTypeId > 5");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查看用户的手机详细信息
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserTelMsg(Connection conn, Long userId, Long tpiid) throws SQLException, DataException {

		Dao.Views.v_t_user_usertel credit = new Dao().new Views().new v_t_user_usertel();
		DataSet dataSet = credit.open(conn, "", " id = " + userId + " AND tpiid =" + tpiid, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);

	}

	/**
	 * 后台根据搜索来统计数量
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */

	public List<Map<String, Object>> querytelphone1(Connection conn) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select tuser.id as id, tuser.username as username, a.co as amountall,tp.realName as realName, ");
		sql.append(" tp.cellPhone as cellPhone,tpbi.requsetTime as requsetTime ");
		sql.append(" from t_phone_binding_info tpbi ");
		sql.append(" left join  t_user tuser   on tuser.id = tpbi.userId ");
		sql.append(" left join t_person tp on tuser.id = tp.userId ");
		sql.append(" left join ");
		sql.append(" (select SUM(investAmount) as co, ti.investor as tiv from t_invest ti GROUP BY ti.investor) a  ");
		sql.append(" on  a.tiv = tuser.id  limit 0 , 10 ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;

	}

	public List<Map<String, Object>> querytelphone2(Connection conn) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select tuser.id as id, tuser.username as username, a.co as amountall,tp.realName as realName, ");
		sql.append(" tp.cellPhone as cellPhone,tpbi.requsetTime as requsetTime, ");
		sql.append(" tpbi.mobilePhone as mobilePhone, ");
		sql.append(" tpbi.`status` as tpStatus ");
		sql.append(" from t_phone_binding_info tpbi ");
		sql.append(" left join  t_user tuser   on tuser.id = tpbi.userId ");
		sql.append(" left join t_person tp on tuser.id = tp.userId ");
		sql.append(" left join ");
		sql.append(" (select SUM(investAmount) as co, ti.investor as tiv from t_invest ti GROUP BY ti.investor) a  ");
		sql.append(" on  a.tiv = tuser.id  limit 0 , 10 ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;

	}

	/**
	 * 更改基本资料的审核状态
	 * 
	 * @param conn
	 * @param userId
	 *            用户id
	 * @param personId
	 *            基本资料的id
	 * @return
	 * @throws SQLException
	 */
	public Long updatePersonauditStatus(Connection conn, Long userId, Long personId, Integer auditStatus) throws SQLException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		if (null != auditStatus) {
			// 审核通过
			person.auditStatus.setValue(auditStatus);
		}
		return person.update(conn, " 1 = 1 AND userId = " + userId + " AND id = " + personId);
	}

	/**
	 * 更改用户的手机审核状态
	 * 
	 * @param conn
	 * @param userId
	 * @param auditStatus
	 *            更改状态
	 * @param option
	 *            审核意见
	 * @param newTelNumber
	 *            新号码
	 * @return
	 * @throws SQLException
	 */

	public Long updateUserPhone(Connection conn, Long userId, String newTelNumber) throws SQLException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.cellPhone.setValue(newTelNumber);
		return person.update(conn, "  userId = " + userId);
		/*
		 * Dao.Tables.t_phone_binding_info tpi = new Dao().new Tables().new
		 * t_phone_binding_info(); if (null != auditStatus && auditStatus != -1)
		 * { // 审核通过 tpi.status.setValue(auditStatus);
		 * tpi.option.setValue(option); tpi.update(conn, "  userId = " +
		 * userId); if (auditStatus == 1) {
		 * person.cellPhone.setValue(newTelNumber); result +=
		 * person.update(conn, "  userId = " + userId); } }
		 */
	}

	/**
	 * 更新和添加用户的跟踪人
	 * 
	 * @param conn
	 * @param userId
	 * @param servicePersonId
	 * @return
	 * @throws SQLException
	 */
	public Long updataUserServiceMan(Connection conn, Long userId, Integer servicePersonId) throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if (servicePersonId != -2 && userId != -1L) {
			if (userId != -1L) {
				user.adminId.setValue(servicePersonId);
				return user.update(conn, " 1 = 1 AND id = " + userId);
			}
		}
		return -1L;
	}

	/**
	 * add by houli 添加信用积分详情
	 */
	/*
	 * public Long addUserIntegralDetail(Connection conn,Long userId,String
	 * intergralType,String remark,String changeType, int changeRecord,int type)
	 * throws SQLException{ Dao.Tables.t_userintegraldetail table = new
	 * Dao().new Tables().new t_userintegraldetail();
	 * table.userid.setValue(userId);
	 * table.intergraltype.setValue(intergralType);
	 * table.remark.setValue(remark); table.changetype.setValue(changeType);
	 * table.changerecore.setValue(changeRecord); table.type.setValue(type);
	 * table.time.setValue(new Date()); return table.insert(conn); }
	 */

	/**
	 * 更新用户的工作信息的状态 必须是基本信息通过 和 工作信息 联系 合和年项目只需工作信息的状态
	 * 
	 * @param conn
	 * @param userId
	 * @param personId
	 * @param auditStatus
	 * @param directedStatus
	 * @param otherStatus
	 * @param moredStatus
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateworkStatus(Connection conn, Long userId, Integer auditStatus, Integer directedStatus, Integer otherStatus, Integer moredStatus)
			throws SQLException, DataException {
		Map<String, String> wormap = null;
		Map<String, String> personmap = null;
		Map<String, String> usermap = null;
		Long result = -1L;
		Dao.Tables.t_workauth workauth = new Dao().new Tables().new t_workauth();
		Dao.Tables.t_user use = new Dao().new Tables().new t_user();
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		try {
			DataSet userds = use.open(conn, "creditrating", " id = " + userId, "", -1, -1);
			DataSet workds = workauth.open(conn, "auditStatus,directedStatus,otherStatus,moredStatus", " userId = " + userId, "", -1, -1);
			DataSet personds = person.open(conn, "auditStatus", " userId = " + userId, "", -1, -1);
			wormap = BeanMapUtils.dataSetToMap(workds);
			personmap = BeanMapUtils.dataSetToMap(personds);
			usermap = BeanMapUtils.dataSetToMap(userds);
		} catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}

		Integer jcauditStatus = -1;
		Integer dirauditStatus = -1;
		Integer otauditStatus = -1;
		Integer moauditStatus = -1;
		Integer perauditStatus = -1;
		Integer precreditrating = -1;// 原来的信用积分
		Integer allStatus = -1;
		if (usermap != null && usermap.size() > 0) {
			precreditrating = Convert.strToInt(usermap.get("creditrating"), -1);
		}
		if (wormap != null && wormap.size() > 0 && personmap != null && personmap.size() > 0) {
			// 获取工作信息中的4个状态值
			jcauditStatus = Convert.strToInt(wormap.get("auditStatus"), -1);
			dirauditStatus = Convert.strToInt(wormap.get("directedStatus"), -1);
			otauditStatus = Convert.strToInt(wormap.get("otherStatus"), -1);
			moauditStatus = Convert.strToInt(wormap.get("moredStatus"), -1);
			perauditStatus = Convert.strToInt(personmap.get("auditStatus"), -1);// 获取person中状态值
			allStatus = jcauditStatus + dirauditStatus + otauditStatus + moauditStatus + perauditStatus;
		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" 1 = 1 ");
		if (userId != null) {
			stringBuffer.append(" AND userId = " + userId);
		}
		if (auditStatus != 1 && auditStatus != null) {
			workauth.auditStatus.setValue(auditStatus);
			if (auditStatus == 3) {
				if (allStatus >= 12) {// 当审核基本信息和 工作信息通过时候 用户的信用分增加 10 分 默认
					use.creditrating.setValue(10 + precreditrating);
					result = use.update(conn, " id = " + userId);// 更新用户的信用积分
					result = userManageDao.addserintegraldetail(conn, userId, 10, "用户工作资料审核", 1, "用户工作资料审核", "增加");
					if (result <= 0) {
						return -1L;
					}
				}
			}
		}
		/*
		 * if (directedStatus != 1 && directedStatus != null) {
		 * workauth.directedStatus.setValue(directedStatus); if (directedStatus
		 * == 3) { if (allStatus >= 12) {// 当审核基本信息和 工作信息通过时候 用户的信用分增加 10 分 默认
		 * use.creditrating.setValue(10 + precreditrating); result =
		 * use.update(conn, " id = " + userId);// 更新用户的信用积分 result =
		 * userManageDao.addserintegraldetail(conn, userId, 10, "用户基本资料审核", 1,
		 * "用户基本资料审核", "增加"); if (result <= 0) { return -1L; } } } } if
		 * (otherStatus != 1 && otherStatus != null) {
		 * workauth.otherStatus.setValue(otherStatus); if (otherStatus == 3) {
		 * if (allStatus >= 12) {// 当审核基本信息和 工作信息通过时候 用户的信用分增加 10 分 默认
		 * use.creditrating.setValue(10 + precreditrating); result =
		 * use.update(conn, " id = " + userId);// 更新用户的信用积分 result =
		 * userManageDao.addserintegraldetail(conn, userId, 10, "用户基本资料审核", 1,
		 * "用户基本资料审核", "增加"); if (result <= 0) { return -1L; } } } } if
		 * (moredStatus != 1 && moredStatus != null) {
		 * workauth.moredStatus.setValue(moredStatus); if (moredStatus == 3) {
		 * if (allStatus >= 12) {// 当审核基本信息和 工作信息通过时候 用户的信用分增加 10 分 默认
		 * use.creditrating.setValue(10 + precreditrating); result =
		 * use.update(conn, " id = " + userId);// 更新用户的信用积分 result =
		 * userManageDao.addserintegraldetail(conn, userId, 10, "用户基本资料审核", 1,
		 * "用户基本资料审核", "增加"); if (result <= 0) { return -1L; } } } }
		 */
		return workauth.update(conn, stringBuffer.toString());

	}

	/**
	 * 审核用户的证件状态
	 * 
	 * @param conn
	 * @param mtdId
	 *            证件明细id
	 * @param userId
	 *            用户id
	 * @param materAuthTypeId
	 *            证件类型id
	 * @param option
	 *            审核意见
	 * @param auditStatus
	 *            审核状态
	 * @param materaldetalId
	 *            证件主表id
	 * @param visiable
	 *            后台管理员是否可见
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 * @throws DataException
	 */
	public Long Updatematerialsauth(Connection conn, Long mtId, Long userId, Long materAuthTypeId, String option, Integer auditStatus,
			Long materaldetalId, Integer visiable) throws SQLException, ParseException, DataException {
		Dao.Tables.t_materialimagedetal materialImagedetal = new Dao().new Tables().new t_materialimagedetal();
		materialImagedetal.option.setValue(option);
		materialImagedetal.auditStatus.setValue(auditStatus);
		Long result1 = -1L;
		materialImagedetal.visiable.setValue(visiable);
		materialImagedetal.checktime.setValue(new Date());
		result1 = materialImagedetal.update(conn, " materialsauthid = " + mtId + " AND id = " + materaldetalId);
		if (result1 <= 0) {
			return -1L;
		}
		return result1;
	}

	/**
	 * 更新信用积分
	 * 
	 * @param conn
	 * @param userId
	 *            用户id
	 * @param alloption
	 *            审核意见
	 * @param creditrating
	 *            信用积分
	 * @param adminId
	 *            审核员id
	 * @param mterType
	 *            证件类型
	 * @param auditStatus
	 *            证件
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Long Updatecreditrating(Connection conn, Long userId, String alloption, Integer creditrating, Long adminId, Integer mterType,
			Integer checkStatus) throws SQLException, Exception {

		Long resut1 = -1L;
		Long resut2 = -1L;
		Integer precreditrating = -1;// 原来的总信用积分
		Integer preTyperating = -1;// 原来的证件单个信用积分
		Dao.Tables.t_user user = null;
		Dao.Tables.t_materialsauth materialsauth = null;
		DataSet ds = null;
		DataSet mads = null;
		boolean flag = false;
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> nmap = new HashMap<String, String>();
		try {
			user = new Dao().new Tables().new t_user();
			materialsauth = new Dao().new Tables().new t_materialsauth();

			ds = user.open(conn, "creditrating", " id = " + userId, "", -1, -1);
			mads = materialsauth.open(conn, "criditing,auditStatus", " userId = " + userId + " AND materAuthTypeId = " + mterType, "", -1, -1);
			map = BeanMapUtils.dataSetToMap(ds);
			nmap = BeanMapUtils.dataSetToMap(mads);
		} catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		if (map != null && map.size() > 0 && nmap.size() > 0 && nmap != null) {
			precreditrating = Convert.strToInt(map.get("creditrating"), 0);
			Convert.strToInt(nmap.get("auditStatus"), -1);
			preTyperating = Convert.strToInt(nmap.get("criditing"), 0);
		} else {
			return -1L;
		}

		if (mterType != null) {
			if (checkStatus == 3) {
				materialsauth.criditing.setValue(creditrating + preTyperating);// 当前信用积分加上传来的信用积分
				materialsauth.option.setValue(alloption);
				materialsauth.authTime.setValue(new Date());// 审核时间
				materialsauth.auditStatus.setValue(3);
				resut1 = materialsauth.update(conn, " userId = " + userId + " AND materAuthTypeId = " + mterType);
				if (resut1 > 0) {
					flag = true;
				} else {
					flag = false;
				}
			} else if (checkStatus == 2) {
				materialsauth.option.setValue(alloption);
				materialsauth.authTime.setValue(new Date());// 审核时间
				materialsauth.auditStatus.setValue(2);
				resut1 = materialsauth.update(conn, " userId = " + userId + " AND materAuthTypeId = " + mterType);
				if (resut1 > 0) {
					flag = true;
				} else {
					flag = false;
				}
			}

		}
		if (creditrating != null && creditrating != -1 && precreditrating != -1) {
			user.creditrating.setValue(creditrating + precreditrating);// 当前的信用积分加上后台添加的信用分数
			resut2 = user.update(conn, " id = " + userId);// 更新信用分
			if (resut2 > 0) {
				flag = true;
			} else {
				flag = false;
			}
		}

		if (flag) {
			return 1L;
		} else {
			return -1L;
		}

	}

	/**
	 * 总审核时候想审核记录表插入记录
	 * 
	 * @param creditrating
	 *            修改分数
	 * @param adminId
	 *            审核员id
	 * @param userId
	 *            用户id
	 * @param mterType
	 *            证件类型
	 * @param cCreditration
	 *            更新前的信用积分
	 * @return
	 * @throws SQLException
	 */
	public Long addCheckRecord(Connection conn, Integer creditrating, Long adminId, Long userId, Integer mterType, Integer cCreditration)
			throws SQLException {
		Long result = -1L;
		Dao.Tables.t_user_check check = new Dao().new Tables().new t_user_check();// 审核员的审核记录更新
		check.checkdate.setValue(new Date());// 设置审核时间
		// 查询t_materialsauth表中信用积分
		Map<String, String> map = null;
		int precreditrating = -1;
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
		try {
			DataSet ds = materialsauth.open(conn, "criditing", " userId =" + userId + " AND materAuthTypeId = " + mterType, "", -1, -1);
			map = BeanMapUtils.dataSetToMap(ds);
		} catch (DataException e) {
			e.printStackTrace();
			return -1L;
		}
		if (map.size() > 0 && map != null) {
			precreditrating = Convert.strToInt(map.get("criditing"), -1);
		}
		if (precreditrating != -1) {
			check.perrecode.setValue(cCreditration);// 设置原来的信用分数
			check.afterrecode.setValue(cCreditration + creditrating);
			check.adminId.setValue(adminId);// 设置审核者的id
			check.userId.setValue(userId);
			check.materialType.setValue(mterType);// 插入用户审核的类型
			return check.insert(conn);// 插入审核员的id
		}
		return result;

	}

	/**
	 * 添加学历认证扣费记录
	 * 
	 * @param conn
	 * @param userId
	 * @param educationFree
	 * @return
	 * @throws SQLException
	 */
	public Long addeducationcost(Connection conn, Long userId, String educationFree) throws SQLException {
		Dao.Tables.t_education_cost education = new Dao().new Tables().new t_education_cost();
		BigDecimal vipFeedecimal = new BigDecimal(educationFree);
		education.freeEducation.setValue(vipFeedecimal);
		education.userId.setValue(userId);
		return education.insert(conn);
	}

	/**
	 * 查询管理员审核记录
	 * 
	 * @param conn
	 * @param ids
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAdminCheckList(Connection conn, Long userId, Integer materAuthTypeIdStr) throws SQLException, DataException {
		Dao.Views.v_t_user_adminchecklist adminchecklist = new Dao().new Views().new v_t_user_adminchecklist();
		DataSet dataSet = adminchecklist.open(conn, "", " 1 = 1 AND userId = " + userId + " AND materialType = " + materAuthTypeIdStr, "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 批量审核
	 * 
	 * @param conn
	 * @param ids
	 * @param admins
	 * @return
	 * @throws SQLException
	 */
	public Long updataUserServiceMans(Connection conn, String ids, String admins) throws SQLException {
		String[] allIds = ids.split(",");
		String[] allAdmins = admins.split(",");
		int count = 0;
		long result = -1L;
		for (int i = 0, n = allIds.length; i < n; i++) {
			result = Database.executeNonQuery(conn, " update t_user set adminId = " + allAdmins[i] + " where id=" + allIds[i]);
			if (result > 0) {
				count++;
			}
		}
		return count == allIds.length ? 1L : -1L;
	}

	public void setUserManageDao(UserManageDao userManageDao) {
		this.userManageDao = userManageDao;
	}

	public UserManageDao getUserManageDao() {
		return userManageDao;
	}

	/**
	 * 和合年 项目 后台统计待审核的用户数量为 X 个。总的待审核的认证数量为XX个
	 * 
	 * @param conn
	 * @param username
	 *            用户名
	 * @param realName
	 *            真实姓名
	 * @param phone
	 *            手机号码
	 * @param idNo
	 *            身份证
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryUserCreditCount(Connection conn, String username, String realName, String phone, String idNo)
			throws Exception {
		List<Map<String, Object>> lists = null;
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(username)) {
			condition.append(" AND usrename  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(username.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" AND realName  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(realName.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(phone)) {
			condition.append(" AND mobilePhone  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(phone.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(idNo)) {
			condition.append(" AND idNo  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(idNo.trim()) + "','%')");
		}
		try {
			// 工作信息认证和必填认证信息
			Dao.Views.v_t_user_auth vtcm = new Dao().new Views().new v_t_user_auth();
			// 必填认证 ,个人信息认证,工作认证
			String fieldList = "tmIdentityauditStatus,tmworkauditStatus,tmaddressauditStatus,tmresponseauditStatus,tmincomeeauditStatus,tpauditStatus,twauditStatus";
			DataSet ds = vtcm.open(conn, fieldList, condition.toString(), "", -1, -1);
			ds.tables.get(0).rows.genRowsMap();
			lists = ds.tables.get(0).rows.rowsMap;
		} finally {
			conn.close();
		}
		return lists;
	}

	public long updateAuditStatus(Connection conn, long userId, int authStep) throws SQLException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		t_user.authStep.setValue(authStep);
		return t_user.update(conn, " id=" + userId + "  and authStep<=" + authStep);
	}

}
