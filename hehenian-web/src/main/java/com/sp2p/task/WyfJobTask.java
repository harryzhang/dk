/*
 * cron4j - A pure Java cron-like scheduler
 * 
 * Copyright (C) 2007-2010 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version
 * 2.1, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License 2.1 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License version 2.1 along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.sp2p.task;

import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.BorrowService;
import com.sp2p.service.admin.BorrowManageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

/**
 * This task counts from 1 to 30.
 */
public class WyfJobTask extends QuartzJobBean {

    private static Log     log       = LogFactory.getLog(WyfJobTask.class);
    private static boolean isRunning = false;

    private JobTaskService        jobTaskService;

    @Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {

        long start = System.currentTimeMillis();
        //自动投标
        JobTaskService jobTaskService = (JobTaskService) getBean("jobTaskService");


        try {
            if (!isRunning) {
                log.info("减免物业费 自动投标一轮开始");
                jobTaskService.autoBidV3();
                log.info("减免物业费 自动投标一轮结束");

                isRunning = false;
            }
        } catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		log.info("用时 : " + (System.currentTimeMillis() - start) + "毫秒");
	}
	public boolean canBePaused() {
		
		return true;
	}

	public boolean canBeStopped() {
		return true;
	}

	public boolean supportsCompletenessTracking() {
		return true;
	}

	public boolean supportsStatusTracking() {
		return true;
	}

	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	
	public JobTaskService getJobTaskService() {
		return jobTaskService;
	}

	public void setJobTaskService(JobTaskService jobTaskService) {
		this.jobTaskService = jobTaskService;
	}




}
