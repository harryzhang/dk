package com.hehenian.web.view.loan.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.loan.ICommonService;
import com.hehenian.biz.common.loan.ILoanApplyService;
import com.hehenian.biz.common.loan.ILoanDetailService;
import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.common.loan.dataobject.JobDo.JobType;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanRelationDo;
import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.notify.dataobject.LoanSMSNotifyDo;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.biz.common.trade.ISettleCalculatorService;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.IdCardUtils;
import com.hehenian.web.common.constant.WebConstants;

/**
 * @Description 首付宝
 * @author huangzl QQ: 272950754
 * @date 2015年4月1日 下午6:36:09
 * @Project hehenian-lend-web
 * @Package com.hehenian.web.view.loan.action
 * @File LoanDetailHouseAction.java
 */
@Controller
// @RequestMapping(value="/web")
public class LoanDetailHouseAction {
	private final Logger logger = Logger.getLogger(this.getClass());
	private DecimalFormat df = new DecimalFormat("##0.00");
	@Autowired
	private ILoanDetailService loanDetailService;
	@Autowired
	private ILoanApplyService loanApplyService;
	@Autowired
	private ISettleCalculatorService settleCalculatorService;
	@Autowired
	private INotifyService smsNotifyService;
	@Autowired
	private ICommonService commonService;

	/**
	 * 新增 申请 跳转界面
	 * 
	 * @author: huangzlmf
	 * @date: 2015年4月2日 21:09:54
	 * @param param
	 * @param sign
	 * @param map
	 * @return
	 */
	@RequestMapping("/house")
	public String house(HttpSession session) {
		logger.info("----house----");
		session.setAttribute("fromUrl", "house.do");
		return "/web/house/house_apply";
	}

	/** 短信发送人 */
	private String getMobileArr(String userMobile) {
		logger.info("----getMobileArr----");
		StringBuffer sb = new StringBuffer();
		sb.append("15019238715");
		return sb.toString();
	}

