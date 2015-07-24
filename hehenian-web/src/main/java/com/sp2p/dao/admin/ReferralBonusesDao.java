package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**
 * 后台借款产品参数中的推荐好友奖励
 * @author Administrator
 *
 */
public class ReferralBonusesDao {



	/**
	 * 更新好友奖励
	 * @param conn
	 * @param id
	 * @param sort
	 * @param columName
	 * @param content
	 * @param publishTimee
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateMReferralBonuses(Connection conn,Integer typeId,String general,String fieldVisit,String organization,String netWorth)
	throws SQLException,DataException{
		Dao.Tables.t_referral_bonuses bonuses=new Dao().new Tables().new t_referral_bonuses();
		bonuses.general.setValue(general);
		bonuses.fieldVisit.setValue(fieldVisit);
		bonuses.organization.setValue(organization);
		bonuses.netWorth.setValue(netWorth);
		
		return bonuses.update(conn, "type="+typeId);
		
	}
	
	/**
	 * 根据typeId获取好友奖励
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getReferralBonusersByTypeId(Connection conn,Integer typeId)
	throws SQLException,DataException{
		Dao.Tables.t_referral_bonuses bonuses=new Dao().new Tables().new t_referral_bonuses();
		
		DataSet dataSet=bonuses.open(conn, "*", " type="+typeId, "", -1, -1);
	     return BeanMapUtils.dataSetToMap(dataSet);		
	}
	/**
	 * 查询好友奖励列表
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryReferralBonusersList(Connection conn)
	throws SQLException,DataException{
		Dao.Tables.t_referral_bonuses bonuses=new Dao().new Tables().new t_referral_bonuses();
		DataSet dataSet=bonuses.open(conn, "*", "", "type asc",-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	
	

}
