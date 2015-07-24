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

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.loan.dataobject.LoanPersonCreditDo;

public interface ILoanPersonCreditDao {

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
	 * 更新用户授信状态
	 * @param userId
	 * @param newStatus
	 */
	public void updateCreditStatusByUser(@Param("userId")Long userId,@Param("status")String status);

}
