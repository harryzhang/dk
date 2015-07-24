package com.hehenian.app.view.loan.eloan.controllor;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.hehenian.biz.common.loan.IManagerLoanService;
import com.hehenian.biz.common.loan.dataobject.CertificateDo;
import com.hehenian.biz.common.loan.dataobject.CertificateDo.CertificateType;
import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.LoanChannelDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo.LoanStatus;
import com.hehenian.biz.common.loan.dataobject.LoanDo.ProcessStep;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanProductDo;
import com.hehenian.biz.common.loan.dataobject.LoanProxyCheckDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.loan.dataobject.LoanStatusDo;
import com.hehenian.biz.common.loan.dataobject.LoanUserDo;
import com.hehenian.biz.common.loan.dataobject.PropertyDo;
import com.hehenian.biz.common.notify.INotifyLoanService;
import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.notify.dataobject.LoanNotifyDo;
import com.hehenian.biz.common.notify.dataobject.LoanSMSNotifyDo;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.trade.ISettleCalculatorService;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.IdCardUtils;
import com.hehenian.biz.common.util.StringUtil;
import com.hehenian.web.common.constant.WebConstants;

/**
 * @Description 描述方法作用
 * @author huangzl QQ: 272950754
 * @date 2015年7月1日 下午7:41:17
 * @Project hehenian-lend-app
 * @Package com.hehenian.app.view.loan.eloan.controllor
 * @File LoanApplyController.java
 */
@Controller
@RequestMapping(value = "/app/elend")
public class LoanApplyController {

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
	private INotifyLoanService noticeService;
	@Autowired
	private ICommonService commonService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IFileServerService fileServerService;
	
	@Autowired
	IManagerLoanService  managerLoanService;

