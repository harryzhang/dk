package com.hehenian.biz.service.dqlc;

import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.dqlc.IDqlcService;
import com.hehenian.biz.common.identifycode.IIdentifyCodeService;
import com.hehenian.biz.common.util.Constants;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * User: liuwtmf
 * Date: 2014/12/30
 * Time: 17:05
 */
@Service("dqlcService")
public class DqlcServiceImpl implements IDqlcService{

    @Autowired
    private IIdentifyCodeService identifyCodeService;
    @Autowired
    private IUserService userService;

    @Override public boolean checkPhoneVerifyCode(String phone,String code) {
        boolean b = identifyCodeService.checkIdentifyCode(phone, code);
        return b;
    }

    @Override public Map<String,Boolean> checkPhoneVerifyCodeAndPwd(Long userId, String pwd, String phone,String code) {
        Map<String,Boolean> map = new HashMap<String, Boolean>();
        boolean b = identifyCodeService.checkIdentifyCode(phone, code);
        map.put("phone",b);
        AccountUserDo userDo = userService.getById(userId);
        String pwdMd5 = DigestUtils.md5Hex(pwd + Constants.PASS_KEY);
        if (pwdMd5.equals(userDo.getPassword())){
            //密码正确
            map.put("pwd",true);
        }else {
            //密码不正确
            map.put("pwd",false);
        }
        return map;
    }
}
