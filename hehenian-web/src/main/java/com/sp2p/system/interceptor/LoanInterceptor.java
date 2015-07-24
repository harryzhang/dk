/**
 * 
 */
package com.sp2p.system.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hehenian.web.common.contant.WebConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 彩生活进入
 * @author xiexiangmf
 *
 */
public class LoanInterceptor extends AbstractInterceptor   {

	private static final long serialVersionUID = 8551478886762182838L;
	
	private static Log log = LogFactory.getLog(LoanInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		//取得用户ID
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
        try {
            Map<String, String> map = (Map<String, String>) session.getAttribute(WebConstants.COLOURLIFE_ADMIN_USER);
            if(map == null){
            	request.setAttribute("message", "请登录系统!");
	            return "noLogin";
            }else {
            	String userId   = map.get("userId");
	            String userName = map.get("userName");
	            log.info("-------------------进入的用户---"+userName+"------------------------");
	            if(userId == "" && userName == "") {
	            	 request.setAttribute("message", "请登录系统!");
	            	return "noLogin";
	            }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "请登录系统!");
            log.equals("程序异常!");
            return "noLogin";
        }
		//查询是否有权限
		return invocation.invoke();
	}
}
