package com.hehenian.common.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.UrlPathHelper;

public final class CommonUtil {
	
	public static final String REQUEST_GET = "get";
	
	public static final String REQUEST_POST = "post";
	
	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";
	
	/**
	 * GBK编码
	 */
	public static final String GBK = "GBK";
	
	/**
	 * cookie中的JSESSIONID名称
	 */
	//public static final String JSESSION_COOKIE = "hsid";
	public static final String JSESSION_COOKIE = "s";
	
	/**
	 * 手机号正则
	 */
	private static final Pattern PHONE_PATTERN = Pattern.compile("((13|15|18)\\d{9})|(14[57]\\d{8})|(17[0678]\\d{8})");
	
	/**
	 * 邮箱正则
	 */
	private static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	
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
	 * 是否为有效手机号码
	 * @param phone
	 * @return
	 */
	public static boolean isValidPhone(String phone){
		if (phone == null || phone.trim().length() != 11 ) {
			return false;
		}
		
		Matcher matcher =  PHONE_PATTERN.matcher(phone);
		if(matcher.matches()){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 是否为email地址
	 * @param email
	 * @return
	 * @author: zhanbmf
	 */
	public static boolean isEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		}
		
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		if(matcher.find()){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 获取QueryString的参数，并使用URLDecoder以UTF8格式转码。如果请求是以post方法提交的，
	 * 那么将通过HttpServletRequest#getParameter获取。
	 * 
	 * @param request
	 *            web请求
	 * @param name
	 *            参数名称
	 * @return
	 */
	public static String getQueryParam(HttpServletRequest request, String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		if (request.getMethod().equalsIgnoreCase(REQUEST_POST)) {
			return request.getParameter(name);
		}
		String s = request.getQueryString();
		if (StringUtils.isBlank(s)) {
			return null;
		}
		try {
			s = URLDecoder.decode(s, "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] values = parseQueryString(s).get(name);
		if (values != null && values.length > 0) {
			return values[values.length - 1];
		} else {
			return null;
		}
	}

	
	public static Map<String, Object> getQueryParams(HttpServletRequest request) {
		Map<String, String[]> map;
		if (request.getMethod().equalsIgnoreCase(REQUEST_POST)) {
			map = request.getParameterMap();
		} else {
			String s = request.getQueryString();
			if (StringUtils.isBlank(s)) {
				return new HashMap<String, Object>();
			}
			try {
				s = URLDecoder.decode(s, UTF8);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			map = parseQueryString(s);
		}

		Map<String, Object> params = new HashMap<String, Object>(map.size());
		int len;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			len = entry.getValue().length;
			if (len == 1) {
				params.put(entry.getKey(), entry.getValue()[0]);
			} else if (len > 1) {
				params.put(entry.getKey(), entry.getValue());
			}
		}
		return params;
	}

	/**
	 * 
	 * Parses a query string passed from the client to the server and builds a
	 * <code>HashTable</code> object with key-value pairs. The query string
	 * should be in the form of a string packaged by the GET or POST method,
	 * that is, it should have key-value pairs in the form <i>key=value</i>,
	 * with each pair separated from the next by a &amp; character.
	 * 
	 * <p>
	 * A key can appear more than once in the query string with different
	 * values. However, the key appears only once in the hashtable, with its
	 * value being an array of strings containing the multiple values sent by
	 * the query string.
	 * 
	 * <p>
	 * The keys and values in the hashtable are stored in their decoded form, so
	 * any + characters are converted to spaces, and characters sent in
	 * hexadecimal notation (like <i>%xx</i>) are converted to ASCII characters.
	 * 
	 * @param s
	 *            a string containing the query to be parsed
	 * 
	 * @return a <code>HashTable</code> object built from the parsed key-value
	 *         pairs
	 * 
	 * @exception IllegalArgumentException
	 *                if the query string is invalid
	 * 
	 */
	public static Map<String, String[]> parseQueryString(String s) {
		String valArray[] = null;
		if (s == null) {
			throw new IllegalArgumentException();
		}
		Map<String, String[]> ht = new HashMap<String, String[]>();
		StringTokenizer st = new StringTokenizer(s, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				continue;
			}
			String key = pair.substring(0, pos);
			String val = pair.substring(pos + 1, pair.length());
			if (ht.containsKey(key)) {
				String oldVals[] = (String[]) ht.get(key);
				valArray = new String[oldVals.length + 1];
				for (int i = 0; i < oldVals.length; i++) {
					valArray[i] = oldVals[i];
				}
				valArray[oldVals.length] = val;
			} else {
				valArray = new String[1];
				valArray[0] = val;
			}
			ht.put(key, valArray);
		}
		return ht;
	}

	
	public static Map<String, String> getRequestMap(HttpServletRequest request,
			String prefix) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> names = request.getParameterNames();
		String name;
		while (names.hasMoreElements()) {
			name = names.nextElement();
			if (name.startsWith(prefix)) {
				request.getParameterValues(name);
				map.put(name.substring(prefix.length()), StringUtils.join(
						request.getParameterValues(name), ','));
			}
		}
		return map;
	}
	
	/**
	 * 获得当的访问路径
	 * 
	 * HttpServletRequest.getRequestURL+"?"+HttpServletRequest.getQueryString
	 * 
	 * @param request
	 * @return
	 */
	public static String getLocation(HttpServletRequest request) {
		UrlPathHelper helper = new UrlPathHelper();
		StringBuffer buff = request.getRequestURL();
		String uri = request.getRequestURI();
		String origUri = helper.getOriginatingRequestUri(request);
		buff.replace(buff.length() - uri.length(), buff.length(), origUri);
		String queryString = helper.getOriginatingQueryString(request);
		if (queryString != null) {
			buff.append("?").append(queryString);
		}
		return buff.toString();
	}

	
	/**
	 * 获得请求的session id，但是HttpServletRequest#getRequestedSessionId()方法有一些问题。
	 * 当存在部署路径的时候，会获取到根路径下的jsessionid。
	 * 
	 * @see HttpServletRequest#getRequestedSessionId()
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestedSessionId(HttpServletRequest request) {
		// 手动从cookie获取
		String cookie = CookieUtils.getCookie(request, JSESSION_COOKIE);
		if (cookie != null) {
			return cookie;
		} else {
			return request.getSession().getId();
		}
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
	 * 获取表名
	 * @param tableName    表前缀名如UserInfo
	 * @param tableAmounts 表数量 100 or 1000
	 * @param id           id如123456789
	 * @return             返回UserInfo_89 or UserInfo_089
	 */
	public static String getTableName(String tableName, int tableAmounts, int id, boolean bool) {
		int bit = 0;
		if(bool){
			if(tableAmounts == 10){
				bit = String.valueOf(tableAmounts).length();
			}else{
				bit = String.valueOf(tableAmounts).length() -1;
			}
		}
		return getTableName(tableName, tableAmounts, id, bit);
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
	
	/**
	 * 描述输出
	 * @param <T>
	 * @param title 描述标题
	 * @param items 描述项
	 * @return
	 */
    public static <T> String getLoggerDes(String title, T ...items){
 	   StringBuilder des = new StringBuilder(title);
 	   des.append("|");
 	   for (Object object : items) {
			des.append(object).append("|");
		}
 	   return des.toString();
    }
    
	/**
	 * 手机号*处理
	 * @param phone 
	 * @return 如137******15
	 */
	public static String encodeM(String phone) {
		if(StringUtils.isBlank(phone) || phone.length() < 11) {
			return phone;
		}
		
		return phone.replace(StringUtils.substring(phone, 3, 9), "******");
	}
	
	public static void copyObject(Object descObj, Object srcObj) throws Exception {
		if(srcObj == null){
			descObj = null;
		}
		Field[] fields2 = srcObj.getClass().getDeclaredFields();
		for(int i = 0; i < fields2.length; i++){
			String attributeName = fields2[i].getName();
			Class<?> type = fields2[i].getType();
			try{
				Field field = descObj.getClass().getDeclaredField(attributeName);
				if(field != null){
					String attributeName1 = field.getName();
					Class<?> type1 = field.getType();
					if(attributeName.equals(attributeName1) && type.equals(type1)){
						fields2[i].setAccessible(true);
						Object obj = (Object)fields2[i].get(srcObj);
						String setMet = "set" + attributeName1.substring(0, 1).toUpperCase() + attributeName1.substring(1);
						Method fieldSetMet = descObj.getClass().getMethod(setMet, type1);
						if(fieldSetMet != null){
							fieldSetMet.invoke(descObj, obj);
						}
					}
				}
			}catch(Exception e){}
			
		}
	}
	/*public static void main(String[] args){
		System.out.println(encodeM("13712340315"));
	}*/
	
}