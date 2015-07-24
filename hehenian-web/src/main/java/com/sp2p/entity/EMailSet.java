package com.sp2p.entity;

import java.io.Serializable;

/**
 * 发送邮件参数邮件参数
 * @author Administrator
 *
 */
public class EMailSet implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String mailaddress;//邮箱密码
	private String mailpassword;//邮箱密码
	private String sendmail;//发自那个邮箱
	private String sendname;//发件人昵称
	private String host;//服务器
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMailaddress() {
		return mailaddress;
	}
	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}
	public String getMailpassword() {
		return mailpassword;
	}
	public void setMailpassword(String mailpassword) {
		this.mailpassword = mailpassword;
	}
	public String getSendmail() {
		return sendmail;
	}
	public void setSendmail(String sendmail) {
		this.sendmail = sendmail;
	}
	public String getSendname() {
		return sendname;
	}
	public void setSendname(String sendname) {
		this.sendname = sendname;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
}
