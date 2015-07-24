package com.hehenian.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;


public class CookieUtils {

	public static final String VALUE_ENCODE = "utf-8";
	public static final String DEFAULT_PATH = "/";
	public static final String DOMAIN = "";
	/**
	 * 最大有效时间
	 */
	public static final int DEFAULT_MAX_AGE = 60 * 60 * 24 * 365;
	/**
	 * 默认有效时间
	 */
	public static final int DEFAULT_AGE = -1;

	/**
	 * 
	 * @param response
	 * @param key
	 * @param value
	 * @param domain
	 * @param path
	 * @param maxAge
	 *            an integer specifying the maximum age of the cookie in
	 *            seconds; if negative, means the cookie is not stored; if zero,
	 *            deletes the cookie
	 */
	public static final void addCookie(final HttpServletResponse response, final String key,
			final String value, final String domain, final String path, final int maxAge) {
		String encodedValue;
		try {
			encodedValue = value == null ? "" : URLEncoder.encode(value, VALUE_ENCODE);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		Cookie c = new Cookie(key, encodedValue);
		if (domain != null) {
			c.setDomain(domain);
		}else
		{
			c.setDomain(DOMAIN);
		}
		c.setPath(path);
		if(maxAge!=0){
			c.setMaxAge(maxAge);
		}
		response.addCookie(c);
	}

	public static final void addCookie(final HttpServletResponse response, final String key,
			final String value, final String path, final int maxAge) {
		addCookie(response, key, value, null, path, maxAge);
	}

	public static final void addCookie(final HttpServletResponse response, final String key,
			final String value, final int maxAge) {
		addCookie(response, key, value, null, DEFAULT_PATH, maxAge);
	}

	public static final void addCookie(final HttpServletResponse response, final String key,
			final String value) {
		addCookie(response, key, value, null, DEFAULT_PATH, DEFAULT_AGE);
	}

	public static final void removeCookie(final HttpServletResponse response, final String key,
			final String domain, final String path) {
		addCookie(response, key, "", domain, path, 0);
	}

	public static final void removeCookie(final HttpServletResponse response, final String key,
			final String path) {
		addCookie(response, key, "", null, path, 0);
	}

	public static final void removeCookie(final HttpServletResponse response, final String key) {
		addCookie(response, key, "", null, DEFAULT_PATH, 0);
	}

	public static final String getCookie(HttpServletRequest request, String key) {	
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie c : cookies) {
			if (StringUtils.equals(key, c.getName())) {
				try {
					return URLDecoder.decode(c.getValue(), VALUE_ENCODE);
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return null;
	}
}
