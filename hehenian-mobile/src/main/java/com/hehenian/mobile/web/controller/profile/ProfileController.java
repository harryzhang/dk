/**  
 * @Project: hehenian-mobile
 * @Package com.hehenian.mobile.web.controller
 * @Title: UserHomeController.java
 * @Description: TODO
 *
 * @author: duanhrmf
 * @date 2015年3月28日 下午6:09:33
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.web.controller.profile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.colorlife.ColorLifeBuyService;
import com.hehenian.biz.common.dqlc.IDqlcService;
import com.hehenian.biz.common.util.huifu.ChinaPnRInterface;
import com.hehenian.biz.common.wygj.IOffsetService;
import com.hehenian.biz.common.wygj.dataobject.OffsetDetailsDo;
import com.hehenian.biz.common.wygj.dataobject.OffsetRecordsDo;
import com.hehenian.common.annotations.RequireLogin;
import com.hehenian.common.utils.ResponseUtils;
import com.hehenian.mobile.common.utils.CommonUtils;
import com.hehenian.mobile.web.controller.BaseController;
import com.hhn.hessian.invest.IInvestProductService;
import com.hhn.hessian.query.IQueryService;
import com.hhn.hessian.recharge.IRechargeService;
import com.hhn.hessian.redeem.IRedeemService;
import com.hhn.pojo.FundBankCard;
import com.hhn.pojo.FundUserAccount;
import com.hhn.pojo.ProductRate;
import com.hhn.util.BaseReturn;
import com.hhn.util.Constants;

/**
 * 个人中心
 * @author duanhrmf
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController extends BaseController {

    @Autowired
    private IQueryService queryService;
    @Autowired
    private IInvestProductService investProductService;
    @Autowired
    private IPersonService personService;
    @Autowired
    private IRechargeService rechargeService;
    @Autowired
	private ColorLifeBuyService colorLifeBuyService;
    @Autowired
    private IOffsetService offsetService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRedeemService redeemService;
    @Autowired
    private IDqlcService dqlcService;
    
    /**
     * 账户中心主页
     */
    @RequireLogin(injectPersonDo = true)
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request,HttpServletResponse response) {
        Long userId = getUserId();
        //绑定的手机号
        try {
        	AccountUserDo aud = super.getAccountUser();
    		String mobile = aud.getMobilePhone();
    		if (StringUtils.isNotBlank(mobile) && mobile.startsWith("-")) {
    			mobile = mobile.substring(1);
    		}
    		request.setAttribute("mobile", Constants.getHidePhone(mobile));//手机号
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        //绑定的银行卡信息
   	 	List<Map<String,Object>> list = bingdingCard(userId);
        request.setAttribute("cardNum",list !=null ?list.size() : "0");
    
        //可用余额
        availableBalance(userId.intValue());
        int channel = CommonUtils.getChannel(request);
		if(2 == channel){
        	return "profile/purchaseRecordsHB";// 购买
        } else if (0 == channel) {
        	AccountUserDo aud = userService.getById(userId);
        	//汇付余额
        	request.setAttribute("AvlBal", aud.getUsableSum());
        	return "profile/center-index-new";
        }
        
        
        return "profile/center-index";
    }
    
    /**
     * +可用余额详情 
     */
    @RequireLogin(injectPersonDo = true)
    @RequestMapping(value = "/totalBalance")
    public String totalBalance(HttpServletRequest request,HttpServletResponse response) {
    	Long userId = getUserId();
    	availableBalance(userId.intValue());
        return "profile/center-io";
    }

    /**
     * +薪宝
     */
    @RequireLogin(injectPersonDo = true)
    @RequestMapping(value = "/salaryDesc")
    public String salaryDesc(HttpServletRequest request,HttpServletResponse response) {
        //渠道
    	int channel = CommonUtils.getChannel(request);
        String flag = request.getParameter("flag");
        if("xin".equals(flag)){
        	xinbao(channel, 0);//+薪宝
        } else if ("adb".equals(flag)) {
        	request.setAttribute("flag", "xin");
        	xinbao(channel, 0);//爱定宝
        	return "profile/center-adb";
        }else if("zu".equals(flag)){
        	xinbao(channel, 1);//+族宝
        }else if("duo".equals(flag)){
        	xinbao(channel, 2);//+多宝
        }else if("che".equals(flag)){
        	xinbao(channel, 3);//+车宝
        }
        request.setAttribute("flag", flag);
        return "profile/center-pro";
    }
    
    /**
	 * 详细信息
	 * @return
	 * @throws Exception
	 */
     @RequireLogin(injectPersonDo = true)
	 @RequestMapping(value = "/userinfo")
	public String queryBaseData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String flag = request.getParameter("flag");
		Long userId = getUserId();
		if (userId != null) {
			PersonDo pd = super.getPerson();
//			PersonDo pd = personService.getByUserId(userId);
			request.setAttribute("realName", pd.getRealName());
			request.setAttribute("cellPhone", pd.getCellPhone());
			request.setAttribute("auditStatus", pd.getAuditStatus());
			request.setAttribute("nickName", getAccountUser().getUsername());
			request.setAttribute("email", StringUtils.isNotEmpty(pd.getEmail()) ? pd.getEmail() : getAccountUser().getEmail());
			request.setAttribute("idNo", pd.getIdNo());
		}
		
		if(flag.equals("auth")){
			return "profile/center-auth";
		}else{
			return "profile/center-count";
		}
	}
     
     /**
      * 银行卡管理
      */
     @RequireLogin(injectPersonDo = true)
	 @RequestMapping(value = "/managerCard")
     public String getBindingCard(HttpServletRequest request,HttpServletResponse response){
    	 Long userId = getUserId();
    	 //绑定的银行卡信息
    	 List<Map<String,Object>> list = bingdingCard(userId);
    	 int pid = NumberUtils.toInt(request.getParameter("pid"), -1);
    	 request.setAttribute("pid", pid);
         request.setAttribute("cardList", list);
         String referer = request.getHeader("referer");
         if(referer.indexOf("moneyVerify.do")>-1){
        	 referer = "http://m.hehenian.com/profile/managerCard.do";
         }
         request.setAttribute("referer",referer);
         return "profile/buy-cb";
     }
    
    /**
     * +薪宝 0 
     * +族宝 1
     * +多宝 2
     * +车宝 3
     */
    public void xinbao(int channel, int sub_channel){
    	Long userId = getUserId();
    	//待收收益
        BaseReturn interested = queryService.queryPropertyInterested(userId.intValue(), channel, sub_channel);
        if (interested==null || interested.getReturnCode()!=0 || interested.getData()==null) {
        	request.setAttribute("interested","0.00");
        }else{
        	request.setAttribute("interested", interested.getData());
        }
        
        //历史累计收益=已收收益+待收收益
        //已收收益
        BaseReturn interest = queryService.queryPropertyInterest(userId.intValue(), channel, sub_channel);
        
        BigDecimal a = (BigDecimal) interest.getData();
        if(a == null) {
        	a = new BigDecimal(0);
        }
        BigDecimal b = (BigDecimal) interested.getData();
        if(b == null) {
        	b = new BigDecimal(0);
        }
        request.setAttribute("totalInterest", a.add(b).toString());
        
        BaseReturn principal = null;
        //投资金额 持有资产
        if (0 == channel) {
	        principal = queryService.queryTotalInvest(userId.intValue());
	        if (principal==null || principal.getReturnCode()!=0 || principal.getData()==null) {
	        	request.setAttribute("principal","0.00");
	        }else{
	        	request.setAttribute("principal", principal.getData());
	        }
        } else {
        	//投资金额  持有资产
        	principal = queryService.queryPropertyInvestment(userId.intValue(), channel, sub_channel);
        	if (principal==null || principal.getReturnCode()!=0 || principal.getData()==null) {
        		request.setAttribute("principal","0.00");
        	}else{
        		request.setAttribute("principal", principal.getData());
        	}
        }
    }
    
     
     /**
      * 查询绑定的银行卡
      */
     public List<Map<String,Object>> bingdingCard(Long userId){
//    	 BaseReturn re = queryService.getUserBindingCard(userId.intValue());//已绑定的银行卡数量
    	BaseReturn cardReturn = queryService.queryBindedBankCard(Long.valueOf(super.getUserId()).intValue());
 		if(cardReturn.getReturnCode() == 0 && cardReturn.getData() != null && !((List)(cardReturn.getData())).isEmpty()) {
 			List<FundBankCard> list = (List)cardReturn.getData();
 			List<Map<String,Object>> bdCardList = new ArrayList<Map<String,Object>>();
 			Map<String,Object> cardMap = null;
 			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
 				FundBankCard fundBankCard = (FundBankCard) iterator.next();
 				cardMap = new HashMap<String, Object>();
 				cardMap.put("card_no", fundBankCard.getCard_no());
 				cardMap.put("subCardNo", Constants.getCardNo(fundBankCard.getCard_no()));
 				cardMap.put("bank_card_id", fundBankCard.getBank_card_id());
 				cardMap.put("account_name", fundBankCard.getAccount_name());
 				cardMap.put("bank_name", fundBankCard.getBank_name());
 				cardMap.put("bank_code", fundBankCard.getBank_code());
 				cardMap.put("bank_status", fundBankCard.getBank_status());
 				bdCardList.add(cardMap);
 			}
 			return bdCardList;
 		}
 		return null;
     }
     
	 
 	/**
     * 可用余额
     */
    public void availableBalance(int userId){
        //通汇余额
        BaseReturn balance = queryService.queryUserBalance(userId);
        FundUserAccount balanceInfo = (FundUserAccount)balance.getData();
        if (balance==null || balance.getReturnCode()!=0 || balance.getData()==null) {
        	request.setAttribute("balance","0.00");
        }else{
            request.setAttribute("balance", balanceInfo.getBalance_amount()==null?0:balanceInfo.getBalance_amount());
        }
        //汇付余额
        String AvlBal = getUserUsableSum();
        request.setAttribute("AvlBal", AvlBal == "" ?"0.00":AvlBal);
        if(AvlBal.equals("")){//汇付可用余额为0
        	request.setAttribute("AvlBalFlag", "0");
        }
    }
	    
    /**
     * 查询汇付可用余额
     */
    public String getUserUsableSum(){
        AccountUserDo user = getAccountUser();
        String AvlBal="";
        if (user!=null&&user.getUsrCustId()>0){
            JSONObject json = null;
            try {
                // 后台查询余额接口
                String jsonStr = ChinaPnRInterface.queryBalanceBg(user.getUsrCustId()+"");
                json = JSONObject.fromObject(jsonStr);
                int RespCode = json.getInt("RespCode");
                if (RespCode == 0) {
                    AvlBal = json.getString("AvlBal").replaceAll(",", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return AvlBal;
    }
    
    /**
     * 购买记录
     */
    @RequireLogin(injectPersonDo = true)
    @RequestMapping(value = "/buy")
    public String buy(){
    	String flag = request.getParameter("flag");
    	String tab_f = request.getParameter("tab_f");
    	String type = request.getParameter("type");
    	request.setAttribute("flag", flag);
    	int channel = NumberUtils.toInt(getSessionStrAttr("channel"));
    	if(2 == channel){
    		if("shouyi".equals(type)){
    			return "profile/purchaseRecordsHBSY";// 收益
    		}else{
    			return "profile/purchaseRecordsHB";// 购买
    		}
    	}
    	
    	if(tab_f.equals("g")){
    		if ("duo".equals(flag)){
        		return "profile/purchaseRecordsDB";// 购买
        	}else if ("che".equals(flag)){
        		return "profile/purchaseRecordsCB";// 购买
        	}else
    		return "profile/purchaseRecords";// 购买
    	}else{
    		if ("duo".equals(flag)){
        		return "profile/investmentRecordsDB";//投资
        	}else if ("che".equals(flag)){
        		return "profile/investmentRecordsCB";//投资
        	}else
    		return "profile/investmentRecords";//投资
    	}
    	
    	
    }
    
    
    /**
     * 手机端
     * 用户购买记录查询
     * @return
     */
    @RequireLogin
    @RequestMapping(value = "/queryTradePhone")
	public void queryTradePhone(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int channel = NumberUtils.toInt(getSessionStrAttr("channel"));
		String flag = request.getParameter("flag");
		if (("xin").equals(flag)) {
			map.put("sub_channel", "0");
		} else if("zu".equals(flag)){
			map.put("sub_channel", "1");
		}else if("duo".equals(flag)){
			map.put("sub_channel", "2");
		}else if("che".equals(flag)){
			map.put("sub_channel", "3");
		}
		map.put("channel", channel);
		map.put("user_id", getUserId() + "");
		
		
		if(1 == channel || 0 == channel){
			List<Map> tradeList = (List<Map>) (investProductService.getPropertyBuyList(map).getData());
			map.put("data", tradeList);
		}
		if(2 == channel){
			String type = request.getParameter("type");
			if("shouyi".equals(type)){
				map.put("status", 2);
			}
			 List<Map<String, Object>> tradeList = (List<Map<String, Object>>) (colorLifeBuyService.weblistBuyInfo(map));
	            if (tradeList.size()>0){
	                for(Map pp:tradeList){
	                	if(pp.get("buyDate") == null){
	                		pp.put("trade_time_info", "");
	                	}else{
	                		pp.put("trade_time_info", DateFormatUtils.format((Date)pp.get("buyDate"), "yyyy-MM-dd"));
	                	}
	                	if(pp.get("updateTime") == null){
	                		pp.put("updateTimeinfo", "");
	                	}else{
	                		pp.put("updateTimeinfo", DateFormatUtils.format((Date)pp.get("updateTime"), "yyyy-MM-dd"));
	                	}
	                	BigDecimal shouyi = new BigDecimal(0);
	                	BigDecimal buyMoney = new BigDecimal((Double)pp.get("buyMoney"));
	                	BigDecimal rate = (BigDecimal)pp.get("rate");
	                	BigDecimal period = new BigDecimal((Integer)(pp.get("productPeriod")));
	                	shouyi = buyMoney.multiply(rate).multiply(period).divide(new BigDecimal(12), 2);
	                	pp.put("shouyi", shouyi);
	                }
	            }
			 
			 map.put("data", tradeList);
		}
		System.out.println(JSONObject.fromObject(map).toString());
		ResponseUtils.renderJson(response, ResponseUtils.UTF8, JSONObject.fromObject(map).toString());
		//return Renew BaseReturn(0, map, "查询成功！");
    }
    
    /**
     * 手机端
     * 用户投资记录查询
     * @return
     */
    @RequireLogin
    @RequestMapping(value = "/getProductPhone")
    public void getProductList(HttpServletRequest request,HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<String, Object>();
        String flag = request.getParameter("flag");
    	if(flag.equals("xin")){
    		map.put("sub_channel", "0");
    	} else if("zu".equals(flag)){
    		map.put("sub_channel", "1");
		}else if("duo".equals(flag)){
			map.put("sub_channel", "2");
		}else if("che".equals(flag)){
			map.put("sub_channel", "3");
    	}
        map.put("user_id", getUserId()+"");
        List<Map> productList = (List<Map>)(investProductService.getPropertyInvestmentList(map).getData());
        if(productList != null) {
        	for (Iterator iterator = productList.iterator(); iterator.hasNext();) {
				Map item = (Map) iterator.next();
				item.put("trade_time_info", DateFormatUtils.format((Date)item.get("invest_time"), "yyyy-MM-dd HH:mm"));
			}
        }
        map.put("productList", productList);
        
		System.out.println(JSONObject.fromObject(map).toString());
		ResponseUtils.renderJson(response, ResponseUtils.UTF8, JSONObject.fromObject(map).toString());
    }
    
    /**
     * 手机端赎回申请页面
     * @param request
     * @return
     */
    @RequireLogin
    @RequestMapping("/redeemPagePhone")
    public ModelAndView redeemPagePhone(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        String trade_id = request.getParameter("tradeId");
        map.put("tradeId", trade_id);
        BaseReturn baseReturn = redeemService.getRedeemInfo(Integer.valueOf(trade_id), new Date());
        view.setViewName("profile/redeemPhone");
        map.putAll((Map)baseReturn.getData());
        view.addAllObjects(map);
        return view;
    }
    
    /**
     * 手机端赎回验证码页面
     * @param request
     * @return
     */
    @RequireLogin
    @RequestMapping(value = "/redeemVerifyCode")
    public ModelAndView redeemVerifyCode(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        String trade_id = request.getParameter("tradeId");
        map.put("tradeId", trade_id);
        String user_id = getUserId()+"";
        BaseReturn userPhone = queryService.queryPhone(Integer.valueOf(user_id));
        Map<String, Object> userMap = (HashMap<String, Object>) userPhone.getData();
        if (userMap!=null && userMap.size()>0) {
            String mobilePhone = (String)userMap.get("mobilePhone");
            if (mobilePhone.startsWith("-")){
                mobilePhone = mobilePhone.substring(1);
            }
            map.put("userHidPhone", Constants.getHidePhone(mobilePhone));
            map.put("userPhone", mobilePhone);
        }
        view.setViewName("profile/redeemVerifyCode");
        view.addAllObjects(map);
        return view;
    }
    
    /**
     * 用户赎回处理
     * @param request
     * @return
     */
    @RequireLogin
    @RequestMapping("/redeemProcess")
    public void redeemProcess(HttpServletRequest request, HttpServletResponse response) {
        String userId = Long.toString(getUserId());
        String trade_id = request.getParameter("tradeId");
        String verifyCode = request.getParameter("verifyCode");
        Map resultMap = new HashMap();
        resultMap.put("returnCode", 1);
        resultMap.put("messageInfo", "订单号不能为空");
        if (trade_id==null || StringUtils.isEmpty(trade_id)){
        	ResponseUtils.renderJson(response, null, JSONObject.fromObject(resultMap).toString());
        	return;
        }
        if (verifyCode==null || StringUtils.isEmpty(verifyCode)){
        	resultMap.put("messageInfo", "验证码不能为空");
        	ResponseUtils.renderJson(response, null, JSONObject.fromObject(resultMap).toString());
        	return;
        }else{
            BaseReturn userPhone = queryService.queryPhone(Integer.valueOf(userId));
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
            	resultMap.put("messageInfo", "验证码不正确");
            	ResponseUtils.renderJson(response, null, JSONObject.fromObject(resultMap).toString());
            	return;
            }
        }
//        logger.info("user redeem start......");
        BaseReturn baseReturn = redeemService.redeem(Integer.valueOf(trade_id),new Date());
//        logger.info("user redeem end......returnCode:::"+baseReturn.getReturnCode());
        resultMap.put("returnCode", baseReturn.getReturnCode());
        resultMap.put("messageInfo", baseReturn.getMessageInfo());
    	ResponseUtils.renderJson(response, null, JSONObject.fromObject(resultMap).toString());
    }
    
    /**
     * 用户换卡
     * @param request
     * @return
     */
    @RequireLogin
    @RequestMapping(value = "/changeBankCard")
    public void changeBankCard(HttpServletRequest request,HttpServletResponse response) {
        Map<String,Object> map = new HashMap<String, Object>();
        String bankCardId = request.getParameter("bankCardId");
        if (bankCardId==null || StringUtils.isEmpty(bankCardId)){
            ResponseUtils.renderJson(response, ResponseUtils.UTF8, JSONObject.fromObject( new BaseReturn(1,"银行卡ID为空")).toString());
            return;
        }
        Long userId = getUserId();
        map.put("userId", String.valueOf(userId));
        map.put("bankCardId", bankCardId);
        BaseReturn changeReturn = rechargeService.changeBankCard(map);
        ResponseUtils.renderJson(response, ResponseUtils.UTF8, JSONObject.fromObject(changeReturn).toString());
    }
    
    /**
     * 第三方合作
     * @return 
     */
    @RequestMapping("thirdparty")
    public String thirdPartyCooperation(HttpServletRequest request) {
    	int channel = CommonUtils.getChannel(request);
    	// 0彩生活|1物业国际
    	if (channel == 0) {
    		return "profile/colorLifeThirdParty";
    	} else if (channel == 1) {
    		
    	}    	
    	return "profile/woyeThirdParty";
    }

	/**
	 * @return json 字符串
	 * @author: zhangyunhmf
	 * @date: 2014年10月30日下午2:52:49
	 */
//	public String orderDetail(HttpServletRequest request,
//			HttpServletResponse response) {
//		String userId = request.getParameter("userId");
//		String orderSN = request.getParameter("orderSN");
//
//		checkUserId(userId);
//		if (StringUtils.isBlank(orderSN)) {
//
//		}
//		activityOrderService.queryOrderDetail(userId, orderSN, "0");
//		return null;
//	}

	/**
	 * @return json 字符
	 * @author: zhangyunhmf
	 * @date: 2014年10月30日下午2:55:31
	 */
//	public String userIncome() {
//		String userId = request.getParameter("userId");
//		checkUserId(userId);
//		userIncomeService.queryUserIncome(userId, UserType.COLOR_LIFE.name());
//		return null;
//
//	}
	


	/**
	 * 用户个人中心的首页， 手机版和PC版都由这个方法处理
	 * 
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("homeInit.do")
	/*public void homeInit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> result = new HashMap<String, Object>();
		AccountUserDo user = (AccountUserDo)
		request.getSession().getAttribute("user");
		PersonDo personDo = personService.getByUserId(user.getId());

		result.put("usrCustId", user.getUsrCustId());// 汇付会员编号
		result.put("userId", user.getId());// 会员编号

		result.put("realName", personDo.getRealName());// 真实姓名
		result.put("idNo", personDo.getIdNo());// 身份证

		DesSecurityUtil des = new DesSecurityUtil();
		String userI = des.encrypt(user.getId().toString());
		result.put("userI", userI);
//		String uri = getPath();
//		result.put("url", uri);

		*//**
		 * usableAmount,freezeSum ,dailyIncome,assetValue,recivedPrincipal
		 *//*
		UserIncomeDo userIncomeDo = userIncomeService.queryUserIncome(
				user.getId() + "", UserType.HEHENIAN.name());

		result.put("userIncomeDo", userIncomeDo);

		ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(result).toString());
	}*/
	
    /**
     * 查询已绑定的银行卡
     * @param request
     * @return
     */
//    @RequestMapping("/queryBindedCard.do")
   /* @ResponseBody
    public BaseReturn queryBindedCard(HttpServletRequest request) {
        Long user_id = getUserId();
        BaseReturn cardReturn = queryService.queryBindedBankCard(user_id.intValue());
        return cardReturn;
    }*/
    

    
    
    /**
     * 账户总览查询
     * @param request
     * @return
     */
//    @RequestMapping("/accountQuery.do")
   /* public ModelAndView accountQuery(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        Long userId = getUserId();
        //余额
        BaseReturn balance = queryService.queryUserBalance(userId.intValue());
        if (balance==null || balance.getReturnCode()!=0 || balance.getData()==null) {
            map.put("balance","0.00");
        }else{
            FundUserAccount balanceInfo = (FundUserAccount)balance.getData();
            map.put("balance", balanceInfo.getBalance_amount()==null?0:balanceInfo.getBalance_amount());
        }
        //投资金额
        BaseReturn principal = queryService.queryTotalInvest(userId.intValue());
        if (principal==null || principal.getReturnCode()!=0 || principal.getData()==null) {
            map.put("principal","0.00");
        }else{
            map.put("principal", principal.getData());
        }
        //赎回金额
        BaseReturn freeze = queryService.queryRound(userId.intValue());
        if (freeze==null || freeze.getReturnCode()!=0 || freeze.getData()==null) {
            map.put("freeze","0.00");
        }else{
            map.put("freeze", freeze.getData());
        }
        //待收收益
        BaseReturn interested = queryService.queryInterested(userId.intValue());
        if (interested==null || interested.getReturnCode()!=0 || interested.getData()==null) {
            map.put("interested","0.00");
        }else{
            map.put("interested", interested.getData());
        }
        //已收收益
        BaseReturn interest = queryService.queryInterest(userId.intValue());
        if (interest==null || interest.getReturnCode()!=0 || interest.getData()==null) {
            map.put("interest","0.00");
        }else{
            map.put("interest", interest.getData());
        }
        //其它收益
        BaseReturn otherInterest = queryService.queryOtherInterest(userId.intValue());
        if (otherInterest==null || otherInterest.getReturnCode()!=0 || otherInterest.getData()==null) {
            map.put("otherInterest","0.00");
        }else{
            map.put("otherInterest", otherInterest.getData());
        }
        view.setViewName("regularOverview");
        view.addAllObjects(map);
        return view;
    }*/
    
    /**
     * 查询冲抵停车期限
     * @param request
     * @return
     */
    @RequireLogin
    @RequestMapping(value = "/queryOffsetRecords")
    public String queryOffsetRecords(HttpServletRequest request,HttpServletResponse response) {
    	long userId = super.getUserId();
		int pid = NumberUtils.toInt(request.getParameter("pid"), -1);
		BaseReturn baseReturn = investProductService.getProductRateById(pid);
		if(baseReturn.getReturnCode() != 0 || baseReturn.getData() == null) {
			return "redirect:/product/plist.do";
		}
		
		ProductRate pr = (ProductRate)baseReturn.getData();
		request.setAttribute("product", pr);
		int infotype = -1;
		if(pr.getChannel()==1&&pr.getSub_channel()==2){//冲抵物业费
			infotype = 0;
		}else if(pr.getChannel()==1&&pr.getSub_channel()==3){
			infotype = 1;
		}
		//根据pid判断infotype
    	List<OffsetRecordsDo> list = offsetService.listOffsetRecords(new Long(userId).intValue(),infotype);
    	request.setAttribute("recordList", list);
    	return "profile/offsetRecordsCB";
    }
    
    /**
     * 查询冲抵停车期限明细
     * @param request
     * @return
     */
    @RequireLogin
    @RequestMapping(value = "/queryOffsetDetails")
    public String queryOffsetDetails(HttpServletRequest request,HttpServletResponse response) {
		int tradeId = NumberUtils.toInt(request.getParameter("tradeId"), -1);
		//根据pid判断infotype
    	List<OffsetDetailsDo> list = offsetService.listOffsetDetails(tradeId);
    	request.setAttribute("detailList", list);
    	return "profile/offsetDetailsCB";
    }
    
}
