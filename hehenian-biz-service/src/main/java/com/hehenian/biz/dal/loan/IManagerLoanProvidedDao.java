package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.loan.dataobject.LoanProvidedDo;

public interface IManagerLoanProvidedDao {

	public List<LoanProvidedDo> queryLoanProvidedList(Map<String,Object> searchItem );

	public int addLoanProvidedDo(LoanProvidedDo loanProvidedDo);

	public LoanProvidedDo getProdById(Long id);

	public int updateProv(LoanProvidedDo loanProvidedDo);
	
	
	public int deleteProv(@Param("ids")List<Long> ids);

	public int queryLoanProvidedCount(Map<String,Object> searchItem);
}
