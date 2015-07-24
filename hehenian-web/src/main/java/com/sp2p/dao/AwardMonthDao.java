package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.sp2p.database.Dao;

public class AwardMonthDao {
	public Long addUserAwardMonth(Connection conn,long pepoleid,double moneys,int type,int applystatus,String month) throws SQLException{
		Dao.Tables.t_award_moth t_award_moth = new Dao().new Tables().new t_award_moth();
		t_award_moth.addtime.setValue(new Date());
		t_award_moth.applystatus.setValue(applystatus);
		t_award_moth.type.setValue(type);
		t_award_moth.moneys.setValue(moneys);
		t_award_moth.pepoleid.setValue(pepoleid);
		t_award_moth.moth.setValue(month);
		return t_award_moth.insert(conn);
	}
	
	
	public Long updateMoneyInfo(Connection conn,long id,int applystatus) throws SQLException{
		Dao.Tables.t_award_moth t_award_moth = new Dao().new Tables().new t_award_moth();
		t_award_moth.applystatus.setValue(applystatus);
		return t_award_moth.update(conn, " id = "+id);
	}

	
	
	
}
