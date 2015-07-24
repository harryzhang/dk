package com.hehenian.manager.actions.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public final class CommonUtil {
	
	public static final String REQUEST_GET = "get";
	
	public static final String REQUEST_POST = "post";
	
	/**
	 * 是否是Get请求
	 * @param request
	 * @return
	 */
	public static boolean isGet(HttpServletRequest request) {
		return REQUEST_GET.equalsIgnoreCase(request.getMethod());
	}

	/**
	 * 是否是POST请求
	 * @param request
	 * @return
	 */
	public static boolean isPost(HttpServletRequest request) {
		return REQUEST_POST.equalsIgnoreCase(request.getMethod());
	}
	
	/**
	 * 获取请求IP
	 * @param request
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request){
		if (request == null){
			return "";
		}
			
		String ipaddr = request.getHeader("Cdn-Src-Ip");
		if(StringUtils.isNotBlank(ipaddr)) {
			return ipaddr;
		}
		
		ipaddr = request.getHeader("X-Real-IP");
		if(StringUtils.isNotBlank(ipaddr)) {
			return ipaddr;
		}
		
		ipaddr = request.getHeader("X-Forwarded-For");
		if(StringUtils.isNotBlank(ipaddr)) {
			return ipaddr;
		}
		
		ipaddr = request.getHeader("X-Remote-Addr");
		if(StringUtils.isNotBlank(ipaddr)) {
			return ipaddr;
		}
		
		ipaddr = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (StringUtils.isBlank(ipaddr)) {
			ipaddr = request.getRemoteAddr();
		}
		return ipaddr;
	}
	
	/**
	 * 获取表名
	 * @param tableName    表前缀名如UserInfo
	 * @param tableAmounts 表数量 100 or 1000
	 * @param id           id如123456789
	 * @return             返回UserInfo_89 or UserInfo_089
	 */
	public static String getTableName(String tableName, int tableAmounts, int id) {
		return getTableName(tableName, tableAmounts, id, String.valueOf(tableAmounts).length() -1);
	}
	
	/**
	 * 获取表名
	 * @param tableName     表前缀名如UserInfo
	 * @param tableAmounts  表数量 5 or 10 or 100 ...
	 * @param id id         如123456789
	 * @param bit           格式化位数 1 or 2...
	 * @return              返回UserInfo_4 UserInfo_9 UserInfo_89  UserInfo_04 UserInfo_09 UserInfo_89
	 */
	public static String getTableName(String tableName, int tableAmounts, int id, int bit) {
		return tableName + "_" + String.format("%0" + bit + "d", id % tableAmounts, bit);
	}
	
	/**
	 * 
	 * @param id
	 * @param mod
	 * @return
	 */
	public static String format(int id, int mod) {
		return String.format("%0" + (String.valueOf(mod).length() -1) + "d", id % mod);
	}

	public static String format(int id, int mod, int bit) {
		return String.format("%0" + bit + "d", id % mod);
	}
	
}