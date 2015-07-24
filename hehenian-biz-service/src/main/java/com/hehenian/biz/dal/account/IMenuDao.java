/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.dal.account;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.MenuDo;

public interface IMenuDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public MenuDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<MenuDo> selectMenuList(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateMenuById(MenuDo newMenuDo);
	
	/**
	 * 新增
	 */
	public int addMenu(MenuDo newMenuDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

	/**
	 * 根据角色查询菜单
	 * @param roleId
	 * @return
	 */
	public List<MenuDo> selectRoleMenuList(Long roleId);

	/**
	 * 查询一级菜单
	 * @return
	 */
	public List<MenuDo> selectFirstLevelMenu();

}
