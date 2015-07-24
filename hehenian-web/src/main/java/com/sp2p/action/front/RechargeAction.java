package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.account.IBankCardService;
import com.hehenian.biz.common.util.CalculateUtils;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.ServletUtils;
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.FinanceService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.RechargebankService;
import com.sp2p.util.ChinaPnRInterface;
import com.sp2p.util.DateUtil;

public class RechargeAction extends BaseFrontAction {

    public static Log                 log              = LogFactory.getLog(FrontMyFinanceAction.class);
    private static final long         serialVersionUID = 1L;
    private RechargeService           rechargeService;
    private HomeInfoSettingService    homeInfoSettingService;
    private UserService               userService;
    private FinanceService            financeService;
    private AdminService              adminService;
    @Autowired
    private IBankCardService          bankCardService;
    private RechargebankService       rechargebankService;
    private String                    startTime;
    private String                    endTime;
    @SuppressWarnings("unused")
    private boolean                   flag;
    private List<Map<String, Object>> potypeList;
    private List<Map<String, Object>> banksList;

    /**
     * 充值提现页面初始化
     * 
     * @return
     */
    public String rechargePageInit() {
        return SUCCESS;
    }

    public String rechargeInit() throws Exception {
        // 获取用户的信息
        AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
        Map<String, String> pmap = userService.queryPersonById(user.getId());
        request().setAttribute("realName", pmap.get("realName"));
        request().setAttribute("email", user.getEmail());
        request().setAttribute("usrCustId", user.getUsrCustId());
        Map<String, String> map = new HashMap<String, String>();
        try {
            map = rechargeService.queryUser(user.getId());
            request().setAttribute("map", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 提现信息加载
     * 
     * @return
     * @throws Exception
     */
    public String withdrawLoad() throws Exception {
        // 获取用户的信息
        AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
        Long userId = user.getId();// 获得用户编号

        try {
            int authStep = user.getAuthStep();// 需要填写个人资料之后才能申请提现
            if (authStep < 2) {
                // getOut().print("<script>alert(' 请填写个人信息! ');window.location.href='owerInformationInit.do';</script>");
                // return "personal";
                // return null;
            }
            List<Map<String, Object>> lists = rechargeService.withdrawLoad(userId);
            // 需要加载信息 真实姓名 手机号码 帐户余额 可用余额 冻结总额 提现银行
//            String realName = user.getRealName();
            String bindingPhone = null;
            int status = -1;
            String[] vals = moneyVal(userId);

            request().setAttribute("handleSum", vals[0]);
            request().setAttribute("usableSum", vals[1]);
            request().setAttribute("freezeSum", vals[2]);
            request().setAttribute("withdrawAmount", vals[3]);
            request().setAttribute("max_withdraw", IConstants.WITHDRAW_MAX);// 最大充值金额，超过之后要收取手续费
            if (lists != null && lists.size() > 0) {
                if (lists.get(0).get("bindingPhone") != null) {
                    bindingPhone = lists.get(0).get("bindingPhone").toString();
                }
                if (lists.get(0).get("status") != null) {
                    status = Convert.strToInt(lists.get(0).get("status").toString(), -1);
                }

                // 如果设置的绑定号码为空，或者绑定的手机号码还未审核通过 则都使用用户注册时的手机号码
                if (bindingPhone == null || status != IConstants.PHONE_BINDING_ON) {
                    bindingPhone = lists.get(0).get("cellPhone") + "";
                }
//                request().setAttribute("realName", realName);
                request().setAttribute("bindingPhone", bindingPhone);
            }

            // 同步用户的银行卡信息
            // bankCardService.updateCardInfo(userId);
            // 绑定的银行卡信息单独查询
            request().setAttribute("banks", querySuccessBankInfoList(userId));
            request().setAttribute("ISDEMO", IConstants.ISDEMO);
        } catch (DataException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        return SUCCESS;
    }

    /**
     * @param userId
     * @return
     * @author: liuzgmf
     * @date: 2014年12月3日下午3:26:19
     */
    private Object querySuccessBankInfoList(Long userId) throws Exception {
        List<Map<String, Object>> banks = homeInfoSettingService.querySuccessBankInfoList(userId);
        if (banks == null || banks.size() == 0) {
            return banks;
        }
        Map<String, Object> target = null;
        for (Map<String, Object> map : banks) {
            if (map.get("isExpress") != null && ((Integer) map.get("isExpress")).intValue() == 1) {
                target = map;
                break;
            }
        }

        if (target != null) {
            banks.clear();
            banks.add(target);
        }
        return banks;
    }

    /**
     * 加载提现记录信息
     * 
     * @return
     * @throws DataException
     * @throws SQLException
     */
    public String queryWithdrawList() throws SQLException, DataException {
        // 获取用户的信息
        AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);

        Long userId = user.getId();// 获得用户编号

        pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
        pageBean.setPageSize(IConstants.PAGE_SIZE_6);
        try {
            rechargeService.queryWithdrawList(pageBean, userId, 0, null);
        } catch (DataException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        return SUCCESS;
    }

    public String addRechargeoutline() throws SQLException, IOException {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map<String, String> recharMap = new HashMap<String, String>();
        AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
        long userid = user.getId();
        String rechargeMoneystr = SqlInfusion.FilteSqlInfusion(paramMap.get("money") + "");
        String realname = SqlInfusion.FilteSqlInfusion(paramMap.get("realname"));
        Pattern pattern = Pattern.compile("^(-)?\\d+(\\.\\d+)?$");
        Matcher m = pattern.matcher(rechargeMoneystr);
        if (StringUtils.isBlank(realname) ) {
            jsonMap.put("msg", " 请输入正确的真实姓名！");
            JSONUtils.printObject(jsonMap);
            return null;
        }
        if (!m.matches()) {
            jsonMap.put("msg", " 请输入正确的充值金额！");
            JSONUtils.printObject(jsonMap);
            return null;
        }
        double rechargeMoney = Convert.strToDouble(rechargeMoneystr, 0);
        if (rechargeMoney <= 0) {
            jsonMap.put("msg", "充值金额不能小于零！");
            JSONUtils.printObject(jsonMap);
            return null;
        }
        String bankName = SqlInfusion.FilteSqlInfusion(paramMap.get("bankName"));
        if (StringUtils.isBlank(bankName)) {
            jsonMap.put("msg", "请选定充值帐户！");
            JSONUtils.printObject(jsonMap);
            return null;
        }
        String rechargeNumber = SqlInfusion.FilteSqlInfusion(paramMap.get("rechargeNumber") + "");
        String remark = SqlInfusion.FilteSqlInfusion(paramMap.get("remark") + "");
        if (StringUtils.isBlank(rechargeNumber)) {
            jsonMap.put("msg", " 交易编号不能为空！");
            JSONUtils.printObject(jsonMap);
            return null;
        }
        if (StringUtils.isBlank(remark)) {
            jsonMap.put("msg", "线下充值备注不能为空！");
            JSONUtils.printObject(jsonMap);
            return null;
        }
        recharMap.put("rechargeMoney", rechargeMoney + "");
        recharMap.put("userId", userid + "");
        SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        recharMap.put("rechargeTime", df.format(new Date()) + "");
        recharMap.put("rechargeType", 4 + "");
        recharMap.put("result", 3 + "");// 3为审核中
        recharMap.put("rechargeNumber", rechargeNumber);
        recharMap.put("remark", remark);
        recharMap.put("bankName", bankName);
        long result = -1;
        result = rechargeService.addRechargeoutline(recharMap);

        if (result < 0) {
            jsonMap.put("msg", "提交失败");
            JSONUtils.printObject(jsonMap);
            return null;
        }

        jsonMap.put("msg", "提交成功");
        JSONUtils.printObject(jsonMap);
        return null;
    }

    /**
     * 前台用户提现
     * 
     * @return
     * @throws Exception
     *             [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String addWithdraw() throws Exception {
        AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);

        String ipAddress = ServletUtils.getRemortIp();
        String dealpwd = request("dealpwd");
        String money = request("money");
        double moneyD = Convert.strToDouble(money, 0);
        String bankId = request("bankId");
        long bankIdL = Convert.strToLong(bankId, -1);
        String type = request("type");
        long userId = user.getId();// 获得用户编号
        String openAcctId = request("openAcctId");
        /*
         * 验证码 汇付不需要 if (!IConstants.ISDEMO.equals("1")) { if
         * (StringUtils.isBlank(code)) { obj.put("msg", "请填写验证码");
         * JSONUtils.printObject(obj); return null; } Object obje =
         * session().getAttribute("randomCode"); if (obje != null) { String
         * randomCode = obje.toString(); if
         * (!randomCode.trim().equals(code.trim())) { obj.put("msg", "验证码错误");
         * JSONUtils.printObject(obj); return null; } } else { obj.put("msg",
         * "验证码无效"); JSONUtils.printObject(obj); return null; } }
         */
        if (StringUtils.isBlank(bankId)) {
            JSONUtils.printStr2("请先选择或者设置银行卡信息");
            return null;
        }
        if (moneyD <= 0) {
            JSONUtils.printStr2("错误金额格式");
            return null;
        }
        // if (StringUtils.isBlank(dealpwd)) {
        // JSONUtils.printStr2("请填写交易密码");
        // return null;
        // }
        // if ("1".equals(IConstants.ENABLED_PASS)) {
        // dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
        // } else {
        // dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim() +
        // IConstants.PASS_KEY);
        // }
        Map<String, String> retMap = rechargeService.withdrawApply(userId, moneyD, dealpwd, bankIdL, type, ipAddress);
        long retVal = -1;
        retVal = Convert.strToLong(retMap.get("ret") + "", -1);
        String ordId = Convert.strToStr(retMap.get("out_ordid"), "-1");
        session().removeAttribute("randomCode");
        String usrCustId = user.getUsrCustId() + "";
        if (retVal <= 0) {
            JSONUtils.printStr2(retMap.get("ret_desc"));
            return null;
        } else {
            if (Convert.strToLong(usrCustId, -1) <= 0) {
                Map<String, String> umap = userService.queryUserById(user.getId());
                usrCustId = umap.get("usrCustId");
            }
            // 汇付取现
            String html = ChinaPnRInterface.cash(ordId, usrCustId, money, openAcctId);
            sendHtml(html);
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public static String getNeedTime(Date currDate) {
        try {
            String currDateStr = DateUtil.YYYY_MM_DD.format(currDate);
            currDate = DateUtil.YYYY_MM_DD.parse(currDateStr);
            long currTime = Date.parse(currDate.toString());
            long delTime = IConstants.WITHDRAW_TIME * 24 * 60 * 60 * 1000;
            long needTime = currTime - delTime;
            Date needDate = new Date();
            needDate.setTime(needTime);
            String needDateStr = DateUtil.YYYY_MM_DD.format(needDate);
            return needDateStr;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 删除提现记录
     * 
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws DataException
     */
    public String deleteWithdraw() throws SQLException, IOException, DataException {
        JSONObject obj = new JSONObject();
        long wid = Convert.strToLong(paramMap.get("wId"), -1);
        AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
        Long userId = user.getId();// 获得用户编号
        try {
            // 删除提现记录
            Map<String, String> resultMap = rechargeService.deleteWithdraw(userId, wid);
            long result = Convert.strToLong(resultMap.get("ret") + "", -1);
            if (result <= 0) {
                obj.put("msg", resultMap.get("ret_desc") + "");
                JSONUtils.printObject(obj);
                return null;
            } else {
                obj.put("msg", "1");
                JSONUtils.printObject(obj);
                return null;
            }
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        } catch (DataException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
    }

    public String queryFundrecordInit() throws SQLException, DataException {
        AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
        Long userId = user.getId();// 获得用户编号
        String[] vals = fundVal(userId);

        request().setAttribute("handleSum", vals[0]);
        request().setAttribute("usableSum", vals[1]);
        request().setAttribute("freezeSum", vals[2]);

        return SUCCESS;
    }

    private String[] fundVal(long userId) throws DataException, SQLException {
        String[] val = new String[3];
        for (int i = 0; i < val.length; i++) {
            val[i] = "0";
        }
        try {
            Map<String, String> map = rechargeService.queryFund(userId);
            if (map != null) {
                double usableSum = Convert.strToDouble(map.get("usableSum"), 0);
                double freezeSum = Convert.strToDouble(map.get("freezeSum"), 0);
                double dueinSum = Convert.strToDouble(map.get("forPI"), 0);
                double handleSum = usableSum + freezeSum + dueinSum;
                val[0] = String.format("%.2f", handleSum);
                val[1] = String.format("%.2f", usableSum);
                val[2] = String.format("%.2f", freezeSum);
            }
        } catch (DataException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        return val;
    }

    public String[] moneyVal(long userId) throws DataException, SQLException {
        String[] val = new String[4];
        for (int i = 0; i < val.length; i++) {
            val[i] = "0";
        }
        try {
            Map<String, String> map = userService.queryUserById(userId);
            if (map != null) {
                double usableSum = Convert.strToDouble(map.get("usableSum"), 0);
                double freezeSum = Convert.strToDouble(map.get("freezeSum"), 0);
                double lockAmount = Convert.strToDouble(map.get("lockAmount"), 0);
                double handleSum = usableSum + freezeSum;
                val[0] = String.format("%.2f", handleSum);// df.format(handleSum);
                val[1] = String.format("%.2f", usableSum);// df.format(usableSum);
                val[2] = String.format("%.2f", freezeSum);// df.format(freezeSum);
                double withdrawAmount = CalculateUtils.sub(usableSum, lockAmount);
                if (CalculateUtils.lt(withdrawAmount, 0)) {
                    withdrawAmount = 0.00;
                }
                val[3] = String.format("%.2f", withdrawAmount);
            }
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        return val;
    }

    /**
     * 合和年 会员中心 资金管理 所有记录
     * 
     * @return
     * @throws Exception
     */
    public String queryFundrecordList() throws Exception {
        return getFundrecordByType(" and fundMode <> '冻结投标金额' ");
    }

    /**
     * 合和年 会员中心 资金管理 回收本息
     * 
     * @return
     * @throws Exception
     */
    public String queryFontFundrecordReback() throws Exception {
        return getFundrecordByType(" and (fundMode='投资收到还款' or fundMode='用户还款资金收入' ) ");
    }

    /**
     * 合和年 会员中心 资金管理 投资
     * 
     * @return
     * @throws Exception
     */
    public String queryFontFundrecordInvest() throws Exception {
        return getFundrecordByType(" and fundMode like '%投标%' and fundMode <> '冻结投标金额' ");
    }

    /**
     * 合和年 会员中心 资金管理 冻结
     * 
     * @return
     * @throws Exception
     */
    public String queryFontFundrecordFreeze() throws Exception {
        return getFundrecordByType(" and fundMode like '%冻%' ");
    }

    /**
     * 
     * @param cmd
     *            查询条件
     * @return
     * @throws SQLException
     * @throws DataException
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    private String getFundrecordByType(String cmd) throws SQLException, DataException, Exception {
        AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
        Long userId = user.getId();// 获得用户编号
        pageBean.setPageNum(SqlInfusion.FilteSqlInfusion(request("curPage")));
        String momeyType = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("momeyType")), "");
        String date2 = endTime;
        if (endTime != null && !(endTime.equals(""))) {
            String[] strs = date2.split("-");
            // 结束日期往后移一天,否则某天0点以后的数据都不呈现
            Date date = new Date();// 取时间
            long time = Date.UTC(Convert.strToInt(strs[0], -1) - 1900, Convert.strToInt(strs[1], -1) - 1,
                    Convert.strToInt(strs[2], -1), 0, 0, 0);
            date.setTime(time);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date2 = formatter.format(date);
        }
        pageBean.setPageSize(6);
        rechargeService.queryFundrecordList(pageBean, userId, startTime, date2, momeyType, cmd);
        paramMap = rechargeService.queryFundrecordSum(userId, startTime, date2, momeyType, cmd);

        // 统计用户最近交易的次数
        Map<String, String> map = rechargeService.queryFundrecordNum(userId, cmd);
        request().setAttribute("map", map);

        int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
        // queryRecordCount();
        request().setAttribute("pageNum", pageNums);
        request().setAttribute("startTime", startTime);
        request().setAttribute("endTime", endTime);
        return SUCCESS;
    }

    /**
     * 添加充值记录
     * 
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws DataException
     */

    @SuppressWarnings("unchecked")
    public String queryRechargeInfo() throws SQLException, DataException {
        AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
        Long userId = user.getId();// 获得用户编号
        List<Map<String, Object>> lists = null;
        int status = -1;
        try {

            pageBean.setPageSize(IConstants.PAGE_SIZE_6);

            rechargeService.queryRechargeInfo(pageBean, userId);
            lists = pageBean.getPage();
            if (lists != null) {
                for (Map<String, Object> map : lists) {
                    map.put("userId", user.getUsername());
                    status = Convert.strToInt(map.get("result") + "", -1);
                    if (status == IConstants.RECHARGE_SUCCESS) {
                        map.put("status", IConstants.RECHARGE_SUCCESS_STR);
                    } else {
                        map.put("status", IConstants.RECHARGE_CHECKING_STR);
                    }
                }
            }
        } catch (DataException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 发送手机验证码
     * 
     * @return
     */
    public String sendPhoneCode() {
        Random rand = new Random();
        int num = rand.nextInt(9999);
        while (num < 1000) {
            num = rand.nextInt(9999);
        }
        String type = request("type");
        session().setAttribute(type + "_phoneCode", num);
        try {
            JSONUtils.printStr(num + "");
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 线下充值审核
     * 
     * @return
     * @throws Exception
     */
    public String updateRechargeDetailStatusById() throws Exception {

        String award = SqlInfusion.FilteSqlInfusion(paramMap.get("award"));

        if (StringUtils.isBlank(award)) {
            award = "";
        } else {
            Pattern pattern = Pattern.compile("^(-)?\\d+(\\.\\d+)?$");
            Matcher m = pattern.matcher(award);
            if (!m.matches()) {
                this.addFieldError("paramMap.allerror", "输入奖励比例错误");
                return "input";
            }
        }
        long id = Convert.strToLong(paramMap.get("id"), -1);
        long userid = Convert.strToLong(paramMap.get("userId"), -1);
        if (id == -1) {
            this.addFieldError("paramMap.allerror", "审核失败");
            return "input";
        }
        if (userid == -1) {
            this.addFieldError("paramMap.allerror", "审核失败");
            return "input";
        }
        String typeStr = SqlInfusion.FilteSqlInfusion(paramMap.get("type"));
        long type = -1;
        if (typeStr.equals("a")) {// 审核成功
            type = 1;
        } else if (typeStr.equals("b")) {
            type = 0;
        } else {
            this.addFieldError("paramMap.allerror", "审核失败");
            return "input";
        }
        double rechargeMoney = Convert.strToDouble(paramMap.get("rechargeMoney") + "", 0);
        if (rechargeMoney == 0) {
            this.addFieldError("paramMap.allerror", "审核失败");
            return "input";
        }
        long result = rechargeService.updateRechargeDetailStatusById(id, userid, type, rechargeMoney, award);
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        operationLogService.addOperationLog("t_recharge_detail", admin.getUserName(), IConstants.UPDATE,
                admin.getLastIP(), 0, "线下审核", 2);
        if (result <= 0) {
            getOut().print("<script>alert('审核失败');window.location.href='queryxxRechargeInit.do';</script>");
            return null;
        }
        return SUCCESS;
    }

    public RechargeService getRechargeService() {
        return rechargeService;
    }

    public void setRechargeService(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public HomeInfoSettingService getHomeInfoSettingService() {
        return homeInfoSettingService;
    }

    public void setHomeInfoSettingService(HomeInfoSettingService homeInfoSettingService) {
        this.homeInfoSettingService = homeInfoSettingService;
    }

    public FinanceService getFinanceService() {
        return financeService;
    }

    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public List<Map<String, Object>> getPotypeList() throws DataException, SQLException {
        if (potypeList == null) {
            potypeList = rechargeService.queryTypeFund();
        }
        return potypeList;
    }

    public void setPotypeList(List<Map<String, Object>> potypeList) {
        this.potypeList = potypeList;
    }

    public List<Map<String, Object>> getBanksList() {
        try {
            banksList = rechargebankService.queryrechargeBanklist();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return banksList;
    }

    public void setBanksList(List<Map<String, Object>> banksList) {
        this.banksList = banksList;
    }

    public RechargebankService getRechargebankService() {
        return rechargebankService;
    }

    public void setRechargebankService(RechargebankService rechargebankService) {
        this.rechargebankService = rechargebankService;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 资金管理 查看 用户 充值记录
     * 
     * @return
     */
    public String queryFontRechargeHistory() {
        try {
            this.rechargeService.queryRechargeInfo(pageBean, this.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 前台--汇付开户
     */
    public String registerChinaPnr() throws Exception {
        AccountUserDo user = (AccountUserDo) session("user");
        String usrId = user.getId() + "";
        Map<String, String> pmap = userService.queryPersonById(user.getId());
        String usrName = Convert.strToStr(pmap.get("realName"), "");
        String idNo = Convert.strToStr(pmap.get("idNo"), "");
        String usrEmail = Convert.strToStr(user.getEmail(), "");
        request().setAttribute("idNo", idNo);
        String mobilePhone = Convert.strToStr(user.getMobilePhone(), "");
        if (mobilePhone != null && mobilePhone.startsWith("-")){
            mobilePhone = mobilePhone.substring(1,mobilePhone.length());
        }
        /*
         * if(mobilePhone == null || mobilePhone.equals("")){ getOut().print(
         * "<script>alert('您还未完善个人资料,进入个人中心完善个人资料');window.location.href='owerInformationInit.do';</script>"
         * ); return null; }
         * 
         * if(usrName == null || usrName.equals("")){ getOut().print(
         * "<script>alert('您还未完善个人资料,进入个人中心完善个人资料');window.location.href='owerInformationInit.do';</script>"
         * ); return null; } if (idNo == null || idNo.equals("")) {
         * getOut().print(
         * "<script>alert('您还未完善个人资料,进入个人中心完善个人资料');window.location.href='owerInformationInit.do';</script>"
         * ); return null; }
         */
        String cmdId = "UserRegister";

        String html = ChinaPnRInterface.userRegister(cmdId, usrId, usrName, idNo, mobilePhone, usrEmail);
        sendHtml(html);
        return null;
    }

}