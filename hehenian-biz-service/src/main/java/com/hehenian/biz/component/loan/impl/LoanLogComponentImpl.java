package com.hehenian.biz.component.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.LoanLogDo;
import com.hehenian.biz.component.loan.ILoanLogComponent;
import com.hehenian.biz.dal.loan.ILoanLogDao;

@Component("loanLogComponent")
public class LoanLogComponentImpl implements ILoanLogComponent{

	@Autowired
	private ILoanLogDao loanLogDao;
	
	@Override
	public int addLoanLog(LoanLogDo loanLogDo) {
		return loanLogDao.addLoanLog(loanLogDo);
	}

	@Override
	public List<LoanLogDo> getLoanLogPage(Map<String, Object> param) {
		return loanLogDao.getLoanLogPage(param);
	}

	@Override
	public int updateLoanLog(LoanLogDo loanLogDo) {
		return loanLogDao.updateLoanLog(loanLogDo);
	}

	@Override
	public int countLoanLog(Map<String, Object> param) {
		return loanLogDao.countLoanLog(param);
	}

}
