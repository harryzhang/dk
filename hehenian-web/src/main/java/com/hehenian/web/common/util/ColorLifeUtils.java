package com.hehenian.web.common.util;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class ColorLifeUtils {
	private static final Logger logger = Logger.getLogger(ColorLifeUtils.class);
	private static final String SECRET = "DJKC#$%CD%des$";

	/**
	 * 校验彩生活对请求参数的MD5摘要
	 * 
	 * @return
	 */
	public static boolean checkSign() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			// 对中文字符解码
			String cname = request.getParameter("cname");
			if (StringUtils.isNotBlank(cname)) {
				cname = URLDecoder.decode(cname, "utf-8");
			}
			String caddress = request.getParameter("caddress");
			if (StringUtils.isNotBlank(caddress)) {
				caddress = URLDecoder.decode(caddress, "utf-8");
			}
			// 拼接MD5摘要串
			StringBuilder paramString = new StringBuilder();
			paramString.append(SECRET);
			paramString.append("bno" + request.getParameter("bno"));
			paramString.append("bsecret" + request.getParameter("bsecret"));
			paramString.append("userid" + request.getParameter("userid"));
			paramString.append("username" + request.getParameter("username"));
			paramString.append("mobile" + request.getParameter("mobile"));
			paramString.append("password" + request.getParameter("password"));
			paramString.append("tjrid" + request.getParameter("tjrid"));
			paramString.append("cid" + request.getParameter("cid"));
			paramString.append("cname" + cname);
			paramString.append("caddress" + caddress);
			paramString.append(SECRET);
			// 校验摘要
			if (DigestUtils.md5Hex(paramString.toString()).equalsIgnoreCase(request.getParameter("sign"))) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
}
