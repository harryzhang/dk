package com.hehenian.mobile.web.controller.account;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.IPhoneVerifyService;
import com.hehenian.biz.common.account.IUserInfoService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.InviteCodeDo;
import com.hehenian.biz.common.account.dataobject.LoginInfoRelate;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.account.dataobject.PhoneVerifyDo;
import com.hehenian.biz.common.account.dataobject.UserBindDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.identifycode.IIdentifyCodeService;
import com.hehenian.biz.common.trade.IOperationLogService;
import com.hehenian.biz.common.util.huifu.IConstants;
import com.hehenian.common.annotations.RequireLogin;
import com.hehenian.common.constants.HHNConstants;
import com.hehenian.common.session.SessionProvider;
import com.hehenian.common.session.cache.SessionCache;
import com.hehenian.common.utils.IDCardUtil;
import com.hehenian.common.utils.ResponseUtils;
import com.hehenian.mobile.common.constants.WebConstants;
import com.hehenian.mobile.web.controller.BaseController;
import com.hhn.util.BaseReturn;


@Controller
@RequestMapping(value = "/account")
public class AccountController extends BaseController {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IIdentifyCodeService identifyCodeService;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IOperationLogService operationLogService;
	@Resource
	private SessionCache sessionCache;
	@Resource
	private SessionProvider session;
	@Resource
	private IPhoneVerifyService phoneVerifyService;

	/**
	 * 注册入口
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-3-29 上午4:08:40
	 */
	@RequestMapping(value = "regIndex")
	public String regIndex(){
		return "login/register";
	}
	
	/**
	 * 注册协议
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-4-4 下午6:04:10
	 */
	@RequestMapping(value = "zcxy")
	public String zcxy(){
		return "login/zcxy";
	}
	
