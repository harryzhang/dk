/**
 * @auther liminglmf
 * @date 2015年4月24日
 */
package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanDictDtlDo;

/**
 * @author liminglmf
 *
 */
public interface ILoanDictDtlComponent {
	
	List<LoanDictDtlDo> getLoanDictDtlPage(Map<String,Object> param);
}
