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

import com.hehenian.biz.common.loan.dataobject.LoanUserDo;

public interface IManagerLoanUserComponent {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanUserDo getById(Long loanUserId);
	
	public LoanUserDo getBySourceUserId(String sourceUserId) ;
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanUserDo> selectLoanUser(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanUserById(LoanUserDo newLoanUserDo);
	
	/**
	 * 新增
	 */
	public int addLoanUser(LoanUserDo newLoanUserDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Long loanUserId);

}
