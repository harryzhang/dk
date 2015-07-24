/**  
 * @Project: hehenian-mobile
 * @Package com.hehenian.mobile.common.web.interceptors
 * @Title: ContextInterceptor.java
 * @Description: Spring上下文
 *
 * @author: zhanbmf
 * @date 2015-3-28 下午2:44:38
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.common.web.interceptors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.common.constants.HHNConstants;
import com.hehenian.common.session.SessionProvider;
import com.hehenian.common.session.cache.SessionCache;
import com.hehenian.mobile.common.constants.WebConstants;
import com.hehenian.mobile.common.constants.WebThreadVariable;
import com.hehenian.mobile.common.utils.CommonUtils;

public class ContextInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private SessionCache sessionCache;
	
	@Resource
	private SessionProvider session;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String root = session.getSessionId(request, response);
		AccountUserDo aud = (AccountUserDo) sessionCache.getAttribute(root, HHNConstants.SESSION_INFO);
		
		if (aud != null) {
			sessionCache.setAttribute(root, HHNConstants.SESSION_INFO, aud, HHNConstants.SESSION_CACHE_TIME);
			if(aud.getId() > 0) {
				request.setAttribute("loginId", aud.getId());
			}
		}
		
		if(StringUtils.isNotBlank(root)){
			sessionCache.setAttribute(root, HHNConstants.SESSION_ROOT, root, HHNConstants.SESSION_CACHE_TIME);
		}
		
		WebThreadVariable.setAccountUserDo(aud);
		//将当前会话的sessionId放进当前线程的局部变量
		WebThreadVariable.setRoot(root);
		request.setAttribute(WebConstants.CHANNEL_NAME, CommonUtils.getChannelName(request));
		return true;
	}
}
