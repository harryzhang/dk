package com.hehenian.manager.modules.login.vo;

import java.io.Serializable;

public class PhoneEmail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7536950665560648567L;

	private int userId;
	
	private String phone;
	
	private String email;
	
	private int phoneValid;
	
	private int emailValid;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhoneValid() {
		return phoneValid;
	}

	public void setPhoneValid(int phoneValid) {
		this.phoneValid = phoneValid;
	}

	public int getEmailValid() {
		return emailValid;
	}

	public void setEmailValid(int emailValid) {
		this.emailValid = emailValid;
	}
	
	
}
