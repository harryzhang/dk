package com.hehenian.manager.actions.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.common.utils.ResponseUtils;
import com.hehenian.manager.actions.BaseAction;
import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.model.Module;
import com.hehenian.manager.modules.sys.service.ModuleService;

@Controller
@RequestMapping("/module/*")
public class ModuleController extends BaseAction {

	@Autowired
	protected ModuleService moduleService;
	
	@RequestMapping("moduleIndex")
	public String moduleIndex(ModelMap modelMap){
		return "/module/moduleIndex";
	}
	
	@RequestMapping("moduleDatas")
	@ResponseBody
	public void moduleDatas(Pagination<Module> modules,HttpServletRequest request,
			HttpServletResponse response){
		modules=moduleService.getAllModules(modules);
		outPrint(response, JSONObject.fromObject(modules,
				getDefaultJsonConfig()));
	}
	
	
	@RequestMapping("editModuleDialog")
	public String addModuleDialog(ModelMap modelMap){
		int id=getInt("moduleId");
		if(id!=-1){
			Module module=moduleService.getModuleById(id);
			modelMap.put("module", module);
		}
		return "/module/addModuleDialog";
	}
	
	
	@RequestMapping("saveModule")
	@ResponseBody
	public void saveModule(@ModelAttribute Module module,HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtils.isBlank(module.getName())){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return;
		}
		if(StringUtils.isBlank(module.getModule())){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return;
		}
		int ret=moduleService.saveModule(module);
		ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
	}
	
	@RequestMapping("deleteModule/{moduleId}")
	@ResponseBody
	public void deleteModule(@PathVariable Integer moduleId,HttpServletRequest request,
			HttpServletResponse response){
		if(moduleId!=null){
			int ret=moduleService.deleteModuleById(moduleId);
			ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
			return;
		}
		ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
	}
}
