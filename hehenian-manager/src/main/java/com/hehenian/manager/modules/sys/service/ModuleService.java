package com.hehenian.manager.modules.sys.service;

import java.util.List;

import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.model.Module;
import com.hehenian.manager.modules.sys.model.Resources;

public interface ModuleService {

	public List<Module> getUserModules(int userId);
	
	/**
	 * 获取菜单
	 * @param pagination
	 * @return
	 */
	public Pagination<Resources> getResources(Pagination<Resources> pagination,String name);
	
	
	
	/**
	 * 获取系统所有的模块
	 * @return
	 */
	public List<Module> getAllModules();
	
	
	/**
	 * 新增菜单
	 * @param r
	 * @return
	 */
	public int saveResources(Resources r);
	
	/**
	 * 删除一个菜单项
	 * @param id
	 * @return
	 */
	public int deleteOneResource(Integer id);
	
	/**
	 * 获取菜单
	 * @param id
	 * @return
	 */
	public Resources getOneResource(Integer id);
	
	/**
	 * 
	 * @param pagination
	 * @param name
	 * @return
	 */
	public Pagination<Module> getAllModules(Pagination<Module> pagination);
	
	/**
	 * 根据模块id获取模块
	 * @param moduleId
	 * @return
	 */
	public Module getModuleById(int moduleId);
	
	/**
	 * 保存模块
	 * @param module
	 * @return
	 */
	public int saveModule(Module module);
	
	/**
	 * 删除一个模块
	 * @param id
	 * @return
	 */
	public int deleteModuleById(Integer id);
}
