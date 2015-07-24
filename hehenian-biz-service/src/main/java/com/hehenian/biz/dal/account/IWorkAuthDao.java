/**
 * 
 */
package com.hehenian.biz.dal.account;

import com.hehenian.biz.common.account.dataobject.WorkAuth;

/**
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.account
 * @Title: IWorkAutoDao
 * @Description: 工作信息认证数据库操作
 * @author: chenzhpmf
 * @date 2015年4月28日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
public interface IWorkAuthDao {

	/**
	 * 更新工作认证信息
	 * 
	 * @param workAuth 工作认证信息
	 * @return 更新的记录数
	 */
	public int updateWorkAuth(WorkAuth workAuth);

	/**
	 * 根据用户ID更新工作认证信息
	 * 
	 * @param workAuth 需要更新的工作认证信息
	 * @return 更新的记录数
	 */
	public int updateWorkAuthByUserId(WorkAuth workAuth);
}
