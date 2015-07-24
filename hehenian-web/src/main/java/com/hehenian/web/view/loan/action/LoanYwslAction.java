package com.hehenian.web.view.loan.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.axis.encoding.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.filesaving.IFileServerService;
import com.hehenian.biz.common.loan.ILoanPersonService;
import com.hehenian.biz.common.loan.dataobject.CertificateDo;
import com.hehenian.biz.common.loan.dataobject.CertificateDo.CertificateType;
import com.hehenian.biz.common.loan.dataobject.CertificateDo.FileType;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo.LoanStatus;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.web.common.util.ServletUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.shove.Convert;
import com.shove.web.util.JSONUtils;
import com.sp2p.entity.Admin;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.util.WebUtil;

@Scope("prototype")
@Component("loanYwslAction")
public class LoanYwslAction extends ActionSupport implements ServletRequestAware {
    private static final long   serialVersionUID = 1L;
    private HttpServletRequest  request;
    @Autowired
    private ILoanPersonService  loanPersonService;
    @Autowired
    private IUserService        accountUserService;
    @Autowired
    private BorrowManageService borrowManageService;
    private LoanPersonDo        loanPersonDo     = new LoanPersonDo();
    @Autowired
    private IFileServerService  fileServerService;
    @Autowired
    private UserService         userService;
    private File[]              files;
    private String[]            filesContentType;
    private String[]            filesFileName;

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 财富管家接入
     * 
     * @return
     * @author: liuzgmf
     * @date: 2015年1月23日下午2:13:11
     */
    public String index() {
        String data = request.getParameter("param");
        String sign = request.getParameter("sign");
        if (StringUtils.isBlank(data) || StringUtils.isBlank(sign)) {
            request.setAttribute("message", "参数有误!");
            return ERROR;
        }

        try {
            String jsonString = new String(Base64.decode(data), "utf-8");
            System.out.println("jsonString:"+jsonString);
            String secret = "HHN&XD#$%CD%des$";
           /* if (!DigestUtils.md5Hex(secret + jsonString + secret).equalsIgnoreCase(sign)) {
                request.setAttribute("message", "请求非法!");
                return ERROR;
            }*/
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> params = mapper.readValue(jsonString, new TypeReference<HashMap<String, String>>() {
            });
            System.out.println("************************************");
            System.out.println(params.toString());
            System.out.println("************************************");
            request.getSession().setAttribute(WebConstants.COLOURLIFE_ADMIN_USER, params);
            request.getSession().setAttribute("fileAccessUrl", fileServerService.getFileAccessUrl());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "程序异常!");
            return ERROR;
        }
    }

    /**
     * 新增借款人相关的借款和个人资料相关的信息
     * 
     * @return
     */
    public String addPerson() {
        IResult<?> result = loanPersonService.addLoanPerson(loanPersonDo);
        if (result.isSuccess()) {
            request.setAttribute(WebConstants.MESSAGE_KEY, "新增借款申请成功!");
            return SUCCESS;
        } else {
            request.setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
            return ERROR;
        }
    }

    /**
     * 修改借款申请成功 xiexiang
     * 
     * @return
     */
    public String updatePerson() {
        CertificateDo certificateDo = null;
        String filePath = null;
        List<CertificateDo> certificateDoList = loanPersonDo.getCertificateDoList();
        Map<String, String> params = (Map<String, String>) request.getSession().getAttribute(
                WebConstants.COLOURLIFE_ADMIN_USER);
        if (certificateDoList == null) {
            certificateDoList = new ArrayList<CertificateDo>();
            for (int i = 0; i < files.length; i++) {
                filePath = fileServerService.saveFile(files[i], filesFileName[i], new int[][] { { 400, 400 } });
                certificateDo = new CertificateDo();
                certificateDo.setLoanId(loanPersonDo.getLoanId());
                certificateDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
                certificateDo.setFilePath(filePath);
                certificateDo.setCreateUser(params.get("userName"));// 之后要获取值填充
                certificateDo.setCertificateName(filesFileName[i]);
                if (i == 0) {
                    certificateDo.setCertificateType(CertificateType.IDCARDZ);
                } else if (i == 1) {
                    certificateDo.setCertificateType(CertificateType.IDCARDF);
                } else if (i == 2) {
                    certificateDo.setCertificateType(CertificateType.HOUSE);
                } else if (i == 3) {
                    certificateDo.setCertificateType(CertificateType.JOB);
                } else if (i == 4) {
                    certificateDo.setCertificateType(CertificateType.INCOME);
                }
                certificateDo.setFileType(FileType.IMAGE);
                certificateDoList.add(certificateDo);
            }
        } else {
            int count = 0;
            for (int i = 0; i < certificateDoList.size(); i++) {
                certificateDo = certificateDoList.get(i);
                if ("".equals(certificateDo.getFilePath()) || certificateDo.getFilePath() == null) {
                    certificateDo.setFilePath(fileServerService.saveFile(files[count], filesFileName[count],
                            new int[][] { { 400, 400 } }));
                    certificateDo.setCertificateName(filesFileName[count]);
                    count++;
                }
                certificateDo.setCreateUser(params.get("userName"));// 之后要获取值填充
            }
        }
        loanPersonDo.setCertificateDoList(certificateDoList);
        loanPersonDo.setUpdateUser(params.get("userName"));
        IResult<?> result = loanPersonService.updateLoanPerson(loanPersonDo);
        if (result.isSuccess()) {
            if (loanPersonDo.getLoanDo() != null) {
                request.setAttribute(WebConstants.MESSAGE_KEY, "数据保存成功!");
                String loanStatus = loanPersonDo.getLoanDo().getLoanStatus() + "";
                if ("PROCESSING".equals(loanStatus)) {
                    request.setAttribute(WebConstants.MESSAGE_KEY, "提交成功!");
                }
            }
            request.setAttribute("c_window", "yes");
            return SUCCESS;
        } else {
            request.setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
            return ERROR;
        }
    }

    /**
     * 查询借款受理信息
     * 
     * @return
     */
    public String getLoanPerson() {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        if (!"".equals(loanPersonDo.getRealName())) {
            searchItems.put("realName", loanPersonDo.getRealName());
        }
        searchItems.put("mobile", loanPersonDo.getMobile());
        searchItems.put("loanStatus", LoanDo.LoanStatus.PENDING);
        Map<String, String> params = (Map<String, String>) request.getSession().getAttribute(
                WebConstants.COLOURLIFE_ADMIN_USER);
        searchItems.put("cname", params.get("areaName"));
        long currentPage = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request
                .getParameter("curPage")) : 1);
        long pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request
                .getParameter("pageSize")) : 10);
        long beginCount = (currentPage - 1) * pageSize;
        /** 预期收益 */
        Map<String, Object> map = loanPersonService.getYqsl(searchItems);
        String slExpectedEarnings = "0.00";
        if (map != null) {
            slExpectedEarnings = map.get("slExpectedEarnings") + "";
        }
        request.setAttribute("slExpectedEarnings", slExpectedEarnings);
        searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
        searchItems.put("pageSize", pageSize);
        NPageDo<LoanPersonDo> pageDo = loanPersonService.getLoanPerson(searchItems);
        pageDo.setCurrentPage(currentPage);
        pageDo.setPageSize(pageSize);
        request.setAttribute("pageDo", pageDo);
        return SUCCESS;
    }

    /**
     * 查询借款受理信息
     * 
     * @return
     */
    public String initAuditData() {
        request.getSession().setAttribute("fileAccessUrl", fileServerService.getFileAccessUrl());
        Map<String, Object> searchItems = new HashMap<String, Object>();
        if (!"".equals(loanPersonDo.getRealName())) {
            searchItems.put("realName", loanPersonDo.getRealName());
        }
        searchItems.put("mobile", loanPersonDo.getMobile());
        searchItems.put("loanStatus", LoanDo.LoanStatus.PROCESSING);
        long currentPage = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request
                .getParameter("curPage")) : 1);
        long pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request
                .getParameter("pageSize")) : 10);
        long beginCount = (currentPage - 1) * pageSize;
        searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
        searchItems.put("pageSize", pageSize);
        NPageDo<LoanPersonDo> pageDo = loanPersonService.queryLoanAuditeds(searchItems);
        pageDo.setCurrentPage(currentPage);
        pageDo.setPageSize(pageSize);
        request.setAttribute("pageDo", pageDo);
        return SUCCESS;
    }

    /**
     * 查询合约签订
     * 
     * @return
     */
    public String loanTreatyQuery() {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        if (!"".equals(loanPersonDo.getRealName())) {
            searchItems.put("realName", loanPersonDo.getRealName());
        }
        searchItems.put("mobile", loanPersonDo.getMobile());
        searchItems.put("loanStatus", LoanDo.LoanStatus.AUDITED);

        // 有哪些小区的权限
        Map<String, String> params = (Map<String, String>) request.getSession().getAttribute(
                WebConstants.COLOURLIFE_ADMIN_USER);
        searchItems.put("cname", params.get("areaName"));
        long currentPage = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request
                .getParameter("curPage")) : 1);
        long pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request
                .getParameter("pageSize")) : 10);
        long beginCount = (currentPage - 1) * pageSize;

        /** 预期收益 */
        Map<String, Object> map = loanPersonService.getYqsl(searchItems);
        String hyExpectedEarnings = "0.00";
        if (map != null) {
            if (!("null".equals(map.get("hyExpectedEarnings"))) && map.get("hyExpectedEarnings") != null) {
                hyExpectedEarnings = map.get("hyExpectedEarnings") + "";
            }
        }
        request.setAttribute("hyExpectedEarnings", hyExpectedEarnings);

        searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
        searchItems.put("pageSize", pageSize);
        NPageDo<LoanPersonDo> pageDo = loanPersonService.getLoanPerson(searchItems);
        pageDo.setCurrentPage(currentPage);
        pageDo.setPageSize(pageSize);
        request.setAttribute("pageDo", pageDo);
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    public String loanAutoQuery() {
        request.getSession().setAttribute("fileAccessUrl", fileServerService.getFileAccessUrl());
        Map<String, Object> searchItems = new HashMap<String, Object>();
        if (!"".equals(loanPersonDo.getRealName())) {
            searchItems.put("realName", loanPersonDo.getRealName());
        }
        searchItems.put("mobile", loanPersonDo.getMobile());
        searchItems.put("flagStatus", LoanDo.LoanStatus.TREATY);
        long currentPage = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request
                .getParameter("curPage")) : 1);
        long pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request
                .getParameter("pageSize")) : 10);
        long beginCount = (currentPage - 1) * pageSize;
        searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
        searchItems.put("pageSize", pageSize);
        NPageDo<LoanPersonDo> pageDo = loanPersonService.queryLoanAuditeds(searchItems);
        pageDo.setCurrentPage(currentPage);
        pageDo.setPageSize(pageSize);
        request.setAttribute("pageDo", pageDo);
        return SUCCESS;
    }

    /**
     * 初始化数据 xiexiang
     * 
     * @return
     */
    public String initData() {
        String loanId = request.getParameter("id");
        request.setAttribute("loanPersonDo", loanPersonService.getInitData(Long.parseLong(loanId)));
        return SUCCESS;
    }

    /**
     * 初始化合约的数据
     * 
     * @return
     */
    public String initTreatyData() {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        String loanId = request.getParameter("id");
        searchItems.put("loanId", loanId);
        request.setAttribute("loanPersonDo", loanPersonService.initTreatyData(searchItems));
        return SUCCESS;
    }

    /**
     * 改变审核信息
     * 
     * @return
     * @throws IOException
     */
    public String updateLoanShInfo() throws IOException, SQLException {
        try {
            Admin admin = (Admin) request.getSession().getAttribute("admin");
            if (admin != null) {
                loanPersonDo.getLoanDo().setAuditUserId(admin.getId());
                loanPersonDo.getLoanDo().setAuditUser(admin.getRealName());
            }
            IResult<?> result = loanPersonService.updateLoanShInfo(loanPersonDo);
            if (result.isSuccess()) {
                JSONUtils.printStr("1");
            } else {
                JSONUtils.printStr("2");
            }
        } catch (IOException e) {
            JSONUtils.printStr("2");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 改变提交状态
     * 
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public String changeloanStatus() throws IOException, SQLException {
        IResult<?> result = loanPersonService.updateLoanPerson(loanPersonDo);
        if (result.isSuccess()) {
            request.setAttribute(WebConstants.MESSAGE_KEY, "提交成功!");
            request.setAttribute("c_window", "yes");
            return SUCCESS;
        } else {
            request.setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
            return ERROR;
        }
    }

    /**
     * 文件上传
     */
    public String uploadFile() throws Exception {
        CertificateDo certificateDo = null;
        String filePath = null;
        List<CertificateDo> certificateDoList = loanPersonDo.getCertificateDoList();
        Map<String, String> params = (Map<String, String>) request.getSession().getAttribute(
                WebConstants.COLOURLIFE_ADMIN_USER);
        if (certificateDoList == null) {
            certificateDoList = new ArrayList<CertificateDo>();
            for (int i = 0; i < files.length; i++) {
                filePath = fileServerService.saveFile(files[i], filesFileName[i], new int[][] { { 400, 400 } });
                certificateDo = new CertificateDo();
                certificateDo.setLoanId(loanPersonDo.getLoanId());
                certificateDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
                certificateDo.setFilePath(filePath);
                certificateDo.setCreateUser(params.get("userName"));
                certificateDo.setCertificateName(filesFileName[i]);
                certificateDo.setCertificateType(CertificateType.PROTOCOL);
                certificateDo.setFileType(FileType.IMAGE);
                certificateDoList.add(certificateDo);
            }
        } else {
            int count = 0;
            for (int i = 0; i < certificateDoList.size(); i++) {
                certificateDo = certificateDoList.get(i);
                if ("".equals(certificateDo.getFilePath()) || certificateDo.getFilePath() == null) {
                    certificateDo.setFilePath(fileServerService.saveFile(files[count], filesFileName[count],
                            new int[][] { { 400, 400 } }));
                    certificateDo.setCertificateName(filesFileName[count]);
                    count++;
                }
                certificateDo.setCreateUser(params.get("userName"));// 之后要获取值填充
            }
        }
        loanPersonDo.setCertificateDoList(certificateDoList);
        IResult<?> result = loanPersonService.uploadFile(loanPersonDo);
        if (result.isSuccess()) {
            request.setAttribute(WebConstants.MESSAGE_KEY, "数据保存成功!");
            if (loanPersonDo.getLoanDo() != null) {
                String loanStatus = loanPersonDo.getLoanDo().getLoanStatus() + "";
                if ("TREATY".equals(loanStatus)) {
                    loanPersonService.changeloanStatus(loanPersonDo);
                    request.setAttribute(WebConstants.MESSAGE_KEY, "提交成功!");
                }
            }
            request.setAttribute("c_window", "yes");
            return SUCCESS;
        } else {
            request.setAttribute(WebConstants.MESSAGE_KEY, result.getErrorMessage());
            return ERROR;
        }
    }

    /**
     * 自动上标
     * 
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public String loanAutoSubject() throws IOException, SQLException {
        JSONObject json = new JSONObject();
        try {
            loanPersonDo = loanPersonService.getCountByIds(loanPersonDo.getLoanId());
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();

            Map<String, String> map = userService.queryByIdNo(loanPersonDo.getIdNo());
            if (map == null || map.size() == 0) {
                map = new HashMap<String, String>();
                map.put("hhnMsg", "身份证不存在");
                list.add(map);
            }
            if (Convert.strToLong(map.get("usrCustId"), -1) <= 0) {
                map = new HashMap<String, String>();
                map.put("hhnMsg", "该用户还不是汇付天下会员");
                list.add(map);
            }

            long userId = Convert.strToLong(map.get("userId"), -1);
            map = new HashMap<String, String>();
            map.put("userId", userId + "");
            map.put("idNo", loanPersonDo.getIdNo());
            map.put("realName", loanPersonDo.getRealName());// 客户名称
            map.put("sex", loanPersonDo.getSex() == null ? "" : ("MALE".equals(loanPersonDo.getSex() + "") ? "男" : "女"));// 性别
            map.put("age", loanPersonDo.getAge() == null ? "0" : loanPersonDo.getAge() + "");// 年龄
            map.put("maritalStatus",
                    loanPersonDo.getMarriaged() == null ? ""
                            : ("UNMARRIED".equals(loanPersonDo.getMarriaged() + "") ? "未婚" : ("MARRIED"
                                    .equals(loanPersonDo.getMarriaged() + "") ? "已婚" : "离异")));// 婚姻状况
            map.put("hasHourse", "买房");// 居住状况
            map.put("highestEdu",
                    loanPersonDo.getEducation() == null ? ""
                            : ("HIGN_SCHOOL".equals(loanPersonDo.getEducation() + "") ? "高中" : ("JUNIOR_COLLEGE"
                                    .equals(loanPersonDo.getEducation() + "") ? "大专" : "本科以上")));// 学历
            map.put("orgName", loanPersonDo.getJobDo().getCompanyName());// 公司名称
            map.put("companyAddress", " ");// 公司地址
            map.put("job", loanPersonDo.getJobDo().getPosition());// 职位级别
            map.put("workYear", loanPersonDo.getJobDo().getJobYear() + "");// 现公司工作年限
            map.put("monthlyIncome", loanPersonDo.getJobDo().getJobIncome() + "");// 月收入

            map.put("borrowAmount", loanPersonDo.getLoanDo().getLoanAmount() + "");// 借款金额
            map.put("deadline", loanPersonDo.getLoanDo().getLoanPeriod() + "");// 申请期限
            map.put("borrowWay", "3");// 产品类型1=薪金贷2=生意贷3=业主贷
            map.put("paymentMode", loanPersonDo.getLoanDo().getLoanPeriod() == 1 ? "2" : "6");// 还款方式1:
                                                                                              // 2,
                                                                                              // 6
                                                                                              // :
                                                                                              // 6
            map.put("annualRate", loanPersonDo.getLoanDo().getLoanPeriod() == 1 ? "7.8" : "9");// 年利率(%)
            map.put("raiseTerm", "5");// 筹标期限（天）
            map.put("moneyPurposes", loanPersonDo.getLoanDo().getLoanUsage());// 贷款资金用途
            map.put("borrowadvisory", "合和年投资有限公司");// 借款咨询方
            map.put("advisorybranch", "合和年投资有限公司");// 咨询方分行
            map.put("borrowGroup", "0");// 标的所属群组
            list.add(map);

            String ip = "";// ((Admin) //
                           // request.getSession().getAttribute(IConstants.SESSION_ADMIN)).getLastIP();
            List<String> result = borrowManageService.importDatasHHN(list, ip, getBasePath(),null,BorrowManageService.OPERATE_TYPE_IMPORT);
            for (String res : result) {
                System.out.println("自动上班结果>>>>>>>>>>>>" + res);
                if (res.contains("导入失败")) {
                    json.put("ret", res);
                    json.put("flag", 2);
                    ServletUtils.write(json.toString());
                } else {
                    json.put("ret", res);
                    json.put("flag", 1);
                    ServletUtils.write(json.toString());
                    LoanDo loanDo = new LoanDo();
                    loanDo.setLoanId(loanPersonDo.getLoanDo().getLoanId());
                    loanDo.setLoanStatus(LoanStatus.SUBJECTED);
                    String idNo = loanPersonDo.getIdNo();
                    loanPersonDo = new LoanPersonDo();
                    loanPersonDo.setLoanDo(loanDo);
                    loanPersonDo.setIdNo(idNo);
                    loanPersonService.changeloanStatus(loanPersonDo);
                }
            }
        } catch (Exception e) {
            json.put("ret", "自动上标失败");
            json.put("flag", 2);
            ServletUtils.write(json.toString());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 查询贷后管理
     * @return
     */
    public String getManagerData() {
    	 Map<String, Object> searchItems = new HashMap<String, Object>();
         if (!"".equals(loanPersonDo.getRealName())) {
             searchItems.put("realName", loanPersonDo.getRealName());
         }
         searchItems.put("mobile", loanPersonDo.getMobile());
         searchItems.put("flagStatus", LoanDo.LoanStatus.SUBJECTED);
         Map<String, String> params = (Map<String, String>) request.getSession().getAttribute(
                 WebConstants.COLOURLIFE_ADMIN_USER);
         searchItems.put("cname", params.get("areaName"));
         long currentPage = (StringUtils.isNotBlank(request.getParameter("curPage")) ? Long.parseLong(request
                 .getParameter("curPage")) : 1);
         long pageSize = (StringUtils.isNotBlank(request.getParameter("pageSize")) ? Long.parseLong(request
                 .getParameter("pageSize")) : 10);
         long beginCount = (currentPage - 1) * pageSize;
         
         Map<String, Object> map = loanPersonService.getdLYqsl(searchItems);
         String dhExpectedEarnings = "0.00";
         if (map != null) {
        	 dhExpectedEarnings = map.get("dhExpectedEarnings") + "";
         }
         request.setAttribute("dhExpectedEarnings", dhExpectedEarnings);
   
         
         searchItems.put("beginCount", (beginCount < 0 ? 0 : beginCount));
         searchItems.put("pageSize", pageSize);
         NPageDo<LoanPersonDo> pageDo = loanPersonService.getLoanManager(searchItems);
         pageDo.setCurrentPage(currentPage);
         pageDo.setPageSize(pageSize);
         request.setAttribute("pageDo", pageDo);
         return SUCCESS;
    }
	
    public String getRepayMentByBwId() {
    	 String borrowId = request.getParameter("borrowId");
    	 NPageDo<RepaymentDo> pageDo = loanPersonService.getRepayMentByBwId(Long.valueOf(borrowId));
         request.setAttribute("pageDo", pageDo);
         return SUCCESS;
    }
    
    public String getIncomeManager() {
    	 Map<String, Object> searchItems = new HashMap<String, Object>();
         if (!"".equals(loanPersonDo.getRealName())) {
             searchItems.put("realName", loanPersonDo.getRealName());
         }
         searchItems.put("mobile", loanPersonDo.getMobile());
         searchItems.put("flagStatus", LoanDo.LoanStatus.SUBJECTED);
         Map<String, String> params = (Map<String, String>) request.getSession().getAttribute(
                 WebConstants.COLOURLIFE_ADMIN_USER);
         searchItems.put("cname", params.get("areaName"));
         Map<String, Object> map = loanPersonService.getIncomeManager(searchItems);
         String dhExpectedEarnings = "0.00";
    	 map.put("successDs", map.get("successDs") == null ? "0" :map.get("successDs"));
		 map.put("borrowAmount", "null".equals(map.get("borrowAmount")) ? dhExpectedEarnings:map.get("borrowAmount"));
		 map.put("hasPI", "null".equals(map.get("hasPI")) ? dhExpectedEarnings:map.get("hasPI"));
		 map.put("stillPI", "null".equals(map.get("stillPI")) ? dhExpectedEarnings:map.get("stillPI"));
		 double loanExpectedEarnings = 0.00;
		 if(!dhExpectedEarnings.equals(map.get("hasPI")+"")) {
			 double still = Double.parseDouble(map.get("hasPI")+"")*0.02/24;
			 BigDecimal bg = new BigDecimal(still);
			 loanExpectedEarnings = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		 }
		 map.put("loanExpectedEarnings", loanExpectedEarnings);
		 double comingExpectedEarnings = 0.00;
		 if(!dhExpectedEarnings.equals(map.get("stillPI")+"")) {
			 double still = Double.parseDouble(map.get("stillPI")+"")*0.02/24;
			 BigDecimal bg = new BigDecimal(still);
			 comingExpectedEarnings = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		 }
		 map.put("comingExpectedEarnings", comingExpectedEarnings);
         request.setAttribute("map", map);
         return SUCCESS;
    }
    
    public String getBasePath() {
        return WebUtil.getWebPath();
    }

    public LoanPersonDo getLoanPersonDo() {
        return loanPersonDo;
    }

    public void setLoanPersonDo(LoanPersonDo loanPersonDo) {
        this.loanPersonDo = loanPersonDo;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public String[] getFilesContentType() {
        return filesContentType;
    }

    public void setFilesContentType(String[] filesContentType) {
        this.filesContentType = filesContentType;
    }

    public String[] getFilesFileName() {
        return filesFileName;
    }

    public void setFilesFileName(String[] filesFileName) {
        this.filesFileName = filesFileName;
    }
}
