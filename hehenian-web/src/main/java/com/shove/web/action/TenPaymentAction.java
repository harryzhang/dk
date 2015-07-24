package com.shove.web.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.config.AllinpayConfig;
import com.shove.config.GopayConfig;
import com.shove.config.TenPayConfig;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.services.AlipayService;
import com.shove.util.AlipayNotify;
import com.shove.util.FormUtil;
import com.shove.web.util.ServletUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.service.RechargeService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SendMessageService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.util.DateUtil;

public class TenPaymentAction extends BaseFrontAction {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(TenPaymentAction.class);
	private RechargeService rechargeService;
	private String urlParam = "";// 接口拼接的参数
	private SelectedService selectedService;
	private SendMessageService sendMessageService;
	private UserService userService;
	private FundManagementService fundManagementService;

	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	public void setFundManagementService(FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public SendMessageService getSendMessageService() {
		return sendMessageService;
	}

	public void setSendMessageService(SendMessageService sendMessageService) {
		this.sendMessageService = sendMessageService;
	}

	public String getUrlParam() {
		return urlParam;
	}

	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}

	public RechargeService getRechargeService() {
		return rechargeService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	/**
	 * 财付通支付
	 * 
	 * @return
	 * @throws Exception
	 */
	public String tenPayment() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);

		String money = request("money");

		if (StringUtils.isBlank(money)) {// 判断是否为空
			return INPUT;
		}
		BigDecimal moneyDecimal;
		try {
			moneyDecimal = new BigDecimal(money);
		} catch (RuntimeException e) {
			return INPUT;
		}
		int temp = moneyDecimal.compareTo(new BigDecimal("0.01"));// 最小金额为0.01元
		if (temp < 0) {
			return INPUT;
		}
	

		String bankType = request("bank_type");
		long userId = this.getUserId();
		// 生成订单
		paramMap.put("rechargeMoney", moneyDecimal + "");
		paramMap.put("userId", userId + "");
		paramMap.put("result", "0");
		Date date = new Date();
		paramMap.put("addTime", DateUtil.dateToString(date));
		Map<String, String> result = rechargeService.addRecharge(paramMap, 3);
		long nunber = Convert.strToInt(result.get("result"), -1);

