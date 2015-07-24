/**  
 * @Project: hehenian-mobile
 * @Package com.hehenian.mobile.common.web.interceptors
 * @Title: LoginInterceptor.java
 * @Description: 登录拦截
 *
 * @author: zhanbmf
 * @date 2015-3-28 下午2:44:22
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.common.web.interceptors;


import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.common.annotations.RequireLogin;
import com.hehenian.mobile.common.constants.WebConstants;
import com.hehenian.mobile.common.constants.WebThreadVariable;
import com.hehenian.mobile.common.utils.CommonUtils;


public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private IPersonService personService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		RequireLogin requireLogin = method.getAnnotation(RequireLogin.class);
		if (requireLogin == null) {
			Class<?> cls = handlerMethod.getBeanType();
			requireLogin = cls.getAnnotation(RequireLogin.class);
		}

		if (requireLogin != null && requireLogin.value()) {
			// 获得cookie中的memberId
			AccountUserDo aud = CommonUtils.getAccountUserDo();
			boolean isLogin = false;
			if (aud != null) {
				isLogin = aud.getId() != null && aud.getId() > 0;
				
				//如果需要注入登录信息
				if(requireLogin.injectPersonDo()){
					PersonDo pd = personService.getByUserId(aud.getId());
					WebThreadVariable.setPersonDo(pd);
				}
			}
			
			if(!isLogin) {
				response.sendRedirect("http://m.hehenian.com/login/index.do");
			}
			return isLogin;
		}
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// Sevlet容器有可能使用线程池，所以必须手动清空线程变量。
		WebThreadVariable.removeAccountUserDo();
		WebThreadVariable.removePersonDo();
		//WebThreadVariable.removeRoot();
		//request.setAttribute(WebConstants.CHANNEL_NAME, CommonUtils.getChannelName(request));
	}
}
