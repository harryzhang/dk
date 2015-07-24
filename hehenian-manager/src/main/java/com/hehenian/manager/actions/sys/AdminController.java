package com.hehenian.manager.actions.sys;

import java.util.List;

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
import com.hehenian.manager.commons.Constants;
import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.model.Authorities;
import com.hehenian.manager.modules.sys.model.Module;
import com.hehenian.manager.modules.sys.model.Resources;
import com.hehenian.manager.modules.sys.model.UsersRoles;
import com.hehenian.manager.modules.sys.service.AuthorityService;
import com.hehenian.manager.modules.sys.service.ModuleService;
import com.hehenian.manager.modules.users.model.Managers;
import com.hehenian.manager.modules.users.model.Roles;
import com.hehenian.manager.modules.users.service.UserService;

@Controller
@RequestMapping("/menuAdmin/*")
public class AdminController extends BaseAction {

	@Autowired
	protected UserService userService;
	
	@Autowired
	protected ModuleService moduleService;
	
	@Autowired
	protected AuthorityService authorityService;
	
	@RequestMapping("index")
	public String menuAdmin(ModelMap modelMap){
		return "/menuAdmin/index";
	}
	
	@RequestMapping("listDatas")
	@ResponseBody
	public void menuDatas(Pagination<Resources> pagination,HttpServletRequest request,
			HttpServletResponse response){
		Pagination<Resources> usersList=moduleService.getResources(pagination, "");
		outPrint(response, JSONObject.fromObject(usersList,
				getDefaultJsonConfig()));
	}
	
	@RequestMapping("addMenuDialog")
	public String addMenuDialog(ModelMap modelMap){
		int id=getInt("id");
		if(id!=-1){
			Resources r=moduleService.getOneResource(id);
			if(r!=null){
				modelMap.put("resource", r);
			}
		}
		List<Module> moduleList=moduleService.getAllModules();
		modelMap.put("modules", moduleList);
		return "/menuAdmin/addMenuDialog";
	}
	
	/**
	 * 新增菜单
	 * @param user
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("addMenu")
	public void addMenu(@ModelAttribute Resources r,HttpServletRequest request,
			HttpServletResponse response){
		if(!validateResources(r)){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1,\"msg\":\"新增菜单失败\"}");
			return;
		}
		int ret=moduleService.saveResources(r);
		if(ret==Constants.SUCCESS){
			ResponseUtils.renderJson(response, null,"{\"ret\":1,\"msg\":\"新增菜单成功\"}");
		}else{
			ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"新增菜单失败\"}");
		}
	}

	private boolean validateResources(Resources r){
		if(StringUtils.isBlank(r.getName())){
			return false;
		}
		if(StringUtils.isBlank(r.getResourceStr())){
			return false;
		}
		if(StringUtils.isBlank(r.getModule())){
			return false;
		}
		return true;
	}
	
	
	@ResponseBody
	@RequestMapping("deleteOneResource")
	public void deleteOneResource(HttpServletRequest request,
			HttpServletResponse response){
		String id=getString("id");
		if(StringUtils.isNotBlank(id) && id.matches("\\d+")){
			int ret=moduleService.deleteOneResource(Integer.parseInt(id));
			ResponseUtils.renderJson(response, null,"{\"ret\":"+ret+"}");
		}else{
			ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"删除失败\"}");
		}
	}
	
	@RequestMapping("userAdmin")
	public String userAdmin(ModelMap modelMap){
		return "/userAdmin/index";
	}
	
	@ResponseBody
	@RequestMapping("userDatas")
	public void userDatas(Pagination<Managers> pagination,HttpServletRequest request,
			HttpServletResponse response ){
		Pagination<Managers> usersList=userService.getManagersByPage(pagination, "");
		outPrint(response, JSONObject.fromObject(usersList,
				getDefaultJsonConfig()));
	}
	
	
	@RequestMapping("addUserDialog")
	public String addUserDialog(ModelMap modelMap){
		int userId=getInt("userId");
		if(userId!=-1){
			Managers user=userService.getUserById(userId);
			modelMap.put("user", user);
		}
		return "/userAdmin/addUserDialog";
	}
	
	/**
	 * 新增用户
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("addUser")
	public void addUser(@ModelAttribute Managers user,HttpServletRequest request,
			HttpServletResponse response){
		if(!validateUserInfo(user)){
			ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"新增用户失败\"}");
			return;
		}
		int ret=userService.addUsers(user);
		if(ret==1){
			ResponseUtils.renderJson(response, null,"{\"ret\":1,\"msg\":\"新增用户成功\"}");
		}else{
			ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"新增用户失败\"}");
		}
	}
	
	/**
	 * 验证新增用户
	 * @param user
	 * @return
	 */
	public boolean validateUserInfo(Managers user){
		if(StringUtils.isBlank(user.getUsername())){
			return false;
		}
		if(StringUtils.isBlank(user.getPassword())){
			return false;
		}
		if(StringUtils.isBlank(user.getNickname())){
			return false;
		}
		return true;
	}
	
	@RequestMapping("deleteUser/{userId}")
	@ResponseBody
	public void deleteOneUser(@PathVariable String userId,HttpServletRequest request,
			HttpServletResponse response){
		if (StringUtils.isBlank(userId) || !userId.matches("\\d+")) {
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return;
		}
		int ret = userService.deleteOneUser(Integer.parseInt(userId));
		ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
	}
	
