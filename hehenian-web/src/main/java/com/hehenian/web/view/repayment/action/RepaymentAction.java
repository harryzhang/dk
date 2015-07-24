package com.hehenian.web.view.repayment.action;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.base.constant.Constants;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.IOperationLogService;
import com.hehenian.biz.common.trade.IRepaymentService;
import com.hehenian.biz.common.trade.RepayOperationType;
import com.hehenian.biz.common.trade.dataobject.InvestRepaymentWrap;
import com.hehenian.biz.common.trade.dataobject.RepaymentContext;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentFeeDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.StringUtil;
import com.hehenian.web.base.action.PageAction;
import com.hehenian.web.common.util.JSONUtils;
import com.hehenian.web.common.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;

/**
 * 这个类是还款、提前还款、待偿的操作 action
 * 
 * @author zhangyunhmf
 * 
 */
@Scope("prototype")
@Component("repaymentAction")
public class RepaymentAction extends PageAction {

    private final Logger           logger = Logger.getLogger(this.getClass());

    // 操作日志服务
    @Autowired
    protected IOperationLogService hehenianOperationLogService;

    @Autowired
    private IRepaymentService      repaymentService;

    /**
     * 修改重复请求的问题
     * 
     * @return
     * @author: zhangyunhmf
     * @throws Throwable
     * @date: 2014年12月2日下午5:17:46
     */
    public String changeInvestRepaymentId() throws Throwable {

        long repaymentId = -1;
        double stillAmount = 0;
        JSONObject json = new JSONObject();
        try {
            repaymentId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("repaymentId")), -1); // 还款ID
            if (-1 == repaymentId) {
                json.put("msg", "参数 repaymentID  无效");
                return null;
            }

