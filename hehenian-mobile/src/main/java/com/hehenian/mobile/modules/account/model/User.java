/**  
 * @Project: hehenian-mobile
 * @Package com.model
 * @Title: User.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-3-23 上午11:40:58
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.modules.account.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{
	private int id;
	private String userName;
	private String password;
	private String mobilePhone;
	
	
	public User() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	
	
}
