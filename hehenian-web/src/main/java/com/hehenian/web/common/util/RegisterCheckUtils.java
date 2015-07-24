package com.hehenian.web.common.util;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;

/**
 * User: liuwtmf
 * Date: 2014/11/25
 * Time: 16:09
 */
public class RegisterCheckUtils {
    public static IResult checkUserName(String userName){
        IResult result = new ResultSupport();
        result.setSuccess(true);
        return result;
    }
    public static IResult checkPhone(String phone){
        IResult result = new ResultSupport();
        result.setSuccess(true);
        return result;
    }
    public static IResult checkEmail(String email){
        IResult result = new ResultSupport();
        result.setSuccess(true);
        return result;
    }
    public static IResult checkPwd(String pwd,String pwd1){
        IResult result = new ResultSupport();
        result.setSuccess(true);
        return result;
    }
}
