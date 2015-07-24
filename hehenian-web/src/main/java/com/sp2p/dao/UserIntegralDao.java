package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Views;
import com.sp2p.database.Dao.Tables.t_materialsauth;
import com.sp2p.database.Dao.Views.v_t_user_credit_apply_msgas;
/**
 * 积分明细
 * @author Administrator
 *
 */
public class UserIntegralDao {
	/**
	 * 添加用户积分明细记录
	 * @return
	 * @throws SQLException 
	 */
	
/*	public Long addUserIntegral(Connection conn,Long userid,String intergraltype,String remark,String changetype,String changerecore,String time,Integer type ) throws SQLException{
		Dao.Tables.t_userintegraldetail userintegraldetail = new Dao().new Tables().new t_userintegraldetail();
		userintegraldetail.userid.setValue(userid);
		userintegraldetail.intergraltype.setValue(intergraltype);
		userintegraldetail.remark.setValue(remark);
		userintegraldetail.changerecore.setValue(changerecore);
		userintegraldetail.changetype.setValue(changetype);
		userintegraldetail.time.setValue(new Date());
		userintegraldetail.type.setValue(type);
		return userintegraldetail.insert(conn);
	}*/
	/**
	 * 前台用户查询用户的vip记录
	 * @param conn
	 * @param userid 用户id
	 * @param type 积分类型
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryUserIntegral(Connection conn,
			Long userid, int type ) throws SQLException,
			DataException {
		Dao.Tables.t_userintegraldetail userintegraldetail = new Dao().new Tables().new t_userintegraldetail();
		DataSet dataSet = userintegraldetail.open(conn, "*", " userid = "+userid+" AND type = "+ type, "", -1,
				-1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public Map<String, String> queryUserIntegral2(Connection conn,
			long userid, int type,String intergraltype) throws SQLException,
			DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT min(id) as minId,changerecore FROM t_userintegraldetail WHERE 1=1 AND userid="+userid+" AND type=2 AND intergraltype = \""+intergraltype+"\"");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
		
	}
    /**
     * 	
	 * 向t_user表添加用户积分 
	 *
     * @param conn
     * @param id
     * @param score 添加会员登录积分 
     * @param prescore 用户之前的积分
     * @return
     * @throws SQLException 
     */
	public Long UpdateUserRating(Connection conn,Long id,int score,int prescore) throws SQLException{
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.rating.setValue((score+prescore));
		return  user.update(conn, " id = "+id);
	}
	/**
     * 	
	 * 向t_userintegraldetail表添加用户积分明细
	 *
     * @param conn
     * @param id
     * @param score 添加会员登录积分 
     * @param prescore 用户之前的积分
     * @return
     * @throws SQLException 
     */
	public Long updateUserIntegral(Connection conn,long changerecore,long score,long id) throws SQLException{
		Dao.Tables.t_userintegraldetail user = new Dao().new Tables().new t_userintegraldetail();
		user.changerecore.setValue((score+changerecore));
		user.time.setValue(new Date());
		return  user.update(conn, " id = "+id);
	}
	
	
	/**
	 * 插入用户登录日志表
	 * 
	 */
	public Long addUserLoginLog(Connection conn,Long userid ) throws SQLException{
		Dao.Tables.user_login_log userintegraldetail = new Dao().new Tables().new user_login_log();
		userintegraldetail.userid.setValue(userid);
		userintegraldetail.logindate.setValue(new Date());
		return userintegraldetail.insert(conn);
	}
	/**
	 * 查询用户登录日志表中这个用户在这一天的登录记录时候有多少次
	 */
	public Map<String, String> queryUserLoginLong(Connection conn, long id)
	throws SQLException, DataException {
		DataSet dataSet = MySQL
		.executeQuery(
				conn,
				"SELECT IFNULL(COUNT(*),0) as cl,ulog.userid as id from user_login_log ulog where ulog.logindate = DATE(NOW()) AND ulog.userid = "+id);
                return BeanMapUtils.dataSetToMap(dataSet);
      }
	}
