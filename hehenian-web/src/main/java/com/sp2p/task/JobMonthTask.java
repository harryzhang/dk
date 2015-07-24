package com.sp2p.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

public class JobMonthTask extends QuartzJobBean {

	private static Log log = LogFactory.getLog(JobMonthTask.class);
	private static boolean isRunning = false;

	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext()
				.getBean(beanName);
	}
	private Object getApplicationMap(String attrName){
		return ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(attrName);
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
//		long start = System.currentTimeMillis();
//
//		AwardService awardService = (AwardService) getBean("awardService");
//		 //获取平台收费
//	    Map<String,Object> platformCostMap = (Map<String, Object>) getApplicationMap(IAmountConstants.FEE_APPLICATION);
		try {
			if (!isRunning) {
				isRunning = true;
//				log.info("开始计算提成奖励");
//				awardService.monthAward();
//				//计算奖励提成
//				AgentcommissionService agentcommissionService  = (AgentcommissionService)getBean("agentcommissionService");
//				agentcommissionService.AddAgentcommission();
//				log.info("提成奖励处理完毕");
				isRunning = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
//		log.info("用时 : " + (System.currentTimeMillis() - start) + "毫秒"
//				+ "SystemMemery:freeMemory" + Runtime.getRuntime().freeMemory()
//				+ "-------maxMemory" + Runtime.getRuntime().maxMemory()
//				+ "-------totalMemory" + Runtime.getRuntime().totalMemory());
	}
}
