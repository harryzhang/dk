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

public class AllinPaymentAction extends BaseFrontAction {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(AllinPaymentAction.class);
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

	public String allinPayment() throws Exception {
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
			String html = createAllinpayUrl(result + "", "在线充值", userId + "",
					moneyDecimal, request("bankType"));// paymentId_orderId_userId:支付类型(在线支付/在线充值)_订单编号/_用户编号
			sendHtml(html);
			return null;
		} else {
			createHelpMessage("支付失败！", "返回首页", "index.do");
			return null;
		}
	}

	private String createAllinpayUrl(String out_trade_no, String body,
			String extraCommonParam, BigDecimal money, String bankType)
			throws Exception {
		long lmoney = (long) (money.doubleValue() * 100);
		extraCommonParam = com.shove.security.Encrypt.encryptSES(
				extraCommonParam, AlipayConfig.ses_key);
		extraCommonParam = URLEncoder.encode(extraCommonParam, "utf-8");
		String version = AllinpayConfig.alllin_version;// 固定值
		String language = AllinpayConfig.alllin_language;// 1 代表中文显示
		String signType = AllinpayConfig.alllin_signType;// 签名类型,1 代表证书签名验签方式
		String merchantId = AllinpayConfig.alllin_merchantId;// 商户号
		String orderNo = out_trade_no;// 商户订单号
		String orderAmount = lmoney + "";// 商户订单金额
		String orderDatetime = sdf.format(new Date());// 商户订单提交时间
		int payType = 0;
		String issuerId = bankType;
		if (StringUtils.isNotBlank(bankType) && !"DEFAULT".equals(bankType)) {
			payType = 1;
		} else {
			issuerId = "";
		}
		// 支付方式

		String key = AllinpayConfig.alllin_signkey;// 用于计算signMsg的key值

		// ---------------若直连telpshx渠道，payerTelephone、payerName、payerIDCard、pan四个字段不可为空

		String orderCurrency = "0";// 订单金额类型
		String inputCharset = "1";// 字符集 1 代表UTF-8
		String pickupUrl = AllinpayConfig.alllin_pickUrl;// 客户的取货地址
		String receiveUrl = AllinpayConfig.alllin_receiveUrl;// 通知商户网站支付结果的url
		// 地址
		String ext1 = extraCommonParam;// 扩展字段1
		String ext2 = "";// 扩展字段2

		com.allinpay.ets.client.RequestOrder requestOrder = new com.allinpay.ets.client.RequestOrder();
		if (null != inputCharset && !"".equals(inputCharset)) {
			requestOrder.setInputCharset(Integer.parseInt(inputCharset));
		}
		requestOrder.setPickupUrl(pickupUrl);
		requestOrder.setReceiveUrl(receiveUrl);
		requestOrder.setVersion(version);
		if (null != language && !"".equals(language)) {
			requestOrder.setLanguage(Integer.parseInt(language));
		}

		requestOrder.setSignType(Integer.parseInt(signType));
		requestOrder.setMerchantId(merchantId);
		requestOrder.setOrderNo(orderNo);
		requestOrder.setOrderAmount(Convert.strToLong(orderAmount, -1));
		requestOrder.setOrderCurrency(orderCurrency);
		requestOrder.setOrderDatetime(orderDatetime);
		requestOrder.setExt1(ext1);
		requestOrder.setExt2(ext2);
		if (StringUtils.isNotBlank(issuerId)) {
			requestOrder.setIssuerId(issuerId);
		}

		requestOrder.setPayType(payType);
		requestOrder.setKey(key); // key为MD5密钥，密钥是在通联支付网关会员服务网站上设置。

		String strSignMsg = requestOrder.doSign(); // 签名，设为signMsg字段值。

		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("version", version);
		sParaTemp.put("language", language);
		sParaTemp.put("signType", signType);
		sParaTemp.put("merchantId", merchantId);
		sParaTemp.put("orderNo", orderNo);
		sParaTemp.put("orderAmount", orderAmount);
		sParaTemp.put("orderDatetime", orderDatetime);
		sParaTemp.put("payType", payType + "");
		sParaTemp.put("inputCharset", inputCharset);
		sParaTemp.put("pickupUrl", pickupUrl);
		sParaTemp.put("receiveUrl", receiveUrl);
		if (StringUtils.isNotBlank(issuerId)) {
			sParaTemp.put("issuerId", issuerId);
		}
		sParaTemp.put("ext1", ext1);
		sParaTemp.put("ext2", ext2);
		sParaTemp.put("signMsg", strSignMsg);
		sParaTemp.put("orderCurrency", orderCurrency);

		return FormUtil.buildHtmlForm(sParaTemp,
				AllinpayConfig.alllin_gate_way, "post");
	}

	/**
	 * 商城取货地址
	 * 
	 * @return
	 * @throws Exception
	 */
	public String pickUrl() throws Exception {
		request().setCharacterEncoding("UTF-8");
		log.info("--allinpay--1--");
		String merchantId = request().getParameter("merchantId");
		String version = request().getParameter("version");
		String language = request().getParameter("language");
		String signType = request().getParameter("signType");
		String payType = request().getParameter("payType");
		String issuerId = request().getParameter("issuerId");
		String paymentOrderId = request().getParameter("paymentOrderId");
		String orderNo = request().getParameter("orderNo");
		String orderDatetime = request().getParameter("orderDatetime");
		String orderAmount = request().getParameter("orderAmount");
		String payDatetime = request().getParameter("payDatetime");
		String payAmount = request().getParameter("payAmount");
		String ext1 = request().getParameter("ext1");
		String ext2 = request().getParameter("ext2");
		String payResult = request().getParameter("payResult");
		String errorCode = request().getParameter("errorCode");
		String returnDatetime = request().getParameter("returnDatetime");
		String signMsg = request().getParameter("signMsg");

		log.error("errorCode==>" + errorCode);
		com.allinpay.ets.client.PaymentResult paymentResult = new com.allinpay.ets.client.PaymentResult();
		paymentResult.setMerchantId(merchantId);
		paymentResult.setVersion(version);
		paymentResult.setLanguage(language);
		paymentResult.setSignType(signType);
		paymentResult.setPayType(payType);
		paymentResult.setIssuerId(issuerId);
		paymentResult.setPaymentOrderId(paymentOrderId);
		paymentResult.setOrderNo(orderNo);
		paymentResult.setOrderDatetime(orderDatetime);
		paymentResult.setOrderAmount(orderAmount);
		paymentResult.setPayDatetime(payDatetime);
		paymentResult.setPayAmount(payAmount);
		paymentResult.setExt1(ext1);
		paymentResult.setExt2(ext2);
		paymentResult.setPayResult(payResult);
		paymentResult.setErrorCode(errorCode);
		paymentResult.setReturnDatetime(returnDatetime);

		// signMsg为服务器端返回的签名值。
		paymentResult.setSignMsg(signMsg);
		// signType为"1"时，必须设置证书路径。
		String classPath = this.getClass().getResource("/").getPath();

		log.info("--allinpay--2--");
		paymentResult.setCertPath(classPath + "TLCert.cer");
		log.info(classPath + "TLCert.cer");
		// 验证签名：返回true代表验签成功；否则验签失败。
		boolean verifyResult = paymentResult.verify();
		log.info("--verifyResult----" + verifyResult);
		log.info("--payResult----" + payResult);
		// 验签成功，还需要判断订单状态，为"1"表示支付成功。
		boolean paySuccess = verifyResult && payResult.equals("1");
		log.info("--allinpay--2--");
		if (!paySuccess) {
			createHelpMessage("支付失败！", "返回首页", "index.do");
		}

		log.info("--allinpay--3--");
	
		
	

	
		String paynumber = URLDecoder.decode(request("orderNo"), "utf-8");

//		String notify_time = URLDecoder.decode(request("returnDatetime"),
//				"utf-8");

		String paybank = AllinpayConfig.bankMap.get(issuerId);// 支付银行
		if (StringUtils.isBlank(paybank)) {// 如果没有银行编号说明是支付宝直接支付的
			paybank = "通联支付";
		}

//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("extraCommonParam", extraCommonParam);
//		map.put("total_fee", new BigDecimal(payAmount));
//		map.put("paynumber", paynumber);
//		map.put("bankName", paybank);
//		map.put("notify_time", notify_time);
		String attach = request("ext1");
		double money = Convert.strToDouble(payAmount, 0);
		String in_paynumber = request("orderNo");
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

	}

	/**
	 * 通知商户网站支付结果
	 * 
	 * @return
	 * @throws Exception
	 */
	public String receiveUrl() throws Exception {
		return pickUrl();
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}



}
