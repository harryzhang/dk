/**  
 * @Project: hehenian-web
 * @Package com.hehenian.web.view.loan.action
 * @Title: LoanInfoAction.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年4月20日 下午3:21:36
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.web.view.loan.action;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.loan.ILoanInfoService;
import com.hehenian.biz.common.loan.dataobject.LoanInfoDo;
import com.hehenian.biz.common.loan.dataobject.LoanInfoDo.LoanStatus;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.web.common.util.ServletUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.shove.Convert;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.util.WebUtil;

/**
 * 
 * @author: liuzgmf
 * @date 2015年4月20日 下午3:21:36
 */
@Scope("prototype")
@Component("loanInfoAction")
public class LoanInfoAction extends ActionSupport implements ServletRequestAware {
    private static final long   serialVersionUID = 1L;
    private final Logger        logger           = Logger.getLogger(this.getClass());
    private DecimalFormat       df               = new DecimalFormat("##0.00");
    private HttpServletRequest  request;
    @Autowired
    private ILoanInfoService    loanInfoService;
    @Autowired
    private IUserService        userService;
    @Autowired
    private BorrowManageService borrowManageService;
    private LoanInfoDo          loanInfoDo;
    private Long                loanInfoId;
    private LoanStatus          loanStatus;
    private List<Long>          loanInfoIdList;

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
        Map<String, Object> param = new LinkedHashMap<String, Object>();
        param.put("realName", "eric");
        param.put("sex", "男");
        param.put("age", 30);
        param.put("marriaged", "己婚");

        param.put("residenceType", "买房");
        param.put("education", "本科");
        param.put("creditNum", 11l);
        param.put("creditAmt", 30000d);
        param.put("idNo", "542124198005267493");

        param.put("carQty", 0);
        param.put("companyName", "合肥赛兰德家具有限公司");
        param.put("companyAddress", "亳州路135号天庆大厦502室");
        param.put("position", "总经理");
        param.put("companyType", "私营企业 ");

        param.put("workYear", "10.8");
        param.put("industry", "居家生活用品");
        param.put("income", 520000d);
        param.put("expense", 173333.33);
        param.put("loanAmt", 60000d);

        param.put("loanPeriod", 12);
        param.put("productType", "生意贷");
        param.put("repayType", "1");//
        param.put("loanAnnualRate", 27.6d);
        param.put("annualRate", 10.8d);
        param.put("tenderDay", 5);

        param.put("loanUsage", "家具公司经营周转");
        param.put("consultant", "合和年信贷");
        param.put("consultantBranch", "合肥分行");
        param.put("borrowGroup", "0");
        param.put("businessNo", " 000001");

