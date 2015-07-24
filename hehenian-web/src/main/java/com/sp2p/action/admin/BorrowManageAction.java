package com.sp2p.action.admin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.Vector;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.BorrowType;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.BorrowService;
import com.sp2p.service.DataApproveService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SendMessageService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.service.admin.PlatformCostService;
import com.sp2p.service.admin.ShoveBorrowTypeService;
import com.sp2p.task.TaskTimer;
import com.sp2p.util.ChinaPnRInterface;

/**
 * @ClassName: FrontMyFinanceAction.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:16:33
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的借款控制层
 */
public class BorrowManageAction extends BaseFrontAction {

    public static Log                 log              = LogFactory.getLog(BorrowManageAction.class);
    private File                      userFile;
    private static final long         serialVersionUID = 1L;

    private BorrowManageService       borrowManageService;

    // ---add by houli
    private DataApproveService        dataApproveService;

    // ---add by C_J
    private ShoveBorrowTypeService    shoveBorrowTypeService;
    private SendMessageService        sendMessageService;
    private BorrowService             borrowService;

    private FinanceService            financeService;

    private SelectedService           selectedService;
    private PlatformCostService       platformCostService;
    private UserService               userService;

    private Map<String, String>       borrowMFADetail;

    private Map<String, String>       borrowMTenderInDetail;

    private Map<String, String>       borrowMFullScaleDetail;

    private Map<String, String>       borrowMFlowMarkDetail;

    private Map<String, String>       borrowMAllDetail;

    private Map<String, String>       borrowCirculationDetail;

    private List<Map<String, Object>> cirList;

    private Object                    borrowId         = "";

    // 下拉列表
    private List<Map<String, Object>> borrowPurposeList;
    private List<Map<String, Object>> borrowDeadlineList;
    private List<Map<String, Object>> borrowAmountList;
    private List<Map<String, Object>> borrowRaiseTermList;
    private List<Map<String, Object>> sysImageList;
    private List<Map<String, Object>> borrowTurnlineList;
    private List<Map<String, Object>> borrowerList;

    public SendMessageService getSendMessageService() {
        return sendMessageService;
    }

    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    /**
     * @MethodName: borrowManageFistAuditInit
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-11 上午09:54:00
     * @Return:
     * @Descb: 后台借款管理初审初始化
     * @Throws:
     */
    public String borrowManageFistAuditInit() throws SQLException, DataException {
        return "success";
    }

    /**
     * @MethodName: borrowManageFistAuditInit
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-10 下午10:58:57
     * @Return:
     * @Descb: 后台借款管理初审列表
     * @Throws:
     */
    @SuppressWarnings("unchecked")
    public String borrowManageFistAuditList() throws SQLException, DataException {
        String pageNums = SqlInfusion.FilteSqlInfusion((String) (request().getParameter("curPage") == null ? ""
                : request().getParameter("curPage")));
        if (StringUtils.isNotBlank(pageNums)) {
            pageBean.setPageNum(pageNums);
        }
        pageBean.setPageSize(IConstants.PAGE_SIZE_10);
        String userName = SqlInfusion
                .FilteSqlInfusion(paramMap.get("userName") == null ? "" : paramMap.get("userName"));
        String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay") == null ? "" : paramMap
                .get("borrowWay"));
        int borrowWayInt = Convert.strToInt(borrowWay, -1);

        // 暂时性的修改 用于修改显示所有初审中的借款
        // 不用做判断处理~
        // borrowManageService.queryBorrowAllByCondition(userName, borrowWayInt,
        // 1,pageBean);
        // 做了判断的处理
        borrowManageService.queryBorrowFistAuditByCondition(userName, borrowWayInt, pageBean);

