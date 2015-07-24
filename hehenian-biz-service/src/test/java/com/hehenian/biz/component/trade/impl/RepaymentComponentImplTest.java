package com.hehenian.biz.component.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.component.trade.IRepaymentComponent;
import com.hehenian.biz.service.BaseTestCase;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class RepaymentComponentImplTest extends BaseTestCase {
	
	@Autowired
    private IRepaymentComponent  repaymentComponent;
	private Random random = new Random();

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Test
	public void testGetById(){
	  long id = 4;
	  RepaymentDo repaymentDo = repaymentComponent.getById(id);
	  Assert.notNull(repaymentDo);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Test
	public void testSelectRepayment(){
	    Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("id" ,3);
		List<RepaymentDo> list = repaymentComponent.selectRepayment(parameterMap);
		Assert.notEmpty(list);
	}
	
	/**
	 *翻页查询
	 */
	@Test
	public void testSelectRepaymentPage(){
	    Map<String,Object> parameterMap = new HashMap<String,Object>();
		//parameterMap.put("id" ,3);
	    PageDo page = new PageDo();
	    parameterMap.put("page", page);
	    //parameterMap.put("username", "'");
		List<Map> list = repaymentComponent.selectRepaymentPage(parameterMap);
		Assert.notEmpty(list);
	}
	
	/**
	 * 更新
	 */
	@Test
	public void  testUpdateRepaymentById(){
		RepaymentDo  repaymentDo =  repaymentComponent.getById(5l);
	//columns START
	   //repaymentDo.setId(random.nextLong());
	   repaymentDo.setRepayDate(new java.util.Date());
	   repaymentDo.setRealRepayDate(new java.util.Date());
	   repaymentDo.setRepayPeriod("RepayPeriod");	   
	   repaymentDo.setHasPi(random.nextDouble());
	   repaymentDo.setStillPrincipal(random.nextDouble());
	   repaymentDo.setStillInterest(random.nextDouble());
	   repaymentDo.setHasFi(random.nextDouble());
	   repaymentDo.setConsultFee(random.nextDouble());
	   repaymentDo.setLateFi(random.nextDouble());
	   repaymentDo.setLateDay(random.nextInt());
	   repaymentDo.setRepayStatus(random.nextInt());
	   repaymentDo.setBorrowId(random.nextLong());
	   repaymentDo.setIsLate(random.nextInt());
	   repaymentDo.setIsWebRepay(random.nextInt());
	   repaymentDo.setInvestorForpayFi(random.nextDouble());
	   repaymentDo.setInvestorHaspayFi(random.nextDouble());
	   repaymentDo.setPrincipalBalance(random.nextDouble());
	   repaymentDo.setInterestBalance(random.nextDouble());
	   repaymentDo.setVersion(random.nextInt());
	   repaymentDo.setExecuteTime(new java.util.Date());
	   repaymentDo.setIdentify("Identify");	   
	   repaymentDo.setRepayFee(random.nextDouble());
	//columns END
		int result = repaymentComponent.updateRepaymentById(repaymentDo);
		Assert.state(result>0);
	}
	
	/**
	 * 新增
	 */
	@Test
	public void testAddRepayment(){
	    RepaymentDo  repaymentDo =  new RepaymentDo();
	//columns START
	   //repaymentDo.setId(random.nextLong());
	   repaymentDo.setRepayDate(new java.util.Date());
	   repaymentDo.setRealRepayDate(new java.util.Date());
	   repaymentDo.setRepayPeriod("RepayPeriod");	   
	   repaymentDo.setHasPi(random.nextDouble());
	   repaymentDo.setStillPrincipal(random.nextDouble());
	   repaymentDo.setStillInterest(random.nextDouble());
	   repaymentDo.setHasFi(random.nextDouble());
	   repaymentDo.setConsultFee(random.nextDouble());
	   repaymentDo.setLateFi(random.nextDouble());
	   repaymentDo.setLateDay(random.nextInt());
	   repaymentDo.setRepayStatus(random.nextInt());
	   repaymentDo.setBorrowId(random.nextLong());
	   repaymentDo.setIsLate(random.nextInt());
	   repaymentDo.setIsWebRepay(random.nextInt());
	   repaymentDo.setInvestorForpayFi(random.nextDouble());
	   repaymentDo.setInvestorHaspayFi(random.nextDouble());
	   repaymentDo.setPrincipalBalance(random.nextDouble());
	   repaymentDo.setInterestBalance(random.nextDouble());
	   repaymentDo.setVersion(random.nextInt());
	   repaymentDo.setExecuteTime(new java.util.Date());
	   repaymentDo.setIdentify("Identify");	   
	   repaymentDo.setRepayFee(random.nextDouble());
	//columns END
		repaymentComponent.addRepayment(repaymentDo);
		Assert.state(repaymentDo.getId()>0);
		System.out.println(repaymentDo);
	}
	
	/**
	 * 删除
	 */
	@Test
	public void testDeleteById(){
	    long  id=3;
		int result = repaymentComponent.deleteById(id);
		Assert.state(result>0);
	}

}

