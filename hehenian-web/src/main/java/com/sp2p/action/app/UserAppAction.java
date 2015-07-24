package com.sp2p.action.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.rpc.ServiceException;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.SMSUtil;
import com.shove.util.UtilDate;
import com.shove.web.util.JSONUtils;
import com.shove.web.util.ServletUtils;
import com.shovesoft.SMS;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.entity.LoginVerify;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.SMSInterfaceService;

public class UserAppAction extends BaseAppAction {
	public static Log log = LogFactory.getLog(UserAppAction.class);
	private static final long serialVersionUID = -9011135946028616456L;

	private UserService userService;
	private UserIntegralService userIntegralService;
	private HomeInfoSettingService homeInfoSettingService;
	private SMSInterfaceService sMSInterfaceService;

	// private BBSRegisterService bbsRegisterService;

	public String login() throws IOException {

		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		Map<String, String> jsonMap = new HashMap<String, String>();
		AccountUserDo user = new AccountUserDo();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String name = appInfoMap.get("name");
			String pwd = appInfoMap.get("pwd");
			// 查找数据库对象中的enable属性
			if (StringUtils.isBlank(name)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户名或手机号为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (StringUtils.isBlank(pwd)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "密码为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String lastIP = ServletUtils.getRemortIp();
			String lastTime = dateformat.format(new Date());
			LoginVerify loginVerify = null;
			// pwd = Encrypt.MD5(pwd);
			Map<String, String> userMap = userService.queryUserByUserAndPwd(
					name, pwd);

			if (userMap == null || userMap.isEmpty()) {

				jsonMap.put("error", "3");
				jsonMap.put("msg", "用户名或密码错误");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.putAll(userMap);
			}

			long userId = Convert.strToLong(jsonMap.get("id"), -1);
			// 刷新登录计数
			userService.loginCountReFresh(userId);

			// 用户登录日志插入
			if (userId > 0) {
				userIntegralService.addUserLoginLog(userId);
			}
			// 用户登录分数
			Map<String, String> logmap = null;
			Map<String, String> usermap = null;
			Integer preScore = null;
			int LongCount = 1;
			int score = 1;
			if (userId > 0) {
				logmap = userIntegralService.queryUserLoginLong(userId);
				usermap = userService.queryUserById(userId);
				if (logmap.size() > 0 && logmap != null) {
					preScore = Convert.strToInt(usermap.get("rating"), 0);
					LongCount = Convert.strToInt(logmap.get("cl"), 0);
					userIntegralService.UpdateLoginRating(userId, score,
							preScore, LongCount);

				}
			}

			if (userMap != null && userMap.size() > 0) {
				user = new AccountUserDo();
				user.setAuthStep(Convert.strToInt(userMap.get("authStep"), -1));
				user.setEmail(Convert.strToStr(userMap.get("email"), null));
				user.setPassword(Convert
						.strToStr(userMap.get("password"), null));
				user.setId(Convert.strToLong(userMap.get("id"), -1L));

				user.setUsername(Convert
						.strToStr(userMap.get("username"), null));
				user.setVipStatus(Convert
						.strToInt(userMap.get("vipStatus"), -1));


			}
			session().setAttribute("user", user);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "登录成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);

		}
		return null;
	}

	public String register() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String name = appInfoMap.get("name");
			String pwd = appInfoMap.get("pwd");
			// String confirmPwd = appInfoMap.get("confirmPwd");
			if (StringUtils.isBlank(name)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户名不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (StringUtils.isBlank(pwd)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String cellPhone = appInfoMap.get("cellPhone");
			if (StringUtils.isBlank(cellPhone)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "手机号码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long str = userService.queryUserIdByPhone(cellPhone);
			if (str > 0) {
				jsonMap.put("error", "9");
				jsonMap.put("msg", "该手机号已注册！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// String code = appInfoMap.get("code");
			// if(StringUtils.isBlank(code)){
			// jsonMap.put("error", "4");
			// jsonMap.put("msg", "验证码不能为空");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			//			
			// String randomCode =
			// Encrypt.decryptSES(appInfoMap.get("randomCode"),AlipayConfig.ses_key);

			// if(!code.equals(randomCode)){
			// jsonMap.put("error", "5");
			// jsonMap.put("msg", "验证码不正确");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }
			//			
			// String recivePhone =
			// Encrypt.decryptSES(appInfoMap.get("recivePhone"),AlipayConfig.ses_key);
			// if(!cellPhone.equals(recivePhone)){
			// jsonMap.put("error", "6");
			// jsonMap.put("msg", "手机号跟接收验证码手机号不一致");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }
			// 验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$

			if (name.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "用户名包含特殊字符");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// -----

			Long userId = -1L;

			Long result = userService.isExistEmailORUserName(null, name);
			if (result > 0) { // 用户名重复
				jsonMap.put("error", "6");
				jsonMap.put("msg", "用户名已经存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			int typelen = -1;
			Map<String, String> lenMap = null;
			lenMap = userService.querymaterialsauthtypeCount(); // 查询证件类型主表有多少种类型
			if (lenMap != null && lenMap.size() > 0) {
				typelen = Convert.strToInt(lenMap.get("cccc"), -1);
				// 调用service
				if (typelen != -1) {
					userId = userService.userAppRegister(null, name, pwd, "",
							null, typelen, cellPhone);// 注册用户 和 初始化图片资料
				}
			}

			if (userId < 0) { // 注册失败
				jsonMap.put("error", "7");
				jsonMap.put("msg", "注册失败");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				// 添加论坛用户
				/*
				 * User user = new User(); user.setUserName(userName);
				 * user.setPassword(password); user.setEmail(email);
				 * bbsRegisterService.doRegisterByAsynchronousMode(user);
				 */

				// 添加通知默认方法
				homeInfoSettingService.addNotes(userId, false, true, false);
				homeInfoSettingService.addNotesSetting(userId, false, false,
						false, false, false, true, true, true, true, true,
						false, false, false, false, false);

				jsonMap.put("error", "-1");
				jsonMap.put("msg", "注册成功");
				jsonMap.put("id", userId + "");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 发送短信
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws DataException
	 * @throws DocumentException
	 */
	public String sendSMS() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();

		try {
			Map<String, String> appInfoMap = getAppInfoMap();

			String phone = appInfoMap.get("cellPhone");
			if (StringUtils.isBlank(phone)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "手机号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// 随机产生4位数字
			int intCount = 0;
			intCount = (new Random()).nextInt(9999);// 最大值位9999
			if (intCount < 1000)
				intCount += 1000; // 最小值位1001

			String randomCode = intCount + "";

			// 发送短信
			Map<String, String> map = sMSInterfaceService.getSMSById(1);

			String content = "尊敬的客户您好,欢迎使用桂林市合和年信贷,手机验证码为:[" + randomCode + "]";
			// 获取短信接口url
			// String url=SMSUtil.getSMSPort(map.get("url"), map.get("UserID"),
			// map.get("Account"), map.get("Password"), randomCode, content,
			// phone, null);
			String retCode = SMSUtil.sendSMS(map.get("Account"), map
					.get("Password"), content, phone, randomCode);

			// 发送短信
			if ("Sucess".equals(retCode)) {
				// JSONUtils.printStr("1");
				// session().setAttribute("randomCode",randomCode);
				// session().setAttribute("phone",phone);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "发送成功");
				jsonMap.put("randomCode", Encrypt.encryptSES(randomCode,
						AlipayConfig.ses_key));
				jsonMap.put("recivePhone", Encrypt.encryptSES(phone,
						AlipayConfig.ses_key));
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "发送失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}

		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;

	}

	// public static void main(String[] args) {
	// System.out.println(Encrypt.MD5("test123"));
	// }
	/**
	 * 重置密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String resetLoginPwd() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String password = appInfoMap.get("newPwd");

			if (StringUtils.isBlank(password)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String code = appInfoMap.get("code");
			if (StringUtils.isBlank(code)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "验证码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String randomCode = appInfoMap.get("randomCode") + "";
			if (StringUtils.isBlank(randomCode)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "随机验证码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			randomCode = Encrypt.decryptSES(randomCode, AlipayConfig.ses_key);
			// String phone = session().getAttribute("phone") +"";
			if (!randomCode.equals(code)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "验证码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String cellPhone = appInfoMap.get("cellPhone");
			if (!randomCode.equals(code)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "验证码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String recivePhone = appInfoMap.get("recivePhone");
			if (StringUtils.isBlank(recivePhone)) {
				jsonMap.put("error", "7");
				jsonMap.put("msg", "接收验证码手机号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			recivePhone = Encrypt.decryptSES(recivePhone, AlipayConfig.ses_key);
			if (StringUtils.isNotBlank(cellPhone)
					&& !cellPhone.equals(recivePhone)) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "手机号跟接收验证码手机号不一致");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = userService.queryUserIdByPhone(cellPhone);

			if (userId == -1) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "手机号与绑定的手机号不一致");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Long result = -1L;

			result = userService.updateLoginPwd(userId, password);

			if (result > 0) {
				// userService.queryUserById(userId);
				// 修改论坛的密码
				// bbsRegisterService.doUpdatePwdByAsynchronousMode(this.getUser()
				// .getUserName(), password, password, 2);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "修改成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "8");
				jsonMap.put("msg", "修改失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "9");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateLoginPwd() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			String password = appInfoMap.get("newPwd");

			if (StringUtils.isBlank(password)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "新密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			password = password.toLowerCase();
			String oldPwd = appInfoMap.get("oldPwd");

			if (StringUtils.isBlank(oldPwd)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "旧密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = Convert.strToLong(authMap.get("uid"), -1L);
			if (userId == -1L) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> userMap = userService.queryUserById(userId);
			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (!oldPwd.toLowerCase().equals(userMap.get("password"))) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "旧密码输入错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Long result = -1L;

			// password = Encrypt.MD5(password);
			result = userService.updateLoginPwd(userId, password);

			if (result > 0) {
				userService.queryUserById(userId);
				// 修改论坛的密码
				// bbsRegisterService.doUpdatePwdByAsynchronousMode(this.getUser()
				// .getUserName(), password, password, 2);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "修改成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "修改失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改交易密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateDealPwd() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			String password = appInfoMap.get("newPwd");

			if (StringUtils.isBlank(password)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "新密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String oldPwd = appInfoMap.get("oldPwd");

			if (StringUtils.isBlank(oldPwd)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "旧密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			long userId = Convert.strToLong(authMap.get("uid"), -1L);
			if (userId == -1L) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> userMap = userService.queryUserById(userId);
			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 设置交易密码
			Long result = -1L;
			if (Encrypt.MD5("-2").equalsIgnoreCase(oldPwd)) {

				result = userService.updateDealPwd(userId, password);
				if (result > 0) {
					jsonMap.put("error", "-1");
					jsonMap.put("msg", "设置成功");
					JSONUtils.printObject(jsonMap);
				} else {
					jsonMap.put("error", "5");
					jsonMap.put("msg", "设置失败");
					JSONUtils.printObject(jsonMap);
				}
				return null;
			}
			if (!oldPwd.equals(userMap.get("dealpwd"))) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "输入旧密码错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			result = userService.updateDealPwd(userId, password);
			if (result > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "修改成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "修改失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 忘记并重置交易密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String resetDealPwd() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			String password = appInfoMap.get("newPwd");

			if (StringUtils.isBlank(password)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "新密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String cellPhone = appInfoMap.get("cellPhone");
			userService.queryUserIdByPhone(cellPhone);
			if (StringUtils.isBlank(cellPhone)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "手机号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Long uid = userService.queryUserIdByPhone(cellPhone);

			long userId = Convert.strToLong(authMap.get("uid"), -1L);
			if (userId == -1L) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> userMap = userService.queryUserById(userId);
			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (uid != userId) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "输入手机号错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Long result = userService.updateDealPwd(userId, password);
			if (result > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "修改成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "修改失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 审核基础资料
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 */
	public String updateUserBaseDataCheck() throws SQLException, IOException,
			DataException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long personId = -1L;
			int auditStatus = 1;// 默认不通过审核
			long userId = Convert.strToLong(authMap.get("uid"), -1l);
			long flag = -1L;
			if (StringUtils.isNotBlank(appInfoMap.get("flag"))) {
				flag = Long.parseLong(appInfoMap.get("flag"));
			}
			if (flag == 3) {
				auditStatus = 3;// 通过审核
			} else if (flag == 2) {
				auditStatus = 2;// 审核不通过
			} else {
				auditStatus = 1;// 等待审核
			}
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			long adminId = admin.getId();
			if (admin != null) {
				personId = userService.updateUserBaseDataCheck(userId,
						auditStatus, adminId);
			}
			// 测试---跳过
			if (IConstants.ISDEMO.equals("1")) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "保存成功");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (personId > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "保存成功");
				JSONUtils.printObject(jsonMap);
				return null;
				// 成功
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "保存失败");
				// 失败
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加认证图片
	 * 
	 * @return
	 * @throws Exception
	 */
	/*public String addImage() throws Exception {
		Map<String, String> Apcmap = null;// 五大基本资料的计数存放map
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long materAuthTypeId = Convert.strToLong(appInfoMap.get(
					"materAuthTypeId").toString(), -1L);
			String imgPath = appInfoMap.get("userHeadImg");

			if (StringUtils.isBlank(imgPath)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "上传失败，请上传认证资料");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			long imageId = -1L;
			long userId = Convert.strToLong(authMap.get("uid"),-1l);
			User user = userService.jumpToWorkData(userId);
			// 认证状态
			if (null != user) {
				userId = user.getId();

				imageId = userService.addImage(materAuthTypeId, imgPath, userId);
				String id = userService.queryPitcturId(userId,materAuthTypeId).get("id");
				String msg = addpastPicturdate(Convert.strToLong(id,-1l),materAuthTypeId,user,imgPath);
				if(msg == null){
					jsonMap.put("error", "-1");// 不给跳转
					jsonMap.put("msg", "保存成功");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				jsonMap.put("error", "11");// 不给跳转
				jsonMap.put("msg", msg);
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (imageId < 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "保存失败");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				Integer allcount = 0;
				Apcmap = userService.queryPicturStatuCount(user.getId());
				if (Apcmap != null && Apcmap.size() > 0) {
					allcount = Convert.strToInt(Apcmap.get("ccc"), 0);
				}
				if (allcount != 0 && allcount >= 5) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "请先填写基本信息和工作信息");
				} else {
					jsonMap.put("error", "-1");// 不给跳转
					jsonMap.put("msg", "成功");
				}
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}*/
	
	//用户提交图片审核
	public String addpastPicturdate(Long tmid,Long materAuthTypeId,AccountUserDo user,String imgPath) throws Exception{
		Map<String, String> jsonMap = new HashMap<String, String>();
		   //------add by houli
		   String btype = request("btype");
		   //-----------
		   List<Map<String, Object>> userPicDate = null;
		   Map<String,String> typmap = null;
		   long userId = user.getId();
		   int len = 1;//上传图片个数
		   if(tmid>0){
			   request().setAttribute("tmid", tmid);
			   userPicDate = userService.queryPerTyhpePicture(tmid);
			   len = userPicDate.size()+1;
		   }
		
		Integer Listlen =  0;//数据库的图片个数
//		Long tmids = Convert.strToLong(appInfoMap.get("tmidStr"), -1L);
		Long result = -1L;
		if(Listlen==-1){
			return "没有图片";
		}
		if(len==-1){
			return "没有图片";
		}
		Integer allPicturecount = len + Listlen;//用户将要上传的图片和数据库图片的个数的总和
		if(materAuthTypeId==1){
			if(5<allPicturecount){
				return "身份证审核图片最多5张";
			}//身份证
		}
		if(materAuthTypeId==2){
			if(10<allPicturecount){
				return "工作认证审核图片最多10张";
			}//工作认证
		}
		if(materAuthTypeId==3){
			if(5<allPicturecount){
				return "居住认证审核图片最多5张";
			}//居住认证
		}
		
		if(materAuthTypeId==4){
			if(30<allPicturecount){
				return "收入认证审核图片最多5张";
			}//收入认证
		}
		if(materAuthTypeId==5){
			if(10<allPicturecount){
				return "信用报告审核图片最多10张";
			}//信用报告
		}
		if(materAuthTypeId==6){
			if(10<allPicturecount){
				return "房产证审核图片最多10张";
			}//房产
		}
		if(materAuthTypeId==7){
			if(10<allPicturecount){
				return "购车证审核图片最多10张";
			}//购车
		}
		if(materAuthTypeId==8){
			if(5<allPicturecount){
				return "结婚证审核图片最多5张";
			}//结婚
		}
		if(materAuthTypeId==9){
			if(5<allPicturecount){
				return "学历认证审核图片最多5张";
			}//学历
		}
		if(materAuthTypeId==10){
			if(5<allPicturecount){
				return "技术认证审核图片最多5张";
			}//技术
		}
		if(materAuthTypeId==11){
			if(5<allPicturecount){
				return "手机认证审核图片最多5张";
			}//手机
		}
		if(materAuthTypeId==12){
			if(5<allPicturecount){
				return "微博认证审核图片最多5张";
			}//微博
		}
/*		if(materAuthTypeId==13){
			//视频
			if(5<allPicturecount){JSONUtils.printStr("13");return null;}//视频
		}*/
		
		if(materAuthTypeId==13){
			if(10<allPicturecount){
				return "现场认证审核图片最多10张";
			}//现场认证
		}
		if(materAuthTypeId==14){
			if(10<allPicturecount){
				return "抵押认证审核图片最多10张";
			}//抵押认证
		}
		
		if(materAuthTypeId==15){
			if(10<allPicturecount){
				return "机构担保审核图片最多10张";
			}//机构担保	
		}
		
		if(materAuthTypeId==16){
			if(30<allPicturecount){
				return "其他资料审核图片最多30张";
			}//其他资料	
		}
		
		
		
		List<Long> lists = new ArrayList<Long>();//已经上传的图片设置他们的可见性
		if(Listlen!=-1&&user!=null){
			for(int i = 1 ;i<=Listlen;i++){
				if(Convert.strToInt(paramMap.get("id"+i),-1)!=-1){
					lists.add(Convert.strToLong(paramMap.get("id"+i),-1)); 
				}
			}
		}
		
		List<String> imglistsy = new ArrayList<String>();
		List<String> imgListsn = new ArrayList<String>();
		if(len!=-1&&user!=null){
			for(int i = 1 ;i<=len;i++){//将要上传图片图片先保存在一个数组里面
				imgListsn.add(imgPath);
			}
		}
		//Map<String, String> authMap = this.getAppAuthMap();
		//long userId = Convert.strToLong(authMap.get("uid"), -1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String uploadingTime = format.format(new Date());//当前时间上传图片时间
		if(user!=null&&tmid!=-1L&&materAuthTypeId!=-1L){
			 result =	userService.addUserImage(1, uploadingTime, lists,imglistsy,imgListsn, tmid,userId,materAuthTypeId,-1l
					 ,len);//遍历将image查到数据库中 1 表示向t_materialsauth 插入图片类型 表示等待审核
		     if(result>0){
		    	 //更新User的状态
		    	 try {
		    		 
		    		 Map<String, String> newstatusmap = null;
		    		 newstatusmap  =	 userService.querynewStatus(userId);//查询放到session中去
					 if(newstatusmap!=null&&newstatusmap.size()>0){
							user.setAuthStep(Convert.strToInt(newstatusmap.get("authStep"),
									-1));
							
							user.setEmail(Convert.strToStr(newstatusmap.get("email"), null));
							user.setPassword(Convert.strToStr(newstatusmap.get("password"),
									null));
							user.setId(Convert.strToLong(newstatusmap.get("id"), -1L));

							user.setVipStatus(Convert.strToInt(newstatusmap.get("vipStatus"),
									-1));
//							user.setKefuid(Convert.strToInt(newstatusmap.get("tukid"), -1));
							//session().setAttribute("user", user);//跟新session中的user
					 }
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		     }else{
				return "失败";
		     }
		     
		}
		return null;
	}
	

	/**
	 * 跳转到上传页面
	 * 
	 * @throws Exception
	 */
	/*public String jumpPassDatapage() throws Exception {

		List<Map<String, Object>> basepictur = null;
		List<Map<String, Object>> selectpictur = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1L);
			User user = userService.jumpToWorkData(userId);
			String from = appInfoMap.get("from");
			String btype = appInfoMap.get("btype");
			// -------------
			if (user != null) {
				if (from == null || from.equals("")) {
					// 获取用户认证进行的步骤
					if (user.getAuthStep() == 1) {
						// 个人信息认证步骤
						jsonMap.put("error", "1");
						jsonMap.put("msg", "请先填写个人信息");
						JSONUtils.printObject(jsonMap);
						return null;
					} else if (user.getAuthStep() == 2) {
						// 工作信息认证步骤
						jsonMap.put("error", "2");
						jsonMap.put("msg", "请先填写工作信息");
						JSONUtils.printObject(jsonMap);
						return null;
					} else if (user.getAuthStep() == 3) {
						// VIP申请认证步骤
						jsonMap.put("error", "3");
						jsonMap.put("msg", "请先成为VIP");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					// ---------add by houli
					else if (user.getAuthStep() == 5
							&& (btype != null && !btype.equals(""))) {
						jsonMap.put("error", "-1");
						jsonMap.put("msg", "成功");
						JSONUtils.printObject(jsonMap);
						return null;
					}
				} else {// 薪金贷跟生意贷操作步骤
					// 获取用户认证进行的步骤
					if (user.getAuthStep() == 1) {
						// 个人信息认证步骤
						jsonMap.put("error", "1");
						jsonMap.put("msg", "请先填写个人信息");
						JSONUtils.printObject(jsonMap);
						return null;
					}

					if (user.getVipStatus() == IConstants.UNVIP_STATUS) {
						jsonMap.put("error", "2");
						jsonMap.put("msg", "请先成为VIP");
						JSONUtils.printObject(jsonMap);
						return null;
					}

					// -------add by houli
					// return jumpToBorrow(btype);
					if (IConstants.BORROW_TYPE_NET_VALUE.equals(btype)) {
						jsonMap.put("error", "-1");
						jsonMap.put("msg", "成功");
						return null;
					} else if (IConstants.BORROW_TYPE_SECONDS.equals(btype)) {
						jsonMap.put("error", "-1");
						jsonMap.put("msg", "成功");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					// -----------------
				}

				basepictur = userService.queryBasePicture(userId);// 五大基本
				selectpictur = userService.querySelectPicture(userId);// 可选
				jsonMap.put("basepictur", basepictur);
				jsonMap.put("selectpictur", selectpictur);
				jsonMap.put("error", "-1");// 不给跳转
				jsonMap.put("msg", "成功");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}*/

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUserIntegralService(UserIntegralService userIntegralService) {
		this.userIntegralService = userIntegralService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public void setSMSInterfaceService(SMSInterfaceService interfaceService) {
		sMSInterfaceService = interfaceService;
	}

}
