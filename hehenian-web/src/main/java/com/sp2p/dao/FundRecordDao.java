/**
 * 
 */
package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.util.DBReflectUtil;

/**
 * 资金记录
 * @author Administrator
 *
 */
public class FundRecordDao {
	
	/**
	 * 添加资金记录表
	 * @param conn
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public long  addFundRecord(Connection conn ,Map<String,String> paramMap) throws SQLException{
		Dao.Tables.t_fundrecord  t_fundrecord = new Dao().new Tables().new t_fundrecord();
		DBReflectUtil.mapToTableValue(t_fundrecord, paramMap);
		return t_fundrecord.insert(conn);
	}
	/**   
	 * @MethodName: addFundRecord  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午05:13:09
	 * @Return:    
	 * @Descb: 添加资金流动记录
	 * @Throws:
	*/
	public long addFundRecord(Connection conn, Object userId,String fundMode,Object handleSum,Object usableSum,Object freezeSum,Object dueinSum,Object trader,String remarks,
			Object income,Object spending,Object borrow_id,Object repayment_id,Object operateType,Object dueOutSum) throws SQLException {
		Dao.Tables.t_fundrecord t_fundrecord = new Dao().new Tables().new t_fundrecord();
		t_fundrecord.userId.setValue(userId);
		t_fundrecord.fundMode.setValue(fundMode);
		t_fundrecord.handleSum.setValue(handleSum);
		t_fundrecord.usableSum.setValue(usableSum);
		t_fundrecord.freezeSum.setValue(freezeSum);
		t_fundrecord.dueinSum.setValue(dueinSum);
		t_fundrecord.trader.setValue(trader);
		t_fundrecord.recordTime.setValue(new Date());
		t_fundrecord.remarks.setValue(remarks);
		t_fundrecord.income.setValue(income);
		t_fundrecord.spending.setValue(spending); //
		t_fundrecord.borrow_id.setValue(borrow_id);
		t_fundrecord.repayment_id.setValue(repayment_id);
		t_fundrecord.operateType.setValue(operateType);
		t_fundrecord.dueoutSum.setValue(dueOutSum);
		return t_fundrecord.insert(conn);
	}
	
	/**
	 * 查询操作金额
	 * @param conn
	 * @param buget
	 * @param userName
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String>  queryAmountSum(Connection  conn,String userName) throws SQLException, DataException{
		StringBuffer  condition = new StringBuffer();
		condition.append("select sum(handleSum) as sumHand  from  v_fundrecord_buget");
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username = '"+StringEscapeUtils.escapeSql(userName)+"'  ");
		}
		DataSet dataSet = MySQL.executeQuery(conn, condition.toString());
		condition = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
}
