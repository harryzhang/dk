package com.sp2p.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class AwardDao {
	/**
	 * 团队长添加奖励
	 * @param conn
	 * @param userId
	 * @param level2userId
	 * @param level2money
	 * @param level1userId
	 * @param level1money
	 * @param iorId
	 * @param iorType
	 * @param status
	 * @param mx
	 * @param mxType
	 * @param month
	 * @param level
	 * @return
	 * @throws SQLException
	 */
	public Long addAward(Connection conn,long userId,long level2userId,BigDecimal level2money,long level1userId,BigDecimal level1money,long iorId,int iorType,int status,BigDecimal mx,int mxType,Integer month,int level) throws SQLException{
		Dao.Tables.t_award award = new Dao().new Tables().new t_award();
		award.userId.setValue(userId);
		award.level2userId.setValue(level2userId);
		award.level2money.setValue(level2money);
		award.level1userId.setValue(level1userId);
		award.level1money.setValue(level1money);
		award.iorId.setValue(iorId);
		award.status.setValue(2);
		award.addDate.setValue(new Date());
		award.mx.setValue(mx);
		award.mxType.setValue(mxType);
		award.month.setValue(month);
		award.level.setValue(level);
		award.iorType.setValue(iorType);
		return award.insert(conn);
	}
	/**
	 * 查询奖励
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryOneLevel1Info(Connection conn,Long userId,int limitStart,int limitCount) throws SQLException, DataException{
		
		StringBuffer  condition=new StringBuffer();
		condition.append("SELECT  b.id  AS  id , b.userName  AS  userName , b.realName AS  realName ,IFNULL(c.totalMoney , 0)AS  totalMoney ,IFNULL(d.hasPaySum , 0)AS  hasPaySum ,( IFNULL( totalMoney , 0)- IFNULL( hasPaySum , 0) )AS  forPaySum  FROM t_admin b "); 
		condition.append(" LEFT JOIN( SELECT level1userId, sum(level1money) AS   totalMoney  FROM t_award GROUP BY level1userId ) c ON b.id  = c.level1userId  ");
		condition.append("LEFT JOIN( SELECT  userId,sum(handleSum) AS  hasPaySum  FROM  t_award_detail GROUP BY userId )d ON b.id  = d. userId   ");
		condition.append(" WHERE b.enable  = 1 AND b.roleId = 1 ");
		condition.append(" and id="+userId);
		DataSet dataSet = MySQL.executeQuery(conn, condition.toString());
		condition = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	public Map<String,String> queryOneLevel2Info(Connection conn,Long userId,int limitStart,int limitCount) throws SQLException, DataException{
		StringBuffer  condition=new StringBuffer();
		condition.append("SELECT b.id AS id, b.userName AS userName, b.realName AS realName, IFNULL(c.totalMoney, 0)AS totalMoney, IFNULL(d.hasPaySum, 0)AS hasPaySum, (IFNULL(totalMoney, 0)- IFNULL(hasPaySum, 0) )AS forPaySum FROM t_admin b ");
		condition.append("LEFT JOIN( SELECT level2userId, sum(level2money)AS totalMoney FROM t_award GROUP BY level2userId )c ON b.id = c.level2userId ");
		condition.append("LEFT JOIN( SELECT userId, sum(handleSum)AS hasPaySum FROM t_award_detail GROUP BY userId )d ON b.id = d.userId ");
		condition.append("WHERE b.enable = 1 AND b.roleId = 2");
		condition.append(" and id="+userId);
		DataSet dataSet = MySQL.executeQuery(conn, condition.toString()) ;
		condition = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	public long updataStatu(Connection conn,long id) throws Exception{
		
		Dao.Tables.t_award award = new Dao().new Tables().new t_award();
		award.status.setValue(1);
		return award.update(conn, " level2userId = " + id);
	}
}
