/**
 * @auther liminglong
 * @date 2015年4月22日
 */
package com.hehenian.biz.common.loan;

import java.util.Map;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.dataobject.LoanDictDo;
import com.hehenian.biz.common.loan.dataobject.LoanDictDtlDo;

/**
 * @author liminglong
 *
 */
public interface ILoanDictDtlService {
	/**
	 * 得到数据字典明细列表
	 * @auther liminglmf
	 * @date 2015年4月24日
	 * @param param
	 * @param page
	 * @return
	 */
	PageDo<LoanDictDtlDo> getLoanDictDtlPage(Map<String,Object> param, PageDo<LoanDictDtlDo> page);

}
