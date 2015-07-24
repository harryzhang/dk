package com.hehenian.biz.component.notify.sms.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hehenian.biz.common.notify.dataobject.SMSManagerDo;
import com.hehenian.biz.service.BaseTestCase;

public class ISMSNotifyManagerDaoTest extends BaseTestCase {
	
	@Autowired
	private ISMSNotifyManagerDao smsNotifyManagerDao;
	
	@Test
	public void testListSMSSupplierBySmsType(){
		String smsType = "cx";
		SMSManagerDo sd = smsNotifyManagerDao.listSMSSupplierBySmsType(smsType);
		Assert.notNull(sd);
	}
	
	@Test
	public void addSMSManager(){
		SMSManagerDo smsManagerDo = new SMSManagerDo();
		smsManagerDo.setOrgId("orgId");
		smsManagerDo.setPwd("123456");
		smsManagerDo.setSendMethod("sendMethod");
		smsManagerDo.setSmsSupplier("default");
		smsManagerDo.setSmsType("default");
		smsManagerDo.setUserId("szcfb");
		smsManagerDo.setValidate("T");
		smsManagerDo.setWsdlUrl("http://sms.gateway.i3km.com/sms_gateway.asmx?wsdl");
		smsManagerDo.setWsdlType("axis");
		smsManagerDo.setGroupSendMethod("groupMethod");
		smsManagerDo.setManagerHomeUrl("http://esm.chaotang.com/customer/szcfbcom/login.ashx");
		smsNotifyManagerDao.addSMSManager(smsManagerDo);
	}
	
	@Test
	public void addSMSManagerYouXunTong(){
		SMSManagerDo smsManagerDo = new SMSManagerDo();
		smsManagerDo.setOrgId("orgId");
		smsManagerDo.setPwd("311101");
		smsManagerDo.setSendMethod("sendMethod");
		smsManagerDo.setSmsSupplier("youxuntong");
		smsManagerDo.setSmsType("youxuntong");
		smsManagerDo.setUserId("311101");
		smsManagerDo.setValidate("T");
		smsManagerDo.setWsdlUrl("http://3tong.net/services/sms?wsdl");
		smsManagerDo.setWsdlType("axis");
		smsManagerDo.setGroupSendMethod("groupMethod");
		smsManagerDo.setManagerHomeUrl("http://3tong.net/services/sms");
		smsNotifyManagerDao.addSMSManager(smsManagerDo);
	}
	
	@Test
	public void updateSMSManager(){
		SMSManagerDo smsManagerDo = smsNotifyManagerDao.getById(3);
//		smsManagerDo.setOrgId("orgId1");
//		smsManagerDo.setPwd("pwd1");
//		smsManagerDo.setSendMethod("sendMethod1");
//		smsManagerDo.setSmsSupplier("supplier1");
//		smsManagerDo.setSmsType("cx");
//		smsManagerDo.setUserId("test11");
//		smsManagerDo.setValidate("F");
//		smsManagerDo.setWsdlUrl("http://www.wsdl_urlA");
//		smsManagerDo.setWsdlType("axisA");
//		smsManagerDo.setGroupSendMethod("groupMethodA");
//		smsManagerDo.setManagerHomeUrl("http://localhost:8080A");
		
		//smsManagerDo.setSmsType("default");
		
		smsManagerDo.setSendMethod("submit");
		smsManagerDo.setGroupSendMethod("submit");
		
		smsNotifyManagerDao.updateSMSManager(smsManagerDo);
		
	}
	
	@Test
	public void deleteById(){
		smsNotifyManagerDao.deleteById(1);
	}
	
	@Test
	public void getById(){
		SMSManagerDo smsManagerDo = smsNotifyManagerDao.getById(1);
		Assert.notNull(smsManagerDo);
	}


}
