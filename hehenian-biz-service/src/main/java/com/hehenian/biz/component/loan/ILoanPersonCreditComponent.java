/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.component.loan;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonCreditDo;

public interface ILoanPersonCreditComponent {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanPersonCreditDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanPersonCreditDo> selectLoanPersonCredit(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanPersonCreditById(LoanPersonCreditDo newLoanPersonCreditDo);
	
	/**
	 * 新增
	 */
	public int addLoanPersonCredit(LoanPersonCreditDo newLoanPersonCreditDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

	/**
	 * 获取补入的征信数据
	 * @param parameterMap
	 * @return
	 */
	public List<LoanPersonCreditDo> selectLoanPersonCreditWithDetail(
			Map<String, Object> parameterMap);

	public int addLoanPersonCredit(LoanPersonCreditDo newLoanPersonCreditDo,
			LoanDo loanDo);
	

}
