package com.hehenian.biz.component.trade.impl;

import com.hehenian.biz.component.trade.IBorrowTypeLogComponent;
import com.hehenian.biz.common.trade.dataobject.BorrowTypeLogDo;
import com.hehenian.biz.dal.trade.IBorrowTypeLogDao;

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



public class BorrowTypeLogComponentImplTest extends BaseTestCase {
	
	@Autowired
    private IBorrowTypeLogComponent  borrowTypeLogComponent;
	private Random random = new Random();

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Test
	public void testGetById(){
	  int id = 1;
	  BorrowTypeLogDo borrowTypeLogDo = borrowTypeLogComponent.getById(id);
	  Assert.notNull(borrowTypeLogDo);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Test
	public void testSelectBorrowTypeLog(){
	    Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("id" ,1);
		List<BorrowTypeLogDo> list = borrowTypeLogComponent.selectBorrowTypeLog(parameterMap);
		Assert.notEmpty(list);
	}
	
	/**
	 * 更新
	 */
	@Test
	public void  testUpdateBorrowTypeLogById(){
		BorrowTypeLogDo  borrowTypeLogDo =  new BorrowTypeLogDo();
	//columns START
	   borrowTypeLogDo.setId(random.nextLong());
	   borrowTypeLogDo.setNid("Nid");	   
	   borrowTypeLogDo.setStatus(random.nextInt());
	   borrowTypeLogDo.setName("Name");	   
	   borrowTypeLogDo.setTitle("Title");	   
	   borrowTypeLogDo.setDescription("Description");	   
	   borrowTypeLogDo.setAccountMultiple(random.nextLong());
	   borrowTypeLogDo.setPasswordStatus(random.nextInt());
	   borrowTypeLogDo.setAmountType(random.nextInt());
	   borrowTypeLogDo.setAmountFirst(random.nextLong());
	   borrowTypeLogDo.setAmountEnd(random.nextLong());
	   borrowTypeLogDo.setFrostScaleVip(random.nextDouble());
	   borrowTypeLogDo.setAprFirst(random.nextDouble());
	   borrowTypeLogDo.setAprEnd(random.nextDouble());
	   borrowTypeLogDo.setCheckFirst(random.nextInt());
	   borrowTypeLogDo.setCheckEnd(random.nextInt());
	   borrowTypeLogDo.setTenderAccountMin("TenderAccountMin");	   
	   borrowTypeLogDo.setTenderAccountMax("TenderAccountMax");	   
	   borrowTypeLogDo.setPeriodMonth("PeriodMonth");	   
	   borrowTypeLogDo.setPeriodDay("PeriodDay");	   
	   borrowTypeLogDo.setValidate("Validate");	   
	   borrowTypeLogDo.setAwardStatus(random.nextInt());
	   borrowTypeLogDo.setAwardScaleFirst(random.nextDouble());
	   borrowTypeLogDo.setAwardScaleEnd(random.nextDouble());
	   borrowTypeLogDo.setAwardAccountFirst(random.nextDouble());
	   borrowTypeLogDo.setAwardAccountEnd(random.nextDouble());
	   borrowTypeLogDo.setSubscribeStatus(random.nextInt());
	   borrowTypeLogDo.setVerifyAutoStatus(random.nextInt());
	   borrowTypeLogDo.setVerifyAutoRemark("VerifyAutoRemark");	   
	   borrowTypeLogDo.setStyles("Styles");	   
	   borrowTypeLogDo.setVipFrostScale(random.nextDouble());
	   borrowTypeLogDo.setLateDaysMonth(random.nextInt());
	   borrowTypeLogDo.setLateDaysDay(random.nextInt());
	   borrowTypeLogDo.setVipLateScale(random.nextDouble());
	   borrowTypeLogDo.setAllLateScale(random.nextDouble());
	   borrowTypeLogDo.setAllFrostScale(random.nextDouble());
	   borrowTypeLogDo.setUserId(random.nextLong());
	   borrowTypeLogDo.setUpdateTime(random.nextLong());
	   borrowTypeLogDo.setUpdateIp("UpdateIp");	   
	   borrowTypeLogDo.setIdentifier("Identifier");	   
	   borrowTypeLogDo.setCounterGuarantee("CounterGuarantee");	   
	   borrowTypeLogDo.setInstitution("Institution");	   
	   borrowTypeLogDo.setLocanFee(random.nextDouble());
	   borrowTypeLogDo.setLocanMonth(random.nextInt());
	   borrowTypeLogDo.setLocanFeeMonth(random.nextDouble());
	   borrowTypeLogDo.setDayFee(random.nextDouble());
	//columns END
		int result = borrowTypeLogComponent.updateBorrowTypeLogById(borrowTypeLogDo);
		Assert.state(result>0);
	}
	
	/**
	 * 新增
	 */
	@Test
	public void testAddBorrowTypeLog(){
	    BorrowTypeLogDo  borrowTypeLogDo =  new BorrowTypeLogDo();
	//columns START
	   borrowTypeLogDo.setId(random.nextLong());
	   borrowTypeLogDo.setNid("Nid");	   
	   borrowTypeLogDo.setStatus(random.nextInt());
	   borrowTypeLogDo.setName("Name");	   
	   borrowTypeLogDo.setTitle("Title");	   
	   borrowTypeLogDo.setDescription("Description");	   
	   borrowTypeLogDo.setAccountMultiple(random.nextLong());
	   borrowTypeLogDo.setPasswordStatus(random.nextInt());
	   borrowTypeLogDo.setAmountType(random.nextInt());
	   borrowTypeLogDo.setAmountFirst(random.nextLong());
	   borrowTypeLogDo.setAmountEnd(random.nextLong());
	   borrowTypeLogDo.setFrostScaleVip(random.nextDouble());
	   borrowTypeLogDo.setAprFirst(random.nextDouble());
	   borrowTypeLogDo.setAprEnd(random.nextDouble());
	   borrowTypeLogDo.setCheckFirst(random.nextInt());
	   borrowTypeLogDo.setCheckEnd(random.nextInt());
	   borrowTypeLogDo.setTenderAccountMin("TenderAccountMin");	   
	   borrowTypeLogDo.setTenderAccountMax("TenderAccountMax");	   
	   borrowTypeLogDo.setPeriodMonth("PeriodMonth");	   
	   borrowTypeLogDo.setPeriodDay("PeriodDay");	   
	   borrowTypeLogDo.setValidate("Validate");	   
	   borrowTypeLogDo.setAwardStatus(random.nextInt());
	   borrowTypeLogDo.setAwardScaleFirst(random.nextDouble());
	   borrowTypeLogDo.setAwardScaleEnd(random.nextDouble());
	   borrowTypeLogDo.setAwardAccountFirst(random.nextDouble());
	   borrowTypeLogDo.setAwardAccountEnd(random.nextDouble());
	   borrowTypeLogDo.setSubscribeStatus(random.nextInt());
	   borrowTypeLogDo.setVerifyAutoStatus(random.nextInt());
	   borrowTypeLogDo.setVerifyAutoRemark("VerifyAutoRemark");	   
	   borrowTypeLogDo.setStyles("Styles");	   
	   borrowTypeLogDo.setVipFrostScale(random.nextDouble());
	   borrowTypeLogDo.setLateDaysMonth(random.nextInt());
	   borrowTypeLogDo.setLateDaysDay(random.nextInt());
	   borrowTypeLogDo.setVipLateScale(random.nextDouble());
	   borrowTypeLogDo.setAllLateScale(random.nextDouble());
	   borrowTypeLogDo.setAllFrostScale(random.nextDouble());
	   borrowTypeLogDo.setUserId(random.nextLong());
	   borrowTypeLogDo.setUpdateTime(random.nextLong());
	   borrowTypeLogDo.setUpdateIp("UpdateIp");	   
	   borrowTypeLogDo.setIdentifier("Identifier");	   
	   borrowTypeLogDo.setCounterGuarantee("CounterGuarantee");	   
	   borrowTypeLogDo.setInstitution("Institution");	   
	   borrowTypeLogDo.setLocanFee(random.nextDouble());
	   borrowTypeLogDo.setLocanMonth(random.nextInt());
	   borrowTypeLogDo.setLocanFeeMonth(random.nextDouble());
	   borrowTypeLogDo.setDayFee(random.nextDouble());
	//columns END
		borrowTypeLogComponent.addBorrowTypeLog(borrowTypeLogDo);
		Assert.state(borrowTypeLogDo.getId()>0);
	}
	
	/**
	 * 删除
	 */
	@Test
	public void testDeleteById(){
	    int  id=2;
		int result = borrowTypeLogComponent.deleteById(id);
		Assert.state(result>0);
	}

}

