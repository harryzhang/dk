package com.hehenian.biz.common.notify.dataobject;

public class NotifyManagerDo {
	
	private int id;
	
	/**
	 * 重做次数
	 */
	private int reDo;

	/**
	 * 是否发送的开关： true 发送， false 不发送
	 */
	private boolean isSend;

	/**
	 * 定时发送的时间
	 */
	private String scheduleTime;
	
	/**
	 * 针对那种消息类型的管理:短信，邮件
	 */
	private String messageType;
	
	/**
	 * 是否可用
	 */
	private String validate;

	public int getReDo() {
		return reDo;
	}

	public void setReDo(int reDo) {
		this.reDo = reDo;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}

	public String getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}
		
}
