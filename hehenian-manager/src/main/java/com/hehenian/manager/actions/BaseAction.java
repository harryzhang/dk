package com.hehenian.manager.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hehenian.manager.modules.sys.model.UserInfos;

public class BaseAction {
	
	/*liminglong add date:2015-05-08 */
	public static final String EXECUTE_STATUS = "executeStatus";
	public static final String VALUES = "values";
	public static final String EXECUTE_SUCCESS = "1";
	public static final String EXECUTE_FAILURE = "0";
	/*liminglong add date:2015-05-08 */
	/**
	 * jsonMap
	 */
	protected Map<String,Object> jsonMap = new HashMap<String, Object>();
	
	public String getBaseBath() {
		String path = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		path = path.substring(0, path.indexOf("/WEB-INF")) + "/upload/images";
		return path;
	}

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	public HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getSession();
	}

	public int getInt(String name) {
		return getInt(name, -1);
	}

	public int getInt(String name, int defaultValue) {
		return NumberUtils.toInt(getRequest().getParameter(name), defaultValue);
	}

	public String getString(String name) {
		return getString(name, null);
	}

	public String getString(String name, String defaultValue) {
		return ObjectUtils.toString(getRequest().getParameter(name), defaultValue);
	}

	public final String getIpAddr() {
		HttpServletRequest request = getRequest();
		if (request == null) {
			return "";
		}

		String ipaddr = request.getHeader("Cdn-Src-Ip");
		if (StringUtils.isNotBlank(ipaddr)) {
			return ipaddr;
		}

		ipaddr = request.getHeader("X-Real-IP");
		if (StringUtils.isNotBlank(ipaddr)) {
			return ipaddr;
		}

		ipaddr = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (StringUtils.isBlank(ipaddr)) {
			ipaddr = request.getRemoteAddr();
		}
		return ipaddr;
	}

	public void outPrint(HttpServletResponse response, Object result) {
		try {
			response.setCharacterEncoding("utf-8");

			PrintWriter out = response.getWriter();
			out.print(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * json转换config
	 * 
	 * @return
	 */
	protected JsonConfig getDefaultJsonConfig() {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			public Object processObjectValue(String key, Object value,
					JsonConfig cfg) {
				if(value!=null && value instanceof Timestamp){
					return timeFormat.format(value);
				}else if (value != null && value instanceof Date) {
					return sdf.format(value);
				}
				return null;
			}

			public Object processArrayValue(Object value, JsonConfig cfg) {
				if(value!=null && value instanceof Timestamp){
					return timeFormat.format(value);
				}else if (value != null && value instanceof Date) {
					return sdf.format(value);
				}
				return null;
			}
		});
		return config;
	}

	public int getUserId() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth.getPrincipal() instanceof UserDetails) {
			UserInfos user = (UserInfos) auth.getPrincipal();
			return user.getUserId();
		}
		return 0;
	}
	
	public UserInfos getUserInfos(){
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth.getPrincipal() instanceof UserDetails) {
			UserInfos user = (UserInfos) auth.getPrincipal();
			return user;
		}
		return null;
	}
}
