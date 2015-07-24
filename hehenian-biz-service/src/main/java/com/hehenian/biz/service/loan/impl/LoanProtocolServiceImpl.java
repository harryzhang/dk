package com.hehenian.biz.service.loan.impl;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.loan.ILoanProtocolService;
import com.hehenian.biz.common.loan.dataobject.LoanProtocolDo;
import com.hehenian.biz.component.loan.ILoanProtocolComponent;


@Service("loanProtocolService")
public class LoanProtocolServiceImpl implements ILoanProtocolService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private ILoanProtocolComponent  loanProtocolComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanProtocolDo getById(int id){
	  return loanProtocolComponent.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanProtocolDo> selectLoanProtocol(Map<String,Object> parameterMap){
		return loanProtocolComponent.selectLoanProtocol(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanProtocolById(LoanProtocolDo newLoanProtocolDo){
		return loanProtocolComponent.updateLoanProtocolById(newLoanProtocolDo);
	}
	
	/**
	 * 新增
	 */
	public int addLoanProtocol(LoanProtocolDo newLoanProtocolDo){
		return loanProtocolComponent.addLoanProtocol(newLoanProtocolDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return loanProtocolComponent.deleteById(id);
	}

}
