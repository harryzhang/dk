package com.sp2p.action.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.account.IPhoneVerifyService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PhoneVerifyDo;
import com.hehenian.biz.common.activity.IActivityOrderService;
import com.hehenian.biz.common.activity.dataobject.ActivityOrderDo;
import com.hehenian.biz.common.trade.IInvestService;
import com.hehenian.liumi.exchange.LiumiClient;
import com.hehenian.liumi.exchange.Lottery;
import com.hehenian.liumi.exchange.PropertiesUtils;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.wechat.exchange.WechatResult;
import com.hehenian.wechat.exchange.WechatUtils;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.SMSUtil;
import com.shove.util.SqlInfusion;
import com.shove.util.UtilDate;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.shove.web.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.BBSRegisterService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.MailSendService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.SendMailService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.RelationService;
import com.sp2p.service.admin.SMSInterfaceService;

/**
 * 用户注册
 * 
 * @author
 * 
 */
public class FrontLongRegisterAction extends BaseFrontAction {
    public static        Log  log              = LogFactory.getLog(FrontLongRegisterAction.class);
    private static final long serialVersionUID = 1L;
    @Autowired
    private   IInvestService         investService;
    @Autowired
    private   IActivityOrderService  activityOrderService;
    @Autowired
    private IPhoneVerifyService phoneVerifyService;
    /**
     */
    protected UserService            userService;
    @Autowired
    private   IUserService           userService1;
    protected SendMailService        sendMailService;
    private   RecommendUserService   recommendUserService;
    private   RelationService        relationService;
    private   HomeInfoSettingService homeInfoSettingService;
    @SuppressWarnings("unused")
    private   UserIntegralService    userIntegralService;
    @SuppressWarnings("unused")
    private   MailSendService        mailSendService;
    private   BBSRegisterService     bbsRegisterService;
    @SuppressWarnings("unused")
    private   AdminService           adminService;
    private   SMSInterfaceService    sMsService;

    public SMSInterfaceService getsMsService() {
        return sMsService;
    }

    public void setsMsService(SMSInterfaceService sMsService) {
        this.sMsService = sMsService;
    }

    public void setMailSendService(MailSendService mailSendService) {
        this.mailSendService = mailSendService;
    }

    public void setUserIntegralService(UserIntegralService userIntegralService) {
        this.userIntegralService = userIntegralService;
    }

