package com.hehenian.biz.service.notify.impl;

import org.springframework.stereotype.Component;

import com.hehenian.biz.common.notify.ISMSManagerService;
import com.hehenian.biz.common.notify.dataobject.SMSManagerDo;
import com.hehenian.biz.component.notify.sms.ISMSNotifyManagerComponent;

@Component("smsManagerService")
public class SMSManagerService implements ISMSManagerService {
	
	private ISMSNotifyManagerComponent smsNotifyManagerComponent;

	/**
	 * 根据短信类别获取短信供应商，注册的账号密码，调用的URL
	 * @param smsType
	 * @return
	 */
	public SMSManagerDo listSMSSupplierBySmsType(String smsType){
		return smsNotifyManagerComponent.listSMSSupplierBySmsType(smsType);
	}

	/**
	 * 新增短信供应商
	 * @param smsManagerDo
	 * @return
	 */
	public int addSMSManager(SMSManagerDo smsManagerDo){
		return smsNotifyManagerComponent.addSMSManager(smsManagerDo);
	}

	/**
	 * 修改短信供应商
	 * @param smsManagerDo
	 * @return
	 */
	public int updateSMSManager(SMSManagerDo smsManagerDo){
		return smsNotifyManagerComponent.updateSMSManager(smsManagerDo);
	}



}
