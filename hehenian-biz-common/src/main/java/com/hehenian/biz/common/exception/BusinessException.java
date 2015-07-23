package com.hehenian.biz.common.exception;

/**
 * 业务异常对象
 * 
 * @author liuzgmf
 *
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String errorCode;

	public BusinessException() {
		super();
	}

	/**
	 * 通过错误信息创建业务异常对象
	 * 
	 * @param message
	 */
	public BusinessException(String message) {
		super(message);
	}

	/**
	 * 通过错误信息，错误码创建业务异常对象
	 * 
	 * @param message
	 * @param errorCode
	 */
	public BusinessException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * 通过错误信息，异常对象，错误码创建业务异常对象
	 * 
	 * @param message
	 * @param cause
	 * @param errorCode
	 */
	public BusinessException(String message, Throwable cause, String errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	/**
	 * 通过异常对象，错误码创建业务异常对象
	 * 
	 * @param cause
	 * @param errorCode
	 */
	public BusinessException(Throwable cause, String errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	/**
	 * @return errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

}
