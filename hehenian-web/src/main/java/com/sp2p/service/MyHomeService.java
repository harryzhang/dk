package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.MyHomeDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.util.DateUtil;
import com.sp2p.util.MoneyUtil;

/**
 * @ClassName: MyHomeService.java
 * @Author: gang.lv
 * @Date: 2013-3-18 下午10:25:00
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的个人主页服务层
 */
public class MyHomeService extends BaseService {

	public static Log log = LogFactory.getLog(MyHomeService.class);
	private MyHomeDao myHomeDao;
	private UserDao userDao;
	private OperationLogDao operationLogDao;

	public MyHomeDao getMyHomeDao() {
		return myHomeDao;
	}

	public void setMyHomeDao(MyHomeDao myHomeDao) {
		this.myHomeDao = myHomeDao;
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

	/**
	 * @MethodName: queryBorrowFinishByCondition
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午10:26:03
	 * @Return:
	 * @Descb: 查询已发布的借款列表
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void queryBorrowFinishByCondition(String title, String publishTimeStart, String publishTimeEnd, String borrowStatus, long userId, PageBean pageBean)
			throws SQLException, DataException {
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and publishTime >'" + StringEscapeUtils.escapeSql(publishTimeStart.trim()) + "'");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and publishTime <'" + StringEscapeUtils.escapeSql(publishTimeEnd.trim()) + "'");
		}
		if (StringUtils.isNotBlank(borrowStatus)) {
			String idStr = StringEscapeUtils.escapeSql("'" + borrowStatus + "'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String[] array = idStr.split(",");
			for (int n = 0; n <= array.length - 1; n++) {
				idSQL += "," + array[n];
			}
			condition.append(" and borrowStatus in(" + idSQL + ")");
		}
		condition.append(" and userId =" + userId);
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_publish", " * ", " order by id desc", condition.toString());
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
	 * @MethodName: queryHomeInfo
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-22 下午07:09:41
	 * @Return:
	 * @Descb: 查询统计后的个人信息
	 * @Throws:
	 */
	public Map<String, String> queryHomeInfo(long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		String startTime = UtilDate.getMonthFirstDay();
		String endTime = UtilDate.getMonthLastDay();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getUserInfo(conn, ds, outParameterValues, userId, startTime, endTime);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryAccountStatisInfo
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-26 上午09:27:44
	 * @Return:
	 * @Descb: 查询账户统计
	 * @Throws:
	 */
	public Map<String, String> queryAccountStatisInfo(long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getUserAmountSumary(conn, ds, outParameterValues, userId);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryLoanStatis
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午02:58:07
	 * @Return:
	 * @Descb: 查询借款统计
	 * @Throws:
	 */
	public Map<String, String> queryLoanStatis(Long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getBorrowStatis(conn, ds, outParameterValues, id, new Date());
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryRepaymentByOwner
	 * @Param: MyHomeService
	 * @Author:
	 * @Date:
	 * @Return:
	 * @Descb: 查询最近还款日及还款金额
	 * @Throws:
	 */

	public Map<String, String> queryRepaymentByOwner(long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeDao.queryRepaymentByOwner(conn, userId);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询成功的充值金额
	 * 
	 * @param userId
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryRechargeInfoOk(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = myHomeDao.queryRechargeInfoOk(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryFinanceStatis
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午02:58:20
	 * @Return:
	 * @Descb: 查询理财统计
	 * @Throws:
	 */
	public Map<String, String> queryFinanceStatis(Long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getFinanceStatis(conn, ds, outParameterValues, id);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryBorrowInvestByCondition
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午11:13:56
	 * @Return:
	 * @Descb: 成功投资借款记录
	 * @Throws:
	 */
	public void queryBorrowInvestByCondition(String title, String publishTimeStart, String publishTimeEnd, String borrowStatus, Long id, PageBean pageBean) throws SQLException,
			DataException {
		// modify by houli 2013-04-25 添加了b.isDayThe字段
		String resultFeilds = "a.id,a.borrowId,b.borrower,b.borrowTitle,b.borrowWay,b.paymentMode,"
				+ "b.annualRate,b.deadline,b.publishTime,b.credit,b.creditrating, round(a.investAmount,2) as investAmount,"
				+ "b.schedules,b.times,b.isDayThe ,b.borrowShow ,a.investTime,b.borrowAmount";
		StringBuffer condition = new StringBuffer();
		if (!"2".equals(borrowStatus)) {
			condition.append(" and ( ( 1=1   ");
		}

		if (StringUtils.isNotBlank(title)) {
			condition.append(" and b.borrowTitle  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and b.publishTime >'" + StringEscapeUtils.escapeSql(publishTimeStart.trim()) + "'");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and b.publishTime <'" + StringEscapeUtils.escapeSql(publishTimeEnd.trim()) + "'");
		}
		if (StringUtils.isNotBlank(borrowStatus)) {
			String idStr = StringEscapeUtils.escapeSql("'" + borrowStatus + "'");
			String idSQL = "-2";
			idStr = idStr.replaceAll("'", "");
			String[] array = idStr.split(",");
			for (int n = 0; n <= array.length - 1; n++) {
				idSQL += "," + array[n];
			}
			condition.append(" and b.borrowStatus in(" + idSQL + ")");
		}
		if (!"2".equals(borrowStatus)) {
			condition.append(" ) or (b.borrowShow=2) )");
		}
		condition.append("   and a.investor =" + id + " order by b.publishTime desc");
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_invest_borrow a  LEFT JOIN   v_t_invest_borrow_list b  ON a.borrowId=b.id ", resultFeilds, "", condition.toString());
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
	 * @MethodName: queryBorrowRecycleByCondition
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 上午11:57:07
	 * @Return:
	 * @Descb: 查询回收中的借款
	 * @Throws:
	 */
	public void queryBorrowRecycleByCondition(String title, Long id, PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE '%" + StringEscapeUtils.escapeSql(title.trim()) + "%'");
		}
		condition.append(" and investor =" + id);
		Connection conn = connectionManager.getConnection();
		StringBuffer comm = new StringBuffer();
		try {
			dataPage(conn, pageBean, " v_t_invest_recycling_my ", resultFeilds, "", condition.toString());
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
	 * @MethodName: queryBorrowRecycledByCondition
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午02:09:03
	 * @Return:
	 * @Descb: 查询已回收的借款
	 * @Throws:
	 */
	public void queryBorrowRecycledByCondition(String title, Long id, PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = "t.bid,t.investor,t.borrowId,t.borrower,t.borrowTitle,t.borrowWay,t.credit,t.creditrating,t.annualRate,t.deadline" + " ,t.isDayThe " + // add
																																										// by
																																										// houli
																																										// 区别天标还是月标
				",round(t.realAmount,2) as realAmount,round(t.forTotalSum,2) forTotalSum,t.isDebt ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and t.borrowTitle  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		condition.append(" and t.investor =" + id + " and IFNULL(t.forTotalSum,0)>0 ");
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, "  (select *  from v_t_invest_recycled_sum_ a LEFT JOIN v_t_invest_borrow_list b ON a.borrowId = b.id where  b.borrowShow = 1 " + " union all"
					+ " select * from  v_t_invest_flow a left join v_t_invest_borrow_list c on  a.borrowId = c.id where  c.borrowShow = 2 " + " ) t", resultFeilds, "",
					condition.toString());
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
	 * @MethodName: queryBorrowForpayById
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:55:11
	 * @Return:
	 * @Descb: 查询投资人回收中的还款详情
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBorrowForpayById(long id, long userId, long iid) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = myHomeDao.queryBorrowForpayById(conn, id, userId, iid);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryBorrowHaspayById
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:55:36
	 * @Return:
	 * @Descb: 查询投资人已回收的还款详情
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBorrowHaspayById(long idLong, long userId, long iid) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = myHomeDao.queryBorrowHaspayById(conn, idLong, userId, iid);
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @MethodName: queryBorrowBackAcountByCondition
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 上午12:02:11
	 * @Return:
	 * @Descb: 借款回账查询
	 * @Throws:
	 */
	public void queryBorrowBackAcountByCondition(String title, String publishTimeStart, String publishTimeEnd, Long id, PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " a.id as investId,c.username as borrower,b.borrowTitle,b.id as borrowId,b.annualRate,b.deadline,"
				+ " round(a.realAmount,2) as realAmount,round((a.hasPrincipal+a.hasInterest),2) forHasSum,round((a.recivedPrincipal+a.recievedInterest-a.hasPrincipal-a.hasInterest),2) forTotalSum"
				+ ",b.isDayThe,b.borrowWay  ";// add by houli 添加是否为天标标志
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and b.borrowTitle  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and b.publishTime >'" + StringEscapeUtils.escapeSql(publishTimeStart.trim()) + " 00:00:00'");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and b.publishTime <'" + StringEscapeUtils.escapeSql(publishTimeEnd.trim()) + " 23:59:59'");
		}
		condition.append(" and a.investor =" + id);
		condition.append(" and (a.recivedPrincipal+a.recievedInterest-a.hasPrincipal-a.hasInterest) > 0");
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " t_invest a left join t_borrow b on a.borrowId = b.id left join t_user c on b.publisher= c.id  ", resultFeilds, "", condition.toString());
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
	 * @MethodName: queryAutomaticBid
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午03:09:33
	 * @Return:
	 * @Descb: 查询用户自动投标设置
	 * @Throws:
	 */
	public Map<String, String> queryAutomaticBid(Long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeDao.queryAutomaticBid(conn, id);
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
	 * @MethodName: automaticBidSet
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:33:29
	 * @Return:
	 * @Descb: 自动投标状态设置
	 * @Throws:
	 */
	public long automaticBidSet(long status, long userId) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = myHomeDao.automaticBidSet(conn, status, userId);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				if (status == 1) {
					operationLogDao.addOperationLog(conn, "t_automaticbid", Convert.strToStr(userMap.get("username"), ""), IConstants.UPDATE,
							Convert.strToStr(userMap.get("lastIP"), ""), 0, "开启自动投标", 1);
				} else {
					operationLogDao.addOperationLog(conn, "t_automaticbid", Convert.strToStr(userMap.get("username"), ""), IConstants.UPDATE,
							Convert.strToStr(userMap.get("lastIP"), ""), 0, "关闭自动投标", 1);
				}
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
	 * @throws DataException
	 * @MethodName: automaticBidModify
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午05:03:37
	 * @Return:
	 * @Descb: 修改自动投标内容
	 * @Throws:
	 */
	public long automaticBidModify(double remandAmount, Long userId, double bidAmount, double rateStart, double rateEnd, int deadlineStart, int deadlineEnd) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			Map<String, String> map = myHomeDao.hasAutomaticBid(conn, userId);
			if (map != null && map.size() > 0) {
				// 更新内容
				result = myHomeDao.automaticBidUpdate(conn, remandAmount, userId, bidAmount,rateStart,rateEnd,deadlineStart,deadlineEnd);
			} else {
				// 修改内容
				result = myHomeDao.automaticBidAdd(conn, remandAmount, userId, bidAmount,rateStart,rateEnd,deadlineStart,deadlineEnd);
			}
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_automaticbid", Convert.strToStr(userMap.get("username"), ""), IConstants.UPDATE,
						Convert.strToStr(userMap.get("lastIP"), ""), 0, "设置自动投标", 1);
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * @MethodName: queryBackAcountStatis
	 * @Param: MyHomeService
	 * @Author: gang.lv
	 * @Date: 2013-3-29 下午11:47:49
	 * @Return:
	 * @Descb: 回账统计
	 * @Throws:
	 */
	public Map<String, String> queryBackAcountStatis(long userId, String startTime, String endTime, String title) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		title = StringEscapeUtils.escapeSql(title.trim());
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getBackAcountStatis(conn, ds, outParameterValues, startTime, endTime, title, userId);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 合和年 查询 用户最近一个月的流水记录
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryFundRecord(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeDao.queryFundRecord(conn, userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}

	public void queryPayingDetailsHHN(long borrowId, HttpServletRequest request) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			this.myHomeDao.queryBorrowneedpayById(conn, borrowId, request);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	// 成功投资
	public void queryInvestSuccessedRecord(Long number, String publishTimeStart, String publishTimeEnd, Long id, PageBean pageBean, String userName) throws SQLException,
			DataException {
		StringBuffer condition = new StringBuffer();
		if (number != -1)
			condition.append(" and number= " + number);

		if (StringUtils.isNotBlank(publishTimeStart))
			condition.append(" and investTime >'" + StringEscapeUtils.escapeSql(publishTimeStart.trim()) + "'");

		if (StringUtils.isNotBlank(publishTimeEnd))
			condition.append(" and investTime <'" + StringEscapeUtils.escapeSql(publishTimeEnd.trim()) + "'");

		if (StringUtils.isNotBlank(userName))
			condition.append(" and username ='" + StringEscapeUtils.escapeSql(userName.trim()) + "'");

		condition.append("   and investor =" + id + " order by investTime desc");
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_invest_successed_record", "*", "", condition.toString());
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

	// 多项合并债权
	public long investsCombine(String ids, Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		long ret = 999L;
		StringBuilder command = new StringBuilder();
		try {
			command.append("select count(distinct borrowId) as count from  t_invest  where id in (" + ids + ")");
			DataSet dataSet = MySQL.executeQuery(conn, command.toString());
			String count = BeanMapUtils.dataSetToMap(dataSet).get("count");
			if (Convert.strToInt(count, -1) != 1) {
				return -2;// 存在不同的标,不能合并
			}

			command = new StringBuilder();
			command.append("select count(1) as count from t_invest where isDebt =3 and id in (" + ids + ")");
			dataSet = MySQL.executeQuery(conn, command.toString());
			count = BeanMapUtils.dataSetToMap(dataSet).get("count");
			if (Convert.strToInt(count, -1) > 0) {
				return -3;// 存在转让的标，不能合并
			}

			// 合并 删除原纪录
			this.myHomeDao.debtCombine(ids);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return ret;// 合并成功
	}

	// 多项拆分债权
	public Map<String, Object> investsSplit(String ids, Long userId) throws Exception {
		StringBuffer command = new StringBuffer();
		Connection conn = MySQL.getConnection();
		Map<String, Object> map1 = new HashMap<String, Object>();
		try {
			command = new StringBuffer();
			command.append("select count(distinct borrowId) as count from t_invest where id in (" + ids + ")");
			DataSet dataSet = MySQL.executeQuery(conn, command.toString());
			String count = BeanMapUtils.dataSetToMap(dataSet).get("count");
			if (Convert.strToInt(count, -1) != 1) {
				map1.put("result", "-2");
				return map1;// 存在不同的标,不能合并
			}

			command = new StringBuffer();
			command.append("select count(1) as count from t_invest where isDebt =3 and id in (" + ids + ")");
			dataSet = MySQL.executeQuery(conn, command.toString());
			count = BeanMapUtils.dataSetToMap(dataSet).get("count");
			if (Convert.strToInt(count, -1) > 0) {
				map1.put("result", "-3");
				return map1;// 存在转让的标，不能合并
			}

			// 条件满足，先合并
			Dao.Tables.t_invest invests = new Dao().new Tables().new t_invest();

			command = new StringBuffer();
			command.append("select sum(realAmount) as investAmount,sum(realAmount) as realAmount,sum(hasPI) as hasPI,sum(recivedPrincipal) as recivedPrincipal,sum(recievedInterest) as recievedInterest,sum(hasPrincipal) as hasPrincipal,sum(hasInterest) as hasInterest,sum(recivedFI) as recivedFI,sum(hasFI) as hasFI,sum(manageFee) as manageFee,sum(reward) as reward,sum(circulationInterest) as circulationInterest,sum(check_principal) as check_principal,sum(check_interest) as check_interest,sum(adjust_principal) as adjust_principal from t_invest where id in (");
			command.append(ids + ")");
			dataSet = MySQL.executeQuery(conn, command.toString());
			Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);

			invests.investAmount.setValue(map.get("investAmount"));
			invests.realAmount.setValue(map.get("realAmount"));
			invests.hasPI.setValue(map.get("hasPI"));
			invests.recivedPrincipal.setValue(map.get("recivedPrincipal"));
			invests.recievedInterest.setValue(map.get("recievedInterest"));
			invests.hasPrincipal.setValue(map.get("hasPrincipal"));
			invests.hasInterest.setValue(map.get("hasInterest"));
			invests.recivedFI.setValue(map.get("recivedFI"));
			invests.hasFI.setValue(map.get("hasFI"));
			invests.manageFee.setValue(map.get("manageFee"));
			invests.reward.setValue(map.get("reward"));
			invests.check_principal.setValue(map.get("check_principal"));
			invests.check_interest.setValue(map.get("check_interest"));
			invests.adjust_principal.setValue(map.get("adjust_principal"));

			String[] idStrings = ids.split(",");
			command = new StringBuffer();
			command.append("select monthRate as monthRate,investor as investor,borrowId as borrowId, investTime as investTime, oriInvestor as oriInvestor, deadline as deadline, hasDeadline as hasDeadline, repayStatus as repayStatus, flag as flag, isAutoBid as isAutoBid, isDebt as isDebt,circulationForpayStatus as circulationForpayStatus,reason as reason,repayDate as repayDate,min_invest_id as min_invest_id,max_invest_id as max_invest_id,isCancel as isCancel,cancelDate as cancelDate,distinguish_debt as distinguish_debt from t_invest where id=");
			command.append(idStrings[0]);
			dataSet = MySQL.executeQuery(conn, command.toString());
			map = BeanMapUtils.dataSetToMap(dataSet);

			invests.monthRate.setValue(map.get("monthRate"));
			invests.investor.setValue(map.get("investor"));
			invests.borrowId.setValue(map.get("borrowId"));
			invests.investTime.setValue(new Date());
			invests.oriInvestor.setValue(map.get("oriInvestor"));
			invests.deadline.setValue(map.get("deadline"));
			invests.hasDeadline.setValue(map.get("hasDeadline"));
			invests.repayStatus.setValue(map.get("repayStatus"));
			invests.flag.setValue(map.get("flag"));
			invests.isAutoBid.setValue(map.get("isAutoBid"));
			invests.isDebt.setValue(map.get("isDebt"));
			invests.reason.setValue(map.get("reason"));
			invests.repayDate.setValue(map.get("repayDate"));

			map1.put("result", "1");
			map1.put("invests", invests);
			return map1;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

	}

	// 查询投资记录按id
	public Map<String, String> queryInvestById(String id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeDao.queryInvestById(conn, id);
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
	 * 根据ID查询出债权总额
	 * 
	 * @param id
	 * @param debtStatus
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @throws DataException
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryDebtSumById(String id) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeDao.queryAlienatorById(conn, id);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	/**
	 * 拆分债权--插入新拆分的债权
	 * 
	 * @param id
	 * @param number
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @throws DataException
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long insertAssignmentDebt(Dao.Tables.t_invest invests, String ids, String userId, Integer number, double realAmount, String split) throws SQLException, DataException {
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = MySQL.getConnection();

		// //将invsets（插入合并结果并删除合并前的旧记录，开始拆分）
		try {
			long result = myHomeDao.deleteAndInsert(conn, invests, ids);
			if (result < 0) {
				return result;
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}
		// 获取何并后的投资id
		StringBuffer command = new StringBuffer();
		command.append("select max(id) as investId from t_invest where investor=" + userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		String id = BeanMapUtils.dataSetToMap(dataSet).get("investId");

		long count = 0;

		try {
			// 根据ID查询出债权的各项信息
			map = myHomeDao.queryAssignmentById(conn, id);
			double rate = 0;

			// 平均拆分
			if (null != map && !split.equals("") && split.equals("1")) {
				rate = 1.0 / number;
				map = mapSplit(map, rate);
				for (int i = 0; i < number; i++) {
					count = myHomeDao.insertAssignmentDebt(conn, map, realAmount);
				}
			}
			// 部分拆分
			if (null != map.get("realAmount") && !map.get("realAmount").equals("0") && split.equals("2") && realAmount < Double.valueOf(map.get("realAmount"))) {
				double money = Double.valueOf(map.get("realAmount")) - realAmount;
				// 拆分金额
				rate = ((double) realAmount / (realAmount + money));
				Map<String, String> temp = myHomeDao.queryAssignmentById(conn, id);
				temp = mapSplit(temp, rate);
				count = myHomeDao.insertAssignmentDebt(conn, temp, realAmount);

				// 拆分余额
				rate = ((double) money / (realAmount + money));
				temp = map;
				temp = mapSplit(temp, rate);
				count = myHomeDao.insertAssignmentDebt(conn, temp, money);
			}
			if (count != 0) {
				myHomeDao.deleteAssignmentDebt(conn, id);
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return count;
	}

	// 删除已合并的债权
	public void deleteInvestsById(String ids) throws SQLException {
		Connection conn;
		conn = MySQL.getConnection();
		try {
			myHomeDao.deleteHasCombineInvest(conn, ids);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> mapSplit(Map<String, String> map, double number) {

		double recivedPrincipal = 0; // 应收本金
		double hasPrincipal = 0;// 已收本金
		double hasInterest = 0; // 已收利息
		double recievedInterest = 0; // 应收利息
		double recivedFI = 0; // 应收罚金
		double hasFI = 0; // 已收罚金
		double reward = 0; // 奖励
		double investAmount = 0;// 投资金额
		double hasPI = 0;// 已收罚息
		double manageFee = 0;// 管理费
		double circulationInterest = 0;// 流转标利息
		double check_principal = 0;// 校验本金
		double check_interest = 0;// 校验利息
		double adjust_principal = 0;// 调整本金

		recivedPrincipal = Double.valueOf(map.get("recivedPrincipal"));
		hasPrincipal = Double.valueOf(map.get("hasPrincipal"));
		hasInterest = Double.valueOf(map.get("hasInterest"));
		recievedInterest = Double.valueOf(map.get("recievedInterest"));
		recivedFI = Double.valueOf(map.get("recivedFI"));
		hasFI = Double.valueOf(map.get("hasFI"));
		reward = Double.valueOf(map.get("reward"));
		investAmount = Double.valueOf(map.get("investAmount"));
		hasPI = Double.valueOf(map.get("hasPI"));
		manageFee = Double.valueOf(map.get("manageFee"));
		circulationInterest = Double.valueOf(map.get("circulationInterest"));
		check_principal = Double.valueOf(map.get("check_principal"));
		check_interest = Double.valueOf(map.get("check_interest"));
		adjust_principal = Double.valueOf(map.get("adjust_principal"));

		if (recivedPrincipal != 0) {
			recivedPrincipal = recivedPrincipal * number; // 应收本金
			map.put("recivedPrincipal", String.valueOf(recivedPrincipal));
		}
		if (hasPrincipal != 0) {
			hasPrincipal = hasPrincipal * number; // 已收本金
			map.put("hasPrincipal", String.valueOf(hasPrincipal));
		}
		// 计算拆分本金、利息
		if (hasInterest != 0) {
			hasInterest = hasInterest * number; // 已收利息
			map.put("hasInterest", String.valueOf(hasInterest));
		}
		if (recievedInterest != 0) {
			recievedInterest = recievedInterest * number; // 应收利息
			map.put("recievedInterest", String.valueOf(recievedInterest));
		}
		if (recivedFI != 0) {
			recivedFI = recivedFI * number; // 应收罚金
			map.put("recivedFI", String.valueOf(recivedFI));
		}
		if (hasFI != 0) {
			hasFI = hasFI * number; // 已收罚金
			map.put("hasFI", String.valueOf(hasFI));
		}
		if (reward != 0) {
			reward = reward * number; // 奖金
			map.put("reward", String.valueOf(reward));
		}// ////////
		if (investAmount != 0) {
			investAmount = investAmount * number; // 投资金额
			map.put("investAmount", String.valueOf(investAmount));
		}
		if (hasPI != 0) {
			hasPI = hasPI * number; // 已收罚息
			map.put("hasPI", String.valueOf(hasPI));
		}
		if (manageFee != 0) {
			manageFee = manageFee * number; // 管理费
			map.put("manageFee", String.valueOf(manageFee));
		}
		if (circulationInterest != 0) {
			circulationInterest = circulationInterest * number; // 流转标利息
			map.put("circulationInterest", String.valueOf(circulationInterest));
		}
		if (check_principal != 0) {
			check_principal = check_principal * number; // 校验本金
			map.put("check_principal", String.valueOf(check_principal));
		}
		if (check_interest != 0) {
			reward = check_interest * number; // 检验利息
			map.put("check_interest", String.valueOf(check_interest));
		}
		if (adjust_principal != 0) {
			adjust_principal = adjust_principal * number; // 调整本金
			map.put("adjust_principal", String.valueOf(adjust_principal));
		}
		return map;
	}

	// 转让
	/**
	 * 添加债权转让
	 * 
	 * @param paramMap
	 *            参数值
	 * @return
	 * @throws SQLException
	 */
	public long addAssignmentDebt(Map<String, String> paramMap) throws SQLException {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long result = -1;
		try {
			if (myHomeDao.isHaveAssignmentDebt(conn, Convert.strToLong(paramMap.get("investId"), -1), Convert.strToLong(paramMap.get("alienatorId"), -1))) {

				result = myHomeDao.addAssignmentDebt(conn, paramMap);
				String borrowTitle = myHomeDao.getBorrowTitle(conn, result);
				// 添加用户动态
				String cotent = "债权转让了借款<a href=queryDebtDetailHHN.do?id=" + result + " target=_blank>" + borrowTitle + "</a>";
				// financeDao.addUserDynamic(conn,
				// Convert.strToLong(paramMap.get("alienatorId"), -1), cotent);
				result = myHomeDao.addUserDynamic(conn, Convert.strToLong(paramMap.get("alienatorId"), -1), cotent);
				userMap = userDao.queryUserById(conn, Convert.strToLong(paramMap.get("alienatorId"), -1));
				operationLogDao.addOperationLog(conn, "t_assignment_debt", Convert.strToStr(userMap.get("username"), ""), IConstants.INSERT,
						Convert.strToStr(userMap.get("lastIP"), ""), 0, "发布债权转让", 1);
				conn.commit();
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}

	// 当前投资
	@SuppressWarnings("unchecked")
	public void queryInvestCurrentRecord(Long investId, String publishTimeStart, String publishTimeEnd, Long id, PageBean pageBean, String userName) throws SQLException,
			DataException {
		StringBuffer condition = new StringBuffer();

		if (investId > 0)
			condition.append(" and id= " + investId);

		if (StringUtils.isNotBlank(publishTimeStart))
			condition.append(" and investTime >'" + StringEscapeUtils.escapeSql(publishTimeStart.trim()) + "'");

		if (StringUtils.isNotBlank(publishTimeEnd))
			condition.append(" and investTime <'" + StringEscapeUtils.escapeSql(publishTimeEnd.trim()) + "'");

		if (StringUtils.isNotBlank(userName))
			condition.append(" and username ='" + StringEscapeUtils.escapeSql(userName.trim()) + "'");

		condition.append("   and investor =" + id);
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_invest_current_record", "*", " order by investTime desc", condition.toString());
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

	// 投资记录金额统计
	public Map<String, String> queryAmount(long userId, String flag) throws Exception {
		StringBuffer command = new StringBuffer();
		Connection conn = MySQL.getConnection();
		try {
			if (flag.equals("success"))
				command.append("select sum(realAmount) as realAmount,sum(hasPI+notPI) as shouldGetAmount,sum(hasPI) as hasGetAmount from v_t_invest_successed_record where investor="
						+ userId);

			if (flag.equals("current"))
				command.append("select freezeSum from t_user where id=" + userId);

			DataSet dataSet = MySQL.executeQuery(conn, command.toString());
			return BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}

	// 用户投资信用积分统计
	public List<Map<String, Object>> queryCreditScores(long id) throws Exception {
		StringBuffer command = new StringBuffer();
		Connection conn = MySQL.getConnection();
		try {
			command.append("SELECT f_credit_rating (tu.creditrating) AS credit, count(ti.investor) AS creditrating");
			command.append(" FROM t_invest ti LEFT JOIN t_borrow tb ON ti.borrowId = tb.id LEFT JOIN t_user tu ON tb.publisher = tu.id");
			command.append(" WHERE investor=" + id + " group by 1 ORDER BY 1 desc");
			DataSet dataSet = MySQL.executeQuery(conn, command.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// 统计所有的投资记录条数
	public Map<String, String> queryAllInvestCount(long id) throws Exception {
		StringBuffer command = new StringBuffer();
		Connection conn = MySQL.getConnection();
		try {
			command.append("select count(*) as counts from t_invest where investor=" + id);
			DataSet dataSet = MySQL.executeQuery(conn, command.toString());
			return BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// 查询用户的推荐人
	public Map<String, String> queryReferee(long userId) throws Exception {
		StringBuffer command = new StringBuffer();
		Connection conn = MySQL.getConnection();
		try {
			command.append("select refferee as refferee from t_user where id=" + userId);
			DataSet dataSet = MySQL.executeQuery(conn, command.toString());
			return BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// 检验用户的推荐是否存在

	// 设置用户的推荐人
	public long setReferee(String referee, String userId) throws Exception {
		StringBuffer command = new StringBuffer();
		Connection conn = MySQL.getConnection();
		try {
			Dao.Tables.t_user user = new Dao().new Tables().new t_user();
			command.append(" id=" + userId);
			user.refferee.setValue(referee);
			long result = user.update(conn, command.toString());
			conn.commit();
			return result;
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 会员中心 当前投资 还款详情
	 */
	public List<Map<String, Object>> queryBorrowForpayHHN(Long userId, long investId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = myHomeDao.queryBorrowForpayHHN(conn, userId, investId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * 系统余额与汇付余额保持相同
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 *             AcctBal 账户余额 = 冻结余额 +可用余额 FrzBal 冻结余额 AvlBal 可用余额
	 */
	public void usableAmountUpdate(long userId, double AvlBal, double FrzBal) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
			t_user.usableSum.setValue(AvlBal);
			t_user.freezeSum.setValue(FrzBal); // FrzBal
			t_user.update(conn, " id = " + userId);
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/** 获取协议详情 **/
	@SuppressWarnings("unchecked")
	public String queryInvestByBorrowId(PageBean pageBean, String borrowId, long userId) throws Exception {
		boolean isOk = false;
		Connection conn = MySQL.getConnection();
		pageBean.setPageSize(999);
		Map<String, String> map = null;
		String content = null;
		try {
			// 投资信息
			dataPage(conn, pageBean, " v_t_agreement ", " * ", "", "  and borrowId=" + borrowId);
			// 借款信息
			map = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, " select * from v_t_agree_borrower where borrowId=" + borrowId));
			// 借款协议模板,无效,改手动拼接
			//content = (BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, " select content from t_message where id=17"))).get("content");
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		if (map == null) {
			return "未找到数据!";
		}
		Date date = null;
		try {
			date = DateUtil.YYYY_MM_DD_MM_HH_SS.parse(map.get("auditTime"));
		} catch (Exception e) {
			date = new Date();
		}
		int status = Convert.strToInt(map.get("borrowStatus"), 0);
		if (userId == Convert.strToLong(map.get("userId"), 0)) {
			isOk = true;
		}
		if (pageBean.getPage() != null) {
			List<Map<String, Object>> list = pageBean.getPage();
			for (Map<String, Object> m : list) {
				if (userId == Convert.strToLong(m.get("userId") + "", 0)) {
					isOk = true;
					break;
				}
			}
		}
		if (status == 4 || status == 5) {
		} else {
			isOk = false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);// 放款时间(合同生效时间)
		String FEE = MoneyUtil.amountToChinese(Convert.strToDouble(map.get("fee"), 0));// 中文大写fee
		map.put("FEE", FEE);
		String html = getAgreement(content, cal, map, pageBean.getPage(), isOk);
		return html;
	}

	/** 拼接协议内容 **/
	protected String getAgreement(String content, Calendar cal, Map<String, String> map, List<Map<String, Object>> page, boolean isOk) {
		StringBuilder sb = new StringBuilder("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		sb.append("<style> p{margin:5px;} td{padding:5px;} </style>");
		sb.append("<title>合和年在线</title>");
		sb.append("</head><body style='font-family:微软雅黑; text-align: left;font-size:12px;'><div align='left'>");
		sb.append("<p align='center' style='text-align:center'><b><span style='font-size:15.0pt;'>借款协议 </span></b></p><p style='text-align:right'>协议编号：").append(map.get("number"))
				.append("</p><p align='left'><b><span>甲方（出借人）： </span> </b></p>");
		sb.append("<table style='width:508px;border-collapse:collapse;text-align: center;font-size:12px;' border='1' cellpadding='0' cellspacing='0'>");
		sb.append("<tbody><tr><td align='center' ><p>合和年在线用户名</p></td>");
		sb.append("<td align='center' ><p>出借金额</p></td>");
		sb.append("<td align='center' ><p>借款期限（月）</p></td></tr>");
		if (isOk) {
			if (page != null && page.size() > 0) {
				for (Map<String, Object> m : page) {
					sb.append("<tr><td  align='center'><p>").append(m.get("username")).append("</p></td><td  align='center' ><p>");
					sb.append(m.get("investAmount")).append("</p></td><td  align='center' ><p>").append(m.get("deadline")).append("</p></td></tr>");
				}
			} else {
				sb.append("<tr><td colspan='3' align='center'><p>").append("暂无出借人").append("</p></td></tr>");
			}
		} else {
			sb.append("<tr><td colspan='3'   align='center'>").append("**成交之后才可查看**").append("</td></tr>");
		}
		sb.append("</tbody></table>").append("<p align='left'><b>乙方（借款人）：");
		if (isOk) {
			sb.append(map.get("realName"));
		} else {
			sb.append("**成交之后才可查看**");
		}
		sb.append("</b></p><p align='left'><b>身份证：");
		if (isOk) {
			StringBuilder idNo = new StringBuilder(map.get("idNo"));
			try {
				idNo = idNo.replace(idNo.length() - 8, idNo.length() - 2, "******");
			} catch (Exception e) {
				e.printStackTrace();
			}
			sb.append(idNo.toString());
		} else {
			sb.append("**成交之后才可查看**");
		}
		sb.append("</b></p><p align='left'><b>用户名：").append(map.get("username")).append("</b></p>");
		content = protol();
		content = content.replace("borrowAmount", map.get("borrowAmount"));// 借款金额
		content = content.replace("annualRate", map.get("annualRate"));
		content = content.replace("deadline", map.get("deadline"));
		content = content.replace("borrowTitle", map.get("borrowTitle"));
		content = content.replace("RMB", map.get("FEE"));// 大写手续费
		content = content.replace("rmb", map.get("fee"));// 小写手续费
		content = content.replace("feeRate", map.get("feeRate"));// 费率

		if (isOk) {
			content = content.replace("yyyy", cal.get(Calendar.YEAR) + "");// 合同生效日
			content = content.replace("mm", (cal.get(Calendar.MONTH) + 1) + "");
			content = content.replace("dd", cal.get(Calendar.DAY_OF_MONTH) + "");
		} else {
			content = content.replace("yyyy", "****");// 合同生效日
			content = content.replace("mm", "**");
			content = content.replace("dd", "**");
		}
		String auditTime = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
		String lastDate = map.get("lastDate");
		try {
			cal.setTime(DateUtil.YYYY_MM_DD_MM_HH_SS.parse(lastDate));
		} catch (ParseException e) {
			cal.setTimeInMillis(System.currentTimeMillis());
		}
		lastDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
		if (isOk) {
			content = content.replace("auditTime", auditTime);// 放款时间.合同生效日
			content = content.replace("repayDay", map.get("repayDay"));// 还款日
			content = content.replace("lastDate", lastDate);// 最后一期还款日
			content = content.replace("cardNO", map.get("cardNO"));// 取现卡号
		} else {
			content = content.replace("auditTime", "********");// 放款时间.合同生效日
			content = content.replace("repayDay", "********");// 还款日
			content = content.replace("lastDate", "********");// 最后一期还款日
			content = content.replace("cardNO", "**********");// 取现卡号
		}

		return sb.append(content).append("</div></body></html>").toString();
	}
	/*
	 * 查询用户所有的投资记录
	 */
	public List<Map<String, Object>> queryAllInvest(long userId,int pageIndex) {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		int offset = (pageIndex-1)*5;
		String sql="SELECT invest_number,date(investTime) investTime,borrowTitle,borrowId,annualRate,t2.deadline,investAmount,t1.id investId,borrowStatus "
				+", (hasPrincipal+hasInterest) hasPI,(recivedPrincipal+recievedInterest-hasPrincipal-hasInterest) dsje "
				+"from t_invest t1 join t_borrow t2 on t1.borrowId=t2.id where t1.investor="+userId+" order by investTime desc limit 10 offset "+offset;
		Connection conn = MySQL.getConnection();
		try {
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/*
	 * 查询用户所有的资金记录
	 */
	public List<Map<String, Object>> queryAllFundrecord(long userId,int pageIndex) {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		int offset = (pageIndex-1)*5;
		String sql="SELECT * from t_fundrecord where userId="+userId+" ORDER BY recordTime desc limit 10 offset "+offset;
		Connection conn = MySQL.getConnection();
		try {
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	public String protol() {
		String sb = "<p><b>丙方（借款管理服务方）：深圳市合和年投资咨询有限公司</b></p><p><b>联系方式：0755-82044824</b></p><p><b>"
				+ "丁方（网络服务方）：深圳市彩付宝网络技术有限公司</b></p><p><b>联系方式：0755-88309824</b></p><p></p><p><b>鉴于：</b></p>"
				+ "<p>1、丁方是一家在深圳市合法成立并有效存续的有限责任公司，拥有网站（www.hehenian.com）（以下简称“该网站”）的经营权，"
				+ "提供信用咨询，为交易提供信息服务；</p><p>2、乙方已在该网站注册，并承诺其提供给丁方的信息是完全真实的；</p><p>"
				+ "3、甲方承诺对本协议涉及的借款具有完全的支配能力，本协议所涉及的借款为其合法所得的自有资金；并承诺其提供给丁方的信息是完全真实的；</p>"
				+ "<p>4、乙方有借款需求，甲方亦同意向乙方提供借款，双方有意建立借贷关系；</p><p>5、丙方是一家在广东省深圳市合法成立并有效存续的有限责任公司；"
				+ "负责为甲乙双方报告订立合同的机会、负责审查乙方的借款申请及相关文件，对乙方是否具备还款能力等情况进行判断、负责本合同项下借款的日常管理工作，"
				+ "并依据与乙方的约定代乙方对甲方的贷款进行代偿。</p><p></p><p><b>各方经协商一致，于 [ yyyy ] 年 [ mm ] 月 [ dd ] 日签订如下协议，共同遵照履行："
				+ "</b></p><p></p><p><b>第一条借款基本信息</b></p><table style='border-collapse:collapse;width:508px;font-size:12px;' border='1' cellpadding='0' cellspacing='0'>"//
				+ "<tr><td><p>借款本金数额</p></td><td><p>borrowAmount</p></td></tr>"
				+ "<tr><td><p>还款方式</p></td><td><p>按月等额本金还款</p></td></tr><tr><td><p>借款年利率</p></td><td><p>annualRate<br/></p></td></tr>"
				+ "<tr><td><p>借款期限（月）</p></td><td><p>deadline</p></td></tr><tr><td><p>起息日（放款日）</p></td><td><p>auditTime</p></td></tr>"
				+ "<tr><td><p>还款日（遇节假日不顺延）</p></td><td><p>repayDay</p></td></tr><tr><td><p>最终到期日</p></td><td><p>lastDate</p></td></tr>"
				+ "<tr><td><p>乙方提现账号</p></td><td><p>cardNO</p></td></tr><tr><td><p>借款用途</p></td><td><p>borrowTitle</p></td></tr></table>"
				+ "<p>注：1、借款期限是指自起息日起至最终到期日（全部借款到期日）止的期间。</p><p>2、起息日（放款日）是指借款资金由上海汇付数据服务有限公司支付平台"
				+ "（以下简称“汇付天下”）从甲方的托管账户成功划出的日期，以汇付天下凭证为准。</p><p>3、最终到期日是指借款期限最后一个月的还款日。</p><p></p>"
				+ "<p><b>第二条各方权利和义务</b></p><p><b>(一)<u>甲方的权利和义务</u></b></p><p>1、甲方应按合同约定的借款日或乙丙双方另行约定的其他" + "日期将足额的借款本金支付给乙方。</p><p>2、甲方同意向乙方出借相应款项时，已委托丁方向汇付天下发出指令在本协议生效时将该笔借款由甲方"
				+ "托管账户直接划付至乙方托管账户。</p><p>3、甲方保证其所用于出借的资金来源合法，甲方是该资金的合法所有人，如果第三人对资金归属、合法性问题发" + "生争议，由甲方负责解决。如甲方未能解决，则放弃享有其所出借款项所带来的利息收益。</p><p>4、甲方享有其所出借款项所带来的利息收益。</p><p>5、如乙"
				+ "方违约，甲方有权要求丁方提供其已获得的乙方信息，乙方同意丁方向甲方提供该等信息。</p><p>6、甲方应缴纳其因收取本协议项下的利息所需依法缴纳的税费。"
				+ "</p><p>7、如乙方还款不足以偿还约定本金、利息及违约金的，各方均同意按照逾期罚息、提前还款服务费、手续费、咨询费、利息、本金的顺序冲销还款。</p><p>"
				+ "<b>(二)<u>乙方权利和义务</u></b></p><p>1、乙方必须按期足额向甲方支付每期应还本金和利息。</p><p>2、乙方按期向甲、丙、丁三方支付本合同项下的借款本"
				+ "金、利息、手续费等其他的相关费用，乙方可采取如下的还款方式进行还款：</p><p>（1）自主还款，乙方将当期应还款项存入乙方在汇付天下的个人托管账户，并同" + "意委托丁方根据《借款协议》、《债权转让协议》、《借款咨询服务协议》的约定向汇付天下发送指令将属于各方的费用划转至各自在汇付天下的托管账户；</p><p>"
				+ "（2）以委托代扣方式还款（具体约定见《代扣授权书》）；</p><p>3、乙方承诺所借款项不用于任何违法用途。</p><p>4、乙方应确保其提供的信息和资料的真实"
				+ "性，不得提供虚假信息或隐瞒重要事实。</p><p>5、乙方有权了解其在丁方的信用评审进度及结果。</p><p>6、乙方不得将本协议项下的任何权利义务转让给任何其"
				+ "他方。</p><p><b>(三)<u>丙方的权利和义务</u></b></p><p>1、丙方应负责审批乙方的借款申请，对乙方是否具备还款能力等情况进行判断并负责本合同项下借款" + "的日常管理工作。丙方有权代甲方在有必要时对乙方进行贷款的违约提醒及催收工作，包括但不限于电话通知、发律师函、对乙方提起诉"
				+ "讼等。甲方在此确认明确委托丙方为其进行以上工作，并授权丙方可以将此工作委托给其他方进行。乙方对前述委托的提醒、催收事项已明确知晓并应积极配合。</p>" + "<p>2、如乙方归还借款本息逾期，甲、乙、丙三方同意丙方代乙方在逾期后第3个工作日先行向甲方偿还乙方逾期归还之借款本息（即借款剩余本金加上当期应还利息）。"
				+ "甲、乙、丙三方同意在丙方完成先行代乙方向甲方偿还乙方逾期归还的借款本息后，丙方取代甲方成为乙方的债权人。乙方除了依据乙方及丙方所签署的《借款咨询服务协议》"
				+ "的约定向丙方支付相关费用外，乙方还应向丙方履行本协议项下其应向甲方履行的义务。</p><p>3、丙方有义务为甲乙双方提供订立合同的机会，促成甲乙双方成立借贷关系。"
				+ "丙方有权就为本合同借款所提供的服务向乙方收取咨询费，咨询费的收取方式由乙丙双方另行约定。</p><p>4、丙方有权了解乙方的信息和借款进展情况。</p><p><b>(四)<u>"
				+ "丁方的权利和义务</u></b></p><p>1、丁方为甲乙双方提供稳定、安全的金融服务网络平台。</p><p>2、丁方有权就为本合同项下借款所提供的服务向乙方收取手续费："
				+ "手续费一次性收取，按借款金额的feeRate%计算，本次借款的手续费为人民币（大写）：<u>RMB</u>，（小写）：￥<u>rmb</u>元。在第一个月的还款日收取，若还款账户内余"
				+ "额不足时，首先冲抵手续费。</p><p>3、丁方应对甲方和乙方的信息及本协议内容保密；如任何一方违约，或因相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构"
				+ "等），丁方有权披露。</p><p><b>第三条违约责任</b></p><p>1、合同各方均应严格履行合同义务，非经各方协商一致或依照本协议约定，任何一方不得解除本协议。</p><p>2、"
				+ "任何一方违约，违约方应承担因违约使得其他各方产生的费用和损失，包括但不限于调查、诉讼费、律师费等，应由违约方承担。如违约方为乙方，甲方或丙方有权立即解除本协议，"
				+ "并要求乙方立即偿还未偿还的本金、利息、咨询费、逾期罚息、服务费等费用。</p><p>如本协议提前解除时，乙方在网站的账户里有任何余款，丁方有权按照本协议第三条第3项的清偿"
				+ "顺序将乙方的余款用于清偿，并要求乙方支付因此产生的相关费用。</p><p>3、乙方的每期还款均应按照如下顺序清偿：</p><p>（1）因乙方逾期还款时乙方向丙方支付的逾期罚息；</p><p>（2）乙方"
				+ "提前还款应支付予丙方的提前还款服务费；</p><p>（3）乙方应支付予丁方的手续费；</p><p>（4）乙方应支付予丙方的咨询费；</p><p>（5）本协议项下借款的利息；</p><p>（6）借款本金。</p><p>4、如果乙方逾期支付任"
				+ "何一期还款超过30天，或连续逾期三期以上（含三期），或累计逾期达五期以上（含五期），或乙方在逾期后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为，可认定为本协议项下"
				+ "的全部借款本息提前到期，乙方应立即清偿本协议下尚未偿付的全部本金、利息、咨询费、逾期罚息、手续费及根据本协议产生的其他全部费用。</p><p>5、如果乙方逾期支付任何一"
				+ "期还款超过30天，或连续逾期三期以上（含三期），或累计逾期达五期以上（含五期），或乙方在逾期后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为，丁方有权将乙方的“"
				+ "逾期记录”记入丁方的“信用信息库”，丁方不承担任何法律责任。</p><p>6、如果乙方逾期支付任何一期还款超过30天，或连续逾期三期以上（含三期），或累计逾期达五期以上"
				+ "（含五期），或乙方在逾期后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为，丁方有权将乙方违约失信的相关信息及乙方其他信息向媒体、用人单位、公安机关、检查机关"
				+ "、法律机关披露，丁方不承担任何责任。</p><p>7、本借款协议中的所有甲方与乙方之间的借款均是相互独立的，一旦乙方逾期未归还借款本息，任何一个甲方有权单独向乙方追"
				+ "索或者提起诉讼。如乙方提供虚假信息的，丁方亦可单独向乙方追索或者提起诉讼。</p><p><b>第五条提前还款</b></p><p>1、自借款起始日三个月内，甲方不接受乙方提前偿"
				+ "还剩余借款。</p><p>2、提前偿还全部剩余借款</p><p>乙方提前清偿全部剩余借款时，如乙方提前清偿的要求得到甲方允许时，甲方委托丙方代为处理提前偿还全部剩余借款的"
				+ "相关事项，乙方除须还清截至还款当日为止的剩余本金及当期应还利息、手续费、咨询费（含逾期产生的所有滞纳金代偿费用）之外，还须向丙方支付提前还款服务费。因提前清偿所"
				+ "产生的提前还款服务费由乙丙双方另行决定，与甲方无关。</p><p>3、出借人不接受部分提前还款。若乙方申请部分提前还款的，部分提前还款资金将由丁方代乙方保管，不提前冲"
				+ "减费用及本息，借款本息、咨询费及其他费用仍按期从中冲减；若余额不足，乙方应按约定的还款方式补足差额部分。</p><p></p><p><b>第六条法律及争议解决</b></p><p>本协"
				+ "议的签订、履行、终止、解释均适用中华人民共和国法律，如发生争议，各方应将争议提交丙方所在地的人民法院管辖。</p><p></p><p><b>第七条附则</b></p><p>1、本协议采"
				+ "用电子文本形式制成，并永久保存在丁方为此设立的专用服务器上备查，各方均认可该形式的协议效力。<br/></p><p>2、本协议自文本最终生成之日生效。</p><p>3、本协议签"
				+ "订之日起至借款全部清偿之日止，甲方或乙方的下列信息如发生变更的，其应当在相关信息发生变更三日内将更新后的信息提供给丁方：本人、本人的家庭联系人及紧急联系人、工"
				+ "作单位、居住地址、住所电话、手机号码、电子邮箱、银行账户的变更。若因任何一方不及时提供上述变更信息而带来的损失或额外费用应由该方承担。</p><p>4、如果本协议中" + "的任何一条或多条违反适用的法律法规，则该条将被视为无效，但该无效条款并不影响本协议其他条款的效力。</p>";
		return sb;
	}
}