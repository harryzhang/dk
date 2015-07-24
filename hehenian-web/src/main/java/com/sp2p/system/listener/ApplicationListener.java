package com.sp2p.system.listener;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shove.Convert;
import com.shove.config.GopayConfig;
import com.shove.config.IPayConfig;
import com.shove.data.DataException;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.service.FrontMyPaymentService;
import com.sp2p.service.admin.AccountPaymentService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.service.admin.GetMailMsgOnUpService;
import com.sp2p.service.admin.GetSEOConfigOnUpService;
import com.sp2p.service.admin.PlatformCostService;
import com.sp2p.service.admin.ShoveApproveNoticeTemplateService;

public class ApplicationListener extends ContextLoaderListener implements
		ServletContextListener,ServletRequestListener {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(ApplicationListener.class);
	private ServletContext context;
	private WebApplicationContext webApplicationContext;

	public void contextDestroyed(ServletContextEvent sce) {
	}

	/**
	 * 服务器启动时加载,初始化一些信息
	 */

	public void contextInitialized(ServletContextEvent sce){
		this.context = sce.getServletContext();
		this.webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		Map<String,Object> feeApplicationMap = new HashMap<String,Object>();
		
		//初始化平台收费
		PlatformCostService  platformCostService = (PlatformCostService) webApplicationContext.getBean("platformCostService");
		List<Map<String,Object>> platformCostList =null;
		
		//初始化通知模板
		ShoveApproveNoticeTemplateService approveNoticeTemplateService = (ShoveApproveNoticeTemplateService) webApplicationContext.getBean("shoveApproveNoticeTemplateService");
		List<Map<String,Object>> noticeTemplateList =null;
		Map<String,Object> informTemplateApplicationMap = new HashMap<String,Object>();
		List<Map<String,Object>>  linkList = new ArrayList<Map<String,Object>>();
		Map<String,String>  investMap = new HashMap<String, String>();
		try {
			platformCostList = platformCostService.queryAllPlatformCost();
			for(Map<String,Object> platformCostMap :platformCostList){
				double costFee = Convert.strToDouble(platformCostMap.get("costFee")+"", 0);
				int costMode=Convert.strToInt(platformCostMap.get("costMode")+"", 0);
				if(costMode==1)
				{
					feeApplicationMap.put(platformCostMap.get("alias")+"", costFee*0.01);
				}else
				{
					feeApplicationMap.put(platformCostMap.get("alias")+"", costFee);
				}
				
				platformCostMap = null;
			}
			//模板
			noticeTemplateList = approveNoticeTemplateService.queryAllInformTemplate();
			for(Map<String,Object> noticeTemplateMap : noticeTemplateList){
				informTemplateApplicationMap.put(noticeTemplateMap.get("nid")+"", noticeTemplateMap.get("template")+"");
				noticeTemplateMap = null;
			}
			////初始化邮件参数
			GetMailMsgOnUpService  getMailMsgOnUpService = (GetMailMsgOnUpService) webApplicationContext.getBean("getMailMsgOnUpService");
			Map<String,String> mailSet =getMailMsgOnUpService.getMailSet();
			if (mailSet != null) {
				IConstants.MAIL_HOST = mailSet.get("host")+"";
				IConstants.MAIL_USERNAME = mailSet.get("mailaddress")+"";
				IConstants.MAIL_PASSWORD = mailSet.get("mailpassword")+"";
				IConstants.MAIL_FROM = mailSet.get("sendmail")+"";
				IConstants.NICK =javax.mail.internet.MimeUtility.encodeText(mailSet.get("sendname"))+"";
			}
		////初始化SEO参数
			GetSEOConfigOnUpService getSEOConfigOnUpService = (GetSEOConfigOnUpService) webApplicationContext.getBean("getSEOConfigOnUpService");
			Map<String,String> seoConfig =getSEOConfigOnUpService.getSEOConfig();
			if (seoConfig != null) {
				IConstants.SEO_TITLE = seoConfig.get("title")+"";
				IConstants.SEO_KEYWORDS = seoConfig.get("keywords")+"";
				IConstants.SEO_DESCRIPTION = seoConfig.get("description")+"";
				IConstants.SEO_OTHERTAGS = seoConfig.get("otherTags")+"";
			}
	
			//初始化  支付方式
			FundManagementService fundManagementService = (FundManagementService) webApplicationContext.getBean("fundManagementService");
			List<Map<String,Object>> mapList = fundManagementService.queryAccountPaymentList();
			for (Map<String, Object> map : mapList) {
				String nid = Convert.strToStr(map.get("nid")+"", "");
				String config = Convert.strToStr(map.get("config")+"","");
				Map<String,String> maps = (Map<String,String>)JSONObject.toBean(JSONObject.fromObject(config),HashMap.class);
				if("IPS".equals(nid)){
					IPayConfig.ipay_mer_code = maps.get("customerID")+"";
					IPayConfig.ipay_certificate =  maps.get("privatekey");
				}
				if ("gopay".equals(nid)) {
					GopayConfig.gopay_virCardNoIn =  maps.get("virCardNoIn")+"";
					GopayConfig.gopay_verficationCode =  maps.get("VerficationCode")+"";
					GopayConfig.gopay_merchantID =  maps.get("merchantID")+"";
				}
			}
			FrontMyPaymentService frontMyPayment = (FrontMyPaymentService) webApplicationContext.getBean("frontpayService");
			investMap = frontMyPayment.querInvesttou();
			context.setAttribute("investMap",investMap);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}finally{
			platformCostList = null;
			noticeTemplateList = null;
			System.gc();
			context.setAttribute(IAmountConstants.FEE_APPLICATION, feeApplicationMap);
			context.setAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION, informTemplateApplicationMap);
		}
	}
	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		String appContext = sre.getServletContext().getContextPath();
		String url = sre.getServletRequest().getScheme()+"://"+sre.getServletRequest().getServerName()+":"+ sre.getServletRequest().getServerPort() + appContext; 
		String basePath = System.getProperty("web.root");
		//设置webroot路径
		if(basePath == null){
			System.setProperty("web.root", url);			
		}
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		String appContext = sre.getServletContext().getContextPath();
		String url = sre.getServletRequest().getScheme()+"://"+sre.getServletRequest().getServerName()+":"+ sre.getServletRequest().getServerPort() + appContext; 
		String basePath = System.getProperty("web.root");
		//设置webroot路径
		if(basePath == null){
			System.setProperty("web.root", url);			
		}		
	}

	
}
