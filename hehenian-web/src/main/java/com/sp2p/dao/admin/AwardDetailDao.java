package com.sp2p.dao.admin;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.sql.Connection;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.sp2p.database.Dao;

public class AwardDetailDao {

	public Long addAwardDetail(Connection conn, long userId,double handleSum,
			long checkId,Date checkTime,String remark) throws SQLException{
		
		Dao.Tables.t_award_detail t_award_detal = new Dao().new Tables().new t_award_detail();
		t_award_detal.userId.setValue(userId);
		t_award_detal.handleSum.setValue(handleSum);
		t_award_detal.checkId.setValue(checkId);
		t_award_detal.checkTime.setValue(checkTime);
		t_award_detal.remark.setValue(remark);
		
		return t_award_detal.insert(conn);
	}
	
	public List<Map<String, Object>> queryAwardDetailList(Connection conn,
			Long userId) throws SQLException, DataException {
		StringBuffer condition = new StringBuffer();
		List<Map<String, Object>> list = null;
		condition.append("1 = 1");
		if (userId != null) {
			condition.append(" AND userId = "
					+ userId );
		}
		Dao.Tables.t_award_detail t_admin = new Dao().new Tables().new t_award_detail();
		DataSet ds = t_admin.open(conn, " id,userName,password,enable,lastTime,lastIp,roleId,realName,telphone,qq,email,img,isLeader,sex,card,summary,nativeplacepro,nativeplacecity,address,addDate,moneys ", condition.toString(), " id DESC  ",
				-1, -1);
		ds.tables.get(0).rows.genRowsMap();
		list = ds.tables.get(0).rows.rowsMap;
		return list;
	}
}
