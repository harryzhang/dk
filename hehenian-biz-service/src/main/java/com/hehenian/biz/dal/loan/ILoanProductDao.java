package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanProductDo;

public interface ILoanProductDao {

	public List<LoanProductDo> queryLoanProductList(Map searchItem );
	
	
	/**
	 * @auther liminglong
	 * @date 2015年4月22日
	 * @param loanProductDo
	 * @return
	 */
	int addLoanProductDo(LoanProductDo loanProductDo);
	
}
