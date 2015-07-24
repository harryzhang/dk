package com.sp2p.action.front;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.activity.IActivityAuthService;
import com.hehenian.biz.common.activity.IActivityOrderService;
import com.hehenian.biz.common.activity.dataobject.ActivityAuthDo;
import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;
import com.hehenian.web.common.util.ServletUtils;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.web.action.BaseAction;
import com.sp2p.constants.IConstants;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.MyHomeService;

/**
 * User: liuwtmf
 * Date: 2014/10/29
 * Time: 9:59
 * 投e理财免物业费
 */
public class WyfAction extends BaseAction {
    private MyHomeService         myHomeService;
    @Autowired
    private IActivityAuthService  activityAuthService;
    @Autowired
    private IActivityOrderService activityOrderService;
    @Autowired
    private IUserService          accountUserService;
    @Autowired
    private HomeInfoSettingService homeInfoSettingService;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final DateFormat DATE_FORMAT1 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final Log log = LogFactory.getLog(WyfAction.class);
    private boolean checkColorLifeSign()  {
        if (IConstants.ISDEMO.equals("1")){
            return true;
        }
        String sign = request("sign");

        StringBuilder param = new StringBuilder(sKey);
        param.append("userid").append(request("userid"));
        param.append("username").append(request("username"));
        param.append("ordNo").append(request("ordNo"));
        param.append("beginDate").append(request("beginDate"));
        param.append("endDate").append(request("endDate"));
        param.append("ordDate").append(request("ordDate"));
        param.append("t").append(request("t"));
        param.append("investAmount").append(request("investAmount"));
        param.append("deductAmount").append(request("deductAmount"));
        param.append("deductPerMonth").append(request("deductPerMonth"));
        param.append("profit").append(request("profit"));
        try {
            param.append("billingAddress").append(URLEncoder.encode(request("billingAddress"), "utf-8"));
            param.append("cId").append(request("cId"));
            param.append("cName").append(URLEncoder.encode(request("cName"), "utf-8"));
            param.append("mobile").append(request("mobile"));
            param.append("password").append(request("password"));
            param.append("via").append(request("via"));
            param.append("rate_type").append(URLEncoder.encode(request("rate_type"), "utf-8"));
            param.append("orderModel").append(request("orderModel"));
            param.append("userRate").append(request("userRate"));
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        param.append(sKey);
        String check = Encrypt.MD5(param.toString());
        check=check.toUpperCase();
        log.info("-----------------param sign is:" + sign + ",sign:" + check);
        return check.equals(sign);
    }
    private static final String sKey="DJKC#$%CD%des$";
    public String appIndex(){
        session().setAttribute("platform", "colorlife_wyf_app");
        if (!checkColorLifeSign()){
            ServletUtils.write("参数无效或 验证签名失败");
            return null;
        }
        return SUCCESS;
    }

    public String webIndex(){
        session().setAttribute("platform", "colorlife_wyf_web");
        if (!checkColorLifeSign()){
            ServletUtils.write("参数无效或 验证签名失败");
            return null;
        }
        return SUCCESS;
    }

    public String index() {
        String title = request("title");
        request().setAttribute("title",title);
        int step = 1;
        String ordNo = request("ordNo");
        if (StringUtils.isNotBlank(ordNo)) {
            session().setAttribute("wyf_ordno", ordNo);
        } else {
            Object object = session("wyf_ordno");
            if (object != null) {
                ordNo = object.toString();
            } else {
                //没有订单信息 返回失败
                return null;
            }
        }

        if (StringUtils.isNotBlank(ordNo)) {
            //查询用户自动投标状态
           AccountUserDo user = (AccountUserDo) session().getAttribute("user");


            ActivityOrderDo activityOrderDo1 = new ActivityOrderDo();
            activityOrderDo1.setOrdNo(ordNo);
            activityOrderDo1.setOrdType(0);
            ActivityOrderDo activityOrderDo = activityOrderService.getByOrdNo(activityOrderDo1);
            if (activityOrderDo==null){
                //订单不存在 保存订单信息
                Object wyf_activityOrderDo = session("wyf_activityOrderDo");
                if (wyf_activityOrderDo!=null){
                    activityOrderDo = (ActivityOrderDo)wyf_activityOrderDo;
                    Long id = activityOrderService.addActivityOrder(activityOrderDo);
                    if (id<=0){
                        //保存失败
                        log.error("addActivityOrder 失败了.activityOrderDo:"+activityOrderDo);
                    }else {
                        activityOrderDo.setOrdId(id);
                        session().removeAttribute("wyf_activityOrderDo");
                    }
                }else{
                    String beginDateStr = request("beginDate");
                    String endDateStr = request("endDate");
                    String ordDateStr = request("ordDate");
                    try {
                        Date beginDate = DATE_FORMAT.parse(beginDateStr);
                        Date endDate = DATE_FORMAT.parse(endDateStr);
                        Date ordDate = DATE_FORMAT1.parse(ordDateStr);
                        String investAmountStr = request("investAmount");
                        String deductAmountStr = request("deductAmount");

                        String rateType = request("rate_type");
                        String orderSubType = request("orderModel");

                        String deductPerMonthStr = request("deductPerMonth");
                        String profitStr = request("profit");
                        String billingAddress = URLDecoder.decode(request("billingAddress"),"utf-8");
                        
                        //动态文字描述：
                        //每月冲抵物业费/冲抵费用
                        String deductPerMonthText = URLDecoder.decode(request("deductPerMonthText"),"utf-8");
                        //冲抵周期/冲抵时长
                        String billingDateText = URLDecoder.decode(request("billingDateText"),"utf-8");
                        //冲抵物业费地址/冲抵地址
                        String billingAddressText = URLDecoder.decode(request("billingAddressText"),"utf-8");
                        //车牌号 (值为：空或者车牌号：粤GF4586)
                        String carNumber = URLDecoder.decode(request("carNumber"),"utf-8");
                        //产品利率
                        String userRate = request("userRate");
                        
                       
                       
                        
                        String cId = request("cId");
                        String cName = URLDecoder.decode(request("cName"),"utf-8");
                        int investAmount = Integer.parseInt(investAmountStr);
                        double deductAmount = Double.parseDouble(deductAmountStr);
                        double deductPerMonth = Double.parseDouble(deductPerMonthStr);
                        double profit = Double.parseDouble(profitStr);
                        double rate =  Double.parseDouble(userRate);
                        
                        activityOrderDo = new ActivityOrderDo();
                        activityOrderDo.setUserId(user.getId());
                        activityOrderDo.setOrdType(0);
                        activityOrderDo.setInvestAmount(investAmount);
                        activityOrderDo.setDeductAmount(deductAmount);
                        activityOrderDo.setDeductPerMonth(deductPerMonth);
                        activityOrderDo.setProfit(profit);
                        activityOrderDo.setcName(cName);
                        activityOrderDo.setcId(cId);
                        activityOrderDo.setBillingAddress(billingAddress);
                        activityOrderDo.setBeginDate(beginDate);
                        activityOrderDo.setEndDate(endDate);
                        activityOrderDo.setOrdDate(ordDate);
                        activityOrderDo.setOrdNo(ordNo);
                        activityOrderDo.setOrdStatus(0);
                        activityOrderDo.setRateType(rateType);
                        activityOrderDo.setOrderSubType(orderSubType);
                        Date now = new Date();
                        activityOrderDo.setCreateTime(now);
                        activityOrderDo.setUpdateTime(now);
                        activityOrderDo.setRechargeMoney(0.0);
                        
                        //设置动态文本
                        activityOrderDo.setBillingAddressText(billingAddressText);
                        activityOrderDo.setBillingDateText(billingDateText);
                        activityOrderDo.setDeductPerMonthText(deductPerMonthText);
                        activityOrderDo.setCarNumber(carNumber);
                        activityOrderDo.setRate(rate);                        
                        
                        Long id = activityOrderService.addActivityOrder(activityOrderDo);
                        if (id<=0){
                            //保存失败
                            log.error("addActivityOrder 失败了。activityOrderDo："+activityOrderDo);
                        }else {
                            activityOrderDo.setOrdId(id);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }


            }else if(!user.getId().equals(activityOrderDo.getUserId())){
                //订单已存在 但不是该用户的订单
                ServletUtils.write("该订单号已被其他用户使用,请联系彩之云客服.");
                return  null;
            }

            if (activityOrderDo !=null){
                AccountUserDo accountUserDo = accountUserService.getById(user.getId());
                request().setAttribute("userinfo",accountUserDo);
                Integer ordStatus = activityOrderDo.getOrdStatus();
                if (ordStatus != 1) {
                    if (accountUserDo.getUsrCustId()!=null&&accountUserDo.getUsrCustId()>0){
                        //已经注册汇付
                        step++;
                    }

                    ActivityAuthDo activityAuthDo1 = new ActivityAuthDo();
                    activityAuthDo1.setOrdId(activityOrderDo.getOrdId());
                    activityAuthDo1.setFromUserId(user.getId());
                    activityAuthDo1.setAuthType(0);
                    ActivityAuthDo activityAuthDo = activityAuthService.getByOrdId(activityAuthDo1);
                    if (activityAuthDo == null){
                        //未授权
                        request().setAttribute("hasAuth",false);
                        request().setAttribute("authAmount",activityOrderDo.getInvestAmount());
                    }else if(activityAuthDo.getAuthAmount() < activityOrderDo.getInvestAmount()){
                        //已授权 但授权金额不够
                        request().setAttribute("hasAuth",false);
                        request().setAttribute("authAmount",activityOrderDo.getInvestAmount()-activityAuthDo.getAuthAmount());
                    }else{
                        step++;
                        if (activityOrderDo.getOrdStatus()!=1){
                            //授权成功，修改订单状态为3
                            ActivityOrderDo activityOrderDo2 = new ActivityOrderDo();
                            activityOrderDo2.setOrdId(activityOrderDo.getOrdId());
                            activityOrderDo2.setOrdStatus(3);
                            activityOrderService.updateActivityOrderById(activityOrderDo2);
                            //通知彩生活
                            activityOrderDo.setOrdStatus(3);
                            activityOrderService.notifyColorLife(accountUserDo,activityOrderDo);
                        }

                        request().setAttribute("hasAuth",true);
                        //判断是否开启自动投标
                        try {
                            Map<String, String> automaticBidMap = myHomeService.queryAutomaticBid(user.getId());
                            request().setAttribute("bidStatus", automaticBidMap.get("bidStatus"));
                            if ("2".equals(automaticBidMap.get("bidStatus"))){
                                //已开启
                                step++;
                                //判断是否充值
                                Integer investAmount = activityOrderDo.getInvestAmount();
                                double investMoney = activityOrderDo.getRechargeMoney();
                                if (investMoney >= investAmount){
                                    //充够钱了
                                    step++;
                                    request().setAttribute("hasRecharge",true);
                                    request().setAttribute("wyf_recharge_money", 0);
                                }else {
                                    //没充够钱
                                    request().setAttribute("hasRecharge",false);
                                    request().setAttribute("wyf_recharge_money", investAmount- investMoney);
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (DataException e) {
                            e.printStackTrace();
                        }
                    }

                    if (step>=5){
                        //更新订单状态
                        activityOrderDo.setOrdStatus(1);
                        int i = activityOrderService.updateStatus(accountUserDo,activityOrderDo);
                        if (i<=0){
                            //修改订单状态失败
                            log.error("修改订单状态失败.activityOrderDo:"+activityOrderDo);
                        }
                    }
                }


                //已成功
                request().setAttribute("activityOrderDo",activityOrderDo);

                if (activityOrderDo.getOrdStatus() == 1){
                    request().setAttribute("title","");
                }
            }

            try {
                List<Map<String, Object>> maps = homeInfoSettingService.queryBankInfoList2(user.getId());
                request().setAttribute("bankList",maps);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DataException e) {
                e.printStackTrace();
            }
        }
        request().setAttribute("step",step);
        if ("colorlife_wyf_app".equals(session("platform"))){
            return "colorlife_wyf_app";
        }else{
            return "colorlife_wyf_web";
        }

    }


//    public String

    public void setMyHomeService(MyHomeService myHomeService) {
        this.myHomeService = myHomeService;
    }

    public void setActivityAuthService(IActivityAuthService activityAuthService) {
        this.activityAuthService = activityAuthService;
    }

    public void setActivityOrderService(IActivityOrderService activityOrderService) {
        this.activityOrderService = activityOrderService;
    }

    public void setAccountUserService(IUserService accountUserService) {
        this.accountUserService = accountUserService;
    }
}
