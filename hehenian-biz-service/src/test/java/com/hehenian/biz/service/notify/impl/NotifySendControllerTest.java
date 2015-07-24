package com.hehenian.biz.service.notify.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.notify.dataobject.MailNotifyDo;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.notify.dataobject.SMSNotifyDo;
import com.hehenian.biz.component.notify.INotifyComponent;
import com.hehenian.biz.service.BaseTestCase;

public class NotifySendControllerTest extends BaseTestCase {

    @Autowired
    private INotifyService   notifySendController;
    @Autowired
    private INotifyComponent mailNotifyComponent;

    @Test
    public void testSendMail() {
        MailNotifyDo notifyDo = new MailNotifyDo();
        notifyDo.setAsync(false);
        notifyDo.setCcList("418403299@qq.com");
        notifyDo.setSender("zhangyunhmf@hehenian.com");
        notifyDo.setSimpleMessage("test send controller");
        notifyDo.setMessageType(MailNotifyDo.MAIL);
        notifyDo.setSendFlag("F");
        notifyDo.setRecievers("zhangyunhmf@hehenian.com");
        notifyDo.setSubject("this is test mail");

        boolean success = notifySendController.send(notifyDo);

        Assert.isTrue(success);
    }

    @Test
    public void testUnsendMail() {

        List<NotifyDo> notifyList = mailNotifyComponent.listUnSendMessageList();
        for (NotifyDo notifyDo : notifyList) {
            boolean success = notifySendController.send(notifyDo);
        }

        // Assert.isTrue(result.isSuccess());
    }

    /**
     * 测试短信, 这个测试用的是优讯通,根据 smsType类型来选择短信供应商
     */
    @Test
    public void testSendSMS() {
        SMSNotifyDo notifyDo = new SMSNotifyDo();
        notifyDo.setAsync(false);
        // notifyDo.setSender("13714621908");
        notifyDo.setSimpleMessage("test 短信");
        notifyDo.setMessageType(MailNotifyDo.SMS);
        notifyDo.setSendFlag("F");
        // notifyDo.setRecievers("13798538071");
        notifyDo.setRecievers("13692177359");
        notifyDo.setSmsType("youxuntong"); // 确定是优讯通供应商

        boolean success = notifySendController.send(notifyDo);

        Assert.isTrue(success);
    }

    /**
     * 测试短信, 这个测试用的是深圳彩付宝短信平台, 根据 smsType类型来选择短信供应商
     */
    @Test
    public void testSendSMSOld() {
        SMSNotifyDo notifyDo = new SMSNotifyDo();
        notifyDo.setAsync(false);
        // notifyDo.setSender("13714621908");
        notifyDo.setSimpleMessage("test 短信");
        notifyDo.setMessageType(MailNotifyDo.SMS);
        notifyDo.setSendFlag("F");
        // notifyDo.setRecievers("13798538071");
        notifyDo.setRecievers("13692177359");
        notifyDo.setSmsType("default"); // 确定是优讯通供应商

        boolean success = notifySendController.send(notifyDo);

        Assert.isTrue(success);
    }

}
