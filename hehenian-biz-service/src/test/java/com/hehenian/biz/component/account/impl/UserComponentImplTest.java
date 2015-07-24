package com.hehenian.biz.component.account.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.service.BaseTestCase;

public class UserComponentImplTest extends BaseTestCase {

	@Autowired
	private IUserComponent userComponent;

	@Test
	public void testGetUserByColorId() {
		AccountUserDo user = userComponent.getUserByColorId(125l);
		System.out.println(user.getId());
	}

}
