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
import com.hehenian.biz.common.loan.dataobject.LoanUserDo;
import com.hehenian.biz.component.loan.IManagerLoanUserComponent;
import com.hehenian.biz.dal.loan.IManagerLoanUserDao;

@Component("managerLoanUserComponent")
public class ManagerLoanUserComponentImpl implements IManagerLoanUserComponent {

	@Autowired
    private IManagerLoanUserDao  managerLoanUserDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public LoanUserDo getById(Long loanUserId){
	  return managerLoanUserDao.getById(loanUserId);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanUserDo> selectLoanUser(Map<String,Object> parameterMap){
		return managerLoanUserDao.selectLoanUser(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanUserById(LoanUserDo newLoanUserDo){
		int result = managerLoanUserDao.updateLoanUserById(newLoanUserDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 新增
	 */
	public int addLoanUser(LoanUserDo newLoanUserDo){
		return managerLoanUserDao.addLoanUser(newLoanUserDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(Long loanUserId){
		return managerLoanUserDao.deleteById(loanUserId);
	}

	@Override
	public LoanUserDo getBySourceUserId(String sourceUserId) {
		return managerLoanUserDao.getBySourceUserId(sourceUserId);
	}

}