            repaymentService.changeInvestRepaymentId(repaymentId);
            json.put("msg", "1");

        } catch (Exception e) {
            json.put("msg", "操作失败");
            logger.equals(e);
        } finally {
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            hehenianOperationLogService.addOperationLog("t_repayment", admin.getUserName(), 2, admin.getLastIP(),
                    stillAmount, "修复重复请求:repayment_id=" + repaymentId, 2);

            JSONUtils.printObject(json);

        }
        return null;

    }

    /**
     * 初始化手工还款页面
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年12月1日上午10:17:12
     */
    public String toManualRepayent() {
        this.initRepaymentContext();
        RepaymentContext rc = (RepaymentContext) request().getAttribute("repaymentContext");
        List<InvestRepaymentWrap> list = rc.getInvestList();
        request().setAttribute("investList", list);
        return this.SUCCESS;
    }

    /**
     * 手工触发还款
     * 
     * @return
     * @author: zhangyunhmf
     * @throws Throwable
     * @date: 2014年12月1日上午10:18:03
     */
    public String manualRepayment() throws Throwable {
        long repaymentId = -1;
        long ordId = -1;
        double stillAmount = 0;
        JSONObject json = new JSONObject();
        try {
            repaymentId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("repaymentId")), -1); // 还款ID
            ordId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("ordId")), -1);// 要回款的t_invest_repayment
            if (-1 == repaymentId || -1 == ordId) {
                json.put("msg", "参数 repaymentID 或ordId 无效");
                return null;
            }
            // id
            double fee603 = StringUtil.strToDouble(ServletUtils.FilteSqlInfusion(paramMap.get("fee603")), 0);
            double fee902 = StringUtil.strToDouble(ServletUtils.FilteSqlInfusion(paramMap.get("fee902")), 0);
            double fee901 = StringUtil.strToDouble(ServletUtils.FilteSqlInfusion(paramMap.get("fee901")), 0);
            double fee903 = StringUtil.strToDouble(ServletUtils.FilteSqlInfusion(paramMap.get("fee903")), 0);
            double realPrincipal = StringUtil.strToDouble(ServletUtils.FilteSqlInfusion(paramMap.get("realPrincipal")),
                    0);
            double realInterest = StringUtil
                    .strToDouble(ServletUtils.FilteSqlInfusion(paramMap.get("realInterest")), 0);

            stillAmount = fee603 + fee902 + fee901 + fee903 + realPrincipal + realInterest;

            String operateType = ServletUtils.FilteSqlInfusion(paramMap.get("operateType"));
            IResult<Object> result = repaymentService.manualRepayment(repaymentId, ordId, operateType, realPrincipal,
                    realInterest, fee603, fee902, fee901, fee903, this.getPath());
            if (result.isSuccess()) {
                json.put("msg", "1");
            } else {
                json.put("msg", result.getErrorMessage());
            }

        } catch (Exception e) {
            json.put("msg", "操作失败");
            logger.equals(e);
        } finally {
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);

            hehenianOperationLogService.addOperationLog("t_repayment", admin.getUserName(), 2, admin.getLastIP(),
                    stillAmount, "手动还款:t_invest_repayment.id=" + ordId, 2);


            JSONUtils.printObject(json);

        }
        return null;
    }

    /**
     * 后天还款菜单初始URL
     * 
     * @return
     */
    public String queryUserFundRepayInit() {
        paramMap.put("repayDate", DateUtil.YYYY_MM_DD.format(new Date()));
        String number = request("numberId");
        request().setAttribute("numberId", number);
        return SUCCESS;

    }

    /**
     * 后台查询还款列表。 查找用户还款资金列表信息
     * 
     * @return
     */
    public String queryUserFundRepayList() {
        String username = ServletUtils.FilteSqlInfusion(paramMap.get("username"));
        String realName = ServletUtils.FilteSqlInfusion(paramMap.get("realName"));
        String number = ServletUtils.FilteSqlInfusion(paramMap.get("number"));
        int repayStatus = StringUtil.strToInt(ServletUtils.FilteSqlInfusion(paramMap.get("repayStatus")), -1);
        String repayDate = ServletUtils.FilteSqlInfusion(paramMap.get("repayDate"));
        if (number == null || number == "") {
            number = StringUtil.strToStr(ServletUtils.FilteSqlInfusion(paramMap.get("numberId")), "");
        }

        Map parameterMap = new HashMap();
        if (StringUtils.isNotBlank(username)) {
            parameterMap.put("username", username);
        }
        if (StringUtils.isNotBlank(realName)) {
            parameterMap.put("realName", realName);
        }
        if (StringUtils.isNotBlank(number)) {
            parameterMap.put("number", number);
        }
        if (-1 != repayStatus) {
            parameterMap.put("repayStatus", repayStatus);
        }

        if (StringUtils.isNotBlank(repayDate)) {
            try {
                Date repayD = DateUtil.YYYY_MM_DD.parse(repayDate);
                parameterMap.put("repayDate", repayD);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        pageBean = repaymentService.selectRepaymentPage(parameterMap, pageBean);
        int pageNum = (int) (pageBean.getPageNum()) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);
        return SUCCESS;
    }

    /**
     * 还款操作
     * 
     * @return
     */
    public String updateRepayment() {

        JSONObject json = null;
        long borrowId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("borrowId")), -1);// 要还款的borrowid
        long repaymentId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("repaymentId")), -1L);
        double stillAmount = StringUtil.strToDouble(ServletUtils.FilteSqlInfusion(paramMap.get("stillAmount")), 0);// 本期应还总额
        String usrCustId = StringUtil.strToStr(ServletUtils.FilteSqlInfusion(paramMap.get("usrCustId")), ""); // 汇付客户号
        long userId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("userId")), -1L);
        String userName = StringUtil.strToStr(ServletUtils.FilteSqlInfusion(paramMap.get("userName")), "");
        try {
            if (repaymentId <= 0 || stillAmount <= 0 || userId <= 0 || StringUtils.isBlank(userName)
                    || StringUtils.isBlank(usrCustId)) {
                json = new JSONObject();
                json.put("msg", "非法操作");
                JSONUtils.printObject(json);
                return null;
            }
            IResult result = repaymentService.doRepay(RepayOperationType.NORMAL_REPAY.toString(), borrowId,
                    repaymentId, usrCustId, userId, userName, "", this.getPath());
            printResultToResponse(result);
            return null;
        } catch (Exception e) {
            logger.error(e);
            try {
                JSONUtils.printStr("{msg:'还款失败'}");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        } finally {
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            hehenianOperationLogService.addOperationLog("t_repayment", admin.getUserName(), 2, admin.getLastIP(),
                    stillAmount, "正常还款:repaymentId=" + repaymentId, 2);
        }
    }

    /**
     * 初始化减免费用的页面
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年11月26日上午11:04:24
     */
    public String derateFeeInit() {
        initFeeSelect();
        String derateRepaymentId = request().getParameter("repaymentId");
        request().setAttribute("repaymentId", derateRepaymentId);
        return this.SUCCESS;
    }

    /**
     * 减免费用
     * 
     * @return
     * @author: zhangyunhmf
     * @throws Throwable
     * @date: 2014年11月26日上午11:04:24
     */
    public String derateFee() throws Throwable {

        JSONObject json = new JSONObject();

        try {
            String[] amounts = request().getParameterValues("amount");
            String[] remarks = request().getParameterValues("remark");
            String[] feecodes = request().getParameterValues("feecode");
            String repaymentIdStr = request().getParameter("repaymentId");

            Long repaymentId = StringUtil.strToLong(repaymentIdStr);

            List<RepaymentFeeDo> repaymentFeeList = new ArrayList<RepaymentFeeDo>();
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);

            for (int i = 0; i < feecodes.length; i++) {
                String amountStr = amounts[i];
                String remarkStr = remarks[i];
                String feecodeStr = feecodes[i];

                if (StringUtils.isBlank(amountStr) || StringUtils.isBlank(remarkStr) || StringUtils.isBlank(feecodeStr)) {
                    continue;
                }

                double amount = StringUtil.strToDouble(amountStr);

                RepaymentFeeDo rf = new RepaymentFeeDo();
                rf.setFeeCode(feecodeStr);
                rf.setHasAmount(amount);
                rf.setRemark(remarkStr);
                rf.setRepaymentId(repaymentId);
                rf.setLastUpdateUser(admin.getId());
                repaymentFeeList.add(rf);
            }

            if (!repaymentFeeList.isEmpty()) {
                repaymentService.addRepaymentFee(repaymentFeeList);
                json.put("msg", "1");
            } else {
                json.put("msg", "参数非法，减免理由和金额不可以为空");
            }

        } catch (Exception e) {
            json.put("msg", "保存失败");
            e.printStackTrace();
        } finally {
            JSONUtils.printObject(json);
        }
        return null;
    }

    /**
     * 手工关闭还款记录
     * 
     * @return
     * @throws Throwable
     * @author: zhangyunhmf
     * @date: 2014年12月1日上午10:15:53
     */
    public String updateRepaymentStatus() throws Throwable {
        JSONObject json = new JSONObject();
        try {
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            String borrowIdStr = request().getParameter("borrowId");
            String repayPeriod = request().getParameter("repayPeriod");
            long borrowId = StringUtil.strToLong(borrowIdStr, 0);

            if (borrowId == 0) {
                json.put("msg", "参数非法");
                return null;
            }
            repaymentService.updateRepaymentStatus(borrowId, repayPeriod, admin.getId());
            json.put("msg", "1");
        } catch (Exception e) {
            json.put("msg", "保存失败");
            e.printStackTrace();
        } finally {
            JSONUtils.printObject(json);
        }
        return null;
    }

    /**
     * 提前结清的确认页面
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年11月26日上午8:34:21
     */
    public String preRepaymentConfirm() {

        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        long borrowId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("borrowId")), -1);// 要还款的borrowid
        long userId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("userId")), -1);// 还款人id
        long payId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("payId")), -1);

        if (admin == null) {
            throw new BusinessException("请先登录");
        }
        RepaymentContext rc = repaymentService.buildContext(RepayOperationType.PRE_SETTLE_REPAY.toString(), borrowId,
                payId, userId);

        // 计算一些统计值
        cacalculatel(rc);
        request().setAttribute("repaymentContext", rc);
        request().setAttribute("compAmount", rc.getCompAmont()); // 汇总代偿
        request().setAttribute("preDate", DateUtil.YYYY_MM_DD.format(new Date()));

        return this.SUCCESS;
    }

    /**
     * 提前还款操作
     * 
     * @return
     */
    public String updatePreRepayment() {

        JSONObject json = new JSONObject();
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        long borrowId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("borrowId")), -1);// 要还款的borrowid
        long outCustId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("usrCustId")), -1);// 还款人客户号,即出账账户号
        long userId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("userId")), -1);// 还款人id
        long payId = StringUtil.strToLong(ServletUtils.FilteSqlInfusion(paramMap.get("payId")), -1);

        try {

            if (borrowId < 0) {
                json.put("msg", "没有找到借款信息");
                JSONUtils.printObject(json);
                return null;
            }
            if (outCustId < 0) {
                json.put("msg", "没有找到该借款人信息");
                JSONUtils.printObject(json);
                return null;
            }
            if (userId < 0) {
                json.put("msg", "没有找到该借款人信息");
                JSONUtils.printObject(json);
                return null;
            }
            if (payId < 0) {
                json.put("msg", "没有找到该借款人信息");
                JSONUtils.printObject(json);
                return null;
            }

            if (admin == null) {
                json.put("msg", "请先登录");
                JSONUtils.printObject(json);
                return null;
            }

            // String ret =
            // fundManagementService.preRepayment(borrowId,outCustId + "",
            // userId, payId,admin);
            IResult result = repaymentService.doRepay(RepayOperationType.PRE_SETTLE_REPAY.toString(), borrowId, payId,
                    outCustId + "", userId, "", "", this.getPath());

            if (result.isSuccess()) {
                json.put("msg", "1");
            } else {
                if (null == result.getErrorMessage()) {
                    json.put("msg", "还款失败");
                } else {
                    json.put("msg", result.getErrorMessage());
                }

            }
            JSONUtils.printObject(json);
            return null;
        } catch (Exception e) {
            try {
                logger.error(e);
                json.put("msg", "还款失败");
                JSONUtils.printObject(json);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;
        } finally {
            hehenianOperationLogService.addOperationLog("t_repayment", admin.getUserName(), 2, admin.getLastIP(), 0d,
                    "提前还款: borrowId=" + borrowId, 2);
        }
    }

    /**
     * 代偿操作
     * 
     * @return
     */
    public String overduePaymentRepaySubmit() throws Exception {
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        String payId = ServletUtils.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
        String borrowIdStr = ServletUtils.FilteSqlInfusion(paramMap.get("borrowId") == null ? "" : paramMap
                .get("borrowId"));
        try {
            long repayId = StringUtil.strToLong(payId, -1L);
            long borrowId = StringUtil.strToLong(borrowIdStr, -1L);
            if (repayId < -1) {
                JSONUtils.printStr2("非法操作");
                return null;
            }

            // String message =
            // afterCreditManageService.overduePaymentRepaySubmit(payId,admin.getId(),
            // getBasePath(), borrowId, admin);
            IResult result = repaymentService.doRepay(RepayOperationType.COMP_REPAY.toString(), borrowId, repayId,
                    admin.getUsrCustId() + "", admin.getId(), admin.getUserName(), "", this.getPath());
            // 操作成功, 兼容jsp的返回
            if (result.isSuccess()) {
                JSONUtils.printStr2("操作成功");
            } else {
                JSONUtils.printStr2(result.getErrorMessage());
            }
            // printResultToResponse(result);
            return null;

        } catch (Exception e) {
            logger.error(e);
            try {
                JSONUtils.printStr2("{msg:'还款失败'}");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        } finally {
            hehenianOperationLogService.addOperationLog("t_repayment", admin.getUserName(), 2, admin.getLastIP(), 0d,
                    "代偿:repaymentId=" + payId, 2);
        }
    }

    /**
     * 初始化减免页面
     * 
     * @author: zhangyunhmf
     * @date: 2014年12月1日上午10:14:35
     */
    private void initFeeSelect() {
        Map<String, String> feeMap = new HashMap<String, String>();
        feeMap.put(Constants.FEE_CODE_CONSULT, "咨询费");
        feeMap.put(Constants.FEE_CODE_FX, "罚息");
        feeMap.put(Constants.FEE_CODE_PRE, "提前结清手续费");
        feeMap.put(Constants.FEE_CODE_SERVICE_CHARGE, "放款手续费");
        request().setAttribute("feeMap", feeMap);

        initRepaymentContext();

        RepaymentContext rc = (RepaymentContext) request().getAttribute("repaymentContext");
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("borrowId", rc.getBorrowId());
        List<RepaymentDo> repayList = repaymentService.selectRepayment(parameterMap);
        Map<Long, String> periodMap = new HashMap<Long, String>();
        if (null != repayList) {
            for (RepaymentDo repayDo : repayList) {
                periodMap.put(repayDo.getId(), repayDo.getRepayPeriod());
            }
        }
        request().setAttribute("periodMap", periodMap);
    }


    /**
     * 创建还款对象
     * 
     * @author: zhangyunhmf
     * @date: 2014年12月1日上午10:14:58
     */
    private void initRepaymentContext() {
        String repaymentIdStr = request().getParameter("repaymentId");
        long payId = StringUtil.strToLong(repaymentIdStr);

        String borrowIdStr = request().getParameter("borrowId");
        long borrowId = StringUtil.strToLong(borrowIdStr);

        String userIdStr = request().getParameter("userId");
        long userId = StringUtil.strToLong(userIdStr);
        RepaymentContext rc = repaymentService.buildContext(RepayOperationType.NORMAL_REPAY.name(), borrowId, payId,
                userId);
        request().setAttribute("repaymentContext", rc);
        // 计算一些统计值
        cacalculatel(rc);
    }

    /**
     * 计算页面展示的统计数据
     * 
     * @param rc
     * @author: zhangyunhmf
     * @date: 2014年12月1日上午10:15:18
     */
    private void cacalculatel(RepaymentContext rc) {

        // 因为有可能减免， 已收等情况存在，所以不能用还款上的费用金额
        double totalAmount = 0;
        RepaymentFeeDo repaymentFee = rc.getRepaymentDo().getRepaymentFee(Constants.FEE_CODE_CONSULT);
        if (null != repaymentFee) {
            request().setAttribute("feeConsult", repaymentFee.getRemainAmount());
            totalAmount = CalculateUtils.add(totalAmount, repaymentFee.getRemainAmount());
        }

        repaymentFee = rc.getRepaymentDo().getRepaymentFee(Constants.FEE_CODE_FX);
        if (null != repaymentFee) {
            request().setAttribute("feeFx", repaymentFee.getRemainAmount());
            totalAmount = CalculateUtils.add(totalAmount, repaymentFee.getRemainAmount());
        }

        repaymentFee = rc.getRepaymentDo().getRepaymentFee(Constants.FEE_CODE_PRE);
        if (null != repaymentFee) {
            request().setAttribute("feePre", repaymentFee.getRemainAmount());
            totalAmount = CalculateUtils.add(totalAmount, repaymentFee.getRemainAmount());
        }

        repaymentFee = rc.getRepaymentDo().getRepaymentFee(Constants.FEE_CODE_SERVICE_CHARGE);
        if (null != repaymentFee) {
            request().setAttribute("feeServiceCharge", repaymentFee.getRemainAmount());
            totalAmount = CalculateUtils.add(totalAmount, repaymentFee.getRemainAmount());
        }

        double totalPrincipal = rc.getInvestListTotalPrincipal();
        double totalInterest = rc.getInvestListTotalInterest();

        request().setAttribute("totalPrincipal", totalPrincipal); // 汇总本金
        request().setAttribute("totalInterest", totalInterest); // 汇总利息

        totalAmount = CalculateUtils.add(totalAmount, totalInterest);
        totalAmount = CalculateUtils.add(totalAmount, totalPrincipal);
        request().setAttribute("totalAmount", totalAmount); // 共计

    }

}
