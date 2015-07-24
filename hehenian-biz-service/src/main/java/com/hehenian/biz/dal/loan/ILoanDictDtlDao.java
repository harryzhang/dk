package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanDictDtlDo;
/**
 * 
 * @author liminglong
 *
 */
public interface ILoanDictDtlDao {
	
	/**
	 * 
	 * @auther liminglong
	 * @date 2015年4月22日
	 * @param searchItem
	 * @return
	 */
	public List<LoanDictDtlDo> queryLoanDictDtlList(Map searchItem );
	
	
	/**
	 * @auther liminglong
	 * @date 2015年4月22日
	 * @param loanDictDtlDo
	 * @return
	 */
	int addLoanDictDtlDo(LoanDictDtlDo loanDictDtlDo);
	
}
