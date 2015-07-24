/**
 * Project Name:hehenian-manager
 * File Name:AddressService.java
 * Package Name:com.hehenian.manager.modules.basicdata.service
 * Date:2015年5月6日上午10:43:21
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata.service;
/**
 * 行政区划查询接口
 * @author   songxjmf
 * @date: 2015年5月6日 上午10:43:21 	 
 */
public interface AddressService {

	/**
	 * 通过小区编码查询省市区小区完整地址
	 * @param communityCode 小区编码
	 * @author songxjmf
	 * @date: 2015年5月6日 上午10:44:49
	 */
	public String getByCommunityCode(String communityCode) throws Exception;
}

