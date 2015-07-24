package com.hehenian.biz.component.account.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.MenuDo;
import com.hehenian.biz.component.account.IMenuComponent;
import com.hehenian.biz.dal.account.IMenuDao;

@Component("menuComponent")
public class MenuComponentImpl implements IMenuComponent {
	
	@Autowired
	IMenuDao menuDao;

	@Override
	public List<MenuDo> selectRoleMenuList(Long roleId) {
		return menuDao.selectRoleMenuList(roleId);
	}

	@Override
	public List<MenuDo> selectFirstLevelMenu() {
		return menuDao.selectFirstLevelMenu();
	}

}
