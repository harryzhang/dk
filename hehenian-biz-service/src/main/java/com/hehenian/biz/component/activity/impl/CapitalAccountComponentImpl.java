/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.component.activity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.activity.dataobject.CapitalAccountDo;
import com.hehenian.biz.component.activity.ICapitalAccountComponent;
import com.hehenian.biz.dal.activity.ICapitalAccountDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("capitalAccountComponent")
public class CapitalAccountComponentImpl implements ICapitalAccountComponent {

	@Autowired
    private ICapitalAccountDao  capitalAccountDao;

	/**
	 * 新增
	 */
	public int addCapitalAccount(CapitalAccountDo newCapitalAccountDo){
		return capitalAccountDao.addCapitalAccount(newCapitalAccountDo);
	}
	
}
