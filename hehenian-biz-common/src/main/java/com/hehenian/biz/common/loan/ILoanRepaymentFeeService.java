/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.common.loan;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;


public interface ILoanRepaymentFeeService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanRepaymentFeeDo getById(Long feeId);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanRepaymentFeeDo> selectLoanRepaymentFee(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanRepaymentFeeById(LoanRepaymentFeeDo newLoanRepaymentFeeDo);
	
	/**
	 * 新增
	 */
	public int addLoanRepaymentFee(LoanRepaymentFeeDo newLoanRepaymentFeeDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Long feeId);
}
