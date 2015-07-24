/**
 * @auther liminglmf
 * @date 2015年4月24日
 */
package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanProvidedDo;

/**
 * @author liminglmf
 *
 */
public interface ILoanProvidedComponent {
	
	List<LoanProvidedDo> getLoanProvidedPage(Map<String,Object> param);

	int addLoanProvidedDo(LoanProvidedDo loanProvidedDo);

	LoanProvidedDo getProdById(Long proId);

	int updateLoanProvidedDo(LoanProvidedDo loanProvidedDo);

	int deleteLoanProvidedDo(List<Long> idsList);

	int selectCountByProdId(Long prodId);
}
