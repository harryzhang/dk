package com.hehenian.web.view.common;

import com.hehenian.biz.common.identifycode.IIdentifyCodeService;
import com.hehenian.web.base.action.BaseAction;
import com.hehenian.web.common.util.ServletUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * User: liuwtmf
 * Date: 2014/11/25
 * Time: 15:58
 */
@Scope("prototype")
@Component("newCommonAction")
public class CommonAction extends BaseAction{
    private static final Logger LOGGER = Logger.getLogger(CommonAction.class);
    @Autowired
    private IIdentifyCodeService identifyCodeService;

    public String sendPhoneVirifyCode() {
        String mobile = request("mobile");
        String s = identifyCodeService.sendIdentifyCode(mobile);
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotBlank(s)) {
            //发送成功
            LOGGER.info("向手机号:"+mobile+"发送验证码成功,验证码为:"+s);
            jsonObject.put("ret","0");
        } else {
            //发送失败
            LOGGER.info("向手机号:"+mobile+"发送验证码失败");
            jsonObject.put("ret","1");
        }
        ServletUtils.writeJson(jsonObject.toString());
        return null;
    }
}









