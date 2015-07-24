package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.util.DBReflectUtil;

public class SearchBasicInfoDao {
	/**
	 * 添加邮件
	 * @param conn
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public long addMail(Connection conn, Map<String, String> paramMap)
			throws SQLException {
		Dao.Tables.t_mail table = new Dao().new Tables().new t_mail();
		DBReflectUtil.mapToTableValue(table, paramMap);
		return table.insert(conn);

	}

	/**
	 * 查找用户要发送的手机号码
	 * 
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getUserMobile(Connection conn, Long userId)
			throws SQLException, DataException {
		// 首先查找用户有没有进行手机绑定
		Dao.Tables.t_phone_binding_info table = new Dao().new Tables().new t_phone_binding_info();
		DataSet dataSet = table.open(conn, "mobilePhone", " userId=" + userId
				+ " and status=" + IConstants.PHONE_BINDING_ON, "", -1, -1);

		Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);

		// 如果没有进行手机绑定,查找用户基本信息表里面的手机号码
		if (map == null || map.size() <= 0) {

			Dao.Tables.t_person tt = new Dao().new Tables().new t_person();
			DataSet ds = tt.open(conn, "cellPhone", " userId=" + userId, "",
					-1, -1);
			map = BeanMapUtils.dataSetToMap(ds);
			if (map != null) {
				map.put("mobilePhone", map.get("cellPhone"));
			}
			return map;

		} else {
			return map;
		}
	}

	/**
	 * 根据Id获取短信接口信息详情
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getSMSById(Connection conn, Integer id)
			throws SQLException, DataException {
		Dao.Tables.t_sms sms = new Dao().new Tables().new t_sms();
		DataSet dataSet = sms.open(conn, "*", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 根据用户id查找用户信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getUserById(Connection conn, Long id)
			throws SQLException, DataException {
		Dao.Tables.t_user table = new Dao().new Tables().new t_user();
		DataSet dataSet = table.open(conn, "*", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryUserUsableSum
	 * @Param: SearchBasicInfoDao
	 * @Author: gang.lv
	 * @Date: 2013-4-19 上午10:58:26
	 * @Return:
	 * @Descb: 查询用户的可用金额
	 * @Throws:
	 */
	public Map<String, String> queryUserUsableSum(Connection conn, Long id)
			throws SQLException, DataException {
		Dao.Tables.t_user table = new Dao().new Tables().new t_user();
		DataSet dataSet = table.open(conn, "*", " usableSum >=0 and id=" + id,
				"", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String, String> queryUserAmount(Connection conn, Long id)
			throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select a.usableSum,a.freezeSum,a.lastIP  as lastIP ,sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest) dueinSum");
		command.append(" from t_user a left join t_invest b on a.id = b.investor where a.id="+id+" group by a.id");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: deductedNoteFee
	 * @Param: SearchBasicInfoDao
	 * @Author: gang.lv
	 * @Date: 2013-4-19 上午11:22:34
	 * @Return:
	 * @Descb: 扣除短信服务费
	 * @Throws:
	 */
	public long deductedNoteFee(Connection conn, Long userId)
			throws SQLException {
		long returnId = -1;
		String command = " update t_user set usableSum =usableSum-0.1 where id="
				+ userId;
		returnId = MySQL.executeNonQuery(conn, command);
		command = null;
		return returnId;
	}

}