	/**
	 * 借款申请首页
	 * 
	 * @param colorId
	 * @param cid
	 * @param cName
	 * @param caddress
	 * @param mobile
	 * @param model
	 * @return
	 * @author: liuzgmf
	 * @date: 2015年1月27日下午2:14:40
	 */
	@RequestMapping("/index")
	public String index(@RequestParam("userid") String sourceUserId, String username, Long cid, String cname, String caddress, String mobile, Integer channelType, String realName, String idNo, Model model, HttpSession session, HttpServletRequest request) {
		// return "redirect:/app/elend/welcome";
		model.addAttribute(WebConstants.MESSAGE_KEY, "");
		session.removeAttribute("message");
		realName = null == realName ? "" : URLDecoder.decode(realName);
		idNo = null == idNo ? "" : URLDecoder.decode(idNo);
		username = null == username ? "" : URLDecoder.decode(username);
		cname = null == cname ? "" : URLDecoder.decode(cname);
		caddress = null == caddress ? "" : URLDecoder.decode(caddress);
		logger.warn("渠道类型ID：" + channelType + "，用户ID：" + sourceUserId + "，小区ID：" + cid + "，小区名称：" + cname + "，小区地址：" + caddress + "，手机号码：" + mobile + "，真实姓名：" + realName + "，身份证号码：" + idNo);
		IResult<AccountUserDo> result = userService.register(-1, username, mobile, DigestUtils.md5Hex(mobile + WebConstants.PASS_KEY), 100, Long.valueOf(null == sourceUserId ? "-1" : sourceUserId));
		AccountUserDo user = result.getModel();
		if (result.isSuccess()) {
			userService.updatePerson(user, realName, idNo, mobile);
			userService.updateColourlifeInfo(user.getId(), Long.valueOf(null == sourceUserId ? "-1" : sourceUserId), cid, cname, caddress);
		}

		if (StringUtils.isBlank(sourceUserId)) {
			model.addAttribute(WebConstants.MESSAGE_KEY, "请求参数有误，请联系客服!");
			return "app/elend/index";
		}
		session.setAttribute("user", user);
		if (channelType == null || channelType.intValue() < 1) {
			channelType = 1; // 彩生活
		}
		if (user.getPerson().getRealName() != null && user.getPerson().getRealName().length() > 0) {
			realName = user.getPerson().getRealName();
		}
		if (user.getPerson().getIdNo() != null && user.getPerson().getIdNo().length() > 0) {
			idNo = user.getPerson().getIdNo();
		}
		LoanChannelDo loanChannelDo = new LoanChannelDo();
		LoanUserDo loanUserDo = new LoanUserDo();
		loanChannelDo.setChannelType(channelType);
		loanChannelDo.setSourceUserId(sourceUserId);
		loanUserDo.setCid(cid);
		loanUserDo.setLevel(1);
		try {
			if (StringUtils.isNotBlank(cname)) {
				loanUserDo.setCname(URLDecoder.decode(cname, "utf-8"));
			}
			if (StringUtils.isNotBlank(caddress)) {
				loanUserDo.setCaddress(URLDecoder.decode(caddress, "utf-8"));
			}
			if (StringUtils.isNotBlank(realName)) {
				loanUserDo.setName(URLDecoder.decode(realName, "utf-8"));
			}
			if (StringUtils.isNotBlank(idNo)) {
				loanUserDo.setIdNo(idNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		loanUserDo.setMobile(mobile);

		loanChannelDo.setLoanUserDo(loanUserDo);
		// 隐藏手机号码和身份证号码部分号码
		if (StringUtils.isNotBlank(mobile) && mobile.length() >= 7) {
			loanUserDo.setMobile(mobile);
			mobile = mobile.substring(0, 4) + "****" + mobile.substring(mobile.length() - 4);
			loanUserDo.setMobileShort(mobile);
		}
		if (StringUtils.isNotBlank(idNo) && idNo.length() >= 7) {
			loanUserDo.setIdNo(idNo);
			idNo = idNo.substring(0, 4) + "***********" + idNo.substring(idNo.length() - 4);
			loanUserDo.setIdNoShort(idNo);
		}
		loanChannelDo.setLoanUserDo(loanUserDo);
		session.setAttribute("loanChannelDo", loanChannelDo);
		setLoanCount(session);
		logger.info("----Start:index---------sessionID:" + session.getId() + "---------------");
//		return "app/elend/index";
		return "redirect:/app/elend/welcome";
//		return "app/elend/indexGuide";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String goElendWelcome(HttpSession session, HttpServletRequest request) {
		AccountUserDo user = (AccountUserDo) session.getAttribute("user");
		LoanChannelDo loanChannelDo = (LoanChannelDo) session.getAttribute("loanChannelDo");
		String realName = user.getPerson().getRealName() == null ? "" : user.getPerson().getRealName();
		String idNo = user.getPerson().getIdNo() == null ? "" : user.getPerson().getIdNo();
		String mobile = user.getMobilePhone() == null ? "" : user.getMobilePhone();
		if (loanChannelDo == null) {
			loanChannelDo = new LoanChannelDo();
			LoanUserDo loanUserDo = new LoanUserDo();
			loanChannelDo.setLoanUserDo(loanUserDo);
			loanChannelDo.setId(user.getId());
			if (StringUtils.isNotBlank(mobile) && mobile.length() >= 7) {
				loanChannelDo.getLoanUserDo().setMobile(mobile);
				mobile = mobile.substring(0, 4) + "****" + mobile.substring(mobile.length() - 4);
				loanUserDo.setMobileShort(mobile);
			}
			if (StringUtils.isNotBlank(realName)) {
				loanUserDo.setName(realName);
			}
			if (StringUtils.isNotBlank(idNo) && idNo.length() >= 7) {
				loanUserDo.setIdNo(idNo);
				idNo = idNo.substring(0, 4) + "***********" + idNo.substring(idNo.length() - 4);
				loanUserDo.setIdNoShort(idNo);
			}
			session.setAttribute("loanChannelDo", loanChannelDo);
		}
		return "app/elend/index";
	}

	/**
	 * 个人信息填写页面
	 * 
	 * @param userId
	 * @param loanId
	 * @param model
	 * @param session
	 * @return
	 * @author: liuzgmf
	 * @date: 2015年1月26日下午2:38:07
	 */
	@RequestMapping(value = "/personalInfo", method = RequestMethod.GET)
	public String saveLoanPerson(Model model, HttpSession session) {
		LoanChannelDo loanChannelDo = (LoanChannelDo) session.getAttribute("loanChannelDo");
		session.removeAttribute("loanId");
		session.removeAttribute("loanDo");
		session.removeAttribute("loanPersonDo");
		AccountUserDo user = (AccountUserDo) session.getAttribute("user");
		if (user != null) {
			LoanDo temp = new LoanDo();
			temp.setUserId(user.getId());
			temp.setProductCode(LoanProductDo.EDK);
			List<LoanDo> loanTemp = loanApplyService.selectLoanList(temp);
			if (loanTemp != null && loanTemp.size() > 0) {
				LoanDo loanDo = loanTemp.get(0);
				String status = loanDo.getLoanStatus().toString();
				LoanStatusDo LoanStatusDo = new LoanStatusDo();
				/**
				 * 以下是不允许申请和修改的状态： AUDITED-已审核，TREATY-已签约，SUBJECTED
				 * -待放款,REPAYING还款中
				 */
				String[] loanStatus1 = { "AUDITED", "TREATY", "SUBJECTED", "REPAYING","PROCESSING" };
				for (String loanStatus : loanStatus1) {
					if (status.equals(loanStatus)) {
						return "redirect:/app/elend/personalCenter";
					}
				}
				/**
				 * 以下是允许申请新贷款单： REPAYED已还清,INVALID失效，NOPASS-审核失败
				 */
				String[] loanStatus2 = { "REPAYED", "INVALID", "NOPASS" };
				for (String loanStatus : loanStatus2) {
					if (status.equals(loanStatus)) {
						return "app/elend/person_update";
					}
				}
				Long loanId = loanTemp.get(0).getLoanId();
				session.setAttribute("loanId", loanId);
				LoanPersonDo loanPersonDo = loanPersonService.getInitData(loanId);
				loanPersonDo.setLoanId(loanId);
				loanDo=loanPersonDo.getLoanDo();
				loanDo.setLoanPersonDo(loanPersonDo);
				session.setAttribute("loanDo", loanDo);
			}
		} else {
			LoanDo loanDo = new LoanDo();
			session.setAttribute("loanDo", loanDo);
		}
		return "app/elend/person_update";
	}

	/**
	 * 个人信息填写页面
	 * 
	 * @param userId
	 * @param loanPersonDo
	 * @param model
	 * @return
	 * @author: liuzgmf
	 * @date: 2015年1月26日下午2:37:17
	 */
	@RequestMapping(value = "/personalInfo", method = RequestMethod.POST)
	public String saveLoanPerson(@ModelAttribute LoanPersonDo loanPersonDo, Model model, HttpSession session) {
		LoanChannelDo loanChannelDo = (LoanChannelDo) session.getAttribute("loanChannelDo");
		LoanDo loanDo = (LoanDo) session.getAttribute("loanDo");
		Long loanId = (Long) session.getAttribute("loanId");
		LoanPersonDo loanPersonDoT;
		if (loanDo != null && loanDo.getLoanPersonDo() != null) {
			loanPersonDoT = loanDo.getLoanPersonDo();
		} else {
			loanPersonDoT = new LoanPersonDo();
			loanDo = new LoanDo();
		}
		if (loanPersonDoT.getCaddress()==null&&loanChannelDo != null) {
			loanPersonDoT.setColorId(loanChannelDo.getSourceUserId());
			loanPersonDoT.setCid(loanChannelDo.getLoanUserDo().getCid());
			loanPersonDoT.setCname(loanChannelDo.getLoanUserDo().getCname());
			loanPersonDoT.setCaddress(loanChannelDo.getLoanUserDo().getCaddress());
		}
		loanPersonDoT.setAge(IdCardUtils.getAgeByIdCard(loanPersonDo.getIdNo()));
		loanPersonDoT.setSex(IdCardUtils.getGenderByIdCard(loanPersonDo.getIdNo()).equals("M") ? LoanPersonDo.Sex.MALE : LoanPersonDo.Sex.FEMALE);
		loanPersonDoT.setRealName(loanPersonDo.getRealName());
		loanPersonDoT.setMobile(loanPersonDo.getMobile());
		loanPersonDoT.setIdNo(loanPersonDo.getIdNo());
		loanPersonDoT.setReferenceMobile(loanPersonDo.getReferenceMobile());
		loanDo.setLoanPersonDo(loanPersonDoT);
		session.setAttribute("loanDo", loanDo);
		if (loanId != null) {
			loanPersonDo.setLoanId(Long.valueOf(loanId));
			loanPersonDo.setLoanPersonId(loanPersonDoT.getLoanPersonId());
			loanPersonService.updateLoanPerson(loanPersonDo);
		}
		return "app/elend/loan_update";
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
	 * @author: liuzgmf
	 * @date: 2015年1月27日上午10:24:50
	 */
	@RequestMapping(value = "/calSettDetail", method = RequestMethod.GET)
	@ResponseBody
	public IResult<?> calSettDetail(Double loanAmount, Integer loanPeriod, String hasHouse) {
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

		/*
		 * if (annualRateArr == null || annualRateArr.length()<1) { return new
		 * ResultSupport<Integer>(false, "月利率参数有误!"); } if (schemeIdArr == null
		 * || schemeIdArr.length() < 1) { return new
		 * ResultSupport<Integer>(false, "还款方式参数有误!"); }
		 */
		String arArr[] = null; // 利率
		String siArr[] = null; // 还款方式
		/*
		 * try { arArr = annualRateArr.split(","); siArr =
		 * schemeIdArr.split(","); if(arArr.length != siArr.length){ return new
		 * ResultSupport<Integer>(false, "还款方式与利率参数长度不一致!"); } } catch
		 * (Exception e) { e.printStackTrace(); return new
		 * ResultSupport<Integer>(false, "还款方式or利率参数有误!"); }
		 */

		// 如果借款期限只有1个-2个月只支持 月率2.25%的等本等息
		if (loanPeriod < 4) {
			arArr = new String[] { "2.25" };
			siArr = new String[] { "104" };
		} else {
			// end 如果借款期限只有1个-2个月只支持 月率2.25%的等本等息

			// start E贷款改版后只有一种还款方式 ： 等本等息， 分两个档次，2.25%/月 大于3个月： 1.48%/月(业主),
			// 1.75%/月(租赁)
			// 2015-05-20
			if (hasHouse.equals("T")) {
				arArr = new String[] { "1.5" };
				siArr = new String[] { "105" };
			} else {
				arArr = new String[] { "1.75" };
				siArr = new String[] { "105" };
			}
		}
		// end E贷款改版后只有一种还款方式 ： 等本等息， 分两个档次，2.25%/月 大于3个月： 1.48%/月(业主),
		// 1.75%/月(租赁)

		DecimalFormat df = new DecimalFormat("##0.00");
		List<List<String>> repayAmountAllList = new ArrayList<List<String>>();
		for (int i = 0; i < arArr.length; i++) {
			Double monthRate = 0d;
			Long schemeId = 0L;
			try {
				monthRate = Double.valueOf(arArr[i]);
				schemeId = Long.valueOf(siArr[i]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return new ResultSupport<Integer>(false, "还款方式or利率参数有误!");
			}
			Double annualRate = getAnnualRate(monthRate);

			List<SettDetailDo> settDetailDoList = settleCalculatorService.calSettDetailForRepayPlanShow(loanAmount, annualRate, loanPeriod, schemeId,new Date());
			List<String> repayAmountList = new ArrayList<String>();
			System.out.println("===================================start repay scheme ============================================");
			for (int k = 0; k < settDetailDoList.size(); k++) {
				SettDetailDo settDetailDo = settDetailDoList.get(k);
				System.out.println(settDetailDo);
				// Double repayAmount = CalculateUtils.add(
				// CalculateUtils.add(CalculateUtils.add(
				// settDetailDo.getPrincipal(),
				// settDetailDo.getInterest()),settDetailDo.getServFee() == null
				// ? 0: settDetailDo.getServFee()),
				// settDetailDo.getConsultFee() == null ? 0 :
				// settDetailDo.getConsultFee());
				Double repayAmount = CalculateUtils.add(settDetailDo.getPrincipal(), settDetailDo.getInterest());
				repayAmountList.add(df.format(repayAmount));
			}
			System.out.println("===================================end repay scheme ============================================");
			repayAmountAllList.add(repayAmountList);
		}

		result.setSuccess(true);
		result.setModel(repayAmountAllList);
		return result;
	}

	/**
	 * 借款申请页面
	 * 
	 * @param loanDo
	 * @param model
	 * @return
	 * @author: liuzgmf
	 * @date: 2015年1月26日下午2:36:44
	 */
	@RequestMapping(value = "/applyStep2", method = RequestMethod.POST)
	public String applyStep(@ModelAttribute LoanPersonDo loanPersonDo, Model model, HttpServletRequest request, HttpSession session) {
		// 年月日 设置年月日
		String companyInTime_year = request.getParameter("companyInTime_year");
		String companyInTime_month = request.getParameter("companyInTime_month");
		String companyInTime_day = request.getParameter("companyInTime_day");

		JobDo jobDo = loanPersonDo.getJobDo();
		jobDo.setJobIncome(0D);
		jobDo.setCompanyName("未知");
		jobDo.setPosition("3");
		jobDo.setCompanyPhone("未知");
		try {
			if (!(companyInTime_year == null || companyInTime_month == null || companyInTime_day == null)) {
				loanPersonDo.getPropertyDo().setPurchaseDate(new SimpleDateFormat("yyyy-MM-dd H:m:s").parse(companyInTime_year + "-" + companyInTime_month + "-" + companyInTime_day + " 00:00:00"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// jobDo.setCompanyInTime(companyInTime_year+"-"+companyInTime_month+"-"+companyInTime_day);

		Long loanId = (Long) session.getAttribute("loanId");
		if (null != loanId) {
			loanPersonDo.setLoanId(loanId);
		}
		IResult result = loanPersonService.updateLoanPersonAndChild(loanPersonDo);
		LoanPersonDo loanPersonDo1 = loanPersonService.getInitData(loanId);
		loanPersonDo1.setLoanId(loanId);
		LoanDo loanDo = loanPersonDo1.getLoanDo();
		loanDo.setLoanPersonDo(loanPersonDo1);
		session.setAttribute("loanDo", loanDo);
		// 发送短信通知
//		sendSMS(session);
		if (loanDo.getLoanType() == 2) {
			return "redirect:/app/elend/showDocumentList";
		}
		return uploadFile(model, request, session);
	}

	/**
	 * 借款申请页面
	 * 
	 * @param loanDo
	 * @param model
	 * @return
	 * @author: liuzgmf
	 * @date: 2015年1月26日下午2:36:44
	 */
	@RequestMapping(value = "/uploadFile")
	public String uploadFile(Model model, HttpServletRequest request, HttpSession session) {
		int step = 1;
		if (request.getParameter("uploadImageStep") != null && request.getParameter("uploadImageStep").trim().length() > 0) {
			step = Integer.parseInt(request.getParameter("uploadImageStep"));
		}
		if(step==999){
//			LoanDo saveLoanDo =new LoanDo();
//			saveLoanDo.setLoanId(Long.valueOf(request.getParameter("loanId")));
//			saveLoanDo.setLoanStatus(LoanStatus.NOPASSSTEP2);
//			loanApplyService.saveLoan(saveLoanDo);
			return "redirect:/app/elend/personalCenter";
		}else if(step==998){
			return "redirect:/app/elend/personalCenter";
		}
		step++;
		int xType = 0;
		int yType = 0;
		LoanDo loanDo = (LoanDo) session.getAttribute("loanDo");
		LoanPersonDo loanPersonDo = loanDo.getLoanPersonDo();
		if (null != loanPersonDo) {
			model.addAttribute("loanPersonDo", loanPersonDo);
		}
		
		double applyAmount = getApplyAmountTemp(loanDo);

		// 根据贷款金额控制步骤
		if (null != loanPersonDo) {
			// 有房
			if ("T".equals(loanPersonDo.getHasHouse())) {
				if (loanDo.getApplyAmount() <= applyAmount) {
					xType = 1;
				} else if (loanDo.getApplyAmount() <= 200000) {
					xType = 2;
				} else {
					xType = 3;
					yType = 1;
				}
			} else {
				if (loanDo.getApplyAmount() <= applyAmount) {
					xType = 4;
				} else {
					xType = 5;
					yType = 1;
				}
			}
		}
		model.addAttribute("uploadImageStep", step);
		
		//修复 第一单的时候跳转到上传页面会报错误，因为reqLoanId为空
		String reqLoanId = request.getParameter("loanId");
		List<CertificateDo> cdList = Collections.EMPTY_LIST;
		if(null != reqLoanId && !"".equals(reqLoanId)){
			cdList = loanPersonService.initCertificateData(Long.valueOf(reqLoanId));
		}
		//end 修复 第一单的时候跳转到上传页面会报错误，因为reqLoanId为空
		
		if (step == 2) {			
			model.addAttribute("IDCARDZ", "");
			model.addAttribute("IDCARDZS", "");
			model.addAttribute("IDCARDF","");
			model.addAttribute("HOUSE", "");
			model.addAttribute("JOB", "");
			model.addAttribute("CREDIT", "");
			model.addAttribute("DRIVERCARD", "");
			model.addAttribute("ASSETS", "");
			
			for(CertificateDo temp:cdList){
				if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("IDCARDZ")){
					model.addAttribute("IDCARDZ", temp.getDestFilePath());
					model.addAttribute("IDCARDZNAME", temp.getCertificateName());
					model.addAttribute("IDCARDZID", temp.getCertificateId());
				}
				if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("IDCARDZS")){
					model.addAttribute("IDCARDZS", temp.getDestFilePath());
					model.addAttribute("IDCARDZSNAME", temp.getCertificateName());
					model.addAttribute("IDCARDZSID", temp.getCertificateId());
				}
				if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("IDCARDF")){
					model.addAttribute("IDCARDF", temp.getDestFilePath());
					model.addAttribute("IDCARDFNAME", temp.getCertificateName());
					model.addAttribute("IDCARDFID", temp.getCertificateId());
				}
				if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("HOUSE")){
					model.addAttribute("HOUSE", temp.getDestFilePath());
					model.addAttribute("HOUSENAME", temp.getCertificateName());
					model.addAttribute("HOUSEID", temp.getCertificateId());
				}
				if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("JOB")){
					model.addAttribute("JOB", temp.getDestFilePath());
					model.addAttribute("JOBNAME", temp.getCertificateName());
					model.addAttribute("JOBID", temp.getCertificateId());
				}
				if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("CREDIT")){
					model.addAttribute("CREDIT", temp.getDestFilePath());
					model.addAttribute("CREDITNAME", temp.getCertificateName());
					model.addAttribute("CREDITID", temp.getCertificateId());
				}
				if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("DRIVERCARD")){
					model.addAttribute("DRIVERCARD", temp.getDestFilePath());
					model.addAttribute("DRIVERCARDNAME", temp.getCertificateName());
					model.addAttribute("DRIVERCARDID", temp.getCertificateId());
				}
				if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("ASSETS")){
					model.addAttribute("ASSETS", temp.getDestFilePath());
					model.addAttribute("ASSETSNAME", temp.getCertificateName());
					model.addAttribute("ASSETSID", temp.getCertificateId());
				}
			}
			
			model.addAttribute("uploadImageXType", xType);
			model.addAttribute("uploadImageYType", yType);
			return "app/elend/loan_upload";
		} else if (step == 3 && yType == 1) {
			List<CertificateDo> certificateDo=new ArrayList<CertificateDo>();
			for(CertificateDo temp:cdList){
				if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("INCOME")){
					certificateDo.add(temp);
				}
			}
			model.addAttribute("certificateDo",certificateDo);
			return "app/elend/loan_upload2";
		} else {
			return "redirect:/app/elend/showDocumentList";
		}

	}

