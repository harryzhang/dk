/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.web.view.activity;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.activity.IActivityAuthService;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.IParameterLogService;
import com.hehenian.biz.common.trade.dataobject.ParameterLogDo.ParameterType;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.web.common.util.CalculateUtils;
import com.hehenian.web.common.util.ServletUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.sp2p.entity.User;

@Scope("prototype")
@Component("activityAuthAction")
public class ActivityAuthAction extends ActionSupport implements ServletRequestAware, SessionAware {
    private static final long    serialVersionUID = 1L;
    @Autowired
    private IActivityAuthService activityAuthService;
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

    /**
     * 授权转账
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日下午5:06:00
     */
    public String transferAuth() {
        String ordId = request.getParameter("ordId");

        if (StringUtils.isBlank(ordId)) {
            request.setAttribute(WebConstants.MESSAGE_KEY, "参数有误!");
            return ERROR;
        }
        AccountUserDo user = (AccountUserDo) session.get(WebConstants.SESSION_USER);
        IResult<?> result = activityAuthService.transferAuth(Long.parseLong(ordId), user.getId());
        if (result != null && result.isSuccess()) {
            String htmlText = (String) result.getModel();
            ServletUtils.write(htmlText);
            return null;
        } else {
            request.setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
            return ERROR;
        }
    }

    /**
     * 授权转账回调
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日下午5:06:04
     */
    public String transferAuthCb() throws Exception {
        ServletUtils.logRequestParameters();// 打印参数日志
        parameterLogService.addParameterLog(ServletUtils.getRequestParameters(), ParameterType.RESP);
        String respCode = request.getParameter("RespCode");
        if (!WebConstants.RESP_CODE_SUCCESS.equals(respCode)) {
            request.setAttribute(WebConstants.MESSAGE_KEY, URLDecoder.decode(request.getParameter("RespDesc"), "UTF-8")
                    + "，请联系客服!");
            return ERROR;
        }
        String usrCustId = request.getParameter("UsrCustId");
        String inUsrCustId = request.getParameter("InUsrCustId");
        String authAmt = request.getParameter("AuthAmt");
        if (StringUtils.isBlank(usrCustId) || StringUtils.isBlank(inUsrCustId) || StringUtils.isBlank(authAmt)) {
            request.setAttribute(WebConstants.MESSAGE_KEY, "参数有误!");
            return ERROR;
        }
        if (CalculateUtils.le(Double.parseDouble(authAmt), 0.00)) {
            request.setAttribute(WebConstants.MESSAGE_KEY, "授权金额有误!");
            return ERROR;
        }

        IResult<?> result = activityAuthService.transferAuthCb(Long.parseLong(usrCustId), Long.parseLong(inUsrCustId),
                Double.parseDouble(authAmt));
        if (result != null && result.isSuccess()) {
            request.setAttribute(WebConstants.MESSAGE_KEY, "定向转账授权成功!");
            return SUCCESS;
        } else {
            request.setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
            return ERROR;
        }
    }
}
