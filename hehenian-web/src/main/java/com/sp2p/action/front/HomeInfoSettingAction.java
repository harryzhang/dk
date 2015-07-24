package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.SqlInfusion;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.BBSRegisterService;
import com.sp2p.service.BeVipService;
import com.sp2p.service.BecomeToFinanceService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.RegionService;
import com.sp2p.service.SendMailService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.util.ChinaPnRInterface;

/**
 * 我的帐户  个人设置
 * @author Administrator
 *
 */
public class HomeInfoSettingAction extends BaseFrontAction {
	
	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private static final long serialVersionUID = 1L;

	private HomeInfoSettingService homeInfoSettingService;
	private UserService userService;
	private BecomeToFinanceService becomeFinanceService;
	private AdminService adminService;
	private FundManagementService fundManagementService;

	private BeVipService beVipService;
	private BBSRegisterService   bbsRegisterService;
	private SendMailService sendMailService;
	private OperationLogService  operationLogService;
	private RegionService regionService;
	private List<Map<String, Object>> provinceList;

    private List<Map<String, Object>> cityList;

    private long workPro = -1L;// 初始化省份默认值
    private long cityId = -1L;// 初始化话默认城市
	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}


	public String homeInfoSettingInit() throws SQLException, DataException{
		//获取用户的信息
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		
		Long id = user.getId();//获得用户编号
		//加载用户真实姓名
//		request().setAttribute("realName", user.getRealName());
		return SUCCESS;
	}
	
	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: renewalVIPInit  
	 * @Param: HomeInfoSettingAction  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:37:37
	 * @Return:    
	 * @Descb: 会员续费初始化
	 * @Throws:
	*/
	public String renewalVIPInit() throws SQLException, DataException{
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		try{
			Map<String, String> renewalVIPMap = homeInfoSettingService
					.queryRenewalVIP(user.getId());
			request().setAttribute("renewalVIPMap", renewalVIPMap);
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return "success";
	}
	
	
	/**   
	 * @throws IOException 
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: renewalVIPSubmit  
	 * @Param: HomeInfoSettingAction  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午10:51:50
	 * @Return:    
	 * @Descb: 提交会员续费
	 * @Throws:
	*/
	public String renewalVIPSubmit() throws IOException, SQLException, DataException{
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		JSONObject obj = new JSONObject();
		String code = SqlInfusion.FilteSqlInfusion((String) session().getAttribute("code_checkCode"));
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code") == null ? "" : paramMap
				.get("code"));
		if (!code.equals(_code)) {
			obj.put("msg", "验证码错误");
			JSONUtils.printObject(obj);
			return null;
		}
			Map<String,String> renewalVIPMap= homeInfoSettingService.renewalVIPSubmit(user.getId(),getPlatformCost());
			String result = renewalVIPMap.get("result") == null?"":renewalVIPMap.get("result");
			//续费成功
			if("1".equals(result)){
				user.setVipStatus(IConstants.VIP_STATUS);
				session().setAttribute(IConstants.SESSION_USER, user);
				obj.put("msg", "VIP续费成功");
				JSONUtils.printObject(obj);
				return null;
			}
			obj.put("msg", result);
			JSONUtils.printObject(obj);
		    return null;
	}
	
	/**
	 * 修改个人头像的时候判断是否填写过个人信息
	 * @return
	 */
	public String queryHeadImg() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		/*if (user.getRealName() == null || "".equals(user.getRealName())){
			JSONUtils.printStr("1");
			return null;
		}
		Map<String, String> map = homeInfoSettingService.queryHeadImg(user.getRealName());
		if(map != null){
			JSONUtils.printStr("2");
			return null;
		}*/
		return null;
	}
	
	/**   
	 * @throws DataException 
	 * @MethodName: updatePersonImg  
	 * @Param: HomeInfoSettingAction  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:08:12
	 * @Return:    
	 * @Descb: 修改个人头像
	 * @Throws:
	*/
	public String updatePersonImg() throws IOException, SQLException, DataException{
		//获取用户的信息
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		String imgPath = SqlInfusion.FilteSqlInfusion(paramMap.get("imgPath") == null?"":paramMap.get("imgPath"));
		JSONObject obj = new JSONObject();
		long returnId = -1;
		try{
			returnId = homeInfoSettingService.updatePersonImg(imgPath, user.getId());
	
			if (returnId <= 0) {
				obj.put("msg", IConstants.ACTION_FAILURE);
			} else {
				obj.put("msg", IConstants.ACTION_SUCCESS);
			}
//			user.setPersonalHead(imgPath);
			session().setAttribute(IConstants.SESSION_USER, user);
			JSONUtils.printObject(obj);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	/**
	 * 修改用户登录密码
	 * @return
	 * @throws Exception 
	 */
	public String updateLoginPass() throws Exception{
		String oldPass = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("oldPassword")), null);
		String newPass = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("newPassword")), null);
		String confirmPass = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("confirmPassword")), null);
		String type = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("type")), null);//用来标志修改的是登录密码还是交易密码
		//add by lw 判断交易面的长度 6 - 20
		 if(newPass.length()<6||newPass.length()>20){
				JSONUtils.printStr("4");
				return null;
		 }
		//end
		 if(!newPass.endsWith(confirmPass)){
			JSONUtils.printStr("1");
			return null;
		}
		
		//获取用户的信息
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		try{
			Long id = user.getId();//获得用户编号
			String password = null;
			if(type.endsWith("login")){
				password = user.getPassword();
			}else{
				Map<String,String> map = homeInfoSettingService.getDealPwd(id);
				//获得交易密码
				password = map.get("dealpwd");//交易密码默认为登录密码
				if(password == null || password.equals("")){
					password = user.getPassword();
				}
			}
			if ("1".equals(IConstants.ENABLED_PASS)){
				oldPass = com.shove.security.Encrypt.MD5(oldPass);
				newPass = com.shove.security.Encrypt.MD5(newPass);
			}else{
				oldPass = com.shove.security.Encrypt.MD5(oldPass+IConstants.PASS_KEY);
				newPass = com.shove.security.Encrypt.MD5(newPass+IConstants.PASS_KEY);
			}
			if(!oldPass.endsWith(password)){//旧密码输入错误
				JSONUtils.printStr("2");
				return null;
			}
			long result = homeInfoSettingService.updateUserPassword(id, newPass,type);
			
			if(result < 0){
				JSONUtils.printStr("3");
			}else{
				if(type.endsWith("login"))
					this.getUser().setPassword(newPass);
				bbsRegisterService.doUpdatePwdByAsynchronousMode(user.getUsername(),newPass, oldPass,1);
//				this.getUser().setEncodeP(Encrypt.encryptSES(newPass, IConstants.PWD_SES_KEY));
			}
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return null;
	}
	
	/**
	 * 查询银行卡信息，以表格显示
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 * @throws IOException 
	 */
	public String bankInfoSetInit() throws DataException,SQLException, IOException{
		//获取用户的信息
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();//获得用户编号
		try{
		    provinceList = regionService.queryRegionListHHN(-1L, 1L, 1);;
            cityList = regionService.queryRegionList(-1L, workPro, 2);
//            if(user.getUsrCustId()==-1 || user.getUsrCustId().equals(""))
//            {
//                JSONUtils.printStr("请先注册汇付账户");
//                request().setAttribute("msg", "请先注册汇付账户!");
//                return null;
//            }
			List<Map<String,Object>> lists = homeInfoSettingService.queryBankInfoList2(id);
			request().setAttribute("lists", lists);
//			request().setAttribute("realName", user.getRealName());
			request().setAttribute("usrCustId", user.getUsrCustId());
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 查询银行卡信息，以表格显示
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	public String queryBankInfoInit() throws DataException,SQLException{
		//获取用户的信息
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		
		Long id = user.getId();//获得用户编号
		try{
			List<Map<String,Object>> lists = homeInfoSettingService.queryBankInfoList(id);
			request().setAttribute("lists", lists);
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 添加提现银行信息
	 * @return
	 * @throws Exception 
	 */
	public String addBankInfo() throws Exception{
		String cardUserName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("cardUserName")), null);
		String bankName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("bankName")), null);
		String openBankId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("openBankId")), null);
		String subBankName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("subBankName")), null);
		String bankCard = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("bankCard")), null);
		
		String province = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("province")), null);
        String city = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("city")), null);
		//获取用户的信息
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();//获得用户编号
		
		String provinceId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("provinceId")), null);
        String cityId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("cityId")), null);
		try{
			Map<String,String> map = homeInfoSettingService.queryCardStatus(id);
			int bindingCardNum = Convert.strToInt(map.get("count(*)"), 0);
			if(bindingCardNum >= 2){//已经绑定两张银行卡，不能再绑定了
				JSONUtils.printStr("2");
				return null;
			}
			//新添加的提现银行卡信息状态为2，表示申请中
			long result = homeInfoSettingService.addBankCardInfo(id, cardUserName, bankName,
	                      subBankName, bankCard, IConstants.BANK_CHECK, province, city,openBankId,provinceId,cityId);
			operationLogService.addOperationLog("t_bankcard", user.getUsername(), IConstants.INSERT, user.getLastIP(), 0,"添加提现银行信息", 1);
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	
	/**
	 * 添加提现银行信息
	 * @return
	 * @throws Exception 
	 */
	public String addBankInfo2() throws Exception{
		String cardUserName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("cardUserName")), null);
		String bankName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("bankName")), null);
		String openBankId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("openBankId")), null);// 银行代号
		String subBankName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("subBankName")), null);
		String bankCard = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("bankCard")), null); // 开户银行账号
		String province = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("province")), null);
		String city = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("city")), null);
		String provinceId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("provinceId")), null);
        String cityId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("cityId")), null);
        String cmdId = "BgBindCard";
        
        Long bankId = Convert.strToLong(paramMap.get("bankId"), -1);
        String openAcctId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("bankCard")), ""); // 开户银行账号
        String openProvId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("provinceId")), ""); // 开户银行省份代号
        String openAreaId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("cityId")), ""); // 开户银行地区代号
        String openBranchName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("subBankName")), ""); // 开户支行
        
		//获取用户的信息
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();//获得用户编号
		try{
		    //查询银行卡是否存在
		    long one = homeInfoSettingService.queryBankCard(id,openAcctId);
		    if(one > 0)
		    {
		        JSONUtils.printStr("1");
		        return null;
		    }
			Map<String,String> map = homeInfoSettingService.queryCardStatus(id);
			int bindingCardNum = Convert.strToInt(map.get("count(*)"), 0);
			if(bindingCardNum >= 5){//已经绑定5张银行卡，不能再绑定了
				JSONUtils.printStr("2");
				return null;
			}
			// 用户绑定银行卡
            JSONObject json = JSONObject.fromObject(ChinaPnRInterface.bgBindCard(cmdId, user.getUsrCustId()+"", openBankId, openAcctId, openProvId, openAreaId,
                    openBranchName));
            int ret = json.getInt("RespCode");
            if (ret == 0) {
                fundManagementService.updateBankInfo(user.getId(), bankId, "通过", 1, user.getUsername(), user.getLastIP());
              //新添加的提现银行卡信息状态为2，表示申请中
                long result = homeInfoSettingService.addBankCardInfo(id, cardUserName, bankName,
                        subBankName, bankCard, 1, province, city, openBankId,provinceId,cityId);
                if(result < 0){
                    JSONUtils.printStr2("失败:" + json.getString("RespDesc"));
                    return null;
                }
                operationLogService.addOperationLog("t_bankcard", user.getUsername(), IConstants.INSERT, user.getLastIP(), 0,"添加提现银行信息", 1);
                JSONUtils.printStr2("操作成功");
            } else {
                JSONUtils.printStr2("失败:" + json.getString("RespDesc"));
            }
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			JSONUtils.printStr2("失败:" + "操作失败");
		}
		return SUCCESS;
	}
	
	/**
	 * 删除提现银行卡信息（这里删除未绑定的银行卡）
	 * @return
	 * @throws SQLException
	 */
	public String deleteBankInfo() throws SQLException{
		Long id = Convert.strToLong(request("bankId"), -1L);
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		try{
			long result = homeInfoSettingService.deleteBankInfo(id);
			//添加系统操作日志
			operationLogService.addOperationLog("t_bankcard", user.getUsername(), IConstants.DELETE, user.getLastIP(), 0, "删除未绑定的银行卡信息", 1);
		}catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 手机绑定页面加载
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String bindingMobileInit() throws DataException, SQLException, IOException{
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		try{
			//查询成功绑定的手机信息
//			Map<String,String> map = homeInfoSettingService.
//						querySucessBindingInfoByUserId(user.getId(),1);
			Map<String,String> map = homeInfoSettingService.
						querySucessBindingInfoByUserId(user.getId());
			JSONObject object = new JSONObject();
			if(map == null){
				object.put("map", "");
			}else{
				object.put("map", map);
			}
			JSONUtils.printObject(object);
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	/**
	 * 添加手机号码绑定信息
	 * @return
	 * @throws Exception 
	 */
	public Long addBindingMobile() throws Exception{
		//为空在jsp页面已经验证
		String mobile = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("mobile")),null);
		String code = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("code")),null);
		String content = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("content")), "");
		
		//手机号码验证
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");       
        Matcher m = p.matcher(mobile); 
        if(!m.matches()){//手机号码无效
        	JSONUtils.printStr("1");
        	return null;
        }
        