	/**
	 * 从session 获取订单id
	 * 
	 * @param session
	 * @return
	 */
	private Long getLoanIdFromSession(HttpSession session) {
		Long loanId = (Long) session.getAttribute("loanId");
		return loanId;
	}

	/**
	 * 借款申请页面
	 * 
	 * @param loanDo
	 * @param model
	 * @return
	 * @author: liuzgmf
	 * @date: 2015年1月26日下午2:36:44
	 */
	@RequestMapping(value = "/applyfor", method = RequestMethod.POST)
	public String saveLoanDetail(@ModelAttribute LoanDo loanDo, @RequestParam String hasHouse, Model model, HttpServletRequest request, HttpSession session) {

		if (loanDo.getLoanType() == null || loanDo.getLoanType().intValue() < 1) {
			model.addAttribute(WebConstants.MESSAGE_KEY, "贷款方式有误!");
			return "app/elend/loan_update";
		}
		if (loanDo.getApplyAmount() == null || CalculateUtils.le(loanDo.getApplyAmount(), 0d)) {
			model.addAttribute("resultCode", "money");
			model.addAttribute(WebConstants.MESSAGE_KEY, "借款金额有误！");
			return "app/elend/loan_update";
		}
		String loanAmoutString = new DecimalFormat("###0.#").format(loanDo.getApplyAmount());
		if (Long.valueOf(loanAmoutString) % 100 != 0) {
			model.addAttribute(WebConstants.MESSAGE_KEY, "请确认金额为100的整数倍！");
			model.addAttribute("resultCode", "money");
			return "app/elend/loan_update";
		}
		if (loanAmoutString.length() > 11) {
			model.addAttribute(WebConstants.MESSAGE_KEY, "输入金额不能超过999亿！");
			model.addAttribute("resultCode", "money");
			return "app/elend/loan_update";
		}
		if (loanDo.getLoanPeriod() == null || (loanDo.getLoanPeriod().intValue() < 1)) {
			model.addAttribute(WebConstants.MESSAGE_KEY, "贷款期限有误!");
			return "app/elend/loan_update";
		}
		if (loanDo.getLoanPeriod() < 4) {
			loanDo.setAnnualRate(2.25);
			loanDo.setSchemeId(104l);
		} else {
			if (hasHouse.equals("T")) {
				loanDo.setAnnualRate(1.5);
				loanDo.setSchemeId(105l);
			} else {
				loanDo.setAnnualRate(1.75);
				loanDo.setSchemeId(105l);
			}
		}
		// start E贷款改版后只有一种还款方式 ： 等本等息， 分两个档次，2.25%/月 大于3个月：
		// 1.48%/月(业主),1.75%/月(租赁)
		// 2015-05-20
		loanDo.setAnnualRate(getAnnualRate(loanDo.getAnnualRate()));
		Long loanId = getLoanIdFromSession(session);
		LoanDo loanDoT = (LoanDo) session.getAttribute("loanDo");
		loanDoT.setLoanType(loanDo.getLoanType());
		loanDoT.setLoanUsage(loanDo.getLoanUsage());
		loanDoT.setApplyAmount(loanDo.getApplyAmount());
		loanDoT.setLoanPeriod(loanDo.getLoanPeriod());
		loanDoT.setAnnualRate(loanDo.getAnnualRate());
		loanDoT.setSchemeId(loanDo.getSchemeId());
		AccountUserDo user = (AccountUserDo) session.getAttribute("user");
		LoanPersonDo loanPersonDo = loanDoT.getLoanPersonDo();
		loanPersonDo.setHasHouse(hasHouse);
		if (user == null) {
			loanDoT.setUserId(-1l);
			loanDo.setUserId(-1l);
		} else {
			loanDoT.setUserId(user.getId());
			loanDo.setUserId(user.getId());
		}
		Long res;
		if (loanId != null) {// 修改订单
			loanDo.setLoanId(loanId);
			LoanPersonDo loanPersonDoTemp = new LoanPersonDo();
			loanPersonDoTemp.setLoanId(loanId);
			loanPersonDoTemp.setLoanPersonId(loanPersonDo.getLoanPersonId());
			loanPersonDoTemp.setHasHouse(hasHouse);
			loanDo.setLoanPersonDo(loanPersonDoTemp);
			res = loanApplyService.saveLoan(loanDo);
		} else {// 新订单
			loanDoT.setLoanPersonDo(loanPersonDo);

			loanDoT.setLoanStatus(LoanStatus.DRAFT);
			loanDoT.setProcessCurrentStep(ProcessStep.TO_EDIT);
			loanDoT.setProcessNextStep(ProcessStep.TO_EDIT);
			// 初始化 产品code 订单号
			loanDoT.setProductCode(LoanProductDo.EDK);
			// 渠道
			loanDoT.setChannelId(1L);// 彩生活
			loanDoT.setOrderCode(commonService.generateOrderCode(LoanProductDo.EDK));
			res = loanApplyService.saveLoan(loanDoT);
			if (res != null && res.longValue() > 0) {
				LoanPersonDo loanPTemp = loanPersonService.getInitData(loanId);
				loanPTemp.setLoanId(loanPTemp.getLoanDo().getLoanId());
				loanDoT=loanPTemp.getLoanDo();
				loanDoT.setLoanPersonDo(loanPTemp);
			}
		}
		if (res != null && res.longValue() > 0) {
			session.setAttribute("loanId", res);
			String caddress = loanDoT.getLoanPersonDo().getCaddress();
			double applyAmount = getApplyAmountTemp(loanDoT);
			loanDoT.setApplyAmounttemp(applyAmount);
			session.removeAttribute("loanDo");
			session.setAttribute("loanDo", loanDoT);
			model.addAttribute("loanDo", loanDoT);
			if (loanDoT.getLoanType() == 2 || !"T".equals(loanDoT.getLoanPersonDo().getHasHouse()) || loanDo.getApplyAmount() > applyAmount) {// 一线城市小于5万或非一线城市小于2万去上传页面
				if (("T").equals(loanDoT.getLoanPersonDo().getHasHouse()) && loanDoT.getLoanPersonDo().getPropertyDo() != null && loanDoT.getLoanPersonDo().getPropertyDo().getPurchaseDate() != null) {
					String purchaseDate = loanDoT.getLoanPersonDo().getPropertyDo().getPurchaseDateString();
					String[] purchaseDateArray = purchaseDate.split("-");
					model.addAttribute("companyInTime_year", purchaseDateArray[0]);
					model.addAttribute("companyInTime_month", purchaseDateArray[1]);
					model.addAttribute("companyInTime_day", purchaseDateArray[2]);
				}
				return "app/elend/apply_step2";
			} else {
				// 发送短信通知
//				sendSMS(session);
				return uploadFile(model, request, session);
			}

		} else {
			model.addAttribute(WebConstants.MESSAGE_KEY, "系统异常，请稍后再试!");
			return "app/elend/loan_update";
		}
	}

