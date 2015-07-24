/**
 * Project Name:hehenian-manager
 * File Name:AddressServiceImpl.java
 * Package Name:com.hehenian.manager.modules.basicdata.service.impl
 * Date:2015年5月6日上午10:46:01
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.manager.modules.basicdata.dao.AddressDao;
import com.hehenian.manager.modules.basicdata.service.AddressService;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao addressDao;
	
	@Override
	public String getByCommunityCode(String communityCode) throws Exception{
		if(StringUtils.isBlank(communityCode)){
			throw new Exception("小区编码为空");
		}
		if(!communityCode.matches("\\d{13}")){
			throw new Exception("小区编码格式错误，必须为13位数字");
		}
		return addressDao.getByCommunityCode(communityCode);
	}

}

