package com.hehenian.biz.common.base.result;

import java.io.Serializable;

/**
 * 
 * @author: liuzg
 * 
 * @date 2014年7月27日 下午3:40:33
 */
public class ResultSupport<T> implements IResult<T>, Serializable {

	private static final long serialVersionUID = 1L;
	private boolean success; // 成功标示
	private String resultCode; // 请求结果状态码
	private String errorMessage; // 错误信息
	private T model; // 模型对象

	public ResultSupport() {
	}

	public ResultSupport(boolean success) {
	    this.success = success;
    }
	
	public ResultSupport(boolean success, String errorMessage) {
		this.success = success;
		this.errorMessage = errorMessage;
	}

	public ResultSupport(boolean success, String resultCode, String errorMessage) {
		this.success = success;
		this.resultCode = resultCode;
		this.errorMessage = errorMessage;
	}

	public ResultSupport(boolean success, T model) {
		this.success = success;
		this.model = model;
	}

	public boolean isSuccess() {
		return this.success;
	}

	@Override
	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String getResultCode() {
		return this.resultCode;
	}

	@Override
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMessage;
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		this.success = false;
	}

	@Override
	public T getModel() {
		return model;
	}

	@Override
	public void setModel(T model) {
		this.model = model;
	}
	
	public static IResult buildResult(int errorCode){
        return new ResultSupport(false, errorCode);
    }
}
