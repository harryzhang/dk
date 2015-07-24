package com.hehenian.biz.component.notify.manager.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hehenian.biz.common.notify.dataobject.NotifyManagerDo;
import com.hehenian.biz.service.BaseTestCase;

public class INotifyManagerDaoTest extends BaseTestCase {
	
	@Autowired
	private INotifyManagerDao notifyManagerDao;
	

	
	@Test
	public void testAddMailNotifyManager(){
		NotifyManagerDo notifyManagerDo = new NotifyManagerDo();
		notifyManagerDo.setMessageType("MAIL");
		notifyManagerDo.setReDo(3);
		notifyManagerDo.setScheduleTime("2014-08-27");
		notifyManagerDo.setSend(true);
		notifyManagerDo.setValidate("T");
		notifyManagerDao.addNotifyManager(notifyManagerDo);		
	}
	
	@Test
	public void testAddSMSNotifyManager(){
		NotifyManagerDo notifyManagerDo = new NotifyManagerDo();
		notifyManagerDo.setMessageType("SMS");
		notifyManagerDo.setReDo(3);
		notifyManagerDo.setScheduleTime("2014-08-27");
		notifyManagerDo.setSend(true);
		notifyManagerDo.setValidate("T");
		notifyManagerDao.addNotifyManager(notifyManagerDo);		
	}
	
	@Test
	public void TestUpdateNotifyManager(){
		
		NotifyManagerDo  notifyManagerDo = notifyManagerDao.getNotifyManagerById(2);
		notifyManagerDo.setMessageType("MAIL");
		notifyManagerDo.setReDo(5);
		notifyManagerDo.setScheduleTime("2014-08-27");
		notifyManagerDo.setSend(false);
		notifyManagerDo.setValidate("T");
		notifyManagerDao.updateNotifyManager(notifyManagerDo);		
	}
	
	@Test
	public void TestDeleteById(){
		notifyManagerDao.deleteById(2);
	}
	
	@Test
	public void testListNotifyManager(){
		List<NotifyManagerDo> l = notifyManagerDao.listNotifyManager();
		Assert.notEmpty(l);
	}
	
	@Test
	public void listNotifyManagerByNotifyType(){
		String notifyType = "MAIL";
		NotifyManagerDo nmd = notifyManagerDao.listNotifyManagerByNotifyType(notifyType);
		Assert.notNull(nmd);
	}



}
