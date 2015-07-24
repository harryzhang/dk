package com.hehenian.manager.modules.sys.dao;

import java.util.List;

import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.model.Module;

public interface ModuleDao {

	/**
	 * 新增模块
	 * @param module
	 * @return
	 */
	public int insertModule(Module module);
	
	/**
	 * 修改模块
	 * @param module
	 * @return
	 */
	public int updateModule(Module module);
	
	/**
	 * 删除模块
	 * @param id
	 * @return
	 */
	public int deleteModule(int id);
	
	/**
	 * 获取用户能访问的模块
	 * @param userId
	 * @return
	 */
	public List<Module> getUserModules(int userId);
	
	/**
	 * 获取所有模块
	 * @return
	 */
	public Pagination<Module> getAllModules(Pagination<Module> page);
	
	/**
	 * 获取所有的模块（不带任何参数）
	 * @return
	 */
	public List<Module> getAllModules();
	
	/**
	 * 根据模块id获取模块
	 * @param moduleId
	 * @return
	 */
	public Module getModuleById(int moduleId);
}
