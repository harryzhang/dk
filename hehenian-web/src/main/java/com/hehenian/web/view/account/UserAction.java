package com.hehenian.web.view.account;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.IPhoneVerifyService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PhoneVerifyDo;
import com.hehenian.biz.common.account.dataobject.UserBindDo;
import com.hehenian.biz.common.activity.IActivityOrderService;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.identifycode.IIdentifyCodeService;
import com.hehenian.web.base.action.BaseAction;
import com.hehenian.web.common.contant.WebConstants;
import com.hehenian.web.common.util.ServletUtils;
import com.shove.web.util.DesSecurityUtil;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.UserService;

/**
 * User: liuwtmf
 * Date: 2014/11/25
 * Time: 15:58
 */
@Scope("prototype")
@Component("newUserAction")
public class UserAction extends BaseAction{
    private static final Logger LOGGER = Logger.getLogger(UserAction.class);
    @Autowired
    private   IUserService          userService;
    @Autowired
    protected UserService           userService1;
    @Autowired
    private   IActivityOrderService activityOrderService;
    @Autowired
    private   OperationLogService   operationLogService;
    @Autowired
    private   IIdentifyCodeService  identifyCodeService;
    @Autowired
    private   IPhoneVerifyService   phoneVerifyService;
    private   String                userName;
    private   String                mobilePhone;
    private   String                pwd;
    private   String                confirmPassword;

