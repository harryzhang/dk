/**
 * 
 */
package com.hehenian.biz.component.account.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.MaterialsAuth;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.account.IMaterialsAuthComponent;
import com.hehenian.biz.dal.account.IMaterialsAuthDao;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.account
 * @Title: MaterialsAuthComponentImpl
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年4月28日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
@Component("materialsAuthComponent")
public class MaterialsAuthComponentImpl implements IMaterialsAuthComponent {
	
	@Autowired
	private IMaterialsAuthDao materialsAuthDao; 
	
	/* (non-Javadoc)
	 * @see com.hehenian.biz.component.account.IMaterialsAuthComponent#save(com.hehenian.biz.common.account.dataobject.MaterialsAuth)
	 */
	@Override
	public int save(MaterialsAuth materialsAuth) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.hehenian.biz.component.account.IMaterialsAuthComponent#update(com.hehenian.biz.common.account.dataobject.MaterialsAuth)
	 */
	@Override
	public int update(MaterialsAuth materialsAuth) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.hehenian.biz.component.account.IMaterialsAuthComponent#updateByUserId(com.hehenian.biz.common.account.dataobject.MaterialsAuth)
	 */
	@Override
	public int updateByUserId(MaterialsAuth materialsAuth) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

}
