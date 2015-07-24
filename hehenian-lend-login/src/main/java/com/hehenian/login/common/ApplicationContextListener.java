package com.hehenian.login.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hehenian.login.common.PropertyUtils;

/**
 * @Description 描述方法作用
 * @author huangzl QQ: 272950754
 * @date 2015年5月27日 下午5:33:59
 * @Project hehenian-lend-login
 * @Package com.hehenian.login.common 
 * @File ApplicationContextListener.java
*/
public class ApplicationContextListener implements ServletContextListener{

	
	public ApplicationContextListener() {
		super();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		event.getServletContext().setAttribute("fileServerUrl", PropertyUtils.getProperty("file.server.url"));
		event.getServletContext().setAttribute("jsversion", PropertyUtils.getProperty("js.version"));
		event.getServletContext().setAttribute("cssversion", PropertyUtils.getProperty("css.version"));
		event.getServletContext().setAttribute("icoversion", PropertyUtils.getProperty("ico.version"));
		event.getServletContext().setAttribute("hhnServerUrl", PropertyUtils.getProperty("hhn.server.url"));
		event.getServletContext().setAttribute("loanServerUrl", PropertyUtils.getProperty("loan.server.url"));
		event.getServletContext().setAttribute("loginServerUrl", PropertyUtils.getProperty("login.server.url"));
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
