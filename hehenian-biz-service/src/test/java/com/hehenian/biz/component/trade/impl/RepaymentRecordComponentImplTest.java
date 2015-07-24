/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.component.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hehenian.biz.common.trade.dataobject.RepaymentRecordDo;
import com.hehenian.biz.component.trade.IRepaymentRecordComponent;
import com.hehenian.biz.service.BaseTestCase;


/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class RepaymentRecordComponentImplTest extends BaseTestCase {
	
	@Autowired
    private IRepaymentRecordComponent  repaymentRecordComponent;
	private Random random = new Random();

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Test
	public void testGetById(){
	  int id = 1;
	  RepaymentRecordDo repaymentRecordDo = repaymentRecordComponent.getById(id);
	  Assert.notNull(repaymentRecordDo);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Test
	public void testSelectRepaymentRecord(){
	    Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("id" ,1);
		List<RepaymentRecordDo> list = repaymentRecordComponent.selectRepaymentRecord(parameterMap);
		Assert.notEmpty(list);
	}
	
	/**
	 * 更新
	 */
	@Test
	public void  testUpdateRepaymentRecordById(){
		RepaymentRecordDo  repaymentRecordDo =  new RepaymentRecordDo();
	//columns START
	   repaymentRecordDo.setId(random.nextLong());
	   repaymentRecordDo.setRepayId(random.nextLong());
	   repaymentRecordDo.setRepayAmount(random.nextDouble());	   
	   repaymentRecordDo.setOporator(random.nextLong());
	   repaymentRecordDo.setRemark("Remark");	   
	   repaymentRecordDo.setCreateTime(new java.util.Date());
	   repaymentRecordDo.setOporatorIp("OporatorIp");	   
	   repaymentRecordDo.setRepayType("RepayType");	   
	   repaymentRecordDo.setProcessStatus("ProcessStatus");	   
	//columns END
		int result = repaymentRecordComponent.updateRepaymentRecordById(repaymentRecordDo);
		Assert.state(result>0);
	}
	
	/**
	 * 新增
	 */
	@Test
	public void testAddRepaymentRecord(){
	    RepaymentRecordDo  repaymentRecordDo =  new RepaymentRecordDo();
	//columns START
	   repaymentRecordDo.setId(random.nextLong());
	   repaymentRecordDo.setRepayId(random.nextLong());
	   repaymentRecordDo.setRepayAmount(random.nextDouble());	   
	   repaymentRecordDo.setOporator(random.nextLong());
	   repaymentRecordDo.setRemark("Remark");	   
	   repaymentRecordDo.setCreateTime(new java.util.Date());
	   repaymentRecordDo.setOporatorIp("OporatorIp");	   
	   repaymentRecordDo.setRepayType("RepayType");	   
	   repaymentRecordDo.setProcessStatus("ProcessStatus");	   
	//columns END
		repaymentRecordComponent.addRepaymentRecord(repaymentRecordDo);
		Assert.state(repaymentRecordDo.getId()>0);
	}
	
	/**
	 * 删除
	 */
	@Test
	public void testDeleteById(){
	    int  id=2;
		int result = repaymentRecordComponent.deleteById(id);
		Assert.state(result>0);
	}

}

