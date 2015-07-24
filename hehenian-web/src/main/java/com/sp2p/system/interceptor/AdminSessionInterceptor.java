package com.sp2p.system.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;

/**
 * 后台登录验证拦截器
 * 
 * @author 杨程
 * @Create Jun 3, 2011
 * 
 */
public class AdminSessionInterceptor implements Interceptor {
	public static Log log = LogFactory.getLog(AdminSessionInterceptor.class);
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	private Integer roleId;
	

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void destroy() {
	}

	public void init() {
	}

	/**
	 * 拦截
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
//		log.info("后台登录拦截");
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		log.info(request.getRequestURL());
		if(!IConstants.ADMIN_SESSION_SWITCH){
			 return invocation.invoke();
			 }
			if (isAjaxRequest()) {
				return ajaxIntercept(invocation);
			}
			return _intercept(invocation);
	}

	/**
	 * 普通请求拦截 没登录返回 noLogin 登录流程继续
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	private String _intercept(ActionInvocation invocation) throws Exception {
//		log.info("普通请求拦截");
			Admin admin = (Admin) session.getAttribute(IConstants.SESSION_ADMIN);
			if (null == admin) {
				log.info("No Login");
				return IConstants.ADMIN_AJAX_LOGIN;
			}
			log.info("admin id：" + admin.getId() + " name：" + admin.getUserName());
			return invocation.invoke();
	}

	/**
	 * ajax请求拦截 没登录返回 NoLogin 登录流程继续
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	private String ajaxIntercept(ActionInvocation invocation) throws Exception {
//		log.info("ajax拦截");
			Admin admin = (Admin) session.getAttribute(IConstants.SESSION_ADMIN);
			if (null == admin) {
				response.setContentType("text/html");
				response.getWriter().print(IConstants.ADMIN_AJAX_LOGIN);
				log.info("No Login");
				return null;
			}
			log.info("admin id：" + admin.getId() + " name：" + admin.getUserName());
			return invocation.invoke();
	}

	/**
	 * 根据请求头数据判断是否是Ajax请求
	 * @return
	 */
	private boolean isAjaxRequest() {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header)) {
			return true;
		}
		return false;
	}
}
