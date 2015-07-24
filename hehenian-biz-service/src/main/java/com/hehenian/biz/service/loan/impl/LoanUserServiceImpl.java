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

import com.hehenian.biz.common.loan.ILoanUserService;
import com.hehenian.biz.common.loan.dataobject.LoanUserDo;
import com.hehenian.biz.component.loan.ILoanUserComponent;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("loanUserService")
public class LoanUserServiceImpl implements ILoanUserService {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private ILoanUserComponent  loanUserComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanUserDo getById(Long loanUserId){
	  return loanUserComponent.getById(loanUserId);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanUserDo> selectLoanUser(Map<String,Object> parameterMap){
		return loanUserComponent.selectLoanUser(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanUserById(LoanUserDo newLoanUserDo){
		return loanUserComponent.updateLoanUserById(newLoanUserDo);
	}
	
	/**
	 * 新增
	 */
	public int addLoanUser(LoanUserDo newLoanUserDo){
		return loanUserComponent.addLoanUser(newLoanUserDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(Long loanUserId){
		return loanUserComponent.deleteById(loanUserId);
	}

}
