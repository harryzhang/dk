package com.hehenian.biz.component.trade.impl;

import com.hehenian.biz.component.trade.IRiskDetailComponent;
import com.hehenian.biz.common.trade.dataobject.RiskDetailDo;
import com.hehenian.biz.dal.trade.IRiskDetailDao;

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



public class RiskDetailComponentImplTest extends BaseTestCase {
	
	@Autowired
    private IRiskDetailComponent  riskDetailComponent;
	private Random random = new Random();

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Test
	public void testGetById(){
	  int id = 1;
	  RiskDetailDo riskDetailDo = riskDetailComponent.getById(id);
	  Assert.notNull(riskDetailDo);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Test
	public void testSelectRiskDetail(){
	    Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("id" ,1);
		List<RiskDetailDo> list = riskDetailComponent.selectRiskDetail(parameterMap);
		Assert.notEmpty(list);
	}
	
	/**
	 * 更新
	 */
	@Test
	public void  testUpdateRiskDetailById(){
		RiskDetailDo  riskDetailDo =  riskDetailComponent.getById(1);
	//columns START
	   //riskDetailDo.setId(random.nextLong());
	   riskDetailDo.setRiskInCome(random.nextDouble());
	   riskDetailDo.setRiskSpending(random.nextDouble());
	   riskDetailDo.setRiskDate(new java.util.Date());
	   riskDetailDo.setRiskBalance(random.nextDouble());
	   riskDetailDo.setRiskType("RiskType");	   
	   riskDetailDo.setResource("Resource");	   
	   riskDetailDo.setTrader(random.nextLong());
	   riskDetailDo.setBorrowId(random.nextLong());
	   riskDetailDo.setRemark("Remark");	   
	   riskDetailDo.setOperator(random.nextLong());
	//columns END
		int result = riskDetailComponent.updateRiskDetailById(riskDetailDo);
		Assert.state(result>0);
	}
	
	/**
	 * 新增
	 */
	@Test
	public void testAddRiskDetail(){
	    RiskDetailDo  riskDetailDo =  new RiskDetailDo();
	//columns START
	   riskDetailDo.setId(random.nextLong());
	   riskDetailDo.setRiskInCome(random.nextDouble());
	   riskDetailDo.setRiskSpending(random.nextDouble());
	   riskDetailDo.setRiskDate(new java.util.Date());
	   riskDetailDo.setRiskBalance(random.nextDouble());
	   riskDetailDo.setRiskType("RiskType");	   
	   riskDetailDo.setResource("Resource");	   
	   riskDetailDo.setTrader(random.nextLong());
	   riskDetailDo.setBorrowId(random.nextLong());
	   riskDetailDo.setRemark("Remark");	   
	   riskDetailDo.setOperator(random.nextLong());
	//columns END
		riskDetailComponent.addRiskDetail(riskDetailDo);
		Assert.state(riskDetailDo.getId()>0);
	}
	
	/**
	 * 删除
	 */
	@Test
	public void testDeleteById(){
	    int  id=2;
		int result = riskDetailComponent.deleteById(id);
		Assert.state(result>0);
	}

}

