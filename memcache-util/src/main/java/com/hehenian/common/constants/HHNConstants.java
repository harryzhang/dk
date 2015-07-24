/**  
 * @Project: memcache-util
 * @Package com.hehenian.common.constants
 * @Title: HHNConstants.java
 * @Description: TODO
 *
 * @author: zhanbmf
 * @date 2015-3-28 下午6:38:37
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.common.constants;

public class HHNConstants {
	
	/**
	 * session过期时间60 *30
	 */
	public static final short SESSION_CACHE_TIME = 60 *30;
	
	/**
	 * cookie过期时间60 *24
	 */
	public static final short COOKIE_CACHE_TIME = 60 *24;
	
	public static final String SESSION_INFO = "user";
	
	public static final String CHANNEL = "channel"; 
	
	public static final String SESSION_ROOT = "s_root";
	
	/**
	 * 邮箱0，手机1，昵称2
	 */
	public enum LoginType {
		/**
		 * 邮箱登录
		 */
		Email(0),
		
		/**
		 * 手机号登录
		 */
		Phone(1),
		
		/**
		 * 用户名登录
		 */
		NickName(2);
		
		private int index;
		
		LoginType(int index){
			this.index = index;
		}
		public int getIndex() { 
			return index; 
		} 
	}
}
