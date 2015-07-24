/**
 *  @ Project : p2pt notify
 *  @ File Name : NotifySchedule.java
 *  @ Date : 2014/8/20
 *  @ Author : harry.zhang
 */

package com.hehenian.biz.service.notify.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.task.DefaultTask;
import com.hehenian.biz.component.notify.INotifyDao;

/**
 * 定时发送
 * 
 * @author zhangyunhmf
 * 
 */
public class NotifySchedule extends DefaultTask {

    private String                  jobName      = "邮件短信发送定时任务";
    private INotifyService          notifyService;
	private Map<String, INotifyDao> notifyDaoMap = new HashMap<String,INotifyDao>();
	//getter and setter
	public void setNotifyDaoMap(Map<String, INotifyDao> notifyDaoMap) {
		this.notifyDaoMap = notifyDaoMap;
	}

    /**
     * @param notifyService
     *            the notifyService to set
     */
    public void setNotifyService(INotifyService notifyService) {
        this.notifyService = notifyService;
    }


    /**
     * 发送短信的job 方法
     */
	public void send() {
		List<NotifyDo> unSendList = new ArrayList<NotifyDo>();
		
		for(Map.Entry<String, INotifyDao> entry : notifyDaoMap.entrySet()){
				INotifyDao notifyDao = entry.getValue();
				unSendList.addAll(notifyDao.listUnSendMessageList());
		}
		
		for(NotifyDo notifyDo:unSendList){
            notifyService.send(notifyDo);
		}
	}
	


    /*
     * (no-Javadoc) <p>Title: getJobName</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see com.hehenian.biz.common.task.DefaultTask#getJobName()
     */
    @Override
    protected String getJobName() {
        return this.jobName;
    }

    /*
     * (no-Javadoc) <p>Title: doJob</p> <p>Description: </p>
     * 
     * @see com.hehenian.biz.common.task.DefaultTask#doJob()
     */
    @Override
    protected void doJob() {
        this.send();
    }

}
