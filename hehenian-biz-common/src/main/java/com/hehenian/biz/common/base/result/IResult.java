package com.hehenian.biz.common.base.result;

/**
 * 
 * @author: liuzg
 * @date 2014年7月27日 下午3:39:02
 */
public interface IResult<T> {

	/**
	 * 请求是否成功
	 * 
	 * @return
	 * @date: 2014年2月27日下午3:48:21
	 */
	boolean isSuccess();

	/**
	 * 设置请求的成功状态
	 * 
	 * @param success
	 * @date: 2014年2月27日下午3:48:34
	 */
	void setSuccess(boolean success);

	/**
	 * 获取结果代码
	 * 
	 * @return
	 * @date: 2014年2月27日下午3:49:20
	 */
	String getResultCode();

	/**
	 * 设置结果代码
	 * 
	 * @param resultCode
	 * @date: 2014年2月27日下午3:49:53
	 */
	void setResultCode(String resultCode);

	/**
	 * 获取错误信息
	 * 
	 * @return
	 * @date: 2014年2月27日下午3:51:24
	 */
	String getErrorMessage();

	/**
	 * 设置错误信息
	 * 
	 * @param errorMessage
	 * @date: 2014年2月27日下午3:51:45
	 */
	void setErrorMessage(String errorMessage);

	/**
	 * 获取模型对象信息
	 * 
	 * @return
	 * @date: 2014年2月27日下午3:52:18
	 */
	T getModel();

	/**
	 * 设置模型对象信息
	 * 
	 * @param model
	 * @date: 2014年2月27日下午3:52:37
	 */
	void setModel(T model);
}
