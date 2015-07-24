package com.hehenian.web.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationContextListener implements ServletContextListener{

	
	public ApplicationContextListener() {
		super();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		event.getServletContext().setAttribute("hhnServerUrl", PropertyUtils.getProperty("hhn.server.url"));
		event.getServletContext().setAttribute("loanServerUrl", PropertyUtils.getProperty("loan.server.url"));
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
