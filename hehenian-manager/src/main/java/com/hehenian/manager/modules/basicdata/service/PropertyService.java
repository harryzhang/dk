/**
 * Project Name:hehenian-manager
 * File Name:BasicDataService.java
 * Package Name:com.hehenian.manager.modules.basicdata.service
 * Date:2015年5月5日下午1:53:41
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata.service;

import com.hehenian.manager.commons.Result;

/**
 * 物业数据业务接口
 * @author   songxjmf
 * @date: 2015年5月5日 下午1:53:41 	 
 */
public interface PropertyService {
	
	/**
	 * 从excel文件导入多宝車宝数据
	 * @author songxjmf
	 * @date: 2015年5月6日 上午11:22:28
	 */
	public Result importPropertyData(String filePath);

}

