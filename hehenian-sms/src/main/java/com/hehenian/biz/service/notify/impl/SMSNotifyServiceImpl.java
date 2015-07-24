/**
 *  @ Project : p2pt notify
 *  @ File Name : ShortMessageNotify.java
 *  @ Date : 2014/8/20
 *  @ Author : harry.zhang
 */
package com.hehenian.biz.service.notify.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.notify.dataobject.SMSManagerDo;
import com.hehenian.biz.common.notify.dataobject.SMSNotifyDo;
import com.hehenian.biz.common.util.JsonUtil;
import com.hehenian.biz.component.notify.sms.ISMSNotifyManagerComponent;
import com.hehenian.biz.facade.notify.ISMSSender;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 短信发送 ， 包可见，不让外部直接调用
 * 
 * @author zhangyunhmf
 * 
 */
@Component("smsNotifyService")
class SMSNotifyServiceImpl extends NotifyServiceImpl {

    private final static String            DEFAULT_TEMPLATE = "sms_template_mobile_check.ftl";

    @Autowired
    private ISMSNotifyManagerComponent     smsNotifyComponent;
    @Autowired
    private FreeMarkerConfigurationFactory freeMarkerConfigurer;
    @Autowired
    private TaskExecutor                   taskExecutor;

    public boolean send(final NotifyDo message) {
        if (message.isAsync()) {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    sendSMS(message);
                }
            });
            return true;
        } else {
            // 发送
            return sendSMS(message);
        }
    }

    private boolean sendSMS(NotifyDo message) {

        SMSNotifyDo msg = null;
        if (message instanceof SMSNotifyDo) {
            msg = (SMSNotifyDo) message;
        } else {
            logger.error("消息类型不正确， 期望是短信实际接收到邮件或其他消息：" + message.getMessage());
            return false;
        }
        // 根据短信类型获取短信供应商，短信发送账号和密码
//        SMSManagerDo smsManagerDo = smsNotifyComponent.listSMSSupplierBySmsType(msg.getSmsType() == null ? "default"
//                : msg.getSmsType());
//
//        if (null == smsManagerDo) {
//            logger.error("没有正确配置短信供应商，请检查 smsnotifyManager的数据");
//            return false;
//        }

        String messageTemplate = message.getMessageTemplate();
        String htmlText = null;
        if (null == messageTemplate || "".equals(messageTemplate)) {
            messageTemplate = DEFAULT_TEMPLATE;
        }

        try {
            Template tpl = freeMarkerConfigurer.createConfiguration().getTemplate(messageTemplate);
            Map messageMap = (Map) JsonUtil.json2Bean(message.getMessage(), Map.class);
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, messageMap);
        } catch (IOException e) {
            logger.error(e);
            return false;
        } catch (TemplateException e) {
            logger.error(e);
            return false;
        }
        if (null == htmlText) {
            htmlText = message.getMessage();
        }

        try {
            ISMSSender smsSender = SMSSenderFactory.getSMSSender(msg.getSmsType());
            long resVal = smsSender.send(null, null, null,message.getRecievers(), htmlText, null, null, null, null);

            if (-1 != resVal) {
                ((SMSNotifyDo) message).setSendId("" + resVal);
                return true;
            }
        } catch (Throwable t) {
            logger.error(t);
        }
        return false;
    }

}
