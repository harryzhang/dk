/**
 *  @ Project : p2pt notify
 *  @ File Name : ShortMessage.java
 *  @ Date : 2014/8/20
 *  @ Author : harry.zhang
 * 
 */

package com.hehenian.biz.common.notify.dataobject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 融资端站内消息 t_notify DO
 * @param subject
 *            通知主题
 * @param MESSAGE
 *            通知内容
 * @param sender
 *            创建者
 * @param recievers
 *            用户ID(loanUserId:userId)
 * @param send_flag
 *            状态：T：读取F：未读
 * @param message_type
 *            消息类型
 * @param is_validate
 *            是否有效：T：有效F：删除
 * @param create_time
 *            创建时间
 * @param update_time
 *            更新时间
 * @param business_type
 *            业务类型(待定)
 * @author huangzl QQ: 272950754
 * @date 2015年7月2日 上午11:27:02
 * @Project hehenian-sms
 * @Package com.hehenian.biz.common.notify.dataobject
 * @File LoanNotifyDo.java message_id ID
 */
public class LoanNotifyDo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 消息类型：融资端站内通知
	 */
	public static final String LOAN_NOTICE = "LOANNOTICE";

	/**
	 * 消息ID
	 */
	protected Long messageId;
	/**
	 * 通知主题
	 */
	protected String subject;
	/**
	 * 通知内容
	 */
	protected String message;
	/**
	 * 创建者
	 */
	protected String sender;
	/**
	 * 用户ID(loanUserId:userId)
	 */
	protected String recievers;
	/**
	 * 状态：T：读取F：未读
	 */
	protected String sendFlag;
	/**
	 * 消息类型
	 */
	protected String messageType = LOAN_NOTICE;

	/**
	 * 是否有效：T：有效F：删除
	 */
	protected String validate = "T";

	/**
	 * 创建时间
	 */
	protected Date createTime;
	protected String createTimeString;
	/**
	 * 最后更新时间
	 */
	protected Date updateTime;
	protected String updateTimeString;

	/**
	 * 业务类型(待定)
	 */
	protected String businessType;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		setCreateTimeString(new SimpleDateFormat("yyyy-MM-dd H:m:s").format(createTime) );
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		setUpdateTimeString(new SimpleDateFormat("yyyy-MM-dd H:m:s").format(updateTime) );
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public static String getLoanNotice() {
		return LOAN_NOTICE;
	}

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public String getUpdateTimeString() {
		return updateTimeString;
	}

	public void setUpdateTimeString(String updateTimeString) {
		this.updateTimeString = updateTimeString;
	}

}
