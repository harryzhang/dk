/**  
 * @Project: hehenian-web
 * @Package com.hehenian.web.view.trade.action
 * @Title: AssignmentDebtAction.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月26日 上午11:09:24
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.web.view.trade.action;

import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.trade.IAssignmentDebtService;
import com.hehenian.biz.common.trade.IAuctionDebtService;
import com.hehenian.biz.common.trade.IParameterLogService;
import com.hehenian.biz.common.trade.dataobject.AssignmentDebtDo;
import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo.AuctionStatus;
import com.hehenian.biz.common.trade.dataobject.ParameterLogDo.ParameterType;
import com.hehenian.biz.common.util.StringUtil;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.web.common.util.ServletUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author: liuzgmf
 * @date 2014年9月26日 上午11:09:24
 */
@Scope("prototype")
@Controller("newAssignmentDebtAction")
public class NewAssignmentDebtAction extends ActionSupport implements ServletRequestAware, SessionAware {
    private static final long      serialVersionUID = 1L;
    @Autowired
    private IAssignmentDebtService newAssignmentDebtService;
    @Autowired
    private IAuctionDebtService    newAuctionDebtService;
    @Autowired
    private IParameterLogService   parameterLogService;
    private HttpServletRequest     request;
    private Map<String, Object>    session;

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * 债权转让申请
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日上午11:10:37
     */
    public String addAssignmentDebt() {
        AssignmentDebtDo assignmentDebtDo = new AssignmentDebtDo();
        assignmentDebtDo.setDebtSum(StringUtil.strToDouble(request.getParameter("paramMap.debtSum"), 0.00));
        String auctionBasePrice = request.getParameter("paramMap.auctionBasePrice");
        assignmentDebtDo.setAuctionBasePrice(StringUtil.strToDouble(auctionBasePrice, 0.00));
        assignmentDebtDo.setDebtLimit(StringUtil.strToInt(request.getParameter("paramMap.debtLimit")));
        assignmentDebtDo.setAuctionMode(StringUtil.strToInt(request.getParameter("paramMap.auctionMode")));
        assignmentDebtDo.setViewCount(StringUtil.strToInt(request.getParameter("paramMap.viewCount")));
        assignmentDebtDo.setBorrowId(StringUtil.strToLong(request.getParameter("paramMap.borrowId")));

        AccountUserDo user = (AccountUserDo) session.get(WebConstants.SESSION_USER);
        assignmentDebtDo.setAlienatorId(user.getId());// 转让人
        assignmentDebtDo.setAuctionerId(StringUtil.strToLong(request.getParameter("paramMap.auctionerId")));
        String auctionHighPrice = request.getParameter("paramMap.auctionHighPrice");
        assignmentDebtDo.setAuctionHighPrice(StringUtil.strToDouble(auctionHighPrice, 0.00));
        String manageFee = request.getParameter("paramMap.manageFee");
        assignmentDebtDo.setManageFee(StringUtil.strToDouble(manageFee, 0.00));
        assignmentDebtDo.setInvestId(StringUtil.strToLong(request.getParameter("paramMap.investId")));

        IResult<?> result = newAssignmentDebtService.addAssignmentDebt(assignmentDebtDo);
        if (result.isSuccess()) {
            ServletUtils.write("1");
        } else {
            ServletUtils.write(result.getErrorMessage());
        }
        return null;
    }

    /**
     * 债权转让审核审核
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日上午11:10:37
     */
    public String updateDebtAudit() {
        AssignmentDebtDo assignmentDebtDo = new AssignmentDebtDo();
        assignmentDebtDo.setDebtStatus(StringUtil.strToInt(request.getParameter("paramMap.status")));
        assignmentDebtDo.setId(StringUtil.strToLong(request.getParameter("paramMap.id")));
        assignmentDebtDo.setInvestId(StringUtil.strToLong(request.getParameter("paramMap.investId")));
        assignmentDebtDo.setDetails(request.getParameter("paramMap.remark"));
        try {
            String publishTime = request.getParameter("paramMap.time");
            if (StringUtils.isNotBlank(publishTime)) {
                assignmentDebtDo.setPublishTime(DateUtils
                        .parseDate(publishTime, new String[] { "yyyy-MM-dd HH:mm:ss" }));
            } else {
                assignmentDebtDo.setPublishTime(new Date());
            }
        } catch (ParseException ignore) {
            assignmentDebtDo.setPublishTime(new Date());
        }
        assignmentDebtDo.setAuctionDays(3);// 债权转让期为3天
        IResult<?> result = newAssignmentDebtService.updateDebtAudit(assignmentDebtDo);
        if (result.isSuccess()) {
            ServletUtils.write("1");
        } else {
            ServletUtils.write("2");
        }
        return null;
    }

