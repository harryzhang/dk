package com.sp2p.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 成为vip
 * @author Administrator
 *
 */
public class BeVipDao {
	/**
	 * 查询用户的手机
	 */
	public Map<String, String>  queryIsPhone(Connection conn , String phone) throws SQLException, DataException{
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		DataSet dataSet  = 	person.open(conn, "cellPhone", " cellPhone = "+ StringEscapeUtils.escapeSql(phone), "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 * t_user  表中查询用户手机号码
	 * @param conn
	 * @param mobilePhone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String>  queryIsPhoneonUser(Connection conn , String mobilePhone) throws SQLException, DataException{
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet dataSet  = 	user.open(conn, "mobilePhone", " mobilePhone = '"+StringEscapeUtils.escapeSql(mobilePhone.trim())+"'","" , -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 去person表查询基本信息的记录
	 * @param id 用户id
	 */
	public Map<String, String>  queryPUser(Connection conn , Long id) throws SQLException, DataException{
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		DataSet dataSet  = 	person.open(conn, "id,cellphone,idNo", " userId = "+id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 更改手机绑定表中审核状态
	 * @throws SQLException 
	 */
	public Long updatePhoneBanding(Connection conn,Long id,int Status) throws SQLException
	{	
		Dao.Tables.t_phone_binding_info phoneInfo = new Dao().new Tables().new t_phone_binding_info();
		phoneInfo.status.setValue(Status);
		return  phoneInfo.update(conn, " userId = "+id);
	}
	
	
	/**
	 * 从手机绑定表中 更改手机号码的绑定状态
	 * 
	 * @param tpiid 手机申请变更的id
	 */
	public Long delectPhone(Connection conn,Long tpiid, int Statuas ,String option) throws SQLException
	{	
		Dao.Tables.t_phone_binding_info phoneInfo = new Dao().new Tables().new t_phone_binding_info();
		phoneInfo.status.setValue(Statuas);
		phoneInfo.option.setValue(option);
		return  phoneInfo.update(conn, " id = "+tpiid);
	}
	
	/**
	 * 查询vip页面状态参数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryVipParamList(Connection conn, long id)
			throws SQLException, DataException {
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						"SELECT tuk.id as kfid, tuser.id as id ,tuser.username as username,tuser.email as email,tuser.vipStatus as vipStatus,tp.realName as realName,tuk.`name` as kefuname from t_user tuser LEFT join t_person tp on tuser.id = tp.userId left join t_user_kefu tuk on tuser.kefuId = tuk.id where tuser.id = "
								+ id);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserById(Connection conn, long id)
	throws SQLException, DataException {
   Dao.Tables.t_user user = new Dao().new Tables().new t_user();
  DataSet dataSet = user.open(conn, "*", " id=" + id, "", -1, -1);
  return BeanMapUtils.dataSetToMap(dataSet);
}
	

	// 更新用户的vip状态
	public Long updateUser(Connection conn, Long uerId, 
			int vipStatus, int servicePersonId, String content, String vipFee,int authStep)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.kefuId.setValue(servicePersonId);
		user.content.setValue(content);
		user.vipCreateTime.setValue(new Date());
		user.vipStatus.setValue(2);// 修改vip状态 2为vip状态
		BigDecimal vipFeedecimal = new BigDecimal(vipFee);
		user.vipFee.setValue(vipFeedecimal);
		//-------modify by houli 申请vip操作的时候，feeStatus=1 1代表未扣费  2代表已扣费
		//user.feeStatus.setValue(2);//为扣费用
		user.feeStatus.setValue(1);
		//--end----
		if(authStep == 3){
			user.authStep.setValue(4) ;	
		}

		return user.update(conn, "id = " + uerId);
	}

/**
 * 更新用户的认证状态
 * @param conn
 * @param uerId
 * @param austept
 * @return
 * @throws SQLException 
 * @throws SQLException
 * @throws DataException
 */
	public Long updateUserAustep(Connection conn, Long id,Integer austept) throws SQLException
			 {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
        user.authStep.setValue(austept);
        return user.update(conn, " id = "+id);
	}
	
	
	
	
	/**
	 * add by houli 查询身份证
	 * @param conn
	 * @param phone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String>  queryIDCard(Connection conn , String idCard) throws SQLException, DataException{
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		DataSet dataSet  = 	person.open(conn, " idNo ", " idNo = '"+StringEscapeUtils.escapeSql(idCard)+"'", "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	
	
	

}
