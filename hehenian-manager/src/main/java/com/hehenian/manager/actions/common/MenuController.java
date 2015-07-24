package com.hehenian.manager.actions.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.hehenian.manager.actions.BaseAction;
import com.hehenian.manager.modules.sys.model.Module;
import com.hehenian.manager.modules.sys.service.ModuleService;

@Controller
@RequestMapping("/menu/*")
public class MenuController extends BaseAction {

	@Autowired
	protected ModuleService moduleService;
	
	
	@RequestMapping("index")
	public String menuDemo(ModelMap modelMap){
		modelMap.put("user", getUserInfos());
		return "/menus/index";
	}
	
	/**
	 * 获取用户可访问的菜单
	 */
	@ResponseBody
	@RequestMapping("getMenus")
	public void getMenus(HttpServletRequest request,
			HttpServletResponse response){
		int userId=getUserId();
		List<Module> modules=moduleService.getUserModules(userId);
		
		outPrint(response, JSONArray.toJSON(modules));
	}
}