	/**
	 * 发送短信
	 */
	private void sendSMS(HttpSession session) {
		LoanDo loanDo = (LoanDo) session.getAttribute("loanDo");
		LoanPersonDo loanPersonDo = loanDo.getLoanPersonDo();
		// smsStr =
		// "贷  款申请提交成功，正在受理,贷  款人:"+loanPersonDo.getRealName()+",手机号码:"+loanPersonDo.getMobile()+",申请贷  款金额:"+loanDo.getApplyAmount()+"元"
		// ;
		// 给贷 款人的短信内容
		// String
		// smsStr="尊敬的E贷 款用户,您申请的贷 款信息已经提交,E贷 款将尽快与您取得联系,请您务必准备好上传资料的原件,谢谢您的配合。如有需要,请联系客服：4008303737";
//		String smsStr = "尊敬的E贷 款用户,您申请的部分贷 款信息已经提交;请您继续完善图片资料上传,保证资料照片的真实、清晰,否则无法给您贷 款;待图片提交后,E贷 款将尽快与您取得联系,并请您准备好原件,谢谢您的配合。如有需要,请联系客服：4008303737";
		String smsStr = "您的"+loanDo.getApplyAmountString()+"元，为期"+loanDo.getLoanPeriod()+"个月的贷款订单已成功提交，月利率为"+ loanDo.getAnnualRate() / 12 +"%（详见E贷款----个人中心）；E贷款将对您的订单进行审核，我们将尽快把审核结果反馈给您。咨询热线-4008303737 ";
		NotifyDo nd = new LoanSMSNotifyDo(smsStr, loanPersonDo.getMobile(), "mail_template_default.ftl");
		smsNotifyService.send(nd);

		// String smsStr =
		// "E贷 款新的贷  款申请订单,贷  款小区:"+loanPersonDo.getCname()+",贷  款人:"+loanPersonDo.getRealName()+",手机号码:"+loanPersonDo.getMobile()+",申请贷  款金额:"+loanDo.getApplyAmount()+"元"
		// ;
		// 给客户经理的短信内容
		smsStr = "E贷 款新订单:" + loanPersonDo.getCaddress() == null ? loanPersonDo.getCname() : loanPersonDo.getCaddress() + "小区 ,贷 款人" + loanPersonDo.getRealName() + " ,金额" + loanDo.getApplyAmountString() + "元,电话" + loanPersonDo.getMobile() + ",请您尽快联系,并务必核实贷 款人上传的资料与原件是否一致(所填信息及照片)。";
		if (loanPersonDo.getCid() != null) {
			String cnameMobile = loanApplyService.getCmoblie(loanPersonDo.getCid());
			if (null != cnameMobile && cnameMobile.length() > 0) {
				nd = new LoanSMSNotifyDo(smsStr, cnameMobile, "mail_template_default.ftl");
				smsNotifyService.send(nd);
			}
		}

		// hehenian
		smsStr = "新的贷 E款：贷  款人:" + loanPersonDo.getRealName() + ",手机号码:" + loanPersonDo.getMobile() + ",申请贷  款金额:" + loanDo.getApplyAmountString() + "元";
		nd = new LoanSMSNotifyDo(smsStr, "13823501900,15019238715", "mail_template_default.ftl");
		smsNotifyService.send(nd);

	}

