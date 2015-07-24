package com.sp2p.system.listener;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import sun.print.resources.serviceui;

import com.shove.Convert;
import com.shove.data.DataException;
import com.sp2p.constants.IConstants;
import com.sp2p.service.SiteInformationService;
import com.sp2p.service.admin.CloseNetWorkService;

/**
 * 服务启动加载t_closeNetWork表中的数据，并保存到application中
 * @author Administrator
 *
 */
public class CloseNetWorkConfigiListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	public void contextInitialized(ServletContextEvent sce) {
	
	
		
		ServletContext context = sce.getServletContext();
		//context.setAttribute(name, object)设置到application中
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		CloseNetWorkService close=(CloseNetWorkService)(webApplicationContext.getBean("closeNetWorkService"));//可以获取到Spring注入
		SiteInformationService  site=(SiteInformationService)(webApplicationContext.getBean("siteInformationService"));
		Map<String,String> map=null;
		Map<String,String> siteMap=null;
		try {
			map=close.getNetWorkById();
			siteMap=site.querySiteAll();
			context.setAttribute(IConstants.Session_CLOSENETWORK, map);//设置到application中
			context.setAttribute("sitemap", siteMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (DataException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}