package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sp2p.dao.admin.StatisManageDao;
import com.sp2p.database.Dao.Procedures;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;

/**
 * @ClassName: AfterCreditManageService.java
 * @Author: gang.lv
 * @Date: 2013-3-19 上午10:18:35
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 统计管理业务处理层
 */
public class StatisManageService extends BaseService {

	public static Log log = LogFactory.getLog(StatisManageService.class);

	private StatisManageDao statisManageDao;

	public StatisManageDao getStatisManageDao() {
		return statisManageDao;
	}

	public void setStatisManageDao(StatisManageDao statisManageDao) {
		this.statisManageDao = statisManageDao;
	}

	/**
	 * @throws DataException
	 * @MethodName: queryLoginStatisByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:49:43
	 * @Return:
	 * @Descb: 查询登录统计
	 * @Throws:
	 */
	public void queryLoginStatisByCondition(String userName, String realName,
			String timeStart, String timeEnd, int countInt, PageBean pageBean)
			throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realname  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and lastDate >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and lastDate <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (countInt != -1) {
			condition.append(" and loginCount = " + countInt);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_login_statis", resultFeilds, " ",
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
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryInvestStatisByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午09:33:22
	 * @Return:
	 * @Descb: 查询投资统计列表
	 * @Throws:
	 */
	public void queryInvestStatisByCondition(String bTitle, String investor,
			String timeStart, String timeEnd, int borrowWayInt,
			int isAutoBidInt, String borrowStatus, int groupInt,
			PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(bTitle)) {
			condition.append(" and  borrowTitle  like '%"
					+ StringEscapeUtils.escapeSql(bTitle.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (borrowWayInt != -1) {
			condition.append(" and borrowWay = " + borrowWayInt);
		}
		if (isAutoBidInt != -1) {
			condition.append(" and isAutoBid = " + isAutoBidInt);
		}
		if (StringUtils.isNotBlank(borrowStatus) && !"-1".equals(borrowStatus)) {
			condition.append(" and borrowStatus in "
					+ StringEscapeUtils.escapeSql(borrowStatus));
		}
		if (groupInt != -1) {
			condition.append(" and groupid = " + groupInt);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_invest_statis ", resultFeilds, " ",
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
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryInvestStatisRankByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午10:35:39
	 * @Return:
	 * @Descb: 查询投资排名列表
	 * @Throws:
	 */
	public void queryInvestStatisRankByCondition(String investor,
			String timeStart, String timeEnd, int groupInt, PageBean pageBean)
			throws SQLException, DataException {
		String resultFeilds = " a.*,b.realSum ";
		StringBuffer condition = new StringBuffer();
		StringBuffer tables = new StringBuffer();
		tables
				.append(" v_t_invest_rank a left join (select investor,sum(realAmount) realSum from v_t_invest_rank where 1=1 ");
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and a.investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
			tables.append(" and investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and a.investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
			tables.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and a.investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
			tables.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (groupInt != -1) {
			condition.append(" and a.groupId = " + groupInt);
			tables.append(" and groupId = " + groupInt);
		}
		tables.append(" group by investor) b on a.investor = b.investor ");
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, tables.toString(), resultFeilds,
					" order by b.realSum desc,a.realAmount desc ", condition
							.toString());
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
	 * @MethodName: queryBorrowStatisByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午10:23:49
	 * @Return:
	 * @Descb: 借款管理费统计
	 * @Throws:
	 */
	public void queryBorrowStatisByCondition(String borrowTitle,
			String borrower, String timeStart, String timeEnd,
			int borrowWayInt, PageBean pageBean) throws SQLException,
			DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(borrowTitle)) {
			condition.append(" and  borrowTitle  like '%"
					+ StringEscapeUtils.escapeSql(borrowTitle.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(borrower)) {
			condition.append(" and borrower  like '%"
					+ StringEscapeUtils.escapeSql(borrower.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and auditTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and auditTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (borrowWayInt != -1) {
			condition.append(" and borrowWay = " + borrowWayInt);
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_statis ", resultFeilds, " ",
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
	 * @MethodName: queryBorrowStatisAmount
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午11:00:40
	 * @Return:
	 * @Descb: 查询借款统计总计
	 * @Throws:
	 */
	public Map<String, String> queryBorrowStatisAmount(String borrowTitle,
			String borrower, String timeStart, String timeEnd, int borrowWayInt)
			throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryBorrowStatisAmount(conn, borrowTitle,
					borrower, timeStart, timeEnd, borrowWayInt);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryborrowStatisInterestByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午03:16:20
	 * @Return:
	 * @Descb: 投标借款管理费统计
	 * @Throws:
	 */
	public void queryborrowStatisInterestByCondition(String investor,
			String timeStart, String timeEnd, PageBean pageBean)
			throws SQLException, DataException {
		String resultFeilds = " a.id,a.investor,a.realName,round(b.manageFI,2) as manageFI,round(b.hasPI,2) as hasPI,round(b.manageFee,2) as manageFee, round(b.hasInterest,2) as hasInterest, round(b.forInterest,2) as forInterest,round(b.forPI,2) as forPI";
		StringBuffer condition = new StringBuffer();
		StringBuffer tables = new StringBuffer();
		tables
				.append(" v_t_invest_interest_statis a left join (select investor,sum(manageFI) manageFI,sum(hasPI) hasPI,sum(manageFee ) manageFee ,");
		tables
				.append("sum(hasInterest) hasInterest,sum(forInterest) forInterest,sum(forPI) forPI   from v_t_invest_interest_statis where 1=1 ");
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and a.investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
			tables.append(" and investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and a.investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
			tables.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and a.investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
			tables.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		tables.append(" group by investor) b on a.investor = b.investor");
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, tables.toString(), resultFeilds, "",
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

	// ........................................................................................................
	/**
	 * 查询用户组统计
	 * 
	 * @param groupName
	 *            查询条件 组名
	 * @param pageBean
	 *            分页
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryborrowStatisUserGroupByCondition(String groupName,
			PageBean pageBean) throws SQLException, DataException {
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		StringBuffer tables = new StringBuffer();
		tables
				.append(" ( select ifnull(sum(a.totalSum),0) totalSum,ifnull(sum(a.usableSum),0) usableSum,ifnull(sum(a.freezeSum),0) freezeSum,round(ifnull(sum(b.forPI),0),2) forPI,round(ifnull(sum(b.forInterest),0),2) forInterest,ifnull(sum(c.manageFee),0) manageFee,");
		tables
				.append(" ifnull(sum(d.vipFee),0) vipFee,ifnull(sum(e.hasPI),0) hasPI,ifnull(sum(f.realAmount),0) realAmount,g.groupId,h.groupName from");
		tables
				.append(" v_t_group_user_amount a left join v_t_group_for_amount b on a.userId = b.userId left join v_t_group_managefee c on a.userId = c.userId left join v_t_group_vip d ");
		tables
				.append(" on a.userId = d.userId left join v_t_has_amount e on a.userId=e.userId left join  (select sum(realAmount) realAmount,userId from v_t_invest_amount group by userId) f");
		tables
				.append(" on a.userId = f.userId left join t_group_user g on a.userId = g.userId left join t_group h on g.groupId =h.id");
		tables
				.append(" where groupId is not null group by g.groupId,h.groupName) t");
		if (StringUtils.isNotBlank(groupName)) {
			condition.append(" and t.groupName ='"
					+ StringEscapeUtils.escapeSql(groupName.trim()) + "'");
		}
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, tables.toString(), resultFeilds,
					" order by groupId desc", condition.toString());
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

	// ......................................................................

	/**
	 * @MethodName: queryFinanceStatis
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午03:17:37
	 * @Return:
	 * @Descb: 查询投资统计
	 * @Throws:
	 */
	public Map<String, String> queryFinanceEarnStatis(String timeStart,
			String timeEnd) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getFinanceEarnStatis(conn, ds, outParameterValues,
					timeStart, timeEnd);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryWebStatis
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午03:18:16
	 * @Return:
	 * @Descb: 查询网站统计
	 * @Throws:
	 */
	public Map<String, String> queryWebStatis() throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getWebStatis(conn, ds, outParameterValues, -1);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 网站资金 统计
	 * 
	 * @MethodName: queryWebStatis
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午03:18:16
	 * @Return:
	 * @Descb: 网站统计
	 * @Throws:
	 */
	public Map<String, String> queryWebStatisFunds() throws SQLException,
			DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getWebStatisFunds(conn, ds, outParameterValues, -1);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 资金类别 统计
	 * @param time 
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> queryFundTypeStatisByCondition(String time, PageBean pageBean) throws Exception {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getStatisFundsType(conn, ds, outParameterValues, time);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
}
