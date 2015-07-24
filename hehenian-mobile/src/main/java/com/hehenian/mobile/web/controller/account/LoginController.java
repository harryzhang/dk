package com.hehenian.mobile.web.controller.account;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hehenian.biz.common.account.IUserInfoService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.LoginInfoRelate;
import com.hehenian.biz.common.identifycode.IIdentifyCodeService;
import com.hehenian.common.constants.HHNConstants;
import com.hehenian.common.session.SessionProvider;
import com.hehenian.common.session.cache.SessionCache;
import com.hehenian.common.utils.ResponseUtils;
import com.hehenian.mobile.common.constants.WebConstants;
import com.hehenian.mobile.web.controller.BaseController;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IIdentifyCodeService identifyCodeService;
	@Autowired
	private IUserInfoService userInfoService;
	@Resource
	private SessionCache sessionCache;
	@Resource
	private SessionProvider session;
	
	//登录页验证码标识
	private final static String pageId = "userlogin";

	/**
	 * 登录入口
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-3-29 上午3:36:11
	 */
	@RequestMapping(value = "index")
	public String index(){
		return "login/login";
	}
	
	/**
	 * 登录
	 * @Description: TODO
	 * @param response
	 * @param user
	 * @return 1用户名或密码未输入|2验证码输入错误 
	 * @author: chenzhpmf
	 * @date 2015-3-29 上午3:48:38
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void login(HttpServletRequest request,HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		String loginInfo = request.getParameter("userName");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		
		//是否输入用户名密码
		if (StringUtils.isBlank(loginInfo) || StringUtils.isBlank(password)){
			 jsonObject.put("result", 1);
			 ResponseUtils.renderText(response, null, jsonObject.toString());
			 return;
		}
		
		//验证码是否正确
		String sessionCode = (String) request.getSession().getAttribute(pageId + "_checkCode");
		if(StringUtils.isBlank(code) || !code.equals(sessionCode)){
			 jsonObject.put("result", 2);
			 ResponseUtils.renderText(response, null,jsonObject.toString());
			 return;
		}
		loginInfo = loginInfo.replaceAll(" ", "");
		//获取登录用户userId
		LoginInfoRelate lir = userInfoService.getByLoginInfo(loginInfo, LoginInfoRelate.class, true);
		AccountUserDo aud = null;
		//存储登录渠道
		String pwdMd5 = DigestUtils.md5Hex(password + WebConstants.PASS_KEY);
		if(lir == null) {
			//如果没有处理老数据，兼容老版本，取一次t_user信息
			aud = userService.loginWithPwd(loginInfo, pwdMd5);
		}else {
			aud = userService.getById(lir.getUserId().longValue());
		}
		
		if(aud == null || !aud.getPassword().equals(pwdMd5)) {
			// 用户名密码错误
			jsonObject.put("result", 3);
			ResponseUtils.renderText(response, null,jsonObject.toString());
			return;
		}
		
		//写表信息 最后登录IP 最后登录时间  登录次数   nnd从来没写过!!!
		
		//userService.  
		request.getSession().setAttribute(HHNConstants.SESSION_INFO, aud);
		// 取缓存登录信息
		String root = session.getSessionId(request, response);
		sessionCache.setAttribute(root, HHNConstants.SESSION_INFO, aud, HHNConstants.SESSION_CACHE_TIME);
		String fromUrl = request.getHeader("referer");
		jsonObject.put("result", 0);
		jsonObject.put("fromUrl", fromUrl);
		
		//
		
		ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @author: zhanbmf
	 * @date 2015-3-31 下午3:36:21
	 */
	@RequestMapping(value = "loginout")
	public String loginout(HttpServletRequest request,HttpServletResponse response) {
		String root = session.getSessionId(request, response);
		sessionCache.setAttribute(root, HHNConstants.SESSION_INFO, null, HHNConstants.SESSION_CACHE_TIME);
		session.logout(request, response);
		return "redirect:/login/index.do";
	}
}
