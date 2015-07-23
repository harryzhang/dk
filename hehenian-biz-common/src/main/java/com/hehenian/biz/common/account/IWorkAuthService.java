/**
 * 
 */
package com.hehenian.biz.common.account;

import com.hehenian.biz.common.account.dataobject.WorkAuth;

/**
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.account
 * @Title: IWorkAuthService
 * @Description: 工作认证服务接口
 * @author: chenzhpmf
 * @date 2015年4月28日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
public interface IWorkAuthService {

	/**
	 * 更新工作认证信息
	 * @param workAuth 工作认证信息
	 * @return
	 */
	public int updateWorkAuth(WorkAuth workAuth);
	
	/**
	 * 根据用户ID更新工作认证信息
	 * @param workAuth 需要更新的工作认证信息
	 * @return
	 */
	public int updateWorkAuthByUserId(WorkAuth workAuth);
}
