package com.hehenian.common.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {
	public final static String UTF8 = "UTF-8"; 
	public final static String GBK = "GBK"; 
	
	/**
	 * 输出Json
	 * @param response
	 * @param charset   默认为UTF-8编码
	 * @param text
	 */
	public static void renderJson(HttpServletResponse response, String charset, String text) {
		render(response, "application/json", charset, text);
	}
	
	/**
	 * 输出Json
	 * @param response
	 * @param charset   默认为UTF-8编码
	 * @param text
	 * @param callback	回调
	 */
	public static void renderJson(HttpServletResponse response, String charset, String text,String callback) {
		if(callback==null || "".equals(callback.trim())){
			renderJson(response, charset, text);
		}else{
			renderJson(response, charset, callback + "(" + text + ");");
		}
		
	}
	
	/**
	 * 输出text
	 * @param response
	 * @param charset 默认为UTF-8编码
	 * @param text
	 */
	public static void renderText(HttpServletResponse response, String charset, String text){
		render(response, "text/plain", charset, text);
	}
	
	/**
	 * 输出text
	 * @param response
	 * @param charset
	 * @param text
	 */
	public static void renderText(HttpServletResponse response, String charset, String text,String callback) {
		if(callback==null || "".equals(callback.trim())){
			renderText(response, charset, text);
		}else{
			renderText(response, charset, callback + "(\"" + text + "\");");
		}
	}
	
	/**
	 * 输出text
	 * @param response
	 * @param charset
	 * @param text
	 */
	public static void renderXml(HttpServletResponse response, String charset, String text){
		render(response, "text/xml", charset, text);
	}
	
	/**
	 * 输出html
	 * @param response
	 * @param charset
	 * @param text
	 */
	public static void renderHtml(HttpServletResponse response, String charset, String text){
		render(response, "text/html", charset, text);
	}
	
	
	/**
	 * 输出javascript
	 * @param response
	 * @param charset
	 * @param text
	 */
	public static void renderJavaScript(HttpServletResponse response, String charset, String text){
		render(response, "text/javascript", charset, text);
	}
	
	/**
	 * 输出x-javascript
	 * @param response
	 * @param charset
	 * @param callback	回调
	 * @param text
	 */
	public static void renderXJavaScript(HttpServletResponse response, String charset, String text){
		render(response, "application/x-javascriptt", charset, text);
	}
	
	/**
	 * 直接输出
	 * @param response
	 * @param contentType
	 * @param charset
	 * @param text
	 */
	public static void render(HttpServletResponse response,String contentType, String charset, String text){
		response.setContentType(contentType + ";charset=" + (charset == null ? UTF8 : GBK));
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
		}
	}
}
