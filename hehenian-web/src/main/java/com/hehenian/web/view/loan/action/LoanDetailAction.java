/**  
 * @Project: hehenian-web
 * @Package com.hehenian.web.view.loan.action
 * @Title: LoanDetailAction.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月11日 上午10:00:04
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.web.view.loan.action;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.loan.ILoanDetailService;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo.LoanStatus;
import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.trade.ISettleCalculatorService;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.web.common.util.ServletUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author: liuzgmf
 * @date 2014年12月11日 上午10:00:04
 */
@Scope("prototype")
@Component("loanDetailAction")
public class LoanDetailAction extends ActionSupport implements ServletRequestAware {
    private static final long           serialVersionUID = 1L;
    private final Logger                logger           = Logger.getLogger(this.getClass());
    private DecimalFormat               df               = new DecimalFormat("##0.00");
    @Autowired
    private ILoanDetailService          loanDetailService;
    @Autowired
    private ISettleCalculatorService    settleCalculatorService;
    private LoanDetailDo                loanDetailDo     = new LoanDetailDo();
    private HttpServletRequest          request;

    private static Map<Integer, Double> creditRateMap    = new HashMap<Integer, Double>();

    static {
        // 借款期限3个月授信比例
        creditRateMap.put(1, 1.5);
        creditRateMap.put(2, 2.0);
        creditRateMap.put(3, 2.5);
        creditRateMap.put(4, 3.0);
        creditRateMap.put(5, 3.5);
        // 借款期限6个月授信比例
        creditRateMap.put(6, 3.0);
        creditRateMap.put(7, 3.5);
        creditRateMap.put(8, 4.0);
        creditRateMap.put(9, 4.5);
        creditRateMap.put(10, 5.0);
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * @return loanDetailDo
     */
    public LoanDetailDo getLoanDetailDo() {
        return loanDetailDo;
    }

    /**
     * @param loanDetailDo
     *            the loanDetailDo to set
     */
    public void setLoanDetailDo(LoanDetailDo loanDetailDo) {
        this.loanDetailDo = loanDetailDo;
    }

    /**
     * 新增借款申请信息
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年12月11日上午10:47:22
     */
    public String addLoanDetail() {
        IResult<?> result = loanDetailService.addLoanDetail(loanDetailDo);
        if (result.isSuccess()) {
            request.setAttribute(WebConstants.MESSAGE_KEY, "借款申请成功!");
            return SUCCESS;
        } else {
            request.setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
            return ERROR;
        }
    }

    /**
     * 修改借款申请借款的状态
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年12月16日上午11:07:57
     */
    public String changeLoanStatus() {
        String loanId = request.getParameter("loanId");
        String status = request.getParameter("loanStatus");

        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isBlank(loanId) || StringUtils.isBlank(status)) {
            jsonObject.put("success", false);
            jsonObject.put("message", "参数有误!");
            ServletUtils.writeJson(jsonObject.toString());
            return null;
        }
        LoanStatus loanStatus = (status.equals("CHECKED") ? LoanStatus.CHECKED : LoanStatus.UNCHECKED);
        IResult<?> result = loanDetailService.changeLoanStatus(Long.parseLong(loanId), loanStatus);
        if (result.isSuccess()) {
            jsonObject.put("success", true);
            jsonObject.put("message", "操作成功!");
            ServletUtils.writeJson(jsonObject.toString());
            return null;
        } else {
            jsonObject.put("success", false);
            jsonObject.put("message", "操作失败!");
            ServletUtils.writeJson(jsonObject.toString());
            return null;
        }
    }

    /**
     * 计算授信金额
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年12月15日上午9:27:47
     */
    public String calCreditAmount() {
        // 如果月收入或借款期限为空，则返回错误信息
        if (StringUtils.isBlank(request.getParameter("income"))
                || StringUtils.isBlank(request.getParameter("loanPeriod"))) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", true);
            jsonObject.put("errorMessage", "计算出错，请刷新页面后再试!");
            ServletUtils.write(jsonObject.toString());
            return null;
        }

