package com.hehenian.app.common.exception;

public class SessionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SessionException() {
		super();
		
	}

//	public SessionException(String message, Throwable cause,
//			boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//		
//	}

	public SessionException(String message) {
		super(message);
		
	}

	public SessionException(Throwable cause) {
		super(cause);
		
	}

	
}
