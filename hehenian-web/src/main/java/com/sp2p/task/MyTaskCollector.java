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

import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskCollector;
import it.sauronsoftware.cron4j.TaskTable;

/**
 * The custom TaskCollector used to retrieve the task list. This sample
 * implementation returns always the same task that the scheduler executes once
 * a minute.
 */
public class MyTaskCollector implements TaskCollector {
	
	public MyTaskCollector(){
		
	}
	
	public TaskTable getTasks() {
		//Minutes - Hours - Days of month - Months - Days of week
		//每隔5分钟启动定时器
		SchedulingPattern inTimePattern = new SchedulingPattern("*/5 * * * * ");
		//每日凌晨0点0分0秒启动定时器 0 0 * * *
		SchedulingPattern dayPattern = new SchedulingPattern(" 0 0 * * *");
		//每月1号凌晨0点0分0秒启动定时器 0 0 1 * * 
		SchedulingPattern monthPattern = new SchedulingPattern(" 0 0 1 * * ");
		TaskTable ret = new TaskTable();
//		Task jobInTimeTask = new JobInTimeTask();
//		ret.add(inTimePattern, jobInTimeTask);
//		Task jobDayTask = new JobDayTask();
//		ret.add(dayPattern, jobDayTask);
//		Task jobMonTask = new JobMonthTask();
//		ret.add(monthPattern, jobMonTask);
		return ret;
	}

}
