package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

/**   
 * @ClassName: RiskManageDao.java
 * @Author: gang.lv
 * @Date: 2013-4-7 上午11:17:46
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 后台风险保障金数据处理
 */
public class RiskManageDao {

	
	/**   
	 * @MethodName: queryRiskDetailById  
	 * @Param: RechargeDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午11:13:23
	 * @Return:    
	 * @Descb: 查询风险保证金详情
	 * @Throws:
	*/
	public Map<String, String> queryRiskDetailById(Connection conn, long id) throws SQLException, DataException {
		Dao.Views.v_t_risk_detail_h t_risk_detail = 
			new Dao().new Views().new v_t_risk_detail_h();
		DataSet ds = t_risk_detail.open(conn, "*", "id="+id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	
	/**   
	 * @MethodName: queryRiskBalance  
	 * @Param: RiskManageDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午02:51:57
	 * @Return:    
	 * @Descb: 查询风险保障金余额
	 * @Throws:
	*/
	public Map<String, String> queryRiskBalance(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_risk_detail t_risk_detail = 
			new Dao().new Tables().new t_risk_detail();
		DataSet ds = t_risk_detail.open(conn, " sum(riskInCome-riskSpending) riskBalance", "", "", 0, 1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	
	/**   
	 * @MethodName: addRisk  
	 * @Param: RiskManageDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午03:02:24
	 * @Return:    
	 * @Descb: 手动添加风险保障金
	 * @Throws:
	*/
	public long addRisk(Connection conn, double amountDouble, long adminId,
			String remark, double riskBalanceDouble, Date riskDate,
			String riskType, String resource) throws SQLException {
		Dao.Tables.t_risk_detail t_risk_detail = new Dao().new Tables().new t_risk_detail();
		t_risk_detail.riskInCome.setValue(amountDouble);
		t_risk_detail.operator.setValue(adminId);
		t_risk_detail.remark.setValue(remark);
		t_risk_detail.riskBalance.setValue(riskBalanceDouble);
		t_risk_detail.riskDate.setValue(riskDate);
		t_risk_detail.riskType.setValue(riskType);
		t_risk_detail.resource.setValue(resource);
		return t_risk_detail.insert(conn);
	}
	
	
	/**   
	 * @MethodName: deductedRisk  
	 * @Param: RiskManageDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午03:02:34
	 * @Return:    
	 * @Descb: 手动扣除风险保障金
	 * @Throws:
	*/
	public long deductedRisk(Connection conn, double amountDouble, long adminId,
			String remark, double riskBalanceDouble, Date riskDate,
			String riskType, String resource) throws SQLException {
		Dao.Tables.t_risk_detail t_risk_detail = new Dao().new Tables().new t_risk_detail();
		t_risk_detail.riskSpending.setValue(amountDouble);
		t_risk_detail.operator.setValue(adminId);
		t_risk_detail.remark.setValue(remark);
		t_risk_detail.riskBalance.setValue(riskBalanceDouble);
		t_risk_detail.riskDate.setValue(riskDate);
		t_risk_detail.riskType.setValue(riskType);
		t_risk_detail.resource.setValue(resource);
		return t_risk_detail.insert(conn);
	}
}
