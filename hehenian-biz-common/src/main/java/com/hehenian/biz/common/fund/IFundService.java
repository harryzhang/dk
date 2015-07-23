/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.common.fund;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.fund.dataobject.FundDo;


public interface IFundService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public FundDo getById(String id);
	
	/**
	 *根据条件查询列表
	 */
	public List<FundDo> selectFund(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateFundById(FundDo newFundDo);
	
	/**
	 * 新增
	 */
	public int addFund(FundDo newFundDo);
	
	/**
	 * 删除
	 */
	public int deleteById(String id);
}