	/**
	 * 新增借款申请信息
	 * 
	 * @return
	 * @author: huangzlmf
	 * @date: 2015年4月2日 21:09:54
	 */
	@RequestMapping(value = "/addLoanDetailHouse")
	@ResponseBody
	public Map<String, Object> addLoanDetailHouse(String realName, String idNo, 
			                                      String mobile, Double jobIncome, 
			                                      Double applyAmount, int loanPeriod,
			                                      String loanProductType,
			                                      String cname,
			                                      String tel1,
			                                      String tel2,
			                                      String tel3,
			                                      String tel4,
			                                      HttpSession session,
			                                      HttpServletRequest request ,
			                                      HttpServletResponse response) {
		
		logger.info("----Start:addLoanDetail;realName=" + realName + "; idNo=" + idNo + "; mobile=" + mobile + "; jobIncome=" + 
		             jobIncome + "; applyAmount=" + applyAmount + "; loanPeriod=" + loanPeriod + ";"
		             +"; cname="+cname
		             +"; tel1="+tel1
		             +"; tel2="+tel2
		             +"; tel3="+tel3
		             +"; tel4="+tel4);
		Map<String, Object> map = new HashMap<String, Object>();
		
		///////////////////////////////testttttt/////////////////////////////////////
		AccountUserDo  userDo = new AccountUserDo();
		userDo.setId( -1L);
		////////////////////////////////////////////////////////////////////
		
//		AccountUserDo userDo =(AccountUserDo) session.getAttribute("user");
//		if(null==userDo || null==userDo.getId()){
//			map.put(WebConstants.MESSAGE_KEY, "用户登录过期，请重新登录！");
//			map.put("isSuccess", false);
//			return map;
//		}
		try {
			realName = URLDecoder.decode(realName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		LoanDo loanDo = new LoanDo();
		loanDo.setLoanPeriod(loanPeriod);
		loanDo.setUserId(userDo.getId());
		loanDo.setApplyAmount(applyAmount);
		loanDo.setLoanType(3);
		loanDo.setLoanUsage("房地产首付");
		loanDo.setChannelId(3L);
		Double annualRate = loanProductType.equals("0") ? 0.98 : 2 ;
		annualRate = CalculateUtils.mul(annualRate, 12d);
		loanDo.setAnnualRate(annualRate);
		loanDo.setProductCode("D06");
		loanDo.setOrderCode(commonService.generateOrderCode("D06"));
		loanDo.setLoanStatus(com.hehenian.biz.common.loan.dataobject.LoanDo.LoanStatus.PENDING);
		// 结算方案ID
		long schemeId =loanProductType.equals("0") ? 204 : 205;
		loanDo.setSchemeId(schemeId);

		LoanPersonDo loanPersonDo = new LoanPersonDo();
		loanPersonDo.setRealName(realName);
		loanPersonDo.setIdNo(idNo);
		loanPersonDo.setMobile(mobile);
		loanPersonDo.setAge(IdCardUtils.getAgeByIdCard(idNo));
		loanPersonDo.setCname(cname); //楼盘信息
		String sexString = IdCardUtils.getGenderByIdCard(idNo);
		loanPersonDo.setSex(sexString.equals("M") ? LoanPersonDo.Sex.MALE : LoanPersonDo.Sex.FEMALE);

		JobDo jobDo = new JobDo();
		jobDo.setJobIncome(jobIncome);
		jobDo.setCompanyName("未知");
		jobDo.setPosition("3");
		jobDo.setJobYear(0);
		jobDo.setCompanyPhone("未知");
		jobDo.setJobType(JobType.SALARYMAN);

		loanPersonDo.setJobDo(jobDo);
		loanDo.setLoanPersonDo(loanPersonDo);
		List<LoanRelationDo> temp =new ArrayList<LoanRelationDo>();
		LoanRelationDo relationDo = new LoanRelationDo();
		relationDo.setMobile(tel1);
		relationDo.setRalationName("家人1");
		relationDo.setRelationship("家人");
		relationDo.setRelationType(1);
		relationDo.setCreateTime(new Date());
		relationDo.setUpdateTime(new Date());
		temp.add(relationDo);
		
		LoanRelationDo relationDo2 = new LoanRelationDo();
		relationDo2.setMobile(tel2);
		relationDo2.setRalationName("家人2");
		relationDo2.setRelationship("家人");
		relationDo2.setRelationType(1);
		relationDo2.setCreateTime(new Date());
		relationDo2.setUpdateTime(new Date());
		temp.add(relationDo2);
		
		LoanRelationDo relationDo3 = new LoanRelationDo();
		relationDo3.setMobile(tel3);
		relationDo3.setRalationName("朋友1");
		relationDo3.setRelationship("朋友");
		relationDo3.setRelationType(3);
		relationDo3.setCreateTime(new Date());
		relationDo3.setUpdateTime(new Date());
		temp.add(relationDo3);
		
		LoanRelationDo relationDo4 = new LoanRelationDo();
		relationDo4.setMobile(tel4);
		relationDo4.setRalationName("朋友2");
		relationDo4.setRelationship("朋友");
		relationDo4.setRelationType(3);
		relationDo4.setCreateTime(new Date());
		relationDo4.setUpdateTime(new Date());
		temp.add(relationDo4);
		
		loanPersonDo.setLoanRelationDoList(temp);;

		Long res = loanApplyService.saveLoan(loanDo);
		
		if (res != null && res.longValue() > 0) {
			String smsTemp = loanDo.getLoanPersonDo().getRealName() + ",手机号码:" + loanDo.getLoanPersonDo().getMobile() + ",申请贷 款金额:" + loanDo.getApplyAmount() + "元,申请时间:" + new SimpleDateFormat("yyyy年MM月dd日").format(new Date()) + "";
			String smsStr = "首付贷新的贷 款申请订单：贷 款人:" + smsTemp;
			NotifyDo nd = new LoanSMSNotifyDo(smsStr, getMobileArr(""), "mail_template_default.ftl");
			smsNotifyService.send(nd);

			smsStr = "贷 款申请提交成功，正在受理：贷 款人:" + smsTemp;
			nd = new LoanSMSNotifyDo(smsStr, loanDo.getLoanPersonDo().getMobile(), "mail_template_default.ftl");
			smsNotifyService.send(nd);

			map.put(WebConstants.MESSAGE_KEY, "恭喜，申请成功!");
			map.put("isSuccess", true);

		} else {
			map.put(WebConstants.MESSAGE_KEY, "系统异常，请稍后再试!");
			map.put("isSuccess", false);
		}
		logger.info("----End:addLoanDetail;realName=" + realName + "; idNo=" + idNo + "; mobile=" + mobile + "; jobIncome=" + jobIncome + "; applyAmount=" + applyAmount + "; loanPeriod=" + loanPeriod + ";");
		return map;
	}

	/**
	 * 保存成功跳转到成功页面
	 * 
	 * @return
	 * @author:huangzlmf
	 * @date: 2015年4月2日 21:09:25
	 */
	@RequestMapping("/toSuccessHouse")
	public String toSuccessHouse() {
		logger.info("----toSuccessHouse----");
		return "/web/house/house_success";
	}

	/**
	 * 计算还款明细金额（本金，利息，还款总额）
	 * 
	 * @return
	 * @author:huangzlmf
	 * @date: 2015年4月2日 21:09:25
	 */
	@RequestMapping("/calRepayDetailHouse")
	@ResponseBody
	public List<Map<String, String>> calRepayDetailHouse(Double applyAmount, Integer loanPeriod, String loanProductType) {
		logger.info("----Start:calRepayDetail;applyAmount=" + applyAmount + "; loanPeriod=" + loanPeriod +";loanProductType="+loanProductType);
		Double annualRate = loanProductType.equals("0") ? 0.98 : 2 ;
		annualRate = CalculateUtils.mul(annualRate, 12d);
		List<SettDetailDo> settDetailDoList = settleCalculatorService.calSettDetailForRepayPlanShow(applyAmount, annualRate, loanPeriod, loanProductType.equals("0")?SettSchemeDo.SettleWay.FPIC:SettSchemeDo.SettleWay.MIFP,new Date());
		List<Map<String, String>> repayDetailList = new ArrayList<Map<String, String>>(settDetailDoList.size());
		double totalAmount = 0.00;
		for (SettDetailDo detailDo : settDetailDoList) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("times", detailDo.getPeriod() + "");
			map.put("principal", df.format(detailDo.getPrincipal()));
			double interest = detailDo.getInterest();
			map.put("interest", df.format(interest));
			double repayAmount = CalculateUtils.add(interest, detailDo.getPrincipal());
			map.put("repayAmount", df.format(repayAmount));
			totalAmount = CalculateUtils.add(totalAmount, repayAmount);
			map.put("totalAmount", df.format(totalAmount));
			repayDetailList.add(map);
		}
		logger.info("----End:calRepayDetail;applyAmount=" + applyAmount + "; loanPeriod=" + loanPeriod + ";");
		return repayDetailList;
	}

}
