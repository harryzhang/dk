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

import com.hehenian.biz.common.loan.dataobject.LoanCheckedDo;
import com.hehenian.biz.common.loan.dataobject.LoanDo;

public interface ILoanCheckedDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanCheckedDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanCheckedDo> selectLoanChecked(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanCheckedById(LoanCheckedDo newLoanCheckedDo);
	
	/**
	 * 新增
	 */
	public int addLoanChecked(LoanCheckedDo newLoanCheckedDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

	public List<LoanCheckedDo> getByLoanIdAndChecked(Map<String, Object> param);

}
