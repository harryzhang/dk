/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.service.fund.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.component.fund.IFundComponent;
import org.apache.ibatis.annotations.Param;
import com.hehenian.biz.common.fund.dataobject.FundDo;
import com.hehenian.biz.common.fund.IFundService;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("fundService")
public class FundServiceImpl implements IFundService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private IFundComponent  fundComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public FundDo getById(String id){
	  return fundComponent.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<FundDo> selectFund(Map<String,Object> parameterMap){
		return fundComponent.selectFund(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateFundById(FundDo newFundDo){
		return fundComponent.updateFundById(newFundDo);
	}
	
	/**
	 * 新增
	 */
	public int addFund(FundDo newFundDo){
		return fundComponent.addFund(newFundDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(String id){
		return fundComponent.deleteById(id);
	}

}
