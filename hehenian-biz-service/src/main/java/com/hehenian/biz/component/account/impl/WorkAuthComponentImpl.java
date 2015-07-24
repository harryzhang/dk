/**
 * 
 */
package com.hehenian.biz.component.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.WorkAuth;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.account.IWorkAuthComponent;
import com.hehenian.biz.dal.account.IWorkAuthDao;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.account
 * @Title: WorkAuthComponentImpl
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年4月28日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
@Component("workAuthComponent")
public class WorkAuthComponentImpl implements IWorkAuthComponent {
	
	@Resource
	private IWorkAuthDao workAuth;

	/* (non-Javadoc)
	 * @see com.hehenian.biz.component.account.IWorkAuthComponent#updateWorkAuth(com.hehenian.biz.common.account.dataobject.WorkAuth)
	 */
	@Override
	public int updateWorkAuth(WorkAuth workAuth) throws BusinessException {
		try {
			return this.workAuth.updateWorkAuth(workAuth);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.hehenian.biz.component.account.IWorkAuthComponent#updateWorkAuthByUserId(com.hehenian.biz.common.account.dataobject.WorkAuth)
	 */
	@Override
	public int updateWorkAuthByUserId(WorkAuth workAuth) throws BusinessException {
		try {
			return this.workAuth.updateWorkAuthByUserId(workAuth);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

}