//..............................................
		 //手机号码与验证码号码匹配
		Object objcet=session().getAttribute("phone");
		if(objcet!=null){
			String phonecode=objcet.toString();
			if(!phonecode.trim().equals(mobile.trim())){
			    
				JSONUtils.printStr("10");
					return null;
			}
		}else{
				
				JSONUtils.printStr("11");
				return null;
		}
		//验证码
	
       if(StringUtils.isBlank(code)){
       
       	JSONUtils.printStr("12");
			return null;
       }		
	    Object obje=session().getAttribute("randomCode");
		if(obje!=null){
		    String randomCode=obje.toString();
		    if(!randomCode.trim().equals(code.trim())){					
			  
		    JSONUtils.printStr("13");
			   return null;
	        }
	    }else{
			
			JSONUtils.printStr("11");
	 			return null;
	    }
//..........................................................
			
      
      //获取用户的信息
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();//获得用户编号
		
		try{
	        //首先查看该用户有没有设置手机绑定
	        Map<String,String> mp = homeInfoSettingService.queryBindingInfoByUserId(id);
	        if(mp != null){//如果该用户已经绑定了手机号码信息
	        	String status = Convert.strToStr(mp.get("status"), null);//查看手机状态
	        	if(status != null){
		        	if(status.equals(IConstants.PHONE_BINDING_ON+"")){//手机号码已经绑定，需要申请更换手机
		        		JSONUtils.printStr("7");
		            	return null;
		        	}else if(status.equals(IConstants.PHONE_BINDING_CHECK+"")){//手机号码正在审核，请等待
		        		
		        		JSONUtils.printStr("8");
		            	return null;
		        	}else if(status.equals(IConstants.PHONE_BINDING_UNPASS+"")){//手机审核不通过
		        		JSONUtils.printStr("9");
		            	return null;
		        	}
		        }
	        }
	        
	        //查看填写的手机号码是不是已经被别人绑定或者在申请绑定
	        Map<String,String> map = homeInfoSettingService.queryBindingMobile(mobile);
	        
	        if(map != null){
	        	String status = Convert.strToStr(map.get("status"), null);
		        if(status != null){
		        	if(status.equals(IConstants.PHONE_BINDING_ON+"")){//手机号码已经绑定，需要申请更换手机
		        		JSONUtils.printStr("3");
		            	return null;
		        	}else if(status.equals(IConstants.PHONE_BINDING_CHECK+"")){//手机号码正在审核，请等待
		        		session().removeAttribute("randomCode");
		        		JSONUtils.printStr("4");
		            	return null;
		        	}
		        }
	        }
	      
	        //add by lw  查询已经绑定的手机号码
	        Map<String,String> phoneMap = null;
	        String oldPhone = null;
	        phoneMap = beVipService.queryPUser(id);
	        if(phoneMap.size()>0&&phoneMap!=null){
	        	oldPhone = phoneMap.get("cellphone");
	        }
	        //end
	        
			//添加手机绑定信息，手机绑定状态位2.2代表正在审核
	        Long result = homeInfoSettingService.addBindingMobile(mobile, id, IConstants.PHONE_BINDING_CHECK,content,oldPhone);
	        if(result < 0){//手机绑定失败
	        	JSONUtils.printStr("5");
	        	return null;
	        }
	        
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
        return null;
	}
	
	public Long addChangeBindingMobile() throws IOException, SQLException, DataException{
		//为空在jsp页面已经验证
		String mobile = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("mobile")),null);
		String code = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("code")),null);
		String content = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("content")), "");
		
		//手机号码验证
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");       
        Matcher m = p.matcher(mobile); 
        if(!m.matches()){//手机号码无效
        	JSONUtils.printStr("1");
        	return null;
        }
        
        
      //..............................................
		 //手机号码与验证码号码匹配
		Object objcet=session().getAttribute("phone");
		if(objcet!=null){
			String phonecode=objcet.toString();
			if(!phonecode.trim().equals(mobile.trim())){
			    
				JSONUtils.printStr("10");
					return null;
			}
		}else{
				
				JSONUtils.printStr("11");
				return null;
		}
		//验证码
	
      if(StringUtils.isBlank(code)){
      
      	JSONUtils.printStr("12");
			return null;
      }		
	    Object obje=session().getAttribute("randomCode");
		if(obje!=null){
		    String randomCode=obje.toString();
		    if(!randomCode.trim().equals(code.trim())){					
			  
		    JSONUtils.printStr("13");
			   return null;
	        }
	    }else{
			
			JSONUtils.printStr("11");
	 			return null;
	    }
