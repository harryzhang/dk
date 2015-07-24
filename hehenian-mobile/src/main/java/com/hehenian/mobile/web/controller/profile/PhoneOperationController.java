package com.hehenian.mobile.web.controller.profile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hehenian.biz.common.dqlc.IDqlcService;
import com.hehenian.biz.common.individualCenter.IIndividualService;
import com.hehenian.common.utils.ResponseUtils;
import com.hehenian.mobile.web.controller.BaseController;
import com.hhn.hessian.cardverify.ICardVerifyService;
import com.hhn.hessian.invest.IFundInvestService;
import com.hhn.hessian.invest.IInvestProductService;
import com.hhn.hessian.query.IQueryService;
import com.hhn.hessian.recharge.IRechargeService;
import com.hhn.hessian.redeem.IRedeemService;
import com.hhn.hessian.withdraw.IWithdrawService;
import com.hhn.pojo.FundActualAccountLog;
import com.hhn.pojo.FundBankCard;
import com.hhn.pojo.FundUserAccount;
import com.hhn.pojo.Invest;
import com.hhn.pojo.ProductRate;
import com.hhn.util.BaseReturn;
import com.hhn.util.Constants;
import com.hhn.util.DateUtil;
import com.hhn.util.DqlcConfig;

@Controller
@RequestMapping(value = "/phoneOperation")
public class PhoneOperationController extends BaseController {

