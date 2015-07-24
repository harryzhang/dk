package com.hehenian.biz.service.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.IRoleService;
import com.hehenian.biz.common.account.dataobject.MenuDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.component.account.IMenuComponent;
import com.hehenian.biz.component.account.IRoleComponent;

@Service("roleService")
public class RoleServiceImpl  implements IRoleService{
	
	@Autowired
	IMenuComponent menuComponent;

	@Override
	public Map<Long,MenuDo> selectRoleMenuList(Long roleId) {
		List<MenuDo>  menuList =  menuComponent.selectRoleMenuList(roleId);
		if(null != menuList){
			return makeTree(menuList);
		}
		return null;
	}
	
	private Map<Long,MenuDo> makeTree(List<MenuDo>  menuList){
		//分配给角色的菜单
		Map<Long,MenuDo> assignedMenuMap = new HashMap<Long,MenuDo>();
		//所有一级菜单
		List<MenuDo> firstLevelMenuList = menuComponent.selectFirstLevelMenu();
		
		Map<Long,MenuDo> menuMap = new HashMap<Long,MenuDo>();
		for(MenuDo menu :firstLevelMenuList){
			//第一级菜单
			menuMap.put(menu.getId(), menu);
		}
		
		//找上级菜单
		for(MenuDo menu : menuList){
			if(null == menu.getParentId() || -999 == menu.getParentId()){
				continue;
			}
			MenuDo parentMenu =  menuMap.get(menu.getParentId());
			if(parentMenu == null){
				throw new BusinessException("错误的菜单配置， 找不菜单的上级菜单， 菜单ID:"+menu.getId());
			}
			parentMenu.addChildMenu(menu);
			assignedMenuMap.put(parentMenu.getId(), parentMenu);
		}
		
		return assignedMenuMap;
		
	}

}
