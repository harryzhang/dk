package com.sp2p.action.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.web.util.JSONUtils;
import com.sp2p.service.BeVipService;
import com.sp2p.service.UserService;
import com.sp2p.service.ValidateService;

public class WorkAppAction extends BaseAppAction {
	public static Log log = LogFactory.getLog(WorkAppAction.class);
	private static final long serialVersionUID = 7226324035784433720L;

	private UserService userService;
	private BeVipService beVipService;
	private ValidateService validateService;

	public String addOrUpdateWork() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			AccountUserDo user = (AccountUserDo) session("user");
//			if(user == null){
//				jsonMap.put("error", "41");
//				jsonMap.put("msg", "请先登陆");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			//
			// if (user.getAuthStep() == 1) {
			// // 个人信息认证步骤
			// json.put("msg", "querBaseData");
			// JSONUtils.printObject(json);
			// return null;
			// }
			Map<String, String> appInfoMap = this.getAppInfoMap();

			String orgName = appInfoMap.get("orgName");
			if (StringUtils.isBlank(orgName)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请正确填写公司名字");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (2 > orgName.length() || 50 < orgName.length()) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "公司名字长度为不小于2和大于50");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String occStatus = appInfoMap.get("occStatus");
			if (StringUtils.isBlank(occStatus)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请填写职业状态");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long workPro = Convert.strToLong(appInfoMap.get("workPro"), -1L);
//			if (workPro == -1L) {
//				jsonMap.put("error", "4");
//				jsonMap.put("msg", "请填写工作城市省份");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			long workCity = Convert.strToLong(appInfoMap.get("workCity"), -1L);
//			if (workCity == -1L) {
//				jsonMap.put("error", "5");
//				jsonMap.put("msg", "请填写工作城市");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String companyType = appInfoMap.get("companyType");
//			if (StringUtils.isBlank(companyType)) {
//				jsonMap.put("error", "6");
//				jsonMap.put("msg", "请填写公司类别");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String companyLine = appInfoMap.get("companyLine");
//			if (StringUtils.isBlank(companyLine)) {
//				jsonMap.put("error", "7");
//				jsonMap.put("msg", "请填写公司行业");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String companyScale = appInfoMap.get("companyScale");
//			if (StringUtils.isBlank(companyScale)) {
//				jsonMap.put("error", "8");
//				jsonMap.put("msg", "请填写公司规模");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String job = appInfoMap.get("job");
//			if (StringUtils.isBlank(job)) {
//				jsonMap.put("error", "9");
//				jsonMap.put("msg", "请填写职位");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String monthlyIncome = appInfoMap.get("monthlyIncome");
//			if (StringUtils.isBlank(monthlyIncome)) {
//				jsonMap.put("error", "10");
//				jsonMap.put("msg", "请填写月收入");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String workYear = appInfoMap.get("workYear");
//			if (StringUtils.isBlank(workYear)) {
//				jsonMap.put("error", "11");
//				jsonMap.put("msg", "请填写现单位工作年限");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String companyTel = appInfoMap.get("companyTel");
			if (StringUtils.isNotBlank(companyTel)) {
				if (!StringUtils.isNumeric(companyTel)) {
					jsonMap.put("error", "13");
					jsonMap.put("msg", "请正确填写公司电话");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				if (companyTel.trim().length() < 7
						|| companyTel.trim().length() > 11) {
					jsonMap.put("error", "14");
					jsonMap.put("msg", "请正确填写公司电话");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			

			String workEmail = appInfoMap.get("workEmail");
//			if (StringUtils.isBlank(workEmail)) {
//				jsonMap.put("error", "15");
//				jsonMap.put("msg", "请填写工作邮箱");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String companyAddress = appInfoMap.get("companyAddress");
//			if (StringUtils.isBlank(companyAddress)) {
//				jsonMap.put("error", "16");
//				jsonMap.put("msg", "请填写公司地址");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String directedName = appInfoMap.get("directedName");
			if (StringUtils.isNotBlank(directedName)) {
				 if (2 > directedName.length() || 50 < directedName.length()) {
					jsonMap.put("error", "18");
					jsonMap.put("msg", "直系联系人姓名长度为不小于2和大于50");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			} 

			String directedRelation = appInfoMap.get("directedRelation");
//			if (StringUtils.isBlank(directedRelation)) {
//				jsonMap.put("error", "19");
//				jsonMap.put("msg", "请填写直系联系人关系");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String directedTel = appInfoMap.get("directedTel");
			if (StringUtils.isNotBlank(directedTel)) {
				if (!StringUtils.isNumeric(directedTel)) {
					jsonMap.put("error", "21");
					jsonMap.put("msg", "请正确填写直系联系人电话");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				if (directedTel.trim().length() != 11) {
					jsonMap.put("error", "22");
					jsonMap.put("msg", "请正确填写直系联系人电话长度错误");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			

			String otherName = appInfoMap.get("otherName");
			if (StringUtils.isNotBlank(otherName)) {
				if (2 > otherName.length() || 30 < otherName.length()) {
					jsonMap.put("error", "24");
					jsonMap.put("msg", "其他联系人姓名长度为不小于2和大于30");
					JSONUtils.printObject(jsonMap);
					return null;
				}

			}
		
			String otherRelation = appInfoMap.get("otherRelation");
//			if (StringUtils.isBlank(otherRelation)) {
//				jsonMap.put("error", "25");
//				jsonMap.put("msg", "请填写其他联系人关系");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String otherTel = appInfoMap.get("otherTel");
			if (StringUtils.isNotBlank(otherTel)) {
				if (!StringUtils.isNumeric(otherTel)) {
					jsonMap.put("error", "27");
					jsonMap.put("msg", "请正确填写其他联系人联系电话");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				if (otherTel.trim().length() != 11) {
					jsonMap.put("error", "28");
					jsonMap.put("msg", "请正确填写其他联系人电话长度错误");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}

			

			String moredName = appInfoMap.get("moredName");
			if (StringUtils.isNotBlank(moredName)) {
				if (2 > moredName.length() || 50 < moredName.length()) {
					jsonMap.put("error", "30");
					jsonMap.put("msg", "更多联系人姓名长度为不小于2和大于50");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			} 
			
			String moredRelation = appInfoMap.get("moredRelation");
//			if (StringUtils.isBlank(moredRelation)) {
//				jsonMap.put("error", "31");
//				jsonMap.put("msg", "更多联系人关系不能为空");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
			String moredTel = appInfoMap.get("moredTel");
			if (StringUtils.isNotBlank(moredTel)) {
				if (!StringUtils.isNumeric(moredTel)) {
					jsonMap.put("error", "33");
					jsonMap.put("msg", "更多联系人关系电话格式不正确");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				if (moredTel.trim().length() != 11) {
					jsonMap.put("error", "34");
					jsonMap.put("msg", "更多联系人关系电话格式不正确");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			
			// 用户Id
			long userId = Convert
					.strToLong(this.getAppAuthMap().get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "35");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Long result = -1L;
			// 判断用户是否已经是vip
			Map<String, String> vipMap = null;
			vipMap = beVipService.queryUserById(userId);
			int vipStatus = 1;// 1 为非vip 2 为vip 3 代扣费vip
			int newutostept = -1;
			if (vipMap.size() > 0 && vipMap != null) {
				vipStatus = Convert.strToInt(vipMap.get("vipStatus"), 1);
				newutostept = Convert.strToInt(vipMap.get("authStep"), -1);// 用户此时的认证步骤状态
			}

			result = userService.updateUserWorkData(orgName, occStatus,
					workPro, workCity, companyType, companyLine, companyScale,
					job, monthlyIncome, workYear, companyTel, workEmail,
					companyAddress, directedName, directedRelation,
					directedTel, otherName, otherRelation, otherTel, moredName,
					moredRelation, moredTel, userId, vipStatus, newutostept);

			if (result > 0) {
				// 保存成功更新认证步骤
//				 if (user.getAuthStep() == 2) {
//					 user.setAuthStep(3);
//				 }

				if (vipStatus != 1) {// 是vip会员
					// 更新用户的session步骤和是更新user表中的认证步骤
//					user.setAuthStep(4);
					jsonMap.put("error", "-1");
					jsonMap.put("msg", "vip保存成功");
					JSONUtils.printObject(jsonMap);
					return null;
				} else {
					jsonMap.put("error", "-1");
					jsonMap.put("msg", "保存成功");
					JSONUtils.printObject(jsonMap);
					return null;
				}

			} else {
				jsonMap.put("error", "39");
				jsonMap.put("msg", "保存失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "40");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			return null;
		}
	}

	public String queryWorkByUserId() throws IOException{
		Map<String,String> jsonMap = new HashMap<String, String>();
		try{
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if(userId == -1){
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String,String> map = validateService.queryWorkDataById(userId);
			if(map == null || map.isEmpty()){
				jsonMap.put("error", "-2");
				jsonMap.put("msg", "用户没有填写个人工作信息");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "获取成功");
			jsonMap.putAll(map);
			JSONUtils.printObject(jsonMap);
		}catch(Exception e){
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	

}
