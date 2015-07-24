/**  
 * @Project: hehenian-mobile
 * @Package com.dao
 * @Title: UserService.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-3-23 上午11:38:47
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.modules.account.service;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;

public interface UserService {
    /**
     * 账号登录验证
     * @Description: TODO
     * @param username
     * @param password
     * @return
     * @author: chenzhpmf
     * @date 2015-3-26 下午3:59:34
     */
	AccountUserDo loginWithPwd(String username,String password);
    /**
     * 注册
     * @Description: TODO
     * @param accountUserDo
     * @return
     * @author: chenzhpmf
     * @date 2015-3-26 下午4:17:07
     */
    IResult registerUser(AccountUserDo accountUserDo);

}
