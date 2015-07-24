/**  @ Project : p2pt notify
 *  @ File Name : MailMessage.java
 *  @ Date : 2014/8/20
 *  @ Author : harry.zhang
 * 
 */

package com.hehenian.biz.common.notify.dataobject;



public class MailNotifyDo extends NotifyDo {
	/**
	 * 多个接受人之间用英文逗号分隔
	 */
	private String ccList;
	/**
	 * 邮件主题
	 */
	private String subject;
	
	private String messageType;
	
	private String filePath;
	
	private String fileName;
	
	public String getMessageType() {
		return this.MAIL;
	}
	public void setMessageType(String messageType) {
		this.messageType = this.MAIL;
	}

	//getter and setter
	public String getCcList() {
		return ccList;
	}
	public void setCcList(String ccList) {
		this.ccList = ccList;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	//end getter and setter
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
