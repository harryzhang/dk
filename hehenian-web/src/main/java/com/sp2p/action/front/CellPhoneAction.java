package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.constants.IConstants;
import com.sp2p.service.BBSRegisterService;
import com.sp2p.service.BeVipService;
import com.sp2p.service.CellPhoneService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.RelationService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;
/**
 * 跳转到手机注册页面
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class CellPhoneAction extends BaseFrontAction{
	public static Log log = LogFactory.getLog(BaseFrontAction.class);
	private UserService userService;
	private RelationService relationService;
	private HomeInfoSettingService homeInfoSettingService;
	private RecommendUserService recommendUserService;
	private CellPhoneService cellPhoneService;
	private BeVipService beVipService;
	private BBSRegisterService bbsRegisterService;
	private AdminService adminService;
	
	
	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public void setCellPhoneService(CellPhoneService cellPhoneService) {
		this.cellPhoneService = cellPhoneService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String cellPhoneinit(){
		session().setAttribute("DEMO", IConstants.ISDEMO);
		return SUCCESS;
	}
	
	public String cellPhonereginit(){
		String cellphone  = SqlInfusion.FilteSqlInfusion(request().getParameter("cp"));
		request().setAttribute("cellphone", cellphone);
		return SUCCESS;
	}
	public String cellPhoneregsinit() throws IOException{
		String cellphone  = SqlInfusion.FilteSqlInfusion(paramMap.get("cellphone"));
		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId")); // 验证码
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code").toString().trim());
		if (code == null || !_code.equals(code)) {
			JSONUtils.printStr("2");//2为验证码错误
			return null;
		}
		if(StringUtils.isBlank(cellphone)){
			JSONUtils.printStr("3");//3为手机验证码为空
			return null;
		}
		try {
			Map<String,String> 	phonemap = beVipService.queryIsPhoneonUser(cellphone);
			Map<String,String> cellMap = cellPhoneService.queryCellPhone(cellphone);
			if(phonemap!= null || cellMap !=null){   //判断手机号码是都否存在
				JSONUtils.printStr("5");//5为 手机号码已存在
				return null;
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			JSONUtils.printStr("2");//
			return null;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			JSONUtils.printStr("2");//
			return null;
		}
		request().setAttribute("cellphone", cellphone);
		JSONUtils.printStr("1");//1通过校验
		return null;
	}
	
	
	/***	
	 * 手机注册
	 * @return
	 * @throws Exception
	 */
	public String cellreginfo() throws Exception{
		JSONObject obj = new JSONObject();
		//判断手机验证码是否正确 start
		/**
		 * 判定用户是否已存在记录
		 */
		//验证手机的唯一性
		String cellphone  =  SqlInfusion.FilteSqlInfusion(paramMap.get("cellphone"));
		Map<String,String> phonemap = null;
		Map<String,String> cellMap = null;
		try{
		phonemap = beVipService.queryIsPhoneonUser(cellphone);
		cellMap = cellPhoneService.queryCellPhone(cellphone);
		
		if(phonemap!=null &&cellMap!=null ){
				obj.put("mailAddress", "手机已存在");
			JSONUtils.printObject(obj);
				return null;
		}	
		 if(phonemap==null){	
			String phonecode=null;
			try {
				Object obje=session().getAttribute("phone");
				if(obje!=null){
					phonecode=obje.toString();
				}else{
					if ("2".equals(IConstants.ISDEMO)) {
						obj.put("mailAddress", "请输入正确的验证码");
						JSONUtils.printObject(obj);
						return null;
					}
				}
		} catch (Exception e) {
						e.printStackTrace();
		}
		if(phonecode!=null){
			if(!phonecode.trim().equals(cellphone.trim())){
				obj.put("mailAddress", "与获取验证码手机号不一致");
				JSONUtils.printObject(obj);
				return null;
			}
						
		}
		if (!"1".equals(IConstants.ISDEMO)) {
					//验证码
					String vilidataNum = SqlInfusion.FilteSqlInfusion(paramMap.get("cellcode"));
			        if(StringUtils.isBlank(vilidataNum)){
			        	obj.put("mailAddress","请填写验证码");
			        	JSONUtils.printObject(obj);
						return null;
			        }
			        
			        String randomCode=null;
					Object objec=session().getAttribute("randomCode");
					if(objec!=null){
						randomCode=objec.toString();
					}else{
						obj.put("mailAddress","请输入正确的验证码");
			        	JSONUtils.printObject(obj);
						return null;
					}
					if(randomCode!=null){
						if(!randomCode.trim().equals(vilidataNum.trim())){
							
							obj.put("mailAddress","请输入正确的验证码");
				        	JSONUtils.printObject(obj);
							return null;
						}
						
					}
		}
			
		}
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName")); // 用户名
		if(userName.length()<2||userName.length()>20){
			obj.put("mailAddress", "18");
			JSONUtils.printObject(obj);
			return null;
		}
		if(StringUtils.isBlank(userName)){
			obj.put("mailAddress", "13");
			JSONUtils.printObject(obj);
			return null;
		}
		//验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
           if (userName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length()!=0) {
        		obj.put("mailAddress", "20");
    		   JSONUtils.printObject(obj);
               return null;     
            } 
          //判断第一个字符串不能使以下划线开头的
           String fristChar = userName.substring(0,1);
           if(fristChar.equals("_")){
       	   obj.put("mailAddress", "21");
 		   JSONUtils.printObject(obj);
            return null;   
           }
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password")); // 用户密码
		String md5Password =password;
		if(StringUtils.isBlank(password)){
			obj.put("mailAddress", "14");
			JSONUtils.printObject(obj);
			return null;
		}
		String confirmPassword = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmPassword")); // 用户密码
		if(StringUtils.isBlank(confirmPassword)){
			obj.put("mailAddress", "15");
			JSONUtils.printObject(obj);
			return null;
		}
		String refferee = SqlInfusion.FilteSqlInfusion(paramMap.get("refferee"));
		@SuppressWarnings("unused")
		String param = SqlInfusion.FilteSqlInfusion(paramMap.get("param")); //邀请好友链接携带的参数
		Map<String,Object> map = null;
		long recommendUserId = -1;
		if(StringUtils.isNotBlank(refferee)){
			Map<String,String> userIdMap = userService.queryIdByUser(refferee);//根据用户查询用户明细
			if(userIdMap != null){
				recommendUserId = Convert.strToLong(userIdMap.get("id"), -1);
			}
			map = relationService.isPromoter(refferee);
			if(map==null){
				refferee = null;
			}
			if(userIdMap==null&&map==null){
				obj.put("mailAddress", "5");
				JSONUtils.printObject(obj);
				return null;
			}
			
		}
		// 判断密码是否一致
		if (!password.equals(confirmPassword)) {
			  obj.put("mailAddress", "1");
			  JSONUtils.printObject(obj);
			return null;
		}
		Long userId = -1L;
			Long result = userService.isExistEmailORUserName(null, userName);
			boolean isExist = adminService.isExistUserName(userName);
			if (result > 0 || isExist) { // 用户名重复
				obj.put("mailAddress", "2");
				JSONUtils.printObject(obj);
				return null;
			}
			int typelen = -1;
			Map<String,String> lenMap = null;
			lenMap = userService.querymaterialsauthtypeCount();	//查询证件类型主表有多少种类型
			if(lenMap!=null&&lenMap.size()>0){
				typelen =  Convert.strToInt(lenMap.get("cccc"), -1);
			// 调用service
				if(typelen!=-1){
					//判断是否使用了加密字符串
					if ("1".equals(IConstants.ENABLED_PASS)){
						md5Password = com.shove.security.Encrypt.MD5(md5Password.trim());
					}else{
						md5Password = com.shove.security.Encrypt.MD5(md5Password.trim()+IConstants.PASS_KEY);
					}
			        userId = cellPhoneService.usercellRegister(cellphone, userName, md5Password,
					refferee,map,typelen);//注册用户 和  初始化图片资料
				}
			}
			if (userId < 0) { // 注册失败
				  obj.put("mailAddress", "4");
				  JSONUtils.printObject(obj);
				  return null;
			} else {
				//添加通知默认方法
				homeInfoSettingService.addNotes(userId, true, false, false);
				homeInfoSettingService.addNotesSetting(userId, true, true, true, true,  true, false, false, false, false, false, false, false, false, false, false);
				  //====
				obj.put("mailAddress", "注册成功");//注册成功
				JSONUtils.printObject(obj);
			}
		
		
		//修改之前的推荐
		try {
			if(recommendUserId>0){//判断是否为空
				
				List<Map<String,Object>> list = recommendUserService.queryRecommendUser(null, userId, null);//查询用户是否已经存在关系了。
				if(list!=null&&list.size()>0){//判断之前是否已经有关系了。
					return null;
				}
				recommendUserService.addRecommendUser(userId, recommendUserId);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		AccountUserDo user = new AccountUserDo();
		user.setUsername(userName);
		user.setPassword(password);
		user.setEmail("default@163.com");
		bbsRegisterService.doRegisterByAsynchronousMode(user);
		}catch (Exception e) {
			obj.put("mailAddress", "16");
			JSONUtils.printObject(obj);
			e.printStackTrace();
			throw e;
		}
		
		return null;
	}
	
	public String cellphoneforgetinit(){
		String cellphone = SqlInfusion.FilteSqlInfusion(request().getParameter("cp"));
		request().setAttribute("cellphone",cellphone );
		String key = Encrypt.encryptSES(cellphone+"-"+new Date().getTime()+"",IConstants.BBS_SES_KEY);
		String sign = Encrypt.MD5(key+IConstants.BBS_SES_KEY).substring(0,10)+key;
		request().setAttribute("sign",sign);

		return SUCCESS;
	}
	/**
	 * 通过手机更改用户登录密码
	 * @return
	 * @throws IOException
	 */
	public String cellphoneforgetinfo() throws IOException{
		JSONObject obj = new JSONObject();
		String sign  = SqlInfusion.FilteSqlInfusion(paramMap.get("cellphone"));
		String mdKey = sign.substring(0,10);
		String mdValue = sign.substring(10,sign.length());
		String mdCompare = Encrypt.MD5(mdValue+IConstants.BBS_SES_KEY).substring(0,10);
		String valAll = Encrypt.decryptSES(mdValue, IConstants.BBS_SES_KEY);
		if(!mdKey.equals(mdCompare)){
		JSONUtils.printStr("签名错误");
		return null;
		}
		String[] keys = valAll.split("-");
		String cellphone = keys[0].toString();
		String dateTime = keys[1].toString();
		long curTime = new Date().getTime();
		// 当用户点击注册时间大于于1分钟
		if (curTime - Long.valueOf(dateTime) >= 60 * 1000) {
		obj.put("mailAddress", "已超时");
		JSONUtils.printObject(obj);
		return null;
		}

		String phonecode=null;
		try {
			Object obje=session().getAttribute("phone");
			if(obje!=null){
				phonecode=obje.toString();
			}else{
				obj.put("mailAddress", "请输入正确的验证码");
				JSONUtils.printObject(obj);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		 
		if(phonecode!=null){
			if(!phonecode.trim().equals(cellphone.trim())){
				obj.put("mailAddress", "与获取验证码手机号不一致");
				JSONUtils.printObject(obj);
				return null;
			}
			
		}
		//验证码
		String vilidataNum = SqlInfusion.FilteSqlInfusion(paramMap.get("cellcode"));
        if(StringUtils.isBlank(vilidataNum)){
        	obj.put("mailAddress","请填写验证码");
        	JSONUtils.printObject(obj);
			return null;
        }
        
        String randomCode=null;
		Object objec=session().getAttribute("randomCode");
		if(objec!=null){
			randomCode=objec.toString();
		}else{
			obj.put("mailAddress","请输入正确的验证码");
        	JSONUtils.printObject(obj);
			return null;
		}
		if(randomCode!=null){
			if(!randomCode.trim().equals(vilidataNum.trim())){
				
				obj.put("mailAddress","请输入正确的验证码");
	        	JSONUtils.printObject(obj);
				return null;
			}
			
		}
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password")); // 用户密码
		if(StringUtils.isBlank(password)){
			obj.put("mailAddress", "1");
			JSONUtils.printObject(obj);
			return null;
		}
		//控制长度
		if(password.length()<6||password.length()>20){
			obj.put("mailAddress", "2");
			JSONUtils.printObject(obj);
			return null;
		}
		String confirmPassword = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmPassword")); // 用户密码
		if(StringUtils.isBlank(confirmPassword)){
			obj.put("mailAddress", "3");
			JSONUtils.printObject(obj);
			return null;
		}
		//检查用户是否存在通过手机号码]
		Map<String,String>	phonemap = null;
		
	try {
		phonemap = beVipService.queryIsPhoneonUser(cellphone);
	} catch (SQLException e1) {
		e1.printStackTrace();
	} catch (DataException e1) {
		e1.printStackTrace();
	}
   if(phonemap==null){
		obj.put("mailAddress", "6");
		JSONUtils.printObject(obj);
		return null;
   }		
		Long resutl = -1L;
		try {
			resutl = cellPhoneService.updatepasswordBycellphone(cellphone, password);
			if(resutl<=0){
				obj.put("mailAddress", "4");
				JSONUtils.printObject(obj);
				return null;
			}else{
				obj.put("mailAddress", "5");
				JSONUtils.printObject(obj);
                AccountUserDo user = this.getUser();
				bbsRegisterService.doUpdatePwdByAsynchronousMode(user.getUsername(),password, password,2);
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
		this.bbsRegisterService = bbsRegisterService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
}
