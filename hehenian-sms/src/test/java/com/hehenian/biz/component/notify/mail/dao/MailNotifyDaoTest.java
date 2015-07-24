package com.hehenian.biz.component.notify.mail.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.notify.dataobject.MailNotifyDo;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.util.JsonUtil;
import com.hehenian.biz.service.BaseTestCase;

public class MailNotifyDaoTest extends BaseTestCase {
	
	@Autowired
	private IMailNotifyDao mailNotifyDao;
	
	@Test
	public void testAddMessage() throws Exception{
		MailNotifyDo msg = new MailNotifyDo();
		msg.setAsync(true);
		msg.setCcList("cc_list");
		Map<String,String> messageMap = new HashMap<String,String>();
		messageMap.put("he","mail content");
		messageMap.put("he2","mail content");
		msg.setMessage(JsonUtil.toString(messageMap));
		msg.setMessageTemplate("template");
		msg.setRecievers("recievers");
		msg.setMessageType(MailNotifyDo.MAIL);
		msg.setSender("sender");
		msg.setSendFlag("F");
		msg.setSubject("subject");
		mailNotifyDao.addMessage(msg);
	}
	
	@Test
	public void testListUnSendMessageList(){
		List<NotifyDo> unSendMessageList = mailNotifyDao.listUnSendMessageList();
		System.out.println(unSendMessageList.size());
	}
	
	@Test
	public void testUpdate(){
		MailNotifyDo msg = (MailNotifyDo) mailNotifyDao.getMessageById(35);
		msg.setMessage("test update2");
		msg.setMessageTemplate("template1");
		mailNotifyDao.updateMessage(msg);
	}

	@Test
	public void testUpdateSendFlag(){
		MailNotifyDo msg = (MailNotifyDo) mailNotifyDao.getMessageById(35);
		mailNotifyDao.updateMessageFlag("T", msg.getMessageId());
	}

}

