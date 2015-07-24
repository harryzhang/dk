package com.sp2p.service.admin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.constants.BorrowType;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.dao.NoticeTaskDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.RepamentDao;
import com.sp2p.dao.RepaymentRecordDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.AdminDao;
import com.sp2p.dao.admin.BorrowManageDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.AwardService;
import com.sp2p.service.BorrowService;
import com.sp2p.service.ChinaPnRInterfaceService;
import com.sp2p.service.SelectedService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.ChinaPnRInterface;

/**
 * @ClassName: BorrowManageService.java
 * @Author: gang.lv
 * @Date: 2013-3-10 下午10:07:28
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 后台借款管理业务处理
 */
public class BorrowManageService extends BaseService {

	public static Log log = LogFactory.getLog(BorrowManageService.class);
	
	public final static int OPERATE_TYPE_IMPORT = 1;// 导入
	public final static int OPERATE_TYPE_DISPATCH = 2;// 分发

	private BorrowManageDao borrowManageDao;

	private SelectedService selectedService;

	private AwardService awardService;

	private BorrowService borrowService;
	private ShoveBorrowTypeService shoveBorrowTypeService;

	private InvestDao investDao;

	private AccountUsersDao accountUsersDao;

	private RepamentDao repamentDao;

	private UserDao userDao;

	private FundRecordDao fundRecordDao;

	private RepaymentRecordDao repaymentRecordDao;

	private AdminDao adminDao;

	private BorrowDao borrowDao;

	private PlatformCostService platformCostService;

	private NoticeTaskDao noticeTaskDao;

	private OperationLogDao operationLogDao;
	private ChinaPnRInterfaceService chinaPnRInterfaceService;

