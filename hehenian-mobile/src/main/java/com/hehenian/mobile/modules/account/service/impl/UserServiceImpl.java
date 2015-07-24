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
package com.hehenian.mobile.modules.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.mobile.modules.account.dao.UserDao;
import com.hehenian.mobile.modules.account.service.UserService;

@Service
public class UserServiceImpl  implements UserService{

	@Autowired
	private UserDao userDao;
	

	@Override
	public AccountUserDo loginWithPwd(String username, String password) {
		AccountUserDo accountUserDo = userDao.findUserByUserNamePwd(username, password);
		 return login(accountUserDo);
	}
	
    private AccountUserDo login(AccountUserDo accountUserDo){
        if (accountUserDo != null){
//            PhoneVerifyDo phoneVerify = phoneVerifyComponent.findPhoneVerify(accountUserDo.getId());
//            if (phoneVerify!=null){
//                accountUserDo.setPhoneHasVerify(true);
//            }else {
//                accountUserDo.setPhoneHasVerify(false);
//            }
//            //记录用户登录日志
//            operationLogComponent.addOperationLog("t_user",accountUserDo.getUsername(),2,"",0.0,"用户登录",1);
        }
        return accountUserDo;
    }



	@Override
	public IResult registerUser(AccountUserDo accountUserDo) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
