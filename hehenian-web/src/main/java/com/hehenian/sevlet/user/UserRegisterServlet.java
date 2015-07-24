/**  
 * @Project: hehenian-web
 * @Package com.hehenian.sevlet.user
 * @Title: UserRegisterServlet.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年11月19日 下午6:12:30
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.sevlet.user;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hehenian.biz.common.activity.dataobject.ActivityConfig;
import com.hehenian.biz.common.util.JsonUtil;
import com.hehenian.biz.common.util.Md5Utils;
import com.hehenian.web.common.util.ServletUtils;
import com.shove.Convert;
import com.shove.security.Encrypt;
import com.shove.web.util.DesSecurityUtil;
import com.sp2p.constants.IConstants;
import com.sp2p.service.UserService;

/** 
 *  
 * @author: zhangyunhmf
 * @date 2014年11月19日 下午6:12:30
 */
public class UserRegisterServlet extends HttpServlet {


    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long     serialVersionUID = 5872549716906862682L;

    private Logger logger = Logger.getLogger(this.getClass());

    protected UserService     userService;

    private ActivityConfig activityConfig;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        super.init(config);
        
        ServletContext servletContext = config.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils
                .getWebApplicationContext(servletContext);
        AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext
                .getAutowireCapableBeanFactory();
        
        activityConfig = (ActivityConfig) autowireCapableBeanFactory.getBean("activityConfig");
        userService = (UserService) autowireCapableBeanFactory.getBean("userService");
    
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    private void printMessage(String code, String msg, HttpServletResponse resp) {
        Map<String, String> status = new HashMap<String, String>();
        String jsonStr = "";
        status.put("statusCode", code);
        status.put("statusDesc", msg);
        try {
            jsonStr = JsonUtil.toString(status);
        } catch (IOException e) {
            jsonStr = "{statusCode:'004'}";
            logger.error(e);
        }
        ServletUtils.write(jsonStr, ServletUtils.RESP_CONTTYPE_JSON, resp);
    }
    /**
     * 总收益查询接口: 接口参数： userId bigInt 非空 20 彩之云用户账号
     * 
     * 返回： userId bigInt 非空 20 彩之云用户账号 totalInvestAmount 保留两位小数Decimal(18,2) 非空
     * 累计投资金额 totalInterestAmount 保留两位小数Decimal(18,2) 非空 累计利息 withdrawalAmount
     * 保留两位小数Decimal(18,2) 非空 可提取金额
     * 
     * @return json 字符
     * @author: zhangyunhmf
     * @date: 2014年10月30日下午2:55:31
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            req.setCharacterEncoding("utf-8");
            String userName = req.getParameter("username"); // 用户名
            String mobilePhone = req.getParameter("mobilePhone");// 手机号
            String cid = req.getParameter("cid");// 小区ID
            String cname = req.getParameter("cname");
            if (null != cname) {
                cname = URLDecoder.decode(cname, "utf-8");// 小区名
            }

            String caddress = req.getParameter("address");
            if (null != caddress) {
                caddress = URLDecoder.decode(caddress, "utf-8");// 用户地址
            }

            String refferee = req.getParameter("refferee");// 彩生活推荐人id
            String colorId = req.getParameter("colorId");// 彩生活用户ID,用户唯一标识，数字1-10位
            String password = req.getParameter("password");// 用户密码
            String enable = req.getParameter("enable");// 是否禁用 1、启用 2、禁用 3.黑名单
                                                       // 默认1
            String ipAddress = req.getParameter("ipAddress");// 注册IP
            String via = req.getParameter("via");// 用户来源平台

            String realName = req.getParameter("realName");
            if (null != realName) {
                realName = URLDecoder.decode(realName, "utf-8");// 真实姓名
            }

            String sign = req.getParameter("sign");

            Map<String, String> status = new HashMap<String, String>();
            String jsonStr = "";

            if (StringUtils.isBlank(userName) || StringUtils.isBlank(mobilePhone) || StringUtils.isBlank(colorId)
                    || mobilePhone.length() != 11 || StringUtils.isBlank(password)) {
                printMessage("003", "无效参数", resp);
                return;
            }

            // md5 校验
            StringBuffer signSb = new StringBuffer();
            signSb.append("username").append(userName).append("mobilePhone").append(mobilePhone).append("refferee")
                    .append(refferee).append("enable").append(enable).append("ipAddress").append(ipAddress)
                    .append("via").append(via).append("realName").append(realName).append("address").append(caddress)
                    .append("cname").append(cname).append("cid").append(cid).append("colorId").append(colorId)
                    .append("password").append(password);

            boolean signOk = Md5Utils.checkMD5(sign, activityConfig.getHehenianKey(), signSb.toString());
            if (!signOk) {
                logger.error("md5验证失败");
                printMessage("002", "md5验证失败", resp);
                return;
            }

            Map<String, String> map = userService.getColourlifeName(userName, mobilePhone, colorId, password, "");
            long result = Convert.strToLong(map.get("ret"), 0);// 若已注册,返回0
                                                               // ;若可注册,返回1
                                                               // ;若信息重复,不可注册,返回-1,
                                                               // 若信息被篡改,返回100

            if (result == 0) {// 若已注册,返回0
                logger.error("已注册");
                printMessage("007", "用户已经存在", resp);
                return;
            }

            if (result == 100) {
                logger.error("请求参数非法");
                printMessage("003", "无效参数", resp);
                return;
            }

            int registerType = 8; // 从彩生活接口注册
            if (result < 0) {// 手机号码已存在
                logger.error(" 手机号码已存在");
                printMessage("005", "手机号码已存在", resp);
                return;
            } else {
                int typelen = -1;
                DesSecurityUtil des = new DesSecurityUtil(activityConfig.getHehenianKey());
                String pwd = des.decrypt(password);
                String md5Password = Encrypt.MD5(pwd + IConstants.PASS_KEY);
                // 注册用户
                Map<String, String> rmap = userService.userRegister1(mobilePhone, userName, md5Password, refferee,
                        null, typelen, mobilePhone, registerType);
                long userId = Convert.strToLong(rmap.get("ret"), -1);
                if (userId < 0) { // 注册失败
                    logger.error("注册失败");
                    status.put("statusCode", "006");
                    printMessage("006", "注册失败", resp);
                    return;
                }
                long newUserId = Convert.strToLong(rmap.get("ret"), -1);
                userService.updateUserCheck(colorId, "", via, newUserId);
                userService.saveColourInfo(newUserId, Convert.strToInt(colorId, -1), Convert.strToInt(cid, -1), cname,
                        caddress, Convert.strToInt(refferee, -1), "");

                printMessage("001", "ok", resp);
            }
        } catch (Throwable e) {
            String jsonStr = "{statusCode:'004'}";
            logger.error(e);
            ServletUtils.write(jsonStr, ServletUtils.RESP_CONTTYPE_JSON, resp);
            return;
        }

    }
}
