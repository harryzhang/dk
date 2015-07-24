package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.RegionService;
import com.sp2p.service.admin.UserAdminService;

@SuppressWarnings("serial")
public class UserAdminAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(UserAdminAction.class);
	private UserAdminService userAdminService;
	private RegionService regionService;
	

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void setUserAdminService(UserAdminService userAdminService) {
		this.userAdminService = userAdminService;
	}

	public String updateUserBaseDataAdmin() throws SQLException, DataException,
			IOException {
		JSONObject json = new JSONObject();

		Long userId = Convert.strToLong(paramMap.get("ui"), -1L);
		if (userId == -1L) {
			json.put("msg", "用户为空");
			JSONUtils.printObject(json);
			return null;
		}
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("realName")),"");// 真实姓名
		if (StringUtils.isBlank(realName)) {
			json.put("msg", "请正确填写真实名字");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > realName.length() || 20 < realName.length()) {
			json.put("msg", "真实姓名的长度为不小于2和大于20");
			JSONUtils.printObject(json);
			return null;
		}

		String idNo = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("idNo")),"");// 身份证号码
		long len = idNo.length();
		if (StringUtils.isBlank(idNo)) {
			json.put("msg", "请正确身份证号码");
			JSONUtils.printObject(json);
			return null;
		} else if (15 != len) {
			if (18 == len) {
			} else {
				json.put("msg", "请正确身份证号码");
				JSONUtils.printObject(json);
				return null;
			}
		}

		String cellPhone = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("cellPhone")),"");// 手机号码
		if (StringUtils.isBlank(cellPhone)) {
			json.put("msg", "请正确填写手机号码");
			JSONUtils.printObject(json);
			return null;
		} else if (cellPhone.length() < 9 || cellPhone.length() > 15) {
			json.put("msg", "手机号码长度不对");
			JSONUtils.printObject(json);
			return null;
		}

		String sex = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("sex")), null);// 性别(男 女)
		if (StringUtils.isBlank(sex)) {
			json.put("msg", "请正确填写性别");
			JSONUtils.printObject(json);
			return null;
		}
		
		String birthday = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("birthday").toString()), null);// 出生日期
		if (StringUtils.isBlank(birthday)) {
			json.put("msg", "请正确填写出生日期");
			JSONUtils.printObject(json);
			return null;
		}

		String highestEdu = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("highestEdu")),null);// 最高学历
		if (StringUtils.isBlank(highestEdu)) {
			json.put("msg", "请正确填写最高学历");
			JSONUtils.printObject(json);
			return null;
		}
		
		String eduStartDay = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("eduStartDay").toString()), null);// 入学年份
		if (StringUtils.isBlank(eduStartDay)) {
			json.put("msg", "请正确填写入学年份");
			JSONUtils.printObject(json);
			return null;
		}

		String school = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("school")),null);// 毕业院校
		if (StringUtils.isBlank(school)) {
			json.put("msg", "请正确填写入毕业院校");
			JSONUtils.printObject(json);
			return null;
		}

		String maritalStatus = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("maritalStatus")),null);// 婚姻状况(已婚 未婚)
		if (StringUtils.isBlank(maritalStatus)) {
			json.put("msg", "请正确填写入婚姻状况");
			JSONUtils.printObject(json);
			return null;
		}

		String hasChild = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("hasChild")),null);// 有无子女(有 无)

		if (StringUtils.isBlank(hasChild)) {
			json.put("msg", "请正确填写入有无子女");
			JSONUtils.printObject(json);
			return null;
		}
		String hasHourse = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("hasHourse")),null);// 是否有房(有 无)
		if (StringUtils.isBlank(hasHourse)) {
			json.put("msg", "请正确填写入是否有房");
			JSONUtils.printObject(json);
			return null;
		}

		String hasHousrseLoan = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("hasHousrseLoan")),null);// 有无房贷(有 无)
		if (StringUtils.isBlank(hasHousrseLoan)) {
			json.put("msg", "请正确填写入有无房贷");
			JSONUtils.printObject(json);
			return null;
		}

		String hasCar = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("hasCar")),null);// 是否有车 (有 无)
		if (StringUtils.isBlank(hasCar)) {
			json.put("msg", "请正确填写入是否有车");
			JSONUtils.printObject(json);
			return null;
		}

		String hasCarLoan = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("hasCarLoan")),null);// 有无车贷 (有 无)
		if (StringUtils.isBlank(hasCarLoan)) {
			json.put("msg", "请正确填写入有无车贷");
			JSONUtils.printObject(json);
			return null;
		}
		Long nativePlacePro = Convert.strToLong(paramMap.get("nativePlacePro"),
				-1);// 籍贯省份(默认为-1)
		if (StringUtils.isBlank(nativePlacePro.toString())) {
			json.put("msg", "请正确填写入籍贯省份");
			JSONUtils.printObject(json);
			return null;
		}
		Long nativePlaceCity = Convert.strToLong(paramMap
				.get("nativePlaceCity"), -1);// 籍贯城市 (默认为-1)
		if (StringUtils.isBlank(nativePlaceCity.toString())) {
			json.put("msg", "请正确填写入籍贯城市");
			JSONUtils.printObject(json);
			return null;
		}

		Long registedPlacePro = Convert.strToLong(paramMap
				.get("registedPlacePro"), -1);// 户口所在地省份(默认为-1)
		if (StringUtils.isBlank(registedPlacePro.toString())) {
			json.put("msg", "请正确填写入户口所在地省份");
			JSONUtils.printObject(json);
			return null;
		}

		Long registedPlaceCity = Convert.strToLong(paramMap
				.get("registedPlaceCity"), -1);// 户口所在地城市(默认为-1)

		if (StringUtils.isBlank(registedPlaceCity.toString())) {
			json.put("msg", "请正确填写入户口所在地城市");
			JSONUtils.printObject(json);
			return null;
		}

		String address = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("address")), "");// 所在地
		if (StringUtils.isBlank(address)) {
			json.put("msg", "请正确填写入所在地");
			JSONUtils.printObject(json);
			return null;
		}

		String telephone = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("telephone")), "");// 居住电话
		if (StringUtils.isBlank(telephone)) {
			json.put("msg", "请正确填写入你的家庭电话");
			JSONUtils.printObject(json);
			return null;
		}
		if(!StringUtils.isBlank(telephone))
		if (telephone.trim().length() < 7 || telephone.trim().length() > 13) {
			json.put("msg", "你的家庭电话输入长度不对");
			JSONUtils.printObject(json);
			return null;
		}

		/* 用户头像 */
		String personalHead = SqlInfusion.FilteSqlInfusion(paramMap.get("personalHead"));// 个人头像 (默认系统头像)
		if (StringUtils.isBlank(personalHead)) {
			personalHead = null;
			json.put("msg", "请正确上传你的个人头像");
			JSONUtils.printObject(json);
			return null;
		}

		long personId = -1L;

		personId = userAdminService.updateUserBaseData(realName, cellPhone,
				sex, birthday, highestEdu, eduStartDay, school, maritalStatus,
				hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
				nativePlacePro, nativePlaceCity, registedPlacePro,
				registedPlaceCity, address, telephone, personalHead, userId,
				idNo);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		
		if (personId > 0) {
			json.put("msg", "保存成功");
			operationLogService.addOperationLog("t_person", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "管理员修改用户基础数据成功", 2);
			JSONUtils.printObject(json);
			return null;
			// 成功
		} else {
			json.put("msg", "保存失败");
			operationLogService.addOperationLog("t_person", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "管理员修改用户基础数据失败", 2);
			// 失败
			JSONUtils.printObject(json);
			return null;
		}

	}

	public String updateUserWorkDataAdmin() throws SQLException, IOException,
			DataException {
		JSONObject json = new JSONObject();
		Long userId = Convert.strToLong(paramMap.get("uid"), -1L);
		if (userId == -1L) {
			json.put("msg", "用户为空");
			JSONUtils.printObject(json);
			return null;
		}
		String orgName = SqlInfusion.FilteSqlInfusion(paramMap.get("orgName"));
		if (StringUtils.isBlank(orgName)) {
			json.put("msg", "请正确填写公司名字");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > orgName.length() || 50 < orgName.length()) {
			json.put("msg", "公司名字长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}

		String occStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("occStatus"));
		if (StringUtils.isBlank(occStatus)) {
			json.put("msg", "请填写职业状态");
			JSONUtils.printObject(json);
			return null;
		}
		Long workPro = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("workPro")), -1L);
		if (workPro == null || workPro == -1L) {
			json.put("msg", "请填写工作城市省份");
			JSONUtils.printObject(json);
			return null;
		}
		Long workCity = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("workCity")), -1L);
		if (workCity == null || workCity == -1L) {
			json.put("msg", "请填写工作城市");
			JSONUtils.printObject(json);
			return null;
		}
		String companyType = SqlInfusion.FilteSqlInfusion(paramMap.get("companyType"));
		if (StringUtils.isBlank(companyType)) {
			json.put("msg", "请填写公司类别");
			JSONUtils.printObject(json);
			return null;
		}
		String companyLine = SqlInfusion.FilteSqlInfusion(paramMap.get("companyLine"));
		if (StringUtils.isBlank(companyLine)) {
			json.put("msg", "请填写公司行业");
			JSONUtils.printObject(json);
			return null;
		}
		String companyScale = SqlInfusion.FilteSqlInfusion(paramMap.get("companyScale"));
		if (StringUtils.isBlank(companyScale)) {
			json.put("msg", "请填写公司规模");
			JSONUtils.printObject(json);
			return null;
		}
		String job = SqlInfusion.FilteSqlInfusion(paramMap.get("job"));
		if (StringUtils.isBlank(job)) {
			json.put("msg", "请填写职位");
			JSONUtils.printObject(json);
			return null;
		}
		String monthlyIncome = SqlInfusion.FilteSqlInfusion(paramMap.get("monthlyIncome"));
		if (StringUtils.isBlank(monthlyIncome)) {
			json.put("msg", "请填写月收入");
			JSONUtils.printObject(json);
			return null;
		}
		String workYear = SqlInfusion.FilteSqlInfusion(paramMap.get("workYear"));
		if (StringUtils.isBlank(workYear)) {
			json.put("msg", "请填写现单位工作年限");
			JSONUtils.printObject(json);
			return null;
		}
		String companyTel = SqlInfusion.FilteSqlInfusion(paramMap.get("companyTel"));
		if (StringUtils.isBlank(companyTel)) {
			json.put("msg", "请真确填写公司电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (companyTel.trim().length() < 7 || companyTel.trim().length() > 13) {
			json.put("msg", "请真确填写公司电话");
			JSONUtils.printObject(json);
			return null;
		}

		String workEmail = SqlInfusion.FilteSqlInfusion(paramMap.get("workEmail"));
		if (StringUtils.isBlank(workEmail)) {
			json.put("msg", "请填写工作邮箱");
			JSONUtils.printObject(json);
			return null;
		}
		String companyAddress = SqlInfusion.FilteSqlInfusion(paramMap.get("companyAddress"));
		if (StringUtils.isBlank(companyAddress)) {
			json.put("msg", "请填写公司地址");
			JSONUtils.printObject(json);
			return null;
		}
		String directedName = SqlInfusion.FilteSqlInfusion(paramMap.get("directedName"));
		if (StringUtils.isBlank(directedName)) {
			json.put("msg", "请填写直系人姓名");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > directedName.length() || 50 < directedName.length()) {
			json.put("msg", "直系人姓名长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}

		String directedRelation = SqlInfusion.FilteSqlInfusion(paramMap.get("directedRelation"));
		if (StringUtils.isBlank(directedRelation)) {
			json.put("msg", "请填写直系人关系");
			JSONUtils.printObject(json);
			return null;
		}
		String directedTel = SqlInfusion.FilteSqlInfusion(paramMap.get("directedTel"));
		if (StringUtils.isBlank(directedTel)) {
			json.put("msg", "请真确填写直系人电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (!StringUtils.isNumeric(directedTel)) {
			json.put("msg", "请真确填写直系人电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (directedTel.trim().length() != 11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}

		String otherName = SqlInfusion.FilteSqlInfusion(paramMap.get("otherName"));
		if (StringUtils.isBlank(workPro.toString())) {
			json.put("msg", "请填写其他人姓名");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > otherName.length() || 50 < otherName.length()) {
			json.put("msg", "其他人姓名长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}

		String otherRelation = SqlInfusion.FilteSqlInfusion(paramMap.get("otherRelation"));
		if (StringUtils.isBlank(otherRelation)) {
			json.put("msg", "请填写其他人关系");
			JSONUtils.printObject(json);
			return null;
		}
		String otherTel = SqlInfusion.FilteSqlInfusion(paramMap.get("otherTel"));
		if (StringUtils.isBlank(otherTel)) {
			json.put("msg", "请正确填写其他人联系电话");
			JSONUtils.printObject(json);
			return null;
		}

		if (!StringUtils.isNumeric(otherTel)) {
			json.put("msg", "请正确填写其他人联系电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (otherTel.trim().length() != 11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}

		String moredName = SqlInfusion.FilteSqlInfusion(paramMap.get("moredName"));
		if (StringUtils.isBlank(moredName)) {
			json.put("msg", "morename");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > moredName.length() || 50 < moredName.length()) {
			json.put("msg", "更多联系人姓名长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}
		String moredRelation = SqlInfusion.FilteSqlInfusion(paramMap.get("moredRelation"));
		if (StringUtils.isBlank(moredRelation)) {
			json.put("msg", "morereation");
			JSONUtils.printObject(json);
			return null;
		}
		String moredTel = SqlInfusion.FilteSqlInfusion(paramMap.get("moredTel"));
		if (StringUtils.isBlank(moredTel)) {
			json.put("msg", "moretel");
			JSONUtils.printObject(json);
			return null;
		}
		if (!StringUtils.isNumeric(moredTel)) {
			json.put("msg", "moretel");
			JSONUtils.printObject(json);
			return null;
		}
		if (moredTel.trim().length() != 11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}
		// 用户Id
		Long result = -1L;
	
			result = userAdminService.updateUserWorkData(orgName, occStatus,
					workPro, workCity, companyType, companyLine, companyScale,
					job, monthlyIncome, workYear, companyTel, workEmail,
					companyAddress, directedName, directedRelation,
					directedTel, otherName, otherRelation, otherTel, moredName,
					moredRelation, moredTel, userId);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (result > 0) {
			json.put("msg", "保存成功");
			operationLogService.addOperationLog("t_workauth", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "管理员修改用户工作资料成功", 2);
			JSONUtils.printObject(json);
			return null;
		} else {
			json.put("msg", "保存失败");
			operationLogService.addOperationLog("t_workauth", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "管理员修改用户工作资料失败", 2);
			JSONUtils.printObject(json);
			return null;
		}

	}
	
	
	public String ajaxqueryRegionadmin() throws SQLException, DataException,
	IOException {
		Long parentId = Convert.strToLong(request("parentId"), -1);
		Integer regionType = Convert.strToInt(request("regionType"), -1);
		List<Map<String, Object>> list;
		try {
			list = regionService.queryRegionList(-1L, parentId, regionType);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		String jsonStr = JSONArray.toJSONString(list);
		JSONUtils.printStr(jsonStr);
		return null;
	}
	
	

}
