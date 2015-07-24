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

import com.hehenian.biz.common.loan.dataobject.LoanCreditRecDo;

public interface ILoanCreditRecDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanCreditRecDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanCreditRecDo> selectLoanCreditRec(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanCreditRecById(LoanCreditRecDo newLoanCreditRecDo);
	
	/**
	 * 新增
	 */
	public int addLoanCreditRec(LoanCreditRecDo newLoanCreditRecDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
