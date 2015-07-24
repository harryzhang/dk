package com.hehenian.login.account;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.IUserInfoService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.LoginInfoRelate;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.identifycode.IIdentifyCodeService;
import com.hehenian.biz.common.util.HttpClientUtils;
import com.hehenian.biz.common.util.Md5Utils;
import com.hehenian.common.constants.HHNConstants;
import com.hehenian.common.session.SessionProvider;
import com.hehenian.common.session.cache.SessionCache;
import com.hehenian.common.utils.ResponseUtils;
import com.hehenian.login.common.BaseController;
import com.hehenian.login.common.constant.WebConstants;

/**
 * @Description 描述方法作用
 * @author huangzl QQ: 272950754
 * @date 2015年5月27日 下午5:33:55
 * @Project hehenian-lend-login
 * @Package com.hehenian.login.account
 * @File LoginController.java
 */
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
	private IPersonService personService;
	@Resource
	private SessionCache sessionCache;
	@Resource
	private SessionProvider session;
	


	// 登录页验证码标识
	private final static String pageId = "userlogin";

	/**
	 * 登录入口
	 * 
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-3-29 上午3:36:11
	 */
	@RequestMapping(value = "index")
	public String index(String fromUrl, ModelMap map, HttpSession sessionS) {
		logger.info("----loginInit(初始化登录页面);fromUrl=" + fromUrl + ";----");
		map.put("fromUrl", fromUrl);
		logger.debug("fromUrl:{}" + fromUrl);
		int loginStrategy = super.getSessionIntAttr("loginStrategy", 0);
		request.setAttribute("loginStrategyInfo", loginStrategy >= 5);
		return "login/login";
	}

	/**
	 * @Description: 登录
	 * @param response
	 * @param user
	 * @return 1用户名或密码未输入|2验证码输入错误
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void login(HttpServletRequest request,HttpServletResponse response,HttpSession sessionS) {
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
		
		int loginStrategy = super.getSessionIntAttr("loginStrategy", 0);
		//密码输入错误5次要求输入验证码
		if(loginStrategy >=5) {
			//验证码是否正确
			String sessionCode = (String) request.getSession().getAttribute(pageId + "_checkCode");
			if(StringUtils.isBlank(code) || !code.equals(sessionCode)){
				jsonObject.put("result", 2);
				ResponseUtils.renderText(response, null,jsonObject.toString());
				return;
			}
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
			request.getSession().setAttribute("loginStrategy", ++loginStrategy);
			jsonObject.put("loginStrategy", loginStrategy >= 5);
			ResponseUtils.renderText(response, null,jsonObject.toString());
			return;
		}
		
		//写表信息 最后登录IP 最后登录时间  登录次数   nnd从来没写过!!!
		aud.setPerson(personService.getByUserId(aud.getId()));
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
	 * 
	 * @param request
	 * @param response
	 * @author: zhanbmf
	 * @date 2015-3-31 下午3:36:21
	 */
	@RequestMapping(value = "loginout")
	public String loginout(HttpServletRequest request, HttpServletResponse response) {
		String root = session.getSessionId(request, response);
		sessionCache.setAttribute(root, HHNConstants.SESSION_INFO, null, HHNConstants.SESSION_CACHE_TIME);
		session.logout(request, response);
		return "redirect:/login/index.do";
	}
	
	/**
	 * 获取彩管家登录认证
	 * @param session
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "getAuth")
	//@ResponseBody
	public String getAuth( String fromUrl, ModelMap map, HttpSession session, HttpServletRequest request) {
		String colorLifeAG_URL = "http://54.223.171.230:8081/v1/auth?"; 
		String COLOR_APP_ID = "ICEHHN00-2206-42D1-96EB-7B843E411934";
		String COLOR_TOKEN ="W06M8Uy2L3N6NC3DIJ7s";
		
		long currentTime  = System.currentTimeMillis()/1000;
		String ts = String.valueOf(currentTime);
		//sign=MD5($appID+$ts+$token+false)
		String sign = Md5Utils.MD5(COLOR_APP_ID+ts+COLOR_TOKEN+"false");
		Map<Object, Object> temp=(Map<Object, Object>) session.getAttribute("parameterMap");
		String[] openId = (String[])temp.get("openID");
		String[] token =(String[])temp.get("accessToken");
		System.out.println("ts="+ts+";sign="+sign+";");
		Map<String,String> params = new HashMap<String,String>(10);
		params.put("openID", openId[0]);
		params.put("accessToken", token[0]);
		
		try {
			StringBuffer url = new StringBuffer();
			url.append(colorLifeAG_URL).append("sign=").append(sign).append("&ts=").append(ts).append("&appID=").append(COLOR_APP_ID);
			String result = HttpClientUtils.post(url.toString(), params);
			logger.info("彩生活认证结果:"+result);
			ObjectMapper mapper = new ObjectMapper();
			Map<Object, Object> params1 = mapper.readValue(result.toString(), new TypeReference<HashMap<Object, Object>>() {});
			int code =Integer.valueOf(params1.get("code").toString());
			if(code==0){
				if(params1.get("content").toString().length()>0){
					Map<Object, Object> contentTemp=(Map<Object, Object>) params1.get("content");
				//注册
				IResult<AccountUserDo> registerResult = userService.register(-1, "cgj_"+contentTemp.get("username").toString(),contentTemp.get("mobile").toString(), DigestUtils.md5Hex(contentTemp.get("mobile").toString() + WebConstants.PASS_KEY), 100,-1L);
				AccountUserDo user = registerResult.getModel();
				if (registerResult.isSuccess()) {
//					userService.updatePerson(user, realName, idNo, mobile);
//					userService.updateColourlifeInfo(user.getId(), Long.valueOf(null == sourceUserId ? "-1" : sourceUserId), cid, cname, caddress);
				}
				//放入session
				session.setAttribute("user", user);
				}else{
					session.setAttribute(WebConstants.MESSAGE_KEY,"财管家数据异常");
					return "common/error";
				}
			}else{
				String message =params1.get("message").toString();
//				String contentEncrypt =params1.get("contentEncrypt").toString();
				session.setAttribute(WebConstants.MESSAGE_KEY,message);
				return "common/error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:"+fromUrl;	
	}
}