    public String regInit() throws Exception {
        AccountUserDo user = (AccountUserDo) session().getAttribute("user");
        if (user != null) {
            // response().setContentType("text/html;charset=UTF-8");
            // response().setCharacterEncoding("UTF-8");//防止弹出的信息出现乱码
            // PrintWriter out = response().getWriter();
            // out.print("<script>alert('已是注册用户！')</script>");
            // out.print("<script>window.location.href='finance.do'</script>");
            // out.close();
        }
        String param = SqlInfusion.FilteSqlInfusion(request("param"));
        if (StringUtils.isNotBlank(param)) {
            DesSecurityUtil des = new DesSecurityUtil();
            Long userId = Convert.strToLong(des.decrypt(param), -1);
            String userName;
            Map<String, String> map = new HashMap<String, String>();
            try {
                map = userService.queryUserById(userId);
            } catch (Exception e) {
                log.error(e);
                e.printStackTrace();
            }
            userName = map.get("username");
            paramMap.put("refferee", userName);
            paramMap.put("userId", userId.toString());
        }
        paramMap.put("param", param);
        return SUCCESS;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 忘记密码
     *
     * @return
     */
    public String forget() {
        return SUCCESS;
    }

    public String forgetSendEMl() throws Exception {
        JSONObject obj = new JSONObject();
        String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email"));
        String username = SqlInfusion.FilteSqlInfusion(paramMap.get("username"));
        if (StringUtils.isBlank(email))
            obj.put("mailAddress", "0");
		else {
			// ===截取emal后面地址
			int dd = email.indexOf("@");
			String mailAddress = null;
			if (dd >= 0)
				mailAddress = "mail." + email.substring(dd + 1);

			Map<String, String> map = userService.queryPassword(email, username);
			if (map != null && map.size() > 0) {
				username = map.get("username");
				Long userId = Convert.strToLong(map.get("id"), -1L);
				DesSecurityUtil des = new DesSecurityUtil();
				String key1 = des.encrypt(userId.toString());
				String key2 = des.encrypt(new Date().getTime() + "");
				String url = getPath(); // request().getRequestURI();
				String VerificationUrl = url + "changePassword.do?key=" + key1 + "-" + key2;

				sendMailService.sendRegisterVerificationEmailPassWordindex(VerificationUrl, username, email);
				obj.put("mailAddress", mailAddress);

			} else
				obj.put("mailAddress", "1");
		}
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * 点击邮箱连接后
	 * 
	 * @return
	 * @throws Exception
	 */
	public String changePasswordfor() throws Exception {
		String key = SqlInfusion.FilteSqlInfusion(request("key").trim());
		String msg = "邮箱验证失败";
		String[] keys = key.split("-");
		if (2 == keys.length) {
			DesSecurityUtil des = new DesSecurityUtil();
			String userId = Encrypt.MD5(key + IConstants.PASS_KEY).substring(0, 10) + key;
			String dateTime = des.decrypt(keys[1].toString());
			long curTime = new Date().getTime();
			// 当用户点击注册时间小于10分钟
			if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {
				ServletActionContext.getRequest().setAttribute("userId", userId);
				return SUCCESS;
			} else {
				msg = "连接失效,<strong>请从新填写你的注册邮箱</a></strong>";
				ServletActionContext.getRequest().setAttribute("msg", msg);
				return "index";
			}

		} else {
			return "index";
		}

	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updatechangePasswordfor() throws Exception {
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("newPassword"));
		String confirmpassword = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmpassword"));
		String key = SqlInfusion.FilteSqlInfusion(paramMap.get("userId"));
		Long userId = -1l;
		String mdKey = key.substring(0, 10);
		String mdValue = key.substring(10, key.length());
		String mdCompare = Encrypt.MD5(mdValue + IConstants.PASS_KEY).substring(0, 10);
		if (!mdKey.equals(mdCompare)) {
			JSONUtils.printStr("4");
			return null;
		}
		String[] keys = mdValue.split("-");
		if (2 == keys.length) {
			DesSecurityUtil des = new DesSecurityUtil();
			userId = Convert.strToLong(des.decrypt(keys[0].toString()), -1);
			String dateTime = des.decrypt(keys[1].toString());
			long curTime = new Date().getTime();
			// 当用户点击注册时间小于10分钟
			if (curTime - Long.valueOf(dateTime) >= 10 * 60 * 1000) {
				JSONUtils.printStr("4");
				return null;
			}
		} else {
			JSONUtils.printStr("4");
			return null;
		}

		if (StringUtils.isBlank(password)) {
			JSONUtils.printStr("3");
			return null;
		}

		if (!confirmpassword.equals(password)) {
			JSONUtils.printStr("5");
			return null;
		}
		// 验证密码的长度
		if (password.length() < 6 || password.length() > 20) {
			JSONUtils.printStr("6");
			return null;
		}

		// Long userId = Convert.strToLong(paramMap.get("userId"), -1L);
		if (userId == null || userId == -1L) {
			JSONUtils.printStr("4");
			return null;
		}
		Long result = -1L;
		if (password != null && password.trim() != "" && userId != null && userId != -1L) {
			result = userService.updateUserPassword(userId, password);
		}
		if (result > 0) {
			Map<String, String> userMap = userService.queryUserById(userId);
			String userName = userMap.get("username") + "";
			// 修改密码
			bbsRegisterService.doUpdatePwdByAsynchronousMode(userName, password, confirmpassword, 2);
			JSONUtils.printStr("1");
			return null;
		} else {
			JSONUtils.printStr("0");
			return null;
		}

	}

	// -------add by houli 将查询推荐人方法单独出来，注册填写推荐人的时候，用户要求失去焦点进行提示推荐人填写正确与否
	public String queryValidRecommer() throws Exception {
		Long refferee = Convert.strToLong(request("refferee"), -1);
		try {
			if (refferee < 0) {
				JSONUtils.printStr("1");
				return null;
			}
			Map<String, String> userIdMap = userService.queryUserById(refferee);// 根据用户查询用户明细
			Map<String, Object> map = relationService.isPromoter(refferee + "");
			if (userIdMap == null && map == null) {
				JSONUtils.printStr("1");
				return null;
			}
			JSONUtils.printStr("2");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			JSONUtils.printStr("1");
			return null;
		}
		return null;
	}

	// ------------end by houli

	/**
	 * 用户注册
	 */
	public String register() throws Exception {
        if (true){
            JSONUtils.printStr2("这个是老的注册接口");
            return null;
        }
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName")); // 用户名
		if (StringUtils.isBlank(userName) || userName.length() < 2 || userName.length() > 20) {
			JSONUtils.printStr2("用户名长度为2-20个字符");
			return null;
		}
		// 验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
		if (userName.replaceAll("^[A-Za-z0-9_]+$", "").length() != 0) {
			JSONUtils.printStr2("用户名由数字字母下划线组成");
			return null;
		}
		// 判断第一个字符串不能使以下划线开头的
		String fristChar = userName.substring(0, 1);
		if (fristChar.equals("_")) {
			JSONUtils.printStr2("用户名第一个字符不能是下划线");
			return null;
		}

		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password")); // 用户密码
		String md5Password = password;
		if (StringUtils.isBlank(password)) {
			JSONUtils.printStr2("请设置您的密码");
			return null;
		}
		String confirmPassword = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmPassword")); // 用户确认密码
		if (StringUtils.isBlank(confirmPassword)) {
			JSONUtils.printStr2("请再次输入密码确认");
			return null;
		}
		if (!password.equals(confirmPassword)) {
			JSONUtils.printStr2("两次输入密码不一致");
			return null;
		}
		String telephone = SqlInfusion.FilteSqlInfusion(paramMap.get("telephone"));// 手机号
		if (StringUtils.isBlank(telephone)) {
			JSONUtils.printStr2("手机号不能为空");
			return null;
		}

		// 查询手机号码是否存在
		long result1 = userService.queryUserIdByPhone(telephone);
		if (result1 > 0) {
			JSONUtils.printStr2("手机号码已经存在");
			return null;
		}
		
		/*String phonecode = null;
		try {
			Object obje = session().getAttribute("phone");
			// 测试--跳过验证码
			if (IConstants.ISDEMO.equals("1")) {

			} else {
				if (obje != null) {
					phonecode = obje.toString();
				} else {
					JSONUtils.printStr2("验证码已失效，请重新获取");
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (phonecode != null) {
			if (!phonecode.trim().equals(telephone.trim())) {
				JSONUtils.printStr2("与获取验证码手机号不一致");
				return null;
			}

		}*/
		// 验证码
		/*String vilidataNum = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmTelephone"));
		if (StringUtils.isBlank(vilidataNum)) {
			JSONUtils.printStr2("请填写验证码");
			return null;
		}
		
		String randomCode = null;
		Object objec = session().getAttribute("randomCode");
		// 测试--跳过验证码
		if (IConstants.ISDEMO.equals("1")) {

		} else {
			if (objec != null) {
				randomCode = objec.toString();
			} else {
				JSONUtils.printStr2("请输入正确的验证码");
				return null;
			}
			if (randomCode != null) {
				if (!randomCode.trim().equals(vilidataNum.trim())) {
					JSONUtils.printStr2("请输入正确的验证码");
					return null;
				}

			}
		}*/
		
		/*
		 * 老版的推荐人逻辑 现在不需要这个  by 刘文韬 20140821
		Long refferId = Convert.strToLong(paramMap.get("refferee"), -1);
		Map<String, Object> map = null;
		long recommendUserId = -1;
		if (refferId > 0) {
			
			Map<String, String> userIdMap = userService.queryUserById(refferId);// 根据用户ID查询用户明细
			if (userIdMap != null) {
				recommendUserId = Convert.strToLong(userIdMap.get("id"), -1);
			}
			map = relationService.isPromoter(refferId + "");
			if (map == null) {
				refferId = null;
			}
			if (userIdMap == null && map == null) {
				JSONUtils.printStr2("推荐人填写错误！");
				return null;
			}
			
		}*/
		
		String reffer = paramMap.get("refferee");
		long refferId = -1;
		if (StringUtils.isNotBlank(reffer)) {
			refferId = userService.findUserByIdOrPhone(reffer);
			if (refferId<=0) {
				JSONUtils.printStr2("推荐人填写错误！");
				return null;
			}
		}
		
		/*Long userId = -1L;
		try {
			Long result = userService.isExistEmailORUserName(null, userName);
			if (result > 0) { // 用户名重复
				JSONUtils.printStr2("用户名重复");
				return null;
			}

			int typelen = -1;
			if ("1".equals(IConstants.ENABLED_PASS)) {
				md5Password = Encrypt.MD5(md5Password.trim());
			} else {
				md5Password = Encrypt.MD5(md5Password.trim() + IConstants.PASS_KEY);
			}

			//如果为彩之云用户注册，则更新彩生活用户验证信息
			String userid = SqlInfusion.FilteSqlInfusion(paramMap.get("userid")); // 用户名
			long useridLong = Convert.strToLong(userid, -1);
			int registerType = 1; //注册类型 默认1网站注册

			if(useridLong!=-1){
				if ("colorlifeapp".equals(session("platform"))) {
					registerType = 4;
				}else{
					registerType = 2;
				}
			}else{
                if (session("sourcefrom")!=null){
                    try {
                        registerType =Integer.parseInt(session("sourcefrom").toString());
                    }catch (Exception e){}
                }else{
                    registerType = Convert.strToInt(paramMap.get("registerType"),1);
                }
			}

			Map<String, String> rmap = userService.userRegister1(telephone, userName, md5Password, refferId + "", null, typelen, null,registerType);// 注册用户
			userId = Convert.strToLong(rmap.get("ret"), -1);
			if (userId < 0) { // 注册失败
				JSONUtils.printStr2(rmap.get("ret_desc"));
				return null;
			}else{
				if (StringUtils.isNotBlank(reffer)){
					try {
						userService.saveUserReffer(reffer, userId);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
				}
			}

			String passwords = SqlInfusion.FilteSqlInfusion(paramMap.get("passwords")); // 用户名
			if(useridLong!=-1){
				userService.updateUserCheck(useridLong+"",passwords,"", userId);// 保存彩生活用户验证信息
			}
			//如果是花样会注册链接注册的话，就将userGroup设为2
			if("hyn".equals(request("via"))){
				userService.updateUserGroup(userId, 2);
			}else if("ftn".equals(request("via"))){
				//如果是养老的注册链接注册的话，就将userGroup设为3
				userService.updateUserGroup(userId, 3);
			}

			request().setAttribute("hhnUname", userName);
		} catch (SQLException e) {
			JSONUtils.printStr2("系统异常");
			e.printStackTrace();
		}

		// 直接登录
		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		String lastIP = ServletUtils.getRemortIp();
		String lastTime = dateformat.format(new Date());
		User user = null;
		try {
			user = userService.userLogin1(userName, password, lastIP, lastTime);
			if (user.getId() == null || user.getRealName() == null) {
				request().setAttribute("idNo", "noIdNo");// 会员身份证号码
				request().setAttribute("realName", "norealName");// 真实姓名
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user == null) {
			JSONUtils.printStr2("注册成功");
			return null;
		}

		Cookie cookie = new Cookie("user", userName);
		if (!"0".equals(paramMap.get("addCookie")))
			cookie.setMaxAge(1209600);// 保存两周
		else
			cookie.setMaxAge(0);
		response().addCookie(cookie);
		session().setAttribute("user", user);
        if ("appcomm".equals(session("platform"))){
            //json.put("token",getUserToken(user.getId(),user.getPassword()));
            cookie = new Cookie("token", getUserToken(user.getId()));
            cookie.setMaxAge(86400);//保存一天
            try {
                response().addCookie(cookie);
            } catch (Exception e) {
            }
        }
		// 注册成功后判断是否是推广注册的。
		// 修改之前的推荐
		try {
			if (refferId > 0) {// 判断是否为空
				List<Map<String, Object>> list = recommendUserService.queryRecommendUser(null, userId, null);// 查询用户是否已经存在关系了。
				if (list != null && list.size() > 0) {// 判断之前是否已经有关系了。
					return null;
				}
				recommendUserService.addRecommendUser(userId, refferId);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		if (userId>0) {
			if ("colorlifeapp".equals(session("platform"))) {
				return "colorlifeapp";
			}
			*//*else{
				return "colorlifeweb";
			}*//*
		}
		JSONUtils.printStr2("注册成功");*/
		return null;
	}

	private long r_userId;

	public long getR_userId() {
		return r_userId;
	}

	public void setR_userId(long id) {
		r_userId = id;
	}

	// add by houli
	public String reActivateEmail() throws IOException, DataException, SQLException {
		String email = request("email") == null ? null : Convert.strToStr(request("email"), null);
		try {
			if (email == null) {
				JSONUtils.printStr("1");
				return INPUT;
			}
			long id = -100;
			// 根据邮件查询用户信息
			Map<String, String> userMap = userService.queryPassword(email);
			if (userMap == null || userMap.isEmpty()) {
				// 按照用户名查找
				userMap = userService.queryIdByUser(email);
				if (userMap == null || userMap.isEmpty()) {
					JSONUtils.printStr("2");
					return INPUT;
				} else {
					id = userMap.get("id") == null ? -100 : Convert.strToLong(userMap.get("id"), -100);
				}
			} else {
				id = userMap.get("id") == null ? -100 : Convert.strToLong(userMap.get("id"), -100);
			}

			setR_userId(id);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
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
	 * 登录BBS
	 *
	 * @return
	 * @throws Exception
	 */
	public String loginBBS() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		String referer = this.request("referer");
		if (referer == null) {
			referer = "";
		}
		if (referer.contains("tid")) {
			referer += "&highlight=";
		}
		if (user == null) {
			this.response().sendRedirect(IConstants.BBS_URL + referer);
			return null;
		}

		// 虚拟用户不能登录论坛
		/*
		 * if (user.getVirtual() == 1) {
		 * this.response().sendRedirect(IConstants.BBS_URL); return null; }
		 */

		Map<String, String> map = new HashMap<String, String>();

		map.put("username", user.getUsername());
//		map.put("password", Encrypt.decryptSES(user.getEncodeP(), IConstants.PWD_SES_KEY));

		map.put("cookietime", "2592000");
		map.put("answer", "");
		map.put("formHash", "6a36c78f");
		map.put("loginfield", "username");
		map.put("loginmode", "");
		map.put("loginsubmit", "true");

		map.put("questionid", "0");

		map.put("referer", referer);
		map.put("styleid", "");

		map.put("k", Encrypt.encryptSES(IConstants.BBS_KEY, IConstants.BBS_SES_KEY));

		String strURL = IConstants.BBS_URL.endsWith("/") ? IConstants.BBS_URL + "logging.jsp?action=login" : IConstants.BBS_URL + "/logging.jsp?action=login";
		String html = buildForm(map, strURL, "post", "登录");
		this.response().setContentType("text/html");
		response().setCharacterEncoding("utf-8");
		PrintWriter out = response().getWriter();
		out.println("<HTML>");
		out.println(" <HEAD><TITLE>sender</TITLE></HEAD>");
		out.println(" <BODY>");
		out.print(html);
		out.println(" </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
		return null;
	}

	private String buildForm(Map<String, String> sParaTemp, String gateway, String strMethod, String strButtonName) {
		log.info("BBS==gateway========>" + gateway);
		// 待请求参数数组
		List<String> keys = new ArrayList<String>(sParaTemp.keySet());

		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form id=\"loginForm\" name=\"loginForm\" action=\"" + gateway + "\" method=\"" + strMethod + "\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = keys.get(i);
			String value = sParaTemp.get(name);
			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
			log.info(name + "=============" + value);
		}

		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['loginForm'].submit();</script>");

		return sbHtml.toString();
	}

	/**
	 * @throws java.io.IOException
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 *             登录初始化
	 *
	 * @return String
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public String loginInit() throws SQLException, DataException, IOException {

		Map<String, String[]> map = request().getParameterMap();

//		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (session().getAttribute("user") != null)
			return "zhanghao";

		/*
		 * DateFormat dateformat = new SimpleDateFormat(UtilDate.simple); String
		 * lastIP = ServletUtils.getRemortIp(); String lastTime = *
		 * dateformat.format(new Date()); Cookie[] cookies =
		 * request().getCookies(); if (cookies != null) { for (Cookie cookie :
		 * cookies) { if ("user".equals(cookie.getName())) { String value =
		 * cookie.getValue(); request().setAttribute("email", value); if
		 * (StringUtils.isNotBlank(value)){ String[] split = value.split(",");
		 * String username = split[0]; String password = split[1]; user =
		 * userService.userLogin1(username, password, lastIP, lastTime);
		 * if(user!=null){ session().setAttribute("user", user); } } } } } if
		 * (session().getAttribute("user") != null) return SUCCESS;
		 */
		return LOGIN;
	}

	/**
	 * 验证用户名和邮箱的唯一性
	 *
	 * @throws com.shove.data.DataException
	 * @throws java.sql.SQLException
	 * @throws java.io.IOException
	 * @return String
	 */
	public String ajaxCheckRegister() throws SQLException, DataException, IOException {
		try {
			String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName")); // 用户名
			String flag = SqlInfusion.FilteSqlInfusion(paramMap.get("flag"));

			Long result = -1L;
			// 判断用户名是否唯一
			if (StringUtils.isNotBlank(userName) && StringUtils.isBlank(flag)) {
				result = userService.isExistEmailORUserName(null, userName);
				if (result > 0) {
					JSONUtils.printStr(IConstants.USER_REGISTER_REPEAT_NAME);
				} else {
					// 检查用户表中是否有重复的名字，如果没有则去t_admin表中
					// Map<String, String> map =
					// adminService.queryIdByUser(userName);
					// if (map == null || map.size() <= 0) {
					// } else {
					// JSONUtils.printStr(IConstants.USER_REGISTER_REPEAT_NAME);
					// }
					JSONUtils.printStr("0");
				}
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用户登录时候的用户名和邮箱验证是否已将激活
	 */
	public String ajaxChecklogin() throws SQLException, DataException, IOException {
		try {
			String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email")); // 电子邮箱
			String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName")); // 用户名
			String flag = SqlInfusion.FilteSqlInfusion(paramMap.get("flag"));
			String cellphone = SqlInfusion.FilteSqlInfusion(paramMap.get("cellphone")); // 用户名
			// 判断邮箱是否唯一
			Long result = -1L;
			Long vidResult = -1L;
			if (StringUtils.isNotBlank(email) && StringUtils.isBlank(flag)) {
				// 检测enable 没有有账号激活的
				result = userService.isUEjihuo(email, null);
				// 不检测enable 检测有没这个账号
				vidResult = userService.isUEjihuo_(email, null);
				if (vidResult < 0) {
					// 没有这个账号
					JSONUtils.printStr("0");
					return null;
					// 有邮箱 但是没有激活
				} else if (result > 0) {
					JSONUtils.printStr("1");
					return null;
				}
				JSONUtils.printStr("4");
				return null;
			}

			// 判断用户名是否唯一
			if (StringUtils.isNotBlank(userName) && StringUtils.isBlank(flag)) {
				// 检测enable 没有有账号激活的
				result = userService.isUEjihuo(null, userName);
				// 不检测enable 检测有没这个账号
				vidResult = userService.isUEjihuo_(null, userName);
				if (vidResult < 0) {
					// 没有这个账号
					JSONUtils.printStr("2");
					return null;
					// 有号 但是没有激活
				} else if (result > 0) {
					JSONUtils.printStr("3");
					return null;
				}
				JSONUtils.printStr("4");
				return null;
			}

			if (StringUtils.isNotBlank(cellphone) && StringUtils.isBlank(flag)) {
				// 检测enable 没有有账号激活的
				// 不检测enable 检测有没这个账号
				vidResult = userService.isPhoneExist(cellphone);
				if (vidResult < 0) {
					// 没有这个账号
					JSONUtils.printStr("5");
					return null;
				}
				JSONUtils.printStr("4");

				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 用户登录
	 *
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public String login() throws Exception {
        if (true){
            JSONUtils.printStr2("这个是老的登录接口");
            return null;
        }
		/*JSONObject json = new JSONObject();

		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		String lastIP = ServletUtils.getRemortIp();
		String lastTime = dateformat.format(new Date());
		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId"));
		String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email"));
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password"));

		User user = null;
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code"));// 验证码
		if (code == null || !_code.equals(code)) {
			json.put("msg", "2");
			JSONUtils.printObject(json);
			return null;
		}
		try {
			user = userService.userLogin1(email, password, lastIP, lastTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (user == null) {
			json.put("msg", "3");
			JSONUtils.printObject(json);
			return null;
		}

		// 查找数据库对象中的enable属性
		user.setEncodeP(Encrypt.encryptSES(password, IConstants.PWD_SES_KEY));
		if (user.getEnable() == 2) {

			json.put("msg", "4");
			JSONUtils.printObject(json);
			return null;
		}
        if (activityOrderService.hasOrder(0,user.getId())>0){
            //用户属于减免物业费的用户 就不能登录了
            json.put("msg", "4");
            JSONUtils.printObject(json);
            return null;
        }
		Cookie cookie = new Cookie("user", email);
		if (!"0".equals(SqlInfusion.FilteSqlInfusion(paramMap.get("addCookie"))))
			cookie.setMaxAge(1209600);// 保存两周
		else
			cookie.setMaxAge(0);
		try {
			response().addCookie(cookie);// 包含中午是报错
		} catch (Exception e) {
		}

		session().setAttribute("user", user);
		String fromUrl = request().getHeader("referer");
		if (fromUrl == null) {
			fromUrl = getBasePath() + "home.do";
		}
		if (fromUrl.endsWith("login.do") || fromUrl.endsWith("logout.do")) {
			fromUrl = fromUrl.replaceAll("login.do", "home.do").replaceAll("logout.do", "home.do");
		}
		// session().setAttribute("fromUrl", fromUrl);
		json.put("msg", "1");
		json.put("fromUrl", fromUrl);
        log.info("---------------------before set token cookie.session(\"platform\"))="+session("platform"));
        if ("appcomm".equals(session("platform"))){
            log.info("---------------------platform = appcomm");
            //json.put("token",getUserToken(user.getId(),user.getPassword()));
            cookie = new Cookie("token", getUserToken(user.getId()));
            cookie.setMaxAge(86400);//保存一天
            try {
                response().addCookie(cookie);
            } catch (Exception e) {
                log.error("---------------------set cookie error");
                log.error(e.getMessage());
            }
        }
		JSONUtils.printObject(json);*/
		// request().setAttribute("idNo", user.getIdNo());
		// // 刷新登录计数
		// userService.loginCountReFresh(user.getId());
		// // 用户登录日志插入
		// if (user.getId() > 0) {
		// userIntegralService.addUserLoginLog(user.getId());
		// }
		// // 用户登录分数
		// Map<String, String> Logmap = null;
		// Map<String, String> Usermap = null;
		// Integer preScore = null;
		// int LongCount = 1;
		// int score = 1;
		// if (user.getId() > 0) {
		// Logmap = userIntegralService.queryUserLoginLong(user.getId());
		// Usermap = userService.queryUserById(user.getId());
		// if (Logmap.size() > 0 && Logmap != null) {
		// preScore = Convert.strToInt(Usermap.get("rating"), 0);
		// LongCount = Convert.strToInt(Logmap.get("cl"), 0);
		// userIntegralService.UpdateLoginRating(user.getId(), score,
		// preScore, LongCount);
		//
		// }
		// }

		return null;
	}

	/**
	 * 验证邮箱
	 *
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public String verificationEmial() throws Exception {
		String key = SqlInfusion.FilteSqlInfusion(request("key").trim());
		String msg = "邮箱验证失败";
		String[] keys = key.split("-");
		if (2 == keys.length) {
			DesSecurityUtil des = new DesSecurityUtil();
			Long userId = Convert.strToLong(des.decrypt(keys[0].toString()), -1);

			String dateTime = des.decrypt(keys[1].toString());
			long curTime = new Date().getTime();
			// 当用户点击注册时间小于10分钟
			if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {
				// 修改用户状态
				Long result = userService.frontVerificationEmial(userId);
				if (result > 0) {
					msg = "恭喜您帐号激活成功！请点击<a href='login.do'>登录</a>";
					ServletActionContext.getRequest().setAttribute("msg", msg);
				} else {
					msg = "注册失败";
					// 这里还要写一个用户删除账号和密码
					ServletActionContext.getRequest().setAttribute("msg", msg);
				}
			} else {
				msg = "连接失效,<strong><a href='reSend.do?id=" + userId + "'>点击重新发送邮件</a></strong>";
				ServletActionContext.getRequest().setAttribute("msg", msg);
			}
		}
		return SUCCESS;

	}

	/**
	 * 重新发送邮件
	 *
	 * @throws Exception
	 */
	public String reSendEmail() throws Exception {
		DesSecurityUtil des = new DesSecurityUtil();
		String key1 = des.encrypt(ServletActionContext.getRequest().getParameter("id"));
		String key2 = des.encrypt(new Date().getTime() + "");
		String url = getPath(); // request().getRequestURI();
		String VerificationUrl = url + "verificationEmial.do?key=" + key1 + "-" + key2;
		long userId = Convert.strToLong(ServletActionContext.getRequest().getParameter("id"), -1);
		// 获取用户email地址 和 userName
		Map<String, String> reMap = null;

		reMap = userService.queryUserById(userId);

		if (null != reMap && reMap.size() > 0) {
			String userName = reMap.get("username");
			String email = reMap.get("email");
			// 发送验证邮件
			sendMailService.sendRegisterVerificationEmail(VerificationUrl, userName, email);
			int dd = email.indexOf("@");
			String mailAddress = null;
			if (dd >= 0) {
				mailAddress = "mail." + email.substring(dd + 1);
			}
			request().setAttribute("emaladdresss", mailAddress);
		}
		return SUCCESS;
	}

	/**
	 * 邮箱提示信息跳转
	 *
	 */
	public String tip() {
		String emaladdresss = SqlInfusion.FilteSqlInfusion(request().getParameter("emaladdresss"));
		request().setAttribute("emaladdresss", emaladdresss);
		return SUCCESS;
	}

	/**
	 * 用户登录后的页面
	 *
	 * @return
	 */
	public String jumpUser() {
		return SUCCESS;
	}

	/**
	 * 虚拟用户登录时没有权限跳转页面
	 *
	 * @return
	 */
	public String noPermission() {
		return SUCCESS;
	}

	/**
	 * @MethodName: logout
	 * @Param: FrontLongRegisterAction
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午11:04:19
	 * @Return:
	 * @Descb: 退出系统
	 * @Throws:
	 */
	public String logout() throws Exception {
		request().getSession().removeAttribute("user");
		session().removeAttribute("user");
		// request().getSession().invalidate();
		session().removeAttribute("bbs");
		session().removeAttribute("hhnflag");
		paramMap.clear();
		paramMap.put("email", "123");
		bbsRegisterService.doExitByAsynchronousMode();
        session().invalidate();
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(-1);
        try {
            response().addCookie(cookie);
            String fromUrl = request("fromUrl");
            if("".equals(fromUrl) || StringUtils.isEmpty(fromUrl)){
            	return LOGIN;
            }else {
            	try {
					response().sendRedirect(fromUrl);
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
            }
        } catch (Exception e) {
            log.error("---------------------set cookie error");
            log.error(e.getMessage());
        }
		// getOut().print("<script>parent.location.href='userInit.do?id=00';</script>");
		return LOGIN;
	}

	public void setSendMailService(SendMailService sendMailService) {
		this.sendMailService = sendMailService;
	}

	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

	public void setHomeInfoSettingService(HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
		this.bbsRegisterService = bbsRegisterService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	// 获取手机验证码
	public String telephoneCode() throws Exception {
		response().setContentType("text/html;charset=utf-8");
		String telephone = Convert.strToStr(paramMap.get("telephone"), null);
		// String userName = paramMap.get("userName"); // 用户名

		// 查询手机号码是否存在
		long result1 = userService.queryUserIdByPhone(telephone);
		if (result1 > 0) {
			JSONUtils.printStr("5");// 手机号码已经存在
			return null;
		}

		if (telephone == null) {
			JSONUtils.printStr("-1");// 获取验证码失败
			return null;
		}
		StringBuffer telephoneCode = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			telephoneCode.append((int) (Math.random() * 10));
		}
		try {
			// 发送短信
			Map<String, String> map = sMsService.getSMSById(1);

			String content = "尊敬的客户您好，请输入手机验证码"+telephoneCode.toString()+"，有效期120秒。客服热线400-8303-737。";

			String retCode = SMSUtil.sendSMS(map.get("Account"), map
                    .get("Password"), content, telephone, null);

			if ("Sucess".equals(retCode)) {
				JSONUtils.printStr("1");
				session().setAttribute("randomCode", telephoneCode.toString());
				session().setAttribute("phone", telephone);
				JSONUtils.printStr2("1");
				return null;
			} else {
				JSONUtils.printStr("-1");// 获取验证码失败
				return null;
			}
		} catch (Exception ex) {
			JSONUtils.printStr("-1");// 获取验证码失败
			return null;
		}
	}

	/**
	 * 去邮箱找回
	 *
	 * @return
	 */
	public String emailhhn() {
		return SUCCESS;
	}

	/**
	 * 去手机找回
	 *
	 * @return
	 */
	public String phonehhn() {
		return SUCCESS;
	}

	/**
	 * 去安全码找回
	 *
	 * @return
	 */
	public String questionhhn() {
		return SUCCESS;
	}

	/**
	 * 判断用户名是否存在
	 *
	 * @return [参数说明]
	 *
	 * @return String [返回类型说明]
	 * @throws Exception
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String isExistUserName() throws Exception {
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		Long result = userService.isExistEmailORUserName(null, userName);

		if (result < 0) { // 用户名不存在
			JSONUtils.printStr("1");
		}
		return null;
	}

	/**
	 * 根据用户名查询手机是否存在
	 *
	 * @return [参数说明]
	 *
	 * @return String [返回类型说明]
	 * @throws Exception
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String isExistPhone() throws Exception {
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String phone = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("telephone")), null);
		Map<String, String> map = null;
		map = userService.isExistPhone(phone, userName);
		if (map == null) { // 手机号是否存在
			JSONUtils.printStr("1");
		}
		return null;
	}

	// 获取手机验证码
	public String telephoneCodeQuestion() throws Exception {
		response().setContentType("text/html;charset=utf-8");
		String telephone = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("telephone")), null);

		if (telephone == null) {
			JSONUtils.printStr("-1");// 获取验证码失败
			return null;
		}
		StringBuffer telephoneCode = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			telephoneCode.append((int) (Math.random() * 10));
		}
		session().setAttribute("telephoneCode", telephoneCode.toString());
		try {
			// String result = ClientMessages.clusterSend("67229:admin",
			// "hyn12345",
			// "", telephone, "您注册合和年信贷会员的手机验证码为："+telephoneCode.toString(), "",
			// "0");
			// result =
			// result.substring(result.indexOf("<code>")+6,result.indexOf("</code>"));
			String result = "1000";

			StringBuffer s = new StringBuffer();

			int res = Integer.parseInt(result);
			if (res == 1000) {
				res = 1;
				s.append("{");
				s.append("code:" + res + ",");
				s.append("message:" + telephoneCode.toString());
				s.append("}");
			} else {
				res = 2;
			}
			PrintWriter pw = response().getWriter();
			pw.print(s);
		} catch (Exception ex) {
			JSONUtils.printStr("-1");// 获取验证码失败
			return null;
		}
		return null;
	}

	/**
	 * 去修改密码--验证手机短信
	 *
	 * @return
	 * @throws java.io.IOException
	 *             [参数说明]
	 *
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String doUpdatePwd() throws IOException {
		String confirmTelephone = SqlInfusion.FilteSqlInfusion((String) session().getAttribute("telephoneCode"));
		String _confirmTelephone = SqlInfusion.FilteSqlInfusion(paramMap.get("code"));// 手机验证码

		if (confirmTelephone == null || !_confirmTelephone.equals(confirmTelephone)) {
			JSONUtils.printStr("1");
			return null;
		} else {
			session().removeAttribute(confirmTelephone);
		}
		return null;
	}

	/**
	 * 验证问题是否正确
	 *
	 * @return
	 * @return String [返回类型说明]
	 * @throws Exception
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String doUpdateQusertionPwd() throws Exception {
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName"));
		String question = SqlInfusion.FilteSqlInfusion(paramMap.get("question"));
		String answer = SqlInfusion.FilteSqlInfusion(paramMap.get("answer"));
		Map<String, String> userList = userService.queryUserList(userName); // 根据用户名查询用户ID
		Map<String, String> answerMap = userService.queryOldAnswer(Long.valueOf(userList.get("id")), answer, question);
		if (answerMap == null || answerMap.equals("")) {
			JSONUtils.printStr("1");
			return null;
		}
		return null;
	}

	/**
	 * 页面跳转--修改密码
	 *
	 * @return [参数说明]
	 *
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String doUpdatePwdes() {
		String userName = SqlInfusion.FilteSqlInfusion(request().getParameter("userName"));// 用户名称
		request().setAttribute("userName", userName);
		return SUCCESS;
	}

	/**
	 * 修改用户登录密码
	 *
	 * @return
	 * @throws Exception
	 */
	public String updateQusetionPass() throws Exception {
		String userName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("userName")), null);
		String password = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("password")), null);
		String type = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("type")), null);
		String email = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("email")), null);

		if ("1".equals(IConstants.ENABLED_PASS)) {
			password = Encrypt.MD5(password.trim());
		} else {
			password = Encrypt.MD5(password.trim() + IConstants.PASS_KEY);
		}
		long result = homeInfoSettingService.updatePhonePwd(userName, password, type, email);
		if (result > 0) {
			JSONUtils.printStr("1");
		} else {
			JSONUtils.printStr("2");
		}
		return null;
	}

	/** 用户注册   */
	private static final char[] pwd_str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static final Random RANDOM=new Random();
	public String colourlife() throws ServletException, IOException, SQLException, DataException {
		String userName = request("username"); // 用户名
		String telephone = request("mobile");// 手机号
		String cid = request("cid");//小区ID
		String cname = request("cname");// 小区名
		String caddress = request("caddress");// 用户地址
		String busGroupName = request("branchName");// 用户所属事业部
        if (StringUtils.isNotBlank(busGroupName)){
            busGroupName = URLDecoder.decode(busGroupName,"utf-8");
        }
		String tjrid = Convert.strToStr(request("tjrid"),"");// 彩生活推荐人id
		String userid = request("userid");// 彩生活用户ID,用户唯一标识，数字1-10位
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(telephone) || StringUtils.isBlank(userid) || telephone.length() != 11 || StringUtils.isBlank(request("password"))){
			request().setAttribute("title", "请求参数非法");
			log.info("colourlife:请求参数非法");
			return "msg";
		}

		if(checkColorLifeSign(telephone,userName,Long.parseLong(userid))){//验证签名
			request().setAttribute("title", "签名验证失败");
			log.info("colourlife:签名验证失败");
			return "msg";
		}

		String passwords = request("password");// 用户唯一码（用于校验使用）,彩之云是根据userid进行一定的加密运算生成32位字符串，与userid一一对应；
		Map<String, String> map = userService.getColourlifeName(userName, telephone, userid,passwords,"");
		long result = Convert.strToLong(map.get("ret"), 0);// 若已注册,返回0 ;若可注册,返回1
															// ;若信息重复,不可注册,返回-1,  若信息被篡改,返回100
		if(result==100){
			request().setAttribute("title", "请求参数非法");
			log.info("colourlife:result=100，请求参数非法");
			return "msg";
		}
        int registerType=4; //注册类型 默认是e理财
        if ("colorlifewyf".equals(request("via"))){
            registerType = 6;
        }else if( "colorlifeweb".equals(session("platform"))){
            registerType=2;
        }
		if (result < 0) {// 手机号码已存在
            if ("colorlifewyf".equals(request("via"))){
                long c = investService.hasInvest(Long.parseLong(map.get("userId")));
                if (c>0){
                    c = activityOrderService.hasOrder( 0, Long.parseLong(map.get("userId")));
                    if (c<=0){
                        request().setAttribute("title", "该手机号码已经注册过合和年在线并投资，不能使用减免物业费服务");
                        return "msg";
                    }
                }
                session().setAttribute("wyf_activityOrderDo",buildWyfOrder(Long.parseLong(map.get("userId"))));
                request().setAttribute("ordNo",request("ordNo"));
            }
			session().setAttribute("colorcheck", passwords);
			session().setAttribute("colorid", userid);
			session().setAttribute("color-phone", telephone);
			return "colourlife-bind-hhn";
		}else if (result > 0) {// 可注册,进行注册,返回注册页面完善信息
//            if ("colorlifeapp".equals(session("platform"))||"colorlifewyf".equals(request("via"))) {
                StringBuilder sb=new StringBuilder();
                while (sb.length()<8) {
                    sb.append(pwd_str[RANDOM.nextInt(36)]);
                }
                int typelen = -1;
                String md5Password;
                if ("1".equals(IConstants.ENABLED_PASS)) {
                    md5Password = Encrypt.MD5(sb.toString());
                } else {
                    md5Password = Encrypt.MD5(sb.toString() + IConstants.PASS_KEY);
                }

                Map<String, String> rmap = userService.userRegister1(telephone, map.get("msg"), md5Password, -1 + "", null, typelen, null,registerType);// 注册用户
                long userId = Convert.strToLong(rmap.get("ret"), -1);
                if (userId < 0) { // 注册失败
                    JSONUtils.printStr2(rmap.get("ret_desc"));
                    return null;
                }
                try {
                    //彩生活用户直接手机号认证通过
                    PhoneVerifyDo phoneVerifyDo = new PhoneVerifyDo();
                    phoneVerifyDo.setUserId(userId);
                    phoneVerifyDo.setMobilePhone(telephone);
                    phoneVerifyDo.setStatus(1);
                    phoneVerifyService.savePhoneVerify(phoneVerifyDo);
                }catch (Exception e){
                    LOG.error(e.getMessage(),e);
                }

                userService.updateUserCheck(userid,passwords,"", userId);// 保存彩生活用户验证信息
            /*}else{
                //才生活网站端
                request().setAttribute("userName",map.get("msg"));
                request().setAttribute("telephone",telephone);
                request().setAttribute("userid",userid);
                request().setAttribute("passwords",passwords);
                return "register";
            }*/

        }

		//用户已存在 进行登录
        String password=userService.getUserPassword(userid,""); //根据彩之云id查询登录密码
		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		String lastIP = ServletUtils.getRemortIp();
		String lastTime = dateformat.format(new Date());
        AccountUserDo user = null;
		try {
            user = userService1.loginWithPwd(map.get("msg"), password);
//			user = //userService.userLogin2(map.get("msg"), password, lastIP, lastTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(user == null){
			request().setAttribute("title", "身份已过期,请重新登录");
			return "msg";
		}

        // 查找数据库对象中的enable属性
//        user.setEncodeP(Encrypt.encryptSES(password, IConstants.PWD_SES_KEY));
        if ( !"colorlifewyf".equals(request("via"))) {//用户属于减免物业费的用户 就不能通过e理财进入了
            long c = activityOrderService.hasOrder( 0, user.getId());
            if (c>0){
                String uri = request().getRequestURI();
                if (!StringUtils.contains(uri,"/webapp/")){
                    //非手机版的
                    request().setAttribute("title", "已经是减免物业费用户，e理财不能使用了");
                    return "msg";
                }else {
                    request().getSession().setAttribute("colourlifeFlag",WebConstants.COLOUR_LIFE_Flag_WYF);
                }

            }

        }else {
            long c = investService.hasInvest(user.getId());
            if (c>0){
                c = activityOrderService.hasOrder( 0, user.getId());
                if (c<=0){
                    request().setAttribute("title", "客官莫急，经小二查询，您的账户已注册e理财，为保证您的资金安全，重新注册彩之云账户才是最明智的选择啦~省钱更安全，请客官返回彩之云更换账号后再来参加活动。");
                    return "msg";
                }

            }

        }

		user.setUserGroup(0);
		session().setAttribute("user", user);
		if (StringUtils.isNotBlank(tjrid)) {
			userService.updateCshTjr(user.getId(), tjrid);
		}
		if (registerType == 4 || registerType==2) {
			try {
				userService.saveColourInfo(user.getId(), Convert.strToInt(userid,-1), Convert.strToInt(cid,-1), cname, caddress, Convert.strToInt(tjrid, -1),busGroupName);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}

		}else if(registerType == 6){
            //减免物业费用户
            return "wyf";
        }
        return SUCCESS;

	}

