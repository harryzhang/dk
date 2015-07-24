package com.sp2p.action.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.security.Encrypt;
import com.shove.web.util.JSONUtils;
import com.sp2p.service.BeVipService;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.UserService;

public class PersonAppAction extends BaseAppAction {

	public static Log log = LogFactory.getLog(PersonAppAction.class);
	private static final long serialVersionUID = 7226324035784433720L;

	private BeVipService beVipService;
	private UserService userService;

	public String addOrUpdatePerson() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String realName = appInfoMap.get("realName");// 真实姓名
			if (StringUtils.isBlank(realName)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请正确填写真实名字");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (2 > realName.length() || 20 < realName.length()) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "真实姓名的长度为不小于2和大于20");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String idNo = appInfoMap.get("idNo");// 身份证号码
//			long len = idNo.length();
//			if (StringUtils.isBlank(idNo)) {
//				jsonMap.put("error", "3");
//				jsonMap.put("msg", "请输入正确的身份证号码");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			} else if (15 != len && 18 != len) {
//				jsonMap.put("error", "4");
//				jsonMap.put("msg", "请输入正确的身份证号码");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}

			// 验证身份证
			int sortCode = 0;
			int MAN_SEX = 0;
//			if (len == 15) {
//				sortCode = Integer.parseInt(idNo.substring(12, 15));
//			} else {
//				sortCode = Integer.parseInt(idNo.substring(14, 17));
//			}
//			if (sortCode % 2 == 0) {
//				MAN_SEX = 1;// 女性身份证
//			} else if (sortCode % 2 != 0) {
//				MAN_SEX = 2;// 男性身份证
//			} else {
//				jsonMap.put("error", "5");
//				jsonMap.put("msg", "身份证不合法");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//			String iDresutl = "";
//			iDresutl = IDCardValidateService.chekIdCard(MAN_SEX, idNo);
//			if (iDresutl != "") {
//				jsonMap.put("error", "6");
//				jsonMap.put("msg", "身份证不合法");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}

			String cellPhone = appInfoMap.get("cellPhone");// 手机号码
			if (StringUtils.isBlank(cellPhone)) {
				jsonMap.put("error", "7");
				jsonMap.put("msg", "请正确填写手机号码");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (cellPhone.length() < 9 || cellPhone.length() > 12) {
				jsonMap.put("error", "8");
				jsonMap.put("msg", "手机号码长度不对");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			/**
			 * 判定用户是否已存在记录
			 */
			Map<String, String> pMap = null;
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "9");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// 验证手机的唯一性
			Map<String, String> phonemap = beVipService.queryIsPhone(cellPhone);
			pMap = beVipService.queryPUser(userId);
			if (pMap == null) {
				if (phonemap != null) {
					jsonMap.put("error", "10");
					jsonMap.put("msg", "手机号已存在");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			} else if (phonemap != null
					&& !cellPhone.trim().equals(pMap.get("cellphone"))) {
				jsonMap.put("error", "10");
				jsonMap.put("msg", "手机号已存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String phonecode = appInfoMap.get("recivePhone") + "";

			phonecode = Encrypt.decryptSES(phonecode, AlipayConfig.ses_key);
//			if (StringUtils.isNotBlank(phonecode)) {
//				if (!phonecode.trim().equals(cellPhone.trim())) {
//					jsonMap.put("error", "12");
//					jsonMap.put("msg", "与获取验证码手机号不一致");
//					JSONUtils.printObject(jsonMap);
//					return null;
//				}
//
//			}
			// 验证码
			String code = appInfoMap.get("code");
//			if (StringUtils.isBlank(code)) {
//				jsonMap.put("error", "13");
//				jsonMap.put("msg", "请填写验证码");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}
//
//			String randomCode = appInfoMap.get("randomCode") + "";
//
//			randomCode = Encrypt.decryptSES(randomCode, AlipayConfig.ses_key);
//			if (!code.trim().equals(randomCode)) {
//				jsonMap.put("error", "14");
//				jsonMap.put("msg", "请输入正确的验证码");
//				JSONUtils.printObject(jsonMap);
//				return null;
//			}

			String sex = appInfoMap.get("sex");// 性别(男 女)
			if (StringUtils.isNotBlank(sex)) {
				if (sex.equals("男") && MAN_SEX == 1) {
					jsonMap.put("error", "12");
					jsonMap.put("msg", "请正确填写性别");
					JSONUtils.printObject(jsonMap);
					return null;
				} else if (sex.equals("女") && MAN_SEX == 2) {
					jsonMap.put("error", "12");
					jsonMap.put("msg", "请正确填写性别");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}

			String birthday = appInfoMap.get("birthday");// 出生日期
			if ("".equals(birthday)) {
				birthday = null;
			}
			// if (StringUtils.isBlank(birthday)) {
			// jsonMap.put("error", "13");
			// jsonMap.put("msg", "请正确填写出生日期");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			String highestEdu = appInfoMap.get("highestEdu");// 最高学历
			// if (StringUtils.isBlank(highestEdu)) {
			// jsonMap.put("error", "14");
			// jsonMap.put("msg", "请正确填写最高学历");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			String eduStartDay = appInfoMap.get("eduStartDay");// 入学年份
			if ("".equals(eduStartDay)) {
				eduStartDay = null;
			}
			// if (StringUtils.isBlank(eduStartDay)) {
			// jsonMap.put("error", "15");
			// jsonMap.put("msg", "请正确填写入学年份");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			String school = appInfoMap.get("school");// 毕业院校
			// if (StringUtils.isBlank(school)) {
			// jsonMap.put("error", "16");
			// jsonMap.put("msg", "请正确填写入毕业院校");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			String maritalStatus = appInfoMap.get("maritalStatus");// 婚姻状况(已婚
			// 未婚)
			// if (StringUtils.isBlank(maritalStatus)) {
			// jsonMap.put("error", "17");
			// jsonMap.put("msg", "请正确填写入婚姻状况");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			String hasChild = appInfoMap.get("hasChild");// 有无子女(有 无)

			// if (StringUtils.isBlank(hasChild)) {
			// jsonMap.put("error", "18");
			// jsonMap.put("msg", "请正确填写入有无子女");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }
			String hasHourse = appInfoMap.get("hasHourse");// 是否有房(有 无)
			// if (StringUtils.isBlank(hasHourse)) {
			// jsonMap.put("error", "19");
			// jsonMap.put("msg", "请正确填写入是否有房");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			String hasHousrseLoan = appInfoMap.get("hasHousrseLoan");// 有无房贷(有
			// 无)
			// if (StringUtils.isBlank(hasHousrseLoan)) {
			// jsonMap.put("error", "19");
			// jsonMap.put("msg", "请正确填写入有无房贷");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			String hasCar = appInfoMap.get("hasCar");// 是否有车 (有 无)
			// if (StringUtils.isBlank(hasCar)) {
			// jsonMap.put("error", "20");
			// jsonMap.put("msg", "请正确填写入是否有车");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			String hasCarLoan = appInfoMap.get("hasCarLoan");// 有无车贷 (有 无)
			// if (StringUtils.isBlank(hasCarLoan)) {
			// jsonMap.put("error", "21");
			// jsonMap.put("msg", "请正确填写入有无车贷");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }
			Long nativePlacePro = Convert.strToLong(appInfoMap
					.get("nativePlacePro"), -1);// 籍贯省份(默认为-1)
			// if (StringUtils.isBlank(nativePlacePro.toString())) {
			// jsonMap.put("error", "22");
			// jsonMap.put("msg", "请正确填写入籍贯省份");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }
			Long nativePlaceCity = Convert.strToLong(appInfoMap
					.get("nativePlaceCity"), -1);// 籍贯城市 (默认为-1)
			// if (StringUtils.isBlank(nativePlaceCity.toString())) {
			// jsonMap.put("error", "23");
			// jsonMap.put("msg", "请正确填写入籍贯城市");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			Long registedPlacePro = Convert.strToLong(appInfoMap
					.get("registedPlacePro"), -1);// 户口所在地省份(默认为-1)
			// if (StringUtils.isBlank(registedPlacePro.toString())) {
			// jsonMap.put("error", "24");
			// jsonMap.put("msg", "请正确填写入户口所在地省份");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			Long registedPlaceCity = Convert.strToLong(appInfoMap
					.get("registedPlaceCity"), -1);// 户口所在地城市(默认为-1)

			// if (StringUtils.isBlank(registedPlaceCity.toString())) {
			// jsonMap.put("error", "25");
			// jsonMap.put("msg", "请正确填写入户口所在地城市");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			String address = appInfoMap.get("address");// 所在地
			// if (StringUtils.isBlank(address)) {
			// jsonMap.put("error", "26");
			// jsonMap.put("msg", "请正确填写居住地址");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			String telephone = appInfoMap.get("telephone");// 居住电话
			if (StringUtils.isNotBlank(telephone)) {
				if (!StringUtils.isNumeric(telephone)) {
					jsonMap.put("error", "15");
					jsonMap.put("msg", "你的家庭电话输入不正确");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				if (telephone.trim().length() < 7
						|| telephone.trim().length() > 12) {
					jsonMap.put("error", "16");
					jsonMap.put("msg", "你的家庭电话输入长度不对");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}

			/* 用户头像 */
			String personalHead = appInfoMap.get("personalHead");// 个人头像
			// (默认系统头像)
			// if (StringUtils.isBlank(personalHead)) {
			// jsonMap.put("error", "30");
			// jsonMap.put("msg", "请正确上传你的个人头像");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			long personId = -1L;

			personId = userService.updateUserBaseData(realName, cellPhone, sex,
					birthday, highestEdu, eduStartDay, school, maritalStatus,
					hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
					nativePlacePro, nativePlaceCity, registedPlacePro,
					registedPlaceCity, address, telephone, personalHead,
					userId, idNo);
			if (personId > 0) {
				session().removeAttribute("randomCode");
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "保存成功");
				JSONUtils.printObject(jsonMap);

				// 成功
			} else {
				// 失败
				jsonMap.put("error", "17");
				jsonMap.put("msg", "保存失败");

				JSONUtils.printObject(jsonMap);

			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "18");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);

		}

		return null;
	}

	public String queryPersonByUserId() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> map = userService.queryPersonById(userId);
			if (map == null || map.isEmpty()) {
				jsonMap.put("error", "-2");
				jsonMap.put("msg", "用户没有填写个人基本信息");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			jsonMap.put("error", "-1");
			jsonMap.put("msg", "获取成功");
			jsonMap.putAll(map);
			JSONUtils.printObject(jsonMap);

		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
