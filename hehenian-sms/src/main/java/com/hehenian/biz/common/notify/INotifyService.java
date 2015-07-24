package com.hehenian.biz.common.notify;

import com.hehenian.biz.common.notify.dataobject.NotifyDo;

/**
 * 发送消息 
 * @author zhangyunhmf
 *
 */
public interface INotifyService {
	
	/**
	 * 发送短信和邮件接口
	 * 
	 * @param notifyDo  发送的内容用 notifyDo的实现类 SMSNotifyDo, MailNotifyDo
	 */
    public boolean send(NotifyDo notifyDo);

    /**
     * 验证手机号是否有效
     * 
     * @param mobile
     *            手机
     * @param identifyCode
     *            手机验证码
     * @return boolean
     * @author: zhangyunhmf
     * @date: 2015年1月5日上午11:34:09
     */
    public boolean checkIdentifyCode(String mobile, String identifyCode);

}
