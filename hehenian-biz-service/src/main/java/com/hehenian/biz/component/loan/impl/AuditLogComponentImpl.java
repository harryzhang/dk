package com.hehenian.biz.component.loan.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.AuditLogDo;
import com.hehenian.biz.component.loan.IAuditLogComponent;
import com.hehenian.biz.dal.loan.IAuditLogDao;

@Component("auditLogComponent")
public class AuditLogComponentImpl implements IAuditLogComponent{

	@Autowired
	private IAuditLogDao auditLogDao;
	
	@Override
	public AuditLogDo getAuditLogById(Long auditId) {
		 
		return auditLogDao.getAuditLogById(auditId);
	}

	@Override
	public int addAuditLog(AuditLogDo auditLogDo) {
		return auditLogDao.addAuditLog(auditLogDo);
	}

	@Override
	public List<AuditLogDo> queryAuditLogByLoanId(Long loanId) {
		return auditLogDao.queryAuditLogByLoanId(loanId);
	}

	@Override
	public AuditLogDo getOneAuditLogByLoanId(Long loanId) {
		return auditLogDao.getOneAuditLogByLoanId(loanId);
	}

}
