package com.sp2p.task;

import java.util.TimerTask;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.sp2p.action.admin.BorrowManageAction;

/**
 * 定时任务
 * 
 * @author xiemin
 * @version [版本号, 2013-10-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TaskTimer extends TimerTask {

	@Override
	public void run() {
		try {
			WebApplicationContext context = ContextLoader
					.getCurrentWebApplicationContext();
			if (context == null) {
				return;
			}
			BorrowManageAction ba = (BorrowManageAction) context
					.getBean("borrowManageAction");
			if (ba == null) {
				return;
			}
			//ba.timerSend();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
