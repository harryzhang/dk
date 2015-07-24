/**  
 * @Project: hehenian-mobile
 * @Package com.hehenian.mobile.web.controller.finance
 * @Title: InvestControll.java
 * @Description: 投资
 *
 * @author: zhanbmf
 * @date 2015-3-31 上午11:34:23
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.web.controller.finance;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.IUserThirdPartyService;
import com.hehenian.biz.common.account.UserType;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.InviteCodeDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.account.dataobject.UserThirdPartyDo;
import com.hehenian.biz.common.colorlife.ColorLifeBuyService;
import com.hehenian.biz.common.dqlc.IDqlcService;
import com.hehenian.biz.common.trade.IOperationLogService;
import com.hehenian.biz.common.userhome.IUserIncomeService;
import com.hehenian.biz.common.userhome.dataobject.UserIncomeDo;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.common.util.DesSecurityUtil;
import com.hehenian.biz.common.util.huifu.IConstants;
import com.hehenian.biz.common.wygj.IOffsetService;
import com.hehenian.biz.common.wygj.IParkingFeeService;
import com.hehenian.biz.common.wygj.IPropertyManagementFeeService;
import com.hehenian.biz.common.wygj.SysCodeService;
import com.hehenian.biz.common.wygj.dataobject.OffsetDetailsDo;
import com.hehenian.biz.common.wygj.dataobject.OffsetRecordsDo;
import com.hehenian.biz.common.wygj.dataobject.ParkingDetailDo;
import com.hehenian.biz.common.wygj.dataobject.ParkingFeeDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementDetailDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementFeeDo;
import com.hehenian.common.annotations.RequireLogin;
import com.hehenian.common.utils.ResponseUtils;
import com.hehenian.mobile.common.utils.ColorLifeUtils;
import com.hehenian.mobile.common.utils.PreciseCompute;
import com.hehenian.mobile.web.controller.BaseController;
import com.hhn.hessian.cardverify.ICardVerifyService;
import com.hhn.hessian.invest.IFundInvestService;
import com.hhn.hessian.invest.IInvestProductService;
import com.hhn.hessian.query.IQueryService;
import com.hhn.hessian.recharge.IRechargeService;
import com.hhn.pojo.FundBankCard;
import com.hhn.pojo.FundTrade;
import com.hhn.pojo.FundUserAccount;
import com.hhn.pojo.Invest;
import com.hhn.pojo.ProductRate;
import com.hhn.util.BaseReturn;
import com.hhn.util.Constants;
import com.hhn.util.DqlcConfig;

@Controller
@RequestMapping(value = "/finance")
public class InvestControll extends BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private IFundInvestService fundInvestmentService;
	@Autowired
	private IInvestProductService investProductService;
	@Autowired
	private IQueryService queryService;
	@Autowired
	private ICardVerifyService cardVerifyService;
	@Autowired
	public DqlcConfig dqlcConfig;
	@Autowired
	private IDqlcService dqlcService;
	@Autowired
    private IRechargeService rechargeService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserThirdPartyService userThirdPartyService;
	@Autowired
	private IOperationLogService operationLogService;
	
	@Autowired
	private ColorLifeBuyService colorLifeBuyService;
	

	@Autowired
    private IPersonService personService;
	
	@Autowired
	private IUserIncomeService userIncomeService;
	

	@Autowired
	private IParkingFeeService parkingFeeService;
	
	@Autowired
	private IOffsetService offsetService;
	
	@Autowired
	private SysCodeService sysCodeService;

	@Autowired
	private IPropertyManagementFeeService manageFeeService;
	
	/**
	 * 购买参数输入
	 * @param request
	 * @param response
	 * @return
	 * @author: zhanbmf
	 * @date 2015-3-30 下午4:37:58
	 */
	@RequireLogin(injectPersonDo = true)
	@RequestMapping(value = "prepay")
	public String prepay(HttpServletRequest request,HttpServletResponse response) {
		int pId = NumberUtils.toInt(request.getParameter("pid"), -1);
		long userId = super.getUserId();
		//绑定卡标识
		int bankCardId = NumberUtils.toInt(request.getParameter("bank_card_id"), -1);
		if(pId <= 0) {
			return "redirect:/product/plist.do";
		}
		
		BaseReturn prbr = investProductService.getProductRateById(pId);
		if(prbr.getReturnCode() != 0 || prbr.getData() == null) {
			return "redirect:/product/plist.do";
		}
		
		ProductRate pr = (ProductRate)prbr.getData();
		if(pr.getChannel() == 1 && pr.getSub_channel() == 1) {
			InviteCodeDo inviteDO = userService.findInviteCodeByDO(new InviteCodeDo(super.getUserId()));
			//如果不是员工
			if(inviteDO == null) {
				return "redirect:/product/plist.do";
			}
		}
		
		request.setAttribute("product", pr);
		FundBankCard fbc = null;
		if(bankCardId > 0) {
			BaseReturn cardBr = cardVerifyService.getBankCardById(bankCardId);
			if(cardBr != null && cardBr.getReturnCode() == 0 && cardBr.getData() != null) {
				fbc = (FundBankCard)cardBr.getData();
				if(fbc.getUser_id().intValue() != Long.valueOf(userId).intValue() ) {
					return "redirect:/product/plist.do";
				}
				
				fbc.setCard_no(Constants.getLastFourCardNo(fbc.getCard_no()));
				request.setAttribute("bingCard", fbc);
			}
		}else{
			//BaseReturn cardReturn = queryService.queryBindedBankCard(Long.valueOf(super.getUserId()).intValue());
			//获取绑定银行卡queryBindedBankCard
			BaseReturn bankReturn = queryService.queryBankCard(Long.valueOf(userId).intValue() );
			if(bankReturn.getReturnCode() == 0 && bankReturn.getData() != null) {
				fbc = (FundBankCard)bankReturn.getData();
				fbc.setCard_no(Constants.getLastFourCardNo(fbc.getCard_no()));
				request.setAttribute("bingCard", fbc);
			}
		}
		
		//账户余额
		BaseReturn balance = queryService.queryUserBalance(Long.valueOf(super.getUserId()).intValue());
		//查询用户余额
		BigDecimal balanceAmount = new BigDecimal(0);
		if (balance.getReturnCode() == 0 && balance.getData() != null) {
			FundUserAccount balanceInfo = (FundUserAccount) balance.getData();
			if (balanceInfo != null) {
				balanceAmount = balanceInfo.getBalance_amount() == null ? new BigDecimal(0) : balanceInfo.getBalance_amount();
			}
		}
		request.setAttribute("balanceAmount", balanceAmount);
		BaseReturn existProduct = queryService.queryPay();
		BigDecimal remainAmount = BigDecimal.ZERO;
		if (existProduct.getReturnCode() != 0 || existProduct.getData() == null) {
			request.setAttribute("canInvest", dqlcConfig.investMoneyScope);
		} else {
			BigDecimal canInvest = (BigDecimal) existProduct.getData();
			if (null != canInvest) {
				remainAmount = canInvest.add(new BigDecimal(dqlcConfig.investMoneyScope));
				if (BigDecimal.ZERO.compareTo(remainAmount) > 0) {
					remainAmount = BigDecimal.ZERO;
				}
			}
			request.setAttribute("canInvest", remainAmount);
		}
		return "finance/prepay";
	}
	
	/**
	 * 产品详情
	 * @param request
	 * @param response
	 * @return
	 * @author: zhanbmf
	 * @date 2015-3-30 下午4:37:58
	 */
	@RequireLogin(injectPersonDo = true)
	@RequestMapping(value = "invest")
	public void invest(HttpServletRequest request,HttpServletResponse response) {
		BaseReturn br = new BaseReturn(BaseReturn.Err_data_inValid, "");
		//选购产品
		int pId = NumberUtils.toInt(request.getParameter("pid"), -1);
		//购买金额
		int amount = NumberUtils.toInt(request.getParameter("amount"));
		//绑定卡标识
		int bankCardId = NumberUtils.toInt(request.getParameter("bank_card_id"), -1);
		//推荐人
		String recommentPhone = request.getParameter("recommentPhone");
		if(pId <= 0 || amount < 1) {
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
			return;
		}
		
		//实名认证 设置支付密码
		PersonDo pd = super.getPerson();
		BaseReturn result =  checkP(request, response, pd);
		if(result.getReturnCode()>0){
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(result).toString()) ;
			return;
		}
		
		
		//查询用户手机号
		Integer userId = Long.valueOf(super.getUserId()).intValue();
		AccountUserDo aud = super.getAccountUser();
		
		BaseReturn prbr = investProductService.getProductRateById(pId);
		if(prbr.getReturnCode() != 0 || prbr.getData() == null) {
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
			return;
		}
		
		
		String mobile = aud.getMobilePhone();
		if (StringUtils.isNotBlank(mobile) && mobile.startsWith("-")) {
			mobile = mobile.substring(1);
		}
		ProductRate pr = (ProductRate)prbr.getData();
		
		HttpSession session = request.getSession();
		//用户投资金额
		BigDecimal investAmount = new BigDecimal(amount);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", userId);
		
		//jiangwmf added 
		//添加冲抵相关信息
		map.put("offsetDetailId", request.getParameter("offsetDetailId"));
		map.put("offsetfee", request.getParameter("offsetfee"));
		map.put("offsetBegin", request.getParameter("offsetBegin"));
		map.put("offsetEnd", request.getParameter("offsetEnd"));
		//end		
		
		//渠道来源
		String source = super.getSourcFrom();
		logger.debug("sourceFrom:=======>"+source);
		
		
		// 查询投资人账户余额
		BaseReturn balance = queryService.queryUserBalance(userId);
		FundUserAccount userAccount = (FundUserAccount) balance.getData();
		BigDecimal accountBalance = new BigDecimal(0);
		if (userAccount != null &&  userAccount.getBalance_amount() != null) {
			accountBalance = userAccount.getBalance_amount();
		}
		session.setAttribute("userBalance", accountBalance);

		//add zhangjhmf 2015-04-07 添加代理人账户判断
        int accountType = 0;
        if(aud != null){
        	accountType= aud.getAccountType();
        }
        if(1 == accountType){
        	FundBankCard fundBankCard = new FundBankCard();
        	 //如果账户余额足够，则直接购买
            if (investAmount.doubleValue() <= accountBalance.doubleValue()) {
            	br = doUserInvest(userId, pr, investAmount, investAmount, new BigDecimal("0.00"), source, fundBankCard.getCard_no(), fundBankCard.getBank_code(), fundBankCard.getBank_name(),recommentPhone, map);
            	map.put("investPro", pr.getProduct_name());
            	System.out.println(JSONObject.fromObject(br).toString());
            	ResponseUtils.renderJson(response, ResponseUtils.UTF8, JSONObject.fromObject(br).toString()) ;
				return;
            }else{
            	br.setReturnCode(8);
    			br.setMessageInfo("账户余额不足");
    			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
    			return;
            }
       }else{
    		// 查询绑卡信息
   		// 否则判断是否先绑卡
   		// HashMap<String,String> param = new HashMap<String,String>();
   		// param.put("userId", userId.toString());
   		// BaseReturn bankCard = cardVerifyService.queryOldBindingCard(param);
    	   
    	   
   		BaseReturn bankCard = cardVerifyService.getBankCardById(bankCardId);
   		FundBankCard fundBankCard = (FundBankCard) bankCard.getData();
   		logger.debug("bankCard info:" + fundBankCard);
//   		if (bankCard.getReturnCode() != 0 || fundBankCard == null || StringUtils.isBlank(fundBankCard.getCard_no())) {
//   			//return new BaseReturn(1, bankCard.getData(), bankCard.getMessageInfo());
//   			//return new BaseReturn(2, "请先绑定银行卡");
//   			br.setReturnCode(2);
//   			br.setMessageInfo("请先绑定银行卡");
//   			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
//   			return;
//   		}

   		
   		Map<String, Object> bankInfo = new HashMap<String, Object>();
   		bankInfo.put("bankCode", fundBankCard.getBank_code());
   		bankInfo.put("bankNo", fundBankCard.getCard_no());
   		BaseReturn bandingCount = queryService.getBingCount(Long.valueOf(super.getUserId()).intValue());
   		bankInfo.put("bankCount", ((Integer) (bandingCount.getData())).intValue());

   		
   		// 投资金额大于风控金额时，需要验证码
/*   		BigDecimal moneySms = new BigDecimal(dqlcConfig.MONEY_NEED_SMS);
   		String verifyCode = request.getParameter("verifyCode");
   		if (investAmount.doubleValue() >= moneySms.doubleValue()) {
   			if (StringUtils.isBlank(verifyCode)) {
   				logger.debug("bankCard info:" + fundBankCard);
   				if (fundBankCard != null && fundBankCard.getBank_status() == 2) {
   					//return new BaseReturn(3, bankInfo, "输入验证金额");
   					br.setReturnCode(3);
   					br.setMessageInfo("输入验证金额");
   					ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
   					return;
   				}
   				map.put("bankName", fundBankCard.getBank_name());
   				map.put("bankNo", fundBankCard.getCard_no());
   				map.put("bankCode", fundBankCard.getBank_code());
   				//return new BaseReturn(4, map, "请提交验证码");
   				br.setReturnCode(4);
   				br.setMessageInfo("请提交验证码");
   				ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
   				return;
   			} else {
   				boolean flag = dqlcService.checkPhoneVerifyCode(mobile, verifyCode);
   				if (!flag) {
   					//return new BaseReturn(1, "验证码不正确");
   					br.setReturnCode(1);
   					br.setMessageInfo("验证码不正确");
   					ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
   					return;
   				}
   			}
   		} else {
               if (StringUtils.isBlank(verifyCode)){
                   if (fundBankCard==null || StringUtils.isEmpty(fundBankCard.getCard_no())){
                       //return new BaseReturn(2, "请先绑定银行卡");
                    br.setReturnCode(2);
   					br.setMessageInfo("您尚未绑定银行卡,无法进行购买/充值.");
   					ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
   					return;
                   }
                   if (fundBankCard!=null && fundBankCard.getBank_status()==2){
                       //return new BaseReturn(3, bankInfo,"输入验证金额");
                       br.setReturnCode(3);
   					br.setMessageInfo("输入验证金额");
   					ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
   					return;
                   }
               }else{
                   boolean flag = dqlcService.checkPhoneVerifyCode(mobile, verifyCode);
                   if (!flag) {
                       //return new BaseReturn(1, "验证码不正确");
                       br.setReturnCode(1);
   					br.setMessageInfo("验证码不正确");
   					ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
   					return;
                   }
               }
           }*/
   		
   		if (recommentPhone!=null && StringUtils.isNotEmpty(recommentPhone)){
               session.setAttribute("recommentPhone", recommentPhone);
           }else{
               recommentPhone = (String)session.getAttribute("recommentPhone");
           }
   		
   		//是否完成实名认证  to do...
   		
   		//是否完成支付密码设定  to do...
   		
   		logger.debug("realName==============>" + pd.getRealName());
   		session.setAttribute("userHidPhone", Constants.getHidePhone(mobile));
   		logger.debug("==============>" + Constants.getHidePhone(mobile));
   		session.setAttribute("userPhone", mobile);
   		logger.debug("==============>" + Constants.getHidePhone(mobile));
   		session.setAttribute("realName", pd.getRealName());
   		session.setAttribute("idNo", Constants.getHideIdNo(pd.getIdNo()));

   		

   		//0:勾选余额，1:未勾选余额
           String payStatus = request.getParameter("payStatus");
           //map.put("userName", super.getUserName(request));
           map.put("userName", aud.getUsername());
           map.put("idNo", pd.getIdNo());
           map.put("investPro", pr.getProduct_name());
           if("1".equals(payStatus)){
               map.put("AMOUNT", investAmount.toString());
               logger.debug("invoke interface charge money parameter: user_id==>" + map.get("user_id") + ",amount==>" + map.get("AMOUNT"));
               logger.debug("charge money...............................start");
               BaseReturn chargeReturn = rechargeService.recharge(map);
               logger.debug("charge money ..............................end,returnCode:"+chargeReturn.getReturnCode());

               if (chargeReturn.getReturnCode()==0) {
//                   return doUserInvest(userId, rateId, mounth, investAmount, accountBalance, investAmount, source, fundBankCard.getCard_no(), fundBankCard.getBank_code(), fundBankCard.getBank_name(), recommentPhone, map);
                   //return doUserInvest(userId, pr.getProduct_rate_id().toString(), pr.getPeriod().toString(), investAmount, accountBalance, investAmount, source, fundBankCard.getCard_no(), fundBankCard.getBank_code(), fundBankCard.getBank_name(), recommentPhone, map);
                   br = doUserInvest(userId, pr, investAmount, accountBalance, investAmount, source, fundBankCard.getCard_no(), fundBankCard.getBank_code(), fundBankCard.getBank_name(), recommentPhone, map);
                   //return chargeReturn;
   				ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
   				return;
               }else if(chargeReturn.getReturnCode() == 1){
                   Map<String,Object> returnMap = new HashMap<String,Object>();
                   returnMap.put("bankInfo", fundBankCard);
                   returnMap.put("data", chargeReturn.getData());
                   chargeReturn.setData(returnMap);
                   //return chargeReturn;
   				ResponseUtils.renderJson(response, null, JSONObject.fromObject(chargeReturn).toString()) ;
   				return;
               }else {
                   //return chargeReturn;
               	//return chargeReturn;
   				ResponseUtils.renderJson(response, null, JSONObject.fromObject(chargeReturn).toString()) ;
   				return;
               }
           }else {
               //如果账户余额足够，则直接购买
               if (investAmount.doubleValue() <= accountBalance.doubleValue()) {
                   //BigDecimal afterBalance = accountBalance.subtract(investAmount);
//                   return doUserInvest(userId, rateId,mounth, investAmount, investAmount, new BigDecimal("0.00"), source, fundBankCard.getCard_no(), fundBankCard.getBank_code(), fundBankCard.getBank_name(),recommentPhone, map);
                   //return doUserInvest(userId, pr.getProduct_rate_id().toString() ,pr.getPeriod().toString(), investAmount, investAmount, new BigDecimal("0.00"), source, fundBankCard.getCard_no(), fundBankCard.getBank_code(), fundBankCard.getBank_name(),recommentPhone, map);
               	br = doUserInvest(userId, pr, investAmount, investAmount, new BigDecimal("0.00"), source, fundBankCard.getCard_no(), fundBankCard.getBank_code(), fundBankCard.getBank_name(),recommentPhone, map);
               	System.out.println(JSONObject.fromObject(br).toString());
               	ResponseUtils.renderJson(response, ResponseUtils.UTF8, JSONObject.fromObject(br).toString()) ;
   				return;
               }

               //充值后购买
               BigDecimal chargeAmount = investAmount.subtract(accountBalance);
               map.put("AMOUNT", chargeAmount.toString());
               logger.debug("invoke interface charge money parameter: user_id==>" + map.get("user_id") + ",amount==>" + map.get("AMOUNT"));
               logger.debug("charge money...............................start");
               BaseReturn chargeReturn = rechargeService.recharge(map);
               logger.debug("charge money ..............................end,returnCode:" + chargeReturn.getReturnCode());

               if (chargeReturn.getReturnCode() == 0) {
//                   return doUserInvest(userId, rateId,mounth, investAmount, accountBalance, chargeAmount, source, fundBankCard.getCard_no(), fundBankCard.getBank_code(), fundBankCard.getBank_name(),recommentPhone, map);
                   //return doUserInvest(userId, pr.getProduct_rate_id().toString() ,pr.getPeriod().toString(), investAmount, accountBalance, chargeAmount, source, fundBankCard.getCard_no(), fundBankCard.getBank_code(), fundBankCard.getBank_name(),recommentPhone, map);
               	br = doUserInvest(userId, pr, investAmount, accountBalance, chargeAmount, source, fundBankCard.getCard_no(), fundBankCard.getBank_code(), fundBankCard.getBank_name(),recommentPhone, map);
               	ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
   				return;
               }else if(chargeReturn.getReturnCode() == 1){
                   Map<String,Object> returnMap = new HashMap<String,Object>();
                   returnMap.put("bankInfo", fundBankCard);
                   returnMap.put("data", chargeReturn.getData());
                   chargeReturn.setData(returnMap);
                   //return chargeReturn;
               }else {
                   //return chargeReturn;
               }
               
               ResponseUtils.renderJson(response, null, JSONObject.fromObject(chargeReturn).toString()) ;
   			return;
           }
       }
		
	}
	
	private BaseReturn checkP(HttpServletRequest request,HttpServletResponse response, PersonDo person){
		BaseReturn br = new BaseReturn();
		//是否完成实名认证  to do...
   		//PersonDo person = personService.getByUserId(super.getUserId());
   		//是否完成支付密码设定  to do...
   		AccountUserDo audo = userService.getById(super.getUserId());
   		//是否已经绑定银行卡
//   		BaseReturn bankCard = cardVerifyService.getBankCardById(bankCardId);
//   		FundBankCard fundBankCard = (FundBankCard) bankCard.getData();
//   		logger.debug("bankCard info:" + fundBankCard);
   		
   		//Long userId = getUserId();
   		//绑定的银行卡信息
   		BaseReturn cardReturn = queryService.queryBindedBankCard(Long.valueOf(super.getUserId()).intValue());
   		List<FundBankCard> list = (List)cardReturn.getData();
   		System.out.println("person======" + person == null);
   		if(NumberUtils.toInt(ObjectUtils.toString(person.getAuditStatus(), "-100"), -100) != 3){
   			br.setReturnCode(5);
			br.setMessageInfo("您尚未完成实名认证,无法进行购买/充值.");
   		}else if(org.springframework.util.StringUtils.isEmpty(audo.getPayPassword())){
   			br.setReturnCode(6);
			br.setMessageInfo("您尚未设置支付密码,无法进行购买/充值.");
   		}else if (list.size() <= 0) {
   			br.setReturnCode(7);
			br.setMessageInfo("您尚未绑定银行卡,无法进行购买/充值.");
   		}
   		return br;
	}
	
	/**
	 * 添加银行之前先验证是否已经实名验证，设置支付密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequireLogin(injectPersonDo = true)
	@RequestMapping(value = "setUp")
    public void setUp(HttpServletRequest request,HttpServletResponse response){
		BaseReturn br = checkP(request, response, super.getPerson());
		ResponseUtils.renderText(response, null, JSONObject.fromObject(br).toString());
    }
	
	//用户投资处理
    private BaseReturn doUserInvest(Integer userId,ProductRate pr,BigDecimal investAmount,BigDecimal balance,BigDecimal charge,String source,String bankNo,String bankCode,String bankName,String recommentPhone,Map<String, Object> map){
        Invest invest = new Invest();
        invest.setUser_id(userId);
        invest.setMonth(Integer.valueOf(pr.getPeriod()));
        invest.setRateId(Integer.valueOf(pr.getProduct_rate_id()));
        invest.setMoney(investAmount);
        invest.setTargetType(source);
        invest.setRecommendPhone(recommentPhone);
        logger.debug("user investment start..........................");
        BaseReturn investReturn = fundInvestmentService.investment(invest);
        logger.debug("user investment end............................returnCode:"+investReturn.getReturnCode());
        if (investReturn.getReturnCode()==0){
        	//+多宝、+车宝 需要插入冲抵期限和明细信息
        	if(pr.getChannel()==1&&(pr.getSub_channel()==2||pr.getSub_channel()==3)){
        		this.insertOffsetLogs(userId,pr,(FundTrade)investReturn.getData(),map);
        	}
            map.put("mouth", pr.getPeriod());
            map.put("investAmount", investAmount);
            map.put("balanceAmount", balance);
            map.put("chargeAmount", charge);
            map.put("bankNo", Constants.getHideBandNo(bankNo));
            map.put("bankCode",bankCode);
            map.put("bankName",bankName);
            map.put("data",investReturn.getData());
            investReturn.setData(map);
            return investReturn;
        }else{
            return new BaseReturn(1, investReturn.getData(),investReturn.getMessageInfo());
        }
    }
    /**
     * 增加+多宝、+车宝的冲抵记录和冲抵明细
     * @param userId
     * @param pr
     * @param fundTrade
     * @param map
     */
    private void insertOffsetLogs(Integer userId,ProductRate pr,FundTrade fundTrade,Map<String, Object> map){
    		//物业国际 getChannel =1、 +多宝getSub_channel()==2、 +车宝getSub_channel()==3
    	
    		//更新冲抵地址的状态为 已冲抵
    		if(pr.getSub_channel()==2){//多宝冲抵物业费
    			PropertyManagementDetailDo pmdd = new PropertyManagementDetailDo();
    			pmdd.setId(Integer.parseInt(map.get("offsetDetailId").toString()));
    			pmdd.setInfotype(1);
    			manageFeeService.updatePropertyManagementDetailDo(pmdd);
    		}else if(pr.getSub_channel()==3){//车宝冲抵停车费	
    			ParkingDetailDo pdd = new ParkingDetailDo();
    			pdd.setId(Integer.parseInt(map.get("offsetDetailId").toString()));
    			pdd.setInfotype(1);
    			parkingFeeService.updateParkingDetail(pdd);
    		}
        	//增加+多宝、+车宝的冲抵记录和冲抵明细
    		OffsetRecordsDo ord = new OffsetRecordsDo();
    		ord.setId(Integer.parseInt(map.get("offsetDetailId").toString()));
        	ord.setTrade_id(fundTrade.getTrade_id());
        	ord.setUser_id(userId);
        	ord.setBegindate(map.get("offsetBegin").toString());
        	ord.setEnddate(map.get("offsetEnd").toString());
        	ord.setFee(Double.parseDouble(map.get("offsetfee").toString()));
    		if(pr.getSub_channel()==2){//多宝冲抵物业费
        		ord.setInfotype(0);
        	}else if(pr.getSub_channel()==3){//车宝冲抵停车费
        		ord.setInfotype(1);
        	}
        	int rCount  = offsetService.insertOffsetRecord(ord);
        	if(rCount>0){
 		   //2.增增加冲抵明细 t_offset_detailinfo
        		int factPeriod = 0;
        		if(pr.getChannel()==1 && pr.getSub_channel()==3){//车宝
        			factPeriod = pr.getPeriod()+1;
        		}else if(pr.getChannel()==1 && pr.getSub_channel()==2){//多宝
        			factPeriod = pr.getPeriod();
        		}
        		for (int i = 1; i <= factPeriod; i++) {
        			OffsetDetailsDo odd = new OffsetDetailsDo();
        			odd.setTrade_id(fundTrade.getTrade_id());
        			odd.setTimeframe(i+"/"+factPeriod);
        			odd.setOffsetdate(DateUtils.getYearAfter(Calendar.MONTH,i)+"-" + getMonthStr(DateUtils.getMonthAfter(Calendar.MONTH,i)));
        			odd.setInfostatus(0);
        			odd.setFee(Double.parseDouble(map.get("offsetfee").toString()));
        			odd.setIsvalid(1);//默认有效，赎回后置为无效
        			offsetService.insertOffsetDetail(odd);
				}
        	}
    }
    
    @RequestMapping(value = "agreement")
    public String agreement(){
    	//是否是彩生活渠道channel=1彩生活渠道|0官方渠道    物业国际普通通道   员工通道
		int channel = 1;
		int subChannel = NumberUtils.toInt(request.getParameter("subChannel"), 0);
    	return "finance/serviceAgreement";
    }
    
    /**
     * 进入到绑卡页面 http://www.hehenian.com/hhn_web/bindCardPhonePage.do
     * @param request
     * @param response
     * @return
     * @author: zhanbmf
     * @date 2015-4-1 上午3:40:37
     */
    @RequireLogin(injectPersonDo = true)
	@RequestMapping(value = "bindCard", method = RequestMethod.GET)
	public String bindCard(HttpServletRequest request,HttpServletResponse response) {
    	Map<String, Object> map = new HashMap<String, Object>();
        //查询账户名和手机号
        PersonDo pd = super.getPerson();
        map.put("realName", pd.getRealName()==null?"":pd.getRealName());
        map.put("idNo", Constants.getHideIdNo(pd.getIdNo()));
        
        request.setAttribute("result", map);
        return "finance/bindBank";
    }
    
    @RequireLogin
    @RequestMapping(value = "bindCardPhone")
    public void bindCardPhone(HttpServletRequest request,HttpServletResponse response) {
        try {
            Map<String, String> param = new HashMap<String, String>();
            String bankCode = request.getParameter("bankCode");//银行代码
            String userAccount = request.getParameter("userAccount");//银行卡号
            userAccount = userAccount.replaceAll(" ", "");
            if (bankCode == null || "".equals(bankCode)) {
                ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"银行代码不能为空")).toString());
                return;
            } else {
                param.put("bankCode", bankCode);
            }
            if (userAccount == null || "".equals(userAccount)) {
                ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"银行卡号不能为空")).toString());
                return;
            } else {
                param.put("bankNo", userAccount);
            }
            param.put("userId", String.valueOf(super.getUserId()));
            logger.debug("bindCardPhone Parameter: bankNo="+userAccount+",bankNo="+bankCode);
            logger.debug("bindCardPhone invocation start............................");
            BaseReturn baseReturn = cardVerifyService.sendBankIdentifyCode(param);
            logger.debug("bindCardPhone sendBankIdentifyCode:::"+baseReturn.getReturnCode());
            logger.debug("bindCardPhone invocation end..............................");
            ResponseUtils.renderText(response, null, JSONObject.fromObject(baseReturn).toString());
        }catch (Exception e){
        	ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"系统正忙请稍后重试")).toString());
        }
    }
    
    /**
     * 绑卡下一步（金额验证页）
     * @param request
     * @param response
     * @return
     * @author: chenzhpmf
     * @date 2015-4-4 下午8:31:24
     */
    @RequireLogin
    @RequestMapping(value = "moneyVerify", method = RequestMethod.GET)
    public String moneyVerification(HttpServletRequest request,HttpServletResponse response){
    	return "finance/moneyVerification";
    }
    
    
    /**
     * 验证银行卡
     * @param request
     * @param response
     * @author: chenzhpmf
     * @date 2015-4-4 下午8:30:48
     */
    @RequireLogin
    @RequestMapping(value = "verifyCard", method = RequestMethod.POST)
    public void verifyCard(HttpServletRequest request,HttpServletResponse response) {
    	Map<String, String> map = new HashMap<String, String>();
        String money = request.getParameter("money");//金额;
        if (StringUtils.isBlank(money)){
        	ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"金额不能为空")).toString());
        	return;
        }
        String account = request.getParameter("account");
        if (account==null || StringUtils.isEmpty(account)){
        	ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"付款银行不能为空")).toString());
        	return;
        }
        Long userId = getUserId();
        map.put("userId", String.valueOf(userId));
        map.put("amount", money);
        map.put("account", account);
        try {
            logger.debug("verifyCardPhone checkBankIdentifyCode::userId:::" + userId + "::amount::" + money);
            logger.debug("verifyCardPhone invocation start............................");
            BaseReturn baseReturn = cardVerifyService.checkBankIdentifyCode(map);
            logger.debug("verifyCardPhone return checkBankIdentifyCode:"+baseReturn.getReturnCode());
            logger.debug("verifyCardPhone invocation end..............................");
            ResponseUtils.renderText(response, null, JSONObject.fromObject(baseReturn).toString());
        }catch (Exception e){
            logger.error(e);
            ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"系统正忙请稍后重试")).toString());
        }
    }
    
    
    /**
	 * 产品详情
	 * @param request
	 * @param response
	 * @return
	 * @author: zhanbmf
	 * @date 2015-3-30 下午4:37:58
	 */
	@RequireLogin(injectPersonDo = true)
	@RequestMapping(value = "investHb")
	public void investHb(HttpServletRequest request,HttpServletResponse response) {
		BaseReturn br = new BaseReturn(BaseReturn.Err_data_inValid, "");
		//选购产品
		int pId = NumberUtils.toInt(request.getParameter("pid"), -1);
		//购买金额
		int amount = NumberUtils.toInt(request.getParameter("amount"));
		String code = request.getParameter("code");
		//推荐人
		if(pId <= 0) {
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
			return;
		}
		BaseReturn prbr = investProductService.getProductRateById(pId);
		if(prbr.getReturnCode() != 0 || prbr.getData() == null) {
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
			return;
		}
		
		//查询用户手机号
		Integer userId = Long.valueOf(super.getUserId()).intValue();
		AccountUserDo aud = super.getAccountUser();
		PersonDo pd = super.getPerson();
		if(pd.getAuditStatus() != 3){
			br.setReturnCode(1);
			br.setMessageInfo("请进行实名认证！");
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
			return ;
		}
		String mobile = aud.getMobilePhone();
		if (StringUtils.isNotBlank(mobile) && mobile.startsWith("-")) {
			mobile = mobile.substring(1);
		}
		ProductRate pr = (ProductRate)prbr.getData();
		
		HttpSession session = request.getSession();
		//用户投资金额
		BigDecimal investAmount = new BigDecimal(amount);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", userId);
		
		//渠道来源
		String source = super.getSourcFrom();
		logger.debug("sourceFrom:=======>"+source);
		
		
		// 查询投资人账户余额
		UserThirdPartyDo upo = userThirdPartyService.getByUserId(new Long(userId).intValue());
		String oauser = upo == null ? "" : upo.getThethirdusername();
		Float balance = ColorLifeUtils.getEmpBalance(oauser);
//		balance = 1000f;
		BigDecimal accountBalance = new BigDecimal(balance);
		if(balance.doubleValue() - investAmount.doubleValue() < 0){
			br.setReturnCode(8);
			br.setMessageInfo("账户余额不足");
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
			return;
		}
		if(investAmount.doubleValue()  - 500 < 0){
			br.setReturnCode(8);
			br.setMessageInfo("购买金额不能少于500");
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
			return;
		}
		session.setAttribute("userBalance", accountBalance);
		int checkPassword = ColorLifeUtils.checkPayPwd(oauser, code);
//		checkPassword = 1;
//		checkPassword = 2;
		//校验密码失败
		if(1 == checkPassword){
			br.setReturnCode(8);
			br.setMessageInfo("红包支付密码输入错误！");
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
			return;
		}else if(2 == checkPassword){
			//无红包支付密码
			br.setReturnCode(8);
			br.setMessageInfo("请设置红包支付密码！");
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
			return;
		}
		//保存订单
		Map<String, Object> corolLifeBuyInfo= new HashMap<String, Object>();
		corolLifeBuyInfo.put("product_id",pr.getProduct_rate_id());
		corolLifeBuyInfo.put("real_name",pd.getRealName());
		corolLifeBuyInfo.put("user_id",pd.getUserId());
		corolLifeBuyInfo.put("buy_money",investAmount);
		corolLifeBuyInfo.put("buy_time",new Date());
		corolLifeBuyInfo.put("status",1);
		corolLifeBuyInfo.put("insert_time",new Date());
		try {
			corolLifeBuyInfo = colorLifeBuyService.saveBuyInfo(corolLifeBuyInfo);
		} catch (Exception e) {
			br.setReturnCode(8);
			br.setMessageInfo("购买产品失败，请重试");
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
			return;
		}
		String orderNo = ObjectUtils.toString(corolLifeBuyInfo.get("order_id"));
		//String orderNo = System.currentTimeMillis() + RandomStringUtils.randomNumeric(2);
		String result = ColorLifeUtils.elicaiSuccessSyntony(oauser,orderNo, pr.getPeriod().toString(), investAmount.toString(),code, "");
//		JSONObject elicai = JSONObject.fromObject(result);
//		String status = ObjectUtils.toString(elicai.get("status"));
		if(null == result) {
			br.setReturnCode(8);
			br.setMessageInfo("扣款失败，请重试！");
			ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
			return;
		}
		logger.info(aud.getId()+aud.getMobilePhone()+oauser+"购买红包理财"+amount+"元");
		//add  zhangjhmf 2015-4-20 将购买金额传入日志中。
		operationLogService.addOperationLog("t_color_life_buy_info", aud.getUsername(), IConstants.UPDATE, aud.getLastIP(), investAmount.doubleValue(), oauser+"购买红包理财"+amount+"元", 1);
        map.put("mouth", 6);
        map.put("investAmount", investAmount);
        map.put("balanceAmount", ObjectUtils.toString(ColorLifeUtils.getEmpBalance(oauser)));
        map.put("chargeAmount", 0);
        map.put("data",br.getData());
        br.setData(map);
        br.setReturnCode(0);
		br.setMessageInfo("操作成功");
		ResponseUtils.renderJson(response, null, JSONObject.fromObject(br).toString()) ;
        return;
	}
	
	@RequireLogin(injectPersonDo = true)
	@RequestMapping(value = "prepayHb")
	public String prepayHb(HttpServletRequest request,HttpServletResponse response) {
		int pId = NumberUtils.toInt(request.getParameter("pid"), -1);
		long userId = super.getUserId();
		BaseReturn prbr = investProductService.getProductRateById(pId);
		if(prbr.getReturnCode() != 0 || prbr.getData() == null) {
			return "redirect:/product/plist.do";
		}
		
		ProductRate pr = (ProductRate)prbr.getData();
		request.setAttribute("product", pr);
		
		//账户余额
//		BaseReturn balance = queryService.queryUserBalance(Long.valueOf(super.getUserId()).intValue());
		//查询用户余额
		Integer userid = new Long(userId).intValue();
		UserThirdPartyDo upo = userThirdPartyService.getByUserId(userid);
		String oauser = upo == null ? "" : upo.getThethirdusername();
		BigDecimal balanceAmount = new BigDecimal(ColorLifeUtils.getEmpBalance(oauser));
//		if (balance.getReturnCode() == 0 && balance.getData() != null) {
//			FundUserAccount balanceInfo = (FundUserAccount) balance.getData();
//			if (balanceInfo != null) {
//				balanceAmount = balanceInfo.getBalance_amount() == null ? new BigDecimal(0) : balanceInfo.getBalance_amount();
//			}
//		}
		request.setAttribute("balanceAmount", balanceAmount);
		BaseReturn existProduct = queryService.queryPay();
		BigDecimal remainAmount = BigDecimal.ZERO;
		if (existProduct.getReturnCode() != 0 || existProduct.getData() == null) {
			request.setAttribute("canInvest", dqlcConfig.investMoneyScope);
		} else {
			BigDecimal canInvest = (BigDecimal) existProduct.getData();
			if (null != canInvest) {
				remainAmount = canInvest.add(new BigDecimal(dqlcConfig.investMoneyScope));
				if (BigDecimal.ZERO.compareTo(remainAmount) > 0) {
					remainAmount = BigDecimal.ZERO;
				}
			}
			request.setAttribute("canInvest", remainAmount);
		}
		return "finance/prepayHB";
	}

	
	/**
	 * 项目投资页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequireLogin(injectPersonDo = true)
	@RequestMapping("projectInvest")
	public String projectInvest(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		AccountUserDo user = getAccountUser();
        PersonDo personDo = personService.getByUserId(user.getId());

        request.setAttribute("usrCustId", user.getUsrCustId());// 汇付会员编号
        request.setAttribute("userId", user.getId());// 会员编号

        request.setAttribute("realName", personDo.getRealName());// 真实姓名
        request.setAttribute("idNo", personDo.getIdNo());// 身份证

		DesSecurityUtil des = new DesSecurityUtil();
		String userI = des.encrypt(user.getId().toString());
		request.setAttribute("userI", userI);
		String uri = getPath();
		request.setAttribute("url", uri);

		/**
		 * usableAmount,freezeSum ,dailyIncome,assetValue,recivedPrincipal
		 */
		UserIncomeDo userIncomeDo = userIncomeService.queryUserIncome(
				user.getId() + "", UserType.HEHENIAN.name());

		request.setAttribute("userIncomeDo", userIncomeDo);

		return "finance/capital";
	}
	

	
	/**
	 * +车宝,+多宝购买
	 * @param request
	 * @param response
	 * @return
	 * @author: jiangwmf
	 * @date 2015-4-23 下午4:37:58
	 */
	@RequireLogin(injectPersonDo = true)
	@RequestMapping(value = "prepayCB")
	public String prepayCB(HttpServletRequest request,HttpServletResponse response) {
		//产品id
		int pId = NumberUtils.toInt(request.getParameter("pid"), -1);
		long userId = super.getUserId();
		//冲抵信息id
		int offsetId = NumberUtils.toInt(request.getParameter("offsetId"), -1);
		//绑定卡标识
		int bankCardId = NumberUtils.toInt(request.getParameter("bank_card_id"), -1);
		
		//查询产品收益率、收益期限  --用来计算费用
		BaseReturn prbr = investProductService.getProductRateById(pId);
		if(prbr.getReturnCode() != 0 || prbr.getData() == null) {
			return "redirect:/product/plist.do";
		}
		
		ProductRate pr = (ProductRate)prbr.getData();
		request.setAttribute("product", pr);
				
		BigDecimal fee = null;//物业费\停车费
		BigDecimal period = null;
		
		if(pr.getSub_channel()==3){
			ParkingDetailDo offsetDetail = null;
			///查询冲抵信息(冲抵停车费信息(t_parking_detailinfo)及停车费金额(t_parking_fee))信息 
			if(offsetId>0){
				offsetDetail = parkingFeeService.getParkingDetailDoById(offsetId);
			}else{
				offsetDetail = parkingFeeService.getDefaultByUserId(new Long(userId).intValue());
			}
			if(offsetDetail==null){
				return "redirect:/product/plist.do";
			}
			request.setAttribute("offsetDetail", offsetDetail);
			ParkingFeeDo offsetFee = parkingFeeService.getByParams(offsetDetail.getMainaddressid(),offsetDetail.getPlate_number());
			if(offsetFee==null){
				return "redirect:/product/plist.do";
			}
			request.setAttribute("offsetFee",offsetFee);
			request.setAttribute("roomOrPlateNo", offsetFee.getPlate_number());
			String addressDesc = sysCodeService.getByCommunityCode(offsetFee.getMainaddressid().toString()).replace(",","");
			request.setAttribute("addressDesc",addressDesc);
			
			fee = new BigDecimal(offsetFee.getParking_fee());//物业费
			
			period = new BigDecimal(pr.getPeriod()+1);
			//period = new BigDecimal(pr.getPeriod());
			
		}else if(pr.getSub_channel()==2){
			
			PropertyManagementDetailDo offsetDetail = null;
			///查询冲抵信息(冲抵停车费信息(t_parking_detailinfo)及停车费金额(t_parking_fee))信息 
			if(offsetId>0){
				offsetDetail = manageFeeService.getPropertyManagementDetailDoById(offsetId);
			}else{
				offsetDetail = manageFeeService.getDefaultByUserId(new Long(userId).intValue());
			}
			if(offsetDetail==null){
				return "redirect:/product/plist.do";
			}
			request.setAttribute("offsetDetail", offsetDetail);
			PropertyManagementFeeDo offsetFee = manageFeeService.getByParams(offsetDetail.getMainaddressid(),offsetDetail.getBuilding(),offsetDetail.getRoomnum(),offsetDetail.getTheowner());
			if(offsetFee==null){
				return "redirect:/product/plist.do";
			}
			request.setAttribute("offsetFee",offsetFee);
			request.setAttribute("roomOrPlateNo", offsetFee.getRoomnum());
			String addressDesc = sysCodeService.getByCommunityCode(offsetFee.getMainaddressid().toString()).replace(",","");
			request.setAttribute("addressDesc",addressDesc);
			
			
			fee = new BigDecimal(offsetFee.getProperty_fee());//物业费
			
			period = new BigDecimal(pr.getPeriod());
		}
		
		//冲抵时间
		String offsetCur = DateUtils.getCurrentYear() + "-" +getMonthStr(DateUtils.getCurrentMonth());
		String offsetDelay = DateUtils.getYearAfter(Calendar.MONTH,period.intValue())+"-" + getMonthStr(DateUtils.getMonthAfter(Calendar.MONTH,period.intValue()));
		request.setAttribute("offsetCur",offsetCur);
		request.setAttribute("offsetDelay",offsetDelay);
		//费用计算（预存金额、每月冲抵物业费、预期收益）
		Map<String,Object> map = calOffsetFee(period,pr.getRate(),fee);
		request.setAttribute("preDepositAmount", map.get("preDepositAmount"));
		request.setAttribute("obtainAllAmount", map.get("obtainAllAmount"));
		
		//检查余额是否足够？
		FundBankCard fbc = null;
		if(bankCardId > 0) {
			BaseReturn cardBr = cardVerifyService.getBankCardById(bankCardId);
			if(cardBr != null && cardBr.getReturnCode() == 0 && cardBr.getData() != null) {
				fbc = (FundBankCard)cardBr.getData();
				if(fbc.getUser_id().intValue() != Long.valueOf(userId).intValue() ) {
					return "redirect:/product/plist.do";
				}
				
				fbc.setCard_no(Constants.getLastFourCardNo(fbc.getCard_no()));
				request.setAttribute("bingCard", fbc);
			}
		}else{
			//BaseReturn cardReturn = queryService.queryBindedBankCard(Long.valueOf(super.getUserId()).intValue());
			//获取绑定银行卡
			BaseReturn bankReturn = queryService.queryBankCard(Long.valueOf(userId).intValue() );
			if(bankReturn.getReturnCode() == 0 && bankReturn.getData() != null) {
				fbc = (FundBankCard)bankReturn.getData();
				fbc.setCard_no(Constants.getLastFourCardNo(fbc.getCard_no()));
				request.setAttribute("bingCard", fbc);
			}
		}
		
		//账户余额
		BaseReturn balance = queryService.queryUserBalance(Long.valueOf(super.getUserId()).intValue());
		//查询用户余额
		BigDecimal balanceAmount = new BigDecimal(0);
		if (balance.getReturnCode() == 0 && balance.getData() != null) {
			FundUserAccount balanceInfo = (FundUserAccount) balance.getData();
			if (balanceInfo != null) {
				balanceAmount = balanceInfo.getBalance_amount() == null ? new BigDecimal(0) : balanceInfo.getBalance_amount();
			}
		}
		request.setAttribute("balanceAmount", balanceAmount);
		BaseReturn existProduct = queryService.queryPay();
		BigDecimal remainAmount = BigDecimal.ZERO;
		if (existProduct.getReturnCode() != 0 || existProduct.getData() == null) {
			request.setAttribute("canInvest", dqlcConfig.investMoneyScope);
		} else {
			BigDecimal canInvest = (BigDecimal) existProduct.getData();
			if (null != canInvest) {
				remainAmount = canInvest.add(new BigDecimal(dqlcConfig.investMoneyScope));
				if (BigDecimal.ZERO.compareTo(remainAmount) > 0) {
					remainAmount = BigDecimal.ZERO;
				}
			}
			request.setAttribute("canInvest", remainAmount);
		}
		return "finance/prepayCB";
	}
	
	private String getMonthStr(int mth){
		String curmthStr = String.valueOf(mth);
		if(mth<10){
			curmthStr = "0"+curmthStr;
		}
		return curmthStr;
	}
	

	/**
	 * 计算预存金额和返还本息收益
	 * @param period
	 * @param rate
	 * @param fee
	 * @return
	 * @author: jiangwmf
	 * @date 2015-4-23 下午4:37:58
	 */
	private Map<String,Object> calOffsetFee(BigDecimal period,BigDecimal rate,BigDecimal fee){
//		客户理财年化收益率3%=0.03
//		合和年理财年化收益率10%=0.1		
		Map<String,Object> map = new HashMap<String,Object>();

		double preDepositAmount = PreciseCompute.div(PreciseCompute.mul(fee.doubleValue(), period.doubleValue()), PreciseCompute.sub(0.1, rate.doubleValue()), 2);
		int amount = (new BigDecimal(preDepositAmount/1000).setScale(0, BigDecimal.ROUND_HALF_UP).intValue()+1)*1000;
		System.out.println("预存本金:"+preDepositAmount +",产品价格："+amount);
		//预存本金=（200*12）/（10%-3%）=34285.71
		//产品价格=35000.00
		double obtainAllAmount = new BigDecimal(PreciseCompute.mul(amount, 1.03)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		map.put("preDepositAmount", amount);
		map.put("obtainAllAmount", obtainAllAmount);
		return map;
	}
	
	 
    
	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis() + "" + RandomStringUtils.randomNumeric(2));
		
		
	}
}
