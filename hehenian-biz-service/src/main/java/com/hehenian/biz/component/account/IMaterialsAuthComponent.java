/**
 * 
 */
package com.hehenian.biz.component.account;

import com.hehenian.biz.common.account.dataobject.MaterialsAuth;
import com.hehenian.biz.common.exception.BusinessException;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.account
 * @Title: IMaterialsAuthComponent
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年4月28日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
public interface IMaterialsAuthComponent {

	/**
	 * 保存资料认证信息
	 * @param materialsAuth
	 * @return 保存的记录数
	 * @throws BusinessException 保存失败异常
	 */
	public int save(MaterialsAuth materialsAuth) throws BusinessException;

	/**
	 * 更新资料认证信息
	 * 
	 * @param materialsAuth
	 * @return 更新的记录数
	 * @throws BusinessException  更新失败异常
	 */
	public int update(MaterialsAuth materialsAuth) throws BusinessException;

	/**
	 * 根据用户ID更新资料认证信息
	 * @param materialsAuth
	 * @return 更新的记录数
	 * @throws BusinessException  更新失败异常
	 */
	public int updateByUserId(MaterialsAuth materialsAuth) throws BusinessException;
}
