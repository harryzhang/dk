package com.shove.web.action;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.config.ChinaPnrConfig;
import com.shove.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.service.AuctionDebtService;
import com.sp2p.service.ChinaPnRInterfaceService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.merService;
import com.sp2p.system.exception.FrontHelpMessageException;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;

/**
 * @汇付天下资金接口
 * @同一笔订单收到两次支付结果，处理的原则是:
 * @a：只要收到订单成功应答，即使以后再次收到失败应答，均不做任何处理
 * @b：某笔订单已经收到成功应答，可能再次收到该笔订单的成功应答，则只需要回应商户专属平台收到成功即可，商户的账务数据不被修改。
 * 
 */

@SuppressWarnings("serial")
public class ChinaPnrmentAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(ChinaPnrmentAction.class);
	private RechargeService rechargeService;
	private ChinaPnRInterfaceService chinaPnRInterfaceService;
	private ChinaPnRInterfaceService chinaService;
	private merService merService;
	private UserService userService;
	private UserService uService;
	private FinanceService financeService;
	private FinanceService financeinvestService;
	private AuctionDebtService auctionDebtService;
	public static Map<String, String> auditStatMap = new HashMap<String, String>();
	static {
		auditStatMap.put("I", "初始");
		auditStatMap.put("T", "提交");
		auditStatMap.put("P", "审核中");
		auditStatMap.put("R", "审核拒绝");
		auditStatMap.put("F", "开户失败");
		auditStatMap.put("K", "开户中");
		auditStatMap.put("Y", "开户成功");
	}

	/**
	 * 用户前台开户
	 * 
	 * @return
	 * @throws Exception
	 *             [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String UserRegister() throws Exception {
		String usrId = "";
		String usrName = "";
		String idNo = "";
		String usrMp = "";
		String usrEmail = "";
		String cmdId = "UserRegister";
		String html = ChinaPnRInterface.userRegister(cmdId, usrId, usrName,
				idNo, usrMp, usrEmail);
		sendHtml(html);
		return null;
	}

	/**
	 * 在线充值
	 */
	public String chinapnrPayment() throws Exception {
		AccountUserDo user = (AccountUserDo) session(IConstants.SESSION_USER);
		if (user == null) {// 未登陆
			return IConstants.ADMIN_AJAX_LOGIN;
		}

		Map<String, String> map = new HashMap<String, String>();
		String openBankId = request("openBankId"); // 开户银行
		String money = request("money");
		String usrCustId = request("usrCustId"); // 用户客户号
		// 前台session返回空的客户号，再次查询当前登录用户的客户号是否存在
		if (usrCustId == null || usrCustId == "-1") {
			map = rechargeService.queryUser(user.getId());
			usrCustId = map.get("usrCustId");
		}
		if (StringUtils.isBlank(money)) {// 判断是否为空
			return INPUT;
		}

		double amount = Convert.strToDouble(money, -1);
		if (amount <= 0) {
			return INPUT;
		}
		String cardDcFlag = Convert.strToStr(request("cardDcFlag"), null);
		if (cardDcFlag == null) {
			return INPUT;
		}

		money = new DecimalFormat("0.00").format(amount);
		long userId = this.getUserId();
		// 生成订单
		paramMap.put("rechargeMoney", money);
		paramMap.put("userId", userId + "");
		paramMap.put("result", "0");
		String date = DateUtil.dateToYMD(new Date());
		paramMap.put("addTime", date);
		// 新增充值记录，返回当前充值记录ID 类型为1 代表汇付天下
		Map<String, String> result = rechargeService.addRecharge(paramMap, 1);
		if (Convert.strToLong(result.get("result"), -1) > 0) {
			String html = ChinaPnRInterface.netSave("在线充值",
					result.get("result"), openBankId, usrCustId, date, money,
					"B2C", "" + userId, ChinaPnrConfig.chinapnr_retUrl,
					cardDcFlag);// paymentId_orderId_userId:支付类型(在线支付/在线充值)_订单编号/_用户编号
			sendHtml(html);
			return null;
		} else {
			sendHtml("支付失败:" + result.get("description"));
			return null;
		}

	}

	/**
	 * 跳转拦截
	 */
	public void createHelpMessage(String title, String msg, String url)
			throws FrontHelpMessageException {
		helpMessage.setMsg(new String[] { "返回首页" });
		helpMessage.setUrl(new String[] { "index" });
		helpMessage.setTitle(title);
		throw new FrontHelpMessageException();
	}

	/**
	 * 汇付天下 后台子账户充值 回调地址
	 */
	public String frontChinaPnrUrlBg() throws Exception {
		String CmdId = request("CmdId");// 消息类型
		if (!"NetSave".equals(CmdId)) {
			request().setAttribute("title", "交易类型错误");
			return SUCCESS;
		}
		int RespCode = Convert.strToInt(request("RespCode"), -1);

		if (RespCode != 0) {// 商户充值失败
			request().setAttribute("title", request("RespDesc"));
			merService.netSaveFail(request("OrdId"), request("TrxId"),
					request("FeeAmt"));
		}
		String title = merService.updateMerRecharge(request("OrdId"),
				request("TrxId"), request("FeeAmt"));
		request().setAttribute("title", title);
		return SUCCESS;
	}

	/**
	 * 开启或关闭自动投标计划,status=1关闭,status=2开启
	 * 
	 * @throws Exception
	 */
	private String doAutoPlan(int status) throws Exception {
		// 签名和结果验证
		String RespCode = request("RespCode");
		try {
			if (Convert.strToInt(RespCode, -1) != 0) {
				return request("RespDesc");
			}
		} catch (Exception e) {
			log.info(e);
			return "开启自动投标计划出现异常";
		}

		synchronized (uService) {
			String ordId = request("MerPriv");
			String retordId = uService.queryAutoInvestOrdId(ordId);
			if (retordId.length() == 0) {
				long UsrCustId = Convert.strToLong(request("UsrCustId"), 0);

				long ret = -1L;
				try {
					ret = userService.updateAutoPlan(UsrCustId, status, ordId);
				} catch (Exception e) {
					log.info(e);
				}
				if (ret > 0 && status == 2) {
					return "开启自动投标计划成功";
				}
				if (ret > 0 && status == 1) {
					return "关闭自动投标计划成功";
				}
				if (status == 1) {
					return "关闭自动投标计划失败";
				}
			}
		}
		return "开启自动投标计划失败";
	}

	/**
	 * 汇付 注册 处理
	 * 
	 * @throws Exception
	 */
	private String doUserRegister(String CmdId) throws Exception {
		// 签名和结果验证
		String RespCode = request("RespCode");
		try {
			if (Convert.strToInt(RespCode, -1) != 0) {
				return request("RespDesc");
			}
		} catch (Exception e) {
			log.info(e);
			return "注册出现异常";
		}
		String UsrId = Convert.strToStr(request("UsrId"), "");// 该字段格式为://
																// xxxx_123456789

		long userId = Convert.strToLong(
				UsrId.substring(UsrId.indexOf("_") + 1), -1);

		synchronized (uService) {
			String custId = uService.queryUserCustId(userId);// 根据用户id查询用户的客户号，如果存在客户号，说明已注册，怎不做注册处理了
            String email = Convert.strToStr(request("UsrEmail"), "");
            try {
                email = URLDecoder.decode(email,"utf-8");
            }catch (Exception e){}
			if (custId.length() == 0) {
				long UsrCustId = Convert.strToLong(request("UsrCustId"), -1);
				String idNo = Convert.strToStr(request("IdNo"), "");
				String realName = Convert.strToStr(request("UsrName"), "");
				realName = URLDecoder.decode(realName, "utf-8");
                long ret = -1L;
				try {
					ret = userService.updateUser(userId, UsrCustId, email,
							idNo, realName);
				} catch (SQLException e) {
					log.info(e);
				}
				if (ret > 0) {
					AccountUserDo user = new AccountUserDo();
					Map<String, String> userInfo = userService
							.queryUserById(userId);
					try {
						if (StringUtils.isBlank(userInfo.get("colorid"))) {
							userService.joinHyh(realName, idNo, userId);
						}
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}

					

					user.setAuthStep(Convert.strToInt(userInfo.get("authStep"),
							-1));
					user.setUsrCustId(Convert.strToLong(
							userInfo.get("usrCustId"), -1));
					user.setEmail(Convert.strToStr(userInfo.get("email"), null));
					user.setPassword(Convert.strToStr(userInfo.get("password"),
							null));
					user.setId(Convert.strToLong(userInfo.get("id"), -1L));

					user.setUsername(Convert.strToStr(userInfo.get("username"),
							null));
					user.setVipStatus(Convert.strToInt(
							userInfo.get("vipStatus"), -1));
					user.setEnable(Convert.strToInt(userInfo.get("enable"), -1));


					user.setUsrCustId(UsrCustId);
					session().setAttribute("user", user);
					return "注册成功";
				}
				return "注册失败";
			} else {
				try {
					AccountUserDo user = (AccountUserDo) session("user");
					if (user != null) {
						user.setUsrCustId(Long.parseLong(custId));
						user.setEmail(email);
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return "注册成功";
	}

	/**
	 * 汇付 取现 处理
	 * 
	 * @throws Exception
	 */
	private String doCash(String CmdId) throws Exception {
		// 签名和结果验证
		String RespCode = request("RespCode");
		try {
			if (Convert.strToInt(RespCode, -1) != 0) {
				// 失败时候删除该数据
				chinaPnRInterfaceService.cashFail(request("OrdId"));
				return request("RespDesc") + "，请联系客服!";
			}
		} catch (SQLException e) {
			log.info(e);
			return "取现出现异常，请联系客服!";
		}

		synchronized (chinaService) {
			String rettrxId = chinaService.queryWithdrawTrxId(Convert
					.strToLong(request("OrdId"), -1));
			if (rettrxId.length() == 0) { // 如果提现记录表中以有trxId，说明已经处理过了，就不再做处理，否则处理
				// 发送冻结请求
				try {
					JSONObject json = JSONObject.fromObject(ChinaPnRInterface
							.usrFreezeBg(request("UsrCustId"), "", "",
									request("OrdId"), request("TransAmt")));
					if (json.getInt("RespCode") != 0) {
						// 失败时候删除该数据
						chinaPnRInterfaceService.cashFail(request("OrdId"));
						return json.getString("RespDesc") + "，请联系客服!";
					}
					// 冻结成功的时候更新汇付TrxId和审核状态
					chinaPnRInterfaceService.updateCashTrxId(
							json.getString("OrdId"), json.getString("TrxId"));
					// 更新用户提现记录金额
					// chinaPnRInterfaceService.insertMoney("申请取现","汇付天下取现","0",request("TransAmt"),"1",user.getId()+"");
				} catch (Exception e) {
					log.info(e);
					return "处理取现请求出现异常，请联系客服!";
				}
			}
		}
		return "恭喜您，取现申请已经提交成功<br/>若是审批通过，资金将在1-2个工作日内到达您的银行账户。";

	}

	/**
	 * 汇付 投标处理
	 */
	private String doInitiativeTender(String CmdId) {
		try {
			// 签名和结果验证
			String RespCode = request("RespCode");
			if (Convert.strToInt(RespCode, -1) != 0) {
				//chinaPnRInterfaceService.deleteBorrowInvest(request("OrdId"));
				return request("RespDesc");
			}

			// 先查询流水记录表中有没有这次请求的流水号，有说明就已处理，没有说明未处理
			String ordId = request().getParameter("OrdId");
			String borrowId = request().getParameter("MerPriv") == null ? "-1"
					: request().getParameter("MerPriv");
			/*synchronized (financeinvestService) { // 加锁
				Map<String, String> retmap = financeinvestService
						.queryBorrowDelStauts(ordId + "_1"); // 投标处理拼接类型1
				if (retmap == null) {
					AccountUserDo user = (AccountUserDo) session().getAttribute("user");
					String subAcctType = request().getParameter("SubAcctType") == null ? ""
							: request().getParameter("SubAcctType");
					String subAcctId = request().getParameter("SubAcctId") == null ? ""
							: request().getParameter("SubAcctId");
					String transAmt = request().getParameter("TransAmt");

					// 成功更新投资记录
					Map<String, String> map = financeService
							.updateBorrowInvest(
									Convert.strToLong(borrowId, -1),
									Convert.strToLong(ordId, -1), user.getId(),
									Convert.strToDouble(transAmt, 0),
									getBasePath(), user.getUserName(), 2, 0,
									subAcctId, subAcctType, user.getUsrCustId());
					if (Convert.strToInt(map.get("ret"), -1) < 0) {
						return map.get("ret_desc");
					}
				}
			}*/
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return "投标出现异常";
		}
		return "投标成功";
	}

	/**
	 * 汇付 债权购买
	 */
	private String doDebtTender(String CmdId) {
		// 签名和结果验证
		String RespCode = request("RespCode");
		if (Convert.strToInt(RespCode, -1) != 0) {
			return request("RespDesc");
		}
		try {
			// 先查询流水记录表中有没有这次请求的流水号，有说明就已处理，没有说明未处理
			String priv = request("MerPriv");
			String ordId = request("OrdId");
			synchronized (financeinvestService) {
				Map<String, String> retmap = financeinvestService
						.queryBorrowDelStauts(ordId + "_3"); // 投标处理拼接类型3
				if (retmap == null) {

					String transAmt = request("CreditDealAmt");
					String pwd = "";// 密码验证已祛除
					long userId = -1;
					if (!StringUtils.isBlank(priv)) {// 值backbuy时为回购,userId存放的是usrCustId
						userId = Convert.strToLong(ChinaPnrConfig.chinapnr_dc,
								0);
						pwd = "9527";// 9527作为标识,处理回购业务
						// Admin admin =
						// (Admin)session().getAttribute(IConstants.SESSION_ADMIN);
					} else {
                        AccountUserDo user = this.getUser();
						userId = user.getId();
					}
					String investId = priv;// 当为回购时,这里传的是investId
					Map<String, String> pro_map = auctionDebtService
							.procedure_Debts(Convert.strToLong(ordId, 0),
									userId, Convert.strToDouble(transAmt, 0),
									pwd, this.getBasePath(), investId);
					long result = Convert.strToLong(pro_map.get("ret"), -1);
					if (result < 0) {
						JSONUtils.printStr2(pro_map.get("ret_desc"));
						return pro_map.get("ret_desc");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
			return "系统出现异常";
		}

		return "债权购买成功";
	}

	/**
	 * 汇付 充值 处理
	 */
	private String doNetSave(String CmdId) throws SQLException {
		// 签名验证
		String RespCode = request("RespCode");
		if (Convert.strToInt(RespCode, -1) != 0) {
			// 失败时候更新充值数据状态
			chinaPnRInterfaceService.netSaveFail(request("OrdId"));
			return request("RespDesc");
		}

		synchronized (financeinvestService) {
			// 先查询流水记录表中有没有这次请求的流水号，有说明就已处理，没有说明未处理
			String ordId = request().getParameter("OrdId");
			Map<String, String> retmap = financeinvestService
					.queryBorrowDelStauts(ordId + "_2"); // 投标处理拼接类型2
			if (retmap == null) {
				long userId = Convert.strToLong(request("MerPriv"), 0);
				double money = Convert.strToDouble(request("TransAmt"), 0);
				// 充值成功
				try {
					Map<String, String> resultMap = rechargeService
							.addUseraddmoney(userId, money, request("OrdId"),
									"充值成功");
					return resultMap.get("description");
				} catch (SQLException e) {
					log.info(e);
					return "充值出现异常";
				}
			}
		}
		return "充值成功";
	}

	/**
	 * 汇付天下 前台调用函数
	 */
	@SuppressWarnings("unchecked")
	public String frontMerUrl() throws Exception {
		log.info("进入前台回调： parameter");
		Map<String, String[]> ret = request().getParameterMap();
		for (String key : ret.keySet()) {
			log.info(key + "====>" + ret.get(key)[0]);
		}

		String CmdId = request("CmdId");// 消息类型
		String result = null;

		if ("CreditAssign".equals(CmdId)) {// 购买债权
			result = doDebtTender(CmdId);
			createHelpMessage(result, "返回首页", "index.do");
		} else if ("NetSave".equals(CmdId)) {// 网银充值
			result = doNetSave(CmdId);
			createHelpMessage(result, "返回首页", "index.do");
		} else if ("UserRegister".equals(CmdId)) {// 注册
			result = doUserRegister(CmdId);
			createHelpMessage(result, "返回首页", "index.do");
		} else if ("Cash".equals(CmdId)) {// 取现
			result = doCash(CmdId);
			createHelpMessage(result, "返回首页", "index.do");
		} else if ("InitiativeTender".equals(CmdId)) {// 投标
			result = doInitiativeTender(CmdId);
			createHelpMessage(result, "返回首页", "index.do");
		} else if ("AutoTenderPlan".equals(CmdId)) {// 开启自动投标计划
			result = doAutoPlan(2);// 2为自动投标开启状态
			/*
			 * response().sendRedirect("/automaticBidInit.do"); result =
			 * "订单:RECV_ORD_ID_" + request("OrdId");
			 * JSONUtils.printStr2(result); return null;
			 */
			createHelpMessage(result, "返回首页", "index.do");
		} else if ("AutoTenderPlanClose".equals(CmdId)) {// 关闭自动投标计划
			result = doAutoPlan(1);// 1为自动投标关闭状态
			/*
			 * response().sendRedirect("/automaticBidInit.do"); result =
			 * "订单:RECV_ORD_ID_" + request("OrdId");
			 * JSONUtils.printStr2(result); return null;
			 */
			createHelpMessage(result, "返回首页", "index.do");
		}

		// 汇付规范:成功后,需在页面输出 RECV_ORD_ID_+订单号
		if ("UserBindCard".equals(CmdId)) {// 绑卡
			if (Convert.strToInt(request("RespCode"), -1) == 0) {
				doUserBindCard();
			}
			result = "订单:RECV_ORD_ID_" + request("TrxId");
		} else if ("PosWhSave".equals(CmdId) || "NetSave".equals(CmdId)
				|| "UserRegister".equals(CmdId)) {
			result = "订单:RECV_ORD_ID_" + request("TrxId");
		} else if ("CorpRegister".equals(CmdId)) {
			doCorpRegister();// 企业开户
			result = "订单:RECV_ORD_ID_" + request("TrxId");
		} else {
			result = "订单:RECV_ORD_ID_" + request("OrdId");
		}

		System.out.println("前台回调返回结果：" + result);
		JSONUtils.printStr2(result);

		return SUCCESS;
	}

	/**
	 * 汇付天下 后台回调函数
	 */
	@SuppressWarnings("unchecked")
	public String backgroundMerUrl() throws Exception {
		String result = null;
		log.info("进入后台回调:parameter");
		Map<String, String[]> ret = request().getParameterMap();
		for (String key : ret.keySet()) {
			log.info(key + "====>" + ret.get(key)[0]);
		}

		String CmdId = request("CmdId");// 消息类型

		if ("CreditAssign".equals(CmdId)) {// 购买债权
			result = doDebtTender(CmdId);
		} else if ("NetSave".equals(CmdId)) {// 网银充值
			result = doNetSave(CmdId);
		} else if ("UserRegister".equals(CmdId)) {// 注册
			result = doUserRegister(CmdId);
		} else if ("Cash".equals(CmdId)) {// 取现
			result = doCash(CmdId);
		} else if ("InitiativeTender".equals(CmdId)) {// 投标
			result = doInitiativeTender(CmdId);
		} else if ("AutoTenderPlan".equals(CmdId)) {// 开启自动投标计划
			result = doAutoPlan(2);// 2为自动投标开启状态
		} else if ("AutoTenderPlanClose".equals(CmdId)) {// 关闭自动投标计划
			result = doAutoPlan(1);// 1为自动投标关闭状态
		}

		// 汇付规范:成功后,需在页面输出 RECV_ORD_ID_+订单号
		if ("UserBindCard".equals(CmdId)) {// 绑卡
			if (Convert.strToInt(request("RespCode"), -1) == 0) {
				doUserBindCard();
			}
			result = "订单:RECV_ORD_ID_" + request("TrxId");
		} else if ("PosWhSave".equals(CmdId) || "NetSave".equals(CmdId)
				|| "UserRegister".equals(CmdId)) {
			result = "订单:RECV_ORD_ID_" + request("TrxId");
		} else if ("CorpRegister".equals(CmdId)) {
			doCorpRegister();// 企业开户
			result = "订单:RECV_ORD_ID_" + request("TrxId");
		} else {
			result = "订单:RECV_ORD_ID_" + request("OrdId");
		}

		System.out.println("后台回调返回结果：" + result);
		JSONUtils.printStr2(result);
		return null;
	}

	/*** 企业开户回调 **/
	@SuppressWarnings("deprecation")
	private void doCorpRegister() {
		// 结果验证
		String RespCode = request("RespCode");
		if (Convert.strToInt(RespCode, -1) != 0) {
			log.info("CorpRegister result:"
					+ URLDecoder.decode(request("RespDesc")));
			return;
		}
		String usrId = request("UsrId").substring("cfbgs_".length());// cfbgs_999980792
		String usrName = URLDecoder.decode(request("UsrName"));// 中文,解码
		String usrCustId = request("UsrCustId");
		/* I： 初始 T：提交 P：审核中 R： 审核拒绝 F： 开户失败 K： 开户中 Y：开户成功 */
		String auditStat = Convert.strToStr(
				auditStatMap.get(request("AuditStat")), "未知状态");
		String auditDesc = request("AuditDesc");// 审核状态描述

		String OpenBankId = request("OpenBankId");// 银行代号
		String CardId = request("CardId");// 银行卡号
		try {
			chinaPnRInterfaceService.doCorpRegister(usrId, usrName, usrCustId,
					auditStat, auditDesc, OpenBankId, CardId);
		} catch (Exception e) {
			log.info(e);
		}
	}

	/** 绑卡回调 */
	private void doUserBindCard() {
		// 结果验证
		String RespCode = request("RespCode");
		if (Convert.strToInt(RespCode, -1) != 0) {
			return;
		}

		// 成功则添加银行卡
		String cardNo = request("OpenAcctId");// 银行卡号
		String openBankId = request("OpenBankId");// 银行代号
		String bankName = ChinaPnrConfig.bankMap.get(openBankId);// 银行名称
		String UsrCustId = request("UsrCustId");
		String sql = "INSERT INTO t_bankcard (userId,cardNo,cardUserName,bankName,cardStatus,openBankId,commitTime) "
				+ "SELECT u.id,'"
				+ cardNo
				+ "',p.realName,'"
				+ bankName
				+ "',1,'"
				+ openBankId
				+ "',now() FROM t_user u LEFT JOIN t_person p ON u.id = p.userId WHERE u.usrCustId = "
				+ UsrCustId;
		try {
			chinaPnRInterfaceService.addBankCard(sql);
		} catch (Exception e) {
			log.info(e);
		}
	}

	protected static long getDistanceTime(Date one, Date two) {// 判断相隔多少天。
		long day = 0;
		long time1 = one.getTime();
		long time2 = two.getTime();
		long diff;
		diff = time1 - time2;
		day = diff / (24 * 60 * 60 * 1000);
		return day;
	}

	public void setMerService(merService merService) {
		this.merService = merService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public void setChinaPnRInterfaceService(
			ChinaPnRInterfaceService chinaPnRInterfaceService) {
		this.chinaPnRInterfaceService = chinaPnRInterfaceService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setAuctionDebtService(AuctionDebtService auctionDebtService) {
		this.auctionDebtService = auctionDebtService;
	}

	public FinanceService getFinanceinvestService() {
		return financeinvestService;
	}

	public void setFinanceinvestService(FinanceService financeinvestService) {
		this.financeinvestService = financeinvestService;
	}

	public UserService getuService() {
		return uService;
	}

	public void setuService(UserService uService) {
		this.uService = uService;
	}

	public ChinaPnRInterfaceService getChinaService() {
		return chinaService;
	}

	public void setChinaService(ChinaPnRInterfaceService chinaService) {
		this.chinaService = chinaService;
	}

}
