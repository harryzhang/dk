/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.fund.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.fund.dataobject.FundDo;
import com.hehenian.biz.component.fund.IFundComponent;
import com.hehenian.biz.component.fund.dao.IFundDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("fundComponent")
public class FundComponentImpl implements IFundComponent {

	@Autowired
    private IFundDao  fundDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public FundDo getById(String id){
	  return fundDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<FundDo> selectFund(Map<String,Object> parameterMap){
		return fundDao.selectFund(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateFundById(FundDo newFundDo){
		int result = fundDao.updateFundById(newFundDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 新增
	 */
	public int addFund(FundDo newFundDo){
		return fundDao.addFund(newFundDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(String id){
		return fundDao.deleteById(id);
	}

}
