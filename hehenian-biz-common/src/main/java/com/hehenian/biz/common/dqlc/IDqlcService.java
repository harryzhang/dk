package com.hehenian.biz.common.dqlc;

import java.util.Map;

public interface IDqlcService {

    /**
     * 校验手机验证码
     * @param phone
     * @param code
     * @return
     */
    boolean checkPhoneVerifyCode(String phone,String code);

    /**
     * 校验手机验证吗和登录密码
     * @param userId
     * @param pwd
     * @param phone
     * @param code
     * @return
     */
    Map<String,Boolean> checkPhoneVerifyCodeAndPwd(Long userId,String pwd,String phone,String code);

}
