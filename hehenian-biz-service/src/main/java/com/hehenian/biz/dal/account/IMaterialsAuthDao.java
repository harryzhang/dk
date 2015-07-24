/**
 * 
 */
package com.hehenian.biz.dal.account;

import com.hehenian.biz.common.account.dataobject.MaterialsAuth;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.account
 * @Title: IMaterialsAuthDao
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年4月28日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
public interface IMaterialsAuthDao {

	/**
	 * 保存资料认证信息
	 * @param materialsAuth
	 * @return 保存的记录数
	 */
	public int save(MaterialsAuth materialsAuth);

	/**
	 * 更新资料认证信息
	 * 
	 * @param materialsAuth
	 * @return 更新的记录数
	 */
	public int update(MaterialsAuth materialsAuth);

	/**
	 * 根据用户ID更新资料认证信息
	 * @param materialsAuth
	 * @return 更新的记录数
	 */
	public int updateByUserId(MaterialsAuth materialsAuth);
}