//..........................................................
			        
       try{
	      //获取用户的信息
			AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
			Long id = user.getId();//获得用户编号
	        //首先查看该用户有没有设置手机绑定
	        List<Map<String,Object>> mp = homeInfoSettingService.queryBindingsByUserId(id);
	        if(mp == null){//如果该用户没有绑定了手机号码信息
	        	JSONUtils.printStr("3");
	        	return null;
	        }else{//查看用户手机的状态 是否已经申请了变更
	        	for(Map<String,Object> mpp : mp){
		        	String status = Convert.strToStr(mpp.get("status").toString(), null);
		        	if(status != null){
		        		if(status.equals(IConstants.PHONE_BINDING_CHECK+"")){//绑定手机还在审核中，不能变更
		        			JSONUtils.printStr("4");
		                	return null;
		        		}else if(status.equals(IConstants.PHONE_BINDING_UNPASS+"")){//手机审核不通过
		        			JSONUtils.printStr("8");
		                	return null;
		        		}
		        	}
	        	}
	        }
	        //查看变更的手机号码是否别人绑定了
	        Map<String,String> map = homeInfoSettingService.queryBindingMobile(mobile);
	        
	        if(map != null){
	        	String status = Convert.strToStr(map.get("status"), null);
		        if(status != null){
		        	if(status.equals(IConstants.PHONE_BINDING_ON+"")){//手机号码已经被别人绑定，需要申请更换手机
		        		JSONUtils.printStr("6");
		            	return null;
		        	}else if(status.equals(IConstants.PHONE_BINDING_CHECK+"")){//手机号码正在审核，请等待
		        		JSONUtils.printStr("7");
		            	return null;
		        	}else if(status.equals(IConstants.PHONE_BINDING_UNPASS+"")){//手机审核不通过
	        			JSONUtils.printStr("9");
	                	return null;
	        		}
		        }
	        }
	        //add by lw
	        
	        //add by lw  查询已经绑定的手机号码
	        Map<String,String> phoneMap = null;
	        String oldPhone = null;
	        phoneMap = beVipService.queryPUser(id);
	        if(phoneMap.size()>0&&phoneMap!=null){
	        	oldPhone = phoneMap.get("cellphone");
	        }
	        //end
	        //end
			//进行手机变更（状态为正在审核）
	        Long result = homeInfoSettingService.addBindingMobile(mobile, id, IConstants.PHONE_BINDING_CHECK,content,oldPhone);
	        if(result < 0){//手机变更失败
	        	JSONUtils.printStr("5");
	        	return null;
	        }
	        operationLogService.addOperationLog("t_phone_binding_info",user.getUsername() , IConstants.INSERT, user.getLastIP(), 0, "发布手机变更请求", 1);
	        session().removeAttribute("randomCode");
       }catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
        return null;
	}
	
	
	/**
	 * 通知设置加载，加载的时候从数据库中读取已经设置的数据
	 * 查询两个地方
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String queryNotesSettingInit() throws SQLException, DataException, IOException{
		// 获取用户的信息
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号
		try {
			Map<String, String> notes = homeInfoSettingService
					.queryNotesList(id);
			List<Map<String, Object>> lists = homeInfoSettingService
					.queryNotesSettingList(id);

			if (lists == null) {
				JSONUtils.printStr("1");
				return null;
			} else {
				if (notes == null && lists.size() <= 0) {// 没有值
					JSONUtils.printStr("1");
					return null;
				}
			}

			List<Map<String, Object>> values = changeList2List(notes, lists);
			String jsonStr = JSONArray.toJSONString(values);
			JSONUtils.printStr(jsonStr);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return null;
	}
	
	private List<Map<String,Object>> changeList2List(Map<String,String> notes,List<Map<String,Object>> lists){
		List<Map<String,Object>> values = new ArrayList<Map<String,Object>>();
		boolean message = false,mail = false,note = false;
		if(notes.get("mailNoticeEnable").equals(IConstants.NOTICE_ON+"")){//2为开启状态
		    message = true;
		}
		if(notes.get("emailNoticeEnable").equals(IConstants.NOTICE_ON+"")){
			mail = true;
		}
		if(notes.get("noteNoticeEnable").equals(IConstants.NOTICE_ON+"")){
			note = true;
		}
		Map<String,Object> val = null;
		if(lists != null && lists.size() >0){
			for(Map<String,Object> o : lists){
				val = add(message,mail,note,o.get("noticeMode"),o.get("reciveRepayEnable"),o.get("showSucEnable"),
						o.get("loanSucEnable"),o.get("rechargeSucEnable"),o.get("capitalChangeEnable"));
				values.add(val);
			}
		}
		return values;
	}
	
	private Map<String,Object> add(boolean message,boolean mail,boolean note,Object noticeMode,Object reciveRepayEnable,Object showSucEnable,
			Object loanSucEnable,Object rechargeSucEnable,Object capitalChangeEnable){
		Map<String,Object> mg = new HashMap<String,Object>();
		mg.put("message", message);
		mg.put("mail", mail);
		mg.put("note", note);
		mg.put("noticeMode", noticeMode);//通知方式(1 邮件 2 站内信 3 短信)
		mg.put("reciveRepayEnable", reciveRepayEnable);
		mg.put("showSucEnable", showSucEnable);
		mg.put("loanSucEnable", loanSucEnable);
		mg.put("rechargeSucEnable", rechargeSucEnable);
		mg.put("capitalChangeEnable", capitalChangeEnable);
		return mg;
	}
	
	/**
	 * 添加通知设置
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws DataException 
	 */
	public Long addNotesSetting() throws SQLException, IOException, DataException{
		//站内信
		
		  boolean message = paramMap.get("message")==null?false:true;//Convert.strToBoolean(paramMap.get("message"),false);//站内信总复选框
	      boolean messageReceive = paramMap.get("messageReceive")==null?false:true;
	      boolean messageDeposit = paramMap.get("messageDeposit")==null?false:true;
	      boolean messageBorrow = paramMap.get("messageBorrow")==null?false:true;
	      boolean messageRecharge = paramMap.get("messageRecharge")==null?false:true;
	      boolean messageChange = paramMap.get("messageChange")==null?false:true;
	      //邮件
	      boolean mail = paramMap.get("mail")==null?false:true;
	      boolean mailReceive = paramMap.get("mailReceive")==null?false:true;
	      boolean mailDeposit = paramMap.get("mailDeposit")==null?false:true;
	      boolean mailBorrow = paramMap.get("mailBorrow")==null?false:true;
	      boolean mailRecharge = paramMap.get("mailRecharge")==null?false:true;
	      boolean mailChange = paramMap.get("mailChange")==null?false:true;
	      //短信
	      boolean notes = paramMap.get("note")==null?false:true;
	      boolean noteReceive = paramMap.get("noteReceive")==null?false:true;
	      boolean noteDeposit = paramMap.get("noteDeposit")==null?false:true;
	      boolean noteBorrow = paramMap.get("noteBorrow")==null?false:true;
	      boolean noteRecharge = paramMap.get("noteRecharge")==null?false:true;
	      boolean noteChange = paramMap.get("noteChange")==null?false:true;
	      
	      //获取用户的信息
		  AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		  Long id = user.getId();//获得用户编号
		  Map<String,String> map = userService.queryUserById(id);
		   if(mail){
			  String ismail =Convert.strToStr( map.get("email"),"");
			  if (StringUtils.isBlank(ismail)) {
				  JSONUtils.printStr("3");
		          return null;
			  }
		  }
		  try{
		      long result = homeInfoSettingService.addNotesSetting(id, messageReceive, 
		    		  messageDeposit, messageBorrow, messageRecharge, 
		    		  messageChange, mailReceive, mailDeposit, mailBorrow, mailRecharge, 
		    		  mailChange, noteReceive, noteDeposit, noteBorrow, noteRecharge, noteChange);
		      
		      long result2 = homeInfoSettingService.addNotes(id, message, mail, notes);
		      //添加操作日志
		      long result3 = operationLogService.addOperationLog("t_noticecon", user.getUsername(), IConstants.UPDATE, user.getLastIP(), 0, "修改通知设置", 1);
		      if(result < 0 || result2 < 0 ||result3 < 0){//设置失败
		    	  JSONUtils.printStr("1");
		          return null;
		      }
		  }catch (DataException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}catch (IOException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}
	      return null;
	}

	public String mailNoticeInit() throws SQLException, DataException{
		//加载邮件信息
		//获取用户的信息
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		request().setAttribute("userName", user.getUsername());
		return SUCCESS;
	}
	
	/**
	 * 判断收件人是否有效
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String judgeUserName() throws SQLException, DataException, IOException{
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		String receiver = SqlInfusion.FilteSqlInfusion(paramMap.get("receiver")); //收件人
		try{
			//检查用户名是否存在 t_user
			long result = homeInfoSettingService.getConcernList(user.getId(), receiver);
			if (result < 0) { // 用户名不存在
				//到t_admin表中检查用户名
				List<Map<String,Object>>  lists = adminService.queryAdminList(receiver, 1);
				if(lists == null || lists.size() <= 0){
					JSONUtils.printStr("1");
					return null;
				}
			}
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	/**
	 * 添加邮件
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws DataException 
	 */
	public String addMail() throws IOException, DataException, SQLException{
		String receiver = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("receiver")),null);
		String title = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("title")),null);
		String content = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("content")),null);
		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId")); // 验证码
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code").toString().trim());
		if (code == null || !_code.equals(code)) {
			JSONUtils.printStr(IConstants.USER_REGISTER_CODE_ERROR);
			return null;
		}
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Integer enable=user.getEnable();
		if(enable==3){
			JSONUtils.printStr("8");
			return null;
		}
		Long id = user.getId();//获得用户编号
		try{
			//前台页面进行了判断，这里名称不可能为空
			Map<String,String> map = userService.queryIdByUser( receiver);
			Long receiverId = -2L;
			if(map == null || map.size() < 0){//到t_admin表中查数据
				List<Map<String,Object>> lists = adminService.queryAdminList(receiver, 1);
				receiverId = Convert.strToLong(lists.get(0).get("id").toString(), -1L);
			}else{
				receiverId = Convert.strToLong(map.get("id"), -1L);
			}
			
			long result = -1;
			/**
			 * 如果是发给admin，系统管理员，则该邮件为系统邮件(如果发件人或者收件人为admin,则为系统消息)
			 */
			if(receiver.equalsIgnoreCase(IConstants.MAIL_SYS)){//新发送的邮件默认为未读 IConstants.MAIL_UN_READ
				result = homeInfoSettingService.addMail(id,receiverId, 
						title, content, IConstants.MAIL_UN_READ, IConstants.MALL_TYPE_SYS);
			}else if(user.getUsername().equalsIgnoreCase(IConstants.MAIL_SYS)){
				result = homeInfoSettingService.addMail(id,receiverId, 
						title, content, IConstants.MAIL_UN_READ, IConstants.MALL_TYPE_SYS);
			}else{
				result = homeInfoSettingService.addMail(id,receiverId, 
						title, content, IConstants.MAIL_UN_READ, IConstants.MALL_TYPE_COMMON);
			}
			if(result  < 0){
				JSONUtils.printStr("1");
				return null;
			}
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	/**
	 * 获得用户的收件箱信息(一般信息)
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryReciveMails() throws SQLException, DataException{
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();//获得用户编号
		
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		int mailStatus = paramMap.get("mailStatus") == null?-1:Convert.strToInt( paramMap.get("mailStatus"), -1);
		try{
				
			homeInfoSettingService.queryReceiveMails(pageBean, id,
					IConstants.MALL_TYPE_COMMON, "",mailStatus);
			List<Map<String,Object>> lists = pageBean.getPage();
			if(lists!=null)
				changeLists2Lists(lists,"");
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	public String querySendMailsInit(){
		return SUCCESS;
	}
	
	public String queryReceiveMailsInit(){
		return SUCCESS;
	}
	
	public String querySysMailsInit(){
		return SUCCESS;
	}
	
	/**
	 * 获得用户的发件箱信息(一般信息)
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String querySendMails() throws SQLException, DataException{
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();//获得用户编号
		
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		try {
			homeInfoSettingService.querySendMails(pageBean, id);
			List<Map<String, Object>> lists = pageBean.getPage();
			if (lists != null)
				changeLists2Lists2(lists);
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 获得用户系统信息
	 */
	public String querySysMails() throws SQLException, DataException{
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();//获得用户编号
		
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		int mailStatus = paramMap.get("mailStatus") == null?-1:Convert.strToInt(paramMap.get("mailStatus"), -1);
		try {
			homeInfoSettingService.queryReceiveMails(pageBean, id,
					IConstants.MALL_TYPE_SYS, "sys",mailStatus);

			
			List<Map<String,Object>> lists = pageBean.getPage();
			if(lists != null){
				changeLists2Lists(lists,"sys");
			}
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 更改lists里面的一些信息。这样前台直接显示。
	 * 将用户id改成用户名，信息状态更改中文显示
	 * @throws SQLException 
	 * @throws DataException 
	 */
	private void changeLists2Lists(List<Map<String,Object>> lists,String type) throws DataException, SQLException{
		String username = "";
		Date sendTime = null;
		Map<String,String> mp = null;
		int status = -1;
		try{
			for(Map<String,Object> map : lists){
				if(type.equalsIgnoreCase("sys")){
					mp = adminService.queryAdminById(Convert.strToLong(map.get("sender").toString(),-1));
					if(mp != null && mp.size() >0){
						username = Convert.strToStr(mp.get("userName"), "");
						map.put("sender", username);
					}
				}else{
					mp = userService.queryUserById(Convert.strToLong(map.get("sender").toString(),-1));
					if(mp != null && mp.size() >0){
						username = Convert.strToStr(mp.get("username"), "");
						map.put("sender", username);
					}
				}
				status = Convert.strToInt( map.get("mailStatus").toString(),-1);
				if(status == IConstants.MAIL_READED){
					map.put("mailStatus", "已读");
				}else if(status == IConstants.MAIL_UN_READ){
					map.put("mailStatus", "未读");
				}
			}
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}
	
	private void changeLists2Lists2(List<Map<String,Object>> lists) throws DataException, SQLException{
		String username = "";
		for(Map<String,Object> map : lists){
			username = this.getUserNameById(Convert.strToLong(map.get("reciver").toString(),-1));
			if(username.equals("")){
				username = this.getAdminNameById(Convert.strToLong(map.get("reciver").toString(),-1));
			}
			map.put("reciver", username);
		}
	}
	
	
	public String deleteMails() throws SQLException, DataException{
		String ids = SqlInfusion.FilteSqlInfusion(request("ids"));
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		String[] allIds = ids.split(",");//进行全选删除的时候获得多个id值
		if (allIds.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if(tempId == -1){
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		homeInfoSettingService.deleteMails(ids,user.getId());
		return SUCCESS;
	}
	
	/**
	 * 更新邮件状态
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String updateMail() throws SQLException, DataException{
		String ids = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("ids")),"");
		String type = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("type")),"");
		String[] allIds = ids.split(",");//进行全选删除的时候获得多个id值
		if (allIds.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if(tempId == -1){
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		try{
			long result = -1;
			//站内信状态(1 默认未读 2 删除 3 已读)
			if(type.equals("readed")){//标记为已读
				result = homeInfoSettingService.updateMails(ids,IConstants.MAIL_READED);
			}else if(type.equals("unread")){//标记为未读
				result = homeInfoSettingService.updateMails(ids,IConstants.MAIL_UN_READ);
			}
			if(result < 0)
				return null;
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 根据用户id获得用户名
	 * @param userId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	private String getUserNameById(long userId) throws DataException, SQLException{
		try{
			Map<String,String> mp = userService.queryUserById(userId);
			if(mp != null){
				return Convert.strToStr(mp.get("username"), "");
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return "";
	}
	
	private String getAdminNameById(long adminId)throws DataException, SQLException{
		try{
			Map<String,String> mp = adminService.queryAdminById(adminId);
			if(mp != null){
				return Convert.strToStr(mp.get("userName"), "");
			}
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return "";
	}
	
	/**
	 * 查询邮件内容
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryEmailById() throws SQLException, DataException {
		Long mailId = Convert.strToLong(request("mailId"), -1);
		int type = Convert.strToInt(request("type"), 0);
		int curPage = request("curPage") == null?1:Convert.strToInt(request("curPage"), 1);
        AccountUserDo  user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		try {
			Map<String, String> map = homeInfoSettingService
					.queryEmailById(mailId);
			if (map == null) {
				return null;
			}
			Long userId = user.getId();
			@SuppressWarnings("unused")
			long result = -1;
			/*if (type == 1) {// 如果是未读信息，则更新数据库，将状态改为已读
				//add by houli
				if(user.getVirtual() != 1){//virtual=1 是后台虚拟用户登录，不用改变邮件状态
					result = homeInfoSettingService.updateMails(mailId + "",
							IConstants.MAIL_READED);
				}
			}*/
			String sender = "", receiver = "", title = "", date = "", content = "";

			int mt = 0;//发件箱
			if (map.get("sender").equals(userId + "")) {
				sender = user.getUsername();
				mt = 100;//标记发件箱
			} else {
				if (map.get("mailType").equals(IConstants.MAIL_SYS_ + "")) {
					sender = getAdminNameById(Convert.strToLong(map
							.get("sender"), -1));
				} else {
					sender = getUserNameById(Convert.strToLong(map
							.get("sender"), -1));
					
				}
			}

			if (map.get("reciver").equals(userId + "")) {
				receiver = user.getUsername();
			} else {
				if (map.get("mailType").equals(IConstants.MAIL_SYS_ + "")) {
					receiver = getAdminNameById(Convert.strToLong(map
							.get("reciver"), -1));
				} else {
					receiver = getUserNameById(Convert.strToLong(map
							.get("reciver"), -1));
				}
			}
			// 操作日志
			operationLogService.addOperationLog("t_mail", user.getUsername(), IConstants.UPDATE, user.getLastIP(), 0, "查看站内信", 1);
			title = map.get("mailTitle");
			date = map.get("sendTime");
			content = map.get("mailContent");
			request().setAttribute("sender", sender);
			request().setAttribute("receiver", receiver);
			request().setAttribute("title", title);
			request().setAttribute("date", date);
			request().setAttribute("content", content);
			request().setAttribute("curPage", curPage);
			if(mt == 100){
				request().setAttribute("mType", 100);
			}else{
				request().setAttribute("mType", Convert.strToInt(map.get("mailType")+"", 0));
			}
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 成为理财人页面初始化
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String become2FinanceInit() throws SQLException, DataException{
		//成为理财人必须是在会员登录以后
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		try{
			Map<String,String> map = becomeFinanceService.queryFinancer(userId);
			if(map == null){//没有记录，非理财人
				return INPUT;
			}else{
				int status = Convert.strToInt(map.get("status"), 1);
				if(status == IConstants.FINANCE_NON){//如果已经是填写了理财人的信息，
					return "waiting";
				}else{
					return SUCCESS;
				}
			}
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}
	public String queryOneBankInfo() throws SQLException, DataException{
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		
		Long bankId = request("bankId")==null?-100:Convert.strToLong(request("bankId"), -100);
		try{
			
			provinceList = regionService.queryRegionListHHN(-1L, 1L, 1);;
            cityList = regionService.queryRegionList(-1L, workPro, 2);
            
			Map<String,String> map = fundManagementService.queryOneBank(bankId);
			if(map == null)
				map = new HashMap<String,String>();
			request().setAttribute("bankCard", map.get("cardNo"));
			request().setAttribute("bankId", map.get("id"));
//			request().setAttribute("realName", user.getRealName());
			request().setAttribute("bankName", map.get("bankName"));
			request().setAttribute("province", map.get("province") );
			request().setAttribute("regCity",  map.get("city"));
		}catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/** 
	 * 银行卡变更
	 * @return
	 * @throws SQLException
	 * @throws IOException [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @throws DataException 
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String updateBankInfo() throws SQLException, IOException, DataException{
        AccountUserDo user = (AccountUserDo)session().getAttribute("user");
		Long bankId = paramMap.get("bankId")==null?-100:Convert.strToLong(paramMap.get("bankId"), -100);
		String mBankName = SqlInfusion.FilteSqlInfusion(paramMap.get("mBankName")==null? null:Convert.strToStr(paramMap.get("mBankName"), null));
		String modifiedOpenBankId = SqlInfusion.FilteSqlInfusion(paramMap.get("modifiedOpenBankId")==null? null:Convert.strToStr(paramMap.get("modifiedOpenBankId"), null));
		String mSubBankName = SqlInfusion.FilteSqlInfusion(paramMap.get("mSubBankName")==null? null:Convert.strToStr(paramMap.get("mSubBankName"), null));
		String mBankCard = SqlInfusion.FilteSqlInfusion(paramMap.get("mBankCard")==null? null:Convert.strToStr(paramMap.get("mBankCard"), null));
		String province = SqlInfusion.FilteSqlInfusion(paramMap.get("province")==null? null:Convert.strToStr(paramMap.get("province"), null));
		String city = SqlInfusion.FilteSqlInfusion(paramMap.get("city")==null? null:Convert.strToStr(paramMap.get("city"), null));
		String provinceId = SqlInfusion.FilteSqlInfusion(paramMap.get("provinceId")==null? null:Convert.strToStr(paramMap.get("provinceId"), null));
        String cityId = SqlInfusion.FilteSqlInfusion(paramMap.get("cityId")==null? null:Convert.strToStr(paramMap.get("cityId"), null));
        
        String cmdId = "BgBindCard";
        String openBankId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("modifiedOpenBankId")), null);// 银行代号
        String openAcctId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("mBankCard")), ""); // 开户银行账号
        String openProvId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("provinceId")), ""); // 开户银行省份代号
        String openAreaId = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("cityId")), ""); // 开户银行地区代号
        String openBranchName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("mSubBankName")), ""); // 开户支行
		try{
		    // 用户绑定银行卡
            JSONObject json = JSONObject.fromObject(ChinaPnRInterface.bgBindCard(cmdId, user.getUsrCustId()+"", openBankId, openAcctId, openProvId, openAreaId,
                    openBranchName));
            int ret = json.getInt("RespCode");
            if (ret == 0) {
                fundManagementService.updateBankInfo(user.getId(), bankId, "通过", 1, user.getUsername(), user.getLastIP());
                Long result = fundManagementService.updateChangeBank(bankId, mBankName,modifiedOpenBankId,mSubBankName, province, city, mBankCard,
                    1,new Date(),true,provinceId,cityId);
                if(result < 0){
                    JSONUtils.printStr2("失败:" + json.getString("RespDesc"));
                    return null;
                }
                operationLogService.addOperationLog("t_bankcard", user.getUsername(), IConstants.INSERT, user.getLastIP(), 0,"添加提现银行信息", 1);
                JSONUtils.printStr2("操作成功");
            } else {
                JSONUtils.printStr2("失败:" + json.getString("RespDesc"));
            }
			
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	/**
	 * 取消银行卡变更
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String bankChangeCancel() throws SQLException, IOException{
		Long bankId = request("bankId")==null?-100:Convert.strToLong(request("bankId"), -100);
        AccountUserDo  user = (AccountUserDo) session().getAttribute("user");
		try{
			Long result = fundManagementService.updateChangeBank(bankId, "","","", "", "", "",
					IConstants.BANK_SUCCESS,null,false, "", "");
			result = operationLogService.addOperationLog("t_bankcard", user.getUsername(), IConstants.UPDATE, user.getLastIP(), 0, "取消银行卡变更", 1);
			if(result < 0){
			    JSONUtils.printStr("1");
			    return null;
			}
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	public String financerWaiting() throws SQLException, DataException{
		return SUCCESS;
	}
	
	public String addBecomeFinance() throws SQLException, IOException{
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));
		String cellPhone = SqlInfusion.FilteSqlInfusion(paramMap.get("cellPhone"));
		String idNo = SqlInfusion.FilteSqlInfusion(paramMap.get("idNo"));
		String code = SqlInfusion.FilteSqlInfusion(paramMap.get("send_phoneCode"));
		
		
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");       
        Matcher m = p.matcher(cellPhone); 
        if(!m.matches()){//手机号码无效
        	JSONUtils.printStr("7");
        	return null;
        }
        if(StringUtils.isBlank(cellPhone)){
            
          	JSONUtils.printStr("8"); //手机号为空
    			return null;
          }		
        
      //..............................................
		 //手机号码与验证码号码匹配
		Object objcet=session().getAttribute("phone");
		if(objcet!=null){
			String phonecode=objcet.toString();
			if(!phonecode.trim().equals(cellPhone.trim())){
			    
				JSONUtils.printStr("10");
					return null;
			}
		}else{
				
				JSONUtils.printStr("11");
				return null;
		}
		//验证码
	
      if(StringUtils.isBlank(code)){
      
      	JSONUtils.printStr("12");
			return null;
      }		
	    Object obje=session().getAttribute("randomCode");
		if(obje!=null){
		    String randomCode=obje.toString();
		    if(!randomCode.trim().equals(code.trim())){					
			  
		    JSONUtils.printStr("13");
			   return null;
	        }
	    }else{
			
			JSONUtils.printStr("11");
	 			return null;
	    }
//..........................................................
		
		try{
			long result = becomeFinanceService.addBecomeFinancer(userId, realName, cellPhone, 
					idNo, IConstants.FINANCE_NON);
			
			if(result < 0){
				JSONUtils.printStr("1");
			}
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	/**
	 * 邮件回复
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String replayMail() throws SQLException, DataException{
		
		Long mailId = Convert.strToLong(request("id"),-1L);
		int type = Convert.strToInt(request("type"), 0);
		
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();
		
		try{
			Map<String,String> map = homeInfoSettingService.queryEmailById(mailId);
			if(map == null){
				return null;
			}
			long result = -1;
			if(type == 1){//如果是未读信息，则更新数据库，将状态改为已读
				result = homeInfoSettingService.updateMails(mailId+"",IConstants.MAIL_READED);
			}
			String sender = "",receiver = "",title="",date="",content="";
			
			if(map.get("sender").equals(userId+"")){
				sender = user.getUsername();
			}else{
				sender = getUserNameById(Convert.strToLong(map.get("sender"), -1));
			}
			
			if(map.get("reciver").equals(userId+"")){
				receiver = user.getUsername();
			}else{
				receiver = getUserNameById(Convert.strToLong(map.get("reciver"), -1));
			}
			
			title = map.get("mailTitle");
			date = map.get("sendTime");
			content = map.get("mailContent");
			request().setAttribute("sender", sender);
			request().setAttribute("receiver", receiver);
			request().setAttribute("title", title);
			request().setAttribute("date", date);
			request().setAttribute("content", content);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	
	 /**
	   * 邮箱管理模块
	   * @return
	 * @throws SQLException 
	 * @throws DataException 
	   */
		public String emailManagerInit() throws DataException, SQLException{
            AccountUserDo user = (AccountUserDo)session().getAttribute(IConstants.SESSION_USER);
			paramMap = userService.queryUserById(user.getId());
			String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email")+"");
			String flag = "1";
			if(email.equals("")){
				flag = "1";
			}else{
				flag = "2";
			}
			paramMap.put("flag", flag);
			session().setAttribute("DEMO", IConstants.ISDEMO);
			return SUCCESS;
		}
		
	
		/**
		 * 账户设置 邮箱设定
		 * @return
		 * @throws Exception
		 */
		public String SendUserEmailSet() throws Exception {
			JSONObject obj = new JSONObject();
			Map<String, String> map = null;
			String username = "";
			Long userId = null;
			String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email"));
			if (StringUtils.isBlank(email)) {
				obj.put("mailAddress", "0");
				JSONUtils.printObject(obj);
				return null;
			} 
			long 	result1 = userService.isExistEmailORUserName(email, null);
			if (result1 > 0) { // email重复
					obj.put("mailAddress", "4");
					JSONUtils.printObject(obj);
					return null;
			}
				// ===截取emal后面地址
			int dd = email.indexOf("@");
			String mailAddress = null;
			if (dd >= 0) {
				mailAddress = "mail." + email.substring(dd + 1);
			}

            AccountUserDo user = (AccountUserDo)session().getAttribute(IConstants.SESSION_USER);
			if(user!=null){
					DesSecurityUtil des = new DesSecurityUtil();
					String key1 = des.encrypt(user.getId()+"");
					String key2 = des.encrypt(new Date().getTime() + "");
					String key3 = email;
					String Name= user.getUsername();
					String url = getPath(); // request().getRequestURI();
					String VerificationUrl = url + "bangdingemail.do?key=" + key1
								+ "-" + key2+"-"+key3;
					sendMailService.SendUserEmailSetInUser(
							VerificationUrl,Name, email);
					obj.put("mailAddress", mailAddress);
					JSONUtils.printObject(obj);
					return null;
			}
					return null;

			}
		
		
		/**
		 * 邮箱绑定
		 * @return
		 * @throws Exception 
		 */
		public String bangdingemail() throws Exception{
			String key = request("key").trim();
			String msg = "邮箱验证失败";
			String[] keys = key.split("-");
			if (3 == keys.length) {
				DesSecurityUtil des = new DesSecurityUtil();
				Long userId = Convert
						.strToLong(des.decrypt(keys[0].toString()), -1);
				String dateTime = des.decrypt(keys[1].toString());
				long curTime = new Date().getTime();
				String emial =  keys[2].toString();
				Pattern p = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
				Matcher matcher = p.matcher(emial);
				if(!matcher.matches()){
				  paramMap.put("msg","邮箱格式错误");
				}else{
					//校验邮箱的唯一性
				 long 	result1 = userService.isExistEmailORUserName(emial, null);
					if (result1 > 0) { // email重复
						 paramMap.put("msg","该邮箱已被绑定,请重新输入");
					}else{
					// 当用户点击注册时间小于10分钟
							if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {
								 long result = 	userService.updateEmalByid(userId, emial);
								 if(result<0){
									 paramMap.put("msg","邮箱绑定失败");
								 }else{
									 paramMap.put("msg","邮箱绑定成功");
									 Map<String,String> map = userService.queEmailUser(userId);
									 String username = map.get("username")+"";
									 bbsRegisterService.doUpdateEmailByAsynchronousMode(username,emial);
								 }
								return SUCCESS;
							} else {
								msg = "连接失效,<strong>请从新绑定</a></strong>";
								paramMap.put("msg",msg);
							}
					}
				}
			} 
			return SUCCESS;
		}
	
	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}


	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public BecomeToFinanceService getBecomeFinanceService() {
		return becomeFinanceService;
	}

	public void setBecomeFinanceService(BecomeToFinanceService becomeFinanceService) {
		this.becomeFinanceService = becomeFinanceService;
	}


	public AdminService getAdminService() {
		return adminService;
	}


	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
		this.bbsRegisterService = bbsRegisterService;
	}

	public void setSendMailService(SendMailService sendMailService) {
		this.sendMailService = sendMailService;
	}


	public OperationLogService getOperationLogService() {
		return operationLogService;
	}


	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}
	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}


	public void setFundManagementService(FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}


    public List<Map<String, Object>> getProvinceList()
    {
        return provinceList;
    }


    public void setProvinceList(List<Map<String, Object>> provinceList)
    {
        this.provinceList = provinceList;
    }


    public List<Map<String, Object>> getCityList()
    {
        return cityList;
    }


    public void setCityList(List<Map<String, Object>> cityList)
    {
        this.cityList = cityList;
    }


    public void setRegionService(RegionService regionService)
    {
        this.regionService = regionService;
    }


    public long getCityId()
    {
        return cityId;
    }


    public void setCityId(long cityId)
    {
        this.cityId = cityId;
    }


}

