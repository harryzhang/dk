package com.hehenian.mobile.web.controller.account;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.UserType;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.activity.IActivityOrderService;
import com.hehenian.biz.common.userhome.IUserIncomeService;
import com.hehenian.biz.common.util.huifu.ChinaPnRInterface;
import com.hehenian.common.utils.ResponseUtils;
import com.hehenian.mobile.common.constants.WebConstants;
import com.hehenian.mobile.web.controller.BaseController;
import com.shove.Convert;

/**
 * @Project: hehenian-mobile
 * @Package com.hehenian.mobile.web.controller.account
 * @Title: HFController
 * @Description: 汇付天下
 * @author: chenzhpmf
 * @date 2015年4月27日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/chinapnr")
public class ChinaPNRController extends BaseController {
	
	@Autowired
	private IActivityOrderService activityOrderService;
	
	@Autowired
	private IUserIncomeService userIncomeService;
    
	@Autowired
    private IPersonService personService;
	
	@Autowired
	private IUserService userService;
	
	private static Log logger = LogFactory.getLog(ChinaPNRController.class);
	
	private void checkUserId(String userId) {
		if (StringUtils.isBlank(userId)) {

		}
		
		try {
			Long.parseLong(userId);
		} catch (Exception e) {
			logger.error("无效参数");
		}
	}

	/**
	 * 注册汇付索引页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("index.do")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		String liumi = request.getParameter("liumi");
		// AccountUserDo accountUserDo = getAccountUser();
		// PersonDo personDo = this.personService.getByUserId(accountUserDo.getId());
		// this.userService.updateUserUsrCust(accountUserDo.getId(), accountUserDo.getId(), accountUserDo.getEmail(), personDo.getIdNo(), personDo.getRealName());
		request.getSession().setAttribute("liumi", liumi);
		return "chinapnr/index";
	}

	/**
	 * 汇付注册页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("register")
	public String register(HttpServletRequest request, HttpServletResponse response) {
		String liumi = request.getParameter("liumi");
		request.getSession().setAttribute("liumi", liumi);
		return "chinapnr/register";
	}
	
	/**
	 * 汇付注册填写推荐人
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setMyReferee")
	public String setMyReferee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String reffer = request.getParameter("refferee");
		JSONObject jsonObject = new JSONObject();
		long refferId = -1;
		if (StringUtils.isNotBlank(reffer)) {
			AccountUserDo accountUserDo = userService.findUserByPhone(reffer);
			if (accountUserDo != null) {
				refferId = accountUserDo.getId();
			}
		}
		if (refferId<=0) {
			jsonObject.put("code", "-2");
			ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
			return null;
		}
		
		AccountUserDo user = getAccountUser();
        if (user.getId()==refferId){
        	jsonObject.put("code", "-3");
        	ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
            return null;
        }
		long result = userService.setReferee(refferId + "", user.getId().toString());
		if (result>0) {
			if (StringUtils.isNotBlank(reffer)){
				try {
					userService.saveUserReffer(reffer, user.getId());
					user.setRefferee(refferId+"");
					request.getSession().setAttribute("user", user);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		jsonObject.put("code", result);
    	ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
    	return null;
	}
	
	/**
	 * 订单收益查询接口 接口参数： userId bigInt 非空 20 彩之云用户账号 接口参数：orderSN 字符 非空 32 订单号
	 * 
	 * 返回： userId bigInt 非空 20 彩之云用户账号 orderSN 字符 非空 32 订单号 orderInvestAmount
	 * 保留两位小数Decimal(18,2) 非空 订单投资金额 orderInterestAmount 保留两位小数Decimal(18,2) 非空
	 * 订单利息 　orderWithdrawalAmount 保留两位小数Decimal(18,2) 非空 订单可提取金额
	 * 
	 * @return json 字符串
	 * @author: zhangyunhmf
	 * @date: 2014年10月30日下午2:52:49
	 */
	public String orderDetail(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String orderSN = request.getParameter("orderSN");

		checkUserId(userId);
		if (StringUtils.isBlank(orderSN)) {

		}

		activityOrderService.queryOrderDetail(userId, orderSN, "0");
		return null;
	}

	/**
	 * 总收益查询接口: 接口参数： userId bigInt 非空 20 彩之云用户账号
	 * 
	 * 返回： userId bigInt 非空 20 彩之云用户账号 totalInvestAmount 保留两位小数Decimal(18,2) 非空
	 * 累计投资金额 totalInterestAmount 保留两位小数Decimal(18,2) 非空 累计利息 withdrawalAmount
	 * 保留两位小数Decimal(18,2) 非空 可提取金额
	 * 
	 * @return json 字符
	 * @author: zhangyunhmf
	 * @date: 2014年10月30日下午2:55:31
	 */
	public String userIncome(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		checkUserId(userId);
		userIncomeService.queryUserIncome(userId, UserType.COLOR_LIFE.name());
		return null;

	}

	public String bjbz() {
		return "";
	}
	public String faq(HttpServletRequest request) {
		String liumi = request.getParameter("liumi");
		request.getSession().setAttribute("liumi", liumi);
		return "";
	}
	public String czzy() {
		return "";
	}
	public String home(HttpServletRequest request) {
        AccountUserDo user = (AccountUserDo) request.getSession().getAttribute("user");
		if (user==null) {
			return "login";
		}
		return "";
	}
	public String cfWeb(HttpServletRequest request) {
		request.getSession().setAttribute("platform", "colorlifeweb");
		request.getSession().setAttribute("sourcefrom", WebConstants.SOURCEFROM_COLOURLIFE);
		return "";
	}
	public String cfApp(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("platform", "colorlifeapp");
		session.setAttribute("sourcefrom", WebConstants.SOURCEFROM_COLOURLIFE_APP);
		session.setAttribute("appstyle","cf");
		return "";
	}
	public String heartbeat() {
		return null;
	}
	
	public String appComm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("platform", "appcomm");
        Object via = request.getParameter("via");
        if (via!=null&&StringUtils.isNotBlank(via.toString())){
            if (session.getAttribute("appvia")!=null){
                via = session.getAttribute("appvia").toString();
            }
        }else{
            via = session.getAttribute("appvia");
        }
        if ("jfq".equals(via)){
            via = "11";
        }
		session.setAttribute("appvia",via);
        if (via!=null&&StringUtils.isNotBlank(via.toString())){
            session.setAttribute("sourcefrom", via);
        }

        //设置手机端展示风格
