/**  
 * @Project: hehenian-mobile
 * @Package com.dao
 * @Title: UserDao.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-3-23 上午11:54:14
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.modules.account.dao;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;


public interface UserDao {
	public AccountUserDo findUserByUserNamePwd(String userName, String password);
	
}
