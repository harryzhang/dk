package com.hehenian.biz.component.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;
import com.hehenian.biz.component.trade.IInvestRepaymentComponent;
import com.hehenian.biz.service.BaseTestCase;


/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class InvestRepaymentComponentImplTest extends BaseTestCase {
	
	@Autowired
    private IInvestRepaymentComponent  investRepaymentComponent;
	private Random random = new Random();

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Test
	public void testGetById(){
        long id = 1;
	  InvestRepaymentDo investRepaymentDo = investRepaymentComponent.getById(id);
	  Assert.notNull(investRepaymentDo);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Test
	public void testSelectInvestRepayment(){
	    Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("id" ,1);
		List<InvestRepaymentDo> list = investRepaymentComponent.selectInvestRepayment(parameterMap);
		Assert.notEmpty(list);
	}
	
	/**
	 * 更新
	 */
	@Test
	public void  testUpdateInvestRepaymentById(){
        InvestRepaymentDo investRepaymentDo = investRepaymentComponent.getById(1l);
	//columns START
	   //investRepaymentDo.setId(random.nextLong());
	   investRepaymentDo.setRepayId(random.nextLong());
	   investRepaymentDo.setRepayPeriod("RepayPeriod");	   
	   investRepaymentDo.setRepayDate(new java.util.Date());
	   investRepaymentDo.setRealRepayDate(new java.util.Date());
	   investRepaymentDo.setRecivedPrincipal(random.nextDouble());
	   investRepaymentDo.setRecivedInterest(random.nextDouble());
	   investRepaymentDo.setHasPrincipal(random.nextDouble());
	   investRepaymentDo.setHasInterest(random.nextDouble());
	   investRepaymentDo.setInterestOwner(random.nextLong());
	   investRepaymentDo.setRecivedFi(random.nextDouble());
	   investRepaymentDo.setIsLate(random.nextInt());
	   investRepaymentDo.setLateDay(random.nextInt());
	   investRepaymentDo.setIsWebRepay(random.nextInt());
	   investRepaymentDo.setPrincipalBalance(random.nextDouble());
	   investRepaymentDo.setInterestBalance(random.nextDouble());
	   investRepaymentDo.setInvestId(random.nextLong());
	   investRepaymentDo.setOwner(random.nextLong());
	   investRepaymentDo.setOwnerlist("Ownerlist");	   
	   investRepaymentDo.setRepayStatus(random.nextInt());
	   investRepaymentDo.setImanageFee(random.nextDouble());
	   investRepaymentDo.setImanageFeeRate(random.nextDouble());
	   investRepaymentDo.setIsDebt(random.nextInt());
	   investRepaymentDo.setBorrowId(random.nextLong());
	   investRepaymentDo.setCirculationForpayStatus(random.nextInt());
	//columns END
		int result = investRepaymentComponent.updateInvestRepaymentById(investRepaymentDo);
		Assert.state(result>0);
	}
	
	/**
	 * 新增
	 */
	@Test
	public void testAddInvestRepayment(){
	    InvestRepaymentDo  investRepaymentDo =  new InvestRepaymentDo();
	//columns START
	   //investRepaymentDo.setId(random.nextLong());
	   investRepaymentDo.setRepayId(random.nextLong());
	   investRepaymentDo.setRepayPeriod("RepayPeriod");	   
	   investRepaymentDo.setRepayDate(new java.util.Date());
	   investRepaymentDo.setRealRepayDate(new java.util.Date());
	   investRepaymentDo.setRecivedPrincipal(random.nextDouble());
	   investRepaymentDo.setRecivedInterest(random.nextDouble());
	   investRepaymentDo.setHasPrincipal(random.nextDouble());
	   investRepaymentDo.setHasInterest(random.nextDouble());
	   investRepaymentDo.setInterestOwner(random.nextLong());
	   investRepaymentDo.setRecivedFi(random.nextDouble());
	   investRepaymentDo.setIsLate(random.nextInt());
	   investRepaymentDo.setLateDay(random.nextInt());
	   investRepaymentDo.setIsWebRepay(random.nextInt());
	   investRepaymentDo.setPrincipalBalance(random.nextDouble());
	   investRepaymentDo.setInterestBalance(random.nextDouble());
	   investRepaymentDo.setInvestId(random.nextLong());
	   investRepaymentDo.setOwner(random.nextLong());
	   investRepaymentDo.setOwnerlist("Ownerlist");	   
	   investRepaymentDo.setRepayStatus(random.nextInt());
	   investRepaymentDo.setImanageFee(random.nextDouble());
	   investRepaymentDo.setImanageFeeRate(random.nextDouble());
	   investRepaymentDo.setIsDebt(random.nextInt());
	   investRepaymentDo.setBorrowId(random.nextLong());
	   investRepaymentDo.setCirculationForpayStatus(random.nextInt());
	//columns END
		investRepaymentComponent.addInvestRepayment(investRepaymentDo);
	}
	
	/**
	 * 删除
	 */
	@Test
	public void testDeleteById(){
	    int  id=2;
		int result = investRepaymentComponent.deleteById(id);
		Assert.state(result>0);
	}

}