	@RequestMapping("rolesIndex")
	public String rolesIndex(ModelMap modelMap){
		return "/roleAdmin/rolesIndex";
	}
	
	
	/**
	 * 角色列表
	 * @param pagination
	 * @param request
	 * @param response
	 */
	@RequestMapping("roleList")
	public void roleDatas(Pagination<Roles> pagination,HttpServletRequest request,
			HttpServletResponse response){
		String roleName=getString("roleName");
		Pagination<Roles> roleList=userService.getRolesByPage(pagination, roleName);
		outPrint(response, JSONObject.fromObject(roleList,
				getDefaultJsonConfig()));
	}
	
	@RequestMapping("addRoleDialog")
	public String addRoleDialog(ModelMap modelMap){
		int roleId=getInt("roleId");
		if(roleId!=-1){
			Roles role=userService.getOneRoleById(roleId);
			modelMap.put("role", role);
		}
		return "/roleAdmin/addRoleDialog";
	}
	
	/**
	 * 新增或者修改角色
	 * @param role
	 * @param request
	 * @param response
	 */
	@RequestMapping("addOneRole")
	@ResponseBody
	public void addOneRole(@ModelAttribute Roles role,HttpServletRequest request,
			HttpServletResponse response){
		if(!validateRole(role)){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1,\"msg\":\"新增角色失败\"}");
			return;
		}
		int ret = userService.updateOneRole(role);
		ResponseUtils.renderJson(response, null, "{\"ret\":"+ret+",\"msg\":\"新增角色失败\"}");
	}
	
	private boolean validateRole(Roles role){
		if(StringUtils.isBlank(role.getName())){
			return false;
		}
		if(StringUtils.isBlank(role.getRoleDesc())){
			return false;
		}
		return true;
	}
	
	/**
	 * 删除一角色
	 * @param roleId
	 * @param request
	 * @param response
	 */
	@RequestMapping("deleteOneRole/{roleId}")
	@ResponseBody
	public void deleteOneRole(@PathVariable String roleId,HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtils.isNotBlank(roleId) && roleId.matches("\\d+")){
			int ret=userService.deleteOneRole(Integer.parseInt(roleId));
			ResponseUtils.renderJson(response, null,"{\"ret\":"+ret+"}");
		}
	}
	
	
	/**
	 * 显示角色权限
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("showRolesAuthorityDialog/{roleId}")
	public String showRolesAuthorityDialog(@PathVariable String roleId,ModelMap modelMap){
		if(StringUtils.isNotBlank(roleId) && roleId.matches("\\d+")){
			Roles role=userService.getOneRoleById(Integer.parseInt(roleId));
			modelMap.put("role", role);
		}
		return "/roleAdmin/showRolesAuthorityDialog";
	}
	
	
	/**
	 * 获取在角色下或者不在角色下的权限
	 * @param pagination
	 * @param request
	 * @param response
	 */
	@RequestMapping("authoritiesInRolesOrNot")
	@ResponseBody
	public void getInOrNotAuthoritiesByRoleId(Pagination<Authorities> pagination,HttpServletRequest request,
			HttpServletResponse response){
		int roleId=getInt("roleId");
		int inRoleOrNot=getInt("inRoleOrNot");
		Pagination<Authorities> authList=authorityService.getInOrNotAuthoritiesByRoleId(pagination, roleId, inRoleOrNot==1);
		outPrint(response, JSONObject.fromObject(authList,
				getDefaultJsonConfig()));
	}
	
	/**
	 * 角色绑定用户的方法
	 * @param roleId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("rolesBindUsers/{roleId}")
	public String rolesBindUsers(@PathVariable String roleId,ModelMap modelMap){
		if(StringUtils.isBlank(roleId) ||!roleId.matches("\\d+")){
			return Constants.INVALIDPAGE;
		}
		Roles role=userService.getOneRoleById(Integer.parseInt(roleId));
		if(role==null){
			return Constants.INVALIDPAGE;
		}else{
			modelMap.put("role", role);
			return "/roleAdmin/rolesBindUsers";
		}
	}
	
	
	@RequestMapping("users2Roles")
	@ResponseBody
	public void users2Roles(Pagination<Managers> page,HttpServletRequest request,
			HttpServletResponse response){
		int roleId=getInt("roleId");
		int inOrNot=getInt("inOrNot");
		//角色ID不为空，并且制定是在角色内或者非该角色标识才进行查询
		if(roleId!=-1 && inOrNot!=-1){
			Pagination<Managers> list=userService.getManagersInOrNotInRoles(page, roleId, inOrNot==1);
			outPrint(response, JSONObject.fromObject(list,
					getDefaultJsonConfig()));
		}
	}
	
	
	@RequestMapping("addUserRoles")
	@ResponseBody
	public void addUsersRoles(@ModelAttribute UsersRoles userRoles,HttpServletRequest request,
			HttpServletResponse response){
		if(!checkUsersRoles(userRoles)){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return;
		}
		userRoles.setEnabled(true);
		int ret=userService.addUsersRoles(userRoles);
		ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
	}
	
	
	@RequestMapping("deleteUserRoles")
	@ResponseBody
	public void deleteUsersRoles(@ModelAttribute UsersRoles userRoles,HttpServletRequest request,
			HttpServletResponse response){
		if(!checkUsersRoles(userRoles)){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return;
		}
		int ret=userService.deleteUsersRoles(userRoles);
		ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
	}
	
	
	private boolean checkUsersRoles(UsersRoles ur){
		if(ur.getRoleId()!=null && ur.getUserId()!=null){
			return true;
		}
		return false;
	}
}

