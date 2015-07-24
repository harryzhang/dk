package com.hehenian.biz.component.notify.sms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.notify.dataobject.SMSNotifyDo;
import com.hehenian.biz.common.util.JsonUtil;
import com.hehenian.biz.service.BaseTestCase;

public class ISMSDaoTest extends BaseTestCase {
	@Autowired
	private ISMSDao smsDao;
	
	@Test
	public void testAddMessage()throws Exception{
		SMSNotifyDo msg = new SMSNotifyDo();
		msg.setAsync(true);
		Map<String,String> messageMap = new HashMap<String,String>();
		messageMap.put("he","mail content");
		messageMap.put("he2","mail content");;
		msg.setMessage(JsonUtil.toString(messageMap));
		msg.setMessageTemplate("template");
		msg.setRecievers("recievers");
		msg.setMessageType(SMSNotifyDo.MAIL);
		msg.setSender("sender");
		msg.setSendFlag("F");
		msg.setSmsType("smsType");
		smsDao.addMessage(msg);
	}
	
	@Test
	public void testListUnSendMessageList(){
		List<NotifyDo> unSendMessageList = smsDao.listUnSendMessageList();
		System.out.println(unSendMessageList.size());
	}
	
	@Test
	public void updateMessage(){
		SMSNotifyDo msg = (SMSNotifyDo) smsDao.getMessageById(36);
		msg.setMessage("test 19");
		msg.setMessageTemplate("sms template1");
		smsDao.updateMessage(msg);
	}

	@Test
	public void testUpdateSendFlag(){
		SMSNotifyDo msg = (SMSNotifyDo) smsDao.getMessageById(36);
		smsDao.updateMessageFlag("T", msg.getMessageId());
	}

}