	/**
	 * 校验用户名是否重复（注册）
	 * @param request
	 * @param response
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-3-29 上午4:39:27
	 */
	@RequestMapping(value = "checkRegister",method = RequestMethod.POST)
	public void checkRegister(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result = new HashMap<String, Object>();
		String userName = request.getParameter("userName");
		if(StringUtils.isBlank(userName)){
			result.put("result", 1);
		}else{
			AccountUserDo accountUser = userService.findUserByUserName(userName);
			result.put("result", accountUser!=null?2:0);
		}
		ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(result).toString());
	}
	
	
	/**
	 * 用户注册
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-3-29 上午5:06:48
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public void register(HttpServletRequest request,HttpServletResponse response){
		String loginInfo = request.getParameter("userName");
		String mobilePhone = request.getParameter("mobilePhone");
		String pwd = request.getParameter("password");
		
		JSONObject jsonObject = new JSONObject();
		if(StringUtils.isBlank(loginInfo)){
			jsonObject.put("result", "请输入登录用户名");
        	ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(jsonObject).toString());
        	return;
		}
		if(StringUtils.isBlank(pwd)){
			jsonObject.put("result", "请设置您的密码");
        	ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(jsonObject).toString());
        	return;
		}
		if(StringUtils.isBlank(mobilePhone)){
			jsonObject.put("result", "请输入手机号");
        	ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(jsonObject).toString());
        	return;
		}
        //手机验证码校验
        String identifyCode =  request.getParameter("identifyCode");
        boolean flag = identifyCodeService.checkIdentifyCode(mobilePhone, identifyCode);
        if (!flag){
            //手机验证码校验不通过
        	jsonObject.put("result", "手机验证码不正确");
        	ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(jsonObject).toString());
        	return;
        }
        
		AccountUserDo aud = new AccountUserDo();
		aud.setUsername(loginInfo.trim());
		aud.setMobilePhone(mobilePhone.trim());
        String pwdMd5 = DigestUtils.md5Hex(pwd + WebConstants.PASS_KEY);
        aud.setPassword(pwdMd5);
        Date now = new Date();
        aud.setCreateTime(now);
        aud.setSource(12); //物业国际
        IResult result = userService.registerUser(aud);
        if (result.isSuccess()) {
        	Long userId = (Long)result.getModel();
          //获取登录用户userId
    		LoginInfoRelate loginir = userInfoService.getByLoginInfo(loginInfo, LoginInfoRelate.class, true);
    		if(loginir == null) {
    			//如果没有处理老数据，兼容老版本，取一次t_user信息
    			aud = userService.loginWithPwd(loginInfo, pwdMd5);
    		}else {
    			aud = userService.getById(userId);
    		}
    		request.getSession().setAttribute("user", aud);
    		// 取缓存登录信息
    		String root = session.getSessionId(request, response);
    		sessionCache.setAttribute(root, HHNConstants.SESSION_INFO, aud, HHNConstants.SESSION_CACHE_TIME);
            try {
                int partnerId = getSessionIntAttr("partnerId",0);
                String partnerUserId = getSessionStrAttr("partnerUserId");
                if (partnerId>0&&StringUtils.isNotBlank(partnerUserId)){
                    UserBindDo userBindDo = new UserBindDo();
                    userBindDo.setPartnerId(partnerId);
                    userBindDo.setPartnerUserId(partnerUserId);
                    userBindDo.setCreateTime(new Date());
                    userBindDo.setUserId(aud.getId());
                    userService.saveUserBind(userBindDo);
                }
            }catch (Exception e){
            	logger.error(e.getMessage());
            	ResponseUtils.renderText(response, null, JSONObject.fromObject(new BaseReturn(1,"系统正忙请稍后重试")).toString());
            	return;
            }
            jsonObject.put("result", "注册成功");
            ResponseUtils.renderText(response, null, JSONObject.fromObject(jsonObject).toString());
        } else {
        	jsonObject.put("result", result.getErrorMessage());
            ResponseUtils.renderText(response, null, JSONObject.fromObject(jsonObject).toString());
        }
	}
	
    /**
     * 重置密码第一步入口
     * @Description: TODO
     * @return
     * @author: chenzhpmf
     * @date 2015-3-27 下午3:40:16
     */
    @RequestMapping(value = "resetPwdIndex")
    public String resetPwdIndex(HttpServletRequest request){
    	AccountUserDo audo = userService.getById(super.getUserId());
    	String pwdFlag = request.getParameter("pwdFlag");
    	request.setAttribute("pwdFlag", pwdFlag);
    	if (audo != null) {
    		request.setAttribute("mobile", audo.getMobilePhone());
    	}
    	return "login/resetPwd";
    }
    
    @RequestMapping(value = "resetPwdStep")
    public String resetPwdStep(){
    	String pwdFlag = request.getParameter("pwdFlag");
    	request.setAttribute("pwdFlag", pwdFlag);
    	return "login/resetPwdStep";
    }
    
    @RequestMapping(value = "reInputPwd")
    public String reInputPwd(){
    	String pwdFlag = request.getParameter("pwdFlag");
    	request.setAttribute("pwdFlag", pwdFlag);
    	return "login/reInputPwd";
    }
    
    /**
     * 支付密码修改成功
     */
    @RequestMapping(value = "resetPaySucc")
    public String resetPaySucc(){
    	return "login/resetSucc";
    }
    
    /**
     * 验证输入支付密码是否正确
     */
    @RequestMapping(value = "checkPayPwd")
    public void checkPayPwd(HttpServletRequest request,HttpServletResponse response){
    	BaseReturn br = new BaseReturn();
    	br.setReturnCode(0);
    	String password = request.getParameter("pwd");//支付密码
    	AccountUserDo audo = userService.getById(super.getUserId());
    	String newPass = com.shove.security.Encrypt.MD5(password+WebConstants.PASS_KEY);
    	if(!audo.getPayPassword().equals(newPass)){
    		br.setReturnCode(1);
			br.setMessageInfo("您输入的支付密码不正确，请重新输入.");
    	}
    	ResponseUtils.renderText(response, null, JSONObject.fromObject(br).toString());
    }
    
    
    /**
     * 验证密码第一步（手机验证）
     * @Description: TODO
     * @param request
     * @param response
     * @author: chenzhpmf
     * @date 2015-3-27 下午3:40:47
     */
    @RequestMapping(value = "verifyPwdStep1")
    public void verifyPwdStep1(HttpServletRequest request,HttpServletResponse response){
    	Map<String,Object> result = new HashMap<String, Object>();
        String identifyCode = request.getParameter("identifyCode");
        String mobilePhone = request.getParameter("mobilePhone");
        boolean verifyResult = identifyCodeService.checkIdentifyCode(mobilePhone, identifyCode);
        if (verifyResult){
        	//手机验证通过后判断是否需要身份验证
        	result.put("result", 0);
        	AccountUserDo user = userService.findUserByPhone(mobilePhone);
        	if(user==null){
        		result.put("result", 2);
        		ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(result).toString());
                return;
        	}
        	request.getSession().setAttribute(WebConstants.USER_ID, user.getId());
        }else {
        	result.put("result", 1);
        }
        ResponseUtils.renderText(response, "UTF-8", JSONObject.fromObject(result).toString());
    }
    
    @RequestMapping(value = "resetJumpPage")
    public String resetJumpPage(HttpServletRequest request){
    	String pwdFlag = request.getParameter("pwdFlag");
    	
    	Object userObject = request.getSession().getAttribute(WebConstants.USER_ID);
    	Long userId = -1l;
    	if(userObject!=null){
    		userId = (Long)userObject;
    	}
    	PersonDo person = personService.getByUserId(userId);
        if(person!=null){
        	//身份验证
        	if(StringUtils.isBlank(person.getIdNo()) && StringUtils.isBlank(person.getRealName())){
        		return "redirect:/account/reInputPwd.do?pwdFlag="+pwdFlag;
        	}
        }
        return "redirect:/account/resetPwdStep.do?pwdFlag="+pwdFlag;
    }
    
    /**
     * 验证密码第二步（身份验证）
     * @Description: TODO
     * @param request
     * @param response
     * @author: chenzhpmf
     * @date 2015-3-27 下午3:40:47
     */
    @RequestMapping(value = "verifyPwdStep2")
    public void verifyPwdStep2(HttpServletRequest request,HttpServletResponse response){
    	JSONObject jsonObject = new JSONObject();
		Object userObject = request.getSession().getAttribute(WebConstants.USER_ID);
	    Long userId = -1l;
	    if(userObject==null){
	    	jsonObject.put("result",1); //会话信息失效
    		ResponseUtils.renderText(response, null, jsonObject.toString());
    		return;
	    }
	    userId = (Long)userObject;
        String realName = request.getParameter("realName"); //真实姓名
        
        String idNo = request.getParameter("idNo"); //身份证号
        PersonDo person = personService.getByUserId(userId);
        if(person!=null){
        	//身份验证
        	if(person.getIdNo().equals(idNo.trim())&&person.getRealName().equals(realName.trim())){
        		jsonObject.put("result",0);
        	}else{
        		jsonObject.put("result",2);
        		request.getSession().setAttribute(WebConstants.USER_ID, userId);
        	}
        }
        ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
    }
    
    /**
     * 第三步（密码重置）
     * @Description: TODO
     * @param request
     * @param response
     * @author: chenzhpmf
     * @date 2015-3-27 下午3:53:41
     */
    @RequestMapping(value = "updateLoginPwd")
    public void updateLoginPwd(HttpServletRequest request,HttpServletResponse response){
     	String pwdFlag = request.getParameter("pwdFlag");
    	JSONObject jsonObject = new JSONObject();
    	String password = request.getParameter("pwd");
		String confirmPass = request.getParameter("confirmPwd");
		if(StringUtils.isBlank(password)){
			 jsonObject.put("result", 1);
			 ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
			 return;
		}
		 if(password.length()<6 || password.length()>20){
			 jsonObject.put("result", 1);
			 ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
			 return;
		 }
		 if(!password.endsWith(confirmPass)){
			 jsonObject.put("result", 2);
			 ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
			 return;
		}
		//userId
		Object userObject = request.getSession().getAttribute(WebConstants.USER_ID);
	    Long userId = -1l;
	    if(userObject!=null){
	    	userId = (Long)userObject;
	    }
		try{
			AccountUserDo aud = userService.getById(userId);
			if(aud == null){
				jsonObject.put("result", 5); //账号有误或者会话信息超时
				ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
				return;
			}
			String newPass = com.shove.security.Encrypt.MD5(confirmPass+WebConstants.PASS_KEY);
			int result = 0;
			if(pwdFlag.equals("pay")){
				String userPwd = aud.getPayPassword();
				if(newPass.equals(userPwd)){
					 jsonObject.put("result", 3);
					 ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
					 return;
				}
				result = userService.updatePayPassword(userId,newPass);//修改支付密码
			}else{
				String userPwd = aud.getPassword();
				if(newPass.equals(userPwd)){
					 jsonObject.put("result", 3);
					 ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
					 return;
				}
				result = userService.updateUserPassword(userId,newPass,"login");//修改登录密码
			}
			if(result > 0){
				//添加日志
				operationLogService.addOperationLog("t_user", aud.getUsername(), IConstants.UPDATE, aud.getLastIP(), 0d, "修改会员密码", 1);
				jsonObject.put("result",result > 0?0:4);
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		ResponseUtils.renderText(response, "UTF-8", jsonObject.toString());
    }
    
    /**
     * 邀请码页面
     * @Description: TODO
     * @return
     * @author: chenzhpmf
     * @date 2015-3-31 上午10:24:10
     */
    @RequestMapping(value = "invite")
    public String invite(){
    	return "login/invite";
    }
    
    /**
     * 绑定邀请码
     * @Description: TODO
     * @param request
     * @param response
     * @author: chenzhpmf
     * @date 2015-3-31 上午10:24:17
     */
    @RequireLogin
    @RequestMapping(value = "bindInviteCode", method = RequestMethod.POST)
    public void bindInviteCode(HttpServletRequest request,HttpServletResponse response){
    	Long userId = getUserId();
    	JSONObject jsonObject = new JSONObject();
    	String code = request.getParameter("code");
    	if(StringUtils.isBlank(code)){
    		jsonObject.put("result", 1);
    		ResponseUtils.renderText(response, null, jsonObject.toString());
    		return;
    	}
    	InviteCodeDo inviteCode = new InviteCodeDo();
    	inviteCode.setCode(code);
    	InviteCodeDo inviteDO = userService.findInviteCodeByDO(inviteCode);
    	if(inviteDO==null){
    		//邀请码不存在
    		jsonObject.put("result", 2);
    		ResponseUtils.renderText(response, null, jsonObject.toString());
        	return;
    	}
    	if(inviteDO.getUserId()!=null){
    		//邀请码已被使用
    		jsonObject.put("result", 3);
    	}else{
    		inviteDO.setUserId(userId);
    		userService.updateInviteCode(inviteDO);
    		jsonObject.put("result",0);
    	}
    	ResponseUtils.renderText(response, null, jsonObject.toString());
    }
    
    
    /**
     * 实名认证
     * @return
     * @author: chenzhpmf
     * @date 2015-4-3 下午11:02:10
     */
    @RequireLogin
    @RequestMapping(value = "realAuth")
    public String realAuth(HttpServletRequest request){
    	Long userId = getUserId();
    	PersonDo person = personService.getByUserId(userId);
    	// 获取来源的页面
    	String from = request.getParameter("from");
    	if(person!=null){
    		if(StringUtils.isNotBlank(person.getRealName())){
    			request.setAttribute("realName",person.getRealName());
    			request.setAttribute("idNO",person.getIdNo());
    		}
    	}
    	//根据渠道判断是国际物业还是彩管家
    	int channel = NumberUtils.toInt(getSessionStrAttr("channel"));
    	if(2 == channel){
    		request.setAttribute("remoteAddr", "http://m.hehenian.com/product/plist.do");
    	}else if(1 == channel){
    		request.setAttribute("remoteAddr", request.getHeader("referer"));
    	}else if (0 == channel) {
    		// 如果来自购买页面的实名认证，认证完之后跳转到购买页面
    		if ("buy".equals(from)) {
    			request.setAttribute("remoteAddr", "http://m.hehenian.com/finance/prepay.do?pid=2");
    		} else {
    			// 否则跳转到个人信息页面
    			request.setAttribute("remoteAddr", "http://m.hehenian.com/profile/userinfo.do?flag=q");
    		}
    	}
    	return "login/realAuth";
    }
    
    /**
     * 实名认证
     * @param request
     * @param response
     * @author: chenzhpmf
     * @date 2015-4-4 下午1:10:14
     */
    @RequireLogin
    @RequestMapping(value = "authRealName", method = RequestMethod.POST)
	public void authRealName(HttpServletRequest request,HttpServletResponse response){
		AccountUserDo user = getAccountUser();
		PersonDo person = personService.getByUserId(user.getId() );
        JSONObject jsonObject = new JSONObject();
        if (person!=null&&StringUtils.isNotBlank(person.getIdNo())){
            //已经保存过身份证了
        	jsonObject.put("msg", "身份证已认证");
            ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
        }

		String realName = request.getParameter("realName");// 真实姓名
		if (StringUtils.isBlank(realName)) {
			jsonObject.put("msg", "请填写真实名字");
			ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
		} 
		if (2 > realName.length() || 20 < realName.length()) {
			jsonObject.put("msg", "真实姓名的长度为不小于2和大于20");
			ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
		}

		String idNo = request.getParameter("idNo");// 身份证号码
		if (StringUtils.isBlank(idNo)) {
			jsonObject.put("msg", "请填写身份证号码");
			ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
		} 
		long len = idNo.length();
		if (15 != len && 18 != len) {
			jsonObject.put("msg", "请填写真实的身份证号码");
			ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
		}
		// 验证身份证
		int sortCode = 0;
		int MAN_SEX = 0;
		if (len == 15) {
			sortCode = Integer.parseInt(idNo.substring(12, 15));
		} else {
			sortCode = Integer.parseInt(idNo.substring(14, 17));
		}
		if (sortCode % 2 == 0) {
			MAN_SEX = 1;// 男性身份证
		} else if (sortCode % 2 != 0) {
			MAN_SEX = 2;// 女性身份证
		} else {
			jsonObject.put("msg", "身份证不合法");
			ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
		}
		String iDresutl = IDCardUtil.chekIdCard(MAN_SEX, idNo);
		if (iDresutl != "") {
			jsonObject.put("msg", "身份证不合法");
			ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
		}
		PersonDo idPerson= personService.getByUserIdNo(idNo);
		if(idPerson!=null){
			jsonObject.put("msg", "身份证已被使用");
			ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
		}
		
		person.setRealName(realName);
		person.setIdNo(idNo);
		person.setIdNoStatus(1);
		person.setAuditStatus(3);
		int result = personService.updatePerson(person);
		if (result>0) {
			if (user.getAuthStep() == 1) {
				user.setAuthStep(2);
			}
	        operationLogService.addOperationLog("t_person", user.getUsername(), IConstants.UPDATE, user.getLastIP(), 0d, "审核基础资料，" + "审核通过", 2);
	        jsonObject.put("msg", "保存成功");
		} else {
			jsonObject.put("msg", "保存失败,请重试");
		}
		ResponseUtils.renderText(response, null, jsonObject.toString());
	}

   
    /**
     * 手机号变更引导页
     * @return
     */
    @RequireLogin
    @RequestMapping("resetMobile")
    public String resetMobileStepIndex() {
    	return "login/resetMobile";
    }

    /**
     * 手机号变更引导页2
     * @return
     */
    @RequireLogin
    @RequestMapping("resetMobileStep2")
    public String resetMobileStepIndex2() {
    	String mobilePhone = (String) request.getSession().getAttribute("changeMobile");
    	if (mobilePhone == null) {
    		return "redirect:/account/resetMobile.do";
    	}
    	return "login/resetMobileIDValidate";
    }

    /**
     * 手机号变更验证第一步，手机号码、短信验证码、登录密码验证
     * @param request
     * @param response
     * @param user
     */
    @RequireLogin
    @RequestMapping("resetMobileValidateStep1")
    public void resetMobileValidateStep1(HttpServletRequest request, HttpServletResponse response) {
    	String identifyCode = request.getParameter("identifyCode");
    	String mobilePhone = request.getParameter("mobilePhone");
    	String loginPassword = request.getParameter("password");
    	JSONObject jsonObject = new JSONObject();
    	
    	// 是否输入手机号
		if (StringUtils.isBlank(mobilePhone)){
			 jsonObject.put("msg", "手机号不能为空");
			 ResponseUtils.renderText(response, null, jsonObject.toString());
			 return;
		}
		// 是否输入密码
		if (StringUtils.isBlank(loginPassword)) {
			jsonObject.put("msg", "登录密码不能为空");
			ResponseUtils.renderText(response, null, jsonObject.toString());
			return;
		}
		// 获取登录用户user
    	AccountUserDo sessionAccountUser = getAccountUser();
    	if (sessionAccountUser != null && mobilePhone.equals(sessionAccountUser.getMobilePhone())) {
    		jsonObject.put("msg", "不能更改为当前手机号码");
    		ResponseUtils.renderText(response, null, jsonObject.toString());
    		return;
		}
    	// 判断该手机号是否已经绑定合和年账户
    	AccountUserDo validationUser = userService.findUserByPhone(mobilePhone);
    	if (validationUser != null && mobilePhone.equals(validationUser.getMobilePhone())) {
    		jsonObject.put("msg", "该手机号已经绑定合和年账户");
    		ResponseUtils.renderText(response, null, jsonObject.toString());
    		logger.info("手机号" + mobilePhone + "已经绑定合和年账户");
    		return;
    	}
		boolean checkedSign = identifyCodeService.checkIdentifyCode(mobilePhone, identifyCode);
    	// 短信验证码验证
    	if (!checkedSign) {
    		jsonObject.put("msg", "验证码不正确");
    		ResponseUtils.renderText(response, null, jsonObject.toString());
    		return;
    	}
		String username = sessionAccountUser.getUsername();
		LoginInfoRelate loginInfoRelate = userInfoService.getByLoginInfo(username, LoginInfoRelate.class, true);
		AccountUserDo accountUser = null;
		//存储登录渠道
		String pwdMd5 = DigestUtils.md5Hex(loginPassword + WebConstants.PASS_KEY);
		if(loginInfoRelate == null) {
			//如果没有处理老数据，兼容老版本，取一次t_user信息
			// accountUser = userService.loginWithPwd(username, pwdMd5);
			accountUser = userService.findUserByUserNamePwd(username, pwdMd5);
		}
		else {
			accountUser = userService.getById(loginInfoRelate.getUserId().longValue());
		}
		if(accountUser == null || !accountUser.getPassword().equals(pwdMd5)) {
			// 用户名密码错误
			jsonObject.put("msg", "登录密码错误");
			ResponseUtils.renderText(response, null,jsonObject.toString());
			return;
		}
		// 判断是否已经实名认证，如果已经实名认证返回值添加verify标识
		PersonDo person = personService.getByUserId(getUserId());
        if (person != null && StringUtils.isNotBlank(person.getIdNo()) && 3 == person.getAuditStatus()){
        	jsonObject.put("verify", 0);
        }
        jsonObject.put("code", 0);
        request.getSession().setAttribute(WebConstants.USER_ID, sessionAccountUser.getId());
        // 将更改的手机号码传到下一步验证
        request.getSession().setAttribute("changeMobile", mobilePhone);
		ResponseUtils.renderText(response, null, jsonObject.toString());
    }
    
    /**
     * 手机号变更验证第二步，真实姓名、身份证号码验证
     * @param request
     * @param response
     */
    @RequireLogin
    @RequestMapping("resetMobileValidateStep2")
    public void resetMobileValidateStep2(HttpServletRequest request, HttpServletResponse response) {
    	String mobilePhone = request.getParameter("mobilePhone");
    	String realName = request.getParameter("realName");
    	String idNo = request.getParameter("idNo");
    	JSONObject jsonObject = new JSONObject();
    	// 是否输入姓名
		if (StringUtils.isBlank(realName)){
			 jsonObject.put("msg", "请填写真实姓名");
			 ResponseUtils.renderText(response, null, jsonObject.toString());
			 return;
		}
		// 校验输入姓名
		if (2 > realName.length() || 20 < realName.length()) {
			jsonObject.put("msg", "真实姓名的长度为不小于2和大于20");
			ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
		}
		// 是否输入身份证
		if (StringUtils.isBlank(idNo)) {
			jsonObject.put("msg", "身份证号码不能为空");
			ResponseUtils.renderText(response, null, jsonObject.toString());
			return;
		}

		long len = idNo.length();
		if (15 != len && 18 != len) {
			jsonObject.put("msg", "请填写真实的身份证号码");
			ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
		}
		// 验证身份证
		int sortCode = 0;
		int MAN_SEX = 0;
		if (len == 15) {
			sortCode = Integer.parseInt(idNo.substring(12, 15));
		} else {
			sortCode = Integer.parseInt(idNo.substring(14, 17));
		}
		if (sortCode % 2 == 0) {
			MAN_SEX = 1;// 男性身份证
		} else if (sortCode % 2 != 0) {
			MAN_SEX = 2;// 女性身份证
		} else {
			jsonObject.put("msg", "身份证不合法");
			ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
		}
		String iDresutl = IDCardUtil.chekIdCard(MAN_SEX, idNo);
		if (iDresutl != "") {
			jsonObject.put("msg", "身份证不合法");
			ResponseUtils.renderText(response, null, jsonObject.toString());
            return;
		}
		PersonDo person = personService.getByUserId(getUserId());
		if (person != null && idNo.equals(person.getIdNo()) && realName.equals(person.getRealName())) {
			jsonObject.put("code", 0);
			// 添加变更手机号业务逻辑
			AccountUserDo accountUserDo = getAccountUser();
			PhoneVerifyDo phoneVerifyDo = new PhoneVerifyDo();
            phoneVerifyDo.setUserId(getUserId());
            phoneVerifyDo.setMobilePhone(mobilePhone);
            phoneVerifyDo.setStatus(1);
            IResult<?> result = phoneVerifyService.savePhoneVerify(phoneVerifyDo);
            if (result.isSuccess()){
                accountUserDo.setMobilePhone(mobilePhone);
                accountUserDo.setPhoneHasVerify(true);
                jsonObject.put("code","0");
            }else {
                jsonObject.put("code","1");
                jsonObject.put("msg",result.getErrorMessage());
            }
		}
		jsonObject.put("msg", "身份校验失败");
		ResponseUtils.renderText(response, null, jsonObject.toString());
    }
    
    /**
     * 手机号变更验证第三步，转发到手机号变更成功页面
     * @return
     */
    @RequireLogin
    @RequestMapping("resetMobileValidateStep3")
    public String resetMobileValidateStep3(Model model) {
    	String mobilePhone = (String) request.getSession().getAttribute("changeMobile");
    	if (mobilePhone == null) {
    		return "redirect:/account/resetMobile.do";
    	}
    	// 添加变更手机号业务逻辑
		AccountUserDo accountUserDo = getAccountUser();
		PhoneVerifyDo phoneVerifyDo = new PhoneVerifyDo();
	    phoneVerifyDo.setUserId(getUserId());
	    phoneVerifyDo.setMobilePhone(mobilePhone);
	    phoneVerifyDo.setStatus(1);
	    phoneVerifyDo.setType(PhoneVerifyDo.ContactType.PHONE);
	    phoneVerifyDo.setSource(PhoneVerifyDo.SourceType.MOBILE);
	    IResult<?> result = phoneVerifyService.savePhoneVerify(phoneVerifyDo);
	    request.getSession().removeAttribute("changeMobile");
	    if (result.isSuccess()){
	    	logger.info("手机号码变更成功" + phoneVerifyDo.getMobilePhone());
	        accountUserDo.setMobilePhone(mobilePhone);
	        accountUserDo.setPhoneHasVerify(true);
	        return "login/resetMobileSucc";
	    }else {
	    	logger.info("手机号码变更失败[" + phoneVerifyDo.getMobilePhone() + "]，失败原因：" + result.getErrorMessage());
	    	model.addAttribute("errMsg", result.getErrorMessage());
	    	return "login/resetMobileSucc";
	    	// return "login/resetMobileFail";
	    }
    }
    
    /**
     * 重置邮箱引导页
     * @return
     */
    @RequireLogin
    @RequestMapping("resetEmailIndex")
    public String resetEmailIndex(Model model) {
    	AccountUserDo accountUser = getAccountUser();
    	accountUser = this.userService.findUserByUserName(accountUser.getUsername());
    	
    	// 判断之前是否设置过邮箱，如果设置过邮箱则显示之前的邮箱，否则不显示
    	if (accountUser != null && StringUtils.isNotBlank(accountUser.getEmail())) {
    		model.addAttribute("email", accountUser.getEmail());
    	}
    	return "login/resetEmail";
    }
    
    /**
     * 重置邮箱
     * @param request
     * @param response
     * @return
     */
    @RequireLogin
    @RequestMapping("resetEmail")
    public void resetEmail(HttpServletRequest request, HttpServletResponse response) {
    	String password = request.getParameter("password");
    	String email = request.getParameter("email");
    	JSONObject jsonObject = new JSONObject();
    	// 是否输入登录密码
		if (StringUtils.isBlank(password)){
			 jsonObject.put("msg", "登录密码不能为空");
			 ResponseUtils.renderText(response, null, jsonObject.toString());
			 return;
		}
		// 是否输入邮箱
		if (StringUtils.isBlank(email)) {
			jsonObject.put("msg", "邮箱不能为空");
			ResponseUtils.renderText(response, null, jsonObject.toString());
			return;
		}
		// 判断修改的邮箱是否被使用
		AccountUserDo emailValidation = this.userService.findUserByEmail(email);
		if (emailValidation != null) {
			jsonObject.put("msg", email + "邮箱已经使用");
			ResponseUtils.renderText(response, null, jsonObject.toString());
			return;
		}
		
    	AccountUserDo sessionAccountUser = getAccountUser();
    	AccountUserDo accountUser = null;
    	String username = sessionAccountUser.getUsername();
		LoginInfoRelate loginInfoRelate = userInfoService.getByLoginInfo(username, LoginInfoRelate.class, true);
		//存储登录渠道
		String pwdMd5 = DigestUtils.md5Hex(password + WebConstants.PASS_KEY);
		if(loginInfoRelate == null) {
			//如果没有处理老数据，兼容老版本，取一次t_user信息
			accountUser = userService.findUserByUserNamePwd(username, pwdMd5);
		}
		else {
			accountUser = userService.getById(loginInfoRelate.getUserId().longValue());
		}
		if(accountUser == null || !accountUser.getPassword().equals(pwdMd5)) {
			// 用户名密码错误
			jsonObject.put("msg", "登录密码错误");
			ResponseUtils.renderText(response, null,jsonObject.toString());
			return;
		}
		
		this.phoneVerifyService.updateEmail(sessionAccountUser.getId(), email);
		jsonObject.put("code", 0);
		ResponseUtils.renderText(response, null,jsonObject.toString());
    }
    
    /**
     * 重置邮箱成功页面
     * @return
     */
    @RequireLogin
    @RequestMapping("resetEmailSuccess")
    public String resetEmailSuccess() {
    	return "login/resetEmailSucc";
    }
    
    /**
     * 母亲节活动介绍页
     */
    @RequestMapping(value = "motherDay")
    public String motherDay(){
    	return "activity/motherDay_rule";
    }
}