	/**验证签名,失败返回true**/
	private boolean checkColorLifeSign(String telephone, String userName, long userid) {
        if (IConstants.ISDEMO.equals("1")){
            return false;
        }
		String sign = request("sign");
		String passwords = request("password");
		String bno = request("bno");
		String tjrid = request("tjrid");
		String bsecret = request().getParameter("bsecret");
		String cid = request().getParameter("cid");
		String cname = request().getParameter("cname");
		String branchName = request().getParameter("branchName");
		String caddress = request("caddress");
		log.info("checkColorLifeSign---cname="+cname+",caddress="+caddress);
		if (StringUtils.isNotBlank(cname)) {
			try {
				cname=URLDecoder.decode(cname,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(caddress)) {
			try {
				caddress=URLDecoder.decode(caddress,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
        if (StringUtils.isNotBlank(branchName)) {
            try {
                branchName=URLDecoder.decode(branchName,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
		StringBuilder param = new StringBuilder("DJKC#$%CD%des$");
		param.append("bno"+bno).append("bsecret"+bsecret).append("userid"+userid).append("username"+userName)
		.append("mobile"+telephone).append("password"+passwords).append("cid"+cid).append("tjrid"+tjrid).append("branchName"+branchName)
		.append("cname" + cname).append("caddress"+caddress).append("DJKC#$%CD%des$");
//		String check = Encrypt.MD5("DJKC#$%CD%des$bnobsecretuserid113153usernameuser_15527730750mobile15527730750password6de9da5b1b19eb9ee9aefa540dd51672cid1tjridbranchName碧水龙庭cname碧水龙庭caddress广东省-深圳市-龙华新区-碧水龙庭DJKC#$%CD%des$");
        String check = DigestUtils.md5Hex(param.toString());
        log.info("e理财签名：param:"+param.toString());
        check=check.toUpperCase();
        log.info("e理财签名：check:"+check);
		if(check.equals(sign)){
			return false;
		}
		return false;
	}

	/**当彩生活用户手机号码已存在的时候,让用户输入网站的用户名密码，将彩生活账号与合和年账号绑定**/
	public String colourlifeBindHhnUser() throws Exception {
		JSONObject json = new JSONObject();
		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		String lastIP = ServletUtils.getRemortIp();
		String lastTime = dateformat.format(new Date());
		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId"));
		String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email"));
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password"));

		AccountUserDo user = null;
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code"));// 验证码
		if (code == null || !_code.equals(code)) {
			json.put("msg", "2");
			JSONUtils.printObject(json);
			return null;
		}
		try {
//			user = userService1.userLogin1(email, password, lastIP, lastTime);
            String pwdMd5 = DigestUtils.md5Hex(password + WebConstants.PASS_KEY);
            user = userService1.loginWithPwd(email, pwdMd5);
			String colorPhone=(String) session().getAttribute("color-phone");
			if(user!=null){
				if(user.getMobilePhone().equals(colorPhone)){
					//校验成功 进行用户绑定
					Object object = session().getAttribute("colorid");
					String colorid=object==null?"":object.toString();
					String colorcheck= (String) session().getAttribute("colorcheck");
					if(colorid!=null&&StringUtils.isNotBlank(colorcheck)){
						userService.updateUserCheck(colorid, colorcheck,"", user.getId());
						String tjrid = Convert.strToStr(request("tjrid"),"");// 彩生活推荐人id
						userService.updateCshTjr(user.getId(), tjrid);
					}
				}else{
					//输入账号的手机号码和彩之云手机号码不一致
					json.put("msg", "5");
					JSONUtils.printObject(json);
					return null;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (user == null) {
			json.put("msg", "3");
			JSONUtils.printObject(json);
			return null;
		}

		// 查找数据库对象中的enable属性
//		user.setEncodeP(Encrypt.encryptSES(password, IConstants.PWD_SES_KEY));
		user.setUserGroup(0);
		session().setAttribute("user", user);
		json.put("msg", "1");
		JSONUtils.printObject(json);
		return null;
	}
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final DateFormat DATE_FORMAT1 = new SimpleDateFormat("yyyyMMddHHmmss");
	private ActivityOrderDo buildWyfOrder(Long userId){
        String beginDateStr = request("beginDate");
        String endDateStr = request("endDate");
        String ordDateStr = request("ordDate");
        try {
            String ordNo = request("ordNo");
            if (StringUtils.isNotBlank(ordNo)) {
                Date beginDate = DATE_FORMAT.parse(beginDateStr);
                Date endDate = DATE_FORMAT.parse(endDateStr);
                Date ordDate = DATE_FORMAT1.parse(ordDateStr);
                String investAmountStr = request("investAmount");
                String deductAmountStr = request("deductAmount");
                String deductPerMonthStr = request("deductPerMonth");
                String profitStr = request("profit");
                String billingAddress = URLDecoder.decode(request("billingAddress"),"utf-8");
                String cId = request("cId");
                String cName = URLDecoder.decode(request("cName"),"utf-8");
                int investAmount = Integer.parseInt(investAmountStr);
                double deductAmount = Double.parseDouble(deductAmountStr);
                double deductPerMonth = Double.parseDouble(deductPerMonthStr);
                double profit = Double.parseDouble(profitStr);
                ActivityOrderDo activityOrderDo = new ActivityOrderDo();
                activityOrderDo.setUserId(userId);
                activityOrderDo.setOrdType(0);
                activityOrderDo.setInvestAmount(investAmount);
                activityOrderDo.setDeductAmount(deductAmount);
                activityOrderDo.setDeductPerMonth(deductPerMonth);
                activityOrderDo.setProfit(profit);
                activityOrderDo.setcName(cName);
                activityOrderDo.setcId(cId);
                activityOrderDo.setBillingAddress(billingAddress);
                activityOrderDo.setBeginDate(beginDate);
                activityOrderDo.setEndDate(endDate);
                activityOrderDo.setOrdDate(ordDate);
                activityOrderDo.setOrdNo(ordNo);
                activityOrderDo.setOrdStatus(0);
                Date now = new Date();
                activityOrderDo.setCreateTime(now);
                activityOrderDo.setUpdateTime(now);
                activityOrderDo.setRechargeMoney(0.0);
                return activityOrderDo;
               /* Long id = activityOrderService.addActivityOrder(activityOrderDo);
                if (id<=0){
                    //保存失败
                    log.error("addActivityOrder 失败了");
                }else {
                    activityOrderDo.setOrdId(id);
                }*/
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
	/*
	 * 通用app的入口
	 */
	public String appBindUser() throws Exception {
		String via = request("via");// 手机号
		String uid = request("uid");// 用户ID,用户唯一标识
		String userName = request("username"); // 用户名
		String telephone = request("mobile");// 手机号
		String realName = request("realName");// 真实姓名
		String idNo = request("idNo");// 身份证号码
		String email = request("email");// 邮箱
		if (StringUtils.isBlank(via) || StringUtils.isBlank(uid) ){
			request().setAttribute("title", "请求参数非法");
			log.info("appBindUser:请求参数非法");
			return "msg";
		}

		/*if(checkColorLifeSign(telephone,userName,userid)){//验证签名
			request().setAttribute("title", "签名验证失败");
			log.info("colourlife:签名验证失败");
			return "msg";
		}*/

		Map<String, String> map = userService.getColourlifeName(userName, telephone, uid,"",via);
		long result = Convert.strToLong(map.get("ret"), 0);// 若已注册,返回0 ;若可注册,返回1
															// ;若信息重复,不可注册,返回-1,  若信息被篡改,返回100
		if(result==100){
			request().setAttribute("title", "请求参数非法");
			log.info("colourlife:result=100，请求参数非法");
			return "msg";
		}
		if (result < 0) {// 不可注册
			/*request().setAttribute("title", map.get("msg"));
			return "msg";*/
			session().setAttribute("colorid", uid);
			session().setAttribute("via", via);
			session().setAttribute("color-phone", telephone);
			return "app-bind-hhn";
		}

		//String password = telephone.substring(2); // 初始用户密码为手机后9位
		String tips = null;
		String password=null;
		if (result > 0) {// 可注册,进行注册,返回注册页面完善信息
			StringBuilder sb=new StringBuilder();
			while (sb.length()<8) {
				sb.append(pwd_str[RANDOM.nextInt(36)]);
			}
			int typelen = -1;
			String md5Password="";
			if ("1".equals(IConstants.ENABLED_PASS)) {
				md5Password = Encrypt.MD5(sb.toString());
			} else {
				md5Password = Encrypt.MD5(sb.toString() + IConstants.PASS_KEY);
			}
			int registerType=1; //注册类型
			if ("jfq".equals(via)) {
				registerType = 5;
			}
			Map<String, String> rmap = userService.userRegister1(telephone, map.get("msg"), md5Password, -1 + "", null, typelen, null,registerType);// 注册用户
			long userId = Convert.strToLong(rmap.get("ret"), -1);
			if (userId < 0) { // 注册失败
				JSONUtils.printStr2(rmap.get("ret_desc"));
				return null;
			}else{
				userService.updateUserCheck(uid, "",via, userId);
			}
		}

		// 直接登录
		if (StringUtils.isBlank(password)) {
			password=userService.getUserPassword(uid,via); 
		}
		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		String lastIP = ServletUtils.getRemortIp();
		String lastTime = dateformat.format(new Date());
		AccountUserDo user = null;
		try {
//			user = userService.userLogin2(map.get("msg"), password, lastIP, lastTime);
            user = userService1.loginWithPwd(map.get("msg"), password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(user == null){
			request().setAttribute("title", "身份已过期,请重新登录");
			return "msg";
		}
		/*if (user.getRealName() == null) {
			request().setAttribute("idNo", "noIdNo");// 会员身份证号码
			request().setAttribute("realName", "norealName");// 真实姓名
		}*/
		request().setAttribute("tips", tips);
		user.setUserGroup(0);
		session().setAttribute("user", user);
		try {
			Cookie cookie = new Cookie("user", userName);
			if (!"0".equals(paramMap.get("addCookie")))
				cookie.setMaxAge(1209600);// 保存两周
			else
				cookie.setMaxAge(0);
			response().addCookie(cookie);
		} catch (Exception e) {
		}
		
		return SUCCESS;
	}
	
	
	/**当app用户手机号码已存在的时候,让用户输入网站的用户名密码，将app账号与合和年账号绑定**/
	public String appBindHhnUser() throws Exception {
		JSONObject json = new JSONObject();
		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		String lastIP = ServletUtils.getRemortIp();
		String lastTime = dateformat.format(new Date());
		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId"));
		String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email"));
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password"));

		AccountUserDo user = null;
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code"));// 验证码
		if (code == null || !_code.equals(code)) {
			json.put("msg", "2");
			JSONUtils.printObject(json);
			return null;
		}
		try {
//			user = userService.userLogin1(email, password, lastIP, lastTime);
            user = userService1.loginWithPwd(email, password);
			String colorPhone=(String) session().getAttribute("color-phone");
			if(user!=null){
				if(user.getMobilePhone().equals(colorPhone)){
					//校验成功 进行用户绑定
					Object object = session().getAttribute("colorid");
					Object object1 = session().getAttribute("via");
					String colorid=object==null?"":object.toString();
					String via=object1==null?"":object1.toString();
					if(colorid!=null&&StringUtils.isNotBlank(via)){
						userService.updateUserCheck(colorid, "",via, user.getId());
					}
				}else{
					//输入账号的手机号码和app手机号码不一致
					json.put("msg", "5");
					JSONUtils.printObject(json);
					return null;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (user == null) {
			json.put("msg", "3");
			JSONUtils.printObject(json);
			return null;
		}

		// 查找数据库对象中的enable属性
//		user.setEncodeP(Encrypt.encryptSES(password, IConstants.PWD_SES_KEY));
		user.setUserGroup(0);
		session().setAttribute("user", user);
		json.put("msg", "1");
		JSONUtils.printObject(json);
		return null;
	}

    private String getUserToken(long userId) throws Exception {
        DesSecurityUtil des = new DesSecurityUtil();
        Object platform = session("platform");
        Object appvia = session("sourcefrom");
        String x = userId+"-"+(platform!=null?platform.toString():"")+"-"+(appvia!=null?appvia.toString():"");
        String key1 = des.encrypt(x);

        return key1;
    }
    
    /**
     * 链接到抢红包页面
     */
    public String toRobflow(){
//		String appid = "WXEAE7B1B55B3A43B0";
//        String secret = "68753837e1095566193d526bf0cf867f";
//        String openId = request("openid");
//        
//        String token_param ="grant_type=client_credential&appid="+ appid+"&secret="+secret;
//    	WechatResult t = WechatUtils.httpRequest("https://api.weixin.qq.com/cgi-bin/token?", "GET",token_param);
//    	
//    	String param ="access_token="+ t.getAccess_token()+"&openid="+openId+"&lang=zh_CN";
//    	WechatResult r = WechatUtils.httpRequest("https://api.weixin.qq.com/cgi-bin/user/info?", "GET",param);
//    	if(r.getNickname()!=null){
//    		session().setAttribute("nickname", r.getNickname());
//        	session().setAttribute("headimgurl", r.getHeadimgurl());
//    	}
		return "success";
    }
    
    
    /**
     * 抢流量活动
     */
    public String robflow(){
    	String mobile = SqlInfusion.FilteSqlInfusion(paramMap.get("mobile"));
		session().setAttribute("mobile", mobile);
		return "success";
    }
    
    public void placeOrder(){ 
//    	String mobile = request("mobile");
//    	//抢过红包的用户不能再次参与抢红包   
//		String mo = PropertiesUtils.readValue("order.properties", mobile);
//		if(mo == null){
//    		try {
//    			int flowSize = Lottery.lottery(mobile);//流量大小
//				LiumiClient.placeOrder(mobile,Lottery.Flag+flowSize,false);//手机号，流量规格
//				session().setAttribute("flowSize",flowSize);
//				return "success";//下订单成功
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
    	toRobflow();
//		return "failure";
    }
    
    /**
     * pc 连接到活动页
     */
    public String actNotice(){
    	return "success";
    }
    
    /**
     * 母亲节活动banner 连接到活动介绍页
     */
    public String motherDayNotice(){
    	return "success";
    }
}
