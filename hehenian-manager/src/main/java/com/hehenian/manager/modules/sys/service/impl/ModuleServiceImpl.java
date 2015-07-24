package com.hehenian.manager.modules.sys.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.manager.commons.Constants;
import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.dao.ModuleDao;
import com.hehenian.manager.modules.sys.dao.ResourcesDao;
import com.hehenian.manager.modules.sys.model.Module;
import com.hehenian.manager.modules.sys.model.Resources;
import com.hehenian.manager.modules.sys.service.ModuleService;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	protected ModuleDao moduleDao;
	
	@Autowired
	protected ResourcesDao resourcesDao;
	
	@Override
	public List<Module> getUserModules(int userId) {
		List<Module>  modules=moduleDao.getUserModules(userId);
		Collections.sort(modules);
		for(Module m:modules){
			if("ADMIN".equals(m.getModule())){
				List<Resources> resources=m.getResources();
				Collections.sort(resources, new Comparator<Resources>(){
					@Override
					public int compare(Resources o1, Resources o2) {
						return o1.getOrder()<=o2.getOrder()?-1:1;
					}
					
				});
//				m.setResources(resources);
			}
			
		}
		
		return modules;
	}

	@Override
	public Pagination<Resources> getResources(Pagination<Resources> pagination,String name) {
		Pagination<Resources> datas=resourcesDao.getResourcesInModule(pagination, name);
		return datas;
	}

	@Override
	public List<Module> getAllModules() {
		return moduleDao.getAllModules();
	}

	@Override
	public int saveResources(Resources r) {
		r.setEnabled(true);
		r.setIssys(false);
		r.setIcon(r.getIcon()==null?"icon-nav":r.getIcon());
		
		int ret=-1;
		if(r.getId()!=null){
			ret=resourcesDao.updateResources(r);
		}else{
			ret=resourcesDao.addResources(r);
		}
		if(ret==1){
			return Constants.SUCCESS;
		}else{
			return Constants.FAIL;
		}
	}

	@Override
	public int deleteOneResource(Integer id) {
		int ret=resourcesDao.deleteOneResource(id);
		return ret==1?Constants.SUCCESS:Constants.FAIL;
	}

	@Override
	public Resources getOneResource(Integer id) {
		return resourcesDao.getOneResource(id);
	}

	@Override
	public Pagination<Module> getAllModules(Pagination<Module> pagination) {
		return moduleDao.getAllModules(pagination);
	}

	@Override
	public Module getModuleById(int moduleId) {
		return moduleDao.getModuleById(moduleId);
	}

	@Override
	public int saveModule(Module module) {
		int ret=Constants.FAIL;
		if(module.getId()!=null){
			ret=moduleDao.updateModule(module);
		}else{
			ret=moduleDao.insertModule(module);
		}
		return ret==1?Constants.SUCCESS:Constants.FAIL;
	}

	@Override
	public int deleteModuleById(Integer id) {
		return moduleDao.deleteModule(id);
	}

}
