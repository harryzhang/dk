package com.hehenian.biz.component.notify.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.notify.dataobject.LoanNotifyDo;
import com.hehenian.biz.component.notify.sms.INotifyLoanComponent;
import com.hehenian.biz.component.notify.sms.dao.INotifyLoanDao;

@Component("notifyLoanComponent")
public class NotifyManagerComponentImpl implements INotifyLoanComponent {
	@Autowired
	private INotifyLoanDao loanDao;

	@Override
	public int addMessage(LoanNotifyDo msg) {
		return loanDao.addMessage(msg);
	}

	@Override
	public int updateMessage(LoanNotifyDo msg) {
		return loanDao.updateMessage(msg);
	}

	@Override
	public List<LoanNotifyDo> selectNotify(LoanNotifyDo queryNotifyDo) {
		return loanDao.selectNotify(queryNotifyDo);
	}

}
