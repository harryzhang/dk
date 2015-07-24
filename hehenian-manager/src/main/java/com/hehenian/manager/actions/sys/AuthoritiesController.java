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
import com.hehenian.manager.commons.Constants;
import com.hehenian.manager.commons.Pagination;
import com.hehenian.manager.modules.sys.model.Authorities;
import com.hehenian.manager.modules.sys.model.AuthorityResources;
import com.hehenian.manager.modules.sys.model.Resources;
import com.hehenian.manager.modules.sys.model.RolesAuthority;
import com.hehenian.manager.modules.sys.service.AuthorityService;
import com.hehenian.manager.modules.users.model.Roles;
import com.hehenian.manager.modules.users.service.UserService;

@Controller
@RequestMapping("/authority/*")
public class AuthoritiesController extends BaseAction {

	@Autowired
	protected AuthorityService authorityService;
	
	@Autowired
	protected UserService userService;
	
	@RequestMapping("authorityIndex")
	public String authorityIndex(ModelMap modelMap){
		return "/authority/authorityIndex";
	}
	
	/**
	 * 权限列表
	 * @param pagination
	 * @param request
	 * @param response
	 */
	@RequestMapping("authoritiesList")
	@ResponseBody
	public void listDatas(Pagination<Authorities> pagination,HttpServletRequest request,
			HttpServletResponse response){
		String name=getString("name");
		pagination=authorityService.getAuthority(pagination,name);
		outPrint(response, JSONObject.fromObject(pagination, getDefaultJsonConfig()));
	}
	
	/**
	 * 修改权限
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("editAuthority")
	public String editAuthority(ModelMap modelMap){
		int authId=getInt("authId");
		if(authId!=-1){
			Authorities auth=authorityService.getAuthorities(authId);
			modelMap.put("auth", auth);
		}
		return "/authority/editAuthority";
	}
	
	
	/**
	 * 保存权限
	 * @param auth
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveAuthority")
	@ResponseBody
	public void saveAuthority(@ModelAttribute Authorities auth,HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtils.isBlank(auth.getName()) || !auth.getName().matches("ROLE_(.*)")){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return;
		}
		int ret=authorityService.saveAuthority(auth);
		ResponseUtils.renderJson(response, null,"{\"ret\":"+ret+"}");
	}
	
	
	@RequestMapping("deleteOneAuth/{authId}")
	@ResponseBody
	public void deleteAuthority(@PathVariable Integer authId,HttpServletRequest request,
			HttpServletResponse response){
		if(authId==null){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return;
		}
		int ret=authorityService.deleteOneAuthority(authId);
		ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
	}
	
	@RequestMapping("bindResources/{authId}")
	public String bindResources(@PathVariable String authId,ModelMap modelMap){
		if(StringUtils.isBlank(authId) ||!authId.matches("\\d+")){
			return Constants.INVALIDPAGE;
		}
		Authorities auth=authorityService.getAuthorities(Integer.parseInt(authId));
		if(auth==null){
			return Constants.INVALIDPAGE;
		}else{
			modelMap.put("auth", auth);
			return "/authority/bindResources";
		}
	}
	
	
	/**
	 * 获取在权限中的菜单
	 * @param page
	 * @param request
	 * @param response
	 */
	@RequestMapping("resourcesInAuthorities")
	@ResponseBody
	public void resourcesInAuthorities(Pagination<Resources> page,HttpServletRequest request,
			HttpServletResponse response){
		int authorityId=getInt("authorityId");
		int inOrNot=getInt("inOrNot");
		Pagination<Resources> pagination=authorityService.getResourcesInOrNotAuthority(page, authorityId, inOrNot==1);
		outPrint(response, JSONObject.fromObject(pagination,
				getDefaultJsonConfig()));
	}
	
	/**
	 * 新增权限菜单关系
	 * @param ar
	 * @param request
	 * @param response
	 */
	@RequestMapping("addResource2Authority")
	@ResponseBody
	public void addResource2Authority(@ModelAttribute AuthorityResources ar,HttpServletRequest request,
			HttpServletResponse response){
		if(!checkRelation(ar)){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return ;
		}
		ar.setEnabled(true);
		int ret=authorityService.addAuthorityResource(ar);
		ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
	}
	
	
	/**
	 * 删除权限菜单对应关系
	 * @param ar
	 * @param request
	 * @param response
	 */
	@RequestMapping("deleteResource2Authority")
	@ResponseBody
	public void deleteResource2Authority(@ModelAttribute AuthorityResources ar,HttpServletRequest request,
			HttpServletResponse response){
		if(!checkRelation(ar)){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return ;
		}
		int ret=authorityService.deleteAuthorityResource(ar);
		ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
	}
	
	
	private boolean checkRelation(AuthorityResources ar){
		if(ar.getResourceId()==null || ar.getAuthorityId()==null){
			return false;
		}
		return true;
	}
	
	
	@RequestMapping("setRolesIntoAuthorityDialog/{authId}")
	public String setRolesIntoAuthorityDialog(@PathVariable String authId,ModelMap modelMap){
		if(StringUtils.isBlank(authId) || !authId.matches("\\d+")){
			return Constants.INVALIDPAGE;
		}
		Authorities auth=authorityService.getAuthorities(Integer.parseInt(authId));
		if(auth==null){
			return Constants.INVALIDPAGE;
		}
		modelMap.put("auth", auth);
		return "/authority/setRolesIntoAuthorityDialog";
	}
	
	@RequestMapping("rolesInAuthority")
	@ResponseBody
	public void rolesInAuthority(Pagination<Roles> page,HttpServletRequest request,
			HttpServletResponse response){
		int authId=getInt("authId");
		int inOrNot=getInt("inOrNot");
		if(authId==-1 || inOrNot==-1){
			return;
		}
		Pagination<Roles> pagination=userService.getRolesInOrNotInAuthorities(page, authId, inOrNot==1);
		outPrint(response, JSONObject.fromObject(pagination,
				getDefaultJsonConfig()));
	}
	
	
	@RequestMapping("addRoles2Authority")
	@ResponseBody
	public void addRoles2Authority(@ModelAttribute RolesAuthority ra,HttpServletRequest request,
			HttpServletResponse response){
		if(!checkRolesAuthority(ra)){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return ;
		}
		ra.setEnabled(true);
		int ret=userService.addRolesAuthority(ra);
		ResponseUtils.renderJson(response, null, "{\"ret\":" +ret+"}");
	}
	
	
	@RequestMapping("deleteRoles2Authority")
	@ResponseBody
	public void deleteRoles2Authority(@ModelAttribute RolesAuthority ra,HttpServletRequest request,
			HttpServletResponse response){
		if(!checkRolesAuthority(ra)){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return ;
		}
		int ret=userService.deleteRolesAuthority(ra);
		ResponseUtils.renderJson(response, null, "{\"ret\":"+ret+"}");
	}
	
	private boolean checkRolesAuthority(RolesAuthority ra){
		if(ra.getAuthorityId()!=null && ra.getRoleId()!=null){
			return true;
		}
		return false;
	}
	
}