	/**
	 * 借款申请成功页面
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 * @author: liuzgmf
	 * @date: 2015年1月28日上午9:49:01
	 */
	@RequestMapping(value = "/complete", method = RequestMethod.GET)
	public String getByLoanId(Model model) {
		// model.addAttribute("now", new Date());
		return "app/elend/completed";
	}

	/**
	 * 显示上传清单
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 * @author: liuzgmf
	 * @date: 2015年1月28日上午9:49:01
	 */
	@RequestMapping(value = "/showUploadList", method = RequestMethod.GET)
	public String showUploadList(HttpServletRequest request, Model model) {
		// CertificateDo certificateDo = new CertificateDo();
		// certificateDo.setLoanId(loanId);
		// List<CertificateDo> cdList =
		// loanPersonService.initCertificateData(loanId);
		// Map<String,List<CertificateDo> > typeMap = new
		// HashMap<String,List<CertificateDo> >(10);
		// if(cdList != null && cdList.size()>0){
		// for(int i=0;i<cdList.size();i++){
		// CertificateDo certificate = cdList.get(i);
		// String type = certificate.getCertificateType().toString();
		// List<CertificateDo> subCertificateList = (List<CertificateDo>
		// )typeMap.get(type);
		// if(null ==subCertificateList){
		// subCertificateList = new ArrayList<CertificateDo>(10);
		// }
		// subCertificateList.add(certificate);
		// typeMap.put(type, subCertificateList);
		//
		// }
		// }
		// model.addAttribute("certificateDo",certificateDo);
		// model.addAttribute("certificateTypeMap",typeMap);

		String loanIdStr = request.getParameter("loanId");
		Long loanId = StringUtil.strToLong(loanIdStr);
		LoanPersonDo loanPersonDo = loanPersonService.getInitData(loanId);
		if (loanPersonDo.getLoanId() == null) {
			loanPersonDo.setLoanId(loanPersonDo.getLoanDo().getLoanId());
		}
    	LoanDo loanDo =loanPersonDo.getLoanDo();
    	loanDo.setLoanPersonDo(loanPersonDo);
    	double applyAmount = getApplyAmountTemp(loanDo);
		JobDo jobDo = loanPersonDo.getJobDo();
		if (jobDo == null) {
			jobDo = new JobDo();
			jobDo.setLoanId(loanPersonDo.getLoanDo().getLoanId());
			jobDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
		}
		// 加载对应数据
		PropertyDo propertyDo = loanPersonService.initPropertyData(loanId);
		if (propertyDo == null) {
			propertyDo = new PropertyDo();
			propertyDo.setLoanId(loanId);
			propertyDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
		}
		if (loanDo.getApplyAmount() <= applyAmount && loanPersonDo.getHasHouse().equals("T")) {
			model.addAttribute("hasType", "1");
		}else if(loanPersonDo.getHasHouse().equals("T")){
			model.addAttribute("hasType", "1T");
		}else{
			model.addAttribute("hasType", "1F");
		}
		if(loanDo.getLoanType()==2){
			model.addAttribute("hasType", "2T");
		}
		List<CertificateDo> pageId1 = uploadList(loanId, 1);
		List<CertificateDo> pageId2 = uploadList(loanId, 2);
		List<CertificateDo> pageId4 = uploadList(loanId, 4);
		List<CertificateDo> pageId3 = uploadList(loanId, 3);
		model.addAttribute("pageId1", pageId1);
		model.addAttribute("pageId2", pageId2);
		model.addAttribute("pageId3", pageId3);
		model.addAttribute("pageId4", pageId4);
		model.addAttribute("propertyDo", propertyDo);
		model.addAttribute("loanPersonDo", loanPersonDo);
		model.addAttribute("fileAccessUrl", fileServerService.getFileAccessUrl());

		return "app/elend/uploadList";
	}