	/**
	 * @MethodName: queryAllCirculationRepayRecordByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-23 下午11:24:18
	 * @Return:
	 * @Descb: 根据条件查询流转标还款记录
	 * @Throws:
	 */
	public void queryAllCirculationRepayRecordByCondition(String userName, int borrowStatus, String borrowTitle, PageBean pageBean)
			throws SQLException, DataException {
		String resultFeilds = " a.*";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and a.username  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(userName.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(borrowTitle)) {
			condition.append(" and a.borrowTitle  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(borrowTitle.trim()) + "','%')");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowStatus) {
			condition.append(" and borrowStatus =" + borrowStatus);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_circulation_repayrecord a", resultFeilds, " order by a.id asc", condition.toString());
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
	 * @MethodName: updateBorrowCirculationStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午03:21:21
	 * @Return:
	 * @Descb: 更新流转标状态
	 * @Throws:
	 */
	public long updateBorrowCirculationStatus(long borrowId, long reciverId, long statusLong, String auditOpinion, Long adminId, String basePath,
			Map<String, Object> platformCostMap) throws SQLException, DataException {
		String content = "";
		Connection conn = Database.getConnection();
		int circulationStatus = -1;
		int sort = 1;
		double borrowSum = 0;
		double yearRate = 0;
		int deadline = 0;
		int isDayThe = 1;
		String borrowTitle = "";
		Long result = -1L;
		try {
			Map<String, String> borrowMap = borrowManageDao.queryBorrowInfo(conn, borrowId);
			if (borrowMap == null)
				borrowMap = new HashMap<String, String>();
			borrowTitle = borrowMap.get("borrowTitle") + "";
			borrowSum = Convert.strToDouble(borrowMap.get("borrowAmount") + "", 0);
			yearRate = Convert.strToDouble(borrowMap.get("annualRate") + "", 0);
			deadline = Convert.strToInt(borrowMap.get("deadline") + "", 0);
			isDayThe = Convert.strToInt(borrowMap.get("isDayThe") + "", 1);
			// 处理流转标
			result = borrowManageDao.updateBorrowCirculationStatus(conn, borrowId, statusLong, auditOpinion, circulationStatus, 10);
			if (result > 0) {
				// 审核通过添加还款记录
				if (statusLong == 2) {
					AmountUtil au = new AmountUtil();
					List<Map<String, Object>> rateCalculateOneList = au.rateCalculateOne(borrowSum, yearRate, deadline, isDayThe);
					for (Map<String, Object> oneMap : rateCalculateOneList) {
						String repayPeriod = oneMap.get("repayPeriod") + "";
						String repayDate = oneMap.get("repayDate") + "";
						// 添加还款记录,还款金额和利息在投资时进行累加
						result = borrowManageDao.addRepayRecord(conn, repayPeriod, 0, 0, borrowId, 0, 0, repayDate);
					}
				}
			}
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
	 * @MethodName: queryBorrowCirculationDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午02:52:55
	 * @Return:
	 * @Descb: 查询流转标详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowCirculationDetailById(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowCirculationDetailById(conn, id);
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
	 * @MethodName: queryCirculationRepayRecord
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午07:37:39
	 * @Return:
	 * @Descb: 查询流转标还款记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryCirculationRepayRecord(long borrowId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = null;
		try {
			list = borrowManageDao.queryCirculationRepayRecord(conn, borrowId);
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
	 * @MethodName: queryAllCirculationByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-20 上午11:37:27
	 * @Return:
	 * @Descb: 查询流转标借款
	 * @Throws:
	 */
	public void queryAllCirculationByCondition(String userName, int borrowWay, int borrowStatus, String undertaker, PageBean pageBean)
			throws SQLException, DataException {
		String resultFeilds = " a.*,b.userid,b.counts,c.userName as undertakerName ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and a.username  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(userName.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(undertaker)) {
			condition.append(" and c.userName  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(undertaker.trim()) + "','%')");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowStatus) {
			condition.append(" and a.borrowStatus =" + borrowStatus);
		}
		condition.append(" and a.borrowShow=2");
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_circulation a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid"
							+ " left join t_admin c on a.undertaker=c.id", resultFeilds, " order by a.borrowStatus asc,a.id desc",
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
	 * @MethodName: queryBorrowFistAuditByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的初审借款记录
	 * @Throws:
	 */
	public void queryBorrowFistAuditByCondition(String userName, int borrowWay, PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}

		String filed = " a.*,b.counts";

		String table = "(select  "
				+ filed
				+ " from v_t_borrow_h_firstaudit a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userId=b.userid "
				+ " INNER JOIN v_t_user_approve_lists c on a.userId = c.uid where a.borrowWay >2 " + "UNION ALL SELECT  " + filed
				+ " from v_t_borrow_h_firstaudit a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth "
				+ "where auditStatus = 3  GROUP BY userid) b ON a.userId=b.userid   "
				+ "INNER JOIN v_t_user_base_approve_lists d  on a.userId=d.uuid where a.borrowWay <3 and d.auditStatus=3) t ";
		// ----
		Connection conn = connectionManager.getConnection();
		try {
			// 秒还净值个人资料通过审核即可 其它借款需要个人资料+工作认证+5项基本认证
			dataPage(conn, pageBean, table,
			// ---
					resultFeilds, " order by id desc ", condition.toString());
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
	 * add by houli 查询等待资料审核的数据
	 * 
	 * @param userName
	 * @param borrowWay
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryBorrowWaitingAuditByCondition(String userName, int borrowWay, PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		String filed = "a.id,a.userId,a.username,a.realName,b.counts as counts,a.borrowWay,a.borrowTitle,"
				+ "a.borrowAmount,a.annualRate,a.deadline,a.raiseTerm,a.isDayThe ,a.borrowShow";
		String table = " (select  "
				+ filed
				+ " from v_t_borrow_h_firstaudit a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userId=b.userid "

				+ " inner join v_t_user_un_approve_lists c on a.userid = c.uid where a.borrowway >2 " + "union all select  " + filed
				+ " from v_t_borrow_h_firstaudit a left join (select userid,count(1) as counts from t_materialsauth "
				+ "where auditstatus = 3  group by userid) b on a.userid=b.userid   "
				+ "inner join v_t_user_base_approve_lists d  on a.userid=d.uuid where a.borrowway <3 and d.auditstatus!=3) t";

		Connection conn = connectionManager.getConnection();
		try {
			// 秒还净值个人资料通过审核即可 其它借款需要个人资料+工作认证+5项基本认证
			dataPage(conn, pageBean, table, resultFeilds, "order by  id desc ", condition.toString());
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
	 * @MethodName: queryBorrowFistAuditDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款初审中的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFistAuditDetailById(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFistAuditDetailById(conn, id);
			map.put("mailContent", "");
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
	 * @MethodName: queryBorrowTenderInByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的招标中借款记录
	 * @Throws:
	 */
	public void queryBorrowTenderInByCondition(String userName, int borrowWay, PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h_tenderin a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid ",
					resultFeilds, " order by id desc", condition.toString());
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
	 * @MethodName: queryBorrowTenderInDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款招标中的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowTenderInDetailById(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapNotick = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTenderInDetailById(conn, id);
			if (map != null) {
				long userId = Convert.strToLong(map.get("userId"), -1L);
				mapNotick = noticeTaskDao.queryNoticeTask(conn, userId, id);
				if (mapNotick == null) {
					Map<String, String> maps = noticeTaskDao.queryNoticeTasklog(conn, userId, id);
					map.put("mailContent", Convert.strToStr(maps.get("mail_info") + "", ""));
				} else {
					map.put("mailContent", Convert.strToStr(mapNotick.get("mail_info") + "", ""));
				}
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
	 * @MethodName: queryBorrowFullScaleByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的满标借款记录
	 * @Throws:
	 */
	public void queryBorrowFullScaleByCondition(String userName, int borrowWay, PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h_fullscale ",
					resultFeilds, " ", condition.toString());
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
	 * @MethodName: queryBorrowFullScaleDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款满标的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFullScaleDetailById(long id) throws Exception {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapNotick = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFullScaleDetailById(conn, id);
			/*
			 * if (map != null) { long userId =
			 * Convert.strToLong(map.get("userId"), -1L); mapNotick =
			 * noticeTaskDao.queryNoticeTask(conn, userId, id); if (mapNotick ==
			 * null) { Map<String, String> maps =
			 * noticeTaskDao.queryNoticeTasklog(conn, userId, id);
			 * map.put("mailContent", Convert.strToStr(maps.get("mail_info") +
			 * "", "")); } else { map.put("mailContent",
			 * Convert.strToStr(mapNotick.get("mail_info") + "", "")); }
			 * 
			 * }
			 */
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryBorrowFlowMarkByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的流标借款记录
	 * @Throws:
	 */
	public void queryBorrowFlowMarkByCondition(String userName, int borrowWay, PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h_flowmark a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid ",
					resultFeilds, " order by id desc", condition.toString());
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
	 * add by houli 获得所有等待资料审核的借款
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAllWaitingBorrow() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			return borrowManageDao.queryAllWaitingBorrow(conn);
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
	 * @MethodName: queryBorrowFlowMarkDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款流标的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFlowMarkDetailById(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFlowMarkDetailById(conn, id);
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
	 * @MethodName: queryBorrowAllByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的借款记录
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void queryBorrowAllByCondition(String userName, int borrowWay, int borrowStatus, PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		if (IConstants.DEFAULT_NUMERIC != borrowStatus) {
			condition.append(" and borrowStatus =" + borrowStatus);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid ",
					resultFeilds, " order by id desc", condition.toString());
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
	 * @MethodName: queryBorrowAllDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowAllDetailById(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowAllDetailById(conn, id);
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
	 * @MethodName: updateBorrowFistAuditStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:19:48
	 * @Return:
	 * @Descb: 更新初审中的借款状态
	 * @Throws:
	 */
	public Long updateBorrowFistAuditStatus(long id, long reciver, int status, String msg, String auditOpinion, long sender, String basePath)
			throws SQLException, DataException {
		// String content = "";
		Long result = -1L;
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> map_ret = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		Connection conn = Database.getConnection();
		// 得到管理员信息
		adminMap = adminDao.queryAdminById(conn, sender);
		if (status == IConstants.BORROW_STATUS_2) {

			try {
				Procedures.p_borrow_audit(conn, ds, outParameterValues, id, sender, status, msg, auditOpinion, basePath, new Date(), 0, "");
				map_ret.put("out_ret", outParameterValues.get(0) + "");
				map_ret.put("out_desc", outParameterValues.get(1) + "");
				result = Convert.strToLong(outParameterValues.get(0) + "", -1);
				if (result < 1) {
					conn.rollback();
					return -1L;
				}
				// 添加操作日志
				operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(adminMap.get("userName"), ""), IConstants.UPDATE,
						Convert.strToStr(adminMap.get("lastIP"), ""), 0, "初审通过", 2);
				conn.commit();
			} catch (SQLException e) {
				log.error(e);
				conn.rollback();
				e.printStackTrace();
				throw e;
			} finally {
				conn.close();
			}
		} else {
			// 撤消初审中的借款
			result = reBackBorrowFistAudit(id, sender, basePath, msg, auditOpinion);
			Procedures.p_borrow_cancel(conn, ds, outParameterValues, id, sender, status, auditOpinion, basePath, 0, "");
			map_ret.put("out_ret", outParameterValues.get(0) + "");
			map_ret.put("out_desc", outParameterValues.get(1) + "");
			// 添加操作日志
			operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(adminMap.get("userName"), ""), IConstants.UPDATE,
					Convert.strToStr(adminMap.get("lastIP"), ""), 0, "管理员撤销借款", 2);
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
			conn.close();
		}

		return result;
	}

	/**
	 * @throws DataException
	 * @MethodName: updateBorrowTenderInStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:20:27
	 * @Return:
	 * @Descb: 更新招标中的借款状态
	 * @Throws:
	 */
	public Long updateBorrowTenderInStatus(long id, String auditOpinion) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = borrowManageDao.updateBorrowTenderInStatus(conn, id, auditOpinion);
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
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: reBackBorrowTenderIn
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午08:40:22
	 * @Return:
	 * @Descb: 撤消借款
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public long reBackBorrow(long id, long aId, String basePath) throws Exception {
		Connection conn = Database.getConnection();
		// 所有投资人信息
		String cmd = "SELECT i.id,i.trxId,i.investor,i.investAmount,b.publisher,b.borrowTitle,b.id borrrowId,u.usableSum,u.freezeSum FROM t_invest i "
				+ "LEFT JOIN t_borrow b on i.borrowId=b.id left join t_user u on u.id=i.investor  where b.id=" + id;
		DataSet ds = MySQL.executeQuery(conn, cmd);
		ds.tables.get(0).rows.genRowsMap();
		List<Map<String, Object>> lists = ds.tables.get(0).rows.rowsMap;
		if (lists != null) {
			for (Map<String, Object> map : lists) {
				// 汇付解冻
				String jsonStr = ChinaPnRInterface.usrUnFreeze(map.get("id") + "", map.get("trxId") + "");
				JSONObject json = JSONObject.fromObject(jsonStr);
				if (json.getInt("RespCode") != 0) {
					log.error(json.getString("RespDesc"));
				}
				// Object money = map.get("investAmount");
				// fundRecordDao.addFundRecord(conn, map.get("id"), "流标解冻",
				// money, map.get("usableSum"), map.get("freezeSum"), 0,
				// map.get("publisher"),
				// "您投资的借款[" + map.get("borrowTitle") + "]已流标,返还冻结金额[" + money +
				// "]", money, 0, map.get("id"), -1, -1, 0);
			}
		}

		long returnId = -1;
		ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> map_ret = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		try {
			Procedures.p_borrow_cancel(conn, ds, outParameterValues, id, aId, 6, "", basePath, 0, "");
			map_ret.put("out_ret", outParameterValues.get(0) + "");
			map_ret.put("out_desc", outParameterValues.get(1) + "");
			returnId = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (returnId < 1) {
				conn.rollback();
				return -1L;
			}
			// 添加操作日志
			adminMap = adminDao.queryAdminById(conn, aId);
			operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(adminMap.get("userName"), ""), IConstants.UPDATE,
					Convert.strToStr(adminMap.get("lastIP"), ""), 0, "管理员撤销借款", 2);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			return -1L;
		} finally {
			conn.close();
		}
		return returnId;
	}

	/**
	 * @MethodName: updateBorrowFullScaleStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:21:09
	 * @Return:
	 * @Descb: 更新满标的借款状态
	 * @Throws:
	 */
	public Map<String, String> updateBorrowFullScaleStatus(long id, long status, String auditOpinion, long adminId, String basePath, String isDefault)
			throws Exception {
		Connection conn = MySQL.getConnection();
		if (auditOpinion == null)
			auditOpinion = "";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String identify = id + "_" + System.currentTimeMillis() + "";
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		List<Object> outParameters = new ArrayList<Object>();
		List<Map<String, Object>> investList = new ArrayList<Map<String, Object>>();
		investList = borrowManageDao.queryInvestByBorrowId(conn, id);

		JSONObject jobj = null;
		String jsonStr = null;
		try {
			// 满标审核前判断处理
			Procedures.p_borrow_auth_fullscale(conn, ds, outParameterValues, id, status, -1, "", new BigDecimal(0.00), new BigDecimal(0.00), 0, 0, 0);
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (ret <= 0) {
				map.put("ret", ret + "");
				map.put("ret_desc", outParameterValues.get(1) + "");
				conn.rollback();
				return map;
			}

			// 放款中断之后继续放款的处理
			Map<String, String> tmap = BeanMapUtils.dataSetToMap(MySQL.executeQuery(conn, "select loansOk,unfreeOk from t_borrow where id=" + id));
			int loansOk = Convert.strToInt(tmap.get("loansOk"), 0);// 汇付成功放款的个数(未放款过的时候为0)
			int unfreeOk = Convert.strToInt(tmap.get("unfreeOk"), 0);// 汇付成功解冻的个数(未放款过的时候为0)
			int loansOk2 = loansOk;
			int unfreeOk2 = unfreeOk;

			for (int i = 0, size = investList.size(); i < size; i++) {
				Map<String, Object> investMap = investList.get(i);
				// 汇付解冻
				if (i >= unfreeOk) {// 从上一次的位置继续解冻
					jsonStr = ChinaPnRInterface.usrUnFreeze(investMap.get("trxId") + "", investMap.get("trxId") + "");
					jobj = JSONObject.fromObject(jsonStr);
					if (jobj.getInt("RespCode") != 0) {
						map.put("ret", "-10086");
						map.put("ret_desc", jobj.getString("RespDesc"));
						if ("重复交易".equals(jobj.getString("RespDesc")))
							unfreeOk2++;
						borrowDao.updateLoansOk(conn, id, loansOk2, unfreeOk2);
						conn.commit();
						return map;
					}
				}

				unfreeOk2++;
				// 汇付放款
				if (i >= loansOk) {// 从上一次的位置继续放款
					jsonStr = ChinaPnRInterface.loans(investMap.get("OrdId") + "", investMap.get("OrdDate") + "", investMap.get("OutCustId") + "",
							investMap.get("TransAmt") + "", investMap.get("Fee") + "", investMap.get("OrdId") + "", investMap.get("OrdDate") + "",
							investMap.get("InCustId") + "", "", isDefault);
					jobj = JSONObject.fromObject(jsonStr);
					if (jobj.getInt("RespCode") != 0) {
						map.put("ret", "-10086");
						map.put("ret_desc", jobj.getString("RespDesc"));
						if ("重复的放款请求".equals(jobj.getString("RespDesc")))
							loansOk2++;
						borrowDao.updateLoansOk(conn, id, loansOk2, unfreeOk2);
						conn.commit();
						return map;
					}
				}
				loansOk2++;
			}
			// 审核通过并且放款成功之后才生成还款记录
			if (ret == 4) {
				double borrowAmount = Convert.strToDouble(outParameterValues.get(2) + "", 0);
				double annualRate = Convert.strToDouble(outParameterValues.get(3) + "", 0);
				int deadline = Convert.strToInt(outParameterValues.get(4) + "", 0);
				int isDayThe = Convert.strToInt(outParameterValues.get(5) + "", 1);

				// 生成还款记录
				List<Map<String, Object>> repayMapList = null;
				AmountUtil au = new AmountUtil();
				// 等额本金还款---合和年只有此种还款方式
				repayMapList = (List<Map<String, Object>>) au.earnCalculateMonthHhn(borrowAmount, deadline, isDayThe, annualRate);
				String repayPeriod = ""; // 还款期数
				double stillPrincipal = 0; // 应还本金
				double stillInterest = 0; // 应还利息
				double principalBalance = 0; // 剩余本金
				double interestBalance = 0; // 剩余利息
				double totalSum = 0; // 本息余额
				double totalAmount = 0; // 还款总额
				double mRate = 0; // 月利率
				double consultFee = 0;// 咨询费
				String repayDate = "";
				int count = 1;
				for (Map<String, Object> paymentMap : repayMapList) {
					repayPeriod = paymentMap.get("deadline") + "";
					stillPrincipal = Convert.strToDouble(paymentMap.get("monPayAmount") + "", 0);
					stillInterest = Convert.strToDouble(paymentMap.get("monPayRate") + "", 0);
					principalBalance = Convert.strToDouble(paymentMap.get("principalBalance") + "", 0);
					interestBalance = Convert.strToDouble(0.00 + "", 0);
					totalSum = Convert.strToDouble(0.00 + "", 0);
					totalAmount = Convert.strToDouble(paymentMap.get("totalAmount") + "", 0);
					repayDate = Convert.strToStr(paymentMap.get("repayDate") + "", "");
					mRate = Convert.strToDouble(paymentMap.get("monthRate") + "", 0);
					consultFee = Convert.strToDouble(paymentMap.get("iManageFee") + "", 0);
					// 添加预还款记录
					ret = repamentDao.addPreRepament(conn, id, identify, repayPeriod, stillPrincipal, stillInterest, principalBalance,
							interestBalance, totalSum, totalAmount, mRate, repayDate, count, consultFee);
					count++;
					if (ret <= 0) {
						break;
					}
				}
				if (ret <= 0) {
					map.put("ret", ret + "");
					map.put("ret_desc", "执行失败");
					conn.rollback();
					return map;
				}
				// 查询借款信息得到借款时插入的平台收费标准,在存储过程中已处理
				// Map<String, String> mc = borrowDao.queryBorrowCost(conn,id);
			}

			// 满标审核处理
			Procedures.p_borrow_deal_fullscale(conn, ds, outParameters, id, adminId, status, sf.format(new Date()), auditOpinion, identify, basePath,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, "");

			ret = Convert.strToLong(outParameters.get(0) + "", -1);

			if (ret > 0 && status == 4) {
				// 添加系统操作日志
				adminMap = adminDao.queryAdminById(conn, adminId);
				operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(adminMap.get("userName"), ""), IConstants.UPDATE,
						Convert.strToStr(adminMap.get("lastIP"), ""), 0, "满标复审通过", 2);

				/*
				 * // 提成奖励 String sql =
				 * " select DISTINCT a.id as id,a.investor as userId,a.realAmount as realAmount,c.publisher as publisher from t_invest a left join t_repayment b on a.borrowId = b.borrowId  left join t_borrow c on a.borrowId = c.id "
				 * ; DataSet ds1 = MySQL.executeQuery(conn, sql +
				 * "  where c.id =" + id); ds1.tables.get(0).rows.genRowsMap();
				 * List<Map<String, Object>> list =
				 * ds1.tables.get(0).rows.rowsMap; for (Map<String, Object> map2
				 * : list) { long uId = Convert.strToLong(map2.get("userId") +
				 * "", -1); long investId = Convert.strToLong(map2.get("id") +
				 * "", -1); Object obj = map2.get("realAmount"); BigDecimal
				 * amounts = BigDecimal.ZERO; if (obj != null) amounts = new
				 * BigDecimal(obj + ""); ret = awardService.updateMoneyNew(conn,
				 * uId, amounts, IConstants.MONEY_TYPE_1, investId); }
				 */
				conn.commit();
			} else {
				conn.rollback();
			}
			map.put("ret", ret + "");
			map.put("ret_desc", outParameters.get(1) + "");
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
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

	public AwardService getAwardService() {
		return awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public long reBackBorrowFistAudit(long idLong, Long id, String basePath, String msg, String auditOpinion) throws SQLException {
		Connection conn = Database.getConnection();
		long returnId = -1;
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> map_ret = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		try {
			Procedures.p_borrow_cancel(conn, ds, outParameterValues, idLong, id, 6, auditOpinion, basePath, 0, "");
			map_ret.put("out_ret", outParameterValues.get(0) + "");
			map_ret.put("out_desc", outParameterValues.get(1) + "");
			returnId = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (returnId <= 0) {
				conn.rollback();
				return Convert.strToLong(map_ret.get("out_ret"), -1);
			}
			// 添加操作日志
			adminMap = adminDao.queryAdminById(conn, id);
			operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(adminMap.get("userName"), ""), IConstants.UPDATE,
					Convert.strToStr(adminMap.get("lastIP"), ""), 0, "管理员撤销借款", 2);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return Convert.strToLong(map_ret.get("out_ret"), 1);
	}

	/**
	 * @MethodName: addCirculationRepay
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午04:32:03
	 * @Return:
	 * @Descb: 添加流转标还款记录
	 * @Throws:
	 */
	public long addCirculationRepay(long repayId, double amountDouble, Long id, String remark) throws SQLException {
		Connection conn = MySQL.getConnection();
		long returnId = -1;
		try {
			returnId = repaymentRecordDao.addRepayMentRecord(conn, repayId, amountDouble, id, remark);
			if (returnId <= 0) {
				conn.rollback();
			}
		} finally {
			conn.close();
		}
		return returnId;
	}

	public Map<String, String> queryBorrowInfo(long idLong) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowInfo(conn, idLong);
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
	 * 查看借款协议中的内容
	 * 
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryBorrowMany(long borrowId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = investDao.queryBorrowMany(conn, borrowId);
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
	 * 根据借款id 和投资id 查询
	 * 
	 * @param borrowId
	 * @param invest_id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryInvestMomey(long borrowId, long invest_id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = investDao.queryInvestMomey(conn, invest_id, borrowId);
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
	 * 查询所有投资人信息
	 * 
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryUsername(long borrowId, long invest_id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = investDao.queryUsername(conn, borrowId, invest_id);
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
	 * 根据借款查询借款应还的金额
	 * 
	 * @param conn
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryBorrowSumMomeny(long borrowId, long invest_id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = investDao.queryBorrowSumMomeny(conn, borrowId, invest_id);
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

	// 借款管理模块中，查询各个报表的总额
	public Map<String, String> queryBorrowTotalFistAuditDetail() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalFistAuditDetail(conn);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	public Map<String, String> queryBorrowTotalWait() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalWait(conn);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	public Map<String, String> queryBorrowTotalTenderDetail() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalTenderDetail(conn);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	public Map<String, String> queryBorrowFlowMarkDetail() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFlowMarkDetail(conn);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	public Map<String, String> queryBorrowTotalFullScaleDetail() throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalFullScaleDetail(conn);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	public Map<String, String> queryBorrowFullList(long borrowId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = null;
		try {
			map = borrowManageDao.queryBorrowFullList(conn, borrowId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询初审列表，分页
	 * 
	 * @param pageBean
	 * @param userName
	 * @param borrowWay
	 * @param borrowStatus
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void queryFirstTrialList(PageBean<Map<String, Object>> pageBean, String userName, String borrowWay, String borrowStatus)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay = '" + StringEscapeUtils.escapeSql(borrowWay.trim()) + "' ");
		}
		if (StringUtils.isNotBlank(borrowStatus)) {
			condition.append(" and borrowStatus = '" + StringEscapeUtils.escapeSql(borrowStatus.trim()) + "' ");
		}

		try {
			dataPage(conn, pageBean, "v_t_borrow_first_trial", "*", " order by id desc ", condition.toString());
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
	 * 根据ID查询借款信息--初审
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryFirsttrialById(String id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryFirsttrialById(conn, id);
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
	 * 初审--发送站内信
	 * 
	 * @param id
	 *            id 借款ID
	 * @param borrowStatus
	 *            借款状态(1 默认等待资料 2 正在招标中 3 已满标 4还款中 5 已还完 6 流标 7 未审核 8 复审中 9 待发布
	 *            10 重新初审 -1 审核失败)
	 * @param windControl
	 *            风控意见
	 * @param firstTrial
	 *            初审人
	 * @param reciver
	 *            收件人
	 * @param mailTitle
	 *            站内信标题
	 * @param sender
	 *            发件人
	 * @param mailContent
	 *            站内信内容
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long updateFirsttrial(String id, String borrowStatus, String windControl, String firstTrial, String reciver, String mailTitle,
			long sender, String mailContent) throws SQLException {
		Connection conn = MySQL.getConnection();

		String[] ids = id.split(",");
		String[] mailTitles = mailTitle.split(",");
		String[] recivers = reciver.split(",");
		String[] mailContents = mailContent.split(",");
		long result = -1L;
		try {
			for (int i = 0; i < ids.length; i++) {
				result = borrowManageDao.updateFirsttrial(conn, ids[i], borrowStatus, windControl, firstTrial);
				if (result != 0) {
					borrowManageDao.insertEmail(conn, ids[i], mailTitles[i], mailContents[i], sender, recivers[i]);
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

	/**
	 * 查询复审列表，分页
	 * 
	 * @param pageBean
	 * @param userName
	 * @param borrowWay
	 * @param borrowStatus
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void queryRecheckList(PageBean<Map<String, Object>> pageBean, String userName, String borrowWay, String id) throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay = '" + StringEscapeUtils.escapeSql(borrowWay.trim()) + "' ");
		}
		if (StringUtils.isNotBlank(id)) {
			condition.append(" and id like '%" + StringEscapeUtils.escapeSql(id.trim()) + "%' ");
		}

		try {
			dataPage(conn, pageBean, "v_t_borrow_recheck", "*", " order by id desc ", condition.toString());
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
	 * 根据ID查询借款信息--初审
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 *             [参数说明]
	 * 
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryRecheckById(String id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryRecheckById(conn, id);
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
	 * 复审
	 * 
	 * @param id
	 *            借款ID
	 * @param borrowStatus
	 *            借款状态(1 默认等待资料 2 正在招标中 3 已满标 4还款中 5 已还完 6 流标 7 未审核 8 复审中 9 待发布
	 *            10 重新初审 -1 审核失败)
	 * @param windControl
	 *            风控意见
	 * @param firstTrial
	 *            初审人
	 * @param recheck
	 *            复审人
	 * @param raiseTerm
	 */
	public long updateRecheck(String id, String borrowStatus, String windControl, String recheck, String reciver, String mailTitle, long sender,
			String mailContent, String minTenderedSum, String maxTenderedSum, String userId, String borrowAmount, String annualRate, String deadline,
			String raiseTerm,String borrowGroupStr) throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;

		String[] ids = id.split(",");
		String[] mailTitles = mailTitle.split(",");
		String[] recivers = reciver.split(",");
		String[] mailContents = mailContent.split(",");
		String[] minTenderedSums = minTenderedSum.split(",");
		String[] annualRates = annualRate.split(",");
		String[] borrowAmounts = borrowAmount.split(",");
		String[] raiseTerms = raiseTerm.split(",");
		String[] deadlines = deadline.split(",");
		String[] borrowGroups = borrowGroupStr.split(",");
		try {
			// 最高金额限制
			String[] maxTenderedSums = null;
			if (null == maxTenderedSum || maxTenderedSum.equals("")) {
				maxTenderedSums = new String[ids.length];
				Arrays.fill(maxTenderedSums, "-1");
			}
			maxTenderedSums = maxTenderedSum.split(",");
			for (int i = 0; i < ids.length; i++) {
				double amount = Convert.strToDouble(borrowAmounts[i], 0);
				double anna = Convert.strToDouble(annualRates[i], 0);
				int line = Convert.strToInt(deadlines[i], -1);
				int raise = Convert.strToInt(raiseTerms[i], -1);
				int borrowGroup = Convert.strToInt(borrowGroups[i], 0);
				if (anna <= 0) {
					anna = 10.0;
				}
				if (line <= 0) {
					line = 12;
				}
				if (raise <= 0) {
					raise = 15;
				}
				result = borrowManageDao.updateRecheck(conn, ids[i], borrowStatus, windControl, recheck, minTenderedSums[i], maxTenderedSums[i],
						amount, anna, line, raise);
				if (result > 0) {
					String sqlString="update t_borrow set borrowGroup="+borrowGroup+" where id="+ids[i];
					MySQL.executeNonQuery(conn, sqlString);
					borrowManageDao.insertEmail(conn, ids[i], mailTitles[i], mailContents[i], sender, recivers[i]);
				}
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
	 * 重新初审
	 * 
	 * @param raiseTerm
	 */
	public long updateFirsttrialAgain(String id, String borrowStatus, String borrowAmount, String annualRate, String deadline, String raiseTerm)
			throws SQLException {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			if (Convert.strToDouble(annualRate, -1) <= 0) {
				annualRate = "10.0";
			}
			if (Convert.strToDouble(deadline, -1) <= 0) {
				deadline = "12";
			}
			if (Convert.strToDouble(raiseTerm, -1) <= 0) {
				raiseTerm = "15";
			}
			result = borrowManageDao.updateFirsttrialAgain(conn, id, borrowStatus, borrowAmount, annualRate, deadline, raiseTerm);
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
	 * 查询待发布列表，分页
	 */
	public void queryReleasedList(PageBean<Map<String, Object>> pageBean, String userName, String source, String borrowAmount) throws SQLException {
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(source)) {
			condition.append(" and source = '" + StringEscapeUtils.escapeSql(source.trim()) + "' ");
		}

		try {
			dataPage(conn, pageBean, "v_t_borrow_released", "*", " order by id desc ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询已定时发布列表
	 */
	public void queryRedayReleasedList(PageBean<Map<String, Object>> pageBean, String userName, String source, String borrowAmount)
			throws SQLException {
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(source)) {
			condition.append(" and source = '" + StringEscapeUtils.escapeSql(source.trim()) + "' ");
		}

		try {
			dataPage(conn, pageBean, "v_t_reday_released", "*", " order by id desc ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 发布借款信息
	 */
	public long updateReleased(String id) throws SQLException {
		Connection conn = MySQL.getConnection();

		// String[] ids = id.split(",");
		long result = -1L;
		try {
			String cmd = "update t_borrow set remainTimeStart=NOW(),borrowStatus=2,remainTimeEnd=DATE_ADD(NOW(),INTERVAL raiseTerm day) "
					+ " where id in (" + id + ")";
			result = MySQL.executeNonQuery(conn, cmd);
			// for (int i = 0; i < ids.length; i++) {
			// result = borrowManageDao.updateReleased(conn, ids[i]);
			// }
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
	 * 查询撮合借款列表，分页
	 */
	public void queryBringList(PageBean<Map<String, Object>> pageBean, String userName, String source) throws SQLException {
		Connection conn = connectionManager.getConnection();
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%" + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(source)) {
			condition.append(" and source = '" + StringEscapeUtils.escapeSql(source.trim()) + "' ");
		}

		try {
			dataPage(conn, pageBean, "v_t_borrow_bring_new", "*", " order by id desc ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 撮合失败
	 */
	public long updateBring(String id) throws SQLException {
		Connection conn = MySQL.getConnection();
		String[] ids = id.split(",");
		long result = -1L;
		try {
			for (int i = 0; i < ids.length; i++) {
				result = borrowManageDao.updateBring(conn, ids[i]);
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 定时
	 * 
	 * @param id
	 * @param times
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return long [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public long updateTimer(String id, String times) throws SQLException {
		Connection conn = MySQL.getConnection();

		String[] ids = id.split(",");
		long result = -1L;
		try {
			for (int i = 0; i < ids.length; i++) {
				result = borrowManageDao.updateTimer(conn, ids[i], times);
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
	 * 定时更新借款状态--定时发布
	 */
	public void updateTimerSend() throws SQLException {
		Connection conn = MySQL.getConnection();

		try {
			StringBuilder sb = new StringBuilder();

			sb.append(" update  t_borrow set remainTimeStart=NOW(),borrowStatus=2,remainTimeEnd=DATE_ADD(NOW(),INTERVAL raiseTerm day),isTimes=-1 ");
			sb.append(" where borrowStatus = 8 and isTimes = 0  and TIME_TO_SEC(TIMEDIFF(publishTime,NOW() ))<6  ");

			MySQL.executeNonQuery(conn, sb.toString());
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	public InvestDao getInvestDao() {
		return investDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}

	public BorrowManageDao getBorrowManageDao() {
		return borrowManageDao;
	}

	public void setBorrowManageDao(BorrowManageDao borrowManageDao) {
		this.borrowManageDao = borrowManageDao;
	}

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public RepamentDao getRepamentDao() {
		return repamentDao;
	}

	public ShoveBorrowTypeService getShoveBorrowTypeService() {
		return shoveBorrowTypeService;
	}

	public void setShoveBorrowTypeService(ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public ChinaPnRInterfaceService getChinaPnRInterfaceService() {
		return chinaPnRInterfaceService;
	}

	public void setChinaPnRInterfaceService(ChinaPnRInterfaceService chinaPnRInterfaceService) {
		this.chinaPnRInterfaceService = chinaPnRInterfaceService;
	}

	public void setRepamentDao(RepamentDao repamentDao) {
		this.repamentDao = repamentDao;
	}

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public void setRepaymentRecordDao(RepaymentRecordDao repaymentRecordDao) {
		this.repaymentRecordDao = repaymentRecordDao;
	}

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	public PlatformCostService getPlatformCostService() {
		return platformCostService;
	}

	public void setPlatformCostService(PlatformCostService platformCostService) {
		this.platformCostService = platformCostService;
	}

	public AccountUsersDao getAccountUsersDao() {
		return accountUsersDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public RepaymentRecordDao getRepaymentRecordDao() {
		return repaymentRecordDao;
	}

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public NoticeTaskDao getNoticeTaskDao() {
		return noticeTaskDao;
	}

	public void setNoticeTaskDao(NoticeTaskDao noticeTaskDao) {
		this.noticeTaskDao = noticeTaskDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	private Map<String, String> getBorrowTypeMap(String type) throws SQLException, DataException {
		String nid = "";
		if (IConstants.BORROW_TYPE_NET_VALUE.equals(type)) {
			// 薪金贷
			nid = BorrowType.WORTH.getValue();
		} else if (IConstants.BORROW_TYPE_SECONDS.equals(type)) {
			// 彩生活
			nid = BorrowType.SECONDS.getValue();
		} else if (IConstants.BORROW_TYPE_GENERAL.equals(type)) {
			// 业主贷
			nid = BorrowType.ORDINARY.getValue();
		}
		return shoveBorrowTypeService.queryShoveBorrowTypeByNid(nid);
	}

	// ///////////借款失败
	// 查询失败的借款
	public void queryAllFailedBorrowList(PageBean<Map<String, Object>> pageBean, String userName, int borrowWay) throws Exception {
		Connection conn = MySQL.getConnection();
		String command = "";
		if (StringUtils.isNotBlank(userName)) {
			command += " and userName like '%" + StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (borrowWay > 0) {
			command += " and borrowWay =" + borrowWay;
		}
		command += " and borrowStatus =" + 6;
		try {
			dataPage(conn, pageBean, "v_t_user_failedborrow_lists", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// 删除选中失败的借款
	public Long deleteFailedBorrow(String ids) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			long result = borrowManageDao.deleteFailedBorrow(conn, ids);
			conn.commit();
			return result;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}

	// 查询选中的失败借款
	public void queryselectedFailedBorrowList(PageBean<Map<String, Object>> pageBean, String ids) throws Exception {
		Connection conn = MySQL.getConnection();
		String command = "";
		command += " and id in ( ";
		command += ids;
		command += " )";
		try {
			dataPage(conn, pageBean, "v_t_user_failedborrow_lists", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// 查询选中的初审借款
	public void queryselectedFirstTrialBorrowList(PageBean<Map<String, Object>> pageBean, String ids) throws Exception {
		Connection conn = MySQL.getConnection();
		String command = "";
		command += " and id in ( ";
		command += ids;
		command += " )";
		try {
			dataPage(conn, pageBean, "v_t_user_firsttrialborrow_lists", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// 查询选中的复审借款
	public void queryselectedRecheckTrialBorrowList(PageBean<Map<String, Object>> pageBean, String ids) throws Exception {
		Connection conn = MySQL.getConnection();
		String command = "";
		command += " and id in ( ";
		command += ids;
		command += " )";
		try {
			dataPage(conn, pageBean, "v_t_user_rechecktrialborrow_lists", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// 查询选中的待发布借款
	public void queryselectedReleaseBorrowList(PageBean<Map<String, Object>> pageBean, String ids) throws Exception {
		Connection conn = MySQL.getConnection();
		String command = "";
		command += " and id in ( ";
		command += ids;
		command += " )";
		try {
			dataPage(conn, pageBean, "v_t_user_releaseborrow_lists", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// 查询选中的撮合借款
	public void queryselectedBringBorrowList(PageBean<Map<String, Object>> pageBean, String ids) throws Exception {
		Connection conn = MySQL.getConnection();
		String command = "";
		command += " and id in ( ";
		command += ids;
		command += " )";
		try {
			dataPage(conn, pageBean, "v_t_user_bringborrow_lists", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryFailedBorrowById(String id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryFailedBorrowById(conn, id);
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

	public List<Map<String, Object>> queryBringByIds(String id) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			return borrowManageDao.queryBringByIds(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return null;
		} finally {
			conn.close();
		}
	}

	@SuppressWarnings("unchecked")
	public void queryInvestPrecentById(PageBean pageBean, String borrowId) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_investing_borrow ", " * ", "", " and borrowId=" + borrowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	/**
	 * 
	 * @param list
	 * @param ip
	 * @param basePath
	 * @param newLoanInfoList 用来记录成功插入数据的借款标的记录
	 * @return
	 * @throws SQLException  
	 * @author: liuzgmf
	 * @date: 2015年4月21日上午10:45:30
	 */
	public List<String> importDatasHHN(List<Map<String, String>> list, String ip, String basePath,List<Map<String, String>> newLoanInfoList,int operateType) throws SQLException {
		Connection conn = MySQL.getConnection();
		List<String> result = new ArrayList<String>();
		try {
			for (int i = 0, size = list.size(); i < size; i++) {
				Map<String, String> map = list.get(i);
				if (map == null || map.size() == 0) {
					if(operateType == 1){
						result.add("第" + (i + 3) + "行导入失败:身份证号码不存在");
					}else{
						result.add("第" + (i + 1) + "行发布失败:身份证号码不存在");
					}
					
					continue;
				}
				if (!StringUtils.isBlank(map.get("hhnMsg"))) {
					if(operateType == 1){
						result.add("第" + (i + 3) + "行导入失败:" + map.get("hhnMsg"));
					}else{
						result.add("第" + (i + 1) + "行发布失败:" + map.get("hhnMsg"));
					}
					continue;
				}
				// 更新person
				borrowManageDao.updatePerson(conn, map.get("userId"), map.get("realName"), map.get("sex"), map.get("age"), map.get("maritalStatus"),
						map.get("hasHourse"), map.get("highestEdu"), map.get("creditNum"), map.get("creditSum"), map.get("hasCar"));
				// 更新work
				borrowManageDao.updateWork(conn, map.get("userId"), map.get("orgName"), map.get("companyAddress"), map.get("job"),
						map.get("companyType"), map.get("workYear"), map.get("companyLine"), map.get("monthlyIncome"), map.get("monthlyOutcome"));
				// 添加借款
				String title = map.get("moneyPurposes");// 贷款资金用途=借款标题
				int borrowWay = Convert.strToInt(map.get("borrowWay"), 1);
				int deadline = Convert.strToInt(map.get("deadline"), 1);
				int paymentMode = Convert.strToInt(map.get("paymentMode"), 1);
				double amount = Convert.strToDouble(map.get("borrowAmount"), 0);
				double annualRate = Convert.strToDouble(map.get("annualRate"), 0);
				int raiseTerm = Convert.strToInt(map.get("raiseTerm"), 1);
				int excitationType = 1;// 不设置奖励

				// 查询标种类型
				Map<String, String> borrowTypeMap = null;
				List<Map<String, Object>> mapList = null;
				try {
					borrowTypeMap = getBorrowTypeMap(borrowWay + "");
					mapList = platformCostService.queryAllPlatformCost();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Map<String, Object> mapfee = new HashMap<String, Object>();
				Map<String, Object> mapFeestate = new HashMap<String, Object>();
				for (Map<String, Object> platformCostMap : mapList) {
					double costFee = Convert.strToDouble(platformCostMap.get("costFee") + "", 0);
					int costMode = Convert.strToInt(platformCostMap.get("costMode") + "", 0);
					String remark = Convert.strToStr(platformCostMap.get("remark") + "", "");
					if (costMode == 1) {
						mapfee.put(platformCostMap.get("alias") + "", costFee * 0.01);
					} else {
						mapfee.put(platformCostMap.get("alias") + "", costFee);
					}
					mapFeestate.put(platformCostMap.get("alias") + "", remark); // 费用说明
				}
				String json = JSONObject.fromObject(mapfee).toString();
				String jsonState = JSONObject.fromObject(mapFeestate).toString();
				String zxf = map.get("borrowadvisory"); //借款咨询方
				String zxfh = map.get("advisorybranch");//咨询方分行
				
				/*
				 * 20140610 by 刘文韬
				 * 增加标的所属群组
				 */
				int borrowGroup = 0;
				try {
					borrowGroup = Integer.parseInt(map.get("borrowGroup"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				/*
                 * 20141014 by zhangyunhua
                 * 增加标的所属群组
                 */
                String businessNo = "";
                try {
                    businessNo = map.get("businessNo");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
				Map<String, String> ret = borrowService.addBorrowByImport(title, randomImg(), borrowWay, 2, deadline, paymentMode, amount, annualRate, 100,
						-1, raiseTerm, excitationType, 0, "", 1, null, -1, ip, Convert.strToLong(map.get("userId"), 0), 0, 1, basePath, "", 0, 0, 0,
						0, borrowTypeMap.get("identifier"), 0, json, jsonState, title, 2, 0,zxf,zxfh,borrowGroup,businessNo);
				if (Convert.strToInt(ret.get("ret"), -1) > 0) {
					if(operateType == 1){
						result.add("第" + (i + 3) + "行:导入成功!");
					}else{
						result.add("第" + (i + 1) + "行:发布成功!");
					}
					
					if(newLoanInfoList != null){//用来记录成功插入数据的借款标的记录
					    newLoanInfoList.add(map);
					}
				} else {
					if(operateType == 1){
						result.add("第" + (i + 3) + "行:导入失败:" + ret.get("ret_desc"));
					}else{
						result.add("第" + (i + 1) + "行:发布失败:" + ret.get("ret_desc"));
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			result.clear();
			result.add("系统异常");
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 随机取得借款默认图片
	 */
	public String randomImg() {
		String[] imgArr = { "images/random/1.jpg", "images/random/2.jpg", "images/random/3.jpg", "images/random/4.jpg", "images/random/5.jpg",
				"images/random/6.jpg" };
		int index = (int) (Math.random() * imgArr.length);
		return imgArr[index];
	}

	/**
	 * 
	 * 撮合失败，插入资金记录
	 * 
	 * @param list
	 *            [参数说明]
	 * @throws SQLException
	 * 
	 */
	public void insertFundrecord(Map<String, Object> map) throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			borrowManageDao.insertFundrecord(conn, map);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}
