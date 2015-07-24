/**
 * 
 */
package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_recharge_detail;
import com.sp2p.util.DBReflectUtil;

/**
 * 充值详细表
 * @author Administrator
 *
 */
public class RechargeDetailDao {
	
	/**
	 * 添加充值详细表
	 * @param conn
	 * @param paramMap 参数值
	 * @return
	 * @throws SQLException
	 */
	public long addRechargeDetail(Connection conn, Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_recharge_detail t_recharge_detail = new Dao().new Tables().new t_recharge_detail();
		DBReflectUtil.mapToTableValue(t_recharge_detail, paramMap);
		return t_recharge_detail.insert(conn);
		
	}
	
	
	/**
	 * 查询充值详细表by id
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> queryupdateRechargeDetailById(Connection conn,long id) throws SQLException, DataException{
		DataSet dataSet = MySQL
		.executeQuery(
				conn,
				" select a.award as award,a.userId as userId,a.id as id,a.rechargeMoney as rechargeMoney,a.result as result,a.rechargeNumber as  rechargeNumber,a.rechargeTime as rechargeTime," +
				" a.rechargeType as rechargeType,a.remark as remark,b.username as username,	b.mobilePhone as mobilePhone,c.realName as realName,c.idNo as idNo" +
				"  from t_recharge_detail a left join t_user b on a.userId = b.id left join t_person c on a.userId = c.userId  where a.id = "+id+" and a.rechargeType = 4 ");
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	
	/**
	 * 修改充值详细表
	 * @param conn
	 * @param paramMap 参数值
	 * @return
	 * @throws SQLException
	 */
	public long updateRechargeDetail(Connection conn, long id,Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_recharge_detail t_recharge_detail = new Dao().new Tables().new t_recharge_detail();
		DBReflectUtil.mapToTableValue(t_recharge_detail, paramMap);
		return t_recharge_detail.update(conn, " result=0 and id=" +id);
		
	}
	
	/**
	 * 删除充值详细表，可删除多个
	 * @param conn
	 * @param ids id字符串，用,隔开
	 * @return
	 * @throws SQLException
	 */
	public long deleteRechargeDetail(Connection conn, String ids) throws SQLException{
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_recharge_detail t_recharge_detail = new Dao().new Tables().new t_recharge_detail();
		return t_recharge_detail.delete(conn, " id in(" + idSQL + ")");
	}
	
	/**
	 * 根据ID获充值详细表信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getRechargeDetail(Connection conn, long id) throws SQLException, DataException{
		Dao.Tables.t_recharge_detail t_recharge_detail = new Dao().new Tables().new t_recharge_detail();
		DataSet ds = t_recharge_detail.open(conn, "*", "id="+id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	
	/**
	 * 根据充值ID删除记录
	 * @param conn
	 * @param ids
	 * @throws SQLException 
	 */
	public long deleteRechargeDetailByRechargeId(Connection conn, String ids) throws SQLException {
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_recharge_detail t_recharge_detail = new Dao().new Tables().new t_recharge_detail();
		return t_recharge_detail.delete(conn, " rechargeId in(" + idSQL + ")");
	}
	

	/**
	 * 修改充值详细表
	 * @param conn
	 * @param paramMap 参数值
	 * @return
	 * @throws SQLException
	 */
	public long updateRechargeDetail_(Connection conn, long id,Map<String, String> paramMap) throws SQLException {
		Dao.Tables.t_recharge_detail t_recharge_detail = new Dao().new Tables().new t_recharge_detail();
		DBReflectUtil.mapToTableValue(t_recharge_detail, paramMap);
		return t_recharge_detail.update(conn, " id=" +id);
		
	}
	
	/**
	 * 汇付充值后 修改充值详细表
	 */
	public long updateRechargeDetailHHN(Connection conn, long id,int result) throws SQLException {
		Dao.Tables.t_recharge_detail t_recharge_detail = new Dao().new Tables().new t_recharge_detail();
		t_recharge_detail.result.setValue(result);
		return t_recharge_detail.update(conn, " id=" +id);
	}
	
}
