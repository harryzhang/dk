package com.sp2p.service.admin;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.config.ChinaPnrConfig;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FrontMyPaymentDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.dao.admin.AfterCreditManageDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.entity.Admin;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.AwardService;
import com.sp2p.service.SelectedService;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;

/**
 * @ClassName: AfterCreditManageService.java
 * @Author: gang.lv
 * @Date: 2013-3-19 上午10:18:35
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 贷后管理业务处理层
 */
public class AfterCreditManageService extends BaseService {

	public static Log log = LogFactory.getLog(AfterCreditManageService.class);

	private AfterCreditManageDao afterCreditManageDao;
	private SelectedService selectedService;
	private AwardService awardService;
	private AssignmentDebtService assignmentDebtService;
	private FrontMyPaymentDao frontpayDao;
	private InvestDao investDao;
	private BorrowDao borrowDao;

	/**
	 * @MethodName: queryAfterCreditAuditByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午10:20:22
	 * @Return:
	 * @Descb: 贷后管理最近3天还款记录
	 * @Throws:
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void queryLastRepayMentByCondition(String userName, int borrowWay, String realName, String title, int status, String type, PageBean pageBean) throws SQLException,
			DataException {
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		Calendar calendar = Calendar.getInstance();
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realName  like '%" + StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and  borrowTitle   LIKE '%" + StringEscapeUtils.escapeSql(title.trim()) + "%'");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and  borrowWay  =" + borrowWay);
		}
		if (IConstants.DEFAULT_NUMERIC != status) {
			condition.append(" and  repayStatus  =" + status);
		}
		if ("".equals(type)) {
			Date date = calendar.getTime();
			condition.append(" and repayDate ='" + sf.format(date) + "'");
		} else if ("1".equals(type)) {
			calendar.add(calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			condition.append(" and repayDate ='" + sf.format(date) + "'");
		} else if ("2".equals(type)) {
			calendar.add(calendar.DAY_OF_YEAR, 2);
			Date date = calendar.getTime();
			condition.append(" and repayDate ='" + sf.format(date) + "'");
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_repayment_h", resultFeilds, " order by  id  desc", condition + "");
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
	 * @MethodName: queryRepaymentAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午11:32:00
	 * @Return:
	 * @Descb: 根据条件统计最近还款总额
	 * @Throws:
	 */
	public Map<String, String> queryRepaymentAmount(String userName, int borrowWay, String realName, String title, int status, String type) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryRepaymentAmount(conn, userName, borrowWay, realName, title, status, type);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	public AfterCreditManageDao getAfterCreditManageDao() {
		return afterCreditManageDao;
	}

	public void setAfterCreditManageDao(AfterCreditManageDao afterCreditManageDao) {
		this.afterCreditManageDao = afterCreditManageDao;
	}

