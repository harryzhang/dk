package com.sp2p.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.sp2p.database.Dao;

/**
 * 理财人奖励
 * @author md002
 *
 */
public class AwardLevel4Dao {
	
	/**
	 * 添加奖励明细
	 * @param conn
	 * @param userId
	 * @param level3userId
	 * @param money
	 * @return
	 * @throws SQLException
	 */
	public Long addAwardLevel4(Connection conn,long userId,long level3userId,BigDecimal money) throws SQLException{
		Dao.Tables.t_award_level4 award = new Dao().new Tables().new t_award_level4();
		award.userId.setValue(userId);
		award.level3userId.setValue(level3userId);
		award.money.setValue(money);
		award.addDate.setValue(new Date());
		return award.insert(conn);
	}
	
	/**
	 * 根据理财人查询奖励明细总数
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long getCountAwardLevel4ByUserId(Connection conn,long userId) throws SQLException{
		Dao.Tables.t_award_level4 award = new Dao().new Tables().new t_award_level4();
		return award.getCount(conn, " userId = "+userId);
	}

}
