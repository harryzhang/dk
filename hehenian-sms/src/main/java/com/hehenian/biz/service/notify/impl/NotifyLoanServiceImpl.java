package com.hehenian.biz.service.notify.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.hehenian.biz.common.notify.INotifyLoanService;
import com.hehenian.biz.common.notify.dataobject.LoanNotifyDo;
import com.hehenian.biz.component.notify.sms.INotifyLoanComponent;

/**
 * @Description 融资端站内通知
 * @author huangzl QQ: 272950754
 * @date 2015年7月1日 下午5:55:49
 * @Project hehenian-sms
 * @Package com.hehenian.biz.service.notify.impl
 * @File NoticeServiceImpl.java
 */
public class NotifyLoanServiceImpl implements INotifyLoanService {

//	protected static final Logger logger = Logger.getLogger(INotifyLoanService.class);

	private INotifyLoanComponent notifyManager;

	public INotifyLoanComponent getNotifyManager() {
		return notifyManager;
	}

	public void setNotifyManager(INotifyLoanComponent notifyManager) {
		this.notifyManager = notifyManager;
	}

	@Override
	public int addMessage(LoanNotifyDo msg) {
		return notifyManager.addMessage(msg);
	}

	@Override
	public int updateMessage(LoanNotifyDo msg) {
		return notifyManager.updateMessage(msg);
	}

	@Override
	public List<LoanNotifyDo> selectNotify(LoanNotifyDo queryNotifyDo) {
		return notifyManager.selectNotify(queryNotifyDo);
	}

}
