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

import com.hehenian.biz.common.loan.dataobject.LoanPersonCheckDo;


public interface ILoanPersonCheckService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanPersonCheckDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanPersonCheckDo> selectLoanPersonCheck(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanPersonCheckById(LoanPersonCheckDo newLoanPersonCheckDo);
	
	/**
	 * 新增
	 */
	public int addLoanPersonCheck(LoanPersonCheckDo newLoanPersonCheckDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);
}
