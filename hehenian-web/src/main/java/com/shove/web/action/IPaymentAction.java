package com.shove.web.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.shove.Convert;
import com.shove.config.IPayConfig;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.FormUtil;
import com.sp2p.constants.IConstants;
import com.sp2p.service.RechargeDetailService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.UserService;
import com.sp2p.system.exception.FrontHelpMessageException;
import com.sp2p.util.DateUtil;

import cryptix.jce.provider.MD5;

public class IPaymentAction extends BasePageAction {
	private static Log log = LogFactory.getLog(IPaymentAction.class);

	private UserService userService;

	private RechargeDetailService rechargeDetailService;
	private RechargeService rechargeService;

	// 在线充值
	public String ipayPayment() throws Exception {
		AccountUserDo user = (AccountUserDo) session(IConstants.SESSION_USER);
		if (user == null) {// 未登陆
			return IConstants.ADMIN_AJAX_LOGIN;
		}
		String money = request("money");
		if (StringUtils.isBlank(money)) {// 判断是否为空
			return INPUT;
		}
		BigDecimal moneyDecimal = null;

		try {
			moneyDecimal = new BigDecimal(money);
		} catch (RuntimeException e) {
			return INPUT;
		}
		String gatewayType = request("gatewayType");
		int temp = moneyDecimal.compareTo(new BigDecimal("0.01"));// 最小金额为0.01元
		if (temp < 0) {
			return INPUT;
		}
		long userId = this.getUserId();
		// 生成订单
		paramMap.put("rechargeMoney", moneyDecimal + "");
		paramMap.put("rechargeType", "5");
		paramMap.put("userId", userId + "");
		paramMap.put("result", "0");
		Date date = new Date();
		paramMap.put("addTime", DateUtil.dateToString(date));

		Map<String, String> result = rechargeService.addRecharge(paramMap, 2);
		long nunber = Convert.strToLong(result.get("result"), -1);

		if (nunber != -1) {
			String html = createIpayUrl("在线充值", nunber, userId,
					DateUtil.YYYYMMDD.format(date), gatewayType, moneyDecimal);// paymentId_orderId_userId:支付类型(在线支付/在线充值)_订单编号/_用户编号
			sendHtml(html);
			return null;
		} else {
			createHelpMessage("支付失败！" + result.get("description"), "返回首页",
					"index.do");
			return null;
		}

	}

	/**
	 * 跳转拦截
	 * 
	 * @param title
	 * @param msg
	 * @param url
	 * @throws FrontHelpMessageException
	 */
	public void createHelpMessage(String title, String msg, String url)
			throws FrontHelpMessageException {
		/* helpMessage.setTitle("用户不存在"); */
		helpMessage.setMsg(new String[] { "返回首页" });
		helpMessage.setUrl(new String[] { "index" });
		helpMessage.setTitle(title);
		/*
		 * helpMessage.setMsg(new String[]{msg}); helpMessage.setUrl(new
		 * String[]{url});
		 */
		throw new FrontHelpMessageException();
	}

	private boolean validateSign() {
		StringBuffer sign = new StringBuffer();
		sign.append("billno");
		sign.append(request("billno"));
		sign.append("currencytype");
		sign.append(request("Currency_type"));
		sign.append("amount");
		sign.append(request("amount"));
		sign.append("date");
		sign.append(request("date"));
		sign.append("succ");
		sign.append(request("succ"));
		sign.append("ipsbillno");
		sign.append(request("ipsbillno"));
		sign.append("retencodetype");
		sign.append(request("retencodetype"));
		sign.append(IPayConfig.ipay_certificate);
		MD5 md5 = new MD5();
		log.info("sign==>"+sign);
		String signMd5 = md5.toMD5(sign.toString()).toLowerCase();
		log.info("signMd5==>"+signMd5);
		log.info("signature==>"+request("signature"));
		return signMd5.equals(request("signature"));

	}