        List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
        params.add(param);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(params);
        System.out.println(jsonString);
        System.out.println(DigestUtils.md5Hex(WebConstants.XD_PASS_KEY + jsonString + WebConstants.XD_PASS_KEY));
    }

    /**
     * 新增借款标的信息
     * 
     * @return
     * @author: liuzgmf
     * @date: 2014年12月11日上午10:47:22
     */
    public String addLoanInfo() {
        String jsonString = request.getParameter("params");
        String sign = request.getParameter("sign");
        logger.info("params:" + jsonString + ",sign:" + sign);
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isBlank(jsonString)
                || StringUtils.isBlank(sign)
                || !DigestUtils.md5Hex(WebConstants.XD_PASS_KEY + jsonString + WebConstants.XD_PASS_KEY)
                        .equalsIgnoreCase(sign)) {
            jsonObject.put("success", false);
            jsonObject.put("message", "请求参数非法!");
            ServletUtils.writeJson(jsonObject.toString());
            return null;
        }
        List<LoanInfoDo> loanInfoDoList = getLoanInfos(jsonString);
        IResult<?> result = loanInfoService.addLoanInfo(loanInfoDoList);
        if (result.isSuccess()) {
            jsonObject.put("success", true);
            jsonObject.put("message", "操作成功!");
            ServletUtils.writeJson(jsonObject.toString());
            return null;
        } else {
            jsonObject.put("success", false);
            jsonObject.put("message", result.getErrorMessage());
            ServletUtils.writeJson(jsonObject.toString());
            return null;
        }
    }

    /**
     * 获取请求参数中的借款标的信息
     * 
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午3:46:52
     */
    private List<LoanInfoDo> getLoanInfos(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> params = mapper.readValue(jsonString,
                    new TypeReference<List<HashMap<String, Object>>>() {
                    });

            List<LoanInfoDo> loanInfoDoList = new ArrayList<LoanInfoDo>();
            for (Map<String, Object> map : params) {
                LoanInfoDo loanInfoDo = new LoanInfoDo();
                loanInfoDo.setRealName((String) map.get("realName"));
                loanInfoDo.setSex((String) map.get("sex"));
                loanInfoDo.setAge((Integer) map.get("age"));
                loanInfoDo.setMarriaged((String) map.get("marriaged"));

                loanInfoDo.setResidenceType((String) map.get("residenceType"));
                loanInfoDo.setEducation((String) map.get("education"));
                loanInfoDo.setCreditNum((Integer) map.get("creditNum"));
                loanInfoDo.setCreditAmt((Double) map.get("creditAmt"));
                loanInfoDo.setIdNo((String) map.get("idNo"));

                loanInfoDo.setCarQty((Integer) map.get("carQty"));
                loanInfoDo.setCompanyName((String) map.get("companyName"));
                loanInfoDo.setCompanyAddress((String) map.get("companyAddress"));
                loanInfoDo.setPosition((String) map.get("position"));
                loanInfoDo.setCompanyType((String) map.get("companyType"));

                loanInfoDo.setWorkYear((String) map.get("workYear"));
                loanInfoDo.setIndustry((String) map.get("industry"));
                loanInfoDo.setIncome((Double) map.get("income"));
                loanInfoDo.setExpense((Double) map.get("expense"));
                loanInfoDo.setLoanAmt((Double) map.get("loanAmt"));

                loanInfoDo.setLoanPeriod((Integer) map.get("loanPeriod"));
                loanInfoDo.setProductType((String) map.get("productType"));
                loanInfoDo.setRepayType(Integer.parseInt((String) map.get("repayType")));
                loanInfoDo.setLoanAnnualRate((Double) map.get("loanAnnualRate"));
                loanInfoDo.setAnnualRate((Double) map.get("annualRate"));

                loanInfoDo.setTenderDay((Integer) map.get("tenderDay"));
                loanInfoDo.setLoanUsage((String) map.get("loanUsage"));
                loanInfoDo.setConsultant((String) map.get("consultant"));
                loanInfoDo.setConsultantBranch((String) map.get("consultantBranch"));
                loanInfoDo.setBorrowGroup((String) map.get("borrowGroup"));

                loanInfoDo.setBusinessNo((String) map.get("businessNo"));

                loanInfoDo.setLoanStatus(LoanStatus.UNRELEASE);
                loanInfoDoList.add(loanInfoDo);
            }
            return loanInfoDoList;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", false);
            jsonObject.put("message", "请求参数非法," + e.getMessage());
            ServletUtils.writeJson(jsonObject.toString());
            return null;
        }
    }

    /**
     * 根据条件查询借款标的信息
     * 
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午3:50:36
     */
    public String queryLoanInfos() {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        if (loanInfoDo == null) {
            loanInfoDo = new LoanInfoDo();
        }
        if (StringUtils.isNotBlank(loanInfoDo.getRealName())) {
            searchItems.put("realName", loanInfoDo.getRealName());
        }
        if (StringUtils.isNotBlank(loanInfoDo.getProductType())) {
            searchItems.put("productType", loanInfoDo.getProductType());
        }
        if (loanInfoDo.getLoanStatus() != null) {
            searchItems.put("loanStatus", loanInfoDo.getLoanStatus());
        }
        long currentPage = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request
                .getParameter("curPage")) : 1);
        long pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request
                .getParameter("pageSize")) : 10);
        long beginCount = (currentPage - 1) * pageSize;
        searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
        searchItems.put("pageSize", pageSize);
        NPageDo<LoanInfoDo> pageDo = loanInfoService.queryLoanInfos(searchItems);
        pageDo.setCurrentPage(currentPage);
        pageDo.setPageSize(pageSize);
        request.setAttribute("pageDo", pageDo);
        return SUCCESS;
    }

    public String getByLoanInfoId() {
        String loanInfoId = request.getParameter("loanInfoId");
        if (StringUtils.isBlank(loanInfoId)) {
            request.setAttribute(WebConstants.MESSAGE_KEY, "参数有误!");
            return ERROR;
        }
        loanInfoDo = loanInfoService.getByLoanInfoId(Long.parseLong(loanInfoId));
        return SUCCESS;
    }

    public String updateLoanInfo() {
        if (loanInfoDo == null || loanInfoDo.getLoanInfoId() == null) {
            request.setAttribute(WebConstants.MESSAGE_KEY, "参数有误!");
            return ERROR;
        }
        IResult<?> result = loanInfoService.updateLoanInfo(loanInfoDo);
        if (result.isSuccess()) {
            request.setAttribute(WebConstants.MESSAGE_KEY, "操作成功!");
            return SUCCESS;
        } else {
            request.setAttribute(WebConstants.MESSAGE_KEY, "参数有误!");
            return ERROR;
        }
    }

    /**
     * 发布借款标的
     * 
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午4:15:36
     */
    public String releaseLoanInfo() {
        JSONObject json = new JSONObject();
        if (loanInfoId == null || loanInfoId <= 0 || loanStatus == null
                || (!loanStatus.equals(LoanStatus.TOCHINAPNR) && !loanStatus.equals(LoanStatus.TODEPOSIT))) {
            json.put("success", false);
            json.put("message", "参数有误!");
            ServletUtils.writeJson(json.toString());
            return null;
        }
        loanInfoIdList = new ArrayList<Long>();
        loanInfoIdList.add(loanInfoId);
        if (LoanStatus.TOCHINAPNR.equals(loanStatus)) {
            json = releaseToChinapnr();
        } else {
            json = releaseToDeposit();
        }
        ServletUtils.writeJson(json.toString());
        return null;

    }

    /**
     * 发布借款标的定存系统
     * 
     * @return
     * @author: liuzgmf
     * @date: 2015年4月21日上午9:49:23
     */
    private JSONObject releaseToDeposit() {
        IResult<?> result = loanInfoService.addLoanDetail(loanInfoIdList);
        JSONObject jsonObject = new JSONObject();
        if (result != null && result.isSuccess()) {
            jsonObject.put("success", true);
            jsonObject.put("message", "操作成功!");
        } else {
            jsonObject.put("success", result.isSuccess());
            jsonObject.put("message", result.getErrorMessage());
        }
        return jsonObject;
    }

    /**
     * 发布借款标的资金托管系统
     * 
     * @return
     * @author: liuzgmf
     * @date: 2015年4月21日上午9:48:19
     */
    private JSONObject releaseToChinapnr() {
        List<LoanInfoDo> loanInfoDoList = loanInfoService.queryByLoanInfoIds(loanInfoIdList);
        List<Map<String, String>> loanInfoList = new ArrayList<Map<String, String>>();
        for (LoanInfoDo loanInfoDo : loanInfoDoList) {
            HashMap<String, String> map = new HashMap<String, String>();
            AccountUserDo userDo = userService.getByIdNo(loanInfoDo.getIdNo());
            map.put("loanInfoId", loanInfoDo.getLoanInfoId().toString());
            map.put("userId", userDo.getId() + "");
            map.put("idNo", loanInfoDo.getIdNo());

            map.put("realName", loanInfoDo.getRealName());// 客户名称
            map.put("sex", loanInfoDo.getSex());// 性别
            map.put("age", loanInfoDo.getAge() + "");// 年龄
            map.put("maritalStatus", loanInfoDo.getMarriaged());// 婚姻状况
            map.put("hasHourse", loanInfoDo.getResidenceType());// 居住状况
            map.put("highestEdu", loanInfoDo.getEducation());// 学历
            map.put("creditNum", loanInfoDo.getCreditNum() + "");// 信用卡总张数
            map.put("creditSum", loanInfoDo.getCreditAmt() + "");// 信用卡总额度
            map.put("hasCar", loanInfoDo.getCarQty() + "");// 车辆信息

            map.put("orgName", loanInfoDo.getCompanyName());// 公司名称
            map.put("companyAddress", loanInfoDo.getCompanyAddress());// 公司地址
            map.put("job", loanInfoDo.getPosition());// 职位级别
            map.put("companyType", loanInfoDo.getCompanyType());// 企业性质
            map.put("workYear", loanInfoDo.getWorkYear() + "");// 现公司工作年限
            map.put("companyLine", loanInfoDo.getIndustry());// 公司行业
            map.put("monthlyIncome", loanInfoDo.getIncome() + "");// 月收入
            map.put("monthlyOutcome", loanInfoDo.getExpense() + "");// 月支出合计

            map.put("borrowAmount", df.format(loanInfoDo.getLoanAmt()));// 借款金额
            map.put("deadline", loanInfoDo.getLoanPeriod() + "");// 申请期限
            map.put("borrowWay", Convert.strToInt(loanInfoDo.getProductType(), 1) + "");// 产品类型1=薪金贷2=生意贷3=业主贷
            map.put("paymentMode", loanInfoDo.getRepayType() + "");// 还款方式
            map.put("annualRate", df.format(loanInfoDo.getAnnualRate()));// 年利率(%)
            map.put("raiseTerm", loanInfoDo.getTenderDay() + "");// 筹标期限（天）
            map.put("moneyPurposes", loanInfoDo.getLoanUsage());// 贷款资金用途
            map.put("borrowadvisory", loanInfoDo.getConsultant());// 借款咨询方
            map.put("advisorybranch", loanInfoDo.getConsultantBranch());// 咨询方分行
            if (StringUtils.isNotBlank(loanInfoDo.getBorrowGroup())) {
                map.put("borrowGroup", loanInfoDo.getBorrowGroup());// 标的所属群组
            } else {
                map.put("borrowGroup", "0");// 标的所属群组
            }
            map.put("businessNo", loanInfoDo.getBusinessNo());// 业务编号
            loanInfoList.add(map);
        }

        String ip = ((Admin) request.getSession().getAttribute(IConstants.SESSION_ADMIN)).getLastIP();
        List<Map<String, String>> newLoanInfoList = new ArrayList<Map<String, String>>();
        JSONObject result = new JSONObject();
        List<String> ret;
        try {
            ret = borrowManageService.importDatasHHN(loanInfoList, ip, WebUtil.getWebPath(), newLoanInfoList,
                    BorrowManageService.OPERATE_TYPE_DISPATCH);
            for (Map<String, String> newLoanInfo : newLoanInfoList) {
                LoanInfoDo loanInfoDo = new LoanInfoDo();
                loanInfoDo.setLoanInfoId(Long.parseLong(newLoanInfo.get("loanInfoId")));
                loanInfoDo.setLoanStatus(LoanStatus.TOCHINAPNR);
                loanInfoService.updateLoanInfo(loanInfoDo);
            }
            result.put("success", true);
            result.put("message", handleDispatchMsg(ret));
        } catch (Exception e) {
            logger.error("发布借款异常!" + e.getMessage(), e);
            result.put("success", false);
            result.put("message", "发布借款异常!");
        }
        return result;
    }

    private String handleDispatchMsg(List<String> list) {
        StringBuffer buffer = new StringBuffer();
        for (String str : list) {
            buffer.append(str);
            buffer.append("\r\n");
        }
        return buffer.toString();
    }

    /**
     * @return loanInfoDo
     */
    public LoanInfoDo getLoanInfoDo() {
        return loanInfoDo;
    }

    /**
     * @param loanInfoDo
     *            the loanInfoDo to set
     */
    public void setLoanInfoDo(LoanInfoDo loanInfoDo) {
        this.loanInfoDo = loanInfoDo;
    }

    /**
     * @return loanInfoId
     */
    public Long getLoanInfoId() {
        return loanInfoId;
    }

    /**
     * @param loanInfoId
     *            the loanInfoId to set
     */
    public void setLoanInfoId(Long loanInfoId) {
        this.loanInfoId = loanInfoId;
    }

    /**
     * @return loanStatus
     */
    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    /**
     * @param loanStatus
     *            the loanStatus to set
     */
    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    /**
     * @return loanInfoIdList
     */
    public List<Long> getLoanInfoIdList() {
        return loanInfoIdList;
    }

    /**
     * @param loanInfoIdList
     *            the loanInfoIdList to set
     */
    public void setLoanInfoIdList(List<Long> loanInfoIdList) {
        this.loanInfoIdList = loanInfoIdList;
    }

}
