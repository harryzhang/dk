package com.sp2p.task;

import java.io.InputStream;
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
import java.util.Properties;

import com.hehenian.biz.common.activity.IActivityOrderService;
import com.hehenian.biz.common.bid.IAutoBidService;
import com.sp2p.database.Dao;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoader;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IFundConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FinanceDao;
import com.sp2p.dao.FrontMyPaymentDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.dao.RepayMentDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.BorrowManageDao;
import com.sp2p.dao.admin.ShortMassegeDao;
import com.sp2p.database.Dao.Functions;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.ChinaPnRInterfaceService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.ShortMaseegeService;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.WebUtil;

/**
 * @ClassName: JobTaskService.java
 * @Author: gang.lv
 * @Date: 2013-4-11 上午11:14:41
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 定时任务处理服务
 */
public class JobTaskService extends BaseService {
	public static Log log = LogFactory.getLog(JobTaskService.class);
    @Autowired
    private IAutoBidService autoBidService;
    @Autowired
    private IActivityOrderService activityOrderService;
	private JobTaskDao jobTaskDao;
	private SelectedService selectedService;
	private UserService userService;
	private UserIntegralService userIntegralService;
	private FinanceDao financeDao;
	private AccountUsersDao accountUsersDao;
	private BorrowManageDao borrowManageDao;
	private InvestDao investDao;
	private BorrowDao borrowDao;
	private RepayMentDao repayMentDao;
	private FrontMyPaymentDao frontpayDao;
	private FundRecordDao fundRecordDao;
	private UserDao userDao;
	private AssignmentDebtService assignmentDebtService;
	private ShortMassegeDao shortMassegeDao;
	private ShortMaseegeService shortMaseegeService;
	private FinanceService financeService;
	private ChinaPnRInterfaceService chinaPnRInterfaceService;

	public ChinaPnRInterfaceService getChinaPnRInterfaceService() {
		return chinaPnRInterfaceService;
	}

