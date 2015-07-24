package com.hehenian.biz.component.trade.impl;

import com.hehenian.biz.component.trade.IGuaranteeInstitutionsComponent;
import com.hehenian.biz.common.trade.dataobject.GuaranteeInstitutionsDo;
import com.hehenian.biz.dal.trade.IGuaranteeInstitutionsDao;

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



public class GuaranteeInstitutionsComponentImplTest extends BaseTestCase {
	
	@Autowired
    private IGuaranteeInstitutionsComponent  guaranteeInstitutionsComponent;
	private Random random = new Random();

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Test
	public void testGetById(){
	  int id = 1;
	  GuaranteeInstitutionsDo guaranteeInstitutionsDo = guaranteeInstitutionsComponent.getById(id);
	  Assert.notNull(guaranteeInstitutionsDo);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Test
	public void testSelectGuaranteeInstitutions(){
	    Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("id" ,1);
		List<GuaranteeInstitutionsDo> list = guaranteeInstitutionsComponent.selectGuaranteeInstitutions(parameterMap);
		Assert.notEmpty(list);
	}
	
	/**
	 * 更新
	 */
	@Test
	public void  testUpdateGuaranteeInstitutionsById(){
		GuaranteeInstitutionsDo  guaranteeInstitutionsDo =  guaranteeInstitutionsComponent.getById(3);
	//columns START
	   //guaranteeInstitutionsDo.setId(random.nextLong());
	   guaranteeInstitutionsDo.setOrganName("OrganName");	   
	   guaranteeInstitutionsDo.setOrganMoney(random.nextDouble());
	   guaranteeInstitutionsDo.setOrganNameber(random.nextInt());
	   guaranteeInstitutionsDo.setUserId(random.nextInt());
	//columns END
		int result = guaranteeInstitutionsComponent.updateGuaranteeInstitutionsById(guaranteeInstitutionsDo);
		Assert.state(result>0);
	}
	
	/**
	 * 新增
	 */
	@Test
	public void testAddGuaranteeInstitutions(){
	    GuaranteeInstitutionsDo  guaranteeInstitutionsDo =  new GuaranteeInstitutionsDo();
	//columns START
	   guaranteeInstitutionsDo.setId(random.nextLong());
	   guaranteeInstitutionsDo.setOrganName("OrganName");	   
	   guaranteeInstitutionsDo.setOrganMoney(random.nextDouble());
	   guaranteeInstitutionsDo.setOrganNameber(random.nextInt());
	   guaranteeInstitutionsDo.setUserId(random.nextInt());
	//columns END
		guaranteeInstitutionsComponent.addGuaranteeInstitutions(guaranteeInstitutionsDo);
		Assert.state(guaranteeInstitutionsDo.getId()>0);
	}
	
	/**
	 * 删除
	 */
	@Test
	public void testDeleteById(){
	    int  id=2;
		int result = guaranteeInstitutionsComponent.deleteById(id);
		Assert.state(result>0);
	}

}

