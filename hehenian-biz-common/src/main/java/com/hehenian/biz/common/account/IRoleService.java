package com.hehenian.biz.common.account;

import java.util.Map;

import com.hehenian.biz.common.account.dataobject.MenuDo;

public interface IRoleService {

	/**
	 * 根据角色ID获取菜单
	 * @param roleId
	 * @return
	 */
	Map<Long,MenuDo> selectRoleMenuList(Long roleId);

}
