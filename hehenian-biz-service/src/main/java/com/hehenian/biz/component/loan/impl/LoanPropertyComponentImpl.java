/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.loan
 * @Title: ILoanDetailComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月11日 上午9:54:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.PropertyDo;
import com.hehenian.biz.component.loan.ILoanPropertyComponent;
import com.hehenian.biz.dal.loan.IPropertyDao;

/**
 * @author xiexiangmf
 *
 */
@Component("loanPropertyComponent")
public class LoanPropertyComponentImpl implements ILoanPropertyComponent{
	
    @Autowired
    private IPropertyDao propertyDao;
	
	@Override
	public PropertyDo getCountByIds(Long id) {
		return propertyDao.getPropertyByIds(id);
	}

	@Override
	public void updateProperty(PropertyDo propertyDo) {
		propertyDao.updateProperty(propertyDo);
	}

	@Override
	public void addProperty(PropertyDo propertyDo) {
		propertyDao.addProperty(propertyDo);
	}

}
