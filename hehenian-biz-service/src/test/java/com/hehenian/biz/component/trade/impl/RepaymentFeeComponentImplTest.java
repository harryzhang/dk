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

import com.hehenian.biz.common.trade.dataobject.RepaymentFeeDo;
import com.hehenian.biz.dal.trade.IRepaymentFeeDao;
import com.hehenian.biz.service.BaseTestCase;


/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class RepaymentFeeComponentImplTest extends BaseTestCase {
	
	@Autowired
    private IRepaymentFeeDao  repaymentFeeDao;
	private Random random = new Random();

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Test
	public void testGetById(){
	  long id = 1;
	  RepaymentFeeDo repaymentFeeDo = repaymentFeeDao.getById(id);
	  Assert.notNull(repaymentFeeDo);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Test
	public void testSelectRepaymentFee(){
	    Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("id" ,1);
		List<RepaymentFeeDo> list = repaymentFeeDao.selectRepaymentFee(parameterMap);
		Assert.notEmpty(list);
	}
	
	/**
	 * 更新
	 */
	@Test
	public void  testUpdateRepaymentFeeById(){
		RepaymentFeeDo  repaymentFeeDo =  new RepaymentFeeDo();
	//columns START
	   repaymentFeeDo.setId(random.nextLong());
	   repaymentFeeDo.setFeeCode("FeeCode");	   
	   repaymentFeeDo.setRepaymentId(random.nextLong());
	   repaymentFeeDo.setStillAmount(random.nextDouble());	   
	   repaymentFeeDo.setHasAmount(random.nextDouble());	   
	   repaymentFeeDo.setLastUpdateDate(new java.util.Date());
	   repaymentFeeDo.setRemark("Remark");	   
	//columns END
		int result = repaymentFeeDao.updateRepaymentFeeById(repaymentFeeDo);
		Assert.state(result>0);
	}
	
	/**
	 * 新增
	 */
	@Test
	public void testAddRepaymentFee(){
	    RepaymentFeeDo  repaymentFeeDo =  new RepaymentFeeDo();
	    //columns START
	   repaymentFeeDo.setId(random.nextLong());
	   repaymentFeeDo.setFeeCode("FeeCode");	   
	   repaymentFeeDo.setRepaymentId(random.nextLong());
	   repaymentFeeDo.setStillAmount(random.nextDouble());	   
	   repaymentFeeDo.setHasAmount(random.nextDouble());	   
	   repaymentFeeDo.setLastUpdateDate(new java.util.Date());
	   repaymentFeeDo.setRemark("Remark");	   
	   //columns END
		repaymentFeeDao.addRepaymentFee(repaymentFeeDo);
		Assert.state(repaymentFeeDo.getId()>0);
	}
	
	/**
	 * 删除
	 */
	@Test
	public void testDeleteById(){
	    int  id=2;
		int result = repaymentFeeDao.deleteById(id);
		Assert.state(result>0);
	}

}