	private String createIpayUrl(String body, long recharId, long userId,
			String tranDateTime, String gatewayType, BigDecimal money)
			throws Exception {
		log.info("12");
		DecimalFormat currentNumberFormat = new DecimalFormat("#0.00");
		ServletContext application = ServletActionContext.getServletContext();
		// 组装接口参数，并进行加密
		Map<String, String> map = new HashMap<String, String>();
		map.put("Mer_code", IPayConfig.ipay_mer_code);
		map.put("Billno", recharId + "");
		map.put("Amount", currentNumberFormat.format(money));
		map.put("Date", tranDateTime);
		map.put("Currency_Type", "RMB");
		map.put("Gateway_Type", gatewayType);
		map.put("Lang", "");
		map.put("Merchanturl", IPayConfig.ipay_mer_chanurl);
		map.put("Attach", Encrypt.encryptSES(""+userId,
				IPayConfig.ipay_see_key)
				+ "");
		map.put("DispAmount", "");
		map.put("OrderEncodeType", "5");
		map.put("RetEncodeType", "17");
		map.put("Rettype", "1");
		map.put("ServerUrl", IPayConfig.ipay_server_url);
		StringBuilder singnMd5 = new StringBuilder();
		singnMd5.append("billno");
		singnMd5.append(map.get("Billno"));
		singnMd5.append("currencytype");
		singnMd5.append(map.get("Currency_Type"));
		singnMd5.append("amount");
		singnMd5.append(map.get("Amount"));
		singnMd5.append("date");
		singnMd5.append(map.get("Date"));
		singnMd5.append("orderencodetype");
		singnMd5.append(map.get("OrderEncodeType"));
		singnMd5.append(IPayConfig.ipay_certificate);

		MD5 md5 = new MD5();

		map.put("SignMD5", md5.toMD5(singnMd5.toString()).toLowerCase());

		return FormUtil.buildHtmlForm(map, IPayConfig.ipay_gateway, "post");
	}

	/**
	 * 环迅支付前端
	 * 
	 * @return
	 * @throws Exception
	 */
	public String merChanUrl() throws Exception {
		log.info("1--frontMerUrl");
		return merServerUrl();
	}

	/**
	 * 环迅支付后端
	 * 
	 * @return
	 * @throws Exception
	 */
	public String merServerUrl() throws Exception {
		log.info("1-----backgroundMerUrl");
		// 环迅支付订单号查询
		// createIpayUrl();
		String respCode = request("succ");// 
		log.info("2--" + respCode);
		if (!"Y".equals(respCode)) {
			log.info("3--");
			createHelpMessage("支付失败！", "返回首页", "index.do");
		}
		log.info("4--" );
		if (!validateSign()) {
			log.info("5--validate sign fail");
			createHelpMessage("支付失败！", "返回首页", "index.do");
		}

		String paybank = "";// 
		if (StringUtils.isBlank(paybank)) {// 无法查询那家银行支付
			paybank = "环迅支付充值";
		}

//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("paybank", paybank);
//		map.put("billno", request("billno"));
//		map.put("mercode", request("mercode"));
//		map.put("amount", request("amount"));
//		map.put("date", request("date"));
//		map.put("ipsbillno", request("ipsbillno"));
//		map.put("attach", request("attach"));
//		map.put("signature", request("signature"));
//		map.put("retencodetype", request("retencodetype"));
//		map.put("succ", request("succ"));
		String attach = request("attach");
		double money = Convert.strToDouble(request("amount"), 0);
		String in_paynumber = request("billno");
		long userId = Convert.strToLong(Encrypt.decryptSES(attach, IPayConfig.ipay_see_key),-1);
		Map<String,String> resultMap = rechargeService.addUseraddmoney(userId, money, in_paynumber, paybank);
		String result = resultMap.get("result");
		String description = resultMap.get("description");
	
		HttpServletResponse httpServletResponse = ServletActionContext
				.getResponse();
		httpServletResponse.setCharacterEncoding("utf-8");
		PrintWriter pw = httpServletResponse.getWriter();
		String msg = description;
		if (!"0".endsWith(result)) {
			log.info("6--");
			pw.println("fail");
			createHelpMessage(msg, "返回首页", "index.do");
		}
		msg = "充值成功";
		pw.println("success");
		log.info("7--");
		createHelpMessage(msg + "", "返回首页", "index.do");

		return null;

	}

	private static long getDistanceTime(Date one, Date two) {// 判断相隔多少天。
		long day = 0;
		long time1 = one.getTime();
		long time2 = two.getTime();
		long diff;
		diff = time1 - time2;
		day = diff / (24 * 60 * 60 * 1000);
		return day;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setRechargeDetailService(
			RechargeDetailService rechargeDetailService) {
		this.rechargeDetailService = rechargeDetailService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

}
