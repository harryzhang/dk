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
import com.hehenian.biz.common.loan.dataobject.LoanChannelDo;
import com.hehenian.biz.component.loan.ILoanChannelComponent;
import com.hehenian.biz.dal.loan.ILoanChannelDao;
import com.hehenian.biz.dal.loan.ILoanUserDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("loanChannelComponent")
public class LoanChannelComponentImpl implements ILoanChannelComponent {
	
//	@Autowired
//	private IUserDao userDao;
//	@Autowired
//	private IPersonDao personDao;

	@Autowired
    private ILoanChannelDao  loanChannelDao;
	
	@Autowired
	private ILoanUserDao loanUserDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanChannelDo getById(Long id){
	  return loanChannelDao.getById(id);
	}
	
	@Override
	public LoanChannelDo getBySourceId(Map<String,Object> parameterMap) {
		return loanChannelDao.getBySourceId(parameterMap);
	}



	/**
	 *根据条件查询列表
	 */
	public List<LoanChannelDo> selectLoanChannel(Map<String,Object> parameterMap){
		return loanChannelDao.selectLoanChannel(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanChannelById(LoanChannelDo newLoanChannelDo){
		int result = loanChannelDao.updateLoanChannelById(newLoanChannelDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 新增
	 */
	public int addLoanChannel(LoanChannelDo loanChannelDo){
		loanUserDao.addLoanUser(loanChannelDo.getLoanUserDo());
		loanChannelDo.setLoanUserId(loanChannelDo.getLoanUserDo().getLoanUserId());
		return loanChannelDao.addLoanChannel(loanChannelDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(Long id){
		return loanChannelDao.deleteById(id);
	}

}
