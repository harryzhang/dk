/**  
 * @Project: hehenian-mobile
 * @Package com.hehenian.mobile.common.constants
 * @Title: WebThreadVariable.java
 * @Description: 线程变量
 *
 * @author: zhanbmf
 * @date 2015-3-28 上午11:36:15
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.common.constants;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;

public class WebThreadVariable {

	/**
	 * 当前用户线程变量
	 */
	private static ThreadLocal<PersonDo> personDoVariable = new ThreadLocal<PersonDo>();
	
	/**
	 * 当前登录用户
	 */
	private static ThreadLocal<AccountUserDo> accountUserVariable = new ThreadLocal<AccountUserDo>();
	
	/**
	 * session
	 */
	private static ThreadLocal<String> rootVariable = new ThreadLocal<String>();
	
	/**
	 * 得到线程当前用户对象
	 * @return
	 */
	public static PersonDo getPersonDo(){
		return personDoVariable.get();
	}
	
	/**
	 * 设置当前用户
	 * 
	 * @param user
	 */
	public static void setPersonDo(PersonDo pd) {
		personDoVariable.set(pd);
	}
	
	/**
	 * 移除当前用户
	 */
	public static void removePersonDo() {
		personDoVariable.remove();
	}
	
	/**
	 * 得到线程当前用户对象
	 * @return
	 */
	public static AccountUserDo getAccountUserDo(){
		return accountUserVariable.get();
	}
	
	/**
	 * 设置当前用户
	 * 
	 * @param user
	 */
	public static void setAccountUserDo(AccountUserDo aud) {
		accountUserVariable.set(aud);
	}
	
	/**
	 * 移除当前用户
	 */
	public static void removeAccountUserDo() {
		accountUserVariable.remove();
	}
	
	public static String getRoot(){
		return rootVariable.get();
	}
	
	public static void setRoot(String root){
		rootVariable.set(root);
	}
	
	public static void removeRoot(){
		rootVariable.remove();
	}
}
