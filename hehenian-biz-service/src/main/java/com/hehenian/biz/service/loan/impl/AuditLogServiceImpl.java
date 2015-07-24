package com.hehenian.biz.service.loan.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.loan.IAuditLogService;
import com.hehenian.biz.common.loan.dataobject.AuditLogDo;
import com.hehenian.biz.component.loan.IAuditLogComponent;

@Service("auditLogService")
public class AuditLogServiceImpl implements IAuditLogService{

	@Autowired
	private IAuditLogComponent auditLogComponent;
	
	@Override
	public AuditLogDo getAuditLogById(Long auditId) {
		 
		return auditLogComponent.getAuditLogById(auditId);
	}

	@Override
	public int addAuditLog(AuditLogDo auditLogDo) {
		return auditLogComponent.addAuditLog(auditLogDo);
	}

	@Override
	public List<AuditLogDo> queryAuditLogByLoanId(Long loanId) {
 		return auditLogComponent.queryAuditLogByLoanId(loanId);
	}

	@Override
	public AuditLogDo getOneAuditLogByLoanId(Long loanId) {
		return auditLogComponent.getOneAuditLogByLoanId(loanId);
	}

}
