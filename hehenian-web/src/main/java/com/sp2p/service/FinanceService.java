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

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.security.Encrypt;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FinanceDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.RepayMentDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.task.JobTaskDao;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;
import com.sp2p.util.WebUtil;

/**
 * @ClassName: FinanceService.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:14:21
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 理财业务处理层
 */
public class FinanceService extends BaseService {

	public static Log log = LogFactory.getLog(FinanceService.class);

	private FinanceDao financeDao;

	private AwardService awardService;

	private SelectedService selectedService;

	private UserDao userDao;

	private OperationLogDao operationLogDao;

	private RepayMentDao repayMentDao;

	private InvestDao investDao;

	private BorrowDao borrowDao;
	
	private JobTaskDao jobTaskDao;
	
	private UserService userService;

	private ChinaPnRInterfaceService chinaPnRInterfaceService;

	/**
	 * @MethodName: queryBorrowByCondition
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-4 下午05:01:31
	 * @Return:
	 * @Descb: 根据条件查询借款信息
	 * @Throws:
	 */
	public void queryBorrowByCondition(String borrowStatus, String borrowWay, String title, String paymentMode, String purpose, String deadline,
			String reward, String arStart, String arEnd, String order, PageBean pageBean, int isTimes) throws Exception {
		String resultFeilds = " id,borrowShow,purpose,imgPath,borrowWay,investNum,borrowTitle,username,vipStatus,credit,creditrating,borrowAmount,number,annualRate,deadline,excitationType,excitationSum,borrowStatus,schedules,vip,hasPWD,isDayThe,auditStatus,publishTime ";
		StringBuffer condition = new StringBuffer();
		condition.append(" and sorts!= 0 and isTimes =" + isTimes);
		Connection conn = connectionManager.getConnection();
		if (StringUtils.isNotBlank(borrowStatus)) {
			condition.append(" and borrowStatus in" + StringEscapeUtils.escapeSql(borrowStatus));
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay in" + StringEscapeUtils.escapeSql(borrowWay));
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(paymentMode) && StringUtils.isNumericSpace(paymentMode)) {
			condition.append(" and paymentMode =" + StringEscapeUtils.escapeSql(paymentMode));
		}
		if (StringUtils.isNotBlank(purpose) && StringUtils.isNumericSpace(purpose)) {
			condition.append(" and purpose =" + StringEscapeUtils.escapeSql(purpose));
		}
		if (StringUtils.isNotBlank(deadline) && StringUtils.isNumericSpace(deadline)) {
			condition.append(" and deadline =" + StringEscapeUtils.escapeSql(deadline));
		}
		if (StringUtils.isNotBlank(reward) && StringUtils.isNumericSpace(reward)) {
			if ("1".equals(reward)) {
				condition.append(" and excitationType =" + StringEscapeUtils.escapeSql(reward));
			} else {
				condition.append(" and excitationType > 1 ");
			}
		}
		if (StringUtils.isNotBlank(arStart) && StringUtils.isNumericSpace(arStart)) {
			condition.append(" and amount > " + StringEscapeUtils.escapeSql(arStart));
		}
		if (StringUtils.isNotBlank(arEnd) && StringUtils.isNumericSpace(arEnd)) {
			condition.append(" and amount <" + StringEscapeUtils.escapeSql(arEnd));
		}
		try {
			dataPage(conn, pageBean, " v_t_borrow_list", resultFeilds, " ", condition.toString());
			List<Map<String, Object>> list = pageBean.getPage();
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					map.put("scend", DateUtil.getMsecondsDiff((Date) map.get("publishTime")));
					String a = DateUtil.remainDateToString(new Date(), (Date) map.get("publishTime"));
					map.put("publishTime", a);

				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	@SuppressWarnings("unchecked")
	public void queryBorrowByConditions(String borrowStatus, String borrowWay, String title, String paymentMode, String purpose, String deadline,
			String reward, String arStart, String arEnd, String order, PageBean pageBean, Date publishTime, String pro, String city,int borrowGroup) throws Exception {
		StringBuffer condition = new StringBuffer();
		condition.append(" and sorts!= 0 ");
		Connection conn = connectionManager.getConnection();
		if (publishTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			condition.append(" and publishTime > '" + sdf.format(publishTime) + "'");
		}
		if (StringUtils.isNotBlank(borrowStatus))
			condition.append(" and borrowStatus in" + StringEscapeUtils.escapeSql(borrowStatus));

		if (StringUtils.isNotBlank(borrowWay))
			condition.append(" and borrowWay in" + StringEscapeUtils.escapeSql(borrowWay));

		if (StringUtils.isNotBlank(title))
			condition.append(" and borrowTitle  LIKE CONCAT('%','" + StringEscapeUtils.escapeSql(title.trim()) + "','%')");

		if (StringUtils.isNotBlank(paymentMode) && StringUtils.isNumericSpace(paymentMode))
			condition.append(" and paymentMode =" + StringEscapeUtils.escapeSql(paymentMode));

		if (StringUtils.isNotBlank(purpose) && StringUtils.isNumericSpace(purpose))
			condition.append(" and purpose =" + StringEscapeUtils.escapeSql(purpose));

		if (StringUtils.isNotBlank(pro) && StringUtils.isNumericSpace(pro))
			condition.append(" and registedPlacePro =" + StringEscapeUtils.escapeSql(pro));

		if (StringUtils.isNotBlank(city) && StringUtils.isNumericSpace(city))
			condition.append(" and registedPlaceCity =" + StringEscapeUtils.escapeSql(city));

		if (StringUtils.isNotBlank(deadline) && StringUtils.isNumericSpace(deadline))
			condition.append(" and deadline =" + StringEscapeUtils.escapeSql(deadline));

		if (StringUtils.isNotBlank(reward) && StringUtils.isNumericSpace(reward))
			if ("1".equals(reward))
				condition.append(" and excitationType =" + StringEscapeUtils.escapeSql(reward));
			else
				condition.append(" and excitationType > 1 ");

		if (StringUtils.isNotBlank(arStart) && StringUtils.isNumericSpace(arStart))
			condition.append(" and amount > " + StringEscapeUtils.escapeSql(arStart));

		if (StringUtils.isNotBlank(arEnd) && StringUtils.isNumericSpace(arEnd))
			condition.append(" and amount <" + StringEscapeUtils.escapeSql(arEnd));
		
		/*
		 * 20140610 by 刘文韬
		 * 查询标的列表 只查出来对应群组的标的
		 */
		condition.append(" and borrowGroup = " + borrowGroup);
		
		
		try {
			dataPage(conn, pageBean, "v_t_borrow_list_hhn", "*", " ", condition.toString());
			List<Map<String, Object>> list = pageBean.getPage();
			if (list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					map.put("scend", DateUtil.getMsecondsDiff((Date) map.get("publishTime")));
					String a = DateUtil.remainDateToString(new Date(), (Date) map.get("publishTime"));
					if(map.get("publishTime") != null && "过期".equals(a)) {
						map.put("publishTime", "正在发布中...");
					} else {
						map.put("publishTime", a);
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryBorrowDetailById
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:18:19
	 * @Return:
	 * @Descb: 根据ID查询借款详细信息
	 * @Throws:
	 */
	public Map<String, String> queryBorrowDetailById(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryBorrowDetailById(conn, id);
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
	 * @MethodName: queryUserInfoById
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午06:04:54
	 * @Return:
	 * @Descb: 根据ID查询借款信息发布者个人信息
	 * @Throws:
	 */
	public Map<String, String> queryUserInfoById(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryUserInfoById(conn, id);
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
	 * @MethodName: queryUserIdentifiedByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:00:04
	 * @Return:
	 * @Descb: 根据ID查询用户认证信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserIdentifiedByid(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserIdentifiedByid(conn, id);
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
	 * @MethodName: queryUserImageByid
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-4-16 上午11:01:28
	 * @Return:
	 * @Descb: 查询用户认证图片
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserImageByid(long typeId, long userId) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserImageByid(conn, typeId, userId);
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @MethodName: queryPaymentByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:03:01
	 * @Return:
	 * @Descb: 根据ID查询本期还款信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryRePayByid(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryRePayByid(conn, id);
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
	 * 根据用户ID查询出风险评定
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 * @return Map<String,Object> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> queryRiskContentById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryRiskContentById(conn, id);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryCollectionByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:04:28
	 * @Return:
	 * @Descb: 根据ID查询本期催收信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryCollectionByid(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryCollectionByid(conn, id);
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
	 * @MethodName: queryInvestByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:06:00
	 * @Return:
	 * @Descb: 根据ID查询投资记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryInvestByid(long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryInvestByid(conn, id);
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
	 * @MethodName: queryBorrowMSGBord
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:30:26
	 * @Return:
	 * @Descb: 根据ID查询留言板信息
	 * @Throws:
	 */
	public void queryBorrowMSGBord(long id, PageBean pageBean) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_msgbord", " * ", " order by id desc ", " and modeId=" + id);
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

	@SuppressWarnings("unchecked")
	public void queryInvestPercent(long id, PageBean pageBean) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_invest_percent", " * ", " order by investTime asc ", " and borrowId=" + id);
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
	 * @MethodName: addBrowseCount
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午03:54:02
	 * @Return:
	 * @Descb: 添加浏览量处理
	 * @Throws:
	 */
	public void addBrowseCount(Long id) throws SQLException {
		Connection conn = MySQL.getConnection();
		long returnId = -1L;
		try {
			returnId = financeDao.addBrowseCount(conn, id);
			if (returnId <= 0) {
				conn.rollback();
			} else {
				conn.commit();
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @throws DataException
	 * @MethodName: addBorrowMSG
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午08:16:45
	 * @Return:
	 * @Descb: 添加借款留言
	 * @Throws:
	 */
	public long addBorrowMSG(long id, long userId, String msgContent) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long returnId = -1;
		try {
			returnId = financeDao.addBorrowMSG(conn, id, userId, msgContent);
			if (returnId <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_msgboard", Convert.strToStr(userMap.get("username"), ""), IConstants.INSERT,
						Convert.strToStr(userMap.get("lastIP"), ""), 0, "添加借款留言", 1);
				conn.commit();
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return returnId;
	}

	/**
	 * @MethodName: getInvestStatus
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午06:46:17
	 * @Return:
	 * @Descb: 获取借款的投标状态,条件是正在招标中
	 * @Throws:
	 */
	public Map<String, String> getInvestStatus(long id) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.getInvestStatus(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: valideInvest
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午04:07:59
	 * @Return:
	 * @Descb: 验证投资人是否符合本次投标
	 * @Throws:
	 */
	public String valideInvest(long id, long userId, double amount) throws SQLException {
		Connection conn = connectionManager.getConnection();
		String result = "";
		try {
			result = financeDao.valideInvest(conn, id, userId, amount);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * @MethodName: valideTradePassWord
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午04:07:43
	 * @Return:
	 * @Descb: 验证交易密码
	 * @Throws:
	 */
	public String valideTradePassWord(long userId, String pwd) throws SQLException {
		Connection conn = connectionManager.getConnection();
		String result = "";
		try {
			result = financeDao.valideTradePassWord(conn, userId, pwd);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * @MethodName: queryBorrowInvest
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午07:30:26
	 * @Return:
	 * @Descb: 根据ID获取投资的借款信息
	 * @Throws:
	 */
	public Map<String, String> queryBorrowInvest(long id) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryBorrowInvest(conn, id);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryUserMonney
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午08:48:43
	 * @Return:
	 * @Descb: 查询用户的金额
	 * @Throws:
	 */
	public Map<String, String> queryUserMonney(long userId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryUserMonney(conn, userId);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @throws Exception
	 * @MethodName: addBorrowInvest
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午05:48:20
	 * @Return:
	 * @Descb: 添加借款投资记录
	 * @Throws:
	 */
	public Map<String, String> addBorrowInvest(long id, long userId, String dealPWD, double investAmount, String basePath, String username,
			int status, int num) throws Exception {
		Connection conn = MySQL.getConnection();
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		List<Object> outValues = new ArrayList<Object>();
		try {
			Procedures.p_borrow_join(conn, ds, outValues, id, userId, basePath, new BigDecimal(investAmount), new Date(), status, num, 0, "", "");
			ret = Convert.strToLong(outValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outValues.get(1) + "");
			map.put("ret_ordid", outValues.get(2) + "");
			if (ret <= 0) {
				conn.rollback();
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_invest", Convert.strToStr(userMap.get("username"), ""), IConstants.INSERT,
						Convert.strToStr(userMap.get("lastIP"), ""), 0, "用户投标借款", 1);
				conn.commit();
			}
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
	 * 投标
	 * 
	 * @param conn
	 * @param investAmount
	 * @param id
	 * @param userId
	 * @param basePath
	 * @param username
	 * @param status
	 * @param num
	 * @return
	 */
	private Map<String, String> validateInvest(Connection conn, double investAmount, long id, long userId, String basePath, String username,
			int status, int num) {
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_borrow_join(conn, ds, outParameterValues, id, userId, basePath, new BigDecimal(investAmount), new Date(), status, num, 0,
					"", "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret <= 0) {
				conn.rollback();
				return map;
			}
			// 添加操作日志
			userMap = userDao.queryUserById(conn, userId);
			operationLogDao.addOperationLog(conn, "t_invest", Convert.strToStr(userMap.get("username"), ""), IConstants.INSERT,
					Convert.strToStr(userMap.get("lastIP"), ""), 0, "用户投标借款", 1);
			// 得到当前用户最新的投资ID
			Map<String, String> maps = investDao.queryInvestId(conn, id, userId);
			// 得到借款当前借款信息
			Map<String, String> borrowMap = borrowDao.queryBorroeById(conn, id);
			if (borrowMap != null) {
				long borrowWay = Convert.strToLong(borrowMap.get("borrowWay"), -1);
				if (borrowWay == 6) {
					// 提成奖励
					ret = awardService.updataMoney(conn, userId, new BigDecimal(investAmount), IConstants.MONEY_TYPE_1,
							Convert.strToLong(maps.get("investId"), -1));
				}
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * @throws DataException
	 * @MethodName: addFocusOn
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午09:00:49
	 * @Return:
	 * @Descb: 添加关注
	 * @Throws:
	 */
	public Long addFocusOn(long id, long userId, int modeType) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = financeDao.addFocusOn(conn, id, userId, modeType);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				if (modeType == 1) {
					operationLogDao.addOperationLog(conn, "t_concern", Convert.strToStr(userMap.get("username"), ""), IConstants.INSERT,
							Convert.strToStr(userMap.get("lastIP"), ""), 0, "添加关注用户", 1);
				} else {
					operationLogDao.addOperationLog(conn, "t_concern", Convert.strToStr(userMap.get("username"), ""), IConstants.INSERT,
							Convert.strToStr(userMap.get("lastIP"), ""), 0, "添加关注借款", 1);
				}

				conn.commit();
			}
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
	 * @MethodName: hasFocusOn
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午11:04:13
	 * @Return:
	 * @Descb: 查询用户是否已经有关注
	 * @Throws:
	 */
	public Map<String, String> hasFocusOn(long id, long userId, int moduleType) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.hasFocusOn(conn, id, userId, moduleType);
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
	 * @MethodName: addUserMail
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:13:57
	 * @Return:
	 * @Descb: 添加用户站内信
	 * @Throws:
	 */
	public long addUserMail(long reciver, Long userId, String title, String content, int mailType) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = financeDao.addUserMail(conn, reciver, userId, title, content, mailType);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_concern", Convert.strToStr(userMap.get("username"), ""), IConstants.INSERT,
						Convert.strToStr(userMap.get("lastIP"), ""), 0, "发送站内信", 1);
				conn.commit();
			}
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
	 * @MethodName: addUserReport
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:15:05
	 * @Return:
	 * @Descb: 添加用户举报
	 * @Throws:
	 */
	public long addUserReport(long reporter, Long userId, String title, String content) throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = financeDao.addUserReport(conn, reporter, userId, title, content);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_report", Convert.strToStr(userMap.get("username"), ""), IConstants.INSERT,
						Convert.strToStr(userMap.get("lastIP"), ""), 0, "添加用户举报", 1);
				conn.commit();
			}
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
	 * @MethodName: queryLastestBorrow
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午09:28:00
	 * @Return:
	 * @Descb: 查询最新的借款前10条记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryLastestBorrow() throws Exception {
		Connection conn = connectionManager.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryLastestBorrow(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @MethodName: investRank
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午11:11:37
	 * @Return:
	 * @Descb: 投资排名前20条记录
	 * @Throws:
	 */
	public List<Map<String, Object>> investRank(int type, int count) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		// List<Map<String, Object>> list = new ArrayList<Map<String,
		// Object>>();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			// list = financeDao.investRank(conn, starTime, endTime);
			Procedures.p_get_topinvestment(conn, ds, outParameterValues, type, count);
			ds.tables.get(0).rows.genRowsMap();

			return ds.tables.get(0).rows.rowsMap;

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
	 * @MethodName: queryTotalRisk
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:36:01
	 * @Return:
	 * @Descb: 查询风险保障金总额
	 * @Throws:
	 */
	public Map<String, String> queryTotalRisk() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryTotalRisk(conn);
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
	 * @MethodName: queryCurrentRisk
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:36:14
	 * @Return:
	 * @Descb: 查询当日风险保障金收支金额
	 * @Throws:
	 */
	public Map<String, String> queryCurrentRisk() throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryCurrentRisk(conn);
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
	 * @throws SQLException
	 * @MethodName: queryBorrowRecord
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午11:03:17
	 * @Return:
	 * @Descb: 查询借款记录统计
	 * @Throws:
	 */
	public Map<String, String> queryBorrowRecord(Long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getBorrowRecord(conn, ds, outParameterValues, id, new Date());
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
	 * @MethodName: queryBorrowRecord
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午11:03:17
	 * @Return:
	 * @Descb: 查询借款记录统计(根据用户ID)
	 * @Throws:
	 */
	public Map<String, String> queryBorrowRecord_user(Long id) throws SQLException, DataException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getBorrowRecord_user(conn, ds, outParameterValues, id, new Date());
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: subscribeSubmit
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-5-21 上午10:30:15
	 * @Return:
	 * @Descb: 认购提交
	 * @Throws:
	 */
	public String subscribeSubmit(long id, int copies, Long userId, String basePath, String username, Map<String, Object> platformCostMap)
			throws SQLException {
		Connection conn = MySQL.getConnection();
		String msg = "";
		long returnId = -1;
		try {
			Map<String, String> borrowTenderInMap = financeDao.queryBorrowTenderIn(conn, id);
			// 认购中的借款
			if (borrowTenderInMap != null && borrowTenderInMap.size() > 0) {
				long remainCirculationNumber = Convert.strToLong(borrowTenderInMap.get("remainCirculationNumber") + "", 0);
				double smallestFlowUnit = Convert.strToDouble(borrowTenderInMap.get("smallestFlowUnit") + "", 0);
				if (copies > remainCirculationNumber) {
					// 校验认购份数是否满足
					msg = "只剩下【" + remainCirculationNumber + "】份可认购,请重新选择!";
				} else {
					// 提交的认购总金额
					double investAmount = smallestFlowUnit * copies;
					// 查询账户上的金额是否满足认购的份数
					Map<String, String> usableSumMap = financeDao.queryUserUsableSum(conn, userId, investAmount);
					if (usableSumMap != null && usableSumMap.size() > 0) {
						double usableSum = Convert.strToDouble(usableSumMap.get("usableSum") + "", 0);
						long minCirculationNumber = 0;
						double needSum = 0;
						if (usableSum < smallestFlowUnit) {
							msg = "您的可用余额少于￥" + smallestFlowUnit + "元，认购失败!";
						} else {
							// 计算向下取数满足最小的认购份数
							for (long n = remainCirculationNumber; n > 0; n--) {
								needSum = smallestFlowUnit * n;
								if (usableSum >= needSum) {
									minCirculationNumber = n;
									break;
								}
							}
							msg = "您的可用余额可认购【" + minCirculationNumber + "】份,请重新选择!";
						}
					} else {
						Map<String, String> map = validateInvest(conn, investAmount, id, userId, basePath, username, 1, copies);
						returnId = Convert.strToLong(map.get("ret"), -1);
						if (returnId <= 0) {
							conn.rollback();
							msg = Convert.strToStr(map.get("ret_desc"), "");
						} else {
							msg = "1";
						}
					}
				}
			} else {
				// 认购已满,更新状态为回购中
				financeDao.updateRepo(conn, id);
				msg = "无效借款投标";
			}

			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			msg = IConstants.ACTION_FAILURE;
		} finally {
			conn.close();
		}
		return msg;
	}

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}

	/**
	 * @throws SQLException
	 * @MethodName: getInvestPWD
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午05:35:00
	 * @Return:
	 * @Descb: 获取投标密码是否正确
	 * @Throws:
	 */
	public Map<String, String> getInvestPWD(Long idLong, String investPWD) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			if ("1".equals(IConstants.ENABLED_PASS)) {
				investPWD = com.shove.security.Encrypt.MD5(investPWD.trim());
			} else {
				investPWD = com.shove.security.Encrypt.MD5(investPWD.trim() + IConstants.PASS_KEY);
			}
			map = financeDao.getInvestPWD(conn, idLong, investPWD);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 获取交易密码是否正确
	 * 
	 * @param idLong
	 * @param dealPwd
	 * @return
	 * @throws SQLException
	 *             [参数说明]
	 * 
	 * @return Map<String,String> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public Map<String, String> getDealPWD(Long idLong, String dealPwd) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			if ("1".equals(IConstants.ENABLED_PASS)) {
				dealPwd = com.shove.security.Encrypt.MD5(dealPwd.trim());
			} else {
				dealPwd = com.shove.security.Encrypt.MD5(dealPwd.trim() + IConstants.PASS_KEY);
			}
			map = financeDao.getDealPWD(conn, idLong, dealPwd);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 根据借款Id查询还款记录
	 * 
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryRepayment(long borrowId) throws SQLException, DataException {

		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		try {
			map = repayMentDao.queryHasPIAndStillPi(conn, borrowId);
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
	 * 查找投资人信息 add by houli
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryInvestorById(long investorId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			return financeDao.queryInvestorById(conn, investorId, -1, -1);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 根据借款ID查询还款记录
	 * 
	 * @param pageBean
	 * @param borrowId
	 * @throws Exception
	 *             [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public void queryRepaymentById(PageBean pageBean, String borrowId) throws Exception {
		Connection conn = connectionManager.getConnection();
		pageBean.setPageSize(25);
		try {
			dataPage(conn, pageBean, " v_t_query_repayment_byid ", "*", " order by repayPeriod+0 ", " and borrowId=" + Integer.valueOf(borrowId));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 根据ID查询催收记录
	 * 
	 * @param pageBean
	 * @param borrowId
	 * @throws Exception
	 *             [参数说明]
	 * 
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public void queryCollectionById(PageBean pageBean, String borrowId) throws Exception {
		Connection conn = connectionManager.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_collection_byid ", "*", " order by borrowId desc ", " and borrowId=" + Integer.valueOf(borrowId));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public AwardService getAwardService() {
		return awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
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

	public RepayMentDao getRepayMentDao() {
		return repayMentDao;
	}

	public void setRepayMentDao(RepayMentDao repayMentDao) {
		this.repayMentDao = repayMentDao;
	}

	public InvestDao getInvestDao() {
		return investDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public JobTaskDao getJobTaskDao() {
		return jobTaskDao;
	}

	public void setJobTaskDao(JobTaskDao jobTaskDao) {
		this.jobTaskDao = jobTaskDao;
	}
	
	

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public long updateUserFreeSum(long usrCustId, double transAmt) throws SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return financeDao.updateUserFreezeSum(conn, usrCustId, transAmt);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return -1;
	}

	/**
	 * 汇付失败后 处理投标
	 */
	public long deleteInvestRecord(String ordId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		try {
			return investDao.deleteRecord(conn, ordId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return -1;
	}

	/**
	 * 汇付成功后 处理投标
	 * 
	 * @param subAcctType
	 * @param subAcctId
	 * 
	 * @throws SQLException
	 */
	public Map<String, String> updateBorrowInvest(long borrowId, long ordid, Long userId, double investAmount, String basePath, String userName,
			int status, int num, String subAcctId, String subAcctType, long	 usrCustId) throws SQLException {
		Connection conn = MySQL.getConnection();
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		List<Object> outValues = new ArrayList<Object>();
		try {
			// 冻结投标金额
			String transAmt = new DecimalFormat("0.00").format(investAmount);
			JSONObject json = JSONObject.fromObject(ChinaPnRInterface.usrFreezeBg(usrCustId + "", subAcctType, subAcctId, ordid + "", transAmt));
			if (json.getInt("RespCode") != 0) {
				// 失败处理
				chinaPnRInterfaceService.deleteBorrowInvest(ordid + "");
				map.put("ret", "-10010");
				map.put("ret_desc", "汇付："+json.getString("RespDesc"));
				conn.commit();
				return map;
			}
			
			Procedures.p_borrow_join_call_back(conn, ds, outValues, borrowId, ordid, userId, basePath, new BigDecimal(investAmount), new Date(),
					status, num, 0, "");
			ret = Convert.strToLong(outValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outValues.get(1) + "");
			if (ret <= 0) {
				conn.rollback();
				map.put("ret_desc","系统："+ outValues.get(1) + "");
				//失败上汇付解冻投资人的投标冻结金额
				String jsonStr = ChinaPnRInterface.usrUnFreeze(json.getString("TrxId"), json.getString("TrxId"));
				log.info("投标平台执行失败:"+ map.get("ret_desc")+",解冻投标金额处理结果："+jsonStr);
			} else {
				// 冻结成功更新trxId
				chinaPnRInterfaceService.updateInvestTrxId(json.getString("OrdId"), json.getString("TrxId"));
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_invest", Convert.strToStr(userMap.get("username"), ""), IConstants.INSERT,
						Convert.strToStr(userMap.get("lastIP"), ""), 0, "用户投标借款", 1);
				conn.commit();
			}
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}

	public void setChinaPnRInterfaceService(ChinaPnRInterfaceService chinaPnRInterfaceService) {
		this.chinaPnRInterfaceService = chinaPnRInterfaceService;
	}
	
	
	/**
	 * 根据标的ID查询处理状态
	 * @param borrowId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryBorrowDelStauts(String ordId) throws SQLException {
		Connection conn = connectionManager.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryBorrowDelStauts(conn,ordId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return map;
	}
	
	/* 如果用户是在彩生活平台投资，在插入投标记录成功后，修改flag的值，标记该笔投资
	 * 20140609 by 刘文韬
	 *  */
	public void updateInvestFlag(String ordId,String platform)  throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			String command = "update  t_invest set flag='"+platform+"' where id="+ordId;
			MySQL.executeNonQuery(conn, command);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * @MethodName: autoBid
	 * @Param: JobTaskService
	 * @Author: lizg
	 * @Date: 2013-6-6  
	 * @Return:
	 * @Descb: 一键投标
	 * @Throws:
	 */
	public void oneKeyBid(long userId) throws SQLException, DataException {
		Connection conn = Database.getConnection();
		List<Map<String, Object>> biderList = jobTaskDao.queryAllBider(conn);
		List<Map<String, Object>> userList = null;
		Map<String, String> userParam = null;
		Map<String, String> bider = null;
		Map<String, String> userAmount = null;
		Map<String, String> borrowOwer = null;
		List<Map<String, Object>> userBiderList = null;
		Map<String, String> borrowInfoMap = null;
		Map<String, String> userAmountMap = null;
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		Map<String, String> hasInvestMap = null;
		Map<String, String> borrowMap = null;
		// 利率开始
		double rateStart = 0;
		// 利率结束
		double rateEnd = 0;
		// 借款期限开始
		int deadLineStart = 0;
		// 借款期限结束
		int deadLineEnd = 0;
		// 信用等级开始
		int creditStart = 0;
		// 信用等级结束
		int creditEnd = 0;
//		// 用户Id
//		long userId = -1;
		// 借款id
		int borrowId = -1;
		// 投标金额
		double bidAmount = 0;
		// 保留金额
		double remandAmount = 0;
		// 借款金额
		double borrowAmount = 0;
		// 已投资金额
		double hasAmount = 0;
		// 可用金额
		double usableAmount = 0;
		// 是否为天标
		int isDayThe = 1;
		// 月利率
		double monthRate = 0;
		// 发布者
		long publisher = -1;
		// 借款期限
		int deadline = 0;
		// 借款标题
		String borrowTitle = "";
		long returnId = -1;
		String username = "";
		DecimalFormat format = new DecimalFormat("0.00");
		try {
			log.info("##############一键投标 开始 用户ID：=="+userId);
			userParam = jobTaskDao.queryUserBidParam(conn, userId);
			if(userParam!=null){
				rateStart = Convert.strToDouble(userParam.get("rateStart") + "", 0);
				rateEnd = Convert.strToDouble(userParam.get("rateEnd") + "", 0);
				deadLineStart = Convert.strToInt(userParam.get("deadlineStart") + "", 0);
				deadLineEnd = Convert.strToInt(userParam.get("deadlineEnd") + "", 0);
				creditStart = Convert.strToInt(userParam.get("creditStart") + "", 0);
				creditEnd = Convert.strToInt(userParam.get("creditEnd") + "", 0);
				bidAmount = Convert.strToDouble(userParam.get("bidAmount") + "", 0);
				remandAmount = Convert.strToDouble(userParam.get("remandAmount") + "", 0);
				
				// 根据用户投标参数获取投标的标的
				biderList = jobTaskDao.queryBiderListByParam(conn, rateStart, rateEnd, deadLineStart, deadLineEnd, creditStart, creditEnd, borrowId, isDayThe);
				if(biderList != null){
				for (Map<String, Object> biderMap : biderList) {
					borrowId = Convert.strToInt(biderMap.get("id") + "", -1);
					borrowAmount = Convert.strToDouble(biderMap.get("borrowAmount") + "", 0);
					hasAmount = Convert.strToDouble(biderMap.get("hasInvestAmount") + "", 0);
//					isDayThe = Convert.strToInt(biderMap.get("isDayThe") + "", -1);
					log.info("一键投标  标的信息：借款ID  borrowId：=="+borrowId);
					log.info("一键投标  标的信息：总借款金额borrowAmount：=="+borrowAmount);
					log.info("一键投标 标的信息：已投资金额hasAmount：=="+hasAmount);
					// 满足投标条件,进行扣费处理 查询借款的基础信息
					borrowInfoMap = jobTaskDao.queryBorrowInfo(conn, borrowId);
					if (borrowInfoMap != null) {
						int borrowGroup=Convert.strToInt(borrowInfoMap.get("borrowGroup")+"",0);
						monthRate = Convert.strToDouble(borrowInfoMap.get("monthRate") + "", 0);
						deadline = Convert.strToInt(borrowInfoMap.get("deadline") + "", 0);
						borrowTitle = borrowInfoMap.get("borrowTitle") + "";
						publisher = Convert.strToLong(borrowInfoMap.get("publisher") + "", 0);
						int version = Convert.strToInt(borrowInfoMap.get("version") + "", 0);
						
					// 如果该借款是发布者的标,则发布者不能投标
					borrowOwer = jobTaskDao.queryBorrowOwer(conn, borrowId, userId);
					// 如果该借款是发布者的标,则发布者不能投标
					if(borrowOwer != null){
						continue;
					}else{
						// 查询用户可用余额
						userAmount = jobTaskDao.queryUserGroupAndAmount(conn, remandAmount, userId);
						if(userAmount!=null){
							usableAmount = Convert.strToDouble(userAmount.get("usableSum") + "", 0);
							Map<String,String> usermap=userService.queryUserInfo(userId+"");
							log.info("一键投标 客户信息：可用投资金额usableAmount：=="+usableAmount);
							int userGroup =  Convert.strToInt(userAmount.get("userGroup") + "", 0);
							//比较用户的群组和标的群组 只投自己所属群组的标的
							if(userGroup!=borrowGroup){
								log.info("borrowGroup="+borrowGroup+",userGroup="+userGroup+",群组不匹配，不投标！");
								continue;
							}
							double investAmount = calculateBidAmount(bidAmount,borrowAmount,hasAmount,usableAmount);
							log.info("一键投标  客户信息：投资金额investAmount：=="+investAmount);
							log.info("一键投标  客户信息：每次投资限额bidAmount：=="+bidAmount);
							log.info("一键投标  客户信息：保留投资金额remandAmount：=="+remandAmount);
							log.info("一键投标  客户信息：用户标识：=="+userId);
//							int i = 0;
//							do{
//								i++;//最多循环5次
//								if(bidAmount ==0){
//									bidAmount = investAmount;
//								}
//								if(investAmount < bidAmount){
//									bidAmount = investAmount;
//									if(bidAmount < 100){
//										continue;
//									}
//									Double d = new Double(bidAmount);
//									int t = d.intValue();
//									bidAmount = t / 100;
//									bidAmount = bidAmount * 100;
//								}
//								investAmount = investAmount - bidAmount;
							
							
							
							double realBidAmount = 0.0;
							//单笔投资没有限额或者限额大于投资金额，则真实投资金额等于投资金额；
							if(bidAmount ==0 || bidAmount >= investAmount){
								realBidAmount = investAmount;
							}
							//否则真实投资金额等于单笔限额
							else if(investAmount > bidAmount){
								realBidAmount = bidAmount;
							}
							//真实投资金额小于100，无法投资
							if(realBidAmount < 100){
									continue;
							}else{
								Double d = new Double(realBidAmount);
								int t = d.intValue();
								realBidAmount = t / 100;
								realBidAmount = realBidAmount * 100;
							
								usableAmount = usableAmount - realBidAmount;
							}
							
								// 添加投资记录
								Map<String, String> result = addBorrowInvest(Convert.strToLong(borrowId+"", -1), userId, "", realBidAmount, "",usermap.get("username"), 2, 0);
								String ordId = result.get("ret_ordid");
								log.info("一键投标   投标信息：投标标识ordId：=="+ordId);
								if (Convert.strToInt(result.get("ret"), -1) < 0 || Convert.strToLong(result.get("ret_ordid"), -1) < 0) {
									log.info("一键投标 调接口前添加投资记录失败："+result.get("ret_desc"));
								}else{
								
									String usrCustId = Convert.strToStr(userAmount.get("usrCustId"), "");
									String transAmt = format.format(realBidAmount);
									JSONObject json = new JSONObject();
									json.put("BorrowerCustId", borrowInfoMap.get("usrCustId"));
									json.put("BorrowerAmt", transAmt);
									// 汇付还款总额为借款金额*(1+利率).改为1.00,防止出现手续费过多出现:本次还款金额加上已还金额超过还款总额的情况
									json.put("BorrowerRate", "1.00");
									
									String strjson1=ChinaPnRInterface.autoTender("", ordId, usrCustId, transAmt, "[" + json.toString() + "]");
									JSONObject jobj1 = JSONObject.fromObject(strjson1);
									log.info("一键投标   投标信息：投标标识jobj1.ordId：=="+jobj1.getString("OrdId"));
									log.info("一键投标 接口处理结果："+jobj1);
									int respCode = jobj1.getInt("RespCode");
		//							respCode = 0;
									if(respCode==0){  //如果投标成功
									
										// 成功更新投资记录
										Map<String, String> map = updateBorrowInvest(Convert.strToLong(borrowId+"", -1), Convert.strToLong(jobj1.getString("OrdId"), -1), userId,
												realBidAmount, WebUtil.getWebPath(), usermap.get("username"), 2, 0, "", "", Convert.strToLong(usermap.get("usrCustId"), -1));
										if (Convert.strToInt(map.get("ret"), -1) < 0) {
											log.info("一键投标 失败："+map.get("ret_desc")) ;
										}else{
											// 更新用户自动投标的标的记录
											returnId = financeDao.addAutoBidRecord(conn, userId, borrowId);
										}
									}
								}
//							}
//							while(investAmount > bidAmount);
//							while(investAmount > bidAmount && i<5);
						}
						}
					}
					log.info("一键投标  客户信息：每次投资限额bidAmount：=="+bidAmount);
					if (returnId < 0) {
						conn.rollback();
					}
					conn.commit();
					}
					}
					// 回收对象
//					userMap = null;
					if (returnId < 0) {
						conn.rollback();
					}
					conn.commit();
//				}

			}
			if (returnId < 0) {
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
			biderList = null;
			userList = null;
			userParam = null;
			bider = null;
			userAmount = null;
			borrowOwer = null;
			userBiderList = null;
			borrowInfoMap = null;
			userAmountMap = null;
//			Usermap = null;
			sf = null;
//			noticeMap = null;
			hasInvestMap = null;
			borrowMap = null;
			System.gc();
		}
	}
	/**
	 * @MethodName: calculateBidAmount
	 * @Param: 
	 * @Author: lizg
	 * @Date: 2013-4-15 上午10:34:39
	 * @Return:
	 * @Descb: 计算最后投标金额,(扣除保留金额)
	 * @Throws:
	 */
	private double calculateBidAmount(double bidAmount, double borrowAmount, double hasAmount,double userAmount) {
//		自动投标金额 = MIN （标的剩余金额，客户剩余金额 - 客户保留金额）    /* 取100的整数倍 */
//                投完后客户剩余金额 = 原有客户剩余金额 - 自动投标金额
//                如果 投完后客户剩余金额- 客户保留金额> 100   ，继续循环下一个标的  ；否则循环下一个客户；  
		double realInvestAmount = 0.0;
		realInvestAmount = (borrowAmount-hasAmount>userAmount? userAmount : borrowAmount-hasAmount);
		Double d = new Double(realInvestAmount);
		int t = d.intValue();
		realInvestAmount = t / 100;
		realInvestAmount = realInvestAmount * 100;
		return realInvestAmount;
	}
	
	/*
	 * 查询总投资额
	 */
	public int  querySumInvest(boolean colourLife) throws DataException {
		Connection conn = connectionManager.getConnection();
		try {
            String sql;
            if (colourLife){
                sql="SELECT SUM(investAmount) s from t_invest t1  join t_user t2 on t1.investor=t2.id join t_borrow t3 on t1.borrowId=t3.id  where t2.colorid is not null  and t2.via='' and t3.borrowStatus in(2,3,4,5) and t1.investTime>20140627;";
            }else {
                sql="SELECT SUM(investAmount) s from t_invest";
            }
			DataSet dataSet = MySQL.executeQuery(conn, sql);
			Map<String, String> map=BeanMapUtils.dataSetToMap(dataSet);
			String xString=StringUtils.isBlank(map.get("s")) ? "0" : map.get("s");
            if (StringUtils.isNotBlank(xString)){
                double sum=Double.parseDouble(xString);
                sum=(int)sum/100*100;
                return (int)sum;
            }else {
                return 0;
            }

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/*
	 * 查询总用户数
	 */
	public int  countUser(boolean colourLife) throws DataException {
		Connection conn = connectionManager.getConnection();
		try {
            String sql;
            if (colourLife){
                sql="SELECT count(1) c from t_user where colorid is not null  and via=''";
            }else {
                sql="SELECT count(1) c from t_user";
            }

			DataSet dataSet = MySQL.executeQuery(conn, sql);
			Map<String, String> map=BeanMapUtils.dataSetToMap(dataSet);
			String xString=map.get("c");
			return Integer.parseInt(xString);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	/*
	 * 查询7日内注册用户数
	 */
	public int  countUserIn7Days(boolean colourLife) throws DataException {
		Connection conn = connectionManager.getConnection();
		try {
            String sql;
            if (colourLife){
                sql="SELECT COUNT(1) c from t_user where createTime > DATE_ADD(CURDATE(),INTERVAL -6 DAY) and colorid is not null  and via=''  ORDER BY createTime";
            }else{
                sql="SELECT COUNT(1) c from t_user where createTime > DATE_ADD(CURDATE(),INTERVAL -6 DAY)  ORDER BY createTime";
            }

			DataSet dataSet = MySQL.executeQuery(conn, sql);
			Map<String, String> map=BeanMapUtils.dataSetToMap(dataSet);
			String xString=map.get("c");
			return Integer.parseInt(xString);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
}
