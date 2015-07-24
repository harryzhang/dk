/**
 * @auther liminglmf
 * @date 2015年4月24日
 */
package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanDictDo;

/**
 * @author liminglmf
 *
 */
public interface ILoanDictComponent {
	
	List<LoanDictDo> getLoanDictPage(Map<String,Object> param);

	LoanDictDo getDictById(long dictId);

	int addDict(LoanDictDo loanDictDo);

	int updateDict(LoanDictDo loanDictDo);

	int deleteDict(long dictId);

}