    //    @SkipValidation
    public String regInit() throws Exception {
        String param = request("param");
        if (StringUtils.isNotBlank(param)) {
            DesSecurityUtil des = new DesSecurityUtil();
            try {
                Long userId = Long.parseLong(des.decrypt(param));
                try {
                    AccountUserDo accountUserDo = userService.getById(userId);
                    if (accountUserDo != null) {
                        paramMap.put("userId", userId + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {

            }

        }

        return SUCCESS;
    }

    public String register() {
        long refferee = getLongParam("refferee", -1);
        //用户来源
        int source = 1;
        Object sourcefrom = session("sourcefrom");
        String sourceCheck = request("source");
        if("".equals(sourceCheck) || StringUtils.isEmpty(sourceCheck) ){
	        if (sourcefrom != null) {
	            try {
	                int s = Integer.parseInt(sourcefrom.toString());
	                if (s > 0) {
	                    source = s;
	                }
	            } catch (Exception e) {
	            }
	        }
        }else {
        	source = Integer.parseInt(sourceCheck);
        }

        AccountUserDo accountUserDo = new AccountUserDo();
        accountUserDo.setUsername(userName);
        accountUserDo.setMobilePhone(mobilePhone);
        String pwdMd5 = DigestUtils.md5Hex(pwd + WebConstants.PASS_KEY);
        accountUserDo.setPassword(pwdMd5);
        if (refferee > 0) {
            if (userService.getById(refferee) == null) {
                AccountUserDo accountUserDo1 = userService.findUserByPhone(refferee + "");
                if (accountUserDo1 != null) {
                    accountUserDo.setReffer(refferee + "");
                    refferee = accountUserDo1.getId();
                } else {
                    request().setAttribute("msg", "推荐人不存在，请重新填写");
                    return "register";
                }
            }
        }
        accountUserDo.setRefferee(refferee+"");
        Date now = new Date();
        accountUserDo.setCreateTime(now);
        accountUserDo.setSource(source);

        //手机验证码校验
        String identifyCode = request("identifyCode");
        boolean b = identifyCodeService.checkIdentifyCode(mobilePhone, identifyCode);
        if (!b){
            //手机验证码校验不通过
            request().setAttribute("msg", "手机验证码不正确");
            return "register";
        }

        IResult result = userService.registerUser(accountUserDo);
        if (result.isSuccess()) {
//        	try {
//        		String liumi = SqlInfusion.FilteSqlInfusion(paramMap.get("liumi"));
//        		if(liumi.equals("1")){ //如果是红包入口过来的，则送流量
//        			LiumiClient.placeOrder(mobilePhone,null,true); //手机号，流量规格
//        		}
//			}catch (Exception e1) {
//				e1.printStackTrace();
//			}
            accountUserDo = userService.loginWithPwd(userName, pwdMd5);
            session().setAttribute("user", accountUserDo);
            try {
                int partnerId = getSessionIntAttr("partnerId",0);
                String partnerUserId = getSessionStrAttr("partnerUserId");
                if (partnerId>0&&StringUtils.isNotBlank(partnerUserId)){
                    UserBindDo userBindDo = new UserBindDo();
                    userBindDo.setPartnerId(partnerId);
                    userBindDo.setPartnerUserId(partnerUserId);
                    userBindDo.setCreateTime(new Date());
                    userBindDo.setUserId(accountUserDo.getId());
                    userService.saveUserBind(userBindDo);
                }
            }catch (Exception e){
                LOGGER.error(e.getMessage(),e);
            }
            String fromUrl = request("fromUrl");
            if("".equals(fromUrl) || StringUtils.isEmpty(fromUrl)){
            	return SUCCESS;
            }else {
            	try {
					response().sendRedirect(fromUrl);
				} catch (IOException e) {
					LOGGER.error(e.getMessage(),e);
				}
            }
        } else {
            //            ServletUtils.write(result.getErrorMessage());
            request().setAttribute("msg", result.getErrorMessage());
            return "register";
        }
        return null;
    }

    public String loginInit() throws Exception {

        return SUCCESS;
    }
    private boolean checkCode(){
        String pageId = paramMap.get("pageId");
        String code = (String) session().getAttribute(pageId + "_checkCode");
        String _code = paramMap.get("code");// 验证码
        if (code == null || !code.equals(_code)) {
            return false;
        }else {
            return true;
        }
    }
    public String login(){
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(pwd)){
            JSONObject jsonObject = new JSONObject();
            if (!checkCode()) {
                jsonObject.put("msg", 2);
                ServletUtils.writeJson(jsonObject.toString());
                return null;
            }
            String pwdMd5 = DigestUtils.md5Hex(pwd+ WebConstants.PASS_KEY);
            AccountUserDo accountUserDo = userService.loginWithPwd(userName, pwdMd5);
            if (accountUserDo!=null){
                //用户属于减免物业费的用户 就不能登录了
                if (activityOrderService.hasOrder(0,accountUserDo.getId())>0){
                    jsonObject.put("msg", "4");
                    ServletUtils.writeJson(jsonObject.toString());
                    return null;
                }

                session().setAttribute("user", accountUserDo);
                
                String fromUrl = request().getHeader("referer");
                if (fromUrl == null) {
                    fromUrl = getBasePath() + "home.do";
                }else {
                    fromUrl = StringUtils.substringBefore(fromUrl, ";s=");
                }
                if (fromUrl.endsWith("login-index.do") ||fromUrl.endsWith("login.do") || fromUrl.endsWith("logout.do")) {
                    fromUrl = getBasePath() + "home.do";
                }
                LOG.info("fromUrl:"+fromUrl);
                jsonObject.put("msg",1);
                jsonObject.put("fromUrl",fromUrl);
                ServletUtils.writeJson(jsonObject.toString());
            }else {
                //用户名密码错误
                jsonObject.put("msg",3);
                ServletUtils.writeJson(jsonObject.toString());
            }
        }

        return null;
    }
    public String cfApp() {
        if (!checkColourSign()){
            ServletUtils.write("验证签名失败");
            return null;
        }
        session().setAttribute("platform", "colorlifeapp");
        session().setAttribute("sourcefrom", WebConstants.SOURCEFROM_COLOURLIFE_APP);
        session().setAttribute("appstyle","cf");
        IResult iResult = bindLogin(1,request("userid"),request("mobile"),request("username"));
        if (!iResult.isSuccess()){
            ServletUtils.write("保存用户信息失败");
            return null;
        }else{
            if (iResult.getModel()!=null&&iResult.getModel().equals(-2)){
                return "bind";
            }else {
                try {
                    Long userId = (Long)iResult.getModel();
                    saveColourInfo(userId);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return SUCCESS;
            }
        }
    }
    private boolean checkColourSign(){
        return true;
    }
    private void saveColourInfo(Long userId){
        int cid = getIntParam("cid",0);//小区ID
        String cname = request("cname");// 小区名
        String caddress = request("caddress");// 用户地址
        int tjrid = getIntParam("tjrid",0);// 彩生活推荐人id
        int userid = getIntParam("userid",-1);// 彩生活用户ID,用户唯一标识，数字1-10位
        try {
            userService1.saveColourInfo(userId, userid, cid, cname, caddress, tjrid,"");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public String bind(){
        int partnerId = getSessionIntAttr("partnerId",0);//getIntParam("partnerId",1);
        String partnerUserId = getSessionStrAttr("partnerUserId");//request().getParameter("partnerUserId");
        JSONObject jsonObject = new JSONObject();
        if (partnerId>0&&StringUtils.isNotBlank(partnerUserId)) {
            if (!checkCode()) {
                jsonObject.put("msg", 2);
                ServletUtils.writeJson(jsonObject.toString());
                return null;
            } else {

                String pwdMd5 = DigestUtils.md5Hex(pwd + WebConstants.PASS_KEY);
                AccountUserDo accountUserDo = userService.loginWithPwd(userName, pwdMd5);
                if (accountUserDo != null) {
                    UserBindDo userBindByUser = userService.findUserBindByUser(partnerId, accountUserDo.getId());
                    if (userBindByUser != null) {
                        //用户名密码错误
                        jsonObject.put("msg", 6);
                        ServletUtils.writeJson(jsonObject.toString());
                    } else {
                        UserBindDo userBindDo = new UserBindDo();
                        userBindDo.setPartnerId(partnerId);
                        userBindDo.setPartnerUserId(partnerUserId);
                        userBindDo.setCreateTime(new Date());
                        userBindDo.setUserId(accountUserDo.getId());
                        userService.saveUserBind(userBindDo);
                        session().setAttribute("user", accountUserDo);
                        jsonObject.put("msg", 1);
                        ServletUtils.writeJson(jsonObject.toString());
                    }

                } else {
                    //用户名密码错误
                    jsonObject.put("msg", 3);
                    ServletUtils.writeJson(jsonObject.toString());
                }
            }
        }
        return null;
        /*int partnerId = getIntParam("partnerId",1);
        String partnerUserId = request().getParameter("partnerUserId");
        AccountUserDo accountUserDo = userService.loginWithPwd(userName, pwd);
        if (accountUserDo!=null){
            UserBindDo userBindDo = new UserBindDo();
            userBindDo.setPartnerId(partnerId);
            userBindDo.setPartnerUserId(partnerUserId);
            userBindDo.setCreateTime(new Date());
            userBindDo.setUserId(accountUserDo.getId());
            userService.saveUserBind(userBindDo);
            session().setAttribute("user", accountUserDo);
            putUserJsontoSessionShare(accountUserDo);
            return SUCCESS;
        }else {
            return "input";
        }*/


    }
    public String bindLogining() {
       /* if (!checkColourSign()){
            ServletUtils.write("验证签名失败");
            return null;
        }*/
//        session().setAttribute("platform", "colorlifeapp");
//        session().setAttribute("sourcefrom", WebConstants.SOURCEFROM_COLOURLIFE_APP);
//        session().setAttribute("appstyle","cf");
        int partnerId = getSessionIntAttr("partnerId", 0);//getIntParam("partnerId",1);
        String partnerUserId = getSessionStrAttr("partnerUserId");
        if (partnerId>0 && StringUtils.isNotBlank(partnerUserId)){
            UserBindDo userBind = userService.findUserBindByPartner(partnerId, partnerUserId);
            if (userBind != null){
                AccountUserDo accountUserDo = userService.loginWithId(userBind.getUserId());
                if (accountUserDo!=null){
                    session().setAttribute("user", accountUserDo);
                    return SUCCESS;
                }else{
                    request().setAttribute("title","请求参数错误");
                    return "msg";
                }
            }else {
                return "reg";
            }
        }else {
            //请求参数错误
          /*  ServletUtils.write("请求参数错误");
            return null;*/
            request().setAttribute("title","请求参数错误");
            return "msg";
        }
    }

    public IResult bindLogin(int partnerId,String partnerUserId,String mobilePhone,String userName){
        IResult iResult = new ResultSupport();
        //校验数据合法性

//        partnerId = getIntParam("partnerId", 0);
//        partnerUserId = request().getParameter("partnerUserId");
        if (partnerId>0 && StringUtils.isNotBlank(partnerUserId)){
            UserBindDo userBind = userService.findUserBindByPartner(partnerId, partnerUserId);
            if (userBind != null){
                AccountUserDo accountUserDo = userService.loginWithId(userBind.getUserId());
                if (accountUserDo!=null){
                    iResult.setModel(accountUserDo.getId());
                    session().setAttribute("user", accountUserDo);
                    iResult.setSuccess(true);
                }else{
                    iResult.setSuccess(false);
                    iResult.setErrorMessage("数据错误");
                }

            }else {
                //没有绑定过用户
                AccountUserDo accountUserDo = userService.findUserByPhone(mobilePhone);
                if (accountUserDo ==null){
                    //手机号码没有被使用过 创建新用户
                    accountUserDo = new AccountUserDo();
                    accountUserDo.setSource(partnerId);
                    accountUserDo.setMobilePhone(mobilePhone);
                    accountUserDo.setUsername(userName);

                    UserBindDo userBindDo = new UserBindDo();
                    userBindDo.setPartnerId(partnerId);
                    userBindDo.setPartnerUserId(partnerUserId);

                    Long userId = userService.bindNewUser(accountUserDo, userBindDo);
                    if (userId ==null || userId <=0){
                        //保存失败哦
//                        ServletUtils.write("保存用户信息失败");
                        iResult.setSuccess(false);
                        iResult.setErrorMessage("保存用户信息失败");
                    }else {
                        accountUserDo.setId(userId);
                        iResult.setSuccess(true);
                        iResult.setModel(accountUserDo.getId());
                        session().setAttribute("user", accountUserDo);
                    }
                }else {
                    //手机号码已存在 提示用户绑定合和年账号
                    iResult.setSuccess(true);
                    iResult.setModel(-2);
                }
            }
        }else {
            //请求参数错误
          /*  ServletUtils.write("请求参数错误");
            return null;*/
            iResult.setSuccess(false);
            iResult.setErrorMessage("请求参数错误");
        }
        return iResult;
    }

    /**
     * 验证手机号码
     * @return
     */
    public String phoneVerify(){
        AccountUserDo accountUserDo = getUser();
        JSONObject jsonObject = new JSONObject();
//        String mobilePhone = accountUserDo.getMobilePhone();
        if (StringUtils.startsWith(mobilePhone,"-")){
            mobilePhone = StringUtils.substringAfter(mobilePhone,"-");
        }
        IResult result = checkPhoneVerify(mobilePhone, accountUserDo.getId());
        if (result.isSuccess()){
        	int i1 = userService.updatePersonPhone(accountUserDo.getId(),accountUserDo.getMobilePhone());
            if (i1<=0){
                LOGGER.error("同步修改person表修改手机号码失败。userId:"+accountUserDo.getId()+"，phone:"+accountUserDo.getMobilePhone());
            }
            accountUserDo.setMobilePhone(mobilePhone);
            accountUserDo.setPhoneHasVerify(true);
            jsonObject.put("ret","0");
        }else {
            jsonObject.put("ret","1");
            jsonObject.put("msg",result.getErrorMessage());
        }
        ServletUtils.writeJson(jsonObject.toString());
        return null;
    }

    public String disablePhoneVerify(){
        AccountUserDo accountUserDo = getUser();
        JSONObject jsonObject = new JSONObject();
        String identifyCode = request("identifyCode");
        boolean b = identifyCodeService.checkIdentifyCode(accountUserDo.getMobilePhone(), identifyCode);
        if (b){
            int i = phoneVerifyService.disablePhoneVerify(accountUserDo.getId());
            if (i>0){
                int i1 = userService.updateUserPhone(accountUserDo.getId(), "-" + accountUserDo.getMobilePhone());
                if (i1<=0){
                    LOGGER.error("用户解绑手机时修改手机号码失败。userId:"+accountUserDo.getId()+"，phone:"+accountUserDo.getMobilePhone());
                }
                jsonObject.put("ret", "0");
                getUser().setPhoneHasVerify(false);
            }else {
                jsonObject.put("ret","2");
                jsonObject.put("msg","删除手机认证记录失败");
            }
        }else {
            jsonObject.put("ret","1");
            jsonObject.put("msg","验证码不正确");
        }
        ServletUtils.writeJson(jsonObject.toString());
        return null;
    }
    /**
     * 修改手机号码
     * @return
     */
    public String modifyPhone(){
        AccountUserDo accountUserDo = getUser();
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotBlank(mobilePhone)){
            //手机号码不能为空
            jsonObject.put("ret","1");
            jsonObject.put("msg","手机号码不能为空");
        }else if (mobilePhone.equals(accountUserDo.getMobilePhone())){
            //手机号码不能与原号码相同
            jsonObject.put("ret","1");
            jsonObject.put("msg","手机号码不能与原号码相同");
        }else {
            PhoneVerifyDo phoneVerify = phoneVerifyService.findPhoneVerify(accountUserDo.getId());
            if (phoneVerify==null){
                IResult result = checkPhoneVerify(mobilePhone, accountUserDo.getId());
                if (result.isSuccess()){
                    jsonObject.put("ret","0");
                }else {
                    jsonObject.put("ret","1");
                    jsonObject.put("msg",result.getErrorMessage());
                }
            }else {
                //还存在验证通过的手机 不能直接修改
                jsonObject.put("ret","2");
                jsonObject.put("msg","非法请求");
            }

        }
        ServletUtils.writeJson(jsonObject.toString());
        return null;
    }
    public String checkPhone(){
        AccountUserDo userDo = userService.findUserByPhone(mobilePhone);
        if (userDo!=null){
            ServletUtils.write("1");
        }else {
            ServletUtils.write("0");
        }
        return null;
    }

    private IResult checkPhoneVerify(String mobilePhone,Long userId){
        String identifyCode = request("identifyCode");
        boolean b = identifyCodeService.checkIdentifyCode(mobilePhone, identifyCode);
        if (b){
            PhoneVerifyDo phoneVerifyDo = new PhoneVerifyDo();
            phoneVerifyDo.setUserId(userId);
            phoneVerifyDo.setMobilePhone(mobilePhone);
            phoneVerifyDo.setStatus(1);
            IResult result = phoneVerifyService.savePhoneVerify(phoneVerifyDo);
            return  result;
            /*if (result.isSuccess()){
                LOGGER.info("保存手机号验证记录成功");
                return 0;
            }else {
                LOGGER.info(result.getErrorMessage());
                return 1;
            }
*/
        }else {
            //手机验证码校验不通过
            IResult result = new ResultSupport();
            result.setErrorMessage("手机验证码不正确");
            return  result;
        }

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}









