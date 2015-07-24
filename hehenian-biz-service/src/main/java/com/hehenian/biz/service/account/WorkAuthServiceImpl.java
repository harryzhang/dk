/**
 * 
 */
package com.hehenian.biz.service.account;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.IWorkAuthService;
import com.hehenian.biz.common.account.dataobject.WorkAuth;
import com.hehenian.biz.component.account.IWorkAuthComponent;
import com.hehenian.biz.dal.account.IWorkAuthDao;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.account
 * @Title: WorkAutoServiceImpl
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年4月28日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
@Service("workAutoService")
public class WorkAuthServiceImpl implements IWorkAuthService {
	
	@Resource
	private IWorkAuthComponent workAuthComponent;

	/* (non-Javadoc)
	 * @see com.hehenian.biz.common.account.IWorkAuthService#updateWorkAuth(com.hehenian.biz.common.account.dataobject.WorkAuth)
	 */
	@Override
	public int updateWorkAuth(WorkAuth workAuth) {
		return this.workAuthComponent.updateWorkAuth(workAuth);
	}

	/* (non-Javadoc)
	 * @see com.hehenian.biz.common.account.IWorkAuthService#updateWorkAuthByUserId(com.hehenian.biz.common.account.dataobject.WorkAuth)
	 */
	@Override
	public int updateWorkAuthByUserId(WorkAuth workAuth) {
		return this.workAuthComponent.updateWorkAuthByUserId(workAuth);
	}
}
