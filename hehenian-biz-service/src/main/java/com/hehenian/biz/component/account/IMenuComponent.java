package com.hehenian.biz.component.account;

import java.util.List;

import com.hehenian.biz.common.account.dataobject.MenuDo;

public interface IMenuComponent {
	
	/**
	 * 根据角色ID查询菜单
	 * @param roleId
	 * @return
	 */
	List<MenuDo> selectRoleMenuList(Long roleId);

	/**
	 * 查询一级菜单
	 * @return
	 */
	List<MenuDo> selectFirstLevelMenu();
	
	
}
