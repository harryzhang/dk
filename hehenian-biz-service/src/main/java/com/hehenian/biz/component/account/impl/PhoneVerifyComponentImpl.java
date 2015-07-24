package com.hehenian.biz.component.account.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.IUserInfoService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.LoginInfoRelate;
import com.hehenian.biz.common.account.dataobject.PhoneVerifyDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.account.IPersonComponent;
import com.hehenian.biz.component.account.IPhoneVerifyComponent;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.dal.account.IPhoneVerifyDao;

@Component("phoneVerifyComponent")
public class PhoneVerifyComponentImpl implements IPhoneVerifyComponent {
    private static final Logger LOGGER = Logger.getLogger(PhoneVerifyComponentImpl.class);
    @Autowired
    private IPhoneVerifyDao phoneVerifyDao;
    @Autowired
    private IUserComponent  userComponent;
    
    @Autowired
    private IPersonComponent personComponent;
    
    @Autowired
    private IUserInfoService userInfoService;

    @Override public IResult savePhoneVerify(PhoneVerifyDo phoneVerifyDo) {
        IResult result = new ResultSupport();
        PhoneVerifyDo phoneVerifyByPhone = findPhoneVerifyByPhone(phoneVerifyDo.getMobilePhone());
        if (phoneVerifyByPhone == null) {
            //不存在重复的
            //将用户之前认证的手机号废除
            disablePhoneVerify(phoneVerifyDo.getUserId());
            int i = phoneVerifyDao.savePhoneVerify(phoneVerifyDo);
            if (i > 0) {
            	AccountUserDo accountUser = this.userComponent.getById(phoneVerifyDo.getUserId());
            	// 修改t_user表中的手机号码
                i = userComponent.updateUserPhone(phoneVerifyDo.getUserId(), phoneVerifyDo.getMobilePhone());
                // 修改t_person表中的手机号码
                this.personComponent.updateMobileByUserId(phoneVerifyDo.getUserId(), phoneVerifyDo.getMobilePhone());
                
                LoginInfoRelate loginInfoRelate = new LoginInfoRelate();
                loginInfoRelate.setLoginInfo(phoneVerifyDo.getMobilePhone());
                Integer userId = Integer.valueOf(phoneVerifyDo.getUserId().toString());
				loginInfoRelate.setUserId(userId);
                loginInfoRelate.setType(1);
                
                LoginInfoRelate deleteInfoRelate = this.userInfoService.getByLoginInfo(accountUser.getMobilePhone(), LoginInfoRelate.class, true);
                if (deleteInfoRelate != null) {
                	LOGGER.info("删除hash表中的登录信息[" + deleteInfoRelate.getLoginInfo() + "]");
                	this.userInfoService.deleteByKeys(deleteInfoRelate);
                }
                this.userInfoService.insert(loginInfoRelate);
                if (i > 0) {
                    result.setSuccess(true);
                } else {
                    LOGGER.error("保存用户手机验证信息时，修改t_user失败，用户id:"+phoneVerifyDo.getUserId()+"手机号码:"+phoneVerifyDo.getMobilePhone());
                    throw new BusinessException("保存手机认证信息失败");
                }
            } else {
                throw new BusinessException("保存手机认证信息失败");
            }
        } else {
            result.setErrorMessage("该手机已被其他用户绑定");
            LOGGER.info("该手机已被其他用户绑定.手机号码:"+phoneVerifyDo.getMobilePhone());
        }
        return result;
    }

    @Override public PhoneVerifyDo findPhoneVerify(Long userId) {

        return phoneVerifyDao.findPhoneVerify(userId);
    }

    @Override public int updatePhoneVerifyStatus(PhoneVerifyDo phoneVerifyDo) {

        return phoneVerifyDao.updatePhoneVerifyStatus(phoneVerifyDo);
    }

    @Override public PhoneVerifyDo findPhoneVerifyByPhone(String mobilePhone) {
        return phoneVerifyDao.findPhoneVerifyByPhone(mobilePhone);
    }

    @Override public int disablePhoneVerify(Long userId) {
        return phoneVerifyDao.disablePhoneVerify(userId);
    }

	@Override
	public IResult<PhoneVerifyDo> updateEmailVerify(Long userId, String email) {
		IResult<PhoneVerifyDo> result = new ResultSupport<PhoneVerifyDo>();
		// 将用户之前的邮箱废除
		this.phoneVerifyDao.disableEmailVerify(userId);
		// 保存新的邮箱到验证信息表中
		PhoneVerifyDo emailVerify = new PhoneVerifyDo();
		emailVerify.setUserId(userId);
		emailVerify.setMobilePhone(email);
		emailVerify.setStatus(1);
		emailVerify.setType(PhoneVerifyDo.ContactType.EMAIL);
		emailVerify.setSource(PhoneVerifyDo.SourceType.MOBILE);
		try {
			int i = this.phoneVerifyDao.savePhoneVerify(emailVerify);
			if (i > 0) {
				this.userComponent.updateUserEmail(userId, email);
				this.personComponent.updateEmailByUserId(userId, email);
			}
			if (i > 0) {
				result.setSuccess(true);
			} else {
				LOGGER.error("保存用户邮箱信息时，修改t_user失败，用户id:" + userId + "邮箱地址:" + email);
                throw new BusinessException("保存邮箱认证信息失败");
			}
		} catch (Exception e) {
			result.setErrorMessage("保存邮箱认证信息失败" + e.getMessage());
			throw new BusinessException("保存邮箱认证信息失败");
		}
		return result;
	}
}
