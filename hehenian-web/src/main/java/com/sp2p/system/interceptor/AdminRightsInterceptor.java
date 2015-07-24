/**
 * 
 */
package com.sp2p.system.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.RoleRightsService;

/**
 * 后台权限拦截器
 * @author rex
 *
 */
public class AdminRightsInterceptor extends AbstractInterceptor   {

	private static final long serialVersionUID = 8551478886762182838L;
	
	private static Log log = LogFactory.getLog(AdminRightsInterceptor.class);
	
	private ServletContext context;
	private RoleRightsService roleRightsService;
	private boolean isInit = false; //防止重复初始化参数
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//初始化参数，防止重复获取
		if(!isInit){
			context = ServletActionContext.getServletContext();
			WebApplicationContext ctx = WebApplicationContextUtils
		      .getWebApplicationContext(context);
			roleRightsService = ctx.getBean(RoleRightsService.class);
			isInit = true; //只初始化一次
		}

		//取得用户ID
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Admin admin = (Admin)session.getAttribute(IConstants.SESSION_ADMIN);
		Long roleId = null;
		if(admin != null){
			roleId = admin.getRoleId(); 
		}
		
		//获取Action路径
		String action = request.getRequestURI();
		action = action.substring(action.lastIndexOf("/")+1);
		System.out.println("action==========="+action);
		
		//查询是否有权限
		boolean isHaveRights = roleRightsService.queryAdminRoleIsHaveRights(roleId, action);
		
		if(isHaveRights){//有权限
			return invocation.invoke();
		}else{
			return IConstants.ADMIN_NO_PERMISSION;
		}
		
		
	}
}
