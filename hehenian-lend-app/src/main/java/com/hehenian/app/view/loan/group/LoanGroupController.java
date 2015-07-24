package com.hehenian.app.view.loan.group;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.filesaving.IFileServerService;
import com.hehenian.biz.common.loan.ICommonService;
import com.hehenian.biz.common.loan.ILoanApplyService;
import com.hehenian.biz.common.loan.ILoanChannelService;
import com.hehenian.biz.common.loan.ILoanPersonService;
import com.hehenian.biz.common.loan.ILoanUserService;
import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.JobDo.JobType;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo.LoanStatus;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanProductDo;
import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.notify.dataobject.LoanSMSNotifyDo;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.trade.ISettleCalculatorService;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.HttpClientUtils;
import com.hehenian.biz.common.util.IdCardUtils;
import com.hehenian.biz.common.util.Md5Utils;
import com.hehenian.web.common.constant.WebConstants;

/**
 * @Description 集团贷款申请控制器
 * @author huangzl QQ: 272950754
 * @date 2015年6月8日 上午10:05:42
 * @Project hehenian-lend-app
 * @Package com.hehenian.app.view.loan.group
 * @File LoanGroupController.java
 */
@Controller
@RequestMapping(value = "/app/group")
public class LoanGroupController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IUserService accountUserService;
	@Autowired
	private IPersonService personService;

	@Autowired
	private ILoanApplyService loanApplyService;
	@Autowired
	private ILoanPersonService loanPersonService;
	@Autowired
	private ILoanUserService loanUserService;
	@Autowired
	private ILoanChannelService loanChannelService;
	@Autowired
	private ISettleCalculatorService settleCalculatorService;
	@Autowired
	private INotifyService smsNotifyService;

	@Autowired
	private ICommonService commonService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IFileServerService fileServerService;
	
	@Value("#{appconfig['color.life.AG.URL']}")
	private String colorLifeAG_URL;
	@Value("#{appconfig['color.life.AG.COLOR.APP.ID']}")
    private String COLOR_APP_ID;
	@Value("#{appconfig['color.life.AG.COLOR.TOKEN']}")
    private String COLOR_TOKEN;

	/**
	 * 获取彩生活登录认证
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAuth")
	//@ResponseBody
	public String getAuth( HttpSession session, HttpServletRequest request) {
		long currentTime  = System.currentTimeMillis()/1000;
		String ts = String.valueOf(currentTime);
		//sign=MD5($appID+$ts+$token+false)
		String sign = Md5Utils.MD5(COLOR_APP_ID+ts+COLOR_TOKEN+"false");
		String openId = request.getParameter("openID");
		String token =request.getParameter("accessToken");
		System.out.println("ts="+ts+";sign="+sign+";");
		Map<String,String> params = new HashMap<String,String>(10);
		params.put("openID", openId);
		params.put("accessToken", token);
		
		try {
			StringBuffer url = new StringBuffer();
			url.append(colorLifeAG_URL).append("?sign=").append(sign).append("&ts=").append(ts).append("&appID=").append(COLOR_APP_ID);
			String result = HttpClientUtils.post(url.toString(), params);
			logger.info("彩生活认证结果:"+result);
			ObjectMapper mapper = new ObjectMapper();
			Map<Object, Object> params1 = mapper.readValue(result.toString(), new TypeReference<HashMap<Object, Object>>() {});
			int code =Integer.valueOf(params1.get("code").toString());
			if(code==0){
				if(params1.get("content").toString().length()>0){
					Map<Object, Object> contentTemp=(Map<Object, Object>) params1.get("content");
//				//注册
//				IResult<AccountUserDo> registerResult = userService.register(-1, "cgj_"+contentTemp.get("username").toString(),contentTemp.get("mobile").toString(), DigestUtils.md5Hex(contentTemp.get("mobile").toString() + WebConstants.PASS_KEY), 100,-1L);
//				AccountUserDo user = registerResult.getModel();
//				if (registerResult.isSuccess()) {
////					userService.updatePerson(user, realName, idNo, mobile);
////					userService.updateColourlifeInfo(user.getId(), Long.valueOf(null == sourceUserId ? "-1" : sourceUserId), cid, cname, caddress);
//				}
				AccountUserDo user = new AccountUserDo();
				user.setUsername(contentTemp.get("username")==null?"":contentTemp.get("username").toString());
				//放入session
				session.setAttribute("user", user);
				session.setAttribute("contentTemp", contentTemp);
				}else{
					session.setAttribute(WebConstants.MESSAGE_KEY,"财管家数据异常");
					return "common/notify_message";
				}
			}else{
				String message =params1.get("message").toString();
//				String contentEncrypt =params1.get("contentEncrypt").toString();
				session.setAttribute(WebConstants.MESSAGE_KEY,message);
				return "common/notify_message";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "app/group/index";
	}
	
//	/**
//	 * 获取彩管家登录认证
//	 * @param session
//	 * @param request
//	 * @return
//	 */
//	@SuppressWarnings("unused")
//	@RequestMapping(value = "getAuth")
//	//@ResponseBody
//	public String getAuth(HttpSession session, HttpServletRequest request) {
//		return "app/group/index";
//	}
//	/**
//	 * 借款申请首页
//	 * 
//	 * @param colorId
//	 * @param cid
//	 * @param cName
//	 * @param caddress
//	 * @param mobile
//	 * @param model
//	 * @return
//	 * @author: huangzl
//	 * @date: 2015年6月8日 19:13:59
//	 */
//	@SuppressWarnings("deprecation")
//	@RequestMapping("/index")
//	public String index(@RequestParam("userid") String sourceUserId, String username, Long cid, String cname, String caddress, String mobile, Integer channelType, String realName, String idNo, Model model, HttpSession session, HttpServletRequest request) {
//		realName = null == realName ? "" : URLDecoder.decode(realName);
//		idNo = null == idNo ? "" : URLDecoder.decode(idNo);
//		username = null == username ? "" : URLDecoder.decode(username);
//		cname = null == cname ? "" : URLDecoder.decode(cname);
//		caddress = null == caddress ? "" : URLDecoder.decode(caddress);
//		logger.warn("渠道类型ID：" + channelType + "，用户ID：" + sourceUserId + "，小区ID：" + cid + "，小区名称：" + cname + "，小区地址：" + caddress + "，手机号码：" + mobile + "，真实姓名：" + realName + "，身份证号码：" + idNo);
//		IResult<AccountUserDo> result = userService.register(-1, username, mobile, DigestUtils.md5Hex(mobile + WebConstants.PASS_KEY), 100, Long.valueOf(null == sourceUserId ? "-1" : sourceUserId));
//		AccountUserDo user = result.getModel();
//		if (result.isSuccess()) {
//			userService.updatePerson(user, realName, idNo, mobile);
//			userService.updateColourlifeInfo(user.getId(), Long.valueOf(null == sourceUserId ? "-1" : sourceUserId), cid, cname, caddress);
//		}
//
//		if (StringUtils.isBlank(sourceUserId)) {
//			model.addAttribute(WebConstants.MESSAGE_KEY, "请求参数有误，请联系客服!");
//			return "app/group/index";
//		}
//		session.setAttribute("user", user);
//		if (channelType == null || channelType.intValue() < 1) {
//			channelType = 1; // 彩生活
//		}
//		realName = user.getPerson().getRealName();
//		idNo = user.getPerson().getIdNo();
//		LoanChannelDo loanChannelDo = new LoanChannelDo();
//		LoanUserDo loanUserDo = new LoanUserDo();
//		loanChannelDo.setChannelType(channelType);
//		loanChannelDo.setSourceUserId(sourceUserId);
//		loanUserDo.setCid(cid);
//		loanUserDo.setLevel(1);
//		try {
//			if (StringUtils.isNotBlank(cname)) {
//				loanUserDo.setCname(URLDecoder.decode(cname, "utf-8"));
//			}
//			if (StringUtils.isNotBlank(caddress)) {
//				loanUserDo.setCaddress(URLDecoder.decode(caddress, "utf-8"));
//			}
//			if (StringUtils.isNotBlank(realName)) {
//				loanUserDo.setName(URLDecoder.decode(realName, "utf-8"));
//			}
//			if (StringUtils.isNotBlank(idNo)) {
//				loanUserDo.setIdNo(idNo);
//			}
//			loanUserDo.setIdNo(idNo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		loanUserDo.setMobile(mobile);
//
//		loanChannelDo.setLoanUserDo(loanUserDo);
//		// 隐藏手机号码和身份证号码部分号码
//		if (StringUtils.isNotBlank(mobile) && mobile.length() >= 7) {
//			loanUserDo.setMobile(mobile);
//			mobile = mobile.substring(0, 4) + "****" + mobile.substring(mobile.length() - 4);
//			loanUserDo.setMobileShort(mobile);
//		}
//		if (StringUtils.isNotBlank(idNo) && idNo.length() >= 7) {
//			loanUserDo.setIdNo(idNo);
//			idNo = idNo.substring(0, 4) + "***********" + idNo.substring(idNo.length() - 4);
//			loanUserDo.setIdNoShort(idNo);
//		}
//		loanChannelDo.setLoanUserDo(loanUserDo);
//		session.setAttribute("loanChannelDo", loanChannelDo);
//		setLoanCount(session);
//		return "app/group/index";
//	}
//	/**
//	 * 借款申请首页
//	 * 
//	 * @param colorId
//	 * @param cid
//	 * @param cName
//	 * @param caddress
//	 * @param mobile
//	 * @param model
//	 * @return
//	 * @author: huangzl
//	 * @date: 2015年6月8日 19:13:59
//	 */
//	@SuppressWarnings("deprecation")
//	@RequestMapping("/index")
//	public String index(@RequestParam("userid") String sourceUserId, String username, Long cid, String cname, String caddress, String mobile, Integer channelType, String realName, String idNo, Model model, HttpSession session, HttpServletRequest request) {
//		realName = null == realName ? "" : URLDecoder.decode(realName);
//		idNo = null == idNo ? "" : URLDecoder.decode(idNo);
//		username = null == username ? "" : URLDecoder.decode(username);
//		cname = null == cname ? "" : URLDecoder.decode(cname);
//		caddress = null == caddress ? "" : URLDecoder.decode(caddress);
//		logger.warn("渠道类型ID：" + channelType + "，用户ID：" + sourceUserId + "，小区ID：" + cid + "，小区名称：" + cname + "，小区地址：" + caddress + "，手机号码：" + mobile + "，真实姓名：" + realName + "，身份证号码：" + idNo);
//		IResult<AccountUserDo> result = userService.register(-1, username, mobile, DigestUtils.md5Hex(mobile + WebConstants.PASS_KEY), 100, Long.valueOf(null == sourceUserId ? "-1" : sourceUserId));
//		AccountUserDo user = result.getModel();
//		if (result.isSuccess()) {
//			userService.updatePerson(user, realName, idNo, mobile);
//			userService.updateColourlifeInfo(user.getId(), Long.valueOf(null == sourceUserId ? "-1" : sourceUserId), cid, cname, caddress);
//		}
//		// 判断小区是否开通
//		List<Map<String, Long>> testAreaList = loanApplyService.getTestAreaName();
//		boolean isUsable = false;
//		if (testAreaList != null && cid != null) {
//			for (Map<String, Long> m : testAreaList) {
//				if (m != null) {
//					Long isUsableCid = m.get("cid");
//					if (isUsableCid == null) {
//						continue;
//					}
//					if (isUsableCid.longValue() == cid.longValue()) {
//						isUsable = true;
//						break;
//					}
//				}
//			}
//			if (false == isUsable) {
//				return "app/group/notify_message";
//			}
//		}
//		// end 判断小区是否开通
//		
//		if (StringUtils.isBlank(sourceUserId)) {
//			model.addAttribute(WebConstants.MESSAGE_KEY, "请求参数有误，请联系客服!");
//			return "app/group/index";
//		}
//		session.setAttribute("user", user);
//		if (channelType == null || channelType.intValue() < 1) {
//			channelType = 1; // 彩生活
//		}
//		realName = user.getPerson().getRealName();
//		idNo = user.getPerson().getIdNo();
//		LoanChannelDo loanChannelDo = new LoanChannelDo();
//		LoanUserDo loanUserDo = new LoanUserDo();
//		loanChannelDo.setChannelType(channelType);
//		loanChannelDo.setSourceUserId(sourceUserId);
//		loanUserDo.setCid(cid);
//		loanUserDo.setLevel(1);
//		try {
//			if (StringUtils.isNotBlank(cname)) {
//				loanUserDo.setCname(URLDecoder.decode(cname, "utf-8"));
//			}
//			if (StringUtils.isNotBlank(caddress)) {
//				loanUserDo.setCaddress(URLDecoder.decode(caddress, "utf-8"));
//			}
//			if (StringUtils.isNotBlank(realName)) {
//				loanUserDo.setName(URLDecoder.decode(realName, "utf-8"));
//			}
//			if (StringUtils.isNotBlank(idNo)) {
//				loanUserDo.setIdNo(idNo);
//			}
//			loanUserDo.setIdNo(idNo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		loanUserDo.setMobile(mobile);
//		
//		loanChannelDo.setLoanUserDo(loanUserDo);
//		// 隐藏手机号码和身份证号码部分号码
//		if (StringUtils.isNotBlank(mobile) && mobile.length() >= 7) {
//			loanUserDo.setMobile(mobile);
//			mobile = mobile.substring(0, 4) + "****" + mobile.substring(mobile.length() - 4);
//			loanUserDo.setMobileShort(mobile);
//		}
//		if (StringUtils.isNotBlank(idNo) && idNo.length() >= 7) {
//			loanUserDo.setIdNo(idNo);
//			idNo = idNo.substring(0, 4) + "***********" + idNo.substring(idNo.length() - 4);
//			loanUserDo.setIdNoShort(idNo);
//		}
//		loanChannelDo.setLoanUserDo(loanUserDo);
//		session.setAttribute("loanChannelDo", loanChannelDo);
//		setLoanCount(session);
//		return "app/group/index";
//	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String goElendWelcome() {
		return "app/group/index";
	}

	/**
	 * 个人信息填写页面
	 * 
	 * @param userId
	 * @param loanId
	 * @param model
	 * @param session
	 * @return
	 * @author: huangzl
	 * @date: 2015年6月8日 19:13:42
	 */
	@RequestMapping(value = "/personalInfo", method = RequestMethod.GET)
	public String saveLoanPerson(Model model, HttpSession session) {
		return "app/group/person_update";
	}

	/**
	 * 个人信息填写页面
	 * 
	 * @param userId
	 * @param loanPersonDo
	 * @param model
	 * @return
	 * @author: huangzl
	 * @date: 2015年6月8日 19:13:34
	 */
	@RequestMapping(value = "/personalInfo", method = RequestMethod.POST)
	public String saveLoanPerson(@ModelAttribute LoanPersonDo loanPersonDo, @RequestParam String company,@RequestParam String dept,Model model, HttpSession session) {
//		LoanChannelDo loanChannelDo = (LoanChannelDo) session.getAttribute("loanChannelDo");
//		if (loanChannelDo != null) {
//			loanPersonDo.setColorId(loanChannelDo.getSourceUserId());
//			loanPersonDo.setCid(loanChannelDo.getLoanUserDo().getCid());
//			loanPersonDo.setCname(loanChannelDo.getLoanUserDo().getCname());
//			loanPersonDo.setCaddress(loanChannelDo.getLoanUserDo().getCaddress());
//		}
		loanPersonDo.setAge(IdCardUtils.getAgeByIdCard(loanPersonDo.getIdNo()));
//		try {
//			if (StringUtils.isNotBlank(loanPersonDo.getRealName())) {
//					loanPersonDo.setRemark(URLDecoder.decode(loanPersonDo.getRealName(), "utf-8"));
//			}
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		session.setAttribute("loanPersonDo", loanPersonDo);
		return "app/group/loan_update";
	}

	/**
	 * 计算结算明细
	 * 
	 * @param loanAmount
	 * @param annualRateArr
	 *            月利率 逗号隔开
	 * @param loanPeriod
	 * @param schemeIdArr
	 *            还款方式 逗号隔开
	 * @return
	 * @author: huangzl
	 * @date: 2015年6月8日 19:13:23
	 */
	@RequestMapping(value = "/calSettDetail", method = RequestMethod.GET)
	@ResponseBody
	public IResult<?> calSettDetail(Double loanAmount, Integer loanPeriod) {
		IResult<Object> result = new ResultSupport<Object>();
		if (loanAmount == null || CalculateUtils.le(loanAmount, 0d)) {
			return new ResultSupport<Integer>(false, "借款金额有误!");
		}
		String loanAmoutString = new DecimalFormat("###0.#").format(loanAmount);
		if (Long.valueOf(loanAmoutString) % 100 != 0) {
			result.setSuccess(false);
			result.setErrorMessage("请确认金额为100的整数倍！");
			result.setResultCode("money");
			return result;
		}
		if (loanAmoutString.length() > 11) {
			result.setSuccess(false);
			result.setErrorMessage("输入金额不能超过999亿！");
			result.setResultCode("money");
			return result;
		}
		if (loanPeriod == null || loanPeriod.intValue() <= 0) {
			return new ResultSupport<Integer>(false, "借款期限有误!");
		}


		DecimalFormat df = new DecimalFormat("##0.00");
		List<List<String>> repayAmountAllList = new ArrayList<List<String>>();
			Long schemeId = 5l;
			Double annualRate = 18.0;
			List<SettDetailDo> settDetailDoList = settleCalculatorService.calSettDetail(loanAmount, annualRate, loanPeriod, schemeId);
			List<String> repayAmountList = new ArrayList<String>();
			System.out.println("===================================start repay scheme ============================================");
			for (int k = 0; k < settDetailDoList.size(); k++) {
				SettDetailDo settDetailDo = settDetailDoList.get(k);
				System.out.println(settDetailDo);
				Double repayAmount = CalculateUtils.add(CalculateUtils.add(CalculateUtils.add(settDetailDo.getPrincipal(), settDetailDo.getInterest()), settDetailDo.getServFee() == null ? 0 : settDetailDo.getServFee()), settDetailDo.getConsultFee() == null ? 0 : settDetailDo.getConsultFee());
				repayAmountList.add(df.format(repayAmount));
			}
			System.out.println("===================================end repay scheme ============================================");
			repayAmountAllList.add(repayAmountList);

		result.setSuccess(true);
		result.setModel(repayAmountAllList);
		return result;
	}

	/**
	 * 计算授信金额
	 * 
	 * @return
	 * @author: huangzlmf
	 * @date: 2015年4月21日 12:49:05
	 */
	@RequestMapping("/calCreditAmountGroup")
	@ResponseBody
	public Map<String, Object> calCreditAmountGroup(String income, String loanPeriod) {
		logger.info("----Start:calCreditAmountGroup;income=" + income + ";loanPeriod=" + loanPeriod + ";");
		Map<String, Object> map = new HashMap<String, Object>();
		// 如果月收入或借款期限为空，则返回错误信息
		if (StringUtils.isBlank(income) || StringUtils.isBlank(loanPeriod)) {
			map.put("error", true);
			map.put("message", "请求非法!");
			logger.info("----End:calCreditAmountGroup;income=" + income + ";loanPeriod=" + loanPeriod + ";message=请求非法!");
			return map;
		}
		double incomeD = Double.parseDouble(income);
		int loanPeriodI = Integer.parseInt(loanPeriod);
		loanPeriodI = (loanPeriodI < 6 ? 3 : 6);//
		// 借款期限小于6个月，则按3个月计算，大于等于6个月，则按6个月计算
		int index = 1;
		if (CalculateUtils.le(incomeD, 3000)) {
			index = 1;
		} else if (CalculateUtils.le(incomeD, 5000)) {
			index = 2;
		} else if (CalculateUtils.le(incomeD, 8000)) {
			index = 3;
		} else if (CalculateUtils.le(incomeD, 10000)) {
			index = 4;
		} else if (CalculateUtils.gt(incomeD, 10000)) {
			index = 5;
		}
		index = (loanPeriodI == 6 ? (index + 5) : index);
		double creditAmount = CalculateUtils.round(CalculateUtils.mul(incomeD, creditRateMap.get(index)), 0);
		// 借款期限为6个月，最高可借贷100000元
		if (loanPeriodI == 6 && CalculateUtils.gt(creditAmount, 100000)) {
			creditAmount = 100000;
		}
		// 借款期限为3个月，最高可借贷70000元
		if (loanPeriodI == 3 && CalculateUtils.gt(creditAmount, 70000)) {
			creditAmount = 70000;
		}
		map.put("error", false);
		map.put("creditAmount", creditAmount);
		logger.info("----End:calCreditAmountGroup;income=" + income + ";loanPeriod=" + loanPeriod + ";");
		return map;
	}
	
	
	/**
	 * 借款申请页面
	 * 
	 * @param loanDo
	 * @param model
	 * @return
	 * @author: huangzl
	 * @date: 2015年6月8日 19:13:12
	 */
	@RequestMapping(value = "/applyfor", method = RequestMethod.POST)
	public String saveLoanDetail(@ModelAttribute LoanDo loanDo, @RequestParam String hasHouse, Model model, HttpServletRequest request, HttpSession session) {
		if (loanDo.getApplyAmount() == null || CalculateUtils.le(loanDo.getApplyAmount(), 0d)) {
			model.addAttribute("resultCode", "money");
			model.addAttribute(WebConstants.MESSAGE_KEY, "借款金额有误！");
			return "app/group/loan_update";
		}
		String loanAmoutString = new DecimalFormat("###0.#").format(loanDo.getApplyAmount());
		if (Long.valueOf(loanAmoutString) % 100 != 0) {
			model.addAttribute(WebConstants.MESSAGE_KEY, "请确认金额为100的整数倍！");
			model.addAttribute("resultCode", "money");
			return "app/group/loan_update";
		}
		if (loanAmoutString.length() > 11) {
			model.addAttribute(WebConstants.MESSAGE_KEY, "输入金额不能超过999亿！");
			model.addAttribute("resultCode", "money");
			return "app/group/loan_update";
		}
		// 借款期限为3个月，最高可借贷70000元
		if (loanDo.getLoanPeriod()  == 3 && CalculateUtils.gt(loanDo.getApplyAmount(), 70000)) {
			model.addAttribute(WebConstants.MESSAGE_KEY, "借款期限为3个月，最高可借贷70000元");
			model.addAttribute("resultCode", "money");
			return "app/group/loan_update";
		}
		// 借款期限为6个月，最高可借贷100000元
		if (loanDo.getLoanPeriod() == 6 && CalculateUtils.gt(loanDo.getApplyAmount(), 100000)) {
			model.addAttribute(WebConstants.MESSAGE_KEY, "借款期限为6个月，最高可借贷100000元");
			model.addAttribute("resultCode", "money");
			return "app/group/loan_update";
		}
		
		
		LoanPersonDo loanPersonDo = (LoanPersonDo) session.getAttribute("loanPersonDo");
		loanPersonDo.setHasHouse(hasHouse);
		String idNo=loanPersonDo.getIdNo();
		loanPersonDo.setAge(IdCardUtils.getAgeByIdCard(idNo));
		String sexString = IdCardUtils.getGenderByIdCard(idNo);
		loanPersonDo.setSex(sexString.equals("M") ? LoanPersonDo.Sex.MALE : LoanPersonDo.Sex.FEMALE);
		
		
//		AccountUserDo user = (AccountUserDo) session.getAttribute("user");
		
//		if (null==user || null==user.getId()) {
			String mobile =loanPersonDo.getMobile();
			String realName=loanPersonDo.getRealName();
			IResult<AccountUserDo> result= userService.register(-1, "", mobile, DigestUtils.md5Hex(mobile + WebConstants.PASS_KEY), 100,-1);
			AccountUserDo user =result.getModel();
			//session.setAttribute("user",  user );
		 	if (result.isSuccess()) {
		        userService.updatePerson(user, realName, idNo, mobile);
	        }
//		} else {
			loanDo.setUserId(user.getId());
//		}
		loanDo.setLoanType(1);
		loanDo.setLoanUsage("集团贷");
		
		loanDo.setSchemeId(5l);
		loanDo.setAnnualRate(18.0);
		loanDo.setLoanStatus(LoanStatus.PENDING);
		// 初始化 产品code 订单号
		loanDo.setProductCode(LoanProductDo.JTD);
		loanDo.setOrderCode(commonService.generateOrderCode(LoanProductDo.JTD));
		//渠道
		loanDo.setChannelId(1L);//彩生活
		
		
		JobDo jobDo = loanPersonDo.getJobDo();
		jobDo.setCompanyName(loanPersonDo.getRemark());
		jobDo.setPosition("3");
		jobDo.setJobYear(0);
		jobDo.setCompanyPhone("未知");
		jobDo.setJobType(JobType.SALARYMAN);

//		loanPersonDo.setJobDo(jobDo);
		loanDo.setLoanPersonDo(loanPersonDo);
		Long res = loanApplyService.saveLoan(loanDo);

		if (res != null && res.longValue() > 0) {
			loanPersonDo.setLoanId(res);
			model.addAttribute("loanPersonDo", loanPersonDo);
			// 发送短信通知
			sendSMS(model, session);
			return "redirect:/app/group/showDocumentList";  

		} else {
			model.addAttribute(WebConstants.MESSAGE_KEY, "系统异常，请稍后再试!");
			return "app/group/loan_update";
		}
	}

	/**
	 * 发送短信
	 */
	private void sendSMS(Model model, HttpSession session){
    	
    	LoanPersonDo loanPersonDo = (LoanPersonDo) session.getAttribute("loanPersonDo");
    	LoanDo loanDo = loanApplyService.getByLoanId(loanPersonDo.getLoanId());
    	//给贷 款人的短信内容
    	String smsStr="尊敬的集团贷 用户，您申请的贷  款信息已经提交，集团贷 将尽快与您取得联系，谢谢您的配合。如有需要，请联系客服：4008303737";
    	NotifyDo nd = new LoanSMSNotifyDo(smsStr,loanPersonDo.getMobile(),"mail_template_default.ftl");
    	smsNotifyService.send(nd);
    	
    	//hehenian
    	smsStr="新的集团贷 ：贷  款人:"+loanPersonDo.getRealName()+",手机号码:"+loanPersonDo.getMobile()+",申请贷  款金额:"+loanDo.getApplyAmountString()+"元";
    	nd = new LoanSMSNotifyDo(smsStr,"13823501900,15019238715","mail_template_default.ftl");
    	smsNotifyService.send(nd);
    	
    	model.addAttribute("applyDo", loanDo);
    }	


	/**
	 * 借款申请成功页面
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 * @author: huangzl
	 * @date: 2015年6月8日 19:12:58
	 */
	@RequestMapping(value = "/showDocumentList", method = RequestMethod.GET)
	public String showDocumentList(HttpServletRequest request) {
		LoanPersonDo loanPersonDo = (LoanPersonDo) request.getSession().getAttribute("loanPersonDo");
		request.setAttribute("loanPersonDo", loanPersonDo);
		return "app/group/success";
	}

	public static void setLoanCount(HttpSession session) {
		int ud = 0;
		try {
			ud = Integer.parseInt((String) session.getServletContext().getAttribute("lcUpdateTime"));
		} catch (Exception e) {

		}
		if (ud == 0) {
			session.getServletContext().setAttribute("loanCount", 263);
			session.getServletContext().setAttribute("loanCountAmt", 3970);
			session.getServletContext().setAttribute("lcUpdateTime", DateUtil.YYYYMMDD.format(new Date()));
		} else {
			int nd = Integer.parseInt(DateUtil.YYYYMMDD.format(new Date()));
			int loanCount = Integer.parseInt(session.getServletContext().getAttribute("loanCount").toString());
			int loanCountAmt = Integer.parseInt(session.getServletContext().getAttribute("loanCountAmt").toString());
			if (ud < nd) {
				int temp = (int) (Math.random() * 10);
				session.getServletContext().setAttribute("loanCountAmt", loanCountAmt + temp * 2);
				loanCount = loanCount + temp;
			}
			session.getServletContext().setAttribute("loanCount", loanCount);
			session.getServletContext().setAttribute("lcUpdateTime", nd);
		}
	}

	private static Map<Integer, Double> creditRateMap = new HashMap<Integer, Double>();
	static {
		// 借款期限3个月授信比例
		creditRateMap.put(1, 1.5);
		creditRateMap.put(2, 2.0);
		creditRateMap.put(3, 2.5);
		creditRateMap.put(4, 3.0);
		creditRateMap.put(5, 3.5);
		// 借款期限6个月授信比例
		creditRateMap.put(6, 3.0);
		creditRateMap.put(7, 3.5);
		creditRateMap.put(8, 4.0);
		creditRateMap.put(9, 4.5);
		creditRateMap.put(10, 5.0);
	}
	
}
