package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_fundrecord;
import com.sp2p.database.Dao.Tables.t_risk_detail;

public class FrontMyPaymentDao {
	/**
	 * 查询一条借款信息
	 * @param conn
	 * @param userId
	 * @param borrowId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryOneBorrowInfo(Connection conn,long userId,
			long borrowId,int limitStart,int limitCount) throws SQLException, DataException{
		Dao.Views.t_borrow_success_list t_info = new Dao().new Views().new t_borrow_success_list();
		DataSet dataSet = t_info.open(conn, "*", " publisher="+userId+" and borrowId="+borrowId,
				"", limitStart, limitStart);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 查询付款详细信息
	 * @param conn
	 * @param borrowId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryPayingDetails(Connection conn,long borrowId,int limitStart,int limitCount)
		throws SQLException, DataException {
		String condition = " borrowId="+borrowId+" and repayStatus!="+IConstants.PAYING_STATUS_SUCCESS;
		Dao.Views.t_success_paying_details t_info = new Dao().new Views().new t_success_paying_details();
		DataSet dataSet = t_info.open(conn, "*", condition, "    ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
    }
	/**
	 * 查询一条付款详细信息
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryOnePayingDetails(Connection conn,long userId) throws SQLException, DataException{
		String command = "SELECT * from t_success_paying_details where borrowId in " +
		"(select id from t_borrow where publisher="+userId+") and borrowId is not null";
		DataSet dataSet = MySQL.executeQuery(conn, command);
		dataSet.tables.get(0).rows.genRowsMap();
		command = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String,Object>> queryPayingBorrowIds(Connection conn,long userId) throws SQLException, DataException{
		String command = "SELECT distinct(borrowId) from t_success_paying_details where borrowId in " +
		"(select id from t_borrow where publisher="+userId+") and borrowId is not null";
		DataSet dataSet = MySQL.executeQuery(conn, command);
		dataSet.tables.get(0).rows.genRowsMap();
		command = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
	 * 查询我的付款数据
	 * @param conn
	 * @param payId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryMyPayData(Connection conn,long payId,int limitStart,int limitCount) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append("select * from v_t_mypay_hhn  where id="+payId+" limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: queryRepayAmountById  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-11 下午05:56:16
	 * @Return:    
	 * @Descb: 查询单个还款记录待还本金
	 * @Throws:
	*/
	public Map<String, String> queryRepayAmountById(Connection conn, long id) throws SQLException, DataException {
		String command = "SELECT stillPrincipal from t_repayment where id="+id+" limit 0,1";
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**   
	 * @MethodName: queryRepayBorrow  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 上午10:51:53
	 * @Return:    
	 * @Descb: 查询处于还款中的借款
	 * @Throws:
	*/
	public Map<String, String> queryRepayBorrow(Connection conn, long id) throws SQLException, DataException {
		String command = "SELECT id ,borrowId FROM t_repayment WHERE repayStatus = 1 and id ="+id+" limit 0,1";
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null ;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: isRepayBorrow  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 上午10:54:35
	 * @Return:    
	 * @Descb: 还款是否已经处理
	 * @Throws:
	*/
	public Map<String, String> isRepayBorrow(Connection conn, long id) throws SQLException, DataException {
		String command = "SELECT (stillPrincipal+stillInterest-hasPI) as needPI,lateFI,stillPrincipal,borrowId FROM t_repayment WHERE id ="+id+" AND repayStatus =1";
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: userAmountMap  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午01:28:53
	 * @Return:    
	 * @Descb: 用户账户金额
	 * @Throws:
	*/
	public Map<String, String> userAmountMap(Connection conn,double needSum, long userId) throws DataException, SQLException {
		String command = "SELECT id FROM t_user WHERE usableSum < "+needSum+" and id ="+userId;
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: validatePassWord  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午01:30:59
	 * @Return:    
	 * @Descb: 验证交易密码是否正确
	 * @Throws:
	*/
	public Map<String, String> validatePassWord(Connection conn, long userId,String dealPWD) throws SQLException, DataException{
		String command = "SELECT dealpwd  FROM t_user where dealpwd = '"+StringEscapeUtils.escapeSql(dealPWD)+"' and id ="+userId+" and dealpwd is not null";
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryHasDeadLine  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午01:40:26
	 * @Return:    
	 * @Descb: 已还款期数
	 * @Throws:
	*/
	public Map<String, String> queryHasDeadLine(Connection conn, long id) throws SQLException, DataException {
		//----modify by houli  添加isDayThe 字段的查询
		String command = "SELECT borrowAmount AS borrowAmount, id as borrowId,hasDeadline as hasDeadline,borrowWay as borrowWay,isDayThe as isDayThe,borrowTitle as borrowTitle,borrowShow FROM t_borrow where id = (select borrowId from t_repayment where id="+id+")";
		DataSet dataSet = Database.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: isRepay  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午01:45:46
	 * @Return:    
	 * @Descb: 是否处于还款中
	 * @Throws:
	*/
	public Map<String, String> isRepay(Connection conn, int hasDeadline, long id) throws SQLException, DataException {
		String command = "SELECT id FROM t_borrow WHERE deadline > "+hasDeadline+" and id = (select borrowId from t_repayment where id="+id+")";
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**   
	 * @return 
	 * @throws SQLException 
	 * @MethodName: updateRepayedBorrow  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午01:49:11
	 * @Return:    
	 * @Descb: 处理下已还完借款
	 * @Throws:
	*/
	public long updateRepayedBorrow(Connection conn, int hasDeadline, long id,int sorts) throws SQLException {
		String command = "update t_borrow set borrowStatus = 5 ,sort = "+sorts+" where deadline <= "+hasDeadline+" and borrowStatus = 4 and id = (select borrowId from t_repayment where id="+id+")";
		long result=MySQL.executeNonQuery(conn, command);
		command= null;
		return result;
	}
	
	/**
	 * add by houli 
	 * 处理天标还款时的状态，天标只需要还一期
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public long updateRepayedBorrow(Connection conn, long id , int sorts) throws SQLException {
		String command = "update t_borrow set borrowStatus = 5 ,sort = "+sorts+"  where hasDeadline  = 1 and borrowStatus = 4 and id = (select borrowId from t_repayment where id="+id+")";
		long result=MySQL.executeNonQuery(conn, command);
		command=null;
		return  result;
	}

	
	/**   
	 * @MethodName: isWebRepay  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午01:59:32
	 * @Return:    
	 * @Descb: 是否网站代还
	 * @Throws:
	*/
	public Map<String, String> isWebRepay(Connection conn, long id) throws SQLException, DataException {
		String command = "SELECT isWebRepay,borrowId FROM t_repayment where id="+id;
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: updateRepayInfo  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午02:10:29
	 * @Return:    
	 * @Descb: 更新还款记录
	 * @Throws:
	*/
	public long updateRepayInfo(Connection conn, double needPI, double lateFI,
			long id,int version) throws SQLException {
		/*t_repayment.hasPI.setValue(needPI);
		t_repayment.realRepayDate.setValue(new Date());
		t_repayment.hasFI.setValue(lateFI);
		t_repayment.repayStatus.setValue(2);*/
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		StringBuffer command = new StringBuffer();
		command.append("update t_repayment set hasPI="+needPI);
		command.append(",realrepaydate=DATE_FORMAT('"+sf.format(new Date())+"','%Y-%m-%d %H:%i:%S')");
		command.append(",hasfi="+lateFI);
		command.append(",repaystatus=2,version=version+1 ");
		command.append(" where id="+id + " and version="+version);
		//return t_repayment.update(conn, " id="+id);
		long result=MySQL.executeNonQuery(conn, command.toString());
		command = null;
		return result;
	}

	
	/**   
	 * @MethodName: updateBorrowInfo  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午02:14:20
	 * @Return:    
	 * @Descb: 更新借款信息
	 * @Throws:
	*/
	public long updateBorrowInfo(Connection conn, long borrowId) throws SQLException {
		String command = "update t_borrow set hasDeadline = hasDeadline+1 where id ="+borrowId;
		long result=MySQL.executeNonQuery(conn, command);
		command= null;
		
		return result;
	}

	
	/**   
	 * @MethodName: updateUserAmount  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午02:16:09
	 * @Return:    
	 * @Descb: 更新用户资金
	 * @Throws:
	*/
	public long updateUserAmount(Connection conn, long userId, double needSum) throws SQLException {
		String command = "update t_user set usableSum = usableSum - "+needSum+" where id ="+userId;
		long result =MySQL.executeNonQuery(conn, command);
		command = null;
		return result;
	}

		/**   
	 * @MethodName: queryUserAmountAfterHander  
	 * @Param: BorrowManageDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午09:11:22
	 * @Return:    
	 * @Descb: 查询用户 的资金
	 * @Throws:
	*/
	public Map<String, String> queryUserAmountAfterHander(Connection conn,
			long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select a.usableSum,a.freezeSum,a.lastIP as lastIP,sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest) forPI");
		command.append(" from t_user a left join t_invest b on a.id = b.investor where a.id="+userId+" group by a.id");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws SQLException 
	 * @MethodName: rebackUserCreditLimit  
	 * @Param: BorrowManageDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-24 下午09:01:50
	 * @Return:    
	 * @Descb: 返还用户信用额度
	 * @Throws:
	*/
	public long rebackUserCreditLimit(Connection conn, double borrowAmount,
			long publisher) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append(" update t_user set usableCreditLimit = usableCreditLimit+"+borrowAmount);
		command.append(" where id="+publisher);
	
		long result=MySQL.executeNonQuery(conn, command.toString());
		 command=null;
		return  result;
		
	}

	/**   
		 * @MethodName: queryRiskBalance  
		 * @Param: BorrowManageDao  
		 * @Author: gang.lv
		 * @Date: 2013-4-22 上午09:46:31
		 * @Return:    
		 * @Descb: 查询风险保障金余额
		 * @Throws:
		*/
		public Map<String, String> queryRiskBalance(Connection conn) throws SQLException, DataException {
			DataSet dataSet = MySQL.executeQuery(conn, "select sum(riskInCome-riskSpending) riskBalance from t_risk_detail");
			return BeanMapUtils.dataSetToMap(dataSet);
		}

	/**   
		 * @MethodName: addRiskAmount  
		 * @Param: BorrowManageDao  
		 * @Author: gang.lv
		 * @Date: 2013-4-22 上午10:35:37
		 * @Return:    
		 * @Descb: 累加风险保障金
		 * @Throws:
		*/
		public long addRiskAmount(Connection conn, double riskBalance,
				double riskAmount, long publisher, long id,String resource) throws SQLException {
			Dao.Tables.t_risk_detail t_risk_detail = new Dao().new Tables().new t_risk_detail();
			t_risk_detail.riskBalance.setValue(riskBalance);
			t_risk_detail.riskInCome.setValue(riskAmount);
			t_risk_detail.riskDate.setValue(new Date());
			t_risk_detail.riskType.setValue("收入");
			t_risk_detail.resource.setValue(resource);
			t_risk_detail.trader.setValue(publisher);
			t_risk_detail.borrowId.setValue(id);
			return t_risk_detail.insert(conn);
		}

		
		/**   
		 * @MethodName: spendingRiskAmount  
		 * @Param: FrontMyPaymentDao  
		 * @Author: gang.lv
		 * @Date: 2013-4-27 下午03:34:09
		 * @Return:    
		 * @Descb: 支出风险保障金
		 * @Throws:
		*/
		public long spendingRiskAmount(Connection conn, double riskBalance,
				double riskAmount, long publisher, long id,String resource) throws SQLException {
			Dao.Tables.t_risk_detail t_risk_detail = new Dao().new Tables().new t_risk_detail();
			t_risk_detail.riskBalance.setValue(riskBalance);
			t_risk_detail.riskSpending.setValue(riskAmount);
			t_risk_detail.riskDate.setValue(new Date());
			t_risk_detail.riskType.setValue("支出");
			t_risk_detail.resource.setValue(resource);
			t_risk_detail.trader.setValue(publisher);
			t_risk_detail.borrowId.setValue(id);
			return t_risk_detail.insert(conn);
		}
	
	/**   
	 * @MethodName: queryInvestors  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午03:59:17
	 * @Return:    
	 * @Descb: 查询某条借款的所有投资人
	 * @Throws:
	*/
	public List<Map<String, Object>> queryInvestors(Connection conn, long id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT a.id,b.id as borrowId,a.investor,a.investAmount,b.borrowAmount,d.username,c.stillPrincipal,c.stillInterest,");
		command.append(" c.lateFI,b.borrowTitle,b.paymentmode,c.repayPeriod,b.publisher,a.hasPrincipal,a.hasInterest   FROM t_invest a LEFT JOIN t_borrow b");
		command.append(" ON a.borrowId = b.id LEFT JOIN t_repayment c ON c.borrowId = b.id left join t_user d on a.investor=d.id WHERE c.id = "+id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		command = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	
	/**   
	 * @MethodName: updateInvestRecord  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午04:57:21
	 * @Return:    
	 * @Descb: 更新投资记录
	 * @Throws:
	*/
	public long updateInvestRecord(Connection conn, double hasP, double hasI,
			double hasLFI, double mFee, long investId) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append(" update t_invest set hasPrincipal = hasPrincipal + "+hasP+", hasInterest=hasInterest+"+hasI);
		command.append(" ,recivedFI=recivedFI+"+hasLFI+",manageFee =manageFee+"+mFee+" where id ="+investId);
		return Database.executeNonQuery(conn, command.toString());
	}


	
	/**   
	 * @MethodName: updateInvestRepay  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午04:59:31
	 * @Return:    
	 * @Descb: 还款完成修改投资状态
	 * @Throws:
	*/
	public long updateInvestRepay(Connection conn, long investId) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_invest set repayStatus = 2 where round(recivedPrincipal+recievedInterest,2)=round(hasPrincipal+hasInterest,2) and id ="+investId);
		long result =MySQL.executeNonQuery(conn, command.toString());
		command=null;
		return result;
	}

	
	/**   
	 * @MethodName: updateInvestorSum  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午05:01:09
	 * @Return:    
	 * @Descb: 更新投资人的资金
	 * @Throws:
	*/
	public long updateInvestorSum(Connection conn, double hasSum, long investor) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_user set usableSum = usableSum + "+hasSum+" where id ="+investor);
		long result= MySQL.executeNonQuery(conn, command.toString());
		command=null;
		return  result;
	}

	
	/**   
	 * @throws SQLException 
	 * @MethodName: closeAutoBid  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午05:02:44
	 * @Return:    
	 * @Descb: 收到还款时关闭自动投标
	 * @Throws:
	*/
	public long closeAutoBid(Connection conn, long investor) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_automaticbid set  bidStatus = 1 where userId ="+investor);
		long result=MySQL.executeNonQuery(conn, command.toString());
		command=  null;
		return result;
	}

	
	/**   
	 * @MethodName: queryOverdueInfo  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午03:04:05
	 * @Return:    
	 * @Descb: 查询逾期时间和借款类型
	 * @Throws:
	*/
	public Map<String, String> queryOverdueInfo(Connection conn, long id) throws DataException, SQLException {
		DataSet dataSet = MySQL.executeQuery(conn, "select a.lateDay,b.borrowWay,isDayThe,b.nid_log from t_repayment a left join t_borrow b on a.borrowId=b.id where a.id ="+id);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: updateOverduePay  
	 * @Param: FrontMyPaymentDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-27 下午03:54:38
	 * @Return:    
	 * @Descb: 更新逾期的还款状态为网站垫付
	 * @Throws:
	*/
	public long updateOverduePay(Connection conn, long id) throws SQLException {
		Dao.Tables.t_repayment t_repayment = new Dao().new Tables().new t_repayment();
		t_repayment.isWebRepay.setValue(2);
		return t_repayment.update(conn, " id="+id);
	}

	/**   
	 * @MethodName: deducatedUserAmount  
	 * @Param: BorrowManageDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-30 下午01:59:21
	 * @Return:    
	 * @Descb: 扣除可用资金
	 * @Throws:
	*/
	public long deducatedUserAmount(Connection conn, long userId,double amount) throws SQLException{
		long returnId = -1;
		returnId = Database.executeNonQuery(conn," update t_user set usableSum=usableSum-"+amount+" where id ="+ userId);
		return returnId;
	}
	
	
}
