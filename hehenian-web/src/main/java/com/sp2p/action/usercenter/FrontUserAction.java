package com.sp2p.action.usercenter;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;

import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.SqlInfusion;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.UserDao;
import com.sp2p.service.RegionService;
import com.sp2p.service.UserService;

public class FrontUserAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontUserAction.class);

	private static final long serialVersionUID = 1L;

	public UserService userService;
	private RegionService regionService;

	private List<Map<String, Object>> provinceList;
	private List<Map<String, Object>> cityList;
	private List<Map<String, Object>> areaList;
	private List<Map<String, Object>> regcityList;

	private long workPro = -1L;// 初始化省份默认值
	private long cityId = -1L;// 初始化话默认城市
	private long regPro = -1L;// 户口区域默认值
	private long regCity = -1L;// 户口区域默认值

	/**
	 * 初始化用户的基本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String userinformationInit() throws Exception {
		// 获取用户的信息
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		String email = user.getEmail();
		int vipStatus = user.getVipStatus();
		String userName = user.getUsername();
		int authStep = user.getAuthStep();
		Long id = user.getId();
		if (vipStatus == 1) {
			ServletActionContext.getRequest().setAttribute("msg", "普通会员");
		} else if (vipStatus == 2) {
			ServletActionContext.getRequest().setAttribute("msg", "尊敬的vip会员");
		}
		ServletActionContext.getRequest().setAttribute("username", userName);
		ServletActionContext.getRequest().setAttribute("email", email);
		ServletActionContext.getRequest().setAttribute("authStep", authStep);
		// paramMap = userService.queryUserById(user.getId());
		// 获取地省份地址
		// Long provinceId = Convert.strToLong(paramMap.get("provinceId"), -1);
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		regcityList = new ArrayList<Map<String, Object>>();
		cityList = new ArrayList<Map<String, Object>>();
		request().setAttribute("regcityList", regcityList);
		request().setAttribute("provinceList", provinceList);
		request().setAttribute("cityList", cityList);
		if (authStep == 2) {
			return "workdata";
		} else if (authStep == 3) {
			Map<String, String> map = new HashMap<String, String>();
			//
			map = userService.queryPersonById(user.getId());
			ServletActionContext.getRequest().setAttribute("realname", map.get("realName"));
			return "vip";
		} else if (authStep == 4) {
			return "pictureData";
		}
		request().setAttribute("ISDEMO", IConstants.ISDEMO);
		return SUCCESS;

	}

	/**
	 * 用户注册时候login的跳转
	 * 
	 * @return
	 */
	public String userInit() {
		return SUCCESS;
	}

	/**
	 * 根据上级地址编号返回下一级所的地址信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String ajaxqueryRegion() throws SQLException, DataException, IOException {
		// Long regionId = Convert.strToLong(paramMap.get("regionId"), -1);
		Long parentId = Convert.strToLong(request("parentId"), -1);
		Integer regionType = Convert.strToInt(request("regionType"), -1);
		List<Map<String, Object>> list;
		try {
			list = regionService.queryRegionList(-1L, parentId, regionType);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			e.printStackTrace();
			throw e;
		}
		String jsonStr = JSONArray.toJSONString(list);
		JSONUtils.printStr(jsonStr);
		return null;
	}

	/**
	 * 用户修改基本信息
	 * 
	 * @return
	 * @throws IOException
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 */
	/*
	 * public String updateUserInformation() throws IOException{ JSONObject json
	 * = new JSONObject(); try { User user = (User)
	 * session().getAttribute(IConstants.SESSION_USER); String name =
	 * paramMap.get("name"); //真实姓名 if (StringUtils.isBlank(name)) {
	 * json.put("msg", "请填写真实姓名！"); JSONUtils.printObject(json); return null;
	 * }else if (name.length()<2||name.length()>20) { json.put("msg",
	 * "名字长度为2-20个字符！"); JSONUtils.printObject(json); return null; } String
	 * gender = paramMap.get("gender"); //性别 if (StringUtils.isBlank(gender)) {
	 * json.put("msg", "请选择性别！"); JSONUtils.printObject(json); return null; }
	 * String mobilePhone = paramMap.get("mobilePhone"); //手机号码 if
	 * (StringUtils.isBlank(mobilePhone)) { json.put("msg", "请填写手机号码！");
	 * JSONUtils.printObject(json); return null; }else
	 * if(mobilePhone.length()<9||mobilePhone.length()>15){ json.put("msg",
	 * "请正确填写手机号码！"); JSONUtils.printObject(json); return null; } String qq =
	 * paramMap.get("qq"); //QQ号 if (StringUtils.isBlank(qq)) { json.put("msg",
	 * "请填写QQ号码！"); JSONUtils.printObject(json); return null; }else if
	 * (qq.length()<5||qq.length()>12) { json.put("msg", "请正确填写QQ号码！");
	 * JSONUtils.printObject(json); return null; } Long provinceId =
	 * Convert.strToLong(paramMap.get("provinceId"), -1); //省Id Long cityId =
	 * Convert.strToLong(paramMap.get("cityId"), -1); //城市id Long areaId =
	 * Convert.strToLong(paramMap.get("areaId"), -1); //区/镇/县id if (areaId==-1)
	 * { json.put("msg", "请选择所在地区！"); JSONUtils.printObject(json); return null;
	 * } String postalcode = paramMap.get("postalcode"); //邮政编码 if
	 * (StringUtils.isBlank(postalcode)) { json.put("msg", "请填写邮政编码！");
	 * JSONUtils.printObject(json); return null; }else if
	 * (postalcode.length()!=6) { json.put("msg", "请正确填写邮政编码！");
	 * JSONUtils.printObject(json); return null; } //普通会员通过填补资料升级为铜牌会员/获得礼劵
	 * boolean flag = userService.userUpgradeAwards(user.getId(),
	 * IConstants.VOUCHERS_TYPE_BRONZE_MEMBER); if (flag) {
	 * userService.frontUpdateUser(user.getId(), name, gender, mobilePhone, qq,
	 * provinceId, cityId, areaId, postalcode); json.put("msg",
	 * "恭喜您，填补资料成功，您已升级为铜牌会员！"); JSONUtils.printObject(json); return null; }
	 * 
	 * Long returnId = userService.frontUpdateUser(user.getId(), name, gender,
	 * mobilePhone, qq, provinceId, cityId, areaId, postalcode); if(returnId
	 * >=0){ json.put("msg", "保存成功!"); JSONUtils.printObject(json); return null;
	 * } else { json.put("msg", "保存失败!"); JSONUtils.printObject(json); return
	 * null; } } catch (Exception e) { log.error(e); e.printStackTrace();
	 * json.put("msg", "操作错误！"); JSONUtils.printObject(json); return null; } }
	 */
	/**
	 * 用户修改头像初始化
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String updateUserHeadImgInit() throws DataException, SQLException {
		// 获取用户的信息
		// AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		String headImg = userService.queryUserById(27).get("headImg");
		request().setAttribute("headImg", headImg);
		return SUCCESS;
	}

	/**
	 * 用户修改头像
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String updateUserHeadImg() throws SQLException {
		// 获取用户的信息
		// AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		String userHeadImg = SqlInfusion.FilteSqlInfusion(request("userHeadImg"));
		userService.frontUpdateUserHeadImg(
		// user.getId()
				(long) 27// 测试数据
				, userHeadImg);
		return SUCCESS;
	}

	public String updateUserPasswordInit() {
		return SUCCESS;
	}

	/**
	 * 用户修改密码
	 * 
	 * @return null
	 * @throws IOException
	 * @throws IOException
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String updateUserPassword() throws IOException {
		JSONObject json = new JSONObject();
		try {
			// 获取用户的信息
			AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
			String oldPassword = SqlInfusion.FilteSqlInfusion(paramMap.get("oldPassword"));
			String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password"));
			String confirmPassword = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmPassword"));
			if (StringUtils.isBlank(oldPassword)) {
				json.put("msg", "原始密码不能为空！");
				JSONUtils.printObject(json);
				return null;
			}
			if (oldPassword.length() < 6 || oldPassword.length() > 20) {
				json.put("msg", "密码长度为6-20个字符！");
				JSONUtils.printObject(json);
				return null;
			}

			if (StringUtils.isBlank(password)) {
				json.put("msg", "新密码不能为空！");
				JSONUtils.printObject(json);
				return null;
			} else if (password.length() < 6 || password.length() > 20) {
				json.put("msg", "新密码长度为6-20个字符！");
				JSONUtils.printObject(json);
				return null;
			}

			if (StringUtils.isBlank(confirmPassword)) {
				json.put("msg", "确认密码不能为空！");
				JSONUtils.printObject(json);
				return null;
			} else if (confirmPassword.length() < 6 || confirmPassword.length() > 20) {
				json.put("msg", "确认密码长度为6-20个字符！");
				JSONUtils.printObject(json);
				return null;
			} else if (!password.equals(confirmPassword)) {
				json.put("msg", "两次输入的密码不一致！");
				JSONUtils.printObject(json);
				return null;
			} else {
				String tempPassword = userService.queryUserById(user.getId()).get("password");
				if ("1".equals(IConstants.ENABLED_PASS)) {
					oldPassword = com.shove.security.Encrypt.MD5(oldPassword.trim());
				} else {
					oldPassword = com.shove.security.Encrypt.MD5(oldPassword.trim() + IConstants.PASS_KEY);
				}
				if (tempPassword.equals(oldPassword)) {
					if ("1".equals(IConstants.ENABLED_PASS)) {
						password = com.shove.security.Encrypt.MD5(password.trim());
					} else {
						password = com.shove.security.Encrypt.MD5(password.trim() + IConstants.PASS_KEY);
					}
					Long returnId = userService.frontUpdatePassword(user.getId(), password);
					if (returnId > 0) {
						json.put("msg", "恭喜您，密码修改成功！");
						JSONUtils.printObject(json);
						return null;
					} else {
						json.put("msg", "对不起，密码修改失败！");
						JSONUtils.printObject(json);
						return null;
					}
				} else {
					json.put("msg", "旧密码输入错误！");
					JSONUtils.printObject(json);
					return null;
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			json.put("msg", "操作错误！");
			JSONUtils.printObject(json);
			return null;
		}
	}

	/**
	 * 查询用户的所有地址记录
	 * 
	 * @return String
	 * @throws SQLException
	 * @throws DataException
	 */
	/*
	 * public String queryUserAddress() throws SQLException, DataException{
	 * //获取用户的信息 User user = (User)
	 * session().getAttribute(IConstants.SESSION_USER); List<Map<String,
	 * Object>> addressList = addressService.frontQueryAddressList(null,
	 * user.getId()); request().setAttribute("addressList", addressList); return
	 * SUCCESS; }
	 */
	/**
	 * 添加收货地址实例化
	 * 
	 * @return String
	 */
	public String addAddressInit() {
		return SUCCESS;
	}

	/**
	 * 增加收货地址
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	/*
	 * public String addAddress() throws SQLException, IOException{ User user =
	 * (User) session().getAttribute(IConstants.SESSION_USER); Long userId =
	 * user.getId(); Long provinceId =
	 * Convert.strToLong(paramMap.get("provinceId"), -1); Long cityId =
	 * Convert.strToLong(paramMap.get("cityId"), -1); Long areaId =
	 * Convert.strToLong(paramMap.get("areaId"), -1); String consignee =
	 * paramMap.get("consignee"); String address = paramMap.get("address");
	 * String mobilePhone = paramMap.get("mobilePhone"); String telephone =
	 * paramMap.get("telephone"); String postalcode =
	 * paramMap.get("postalcode"); Integer isDefault =
	 * Convert.strToInt("isDefault", 2); if (provinceId != null && cityId !=
	 * null && areaId != null && StringUtils.isNotBlank(consignee) &&
	 * StringUtils.isNotBlank(address) && StringUtils.isNotBlank(postalcode) &&
	 * (StringUtils.isNotBlank(mobilePhone) ||
	 * StringUtils.isNotBlank(telephone))) { Long returnId =
	 * addressService.addAddress(userId, consignee, provinceId, cityId, areaId,
	 * address, mobilePhone, telephone, postalcode, isDefault); if(returnId
	 * >=0){ JSONUtils.printStr("1"); }else { JSONUtils.printStr("2"); }
	 * 
	 * }else { JSONUtils.printStr("3"); } return null; }
	 */
	/**
	 * 用户修改地址初始化
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	/*
	 * public String updateAddressInit() throws SQLException, DataException{
	 * Long id = Convert.strToLong(request("id"), -1); paramMap =
	 * addressService.frontQueryAddressById(id);
	 * 
	 * //获取地省份地址 Long provinceId = Convert.strToLong(paramMap.get("provinceId"),
	 * -1); Long cityId = Convert.strToLong(paramMap.get("cityId"), -1);
	 * provinceList = regionService.queryRegionList(-1L, 1L, 1); cityList =
	 * regionService.queryRegionList(-1L, provinceId, 2); areaList =
	 * regionService.queryRegionList(-1L, cityId, 3);
	 * 
	 * return SUCCESS; }
	 */

	/**
	 * 修改用户地址
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	/*
	 * public String updateAddress() throws SQLException, IOException{ Long id =
	 * Convert.strToLong(paramMap.get("id"), -1); User user = (User)
	 * session().getAttribute("user"); Long userId = user.getId(); String
	 * consignee = paramMap.get("consignee"); Long provinceId =
	 * Convert.strToLong(paramMap.get("provinceId"), -1); Long cityId =
	 * Convert.strToLong(paramMap.get("cityId"), -1); Long areaId =
	 * Convert.strToLong(paramMap.get("areaId"), -1); String address =
	 * paramMap.get("address"); String mobilePhone =
	 * paramMap.get("mobilePhone"); String telephone =
	 * paramMap.get("telephone"); String postalcode =
	 * paramMap.get("postalcode"); Integer isDefault =
	 * Convert.strToInt("isDefault", 1);
	 * 
	 * if (provinceId != null && cityId != null && areaId != null &&
	 * StringUtils.isNotBlank(consignee) && StringUtils.isNotBlank(address) &&
	 * StringUtils.isNotBlank(postalcode) &&
	 * (StringUtils.isNotBlank(mobilePhone) ||
	 * StringUtils.isNotBlank(telephone))) { Long returnId
	 * =addressService.frontUpdateAddress(id, userId, consignee, provinceId,
	 * cityId, areaId, address, mobilePhone, telephone, postalcode, isDefault);
	 * if(returnId >=0){ JSONUtils.printStr("1"); }else {
	 * JSONUtils.printStr("2"); }
	 * 
	 * }else { JSONUtils.printStr("3"); } return null; }
	 */
	/**
	 * 修改用户默认地址
	 * 
	 * @return
	 * @throws SQLException
	 */
	/*
	 * public String updateDefaultAddress() throws SQLException{ User user =
	 * (User) session().getAttribute(IConstants.SESSION_USER); Long id =
	 * Convert.strToLong(request("id"), -1); addressService.defaultAddress(id,
	 * user.getId()); return SUCCESS; }
	 */

	/**
	 * 删除用户地址
	 * 
	 * @return
	 * @throws SQLException
	 */
	/*
	 * public String deleteUserAddress() throws SQLException{ Long id =
	 * Convert.strToLong(request("id"), -1) ;
	 * addressService.frontDeleteAddress(id); return SUCCESS; }
	 */
	/**
	 * 默认地址
	 * 
	 * @Title: defaultAddress
	 * @return String
	 * @throws
	 */
	/*
	 * public String defaultAddress() throws SQLException{ User user = (User)
	 * session().getAttribute("user"); Long id =
	 * Convert.strToLong(request("id"), -1) ; addressService.defaultAddress(id,
	 * user.getId()); return SUCCESS; }
	 */

	/**
	 * 用户课程管理初始化
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String userCourseInit() {

		return SUCCESS;
	}

	/**
	 * 查询课程下的课时信息
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	/*
	 * public String userLessonList() throws SQLException, DataException{ Long
	 * commonId = Convert.strToLong(request("commonId"), -1); if(commonId<=0){
	 * return SUCCESS; } AccountUserDo user = (AccountUserDo) session(IConstants.SESSION_USER);
	 * boolean flag = userCourseService.isExpired(user.getId(), commonId);
	 * if(flag){ List<Map<String,Object>> lessonList =
	 * userCourseService.queryLessonListByCourseId(commonId);
	 * request().setAttribute("lessonList", lessonList); } return SUCCESS; }
	 */

	/**
	 * 用户课时详情
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	/*
	 * public String userLessonDetails() throws SQLException, DataException{
	 * Long commonId = Convert.strToLong(request("commonId"), -1);
	 * if(commonId<=0){ return INPUT; } User user = (User)
	 * session(IConstants.SESSION_USER); boolean flag =
	 * userCourseService.isExpiredLessonByCommonId(user.getId(), commonId);
	 * //根据编号查询讲义信息 if(!flag){ return INPUT; } paramMap =
	 * lessonService.queryLessonTeacherInfoByCommonId(commonId); Long lessonId =
	 * Convert.strToLong(paramMap.get("id"),-1); List<Map<String,Object>>
	 * lectureList = lectureService.queryLectureList(lessonId, null);//查询讲义
	 * request().setAttribute("lectureList", lectureList);//讲义集合 return SUCCESS;
	 * }
	 */

	/**
	 * 添加学习课时记录
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	/*
	 * public String addStudyLesson() throws SQLException, IOException,
	 * ParseException{ Long lessonId = Convert.strToLong(request("lessonId"),
	 * -1); if(lessonId<0){ return null; } User user = (User)
	 * session(IConstants.SESSION_USER); Long lessonStudyId = -1L; String
	 * startDateStr = request("startDate"); SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date startDate = null; try {
	 * startDate = sdf.parse(startDateStr); } catch (ParseException e) {
	 * log.error(e); e.printStackTrace(); throw e; } try { lessonStudyId =
	 * lessonStudyService.addLessonStudy(user.getId(), lessonId,startDate,new
	 * Date ()); } catch (SQLException e) { log.error(e); e.printStackTrace();
	 * throw e; } JSONUtils.printStr(lessonStudyId+""); return null; }
	 */

	/**
	 * 用户学习记录
	 * 
	 * @return
	 */
	public String userLessonStudyInit() {
		return SUCCESS;
	}

	/**
	 * 用户学习记录
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	/*
	 * public String userLessonStudyInfo() throws SQLException, DataException{
	 * AccountUserDo user = (AccountUserDo) session(IConstants.SESSION_USER);
	 * lessonStudyService.queryLessonStudy(pageBean,user.getId()); return
	 * SUCCESS; }
	 */
	/**
	 * 会员中心答疑板实例化
	 * 
	 * @Title: userQuestionBoardInit
	 * @return String
	 */
	public String userQuestionBoardInit() {
		return SUCCESS;
	}

	/**
	 * 会员中心答疑板内容列表
	 * 
	 * @Title: userQuestionBoard
	 * @return String
	 */
	/*
	 * public String userQuestionBoard(){ try { User user = (User)
	 * session(IConstants.SESSION_USER); long userId = user.getId(); String
	 * title = paramMap.get("title"); String content = paramMap.get("content");
	 * lessonService.queryLessonQuestionBoard(pageBean,userId, title, content);
	 * } catch (Exception e) { log.error(e); e.printStackTrace(); } return
	 * SUCCESS; }
	 */
	/**
	 * 根据id查询会员中心答疑板详情
	 * 
	 * @Title: queryUserQuestionBoardDetail
	 * @return String
	 */
	/*
	 * public String queryUserQuestionBoardDetail(){ try { long id =
	 * Convert.strToLong(request("id"), -1); paramMap =
	 * lessonService.queryUserQuestionBoardDetail(id); } catch (Exception e) {
	 * log.error(e); e.printStackTrace(); } return SUCCESS; }
	 */

	public List<Map<String, Object>> getProvinceList() throws DataException, SQLException {
		if (provinceList == null) {
			try {
				provinceList = regionService.queryRegionList(-1L, 1L, 1);
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} catch (DataException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return provinceList;
	}

	public List<Map<String, Object>> getCityList() {
		if (cityList == null) {
			cityList = new ArrayList<Map<String, Object>>();
		}
		return cityList;
	}

	public List<Map<String, Object>> getAreaList() {
		if (areaList == null) {
			areaList = new ArrayList<Map<String, Object>>();
		}
		return areaList;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/*
	 * public void setAddressService(AddressService addressService) {
	 * this.addressService = addressService; }
	 */

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
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

	public void setProvinceList(List<Map<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}

	public void setCityList(List<Map<String, Object>> cityList) {
		this.cityList = cityList;
	}

	/*
	 * public void setCourseService(CourseService courseService) {
	 * this.courseService = courseService; }
	 * 
	 * public void setBannerService(BannerService bannerService) {
	 * this.bannerService = bannerService; }
	 * 
	 * public void setTypeService(TypeService typeService) { this.typeService =
	 * typeService; }
	 * 
	 * public void setOrderService(OrderService orderService) {
	 * this.orderService = orderService; }
	 * 
	 * public void setUserCourseService(UserCourseService userCourseService) {
	 * this.userCourseService = userCourseService; }
	 * 
	 * public void setLessonService(LessonService lessonService) {
	 * this.lessonService = lessonService; }
	 * 
	 * public void setLectureService(LectureService lectureService) {
	 * this.lectureService = lectureService; }
	 * 
	 * public void setLessonStudyService(LessonStudyService lessonStudyService)
	 * { this.lessonStudyService = lessonStudyService; }
	 */

}