		if (nunber != -1) {

			String html = createTenUrl(nunber+"", "在线充值", moneyDecimal,
					userId+"", bankType);
			sendHtml(html);
			return null;
		} else {
			createHelpMessage("支付失败！", "返回首页", "index.do");
			return null;
		}
	}

	public String createTenUrl(String out_trade_no, String body,
			BigDecimal money, String attach, String bankType) throws Exception {
		long lmoney = (long) (money.doubleValue() * 100);
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("body", body);
		paraMap.put("bank_type", bankType);
		paraMap.put("service_version", "1.0");
		paraMap.put("sign_type", "MD5");
		paraMap.put("input_charset", "utf-8");
		paraMap.put("notify_url", TenPayConfig.tenpay_online_notifyReceiver);
		paraMap.put("return_url", TenPayConfig.tenpay_online_callBack);
		paraMap.put("fee_type", "1");
		paraMap.put("partner", TenPayConfig.tenpay_online_bargainor_id);
		paraMap.put("total_fee", lmoney + "");
		paraMap.put("out_trade_no", out_trade_no);
		paraMap.put("spbill_create_ip", request().getRemoteAddr());
		attach = com.shove.security.Encrypt.encryptSES(attach,
				AlipayConfig.ses_key);
		paraMap.put("attach", attach);
		String sign = createSign(paraMap);
		paraMap.put("sign", sign);
	
		return FormUtil.buildHtmlForm(paraMap, TenPayConfig.tenpay_online_reqUrl, "post");
	}

	protected String createSign(Map<String, String> para) {
		Map<String, String> parameters = new TreeMap<String, String>(para);
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + TenPayConfig.tenpay_online_key);

		String sign = Encrypt.MD5(sb.toString(), "UTF-8").toLowerCase();

		return sign;

		// debug信息

	}

	

	

	public boolean isTenpaySign(Map<String, String> paraMap) {
		StringBuffer sb = new StringBuffer();
		Map<String, String> parameters = new TreeMap<String, String>(paraMap);
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		sb.append("key=" + TenPayConfig.tenpay_online_key);

		String sign = Encrypt.MD5(sb.toString(), "UTF-8").toLowerCase();

		String tenpaySign = parameters.get("sign").toLowerCase();

		return tenpaySign.equals(sign);
	}

	public String tenPayReceive() throws Exception {
		log.info("----tenPayReceive");
		// 密钥
		String key = TenPayConfig.tenpay_online_key;

		Map<String, String> para = new TreeMap<String, String>();
		Enumeration<String> keys = request().getParameterNames();
		while (keys.hasMoreElements()) {
			String paraName = keys.nextElement();
			para.put(paraName, request(paraName));
		}

		// 判断签名
		if (isTenpaySign(para)) {
			log.info("--tenpay--2--");

			if (!"0".equals(request("trade_state"))) {
				createHelpMessage("支付失败！", "返回首页", "index.do");
			}
			
//			String paynumber = request("");
//			log.info("--tenpay--3--");
	
			String paybank = TenPayConfig.bankMap.get(request("bank_type"));// 支付银行
			if (StringUtils.isBlank(paybank)) {// 
				paybank = "财付通支付";
			}
			// String buyer_email = URLDecoder.decode(request("buyer_email"),
			// "utf-8");// 支付银行
			// String buyer_id = URLDecoder.decode(request("buyer_id"),
			// "utf-8");// 支付银行
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("extraCommonParam", extraCommonParam);
//			map.put("total_fee", new BigDecimal(request("total_fee")));
//			map.put("paynumber", paynumber);
//			map.put("bankName", paybank);
//			// map.put("buyer_email", buyer_email);
//			// map.put("buyer_id", buyer_id);
//			map.put("notify_time", notify_time);
			String attach = request("attach");
			double money = Convert.strToDouble(request("total_fee"), 0);
			String in_paynumber = request("out_trade_no");
			long userId = Convert.strToLong(Encrypt.decryptSES(attach,
					GopayConfig.gopay_see_key), -1);
			Map<String, String> resultMap = rechargeService.addUseraddmoney(userId,
					money, in_paynumber, paybank);
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
			return "message";
		} else {

			createHelpMessage("充值失败", "返回首页", "index.do");

		}
		return "message";

	}

	

	// 暗调
	public String tenPayNotify() throws Exception {
		log.info("----tenPayNotify");
		Map<String, String> rechargeMap = null;
		try {
			tenPayReceive();
		} catch (Exception e) {

		} finally {
			String extra_common_param = request("attach");// 获得参数信息
			// 支付类型_订单编号/金钱_用户编号
			if (StringUtils.isBlank(extra_common_param)) {
				response().getWriter().write("fail");
				log.info("--pay fail");
			}
			extra_common_param = URLDecoder.decode(extra_common_param, "utf-8");
			extra_common_param = com.shove.security.Encrypt.decryptSES(
					extra_common_param, AlipayConfig.ses_key);
			String[] extraCommonParam = extra_common_param.split("_");
			if (extraCommonParam == null || extraCommonParam.length != 2) {
				response().getWriter().write("fail");
				log.info("--pay fail");
			}
			rechargeMap = rechargeService.getRechargeDetail(Convert.strToLong(
					extraCommonParam[0], -1));
			if (rechargeMap != null) {
				if ("1".equals(rechargeMap.get("result"))) {
					response().getWriter().write("success");
					log.info("--pay success");
				} else {
					response().getWriter().write("fail");
					log.info("---pay fail");
				}
			} else {
				response().getWriter().write("fail");
				log.info("--pay fail");
			}

		}
		return null;
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


}