        double income = Double.parseDouble(request.getParameter("income"));
        int loanPeriod = Integer.parseInt(request.getParameter("loanPeriod"));
        loanPeriod = (loanPeriod < 6 ? 3 : 6);// 借款期限小于6个月，则按3个月计算，大于等于6个月，则按6个月计算
        int index = 1;
        if (CalculateUtils.le(income, 3000)) {
            index = 1;
        }
        if (CalculateUtils.gt(income, 3000) && CalculateUtils.le(income, 5000)) {
            index = 2;
        }
        if (CalculateUtils.gt(income, 5000) && CalculateUtils.le(income, 8000)) {
            index = 3;
        }
        if (CalculateUtils.gt(income, 8000) && CalculateUtils.le(income, 10000)) {
            index = 4;
        }
        if (CalculateUtils.gt(income, 10000)) {
            index = 5;
        }
        index = (loanPeriod == 6 ? (index + 5) : index);
        double creditAmount = CalculateUtils.round(CalculateUtils.mul(income, creditRateMap.get(index)), 0);
        // 借款期限为6个月，最高可借贷100000元
        if (loanPeriod == 6 && CalculateUtils.gt(creditAmount, 100000)) {
            creditAmount = 100000;
        }
        // 借款期限为3个月，最高可借贷70000元
        if (loanPeriod == 3 && CalculateUtils.gt(creditAmount, 70000)) {
            creditAmount = 70000;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", false);
        jsonObject.put("creditAmount", creditAmount);
        ServletUtils.write(jsonObject.toString());
        return null;
    }