	/**
	 * 借款申请成功页面
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 * @author: liuzgmf
	 * @date: 2015年1月28日上午9:49:01
	 */
	@RequestMapping(value = "/showDocumentList", method = RequestMethod.GET)
	public String showDocumentList(Model model, HttpServletRequest request, HttpSession session) {

		LoanDo loan = (LoanDo) session.getAttribute("loanDo");
		LoanPersonDo loanPersonDo = loanPersonService.getInitData(loan.getLoanId());
		if (loanPersonDo.getLoanId() == null) {
			loanPersonDo.setLoanId(loanPersonDo.getLoanDo().getLoanId());
		}
		loan =loanPersonDo.getLoanDo();
		loan.setLoanPersonDo(loanPersonDo);
		//判断是否是一线城市，一线城市5W，别的2W
    	double applyAmount =  getApplyAmountTemp(loan);
		LoanDo loanDo = new LoanDo();
//		boolean tz = false;
//		if (loan.getLoanStatus().equals(LoanStatus.DRAFT)) {
//			tz = true;
//		}
		//判断是否有房
		boolean houseStatus =false;
		if(loanPersonDo.getHasHouse()!=null &&loanPersonDo.getHasHouse().equals("T")){
			houseStatus=true;
		}
		//判断是否把所需要的图片都上传了
		boolean status=false;
		if(loan.getLoanType()==1){
			List<CertificateDo> cdList = loanPersonService.initCertificateData(loan.getLoanId());
			if(cdList!=null && cdList.size()>0){
				/**
				  身份证正面：IDCARDZ
				  本人身份证正面：IDCARDZS
					身份证反面：IDCARDF
					房产证明：HOUSE
					收入流水：INCOME
					工作证明：JOB
					DRIVERCARD-驾驶证
					ASSETS -资产相关证明
				 */
				boolean idcardz=false;
				boolean idcardzs=false;
				boolean idcardf=false;
				boolean house=false;
				boolean income=false;
				boolean job=false;
				boolean drivercard=false;
				boolean assets=false;
				for(CertificateDo temp:cdList){
					if(!idcardz){
						if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("IDCARDZ")){
							idcardz=true;
						}
					}
					if(!idcardzs){
						if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("IDCARDZS")){
							idcardzs=true;
						}
					}
					if(!idcardf){
						if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("IDCARDF")){
							idcardf=true;
						}
					}
					if(!house){
						if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("HOUSE")){
							house=true;
						}
					}
					if(!income){
						if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("INCOME")){
							income=true;
						}
					}
					if(!job){
						if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("JOB")){
							job=true;
						}
					}
					if(!drivercard){
						if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("DRIVERCARD")){
							drivercard=true;
						}
					}
					if(!assets){
						if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("ASSETS")){
							assets=true;
						}
					}
				}
				//判断有房产的图片是否上传完毕
				if(loanPersonDo.getHasHouse().equals("T")){
					if(idcardz&&idcardzs&&idcardf&&house){
						status=true;
					} 
					if(loan.getApplyAmount()>applyAmount ){
						if(!job){
							status=false;
						}
					}
					if(loan.getApplyAmount()>200000.0 ){
						if(!income){
							status=false;
						}
					}
				//判断没有房产的图片是否上传完毕
				}else if(loanPersonDo.getHasHouse().equals("F")){
					if(idcardz&&idcardzs&&idcardf&&job&&assets){
						status=true;
					}
					if(loan.getApplyAmount()>applyAmount){
						if( !income){
							status=false;
						}
					}
					if(loanPersonDo.getHasHouse().equals("F")&&loanPersonDo.getPropertyDo()!=null&&loanPersonDo.getPropertyDo().getCarDy()!=null&&loanPersonDo.getPropertyDo().getCarDy()==1){
						if(!drivercard){
							status=false;
						}
					}
				}
			}
		}else if(loan.getLoanType()==2) {
			status=true;
		}
		//图片传完，或抵押贷直接修改状态
		if(status){
			loanDo.setLoanStatus(LoanStatus.PENDING);
			if(houseStatus){
				loanDo.setProcessCurrentStep(ProcessStep.TO_EDIT);
				loanDo.setProcessNextStep(ProcessStep.CALL_COLOR_HOUSE_CHECK);
			}else{
				loanDo.setProcessCurrentStep(ProcessStep.CALL_COLOR_HOUSE_CHECK);
				loanDo.setProcessNextStep(ProcessStep.PROXY_CHECK);
			}
		}else{
			loanDo.setLoanStatus(LoanStatus.DRAFT);
			loanDo.setProcessNextStep(ProcessStep.TO_EDIT);
			loanDo.setProcessCurrentStep(ProcessStep.TO_EDIT);
		}
		
		loanDo.setLoanId(getLoanIdFromSession(session));
		Long res = loanApplyService.saveLoan(loanDo);
		if (res != null && res.longValue() > 0&&status) {
//			if (tz) {
				//callColorHouseCheck
			//有房验证是否是业主
			if(houseStatus){
				
				
				managerLoanService.callColorHouseCheck(loanPersonDo.getRealName(), loanPersonDo.getIdNo(), loanPersonDo.getCname(), getApplyAmountTemp(loanPersonDo), loan.getLoanId());
			}
			LoanNotifyDo addNotifyDo = new LoanNotifyDo();
			AccountUserDo user = (AccountUserDo) session.getAttribute("user");
			addNotifyDo.setRecievers("loanUserId:" + user.getId());
			addNotifyDo.setSubject("您的" + loan.getApplyAmountString() + "元借款订单已成功提交");
			addNotifyDo.setMessage("尊敬的E贷款用户，您有一条借款订单已成功提交，借款金额为" + loan.getApplyAmountString() + "元，借款期限" + loan.getLoanPeriod() + "个月，月利率为" + loan.getAnnualRate() / 12 + "%，还款方式为“等本等息（详见个人中心----还款计划表）”；E贷款将对您的订单进行审核，我们将尽快把审核结果反馈给您。咨询热线:4008303737");
			addNotifyDo.setSender("sys");
			addNotifyDo.setSendFlag("F");
			addNotifyDo.setValidate("T");
			addNotifyDo.setBusinessType("");
			noticeService.addMessage(addNotifyDo);
			sendSMS(session);
//			}
		}
