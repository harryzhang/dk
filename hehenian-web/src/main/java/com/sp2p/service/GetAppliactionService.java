package com.sp2p.service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.web.context.ServletConfigAware;
import com.sp2p.entity.EMailSet;

@SuppressWarnings("serial")
public class GetAppliactionService implements ServletContextAware ,ServletConfigAware {
	private ServletContext application;
  public  EMailSet getEMailSet(){
	  System.out.println("ssssss");
	  System.out.println(application+"==");
	  return null;
  }

@Override
public void setServletContext(ServletContext arg0) {
	System.out.println("ssssffsd");
	this.application = arg0;
}

@Override
public void setServletConfig(ServletConfig arg0) {
	
} 
}
