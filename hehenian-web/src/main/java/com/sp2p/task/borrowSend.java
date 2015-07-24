package com.sp2p.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时发布借款
 * 
 * @author xiemin
 * @version [版本号, 2013-10-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class borrowSend extends QuartzJobBean {
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		{
//			 Timer timer = new Timer();
//			 timer.schedule(new TaskTimer(), 0, 6000);
			new TaskTimer().run();
		}

	}

}