//        int appstyle = Convert.strToInt(request("appstyle"),0);
        String appstyle = request.getParameter("appstyle");
        if (StringUtils.isNotBlank(appstyle)){
            session.setAttribute("appstyle",appstyle);
        }
        if (session.getAttribute("user")!=null){
           return "index";
        }
		return "";
	}
	
	 /**
     * 前台--汇付开户
     */
	@RequestMapping("registerChinaPnr")
    public void registerChinaPnr(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AccountUserDo user = getAccountUser();
        String usrId = user.getId() + "";
        PersonDo personDo = personService.getByUserId(user.getId());
        String usrName = Convert.strToStr(personDo.getRealName(), "");
        String idNo = Convert.strToStr(personDo.getIdNo(), "");
        String usrEmail = Convert.strToStr(user.getEmail(), "");
        request.setAttribute("idNo", idNo);
        String mobilePhone = Convert.strToStr(user.getMobilePhone(), "");
        if (mobilePhone != null && mobilePhone.startsWith("-")){
            mobilePhone = mobilePhone.substring(1,mobilePhone.length());
        }
        /*
         * if(mobilePhone == null || mobilePhone.equals("")){ getOut().print(
         * "<script>alert('您还未完善个人资料,进入个人中心完善个人资料');window.location.href='owerInformationInit.do';</script>"
         * ); return null; }
         * 
         * if(usrName == null || usrName.equals("")){ getOut().print(
         * "<script>alert('您还未完善个人资料,进入个人中心完善个人资料');window.location.href='owerInformationInit.do';</script>"
         * ); return null; } if (idNo == null || idNo.equals("")) {
         * getOut().print(
         * "<script>alert('您还未完善个人资料,进入个人中心完善个人资料');window.location.href='owerInformationInit.do';</script>"
         * ); return null; }
         */
        String cmdId = "UserRegister";

        String html = ChinaPnRInterface.userRegister(cmdId, usrId, usrName, idNo, mobilePhone, usrEmail);
        ResponseUtils.renderText(response, "UTF-8", html);
    }
	
	/**
	 * 汇付 注册 处理
	 * 
	 * @throws Exception
	 */
	private String doUserRegister(String CmdId) throws Exception {
		// 签名和结果验证
		String RespCode = request.getParameter("RespCode");
		try {
			if (Convert.strToInt(RespCode, -1) != 0) {
				return request.getParameter("RespDesc");
			}
		} catch (Exception e) {
			logger.info(e);
			return "注册出现异常";
		}
		String UsrId = Convert.strToStr(request.getParameter("UsrId"), "");// 该字段格式为://
		// xxxx_123456789

		long userId = Convert.strToLong(UsrId.substring(UsrId.indexOf("_") + 1), -1);

		synchronized (userService) {
			AccountUserDo accountUserDo = userService.getById(userId);// 根据用户id查询用户的客户号，如果存在客户号，说明已注册，怎不做注册处理了
			String custId = String.valueOf(accountUserDo.getUsrCustId());
			String email = Convert.strToStr(request.getParameter("UsrEmail"), "");
			try {
				email = URLDecoder.decode(email, "utf-8");
			} catch (Exception e) {
			}
			if (custId.length() == 0) {
				long usrCustId = Convert.strToLong(request.getParameter("UsrCustId"), -1);
				String idNo = Convert.strToStr(request.getParameter("IdNo"), "");
				String realName = Convert.strToStr(request.getParameter("UsrName"), "");
				realName = URLDecoder.decode(realName, "utf-8");
				long ret = -1L;
				ret = userService.updateUserUsrCust(userId, usrCustId, email, idNo, realName);
				if (ret > 0) {
					AccountUserDo userInfo = userService.getById(userId);
					try {
						if (StringUtils.isBlank(String.valueOf(userInfo.getColorid()))) {
							userService.joinHyh(realName, idNo, userId);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					request.getSession().setAttribute("user", userInfo);
					return "注册成功";
				}
				return "注册失败";
			} else {
				try {
					AccountUserDo user = getAccountUser();
					if (user != null) {
						user.setUsrCustId(Long.parseLong(custId));
						user.setEmail(email);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return "注册成功";
	}
	
	/**
	 * 汇付天下 前台调用函数
	 */
	@RequestMapping("frontMerUrl")
	public String frontMerUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("进入前台回调： parameter");
		Map<String, String[]> ret = request.getParameterMap();
		for (String key : ret.keySet()) {
			logger.info(key + "====>" + ret.get(key)[0]);
		}

		String CmdId = request.getParameter("CmdId");// 消息类型
		String result = null;

		if ("CreditAssign".equals(CmdId)) {// 购买债权
			//result = doDebtTender(CmdId);
			//createHelpMessage(result, "返回首页", "index.do");
		} else if ("NetSave".equals(CmdId)) {// 网银充值
			//result = doNetSave(CmdId);
			//createHelpMessage(result, "返回首页", "index.do");
		} else if ("UserRegister".equals(CmdId)) {// 注册
			result = doUserRegister(CmdId);
			//createHelpMessage(result, "返回首页", "index.do");
		} else if ("Cash".equals(CmdId)) {// 取现
			//result = doCash(CmdId);
			//createHelpMessage(result, "返回首页", "index.do");
		} else if ("InitiativeTender".equals(CmdId)) {// 投标
			//result = doInitiativeTender(CmdId);
			//createHelpMessage(result, "返回首页", "index.do");
		} else if ("AutoTenderPlan".equals(CmdId)) {// 开启自动投标计划
			//result = doAutoPlan(2);// 2为自动投标开启状态
			/*
			 * response().sendRedirect("/automaticBidInit.do"); result =
			 * "订单:RECV_ORD_ID_" + request("OrdId");
			 * JSONUtils.printStr2(result); return null;
			 */
			//createHelpMessage(result, "返回首页", "index.do");
		} else if ("AutoTenderPlanClose".equals(CmdId)) {// 关闭自动投标计划
			//result = doAutoPlan(1);// 1为自动投标关闭状态
			/*
			 * response().sendRedirect("/automaticBidInit.do"); result =
			 * "订单:RECV_ORD_ID_" + request("OrdId");
			 * JSONUtils.printStr2(result); return null;
			 */
			//createHelpMessage(result, "返回首页", "index.do");
		}

		// 汇付规范:成功后,需在页面输出 RECV_ORD_ID_+订单号
		if ("UserBindCard".equals(CmdId)) {// 绑卡
			//if (Convert.strToInt(request("RespCode"), -1) == 0) {
			//	doUserBindCard();
			//}
			//result = "订单:RECV_ORD_ID_" + request("TrxId");
		} else if ("PosWhSave".equals(CmdId) || "NetSave".equals(CmdId)
				|| "UserRegister".equals(CmdId)) {
			//result = "订单:RECV_ORD_ID_" + request("TrxId");
		} else if ("CorpRegister".equals(CmdId)) {
			//doCorpRegister();// 企业开户
			result = "订单:RECV_ORD_ID_" + request.getParameter("TrxId");
		} else {
			result = "订单:RECV_ORD_ID_" + request.getParameter("OrdId");
		}

		System.out.println("前台回调返回结果：" + result);
        ResponseUtils.renderText(response, "UTF-8", result);

		return "redirect:/index.do";
	}
}
