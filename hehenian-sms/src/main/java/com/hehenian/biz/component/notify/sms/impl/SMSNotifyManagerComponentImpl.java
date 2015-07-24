package com.hehenian.biz.component.notify.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.notify.dataobject.SMSManagerDo;
import com.hehenian.biz.component.notify.sms.ISMSNotifyManagerComponent;
import com.hehenian.biz.component.notify.sms.dao.ISMSNotifyManagerDao;

@Component("smsNotifyManagerComponent")
public class SMSNotifyManagerComponentImpl implements ISMSNotifyManagerComponent {

	@Autowired
	private ISMSNotifyManagerDao smsNotifyManagerDao;
	
	public SMSManagerDo listSMSSupplierBySmsType(String smsType) {
		return smsNotifyManagerDao.listSMSSupplierBySmsType(smsType);
	}
	
	
	public int addSMSManager(SMSManagerDo smsManagerDo){
		return smsNotifyManagerDao.addSMSManager(smsManagerDo);
	}
	
	public int updateSMSManager(SMSManagerDo smsManagerDo){
		return smsNotifyManagerDao.updateSMSManager(smsManagerDo);
	}

}
