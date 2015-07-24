/**
 * @auther liminglmf
 * @date 2015年4月24日
 */
package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanProductDo;

/**
 * @author liminglmf
 *
 */
public interface ILoanProductComponent {
	
	List<LoanProductDo> getLoanProductPage(Map<String,Object> param);

	int addLoanProductDo(LoanProductDo loanProductDo);

	LoanProductDo getProdById(Long proId);

	int updateLoanProductDo(LoanProductDo loanProductDo);

	int deleteLoanProductDo(List<Long> idsList);
}
