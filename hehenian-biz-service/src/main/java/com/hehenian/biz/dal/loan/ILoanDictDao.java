package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanDictDo;
/**
 * 
 * @author liminglong
 *
 */
public interface ILoanDictDao {
	
	/**
	 * 
	 * @auther liminglong
	 * @date 2015年4月22日
	 * @param searchItem
	 * @return
	 */
	public List<LoanDictDo> queryLoanDictList(Map searchItem );
	
	
	/**
	 * @auther liminglong
	 * @date 2015年4月22日
	 * @param loanDictDo
	 * @return
	 */
	int addLoanDictDo(LoanDictDo loanDictDo);
	
}
