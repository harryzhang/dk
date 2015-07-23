package com.hehenian.biz.common.loan;

import java.util.List;

import com.hehenian.biz.common.loan.dataobject.AuditLogDo;

public interface IAuditLogService {


	AuditLogDo getAuditLogById(Long auditId);
	
	int addAuditLog(AuditLogDo auditLogDo);
	
	List<AuditLogDo> queryAuditLogByLoanId(Long loanId);
	
	AuditLogDo getOneAuditLogByLoanId(Long loanId);
	
}
