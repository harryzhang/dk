package com.sp2p.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import com.shove.base.BaseService;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.sp2p.dao.CostManagerDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 费用设置
 * @author Administrator
 *
 */
public class CostManagerService extends BaseService {
	public static Log log=LogFactory.getLog(CostManagerService.class);
	
	private CostManagerDao costManagerDao;
	
	private ConnectionManager connectionManager;
	
	/**
	 * 添加费用设置信息
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
	public Long addCostManager(String des,Long money,Integer type)throws SQLException,DataException{
		
		Connection conn =connectionManager.getConnection();
		Long downloadId=0L;
		try {
			downloadId=costManagerDao.addCostManager(conn, des, money, type);
			
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return downloadId;
		
	}
	
	/**
	 * 更新费用设置
	 * @param id
	 * @param title
	 * @param publishTime
	 * @param state
	 * @param seqNum
	 * @param attachment
	 * @return
	 * @throws SQLException
	 */
	public Long updateCostManager(Integer type,Double number)throws SQLException,DataException{
		Connection conn=MySQL.getConnection();
		Long downloadId=1L;
		try {
			if(number != -1){
				downloadId = costManagerDao.UpdateCostManager(conn, type, number);
				downloadId = MySQL.executeNonQuery(conn, "update t_user set vipFee = "+number);
			}
			if(downloadId <=0){
				conn.rollback();
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
		return downloadId;
	}
	
	
	public Map<String, String> getCostManagerByType(Integer type)throws SQLException,DataException{
		Connection conn=connectionManager.getConnection();
		Map<String, String> map=null;
		try {
			map=costManagerDao.getCostManagerByType(conn, type);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch(DataException e){
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return map;
		
	}
	
	public CostManagerDao getCostManagerDao() {
		return costManagerDao;
	}

	public void setCostManagerDao(CostManagerDao costManagerDao) {
		this.costManagerDao = costManagerDao;
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
	
	
	

}
