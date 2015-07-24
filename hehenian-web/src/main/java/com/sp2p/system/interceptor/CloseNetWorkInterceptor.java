package com.sp2p.system.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hehenian.web.common.contant.WebConstants;
import com.shove.security.Encrypt;
import com.shove.util.CookieUtils;
import com.shove.util.UtilDate;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.ServletUtils;
import com.sp2p.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.shove.Convert;
import com.sp2p.constants.IConstants;

/**
 * 网站是否已经关闭判断拦截
 * 
 * @author 钟垂青
 * @Create Jun 3, 2011
 * 
 */
public class CloseNetWorkInterceptor implements Interceptor {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(CloseNetWorkInterceptor.class);
	private HttpSession session;
	private HttpServletResponse response;
	private HttpServletRequest request;
    private UserService userService;
	public void destroy() {
	}

	public void init() {
	}

	@SuppressWarnings("unchecked")
	public String isCloseNetWork(ActionInvocation invocation) throws Exception {

		ServletContext application = request.getSession().getServletContext();
		Map<String, String> map = (Map<String, String>) application.getAttribute(IConstants.Session_CLOSENETWORK);
		Integer status = Convert.strToInt(map.get("status"), -1);
		if (status == 2) {
			session.setAttribute("netWork", map.get("content"));
			return IConstants.Session_CLOSENETWORK;
		}
		return invocation.invoke();
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		session = request.getSession();
		String retUrl = request.getHeader("Referer");
		log.info("-------------------" + retUrl);
		log.info(request.getRequestURL());
		// 判断请求是否带参数
		String params = request.getQueryString();
		if (null != params) {
			isScript(params);
		}
		if (isAjaxRequest()) {
			return ajaxIntercept(invocation);
		}
		
		String hostName = "http://"+request.getServerName();  
        if(hostName.startsWith("http://hehenian.com")) {  
            String queryString = (request.getQueryString() == null ? "" : "?"+request.getQueryString());  
            response.setStatus(301);  
            String requestUrl = request.getRequestURL().toString();
            requestUrl = requestUrl.replace("http://hehenian.com", "http://www.hehenian.com");  
             response.setHeader( "Location", requestUrl + queryString);  
             response.setHeader( "Connection", "close" );  
        }
//通过cookie登录
        /*String token = (String)CookieUtils.getKey(request,"token");
        if (request.getSession().getAttribute("user")==null&&StringUtils.isNotBlank(token)){
            DesSecurityUtil des = new DesSecurityUtil();
            String key1 = des.decrypt(token);
            if (StringUtils.isNotBlank(key1)){
                String[] ss = key1.split("-");
                if (ss.length==3){
                    Map<String, String> map = userService.queryUserById(Integer.parseInt(ss[0]));
                    DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
                    String lastTime = dateformat.format(new Date());
                    User user = userService.userLogin2(map.get("username"), map.get("password"), ServletUtils.getRemortIp(), lastTime);
                    session.setAttribute("user", user);
                    session.setAttribute("platform",ss[1]);
                    session.setAttribute("sourcefrom",ss[2]);
                }
            }
        }*/
        //设置用户来源
        String sourcefrom = request.getParameter("_sourcefrom_");
        if (StringUtils.isNotBlank(sourcefrom)){
            session.setAttribute("sourcefrom",sourcefrom);
        }
        if (WebConstants.COLOUR_LIFE_Flag_WYF.equals(session.getAttribute("colourlifeFlag"))){
            //是彩富人生的用户
            String uri = request.getRequestURI();
            for (String eFilterUrl : eFilterUrls) {
                if (StringUtils.contains(uri,eFilterUrl)){
                    //在受限制的url中
                    return "e-webapp-msg";
                }
            }

        }
        return isCloseNetWork(invocation);

	}
    private static String[] eFilterUrls ;
    static {
        String xx = "webapp-money.do,investBorrow.do,webapp-tz-intro.do,webapp-tz-intro.do";
        eFilterUrls = xx.split(",");
    }
	public void isScript(String params) throws UnsupportedEncodingException {
		//获取请求中的参数
		params = URLDecoder.decode(params, "utf-8");
		boolean b = (params.contains("script") || params.contains("<script>") || params.contains("</script>") || params.contains(
                "alert"));
		if (b) {
			try {
				// request.getRequestDispatcher("index.jsp").forward(request,response);
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + path + "/";
//				//重定向到错误页面
//				request.getRequestDispatcher("/error/404.jsp").forward(request,
//						response);
//				 response.sendRedirect(basePath);
				request.getRequestDispatcher("/error/404.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ajax请求拦截 没登录返回 NoLogin 登录流程继续
	@SuppressWarnings("unchecked")
	private String ajaxIntercept(ActionInvocation invocation) throws Exception {
		log.info("ajax拦截");
		ServletContext application = request.getSession().getServletContext();
		Map<String, String> map = (Map<String, String>) application.getAttribute(IConstants.Session_CLOSENETWORK);
		Integer status = Convert.strToInt(map.get("status"), -1);
		if (status == 2) {
			session.setAttribute("netWork", map.get("content"));
			response.setContentType("text/html");
			response.getWriter().print(IConstants.Session_CLOSENETWORK);
			log.info("No Login");
			return null;
		}
		return invocation.invoke();

	}

	private boolean isAjaxRequest() {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header)) {
			return true;
		}
		return false;
	}

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
