package com.hehenian.biz.dal.individualCenter.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hehenian.biz.common.individualCenter.BeanMapUtils;
import com.hehenian.biz.common.individualCenter.Dao;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
@Repository
public class UserManageDao {
   
	/**
	 * 用户基本信息里面的查看用户的基本信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserManageInnerMsg(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_usermanage_baseinfoinner baseinfoinner = new Dao().new Views().new v_t_usermanage_baseinfoinner();

		DataSet dataSet = baseinfoinner.open(conn, "", " id = " + id, "",
				-1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
  /**
   * 弹出框显示信息初始化
   * @param conn
   * @param userId 用户id
   * @return
   * @throws SQLException
   * @throws DataException
   */
	public Map<String,String> queryUserManageaddInteral(Connection conn,Long userId) throws SQLException, DataException{
		DataSet dataSet = MySQL.executeQuery(conn, "select  tuser.id,tuser.username as username,tuser.creditrating as creditrating,tuser.rating as rating ,tp.realName as realName  from t_user tuser left join t_person tp on tuser.id = tp.userId where tuser.id = "+userId);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String,String> queryUserInfo(Connection conn,long userId) throws SQLException, DataException{
		DataSet dataSet = MySQL.executeQuery(conn, "select tuser.id as id,tuser.username as username,tuser.creditrating as creditrating,tuser.rating as rating ,tuser.createTime as createTime,tp.realName as realName,tp.qq as qq,tuser.email as email,tuser.lastIP as lastIP,tp.cellPhone as cellPhone  from t_user tuser left join t_person tp on tuser.id = tp.userId where tuser.id = "+userId);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Long updateUserqq(Connection conn,Long userId,String qq){
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();		
		try {
			person.qq.setValue(qq);
			return  person.update(conn, " userId = "+userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1L;
	}

	/**
	 *  * 向user表插入数据
	 * @param conn
	 * @param userId
	 * @param score
	 * @param type
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addUserManageaddInteral(Connection conn,Long userId,Integer score,Integer type) throws SQLException, DataException{
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Map<String,String> userMap = null;
		DataSet dataSet = user.open(conn, "creditrating,rating", " id = "+userId, "", -1, -1);
		userMap = BeanMapUtils.dataSetToMap(dataSet);
		Integer precreditrating = null;
		Integer prerating = null;
		if(userMap!=null&&userMap.size()>0){
			precreditrating = Convert.strToInt(userMap.get("creditrating"), -1) ;
			prerating = Convert.strToInt(userMap.get("rating"), -1) ;
			if(precreditrating!=-1&&type==1){
				user.creditrating.setValue(precreditrating+score);
			     return  user.update(conn, " id = "+userId);
			}
			if(prerating!=-1&&type==2){
				user.rating.setValue(prerating+score);
				return  user.update(conn, " id = "+userId);
			}
		}
		return -1L;
	}
	
	
	/**
	 * 向积分记录表添加记录
	 * @param conn
	 * @param userId
	 * @param score
	 * @param type
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addserintegraldetail(Connection conn,Long userId,Integer score,String typeStr,Integer type,String remark,String changetype) throws SQLException, DataException{
		Dao.Tables.t_userintegraldetail  integraldetail = new Dao().new Tables().new t_userintegraldetail();
		integraldetail.changerecore.setValue(score);
		integraldetail.intergraltype.setValue(typeStr);
		integraldetail.remark.setValue(remark);
		integraldetail.changetype.setValue(changetype);//先设置成增加
		integraldetail.time.setValue(new Date());
		integraldetail.userid.setValue(userId);
		if(type==1){//信用积分
			integraldetail.type.setValue(1);
		}
		if(type==2){//vip积分
			integraldetail.type.setValue(2);
		}
		
		return  integraldetail.insert(conn);
	}
	
	
	
	/**
	 * add by houli 查询用户资金信息
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryUserCashInfo(Connection conn,Long userId) throws SQLException, DataException{
		String sqlStr = "SELECT (usableSum+freezeSum) as totalSum,usableSum from t_user where id="+userId;
		DataSet dataSet = MySQL.executeQuery(conn, sqlStr);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	public List<Map<String, Object>> queryUserList(Connection conn) throws Exception{
		Dao.Tables.t_user  user = new Dao().new Tables().new t_user();
		DataSet dataSet = user.open(conn, "", "", "  ", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		conn.close();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	

}
