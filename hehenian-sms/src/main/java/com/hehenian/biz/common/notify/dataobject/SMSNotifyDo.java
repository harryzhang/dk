/**
 *  @ Project : p2pt notify
 *  @ File Name : ShortMessage.java
 *  @ Date : 2014/8/20
 *  @ Author : harry.zhang
 * 
 */

package com.hehenian.biz.common.notify.dataobject;


/**
 * 短信
 * @author zhangyunhmf
 *
 */
public class SMSNotifyDo extends NotifyDo {

    public SMSNotifyDo() {
        this.setAsync(false);
        this.setSendFlag("F");
        this.setValidate("T");
        this.setSmsType(SmsType.MONTERNET.name());
        this.messageType = this.SMS;
    }

    public SMSNotifyDo(String messageContext, String mobile) {
        this();
        this.setSimpleMessage(messageContext);
        this.recievers = mobile;
    }

    public SMSNotifyDo(String messageContext, String mobile, String businessType) {
        this();
        this.setSimpleMessage(messageContext);
        this.recievers = mobile;
        this.businessType = businessType;
    }
	
	/**
	 * 短信类型， 根据这个类型取短信供应商 : youxuntong, default
	 */
	private String smsType;
	/**
	 * 消息类型 : MAIL, SMS
	 */
	private String messageType;
	
	/**
	 * 发送成功后返回的ID ,可以根据这个ID到短信查看消息
	 */
	private String sendId;
	

	public String getSmsType() {
		return smsType;
	}
	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
	
	//返回常量
	public String getMessageType() {
		return this.SMS;
	}
	public void setMessageType(String messageType) {
		this.messageType = this.SMS;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	
	
}
