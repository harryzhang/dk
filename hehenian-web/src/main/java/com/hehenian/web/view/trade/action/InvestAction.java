package com.hehenian.web.view.trade.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.IInvestService;
import com.hehenian.biz.common.trade.IParameterLogService;
import com.hehenian.biz.common.trade.dataobject.InvestDo;
import com.hehenian.biz.common.trade.dataobject.ParameterLogDo.ParameterType;
import com.hehenian.web.base.action.BaseAction;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.web.common.util.ServletUtils;
import com.sp2p.entity.User;

/**
 * User: liuwtmf Date: 2014/9/26 Time: 14:14
 */
@Scope("prototype")
@Component("newInvestAction")
public class InvestAction extends BaseAction implements ServletRequestAware, SessionAware {
    private static final long    serialVersionUID = 1L;
    @Autowired
    private IInvestService       investService;
    @Autowired
    private IUserService         userService;
    @Autowired
    private IParameterLogService parameterLogService;
    private HttpServletRequest   request;
    private Map<String, Object>  session;

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public String borrowInvest() {
        long borrowId = getLongParam("paramMap.id", 0);
        double amount = getDoubleParam("paramMap.amount", 0);
        if (borrowId > 0) {
            InvestDo newInvestDo = new InvestDo();
            newInvestDo.setBorrowId(borrowId);
            newInvestDo.setInvestAmount(amount);
            newInvestDo.setInvestor(getUserId());
            newInvestDo.setSourceFrom(getSourceFrom());
            AccountUserDo user = getUser();
            AccountUserDo accountUserDo = userService.getById(user.getId());
            IResult<?> result = investService.addInvest(newInvestDo, user.getUsrCustId(), user.getUserGroup(),
                    accountUserDo.getUsableSum() + "");
            if (result != null && result.isSuccess()) {
                // 成功罗
                String htmlText = (String) result.getModel();
                ServletUtils.write(htmlText);
                return null;
            } else {
                // 不成功
                // ServletUtils.write(result.getErrorMessage());
                request().setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
                return "msg";
            }
        } else {
            request().setAttribute(WebConstants.MESSAGE_KEY, "参数错误,请重试");
            return "msg";
        }

    }

    public String borrowInvestCallback() throws UnsupportedEncodingException {
        ServletUtils.logRequestParameters();// 打印参数日志
        parameterLogService.addParameterLog(ServletUtils.getRequestParameters(), ParameterType.RESP);
        InvestDo investDo = new InvestDo();
        String respCodeStr = request("RespCode");
        Integer respCode = 0;
        try {
            respCode = Integer.parseInt(respCodeStr);
        } catch (Exception e) {
        }
        if (respCode != 0) {
            // 失败
            ServletUtils.write(URLDecoder.decode(request("RespDesc"), "UTF-8"));
            return null;
        } else {
            long borrowId = Long.parseLong(request("MerPriv"));
            double money = Double.parseDouble(request("TransAmt"));
            long orderId = Long.parseLong(request("OrdId"));
            investDo.setId(orderId);
            investDo.setInvestAmount(money);
            investDo.setBorrowId(borrowId);
            // investDo.setTrxId(12l);
            investDo.setTrxId(Long.parseLong(request("FreezeTrxId")));
            IResult<?> result = investService.addInvestCallback(investDo);
            if (result.isSuccess()) {
                // ServletUtils.write("恭喜您，投资成功。");
                request().setAttribute(WebConstants.MESSAGE_KEY, "恭喜您，投资成功。");
                return "msg";
            } else {
                request().setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
                return "msg";
            }
        }
    }

    /**
     * 流标
     * 
     * @return
     */
    public String discardBorrow() {
        String idsStr = request("id");
        if (StringUtils.isNotBlank(idsStr)) {
            String[] ids = idsStr.split(",");
            for (String idStr : ids) {
                long id = 0;
                try {
                    id = Long.parseLong(idStr);
                } catch (Exception e) {

                }
                if (id > 0) {
                    IResult<?> result = investService.discardBorrow(id);
                    if (result.isSuccess()) {
                        ServletUtils.write("操作成功");
                    } else {
                        ServletUtils.write("操作失败." + result.getErrorMessage());
                    }
                }

            }
        }
        return null;
    }

}
