/**
 * Project Name:hehenian-manager
 * File Name:PropertyTest.java
 * Package Name:com.hehenian.manager.modules.basicdata
 * Date:2015年5月5日下午6:26:22
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.manager.BaseTestCase;
import com.hehenian.manager.modules.basicdata.service.AddressService;
import com.hehenian.manager.modules.basicdata.service.PropertyService;

/**
 * 
 * @author   songxjmf
 * @date: 2015年5月5日 下午6:26:22 	 
 */
public class PropertyTest extends BaseTestCase {
	
	@Autowired
	PropertyService propertyService;
	@Autowired
	AddressService addressService;
	
	@Test
	public void importPropertyData(){
		String address = null;
		try {
			address = addressService.getByCommunityCode("1015101240001");
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		Assert.assertNotNull(address);
	}
}

