/**
 * 
 */
package com.hehenian.biz.component.account;

import com.hehenian.biz.common.account.dataobject.WorkAuth;
import com.hehenian.biz.common.exception.BusinessException;

/**
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.account
 * @Title: IWorkAuthComponent
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年4月28日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
public interface IWorkAuthComponent {

	/**
	 * 更新工作认证信息
	 * @param workAuth 工作认证信息
	 * @return
	 * @throws BusinessException 更新失败异常
	 */
	public int updateWorkAuth(WorkAuth workAuth) throws BusinessException;

	/**
	 * 根据用户ID更新工作认证信息
	 * @param workAuth 需要更新的工作认证信息
	 * @return
	 * @throws BusinessException 更新失败异常
	 */
	public int updateWorkAuthByUserId(WorkAuth workAuth) throws BusinessException;
}
