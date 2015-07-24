package com.sp2p.dao.admin;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.sp2p.database.Dao;

public class ShortMassegeDao {
	
	public long addShortMassege(String title,String content,int style, 
			Date date ,int status,int receiverType, String receiverId,Connection conn) throws Exception{
		Dao.Tables.t_short_massege t_short_massege = new Dao().new Tables().new t_short_massege();
		t_short_massege.receiverId.setValue(receiverId);
		t_short_massege.content.setValue(content);
		t_short_massege.receiverType.setValue(receiverType);
		t_short_massege.sendTime.setValue(date);
		t_short_massege.title.setValue(title);
		t_short_massege.style.setValue(style);
		t_short_massege.status.setValue(status);
		return t_short_massege.insert(conn);
	}
	
	public List<Map<String,Object>>  queryShortMassege(Connection conn ) throws Exception{
		Dao.Tables.t_short_massege t_short_massege = new Dao().new Tables().new t_short_massege();
		DataSet dataSet = t_short_massege.open(conn, " * ", " status != 1 and  sendTime <SYSDATE()", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public long deleteShortMassege( Connection conn,String ids) throws Exception{
		Dao.Tables.t_short_massege t_short_massege = new Dao().new Tables().new t_short_massege();
		String condition = "id in ( " +ids+")";
		return t_short_massege.delete(conn, condition);
	}
	public long updateShortMassege(Connection conn,Long id) throws Exception{
		long returnId = 0L;
		String command = "update t_short_massege set sendTime = SYSDATE() where id=" +id;
		returnId = Database.executeNonQuery(conn, command);
		return returnId;
	}
}
