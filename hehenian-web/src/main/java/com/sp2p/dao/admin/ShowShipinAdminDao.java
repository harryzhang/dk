package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class ShowShipinAdminDao {
	/**
	 * 更新视频认证的审核状态
	 * 
	 * @param conn
	 * @param tmid
	 *            证件主表id
	 * @param status
	 *            审核状态
	 * @return
	 * @throws SQLException
	 */
	public Long updateMa(Connection conn, Long tmid, int status)
			throws SQLException {
		Dao.Tables.t_materialsauth tm = new Dao().new Tables().new t_materialsauth();
		tm.auditStatus.setValue(status);
		return tm.update(conn, " id = " + tmid);
	}

	/**
	 * 更新或者插入视频资料审核明细表中
	 * 
	 * @param conn
	 * @param tmid
	 *            证件主表id
	 * @param tmtype
	 *            证件类型
	 * @param status
	 *            审核状态
	 * @param flag
	 *            判断是否插入或者更新
	 * @param tmdid
	 *            证件主表下的明细表
	 * @return
	 * @throws SQLException
	 */
	public Long updateMade(Connection conn, Long tmid, Long tmtype, int status,
			boolean flag, Long tmdid) throws SQLException {
		Dao.Tables.t_materialimagedetal tmd = new Dao().new Tables().new t_materialimagedetal();
		tmd.uploadingTime.setValue(new Date());
		tmd.auditStatus.setValue(status);
		tmd.materialsauthid.setValue(tmid);
		if (flag) {
			return tmd.insert(conn);
		} else {
			return tmd.update(conn, " id =  " + tmdid);
		}
	}

	/**
	 * 查询视频资料审核明细表中
	 * 
	 * @param tmid
	 *            资料主表id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryMade(Connection conn, Long tmid)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ");
		sql.append(" tmd.id as tmdid ");
		sql.append(" from  ");
		sql.append(" t_materialimagedetal tmd ");
		sql.append(" where tmd.materialsauthid =  " + tmid);
		sql.append(" LIMIT  0 , 1 ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql=null;
		return BeanMapUtils.dataSetToMap(dataSet);

	}

	public Long updateMa1(Connection conn, Long tmid, int status,String content,Integer sro)
			throws SQLException {
		Dao.Tables.t_materialsauth tm = new Dao().new Tables().new t_materialsauth();
		tm.auditStatus.setValue(status);
		tm.authTime.setValue(new Date());
		tm.option.setValue(content);
		tm.criditing.setValue(sro);
		return tm.update(conn, " id = " + tmid);
	}
    /**
     * 查询证件主表的审核状态
     * @param conn
     * @param tmid
     * @param status
     * @param content
     * @param sro
     * @return
     * @throws SQLException
     * @throws DataException 
     */
	public Map<String, String>  querydateMa1(Connection conn, Long tmid)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select tm.auditStatus as auditStatus from t_materialsauth tm where tm.id = "+tmid);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	
	/**
	 * 添加信用总分
	 * @param conn
	 * @param userId
	 * @param alloption
	 * @param creditrating
	 * @param adminId
	 * @param mterType
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Long Updatecreditrating(Connection conn, Long userId,
			String alloption, Integer creditrating,
			Integer mterType,int status) throws SQLException, Exception {
		
		Long resut1 = -1L;
		Long resut2 = -1L;
		
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
		DataSet ds = user.open(conn, "creditrating", " id = " + userId, "", -1,
				-1);
		DataSet mads = materialsauth.open(conn, "auditStatus", " userId = " + userId
				+ " AND materAuthTypeId = " + mterType, "", 0, -1);

		Integer precreditrating = -1;// 原来的信用积分
		Integer auditStatus = -1;
		boolean flag = false;
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> nmap = new HashMap<String, String>();
		map = BeanMapUtils.dataSetToMap(ds);
		nmap = BeanMapUtils.dataSetToMap(mads);
		if (map != null && map.size() > 0&&nmap.size()>0&&nmap!=null) {
			precreditrating = Convert.strToInt(map.get("creditrating"), -1);
			auditStatus = Convert.strToInt(nmap.get("auditStatus"), -1);
		}else{
			return -1L;
		}

		if (StringUtils.isNotBlank(alloption) && mterType != null
				&& alloption != null) {// 向证件类型主表更新数据

			// 设置失效时间
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String authTime = format.format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(format.parse(authTime));
			cal.add(Calendar.DATE, 30);
			String passDate = null;
			passDate = format.format(cal.getTime());
			if (passDate != null && auditStatus == 2 && auditStatus != -1) {
				materialsauth.pastTime.setValue(passDate);
			}
			// =====
			materialsauth.criditing.setValue(creditrating);
			materialsauth.option.setValue(alloption);
			materialsauth.auditStatus.setValue(status);
			resut1 = materialsauth.update(conn, " userId = " + userId
					+ " AND materAuthTypeId = " + mterType);
			if(resut1>0){
				flag = true;
			}else{
				flag = false;
			}
		 }
			if (creditrating != null && creditrating != -1
					&& precreditrating != -1) {
				user.creditrating.setValue(creditrating + precreditrating);// 当前的信用积分加上后台添加的信用分数
				resut2 =  user.update(conn, " id = " + userId);// 更新信用分
				if(resut2>0){
					flag = true;
				}else{
					flag = false;
				}
			}
			map=null;
			nmap=null;
			ds=null;
			mads=null;
			if(flag){
				return 1L;
			}else{
				return -1L;
			}
			

	}

	public Long addCheckRecord(Connection conn, Integer creditrating,
			Long adminId, Long userId, Integer mterType) throws SQLException {
		Long result = -1L;
		Dao.Tables.t_user_check check = new Dao().new Tables().new t_user_check();// 审核员的审核记录更新
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = format.format(new Date());// 
		check.checkdate.setValue(date);// 设置审核时间
		// 查询t_materialsauth表中信用积分
		Map<String, String> map = null;
		int precreditrating = -1;
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
		try {
			DataSet ds = materialsauth
					.open(conn, "criditing", " userId =" + userId
							+ " AND materAuthTypeId = " + mterType, "", -1, -1);
			map = BeanMapUtils.dataSetToMap(ds);
		} catch (DataException e) {
			e.printStackTrace();
		}
		if (map.size() > 0 && map != null) {
			precreditrating = Convert.strToInt(map.get("criditing"), -1);
		}
		if (precreditrating != -1) {
			check.perrecode.setValue(precreditrating);// 设置原来的信用分数
			check.afterrecode.setValue(precreditrating + creditrating);
			check.adminId.setValue(adminId);// 设置审核者的id
			check.userId.setValue(userId);
			check.materialType.setValue(mterType);// 插入用户审核的类型
			return check.insert(conn);// 插入审核员的id
		}
		return result;

	}
	/**
	 * 资料认证统计查询
	 * @param conn
	 * @param typeid 类型种类
	 * @param userId 用户id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryCountMsg(Connection conn, Long typeid,Long userId)
	throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tuser.id  as id, ");
		sql.append(" tuser.username as username, ");
		sql.append(" ty.`name` as tyname, ");
		sql.append(" tm.`option` as tmoption ");
		sql.append(" from t_user tuser  ");
		sql.append(" left join t_materialsauth tm on tm.userId = tuser.id   ");
		sql.append(" left join t_materialsauthtype ty on tm.materAuthTypeId = ty.id     ");
		sql.append(" where tuser.id =  "+userId);
		sql.append(" and tm.materAuthTypeId =  "+typeid);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql=null;
        return BeanMapUtils.dataSetToMap(dataSet);
      }
	/**
	 * 资料认证统计图片类表
	 * @param conn
	 * @param typeid
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryCountPictureList(Connection conn,Long tmid) throws SQLException, DataException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tmd.id as tmdid, ");
		sql.append(" tmd.imagePath as imagePath ");
		sql.append(" from t_materialimagedetal tmd ");
		sql.append(" WHERE tmd.materialsauthid =  "+tmid);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql=null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
	 * 查询materaldetal的id
	 */
	public Map<String, String> querytmid(Connection conn, Long typeid,Long userId)
	throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tm.id  as id  ");
		sql.append(" from t_materialsauth tm  ");
		sql.append(" where tm.userId = "+userId);
		sql.append(" and tm.materAuthTypeId = "+typeid);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql=null;
        return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询图片信息和审核情况
	 * @param conn
	 * @param tmdid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryonemsg(Connection conn, Long tmdid)
	throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tmd.`option` as  tmdoption,  ");
		sql.append(" tmd.auditStatus as auditStatus,  ");
		sql.append(" tmd.imagePath as imagePath,");
		sql.append(" tmd.visiable as visiable ");
		sql.append(" from t_materialimagedetal tmd where tmd.id =  "+tmdid);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql=null;
        return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 查询用户名称
	 * @param conn
	 * @param tmdid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryuser(Connection conn, Long id)
	throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tuser.username  as username  ");
		sql.append(" from t_user tuser  ");
		sql.append(" where tuser.id = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql=null;
        return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	
	

}
