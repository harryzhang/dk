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
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;
import com.hehenian.biz.component.loan.ILoanRepaymentFeeComponent;
import com.hehenian.biz.dal.loan.ILoanRepaymentFeeDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("loanRepaymentFeeComponent")
public class LoanRepaymentFeeComponentImpl implements ILoanRepaymentFeeComponent {

	@Autowired
    private ILoanRepaymentFeeDao  loanRepaymentFeeDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanRepaymentFeeDo getById(Long feeId){
	  return loanRepaymentFeeDao.getById(feeId);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanRepaymentFeeDo> selectLoanRepaymentFee(Map<String,Object> parameterMap){
		return loanRepaymentFeeDao.selectLoanRepaymentFee(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanRepaymentFeeById(LoanRepaymentFeeDo newLoanRepaymentFeeDo){
		int result = loanRepaymentFeeDao.updateLoanRepaymentFeeById(newLoanRepaymentFeeDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 新增
	 */
	public int addLoanRepaymentFee(LoanRepaymentFeeDo newLoanRepaymentFeeDo){
		return loanRepaymentFeeDao.addLoanRepaymentFee(newLoanRepaymentFeeDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(Long feeId){
		return loanRepaymentFeeDao.deleteById(feeId);
	}

}
