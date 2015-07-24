/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.component.bank.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hehenian.biz.common.bank.dataobject.BankBingDo;
import com.hehenian.biz.component.bank.IBankBingComponent;
import com.hehenian.biz.service.BaseTestCase;


/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class BankBingComponentImplTest extends BaseTestCase {
	
	@Autowired
    private IBankBingComponent  bankBingComponent;
	private Random random = new Random();

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Test
	public void testGetById(){
        int id = 2;
        BankBingDo tdBankBingDo = bankBingComponent.getById(id);
	  Assert.notNull(tdBankBingDo);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Test
    public void testSelectBankBing() {
	    Map<String,Object> parameterMap = new HashMap<String,Object>();
        parameterMap.put("id", 1);
        List<BankBingDo> list = bankBingComponent.selectBankBing(parameterMap);
		Assert.notEmpty(list);
	}
	
	/**
	 * 更新
	 */
	@Test
    public void testUpdateBankBingById() {
		BankBingDo  tdBankBingDo =  new BankBingDo();
	//columns START
        tdBankBingDo.setId(2l);
        tdBankBingDo.setUserId(66666666l);
        tdBankBingDo.setBankCode("BankCode1");
        tdBankBingDo.setAmount(new BigDecimal(4));
        tdBankBingDo.setSendFlag("F");
        tdBankBingDo.setCheckNumber((short) 3);
        tdBankBingDo.setCheckFlag("F");
	   tdBankBingDo.setSendTime(new java.util.Date());
	   tdBankBingDo.setCheckTime(new java.util.Date());
        tdBankBingDo.setRecordStatus("F");
	   tdBankBingDo.setBusinessRecordId("BusinessRecordId");	   
	//columns END
        int result = bankBingComponent.updateBankBingById(tdBankBingDo);
		Assert.state(result>0);
	}
	
	/**
	 * 新增
	 */
	@Test
	public void testAddTdBankBing(){
	    BankBingDo  tdBankBingDo =  new BankBingDo();
	//columns START
	   tdBankBingDo.setId(random.nextLong());
	   tdBankBingDo.setUserId(random.nextLong());
	   tdBankBingDo.setBankCode("BankCode");	   
        tdBankBingDo.setAmount(new BigDecimal(55));
        tdBankBingDo.setSendFlag("F");
        tdBankBingDo.setCheckNumber((short) 3);
        tdBankBingDo.setCheckFlag("F");
	   tdBankBingDo.setSendTime(new java.util.Date());
	   tdBankBingDo.setCheckTime(new java.util.Date());
        tdBankBingDo.setRecordStatus("T");
	   tdBankBingDo.setBusinessRecordId("BusinessRecordId");	   
	//columns END
        bankBingComponent.addBankBing(tdBankBingDo);
		Assert.state(tdBankBingDo.getId()>0);
	}
	
	/**
	 * 删除
	 */
	@Test
	public void testDeleteById(){
	    int  id=2;
        int result = bankBingComponent.deleteById(id);
		Assert.state(result>0);
	}

}

