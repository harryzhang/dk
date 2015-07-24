package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanDictDo;

public interface IManagerLoanDictDao {

	public List<LoanDictDo> queryList(Map<String,Object> searchItem );
	
	public List<LoanDictDo> queryListPage(Map<String,Object> searchItem );
	
	LoanDictDo getDictById(long dictId);

	int addLoanDictDo(LoanDictDo loanDictDo);

	int updateDict(LoanDictDo loanDictDo);

	int deleteDict(long dictId);
}