        Map<String, String> repaymentMap = borrowManageService.queryBorrowTotalFistAuditDetail();
        request().setAttribute("repaymentMap", repaymentMap);
        // 统计当前页应收款
        double fistAuditAmount = 0;
        List<Map<String, Object>> payList = pageBean.getPage();
        if (payList != null) {
            for (Map<String, Object> map : payList) {
                fistAuditAmount = fistAuditAmount + Convert.strToDouble(map.get("borrowAmount") + "", 0);
            }
        }
        DecimalFormat fmt = new DecimalFormat("0.00");
        request().setAttribute("fistAuditAmount", fmt.format(fistAuditAmount));
        int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);

        return "success";
    }

    @SuppressWarnings("unchecked")
    public String borrowManageWaitingAuditList() throws SQLException, DataException {
        String pageNums = SqlInfusion.FilteSqlInfusion((String) (request().getParameter("curPage") == null ? ""
                : request().getParameter("curPage")));
        if (StringUtils.isNotBlank(pageNums)) {
            pageBean.setPageNum(pageNums);
        }
        pageBean.setPageSize(IConstants.PAGE_SIZE_10);
        String userName = SqlInfusion
                .FilteSqlInfusion(paramMap.get("userName") == null ? "" : paramMap.get("userName"));
        String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay") == null ? "" : paramMap
                .get("borrowWay"));
        int borrowWayInt = Convert.strToInt(borrowWay, -1);
        borrowManageService.queryBorrowWaitingAuditByCondition(userName, borrowWayInt, pageBean);

        Map<String, String> waitTotalAmount = borrowManageService.queryBorrowTotalWait();
        request().setAttribute("waitTotalAmount", waitTotalAmount);
        // 统计当前页等待
        double waitingAuditAmount = 0;
        List<Map<String, Object>> payList = pageBean.getPage();
        if (payList != null) {
            for (Map<String, Object> map : payList) {
                waitingAuditAmount = waitingAuditAmount + Convert.strToDouble(map.get("borrowAmount") + "", 0);
            }
        }
        DecimalFormat fmt = new DecimalFormat("0.00");
        request().setAttribute("waitingAuditAmount", fmt.format(waitingAuditAmount));
        int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);

        return "success";
    }

    /**
     * @throws Exception
     * @MethodName: borrowManageFistAuditDetail
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-10 下午11:02:22
     * @Return:
     * @Descb: 后台借款管理中的借款详情
     * @Throws:
     */
    public String borrowManageFistAuditDetail() throws Exception {
        String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id") == null ? "" : request().getParameter(
                "id"));
        long idLong = Convert.strToLong(id, -1);
        Map<String, String> TypeLogMap = null;
        if (borrowMFADetail == null) {
            // 初审中的借款详情
            borrowMFADetail = borrowManageService.queryBorrowFistAuditDetailById(idLong);
            String nid_log = borrowMFADetail.get("nid_log");
            if (StringUtils.isNotBlank(nid_log)) {
                TypeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
                int stauts = Convert.strToInt(TypeLogMap.get("subscribe_status"), -1);
                request().setAttribute("subscribes", stauts);

            }
        }
        return "success";
    }

    /**
     * @throws DataException
     * @throws SQLException
     * @throws IOException
     * @throws DataException
     * @throws IOException
     * @MethodName: updateBorrowF
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-11 下午03:58:28
     * @Return:
     * @Descb: 审核借款中的初审记录
     * @Throws:
     */
    public String updateBorrowF() throws SQLException, DataException, IOException {
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        JSONObject obj = new JSONObject();
        String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
        long idLong = Convert.strToLong(id, -1);
        String reciver = SqlInfusion.FilteSqlInfusion(paramMap.get("reciver"));
        long reciverLong = Convert.strToLong(reciver, -1);
        String status = SqlInfusion.FilteSqlInfusion(paramMap.get("status"));
        int statusLong = Convert.strToInt(status, -1);
        String msg = SqlInfusion.FilteSqlInfusion(paramMap.get("msg"));
        String auditOpinion = SqlInfusion.FilteSqlInfusion(paramMap.get("auditOpinion"));
        long result = -1;

        try {
            result = borrowManageService.updateBorrowFistAuditStatus(idLong, reciverLong, statusLong, msg,
                    auditOpinion, admin.getId(), getBasePath());
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        if (result <= 0) {
            // 操作失败提示
            obj.put("msg", IConstants.ACTION_FAILURE);
            JSONUtils.printObject(obj);
            return null;
        }
        // 前台跳转地址
        obj.put("msg", "1");
        JSONUtils.printObject(obj);
        return null;
    }

    /**
     * @MethodName: borrowManageTenderInInit
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-13 上午11:36:01
     * @Return:
     * @Descb: 后台借款管理招标中初始化
     * @Throws:
     */
    public String borrowManageTenderInInit() throws SQLException, DataException {
        return "success";
    }

    /**
     * @MethodName: borrowManageTenderInList
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-13 上午11:36:32
     * @Return:
     * @Descb: 后台借款招标中的记录
     * @Throws:
     */
    @SuppressWarnings("unchecked")
    public String borrowManageTenderInList() throws SQLException, DataException {
        String pageNums = SqlInfusion.FilteSqlInfusion((String) (request().getParameter("curPage") == null ? ""
                : request().getParameter("curPage")));
        if (StringUtils.isNotBlank(pageNums)) {
            pageBean.setPageNum(pageNums);
        }
        pageBean.setPageSize(IConstants.PAGE_SIZE_10);
        String userName = SqlInfusion
                .FilteSqlInfusion(paramMap.get("userName") == null ? "" : paramMap.get("userName"));
        String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay") == null ? "" : paramMap
                .get("borrowWay"));
        int borrowWayInt = Convert.strToInt(borrowWay, -1);
        borrowManageService.queryBorrowTenderInByCondition(userName, borrowWayInt, pageBean);

        Map<String, String> repaymentMap = borrowManageService.queryBorrowTotalTenderDetail();
        request().setAttribute("repaymentMap", repaymentMap);
        // 统计当前页等待
        double tenderAmount = 0;
        List<Map<String, Object>> payList = pageBean.getPage();
        if (payList != null) {
            for (Map<String, Object> map : payList) {
                tenderAmount = tenderAmount + Convert.strToDouble(map.get("borrowAmount") + "", 0);
            }
        }
        DecimalFormat fmt = new DecimalFormat("0.00");
        request().setAttribute("tenderAmount", fmt.format(tenderAmount));
        int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);
        return "success";
    }

    /**
     * @throws Exception
     * @MethodName: borrowManageFistAuditDetail
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-10 下午11:02:22
     * @Return:
     * @Descb: 后台借款管理招标中的借款详情
     * @Throws:
     */
    public String borrowManageTenderInDetail() throws Exception {
        return "success";
    }

    /**
     * @throws IOException
     * @throws DataException
     * @MethodName: updateBorrowF
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-11 下午03:58:28
     * @Return:
     * @Descb: 审核借款中的招标中记录
     * @Throws:
     */
    public String updateBorrowTenderIn() throws SQLException, IOException, DataException {
        JSONObject obj = new JSONObject();
        String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
        long idLong = Convert.strToLong(id, -1);
        String auditOpinion = paramMap.get("auditOpinion");
        long result = -1;
        result = borrowManageService.updateBorrowTenderInStatus(idLong, auditOpinion);
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        if (result <= 0) {
            obj.put("msg", IConstants.ACTION_FAILURE);
            JSONUtils.printObject(obj);
            operationLogService.addOperationLog("t_borrow", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
                    0, "审核借款中的招标中记录，操作失败", 2);
            return null;
        }
        // 前台跳转地址
        obj.put("msg", "1");
        JSONUtils.printObject(obj);
        operationLogService.addOperationLog("t_borrow", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
                "审核借款中的招标中记录，操作成功", 2);
        return null;
    }

    public String reBackBorrowFistAudit() throws IOException, SQLException {
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        JSONObject obj = new JSONObject();
        long result = -1;
        String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
        long idLong = Convert.strToLong(id, -1);
        // 调用撤消服务
        result = borrowManageService.reBackBorrowFistAudit(idLong, admin.getId(), getBasePath(), "", "");

        if (result <= 0) {
            // 操作失败
            obj.put("msg", IConstants.ACTION_FAILURE);
            JSONUtils.printObject(obj);
            return null;
        }
        // 操作成功
        obj.put("msg", "1");
        JSONUtils.printObject(obj);
        return null;
    }

    /**
     * @throws DataException
     * @throws SQLException
     * @throws IOException
     * @MethodName: reBackBorrowTenderIn
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-13 下午04:00:42
     * @Return:
     * @Descb: 撤消招标中的借款
     * @Throws:
     */
    public String reBackBorrowTenderIn() throws Exception {
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        JSONObject obj = new JSONObject();
        long result = -1;
        String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id") == null ? "" : paramMap.get("id"));
        long idLong = Convert.strToLong(id, -1);
        // 调用撤消服务
        result = borrowManageService.reBackBorrow(idLong, admin.getId(), getBasePath());
        if (result < 0) {
            // 操作失败
            obj.put("msg", IConstants.ACTION_FAILURE);
            JSONUtils.printObject(obj);
            return null;
        }
        // 操作成功
        obj.put("msg", "1");
        JSONUtils.printObject(obj);
        return null;
    }

    /**
     * @MethodName: borrowManageFullScaleInit
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-13 上午11:36:01
     * @Return:
     * @Descb: 后台借款管理满标初始化
     * @Throws:
     */
    public String borrowManageFullScaleInit() throws SQLException, DataException {
        return "success";
    }

    /**
     * @MethodName: borrowManageFullScaleList
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-13 上午11:36:32
     * @Return:
     * @Descb: 后台借款满标的记录
     * @Throws:
     */
    @SuppressWarnings("unchecked")
    public String borrowManageFullScaleList() throws SQLException, DataException {
        String pageNums = (String) (request().getParameter("curPage") == null ? "" : SqlInfusion
                .FilteSqlInfusion(request().getParameter("curPage")));
        if (StringUtils.isNotBlank(pageNums)) {
            pageBean.setPageNum(pageNums);
        }
        pageBean.setPageSize(IConstants.PAGE_SIZE_10);
        String userName = paramMap.get("userName") == null ? "" : SqlInfusion
                .FilteSqlInfusion(paramMap.get("userName"));
        String borrowWay = paramMap.get("borrowWay") == null ? "" : SqlInfusion.FilteSqlInfusion(paramMap
                .get("borrowWay"));
        int borrowWayInt = Convert.strToInt(borrowWay, -1);
        borrowManageService.queryBorrowFullScaleByCondition(userName, borrowWayInt, pageBean);

        Map<String, String> repaymentMap = borrowManageService.queryBorrowTotalFullScaleDetail();
        request().setAttribute("repaymentMap", repaymentMap);
        // 统计当前页应收款
        double fullScaleAmount = 0;
        List<Map<String, Object>> payList = pageBean.getPage();
        if (payList != null) {
            for (Map<String, Object> map : payList) {
                fullScaleAmount = fullScaleAmount + Convert.strToDouble(map.get("borrowAmount") + "", 0);
            }
        }
        DecimalFormat fmt = new DecimalFormat("0.##");
        request().setAttribute("fistAuditAmount", fmt.format(fullScaleAmount));
        int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);
        return "success";
    }

    /**
     * @throws Exception
     * @MethodName: borrowManageFistAuditDetail
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-10 下午11:02:22
     * @Return:
     * @Descb: 后台借款管理满标的借款详情
     * @Throws:
     */
    public String borrowManageFullScaleDetail() throws Exception {
        String id = request("id") == null ? "" : SqlInfusion.FilteSqlInfusion(request("id"));
        long idLong = Convert.strToLong(id, -1);
        Map<String, String> fullScaleDetail = new HashMap<String, String>();
        // 满标的借款详情
        fullScaleDetail = borrowManageService.queryBorrowFullScaleDetailById(idLong);
        // String nid_log = fullScaleDetail.get("nid_log");
        // if (StringUtils.isNotBlank(nid_log)) {
        // TypeLogMap =
        // shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
        // int stauts = Convert.strToInt(TypeLogMap.get("subscribe_status"),
        // -1);
        // request().setAttribute("subscribes", stauts);
        // }
        // ---add by houli 屏蔽链接
        if (fullScaleDetail != null) {
            String mailContent = fullScaleDetail.get("mailContent");
            String newStr = changeStr2Str(mailContent);
            fullScaleDetail.put("mailContent", newStr);
        }
        request().setAttribute("fullScaleDetail", fullScaleDetail);
        // ---------end
        return "success";
    }

    /**
     * @throws Exception
     * @MethodName: updateBorrowF
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-11 下午03:58:28
     * @Return:
     * @Descb: 审核借款中的满标记录
     * @desc 投资人放款给借款人，同时通过分账串商户可以收取费用等。 后台方式，需在页面上打印 RECV_ORD_ID_ 和 OrdId
     * @Throws:
     */
    public String updateBorrowFullScale() throws Exception {
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        JSONObject obj = new JSONObject();
        String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
        long idLong = Convert.strToLong(id, -1);
        String status = SqlInfusion.FilteSqlInfusion(paramMap.get("status"));
        long statusLong = Convert.strToLong(status, -1);
        String auditOpinion = SqlInfusion.FilteSqlInfusion(paramMap.get("auditOpinion"));
        String isDefault = "N";
        /*
         * SqlInfusion.FilteSqlInfusion(paramMap.get("isDefault")); if
         * (StringUtils.isBlank(isDefault)) { isDefault = "N"; } else {
         * isDefault = "Y"; }
         */
        Map<String, String> retMap = borrowManageService.updateBorrowFullScaleStatus(idLong, statusLong, auditOpinion,
                admin.getId(), getBasePath(), isDefault);
        long retVal = -1;
        retVal = Convert.strToLong(retMap.get("ret") + "", -1);
        request().setAttribute("borrowStatus", retMap.get("borrowStatus"));
        session().removeAttribute("randomCode");
        if (retVal <= 0) {
            obj.put("msg", retMap.get("ret_desc"));
            JSONUtils.printObject(obj);
            return null;
        } else {
            obj.put("msg", "1");
            JSONUtils.printObject(obj);
            return null;
        }
    }

    /**
     * @MethodName: borrowManageFlowMarkInit
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-13 上午11:36:01
     * @Return:
     * @Descb: 后台借款管理流标初始化
     * @Throws:
     */
    public String borrowManageFlowMarkInit() throws SQLException, DataException {
        // String OrdId = paramMap.get("id");
        // Map<String, String> fullMap =
        // borrowManageService.queryBorrowFullList(Convert.strToLong(OrdId,
        // -1));
        // chinapnt.loans(OrdId, OutCustId, TransAmt, Fee, SubOrdId, SubOrdDate,
        // InCustId, DivDetails, IsDefault);
        return "success";
    }

    /**
     * @MethodName: borrowManageFlowMarkList
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-13 上午11:36:32
     * @Return:
     * @Descb: 后台借款流标的记录
     * @Throws:
     */
    @SuppressWarnings("unchecked")
    public String borrowManageFlowMarkList() throws SQLException, DataException {
        String pageNums = SqlInfusion.FilteSqlInfusion((String) (request().getParameter("curPage") == null ? ""
                : request().getParameter("curPage")));
        if (StringUtils.isNotBlank(pageNums)) {
            pageBean.setPageNum(pageNums);
        }
        pageBean.setPageSize(IConstants.PAGE_SIZE_10);
        String userName = SqlInfusion
                .FilteSqlInfusion(paramMap.get("userName") == null ? "" : paramMap.get("userName"));
        String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay") == null ? "" : paramMap
                .get("borrowWay"));
        int borrowWayInt = Convert.strToInt(borrowWay, -1);
        borrowManageService.queryBorrowFlowMarkByCondition(userName, borrowWayInt, pageBean);
        Map<String, String> repaymentMap = borrowManageService.queryBorrowFlowMarkDetail();
        request().setAttribute("repaymentMap", repaymentMap);
        // 统计当前页应收款
        double flowmarkAmount = 0;
        List<Map<String, Object>> payList = pageBean.getPage();
        if (payList != null) {
            for (Map<String, Object> map : payList) {
                flowmarkAmount = flowmarkAmount + Convert.strToDouble(map.get("borrowAmount") + "", 0);
            }
        }
        DecimalFormat fmt = new DecimalFormat("0.00");
        request().setAttribute("flowmarkAmount", fmt.format(flowmarkAmount));
        int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);

        return "success";
    }

    /**
     * @throws Exception
     * @MethodName: borrowManageFistAuditDetail
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-10 下午11:02:22
     * @Return:
     * @Descb: 后台借款管理流标的借款详情
     * @Throws:
     */
    public String borrowManageFlowMarkDetail() throws Exception {
        return "success";
    }

    /**
     * @MethodName: borrowManageAllInit
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-13 上午11:36:01
     * @Return:
     * @Descb: 后台借款管理初始化
     * @Throws:
     */
    public String borrowManageAllInit() throws SQLException, DataException {
        return "success";
    }

    /**
     * @MethodName: borrowManageAllList
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-13 上午11:36:32
     * @Return:
     * @Descb: 后台借款的记录
     * @Throws:
     */
    @SuppressWarnings("unchecked")
    public String borrowManageAllList() throws SQLException, DataException {
        pageBean.setPageSize(IConstants.PAGE_SIZE_10);
        String userName = SqlInfusion
                .FilteSqlInfusion(paramMap.get("userName") == null ? "" : paramMap.get("userName"));
        String borrowStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowStatus") == null ? "" : paramMap
                .get("borrowStatus"));
        int borrowStatusInt = Convert.strToInt(borrowStatus, -1);
        String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay") == null ? "" : paramMap
                .get("borrowWay"));
        int borrowWayInt = Convert.strToInt(borrowWay, -1);
        borrowManageService.queryBorrowAllByCondition(userName, borrowWayInt, borrowStatusInt, pageBean);
        // ----add by houli 对等待资料的借款进行标记
        List<Map<String, Object>> lists = borrowManageService.queryAllWaitingBorrow();
        Vector<String> ids = new Vector<String>();
        if (lists != null && lists.size() > 0) {
            for (Map<String, Object> map : lists) {
                ids.add(map.get("id").toString());
            }
        }
        List<Map<String, Object>> lls = pageBean.getPage();
        if (lls != null && lls.size() > 0) {
            for (Map<String, Object> map : lls) {
                if (ids.contains(map.get("id").toString())) {
                    map.put("flag", "0");
                } else {
                    map.put("flag", "1");
                }
            }
        }
        int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);
        return "success";
    }

    // 校验提交借款参数
    public boolean isValidate(double amountDouble, String excitationType, double sumRateDouble, double annualRateDouble)
            throws SQLException, DataException {
        // 获取借款的范围
        Map<String, String> tempBorrwBidMessage = new HashMap<String, String>();
        tempBorrwBidMessage = shoveBorrowTypeService.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
        // 取得按借款金额的比例进行奖励
        double accountfirst = Convert.strToDouble(tempBorrwBidMessage.get("award_account_first") + "", 0);
        double accountend = Convert.strToDouble(tempBorrwBidMessage.get("award_account_end") + "", 0);
        if (StringUtils.isNotBlank(excitationType)) {
            // 按借款金额比例奖励
            if (StringUtils.isNumericSpace(excitationType) && "2".equals(excitationType)) {
                if (sumRateDouble < accountfirst || sumRateDouble > accountend) {
                    this.addFieldError("paramMap['sum']", "固定总额奖励填写不正确");
                    return false;
                }
            }
        }
        // 如果选择金额的话，则按此奖励的金额范围
        double scalefirst = Convert.strToDouble(tempBorrwBidMessage.get("award_scale_first") + "", 0);
        double scaleend = Convert.strToDouble(tempBorrwBidMessage.get("award_scale_end") + "", 0);
        if (StringUtils.isNotBlank(excitationType)) {
            // 按借款金额比例奖励
            if (StringUtils.isNumericSpace(excitationType) && "3".equals(excitationType)) {
                if (sumRateDouble < scalefirst || sumRateDouble > scaleend) {
                    this.addFieldError("paramMap['sumRate']", "奖励比例填写不正确");
                    return false;
                }
            }
        }
        // 借款额度
        double borrowMoneyfirst = Convert.strToDouble(tempBorrwBidMessage.get("amount_first") + "", 0);
        double borrowMoneyend = Convert.strToDouble(tempBorrwBidMessage.get("amount_end") + "", 0);
        if (borrowMoneyfirst > amountDouble || borrowMoneyend < amountDouble) {
            this.addFieldError("paramMap['amount']", "输入的借款总额不正确");
            return false;
        }
        // 借款额度倍数
        double accountmultiple = Convert.strToDouble(tempBorrwBidMessage.get("account_multiple") + "", -1);
        if (accountmultiple != 0) {
            if (amountDouble % accountmultiple != 0) {
                this.addFieldError("paramMap['amount']", "输入的借款总额的倍数不正确");
                return false;
            }
        }
        // 年利率
        double aprfirst = Convert.strToDouble(tempBorrwBidMessage.get("apr_first") + "", 0);
        double aprend = Convert.strToDouble(tempBorrwBidMessage.get("apr_end") + "", 0);
        if (aprfirst > annualRateDouble || aprend < annualRateDouble) {
            this.addFieldError("paramMap['annualRate']", "输入的年利率不正确");
            return false;
        }
        return true;
    }

    /**
     * @MethodName: circulationBorrowInit
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-5-20 上午11:33:18
     * @Return:
     * @Descb: 流转标借款初始化
     * @Throws:
     */
    public String circulationBorrowInit() throws SQLException, DataException {
        return "success";
    }

    /**
     * @throws DataException
     * @throws SQLException
     * @MethodName: circulationBorrowList
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-5-20 上午11:35:50
     * @Return:
     * @Descb: 流转标借款
     * @Throws:
     */
    public String circulationBorrowList() throws SQLException, DataException {
        String userName = SqlInfusion
                .FilteSqlInfusion(paramMap.get("userName") == null ? "" : paramMap.get("userName"));
        String undertaker = SqlInfusion.FilteSqlInfusion(paramMap.get("undertaker") == null ? "" : paramMap
                .get("undertaker"));
        String borrowStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowStatus") == null ? "" : paramMap
                .get("borrowStatus"));
        int borrowStatusInt = Convert.strToInt(borrowStatus, -1);
        borrowManageService.queryAllCirculationByCondition(userName, -1, borrowStatusInt, undertaker, pageBean);
        int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);

        return "success";
    }

    /**
     * @MethodName: borrowCirculationDetail
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-5-20 下午01:44:03
     * @Return:
     * @Descb: 流转标借款详情
     * @Throws:
     */
    public String borrowCirculationDetail() throws SQLException, DataException {
        String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id") == null ? "" : request().getParameter(
                "id"));
        long idLong = Convert.strToLong(id, -1);
        if (borrowCirculationDetail == null) {
            // 初审中的借款详情
            borrowCirculationDetail = borrowManageService.queryBorrowCirculationDetailById(idLong);
        }
        return "success";
    }

    /**
     * @MethodName: updateBorrowCirculation
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-5-20 下午03:23:42
     * @Return:
     * @Descb:
     * @Throws:
     */
    public String updateBorrowCirculation() throws IOException {
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        JSONObject obj = new JSONObject();
        String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
        long borrowId = Convert.strToLong(id, -1);
        String reciver = SqlInfusion.FilteSqlInfusion(paramMap.get("reciver"));
        long reciverId = Convert.strToLong(reciver, -1);
        String status = SqlInfusion.FilteSqlInfusion(paramMap.get("status"));
        long statusLong = Convert.strToLong(status, -1);
        String auditOpinion = SqlInfusion.FilteSqlInfusion(paramMap.get("auditOpinion"));
        long result = -1;
        if (statusLong == -1) {
            obj.put("msg", "请选择审核状态");
            JSONUtils.printObject(obj);
            return null;
        }
        if (!StringUtils.isNotBlank(auditOpinion)) {
            obj.put("msg", "请填写风险控制措施");
            JSONUtils.printObject(obj);
            return null;
        } else if (auditOpinion.length() > 500) {
            obj.put("msg", "风险控制措施内容不能超过500字符");
            JSONUtils.printObject(obj);
            return null;
        }
        try {
            result = borrowManageService.updateBorrowCirculationStatus(borrowId, reciverId, statusLong, auditOpinion,
                    admin.getId(), getBasePath(), getPlatformCost());
            operationLogService.addOperationLog("t_borrow", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
                    0, "更新流转标的状态", 2);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        if (result <= 0) {
            // 操作失败提示
            obj.put("msg", IConstants.ACTION_FAILURE);
            JSONUtils.printObject(obj);
            return null;
        }
        // 前台跳转地址
        obj.put("msg", "1");
        JSONUtils.printObject(obj);
        return null;
    }

    /**
     * @throws Exception
     * @MethodName: borrowManageFistAuditDetail
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-3-10 下午11:02:22
     * @Return:
     * @Descb: 后台借款管理的借款详情
     * @Throws:
     */
    public String borrowManageAllDetail() throws Exception {
        return "success";
    }

    public BorrowManageService getBorrowManageService() {
        return borrowManageService;
    }

    public void setBorrowManageService(BorrowManageService borrowManageService) {
        this.borrowManageService = borrowManageService;
    }

    public Map<String, String> getBorrowMFADetail() throws SQLException, DataException {
        return borrowMFADetail;
    }

    public Map<String, String> getBorrowMTenderInDetail() throws SQLException, DataException {
        String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id") == null ? "" : request().getParameter(
                "id"));
        long idLong = Convert.strToLong(id, -1);
        Map<String, String> TypeLogMap = null;
        if (borrowMTenderInDetail == null) {
            // 招标中的借款详情
            borrowMTenderInDetail = borrowManageService.queryBorrowTenderInDetailById(idLong);
            String nid_log = borrowMTenderInDetail.get("nid_log");
            if (StringUtils.isNotBlank(nid_log)) {
                TypeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
                int stauts = Convert.strToInt(TypeLogMap.get("subscribe_status"), -1);
                request().setAttribute("subscribes", stauts);
            }
        }
        // ---add by houli 屏蔽链接
        String mailContent = borrowMTenderInDetail.get("mailContent");
        String newStr = changeStr2Str(mailContent);
        borrowMTenderInDetail.put("mailContent", newStr);
        // ---------end
        return borrowMTenderInDetail;
    }

    public void setPlatformCostService(PlatformCostService platformCostService) {
        this.platformCostService = platformCostService;
    }

    public Map<String, String> getBorrowMFullScaleDetail() throws Exception {
        String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id") == null ? "" : request().getParameter(
                "id"));
        long idLong = Convert.strToLong(id, -1);
        Map<String, String> TypeLogMap = null;
        if (borrowMFullScaleDetail == null) {
            // 满标的借款详情
            borrowMFullScaleDetail = borrowManageService.queryBorrowFullScaleDetailById(idLong);
            String nid_log = borrowMFullScaleDetail.get("nid_log");
            if (StringUtils.isNotBlank(nid_log)) {
                TypeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
                int stauts = Convert.strToInt(TypeLogMap.get("subscribe_status"), -1);
                request().setAttribute("subscribes", stauts);
            }
        }
        // ---add by houli 屏蔽链接
        String mailContent = borrowMFullScaleDetail.get("mailContent");
        String newStr = changeStr2Str(mailContent);
        borrowMFullScaleDetail.put("mailContent", newStr);
        // ---------end
        return borrowMFullScaleDetail;
    }

    public Map<String, String> getBorrowMFlowMarkDetail() throws SQLException, DataException {
        String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id") == null ? "" : request().getParameter(
                "id"));
        long idLong = Convert.strToLong(id, -1);
        Map<String, String> TypeLogMap = null;
        if (borrowMFlowMarkDetail == null) {
            // 流标的借款详情
            borrowMFlowMarkDetail = borrowManageService.queryBorrowFlowMarkDetailById(idLong);
            String nid_log = borrowMFlowMarkDetail.get("nid_log");
            if (StringUtils.isNotBlank(nid_log)) {
                TypeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
                int stauts = Convert.strToInt(TypeLogMap.get("subscribe_status"), -1);
                request().setAttribute("subscribes", stauts);
            }
        }
        return borrowMFlowMarkDetail;
    }

    public String circulationRepayRecordInit() throws SQLException, DataException {
        return "success";
    }

    /**
     * @throws DataException
     * @throws SQLException
     * @MethodName: circulationBorrowList
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-5-20 上午11:35:50
     * @Return:
     * @Descb: 流转标借款
     * @Throws:
     */
    public String circulationRepayRecordList() throws SQLException, DataException {
        String userName = SqlInfusion
                .FilteSqlInfusion(paramMap.get("userName") == null ? "" : paramMap.get("userName"));
        String borrowTitle = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowTitle") == null ? "" : paramMap
                .get("borrowTitle"));
        String borrowStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowStatus") == null ? "" : paramMap
                .get("borrowStatus"));
        int borrowStatusInt = Convert.strToInt(borrowStatus, -1);
        borrowManageService.queryAllCirculationRepayRecordByCondition(userName, borrowStatusInt, borrowTitle, pageBean);
        return "success";
    }

    /**
     * @MethodName: circulationRepayForAdd
     * @Param: BorrowManageAction
     * @Return:
     * @Descb: 流转标还款详情添加初始化
     * @Throws:
     */
    public String circulationRepayForAdd() {
        return "success";
    }

    /**
     * @throws SQLException
     * @MethodName: circulationRepayAdd
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-7-23 下午03:48:45
     * @Return:
     * @Descb: 添加流转标详情
     * @Throws:
     */
    public String circulationRepayAdd() throws SQLException {
        Admin admin = (Admin) session().getAttribute("admin");
        String amount = SqlInfusion.FilteSqlInfusion(paramMap.get("amount"));
        double amountDouble = Convert.strToDouble(amount, -1);
        String remark = SqlInfusion.FilteSqlInfusion(paramMap.get("remark"));
        Object id = session().getAttribute("repayId");
        long repayId = Convert.strToLong(id + "", -1);
        if (repayId == -1) {
            this.addFieldError("paramMap['action']", "操作失败");
            return "input";
        }
        if (amountDouble == -1 || amountDouble <= 0) {
            this.addFieldError("paramMap['amount']", "金额格式错误");
            return "input";
        }
        if (remark.length() > 500) {
            this.addFieldError("paramMap['remark']", "备注不能超过500字符");
            return "input";
        }
        long returnId = -1;
        returnId = borrowManageService.addCirculationRepay(repayId, amountDouble, admin.getId(), remark);
        operationLogService.addOperationLog("t_borrow", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0,
                "添加流转标还款记录", 2);

        if (returnId < 1) {
            this.addFieldError("paramMap['action']", "操作失败");
            return "input";
        }
        borrowId = session().getAttribute("borrowId");
        ;
        return "success";
    }

    public Map<String, String> getBorrowMAllDetail() throws SQLException, DataException {
        String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id") == null ? "" : request().getParameter(
                "id"));
        long idLong = Convert.strToLong(id, -1);
        if (borrowMAllDetail == null) {
            // 所以的借款详情
            borrowMAllDetail = borrowManageService.queryBorrowAllDetailById(idLong);
        }
        return borrowMAllDetail;
    }

    /**
     * 验证用户资料信息
     * 
     * @return
     */
    public String isNotUnderCoirculationBorrow() {
        // JSONObject obj = new JSONObject();
        long uId = Convert.strToLong(request().getParameter("i"), -1);
        Map<String, String> userMap;
        try {
            userMap = userService.queryUserById(uId);
            if (Convert.strToInt(userMap.get("vipStatus"), 0) == IConstants.UNVIP_STATUS) {// 没有成为VIP
                JSONUtils.printStr("3");
                return null;
            }
            if (Convert.strToInt(userMap.get("authStep"), 0) == 1) {
                // 基本信息认证步骤
                JSONUtils.printStr("7");
                return null;
            } else if (Convert.strToInt(userMap.get("authStep"), 0) == 2) {
                // 工作信息认证步骤
                JSONUtils.printStr("4");
                return null;
            } else if (Convert.strToInt(userMap.get("authStep"), 0) == 3) {
                // VIP申请认证步骤
                JSONUtils.printStr("5");
                return null;
            } else if (Convert.strToInt(userMap.get("authStep"), 0) == 4) {
                // 上传资料认证步骤
                JSONUtils.printStr("6");
                return null;
            }
            Map<String, String> map = dataApproveService.querySauthId(uId, IConstants.FLOW_PHONE);
            if (map == null) {
                JSONUtils.printStr("2");// 手机认证
                return null;
            } else {
                Long sauthId = Convert.strToLong(map.get("id"), -1L);
                Long status = dataApproveService.queryApproveStatus(sauthId);
                if (status < 0) {
                    JSONUtils.printStr("8");// 手机认证待审核
                    return null;
                }
            }
            // 条件满足
            JSONUtils.printStr("1");
            return null;
        } catch (DataException e) {
            log.error(e);
            e.printStackTrace();
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @MethodName: underCirculationBorrow
     * @Param: BorrowManageAction
     * @Author: gang.lv
     * @Date: 2013-5-20 下午04:38:02
     * @Return:
     * @Descb: 代发流转标
     * @Throws:
     */
    public String underCirculationBorrow() {
        String uId = SqlInfusion.FilteSqlInfusion(request().getParameter("i") == null ? "" : request()
                .getParameter("i"));
        session().setAttribute("uId", uId);

        Map<String, String> tempBorrwBidMessage = new HashMap<String, String>();
        try {
            tempBorrwBidMessage = shoveBorrowTypeService.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
            // 取得按借款金额的比例进行奖励
            paramMap.put("scalefirst", tempBorrwBidMessage.get("award_scale_first") + "");
            paramMap.put("scaleend", tempBorrwBidMessage.get("award_scale_end") + "");
            // 如果选择金额的话，则按此奖励的金额范围
            paramMap.put("accountfirst", tempBorrwBidMessage.get("award_account_first") + "");
            paramMap.put("accountend", tempBorrwBidMessage.get("award_account_end") + "");
            // 借款额度
            paramMap.put("borrowMoneyfirst", tempBorrwBidMessage.get("amount_first") + "");
            paramMap.put("borrowMoneyend", tempBorrwBidMessage.get("amount_end") + "");
            // 借款额度倍数
            paramMap.put("accountmultiple", tempBorrwBidMessage.get("account_multiple") + "");
            // 年利率
            paramMap.put("aprfirst", tempBorrwBidMessage.get("apr_first") + "");
            paramMap.put("aprend", tempBorrwBidMessage.get("apr_end") + "");
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
        } catch (DataException e) {
            log.error(e);
            e.printStackTrace();
        }

        return "success";
    }

    public DataApproveService getDataApproveService() {
        return dataApproveService;
    }

    public void setDataApproveService(DataApproveService dataApproveService) {
        this.dataApproveService = dataApproveService;
    }

    private String changeStr2Str(String mailContent) {
        if (mailContent != null && !mailContent.equals("")) {
            int ind1 = mailContent.indexOf("<");
            int ind2 = mailContent.indexOf(">");
            if (ind1 < 0 || ind2 < 0 || ind2 <= ind1) {
                return mailContent;
            }
            String newStr = mailContent.substring(0, ind1) + mailContent.substring(ind2 + 1);
            // 处理<a>链接的结束标签
            newStr.replace("</a>", "");
            return newStr;
        }
        return mailContent;
    }

    public void setShoveBorrowTypeService(ShoveBorrowTypeService shoveBorrowTypeService) {
        this.shoveBorrowTypeService = shoveBorrowTypeService;
    }

    public List<Map<String, Object>> getCirList() {
        return cirList;
    }

    public void setCirList(List<Map<String, Object>> cirList) {
        this.cirList = cirList;
    }

    public Map<String, String> getBorrowCirculationDetail() {
        return borrowCirculationDetail;
    }

    public void setBorrowCirculationDetail(Map<String, String> borrowCirculationDetail) {
        this.borrowCirculationDetail = borrowCirculationDetail;
    }

    public File getUserFile() {
        return userFile;
    }

    public void setUserFile(File userFile) {
        this.userFile = userFile;
    }

    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    public Object getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Object borrowId) {
        this.borrowId = borrowId;
    }

    public static Log getLog() {
        return log;
    }

    public List<Map<String, Object>> getBorrowPurposeList() throws SQLException, DataException {
        if (borrowPurposeList == null) {
            // 借款目的列表
            borrowPurposeList = selectedService.borrowPurpose();
        }
        return borrowPurposeList;
    }

    public List<Map<String, Object>> getBorrowDeadlineList() throws SQLException, DataException {
        if (borrowDeadlineList == null) {
            // 借款期限列表
            borrowDeadlineList = selectedService.borrowDeadline();
        }
        return borrowDeadlineList;
    }

    public List<Map<String, Object>> getBorrowRaiseTermList() throws SQLException, DataException {
        if (borrowRaiseTermList == null) {
            // 筹款期限列表
            borrowRaiseTermList = selectedService.borrowRaiseTerm();
        }
        return borrowRaiseTermList;
    }

    public List<Map<String, Object>> getSysImageList() throws SQLException, DataException {
        if (sysImageList == null) {
            // 系统列表
            sysImageList = selectedService.sysImageList();
        }
        return sysImageList;
    }

    /**
     * 初审列表初始化
     */
    public String queryFirstTrialIndex() {
        return SUCCESS;
    }

    /**
     * 查询初审列表
     */
    @SuppressWarnings("unchecked")
    public String queryFirstTrialList() throws SQLException, DataException {
        String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
        String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay"));
        String borrowStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowStatus"));

        borrowManageService.queryFirstTrialList(pageBean, userName, borrowWay, borrowStatus);
        int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);

        return SUCCESS;
    }

    /**
     * 根据ID查询借款信息--初审 queryFirsttrialById
     */
    public String queryFirsttrialById() throws DataException, SQLException {
        String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id"));
        Map<String, String> map = null;
        try {
            map = borrowManageService.queryFirsttrialById(id);
            request().setAttribute("map", map);
        } catch (DataException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 初审--发送站内信
     * 
     * @return
     * @throws SQLException
     *             [参数说明]
     * 
     * @return String [返回类型说明]
     * @throws IOException
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String updateFirsttrial() throws SQLException, IOException {
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
        String borrowStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowStatus"));
        String windControl = SqlInfusion.FilteSqlInfusion(paramMap.get("windControl").trim());
        String reciver = SqlInfusion.FilteSqlInfusion(paramMap.get("reciver"));
        String mailTitle = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowTitle"));
        String mailContent = SqlInfusion.FilteSqlInfusion(paramMap.get("mailContent").trim());
        String firstTrial = admin.getUserName();
        long sender = admin.getId();
        long result = 0;
        try {
            result = borrowManageService.updateFirsttrial(id, borrowStatus, windControl, firstTrial, reciver,
                    mailTitle, sender, mailContent);
            if (result > 0) {

                JSONUtils.printStr("1");
            } else {
                JSONUtils.printStr("2");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.printStr("2");
        }
        return null;
    }

    /**
     * 复审列表初始化
     * 
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String queryRecheckIndex() {
        return SUCCESS;
    }

    /**
     * 查询复审列表
     * 
     * @return
     * @throws SQLException
     * @throws DataException
     *             [参数说明]
     * 
     * @return String [返回类型说明]
     * @throws DataException
     * @throws SQLException
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public String queryRecheckList() throws SQLException, DataException {
        String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
        String borrowWay = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay"));
        String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
        Admin admin = (Admin) session().getAttribute("admin");
        borrowManageService.queryRecheckList(pageBean, userName, borrowWay, id);
        int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);
        request().setAttribute("adminName", admin.getUserName());
        return SUCCESS;
    }

    /**
     * 根据ID查询借款信息--复审 queryRecheckById
     * 
     * @return [参数说明]
     * 
     * @return Map<String,String> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String queryRecheckById() throws DataException, SQLException {
        String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id"));
        Map<String, String> map = null;
        try {
            map = borrowManageService.queryRecheckById(id);
            // 测试服务器报错,小小的处理一下
            String excitationSum = map.get("excitationSum");
            if (Convert.strToInt(excitationSum, -1) < 0) {
                excitationSum = "不设置奖励";
            }
            map.put("excitationSum", excitationSum);
            String maxTenderedSum = map.get("maxTenderedSum");
            if (Convert.strToDouble(maxTenderedSum, -1) < 0) {
                maxTenderedSum = "没有限制";
            }
            map.put("maxTenderedSum", maxTenderedSum);
            request().setAttribute("map", map);
        } catch (DataException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 复审--发送站内信--新增修改借款信息
     */
    public String updateRecheck() throws SQLException, IOException {
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
        String borrowStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowStatus"));
        String windControl = SqlInfusion.FilteSqlInfusion(paramMap.get("windControl"));
        String reciver = SqlInfusion.FilteSqlInfusion(paramMap.get("reciver"));
        String mailTitle = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowTitle"));
        String mailContent = SqlInfusion.FilteSqlInfusion(paramMap.get("mailContent").trim());
        String minTenderedSum = SqlInfusion.FilteSqlInfusion(paramMap.get("minTenderedSum"));
        String maxTenderedSum = SqlInfusion.FilteSqlInfusion(paramMap.get("maxTenderedSum"));
        String userIds = SqlInfusion.FilteSqlInfusion(paramMap.get("userIds"));
        String recheck = admin.getUserName();
        long sender = admin.getId();
        long result = 0;

        /** 新增修改借款信息,不存在给予默认值 */
        String borrowAmounts = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("borrowAmount")), "0");// 没有给0
        String annualRate = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("annualRate")), "-1");
        String deadline = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("deadline")), "-1");
        String raiseTerm = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("raiseTerm")), "-1");

        String borrowGroup = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("borrowGroup")), "0");
        try {
            // 重新初审
            if (borrowStatus == "9" || borrowStatus.equals("9")) {
                result = borrowManageService.updateFirsttrialAgain(id, borrowStatus, borrowAmounts, annualRate,
                        deadline, raiseTerm);
                if (result > 0) {
                    JSONUtils.printStr("1");
                } else {
                    JSONUtils.printStr("2");
                }
            } else {
                //
                result = borrowManageService.updateRecheck(id, borrowStatus, windControl, recheck, reciver, mailTitle,
                        sender, mailContent, minTenderedSum, maxTenderedSum, userIds, borrowAmounts, annualRate,
                        deadline, raiseTerm, borrowGroup);
                if (result > 0) {
                    JSONUtils.printStr("1");
                } else {
                    JSONUtils.printStr("2");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.printStr("2");
        }
        return null;
    }

    /**
     * 查询待发布列表初始化
     * 
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String queryReleasedListIndex() {
        return SUCCESS;
    }

    /**
     * 查询待发布列表
     * 
     * @return
     * @throws SQLException
     * @throws DataException
     *             [参数说明]
     * 
     * @return String [返回类型说明]
     * @throws DataException
     * @throws SQLException
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public String queryReleasedList() throws SQLException, DataException {
        String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
        String source = SqlInfusion.FilteSqlInfusion(paramMap.get("source"));
        String borrowAmount = SqlInfusion.FilteSqlInfusion(paramMap.get("borrowAmount"));
        Admin admin = (Admin) session().getAttribute("admin");
        borrowManageService.queryReleasedList(pageBean, userName, source, borrowAmount);
        int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);
        request().setAttribute("adminName", admin.getUserName());
        return SUCCESS;
    }

    /**
     * 发布借款
     * 
     * @return
     * @throws SQLException
     * @throws IOException
     *             [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String updateReleased() throws SQLException, IOException {
        String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
        long result = 0;
        try {
            result = borrowManageService.updateReleased(id);
            if (result > 0) {
                JSONUtils.printStr("1");
            } else {
                JSONUtils.printStr("2");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.printStr("2");
        }
        return null;
    }

    /**
     * 撮合借款列表初始化
     */
    public String queryBringListIndex() {
        return SUCCESS;
    }

    /**
     * 查询撮合借款列表
     */
    @SuppressWarnings("unchecked")
    public String queryBringList() throws SQLException, DataException {
        String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
        String source = SqlInfusion.FilteSqlInfusion(paramMap.get("source"));

        borrowManageService.queryBringList(pageBean, userName, source);
        int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        request().setAttribute("pageNum", pageNum);

        return SUCCESS;
    }

    /**
     * 撮合借款
     */
    public String updateBring() throws Exception {
        String idstr = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
        double collagen = Convert.strToDouble(paramMap.get("collagen"), -1); // 投标进度

        if (collagen <= 0) {
            borrowManageService.updateBring(idstr);
            JSONUtils.printStr2("操作成功");
            return null;
        }

        List<Map<String, Object>> list = borrowManageService.queryBringByIds(idstr);
        if (list == null || list.size() == 0) {
            JSONUtils.printStr2("未找到数据");
            return null;
        }
        for (Map<String, Object> map : list) {
            String id = map.get("id") + "";
            String number = map.get("number") + "";
            JSONObject json = null;
            String str = ChinaPnRInterface.usrUnFreeze(map.get("ordId") + "", map.get("trxId") + "");
            try {
                json = JSONObject.fromObject(str);
                // 失败借款，插入资金记录
                borrowManageService.insertFundrecord(map);

                // -----------------c_j
                /*
                 * Map<String,String> usersex =
                 * userService.queryIdNOsex(Convert.
                 * strToLong(map.get("investor")+"",-1L)); if (usersex!=null) {
                 * String username_sex =
                 * AmountUtil.isnoQuerySex(usersex.get("realName"),
                 * usersex.get("idNo")); String sendcontent =
                 * "尊敬的"+username_sex+
                 * "您好，你投资的编号为"+number+"的借款标的，由于在特定时间内未完成资金募集，交易撮合失败，您所投资的资金"
                 * +map.get("transAmt")+"元解冻，请您投资其它标的.";
                 * sendMessageService.noteSend(sendcontent,
                 * Convert.strToLong(map.get("investor")+"",-1L));
                 * //------------------- }
                 */

            } catch (Exception e) {
                str = str.substring(str.indexOf("<p class=\"desc\">") + "<p class=\"desc\">".length());
                JSONUtils.printStr2(str.substring(0, str.indexOf("</p>")));
                e.printStackTrace();
                return null;
            }
            if (0 != json.getInt("RespCode")) {// 成功
                JSONUtils.printStr2("失败:" + json.getString("RespDesc"));
                return null;
            }
            borrowManageService.updateBring(id);
        }
        JSONUtils.printStr2("操作成功");
        return null;
    }

    public void timer() {
        Timer timer = new Timer();
        timer.schedule(new TaskTimer(), 0, 2000);
    }

    /**
     * 定时发布
     */
    public String timerSend() throws SQLException, DataException, IOException {
        log.info("定时发布借款开始......");
        // borrowManageService.queryRedayReleasedList(pageBean, "", "", "");
        // List<Map<String, Object>> list = pageBean.getPage(); // 查询出系统发布时间
        // if (null != list && list.size() != 0) {
        // }
        try {
            borrowManageService.updateTimerSend();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("定时发布借款OK......");
        return null;
    }

    /**
     * 定时
     */
    public String updateReleasedTimeList() {
        String id = SqlInfusion.FilteSqlInfusion(paramMap.get("id"));
        String times = SqlInfusion.FilteSqlInfusion(paramMap.get("times"));
        long result = 0;
        try {
            result = borrowManageService.updateTimer(id, times);
            if (result > 0) {
                JSONUtils.printStr("1");
            } else {
                JSONUtils.printStr("2");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SelectedService getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(SelectedService selectedService) {
        this.selectedService = selectedService;
    }

    public List<Map<String, Object>> getBorrowAmountList() {
        return borrowAmountList;
    }

    public void setBorrowAmountList(List<Map<String, Object>> borrowAmountList) {
        this.borrowAmountList = borrowAmountList;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setBorrowService(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    public ShoveBorrowTypeService getShoveBorrowTypeService() {
        return shoveBorrowTypeService;
    }

    public FinanceService getFinanceService() {
        return financeService;
    }

    public void setBorrowMFADetail(Map<String, String> borrowMFADetail) {
        this.borrowMFADetail = borrowMFADetail;
    }

    public void setBorrowMTenderInDetail(Map<String, String> borrowMTenderInDetail) {
        this.borrowMTenderInDetail = borrowMTenderInDetail;
    }

    public void setBorrowMFullScaleDetail(Map<String, String> borrowMFullScaleDetail) {
        this.borrowMFullScaleDetail = borrowMFullScaleDetail;
    }

    public void setBorrowMFlowMarkDetail(Map<String, String> borrowMFlowMarkDetail) {
        this.borrowMFlowMarkDetail = borrowMFlowMarkDetail;
    }

    public void setBorrowMAllDetail(Map<String, String> borrowMAllDetail) {
        this.borrowMAllDetail = borrowMAllDetail;
    }

    public void setBorrowPurposeList(List<Map<String, Object>> borrowPurposeList) {
        this.borrowPurposeList = borrowPurposeList;
    }

    public void setBorrowDeadlineList(List<Map<String, Object>> borrowDeadlineList) {
        this.borrowDeadlineList = borrowDeadlineList;
    }

    public void setBorrowRaiseTermList(List<Map<String, Object>> borrowRaiseTermList) {
        this.borrowRaiseTermList = borrowRaiseTermList;
    }

    public void setSysImageList(List<Map<String, Object>> sysImageList) {
        this.sysImageList = sysImageList;
    }

    /** 借款人(用户列表) */
    public List<Map<String, Object>> getBorrowerList() throws SQLException {
        borrowerList = userService.queryUsers();
        return borrowerList;
    }

    public void setBorrowerList(List<Map<String, Object>> borrowerList) {
        this.borrowerList = borrowerList;
    }

    public List<Map<String, Object>> getBorrowTurnlineList() {
        if (borrowTurnlineList == null) {
            // 借款期限列表
            // borrowDeadlineList = selectedService.borrowDeadline();
            // 获取的到相应的map
            Map<String, String> borrowTrunlineMap;
            try {
                borrowTrunlineMap = shoveBorrowTypeService.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
                borrowTurnlineList = new ArrayList<Map<String, Object>>();
                if (borrowTrunlineMap != null && borrowTrunlineMap.size() > 0) {
                    String trunmonth = Convert.strToStr(borrowTrunlineMap.get("period_month") + "", "");
                    String[] trunmonths = trunmonth.split(",");// 截取;符号
                    // 放入String数组
                    if (trunmonths != null) {
                        String str = " 个月";
                        for (String file : trunmonths) {// 遍历String数组
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("key", file.trim());
                            map.put("value", file + str);
                            borrowTurnlineList.add(map);
                        }
                    }

                }
            } catch (SQLException e) {
                log.error(e);
                e.printStackTrace();
            } catch (DataException e) {
                log.error(e);
                e.printStackTrace();
            }

        }
        return borrowTurnlineList;
    }

    public void setBorrowTurnlineList(List<Map<String, Object>> borrowTurnlineList) {
        this.borrowTurnlineList = borrowTurnlineList;
    }

    public OperationLogService getOperationLogService() {
        return operationLogService;
    }

    @SuppressWarnings("unchecked")
    public void setOperationLogService(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    // ////////////失败借款
    public String failedBorrowIndex() throws Exception {
        return SUCCESS;
    }

    // 失败借款列表
    @SuppressWarnings("unchecked")
    public String failedBorrowList() throws Exception {

        String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
        int borrowWay = Convert.strToInt(SqlInfusion.FilteSqlInfusion(paramMap.get("borrowWay")), 0);

        try {
            borrowManageService.queryAllFailedBorrowList(pageBean, userName, borrowWay);
            int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
            request().setAttribute("pageNum", pageNum);
            request().setAttribute("totalNum", pageBean.getTotalNum());
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        return SUCCESS;
    }

    // 删除失败借款
    public String deleteFailedBorrow() throws Exception {
        String ids = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("id")), null);
        if (ids != null) {
            try {
                long result = borrowManageService.deleteFailedBorrow(ids);
                if (result > 0) {
                    JSONUtils.printStr("1");
                } else {
                    JSONUtils.printStr("2");
                }
            } catch (Exception e) {
                log.error(e);
                e.printStackTrace();
                throw e;
            }
        }
        return null;
    }

    // 导出选中的失败借款数据
    @SuppressWarnings("unchecked")
    public String exportAllFailedBorrow() {
        String idString = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("ids")), null);
        pageBean.pageNum = 1;
        pageBean.setPageSize(100000);
        String exporName = "借款失败";
        try {
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            borrowManageService.queryselectedFailedBorrowList(pageBean, idString);
            if (pageBean.getPage() == null) {
                getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
                return null;
            }
            if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
                getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
                return null;
            }
            HSSFWorkbook wb = ExcelUtils.exportExcel(exporName, pageBean.getPage(), new String[] { "标的id", "用户名",
                    "真实姓名", "来源", "标的类型", "借款标题", "借款金额", "利率", "期限", "筹标期限", "状态" }, new String[] { "id", "userName",
                    "realName", "source", "borrowWay", "borrowTitle", "borrowAmount", "annualRate", "deadLine",
                    "raiseTerm", "borrowStatus", });
            this.export(wb, new Date().getTime() + ".xls");
            // 系统操作日志
            operationLogService.addOperationLog("v_t_user_failedborrow_lists", admin.getUserName(), IConstants.EXCEL,
                    admin.getLastIP(), 0, "导出" + exporName, 2);
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (DataException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 导出选中的初审借款数据
    @SuppressWarnings("unchecked")
    public String exportFirstTrialBorrow() {
        String idString = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("ids")), null);
        pageBean.pageNum = 1;
        pageBean.setPageSize(100000);
        String exporName = "初审借款";
        try {
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            borrowManageService.queryselectedFirstTrialBorrowList(pageBean, idString);
            if (pageBean.getPage() == null) {
                getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
                return null;
            }
            if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
                getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
                return null;
            }

            // HSSFWorkbook wb = ExcelUtils.exportExcel(exporName,
            // pageBean.getPage(), new String[] { "标的id", "用户名", "真实姓名", "借款来源",
            // "标的类型", "借款标题",
            // "借款金额", "利率", "期限", "筹标期限", "状态" }, new String[] { "id",
            // "userName", "realName", "source", "borrowWay", "borrowTitle",
            // "borrowAmount", "annualRate", "deadLine", "raiseTerm",
            // "circulationStatus", });
            HSSFWorkbook wb = ExcelUtils.exportExcel(exporName, pageBean.getPage(), new String[] { "借款编号", "真实姓名",
                    "标的类型", "借款标题", "借款金额", "利率", "期限", "筹标期限", "状态" }, new String[] { "number", "realName",
                    "borrowWay", "borrowTitle", "borrowAmount", "annualRate", "deadLine", "raiseTerm",
                    "circulationStatus", });
            this.export(wb, new Date().getTime() + ".xls");
            // 系统操作日志
            operationLogService.addOperationLog("v_t_user_firsttrialborrow_lists", admin.getUserName(),
                    IConstants.EXCEL, admin.getLastIP(), 0, "导出" + exporName, 2);
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (DataException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 导出选中的复审借款数据
    @SuppressWarnings("unchecked")
    public String exportAllRecheckBorrow() {
        String idString = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("ids")), null);
        pageBean.pageNum = 1;
        pageBean.setPageSize(100000);
        String exporName = "复审借款";
        try {
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            borrowManageService.queryselectedRecheckTrialBorrowList(pageBean, idString);
            if (pageBean.getPage() == null) {
                getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
                return null;
            }
            if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
                getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
                return null;
            }
            // HSSFWorkbook wb = ExcelUtils.exportExcel(exporName,
            // pageBean.getPage(), new String[] { "标的id", "用户名", "真实姓名", "借款来源",
            // "标的类型", "借款标题",
            // "借款金额", "利率", "期限", "筹标期限", "初审人" }, new String[] { "id",
            // "userName", "realName", "source", "borrowWay", "borrowTitle",
            // "borrowAmount", "annualRate", "deadLine", "raiseTerm",
            // "firstTrial", });
            HSSFWorkbook wb = ExcelUtils.exportExcel(exporName, pageBean.getPage(), new String[] { "借款编号", "真实姓名",
                    "标的类型", "借款标题", "借款金额", "利率", "期限", "筹标期限", "初审人" }, new String[] { "number", "realName",
                    "borrowWay", "borrowTitle", "borrowAmount", "annualRate", "deadLine", "raiseTerm", "firstTrial", });
            this.export(wb, new Date().getTime() + ".xls");
            // 系统操作日志
            operationLogService.addOperationLog("v_t_user_rechecktrialborrow_lists", admin.getUserName(),
                    IConstants.EXCEL, admin.getLastIP(), 0, "导出" + exporName, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 导出选中的待发布借款数据
    @SuppressWarnings("unchecked")
    public String exportAllReleaseBorrow() {
        String idString = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("ids")), null);
        pageBean.pageNum = 1;
        pageBean.setPageSize(100000);
        String exporName = "待发布借款";
        try {
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            borrowManageService.queryselectedReleaseBorrowList(pageBean, idString);
            if (pageBean.getPage() == null) {
                getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
                return null;
            }
            if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
                getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
                return null;
            }

            HSSFWorkbook wb = ExcelUtils.exportExcel(exporName, pageBean.getPage(), new String[] { "标的id", "用户名",
                    "真实姓名", "借款来源", "标的类型", "借款标题", "借款金额", "利率", "期限", "筹标期限", "复审人" }, new String[] { "id",
                    "userName", "realName", "source", "borrowWay", "borrowTitle", "borrowAmount", "annualRate",
                    "deadLine", "raiseTerm", "recheck", });
            this.export(wb, new Date().getTime() + ".xls");
            // 系统操作日志
            operationLogService.addOperationLog("v_t_user_releaseborrow_lists", admin.getUserName(), IConstants.EXCEL,
                    admin.getLastIP(), 0, "导出" + exporName, 2);
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (DataException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> getBorrowTypeMap(String type) throws SQLException, DataException {
        String nid = "";
        if (IConstants.BORROW_TYPE_NET_VALUE.equals(type)) {
            // 薪金贷
            nid = BorrowType.WORTH.getValue();
        } else if (IConstants.BORROW_TYPE_SECONDS.equals(type)) {
            // 彩生活
            nid = BorrowType.SECONDS.getValue();
        } else if (IConstants.BORROW_TYPE_GENERAL.equals(type)) {
            // 业主贷
            nid = BorrowType.ORDINARY.getValue();
        }
        session().setAttribute("nid", nid);
        return shoveBorrowTypeService.queryShoveBorrowTypeByNid(nid);
    }

    // 导出选中的撮合借款数据
    @SuppressWarnings("unchecked")
    public String exportAllBringBorrow() {
        String idString = Convert.strToStr(SqlInfusion.FilteSqlInfusion(request("ids")), null);
        pageBean.pageNum = 1;
        pageBean.setPageSize(100000);
        String exporName = "撮合借款";
        try {
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            borrowManageService.queryselectedBringBorrowList(pageBean, idString);
            if (pageBean.getPage() == null) {
                getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
                return null;
            }
            if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
                getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
                return null;
            }

            HSSFWorkbook wb = ExcelUtils.exportExcel(exporName, pageBean.getPage(), new String[] { "标的id", "用户名",
                    "真实姓名", "借款来源", "标的类型", "借款标题", "借款金额", "利率", "期限", "筹标期限", "投标状态", "复审人" }, new String[] { "id",
                    "userName", "realName", "source", "borrowWay", "borrowTitle", "borrowAmount", "annualRate",
                    "deadLine", "raiseTerm", "collagen", "recheck", });
            this.export(wb, new Date().getTime() + ".xls");
            // 系统操作日志
            operationLogService.addOperationLog("v_t_user_bringborrow_lists", admin.getUserName(), IConstants.EXCEL,
                    admin.getLastIP(), 0, "导出" + exporName, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 按id查询失败的借款
    public String queryFailedBorrowById() throws DataException, SQLException {
        String id = SqlInfusion.FilteSqlInfusion(request().getParameter("id"));
        Map<String, String> map = null;
        try {
            map = borrowManageService.queryFailedBorrowById(id);
            request().setAttribute("map", map);
        } catch (DataException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 借款 复审 后台批量导入彩生活用户 和 借款信息
     */
    public String borrowUserFromColorlife() {
        return SUCCESS;
    }

    /**
     * 用户是否存在
     */
    public String checkUserExits() throws IOException {
        try {
            String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName")); // 用户名
            Long vidResult = -1L;
            // 判断用户名是否唯一
            if (StringUtils.isNotBlank(userName)) {
                vidResult = userService.isUEjihuo_(null, userName);
                if (vidResult < 0) {// 没有这个账号
                    JSONUtils.printStr("2");
                    return null;
                }
                JSONUtils.printStr("4");// 有这个账号
                return null;
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        JSONUtils.printStr("2");
        return null;
    }

    /** 手动录入标的初始化 */
    public String enterBorrowInit() {
        return SUCCESS;
    }

    /** 后台 审核管理 手动录入标 */
    public String addEnterBorrow() throws IOException {
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("title")), "");
        JSONObject json = new JSONObject();
        if (StringUtils.isBlank(title)) {
            json.put("msg", "请填入借款标题");
            JSONUtils.printObject(json);
            return null;
        }
        String username = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("username")), "");
        if (StringUtils.isBlank(username)) {
            json.put("msg", "请输入借款人");
            JSONUtils.printObject(json);
            return null;
        }
        int borrowWay = Convert.strToInt(paramMap.get("borrowway"), 0);
        if (borrowWay < 1 || borrowWay > 3) {
            json.put("msg", "请选择借款类型");
            JSONUtils.printObject(json);
            return null;
        }
        String purpose = SqlInfusion.FilteSqlInfusion(paramMap.get("purpose"));
        if (StringUtils.isBlank(purpose)) {
            json.put("msg", "请选择借款用途");
            JSONUtils.printObject(json);
            return null;
        }
        int deadLine = Convert.strToInt(paramMap.get("deadLine"), -1);
        if (deadLine < 0) {
            json.put("msg", "请选择借款期限");
            JSONUtils.printObject(json);
            return null;
        }
        int daythe = 1;// 1月标
        double amount = Convert.strToDouble(paramMap.get("amount"), 0);
        if (amount < 0) {
            json.put("msg", "请填入借款金额");
            JSONUtils.printObject(json);
            return null;
        }
        double annualRate = Convert.strToDouble(paramMap.get("anna"), 0);
        if (annualRate < 0) {
            json.put("msg", "请填入借款利率");
            JSONUtils.printObject(json);
            return null;
        }
        int maxInvest = Convert.strToInt(paramMap.get("maxInvest"), 0);
        if (maxInvest > 0 && (maxInvest < 2 || maxInvest > 49)) {
            json.put("msg", "最大投标人数在2~49人之间");
            JSONUtils.printObject(json);
            return null;
        }
        int raiseTerm = Convert.strToInt(paramMap.get("raiseTerm"), 0);
        if (raiseTerm < 0) {
            json.put("msg", "请选择筹标期限");
            JSONUtils.printObject(json);
            return null;
        }
        String zxf = paramMap.get("zxf");// 借款咨询方
        String zxfh = paramMap.get("zxfh");// 咨询方分行

        long publisher = -1L;
        long userCustId = -1L;
        try {
            Map<String, String> map = userService.queryUserByName(username);
            if (map != null && map.size() > 0) {
                publisher = Convert.strToLong(map.get("id"), -1);
                userCustId = Convert.strToLong(map.get("usrCustId"), -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (publisher < 0) {// 没有这个账号
            json.put("msg", "该用户不存在,请重新输入");
            JSONUtils.printObject(json);
            return null;
        }
        if (userCustId < 0) {
            json.put("msg", "该用户还不是汇付天下会员");
            JSONUtils.printObject(json);
            return null;
        }

        String detail = paramMap.get("info");
        int excitationType = 1;// 没有奖励

        // 查询标种类型
        Map<String, String> borrowTypeMap = null;
        List<Map<String, Object>> mapList = null;
        try {
            borrowTypeMap = getBorrowTypeMap(borrowWay + "");
            mapList = platformCostService.queryAllPlatformCost();
        } catch (Exception e) {
            e.printStackTrace();
        }

        double frozenMargin = amount * Double.parseDouble(borrowTypeMap.get("all_frost_scale")) / 100;
        Map<String, Object> mapfee = new HashMap<String, Object>();
        Map<String, Object> mapFeestate = new HashMap<String, Object>();
        for (Map<String, Object> platformCostMap : mapList) {
            double costFee = Convert.strToDouble(platformCostMap.get("costFee") + "", 0);
            int costMode = Convert.strToInt(platformCostMap.get("costMode") + "", 0);
            String remark = Convert.strToStr(platformCostMap.get("remark") + "", "");
            if (costMode == 1) {
                mapfee.put(platformCostMap.get("alias") + "", costFee * 0.01);
            } else {
                mapfee.put(platformCostMap.get("alias") + "", costFee);
            }
            mapFeestate.put(platformCostMap.get("alias") + "", remark); // 费用说明
        }
        String json1 = JSONObject.fromObject(mapfee).toString();
        String jsonState = JSONObject.fromObject(mapFeestate).toString();
        try {
            // title = purpose
            /*
             * 20140610 by 刘文韬 增加标的所属群组
             */
            int borrowGroup = 0;
            Map<String, String> map = borrowService.addBorrow(purpose, "images/default-img.jpg", borrowWay, -1,
                    deadLine, 1, amount, annualRate, 100, 1000, raiseTerm, excitationType, -1, detail, 1, null, -1,
                    admin.getLastIP(), publisher, 0, daythe, getBasePath(), "", 0, 0, 0, 0,
                    borrowTypeMap.get("identifier"), frozenMargin, json1, jsonState, purpose, 2, maxInvest, zxf, zxfh,
                    borrowGroup);
            if (Convert.strToInt(map.get("ret"), -1) > 0) {
                json.put("msg", "录入成功");
                JSONUtils.printObject(json);
            } else {
                json.put("msg", "失败:" + map.get("ret_desc"));
                JSONUtils.printObject(json);
            }
        } catch (Exception e) {
            json.put("msg", "系统出现异常");
            e.printStackTrace();
            JSONUtils.printObject(json);
        }
        return null;
    }

    /** 后台 审核管理 手动录入标的 查询用户 */
    public String queryBorrowerListHHN() throws IOException {
        String uname = SqlInfusion.FilteSqlInfusion(paramMap.get("keyword"));
        List<Map<String, Object>> list = null;
        try {
            list = userService.queryUsersForImport(uname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONUtils.printArray(list);
        return null;
    }

    /** 后台 审核管理 自动导入 */
    public String importBorrowDetailHHN() throws SQLException, IOException {
        if (userFile == null) {
            this.addFieldError("userFile", "数据上传错误,请选择文件!");
            return INPUT;
        }
        String[][] datas = null;
        try {
            datas = ExcelUtils.getData(userFile, 2);
        } catch (Exception e) {
            e.printStackTrace();
            this.addFieldError("userFile", "数据文件格式错误,请选择正确的文件!");
            return INPUT;
        }
        if (datas == null || datas.length == 0) {
            this.addFieldError("userFile", "数据导入为空,请按照模板填写资料后再导入!");
            return INPUT;
        }

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = 0; i < datas.length; i++) {
            String idNo = Convert.strToStr(datas[i][8], "");// 申请者身份证号码
            if ("".equals(idNo)) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("hhnMsg", "身份证未填写");
                list.add(map);
                continue;
            }
            Map<String, String> map = userService.queryByIdNo(idNo);
            if (map == null || map.size() == 0) {
                map = new HashMap<String, String>();
                map.put("hhnMsg", "身份证不存在");
                list.add(map);
                continue;
            }
            if (Convert.strToLong(map.get("usrCustId"), -1) <= 0) {
                map = new HashMap<String, String>();
                map.put("hhnMsg", "该用户还不是汇付天下会员");
                list.add(map);
                continue;
            }

            /**
             * 加业务编码导入的检查
             */
            if ("".equals(Convert.strToStr(datas[i][28], ""))) {
                map = new HashMap<String, String>();
                map.put("hhnMsg", "业务编码不可以为空");
                list.add(map);
                continue;
            }

            long userId = Convert.strToLong(map.get("userId"), -1);
            map = new HashMap<String, String>();
            map.put("userId", userId + "");
            map.put("idNo", idNo);

            map.put("realName", Convert.strToStr(datas[i][0], ""));// 客户名称
            map.put("sex", Convert.strToStr(datas[i][1], ""));// 性别
            map.put("age", Convert.strToInt(datas[i][2], 18) + "");// 年龄
            map.put("maritalStatus", Convert.strToStr(datas[i][3], ""));// 婚姻状况
            map.put("hasHourse", Convert.strToStr(datas[i][4], ""));// 居住状况
            map.put("highestEdu", Convert.strToStr(datas[i][5], ""));// 学历
            map.put("creditNum", Convert.strToStr(datas[i][6], ""));// 信用卡总张数
            map.put("creditSum", Convert.strToStr(datas[i][7], ""));// 信用卡总额度
            map.put("hasCar", Convert.strToStr(datas[i][9], ""));// 车辆信息

            map.put("orgName", Convert.strToStr(datas[i][10], ""));// 公司名称
            map.put("companyAddress", Convert.strToStr(datas[i][11], ""));// 公司地址
            map.put("job", Convert.strToStr(datas[i][12], ""));// 职位级别
            map.put("companyType", Convert.strToStr(datas[i][13], ""));// 企业性质
            map.put("workYear", Convert.strToStr(datas[i][14], ""));// 现公司工作年限
            map.put("companyLine", Convert.strToStr(datas[i][15], ""));// 公司行业
            map.put("monthlyIncome", Convert.strToStr(datas[i][16], ""));// 月收入
            map.put("monthlyOutcome", Convert.strToStr(datas[i][17], ""));// 月支出合计

            map.put("borrowAmount", Convert.strToDouble(datas[i][18], 0) + "");// 借款金额
            map.put("deadline", Convert.strToInt(datas[i][19], 1) + "");// 申请期限
            map.put("borrowWay", Convert.strToInt(datas[i][20], 1) + "");// 产品类型1=薪金贷2=生意贷3=业主贷
            map.put("paymentMode", getPaymentMode(Convert.strToStr(datas[i][21], "")));//还款方式
            map.put("annualRate", Convert.strToDouble(datas[i][22], 0) + "");// 年利率(%)
            map.put("raiseTerm", Convert.strToInt(datas[i][23], 3) + "");// 筹标期限（天）
            map.put("moneyPurposes", Convert.strToStr(datas[i][24], ""));// 贷款资金用途
            map.put("borrowadvisory", Convert.strToStr(datas[i][25], ""));// 借款咨询方
            map.put("advisorybranch", Convert.strToStr(datas[i][26], ""));// 咨询方分行
            int borrowGroup = 0;
            if (datas[i].length >= 28) {
                try {
                    borrowGroup = Integer.parseInt(datas[i][27]);
                } catch (Exception e) {
                    log.info("borrowGroup is not a number.----" + datas[i][27]);
                    ;
                }
            }
            map.put("borrowGroup", borrowGroup + "");// 标的所属群组

            /**
             * 2014-10-14 增加导入业务编码 zhangyunhua
             */
            if (datas[i].length >= 29) {
                map.put("businessNo", Convert.strToStr(datas[i][28], ""));// 业务编号
            }
            list.add(map);
        }

        String ip = ((Admin) session().getAttribute(IConstants.SESSION_ADMIN)).getLastIP();
        List<String> ret = borrowManageService.importDatasHHN(list, ip, getBasePath(),null,BorrowManageService.OPERATE_TYPE_IMPORT);
        request().setAttribute("result", ret);
        return SUCCESS;
    }

    /**
     * 获取还款方式
     * 
     * @param paymentModel
     * @return
     * @author: liuzgmf
     * @date: 2014年12月30日上午10:19:48
     */
    private String getPaymentMode(String paymentModel) {
        if (StringUtils.isBlank(paymentModel)) {// 默认为等本等息
            return "1";
        }
        if (paymentModel.equals("IIFP") || paymentModel.equals("2")) {// 一次付息到期还本
            return "2";
        }
        if (paymentModel.equals("MIFP") || paymentModel.equals("3")) {// 按月付息到期还本
            return "3";
        }
        if (paymentModel.equals("PI") || paymentModel.equals("4")) {// 一次付息到期还本
            return "4";
        }
        if (paymentModel.equals("JTD") || paymentModel.equals("5")) {// 集团贷还款方式
            return "5";
        }
        
        if (paymentModel.equals("E_FPIC") || paymentModel.equals("101")) {// 集团贷还款方式
            return "101";
        }
        
        if (paymentModel.equals("E_LP") || paymentModel.equals("102")) {// 集团贷还款方式
            return "102";
        }
        
        if (paymentModel.equals("E_PI") || paymentModel.equals("103")) {// 集团贷还款方式
            return "103";
        }
        
        
        int value = Convert.strToInt(paymentModel, 0);
        if(value > 0){
            return value + "";
        }
        return "1";
    }

    /** 后台 撮合借款 查看借款进度 */
    public String queryInvestPrecentById() throws SQLException {
        String borrowId = request("id");
        borrowManageService.queryInvestPrecentById(pageBean, borrowId);
        return SUCCESS;
    }

}