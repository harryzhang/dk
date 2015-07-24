package com.hehenian.web.view.trade.action;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.IParameterLogService;
import com.hehenian.biz.common.trade.IWithdrawService;
import com.hehenian.biz.common.trade.dataobject.MerCashDo;
import com.hehenian.biz.common.trade.dataobject.WithdrawDo;
import com.hehenian.biz.common.trade.dataobject.ParameterLogDo.ParameterType;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.web.common.util.CalculateUtils;
import com.hehenian.web.common.util.ServletUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;

/**
 * 提现
 * 
 * @author liuzgmf
 * 
 */
@Scope("prototype")
@Component("withdrawAction")
public class WithdrawAction extends ActionSupport implements ServletRequestAware, SessionAware {
    private static final long    serialVersionUID = 1L;
    @Autowired
    private IWithdrawService     withdrawService;
    @Autowired
    private IUserService         accountUserService;
    @Autowired
    private IParameterLogService parameterLogService;
    private HttpServletRequest   request;
    private Map<String, Object>  session;
    private IResult<?>           result;
    private Double               money;
    private Long                 bankId;
    private String               openAcctId;

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * 提交提现申请
     * 
     * @return
     */
    public String addWithdraw() throws Exception {
        if (money == null || CalculateUtils.lt(money, 0.00)) {
            ServletUtils.write("错误金额格式!");
            return null;
        }

        // 提现信息
        WithdrawDo withdrawDo = new WithdrawDo();
        AccountUserDo user = (AccountUserDo) session.get(WebConstants.SESSION_USER);
        withdrawDo.setUserId(user.getId());
        withdrawDo.setIpAddress(ServletUtils.getClientIp());
        withdrawDo.setSum(money);
        result = withdrawService.addWithdraw(withdrawDo);

        // 发送请求到汇付
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
     * 提现汇付回调方法（前台）
     * 
     * @return
     * @throws Exception
     */
    public String addWithdrawCallback() throws Exception {
        ServletUtils.logRequestParameters();// 打印参数日志
        parameterLogService.addParameterLog(ServletUtils.getRequestParameters(), ParameterType.RESP);
        String respCode = request.getParameter("RespCode");
        if (!WebConstants.RESP_CODE_SUCCESS.equals(respCode)) {// 提现失败处理
            request.setAttribute(WebConstants.MESSAGE_KEY, URLDecoder.decode(request.getParameter("RespDesc"), "UTF-8")
                    + "，请联系客服!");
            return ERROR;
        }

        // 提现成功处理
        WithdrawDo withdrawDo = new WithdrawDo();
        withdrawDo.setId(Long.parseLong(request.getParameter("OrdId")));
        withdrawDo.setAcount(request.getParameter("OpenAcctId"));// 提现银行卡号
        withdrawDo.setSum(new Double(request.getParameter("TransAmt")));
        withdrawDo.setUsrCustId(request.getParameter("UsrCustId"));
        String servFee = request.getParameter("ServFee");
        if (StringUtils.isNotBlank(servFee) && CalculateUtils.gt(Double.parseDouble(servFee), 0.00)) {
            withdrawDo.setPoundage(Double.parseDouble(servFee));
        }
        withdrawService.addWithdrawCallback(withdrawDo);
        request.setAttribute(WebConstants.MESSAGE_KEY, "恭喜您，取现申请已经提交成功<br/>若是审批通过，资金将在1-2个工作日内到达您的银行账户。");
        return SUCCESS;
    }

    /**
     * 提现汇付回调方法（后台）
     * 
     * @return
     * @throws Exception
     */
    public String addWithdrawCallbackBg() throws Exception {
        ServletUtils.logRequestParameters();// 打印参数日志
        parameterLogService.addParameterLog(ServletUtils.getRequestParameters(), ParameterType.RESP);
        String respCode = request.getParameter("RespCode");
        Long ordId = Long.parseLong(request.getParameter("OrdId"));
        if (!WebConstants.RESP_CODE_SUCCESS.equals(respCode)) {// 提现申请是否成功
            withdrawService.updateStatus(ordId, 5, 0);// 设置状态为提现失败
            ServletUtils.write("RECV_ORD_ID_" + ordId);
            return null;
        }

        // 变更提现记录
        WithdrawDo withdrawDo = new WithdrawDo();
        withdrawDo.setId(ordId);
        withdrawDo.setAcount(request.getParameter("OpenAcctId"));// 提现银行卡号
        withdrawDo.setSum(new Double(request.getParameter("TransAmt")));
        withdrawDo.setUsrCustId(request.getParameter("UsrCustId"));
        String servFee = request.getParameter("ServFee");
        if (StringUtils.isNotBlank(servFee) && CalculateUtils.gt(Double.parseDouble(servFee), 0.00)) {
            withdrawDo.setPoundage(Double.parseDouble(servFee));
        }
        result = withdrawService.addWithdrawCallback(withdrawDo);
        if (result.isSuccess()) {
            ServletUtils.write("RECV_ORD_ID_" + ordId);
            return null;
        }
        return null;
    }

    /**
     * 新增提现复核
     * 
     * @return
     */
    public String updateWithdrawAudit() throws Exception {
        WithdrawDo withdrawDo = new WithdrawDo();
        withdrawDo.setId(Long.parseLong(request.getParameter("paramMap.wid")));// 提现ID
        withdrawDo.setRemark(request.getParameter("paramMap.remark"));// 初审备注
        withdrawDo.setStatus(Integer.parseInt(request.getParameter("paramMap.status")));// 提现ID
        Admin admin = (Admin) session.get(IConstants.SESSION_ADMIN);
        withdrawDo.setCheckId(admin.getId());
        IResult<?> result = withdrawService.updateWithdrawAudit(withdrawDo);
        if (result.isSuccess()) {
            JSONObject json = new JSONObject();
            json.put("msg", "操作成功!");
            ServletUtils.write(json.toString());
        } else {
            JSONObject json = new JSONObject();
            json.put("msg", result.getErrorMessage());
            ServletUtils.write(json.toString());
        }
        return null;
    }

    /**
     * 新增提现转账
     * 
     * @return
     */
    public String updateWithdrawTransfer() throws Exception {
        String ordId = request.getParameter("paramMap.wid");
        String status = request.getParameter("paramMap.status");

        // 复核提现
        WithdrawDo withdrawDo = new WithdrawDo();
        withdrawDo.setId(Long.valueOf(ordId));
        Admin admin = (Admin) session.get(WebConstants.SESSION_ADMIN);
        withdrawDo.setCheckId(admin.getId());// 审核人
        withdrawDo.setStatus(Integer.parseInt(status));// 复核状态
        IResult<?> result = withdrawService.updateWithdrawTransfer(withdrawDo);
        if (result.isSuccess()) {
            ServletUtils.write("操作成功!");
        } else {
            ServletUtils.write(result.getErrorMessage());
        }
        return null;
    }

    /**
     * 商户提现（平台提现）
     * 
     * @return
     * @throws Exception
     */
    public String addMerWithdraw() throws Exception {
        MerCashDo merCashDo = new MerCashDo();
        merCashDo.setUserId(Long.parseLong(request.getParameter("userId")));// 提现用户ID
        merCashDo.setUsrCustId(request.getParameter("usrCustId"));// 汇付用户编号
        merCashDo.setSum(Double.parseDouble(request.getParameter("transAmt")));// 提现金额
        Admin admin = (Admin) session.get(WebConstants.SESSION_ADMIN);
        merCashDo.setAdminId(admin.getId());// 管理员ID
        IResult<?> result = withdrawService.addMerWithdraw(merCashDo);
        if (result.isSuccess()) {
            ServletUtils.write("操作成功!");
        } else {
            ServletUtils.write(result.getErrorMessage() + "，提现失败!");
        }
        return null;
    }

    public IWithdrawService getWithdrawService() {
        return withdrawService;
    }

    public void setWithdrawService(IWithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    public IUserService getAccountUserService() {
        return accountUserService;
    }

    public void setAccountUserService(IUserService accountUserService) {
        this.accountUserService = accountUserService;
    }

    public IResult<?> getResult() {
        return result;
    }

    public void setResult(IResult<?> result) {
        this.result = result;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getOpenAcctId() {
        return openAcctId;
    }

    public void setOpenAcctId(String openAcctId) {
        this.openAcctId = openAcctId;
    }

}
