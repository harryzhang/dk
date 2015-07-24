package com.hehenian.app.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
