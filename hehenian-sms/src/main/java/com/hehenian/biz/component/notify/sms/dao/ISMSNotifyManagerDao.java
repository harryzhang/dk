package com.hehenian.biz.component.notify.sms.dao;

import com.hehenian.biz.common.notify.dataobject.SMSManagerDo;

public interface ISMSNotifyManagerDao {
	
	/**
	 * 根据短信类型查找一个有效的短信供应商
	 * @param smsType
	 * @return
	 */
	public SMSManagerDo listSMSSupplierBySmsType(String smsType);
	
	/**
	 * 新增短信供应商
	 * @param smsManagerDo
	 * @return
	 */
	public int addSMSManager(SMSManagerDo smsManagerDo);
	
	/**
	 * 修改短信供应商
	 * @param smsManagerDo
	 * @return
	 */
	public int updateSMSManager(SMSManagerDo smsManagerDo);
	
	/**
	 * 根据ID 删除对象
	 * @param id
	 */
	public void deleteById(int id);
	
	/**
	 * 根据ID 查询对象
	 * @param id
	 * @return
	 */
	public SMSManagerDo getById(int id);

}
