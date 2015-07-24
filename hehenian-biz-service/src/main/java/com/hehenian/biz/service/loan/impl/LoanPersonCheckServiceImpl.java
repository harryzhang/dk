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

import com.hehenian.biz.common.loan.ILoanPersonCheckService;
import com.hehenian.biz.common.loan.dataobject.LoanPersonCheckDo;
import com.hehenian.biz.component.loan.ILoanPersonCheckComponent;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("loanPersonCheckService")
public class LoanPersonCheckServiceImpl implements ILoanPersonCheckService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private ILoanPersonCheckComponent  loanPersonCheckComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanPersonCheckDo getById(Long id){
	  return loanPersonCheckComponent.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanPersonCheckDo> selectLoanPersonCheck(Map<String,Object> parameterMap){
		return loanPersonCheckComponent.selectLoanPersonCheck(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanPersonCheckById(LoanPersonCheckDo newLoanPersonCheckDo){
		return loanPersonCheckComponent.updateLoanPersonCheckById(newLoanPersonCheckDo);
	}
	
	/**
	 * 新增
	 */
	public int addLoanPersonCheck(LoanPersonCheckDo newLoanPersonCheckDo){
		return loanPersonCheckComponent.addLoanPersonCheck(newLoanPersonCheckDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return loanPersonCheckComponent.deleteById(id);
	}

}
