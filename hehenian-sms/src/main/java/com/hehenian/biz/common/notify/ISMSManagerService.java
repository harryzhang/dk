package com.hehenian.biz.common.notify;

import com.hehenian.biz.common.notify.dataobject.SMSManagerDo;

/**
 * 短信营运商的管理
 * @author zhangyunhmf
 *
 */
public interface ISMSManagerService {
	


	/**
	 * 根据短信类别获取短信供应商，注册的账号密码，调用的URL
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



}
