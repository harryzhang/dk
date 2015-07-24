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

import com.hehenian.biz.common.loan.ILoanChannelService;
import com.hehenian.biz.common.loan.dataobject.LoanChannelDo;
import com.hehenian.biz.component.loan.ILoanChannelComponent;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("loanChannelService")
public class LoanChannelServiceImpl implements ILoanChannelService {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private ILoanChannelComponent  loanChannelComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanChannelDo getById(Long id){
	  return loanChannelComponent.getById(id);
	}
	
	@Override
	public LoanChannelDo getBySourceId(Map<String,Object> parameterMap) {
		return loanChannelComponent.getBySourceId(parameterMap);
	}



	/**
	 *根据条件查询列表
	 */
	public List<LoanChannelDo> selectLoanChannel(Map<String,Object> parameterMap){
		return loanChannelComponent.selectLoanChannel(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanChannelById(LoanChannelDo newLoanChannelDo){
		return loanChannelComponent.updateLoanChannelById(newLoanChannelDo);
	}
	
	/**
	 * 新增
	 */
	public int addLoanChannel(LoanChannelDo loanChannelDo){
		return loanChannelComponent.addLoanChannel(loanChannelDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(Long id){
		return loanChannelComponent.deleteById(id);
	}

}
