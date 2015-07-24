/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.fund.impl;

import com.hehenian.biz.component.fund.IFundComponent;
import com.hehenian.biz.component.fund.dao.IFundDao;
import com.hehenian.biz.common.fund.dataobject.FundDo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import com.hehenian.biz.service.BaseTestCase;


/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class FundComponentImplTest extends BaseTestCase {
	
	@Autowired
    private IFundComponent  fundComponent;
	private Random random = new Random();

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Test
	public void testGetById(){
	  String id = "604";
	  FundDo fundDo = fundComponent.getById(id);
	  Assert.notNull(fundDo);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Test
	public void testSelectFund(){
	    Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("fundCode" ,"604");
		List<FundDo> list = fundComponent.selectFund(parameterMap);
		Assert.notEmpty(list);
	}
	
	/**
	 * 更新
	 */
	@Test
	public void  testUpdateFundById(){
		FundDo  fundDo =  new FundDo();
	//columns START
	   fundDo.setFundCode("FundCode");	   
	   fundDo.setFundName("FundName");	   
	   fundDo.setRemark("Remark");	   
	   fundDo.setValidate("Validate");	   
	//columns END
		int result = fundComponent.updateFundById(fundDo);
		Assert.state(result>0);
	}
	
	/**
	 * 新增
	 */
	@Test
	public void testAddFund(){
	    FundDo  fundDo =  new FundDo();
	//columns START
	   fundDo.setFundCode("FundCode");	   
	   fundDo.setFundName("FundName");	   
	   fundDo.setRemark("Remark");	   
	   fundDo.setValidate("Validate");	   
	//columns END
		fundComponent.addFund(fundDo);
	}
	
	/**
	 * 删除
	 */
	@Test
	public void testDeleteById(){
	    String  id= "55";
		int result = fundComponent.deleteById(id);
		Assert.state(result>0);
	}

}

