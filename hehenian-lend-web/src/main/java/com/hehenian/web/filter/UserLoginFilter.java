package com.hehenian.web.filter;

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

/**
 * 
 *  用来跳转到统一的登录认证中心
 *  
 *  系统需要配置登录URL
 *  并且将用户访问的url传递给登录中心， 登录成功后跳回用户想访问的url
 *  参数名称： fromUrl
 *  
 * 
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
        if (uri.equals("/")  ||uri.endsWith("/house.do")  ||uri.endsWith("/calCreditAmount.do")  ||uri.endsWith("/calRepayDetail.do")  ||uri.endsWith("/addLoanDetail.do")  ||uri.endsWith("/getByIdNo.do") ||uri.indexOf(".ico")!=-1 ||uri.indexOf("/css/")!=-1||uri.indexOf("/web_res/")!=-1||uri.indexOf("/js/")!=-1){
            chain.doFilter(request, response);
        }else {
            Object obj = session.getAttribute("user");
            ObjectMapper userJsonMap = new ObjectMapper();
            Map userMap = userJsonMap.convertValue(obj,Map.class);

            logger.info("uri:=======>"+uri);
            String loginCenterUrl = loginView;
            if (obj == null || StringUtils.isEmpty(userMap.get("id"))) {
                if (uri.indexOf("/view/mobile/")>-1){
                   // logger.info("mobileLoginView:=======>"+mobileLoginView);
                   loginCenterUrl= mobileLoginView;
                }
                logger.info("loginView:=======>"+loginCenterUrl);
                
                //add sesionId parameter
                uri = uri + ";s="+ session.getId();
                uri =  java.net.URLDecoder.decode("http://localhost"+uri,   "utf-8"); 
                //end  add sessionid parameterx`
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
