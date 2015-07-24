/**
 * 
 */
package com.hehenian.biz.service.account;

import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.IMaterialsAuthService;
import com.hehenian.biz.common.account.dataobject.MaterialsAuth;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.account
 * @Title: MaterialsAuthServiceImpl
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年4月28日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
@Service("materialsAuthService")
public class MaterialsAuthServiceImpl implements IMaterialsAuthService {

	/* (non-Javadoc)
	 * @see com.hehenian.biz.common.account.IMaterialsAuthService#save(com.hehenian.biz.common.account.dataobject.MaterialsAuth)
	 */
	@Override
	public int save(MaterialsAuth materialsAuth) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.hehenian.biz.common.account.IMaterialsAuthService#update(com.hehenian.biz.common.account.dataobject.MaterialsAuth)
	 */
	@Override
	public int update(MaterialsAuth materialsAuth) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.hehenian.biz.common.account.IMaterialsAuthService#updateByUserId(com.hehenian.biz.common.account.dataobject.MaterialsAuth)
	 */
	@Override
	public int updateByUserId(MaterialsAuth materialsAuth) {
		// TODO Auto-generated method stub
		return 0;
	}

}
