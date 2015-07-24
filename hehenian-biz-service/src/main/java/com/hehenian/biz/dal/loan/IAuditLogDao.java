package com.hehenian.biz.dal.loan;

import java.util.List;

import com.hehenian.biz.common.loan.dataobject.AuditLogDo;

/**
 * 
 * @author wangt
 *
 */
public interface IAuditLogDao {

	AuditLogDo getAuditLogById(Long auditId);
	
	int addAuditLog(AuditLogDo auditLogDo);
	
	List<AuditLogDo> queryAuditLogByLoanId(Long loanId);
	
	AuditLogDo getOneAuditLogByLoanId(Long loanId);
	
	
}
