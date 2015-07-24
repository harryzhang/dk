/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.service.loan.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.loan.ILoanRepaymentFeeService;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;
import com.hehenian.biz.component.loan.ILoanRepaymentFeeComponent;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("loanRepaymentFeeService")
public class LoanRepaymentFeeServiceImpl implements ILoanRepaymentFeeService {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private ILoanRepaymentFeeComponent  loanRepaymentFeeComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanRepaymentFeeDo getById(Long feeId){
	  return loanRepaymentFeeComponent.getById(feeId);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanRepaymentFeeDo> selectLoanRepaymentFee(Map<String,Object> parameterMap){
		return loanRepaymentFeeComponent.selectLoanRepaymentFee(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanRepaymentFeeById(LoanRepaymentFeeDo newLoanRepaymentFeeDo){
		return loanRepaymentFeeComponent.updateLoanRepaymentFeeById(newLoanRepaymentFeeDo);
	}
	
	/**
	 * 新增
	 */
	public int addLoanRepaymentFee(LoanRepaymentFeeDo newLoanRepaymentFeeDo){
		return loanRepaymentFeeComponent.addLoanRepaymentFee(newLoanRepaymentFeeDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(Long feeId){
		return loanRepaymentFeeComponent.deleteById(feeId);
	}

}
