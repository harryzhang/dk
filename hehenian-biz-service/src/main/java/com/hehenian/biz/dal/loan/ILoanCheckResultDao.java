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

import com.hehenian.biz.common.loan.LoanCheckResultDo;

public interface ILoanCheckResultDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanCheckResultDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanCheckResultDo> selectLoanCheckResult(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanCheckResultById(LoanCheckResultDo newLoanCheckResultDo);
	
	/**
	 * 新增
	 */
	public int addLoanCheckResult(LoanCheckResultDo newLoanCheckResultDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
