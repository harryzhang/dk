package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanDictDtlDo;

public interface IManagerLoanDictDtlDao {

	public List<LoanDictDtlDo> queryList(Map<String,Object> searchItem );
	
	public List<LoanDictDtlDo> queryListPage(Map<String,Object> searchItem );
}
