package com.sp2p.service.admin;

import java.sql.Connection;

import com.shove.Convert;
import com.shove.data.dao.MySQL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.RiskManageDao;


/**   
 * @ClassName: RiskManageService.java
 * @Author: gang.lv
 * @Date: 2013-4-7 上午11:19:06
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 后台风险保障金处理
 */
public class RiskManageService extends BaseService {

	public static Log log = LogFactory.getLog(RiskManageService.class);

	private RiskManageDao riskManageDao;

	public RiskManageDao getRiskManageDao() {
		return riskManageDao;
	}

	public void setRiskManageDao(RiskManageDao riskManageDao) {
		this.riskManageDao = riskManageDao;
	}

	
	/**   
	 * @MethodName: queryRiskByCondition  
	 * @Param: RiskManageService  
	 * @Author: gang.lv
	 * @Date: 2013-4-7 上午11:22:29
	 * @Return:    
	 * @Descb: 查询风险保障金列表
	 * @Throws:
	*/
	public void queryRiskByCondition(String resource, String timeStart,
			String timeEnd, String riskType, PageBean pageBean) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition =new StringBuffer();
		if(StringUtils.isNotBlank(resource)){
			condition.append(" and resource  like '%"+StringEscapeUtils.escapeSql(resource.trim())+"%' ");
		}
		if(StringUtils.isNotBlank(riskType)){
			condition.append(" and riskType ='"+StringEscapeUtils.escapeSql(riskType.trim())+"'");
		}
		if(StringUtils.isNotBlank(timeStart)){
			condition.append(" and riskDate >='"+StringEscapeUtils.escapeSql(timeStart)+"'");
		}
		if(StringUtils.isNotBlank(timeEnd)){
			condition.append(" and riskDate <='"+StringEscapeUtils.escapeSql(timeEnd)+"'");
		}
		try {
			dataPage(conn, pageBean, "v_t_risk_list_h", "*", " ", condition.toString());
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
	}
	
	/**   
	 * @throws SQLException 
	 * @throws DataException 
	 * @MethodName: queryRiskDetailById  
	 * @Param: RechargeService  
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午11:10:26
	 * @Return:    
	 * @Descb: 查询风险保证金详情
	 * @Throws:
	*/
	public Map<String,String> queryRiskDetailById(long id) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = riskManageDao.queryRiskDetailById(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	/**   
	 * @MethodName: addRisk  
	 * @Param: RiskManageService  
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午03:03:45
	 * @Return:    
	 * @Descb: 手动添加风险保障金
	 * @Throws:
	*/
	public long addRisk(double amountDouble,long adminId,String remark) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();

		long result = -1L;
		try {
			Map<String,String> map = riskManageDao.queryRiskBalance(conn);
			String riskBalance = map.get("riskBalance");
			double riskBalanceDouble = 1000000+Convert.strToDouble(riskBalance, 0);
			Date riskDate = new Date();
			String riskType = "收入";
			String resource = "手动添加风险保障金";
			result = riskManageDao.addRisk(conn,amountDouble,adminId,remark,riskBalanceDouble,riskDate,riskType,resource);
			if (result <= 0) {
				conn.rollback();
				return -1;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**   
	 * @MethodName: deductedRisk  
	 * @Param: RiskManageService  
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午03:03:33
	 * @Return:    
	 * @Descb: 手动扣除风险保障金
	 * @Throws:
	*/
	public long deductedRisk(double amountDouble,long adminId,String remark) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();

		long result = -1L;
		try {
			Map<String,String> map = riskManageDao.queryRiskBalance(conn);
			String riskBalance = map.get("riskBalance");
			double riskBalanceDouble = 1000000+Convert.strToDouble(riskBalance, 0);
			Date riskDate = new Date();
			String riskType = "支出";
			String resource = "手动扣除风险保障金";
			result = riskManageDao.deductedRisk(conn,amountDouble,adminId,remark,riskBalanceDouble,riskDate,riskType,resource);
			if (result <= 0) {
				conn.rollback();
				return -1;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
}
