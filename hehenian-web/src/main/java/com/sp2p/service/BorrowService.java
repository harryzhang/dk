package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.sp2p.constants.IConstants;
import com.sp2p.constants.IFundConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.RechargeDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao.Functions;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.admin.BorrowManageService;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;

/**
 * @ClassName: FinanceService.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:14:21
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 借款业务处理层
 */
public class BorrowService extends BaseService {

	public static Log log = LogFactory.getLog(BorrowService.class);

	private BorrowDao borrowDao;
	private RechargeDao rechargeDao;
	private FundRecordDao fundRecordDao;
	private SelectedService selectedService;
	private BorrowManageService borrowManageService;
	private  AccountUsersDao  accountUsersDao;
	private UserDao  userDao;
	private OperationLogDao  operationLogDao;
	
	public BorrowManageService getBorrowManageService() {
		return borrowManageService;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

	
	/**
     * @param source 
     * @param maxInvest 
     * @param i 
     * @throws DataException
     * @MethodName: addBorrow
     * @Param: BorrowService
     * @Author: gang.lv
     * @Date: 2013-3-7 下午05:02:31
     * @Return: 
     * @Descb: 添加借款业务处理
     * @Throws:
     */
    public Map<String, String> addBorrow(String title, String imgPath, int borrowWay,
            int purpose, int deadLine, int paymentMode, double amount,
            double annualRate, double minTenderedSum, double maxTenderedSum,
            int raiseTerm, int excitationType, double sum, String detail,
            int excitationMode, String investPWD, int hasPWD, String remoteIP,
            long publisher, double fee,int daythe,String basePath,String username,
            double smallestFlowUnit,int circulationNumber, int hasCirculationNumber ,
            int subscribe_status,String  nid_log,double frozen_margin,String json,
            String jsonState,String in_money_purposes, int source, int maxInvest,String zxf,String zxfh,
            int borrowGroup)throws SQLException, DataException{
        
        return addBorrowByImport(title, imgPath, borrowWay,
                purpose, deadLine, paymentMode, amount,
                annualRate, minTenderedSum, maxTenderedSum,
                raiseTerm,  excitationType, sum,  detail,
                excitationMode, investPWD, hasPWD,  remoteIP,
                publisher, fee, daythe, basePath, username,
                smallestFlowUnit, circulationNumber,  hasCirculationNumber ,
                subscribe_status,  nid_log, frozen_margin, json,
                jsonState, in_money_purposes,  source,  maxInvest, zxf, zxfh,
                borrowGroup,"");
        
    }
            
	/**
	 * @param source 
	 * @param maxInvest 
	 * @param i 
	 * @throws DataException
	 * @MethodName: addBorrow
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-7 下午05:02:31
	 * @Return: 
	 * @Descb: 添加借款业务处理
	 * @Throws:
	 */
	public Map<String, String> addBorrowByImport(String title, String imgPath, int borrowWay,
			int purpose, int deadLine, int paymentMode, double amount,
			double annualRate, double minTenderedSum, double maxTenderedSum,
			int raiseTerm, int excitationType, double sum, String detail,
			int excitationMode, String investPWD, int hasPWD, String remoteIP,
			long publisher, double fee,int daythe,String basePath,String username,
			double smallestFlowUnit,int circulationNumber, int hasCirculationNumber ,
			int subscribe_status,String  nid_log,double frozen_margin,String json,
			String jsonState,String in_money_purposes, int source, int maxInvest,String zxf,String zxfh,
			int borrowGroup
			,String businessNo) throws SQLException, DataException {
		Connection conn = Database.getConnection();
        DataSet  ds  = new DataSet();
        List<Object> outParameterValues = new ArrayList<Object>();
        Map<String, String> maps = new HashMap<String, String>();
        Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		
		try {
			Procedures.p_borrow_initialization_hhn(conn, ds, outParameterValues, publisher, title, imgPath, borrowWay, "", deadLine, paymentMode,
					new BigDecimal(amount), new BigDecimal(annualRate),new BigDecimal( minTenderedSum ), new BigDecimal(maxTenderedSum),
					new BigDecimal(raiseTerm), detail, 1, publisher, excitationType, new BigDecimal( sum ), excitationMode,
					purpose,  hasPWD, investPWD, new Date(), remoteIP, daythe,  new BigDecimal(smallestFlowUnit),  circulationNumber, nid_log, new BigDecimal(frozen_margin), 
					"", basePath, new BigDecimal(fee),json, jsonState, "汇付天下",  "",  "",  "",  in_money_purposes, 1,source,maxInvest,zxf,zxfh,borrowGroup,businessNo,0, "");
			
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
//			int borrowId = Convert.strToInt(outParameterValues.get(2) + "", -1);
			maps.put("ret", result+"");
			maps.put("ret_desc", outParameterValues.get(1) + "");
			
			if(result <= 0){
				conn.rollback();
				return maps;
			}
			/*
			 * 20140610 by 刘文韬
			 * 增加用户群组字段
			 */
		/*	String sqlString="update t_borrow set borrowGroup = "+borrowGroup+" where id="+borrowId;
			MySQL.executeNonQuery(conn,sqlString);
			*/
			userMap = userDao.queryUserById(conn, publisher);
			operationLogDao.addOperationLog(conn, "t_borrow",Convert.strToStr(userMap.get("username"), "") , IConstants.INSERT, Convert.strToStr(userMap.get("lastIP"), "") , 0, "用户发布借款", 1);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return maps;
	}
	
    /** 
     * <一句话功能简述><功能详细描述>
     * @param applyAmount
     * @param applyDetail
     * @param userId
     * @return
     * @throws SQLException
     * @throws DataException [参数说明]
     * 
     * @return Long [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void updateUserAddress(long userId, String address)
        throws Exception
    {
        Connection conn = MySQL.getConnection();
        Long result = -1L;
        try
        {
            result = borrowDao.updateUserAddress(conn, userId, address);
            if(result > 0){
                conn.commit();
            }
        }
        catch (Exception e)
        {
            log.error(e);
            conn.rollback();
            e.printStackTrace();
        }
        finally
        {
            conn.close();
        }
    }
	
	public void queryBorrowConcernAppByCondition(String title,
			String publishTimeStart, String publishTimeEnd, long userId,
			PageBean pageBean,String deadline,String borrowWay) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and publishTime >'" + StringEscapeUtils.escapeSql(publishTimeStart.trim())
					+ "'");
		}
		if (StringUtils.isNotBlank(deadline)) {
			condition.append(" and deadline ='" + StringEscapeUtils.escapeSql(deadline.trim())
					+ "'");
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay ='" + StringEscapeUtils.escapeSql(borrowWay.trim())
					+ "'");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and publishTime <'" + StringEscapeUtils.escapeSql(publishTimeEnd.trim())
					+ "'");
		}
		condition.append(" and userId =" + userId);
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_concern", resultFeilds,
					" order by id desc", condition.toString());
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
	}


	/**
	 * @MethodName: queryCreditingByCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午05:13:17
	 * @Return:
	 * @Descb: 根据条件查询信用申请信息
	 * @Throws:
	 */
	public void queryCreditingByCondition(long userId, PageBean pageBean)
			throws SQLException, DataException {
		String resultFeilds = " id,creditingName,applyAmount,applyDetail,status";
		StringBuffer condition = new StringBuffer();
		condition.append(" and applyer =" + userId);
		Connection conn = connectionManager.getConnection();

		try {
			dataPage(conn, pageBean, " v_t_crediting_list", resultFeilds,
					" order by id desc", condition.toString());
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
	}

	/**
	 * @MethodName: queryCreditingApply
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午07:53:11
	 * @Return:
	 * @Descb: 查询能够再次申请信用额度的记录
	 * @Throws:
	 */
	public Map<String, String> queryCreditingApply(long userId)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryCreditingApply(conn, userId);
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
	 * @throws DataException 
	 * @MethodName: addCrediting
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午04:29:23
	 * @Return:
	 * @Descb: 添加信用申请
	 * @Throws:
	 */
	public Long addCrediting(double applyAmount, String applyDetail, long userId)
			throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String,String>  userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = borrowDao.addCrediting(conn, applyAmount, applyDetail,
					userId);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}else{
				userMap= userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_crediting", Convert.strToStr(userMap.get("username"), ""), IConstants.INSERT, Convert.strToStr(userMap.get("lastIP"),""), 0, "发布额度申请", 1);
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
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
	 * @MethodName: queryBorrowTypeNetValueCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-9 下午01:00:07
	 * @Return:
	 * @Descb: 查询用户的薪金贷条件记录
	 * @Throws:
	 */
	public Map<String, String> queryBorrowTypeNetValueCondition(long userId,double borrowSum)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		double amount = 0;
		//待收金额
		double forpaySum = 0;
		//待还金额
		double forRepaySum = 0;
		//可用金额
		double usableSum = 0;
		
		try {
			//待收借款
			Map<String,String> forpayBorrowMap = borrowDao.queryForpayBorrow(conn,userId);
			if(forpayBorrowMap == null){forpayBorrowMap = new HashMap<String, String>();}
			forpaySum = Convert.strToDouble(forpayBorrowMap.get("forpaySum")+"", 0);
			//待还借款
			Map<String,String> forRePaySumMap = borrowDao.queryForRepayBorrow(conn,userId);
			if(forRePaySumMap == null){forRePaySumMap = new HashMap<String, String>();}
			forRepaySum = Convert.strToDouble(forRePaySumMap.get("forRepaySum")+"", 0);
			//用户可用金额
			Map<String,String> userAmountMap = borrowDao.queryUserAmount(conn, userId);
			if(userAmountMap == null){userAmountMap = new HashMap<String, String>();}
			usableSum = Convert.strToDouble(userAmountMap.get("usableSum")+"", 0);
			amount = usableSum + forpaySum - forRepaySum;
			//净值金额大于10000才可以发布
		    if(amount >= 10000){
		          if(borrowSum >0) {
		              //发布借款的上限额
		               amount = amount*0.7;
		              if(borrowSum <=amount) {
		            	  map.put("result", "1");
		              }else{
		            	  map.put("result", "0");
		              }
		          }else{
		        	  map.put("result", "1");
		          }
		    }else{ 
		         map.put("result", "0");
		    }
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
	 * @MethodName: queryBorrowTypeSecondsCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-9 下午01:01:16
	 * @Return:
	 * @Descb: 查询用户的生意贷条件记录
	 * @Throws:
	 */
	public Map<String, String> queryBorrowTypeSecondsCondition(
			double borrowAmount, double borrowAnnualRate, long userId,Map<String,Object> platformCostMap,double frozenMargin)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryBorrowTypeSecondsCondition(conn, borrowAmount,
					borrowAnnualRate, userId,platformCostMap,frozenMargin);
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
	 * 查询用户可以资金是否小于保障金额
	 * @param frozen
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public  Map<String,String>   queryBorrowFinMoney(double frozen,long userId) throws SQLException, DataException{
		Connection  conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryBorrowFinMoney(conn, frozen, userId);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return map;
	}
	/**
	 * @MethodName: refreshBorrowTime
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-17 上午10:58:33
	 * @Return:
	 * @Descb: 刷新借款时间
	 * @Throws:
	 */
	public Long refreshBorrowTime() throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		//借款id
	    long id = -1;
	    //用户id
	    SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
	    String date = "";
	    Map<String,String> maxTime = null;
	    Map<String,String> minTime = null;
	    Map<String,String> borrowStatus = null;
		try {
			List<Map<String,Object>> borrowList = borrowDao.queryBorrow(conn);
			for(Map<String,Object> borrowMap : borrowList){
				date = sf.format(new Date());
				id = Convert.strToLong(borrowMap.get("id")+"", -1);
				//当前时间小于剩余结束时间,剩余开始时间为当前时间
				maxTime = borrowDao.queryMaxTime(conn,id,date);
				if(maxTime !=null && maxTime.size() > 0){
					borrowDao.updateTime(conn,id,date);
				}
				//当前时间大于剩余结束时间,剩余开始时间为剩余结束时间
				minTime = borrowDao.queryMinTime(conn,id,date);
				if(minTime !=null && minTime.size() > 0){
					//借款总额等于投资总额,则为满标,否则流标
					borrowStatus = borrowDao.queryBorrowState(conn,id);
					if(borrowStatus !=null && borrowStatus.size() > 0){
						//更新借款为满标    满标sorts 为 5 
						borrowDao.updateBorrowState(conn,id,IConstants.BORROW_STATUS_3,5);
					}else{
						//更新借款为流标
						borrowManageService.reBackBorrow(id,-1,"");
					}
				}
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

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryBorrowConcernByCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午11:45:15
	 * @Return:
	 * @Descb: 关注的借款列表查询
	 * @Throws:
	 */
	public void queryBorrowConcernByCondition(String title,
			String publishTimeStart, String publishTimeEnd, long userId,
			PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and publishTime >'" + StringEscapeUtils.escapeSql(publishTimeStart.trim())
					+ "'");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and publishTime <'" + StringEscapeUtils.escapeSql(publishTimeEnd.trim())
					+ "'");
		}
		condition.append(" and userId =" + userId);
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_concern", resultFeilds,
					" order by id desc", condition.toString());
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
	}

	
	/**
	 * @throws DataException 
	 * @MethodName: delBorrowConcern
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午12:24:56
	 * @Return:
	 * @Descb: 删除关注的借款
	 * @Throws:
	 */
	public void delBorrowConcern(long idLong, Long userId) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String,String>  userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = borrowDao.delBorrowConcern(conn, idLong, userId);
			if (result < 0) {
				conn.rollback();
			}else{
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_concern", Convert.strToStr(userMap.get("username"), ""), IConstants.DELETE, Convert.strToStr(userMap.get("lastIP"), ""), 0, "删除关注的借款", 1);
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryCreditLimit
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-25 下午10:09:55
	 * @Return:
	 * @Descb: 查询可用信用额度
	 * @Throws:
	 */
	public Map<String, String> queryCreditLimit(double amountDouble, Long id)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryCreditLimit(conn, amountDouble, id);
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
	 * houli 查询是否有未满标审核的借款标的
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long queryBorrowStatus(Long userId) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> lists ;
		int count = 0;
		try {
			lists = borrowDao.queryBorrowStatus(conn,userId);
			if(lists == null || lists.size() <= 0){//如果没有该用户的借款信息，那么该用户可以发布借款
				return 1L;
			}else{
				for(Map<String,Object> map : lists){
					int status = Convert.strToInt(map.get("borrowStatus").toString(), -1);
					if(status > 3 && status < 7){//如果该用户的借款标的已经满标审核通过
						count ++;
					}
				}
				return count == lists.size()? 1L :-1L;
			}
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
	}
	
	/**
	 * add by houli
	 * @param userId
	 * @param status
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long queryBaseApprove(Long userId,int status) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		try {
			Map<String,String> map = borrowDao.queryBaseApprove(conn, userId, status);
			if(map == null || map.size() <= 0){
				return -1L;
			}
			return 1L;
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
	}
	
	
    /** 
     * 根据id查询用户所在地区
     * @param userId
     * @return
     * @throws SQLException
     * @throws DataException [参数说明]
     * 
     * @return Map<String,String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public Map<String, String> queryCity(Long userId) throws Exception{
        Connection conn = connectionManager.getConnection();
        try {
           return borrowDao.queryCity(conn, userId);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        } finally {
            conn.close();
        }
    }
	
	
	public Long queryBaseFiveApprove(Long userId) throws SQLException, DataException{
		Connection conn = connectionManager.getConnection();
		try {
			Map<String,String> map = borrowDao.queryBaseFiveApprove(conn, userId);
			if(map == null || map.size() <= 0){
				return -1L;
			}
			int status = Convert.strToInt(map.get("auditStatus"),-1);
			if(status < 15){
				return -1L;
			}
			return 1L;
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
	}
	
	/**
	 * @throws SQLException 
	 * @throws DataException 
	 * @MethodName: addCirculationBorrow
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-5-17 下午05:06:07
	 * @Return:
	 * @Descb: 添加流转标借款
	 * @Throws:
	 */
	public long addCirculationBorrow(String title, String imgPath,
			int borrowWay, int purposeInt, int deadLineInt, int paymentMode,
			double amountDouble, double annualRateDouble, String remoteIP,
			int circulationNumber, double smallestFlowUnitDouble, Long id,
			String businessDetail, String assets,String moneyPurposes,
			int dayThe, String basePath, String userName,int excitationTypeInt,
			double sumInt,String json,String jsonState,String nid,String  agent,
			String counterAgent,double fee,int source,int maxInvest) throws SQLException, DataException {
		Connection conn = Database.getConnection();
	    DataSet  ds  = new DataSet();
        List<Object> outParameterValues = new ArrayList<Object>();
        Map<String, String> maps = new HashMap<String, String>();
        Map<String,String>  userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			Procedures.p_borrow_initialization(conn, ds, outParameterValues, id, title, imgPath, borrowWay, "", deadLineInt, paymentMode, 
					new BigDecimal(amountDouble),new BigDecimal(annualRateDouble), new BigDecimal(-1), new BigDecimal(-1), new BigDecimal(deadLineInt), "", 1, id, excitationTypeInt, 
					new BigDecimal(sumInt), 1, purposeInt, -1, "", new Date(), remoteIP, dayThe, 
					new BigDecimal(smallestFlowUnitDouble), circulationNumber, nid, new BigDecimal(fee), "", basePath, new BigDecimal(fee),
					json, jsonState, agent, counterAgent, businessDetail, assets, moneyPurposes,2,source,maxInvest,0, "");
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
			maps.put("ret", result+"");
			maps.put("ret_desc", outParameterValues.get(1) + "");
			if(result <= 0){
				conn.rollback();
				return -1L;
			}
			//添加操作日志
			userMap = userDao.queryUserById(conn, id);
			operationLogDao.addOperationLog(conn, "t_borrow",Convert.strToStr(userMap.get("username"), "") , IConstants.INSERT, Convert.strToStr(userMap.get("lastIP"), "") , 0, "用户发布借款", 1);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public RechargeDao getRechargeDao() {
		return rechargeDao;
	}

	public void setRechargeDao(RechargeDao rechargeDao) {
		this.rechargeDao = rechargeDao;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	/**   
	 * @MethodName: queryCurrentCreditLimet  
	 * @Param: BorrowService  
	 * @Author: gang.lv
	 * @Date: 2013-5-11 下午04:47:31
	 * @Return:    
	 * @Descb: 查询当前可用信用额度
	 * @Throws:
	*/
	public Map<String,String> queryCurrentCreditLimet(Long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryCreditLimit(conn, id);
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

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}
	
}
