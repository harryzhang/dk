/**
 * 
 */
package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.sp2p.database.Dao;
import com.sp2p.util.DBReflectUtil;

/**
 * 投资表和投资历史表
 * @author Administrator
 *
 */
public class InvestDao {
	
	/**
	 * 添加投资历史表
	 * @param conn
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public long  addInvestHistory(Connection conn ,Map<String,String> paramMap) throws SQLException{
		Dao.Tables.t_invest_history  t_invest_history = new Dao().new Tables().new t_invest_history();
		DBReflectUtil.mapToTableValue(t_invest_history, paramMap);
		return t_invest_history.insert(conn);
	}
	
	/**
	 * 获取投资信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getInvest(Connection conn,long id) throws SQLException, DataException{
		Dao.Tables.t_invest  t_invest = new Dao().new Tables().new t_invest();
		DataSet ds = t_invest.open(conn, "*", " id="+id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 添加投资历史表
	 * @param conn
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	public long  updateInvest(Connection conn ,long id,Map<String,String> paramMap) throws SQLException{
		Dao.Tables.t_invest  t_invest = new Dao().new Tables().new t_invest();
		DBReflectUtil.mapToTableValue(t_invest, paramMap);
		return t_invest.update(conn, " id="+id);
	}
	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryInvestInfoById  
	 * @Param: InvestDao  
	 * @Author: gang.lv
	 * @Date: 2013-6-2 下午07:26:48
	 * @Return:    
	 * @Descb: 根据借款id查询投资信息
	 * @Throws:
	*/
	public List<Map<String, Object>> queryInvestInfoById(Connection conn,
			long borrowId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select a.id,a.investAmount,a.investor,b.vipStatus from t_invest a");
		command.append(" left join t_user b on a.investor=b.id  where a.borrowId="+borrowId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryInvestInfoById  
	 * @Param: InvestDao  
	 * @Author: gang.lv
	 * @Date: 2013-6-2 下午07:26:48
	 * @Return:    
	 * @Descb: 根据借款id 查询投资信息
	 * @Throws:
	*/
	public Map<String, String> queryBorrowMany(Connection conn,
			long borrowId ) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" SELECT  DISTINCT(count(*)) ,(tt.stillInterest+tt.stillPrincipal) as stillPI,a.id as id ,a.feelog as feelog, date_format(a.auditTime,'%Y.%m') as Datesdot ,date_format(a.auditTime,'%Y%m%d')  as DatesNumber ,date_format(a.auditTime,'%Y年%m月%d日')  as DateTime,a.annualRate as annualRate,a.isDayThe as isDayThe,a.deadline as deadline,a.paymentMode as paymentMode ,a.borrowAmount as borrowAmount,s.selectName as purpose,date_format(a.auditTime,'%Y-%m-%d') as starTime,f.idNo as isno,CASE isDayThe WHEN 1 THEN   date_format(DATE_SUB(a.auditTime , INTERVAL -a.deadline MONTH),'%Y-%m-%d')   WHEN 2 THEN date_format(DATE_SUB(a.auditTime , INTERVAL -a.deadline DAY),'%Y-%m-%d')   END  as endTime ,   b.username as Busername , date_format(a.auditTime ,'%d') as days,a.detail as detail,f.realName as realName   ");
		command.append(" FROM t_borrow a LEFT JOIN t_user b ON a.publisher = b.id LEFT JOIN t_person f ON f.userId = b.id LEFT JOIN t_select s ON s.selectValue = a.purpose  LEFT JOIN t_repayment  tt on tt.borrowId = a.id  WHERE  a.id ="+borrowId+" and s.typeId =1 " );
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 根据投资ID查询投资信息
	 * @param conn
	 * @param investId
	 * @param borrwoId
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String,Object>> queryInvestMomey(Connection  conn,long invest_id,long borrowId) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append(" SELECT u.username as username,  round(sum(b.recivedInterest),2) AS recivedInterest, round(SUM(b.recivedPrincipal),2) AS recivedPrincipal, round(sum(b.recivedInterest +b.recivedPrincipal ),2)  as sumPI, round((a.stillInterest + a.stillPrincipal),2) as stillPI ,date_format(tb.auditTime,'%Y-%m-%d') as starTime,CASE tb.isDayThe WHEN 1 THEN   date_format(DATE_SUB(tb.auditTime , INTERVAL -tb.deadline MONTH),'%Y-%m-%d')   WHEN 2 THEN date_format(DATE_SUB(tb.auditTime , INTERVAL -tb.deadline DAY),'%Y-%m-%d')   END  as endTime , tb.isDayThe as isDayThe ,tb.annualRate as annualRate ,tb.deadline as deadline ");
		command.append(" FROM  t_repayment a LEFT JOIN t_invest_repayment b  ON a.id = b.repayId   LEFT JOIN t_user u  on u.id = b.`owner` LEFT JOIN  t_borrow  tb on tb.id = a.borrowId WHERE  a.borrowId ="+borrowId+"  " );
		if (invest_id>0) {
			command.append(" and  invest_id = "+invest_id );
		}
		command.append("  GROUP BY invest_id");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		command = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
	 * 查询所有投资人信息
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public List<Map<String,Object>>  queryUsername(Connection  conn, long borrowId ,long invest_id) throws SQLException, DataException{
		
		 StringBuffer command = new StringBuffer();
		command.append(" SELECT u.username as username,f.realName as realName   FROM   t_repayment a LEFT JOIN t_invest_repayment b  ON a.id = b.repayId    LEFT JOIN t_user u  on u.id = b.`owner` LEFT JOIN t_person f ON f.userId =u.id  LEFT JOIN  t_borrow  tb on tb.id = a.borrowId WHERE  a.borrowId = "+borrowId );
		if (invest_id>0) {
			command.append(" and  b.invest_id = "+invest_id );
		}
		command.append("  group by u.username");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		command = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
	 * 根据借款查询借款应还的金额
	 * @param conn
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryBorrowSumMomeny(Connection  conn,long borrowId,long invest_id) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append(" SELECT  round(sum(b.recivedInterest),2) AS Sumrt, round(SUM(b.recivedPrincipal),2) AS sumPal, round(sum( b.recivedInterest + b.recivedPrincipal ),2) AS sumPI ");
		command.append(" FROM  t_repayment a LEFT JOIN t_invest_repayment b ON a.id = b.repayId  WHERE  a.borrowId = "+borrowId+"");
		if (invest_id>0) {
			command.append(" and b.invest_id=  "+invest_id);
		}
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null; 
		return BeanMapUtils.dataSetToMap(dataSet);
		
	}
	/**   
	 * @MethodName: addInvestRepayment  
	 * @Param: InvestDao  
	 * @Author: gang.lv
	 * @Date: 2013-6-2 下午06:36:26
	 * @Return:    
	 * @Descb: 添加投资还款记录
	 * @Throws:
	*/
	public long addInvestRepayment(Connection conn,long repayId,String repayPeriod ,String repayDate ,double recivedPrincipal,double recivedInterest,
			double principalBalance,double interestBalance,long invest_id,long owner,String ownerlist,
			double iManageFeeRate,double hasPrincipal,double hasInterest,double iManageFee) throws SQLException{
		Dao.Tables.t_invest_repayment t_invest_repayment = new Dao().new Tables().new t_invest_repayment();
		t_invest_repayment.repayId.setValue(repayId);
		t_invest_repayment.repayPeriod.setValue(repayPeriod);
		t_invest_repayment.repayDate.setValue(repayDate);
		t_invest_repayment.recivedPrincipal.setValue(recivedPrincipal);
		t_invest_repayment.recivedInterest.setValue(recivedInterest);
		t_invest_repayment.principalBalance.setValue(principalBalance);
		t_invest_repayment.interestBalance.setValue(interestBalance);
		t_invest_repayment.invest_id.setValue(invest_id);
		t_invest_repayment.owner.setValue(owner);
		t_invest_repayment.ownerlist.setValue(ownerlist);
		t_invest_repayment.iManageFee.setValue(iManageFee);
		t_invest_repayment.iManageFeeRate.setValue(iManageFeeRate);
		t_invest_repayment.hasPrincipal.setValue(hasPrincipal);
		t_invest_repayment.hasInterest.setValue(hasInterest);
		if(hasPrincipal > 0){
			t_invest_repayment.repayStatus.setValue(2);//设置状态已还
			t_invest_repayment.realRepayDate.setValue(new Date());//设置实际还款时间
		}

		return t_invest_repayment.insert(conn);
	}
	
	
	
	/**
	 *   修改   实际得到的利息 和 扣除的管理费
	 * @return
	 * @throws SQLException 
	 */
	public long updateHasIntert(Connection conn, long repayId,long investId, long owner ,double getInterest) throws SQLException{
		Dao.Tables.t_invest_repayment t_invest_repayment = new Dao().new Tables().new t_invest_repayment();
		StringBuffer command = new StringBuffer();
		command.append(" invest_id="+investId+" and owner="+owner);
		command.append(" and repayId="+repayId);
		long result =   t_invest_repayment.update(conn, command.toString());
		command = null;
		return result;
	}
	public long updateInvestRepayment(Connection conn,long repayId,long investId, 
			long owner, double lateFI,long interestOwner,long isWebRepay ) throws SQLException {
		StringBuffer command = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		command.append("update t_invest_repayment SET hasPrincipal = recivedPrincipal,hasInterest=recivedInterest,");
		command.append("realRepayDate='"+sf.format(new Date())+"'");
		command.append(",interestOwner = "+interestOwner);
		command.append(",isWebRepay = "+isWebRepay);
		command.append(", iManageFee= recivedInterest * iManageFeeRate");
		command.append(",repayStatus=2,recivedFI="+lateFI+" where");
		command.append(" invest_id="+investId+" and owner="+owner);
		command.append(" and repayId="+repayId);
		long result= MySQL.executeNonQuery(conn, command.toString());
		command = null;
		sf = null;
		return result;
	}
	
	/** @throws SQLException 
	 * @MethodName: updateInvestRepayment  
	 * @Param: InvestDao  
	 * @Author: gang.lv
	 * @Date: 2013-6-2 下午09:00:08
	 * @Return:    
	 * @Descb: 更新投资还款
	 * @Throws:
	*/
	public long updateInvestRepayment(Connection conn,long repayId,long investId, 
			long owner, double lateFI,long interestOwner,long isWebRepay,double hasP,double hasI) throws SQLException {
		StringBuffer command = new StringBuffer();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		command.append("update t_invest_repayment SET hasPrincipal = "+hasP+",hasInterest="+hasI+",");
		command.append("realRepayDate='"+sf.format(new Date())+"'");
		command.append(",interestOwner = "+interestOwner);
		command.append(",isWebRepay = "+isWebRepay);
		command.append(", iManageFee= recivedInterest * iManageFeeRate");
		command.append(",repayStatus=1,recivedFI="+lateFI+" where");
		command.append(" invest_id="+investId+" and owner="+owner);
		command.append(" and repayId="+repayId);
		long result= MySQL.executeNonQuery(conn, command.toString());
		command = null;
		sf = null;
		return result;
	}
	
	
	
	/**   
	 * @MethodName: updateInvestDebtStatus  
	 * @Param: InvestDao  
	 * @Author: gang.lv
	 * @Date: 2013-6-6 上午10:38:29
	 * @Return:    
	 * @Descb: 更新投资还款记录是债权转让的状态
	 * @Throws:
	*/
	public long updateInvestDebtStatus(Connection conn, long investId,long owner) throws SQLException {
		String command = "update t_invest_repayment SET owner = "+owner+",ownerlist=concat(ownerlist,',"+owner+"'),isDebt=2 where invest_id="+investId+" and repayStatus=1";
		return MySQL.executeNonQuery(conn, command);
	}

	
	public Map<String,String> queryInvestRepayment(Connection  conn,long investId,long repayId) throws DataException, SQLException{
		StringBuffer command = new StringBuffer();
		command.append(" select hasPrincipal as hhap, hasInterest as hhaI from t_invest_repayment ");
		command.append(" where   invest_id = "+investId+" and repayId = "+repayId+" ");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	

	/**
	 * 流转标在投标时候添加还款记录
	 * @param conn
	 * @param repayId
	 * @param repayPeriod
	 * @param repayDate
	 * @param recivedPrincipal
	 * @param recivedInterest
	 * @param principalBalance
	 * @param interestBalance
	 * @param invest_id
	 * @param owner
	 * @param ownerlist
	 * @param iManageFeeRate
	 * @param hasPrincipal
	 * @param hasInterest
	 * @param iManageFee
	 * @return
	 * @throws SQLException
	 */
	public long addInvestRepayment_(Connection conn,long repayId,String repayPeriod ,String repayDate ,double recivedPrincipal,double recivedInterest,
			double principalBalance,double interestBalance,long invest_id,long owner,String ownerlist,
			double iManageFeeRate,double hasPrincipal,double hasInterest,double iManageFee) throws SQLException{
		Dao.Tables.t_invest_repayment t_invest_repayment = new Dao().new Tables().new t_invest_repayment();
		t_invest_repayment.repayId.setValue(repayId);
		t_invest_repayment.repayPeriod.setValue(repayPeriod);
		t_invest_repayment.repayDate.setValue(repayDate);
		t_invest_repayment.recivedPrincipal.setValue(recivedPrincipal);
		t_invest_repayment.recivedInterest.setValue(recivedInterest);
		t_invest_repayment.principalBalance.setValue(principalBalance);
		t_invest_repayment.interestBalance.setValue(interestBalance);
		t_invest_repayment.invest_id.setValue(invest_id);
		t_invest_repayment.owner.setValue(owner);
		t_invest_repayment.ownerlist.setValue(ownerlist);
		t_invest_repayment.iManageFeeRate.setValue(iManageFeeRate);
		t_invest_repayment.iManageFee.setValue(iManageFee);
		t_invest_repayment.hasPrincipal.setValue(hasPrincipal);
		t_invest_repayment.hasInterest.setValue(hasInterest);
		if(hasPrincipal > 0){
			t_invest_repayment.repayStatus.setValue(2);//设置状态已还
			t_invest_repayment.realRepayDate.setValue(new Date());//设置实际还款时间
		}
		return t_invest_repayment.insert(conn);
		
	}
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryInvestorByBorrowId  
	 * @Param: InvestDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-21 下午11:55:09
	 * @Return:    
	 * @Descb: 查询当日到期要还款的投资人
	 * @Throws:
	*/
	public List<Map<String,Object>> queryInvestorByBorrowId(Connection conn,long id) throws SQLException, DataException{
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		String command = " select a.id,a.investor,a.investAmount,a.recivedPrincipal,a.recievedInterest,b.username ,a.realAmount as realAmount,a.oriInvestor,a.repayStatus "
		+" from t_invest a left join t_user b on a.investor=b.id where"
		+" a.circulationForpayStatus=1 and a.repayDate is not null"
		+" and a.repayDate <='"+sf.format(new Date())+"'"
		+" and a.borrowId="+id;
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sf = null;
		command = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	/**   
	 * @MethodName: updateInvestRepayStatus  
	 * @Param: InvestDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-22 上午11:12:49
	 * @Return:    
	 * @Descb: 更新流转标还款状态为已还
	 * @Throws:
	*/
	public long updateInvestRepayStatus(Connection conn, long investId) throws SQLException {
		long returnId = -1;
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		String command = "UPDATE t_invest SET hasPrincipal = recivedPrincipal,hasInterest=recievedInterest,"
		+" repayStatus=2,circulationForpayStatus=2  where "
		+" circulationForpayStatus=1 and repayDate is not null "
		+" and repayDate <='"+sf.format(new Date())+"'"
		+" and id="+investId ;
		returnId = MySQL.executeNonQuery(conn, command.toString());
		sf = null;
		command = null;
		return returnId;
	}
	public long updateInvestRepaymentStatus(Connection conn,long invest_id) throws SQLException{
		String command = "update t_invest_repayment  set repayStatus = 2 , circulationForpayStatus =2  where invest_id =  "+invest_id;
		return MySQL.executeNonQuery(conn, command);
	}
	
	/**
	 * 添加 投标Id   用于判断流转标是否还款
	 * @param conn
	 * @param invest_id
	 * @return
	 * @author C_J
	 * @throws SQLException
	 */
	public long addFlowRepayment(Connection conn,long invest_id) throws SQLException{
		Dao.Tables.t_flow_repayment t_flow_reRepayment  = new Dao().new Tables().new t_flow_repayment();
		t_flow_reRepayment.invest_id.setValue(invest_id);
		
		return  t_flow_reRepayment.insert(conn);
	}
	
	/**
	 * 根据投资ID 查询流转标是否还款
	 * @param conn
	 * @param invest_id
	 * @return
	 * @author C_J
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> queryFlowMap(Connection  conn,long invest_id) throws DataException, SQLException{
		Dao.Tables.t_flow_repayment t_flow_reRepayment  = new Dao().new Tables().new t_flow_repayment();
		DataSet  ds = t_flow_reRepayment.open(conn, " * ", " invest_id =  " + invest_id, "", -1, -1);
		
		return BeanMapUtils.dataSetToMap(ds);
	}
	/**
	 * 更新当前投资的已还金额和和利息
	 * @param conn
	 * @param investid
	 * @param hasPrincipal
	 * @param hasInterest
	 * @return
	 * @throws SQLException
	 */
	public long updateInvesthasPrincipalAndhasInterest(Connection conn,long investid,double hasPrincipal,double hasInterest) throws SQLException{
		long returnId = -1;
	    StringBuffer sb = new StringBuffer();
	    sb.append("update t_invest set hasPrincipal  =   "+hasPrincipal+",t_invest.hasInterest = "+hasInterest+" where id = "+investid);
		returnId = MySQL.executeNonQuery(conn, sb.toString());
		sb = null;
		return returnId;
	}
	
	/**
	 * 根据用户ID 和借款ID查询最新的投资ID
	 * @param conn
	 * @param borrowId
	 * @param userId
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String, String> queryInvestId(Connection  conn,long borrowId,long userId) throws SQLException, DataException{
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		DataSet  ds = t_invest.open(conn, "  max(id) as investId  ", " borrowId = "+borrowId+"  and  investor = "+userId, "", -1, -1);
		return  BeanMapUtils.dataSetToMap(ds);
	}

	public long deleteRecord(Connection conn, String id) throws SQLException {
		Dao.Tables.t_invest invest = new Dao().new Tables().new t_invest();
		return invest.delete(conn, " id="+id);
	}
}
