/**
 * Project Name:hehenian-manager
 * File Name:Result.java
 * Package Name:com.hehenian.manager.commons
 * Date:2015年5月5日下午2:04:22
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.commons;

import java.io.Serializable;

/**
 * 
 * @author   songxjmf
 * @date: 2015年5月5日 下午2:04:22 	 
 */
public class Result implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 是否成功标识
	 */
	private boolean isSuccess;
	/**
	 * 结果代码
	 */
	private String resultCode;
	/**
	 * 结果信息描述
	 */
	private String resultMsg;
	
	public Result(boolean isSuccess){
		this.isSuccess = isSuccess;
	}
	
	public Result(boolean isSuccess,String resultCode,String resultMsg){
		this.isSuccess = isSuccess;
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}

