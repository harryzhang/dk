/**
 * @auther liminglong
 * @date 2015年4月22日
 */
package com.hehenian.biz.common.loan;

import java.util.Map;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.dataobject.LoanDictDo;

/**
 * @author liminglong
 *
 */
public interface ILoanDictService {
	
	/**
	 * 得到数据字典列表
	 * @auther liminglmf
	 * @date 2015年4月24日
	 * @param param
	 * @param page
	 * @return
	 */
	PageDo<LoanDictDo> getLoanDictPage(Map<String,Object> param, PageDo<LoanDictDo> page);
	
	LoanDictDo getDictById(long dictId);

	int addDict(LoanDictDo loanDictDo);

	int updateDict(LoanDictDo loanDictDo);

	int deleteDict(long dictId);
	
}
