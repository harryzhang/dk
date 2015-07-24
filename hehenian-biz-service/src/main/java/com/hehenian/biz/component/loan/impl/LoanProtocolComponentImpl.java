package com.hehenian.biz.component.loan.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.loan.dataobject.LoanProtocolDo;
import com.hehenian.biz.component.loan.ILoanProtocolComponent;
import com.hehenian.biz.dal.loan.ILoanProtocolDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("loanProtocolComponent")
public class LoanProtocolComponentImpl implements ILoanProtocolComponent {

	@Autowired
    private ILoanProtocolDao  loanProtocolDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanProtocolDo getById(int id){
	  return loanProtocolDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanProtocolDo> selectLoanProtocol(Map<String,Object> parameterMap){
		return loanProtocolDao.selectLoanProtocol(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanProtocolById(LoanProtocolDo newLoanProtocolDo){
		int result = loanProtocolDao.updateLoanProtocolById(newLoanProtocolDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 新增
	 */
	public int addLoanProtocol(LoanProtocolDo newLoanProtocolDo){
		return loanProtocolDao.addLoanProtocol(newLoanProtocolDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return loanProtocolDao.deleteById(id);
	}

}
