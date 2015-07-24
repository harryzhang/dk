/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.dal.loan;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;

public interface ILoanRepaymentDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanRepaymentDo getById(Long repaymentId);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanRepaymentDo> selectLoanRepayment(Map<String,Object> parameterMap);
	
	/**
	 * 逾期、待还款、已还款、已还清 列表查询
	 * @param parameterMap
	 * @return
	 */
	public List<Map<String,Object>> listRepayment(Map<String,Object> parameterMap);
	public List<Map<String,Object>> listRepaymentPage(Map<String,Object> parameterMap);
	
	public List<Map<String,Object>> getByLoanStatus (Map<String,Object> parameterMap);
	
	public Map<String,Object> getRepayDetail(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanRepaymentById(LoanRepaymentDo newLoanRepaymentDo);
	
	/**
	 * 新增
	 */
	public int addLoanRepayment(LoanRepaymentDo newLoanRepaymentDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Long repaymentId);
	
	/**
	 * 根据loanid 查询 还款计划
	 * @author wangt  
	 *
	 * 2015年4月2日 下午9:36:08 
	 * @param loanId
	 * @return
	 */
	public List<LoanRepaymentDo> queryRepaymentByLoanId(Long loanId);

}