	public void setChinaPnRInterfaceService(
			ChinaPnRInterfaceService chinaPnRInterfaceService) {
		this.chinaPnRInterfaceService = chinaPnRInterfaceService;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public ShortMassegeDao getShortMassegeDao() {
		return shortMassegeDao;
	}

	public void setShortMassegeDao(ShortMassegeDao shortMassegeDao) {
		this.shortMassegeDao = shortMassegeDao;
	}

	public ShortMaseegeService getShortMaseegeService() {
		return shortMaseegeService;
	}

	public void setShortMaseegeService(ShortMaseegeService shortMaseegeService) {
		this.shortMaseegeService = shortMaseegeService;
	}

	public AccountUsersDao getAccountUsersDao() {
		return accountUsersDao;
	}

	public BorrowManageDao getBorrowManageDao() {
		return borrowManageDao;
	}

	public InvestDao getInvestDao() {
		return investDao;
	}

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public RepayMentDao getRepayMentDao() {
		return repayMentDao;
	}

	public FrontMyPaymentDao getFrontpayDao() {
		return frontpayDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public AssignmentDebtService getAssignmentDebtService() {
		return assignmentDebtService;
	}

	public void setRepayMentDao(RepayMentDao repayMentDao) {
		this.repayMentDao = repayMentDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setAssignmentDebtService(AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public void setFrontpayDao(FrontMyPaymentDao frontpayDao) {
		this.frontpayDao = frontpayDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public void setBorrowManageDao(BorrowManageDao borrowManageDao) {
		this.borrowManageDao = borrowManageDao;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public JobTaskDao getJobTaskDao() {
		return jobTaskDao;
	}

	public void setJobTaskDao(JobTaskDao jobTaskDao) {
		this.jobTaskDao = jobTaskDao;
	}

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserIntegralService getUserIntegralService() {
		return userIntegralService;
	}

	public void setUserIntegralService(UserIntegralService userIntegralService) {
		this.userIntegralService = userIntegralService;
	}

	/**
	 * VIP会费处理
	 * 
	 * @throws DataException
	 */
	public void autoDeductedVIPFee() throws SQLException, DataException {
		Connection conn = MySQL.getConnection();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_auto_pastvipfee(conn, ds, outParameterValues, 0, "");
			Procedures.p_auto_firstvip(conn, ds, outParameterValues, 0, "");
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

	}

	/**
	 * add by houli 当用户首次成为vip并且有会费余额，则立即进行会费扣除（用户申请会员时引用）
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Long beToVip(Long userId, Map<String, Object> platformCostMap) throws SQLException {
		Connection conn = Database.getConnection();
		Map<String, String> noticeMap = new HashMap<String, String>();
		Map<String, String> map = null;
		Map<String, String> vipFeeMap = null;
		Map<String, String> userAmountMap = null;
		Map<String, String> userSumMap = null;
		String username = "";
		// 模板
		Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext()
				.getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		try {
			map = jobTaskDao.queryFirstVipById(conn, userId);
			if (map == null || map.size() <= 0) {// 用户不是首次成为vip
				return -1L;
			}
			double vipFee = Convert.strToDouble(platformCostMap.get(IAmountConstants.VIP_FEE_RATE) + "", 0);
			// 如果代扣的VIP用户账户有钱就进行VIP会费扣除
			userAmountMap = jobTaskDao.queryUserHasVipFee(conn, userId, vipFee);
			if (userAmountMap != null && userAmountMap.size() > 0 && vipFee > 0) {
				username = userAmountMap.get("userAmountMap") + "";
				// 扣除VIP会费
				// jobTaskDao.deductedUserVipFee(conn, userId, vipFee);
				// 添加VIP会费扣除记录
				// jobTaskDao.addVipFeeRecord(conn, userId, vipFee);
				// 查询投资后的账户金额
				userSumMap = financeDao.queryUserAmountAfterHander(conn, userId);
				if (userSumMap == null) {
					userSumMap = new HashMap<String, String>();
				}
				double usableSum = Convert.strToDouble(userSumMap.get("usableSum") + "", 0);
				double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum") + "", 0);
				double forPI = Convert.strToDouble(userSumMap.get("forPI") + "", 0);
				// 添加资金流动记录
				// fundRecordDao.addFundRecord(conn, userId, "VIP会员续费", vipFee,
				// usableSum, freezeSum, forPI, -1, "扣除VIP会员费", 0.0, vipFee, -1,
				// -1, 804,
				// 0.0);

				String addIP = Convert.strToStr(userSumMap.get("lastIP"), null);
				// accountUsersDao.addAccountUsers(conn,
				// IFundConstants.VIP_RENEW, userId, new BigDecimal(vipFee),
				// -1L, "扣除VIP会员费,续费成功", addIP);

				// 站内信
				String informTemplate = informTemplateMap.get(IInformTemplateConstants.VIP_SUCCESS_XU).toString();
				informTemplate = informTemplate.replace("vipFee", vipFee + "");
				// 短信
				String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_VIP_SUCCESS_XU).toString();
				s_informTemplate = s_informTemplate.replace("username", username);
				s_informTemplate = s_informTemplate.replace("vipFee", vipFee + "");
				// 邮件
				String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_VIP_SUCCESS_XU).toString();
				e_informTemplate = e_informTemplate.replace("vipFee", vipFee + "");
				noticeMap.put("mail", informTemplate);// 站内信
				noticeMap.put("note", s_informTemplate);// 短信
				noticeMap.put("email", e_informTemplate);// 邮件
				// 发送通知
				// selectedService.sendNoticeMSG(conn, userId, "VIP会员成功续费",
				// noticeMap, IConstants.NOTICE_MODE_5);
			}
			conn.commit();
			return 1L;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
			noticeMap = null;
			map = null;
			vipFeeMap = null;
			userAmountMap = null;
			userSumMap = null;
			System.gc();
		}
		return -1L;
	}

	/**
	 * @MethodName: pr_inviteFriendsReward
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-11 下午01:29:55
	 * @Return:
	 * @Descb: 好友奖励发放
	 * @Throws:
	 */
	public void inviteFriendsReward() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		Map<String, String> userSumMap = null;
		Map<String, String> riskMap = null;
		Map<String, String> noticeMap = new HashMap<String, String>();
		List<Map<String, Object>> friendsRewardList = null;
		Map<String, String> fmap = null;
		long id = -1;
		// 奖励金额
		double money = 0;
		// 用户Id
		long userId = -1;
		String username = "";
		// 模板
		Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext()
				.getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);

		try {
			// 清除异常数据(奖励的用户为小于1的用户)
			jobTaskDao.clearExceptionDate(conn);
			// 处理好友奖励
			friendsRewardList = jobTaskDao.queryFriendsReward(conn);
			for (Map<String, Object> vipMap : friendsRewardList) {
				id = Convert.strToLong(vipMap.get("id") + "", -1);
				money = Convert.strToLong(vipMap.get("money") + "", -1);
				userId = Convert.strToLong(vipMap.get("userId") + "", -1);
				username = vipMap.get("username") + "";
				// 查询风险保障金余额
				riskMap = jobTaskDao.queryRiskBalance(conn);
				if (riskMap == null) {
					riskMap = new HashMap<String, String>();
				}
				double riskBalance = 1000000 + Convert.strToDouble(riskMap.get("riskBalance") + "", 0);
				// 扣除风险保障金
				jobTaskDao.spendingRiskAmount(conn, riskBalance, money, -1, id, "好友邀请奖励");
				// 更新已奖励状态为已奖励
				jobTaskDao.updateRewardStatus(conn, id, userId);
				// 邀请奖励发给邀请人
				jobTaskDao.addUserAmount(conn, userId, money);
				// 查询投资后的账户金额
				userSumMap = financeDao.queryUserAmountAfterHander(conn, userId);
				if (userSumMap == null) {
					userSumMap = new HashMap<String, String>();
				}
				double usableSum = Convert.strToDouble(userSumMap.get("usableSum") + "", 0);
				double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum") + "", 0);
				double forPI = Convert.strToDouble(userSumMap.get("forPI") + "", 0);
				// 发送通知
				// --------------add by houli
				fmap = jobTaskDao.queryFriendInfo(conn, id, userId);
				String friendName = "";
				if (fmap != null) {
					friendName = fmap.get("username");
				}
				// 添加资金流动记录
				fundRecordDao.addFundRecord(conn, userId, "好友邀请奖励", money, usableSum, freezeSum, forPI, -1, "您邀请的用户<a href='userMeg.do?id=" + userId + "' target='_blank'>【"
						+ friendName + "】</a>已成为VIP会员,在此奖励￥" + money + "元,再接再厉!", money, 0.0, -1, -1, 251, 0.0);

				// ----------------/
				// 添加记录到新表
				String addIP = Convert.strToStr(userSumMap.get("lastIP"), null);
				accountUsersDao.addAccountUsers(conn, IFundConstants.RECOMMAND_FRIENDS, userId, new BigDecimal(money), -1L, "您邀请的用户<a href='userMeg.do?id=" + userId
						+ "' target='_blank'>【" + friendName + "】</a>已成为VIP会员,在此奖励￥" + money + "元,再接再厉!", addIP);

				// ------------------/
				// 模板通知
				// 站内信
				String informTemplate = informTemplateMap.get(IInformTemplateConstants.GOOD_INVITATION).toString();
				informTemplate = informTemplate.replace("friendName", friendName);
				informTemplate = informTemplate.replace("money", money + "");
				// 短信
				String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_GOOD_INVITATION).toString();
				s_informTemplate = s_informTemplate.replace("friendName", friendName);
				s_informTemplate = s_informTemplate.replace("username", username);
				s_informTemplate = s_informTemplate.replace("money", money + "");
				// 邮件
				String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_GOOD_INVITATION).toString();
				e_informTemplate = e_informTemplate.replace("friendName", friendName);
				e_informTemplate = e_informTemplate.replace("money", money + "");
				noticeMap.put("mail", informTemplate); // 站内信
				noticeMap.put("email", e_informTemplate);// 邮件
				noticeMap.put("note", s_informTemplate);// 短信
				selectedService.sendNoticeMSG(conn, userId, "好友邀请奖励", noticeMap, IConstants.NOTICE_MODE_5);
				// 回收对象
				vipMap = null;
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
			userSumMap = null;
			riskMap = null;
			noticeMap = null;
			friendsRewardList = null;
			fmap = null;
			System.gc();
		}
	}

	/**
	 * @MethodName: updateOverDueRepayment
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-11 下午02:42:50
	 * @Return:
	 * @Descb: 更新逾期的还款
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void updateOverDueRepayment() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		DecimalFormat df = new DecimalFormat("#0.00");
		List<Map<String, Object>> overDueRepaymentList = null;
		String date = sf.format(new Date());
		long borrowId = -1L;
		long id = -1;
		// 剩余本金
		double principalBalance = 0;
		// 逾期罚息
		double lateFee = 0;
		// 逾期天数
		int overdueDay = 0;
		// 查询借款信息得到借款时插入的平台收费标准
		Map<String, String> map = null;
		String feelog = "";
		Map<String, Double> feeMap = null;
		double overdueFeeRate = 0;
		try {
			overDueRepaymentList = jobTaskDao.queryOverDueRepayment(conn, date);
			for (Map<String, Object> overDueMap : overDueRepaymentList) {
				id = Convert.strToLong(overDueMap.get("id") + "", -1);
				borrowId = Convert.strToLong(overDueMap.get("borrowId") + "", 0);
				principalBalance = Convert.strToDouble(overDueMap.get("principalBalance") + "", 0);
				overdueDay = Convert.strToInt(overDueMap.get("overdueDay") + "", 0);
				map = borrowManageDao.queBorrowInfo(conn, borrowId);
				if (map != null) {
					// 得到收费标准的json代码
					feelog = Convert.strToStr(map.get("feelog"), "");
					feeMap = (Map<String, Double>) JSONObject.toBean(JSONObject.fromObject(feelog), HashMap.class);
					overdueFeeRate = Convert.strToDouble(feeMap.get(IAmountConstants.OVERDUE_FEE_RATE) + "", 0);
				} else
					overdueFeeRate = 0;
				// 不符合条件的情况，将逾期天数重置为0
				if (overdueDay < 0)
					overdueDay = 0;
				// 合和年逾期三天内不算利息 ,,计算罚息=剩余本金*逾期天数*罚息利率
				if (overdueDay < 4)
					lateFee = 0;
				else
					lateFee = principalBalance * (overdueDay - 3) * overdueFeeRate;

				lateFee = Double.valueOf(df.format(lateFee));
				// 更新逾期还款
				if (overdueDay > 0)
					jobTaskDao.updateOverDueRepayment(conn, id, lateFee, overdueDay, date);
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals(e);
			conn.rollback();
		} finally {
			conn.close();
			System.gc();
		}
	}

	/**
	 * @MethodName: autoDeductedXLFee
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-12 下午05:05:24
	 * @Return:
	 * @Descb: 自动扣除学历认证费用
	 * @Throws:
	 */
	public void autoDeductedXLFee() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		Map<String, String> userSumMap = null;
		Map<String, String> noticeMap = new HashMap<String, String>();
		List<Map<String, Object>> xlFeeList = null;
		// 扣费id
		long costId = -1;
		// 用户Id
		long userId = -1;
		// 学历认证费用
		double freeEducation = 0;
		// 用户可用金额
		double useableAmount = 0;
		String username = "";
		Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext()
				.getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		try {
			xlFeeList = jobTaskDao.queryDeductedXLFee(conn);
			for (Map<String, Object> xlFeeMap : xlFeeList) {
				costId = Convert.strToLong(xlFeeMap.get("id") + "", -1);
				userId = Convert.strToLong(xlFeeMap.get("userId") + "", 0);
				freeEducation = Convert.strToDouble(xlFeeMap.get("freeEducation") + "", 0);
				username = xlFeeMap.get("username") + "";
				useableAmount = Convert.strToDouble(xlFeeMap.get("usableSum") + "", 0);
				// 如果扣费费用和用户有钱就进行扣费处理
				if (freeEducation > 0 && useableAmount > freeEducation) {
					// 更新费用扣除状态
					jobTaskDao.updateXLFeeStatus(conn, costId);
					// 扣除学历认证费用
					jobTaskDao.deductedXLFee(conn, userId, freeEducation);
					// 查询投资后的账户金额
					userSumMap = financeDao.queryUserAmountAfterHander(conn, userId);
					if (userSumMap == null) {
						userSumMap = new HashMap<String, String>();
					}
					double usableSum = Convert.strToDouble(userSumMap.get("usableSum") + "", 0);
					double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum") + "", 0);
					double forPI = Convert.strToDouble(userSumMap.get("forPI") + "", 0);
					// 添加资金流动记录
					fundRecordDao.addFundRecord(conn, userId, "学历认证费", freeEducation, usableSum, freezeSum, forPI, -1, "管理员以对您的学历进行了审核，本次产生的费用为：￥" + freeEducation + "元", 0.0,
							freeEducation, -1, -1, 802, 0.0);

					// ------------资金记录 新表---------------/
					String addIP = Convert.strToStr(userSumMap.get("lastIP"), null);
					accountUsersDao.addAccountUsers(conn, IFundConstants.DEDUCT_EDUCATION, userId, new BigDecimal(userId), -1L, "管理员以对您的学历进行了审核，本次产生的费用为：￥" + freeEducation + "元",
							addIP);

					// 消息模版 学历认证成功
					// 站内信
					String informTemplate = informTemplateMap.get(IInformTemplateConstants.APPROVE_EDU_SUCCESS).toString();
					informTemplate = informTemplate.replace("freeEducation", freeEducation + "");
					// 短信
					String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_APPROVE_EDU_SUCCESS).toString();
					s_informTemplate = s_informTemplate.replace("username", username);
					s_informTemplate = s_informTemplate.replace("freeEducation", freeEducation + "");
					// 邮件
					String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_APPROVE_EDU_SUCCESS).toString();
					e_informTemplate = e_informTemplate.replace("freeEducation", freeEducation + "");
					// 站内信
					noticeMap.put("mail", informTemplate);
					// 邮件
					noticeMap.put("email", e_informTemplate);
					// 短信
					noticeMap.put("note", s_informTemplate);
					/*
					 * //站内信 noticeMap.put("mail",
					 * "管理员以对您的学历进行了审核，本次产生的费用为：￥"+freeEducation+"元"); //邮件
					 * noticeMap
					 * .put("email","管理员以对您的学历进行了审核，本次产生的费用为：￥"+freeEducation
					 * +"元"); //短信 noticeMap.put("note",
					 * "尊敬的"+username+":\n    管理员以对您的学历进行了审核，本次产生的费用为：￥"
					 * +freeEducation+"元");
					 */
					// 发送通知
					selectedService.sendNoticeMSG(conn, userId, "学历认证费", noticeMap, IConstants.NOTICE_MODE_5);
					// 回收对象
					xlFeeMap = null;
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
			userSumMap = null;
			noticeMap = null;
			xlFeeList = null;
			System.gc();
		}
	}

	/**
	 * @MethodName: autoBid
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-14 下午11:50:51
	 * @Return:
	 * @Descb: 自动投标
	 * @Throws:
	 */
	public void autoBid() throws SQLException, DataException {
		log.info("---------------老版自动投标有开始跑了.........");
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		if (stackTraceElements!=null&&stackTraceElements.length>0) {
			for (StackTraceElement stackTraceElement : stackTraceElements) {
				log.info("-------老版自动投标堆栈：class-"+stackTraceElement.getClassName()+",method-"+stackTraceElement.getMethodName());
			}
			
		}
		/*Connection conn = Database.getConnection();
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
		// 用户Id
		long userId = -1;
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
			// 遍历所有的符合条件进度低于100%的招标中的借款
			for (Map<String, Object> biderMap : biderList) {
				borrowId = Convert.strToInt(biderMap.get("id") + "", -1);
				borrowAmount = Convert.strToDouble(biderMap.get("borrowAmount") + "", 0);
				hasAmount = Convert.strToDouble(biderMap.get("hasInvestAmount") + "", 0);
				isDayThe = Convert.strToInt(biderMap.get("isDayThe") + "", -1);
				// 查询符合自动投标的用户
				userList = jobTaskDao.queryAutoBidUser(conn);
				// 所有符合条件的用户排队对每条借款进行自动投标
				for (Map<String, Object> userMap : userList) {
					userId = Convert.strToInt(userMap.get("userId") + "", -1);
					// 如果该借款是发布者的标,则发布者不能投标,用户自动排队到后面
					borrowOwer = jobTaskDao.queryBorrowOwer(conn, borrowId, userId);
					if (borrowOwer != null) {
						// 是发布者的标,退出本次投标,用户自动排队到后面
						jobTaskDao.updateUserAutoBidTime(conn, userId);
					} else {
						userParam = jobTaskDao.queryUserBidParam(conn, userId);
						// 当用户设置了自动投标参数
						if (userParam != null) {
 							userBiderList = jobTaskDao.queryUserBider(conn, borrowId, userId);
							// 用户已经投标的标的不能再自动投标
							if (userBiderList != null && userBiderList.size() > 0) {
								// 已经投标,用户自动排队到后面
								jobTaskDao.updateUserAutoBidTime(conn, userId);
							} else {
								rateStart = Convert.strToDouble(userParam.get("rateStart") + "", 0);
								rateEnd = Convert.strToDouble(userParam.get("rateEnd") + "", 0);
								deadLineStart = Convert.strToInt(userParam.get("deadlineStart") + "", 0);
								deadLineEnd = Convert.strToInt(userParam.get("deadlineEnd") + "", 0);
								creditStart = Convert.strToInt(userParam.get("creditStart") + "", 0);
								creditEnd = Convert.strToInt(userParam.get("creditEnd") + "", 0);
								bidAmount = Convert.strToDouble(userParam.get("bidAmount") + "", 0);
								remandAmount = Convert.strToDouble(userParam.get("remandAmount") + "", 0);

								// 用户设置的借款类型,合和年没有此项限制,任何类型都可投资 if (borrowWay.contains(way)) {
								// 根据用户投标参数获取投标的标的
								bider = jobTaskDao.queryBiderByParam(conn, rateStart, rateEnd, deadLineStart, deadLineEnd, creditStart, creditEnd, borrowId, isDayThe);
								// 找到了符合自动投标的标的
								if (bider != null) {
									// 计算投标金额
									bidAmount = calculateBidAmount(bidAmount, borrowAmount, hasAmount);
									if (bidAmount > 0) {
										// 查询用户可用余额是否足够
										userAmount = jobTaskDao.queryUserAmount(conn, remandAmount, userId);
										if (userAmount != null) {
											// 获取用户减掉预留金额后的可用金额
											usableAmount = Convert.strToDouble(userAmount.get("usableSum") + "", 0);
											if (usableAmount >= bidAmount && usableAmount > 0) {
												// 查询借款的状态,借款未达到100%且处于招标中
												borrowMap = jobTaskDao.queryBorrow(conn, borrowId);
												if (borrowMap != null) {
													borrowAmount = Convert.strToDouble(borrowMap.get("borrowAmount") + "", 0);
													hasAmount = Convert.strToDouble(borrowMap.get("hasInvestAmount") + "", 0);
													bidAmount = calculateBidAmount(bidAmount, borrowAmount, hasAmount);
													// 满足投标条件,进行扣费处理 查询借款的基础信息
													borrowInfoMap = jobTaskDao.queryBorrowInfo(conn, borrowId);
													if (borrowInfoMap != null) {
														monthRate = Convert.strToDouble(borrowInfoMap.get("monthRate") + "", 0);
														deadline = Convert.strToInt(borrowInfoMap.get("deadline") + "", 0);
														borrowTitle = borrowInfoMap.get("borrowTitle") + "";
														publisher = Convert.strToLong(borrowInfoMap.get("publisher") + "", 0);
														int version = Convert.strToInt(borrowInfoMap.get("version") + "", 0);
														
															// 查询已投资总额是否小于等于借款总额，否则是无效投标
															hasInvestMap = financeDao.queryHasInvest(conn, borrowId);
															if (hasInvestMap != null && hasInvestMap.size() > 0) {
																Map<String,String> usermap=userService.queryUserInfo(userId+"");
																// 添加投资记录
																//returnId = financeDao.addInvest(conn, bidAmount, bidAmount, monthRate, userId, userId, borrowId, deadline, 2);
																Map<String, String> result = financeService.addBorrowInvest(Convert.strToLong(borrowId+"", -1), userId, "", bidAmount, "",usermap.get("username"), 2, 0);
																String ordId = result.get("ret_ordid");
																if (Convert.strToInt(result.get("ret"), -1) < 0 || Convert.strToLong(result.get("ret_ordid"), -1) < 0) {
																	log.info("自动投标调接口前添加投资记录失败："+result.get("ret_desc"));
																}else{
																
																String usrCustId = Convert.strToStr(userAmount.get("usrCustId"), "");
																String transAmt = format.format(bidAmount);
																JSONObject json = new JSONObject();
																json.put("BorrowerCustId", borrowInfoMap.get("usrCustId"));
																json.put("BorrowerAmt", transAmt);
																// 汇付还款总额为借款金额*(1+利率).改为1.00,防止出现手续费过多出现:本次还款金额加上已还金额超过还款总额的情况
																json.put("BorrowerRate", "1.00");
																
																String strjson1=ChinaPnRInterface.autoTender("", ordId, usrCustId, transAmt, "[" + json.toString() + "]");
																JSONObject jobj1 = JSONObject.fromObject(strjson1);
																
																log.info("自动投标接口处理结果："+jobj1);
																if(jobj1.getInt("RespCode")==0){  //如果投标成功
																
																	// 成功更新投资记录
																	Map<String, String> map = financeService.updateBorrowInvest(Convert.strToLong(borrowId+"", -1), Convert.strToLong(jobj1.getString("OrdId"), -1), userId,
																			bidAmount, WebUtil.getWebPath(), usermap.get("username"), 2, 0, "", "", Convert.strToLong(usermap.get("usrCustId"), -1));
																	if (Convert.strToInt(map.get("ret"), -1) < 0) {
																		log.info("自动投标失败："+map.get("ret_desc")) ;
																	}else{
																		// 更新用户自动投标的标的记录
																		returnId = financeDao.addAutoBidRecord(conn, userId, borrowId);
																	}
															  }else{
																  //失败删除投资记录
																  chinaPnRInterfaceService.deleteBorrowInvest(ordId + "");
															  }
															}
														  } else {
																returnId = -1;
													    }
													}
													// 投标完成,用户自动排队到后面
													jobTaskDao.updateUserAutoBidTime(conn, userId);
												} else {
													// 借款状态已改变,投标失败,用户自动排队到后面
													jobTaskDao.updateUserAutoBidTime(conn, userId);
												}
											} else {
												// 用户的可用余额不够投标，投标失败,用户自动排队到后面
												jobTaskDao.updateUserAutoBidTime(conn, userId);
											}
										} else {
											// 用户的可用余额不足，投标失败,用户自动排队到后面
											jobTaskDao.updateUserAutoBidTime(conn, userId);
										}
									} else {
										// 投标金额为0,投标失败,用户自动排队到后面
										jobTaskDao.updateUserAutoBidTime(conn, userId);
									}
								} else {
									// 没有找到符合用的标的,用户自动排队到后面
									jobTaskDao.updateUserAutoBidTime(conn, userId);
								}
								// } else {
								// // 不符合设置的借款类型,用户自动排队到后面
								// jobTaskDao.updateUserAutoBidTime(conn,
								// userId);
								// }

							}
						}
					}
					// 回收对象
					userMap = null;
					if (returnId < 0) {
						conn.rollback();
					}
					conn.commit();
				}
				// 回收对象
				biderMap = null;
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
		}*/
	}

	/**
	 * @MethodName: calculateBidAmount
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-15 上午10:34:39
	 * @Return:
	 * @Descb: 计算最后投标金额,(扣除保留金额)
	 * @Throws:
	 */
	private double calculateBidAmount(double bidAmount, double borrowAmount, double hasAmount) {
		double maxBidAmount = borrowAmount * 0.2;
		double schedule = hasAmount / borrowAmount;
		double invesAmount = 0;
		if (schedule < 0.9500) {
			while (bidAmount > maxBidAmount) {
				bidAmount = bidAmount - 50;
			}

			do {
				invesAmount = hasAmount + bidAmount;
				schedule = invesAmount / borrowAmount;
				if (schedule > 0.9500) {
					bidAmount = bidAmount - 50;
				}
			} while (schedule > 0.9500);
		}
		return bidAmount;
	}

	/**
	 * @MethodName: updateOverDueInvestRepayment
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-6-2 下午10:25:20
	 * @Return:
	 * @Descb: 更新逾期投资还款记录
	 * @Throws:
	 */
	public void updateOverDueInvestRepayment() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		DecimalFormat df = new DecimalFormat("#0.00");
		List<Map<String, Object>> overDueRepaymentList = null;
		String date = sf.format(UtilDate.getYesterDay());
		long id = -1;
		// 应还本金
		double stillPrincipal = 0;
		// 应还利息
		// double stillInterest = 0;
		// 逾期罚息
		double lateFee = 0;
		long repayId = 0;
		int lateDay = 0;
		int isLate = 0;
		int borrowWay = 0;
		// 查询借款信息得到借款时插入的平台收费标准
		// Map<String, String> map = null;
		// String feelog = "";
		// Map<String, Double> feeMap = null;
		// 得到收费标准的说明信息
		// double overdueFeeRate = 0;
		try {
			overDueRepaymentList = jobTaskDao.queryOverDueInvestRepayment(conn, date);
			for (Map<String, Object> overDueMap : overDueRepaymentList) {
				// long borrowId = Convert.strToLong(overDueMap.get("borrowId")
				// + "", 0);
				// map = borrowManageDao.queryBorrowInfo(conn, borrowId);
				// 得到收费标准的json代码
				// feelog = Convert.strToStr(map.get("feelog"), "");
				// feeMap = (Map<String, Double>)
				// JSONObject.toBean(JSONObject.fromObject(feelog),
				// HashMap.class);
				// overdueFeeRate =
				// Convert.strToDouble(feeMap.get(IAmountConstants.OVERDUE_FEE_RATE)
				// + "", 0);

				// 计算罚息
				id = Convert.strToLong(overDueMap.get("id") + "", -1);
				repayId = Convert.strToLong(overDueMap.get("repayId") + "", -1);
				lateDay = Convert.strToInt(overDueMap.get("lateDay") + "", 0);
				isLate = Convert.strToInt(overDueMap.get("isLate") + "", 1);
				stillPrincipal = Convert.strToDouble(overDueMap.get("recivedPrincipal") + "", 0);
				// stillInterest =
				// Convert.strToDouble(overDueMap.get("recivedInterest") + "",
				// 0);
				double lateFI = Convert.strToDouble(overDueMap.get("lateFI") + "", 0);// 还款人罚息
				double principal = Convert.strToDouble(overDueMap.get("principal") + "", 0);// 还款人本期支付的本金

				lateFee = lateFI * stillPrincipal / principal;
				lateFee = Double.valueOf(df.format(lateFee));
				borrowWay = Convert.strToInt(overDueMap.get("borrowWay") + "", 0);
				// 更新逾期还款
				// 6 为流转标 不处理
				if (borrowWay != 6) {
					jobTaskDao.updateOverDueInvestRepayment(conn, id, repayId, lateFee, lateDay, isLate);
				}
				// 回收对象
				overDueMap = null;
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
			sf = null;
			df = null;
			overDueRepaymentList = null;
			System.gc();
		}
	}

	/**
	 * 合和年 (代偿还款)
	 * 
	 * @throws Exception
	 */
	public void autoRepayment() throws Exception {
		Connection conn = null;
		try {
			conn = MySQL.getConnection();
			conn.setAutoCommit(false);
			List<Map<String, Object>> repaymentList = repayMentDao.queryNeedRepaymentInfo(conn);
			if (repaymentList == null)
				return;
			long result = -1L;
			for (Map<String, Object> overDueMap : repaymentList) {
				// 逾期3天后,网站代偿,更新逾期还款状态(还款状态不变,网站代偿更新)
				int lateDay = Convert.strToInt(overDueMap.get("lateDay") + "", 0);
				Long repayId = Convert.strToLong(overDueMap.get("repayId") + "", -1);
				if (lateDay >= 3) {
					result = jobTaskDao.updateOverDueRepayment(conn, repayId);
					if (result < 0)
						throw new Exception();
				}
				long borrowId = Convert.strToLong(overDueMap.get("borrowId") + "", 0);
				Double stillPrincipal = Convert.strToDouble(overDueMap.get("stillPrincipal") + "", 0);
				Double stillInterest = Convert.strToDouble(overDueMap.get("stillInterest") + "", 0);
				Double repayAmount = stillInterest + stillPrincipal;
				String repayPeriod = Convert.strToStr(overDueMap.get("repayPeriod") + "", "");
				// 更新投资人收款纪录
				String basePath = WebUtil.getWebPath();
				result = jobTaskDao.updateInvestRepayment(conn, borrowId, repayId, repayPeriod, stillPrincipal, stillInterest, basePath);
				if (result <= 0)
					throw new Exception();
				// 添加还款记录 ,自动还款操作填写admin
				result = jobTaskDao.addRepaymentRecord(conn, repayId, repayAmount, -1);
				if (result < 0)
					throw new Exception();
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			log.error(e);
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * @throws SQLException
	 * @MethodName: AutomaticPayment
	 * @Descb: 流转标 自动还款
	 */
	@SuppressWarnings("unchecked")
	public void automaticPayment() throws SQLException {
		double investFeeRate = 0;
		// 处理流转标状态为认购中或回购中的借款
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> circulationList = null;
		List<Map<String, Object>> investList = null;
		Map<String, String> noticeMap = new HashMap<String, String>();
		DecimalFormat df = new DecimalFormat("#0.00");
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		Map<String, String> userSumMap = null;
		// 借款人
		long borrower = -1;
		// 投资人
		long investor = -1;
		// 借款id
		long borrowId = -1;
		// 投资id
		long investId = -1;
		// 返回值
		long returnId = -1;
		// 应收本金
		double recivedPrincipal = 0;
		// 应收利息
		double recievedInterest = 0;
		// 应收本息
		double recivedPI = 0;
		// 借款标题
		String borrowTitle = "";
		// 实得还款金额
		double hasSum = 0;
		// 投资管理费
		double mFee = 0;
		// 投资人名称
		String username = "";
		// 回购期数
		int hasRepoNumber = 0;
		// 最小流转单位
		double smallestFlowUnit = 0;
		// 实际投资金额
		double realAmount = 0;
		int repayId = -1;
		long oriInvestor = -1; // 原始投资人
		String basePath = WebUtil.getWebPath();
		// 查询借款信息得到借款时插入的平台收费标准
		String feelog = "";
		Map<String, Double> feeMap = null;
		Map<String, String> flowMap = null;
		// 模板
		Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext()
				.getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		try {
			// 查询状态为认购中或回购中的借款或者借款人还完的借款
			circulationList = borrowDao.queryCirculationBorrow(conn);
			for (Map<String, Object> circulationMap : circulationList) {
				borrowId = Convert.strToLong(circulationMap.get("id") + "", -1);
				borrowTitle = circulationMap.get("borrowTitle") + "";
				borrower = Convert.strToLong(circulationMap.get("publisher") + "", -1);
				smallestFlowUnit = Convert.strToDouble(circulationMap.get("smallestFlowUnit") + "", 0);
				repayId = Convert.strToInt(circulationMap.get("repayId") + "", -1);
				// 得到收费标准的json代码
				feelog = circulationMap.get("feelog") + "";
				feeMap = (Map<String, Double>) JSONObject.toBean(JSONObject.fromObject(feelog), HashMap.class);
				investFeeRate = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE) + "", 0);
				// 查询流转标借款待收款投资人
				investList = investDao.queryInvestorByBorrowId(conn, borrowId);
				for (Map<String, Object> investMap : investList) {
					investId = Convert.strToLong(investMap.get("id") + "", -1);
					investor = Convert.strToLong(investMap.get("investor") + "", -1);
					oriInvestor = Convert.strToLong(investMap.get("oriInvestor") + "", -1);
					username = investMap.get("username") + "";
					realAmount = Convert.strToDouble(investMap.get("realAmount") + "", 0);
					recivedPrincipal = Convert.strToDouble(investMap.get("recivedPrincipal") + "", 0);
					recievedInterest = Convert.strToDouble(investMap.get("recievedInterest") + "", 0);
					recivedPI = recivedPrincipal + recievedInterest;
					mFee = recievedInterest * investFeeRate;
					mFee = Double.valueOf(df.format(mFee));
					hasSum = recivedPI - mFee;
					// 查询流转标还款记录ID
					flowMap = investDao.queryFlowMap(conn, investId);
					if (flowMap == null) { // 为空则该条投资没有还
						// 更新流转标还款状态为已还款
						returnId = investDao.updateInvestRepayStatus(conn, investId);
						// 更新t_invest_repayment的状态为已还款
						returnId = investDao.updateInvestRepaymentStatus(conn, investId);
						// 更新流转标投资 辅助表
						returnId = investDao.addFlowRepayment(conn, investId);
						// 更新t_invest借款人已收本息
						returnId = investDao.updateInvesthasPrincipalAndhasInterest(conn, investId, recivedPrincipal, recievedInterest);
						// 更新invest
						investDao.updateInvestRepayment(conn, repayId, investId, investor, 0, oriInvestor, 2);
						if (returnId > 0) {
							// 更新借款已回购份数
							hasRepoNumber = (int) (realAmount / smallestFlowUnit);
							borrowDao.updateHasRepoNumber(conn, borrowId, hasRepoNumber);
							String informTemplate = informTemplateMap.get(IInformTemplateConstants.RECOVER_ADVANCE_SUCCESS).toString();
							informTemplate = informTemplate.replace("title", borrowTitle + "");
							informTemplate = informTemplate.replace("[repayPeriod]", "1/1");
							informTemplate = informTemplate.replace("[paymentModeStr]", "");
							informTemplate = informTemplate.replace("[recivedSum]", recivedPI + "");
							informTemplate = informTemplate.replace("[hasP]", recivedPrincipal + "");
							informTemplate = informTemplate.replace("[hasI]", recievedInterest + "");
							informTemplate = informTemplate.replace("[mFee]", mFee + "");
							informTemplate = informTemplate.replace("[msFee]", df.format(hasSum) + "");
							informTemplate = informTemplate.replace("[hasLFI]", "0");
							informTemplate = informTemplate.replace("[paymentModeStr]", "还款方式：[一次性还款]<br/>");
							// 短信
							String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_RECOVER_ADVANCE_SUCCESS).toString();
							s_informTemplate = s_informTemplate.replace("username", username);
							s_informTemplate = s_informTemplate.replace("title", borrowTitle + "");
							s_informTemplate = s_informTemplate.replace("[repayPeriod]", "1/1");
							s_informTemplate = s_informTemplate.replace("[paymentModeStr]", "");
							s_informTemplate = s_informTemplate.replace("[recivedSum]", recivedPI + "");
							s_informTemplate = s_informTemplate.replace("[hasP]", recivedPrincipal + "");
							s_informTemplate = s_informTemplate.replace("[hasI]", recievedInterest + "");
							s_informTemplate = s_informTemplate.replace("[mFee]", mFee + "");
							s_informTemplate = s_informTemplate.replace("[msFee]", df.format(hasSum) + "");
							s_informTemplate = s_informTemplate.replace("[paymentModeStr]", "还款方式：[一次性还款]<br/>");
							s_informTemplate = s_informTemplate.replace("[hasLFI]", "0");
							// 邮件
							String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_RECOVER_ADVANCE_SUCCESS).toString();
							e_informTemplate = e_informTemplate.replace("title", borrowTitle + "");
							e_informTemplate = e_informTemplate.replace("[repayPeriod]", "1/1");
							e_informTemplate = e_informTemplate.replace("[paymentModeStr]", "");
							e_informTemplate = e_informTemplate.replace("[recivedSum]", recivedPI + "");
							e_informTemplate = e_informTemplate.replace("[hasP]", recivedPrincipal + "");
							e_informTemplate = e_informTemplate.replace("[hasI]", recievedInterest + "");
							e_informTemplate = e_informTemplate.replace("[mFee]", mFee + "");
							e_informTemplate = e_informTemplate.replace("[msFee]", df.format(hasSum) + "");
							e_informTemplate = e_informTemplate.replace("[paymentModeStr]", "还款方式：[一次性还款]<br/>");
							e_informTemplate = e_informTemplate.replace("[hasLFI]", "0");

							noticeMap.put("mail", informTemplate);// 站内信
							noticeMap.put("email", e_informTemplate);// 邮件
							noticeMap.put("note", s_informTemplate);// 短信
							// 消息模版
							// 站内信
							/*
							 * noticeMap.put("mail",
							 * "您投资的借款[<a href="+basePath+"/financeDetail.do?id="
							 * +borrowId+">"+borrowTitle+"</a>],已经完成.<br/>"+
							 * "本期应得总额：￥"
							 * +recivedPI+",其中本金部分为："+recivedPrincipal+
							 * "元,利息部分："+
							 * recievedInterest+"元<br/>扣除投资管理费：￥"+mFee+
							 * "元"+"<br/>实得总额：￥"+hasSum+"元"); //邮件
							 * noticeMap.put("email","您投资的借款[<a href="+basePath+
							 * "/financeDetail.do?id="
							 * +borrowId+">"+borrowTitle+"</a>],已经完成.<br/>"+
							 * "本期应得总额：￥"
							 * +recivedPI+",其中本金部分为："+recivedPrincipal+
							 * "元,利息部分："+
							 * recievedInterest+"元<br/>扣除投资管理费：￥"+mFee+
							 * "元"+"<br/>实得总额：￥"+hasSum+"元"); //短信
							 * noticeMap.put("note",
							 * "尊敬的"+username+":\n    ["+sf.format(new
							 * Date())+"] 您投资的借款["+borrowTitle+"]已经完成.\n"+
							 * "本期应得总额：￥"
							 * +recivedPI+",其中本金部分为："+recivedPrincipal+
							 * "元,利息部分："+
							 * recievedInterest+"元\n扣除投资管理费：￥"+mFee+"元"
							 * +"<br/>实得总额：￥"+hasSum+"元");
							 */
							// 查询风险保障金余额
							Map<String, String> riskMap = frontpayDao.queryRiskBalance(conn);
							double riskBalance = 1000000 + Convert.strToDouble(riskMap.get("riskBalance") + "", 0);
							// 投资手续费累加到风险保障金
							returnId = frontpayDao.addRiskAmount(conn, riskBalance, mFee, investor, -1, "投资管理费累加风险保障金");
							// 关闭自动投标
							returnId = frontpayDao.closeAutoBid(conn, investor);
							// 投资人帐号资金收入
							returnId = userDao.addUserUsableAmount(conn, recivedPI, investor);
							// 查询投资后的账户金额
							userSumMap = userDao.queryUserAmountAfterHander(conn, investor);
							if (userSumMap == null) {
								userSumMap = new HashMap<String, String>();
							}
							double usableSum = Convert.strToDouble(userSumMap.get("usableSum") + "", 0);
							double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum") + "", 0);
							double forPI = Convert.strToDouble(userSumMap.get("forPI") + "", 0);
							// 添加资金流动记录
							returnId = fundRecordDao.addFundRecord(conn, investor, "用户还款资金收入", recivedPI, usableSum, freezeSum, forPI, borrower, "您投资的借款[<a href=" + basePath
									+ "/financeDetail.do?id=" + borrowId + ">" + borrowTitle + "</a>]", recivedPI, 0.0, borrowId, repayId, 151, 0.0);

							// 投资人扣除投资管理费
							returnId = userDao.deducteUserUsableAmount(conn, mFee, investor);
							// 查询投资后的账户金额
							userSumMap = userDao.queryUserAmountAfterHander(conn, investor);
							usableSum = Convert.strToDouble(userSumMap.get("usableSum") + "", 0);
							freezeSum = Convert.strToDouble(userSumMap.get("freezeSum") + "", 0);
							forPI = Convert.strToDouble(userSumMap.get("forPI") + "", 0);
							// 添加资金流动记录
							returnId = fundRecordDao.addFundRecord(conn, investor, "投资收款扣除管理费", mFee, usableSum, freezeSum, forPI, -1, "您投资的借款[<a href=" + basePath
									+ "/financeDetail.do?id=" + borrowId + ">" + borrowTitle + "</a>]", 0.0, mFee, borrowId, repayId, 651, 0.0);
							if (returnId > 0) {
								// 给投资人发送消息
								selectedService.sendNoticeMSG(conn, investor, "用户还款资金收入报告", noticeMap, IConstants.NOTICE_MODE_1);
								// 自动结束提前还款正在竞拍中的债权
								assignmentDebtService.preRepayment(conn, repayId);
							}

						} else {
							returnId = -1;
						}
						// 回收对象
						investMap = null;
						if (returnId <= 0) {
							conn.rollback();
						}
						conn.commit();
					}
				}
				// 回收对象
				circulationMap = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
			circulationList = null;
			investList = null;
			noticeMap = null;
			df = null;
			sf = null;
			userSumMap = null;
			df = null;
			System.gc();
		}
	}

	/**
	 * 发送短信 模板
	 * 
	 * @throws SQLException
	 */
	public void sendtoTemple() throws SQLException {
		Connection conn = MySQL.getConnection();
		Map<String, String> noticeMap = new HashMap<String, String>();
		List<Map<String, Object>> tmplList = new ArrayList<Map<String, Object>>();
		PageBean<Map<String, Object>> pagebean = new PageBean<Map<String, Object>>();
		long investor = -1;
		String title = "";
		int id = 0;
		try {
			pagebean.setPageSize(150);
			jobTaskDao.queryTmpleAll(conn, pagebean);
			tmplList = pagebean.getPage();
			if (tmplList != null) {
				for (Map<String, Object> map : pagebean.getPage()) {
					investor = Convert.strToLong(map.get("user_id") + "", -1L);
					title = Convert.strToStr(map.get("sendtitle") + "", "");
					id = Convert.strToInt(map.get("id") + "", -1);
					noticeMap.put("email", Convert.strToStr(map.get("email_info") + "", ""));
					noticeMap.put("mail", Convert.strToStr(map.get("mail_info") + "", ""));
					noticeMap.put("note", Convert.strToStr(map.get("sms_info") + "", ""));
					noticeMap.put("operate_id", Convert.strToStr(map.get("operate_id") + "", ""));
					selectedService.sendNoticeMSG(conn, investor, title, noticeMap, Convert.strToStr(map.get("s_nid") + "", ""));

					Functions.f_send_temple(conn, id);
					map = null;
				}
			}
			conn.commit();
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
			noticeMap = null;
			pagebean = null;
			tmplList = null;
			System.gc();
		}
	}

	public void sendShortMessge() throws SQLException {

		Connection conn = MySQL.getConnection();
		try {
			List<Map<String, Object>> list = shortMassegeDao.queryShortMassege(conn);
			for (Map<String, Object> map : list) {
				try {
					int i = shortMaseegeService.jobTaskSend(map);
					if (i == 1) {
						jobTaskDao.sendShortMassege(conn, (Long) map.get("id"));
					}

				} catch (Exception e) {
					e.printStackTrace();
					log.equals(e);
				}
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			log.error(e);
		} finally {
			conn.close();
		}

	}

	public void publistBorrow() throws SQLException {
		Connection conn = MySQL.getConnection();
		try {
			jobTaskDao.updateBorrow(conn);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			log.error(e);
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
								Map<String, String> result = financeService.addBorrowInvest(Convert.strToLong(borrowId+"", -1), userId, "", realBidAmount, "",usermap.get("username"), 2, 0);
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
										Map<String, String> map = financeService.updateBorrowInvest(Convert.strToLong(borrowId+"", -1), Convert.strToLong(jobj1.getString("OrdId"), -1), userId,
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
	 * @MethodName: autoBid
	 * @Param: JobTaskService
	 * @Author: lizg
	 * @Date: 2013-4-14 下午11:50:51
	 * @Return:
	 * @Descb: 新版自动投标
	 * @Throws:
	 */
	public void autoBidNew() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		List<Map<String, Object>> biderList = null;
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
		// 用户Id
		long userId = -1;
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
		 //月利率
		double monthRate = 0;
		// 发布者
		long publisher = -1;
		// 借款期限
		int deadline = 0;
		// 借款标题
		String borrowTitle = "";
		long returnId = -1;
		
		int userGroup = 0;
//		String username = "";
		DecimalFormat format = new DecimalFormat("0.00");
		try {
			// 查询符合自动投标的用户
			userList = jobTaskDao.queryAutoBidUser(conn);
			for (Map<String, Object> userMap : userList) {
				userId = Convert.strToInt(userMap.get("userId") + "", -1);
                log.info("##############自动投标 循环开始 用户ID：=="+userId);
                if (activityOrderService.hasOrder(0,userId)>0){
                    log.info("##############自动投标 用户ID：=="+userId+" 属于减免物业费用户，直接跳过");
                    continue;
                }

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
					
					// 查询用户可用余额
					userAmount = jobTaskDao.queryUserGroupAndAmount(conn, remandAmount, userId);
					if(userAmount!=null){
						usableAmount = Convert.strToDouble(userAmount.get("usableSum") + "", 0);
						userGroup =  Convert.strToInt(userAmount.get("userGroup") + "", 0);
					}else{
						log.info("自动投标 客户信息：用户没有可投资金userId：=="+userId);
						continue;
					}
					
					log.info("自动投标 客户信息：可用投资金额usableAmount：=="+usableAmount);
					
					Map<String,String> usermap=userService.queryUserInfo(userId+"");
					
					// 根据用户投标参数获取投标的标的
					biderList = jobTaskDao.queryBiderListByParam(conn, rateStart, rateEnd, deadLineStart, deadLineEnd, creditStart, creditEnd, borrowId, isDayThe);
					if(biderList != null){
					for (Map<String, Object> biderMap : biderList) {
						borrowId = Convert.strToInt(biderMap.get("id") + "", -1);
						borrowAmount = Convert.strToDouble(biderMap.get("borrowAmount") + "", 0);
						hasAmount = Convert.strToDouble(biderMap.get("hasInvestAmount") + "", 0);
//						isDayThe = Convert.strToInt(biderMap.get("isDayThe") + "", -1);
						log.info("自动投标 标的信息：借款ID  borrowId：=="+borrowId);
						log.info("自动投标 标的信息：总借款金额borrowAmount：=="+borrowAmount);
						log.info("自动投标 标的信息：已投资金额hasAmount：=="+hasAmount);
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

								//比较用户的群组和标的群组 只投自己所属群组的标的
								if(userGroup!=borrowGroup){
									log.info("borrowGroup="+borrowGroup+",userGroup="+userGroup+",群组不匹配，不投标！");
									continue;
								}
								double investAmount = calculateBidAmount(bidAmount,borrowAmount,hasAmount,usableAmount);
								log.info("自动投标 客户信息：投资金额investAmount：=="+investAmount);
								log.info("自动投标 客户信息：每次投资限额bidAmount：=="+bidAmount);
								log.info("自动投标 客户信息：保留投资金额remandAmount：=="+remandAmount);
								log.info("自动投标 客户信息：用户标识：=="+userId);
//								int i = 0;
//								do{
//									i++;//最多循环5次
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
								Map<String, String> result = financeService.addBorrowInvest(Convert.strToLong(borrowId+"", -1), userId, "", realBidAmount, "",usermap.get("username"), 2, 0);
								String ordId = result.get("ret_ordid");
								log.info("自动投标  投标信息：投标标识ordId：=="+ordId);
								if (Convert.strToInt(result.get("ret"), -1) < 0 || Convert.strToLong(result.get("ret_ordid"), -1) < 0) {
									log.info("自动投标调接口前添加投资记录失败："+result.get("ret_desc"));
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
                                    log.info("自动投标  汇付返回："+strjson1);
//									log.info("自动投标  投标信息：投标标识jobj1.ordId：=="+jobj1.getString("OrdId"));
									log.info("自动投标接口处理结果："+jobj1);
									int respCode = jobj1.getInt("RespCode");
		//							respCode = 0;
									if(respCode==0){  //如果投标成功
									
										// 成功更新投资记录
										Map<String, String> map = financeService.updateBorrowInvest(Convert.strToLong(borrowId+"", -1), Convert.strToLong(jobj1.getString("OrdId"), -1), userId,
												realBidAmount, WebUtil.getWebPath(), usermap.get("username"), 2, 0, "", "", Convert.strToLong(usermap.get("usrCustId"), -1));
										if (Convert.strToInt(map.get("ret"), -1) < 0) {
											log.info("自动投标失败："+map.get("ret_desc")) ;
										}else{
											// 更新用户自动投标的标的记录
											returnId = financeDao.addAutoBidRecord(conn, userId, borrowId);
										}
									}
								}
//								}
//								while(investAmount > bidAmount);
//								while(investAmount > bidAmount && i<5);
							}
							
							if (returnId < 0) {
								conn.rollback();
							}
							conn.commit();
						}
//						}
						}
					
					}
					userMap = null;

					
				}
//				// 回收对象
//				biderMap = null;
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


    public void autoBidV3() throws SQLException, DataException {
        Connection conn = Database.getConnection();
        List<Map<String, Object>> biderList = null;
        List<Map<String, Object>> userList = null;
        Map<String, String> borrowInfoMap = null;
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
        // 用户Id
        long userId = -1;
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

        long returnId = -1;

        int userGroup = 0;

        DecimalFormat format = new DecimalFormat("0.00");
        try {
            // 查询符合自动投标的用户
            userList = jobTaskDao.queryAutoBidUser(conn);
            for (Map<String, Object> userMap : userList) {
                userId = Convert.strToInt(userMap.get("userId") + "", -1);
                log.info("##############减免物业费 自动投标 循环开始 用户ID：=="+userId);
                if (activityOrderService.hasOrder(0,userId)<=0){
                    log.info("##############减免物业费 自动投标 用户ID：=="+userId+" 不属于减免物业费用户，直接跳过");
                    continue;
                }
                Map<String, String> userParam = jobTaskDao.queryUserBidParam(conn, userId);
                rateStart = Convert.strToDouble(userParam.get("rateStart") + "", 0);
                rateEnd = Convert.strToDouble(userParam.get("rateEnd") + "", 0);
                deadLineStart = Convert.strToInt(userParam.get("deadlineStart") + "", 0);
                deadLineEnd = Convert.strToInt(userParam.get("deadlineEnd") + "", 0);
                creditStart = Convert.strToInt(userParam.get("creditStart") + "", 0);
                creditEnd = Convert.strToInt(userParam.get("creditEnd") + "", 0);
                bidAmount = Convert.strToDouble(userParam.get("bidAmount") + "", 0);
                remandAmount = Convert.strToDouble(userParam.get("remandAmount") + "", 0);

                // 查询用户可用余额
                usableAmount = autoBidService.getAutoBidAmount((Long)(userMap.get("userId")),new Date());
                if(usableAmount<100){
                    log.info("减免物业费 自动投标 客户信息：用户可投资金为："+usableAmount+",少于100,不投标userId：=="+userId);
                    continue;
                }

                log.info("减免物业费 自动投标 客户信息：可用投资金额usableAmount：=="+usableAmount);

                Map<String,String> usermap=userService.queryUserInfo(userId+"");

                // 根据用户投标参数获取投标的标的
                biderList = jobTaskDao.queryBiderListByParam(conn, rateStart, rateEnd, deadLineStart, deadLineEnd, creditStart, creditEnd, borrowId, isDayThe);
                if(biderList != null){
                    for (Map<String, Object> biderMap : biderList) {
                        borrowId = Convert.strToInt(biderMap.get("id") + "", -1);
                        borrowAmount = Convert.strToDouble(biderMap.get("borrowAmount") + "", 0);
                        hasAmount = Convert.strToDouble(biderMap.get("hasInvestAmount") + "", 0);
                        log.info("减免物业费 自动投标 标的信息：借款ID  borrowId：=="+borrowId);
                        log.info("减免物业费 自动投标 标的信息：总借款金额borrowAmount：=="+borrowAmount);
                        log.info("减免物业费 自动投标 标的信息：已投资金额hasAmount：=="+hasAmount);
                        // 满足投标条件,进行扣费处理 查询借款的基础信息
                        borrowInfoMap = jobTaskDao.queryBorrowInfo(conn, borrowId);
                        if (borrowInfoMap != null) {
                            int borrowGroup=Convert.strToInt(borrowInfoMap.get("borrowGroup")+"",0);

                                //比较用户的群组和标的群组 只投自己所属群组的标的
                                if(userGroup!=borrowGroup){
                                    log.info("borrowGroup="+borrowGroup+",userGroup="+userGroup+",群组不匹配，不投标！");
                                    continue;
                                }
                                double investAmount = calculateBidAmount(bidAmount,borrowAmount,hasAmount,usableAmount);
                                log.info("减免物业费 自动投标 客户信息：投资金额investAmount：=="+investAmount);
                                log.info("减免物业费 自动投标 客户信息：每次投资限额bidAmount：=="+bidAmount);
                                log.info("减免物业费 自动投标 客户信息：保留投资金额remandAmount：=="+remandAmount);
                                log.info("减免物业费 自动投标 客户信息：用户标识：=="+userId);

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
                                Map<String, String> result = financeService.addBorrowInvest(Convert.strToLong(borrowId+"", -1), userId, "", realBidAmount, "",usermap.get("username"), 2, 0);
                                String ordId = result.get("ret_ordid");
                                log.info("减免物业费 自动投标  投标信息：投标标识ordId：=="+ordId);
                                if (Convert.strToInt(result.get("ret"), -1) < 0 || Convert.strToLong(result.get("ret_ordid"), -1) < 0) {
                                    log.info("减免物业费 自动投标调接口前添加投资记录失败："+result.get("ret_desc"));
                                }else{

                                    String usrCustId = Convert.strToStr(usermap.get("usrCustId"), "");
                                    String transAmt = format.format(realBidAmount);
                                    JSONObject json = new JSONObject();
                                    json.put("BorrowerCustId", borrowInfoMap.get("usrCustId"));
                                    json.put("BorrowerAmt", transAmt);
                                    // 汇付还款总额为借款金额*(1+利率).改为1.00,防止出现手续费过多出现:本次还款金额加上已还金额超过还款总额的情况
                                    json.put("BorrowerRate", "1.00");

                                    String strjson1=ChinaPnRInterface.autoTender("", ordId, usrCustId, transAmt, "[" + json.toString() + "]");
                                    JSONObject jobj1 = JSONObject.fromObject(strjson1);
                                    log.info("减免物业费 自动投标  投标信息：投标标识jobj1.ordId：=="+jobj1.getString("OrdId"));
                                    log.info("减免物业费 自动投标接口处理结果："+jobj1);
                                    int respCode = jobj1.getInt("RespCode");
                                    if(respCode==0){  //如果投标成功
                                        // 成功更新投资记录
                                        Map<String, String> map = financeService.updateBorrowInvest(Convert.strToLong(borrowId+"", -1), Convert.strToLong(jobj1.getString("OrdId"), -1), userId,
                                                realBidAmount, WebUtil.getWebPath(), usermap.get("username"), 2, 0, "", "", Convert.strToLong(usermap.get("usrCustId"), -1));
                                        if (Convert.strToInt(map.get("ret"), -1) < 0) {
                                            log.info("减免物业费 自动投标失败："+map.get("ret_desc")) ;
                                        }else{
                                            // 更新用户自动投标的标的记录
                                            returnId = financeDao.addAutoBidRecord(conn, userId, borrowId);
//                                            MySQL.executeNonQuery(conn,"update t_user set usableSum = usableSum - "+realBidAmount+",freezeSum=freezeSum+"+realBidAmount+" where id = "+userId);
                                        }
                                    }
                                }

                            if (returnId < 0) {
                                conn.rollback();
                            }
                            conn.commit();
                        }
                    }

                }

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
//            userAmount = null;
//            borrowOwer = null;
            borrowInfoMap = null;
        }
    }


	/**
	 * @MethodName: calculateBidAmount
	 * @Param: JobTaskDao
	 * @Author: lizg
	 * @Date: 2013-4-15 上午10:34:39
	 * @Return:
	 * @Descb: 计算最后投标金额,(扣除保留金额)
	 * @Throws:
	 */
	private double calculateBidAmount(double bidAmount, double borrowAmount, double hasAmount,double userAmount) {
//		double maxBidAmount = borrowAmount * 0.2;
//		double schedule = hasAmount / borrowAmount;
//		double invesAmount = 0;
//		if (schedule < 0.9500) {
//			while (bidAmount > maxBidAmount) {
//				bidAmount = bidAmount - 50;
//			}
//
//			do {
//				invesAmount = hasAmount + bidAmount;
//				schedule = invesAmount / borrowAmount;
//				if (schedule > 0.9500) {
//					bidAmount = bidAmount - 50;
//				}
//			} while (schedule > 0.9500);
//		}
//		自动投标金额 = MIN （标的剩余金额，客户剩余金额 - 客户保留金额）    /* 取100的整数倍 */
//                投完后客户剩余金额 = 原有客户剩余金额 - 自动投标金额
//                如果 投完后客户剩余金额- 客户保留金额> 100   ，继续循环下一个标的  ；否则循环下一个客户；  
		double realInvestAmount = 0.0;
		realInvestAmount = (borrowAmount-hasAmount>userAmount? userAmount : borrowAmount-hasAmount);
		Double d = new Double(realInvestAmount);
		int t = d.intValue();
		realInvestAmount = t / 100;
		realInvestAmount = realInvestAmount * 100;
//		bidAmount = bidAmount / 100 ;
//		bidAmount = bidAmount * 100;
		return realInvestAmount;
	}
}
