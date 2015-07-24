package com.shove.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtils {

	/**
	 * 从Cookie里获取指定的值
	 * @param request 
	 * @param compName 键
	 * @return
	 */
	public static Object getKey(HttpServletRequest request, String compName){
		Cookie[] ck = request.getCookies();//返回一个数组，该数组包含这个请求中当前的所有cookie
		for (Cookie cookie : ck) {
			String key = cookie.getName();
			if(compName.equals(key)){//对比
				return cookie.getValue();
			}
		}
		return null;
	}
}
