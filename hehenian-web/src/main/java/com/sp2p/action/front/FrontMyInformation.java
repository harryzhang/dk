package com.sp2p.action.front;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.IPhoneVerifyService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.account.dataobject.PhoneVerifyDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.invite.IRecommandRewardService;
import com.hehenian.biz.common.invite.InviteDetailService;
import com.hehenian.biz.common.invite.InviteFriendService;
import com.hehenian.biz.common.invite.dataobject.InviteDetail;
import com.hehenian.biz.common.invite.dataobject.InviteFriend;
import com.hehenian.biz.common.invite.dataobject.InviteType;
import com.hehenian.biz.common.invite.dataobject.RecommondRewardDo;
import com.hehenian.web.common.util.CommonUtils;
import com.hehenian.web.common.util.ServletUtils;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.SqlInfusion;
import com.shove.vo.PageBean;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.BeVipService;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.RegionService;
import com.sp2p.service.UserService;
import com.sp2p.service.ValidateService;

public class FrontMyInformation extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyInformation.class);

	private static final long serialVersionUID = 1L;

	private UserService userService;

	private RegionService regionService;

	private ValidateService validateService;

	private BeVipService beVipService;

	@SuppressWarnings("unused")
	private IDCardValidateService iDCardValidateService;

	private List<Map<String, Object>> provinceList;

	private List<Map<String, Object>> cityList;

	private List<Map<String, Object>> areaList;

	private List<Map<String, Object>> regcityList;

	private long workPro = -1L;// 初始化省份默认值

	private long cityId = -1L;// 初始化话默认城市

	private long regPro = -1L;// 户口区域默认值

	private long regCity = -1L;// 户口区域默认值
	
	private IPhoneVerifyService phoneVerifyService;

	private InviteFriendService inviteFriendService;
	
	private List<InviteFriend> inviteFriendList;
	
	private InviteDetail inviteDetail;
	
	private InviteDetailService inviteDetailService;
	
	private IPersonService personService;
	
	private IRecommandRewardService recommandRewardService;

	private RecommondRewardDo recommondRewardDo;

	private RecommondRewardDo sumRecommondRewardDo;
	
	public RecommondRewardDo getSumRecommondRewardDo() {
		return sumRecommondRewardDo;
	}

	public void setSumRecommondRewardDo(RecommondRewardDo sumRecommondRewardDo) {
		this.sumRecommondRewardDo = sumRecommondRewardDo;
	}

	public RecommondRewardDo getRecommondRewardDo() {
		return recommondRewardDo;
	}

	public void setRecommondRewardDo(RecommondRewardDo recommondRewardDo) {
		this.recommondRewardDo = recommondRewardDo;
	}

	/**
	 * @param recommandRewardService the recommandRewardService to set
	 */
	public void setRecommandRewardService(IRecommandRewardService recommandRewardService) {
		this.recommandRewardService = recommandRewardService;
	}

	/**
	 * @param personService the personService to set
	 */
	public void setPersonService(IPersonService personService) {
		this.personService = personService;
	}

	/**
	 * @param inviteDetailService the inviteDetailService to set
	 */
	public void setInviteDetailService(InviteDetailService inviteDetailService) {
		this.inviteDetailService = inviteDetailService;
	}

	/**
	 * @return the inviteDetail
	 */
	public InviteDetail getInviteDetail() {
		return inviteDetail;
	}

	/**
	 * @param inviteDetail the inviteDetail to set
	 */
	public void setInviteDetail(InviteDetail inviteDetail) {
		this.inviteDetail = inviteDetail;
	}

	/**
	 * @return the inviteFriendList
	 */
	public List<InviteFriend> getInviteFriendList() {
		return inviteFriendList;
	}

	/**
	 * @param inviteFriendList the inviteFriendList to set
	 */
	public void setInviteFriendList(List<InviteFriend> inviteFriendList) {
		this.inviteFriendList = inviteFriendList;
	}

	/**
	 * @param inviteFriendService the inviteFriendService to set
	 */
	public void setInviteFriendService(InviteFriendService inviteFriendService) {
		this.inviteFriendService = inviteFriendService;
	}

	public void setPhoneVerifyService(IPhoneVerifyService phoneVerifyService) {
		this.phoneVerifyService = phoneVerifyService;
	}

	//开设汇付账户
	public String registerChinaHHN()
	{
	    AccountUserDo user = (AccountUserDo) session().getAttribute("user");
	   String idNO = null;
	try {
		idNO = userService.queryIdNO(user.getId());
	} catch (SQLException e) {
		e.printStackTrace();
	}
//	   user.setIdNo(idNO);
	    request().setAttribute("idNo", idNO);
	    request().setAttribute("usrCustId", user.getUsrCustId());
	    return SUCCESS;
	}
	
	/**
	 * 查询个详细信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryBaseData() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String birth = null;
		String rxedate = null;
		if (user != null) {
			map = userService.queryPersonById(user.getId());
			if (map != null && map.size() > 0) {
				workPro = Convert.strToLong(map.get("nativePlacePro") + "", -1L);
				cityId = Convert.strToLong(map.get("nativePlaceCity") + "", -1L);
				regPro = Convert.strToLong(map.get("registedPlacePro") + "", -1L);
				regCity = Convert.strToLong(map.get("registedPlaceCity") + "", -1L);
				birth = Convert.strToStr(map.get("birthday"), null);
				rxedate = Convert.strToStr(map.get("eduStartDay"), null);
				if (birth != null) {
					birth = birth.substring(0, 10);
				}
				if (rxedate != null) {
					rxedate = rxedate.substring(0, 10);
				}

			}
		} else {
			return "login";
		}
		
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		regcityList = regionService.queryRegionList(-1L, regPro, 2);
		request().setAttribute("map", map);
		request().setAttribute("provinceList", provinceList);
		request().setAttribute("cityList", cityList);
		request().setAttribute("regcityList", regcityList);
		request().setAttribute("birth", birth);
		request().setAttribute("rxedate", rxedate);
		request().setAttribute("realName", map.get("realName"));

		String tab_type = SqlInfusion.FilteSqlInfusion(request("tab_type") == null ? null : request("tab_type"));
		if (tab_type != null) {
			request().setAttribute("tab_type", tab_type);
		}

		String yy = SqlInfusion.FilteSqlInfusion(request("yy") == null ? null : request("yy"));
		if (yy != null) {
			request().setAttribute("yy", yy);
		}
		Map<String, String> pmap = userService.queryPersonById(user.getId());
		if (pmap != null && pmap.size() > 0) {
			request().setAttribute("pass", pmap.get("auditStatus"));
		}
		int authStep = user.getAuthStep();
		if (authStep < 2) {
			request().setAttribute("person", "0");
		} else {
			request().setAttribute("person", "1");
		}
		request().setAttribute("ISDEMO", IConstants.ISDEMO);
		return SUCCESS;
	}

	// 添加或更新个人信息
	public String updateUserBaseData() throws SQLException, IOException, DataException, NumberFormatException, ParseException {
		AccountUserDo user = (AccountUserDo) session("user");

		JSONObject json = new JSONObject();
		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));// 真实姓名
		if (StringUtils.isBlank(realName)) {
			json.put("msg", "请正确填写真实名字");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > realName.length() || 20 < realName.length()) {
			json.put("msg", "真实姓名的长度为不小于2和大于20");
			JSONUtils.printObject(json);
			return null;
		}

		String idNo = SqlInfusion.FilteSqlInfusion(paramMap.get("idNo"));// 身份证号码
		long len = idNo.length();
		if (StringUtils.isBlank(idNo)) {
			json.put("msg", "请正确输入身份证号码");
			JSONUtils.printObject(json);
			return null;
		} else if (15 != len) {
			if (18 == len) {
			} else {
				json.put("msg", "请正确输入身份证号码");
				JSONUtils.printObject(json);
				return null;
			}
		}
		// 验证身份证
		String iDresutl = "";
		// iDresutl = iDCardValidateService.IDCardValidate(idNo);
		if (iDresutl != "") {
			json.put("msg", "身份证不合法");
			JSONUtils.printObject(json);
			return null;
		}
		String cellPhone = SqlInfusion.FilteSqlInfusion(paramMap.get("cellPhone"));// 手机号码
		// 判断是否是手机注册用户
		String iscellPhone = SqlInfusion.FilteSqlInfusion(paramMap.get("iscellPhone"));
		if (StringUtils.isBlank(iscellPhone)) {
			if (StringUtils.isBlank(cellPhone)) {
				json.put("msg", "请正确填写手机号码");
				JSONUtils.printObject(json);
				return null;
			} else if (cellPhone.length() < 9 || cellPhone.length() > 15) {
				json.put("msg", "手机号码长度不对");
				JSONUtils.printObject(json);
				return null;
			}

			/**
			 * 判定用户是否已存在记录
			 */
			Map<String, String> pMap = null;

			pMap = beVipService.queryPUser(user.getId());
			// 验证手机的唯一性
			Map<String, String> phonemap = null;
			phonemap = beVipService.queryIsPhone(cellPhone);

			if (pMap == null) {

				if (phonemap != null) {
					json.put("msg", "手机已存在");
					JSONUtils.printObject(json);
					return null;
				}

				if (phonemap == null) {
					String phonecode = null;
					try {
						Object obje = session().getAttribute("phone");
						if (obje != null) {
							phonecode = obje.toString();
						} else {
							json.put("msg", "请输入正确的验证码");
							JSONUtils.printObject(json);
							return null;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (phonecode != null) {
						if (!phonecode.trim().equals(cellPhone.trim())) {
							json.put("msg", "与获取验证码手机号不一致");
							JSONUtils.printObject(json);
							return null;
						}

					}
					// 验证码
					String vilidataNum = SqlInfusion.FilteSqlInfusion(paramMap.get("vilidataNum"));
					if (StringUtils.isBlank(vilidataNum)) {
						json.put("msg", "请填写验证码");
						JSONUtils.printObject(json);
						return null;
					}

					String randomCode = null;
					Object objec = session().getAttribute("randomCode");
					if (objec != null) {
						randomCode = objec.toString();
					} else {
						json.put("msg", "请输入正确的验证码");
						JSONUtils.printObject(json);
						return null;
					}
					if (randomCode != null) {
						if (!randomCode.trim().equals(vilidataNum.trim())) {
							json.put("msg", "请输入正确的验证码");
							JSONUtils.printObject(json);
							return null;
						}

					}

				}

			}

		}

		String sex = SqlInfusion.FilteSqlInfusion(paramMap.get("sex"));// 性别(男 女)
		if (StringUtils.isBlank(sex)) {
			json.put("msg", "请正确填写性别");
			JSONUtils.printObject(json);
			return null;
		}

		String birthday = SqlInfusion.FilteSqlInfusion(paramMap.get("birthday"));// 出生日期
		if (StringUtils.isBlank(birthday)) {
			json.put("msg", "请正确填写出生日期");
			JSONUtils.printObject(json);
			return null;
		}

		String highestEdu = SqlInfusion.FilteSqlInfusion(paramMap.get("highestEdu"));// 最高学历
		if (StringUtils.isBlank(highestEdu)) {
			json.put("msg", "请正确填写最高学历");
			JSONUtils.printObject(json);
			return null;
		}

		String eduStartDay = SqlInfusion.FilteSqlInfusion(paramMap.get("eduStartDay"));// 入学年份
		if (StringUtils.isBlank(eduStartDay)) {
			json.put("msg", "请正确填写入学年份");
			JSONUtils.printObject(json);
			return null;
		}

		String school = SqlInfusion.FilteSqlInfusion(paramMap.get("school"));// 毕业院校
		if (StringUtils.isBlank(school)) {
			json.put("msg", "请正确填写入毕业院校");
			JSONUtils.printObject(json);
			return null;
		}

		String maritalStatus = SqlInfusion.FilteSqlInfusion(paramMap.get("maritalStatus"));// 婚姻状况(已婚 未婚)
		if (StringUtils.isBlank(maritalStatus)) {
			json.put("msg", "请正确填写入婚姻状况");
			JSONUtils.printObject(json);
			return null;
		}

		String hasChild = SqlInfusion.FilteSqlInfusion(paramMap.get("hasChild"));// 有无子女(有 无)

		if (StringUtils.isBlank(hasChild)) {
			json.put("msg", "请正确填写入有无子女");
			JSONUtils.printObject(json);
			return null;
		}
		String hasHourse = SqlInfusion.FilteSqlInfusion(paramMap.get("hasHourse"));// 是否有房(有 无)
		if (StringUtils.isBlank(hasHourse)) {
			json.put("msg", "请正确填写入是否有房");
			JSONUtils.printObject(json);
			return null;
		}

		String hasHousrseLoan = SqlInfusion.FilteSqlInfusion(paramMap.get("hasHousrseLoan"));// 有无房贷(有 无)
		if (StringUtils.isBlank(hasHousrseLoan)) {
			json.put("msg", "请正确填写入有无房贷");
			JSONUtils.printObject(json);
			return null;
		}

		String hasCar = SqlInfusion.FilteSqlInfusion(paramMap.get("hasCar"));// 是否有车 (有 无)
		if (StringUtils.isBlank(hasCar)) {
			json.put("msg", "请正确填写入是否有车");
			JSONUtils.printObject(json);
			return null;
		}

		String hasCarLoan = SqlInfusion.FilteSqlInfusion(paramMap.get("hasCarLoan"));// 有无车贷 (有 无)
		if (StringUtils.isBlank(hasCarLoan)) {
			json.put("msg", "请正确填写入有无车贷");
			JSONUtils.printObject(json);
			return null;
		}
		Long nativePlacePro = Convert.strToLong(paramMap.get("nativePlacePro"), -1);// 籍贯省份(默认为-1)
		if (StringUtils.isBlank(nativePlacePro.toString())) {
			json.put("msg", "请正确填写入籍贯省份");
			JSONUtils.printObject(json);
			return null;
		}
		Long nativePlaceCity = Convert.strToLong(paramMap.get("nativePlaceCity"), -1);// 籍贯城市
		// (默认为-1)
		if (StringUtils.isBlank(nativePlaceCity.toString())) {
			json.put("msg", "请正确填写入籍贯城市");
			JSONUtils.printObject(json);
			return null;
		}

		Long registedPlacePro = Convert.strToLong(paramMap.get("registedPlacePro"), -1);// 户口所在地省份(默认为-1)
		if (StringUtils.isBlank(registedPlacePro.toString())) {
			json.put("msg", "请正确填写入户口所在地省份");
			JSONUtils.printObject(json);
			return null;
		}

		Long registedPlaceCity = Convert.strToLong(paramMap.get("registedPlaceCity"), -1);// 户口所在地城市(默认为-1)

		if (StringUtils.isBlank(registedPlaceCity.toString())) {
			json.put("msg", "请正确填写入户口所在地城市");
			JSONUtils.printObject(json);
			return null;
		}

		String address = SqlInfusion.FilteSqlInfusion(paramMap.get("address"));// 所在地
		if (StringUtils.isBlank(address)) {
			json.put("msg", "请正确填写入所在地");
			JSONUtils.printObject(json);
			return null;
		}

		String telephone = SqlInfusion.FilteSqlInfusion(paramMap.get("telephone"));// 居住电话
		if (StringUtils.isBlank(telephone)) {
			json.put("msg", "请正确填写入你的家庭电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (!StringUtils.isBlank(telephone)) {
			if (!StringUtils.isNumeric(telephone)) {
				json.put("msg", "你的家庭电话输入不正确");
				JSONUtils.printObject(json);
				return null;
			}
			if (telephone.trim().length() < 7 || telephone.trim().length() > 11) {
				json.put("msg", "你的家庭电话输入长度不对");
				JSONUtils.printObject(json);
				return null;
			}
		}
		/* 用户头像 */
		String personalHead = SqlInfusion.FilteSqlInfusion(paramMap.get("personalHead"));// 个人头像 (默认系统头像)
		if (StringUtils.isBlank(personalHead)) {
			personalHead = null;
			json.put("msg", "请正确上传你的个人头像");
			JSONUtils.printObject(json);
			return null;
		}
		if (user == null) {
			json.put("msg", "超时请重新登录");
			JSONUtils.printObject(json);
			return null;
		}

		long personId = -1L;
		if (iscellPhone != null) {
			cellPhone = iscellPhone;
		}

		personId = userService.updateUserBaseData(realName, cellPhone, sex, birthday, highestEdu, eduStartDay, school, maritalStatus, hasChild,
				hasHourse, hasHousrseLoan, hasCar, hasCarLoan, nativePlacePro, nativePlaceCity, registedPlacePro, registedPlaceCity, address,
				telephone, personalHead, user.getId(), idNo);
		if (personId > 0) {
			// ==
			if (user.getAuthStep() == 1) {
				user.setAuthStep(2);
			}
			session().removeAttribute("randomCode");
//			user.setPersonalHead(personalHead);// 将个人头像放到session里面
			json.put("msg", "保存成功");
			JSONUtils.printObject(json);
			request().setAttribute("person", "1");
//			user.setRealName(realName);
			session().setAttribute("user", user);
			return null;
			// 成功
		} else {
			json.put("msg", "保存失败");
			// 失败
			JSONUtils.printObject(json);
			return null;
		}
	}

	/**
	 * 
	 * 初始化工作认证信息，
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryWorkInit() throws Exception {

		// ==============================
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> allworkmap = new HashMap<String, String>();
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		request().setAttribute("provinceList", provinceList);

		map = validateService.queryWorkDataById(user.getId());
		allworkmap = validateService.queryAllWorkStatus(user.getId());
		if (map != null && map.size() > 0) {
			workPro = Convert.strToLong(map.get("workPro").toString(), -1L);
			cityId = Convert.strToLong(map.get("workCity").toString(), -1L);
		}
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		request().setAttribute("cityList", cityList);
		request().setAttribute("map", map);
		request().setAttribute("allworkmap", allworkmap);
		Map<String, String> pmap = userService.queryPersonById(user.getId());
		if (pmap != null && pmap.size() > 0) {
			request().setAttribute("pass", pmap.get("auditStatus"));
		}
		// 得都用户基本资料信息
		Map<String, String> mapList = userService.queryPersonById(user.getId());
		if (mapList == null) {
			mapList = new HashMap<String, String>();
		}
		int authStep = user.getAuthStep();
		if (authStep < 2) {
			request().setAttribute("person", "0");
		} else {
			request().setAttribute("person", "1");
		}
		request().setAttribute("realName", pmap.get("realName"));

		return SUCCESS;
	}

	// =============================

	/**
	 * 
	 * 初始化基本资料信息，
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryBasicInit() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		String birth = null;
		String rxedate = null;
		if (user != null) {
			map = userService.queryPersonById(user.getId());
			if (map != null && map.size() > 0) {
				workPro = Convert.strToLong(map.get("nativePlacePro").toString(), -1L);
				cityId = Convert.strToLong(map.get("nativePlaceCity").toString(), -1L);
				regPro = Convert.strToLong(map.get("registedPlacePro").toString(), -1L);
				regCity = Convert.strToLong(map.get("registedPlaceCity").toString(), -1L);
				birth = Convert.strToStr(map.get("birthday"), null);
				rxedate = Convert.strToStr(map.get("eduStartDay"), null);
				if (birth != null) {
					birth = birth.substring(0, 10);
				}
				if (rxedate != null) {
					rxedate = rxedate.substring(0, 10);
				}
			}
		}
		// 判断用户是否已经填写了基本信息
		String flag = "";
		if (map != null && map.size() > 0) {// 用户基本资料有数据但是不一定是已经填写了基本资料信息
											// 还有可能是上传了个人头像
			if (!StringUtils.isBlank(map.get("realName"))) {// 不为空
				flag = "1";
			} else {
				flag = "2";
			}
		} else {
			flag = "2";
		}
		request().setAttribute("flag", flag);
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		regcityList = regionService.queryRegionList(-1L, regPro, 2);
		request().setAttribute("map", map);
		request().setAttribute("provinceList", provinceList);
		request().setAttribute("cityList", cityList);
		request().setAttribute("regcityList", regcityList);
		request().setAttribute("birth", birth);
		request().setAttribute("rxedate", rxedate);
		request().setAttribute("ISDEMO", IConstants.ISDEMO);
		return SUCCESS;
	}

	/**
	 * 更新的工作认证资料添加
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 */
	public String updateUserWorkData() throws SQLException, IOException, DataException {
		JSONObject json = new JSONObject();
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (user.getAuthStep() == 1) {
			// 个人信息认证步骤
			json.put("msg", "querBaseData");
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
		Long workPro = Convert.strToLong(paramMap.get("workPro"), -1L);
		if (workPro == null || workPro == -1L) {
			json.put("msg", "请填写工作城市省份");
			JSONUtils.printObject(json);
			return null;
		}
		Long workCity = Convert.strToLong(paramMap.get("workCity"), -1L);
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
		if (!StringUtils.isNumeric(companyTel)) {
			json.put("msg", "请真确填写公司电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (companyTel.trim().length() < 7 || companyTel.trim().length() > 11) {
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
		Long userId = user.getId();
		Long result = -1L;
		// 判断用户是否已经是vip
		Map<String, String> vipMap = null;
		vipMap = beVipService.queryUserById(user.getId());
		int vipStatus = 1;// 1 为非vip 2 为vip 3 代扣费vip
		int newutostept = -1;
		if (vipMap.size() > 0 && vipMap != null) {
			vipStatus = Convert.strToInt(vipMap.get("vipStatus"), 1);
			newutostept = Convert.strToInt(vipMap.get("authStep"), -1);// 用户此时的认证步骤状态
		}
		if (user != null) {
			result = userService.updateUserWorkData(orgName, occStatus, workPro, workCity, companyType, companyLine, companyScale, job,
					monthlyIncome, workYear, companyTel, workEmail, companyAddress, directedName, directedRelation, directedTel, otherName,
					otherRelation, otherTel, moredName, moredRelation, moredTel, userId, vipStatus, newutostept);
		}

		if (result > 0) {
			// 保存成功更新认证步骤
			if (user.getAuthStep() == 2) {
				user.setAuthStep(3);
			}

			if (vipStatus != 1) {// 是vip会员
									// 更新用户的session步骤和是更新user表中的认证步骤
				user.setAuthStep(4);
				json.put("msg", "vip保存成功");
				JSONUtils.printObject(json);
				return null;
			} else {
				json.put("msg", "保存成功");
				JSONUtils.printObject(json);
				return null;
			}

		} else {
			json.put("msg", "保存失败");
			JSONUtils.printObject(json);
			return null;
		}

	}

	/**
	 * (前台)个人设置中修改密码
	 * 
	 * @return
	 */
	public String updatexgmm() {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		try {
			Map<String, String> pmap = userService.queryPersonById(user.getId());
			if (pmap != null && pmap.size() > 0) {
				request().setAttribute("pass", pmap.get("auditStatus"));
			}
			String j = request("j");
			request().setAttribute("j", j);
			/*
			 * int authStep = user.getAuthStep(); if(authStep < 2){
			 * request().setAttribute("person", "0"); }else{ }
			 */
			request().setAttribute("person", "1");
			request().setAttribute("emailBound", user.getEmail());
			request().setAttribute("realName", pmap.get("realName"));

			// 根据用户ID查询安全问题
			Map<String, String> map = userService.queryQuestion(user.getId());
			request().setAttribute("map", map);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	public void setiDCardValidateService(IDCardValidateService iDCardValidateService) {
		this.iDCardValidateService = iDCardValidateService;
	}

	public UserService getUserService() {
		return userService;
	}

	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public List<Map<String, Object>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Map<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}

	public List<Map<String, Object>> getCityList() {
		return cityList;
	}

	public void setCityList(List<Map<String, Object>> cityList) {
		this.cityList = cityList;
	}

	public List<Map<String, Object>> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Map<String, Object>> areaList) {
		this.areaList = areaList;
	}

	public List<Map<String, Object>> getRegcityList() {
		return regcityList;
	}

	public void setRegcityList(List<Map<String, Object>> regcityList) {
		this.regcityList = regcityList;
	}

	public long getWorkPro() {
		return workPro;
	}

	public void setWorkPro(long workPro) {
		this.workPro = workPro;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getRegPro() {
		return regPro;
	}

	public void setRegPro(long regPro) {
		this.regPro = regPro;
	}

	public long getRegCity() {
		return regCity;
	}

	public void setRegCity(long regCity) {
		this.regCity = regCity;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public void setIDCardValidateService(IDCardValidateService cardValidateService) {
		iDCardValidateService = cardValidateService;
	}

	/**
	 * 彩生活导入个人资料数据
	 * 
	 * @return
	 * @throws IOException
	 */
	public String importFromBeautyLife() throws Exception {
		JSONObject json = new JSONObject();
		String realName = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("realName")), null);
		String idNo = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("idNo")), null);
		// 未正确传入数据
		if (StringUtils.isBlank(realName) || StringUtils.isBlank(idNo)) {
			json.put("msg", "-1");
			JSONUtils.printObject(json);
			return null;
		}

		Map<String, String> map = regionService.queryFromBeautyLife(realName, idNo);

		// 无匹配数据
		if (map == null || map.size() == 0) {
			json.put("msg", "0");
			JSONUtils.printObject(json);
			return null;
		}

		// 该用户已经被激活过
		int state = Convert.strToInt(map.get("activatedState"), 0);
		if (state == 2) {
			json.put("msg", "-2");
			JSONUtils.printObject(json);
			return null;
		}

		// 未知错误
		if (state == 0) {
			json.put("msg", "-3");
			JSONUtils.printObject(json);
			return null;
		}

		// 导入数据
		long result = regionService.importFromBeautyLife(map, this.getUserId());
		if (result > 0) {
			this.getUser().setAuthStep(5);
			json.put("msg", "2");
		} else
			json.put("msg", "1");
		JSONUtils.printObject(json);
		return null;
	}

	/**
	 * 新增安全问题
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String addQuestion() {
		AccountUserDo user = (AccountUserDo) session("user");
		long userId = Convert.strToLong(user.getId().toString(), -1L);
		String question = SqlInfusion.FilteSqlInfusion(paramMap.get("question"));
		String answer = SqlInfusion.FilteSqlInfusion(paramMap.get("answer"));
		long result = -1;
		try {
			result = userService.addQuestion(userId, question, answer);
			if (result > 0) {
				JSONUtils.printStr("1");
			} else {
				JSONUtils.printStr("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改安全问题
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String updateQuestion() {
		AccountUserDo user = (AccountUserDo) session("user");
		long userId = Convert.strToLong(user.getId().toString(), -1L);
		String question = SqlInfusion.FilteSqlInfusion(paramMap.get("question"));
		String oldAnswer = SqlInfusion.FilteSqlInfusion(paramMap.get("oldAnswer"));
		String answer = SqlInfusion.FilteSqlInfusion(paramMap.get("answer"));
		String oldQuestion = SqlInfusion.FilteSqlInfusion(paramMap.get("oldQuestion"));

		long result = -1;
		try {
			// 根据用户ID查询原始答案
			Map<String, String> answerMap = userService.queryOldAnswer(userId, oldAnswer, oldQuestion);
			if (answerMap == null || answerMap.equals("")) {
				JSONUtils.printStr("3");
				return null;
			}

			result = userService.updateQuestion(userId, question, answer);
			if (result > 0) {
				JSONUtils.printStr("1");
			} else {
				JSONUtils.printStr("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询邮箱是否设置
	 * 
	 * @return [参数说明]
	 * 
	 * @return String [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String queryEmail() {
		AccountUserDo user = (AccountUserDo) session("user");
		try {
			// 根据用户ID查询邮箱
			Map<String, String> userList = userService.queryPersonById(user.getId());
			request().setAttribute("userList", userList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 根据用户ID--修改、新增邮箱
	 * 
	 * @return [参数说明]
	 * 
	 * @return Stirng [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String updateEmail() {
		AccountUserDo user = (AccountUserDo) session("user");
		long userId = Convert.strToLong(user.getId().toString(), -1L);
		String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email"));

		// long result = -1;
		try {
			String pwd = user.getPassword();// 邮箱设置--查询密码
			String password = Convert.strToStr(paramMap.get("password"), null);
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim() + IConstants.PASS_KEY);
			}
			if (!pwd.equals(password)) {
				JSONUtils.printStr("2");
				return null;
			}
			// 验证邮箱的唯一性
			Map<String, String> map = userService.queryIsEimal(email);
			if (null != map) {
				JSONUtils.printStr("3");
				return null;
			}
			// 新增、修改成功
			// result = userService.updateEmail(userId, email);
			IResult<PhoneVerifyDo> updateResult = this.phoneVerifyService.updateEmail(userId, email);
			user.setEmail(email);
			if (updateResult.isSuccess()) {
				JSONUtils.printStr("1");
			}
			session().removeAttribute("emailBound");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
     * 某一个月第一天和最后一天
     * @param date
     * @return
     */
    private  Map<String, Date> getFirstday_Lastday_Month(Date date)throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        
        //第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(date);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();

        //最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();

        Map<String, Date> map = new HashMap<String, Date>();
        map.put("first", df.parse(day_first));
        map.put("last", df.parse(day_last));
        return map;
    }
    
	/**
	 * 邀请好友列表
	 * @return
	 */
	public String inviteFriend() {
		PageBean<?> pageBean = getPageBean();
		Map<String, String> paramMap = getParamMap();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.putAll(paramMap);
		
		Long userId = getUser().getId();
		queryParams.put("userId", userId);
		
		Long totalNum = this.inviteFriendService.countInviteFriend(queryParams);
		pageBean.setTotalNum(totalNum);
		
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		queryParams.put("pageNum", pageNum);
		queryParams.put("pageSize", pageBean.getPageSize());
		this.inviteFriendList = this.inviteFriendService.listInviteFriend(queryParams);
		for (InviteFriend inviteFriend : this.inviteFriendList) {
			double awardMoney = this.recommandRewardService.getReferGroupReward(inviteFriend.getFriendUserInfo().getId() + "", userId + "");
			inviteFriend.setAwardMoney(new BigDecimal(awardMoney));
		}
		Map<String, Date> map = new HashMap<String, Date>();
		try {
			map = this.getFirstday_Lastday_Month(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.recommondRewardDo = this.recommandRewardService.getSumRewardAmount(userId+"", map.get("first"), map.get("last"));

		this.sumRecommondRewardDo = this.recommandRewardService.getSumRewardAmount(userId+"", null, null);
		
		return SUCCESS;
	}
	
	/**
	 * 短信邀请
	 * @return
	 */
	public String smsInvite() {
		JSONObject jsonObject = new JSONObject();
		if (!checkCode()) {
			jsonObject.put("msg", "验证码错误");
            ServletUtils.writeJson(jsonObject.toString());
            return null;
		}
		AccountUserDo inviteUser = getUser();
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("invite", inviteUser.getId());

		if (this.inviteDetail != null) {
			// 判断每个号码一天只能发送一次的逻辑
			List<InviteDetail> inviteDetailList = this.inviteDetailService.getList(queryParams);
			String mobileStr = this.inviteDetail.getInvitee().getMobilePhone();
			String[] mobileArray = mobileStr.split(",");
			for (InviteDetail inviteDetail : inviteDetailList) {
				for (String mobile : mobileArray) {
					if (mobile.equals(inviteDetail.getInvitee().getMobilePhone())) {
						jsonObject.put("msg", "手机号" + mobile + "今天已经邀请过，请填写其他手机号码");
						ServletUtils.writeJson(jsonObject.toString());
						return null;
					}
				}
			}
			
			Long todaySendCount = this.inviteDetailService.count(queryParams);
			if (todaySendCount + mobileArray.length > 5) {
				jsonObject.put("msg", "当天发送短信邀请不能超过5次，你已经发送" + todaySendCount + "次");
				ServletUtils.writeJson(jsonObject.toString());
				return null;
			}
			List<InviteDetail> inviteDetails = getInviteDetailList(inviteUser, mobileStr);
			System.out.println(this.inviteDetailService.addInvite(inviteDetails));
		}
		jsonObject.put("code", 0);
		ServletUtils.writeJson(jsonObject.toString());
		return null;
	}

	/**
	 * 获取邀请记录集合
	 * @param inviteUser
	 * @param mobileStr
	 * @return
	 */
	private List<InviteDetail> getInviteDetailList(AccountUserDo inviteUser, String mobileStr) {
		InviteDetail inviteRecord;
		AccountUserDo invitee;
		Long userId = -1l;
		List<InviteDetail> inviteDetails = new ArrayList<InviteDetail>();
		if (StringUtils.isNotEmpty(mobileStr)) {
			for (String mobile : mobileStr.split(",")) {
				try {
					userId = this.userService.findUserByIdOrPhone(mobile);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				inviteRecord = new InviteDetail();
				invitee = new AccountUserDo();
				invitee.setId(userId);
				invitee.setMobilePhone(mobile);
				
				inviteRecord.setInvite(inviteUser);
				inviteRecord.setInvitee(invitee);
				inviteRecord.setType(InviteType.SMS);
				inviteRecord.setContent(this.inviteDetail.getContent());
				
				inviteDetails.add(inviteRecord);
			}
		}
		return inviteDetails;
	}
	
	private boolean checkCode() {
		String pageId = paramMap.get("pageId");
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code");// 验证码
		if (code == null || !code.equals(_code)) {
			return false;
		} else {
			return true;
		}
	}
	
	public String showImage() throws IOException, ServiceException {
		PersonDo person = this.personService.getByUserId(getUserId());
		String imagePath = CommonUtils.getImagePath(person.getInvite2dcodePath());
		File file = new File(imagePath);
		FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        inputStream.read(data);
        inputStream.close();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("image/png");
        OutputStream stream = null;
		try {
			stream = response.getOutputStream();
			stream.write(data);
			stream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stream.close();
		}
        
        return null;
	}
}
