/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.component.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;
import com.hehenian.biz.component.loan.ILoanRepaymentComponent;
import com.hehenian.biz.dal.loan.ILoanRepaymentDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("loanRepaymentComponent")
public class LoanRepaymentComponentImpl implements ILoanRepaymentComponent {

	@Autowired
    private ILoanRepaymentDao  loanRepaymentDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanRepaymentDo getById(Long repaymentId){
	  return loanRepaymentDao.getById(repaymentId);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanRepaymentDo> selectLoanRepayment(Map<String,Object> parameterMap){
		return loanRepaymentDao.selectLoanRepayment(parameterMap);
	}
	
	/**
	 * 逾期、待还款、已还款、已还清 列表查询
	 * @param parameterMap
	 * @return
	 */
	public List<Map<String,Object>> listRepayment(Map<String,Object> parameterMap){
		return loanRepaymentDao.listRepayment(parameterMap);
	}
	/**
	 * 逾期、待还款、已还款、已还清 列表查询
	 * @param parameterMap
	 * @return
	 */
	public List<Map<String,Object>> listRepaymentPage(Map<String,Object> parameterMap){
		return loanRepaymentDao.listRepaymentPage(parameterMap);
	}
	
	public List<Map<String,Object>> getByLoanStatus (Map<String,Object> parameterMap){
		return loanRepaymentDao.getByLoanStatus(parameterMap);
	}
	
	public Map<String,Object> getRepayDetail(Map<String,Object> parameterMap){
		return loanRepaymentDao.getRepayDetail(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanRepaymentById(LoanRepaymentDo newLoanRepaymentDo){
		int result = loanRepaymentDao.updateLoanRepaymentById(newLoanRepaymentDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 新增
	 */
	public int addLoanRepayment(LoanRepaymentDo newLoanRepaymentDo){
		return loanRepaymentDao.addLoanRepayment(newLoanRepaymentDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(Long repaymentId){
		return loanRepaymentDao.deleteById(repaymentId);
	}

	@Override
	public List<LoanRepaymentDo> queryRepaymentByLoanId(Long loanId) {
		 
		return loanRepaymentDao.queryRepaymentByLoanId(loanId);
	}

}
