package com.hehenian.biz.service.notify.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.notify.dataobject.MailNotifyDo;
import com.hehenian.biz.service.BaseTestCase;

public class MailNotifyServiceImplTest extends BaseTestCase {
	
	@Autowired
	private INotifyService mailNotifyService;
	
	@Test
	public void testSend(){
		MailNotifyDo msg = new MailNotifyDo();
		msg.setAsync(false);
		msg.setCcList("zhangyunhmf@hehenian.com");
		//JSONObject jobject = new JSONObject();
		//jobject.put("mail_content", "hello zhang this is mail content");
		//msg.setMessage(jobject);
		msg.setMessage("hello zhang this is mail content");
		msg.setMessageType("MAIL");
		msg.setRecievers("zhangyunhmf@hehenian.com");
		msg.setSendFlag("F");
		msg.setSubject("my first mail");
		msg.setMessageTemplate("mail_template1.ftl");
		mailNotifyService.send(msg);
	}

}
