/**  
 * @Project: hehenian-mobile
 * @Package com.hehenian.mobile.web.controller.finance
 * @Title: BalancesController.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-4-1 上午2:37:04
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.web.controller.finance;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.dqlc.IDqlcService;
import com.hehenian.biz.common.identifycode.IIdentifyCodeService;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.common.annotations.RequireLogin;
import com.hehenian.common.utils.ResponseUtils;
import com.hehenian.mobile.web.controller.BaseController;
import com.hhn.hessian.cardverify.ICardVerifyService;
import com.hhn.hessian.query.IQueryService;
import com.hhn.hessian.recharge.IRechargeService;
import com.hhn.hessian.withdraw.IWithdrawService;
import com.hhn.pojo.FundActualAccountLog;
import com.hhn.pojo.FundBankCard;
import com.hhn.pojo.FundUserAccount;
import com.hhn.util.BaseReturn;
import com.hhn.util.Constants;
import com.hhn.util.DqlcConfig;


@Controller
@RequestMapping(value = "/balance")
public class BalanceController extends BaseController{
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IUserService userService;
	@Autowired
    private IQueryService queryService;
    @Autowired
    private IDqlcService dqlcService;
    @Autowired
    private IWithdrawService withdrawService;
    @Autowired
    private IRechargeService rechargeService;
	@Autowired
	public DqlcConfig dqlcConfig;
	@Autowired
	private ICardVerifyService cardVerifyService;
	
	@Autowired
	private IIdentifyCodeService identifyCodeService;
	
	/**
	 * 可用余额页
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-4-1 上午2:50:41
	 */
	@RequireLogin
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request,HttpServletResponse response){
		Long userId = getUserId();
		String balanceAmount = "0.00";
        BaseReturn balance = queryService.queryUserBalance(userId.intValue());
        if (balance==null || balance.getReturnCode()!=0 || balance.getData()==null) {
        	balanceAmount = "0.00";
        }else{
            FundUserAccount balanceInfo = (FundUserAccount)balance.getData();
            balanceAmount = balanceInfo.getBalance_amount()==null?"0":balanceInfo.getBalance_amount().toString();
        }
        AccountUserDo aud = userService.getById(userId);
        //通联余额
        request.setAttribute("balanceAmount", balanceAmount);
        //汇付余额
        request.setAttribute("withdrawalAmount", aud.getUsableSum());
		return "finance/balance";
	}
	
	/**
	 * 充值记录
	 * @Description: TODO
	 * @return
	 * @author: chenzhpmf
	 * @throws ParseException 
	 * @date 2015-4-1 上午2:50:57
	 */
	@RequireLogin
	@RequestMapping(value = "chargeList")
	public String chargeList(HttpServletRequest request) throws ParseException{
		Long userId = getUserId();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
	    List<FundActualAccountLog> chargeList = (List<FundActualAccountLog>)(queryService.getChargeList(paramMap).getData());
	    List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
	    if(CollectionUtils.isNotEmpty(chargeList)){
	    	 String formatTime = null;
	    	 Map<String,Object> map = null;
	         for(int i=0;i<chargeList.size();i++){
	        	map = new HashMap<String, Object>();
	            FundActualAccountLog actualAccountLog = chargeList.get(i);
	            map.put("actual_account_log_id", actualAccountLog.getActual_account_log_id());
	            map.put("from_account",Constants.getHide2BandNo(actualAccountLog.getFrom_account()));
	            formatTime=DateUtils.formatTime(actualAccountLog.getThird_trade_time());
	            map.put("third_trade_time",formatTime);
	            map.put("from_account",Constants.getHide2BandNo(actualAccountLog.getFrom_account()).substring(5));
	            map.put("agreementFileName",actualAccountLog.getAgreementFileName());
	            map.put("transfer_status", actualAccountLog.getTransfer_status());
	            map.put("trade_amount",actualAccountLog.getTrade_amount());
	            mapList.add(map);
	         }
	    }
	    request.setAttribute("chargeList", mapList);
		return "finance/chargeList";
	}
	
	/**
	 * 提现记录
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-4-1 上午2:51:05
	 */
	@RequireLogin
	@RequestMapping(value = "withdrawList")
	public String withdrawList(HttpServletRequest request){
		Long userId = getUserId();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user_id",userId);
		List<Map> drawList = (List<Map>)(queryService.getWebWithdrawList(paramMap).getData());
	    request.setAttribute("drawList", drawList);
		return "finance/withdrawList";
	}
	
    /**
     * 提现页面
     * @param request
     * @return
     */
	@RequireLogin
    @RequestMapping("/withdrawPage")
    public ModelAndView withdrawPage(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        Integer user_id = getCurrentUserId();
        BaseReturn userPhone = queryService.queryPhone(user_id);
        @SuppressWarnings("unchecked")
		Map<String, Object> userMap = (HashMap<String, Object>) userPhone.getData();
        if (userMap!=null && userMap.size()>0) {
            String mobilePhone = (String) userMap.get("mobilePhone");
            if (mobilePhone.startsWith("-")){
                mobilePhone = mobilePhone.substring(1);
            }
            String realName = (String) userMap.get("realName");
            String idNo = (String) userMap.get("idNo");
            map.put("phone", mobilePhone);
            map.put("hidPhone", Constants.getHidePhone(mobilePhone));
            map.put("realName", realName);
            map.put("idNo", idNo);
        }
        BaseReturn balance = queryService.queryUserBalance(user_id);
        if (balance.getReturnCode() != 0 || balance.getData() == null) {
            map.put("balance", 0);
        } else {
            FundUserAccount balanceInfo = (FundUserAccount) balance.getData();
            map.put("balance",balanceInfo.getBalance_amount()==null?"0":balanceInfo.getBalance_amount());
        }
        BaseReturn bankReturn = queryService.queryBankCard(Integer.valueOf(user_id));
        FundBankCard bankCard = (FundBankCard)bankReturn.getData();
        if (bankCard!=null && StringUtils.isNotEmpty(bankCard.getCard_no())){
            int length = bankCard.getCard_no().length();
            String weiCard = bankCard.getCard_no().substring(length-4);
            bankCard.setCard_no(weiCard);
            map.put("userCard", bankCard);
            map.put("bankCode", bankCard.getBank_code());
        }
        Integer todayTimes = (Integer)queryService.getTodayTimes(Integer.valueOf(user_id)).getData();
        map.put("todayTimes", todayTimes);
        BigDecimal todayAmt = (BigDecimal)queryService.getTodayTotalAmt(Integer.valueOf(user_id)).getData();
        map.put("todayAmt", todayAmt);
        map.put("limitOne",dqlcConfig.LIMIT_ONE_MONEY);
        map.put("limitDay", dqlcConfig.DAY_LIMIT_MONEY);
        view.setViewName("finance/withdraw");
        view.addAllObjects(map);
        return view;
    }
    
    
    /**
     * 用户提现
     * @param request
     * @return
     */
    @RequireLogin
    @RequestMapping(value = "/userWithdraw", method = RequestMethod.POST)
    public void userWithdraw(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        String withdrawMoney = request.getParameter("withdrawMoney");
        String timeFlag = request.getParameter("receiveTime");
        String verifyCode = request.getParameter("code");
        if (StringUtils.isBlank(withdrawMoney)){
        	ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"提现金额不能为空")).toString());
            return;
        }
        map.put("withMoney", withdrawMoney);
        if (StringUtils.isBlank(timeFlag)){
        	 ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"到账时间不能为空")).toString());
             return;
        }
        map.put("timeFlag", timeFlag);
        if (verifyCode==null || StringUtils.isEmpty(verifyCode)){
            ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"验证码不能为空")).toString());
            return;
        }
        Integer user_id = getCurrentUserId();
        Integer userId = Integer.valueOf(user_id);
        //查询用户手机号
        BaseReturn userPhone = queryService.queryPhone(userId);
        Map<String, Object> userMap = (HashMap<String, Object>) userPhone.getData();
        String mobilePhone = "";
        if (userMap!=null && userMap.size()>0) {
            mobilePhone = (String) userMap.get("mobilePhone");
            if (mobilePhone != null && !"".equals(mobilePhone) && mobilePhone.startsWith("-")) {
                mobilePhone = mobilePhone.substring(1);
            }
        }
        boolean flag = dqlcService.checkPhoneVerifyCode(mobilePhone, verifyCode);
        if (!flag) {
            ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(new BaseReturn(1,"验证码不正确")).toString());
            return;
        }
        map.put("user_id", userId);
        logger.info("withdraw invoke start..............");
        BaseReturn baseReturn = withdrawService.widthdrawMoney(map);
        logger.info("withdraw invoke end................returnCode:::="+baseReturn.getReturnCode());
        ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(baseReturn).toString());
    }

    /**
     * 充值页面
     * @param request
     * @return
     */
    @RequireLogin
    @RequestMapping(value = "/chargePage")
    public String chargePage(HttpServletRequest request){    	
        int bankCardId = NumberUtils.toInt(request.getParameter("bank_card_id"), -1);
        Map<String, Object> map = new HashMap<String, Object>();
        Integer userId = getCurrentUserId();
        BaseReturn userPhone = queryService.queryPhone(userId);
        if(userPhone==null || userPhone.getData()==null){
        	return "redirect:/balance/index.do";
        }
        Map<String,Object> userMap = (HashMap<String,Object>)userPhone.getData();
        String mobilePhone = (String)userMap.get("mobilePhone");
        if (mobilePhone.startsWith("-")){
            mobilePhone = mobilePhone.substring(1);
        }
        String realName = (String)userMap.get("realName");
        String idNumber = (String)userMap.get("idNo");
        logger.debug("账户名：" + realName + ",手机号:" + mobilePhone + ",身份证号:" + idNumber);
        map.put("userName", realName);
        map.put("phone",mobilePhone);
        map.put("hidPhone",Constants.getHidePhone(mobilePhone));
        map.put("idNumber", Constants.getHideIdNo(idNumber));
        FundBankCard fundBankCard = null;
        BaseReturn bankCard = null;
        if(bankCardId>0){
        	 bankCard = cardVerifyService.getBankCardById(bankCardId);
        }else{
	         bankCard = queryService.queryBankCard(userId);
        }
        fundBankCard = (FundBankCard)bankCard.getData();
        if(fundBankCard!=null && StringUtils.isNotEmpty(fundBankCard.getCard_no())){
        	if(fundBankCard.getUser_id().intValue() != userId.intValue() ) {
        		return "redirect:/balance/index.do";
        	}
        	fundBankCard.setCard_no(Constants.getCardNo(fundBankCard.getCard_no()));
        }
        request.setAttribute("bankCard", fundBankCard);
        return "finance/charge"; //用户充值页面
    }

    
    /**
     * 用户充值
     * @param request
     * @return
     */
    @RequireLogin
    @RequestMapping(value = "/userCharge", method = RequestMethod.POST)
    public void userCharge(HttpServletRequest request,HttpServletResponse response) {
    	 Map<String, Object> map = new HashMap<String, Object>();
         String amount = request.getParameter("amount"); //充值金额
         String verfiyCode = request.getParameter("verfiyCode");//验证码
         if (amount == null || "".equals(amount)) {
             ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"充值金额不能为空")).toString());
             return;
         } else {
             map.put("AMOUNT", amount);
         }
         if (verfiyCode==null || "".equals(verfiyCode)){
             ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"验证码不能为空")).toString());
             return;
         }
        try {
            Integer userId =  getCurrentUserId();
            //查询账户名和手机号
            BaseReturn userPhone = queryService.queryPhone(userId);
            Map<String,Object> userMap = (HashMap<String,Object>)userPhone.getData();
            String mobilePhone = (String)userMap.get("mobilePhone");
            if (mobilePhone.startsWith("-")){
                mobilePhone = mobilePhone.substring(1);
            }
            logger.debug("手机号:" + mobilePhone);
            map.put("user_id", userId); //userId
            boolean flag = dqlcService.checkPhoneVerifyCode(mobilePhone,verfiyCode);
            if (!flag){
                ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"验证码不正确")).toString());
                return;
            }
            logger.info("user charge money start......");
            logger.info("user charge money parameterMap:"+map);
            BaseReturn baseReturn = rechargeService.recharge(map);
            logger.info("user charge money end......returnCode:::"+baseReturn.getReturnCode());
            ResponseUtils.renderText(response, null, JSONObject.fromObject(baseReturn).toString());
        }catch (Exception e){
            logger.error("error",e);
            ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"系统正忙请稍后重试")).toString());
        }
    }
    
    /**
     * 充值授权协议
     * @return
     * @author: chenzhpmf
     * @date 2015-4-1 下午10:35:18
     */
    @RequestMapping("/withholdingAgreement")
    public String withholdingAgreement(){
    	return "finance/withholdingAgreement";
    }
}
