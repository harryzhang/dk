package com.hehenian.manager.actions.login;
 
import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hehenian.common.utils.ResponseUtils;
import com.hehenian.manager.actions.BaseAction;
import com.hehenian.manager.modules.sys.model.UserInfos;
import com.hehenian.manager.modules.users.service.UserService;
 
@Controller
@RequestMapping("/auth/*")
public class LoginController extends BaseAction{
 
	@Resource
	protected UserService userService;
	
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal ) {
		
		String name = principal.getName();
		
		model.addAttribute("username", name);
		model.addAttribute("message", "Spring Security Custom Form example");
		return "/login/hello";
 
	}
	
	@RequestMapping("accessDenied")
	public String accessDenied(){
		return "/login/accessDenied";
	}
 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "/login/login";
	}
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		model.addAttribute("error", "true");
		return "/login/login";
 
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		return "/login/login";
 
	}
	
	@RequestMapping("resetLoginUserPwd")
	@ResponseBody
	public void resetLoginUserPwd(HttpServletRequest request,
			HttpServletResponse response){
		
		String oldPassword=getString("oldPassword");
		String newPassword=getString("newPassword");
		String newPasswordAgain=getString("newPasswordAgain");
		//旧密码、新密码都不能为空，确认密码必须等于新密码
		if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword) 
				|| StringUtils.isBlank(newPasswordAgain)
				|| !newPassword.equals(newPasswordAgain)){
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return;
		}
		UserInfos currentUser=getUserInfos();
		int ret=userService.resetCurrentUserPwd(currentUser.getUserId(), oldPassword, newPassword);
		ResponseUtils.renderJson(response, null, "{\"ret\":"+ret+"}");
	}
	
}