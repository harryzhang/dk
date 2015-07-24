package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.shove.Convert;
import com.shove.security.Encrypt;
import com.shove.util.SqlInfusion;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.ApproveService;
import com.sp2p.service.SendMailService;

public class ApproveAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(ApproveAction.class);
	private ApproveService approveService;
	protected SendMailService mailSendService;

	/**
	 * 跳转到输入改变交易密码page
	 * 
	 * @return
	 */
	public String forgetTradepassword() {
		return SUCCESS;
	}

	/**
	 * 更新交易密码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateTradepasswordM() throws Exception {
		JSONObject obj = new JSONObject();
		Map<String, String> map = null;
		String username = null;
		Long userId = null;
		String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email"));
		if (StringUtils.isBlank(email)) {
			obj.put("mailAddress", "0");
			JSONUtils.printObject(obj);
			return null;
		} else {
			// ===截取emal后面地址
			int dd = email.indexOf("@");
			String mailAddress = null;
			if (dd >= 0) {
				mailAddress = "mail." + email.substring(dd + 1);
			}
			// ====
			map = approveService.querytrancePassword(email);
			if (map != null && map.size() > 0) {
				username = map.get("username");
				userId = Convert.strToLong(map.get("id"), -1L);
				DesSecurityUtil des = new DesSecurityUtil();
				String key1 = des.encrypt(userId.toString());
				String key2 = des.encrypt(new Date().getTime() + "");
				String url = getPath(); // request().getRequestURI();
				url = url.endsWith("/") ? url : url + "/";
				String VerificationUrl = url + "changeTrancePassword.do?key=" + key1 + "-" + key2;

				mailSendService.sendTrancepasswordLogin(VerificationUrl, username, email);
				obj.put("mailAddress", mailAddress);
				JSONUtils.printObject(obj);
				return null;

			} else {
				obj.put("mailAddress", "1");
				JSONUtils.printObject(obj);
				return null;
			}
		}
	}

	/**
	 * 验证邮箱有效和跳转到修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String udpateTrancePassword() throws Exception {
		String key = SqlInfusion.FilteSqlInfusion(request("key").trim());
		String msg = "邮箱验证失败";
		String[] keys = key.split("-");
		if (2 == keys.length) {
			DesSecurityUtil des = new DesSecurityUtil();
			// Long userId = Convert
			// .strToLong(des.decrypt(keys[0].toString()), -1);
			String userId = Encrypt.MD5(key + IConstants.BBS_SES_KEY).substring(0, 10) + key;
			String dateTime = des.decrypt(keys[1].toString());
			long curTime = new Date().getTime();
			// 当用户点击注册时间小于10分钟
			if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {
				// 修改用户状态
				// Long result = userService.frontVerificationEmial(userId);
				/*
				 * if (result > 0) { msg =
				 * "恭喜您帐号激活成功！请点击<a href='login.do'>登录</a>";
				 * ServletActionContext.getRequest().setAttribute("msg", msg); }
				 * else { msg = "注册失败"; // 这里还要写一个用户删除账号和密码
				 * ServletActionContext.getRequest().setAttribute("msg", msg); }
				 */
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

	// 修改交易密码
	public String updateTrancePasswordfor() throws Exception {
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("newPassword"));
		String confirmpassword = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmpassword"));
		String key = SqlInfusion.FilteSqlInfusion(paramMap.get("userId"));
		String msg = "邮箱验证失败";
		Long userId = -1l;
		String mdKey = key.substring(0, 10);
		String mdValue = key.substring(10, key.length());
		String mdCompare = Encrypt.MD5(mdValue + IConstants.BBS_SES_KEY).substring(0, 10);
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
		// 判断长度必须是6到20个字符
		if (password.length() < 6 || password.length() > 20) {
			JSONUtils.printStr("6");
			return null;
		}

		userId = Convert.strToLong(paramMap.get("userId"), -1L);
		if (userId == null || userId == -1L) {
			JSONUtils.printStr("4");
			return null;
		}
		Long result = -1L;
		if (password != null && password.trim() != "" && userId != null && userId != -1L) {
			result = approveService.updateUserTrancePassword(userId, password);
		}
		if (result > 0) {
			JSONUtils.printStr("1");
			return null;
		} else {
			JSONUtils.printStr("0");
			return null;
		}

	}

	public void setApproveService(ApproveService approveService) {
		this.approveService = approveService;
	}

	public void setSendMailService(SendMailService mailSendService) {
		this.mailSendService = mailSendService;
	}

}
