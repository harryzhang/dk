package com.hehenian.mobile.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.common.constants.HHNConstants;
import com.hehenian.common.utils.CookieUtils;
import com.hehenian.mobile.common.constants.WebConstants;
import com.hehenian.mobile.common.constants.WebThreadVariable;


public class CommonUtils {
	
	/**
	 * 得到第三方 OpenId
	 * @param request
	 * @return
	 */
	public static String getOpenId(HttpServletRequest request){
		return "openId";
	}

	/**
	 * 得到登录登录用户
	 * @param request
	 * @param response
	 * @return
	 */
	public static PersonDo getPersonDo(){
		return WebThreadVariable.getPersonDo();
	}
	
	/**
	 * 得到登录登录用户
	 * @param request
	 * @param response
	 * @return
	 */
	public static AccountUserDo getAccountUserDo(){
		return WebThreadVariable.getAccountUserDo();
	}
	
	/**
	 * 得到登录memberId
	 * @param request
	 * @param response
	 * @return
	 */
	public static Long getLoginId(){
		AccountUserDo aud = getAccountUserDo();
		if (aud != null) {
			return aud.getId();
		}
		return null;
	}
	
	/**
	 * 从当前线程局部变量中获取当前登录用户sessionId
	 * @return
	 */
	public static String getRoot(){
		return WebThreadVariable.getRoot();
	}
	
	/**
	 * 获取渠道名称
	 * @param request
	 * @return
	 * @author: zhanbmf
	 * @date 2015-4-15 下午11:00:16
	 */
	public static String getChannelName(HttpServletRequest request) {
		return WebConstants.channelMap.get(getChannel(request));
	}
	
	/**
	 * 获取渠道标识
	 * @param request
	 * @return
	 * @author: zhanbmf
	 * @date 2015-4-15 下午11:00:16
	 */
	public static int getChannel(HttpServletRequest request) {
		//优先从url中获取
		int channel = NumberUtils.toInt(request.getParameter(HHNConstants.CHANNEL), -1);
		if(WebConstants.channelMap.get(channel) != null) {
			return channel;
		}
		
		//cookie中获取
		channel = NumberUtils.toInt(CookieUtils.getCookie(request, HHNConstants.CHANNEL), -1);
		if(WebConstants.channelMap.get(channel) != null) {
			return channel;
		}
		
		//request中获取
		channel = NumberUtils.toInt(ObjectUtils.toString(request.getAttribute(HHNConstants.CHANNEL)), -1);
		if(WebConstants.channelMap.get(channel) != null) {
			return channel;
		}
		
		//session中获取
		channel = NumberUtils.toInt(ObjectUtils.toString(request.getSession().getAttribute(HHNConstants.CHANNEL)), -1);
		if(WebConstants.channelMap.get(channel) != null) {
			return channel;
		}
		
		return 0;
	}
}
