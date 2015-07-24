package com.hehenian.app.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;

/**
 * @Description 
 *  用来跳转到统一的登录认证中心
 *  系统需要配置登录URL
 *  并且将用户访问的url传递给登录中心， 登录成功后跳回用户想访问的url
 *  参数名称： fromUrl
 * @author huangzl QQ: 272950754
 * @date 2015年6月15日 下午2:47:40
 * @Project hehenian-lend-app
 * @Package com.hehenian.app.filter 
 * @File UserLoginFilter.java
*/
public class UserLoginFilter implements Filter {
    protected String loginView;
    protected String mobileLoginView;

    Logger logger = Logger.getLogger(this.getClass());

    public String getLoginView() {
        return loginView;
    }

    public void setLoginView(String loginView) {
        this.loginView = loginView;
    }

    public String getMobileLoginView() {
        return mobileLoginView;
    }

    public void setMobileLoginView(String mobileLoginView) {
        this.mobileLoginView = mobileLoginView;
    }

    public UserLoginFilter() {}

    public void destroy() {}

    /**
     * 
     * 过滤掉不需要登录就可以查看的url
     * 其他url 都先检测是否登录过， 没有重定向到登录中心
     * 
     * 
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        //String fromUrl =req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+uri;
        HttpSession session = req.getSession();
        setParameter(session, new HashMap(req.getParameterMap()));
        if (uri.endsWith("/index") ||uri.indexOf("/app/mhk/")!=-1||uri.indexOf("/app/elend/")!=-1||uri.indexOf("/app/group/")!=-1||uri.indexOf(".ico")!=-1 ||uri.indexOf("/app_res/")!=-1){//uri.endsWith("/index") ||uri.indexOf("/app/mhk/")!=-1||uri.indexOf("/app/elend/")!=-1||uri.indexOf("/app/group/")!=-1
            chain.doFilter(request, response);
        }else {
        	AccountUserDo user = (AccountUserDo)session.getAttribute("user");
            logger.info("uri:=======>"+uri);
            String loginCenterUrl = loginView;
            if (user == null || user.getId()==null) {
                if (uri.indexOf("/view/mobile/")>-1){
                   loginCenterUrl= mobileLoginView;
                }else if(uri.endsWith("/getAuth.do")){
                	loginCenterUrl=("http://dkdev.hehenian.cn/login/login/getAuth.do");
                }
                logger.info("loginView:=======>"+loginCenterUrl);
                StringBuffer urlTemp=req.getRequestURL();
                String urlTemp1=req.getQueryString();
                uri = urlTemp + ";s="+ session.getId();
                uri =  java.net.URLDecoder.decode(uri,   "utf-8"); 
                resp.sendRedirect(loginCenterUrl + ";s="+ session.getId()+"?fromUrl=" + uri);
            } else {
                chain.doFilter(request, response);
            }	
        }
    }

    private void setParameter(HttpSession session, Map map){
        if (map!=null && map.size()>0){
            Map temp=(Map)session.getAttribute("parameterMap");
            Map newMap=new HashMap();
            if(temp!=null) {
                newMap.putAll(temp);
            }
            if(map!=null) {
                newMap.putAll(map);
            }
            session.setAttribute("parameterMap", newMap);
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
       ServletContext sc=fConfig.getServletContext();
        sc.setAttribute("doLoginView", loginView);
        sc.setAttribute("mobileLoginView", mobileLoginView);
    }

}
