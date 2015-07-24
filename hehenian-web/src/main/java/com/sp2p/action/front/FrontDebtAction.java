package com.sp2p.action.front;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.AuctionDebtService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.util.DateUtil;

/**
 * 债权转让
 */
public class FrontDebtAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontDebtAction.class);
	private static final long serialVersionUID = 1L;

	private AssignmentDebtService assignmentDebtService;

	private AuctionDebtService auctionDebtService;

	private MyHomeService myHomeService;

	private UserService userService;

	@SuppressWarnings("unused")
	private SelectedService selectedService;

	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 前台 债权转让 获取已发布债权
	 */
	@SuppressWarnings("unchecked")
	public String queryFrontAllDebt() {
		pageBean.setPageNum(request("curPage"));
		try {
			assignmentDebtService.queryAllDebt(null, -1, -1, -1, -1, -1, "2,3", pageBean);
			List<Map<String, Object>> list = pageBean.getPage();
			Date nowDate = new Date();
			if (list != null)
				for (Map<String, Object> map : list) {
					Date date = (Date) map.get("actionTime");
					Date publishTime = (Date) map.get("publishTime");
					int auctionDays = (Integer) map.get("auctionDays");
					long times = DateUtil.getMsecondsDiff(DateUtil.dateAddDay(publishTime, (int) auctionDays));
					map.put("remainTimes", times);
					int status = Integer.parseInt(map.get("debtStatus") + "");
					if (status != 2) {
						map.put("remainDays", "--");
						continue;
					}
					String remainDays = DateUtil.remainDateToString(nowDate, date);
					map.put("remainDays", remainDays);
				}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		// 将参数设置到paramMap
		setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 正在转让的债权
	 */
	@SuppressWarnings("unchecked")
	public String queryPublishDebt() {
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		try {
			assignmentDebtService.queryAllDebt(null, -1, -1, -1, -1, -1, null, null);
			List<Map<String, Object>> list = pageBean.getPage();
			if (list != null)
				for (Map<String, Object> map : list) {
					String a = map.get("repayPeriod").toString();
					String[] c = a.split("/");
					map.put("num", Integer.parseInt(c[1]) - Integer.parseInt(c[0]));
					int pre = 100 - ((Integer.parseInt(c[0]) - 1) * 100) / Integer.parseInt(c[1]);
					map.put("repayPeriod", pre);
				}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		// 将参数设置到paramMap
		setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 查询前台的债权转让
	 * 
	 * @return
	 */
	public String queryFrontSuccessDebt() {
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		long debtSum = Convert.strToLong(request("debtSum"), -1);
		long auctionBasePrice = Convert.strToLong(request("auctionBasePrice"), -1);
		long auctionMode = Convert.strToLong(request("auctionMode"), -1);
		long isLate = Convert.strToLong(request("isLate"), -1);
		long publishDays = Convert.strToLong(request("publishDays"), -1);
		String borrowTitle = SqlInfusion.FilteSqlInfusion(request("borrowTitle"));

		try {
			assignmentDebtService.queryAllDebt(borrowTitle, debtSum, auctionBasePrice, auctionMode, isLate, publishDays, "3", pageBean);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		// 将参数设置到paramMap
		setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 查询债权债权详情
	 * 
	 * @return
	 */
	public String queryDebtDetail() {
		long id = Convert.strToLong(request("id"), -1);
		try {

			Map<String, String> map = assignmentDebtService.getAssignmentDebt(id);
			if (map != null) {
				long viewCount = Convert.strToLong(map.get("viewCount"), 0);
				viewCount++;
				paramMap.putAll(map);
				long borrowId = Convert.strToLong(map.get("borrowId"), -1);
				long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
				paramMap.put("borrowerId", borrowerId + "");
				paramMap.put("viewCount", viewCount + "");
				map = new HashMap<String, String>();
				map.put("viewCount", viewCount + "");
				assignmentDebtService.updateAssignmentDebt(id, -1, map);
				String publishTime = paramMap.get("publishTime");
				long auctionDays = Convert.strToLong(paramMap.get("auctionDays"), 0);
				String remainDays = DateUtil.remainDateToString(new Date(), DateUtil.dateAddDay(DateUtil.strToDate(publishTime), (int) auctionDays));
				paramMap.put("remainDays", remainDays);
				long debtId = Convert.strToLong(paramMap.get("id"), -1);
				paramMap.put("debtId", paramMap.get("id"));
				paramMap.putAll(auctionDebtService.queryAuctionMaxPriceAndCount(debtId));
				long alienatorId = Convert.strToLong(paramMap.get("alienatorId"), -1);
				Map<String, String> userMap = auctionDebtService.getUserAddressById(alienatorId);
				request().setAttribute("userMap", userMap);
				List<Map<String, Object>> investList = auctionDebtService.queryInvestByid(id);
				request().setAttribute("investList", investList);
				Map<String, String> borrowDetailMap = auctionDebtService.queryBorrowDetailById(id);
				request().setAttribute("borrowDetailMap", borrowDetailMap);
			}

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 添加留言
	 */
	public String addDebtMSG() throws IOException, SQLException, DataException {
        AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = SqlInfusion.FilteSqlInfusion((String) session().getAttribute("msg_checkCode"));
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code") == null ? "" : paramMap.get("code"));
		if (!code.equals(_code)) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String msgContent = paramMap.get("msg") == null ? "" : paramMap.get("msg");
		long returnId = -1;
		returnId = assignmentDebtService.addDebtMsg(idLong, user.getId(), msgContent);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * 留言初始化
	 * 
	 */
	public String debtMSGInit() throws SQLException, DataException {
		String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
		long idLong = Convert.strToLong(id, -1);
		String pageNum = SqlInfusion.FilteSqlInfusion(paramMap.get("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_6);
		if (idLong == -1) {
			return "404";
		}
		assignmentDebtService.queryDebtMSGBord(idLong, pageBean);
		request().setAttribute("id", id);
		return "success";
	}

	/**
	 * 竞拍初始化
	 * 
	 * @return
	 */
	public String auctingDebtInit() {
		long userId = this.getUserId();
		try {
			paramMap.put("debtId", request("debtId"));
			Map<String, String> map = auctionDebtService.getUserById(userId);
			if (map != null) {
				paramMap.put("usableSum", map.get("usableSum"));
				paramMap.put("totalSum", String.format("%.2f", Convert.strToDouble(map.get("freezeSum"), 0.0) + Convert.strToDouble(map.get("usableSum"), 0.0)));
			}
			Map<String, String> debtMap = assignmentDebtService.getAssignmentDebt(Convert.strToLong(request("debtId"), -1));
			if (debtMap != null) {
				paramMap.putAll(debtMap);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 债权购买
	 */
	public String addAuctingDebt() throws Exception {
		long debtId = Convert.strToLong(paramMap.get("id"), -1);
		long userId = this.getUserId();
		if (userId <= 0) {
			JSONUtils.printStr2("请先登录");
			return null;
		}
		try {
			double auctionPrice = Convert.strToDouble(paramMap.get("auctionPrice"), 0.0);
			Map<String, String> debtMap = assignmentDebtService.getAssignmentDebt(debtId);
			Map<String, String> userMap = auctionDebtService.getUserById(userId);

			if (debtMap != null && userMap != null) {
				if (debtMap.get("alienatorId").equals(userId + "")) {
					JSONUtils.printStr2("不能购买自己转让的的债权"); // 不能投自己转让的的债权
					return null;
				}
				long borrowId = Convert.strToLong(debtMap.get("borrowId"), -1);
				Map<String, String> aucctionMap = auctionDebtService.getAuctionDebt(debtId, userId);
				double oldAuctionPrice = 0.0;
				if (aucctionMap != null) {
					oldAuctionPrice = Convert.strToDouble(aucctionMap.get("auctionPrice"), 0.0);
				}

				double usableSum = Convert.strToDouble(userMap.get("usableSum"), 0.0);
				if (usableSum < (auctionPrice - oldAuctionPrice)) {
					JSONUtils.printStr2("可用余额不足"); // 可用余额不足
					return null;
				}

			/*	Map<String, String> investPWDMap = auctionDebtService.getDealPWD(userId, dealpass);
				if (investPWDMap == null || investPWDMap.size() == 0) {
					JSONUtils.printStr2("密码错误"); // 密码错误
					return null;
				}*/

				long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
				if (borrowerId == userId) {
					JSONUtils.printStr2("借款者不能购买该债权"); // 借款者不能竞拍该债权
					return null;
				}

				if (!"2".equals(debtMap.get("debtStatus"))) {
					JSONUtils.printStr2("购买失败,非转让中的债权"); // 竞拍失败
					return null;
				}

				String transAmt = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("auctionPrice")), "0.00");

				StringBuffer sb = new StringBuffer();
				if (transAmt != null && transAmt.indexOf(".") == -1) {
					transAmt = sb.append(transAmt).append(".00").toString();
				} else {
					transAmt = transAmt.substring(0, transAmt.indexOf(".") + 3);
				}
				String html=  auctionDebtService.sendDebtHtml(debtId, auctionPrice, this.getUser().getUsrCustId());
				sendHtml(html);
//				Map<String, String> pro_map = auctionDebtService.procedure_Debts(debtId, userId, debtUsrCustId, auctionPrice, dealpass, this.getBasePath(), this.getUser()
//						.getUsrCustId());
//				if (Convert.strToInt(pro_map.get("ret"), -1) < 0) {
//					JSONUtils.printStr2(pro_map.get("ret_desc"));
//					return null;
//				}
//				JSONUtils.printStr2(pro_map.get("ret_desc"));
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询竞拍记录
	 * 
	 * @return
	 */
	public String queryAcutionRecordInfo() {
		long id = Convert.strToLong(paramMap.get("id"), -1);
		try {
			List<Map<String, Object>> list = auctionDebtService.queryAuctionDebtByDebtId(id);
			request().setAttribute("list", list);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAssignmentDebtService(AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public void setAuctionDebtService(AuctionDebtService auctionDebtService) {
		this.auctionDebtService = auctionDebtService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	/**
	 * 合和年 前台 我要转让债权
	 * 
	 * @return
	 */
	public String queryFrontCanDebt() {
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		pageBean.setPageSize(8);
		try {
			assignmentDebtService.queryAssignmentDebtable(pageBean, this.getUserId());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		// 将参数设置到paramMap
		setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 合和年 前台 即将发布债权
	 * 
	 * @return
	 */
	public String queryFrontRedayDebt() {
		pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
		try {
			assignmentDebtService.queryAssigRedayDebt(pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 合和年查询债权债权详情
	 * 
	 * @return
	 */
	public String queryDebtDetailHHN() {
		long id = Convert.strToLong(request("id"), -1);
		// 兼容标准版处理
		if (id == -1)
			id = Convert.strToLong(paramMap.get("id"), -1);
		request().setAttribute("debtId", id);
		if (id < 0)
			return SUCCESS;
		try {
			Map<String, String> map = assignmentDebtService.getAssignmentDebt(id);
            AccountUserDo user = (AccountUserDo) session().getAttribute("user");
			if (map != null) {
				long viewCount = Convert.strToLong(map.get("viewCount"), 0);
				viewCount++;
				paramMap.putAll(map);
				long borrowId = Convert.strToLong(map.get("borrowId"), -1);
				long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
				paramMap.put("borrowerId", borrowerId + "");
				paramMap.put("viewCount", viewCount + "");
				map = new HashMap<String, String>();
				map.put("viewCount", viewCount + "");
				assignmentDebtService.updateAssignmentDebt(id, -1, map);
				String publishTime = paramMap.get("publishTime");
				long auctionDays = Convert.strToLong(paramMap.get("auctionDays"), 0);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (null == publishTime || publishTime.equals("")) {
					publishTime = df.format(new Date());
				}
//				String remainDays = DateUtil.remainDateToString(new Date(), DateUtil.dateAddDay(DateUtil.strToDate(publishTime), (int) auctionDays));
				//paramMap.put("remainDays", remainDays);
				long times = DateUtil.getMsecondsDiff(DateUtil.dateAddDay(DateUtil.strToDate(publishTime), (int) auctionDays));
				request().setAttribute("times", times);

				long debtId = Convert.strToLong(paramMap.get("id"), -1);
				paramMap.put("debtId", paramMap.get("id"));
				paramMap.putAll(auctionDebtService.queryAuctionMaxPriceAndCount(debtId));
				request().setAttribute("nextDay", paramMap.get("nextDay").substring(0, 10));
				
				double bal =Convert.strToDouble( paramMap.get("balance"), 0);//剩余本金
				double interest =Convert.strToDouble( paramMap.get("interest"), 0);//剩余应收利息
				double days =Convert.strToDouble( paramMap.get("dayss"), 0);//剩余期限(天)
				double price =Convert.strToDouble( paramMap.get("auctionBasePrice"), 0);//转让价格
				double earn = (bal+interest-price)*36000/(price*days);
				paramMap.put("earn", new DecimalFormat("0.00").format(earn));

				long alienatorId = Convert.strToLong(paramMap.get("alienatorId"), -1);
				Map<String, String> userMap = auctionDebtService.getUserAddressById(alienatorId);
				request().setAttribute("userMap", userMap);

				Map<String, String> borrowMap = auctionDebtService.getBorrowDetailById(borrowId);
				if(StringUtils.isBlank(borrowMap.get("detail"))){
					borrowMap.put("detail", "无");
				}
				request().setAttribute("borrowMap", borrowMap);

				long publisherId = Convert.strToLong(borrowMap.get("publisher"), -1);
				Map<String, String> publisherMap = auctionDebtService.getUserAddressById(publisherId);
				request().setAttribute("publisherMap", publisherMap);

				Map<String, String> borrowerMap = auctionDebtService.getUserAddressById(borrowerId);
				request().setAttribute("borrowerMap", borrowerMap);

				Map<String, String> accmountStatisMap = myHomeService.queryAccountStatisInfo(user.getId());
				request().setAttribute("accmountStatisMap", accmountStatisMap);
				Map<String, String> investCountMap = auctionDebtService.queryInvestCountByid(user.getId());
				request().setAttribute("investCountMap", investCountMap);

				Map<String, String> personMap = userService.queryPersonById(user.getId());
				request().setAttribute("personMap", personMap);
				// 账户总额（投资总额+可用余额..）
				String investCount = investCountMap.get("investCount");
				String usableAmount = accmountStatisMap.get("usableAmount");
				DecimalFormat decf = new DecimalFormat("0.00");
				BigDecimal invest;
				BigDecimal usable;
				BigDecimal countMoney = new BigDecimal(0);
				if (investCount != null && investCount.length() > 0) {
					invest = new BigDecimal(investCount);
					usable = new BigDecimal(usableAmount);
					countMoney = usable.add(invest);
				} else if (usableAmount != null && usableAmount.length() > 0) {
					usable = new BigDecimal(usableAmount);
					countMoney = usable;
				}
				request().setAttribute("countMoney", decf.format(countMoney));
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 合和年确认购买债权
	 * 
	 * @return
	 */
	public String buyDebtDetailHHN() {
		long id = Convert.strToLong(request("debtId"), -1);
		request().setAttribute("debtId", id);
		try {
			Map<String, String> map = assignmentDebtService.getAssignmentDebt(id);
			// Map<String, String> personMap = new HashMap<String, String>();
            AccountUserDo user = (AccountUserDo) session().getAttribute("user");
			if (map != null) {
				long viewCount = Convert.strToLong(map.get("viewCount"), 0);
				viewCount++;
				paramMap.putAll(map);
				long borrowId = Convert.strToLong(map.get("borrowId"), -1);
				long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
				paramMap.put("borrowerId", borrowerId + "");
				paramMap.put("viewCount", viewCount + "");
				map = new HashMap<String, String>();
				map.put("viewCount", viewCount + "");
				assignmentDebtService.updateAssignmentDebt(id, -1, map);
				String publishTime = SqlInfusion.FilteSqlInfusion(paramMap.get("publishTime"));
				long auctionDays = Convert.strToLong(paramMap.get("auctionDays"), 0);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (null == publishTime || publishTime.equals("")) {
					publishTime = df.format(new Date());
				}
				String remainDays = DateUtil.remainDateToString(new Date(), DateUtil.dateAddDay(DateUtil.strToDate(publishTime), (int) auctionDays));
				paramMap.put("remainDays", remainDays);

				long debtId = Convert.strToLong(paramMap.get("id"), -1);
				paramMap.put("debtId", paramMap.get("id"));
				paramMap.putAll(auctionDebtService.queryAuctionMaxPriceAndCount(debtId));

				// 借款人的信息
				long alienatorId = Convert.strToLong(paramMap.get("alienatorId"), -1);
				Map<String, String> userMap = auctionDebtService.getUserAddressById(alienatorId);
				request().setAttribute("userMap", userMap);

				Map<String, String> borrowMap = auctionDebtService.getBorrowDetailById(borrowId);
				request().setAttribute("borrowMap", borrowMap);

				// 发布人的信息
				long publisherId = Convert.strToLong(borrowMap.get("publisher"), -1);
				Map<String, String> publisherMap = auctionDebtService.getUserAddressById(publisherId);
				request().setAttribute("publisherMap", publisherMap);

				Map<String, String> borrowerMap = auctionDebtService.getUserAddressById(borrowerId);
				request().setAttribute("borrowerMap", borrowerMap);

				Map<String, String> accmountStatisMap = myHomeService.queryAccountStatisInfo(user.getId());
				request().setAttribute("accmountStatisMap", accmountStatisMap);
				Map<String, String> investCountMap = auctionDebtService.queryInvestCountByid(user.getId());
				request().setAttribute("investCountMap", investCountMap);

				Map<String, String> personMap = userService.queryPersonById(user.getId());
				request().setAttribute("personMap", personMap);
				// 账户总额（投资总额+可用余额..）
				String investCount = investCountMap.get("investCount");
				String usableAmount = accmountStatisMap.get("usableAmount");
				DecimalFormat decf = new DecimalFormat("0.00");
				BigDecimal invest;
				BigDecimal usable;
				BigDecimal countMoney = new BigDecimal(0);
				if (investCount != null && investCount.length() > 0) {
					invest = new BigDecimal(investCount);
					usable = new BigDecimal(usableAmount);
					countMoney = usable.add(invest);
				} else if (usableAmount != null && usableAmount.length() > 0) {
					usable = new BigDecimal(usableAmount);
					countMoney = usable;
				}
				request().setAttribute("countMoney", decf.format(countMoney));
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
