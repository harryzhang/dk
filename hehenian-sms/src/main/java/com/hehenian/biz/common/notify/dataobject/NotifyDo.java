package com.hehenian.biz.common.notify.dataobject;

import java.io.Serializable;
import java.util.Date;

public abstract class NotifyDo implements Serializable {

    /**
     * 消息类型：邮件
     */
    public static final String MAIL         = "MAIL";
    /**
     * 消息类型：短信
     */
    public static final String SMS          = "SMS";
    /**
     * 消息类型：融资端站内通知
     */
    public static final String LOAN_NOTICE  = "LOANNOTICE";
    
    /**
     * 站内通知
     */
    public static final String SITE_MESSAGE = "SITE_MESSAGE";
    
    public static  String MESSAGE_CONTEXT="亲，您好，附件为平台每日资金报表，加油！";

    /**
     * 消息ID
     */
    protected int              messageId;
    /**
     * 消息内容, 是一个JSON格式的字符串表示的map或者jsonobject map的key需要跟模板一致
     */
    protected String           message;
    /**
     * 消息模板
     */
    protected String           messageTemplate;
    /**
     * 消息发送者
     */
    protected String           sender;

    /**
     * 短信多个接受人之间用英文逗号分隔,邮件用英文;分隔
     */
    protected String           recievers;
    /**
     * 发送成功失败标志： F 失败， T发送成功
     */
    protected String           sendFlag     = "F";

    /**
     * 是否异步
     */
    protected boolean          async;
    /**
     * 发送失败是否转定时发送：T转定时， F不转定时
     */
    protected boolean          failConvertSchedule;
    /**
     * 是否有效： T 有效， F无效
     */
    protected String           validate     = "T";

    /**
     * 最后更新时间
     */
    protected Date             updateTime;

    /**
     * 业务类型
     */
    protected String           businessType;

    abstract public String getMessageType();

    /**
     * 如果指发送一个简单的消息，建议直接调用这个方法 这个方法会构造一个简单的JSON字符串，并以 content作为key 应用默认的消息模板
     */
    public void setSimpleMessage(String simpleMessge) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"content\":\"").append(simpleMessge).append("\"}");

        this.message = sb.toString();
    }

    // getter and setter
    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecievers() {
        return recievers;
    }

    public void setRecievers(String recievers) {
        this.recievers = recievers;
    }

    public String getCcList() {
        return null;
    }

    public String getSubject() {
        return null;
    }
    
    public String getFilePath() {
    	return null;
    }
    
    public String getFileName() {
    	return null;
    }
    
	public String getMessageContext() {
		return null;
	}

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public boolean isFailConvertSchedule() {
        return failConvertSchedule;
    }

    public void setFailConvertSchedule(boolean failConvertSchedule) {
        this.failConvertSchedule = failConvertSchedule;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    /**
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     *            the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return businessType
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * @param businessType
     *            the businessType to set
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    // end getter and setter
}
