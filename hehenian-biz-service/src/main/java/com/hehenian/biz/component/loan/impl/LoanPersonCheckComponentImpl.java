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
import com.hehenian.biz.common.loan.dataobject.LoanPersonCheckDo;
import com.hehenian.biz.component.loan.ILoanPersonCheckComponent;
import com.hehenian.biz.dal.loan.ILoanPersonCheckDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("loanPersonCheckComponent")
public class LoanPersonCheckComponentImpl implements ILoanPersonCheckComponent {

	@Autowired
    private ILoanPersonCheckDao  loanPersonCheckDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanPersonCheckDo getById(Long id){
	  return loanPersonCheckDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanPersonCheckDo> selectLoanPersonCheck(Map<String,Object> parameterMap){
		return loanPersonCheckDao.selectLoanPersonCheck(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanPersonCheckById(LoanPersonCheckDo newLoanPersonCheckDo){
		int result = loanPersonCheckDao.updateLoanPersonCheckById(newLoanPersonCheckDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 新增
	 */
	public int addLoanPersonCheck(LoanPersonCheckDo newLoanPersonCheckDo){
		return loanPersonCheckDao.addLoanPersonCheck(newLoanPersonCheckDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return loanPersonCheckDao.deleteById(id);
	}

}