	/**
	 * @throws DataException
	 * @MethodName: queryRepayMentNoticeByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-19 下午02:53:11
	 * @Return:
	 * @Descb: 查询还款的沟通记录
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void queryRepayMentNoticeByCondition(long idLong, PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " id,serviceContent,date_format(serviceTime,'%Y-%c-%d %T') as serviceTime ";
		StringBuffer condition = new StringBuffer();
		condition.append(" and  repayId  = " + idLong);
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " t_repayment_service", resultFeilds, " order by  id desc", condition.toString());
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
	 * @MethodName: addRepayMentNotice
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-19 下午07:02:10
	 * @Return:
	 * @Descb: 添加还款沟通记录
	 * @Throws:
	 */
	public long addRepayMentNotice(long idLong, String content) throws SQLException {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = afterCreditManageDao.addRepayMentNotice(conn, idLong, content);
			if (result <= 0) {
				conn.rollback();
				return -1L;
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
	 * @MethodName: queryForPaymentByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午06:32:51
	 * @Return:
	 * @Descb: 根据条件查询待收款记录
	 * @Throws: modify by houli 添加反选标志 inverse
	 */
	@SuppressWarnings("unchecked")
	public void queryForPaymentByCondition(String investor, String timeStart, String timeEnd, String title, int borrowWayInt, int groupInt, PageBean pageBean, boolean inverse)
			throws SQLException, DataException {

		String resultFeilds = " investor,realName ,groupName,  investTime  ,repayPeriod,id,borrowTitle,borrowWay,repayDate,isDayThe,round(forTotalSum,2) forTotalSum,username ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and  investor  like '%" + StringEscapeUtils.escapeSql(investor.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" AND  repayDate  >= '" + StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" AND  repayDate  <= '" + StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and  borrowTitle  like '%" + StringEscapeUtils.escapeSql(title.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			condition.append(" AND  borrowWay  =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != groupInt) {
			if (inverse) {// 如果是反选
				condition.append(" AND (  groupId  !=" + groupInt + "  or  groupId  is null ) ");
			} else {
				condition.append(" AND  groupId  =" + groupInt);
			}
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_forpayment_h ", resultFeilds, "", condition.toString());
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
	 * @MethodName: queryForPaymentByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午06:32:51
	 * @Return:
	 * @Descb: 根据条件查询待还款记录
	 * @Throws: modify by houli 添加反选标志 inverse
	 */
	@SuppressWarnings("unchecked")
	public void queryForPaymentByDueIn(String investor, String timeStart, String timeEnd, String title, int borrowWayInt, int groupInt, PageBean pageBean, boolean inverse)
			throws SQLException, DataException {

		String resultFeilds = "   *  ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and  investor  like '%" + StringEscapeUtils.escapeSql(investor.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" AND  repayDate  >= '" + StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" AND  repayDate  <= '" + StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and  borrowTitle  like '%" + StringEscapeUtils.escapeSql(title.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			condition.append(" AND  borrowWay  =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != groupInt) {
			if (inverse) {// 如果是反选
				condition.append(" AND (  groupId  !=" + groupInt + "  or  groupId  is null ) ");
			} else {
				condition.append(" AND  groupId  =" + groupInt);
			}
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_deuin_list ", resultFeilds, " order by id desc", condition.toString());
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
	 * @MethodName: queryForPaymentAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午06:33:16
	 * @Return:
	 * @Descb: 根据条件查询待收款统计
	 * @Throws:
	 */
	public Map<String, String> queryForPaymentAmount(String investor, String timeStart, String timeEnd, String title, int borrowWayInt, int groupInt, boolean inverse)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryForPaymentAmount(conn, investor, timeStart, timeEnd, title, borrowWayInt, groupInt, inverse);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryForPaymentTotalByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午10:09:16
	 * @Return:
	 * @Descb: 代收款总计记录
	 * @Throws: add by houli 2014-04-26 添加反选条件 inverse
	 */
	@SuppressWarnings("unchecked")
	public void queryForPaymentTotalByCondition(String investor, String timeStart, String timeEnd, int deadlineInt, int groupInt, PageBean pageBean, boolean inverse)
			throws SQLException, DataException {
		String resultFeilds = " investor , realName,groupName,round(investAmount,2) as investAmount ,scale , publishTime , round(borrowAmount,2) as borrowAmount ,borrowWay,isDayThe,repayPeriod,repayDate,round(forTotalSum,2) as forTotalSum";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and  investor  like '%" + StringEscapeUtils.escapeSql(investor.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and  repayDate  >= '" + StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and  repayDate  <= '" + StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (IConstants.DEFAULT_NUMERIC != deadlineInt) {
			condition.append(" and  deadline  =" + deadlineInt);
		}
		if (IConstants.DEFAULT_NUMERIC != groupInt) {
			if (inverse) {
				condition.append(" and (  groupId  !=" + groupInt + "  or  groupId  is null )");
			} else {
				condition.append(" and  groupId  =" + groupInt);
			}
		}

		StringBuffer sql = new StringBuffer();
		sql.append(" v_t_forpayment_h ");
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, sql.toString(), resultFeilds, "", condition.toString());
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
	 * @MethodName: queryForPaymentTotalAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午10:08:58
	 * @Return:
	 * @Descb: 代收款总计统计
	 * @Throws:
	 */
	public Map<String, String> queryForPaymentTotalAmount(String investor, String timeStart, String timeEnd, int deadlineInt, int groupInt, boolean inverse) throws DataException,
			SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryForPaymentTotalAmount(conn, investor, timeStart, timeEnd, deadlineInt, groupInt, inverse);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryHasRepayByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午02:37:15
	 * @Return:
	 * @Descb: 查询已收款列表
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void queryHasRepayByCondition(String userName, String realName, String timeStart, String timeEnd, int borrowWayInt, int deadlineInt, int repayStatusInt,
			PageBean pageBean, String timeStart1, String timeEnd1)// add
																	// by
																	// houli
																	// 添加两个时间变量
			throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username   like '%" + StringEscapeUtils.escapeSql(userName) + "%' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realName   like '%" + StringEscapeUtils.escapeSql(realName) + "%' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and realRepayDate >= '" + StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and realRepayDate <= '" + StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			condition.append(" and borrowWay =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != deadlineInt) {
			condition.append(" and deadline =" + deadlineInt);
		}
		if (IConstants.DEFAULT_NUMERIC != repayStatusInt) {
			condition.append(" and repayStatus =" + repayStatusInt);
		}
		// -------add by houli
		if (StringUtils.isNotBlank(timeStart1)) {
			condition.append(" and repayDate >= '" + StringEscapeUtils.escapeSql(timeStart1) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd1)) {
			condition.append(" and repayDate <= '" + StringEscapeUtils.escapeSql(timeEnd1) + "'");
		}
		// ---------------
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_hasrepay_h ", resultFeilds, " order by  id desc", condition.toString());
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
	 * @MethodName: queryHasRePayAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午02:37:02
	 * @Return:
	 * @Descb: 已收款统计
	 * @Throws:
	 */
	public Map<String, String> queryHasRePayAmount(String userName, String realName, String timeStart, String timeEnd, int borrowWayInt, int deadlineInt, int repayStatusInt,
			String timeStart1, String timeEnd1) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryHasRePayAmount(conn, userName, realName, timeStart, timeEnd, borrowWayInt, deadlineInt, repayStatusInt,
			// add by houli
					timeStart1, timeEnd1);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryLateRepayByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:16:54
	 * @Return:
	 * @Descb: 逾期的借款记录
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void queryLateRepayByCondition(String userName, int borrowWayInt, int statusInt, PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and userName  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			condition.append(" and  borrowWay  =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != statusInt) {
			condition.append(" and  repayStatus  =" + statusInt);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_laterepay_h", resultFeilds, "", condition.toString());
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
	 * @MethodName: queryLateRepayAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:17:01
	 * @Return:
	 * @Descb: 逾期的借款统计
	 * @Throws:
	 */
	public Map<String, String> queryLateRepayAmount(String userName, int borrowWayInt, int statusInt) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryLateRepayAmount(conn, userName, borrowWayInt, statusInt);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 根据还款ID 查询还款详情
	 * 
	 * @throws DataException
	 * @throws SQLException
	 */
	public void queryByrepayId(int id, PageBean<Map<String, Object>> pageBean) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			StringBuffer table = new StringBuffer();
			table.append(" t_invest_repayment a	LEFT JOIN t_invest b ON a.invest_id = b.id	LEFT JOIN t_user c ON b.investor = c.id	left join (select username as borrowName ,t.id from t_borrow t LEFT JOIN t_user u  on  t.publisher = u.id) e	on e.id = b. borrowId ");
			StringBuffer filed = new StringBuffer();
			filed.append(" c.username , a.repayPeriod,a.lateDay, a.repayId ,a.realRepayDate ,e.borrowName ,FORMAT(a.recivedInterest,2) as recivedInterest , FORMAT(a.hasPrincipal,2) as hasPrincipal,FORMAT(a.hasInterest,2) as hasInterest,a.isWebRepay,a.isLate,FORMAT( a.recivedFI ,2) as recivedFI  ");
			dataPage(conn, pageBean, table.toString(), filed.toString(), "", " and  a.repayStatus = 2 and a.repayId = " + id);
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
	 * 根据还款ID 查询还款详情
	 * 
	 * @throws DataException
	 * @throws SQLException
	 */
	public void queryByrepayIdDueId(int id, PageBean<Map<String, Object>> pageBean) throws DataException, SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			StringBuffer table = new StringBuffer();
			table.append("  t_invest_repayment a LEFT JOIN  t_repayment s  on a.repayId = s.id	LEFT JOIN t_invest b ON a.invest_id = b.id	LEFT JOIN t_user c ON b.investor = c.id	left join (select username as borrowName ,t.id from t_borrow t LEFT JOIN t_user u  on  t.publisher = u.id) e	on e.id = b. borrowId ");
			StringBuffer filed = new StringBuffer();
			filed.append(" c.username , a.repayPeriod,s.lateDay,FORMAT(a.recivedInterest,2) as recivedInterest , FORMAT(a.recivedPrincipal,2) as recivedPrincipal,FORMAT(a.hasInterest,2) as hasInterest,a.isLate,FORMAT( a.recivedFI ,2) as recivedFI ,s.id ,date_format(s.repayDate, '%Y-%m-%d') as repayDate,e.borrowName ,a.isWebRepay ");
			dataPage(conn, pageBean, table.toString(), filed.toString(), "", " and  a.repayStatus = 1 and a.repayId = " + id);
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
	 * @throws SQLException
	 * @MethodName: queryOverduePaymentByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:42:31
	 * @Return:
	 * @Descb: 逾期垫付的借款记录
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void queryOverduePaymentByCondition(String userName, int borrowWayInt, int statusInt, PageBean pageBean) throws DataException, SQLException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and userName  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			condition.append(" and  borrowWay  =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != statusInt) {
			condition.append(" and  repayStatus  =" + statusInt);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_overduepayment_h", resultFeilds, "", condition.toString());
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
	 * @MethodName: queryOverduePaymentAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:43:05
	 * @Return:
	 * @Descb: 逾期垫付的借款统计
	 * @Throws:
	 */
	public Map<String, String> queryOverduePaymentAmount(String userName, int borrowWayInt, int statusInt) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryOverduePaymentAmount(conn, userName, borrowWayInt, statusInt);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryRepaymentDetail
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午02:12:38
	 * @Return:
	 * @Descb: 还款记录详情
	 * @Throws:
	 */
	public Map<String, String> queryRepaymentDetail(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryRepaymentDetail(conn, id);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryRepaymentService
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午02:12:08
	 * @Return:
	 * @Descb: 借款客服沟通记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryRepaymentService(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = afterCreditManageDao.queryRepaymentService(conn, id);
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
		return list;
	}

	/**
	 * @MethodName: queryRepaymentColectoin
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午02:12:27
	 * @Return:
	 * @Descb: 借款催收记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryRepaymentCollection(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = afterCreditManageDao.queryRepaymentCollection(conn, id);
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
		return list;
	}

	/**
	 * @throws SQLException
	 * @MethodName: addCollection
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午04:12:57
	 * @Return:
	 * @Descb: 添加催款记录
	 * @Throws:
	 */
	public long addCollection(long idLong, String colResult, String remark) throws SQLException {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = afterCreditManageDao.addCollection(conn, idLong, colResult, remark);
			if (result <= 0) {
				conn.rollback();
				return -1L;
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
	 * @throws SQLException
	 * @MethodName: delCollection
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午04:35:41
	 * @Return:
	 * @Descb: 删除催收记录
	 * @Throws:
	 */
	public long delCollection(long idLong) throws SQLException {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = afterCreditManageDao.delCollection(conn, idLong);
			if (result <= 0) {
				conn.rollback();
				return -1L;
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
	 * @Descb: 逾期垫付还款
	 */
	@SuppressWarnings("unchecked")
	public String overduePaymentRepaySubmit(long repayId, Long adminId, String basePath, String borrowId, Admin admin) throws Exception {

		Connection conn = MySQL.getConnection();
//		Map<String, String> map = new HashMap<String, String>();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		long ret = -1;
		String result = null;
		try {
			// 查询借款信息得到借款时插入的平台收费标准
			Map<String, String> mapacc = borrowDao.queryBorrowCostByPayId(conn, repayId);
			String feelog = Convert.strToStr(mapacc.get("feelog"), "");
			Map<String, Double> feeMap = (Map<String, Double>) JSONObject.toBean(JSONObject.fromObject(feelog), HashMap.class);
			double investFeeRate = Convert.strToDouble(feeMap.get(IAmountConstants.BORROW_FEE_HHN) + "", 0);

			// 根据借款ID查询出逾期投资人信息
			List<Map<String, Object>> list = borrowDao.queryAllInvestInfo(conn, repayId + "");

			JSONObject json = new JSONObject();

			if (list == null || list.equals("[]")) {
				return null;
			}

			String outCustId = ChinaPnrConfig.chinapnr_dc;// 担保公司(代偿,只需具备担保资格的担保公司客户号,即企业开户账户)
			String outAcctId = "MDT000001";
			for (Map<String, Object> maps : list) {

				String amount = maps.get("amount") + "";
				StringBuffer sb = new StringBuffer();
				if (null != amount && amount.indexOf(".") == -1) {
					amount = sb.append(amount).append(".00").toString();
				} else {
					amount = amount.substring(0, amount.indexOf(".") + 3);
				}

				String inAcctId = "";
				String inCustId = maps.get("usrCustId") + ""; // 入账客户号

				long id = MySQL.executeNonQuery(conn, "insert into t_invest_repayment (isLate) values (1)");
				String ordId = id + "";// ordId取t_invest_repayment
				MySQL.executeNonQuery(conn, "delete from t_invest_repayment where id=" + id);
				Object assignmentId = maps.get("assignmentId");
				String subOrdId ;
				if (assignmentId!=null&&StringUtils.isNotBlank(assignmentId.toString())) {
					subOrdId = assignmentId + "";
				}else{
					subOrdId = maps.get("subOrdId") + "";
				}
			
				String subOrdDate = maps.get("subOrdDate") + "";
				if (subOrdDate.length() > 8) {
					subOrdDate = DateUtil.YYYYMMDD.format(DateUtil.YYYY_MM_DD.parse(subOrdDate));
				}
				String fee = "0.00"; // 代偿无手续费
				json = JSONObject.fromObject(ChinaPnRInterface.repayment2("20", ordId, outCustId, outAcctId, amount, inCustId, inAcctId, subOrdId, subOrdDate, fee, ""));
//				json = JSONObject.fromObject(ChinaPnRInterface.repayment("10", ordId, outCustId, outAcctId, amount, inCustId, inAcctId, subOrdId, subOrdDate, fee, ""));
				System.out.println(json);
				if (json.getInt("RespCode") != 0) {
					result = URLDecoder.decode(json.getString("RespDesc"), "utf-8");
				}
			}

			Procedures.p_borrow_repayment_overdue(conn, ds, outParameterValues, repayId, adminId, basePath, new Date(), new BigDecimal(investFeeRate), 0, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (ret <= 0 || !StringUtils.isBlank(result)) {
				conn.rollback();
				return result;
			} else {
				conn.commit();
				return "操作成功";
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return "操作失败";
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public AwardService getAwardService() {
		return awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
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

	public InvestDao getInvestDao() {
		return investDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	/** 合和年回购 列表 */
	@SuppressWarnings("unchecked")
	public void queryBackBuyList(String userName, int borrowWayInt, int status, PageBean pageBean) throws Exception {
		StringBuffer condition = new StringBuffer(" and status=" + status);
		if (StringUtils.isNotBlank(userName))
			condition.append(" and userName  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt)
			condition.append(" and  borrowWay  =" + borrowWayInt);

		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_back_buy_list", " * ", "", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public String backBuyByborrowId(long investId, String price, String dealPrice) throws Exception {
		Connection conn = MySQL.getConnection();
		long ordId = -1L;
		Map<String, String> mm = null;
		try {
			ordId = MySQL.executeNonQuery(conn, " insert into t_assignment_debt(details) values('hhn')");
			MySQL.executeNonQuery(conn, "delete from t_assignment_debt where id=" + ordId);
			String sql = "select u.usrCustId SellCustId,i.id BidOrdId,i.investTime BidOrdDate,round(i.investAmount-i.hasPrincipal,2) BidCreditAmt,bu.usrCustId,i.hasPrincipal "
					+ " from t_invest i left join t_user u on i.investor=u.id left join t_borrow b on b.id=i.borrowId left join t_user bu on bu.id=b.publisher where i.id="
					+ investId;
			mm = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, sql));
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		if (ordId < 0 || mm == null) {
			return "非法操作";
		}
		JSONObject BidDetail = new JSONObject();
		BidDetail.put("BidOrdId", mm.get("BidOrdId"));// 投标订单号
		BidDetail.put("BidOrdDate", mm.get("BidOrdDate").replaceAll("-", "").substring(0, 8));// 投标日期
		BidDetail.put("BidCreditAmt", mm.get("BidCreditAmt"));// 原投标订单中需要转出的本金

		// 拼接 BorrowerDetails
		JSONArray BorrowerDetails = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("BorrowerCustId", mm.get("usrCustId"));
		json.put("BorrowerCreditAmt", mm.get("BidCreditAmt"));// 从原投标订单借款人转出的已放款金额
		json.put("PrinAmt", mm.get("hasPrincipal"));// 已还本金
		BorrowerDetails.add(json);
		BidDetail.put("BorrowerDetails", BorrowerDetails.toString());// 借款人信息

		JSONArray BidDetails = new JSONArray();
		BidDetails.add(BidDetail);
		json = new JSONObject();
		json.put("BidDetails", BidDetails.toString());

		String BuyCustId = ChinaPnrConfig.chinapnr_dc;
		String OrdDate = DateUtil.dateToYMD(new Date());
		String html = ChinaPnRInterface.creditAssign(ordId + "", mm.get("SellCustId"), price, dealPrice, json.toString(), "0.00", "", BuyCustId, OrdDate, investId + "");
		return html;
	}

	/** 合和年可发起回购 列表 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> addBackBuyList(String userName, int borrowWay, PageBean pageBean) throws Exception {
		String command = "";
		if (!StringUtils.isBlank(userName)) {
			command += " and u.username like '%" + userName + "%'";
		}
		if (borrowWay > 0) {
			command += " and b.borrowWay=" + borrowWay;
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " can_backbuy ", " * ", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}

	/** 发起回购 **/
	public String addBackBuy(long borrowId) throws Exception {
		String command = "insert into t_backbuy(borrowId) values (" + borrowId + ")";
		Connection conn = MySQL.getConnection();
		try {
			long ret = MySQL.executeNonQuery(conn, command);
			if (ret > 0) {
				conn.commit();
				return "操作成功";
			}
			return "操作失败";
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return "数据库异常";
		} finally {
			conn.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryBackBuyDetails(long borrowId) throws SQLException {
		String command = "select case when day<0 then 0 else day end as day,balance,username,interest,borrowId,investId,status  from v_back_buyer_list where borrowId=" + borrowId;
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = null;
		try {
			DataSet dataSet = MySQL.executeQuery(conn, command);
			dataSet.tables.get(0).rows.genRowsMap();
			list = dataSet.tables.get(0).rows.rowsMap;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return list;
	}
}
