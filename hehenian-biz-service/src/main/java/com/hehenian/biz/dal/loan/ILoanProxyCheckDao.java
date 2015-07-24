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

import com.hehenian.biz.common.loan.dataobject.LoanProxyCheckDo;

public interface ILoanProxyCheckDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanProxyCheckDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanProxyCheckDo> selectLoanProxyCheck(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanProxyCheckById(LoanProxyCheckDo newLoanProxyCheckDo);
	
	/**
	 * 新增
	 */
	public int addLoanProxyCheck(LoanProxyCheckDo newLoanProxyCheckDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
