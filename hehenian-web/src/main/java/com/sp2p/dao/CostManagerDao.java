package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class CostManagerDao {

	/**
	 * 添加费用管理
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
	public Long addCostManager(Connection conn,String des,Long money,Integer type) throws SQLException,
			DataException {

		Dao.Tables.t_cost_manager costmanager=new Dao().new Tables().new t_cost_manager();
		costmanager.descreption.setValue(des);
		costmanager.number.setValue(money);
		costmanager.type.setValue(type);
		return costmanager.insert(conn);
	}

	/**
	 * 更新费用设置
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
	public Long UpdateCostManager(Connection conn,Integer type,Double number)
			throws SQLException, DataException {
		Dao.Tables.t_cost_manager costmanager=new Dao().new Tables().new t_cost_manager();
		
		costmanager.number.setValue(number);

		return costmanager.update(conn, " type=" + type);
	}
	
	public Map<String, String> getCostManagerByType(Connection conn,Integer type)throws SQLException,DataException{
		Dao.Tables.t_cost_manager costmanager=new Dao().new Tables().new t_cost_manager();
		DataSet dataSet = costmanager.open(conn, "", "type=" + type, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}



	public double getCostManagerNumberByType(Connection conn,Integer type)throws SQLException,DataException{
		Dao.Tables.t_cost_manager costmanager=new Dao().new Tables().new t_cost_manager();
		DataSet dataSet = costmanager.open(conn, " number ", " type= " + type, "", -1, -1);
		return Convert.strToDouble(BeanMapUtils.dataSetToMap(dataSet).get("number"),0.0);
	}
	


}
