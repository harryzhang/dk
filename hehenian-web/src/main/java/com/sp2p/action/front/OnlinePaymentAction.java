package com.sp2p.action.front;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.data.DataException;
import com.shove.services.AlipayService;
import com.shove.util.AlipayNotify;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.RechargeService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SendMessageService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.util.DateUtil;

public class OnlinePaymentAction extends BaseFrontAction{

	private static Log log = LogFactory.getLog(OnlinePaymentAction.class);
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

	// 在线充值
	public String alipayPayment() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		
		String money = SqlInfusion.FilteSqlInfusion(request("money"));
		
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
		long userId = user.getId();
		// 生成订单
		paramMap.put("rechargeMoney", moneyDecimal + "");
		paramMap.put("userId", userId + "");
		paramMap.put("result", "0");
		paramMap.put("addTime", DateUtil.dateToString(new Date()));
		
		//ip地址
		String ipAddress = ServletUtils.getRemortIp();
		paramMap.put("ipAddress", ipAddress);
		
		Map<String,String> result = rechargeService.addRecharge(paramMap,2);//调用存储过程
		
		int nunber  = Convert.strToInt(result.get("result"),-1);
		if ( nunber!= -1) {
			Map<String, String> map = rechargeService
					.getRechargeDetail(nunber);

			String html = createUrl(map.get("rechargeNumber"), "在线充值", result
					+ "_" + userId, moneyDecimal);// paymentId_orderId_userId:支付类型(在线支付/在线充值)_订单编号/_用户编号
			this.response().setContentType("text/html");
			response().setCharacterEncoding("utf-8");
			PrintWriter out = response().getWriter();
			out.println("<HTML>");
			out.println(" <HEAD><TITLE>sender</TITLE></HEAD>");
			out.println(" <BODY>");
			out.print(html);
			out.println(" </BODY>");
			out.println("</HTML>");
			out.flush();
			out.close();
			return null;
		} else {
			createHelpMessage("支付失败！", "返回首页", "index.do");
			return null;
		}
	}
	
	private String createUrl(String out_trade_no, String body,
			String extraCommonParam, BigDecimal money) throws Exception {
		log.info("12");
		// 组装接口参数，并进行加密
		Map<String, String> sPara = new HashMap<String, String>();
		sPara.put("_input_charset", AlipayConfig.input_charset);
		sPara.put("subject", "桂林市合和年信贷充值编号:" + out_trade_no);
		sPara.put("total_fee", money.toString() + "");
		sPara.put("service", "create_direct_pay_by_user");
		sPara.put("notify_url", AlipayConfig.notify_url);
		sPara.put("partner", AlipayConfig.partner);
		sPara.put("seller_email", AlipayConfig.seller_email);
		sPara.put("out_trade_no", out_trade_no);
		sPara.put("payment_type", "1");
		sPara.put("return_url", AlipayConfig.return_url);
		extraCommonParam = com.shove.security.Encrypt.encryptSES(
				extraCommonParam, AlipayConfig.ses_key);
		extraCommonParam = URLEncoder.encode(extraCommonParam, "utf-8");
		sPara.put("extra_common_param", extraCommonParam);
		String html = AlipayService.create_direct_pay_by_user(sPara);
		return html;
	}
	
	// 回调方法：明

	public String alipayReceive() throws Exception {
		log.info("alipayReceive");
		Map<String, String> params = new HashMap<String, String>();//
		// trade_no订单流水号
		// notify_time支付回调时间
		Map requestParams = request().getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		boolean verify_result = AlipayNotify.verify(params);// 验证参数是否是支付宝那边返回过来的。
		if (!verify_result) {
			createHelpMessage("支付失败！", "返回首页", "index.do");
		}
		String extra_common_param = request("extra_common_param");// 获得参数信息
		// 支付类型_订单编号/金钱_用户编号
		if (StringUtils.isBlank(extra_common_param)) {
			createHelpMessage("支付失败！", "返回首页", "index.do");
		}
		extra_common_param = URLDecoder.decode(extra_common_param, "utf-8");
		extra_common_param = com.shove.security.Encrypt.decryptSES(
				extra_common_param, AlipayConfig.ses_key);
		String[] extraCommonParam = extra_common_param.split("_");
		if (extraCommonParam == null || extraCommonParam.length != 2) {
			// 通过"_"进行截取，判断是否符合规范
			createHelpMessage("支付失败！", "返回首页", "index.do");
		}
		String sellerEmail = SqlInfusion.FilteSqlInfusion(request("seller_email"));// 商户邮箱
		if (!sellerEmail.equals(AlipayConfig.seller_email)) {// 比较商户邮箱看是否符合
			createHelpMessage("支付失败！", "返回首页", "index.do");
		}
		String paynumber = URLDecoder.decode(SqlInfusion.FilteSqlInfusion(request("trade_no")), "utf-8");
		// 支付宝编号
		String notify_time = URLDecoder.decode(SqlInfusion.FilteSqlInfusion(request("notify_time")), "utf-8");// 支付宝编号
		String paybank = null;// 支付银行
		if (StringUtils.isBlank(paybank)) {// 如果没有银行编号说明是支付宝直接支付的
			paybank = "支付宝余额支付";
		}
		String buyer_email = URLDecoder.decode(SqlInfusion.FilteSqlInfusion(request("buyer_email")), "utf-8");// 支付银行
		String buyer_id = URLDecoder.decode(SqlInfusion.FilteSqlInfusion(request("buyer_id")), "utf-8");// 支付银行
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("extraCommonParam", extraCommonParam);
		map.put("total_fee", new BigDecimal(SqlInfusion.FilteSqlInfusion(request("total_fee"))));
		map.put("paynumber", paynumber);
		map.put("bankName", paybank);
		map.put("buyer_email", buyer_email);
		map.put("buyer_id", buyer_id);
		map.put("notify_time", notify_time);
		int returnId =-1;
		try {
			returnId = rechargeService.userPay(map);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		HttpServletResponse httpServletResponse = ServletActionContext
				.getResponse();
		httpServletResponse.setCharacterEncoding("utf-8");
		PrintWriter pw = httpServletResponse.getWriter();
		String msg = "";
		if (returnId < 0) {
			pw.println("fail");
			if (returnId == -1) {
				msg = "用户不存在";
			} else if (returnId == -2) {
				msg = "支付号错误";
			} else if (returnId == -3) {
				msg = "此笔支付记录已经被处理过";
			} else if (returnId == -4) {
				msg = "充值金额与本地记录中的金额不一致";
			} else if (returnId == -5) {
				msg = "充值金额错误";
			} else if (returnId == -6) {
				msg = "支付详细不存在";
			} else if (returnId == -7) {
				msg = "订单支付明细，状态修改失败。";
			} else {
				msg = "操作错误！";
			}
			createHelpMessage(msg, "返回首页", "index.do");
		}else{
			//根据用户的通知设置，进行邮件、短信、站内信的通知
			Long userId = Convert.strToLong(extraCommonParam[1], -1);// 获得用户编号
			sendMessage(userId,Convert.strToDouble(request("total_fee"),0),0);
			//------------------
			
		}

		msg = "交易成功！";
		pw.println("success");
		createHelpMessage(msg + "", "返回首页", "index.do");
		return "message";
	}
	
	private void sendMessage(Long userId,double total,double money) throws SQLException, DataException, UnsupportedEncodingException{
		try{
			String title = "资金变动提醒";
			//查找通知类型的通知状态
			List<Map<String,Object>> lists = 
				selectedService.queryNoticeMode(userId, IConstants.NOTICE_MODE_4);
			if(lists != null && lists.size()>0 ){
				String content = "你已成功从桂林市合和年信贷充值￥"
					+total+"元，扣除手续费后到账金额为￥"+money+"元，请注意查收!";
				//[通知方式(1 邮件 2 站内信 3 短信]
				if(lists.get(0).get("flag").toString().equals(String.valueOf(IConstants.NOTICE_ON))){
					sendMessageService.emailSend(title, content, userId);
				}
				if(lists.get(1).get("flag").toString().equals(String.valueOf(IConstants.NOTICE_ON))){
					sendMessageService.mailSend(title, content, userId);
				}
				if(lists.get(2).get("flag").toString().equals(String.valueOf(IConstants.NOTICE_ON))){
					Map<String,String> userMap = userService.queryUserById(userId);
					if(userMap != null){
						Long result = sendMessageService.noteSend(content, userId);
					}else{
						if(money > IConstants.NOTE_CHARGE){
							Long result = sendMessageService.noteSend(content, userId);
							if(result > 0){//信息发送成功，更新资金记录表
								Long result2 = fundManagementService.updateFundrecord(userId,IConstants.NOTE_CHARGE,IConstants.WITHDRAW);
								if(result2 > 0){
									Map<String,String> uMap = userService.queryUserById(userId);
									Map<String,String> map = new HashMap<String,String>();
									map.put("handleSum", String.valueOf(IConstants.NOTE_CHARGE));
									map.put("usableSum", uMap.get("usableSum"));
									map.put("freezeSum", uMap.get("freezeSum"));
									map.put("dueinSum", uMap.get("dueinSum"));
									map.put("dueOutSum", uMap.get("dueOutSum"));
									map.put("fundMode", "扣除短信服务费");
									map.put("remarks", "扣除短信服务费");
									fundManagementService.addFundRecord(userId,map);
								}
							}
						}
					}
				}
			}
		}catch(SQLException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch(DataException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	// 回调方法：暗
	public String alipayNotify() throws Exception {
		log.info("alipayNotify");
		return alipayReceive();

	}
	
	public String alipayNotify_back() throws Exception {
		log.info("alipayNotify_back");
		return alipayNotify_back();

	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
