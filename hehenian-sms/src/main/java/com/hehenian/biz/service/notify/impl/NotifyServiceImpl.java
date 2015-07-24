/**
 *  @ Project : p2pt notify
 *  @ File Name : NotifySender.java
 *  @ Date : 2014/8/20
 *  @ Author : harry.zhang
 * 
 */
package com.hehenian.biz.service.notify.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.notify.SMSConfig;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.notify.dataobject.NotifyManagerDo;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.common.util.JsonUtil;
import com.hehenian.biz.common.util.StringUtil;
import com.hehenian.biz.component.notify.INotifyComponent;
import com.hehenian.biz.component.notify.manager.INotifyManagerComponent;

/**
 * 发送器
 * 
 * @author zhangyunhmf
 * 
 */
public class NotifyServiceImpl implements INotifyService {
	
    protected static final Logger         logger = Logger.getLogger(INotifyService.class);
	
	/**
	 * 短信管理操作类
	 */
	private INotifyManagerComponent notifyManager;
	
	/**
	 * 短信类型做key: MAIL, SMS,  短信类型对应的component做value
	 */
	private Map<String, INotifyComponent> notifyComponentMap;

	/**
	 * 短信类型做key: MAIL, SMS， 短信类型对应的发生器做value
	 * 通知发送器：mail实现或者短信实现
	 */
	private Map<String,INotifyService> senderMap;
	
    /**
	 * 
	 */
    @Autowired
    private SMSConfig                     smsConfig;

	//getter and setter
	public void setNotifyManager(INotifyManagerComponent notifyManager) {
		this.notifyManager = notifyManager;
	}


	public void setNotifyComponentMap(
			Map<String, INotifyComponent> notifyComponentMap) {
		this.notifyComponentMap = notifyComponentMap;
	}


	public void setSenderMap(Map<String, INotifyService> senderMap) {
		this.senderMap = senderMap;
	}
    //end getter and setter
	

	/**
	 * 定时发送方法入口
	 * @param notifyDo
	 * @return
	 */
	public boolean scheduleSend(NotifyDo notifyDo){
        return send(notifyDo);
	}


	/**
	 * 发送消息
	 * @param msg
	 * @return
	 */
    public boolean send(NotifyDo notifyDo) {
        boolean isOk = false;
		NotifyManagerDo notifyManagerDo = notifyManager.listNotifyManagerByNotifyType(notifyDo.getMessageType());
		if(null == notifyManagerDo){
			logger.error("没有正确配置消息发送，请检查 notifyManager的数据");
            return false;
		}
		
		if(! notifyManagerDo.isSend()){//开关控制不发送
            logger.warn("消息配置成不发送，请检查 notifyManager的数据");
            logger(notifyDo);
            return true;
		}
		
		if("T".equals(notifyDo.getSendFlag())){ //已经发送
            logger.error("消息发送状态是已发送了，不需要重复发送");
            return false;
		}
				
		int redo = notifyManagerDo.getReDo();
		/**
		 * 默认为一次
		 */
		if(redo == 0 ){
			redo = 1;
		}

        INotifyService notifyService = senderMap.get(notifyDo.getMessageType());
		for(int i = 0 ; i < redo ; i++){//重做次数
            // 发送成功
            isOk = notifyService.send(notifyDo);
            logger.debug("====================================call send sms result:" + isOk);
            if (isOk) { // 成功退出
                break;
            }
		}
		
        if (isOk) {
			notifyDo.setSendFlag("T");
		}else if(notifyDo.isFailConvertSchedule()){
			notifyDo.setSendFlag("F");
		}
		
		try{
			logger(notifyDo);
		}catch(Throwable t){
			logger.error("保持消息失败：");
            logger.error(t);
            return false;
		}
		
        return false;
	}

	/**
	 * 
	 * 如果是定时任务发送需要更新发送成功状态
	 * @param notifyDo
	 */
	private void logger(NotifyDo notifyDo){
		INotifyComponent notifyComponent  = notifyComponentMap.get(notifyDo.getMessageType());
		if(notifyDo.getMessageId() != 0){
			NotifyDo tempNotify = notifyComponent.getMessageById(notifyDo.getMessageId());
			if(null != tempNotify){
				notifyComponent.updateMessageFlag("T", notifyDo.getMessageId());
			}else{
				notifyComponent.addMessage(notifyDo);
			}
		}else{
			notifyComponent.addMessage(notifyDo);
		}
	}

    /*
     * (no-Javadoc) <p>Title: checkIdentifyCode</p> <p>Description: </p>
     * 
     * @param mobile
     * 
     * @param identifyCode
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.common.notify.INotifyService#checkIdentifyCode(java.
     * lang.String, java.lang.String)
     */
    @Override
    public boolean checkIdentifyCode(String mobile, String identifyCode) {

        NotifyManagerDo notifyManagerDo = notifyManager.listNotifyManagerByNotifyType(NotifyDo.SMS);
        if (null == notifyManagerDo) {
            logger.error("没有正确配置消息发送，请检查 notifyManager的数据");
            return false;
        }

        if (!notifyManagerDo.isSend()) {// 开关控制不发送
            logger.warn("消息配置成不发送，请检查 notifyManager的数据");
            return true;
        }

        INotifyComponent smsComponent = notifyComponentMap.get("SMS");
        if (null == smsComponent) {
            throw new BusinessException("没配置 SMSComponent");
        }
        List<NotifyDo> notifyList = smsComponent.getLastIdentifyCode(mobile);
        if (null == notifyList) {
            return false;
        }
        for (NotifyDo notify : notifyList) {
            Map<String, String> msgMap = (Map) JsonUtil.json2Bean(notify.getMessage(), Map.class);
            if (identifyCode.equals(msgMap.get("content")) && "T".equals(notify.getSendFlag())
                    && "T".equals(notify.getValidate())) {
                Date now = new Date();
                long ss = DateUtil.diffDate(notify.getUpdateTime(), now);
                notify.setValidate("F");
                smsComponent.updateMessage(notify);
                if (ss < StringUtil.strToInt(smsConfig.getSmsValidateTime()) * 60) {// 30分钟*60秒
                    return true;
                }


            }
        }
        return false;
    }

    /**
     * @param smsConfig
     *            the smsConfig to set
     */
    public void setSmsConfig(SMSConfig smsConfig) {
        this.smsConfig = smsConfig;
    }
	
}
