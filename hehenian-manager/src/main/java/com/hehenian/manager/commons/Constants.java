package com.hehenian.manager.commons;

public class Constants {
	
	/**
	 * 成功状态码
	 */
	public static final int SUCCESS = 1;

	/**
	 * 失败状态码
	 */
	public static final int FAIL = -1;

	/**
	 * 不合法状态码
	 */
	public static final int INVALID = -2;
	
	/**
	 * 已经存在
	 */
	public static final int HASEXSIT=-3;
	
	/**
	 * 不存在状态码
	 */
	public static final int NOTEXIT=-4;

	public static String FAILURESTRING = "failure";
	
	public static String COOKIE_DOMAIN = ".hehenian.com";
	
	public static String SESSION_KEY = "enFlag";
	
	public static String UNLOGIN_KEY="sid";
	
	public static String AUTHCODE_PREFIX="auth:";
	
	public static String CURRENT_USER_ID = "currentUserId";
	
	public static final String SESSION_LOGIN_TIME = "loginTime";

	public static final String SESSION_LOGIN_IP = "loginIp";

	public static final String SESSION_CREATE_TIME = "createTime";
	
	public static final String SESSION_RECOMMAND="recommand";
	
	public static final String COOKIE_VALID_EMAIL="validEMAIL";
	public static final String COOKIE_VALID_PHONE="validPhone";
	
	/**
	 * 权限标签
	 */
	public static final String AUTHORITIES = "authorities";
	
    /**
	 * 验证不通过页面
	 */
	public static final String INVALIDPAGE="invalid";
	
	/**
	 * 登录状况常量
	 * 
	 *
	 */
	public static class LoginInfoStatus{
		
		/**
		 * 用户ID
		 */
		public static final int USERID=1;
		
		/**
		 * 手机或者邮箱
		 */
		public static final int PHONEOREMAIL=2;
	}
	
	
}