    @Autowired
    private IInvestProductService investProductService;
    @Autowired
    private IQueryService queryService;
    @Autowired
    private IRechargeService rechargeService;
    @Autowired
    private IWithdrawService withdrawService;
    @Autowired
    private IFundInvestService fundInvestmentService;
    @Autowired
    private IDqlcService dqlcService;
    @Autowired
    private ICardVerifyService cardVerifyService;
    @Autowired
    private IRedeemService redeemService;
    @Autowired
    private IIndividualService userService;
    
//  @Autowired
//  public DqlcConfig dqlcConfig;
   
    
    /**
     * 手机端
     * 定期理财主页
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/accountDetailPhone")
    public void accountDetailPhone(HttpServletRequest request,HttpServletResponse response) {
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
        BaseReturn yestProfit = queryService.queryYestProfit(userId.intValue());
        if (yestProfit==null || yestProfit.getReturnCode()!=0 || yestProfit.getData()==null) {
            map.put("yestProfit","0.00");
        }else{
            map.put("yestProfit", yestProfit.getData());
        }
        //资产估值
        BaseReturn totalValue = queryService.queryTotalValue(userId.intValue());
        if (totalValue==null || totalValue.getReturnCode()!=0 || totalValue.getData()==null) {
            map.put("totalValue","0.00");
        }else{
            map.put("totalValue", totalValue.getData());
        }
        ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(map).toString());
    }

    
    /**
     * 移动端
     * 查询投资期限的利率
     * @return
     */
    @RequestMapping(value = "/getInvestRate")
    @ResponseBody
    public void getInvestRate(HttpServletRequest request,HttpServletResponse response){
    	HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            String mounth = request.getParameter("mounth");//期数
            if (mounth == null || "".equals(mounth)) {
            	map.put("code", "1");
        		map.put("msg","投资期限不能为空！");
            } else {
                //查询用户余额
                BigDecimal balanceAmount = new BigDecimal(0);
                Long user_id = getUserId();
                BaseReturn balance = queryService.queryUserBalance(user_id.intValue());
                if (balance.getReturnCode()==0 && balance.getData()!=null) {
                    FundUserAccount balanceInfo = (FundUserAccount) balance.getData();
                    if (balanceInfo != null) {
                        balanceAmount = balanceInfo.getBalance_amount() == null ? new BigDecimal(0) : balanceInfo.getBalance_amount();
                    }
                }
//                logger.debug("dqlcConfig.investMoneyScope:"+dqlcConfig.investMoneyScope);
                BaseReturn existProduct = queryService.queryPay();
                BigDecimal remainAmount = BigDecimal.ZERO;
                if (existProduct.getReturnCode()!=0 || existProduct.getData()==null){
//                    map.put("canInvest", dqlcConfig.investMoneyScope);
                }else{
                    BigDecimal canInvest = (BigDecimal)existProduct.getData();
                    if (null != canInvest) {
//                        remainAmount = canInvest.add(new BigDecimal(dqlcConfig.investMoneyScope));
                        if (BigDecimal.ZERO.compareTo(remainAmount) > 0) {
                            remainAmount = BigDecimal.ZERO;
                        }
                    }
                    map.put("canInvest", remainAmount);
                }
                //查询当前利率
                map.put("mounth", Integer.valueOf(mounth));
                BaseReturn baseReturn = investProductService.getProductRate(map);
                map.put("balance", balanceAmount);
                map.put("rate", baseReturn.getData());
            }
        }catch (Exception e){
//            logger.error("error",e);
        	map.put("code", "1");
    		map.put("msg","系统正忙请稍后重试！");
        }
        
        ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(map).toString());
    }

    /**
     * 移动端
     * 用户购买
     * @return
     */
    @RequestMapping(value = "/buyFunancial")
    @ResponseBody
    public void buyFunancial(HttpServletRequest request,HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<String, Object>();
        String mounth = request.getParameter("mounth");//期数
        String amount = request.getParameter("amount");//金额
        String source = request.getParameter("source");//来源
        if (mounth != null && !"".equals(mounth)) {
            map.put("mounth", Integer.valueOf(mounth));
        } else {
        	map.put("code", "1");
    		map.put("msg","投资期限不能为空！");
        }
        if (amount != null && !"".equals(amount)) {
            BigDecimal investAmount = new BigDecimal(amount);
            investAmount = investAmount;
            map.put("amount", investAmount);
        } else {
            map.put("code", "1");
    		map.put("msg","投资金额不能为空！");
        }
        if (source != null && !"".equals(source)) {
            map.put("source", source);
        } else {
        	map.put("code", "1");
    		map.put("msg","投资来源不能为空！");
        }
        Long userId = getUserId();
        String sourceFrom = getSourcFrom();
//        logger.debug("sourceFrom:=====>" + sourceFrom);
        //判断投资金额与账户余额比较
        BaseReturn baseBalance = queryService.queryUserBalance(userId.intValue());
        FundUserAccount balanceAmount = (FundUserAccount)baseBalance.getData();
        if (balanceAmount!=null) {
            BigDecimal balance = balanceAmount.getBalance_amount();
            balance = balance==null?new BigDecimal(0):balance;
            BigDecimal investAmount = new BigDecimal(amount);
            //投资金额小于或等于余额时
            if (investAmount.compareTo(balance) <= 0) {
                //余额够用，调投资接口
                BaseReturn baseReturn = doPhoneInvestment(userId+"",mounth,amount,sourceFrom);
                map.put("data",baseReturn.getData());
                map.put("messageInfo",baseReturn.getMessageInfo());
                map.put("tradeTime", DateUtil.format(new Date()));
//                baseReturn.setData(map);
            }else{
                //先充值，后投资
                BaseReturn userPhone = queryService.queryPhone(userId.intValue());
                Map<String,Object> userMap = (HashMap<String,Object>)userPhone.getData();
                String mobilePhone = (String)userMap.get("mobilePhone");
                if (mobilePhone.startsWith("-")){
                    mobilePhone = mobilePhone.substring(1);
                }
                String realName = (String)userMap.get("realName");
                map.put("userName",realName);
                map.put("phone",mobilePhone);
                map.put("balance",balance);
                BigDecimal phAmount = investAmount.subtract(balance);
                map.put("amount", phAmount);
                BigDecimal phTotalAmount = new BigDecimal(amount);
                map.put("totalAmount", phTotalAmount);
                HttpSession session = request.getSession();
                session.setAttribute("phMounth",mounth);
                session.setAttribute("phAmount",phAmount);
                session.setAttribute("phTotalAmount", phTotalAmount);
//                BaseReturn baseReturn = new BaseReturn();
//                baseReturn.setReturnCode(2);//2-先充值
//                baseReturn.setData(map);
            }
        }else {
            BaseReturn userPhone = queryService.queryPhone(userId.intValue());
            Map<String,Object> userMap = (HashMap<String,Object>)userPhone.getData();
            String mobilePhone = (String)userMap.get("mobilePhone");
            if (mobilePhone.startsWith("-")){
                mobilePhone = mobilePhone.substring(1);
            }
            String realName = (String)userMap.get("realName");
            map.put("userName",realName);
            map.put("phone",mobilePhone);
            map.put("balance", 0);
            map.put("amount", map.get("amount"));
            map.put("totalAmount", map.get("amount"));
            HttpSession session = request.getSession();
            session.setAttribute("phMounth",mounth);
            session.setAttribute("phAmount",map.get("amount"));
            session.setAttribute("phTotalAmount", map.get("amount"));
//            BaseReturn baseReturn = new BaseReturn();
//            baseReturn.setReturnCode(2);//2-先充值
//            baseReturn.setData(map);
        }
        ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(map).toString());
    }
    /**
     * 移动端
     * 用户投资充值
     * @return
     */
    @RequestMapping(value = "/chargeMoneyPhone")
    @ResponseBody
    public void chargeMoneyPhone(HttpServletRequest request,HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            String user_name = request.getParameter("userName");//用户姓名
            String bankCode = request.getParameter("bankCode");//银行代码
            String account_no = request.getParameter("userAccount");//银行帐号
            String password = request.getParameter("passwd");//登录密码
            String mounth = request.getParameter("mounth");//购买期限
            String amount = request.getParameter("amount"); //充值金额
            String totalAmount = request.getParameter("totalAmount");//总金额
            String phone = request.getParameter("phone");//手机号
            String source = request.getParameter("source");//来源
            String verfiyCode = request.getParameter("verfiyCode");//验证码
            Long user_id = getUserId(); //用户ID
            map.put("user_id", user_id);
            if (password != null && !"".equals(password)) {
                if (verfiyCode ==null || "".equals(verfiyCode)) {
                    map.put("code", "1");
            		map.put("msg","验证码不能为空！");
                }
                Integer userId = user_id.intValue();
                //查询账户名和手机号
                BaseReturn userPhone = queryService.queryPhone(userId);
                Map<String,Object> userMap = (HashMap<String,Object>)userPhone.getData();
                String mobilePhone = (String)userMap.get("mobilePhone");
                if (mobilePhone.startsWith("-")){
                    mobilePhone = mobilePhone.substring(1);
                }
                String realName = (String)userMap.get("realName");
//                logger.info("账户名：" + realName + ",手机号:" + mobilePhone);
                map.put("ACCOUNT_NAME", realName);//帐户名
                Map<String,Boolean> validMap = dqlcService.checkPhoneVerifyCodeAndPwd(userId.longValue(),password,mobilePhone,verfiyCode);
                if (!validMap.get("pwd")){
                	map.put("code", "1");
            		map.put("msg","登录密码不正确！");
                }
                if (!validMap.get("phone")) {
                	map.put("code", "1");
            		map.put("msg","验证码不正确！");
                }
            } else {
            	map.put("code", "1");
        		map.put("msg","登录密码不能为空！");
            }
            if (bankCode!=null && !"".equals(bankCode)){
                map.put("BANK_CODE", bankCode);
            }else{
            	map.put("code", "1");
        		map.put("msg","未选择银行！");
            }
            if (account_no != null && !"".equals(account_no)) {
                map.put("ACCOUNT_NO", account_no.replaceAll(" ", ""));
            } else {
            	map.put("code", "1");
        		map.put("msg","用户ID不能为空！");
            }
            HttpSession session = request.getSession();
            if (amount != null && !"".equals(amount)) {
                map.put("AMOUNT", session.getAttribute("phAmount"));
            } else {
            	map.put("code", "1");
        		map.put("msg","充值金额不能为空！");
            }
            String sourceFrom = getSourcFrom();
//            logger.debug("sourceFrom:=====>"+sourceFrom);
            BaseReturn baseReturn = rechargeService.recharge(map);
            if (baseReturn.getReturnCode() == 0) {
                String phmounth = (String)session.getAttribute("phMounth");
                String phAmount = (String)session.getAttribute("phAmount");
                String phTotalAmount = (String)session.getAttribute("phTotalAmount");
                //调投资接口
                BaseReturn baseReturn1 = doPhoneInvestment(String.valueOf(user_id),phmounth,phAmount,sourceFrom);
                map.put("data",baseReturn.getData());
                map.put("messageInfo",baseReturn.getMessageInfo());
                map.put("tradeTime", DateUtil.format(new Date()));
//                baseReturn1.setData(map);
//                return baseReturn1;
            }else{
//                logger.info("手机端充值失败。。。。。。。。。。。。。。");
//                logger.info(baseReturn.getMessageInfo());
//                logger.info(baseReturn.getData());
//                return baseReturn;
            }
        }catch (Exception e){
//            logger.error("error",e);
//            return new BaseReturn(1,"系统正忙请稍后重试！");
        }
        ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(map).toString());
    }

    //手机用户投资
    private BaseReturn doPhoneInvestment(String userId,String mounth,String amount,String source){
        Invest invest = new Invest();
        invest.setUser_id(Integer.valueOf(userId));
        invest.setMonth(Integer.valueOf(mounth));
        BigDecimal withdraw_amount = new BigDecimal(amount);
        invest.setMoney(withdraw_amount);
        invest.setTargetType(source);
//        logger.info("手机端调投资接口开始..................start.");
        BaseReturn baseReturn = fundInvestmentService.investment(invest);
//        logger.info("手机端调投资接口返回......................end.");
        return baseReturn;
    }
    
    /**
     * 查询已绑定的银行卡
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryBindedCard")
    @ResponseBody
    public void queryBindedCard(HttpServletRequest request,HttpServletResponse response) {
    	Map<String, Object> map = new HashMap<String, Object>();
        Long user_id = getUserId();
        Integer userId = user_id.intValue();
        BaseReturn cardReturn = queryService.queryBindedBankCard(userId);
        map.put("data",cardReturn.getData());
        ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(map).toString());
    }

    /**
     * 手机端
     * 绑定银行卡
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindCardPhone")
    @ResponseBody
    public BaseReturn bindCardPhone(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Map<String, String> param = new HashMap<String, String>();
            String bankCode = request.getParameter("bankCode");//银行代码
            String userAccount = request.getParameter("userAccount");//银行卡号
            userAccount = userAccount.replaceAll(" ", "");
            long userId = getUserId();
            if (bankCode == null || "".equals(bankCode)) {
                return new BaseReturn(1, "银行代码不能为空");
            } else {
                param.put("bankCode", bankCode);
            }
            if (userAccount == null || "".equals(userAccount)) {
                return new BaseReturn(1, "银行卡号不能为空");
            } else {
                param.put("bankNo", userAccount);
            }
            param.put("userId", userId+"");
//            logger.debug("bindCardPhone Parameter: bankNo="+userAccount+",bankNo="+bankCode);
//            logger.debug("bindCardPhone invocation start............................");
            BaseReturn baseReturn = cardVerifyService.sendBankIdentifyCode(param);
//            logger.debug("bindCardPhone sendBankIdentifyCode:::"+baseReturn.getReturnCode());
//            logger.debug("bindCardPhone invocation end..............................");
            return baseReturn;
        }catch (Exception e){
            return new BaseReturn(1, "系统正忙请稍后重试");
        }
    }

    /**
     * 验证银行卡
     * @param request
     * @return
     */
    @RequestMapping(value = "/verifyCardPhone")
    @ResponseBody
    public BaseReturn verifyCardPhone(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            Map<String, String> map = new HashMap<String, String>();
            String money = request.getParameter("money");//金额;
            if (money==null || "".equals(money)){
                return new BaseReturn(1,"金额不能为空");
            }
            String account = request.getParameter("account");
            if (account==null || StringUtils.isEmpty(account)){
                return new BaseReturn(1,"付款银行不能为空");
            }
            String userId = getUserId()+"";
            map.put("userId", userId);
            map.put("amount", money);
            map.put("account", account);
//            logger.debug("verifyCardPhone checkBankIdentifyCode::userId:::" + userId + "::amount::" + money);
//            logger.debug("verifyCardPhone invocation start............................");
            BaseReturn baseReturn = cardVerifyService.checkBankIdentifyCode(map);
//            logger.debug("verifyCardPhone return checkBankIdentifyCode:"+baseReturn.getReturnCode());
//            logger.debug("verifyCardPhone invocation end..............................");
            return baseReturn;
        }catch (Exception e){
//            logger.error(e);
            return new BaseReturn(1, "系统正忙请稍后重试");
        }
    }

    /**
     * 手机端
     * 用户购买记录查询
     * @return
     */
    @RequestMapping(value = "/queryTradePhone")
    @ResponseBody
    public BaseReturn queryTradePhone(HttpServletRequest request,HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", getUserId()+"");
        List<Map> tradeList = (List<Map>)(investProductService.getWebTradeList(map).getData());
        map.put("tradeList", tradeList);
        return new BaseReturn(0, map, "查询成功！");
    }

    /**
     * 手机端
     * 用户投资记录查询
     * @return
     */
    @RequestMapping(value = "/getProductPhone")
    @ResponseBody
    public BaseReturn getProductList(HttpServletRequest request,HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", getUserId()+"");
        List<Map> productList = (List<Map>)(investProductService.getWebInvestmentList(map).getData());
        map.put("productList", productList);
        return new BaseReturn(0, map, "查询成功！");
    }

    

    /**
     * 手机端
     * 定期理财首页
     * @param request
     * @return
     */
    @RequestMapping(value = "/termFinancePhone")
    public ModelAndView termFinancePhone(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        BaseReturn baseReturn = investProductService.getProductRateList();
        List<ProductRate> list = null;
        if (baseReturn.getReturnCode()==0 && baseReturn.getData()!=null){
            list = (List<ProductRate>)baseReturn.getData();
        }
        if (list!=null && list.size()>0){
            int count = list.size();
            for (int i=0;i<count;i++){
                ProductRate rate = list.get(i);
                map.put("rateIdPhone"+(i+1), rate.getProduct_rate_id());
                map.put("periodPhone"+(i+1), rate.getPeriod());
                map.put("ratePhone"+(i+1), rate.getRate());
            }
            setPhoneParamters(request, list);
        }
        view.addAllObjects(map);
        view.setViewName("mobile/index");
        return view;
    }

    /**
     * 手机端
     * 投资成功返回成功页面
     * @return
     */
    @RequestMapping(value = "/toSuccessPage")
    public ModelAndView toSuccessPage(HttpServletRequest request,HttpServletResponse response){
        ModelAndView view = new ModelAndView();
        Map<String,Object> map = new HashMap<String,Object>();
        String mounth = request.getParameter("mounth");
        String invest = request.getParameter("invest");
        String balance = request.getParameter("balance");
        String bankNo = request.getParameter("bankNo");
        String bankCode = request.getParameter("bankCode");
        String bankName = request.getParameter("bankName");
        String charge = request.getParameter("charge");
        if (mounth != null && StringUtils.isNotEmpty(mounth)) {
            map.put("mounth", mounth);
        }
        if (invest != null && StringUtils.isNotEmpty(invest)) {
            map.put("invest", invest);
        }
        if (balance != null && StringUtils.isNotEmpty(balance)) {
            map.put("balance", balance);
        }
        if (bankNo != null && StringUtils.isNotEmpty(bankNo)) {
            map.put("bankNo", bankNo);
        }
        if (bankCode != null && StringUtils.isNotEmpty(bankCode)) {
            map.put("bankCode", bankCode);
        }
        try {
            if (bankName != null && StringUtils.isNotEmpty(bankName)) {
                map.put("bankName", new String(bankName.getBytes("iso-8859-1"), "UTF-8"));
            }
        }catch (Exception e){
            map.put("bankName", bankName);
        }
        if (charge != null && StringUtils.isNotEmpty(charge)) {
            map.put("charge", charge);
        }
        BaseReturn baseReturn = investProductService.getProductRateList();
        List<ProductRate> list = null;
        if (baseReturn.getReturnCode() == 0 && baseReturn.getData() != null) {
            list = (List<ProductRate>) baseReturn.getData();
        }
        setPhoneParamters(request, list);
        view.setViewName("mobile/success");
        view.addAllObjects(map);
        return view;
    }

    /**
     * 手机端
     * 返回充值处理中页面
     * @return
     */
    @RequestMapping(value = "/chargeProcessingPage")
    public ModelAndView chargeProcessingPage(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        BaseReturn baseReturn = investProductService.getProductRateList();
        List<ProductRate> list = null;
        if (baseReturn.getReturnCode()==0 && baseReturn.getData()!=null){
            list = (List<ProductRate>)baseReturn.getData();
        }
        setPhoneParamters(request, list);
        view.setViewName("mobile/orderGeneration");
        view.addAllObjects(map);
        return view;
    }

    /**
     * 手机端
     * 设置最大最小利率
     * @param request
     * @param list
     */
    private void setPhoneParamters(HttpServletRequest request,List<ProductRate> list){
        if (list!=null && list.size()>0){
            int count = list.size();
            for (int i=0;i<count;i++){
                ProductRate rate = list.get(i);
                HttpSession session = request.getSession();
                if (i==0){
                    session.setAttribute("maxRateIdPhone", rate.getProduct_rate_id());
                    session.setAttribute("maxPeriodPhone", rate.getPeriod());
                    session.setAttribute("maxRatePhone", rate.getRate());
                }else if(i==(count-1)){
                    session.setAttribute("minRateIdPhone", rate.getProduct_rate_id());
                    session.setAttribute("minPeriodPhone", rate.getPeriod());
                    session.setAttribute("minRatePhone", rate.getRate());
                }
            }
        }
    }
    /**
     * 手机端
     * 用户充值记录查询
     * @return
     */
    @RequestMapping(value = "/userChargeListPhone")
    @ResponseBody
    public BaseReturn userChargeListPhone(HttpServletRequest request,HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userId", getUserId()+"");
        List<FundActualAccountLog> chargeList = (List<FundActualAccountLog>)(queryService.getChargeList(map).getData());
        if(chargeList!=null && chargeList.size()>0){
            for(int i=0;i<chargeList.size();i++){
                FundActualAccountLog actualAccountLog = chargeList.get(i);
                actualAccountLog.setFrom_account(Constants.getHide2BandNo(actualAccountLog.getFrom_account()));
            }
        }
        map.put("chargeList", chargeList);
        return new BaseReturn(0, map, "查询成功！");
    }

    /**
     * 手机端
     * 返回绑卡页面
     * @return
     */
    @RequestMapping(value = "/bindCardPhonePage")
    public ModelAndView bindCardPhonePage(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        String user_id = getUserId()+"";
        Integer userId = Integer.valueOf(user_id);
        //查询账户名和手机号
        BaseReturn userPhone = queryService.queryPhone(userId);
        Map<String,Object> userMap = (HashMap<String,Object>)userPhone.getData();
        if (userMap!=null && userMap.size()>0) {
            String realName = (String) userMap.get("realName");
            String idNo = (String) userMap.get("idNo");
            map.put("realName", realName);
            map.put("idNo", Constants.getHideIdNo(idNo));
        }
        view.setViewName("mobile/bindBank");
        view.addAllObjects(map);
        return view;
    }

    /**
     * 手机端提现页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/withdrawPagePhone")
    public ModelAndView withdrawPagePhone(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        String user_id = getUserId()+"";
        BaseReturn userPhone = queryService.queryPhone(Integer.valueOf(user_id));
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
        BaseReturn balance = queryService.queryUserBalance(Integer.valueOf(user_id));
        if (balance.getReturnCode() != 0 || balance.getData() == null) {
            map.put("balance", 0);
        } else {
            FundUserAccount balanceInfo = (FundUserAccount) balance.getData();
            map.put("balance", balanceInfo.getBalance_amount() == null ? 0 : balanceInfo.getBalance_amount());
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
//        map.put("limitOne",dqlcConfig.LIMIT_ONE_MONEY);
//        map.put("limitDay", dqlcConfig.DAY_LIMIT_MONEY);
        view.setViewName("mobile/withdrawPhone");
        view.addAllObjects(map);
        return view;
    }

    /**
     * 手机端
     * 用户提现记录查询
     * @return
     */
    @RequestMapping(value = "/withdrawListPhone")
    @ResponseBody
    public BaseReturn withdrawListPhone(HttpServletRequest request,HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", getUserId());
        List<Map> drawList = (List<Map>)(queryService.getWebWithdrawList(map).getData());
        map.put("drawList", drawList);
        return new BaseReturn(0, map, "查询成功！");
    }

    /**
     * 手机端赎回申请页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/redeemPagePhone")
    public ModelAndView redeemPagePhone(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        String trade_id = request.getParameter("tradeId");
        map.put("tradeId", trade_id);
        BaseReturn baseReturn = redeemService.getRedeemInfo(Integer.valueOf(trade_id), new Date());
        view.setViewName("mobile/redeemPhone");
        map.putAll((Map)baseReturn.getData());
        view.addAllObjects(map);
        return view;
    }



}
