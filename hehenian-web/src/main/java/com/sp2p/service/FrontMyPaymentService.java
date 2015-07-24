package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FinanceDao;
import com.sp2p.dao.FrontMyPaymentDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.util.DateUtil;

public class FrontMyPaymentService extends BaseService {

	public static Log log = LogFactory.getLog(FrontMyPaymentService.class);

	private FrontMyPaymentDao frontpayDao;

	private AwardService awardService;

	private SelectedService selectedService;
	private FinanceDao financeDao;

	private AssignmentDebtService assignmentDebtService;

	private InvestDao investDao;

	private BorrowDao borrowDao;

	private OperationLogDao operationLogDao;

	private UserDao userDao;

	// 用于查询逾期的记录

	@SuppressWarnings("unchecked")
	public void queryMySuccessBorrowList(PageBean pageBean, long userId, String startTime, String endTime, String title, int borrowStatus) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String condition = " and publisher=" + userId + "   and borrowId is not null ";
		if (endTime != null) {
			condition += " and publishTime<'" + StringEscapeUtils.escapeSql(endTime) + "'";
		}
		if (startTime != null) {
			condition += " and publishTime>'" + StringEscapeUtils.escapeSql(startTime) + "'";
		}
		if (title != null) {
			condition += " and borrowTitle like '%" + StringEscapeUtils.escapeSql(title) + "%'";
		}
		if (borrowStatus != -1) {// 还款中的记录
			condition += " and borrowStatus =" + borrowStatus;
		} else {
			condition += " and borrowStatus in(4,5)";
		}
		String filed = "id,borrowTitle,borrowWay,borrowAmount,annualRate,deadline,date_format(publishTime,'%Y-%m-%d')  as publishTime,publisher,borrowStatus,paymentMode, isDayThe,borrowId,stillTotalSum,hasPI,hasSum,hasFI,date_format(auditTime,'%Y-%m-%d') as auditTime ";
		try {
			dataPage(conn, pageBean, "t_borrow_success_list", filed, " ", condition);// return
			// myHomeInfoSettingDao.queryReceiveMails(conn,
			// userId,mailType,
			// -1,
			// -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryOneBorrowInfo(long userId, long borrowId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return frontpayDao.queryOneBorrowInfo(conn, userId, borrowId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void queryPayingDetails(PageBean pageBean, long borrowId, Integer repayStatus) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		// 去掉已还状态的明细
		String condition = "";
		if (repayStatus == IConstants.PAYING_STATUS_SUCCESS) {
			condition = " and borrowId=" + borrowId + " and repayStatus=" + IConstants.PAYING_STATUS_SUCCESS;
		} else {
			condition = " and borrowId=" + borrowId;
		}
		try {
			// 按还款日期升序排
			dataPage(conn, pageBean, "t_success_paying_details", " * ", " order by repayDate asc", condition);// return
			// myHomeInfoSettingDao.queryReceiveMails(conn,
			// userId,mailType,
			// -1,
			// -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	@SuppressWarnings("unchecked")
	public void queryAllDetails(PageBean pageBean, long userId, String startTime, String endTime, String title) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		// 去掉已还状态的明细
		String condition = " and publisher=" + userId;
		if (endTime != null) {
			condition += " and publishTime<='" + StringEscapeUtils.escapeSql(endTime) + "'";
		}
		if (startTime != null) {
			condition += " and publishTime>='" + StringEscapeUtils.escapeSql(startTime) + "'";
		}
		if (title != null) {
			condition += " and borrowTitle like '%" + StringEscapeUtils.escapeSql(title) + "%'";
		}
		try {
			// 按还款日期升序排
			dataPage(conn, pageBean, "v_t_repayment_detail", "*", "", condition);// return
			// myHomeInfoSettingDao.queryReceiveMails(conn,
			// userId,mailType,
			// -1,
			// -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	@SuppressWarnings("unchecked")
	public void queryBorrowInvestorInfo(PageBean pageBean, long userId, String investor) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		// 去掉已还状态的明细
		String condition = " and a.publisher=" + userId + " and a.borrowId is not null ";
		if (StringUtils.isNotBlank(investor)) {
			condition += " and a.username  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(investor.trim()) + "','%')";
		}
		try {
			// 按还款日期升序排
			dataPage(conn, pageBean, "(SELECT * FROM v_t_bacount_detail UNION ALL SELECT * FROM v_t_bacount_history_detail) a", "*", " ", condition);// return
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<Map<String, Object>> queryPayingBorrowIds(long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();

		try {
			return frontpayDao.queryPayingBorrowIds(conn, userId);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryMyPayData(long payId) throws SQLException, DataException {
		if (payId < 0)
			return null;
		Connection conn = connectionManager.getConnection();
		try {
			return frontpayDao.queryMyPayData(conn, payId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @Descb: 提交还款
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> submitPay(long id, long userId, String dealPWD, String basePath, String username, double needSum, double fee, double consultFee) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		if ("1".equals(IConstants.ENABLED_PASS)) {
			dealPWD = com.shove.security.Encrypt.MD5(dealPWD.trim());
		} else {
			dealPWD = com.shove.security.Encrypt.MD5(dealPWD.trim() + IConstants.PASS_KEY);
		}
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();

		long ret = -1;
		try {
			// 查询借款信息得到借款时插入的平台收费标准
			Map<String, String> mapacc = borrowDao.queryBorrowCostByPayId(conn, id);
			String feelog = Convert.strToStr(mapacc.get("feelog"), "");
			Map<String, Double> feeMap = (Map<String, Double>) JSONObject.toBean(JSONObject.fromObject(feelog), HashMap.class);
			double investFeeRate = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE) + "", 0);
			Procedures.p_borrow_repayment(conn, ds, outParameterValues, id, userId, dealPWD, basePath, new Date(), new BigDecimal(investFeeRate),
					new DecimalFormat("0.00").format(needSum), new DecimalFormat("0.00").format(fee), new DecimalFormat("0.00").format(consultFee), -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret <= 0) {
				conn.rollback();
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_repayment", Convert.strToStr(userMap.get("username"), ""), IConstants.UPDATE, Convert.strToStr(userMap.get("lastIP"), ""),
						0, "用户还款", 1);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return map;
		
	}

	/**
	 * @param needSum
	 * @throws Exception
	 * @MethodName: submitPay
	 * @Param: FrontMyPaymentService
	 * @Date: 2013-4-11 下午05:50:31
	 * @Return:
	 * @Descb: 后台提交还款
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> submitPayAdmin(long id, long userId, String basePath, String username, Date repayDate, double needSum, double fee, double consultFee)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();

//		Date times = new Date();
		// 计算当前时间是否等于还款时间：date = 0,则时间相等
//		int date = (int) DateUtil.diffDays(times, repayDate);

		// 根据用户id查询用户信息
		userMap = userDao.queryUserById(conn, userId);
		long ret = -1;
		try {
			// 判断还款时间是否等于当前时间
//			if (0 < date) {// 如果当前还款日期小于实际还款日期，则是提前还款，
//							// 如提前还款，收取3%提前还款手续费
//				// 查询借款信息得到借款时插入的平台收费标准
//				Map<String, String> mapacc = borrowDao.queryBorrowCostByPayId(conn, id);
//				String feelog = Convert.strToStr(mapacc.get("feelog"), "");
//				Map<String, Double> feeMap = (Map<String, Double>) JSONObject.toBean(JSONObject.fromObject(feelog), HashMap.class);
//				double investFeeRate = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE) + "", 0);
//				Procedures.p_borrow_repayment_time(conn, ds, outParameterValues, id, userId, userMap.get("dealpwd"), basePath, new Date(), new BigDecimal(investFeeRate),
//						new BigDecimal(needSum), new DecimalFormat("0.00").format(fee), new DecimalFormat("0.00").format(consultFee), -1, "");
//				ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
//				map.put("ret", ret + "");
//				map.put("ret_desc", outParameterValues.get(1) + "");
//				if (ret <= 0) {
//					conn.rollback();
//				} else {
//					operationLogDao.addOperationLog(conn, "t_repayment", Convert.strToStr(userMap.get("username"), ""), IConstants.UPDATE,
//							Convert.strToStr(userMap.get("lastIP"), ""), 0, "用户还款", 1);
//					conn.commit();
//				}
//			} else {
				// 查询借款信息得到借款时插入的平台收费标准
				Map<String, String> mapacc = borrowDao.queryBorrowCostByPayId(conn, id);
				String feelog = Convert.strToStr(mapacc.get("feelog"), "");
				Map<String, Double> feeMap = (Map<String, Double>) JSONObject.toBean(JSONObject.fromObject(feelog), HashMap.class);
				double investFeeRate = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE) + "", 0);
				Procedures.p_borrow_repayment(conn, ds, outParameterValues, id, userId, userMap.get("dealpwd"), basePath, new Date(), new BigDecimal(investFeeRate),
						new DecimalFormat("0.00").format(needSum), new DecimalFormat("0.00").format(fee), new DecimalFormat("0.00").format(consultFee), -1, "");
				ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
				map.put("ret", ret + "");
				map.put("ret_desc", outParameterValues.get(1) + "");
				if (ret <= 0) {
					conn.rollback();
				} else {
					userMap = userDao.queryUserById(conn, userId);
					operationLogDao.addOperationLog(conn, "t_repayment", Convert.strToStr(userMap.get("username"), ""), IConstants.UPDATE,
							Convert.strToStr(userMap.get("lastIP"), ""), 0, "用户还款", 1);
					conn.commit();
				}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询当前最大投资金额
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querInvesttou() throws SQLException, DataException {
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			Procedures.pr_investStatistics(conn, ds, outParameterValues, 0);
			map = BeanMapUtils.dataSetToMap(ds);
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

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public InvestDao getInvestDao() {
		return investDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}

	public AssignmentDebtService getAssignmentDebtService() {
		return assignmentDebtService;
	}

	public void setAssignmentDebtService(AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public FrontMyPaymentDao getFrontpayDao() {
		return frontpayDao;
	}

	public void setFrontpayDao(FrontMyPaymentDao frontpayDao) {
		this.frontpayDao = frontpayDao;
	}

	public AwardService getAwardService() {
		return awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}

	public Map<String, String> getDealPWD(Long id, String dealpwd) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			if ("1".equals(IConstants.ENABLED_PASS)) {
				dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
			} else {
				dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim() + IConstants.PASS_KEY);
			}
			map = financeDao.getDealPWD(conn, id, dealpwd);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 通过还款id获得所有所需收款的信息
	 */
	public List<Map<String, Object>> queryAllInvestInfo(long payId) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			return financeDao.queryAllInvestInfo(conn, payId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}

	/**
	 * 提前还款的信息
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryPreInvestInfo(long payId) throws SQLException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			DataSet ds = MySQL.executeQuery(conn, "select borrowId from t_repayment where id=" + payId);
			map = BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (map == null || map.size() == 0){
			conn.close();
			return null;
		}

		String sql = "SELECT ordId,inCustId,subOrdDate,subOrdId,sum(repayFees) repayFee,sum(consultFees) consultFee,sum(interests) interest,principal ";
		sql += " FROM (SELECT ordId,inCustId,subOrdDate,subOrdId, repayFee,outCustId,IF (payId = " + payId + ", repayFee, 0)  repayFees,";
		sql += " IF (payId = " + payId + ",consultFee,0) consultFees,IF (payId = " + payId + ", interest, 0) interests, SUM(principal) principal ";
		sql += " FROM v_t_chianpnr_repayhhn WHERE payId >= " + payId + " AND borrowId = " + map.get("borrowId") + " GROUP BY subOrdId ) T GROUP BY T.subOrdId ";
		try {
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}

}
