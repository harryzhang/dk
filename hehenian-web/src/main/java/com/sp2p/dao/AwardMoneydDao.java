package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import com.shove.data.DataException;
import com.sp2p.database.Dao;

public class AwardMoneydDao {

	/**
	 * 添加奖励
	 * 
	 * @param conn
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param publisher
	 * @param visits
	 * @param state
	 * @param seqNum
	 * @param attachment
	 * @return
	 * @throws SQLException
	 */
	public Long addMoneyThing(Connection conn,Long userId,Double money) throws SQLException,
			DataException {

		Dao.Tables.t_money tmoney=new Dao().new Tables().new t_money();
		tmoney.userId.setValue(userId);
		tmoney.money.setValue(money);
		tmoney.addData.setValue(new Date());
		

		return tmoney.insert(conn);
	}

	/**
	 * 更新奖励
	 * 
	 * @param conn
	 * @param id
	 * @param title
	 * @param publishTime
	 * @param state
	 * @param seqNum
	 * @param attachment
	 * @return
	 * @throws SQLException
	 */
	public Long UpdateMoney(Connection conn,Long id, Long userId,Integer status,Long money,String endData)
			throws SQLException, DataException {
		Dao.Tables.t_money tmoney=new Dao().new Tables().new t_money();
		tmoney.userId.setValue(userId);
		tmoney.status.setValue(status);
		tmoney.money.setValue(money);	
		tmoney.endData.setValue(endData);

		return tmoney.update(conn, " id=" + id);
	}

}
