package com.hehenian.web.view.trade.action;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.INewRechargeService;
import com.hehenian.biz.common.trade.IParameterLogService;
import com.hehenian.biz.common.trade.dataobject.RechargeDo;
import com.hehenian.biz.common.trade.dataobject.ParameterLogDo.ParameterType;
import com.hehenian.web.base.action.BaseAction;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.web.common.util.ServletUtils;
import com.sp2p.entity.User;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: liuwtmf Date: 2014/9/24 Time: 11:25
 */
@Scope("prototype")
@Component("newRechargeAction")
public class NewRechargeAction extends BaseAction {
    @Autowired
    private INewRechargeService  newRechargeService;
    @Autowired
    private IUserService         accountUserService;
    @Autowired
    private IParameterLogService parameterLogService;

    public void setAccountUserService(IUserService accountUserService) {
        this.accountUserService = accountUserService;
    }

    public void setNewRechargeService(INewRechargeService newRechargeService) {
        this.newRechargeService = newRechargeService;
    }

    public String addRecharge() {
        AccountUserDo user = (AccountUserDo) session(WebConstants.SESSION_USER);
        String openBankId = request("openBankId"); // 开户银行
        String gateBusiId = request("gateBusiId"); // 支付网关
        String money = request("money");
        if (StringUtils.isBlank(money)) {// 判断是否为空
            request().setAttribute(WebConstants.MESSAGE_KEY, "充值金额不能为空");
            return "msg";
        }

        double amount = Double.parseDouble(money);
        if (amount < 1) {
            request().setAttribute(WebConstants.MESSAGE_KEY, "充值金额不能少于1元");
            return "msg";
        }

        try {
            amount = new DecimalFormat("0.00").parse(money).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RechargeDo rechargeDo = new RechargeDo();
        rechargeDo.setResult(0);
        rechargeDo.setBankName(openBankId);
        rechargeDo.setRechargeMoney(amount);
        rechargeDo.setRechargeTime(new Date());
        rechargeDo.setUserId(user.getId());
        rechargeDo.setGateBusiId(gateBusiId);
        rechargeDo.setInfo("");

        String type = request("type");
        long ordId = getLongParam("ordId", 0);
        if (StringUtils.isNotBlank(type) && ordId > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", type);// 给充值加上附加的信息 回调的时候 在进行相应的处理
                                         // wyf表示是减免物业费的
            jsonObject.put("id", ordId);
            rechargeDo.setInfo(new String(Base64.encodeBase64(jsonObject.toString().getBytes())));

        }

        IResult<?> result = newRechargeService.addRecharge(rechargeDo);
        // 发送请求到汇付
        if (result != null && result.isSuccess()) {
            String htmlText = (String) result.getModel();
            ServletUtils.write(htmlText);
            return null;
        } else {
            // ServletUtils.write(result.getErrorMessage());
            request().setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
            return "msg";
        }

    }

    public String addRechargeCallback() throws UnsupportedEncodingException {
        ServletUtils.logRequestParameters();// 打印参数日志
        parameterLogService.addParameterLog(ServletUtils.getRequestParameters(), ParameterType.RESP);
        RechargeDo rechargeDo = new RechargeDo();
        String respCode = request("RespCode");
        if (Integer.parseInt(respCode) != 0) {
            // 失败
            ServletUtils.write(request("RespDesc"));
            return null;
        } else {
            String merPriv = request("MerPriv");
            double money = Double.parseDouble(request("TransAmt"));
            int orderId = Integer.parseInt(request("OrdId"));
            if (StringUtils.isNotBlank(merPriv)) {
                String info = new String(Base64.decodeBase64(merPriv.getBytes()));
                LOG.info("--------info:" + info);
                rechargeDo.setInfo(info);
            }
            rechargeDo.setRechargeMoney(money);
            rechargeDo.setId(orderId);
            rechargeDo.setPaynumber(request("TrxId"));
            rechargeDo.setBankName(request("GateBankId"));
            rechargeDo.setGateBusiId(request("GateBusiId"));
            rechargeDo.setCost(getDoubleParam("FeeAmt", 0));
            IResult<?> result = newRechargeService.addRechargeCallback(rechargeDo);
            if (result.isSuccess()) {
                // ServletUtils.write("订单:RECV_ORD_ID_" + request("TrxId"));
                request().setAttribute(WebConstants.MESSAGE_KEY, "充值成功");
                return "msg";
            } else {
                request().setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
                return "msg";
            }
        }

    }
}