    /**
     * 债权转让申请购买
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日上午11:10:37
     */
    public String addPurchaseDebt() {
        Long debtId = Long.parseLong(request.getParameter("paramMap.id"));
        Double auctionPrice = Double.parseDouble(request.getParameter("paramMap.auctionPrice"));
        AccountUserDo user = (AccountUserDo) session.get(WebConstants.SESSION_USER);
        IResult<?> result = newAssignmentDebtService.addPurchaseDebt(user.getId(), debtId, auctionPrice);
        // 发送请求到汇付
        if (result != null && result.isSuccess()) {
            String htmlText = (String) result.getModel();
            ServletUtils.write(htmlText);
            return null;
        } else {
            ServletUtils.write(result.getErrorMessage());
            return null;
        }
    }

    /**
     * 债权转让汇付天下回调（前台回调 ）
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日上午11:10:37
     */
    public String updatePurchaseDebt() throws Exception {
        ServletUtils.logRequestParameters();// 打印参数日志
        parameterLogService.addParameterLog(ServletUtils.getRequestParameters(), ParameterType.RESP);
        String respCode = request.getParameter("RespCode");
        String ordId = request.getParameter("OrdId");
        if (!WebConstants.RESP_CODE_SUCCESS.equals(respCode)) {
            newAssignmentDebtService.updateAuctionStatus(ordId, AuctionStatus.FAILURE, AuctionStatus.PROCESSING);// 设置状态为提现失败
            request.setAttribute(WebConstants.MESSAGE_KEY, URLDecoder.decode(request.getParameter("RespDesc"), "UTF-8")
                    + "，请联系客服!");
            return ERROR;
        }

        String creditDealAmt = request.getParameter("CreditDealAmt");
        newAssignmentDebtService.updatePurchaseDebt(StringUtil.strToLong(ordId), StringUtil.strToDouble(creditDealAmt));
        request.setAttribute(WebConstants.MESSAGE_KEY, "债权转让成功!");
        return SUCCESS;
    }

    /**
     * 债权转让汇付天下回调（后台）
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日上午11:10:37
     */
    public String updatePurchaseDebtBg() throws Exception {
        ServletUtils.logRequestParameters();// 打印参数日志
        parameterLogService.addParameterLog(ServletUtils.getRequestParameters(), ParameterType.RESP);
        String respCode = request.getParameter("RespCode");
        String ordId = request.getParameter("OrdId");
        if (!WebConstants.RESP_CODE_SUCCESS.equals(respCode)) {
            newAssignmentDebtService.updateAuctionStatus(ordId, AuctionStatus.FAILURE, AuctionStatus.PROCESSING);// 设置状态为提现失败
            ServletUtils.write("RECV_ORD_ID_" + ordId);// 成功应答，标示订单已成功处理
            return null;
        }

        String creditDealAmt = request.getParameter("CreditDealAmt");
        IResult<?> result = newAssignmentDebtService.updatePurchaseDebt(StringUtil.strToLong(ordId),
                StringUtil.strToDouble(creditDealAmt));
        if (result.isSuccess()) {
            ServletUtils.write("RECV_ORD_ID_" + ordId);// 成功应答，标示订单已成功处理
            return null;
        }
        return null;
    }

    /**
     * 商户扣款对账
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日下午1:58:39
     */
    public String trfReconciliation() {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String pageNum = request.getParameter("PageNum");

        Map<String, Object> params = newAssignmentDebtService.trfReconciliation(beginDate, endDate, pageNum);
        request.setAttribute("params", params);
        return SUCCESS;
    }

    /**
     * 投标对账(放款和还款对账)
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日下午1:57:39
     */
    public String reconciliation() {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String pageNum = request.getParameter("PageNum");
        String queryTransType = request.getParameter("QueryTransType");

        Map<String, Object> params = newAssignmentDebtService.reconciliation(beginDate, endDate, pageNum,
                queryTransType);
        request.setAttribute("params", params);
        return SUCCESS;
    }

    /**
     * 充值对账
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日下午1:58:12
     */
    public String saveReconciliation() {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String pageNum = request.getParameter("PageNum");

        Map<String, Object> params = newAssignmentDebtService.saveReconciliation(beginDate, endDate, pageNum);
        request.setAttribute("params", params);
        return SUCCESS;
    }

    /**
     * 取现对账
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日下午1:58:23
     */
    public String cashReconciliation() {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String pageNum = request.getParameter("PageNum");

        Map<String, Object> params = newAssignmentDebtService.cashReconciliation(beginDate, endDate, pageNum);
        request.setAttribute("params", params);
        return SUCCESS;
    }

    /**
     * 账户明细查询
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年10月13日下午1:58:23
     */
    public String queryAcctDetails() {
        String userId = request.getParameter("userId");
        Map<String, Object> params = newAssignmentDebtService.queryAcctDetails(Long.parseLong(userId));

        ServletUtils.write((String) params.get("htmlText"));
        return null;
    }

}