//		else {
//			model.addAttribute(WebConstants.MESSAGE_KEY, "系统异常，请稍后再试!");
//			return "common/notify_message";
//		}
		return "app/elend/success";
	}

	/**
	 * 个人信息页面
	 * 
	 * @param loanId
	 * @param model
	 * @return
	 * @author: liuzgmf
	 * @date: 2015年1月28日上午9:49:14
	 */
	@RequestMapping(value = "/personalCenter", method = RequestMethod.GET)
	public String showUserCenter(Long colorId, Model model, HttpSession session) {
		AccountUserDo user = (AccountUserDo) session.getAttribute("user");
		LoanChannelDo loanChannelDo = (LoanChannelDo) session.getAttribute("loanChannelDo");
		loanChannelDo.setId(user.getId());
		loanChannelDo.getLoanUserDo().setMobile(user.getMobilePhone());
		// 获取个人统计信息
		Map<String, Object> personTotalMap = loanApplyService.getTotalInfoByPerson(loanChannelDo);
		if (personTotalMap != null) {
			model.addAttribute("personTotalMap", personTotalMap);
		}
		// end 获取个人统计信息

		List<LoanDo> loanDoList1 = loanApplyService.queryBySourceUserId(user.getId() + "");
		if (loanDoList1 == null || loanDoList1.size() == 0) {
			return "app/elend/personal_center";
		}
		List<LoanDo> loanDoList =new ArrayList<LoanDo>();
		for(LoanDo temp:loanDoList1){
			if(temp.getProductCode()!=null && temp.getProductCode().equals(LoanProductDo.EDK)){
				loanDoList.add(temp);
			}
		}
		if (loanDoList == null || loanDoList.size() == 0) {
			return "app/elend/personal_center";
		}
		

		// 获取还款明细信息
		Long loanId = null;		
		//获取当前订单： 优先显示还款中的订单
		for(int j = 0 ; j<loanDoList.size();j++){
			LoanDo tmpLoanDo = loanDoList.get(j);
			String status = tmpLoanDo.getLoanStatus().toString();			
			if("REPAYING".equals(status)){
				loanId = tmpLoanDo.getLoanId();
				break;
			}
		}
		
		// 没有还款中的订单是显示状态不等于失效最后提交的订单
		if(loanId == null){
			for(int j = 0 ; j<loanDoList.size();j++){
				LoanDo tmpLoanDo = loanDoList.get(j);
				String status = tmpLoanDo.getLoanStatus().toString();			
				if("INVALID".equals(status) ||"NOPASS".equals(status)){
					continue;
				}
				loanId = tmpLoanDo.getLoanId();
				break;
			}
		}
		
		//没有有效订单
		if (loanId == null) {
			return "app/elend/personal_center";
		}
		
		model.addAttribute("loanDoList", loanDoList);
		model.addAttribute("loanDoListSize", loanDoList.size());
		
		session.setAttribute("loanId", loanId);
		LoanPersonDo loanPersonDo = loanPersonService.getInitData(loanId);
		loanPersonDo.setLoanId(loanId);
		LoanDo loanDo = loanPersonDo.getLoanDo();
		loanDo.setLoanPersonDo(loanPersonDo);
		loanDo.setApplyAmounttemp(confirmAmountCalculation(loanDo));
		session.setAttribute("loanDo", loanDo);
		model.addAttribute("loanDo", loanDo);
		Double loanAmount = (loanDo.getLoanAmount() == null ? loanDo.getApplyAmount() : loanDo.getLoanAmount());
		List<SettDetailDo> settDetailDoList = settleCalculatorService.calSettDetailForRepayPlanShow(loanAmount, loanDo.getAnnualRate(), loanDo.getLoanPeriod(), loanDo.getSchemeId(),loanDo.getLoanTime()==null?loanDo.getCreateTime():loanDo.getLoanTime());
		// .calSettDetail(loanAmount,
		// loanDo.getAnnualRate(),loanDo.getLoanPeriod(), loanDo.getSchemeId());
		SettDetailDo settDetailDo = settDetailDoList.get(0);
		LoanNotifyDo queryNotifyDo = new LoanNotifyDo();
		queryNotifyDo.setRecievers("loanUserId:"+user.getId());
		queryNotifyDo.setSendFlag("F");
		List<LoanNotifyDo> temp = noticeService.selectNotify(queryNotifyDo);
		model.addAttribute("tzNum",temp==null?"0":temp.size());
		model.addAttribute("settDetailDo", settDetailDo);
		model.addAttribute("today", new Date());
		model.addAttribute("settDetailDoList", settDetailDoList);
		session.setAttribute("loanChannelDo", loanChannelDo);
		return "app/elend/personal_center";
	}

	/**
	 * 用户等级信息页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/levelInfo")
	public String levelInfo() {
		return "app/elend/rating";
	}

	private static Double getAnnualRate(Double monthRate) {
		return CalculateUtils.mul(monthRate, 12);
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

	public static void main(String args[]) {
		String str = "%E8%8A%B1%E9%83%A1";
		try {
			System.out.println(URLDecoder.decode(str, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public List<CertificateDo> uploadList(Long loanId, Integer pageId) {
		LoanPersonDo loanPersonDo = loanPersonService.getInitData(loanId);
		if (loanPersonDo.getLoanId() == null) {
			loanPersonDo.setLoanId(loanPersonDo.getLoanDo().getLoanId());
		}
    	LoanDo loanDo =loanPersonDo.getLoanDo();
    	loanDo.setLoanPersonDo(loanPersonDo);
    	double applyAmount =  getApplyAmountTemp(loanDo);
		CertificateDo certificateDo = new CertificateDo();
		certificateDo.setLoanId(loanId);
		certificateDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
		List<CertificateDo> cdList = loanPersonService.initCertificateData(loanId);
		List<CertificateDo> cdListOut = new ArrayList<CertificateDo>();
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("loanId", loanId);
		parameterMap.put("tableCode", "CertificateDo");
		// 页面一
		if (pageId.intValue() == 1) {
			// 加载图片与对应状态
			List<LoanProxyCheckDo> tempMap = loanApplyService.selectLoanProxyCheck(parameterMap);
			if (cdList != null && cdList.size() > 0) {
				for (CertificateDo temp : cdList) {
					if (temp.getCertificateType() != null &&( temp.getCertificateType().toString().equals("IDCARDZ") || temp.getCertificateType().toString().equals("IDCARDZS")||temp.getCertificateType().toString().equals("IDCARDF"))) {
						if (null != tempMap && tempMap.size() > 0) {
							for (LoanProxyCheckDo proxyCheck : tempMap) {
								if (proxyCheck.getNameCode().toString().equals("IDCARDZ") || proxyCheck.getNameCode().toString().equals("IDCARDF")) {
									if (temp.getCertificateId().longValue() == proxyCheck.getRecordId().longValue()) {
										temp.setStatusId(proxyCheck.getId());
										temp.setStatusInt(proxyCheck.getStatus());
									}
								}
							}
						}
						cdListOut.add(temp);
					}
				}
			}
			// 页面2
		} else if (pageId.intValue() == 2) {
			// 加载图片与对应状态
			List<LoanProxyCheckDo> tempMap = loanApplyService.selectLoanProxyCheck(parameterMap);
			if (cdList != null && cdList.size() > 0) {
				for (CertificateDo temp : cdList) {
					if (temp.getCertificateType() != null && temp.getCertificateType().toString().equals("JOB")) {
						if (null != tempMap && tempMap.size() > 0) {
							for (LoanProxyCheckDo proxyCheck : tempMap) {
								if (proxyCheck.getNameCode().toString().equals("JOB") && temp.getCertificateId().longValue() == proxyCheck.getRecordId().longValue()) {
									temp.setStatusId(proxyCheck.getId());
									temp.setStatusInt(proxyCheck.getStatus());
								}
							}
						}
						cdListOut.add(temp);
					}
				}
			}
			// 页面3
		} else if (pageId.intValue() == 3) {
			PropertyDo propertyDo = loanPersonService.initPropertyData(loanId);
			if (propertyDo == null) {
				propertyDo = new PropertyDo();
				propertyDo.setLoanId(loanId);
				propertyDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
			}
			// 加载图片与对应状态
			List<LoanProxyCheckDo> tempMap = loanApplyService.selectLoanProxyCheck(parameterMap);
			if (cdList != null && cdList.size() > 0) {
				String typeTemp="";
				if (loanPersonDo.getHasHouse().equals("T")) {
					typeTemp="HOUSE";
				}else{
					typeTemp="ASSETS";
				}
					for (CertificateDo temp : cdList) {
						if (temp.getCertificateType() != null && temp.getCertificateType().toString().equals(typeTemp)) {
							if (null != tempMap && tempMap.size() > 0) {
								for (LoanProxyCheckDo proxyCheck : tempMap) {
									if (proxyCheck.getNameCode().toString().equals(typeTemp) && temp.getCertificateId().longValue() == proxyCheck.getRecordId().longValue()) {
										temp.setStatusId(proxyCheck.getId());
										temp.setStatusInt(proxyCheck.getStatus());
									}
								}
							}
							cdListOut.add(temp);
						}
					}
//				} else {
//					// 无房产的显示资产信息(手工录入)
//					parameterMap.put("nameCode", "HOUSE");
//					parameterMap.put("fieldName", "remark");
//					List<LoanProxyCheckDo> tempMap1 = loanApplyService.selectLoanProxyCheck(parameterMap);
//					CertificateDo temp = new CertificateDo();
//					temp.setLoanId(loanId);
//					temp.setLoanPersonId(loanPersonDo.getLoanPersonId());
//					temp.setCertificateType(CertificateType.HOUSE);
//					temp.setCertificateName("remark");
//					temp.setCertificateTypeHead(CertificateType.HOUSE.toString());
//					temp.setRelationType(propertyDo.getRemark());
//					temp.setCertificateId(propertyDo.getPropertyId());
//					if (null != tempMap1 && tempMap1.size() > 0) {
//						for (LoanProxyCheckDo proxyCheck : tempMap1) {
//							if (temp.getLoanId().longValue() == proxyCheck.getLoanId().longValue()) {
//								temp.setStatusId(proxyCheck.getId());
//								temp.setStatusInt(proxyCheck.getStatus());
//							}
//						}
//					}
//					cdListOut.add(temp);
//				}
				// 2万以下，有房产的不用取这些数据
//				if (loanDo.getApplyAmount() > applyAmount || loanPersonDo.getHasHouse().equals("F")) {
					int certificateTypeHead = 0;
					if(loanPersonDo.getPropertyDo()!=null &&loanPersonDo.getPropertyDo().getCarDy()!=null&&loanPersonDo.getPropertyDo().getCarDy()==1&&loanPersonDo.getHasHouse()!=null&&loanPersonDo.getHasHouse().equals("F")){
						for (CertificateDo temp : cdList) {
							if (temp.getCertificateType() != null && temp.getCertificateType().toString().equals("DRIVERCARD")) {
								if (null != tempMap && tempMap.size() > 0) {
									for (LoanProxyCheckDo proxyCheck : tempMap) {
										if (proxyCheck.getNameCode().toString().equals("DRIVERCARD") && temp.getCertificateId().longValue() == proxyCheck.getRecordId().longValue()) {
											temp.setStatusId(proxyCheck.getId());
											temp.setStatusInt(proxyCheck.getStatus());
										}
									}
								}
								if (certificateTypeHead++ == 0) {
									temp.setCertificateTypeHead("DRIVERCARD");
								}
								cdListOut.add(temp);
							}
						}
					}
					certificateTypeHead = 0;
					for (CertificateDo temp : cdList) {
						if (temp.getCertificateType() != null && temp.getCertificateType().toString().equals("INCOME")) {
							if (null != tempMap && tempMap.size() > 0) {
								for (LoanProxyCheckDo proxyCheck : tempMap) {
									if (proxyCheck.getNameCode().toString().equals("INCOME") && temp.getCertificateId().longValue() == proxyCheck.getRecordId().longValue()) {
										temp.setStatusId(proxyCheck.getId());
										temp.setStatusInt(proxyCheck.getStatus());
									}
								}
							}
							if (certificateTypeHead++ == 0) {
								temp.setCertificateTypeHead("INCOME");
							}
							cdListOut.add(temp);
						}
					}
//				}
			}
//			if (loanDo.getApplyAmount() <= applyAmount && loanPersonDo.getHasHouse().equals("T")) {
//				loanPersonDo.setHasHouse("3");
//			}
			// 页面4
		} else if (pageId.intValue() == 4) {
			// 加载联系人信息与对应状态
			parameterMap.put("tableCode", "LoanRelationDo");
			List<LoanProxyCheckDo> tempMap = loanApplyService.selectLoanProxyCheck(parameterMap);
			List<LoanRelationDo> lrdList = loanPersonDo.getLoanRelationDoList();
			if (lrdList != null && lrdList.size() > 0) {
				for (LoanRelationDo temp : lrdList) {
					CertificateDo tempAdd = new CertificateDo();
					tempAdd.setLoanId(loanId);
					tempAdd.setLoanPersonId(loanPersonDo.getLoanPersonId());
					tempAdd.setCertificateType(CertificateType.CREDIT);
					tempAdd.setCertificateName(temp.getRalationName());
					tempAdd.setCertificateTypeHead(temp.getMobile());
					tempAdd.setRelationType(temp.getRelationship());
					tempAdd.setCertificateId(temp.getRalationId());
					if (null != tempMap && tempMap.size() > 0) {
						for (LoanProxyCheckDo proxyCheck : tempMap) {
							if (tempAdd.getCertificateId().longValue() == proxyCheck.getRecordId().longValue()) {
								tempAdd.setStatusId(proxyCheck.getId());
								tempAdd.setStatusInt(proxyCheck.getStatus());
							}
						}
					}
					cdListOut.add(tempAdd);
				}
			}
		}
		return cdListOut;
	}

	/**
	 * 通知页面
	 * 
	 * @param loanId
	 * @param pageId
	 * @return
	 */
	@RequestMapping( "/tongzhi")
	public String tongzhi( Model model, HttpServletRequest request, HttpSession session){
		LoanNotifyDo queryNotifyDo = new LoanNotifyDo();
		AccountUserDo user = (AccountUserDo) session.getAttribute("user");
		queryNotifyDo.setRecievers("loanUserId:"+user.getId());
		List<LoanNotifyDo> temp = noticeService.selectNotify(queryNotifyDo);
		model.addAttribute("notifyDoList",temp);
		return "app/elend/tongzhi";
	}
	/**
	 * 通知明细面
	 * 
	 * @param loanId
	 * @param pageId
	 * @return
	 */
	@RequestMapping( "/tongzhi2")
	public String tongzhi2(Long messageId, Model model, HttpServletRequest request, HttpSession session){
		LoanNotifyDo queryNotifyDo = new LoanNotifyDo();
		AccountUserDo user = (AccountUserDo) session.getAttribute("user");
		queryNotifyDo.setRecievers("loanUserId:"+user.getId());
		queryNotifyDo.setMessageId(messageId);
		List<LoanNotifyDo> temp = noticeService.selectNotify(queryNotifyDo);
		if(temp!=null&&temp.size()==1){
			model.addAttribute("notifyDo",temp.get(0));
			if(temp.get(0).getSendFlag().equals("F")){
				tongzhiU(messageId);
			}
		}else{
			queryNotifyDo.setMessage("打开异常，请重新登录！");
			model.addAttribute("notifyDo",queryNotifyDo);
		}
		return "app/elend/tongzhi2";
	}

	/**
	 * 通知页面删除
	 * 
	 * @param loanId
	 * @param pageId
	 * @return
	 */
	@RequestMapping("/tongzhiD")
	@ResponseBody
	public Map tongzhiD(String messageId, Model model, HttpServletRequest request, HttpSession session) {
		Map m = new HashMap();
		String[] messageIdS = messageId.split(";");
		int idsLength = messageIdS.length;
		if (idsLength > 0) {
			for (int i = 0; i < idsLength; i++) {
				LoanNotifyDo updataNotifyDo = new LoanNotifyDo();
				updataNotifyDo.setMessageId(Long.valueOf(messageIdS[i]));
				updataNotifyDo.setValidate("F");
				int a = noticeService.updateMessage(updataNotifyDo);
			}
		}
		return m;
	}
	/**
	 * 通知页面更新状态
	 * 
	 * @param loanId
	 * @param pageId
	 * @return
	 */
	public void tongzhiU(Long messageId) {
		LoanNotifyDo updataNotifyDo = new LoanNotifyDo();
		updataNotifyDo.setMessageId(messageId);
		updataNotifyDo.setSendFlag("T");
		int a = noticeService.updateMessage(updataNotifyDo);
	}
	/**
	 * 更改订单状态
	 * 
	 * @param loanId
	 * @param pageId
	 * @return
	 */
	@RequestMapping("/updataLoanStatus")
	public String updataLoanStatus(Long loanId, int status, Model model, HttpSession session) {
		LoanDo saveLoanDo =new LoanDo();
		saveLoanDo.setLoanId(loanId);
		LoanDo loan = (LoanDo) session.getAttribute("loanDo");
		if(status == 0){
			saveLoanDo.setRemark("用户不同意贷款！");
			saveLoanDo.setProcessCurrentStep(ProcessStep.TO_EDIT);
			saveLoanDo.setProcessNextStep(ProcessStep.NULL);
			saveLoanDo.setLoanStatus(LoanStatus.INVALID);
		}else if(status == 1){
//			saveLoanDo.setApplyAmount(loan.getAuditAmount().compareTo(new BigDecimal(loan.getApplyAmount()))==1?loan.getApplyAmount():loan.getAuditAmount().intValue());
			saveLoanDo.setApplyAmount(confirmAmountCalculation(loan));
			saveLoanDo.setLoanStatus(LoanStatus.SUBJECTED);
		}else{
			model.addAttribute(WebConstants.MESSAGE_KEY, "系统异常，请稍后再试!");
			return "app/elend/loan_update";
		}
		loanApplyService.saveLoan(saveLoanDo);
		return "redirect:/app/elend/personalCenter";
	}
	/**
	 * 上传协议跳转页面
	 * 
	 * @param loanId
	 * @param pageId
	 * @return
	 */
	@RequestMapping("/uploadAgreement")
	public String uploadAgreement(Long loanId,Model model, HttpServletRequest request, HttpSession session) {
		List<CertificateDo> cdList = loanPersonService.initCertificateData(loanId);
		model.addAttribute("ENTRUST_PROTOCOL", "");
		model.addAttribute("CREDIT_AUDITK_PROTOCOL", "");
		for(CertificateDo temp:cdList){
			if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("ENTRUST_PROTOCOL")){
				model.addAttribute("ENTRUST_PROTOCOL", temp.getDestFilePath());
				model.addAttribute("ENTRUST_PROTOCOLNAME", temp.getCertificateName());
			}
			if(temp.getCertificateType()!=null&&temp.getCertificateType().toString().equals("CREDIT_AUDITK_PROTOCOL")){
				model.addAttribute("CREDIT_AUDITK_PROTOCOL", temp.getDestFilePath());
				model.addAttribute("CREDIT_AUDITK_PROTOCOLNAME", temp.getCertificateName());
			}
		}
		return "app/elend/personal_center_upload";
	}
	/**
	 * 通知页面金额计算
	 * 
	 * @param loanId
	 * @param pageId
	 * @return
	 */
	public double confirmAmountCalculation(LoanDo loanDo) {
    	double applyAmountTemp=loanDo.getApplyAmount();
    	if(loanDo.getAuditAmount()!=null){
    		double auditAmountTemp=loanDo.getAuditAmount().doubleValue();
    		if(applyAmountTemp-auditAmountTemp>0){
    			applyAmountTemp=auditAmountTemp;
    		}
    	}
		return applyAmountTemp;
	}
	
	/**
	 * 读取否是一线城市的，一线城市5W别的2W
	 * @param loanDo
	 * @param 
	 * @return
	 */
	public double getApplyAmountTemp(LoanDo loanDo) {
		String caddress = loanDo.getLoanPersonDo().getCaddress();
		double applyAmount = 20000.0;
		if (caddress != null && caddress.length() > 0) {
			String[] caddressArray = caddress.split("-");
			if (caddressArray != null && caddressArray.length > 2) {
				if (caddressArray[1].equals("深圳市") || caddressArray[1].equals("北京市") || caddressArray[1].equals("上海市") || caddressArray[1].equals("广州市") || caddressArray[1].equals("澳门市") || caddressArray[1].equals("香港市")) {
					applyAmount = 50000.0;
				}
			}
		}
		return applyAmount;
	}
	/**
	 * 解析房号
	 * @param loanPersonDo
	 * @param 
	 * @return
	 */
	private String getApplyAmountTemp(LoanPersonDo loanPersonDo) {
		PropertyDo temp =loanPersonDo.getPropertyDo();
		String houseNo="";
		if(temp!=null&&temp.getHouseAddress()!=null){
			String caddress =temp.getHouseAddress().trim();
			if (caddress != null && caddress.length() > 0) {
				String[] caddressArray = caddress.split(",");
				if (caddressArray != null && caddressArray.length >= 2) {
					houseNo=caddressArray[caddressArray.length-1];
				}
			}
		}
		return houseNo;
	}
	
}
