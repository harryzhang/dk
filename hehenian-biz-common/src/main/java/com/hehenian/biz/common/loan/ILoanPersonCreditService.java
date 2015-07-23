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

import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonCreditDo;


public interface ILoanPersonCreditService{

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
	 * 新增
	 */
	public int addLoanPersonCreditWithLoan(LoanPersonCreditDo newLoanPersonCreditDo,LoanDo loanDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

	/**
	 * 根据小区和用户ID取排名
	 * @param cid
	 * @param userId
	 * @return
	 */
	public Long getSortedByMember(Long cid, Long userId);

	/**
	 * 保存用户的信用额度到Redis的有序集合里
	 * @param cid
	 * @param userId
	 * @param creditAmt
	 */
	public void saveCreditToSet(Long cid, Long userId,
			double creditAmt);

	/**
	 * 获取补入的征信数据
	 * @param parameterMap
	 * @return
	 */
	public List<LoanPersonCreditDo> selectLoanPersonCreditWithDetail(
			Map<String, Object> parameterMap);
	
}