    /**
     * 计算还款明细金额（本金，利息，还款总额）
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年12月15日上午11:27:39
     */
    public String calRepayDetail() {
        double loanAmount = Double.parseDouble(request.getParameter("loanAmount"));
        int loanPeriod = Integer.parseInt(request.getParameter("loanPeriod"));
        loanPeriod = (loanPeriod < 6 ? 3 : 6);// 借款期限小于6个月，则按3个月计算，大于等于6个月，则按6个月计算
        double annualRate = (loanPeriod == 3 ? 8.4 : 9.0);// 借款期限为3个月为8.4%，6个月为9%
        List<SettDetailDo> settDetailDoList = settleCalculatorService.calSettDetail(loanAmount, annualRate, loanPeriod,
                5l);
        List<Map<String, String>> repayDetailList = new ArrayList<Map<String, String>>(settDetailDoList.size());
        double totalAmount = 0.00;
        for (SettDetailDo detailDo : settDetailDoList) {
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("times", detailDo.getPeriod() + "");
            map.put("principal", df.format(detailDo.getPrincipal()));
            double interest = CalculateUtils.add(CalculateUtils.add(detailDo.getInterest(), detailDo.getConsultFee()),
                    detailDo.getServFee());
            map.put("interest", df.format(interest));
            double repayAmount = CalculateUtils.add(interest, detailDo.getPrincipal());
            map.put("repayAmount", df.format(repayAmount));
            totalAmount = CalculateUtils.add(totalAmount, repayAmount);
            map.put("totalAmount", df.format(totalAmount));
            repayDetailList.add(map);
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(repayDetailList);
            ServletUtils.writeJson(jsonString);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String queryLoanDetails() {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        searchItems.put("idNo", loanDetailDo.getIdNo());
        searchItems.put("mobile", loanDetailDo.getMobile());
        searchItems.put("loanStatus", loanDetailDo.getLoanStatus());
        long currentPage = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request
                .getParameter("curPage")) : 1);
        long pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request
                .getParameter("pageSize")) : 10);
        long beginCount = (currentPage - 1) * pageSize;
        searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
        searchItems.put("pageSize", pageSize);
        NPageDo<LoanDetailDo> pageDo = loanDetailService.queryLoanDetails(searchItems);
        pageDo.setCurrentPage(currentPage);
        pageDo.setPageSize(pageSize);
        request.setAttribute("pageDo", pageDo);
        return SUCCESS;
    }

    /**
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年12月24日下午2:22:30
     */
    public String exportLoanDetails() {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        searchItems.put("idNo", loanDetailDo.getIdNo());
        searchItems.put("mobile", loanDetailDo.getMobile());
        searchItems.put("loanStatus", loanDetailDo.getLoanStatus());
        long currentPage = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request
                .getParameter("curPage")) : 1);
        long pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request
                .getParameter("pageSize")) : 10);
        long beginCount = (currentPage - 1) * pageSize;
        searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
        searchItems.put("pageSize", pageSize);
        NPageDo<LoanDetailDo> pageDo = loanDetailService.queryLoanDetails(searchItems);
        ServletOutputStream out = null;
        try {
            // Write the output to a file
            HSSFWorkbook wb = createWorkbook(pageDo.getModelList());
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSS") + ".xls");
            out = ServletActionContext.getResponse().getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    private HSSFWorkbook createWorkbook(List<LoanDetailDo> loanDetailDoList) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        List<Object[]> rows = new ArrayList<Object[]>(loanDetailDoList.size());
        rows.add(new Object[] { "个人资料" });

        rows.add(new Object[] { "客户名称", "性别", "年龄", "婚姻状况", "居住状况", "学历", "信用卡总张数", "信用卡总额度", "申请者身份证号码", "车辆总数量",
                "公司名称", "公司地址", "职位级别", "企业性质", "现公司工作年限", "公司行业", "月收入 ", "支出合计", "借款金额", "申请期限", "产品类型", "*还款方式",
                "*年利率(%)", "*筹标期限（天）", " 贷款资金用途", "借款咨询方", "咨询方分行", "所属专区", "业务编号" });

        for (LoanDetailDo loanDetailDo : loanDetailDoList) {
            double annualRate = (loanDetailDo.getLoanPeriod() == 3 ? 8.4 : 9.0);
            rows.add(new Object[] { loanDetailDo.getRealName(), loanDetailDo.getSex(), loanDetailDo.getAge(), "已婚",
                    "买房", "本科及以上", 0, 0, loanDetailDo.getIdNo(), 0, "花样年集团（中国）有限公司", "深圳市福田区保税区福年广场", "高级经理", "股份制企业",
                    3, "商业服务", loanDetailDo.getIncome(), 2000.00, loanDetailDo.getLoanAmount(),
                    loanDetailDo.getLoanPeriod(), 1, "JTD", annualRate, 5, "消费贷", "合和年在线", "合和年在线", 0, "000001" });
        }

        int rownum = 0;
        for (Object[] data : rows) {
            HSSFRow row = sheet.createRow(rownum++);
            int cellnum = 0;
            for (Object obj : data) {
                sheet.autoSizeColumn((short) cellnum);
                HSSFCell cell = row.createCell((short) (cellnum++));
                if (obj instanceof Date)
                    cell.setCellValue((Date) obj);
                else if (obj instanceof Boolean)
                    cell.setCellValue((Boolean) obj);
                else if (obj instanceof String)
                    cell.setCellValue(new HSSFRichTextString((String) obj));
                else if (obj instanceof Double)
                    cell.setCellValue((Double) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
                else if (obj instanceof Short)
                    cell.setCellValue((Short) obj);
                else if (obj instanceof Long)
                    cell.setCellValue((Long) obj);
            }
        }
        return wb;
    }

    /**
     * 根据身份证号码查询借款申请信息
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年12月16日下午7:33:10
     */
    public String getByIdNo() {
        String idNo = request.getParameter("idNo");
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isBlank(idNo)) {
            jsonObject.put("error", true);
            jsonObject.put("message", "参数有误!");
            ServletUtils.write(jsonObject.toString());
            return null;
        }
        LoanDetailDo loanDetailDo = loanDetailService.getByIdNo(idNo);
        if (loanDetailDo == null) {
            jsonObject.put("error", true);
            jsonObject.put("message", "身份证号码不存在!");
            ServletUtils.write(jsonObject.toString());
            return null;
        }
        jsonObject.put("error", false);
        jsonObject.put("realName", loanDetailDo.getRealName());
        jsonObject.put("loanStatus", loanDetailDo.getLoanStatus());
        jsonObject.put("applyDate", DateFormatUtils.format(loanDetailDo.getCreateTime(), "yyyy-MM-dd"));
        if (loanDetailDo.getLoanStatus().equals(LoanStatus.CHECKED)
                || loanDetailDo.getLoanStatus().equals(LoanStatus.LOANS)) {
            jsonObject.put("auditDate", DateFormatUtils.format(loanDetailDo.getUpdateTime(), "yyyy-MM-dd"));
        } else {
            jsonObject.put("auditDate", "");
        }
        if (loanDetailDo.getLoanStatus().equals(LoanStatus.LOANS)) {
            jsonObject.put("loanDate", DateFormatUtils.format(loanDetailDo.getBorrowDo().getAuditTime(), "yyyy-MM-dd"));
        } else {
            jsonObject.put("loanDate", "");
        }
        ServletUtils.write(jsonObject.toString());
        return null;
    }

    /**
     * 根据借款状态查询借款申请信息
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年12月11日上午10:47:36
     */
    public String queryUncheckedLoanDetails() {
        List<LoanDetailDo> loanDetailDoList = loanDetailService.queryByLoanStatus(LoanStatus.PROCESSING);
        List<Map<String, String>> loanDetailList = new ArrayList<Map<String, String>>(loanDetailDoList.size());
        for (LoanDetailDo loanDetailDo : loanDetailDoList) {
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("loanId", loanDetailDo.getLoanId() + "");
            map.put("realName", loanDetailDo.getRealName());
            map.put("idNo", loanDetailDo.getIdNo());
            map.put("mobile", loanDetailDo.getMobile());
            map.put("income", df.format(loanDetailDo.getIncome()));
            loanDetailList.add(map);
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(loanDetailList);
            ServletUtils.writeJson(jsonString);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 通知借款申请校验结果
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年12月11日下午4:48:56
     */
    public String checkLoanDetail() {
        String jsonString = request.getParameter("checkDetail");
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<HashMap<String, String>> params = mapper.readValue(jsonString,
                    new TypeReference<List<HashMap<String, String>>>() {
                    });
            if (params == null || params.size() == 0) {
                return null;
            }
            List<LoanDetailDo> loanDetailDoList = new ArrayList<LoanDetailDo>(params.size());
            for (HashMap<String, String> map : params) {
                LoanDetailDo loanDetailDo = new LoanDetailDo();
                loanDetailDo.setLoanId(Long.parseLong(map.get("loanId")));
                if (map.get("checked") != null && map.get("checked").equals("true")) {
                    loanDetailDo.setLoanStatus(LoanStatus.CHECKED);
                } else {
                    loanDetailDo.setLoanStatus(LoanStatus.UNCHECKED);
                    loanDetailDo.setCheckDesc(getCheckDesc(map));
                }
                loanDetailDoList.add(loanDetailDo);
            }
            loanDetailService.updateLoanDetail(loanDetailDoList);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 获取借款申请验证描叙
     * 
     * @param map
     * @return
     * @author: liuzgmf
     * @date: 2014年12月11日下午2:15:50
     */
    private String getCheckDesc(HashMap<String, String> map) {
        StringBuffer checkedDesc = new StringBuffer();
        // 真实姓名
        String checked = map.get("realName");
        if (StringUtils.isNotBlank(checked)) {
            checkedDesc.append("realName:" + checked + ";");
        }
        // 身份证号码
        checked = map.get("idNo");
        if (StringUtils.isNotBlank(checked)) {
            checkedDesc.append("idNo:" + checked + ";");
        }
        // 手机号码
        checked = map.get("mobile");
        if (StringUtils.isNotBlank(checked)) {
            checkedDesc.append("mobile:" + checked + ";");
        }
        // 月收入
        checked = map.get("income");
        if (StringUtils.isNotBlank(checked)) {
            checkedDesc.append("income:" + checked + ";");
        }
        if (checkedDesc.length() > 1) {
            return (checkedDesc.substring(0, checkedDesc.length() - 1));
        } else {
            return null;
        }
    }

}
