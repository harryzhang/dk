package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.sp2p.database.Dao;

/**
 * 本金保障处理
 * @author li.hou
 *
 */
public class CapitalEnsureDao {

	public List<Map<String,Object>> queryConfigList(Connection conn,int limitStart,int limitCount)
		throws SQLException, DataException {
			Dao.Tables.bt_config t_config = new Dao().new Tables().new bt_config();
			/**
			 * 类型为1
			 */
			DataSet dataSet = t_config.open(conn, "*", " type=1", "", limitStart, limitCount);
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String,Object>> queryMessages(Connection conn,String flag)throws SQLException, DataException {
		Dao.Tables.t_news news=new Dao().new Tables().new t_news();
		
		String type=new String();
		if(flag.equals("1")){
			type=" type=11";
		}else if(flag.equals("2")){
			type=" type=12";
		}else if(flag.equals("3")){
			type=" type=13";
		}else if(flag.equals("4")){
			type=" type=14";
		}else if(flag.equals("5")){
			type=" type=15";
		}else if(flag.equals("6")){
			type=" type=16";
		}else if(flag.equals("7")){
            type=" type=17";
        }else if(flag.equals("8")){
            type=" type=18";
        }
		
		DataSet dataSet = news.open(conn, "*", type, "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
}
