package com.sp2p.action.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.ExcelUtils;
import com.shove.util.SMSUtil;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.BorrowType;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IPersonListsConstants;
import com.sp2p.dao.BeVipDao;
import com.sp2p.entity.Admin;
import com.sp2p.service.AwardMoneyService;
import com.sp2p.service.BeVipService;
import com.sp2p.service.CostManagerService;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.PublicModelService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.RegionService;
import com.sp2p.service.UserService;
import com.sp2p.service.ValidateService;
import com.sp2p.service.admin.SMSInterfaceService;
import com.sp2p.service.admin.ShoveBorrowTypeService;
import com.sp2p.task.JobTaskService;
import com.sp2p.util.DateUtil;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserAction extends BasePageAction {
	public static Log log = LogFactory.getLog(UserAction.class);
	private static final long serialVersionUID = 1L;

	public UserService userService;
	private RegionService regionService;
	private ValidateService validateService;
	private AwardMoneyService awardMoneyService;
	private CostManagerService costManagerService;
	private SMSInterfaceService sMsService;
	private RecommendUserService recommendUserService;
	@SuppressWarnings("unused")
	private IDCardValidateService iDCardValidateService;
	private ShoveBorrowTypeService shoveBorrowTypeService;
	private BeVipService beVipService;
	// add by houli vip扣费处理
	private JobTaskService jobTaskService;
	//
	private List<Map<String, Object>> provinceList;
	private List<Map<String, Object>> regioncity;
	private List<Map<String, Object>> cityList;
	private List<Map<String, Object>> regcityList;
	private List<Map<String, Object>> areaList;
	private List<Map<String, Object>> typeList;//
	private long workPro = -1L;// 初始化省份默认值
	private long cityId = -1L;// 初始化话默认城市
	private long regPro = -1L;// 户口区域默认值
	private long regCity = -1L;// 户口区域默认值
	private Map<String, String> maps;
	private static Map<String, String> bankIdMap = new HashMap<String, String>();
	/** 导入用户资料 */
	private File userFile;
	private String userFileName;
	private String userFileContentName;
	private Map<String, String> prov = new HashMap<String, String>(); // 省列表
	private Map<String, String> city = new HashMap<String, String>(); // 地区列表
	@SuppressWarnings("unused")
	private BeVipDao beVipDao;
	/** end */
	private PublicModelService agreementService;
	static {
		bankIdMap.put("工商银行", "ICBC");
		bankIdMap.put("农行", "ABC");
		bankIdMap.put("招行", "CMB");
		bankIdMap.put("建设银行", "CCB");
		bankIdMap.put("北京银行", "BCCB");
		bankIdMap.put("北京农村商业银行", "BJRCB	");
		bankIdMap.put("中国银行", "BOC");
		bankIdMap.put("交通银行", "BOCOM");
		bankIdMap.put("民生银行", "CMBC");
		bankIdMap.put("上海银行", "BOS");
		bankIdMap.put("渤海银行", "CBHB");
		bankIdMap.put("光大银行", "CEB");
		bankIdMap.put("兴业银行", "CIB");
		bankIdMap.put("中信银行", "CITIC");
		bankIdMap.put("浙商银行", "CZB");
		bankIdMap.put("广发银行", "GDB");
		bankIdMap.put("东亚银行", "HKBEA");
		bankIdMap.put("华夏银行", "HXB");
		bankIdMap.put("杭州银行", "HZCB");
		bankIdMap.put("南京银行", "NJCB");
		bankIdMap.put("平安银行", "PINGAN");
		bankIdMap.put("邮储银行", "PSBC");
		bankIdMap.put("深发银行", "SDB");
		bankIdMap.put("浦发", "SPDB");
		bankIdMap.put("上海农村商业银行", "SRCB");
	}

	public PublicModelService getAgreementService() {
		return agreementService;
	}

	public void setAgreementService(PublicModelService agreementService) {
		this.agreementService = agreementService;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public void setShoveBorrowTypeService(ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public void setIDCardValidateService(IDCardValidateService cardValidateService) {
		iDCardValidateService = cardValidateService;
	}

	public Map<String, String> getMaps() throws Exception {
		maps = userService.queryPersonById(62);
		return maps;
	}

	public AwardMoneyService getAwardMoneyService() {
		return awardMoneyService;
	}

	public void setAwardMoneyService(AwardMoneyService awardMoneyService) {
		this.awardMoneyService = awardMoneyService;
	}

	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public CostManagerService getCostManagerService() {
		return costManagerService;
	}

	public void setCostManagerService(CostManagerService costManagerService) {
		this.costManagerService = costManagerService;
	}

	public List<Map<String, Object>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Map<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}

	public List<Map<String, Object>> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Map<String, Object>> typeList) {
		this.typeList = typeList;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setCityList(List<Map<String, Object>> cityList) {
		this.cityList = cityList;
	}

	public void setAreaList(List<Map<String, Object>> areaList) {
		this.areaList = areaList;
	}

	public SMSInterfaceService getSMsService() {
		return sMsService;
	}

	public void setSMsService(SMSInterfaceService msService) {
		sMsService = msService;
	}

	// ======地区列表
	public String ajaxqueryRegion() throws SQLException, DataException, IOException {
		// Long regionId = Convert.strToLong(paramMap.get("regionId"), -1);
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

	/**
	 * 上传页面点击上传按钮 展示用户图片和上传
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryUserPictur() throws DataException, SQLException {
		String tmidStr = SqlInfusion.FilteSqlInfusion(request().getParameter("dm"));
		// ------add by houli
		String btype = request("btype");
		List<Map<String, Object>> userPicDate = null;
		Map<String, String> typmap = null;
        AccountUserDo user = null;
		int len = 0;// 集合的大小
		@SuppressWarnings("unused")
		Long materAuthTypeId = null;
		if (StringUtils.isNotBlank(tmidStr)) {
			Long tmid = Convert.strToLong(tmidStr, -1L);
			request().setAttribute("tmid", tmid);
			userPicDate = userService.queryPerTyhpePicture(tmid);
			len = userPicDate.size();
			user = (AccountUserDo) session().getAttribute("user");
			if (user != null) {
				Long userId = user.getId();
				typmap = userService.queryPitcturTyep(userId, tmid);
			}
		}
		request().setAttribute("len", len);
		request().setAttribute("userPicDate", userPicDate);
		request().setAttribute("typmap", typmap);
		request().setAttribute("tmidStr", tmidStr);
		// ----add by houli
		if (btype != null) {
			request().setAttribute("btype", btype);
		}
		return SUCCESS;
	}

	// 用户提交图片审核
	/*public String addpastPicturdate() throws SQLException, DataException, IOException {
		Long tmid = Convert.strToLong(paramMap.get("tmid"), -1L);// materAuth的id
		Long materAuthTypeId = Convert.strToLong(paramMap.get("materAuthTypeId"), -1L);
		Integer len = Convert.strToInt(paramMap.get("len"), -1);// 上传图片个数
		Integer Listlen = Convert.strToInt(paramMap.get("listlen"), -1);// 数据库的图片个数
		Long tmids = Convert.strToLong(paramMap.get("tmidStr"), -1L);
		Long result = -1L;
		if (Listlen == -1) {
			JSONUtils.printStr("17");
			return null;
		}
		if (len == -1) {
			JSONUtils.printStr("18");
			return null;
		}
		Integer allPicturecount = len + Listlen;// 用户将要上传的图片和数据库图片的个数的总和
		if (materAuthTypeId == 1) {
			if (5 < allPicturecount) {
				JSONUtils.printStr("1");
				return null;
			}// 身份证
		}
		if (materAuthTypeId == 2) {
			if (10 < allPicturecount) {
				JSONUtils.printStr("2");
				return null;
			}// 工作认证
		}
		if (materAuthTypeId == 3) {
			if (5 < allPicturecount) {
				JSONUtils.printStr("3");
				return null;
			}// 居住认证
		}

		if (materAuthTypeId == 4) {
			if (30 < allPicturecount) {
				JSONUtils.printStr("4");
				return null;
			}// 收入认证
		}
		if (materAuthTypeId == 5) {
			if (10 < allPicturecount) {
				JSONUtils.printStr("5");
				return null;
			}// 信用报告
		}
		if (materAuthTypeId > 5) {
			if (10 < allPicturecount) {
				JSONUtils.printStr("6");
				return null;
			}//
		}

		AccountUserDo user = (AccountUserDo) session().getAttribute("user");// 获取user实体
		List<Long> lists = new ArrayList<Long>();// 已经上传的图片设置他们的可见性
		if (Listlen != -1 && user != null) {
			for (int i = 1; i <= Listlen; i++) {
				if (Convert.strToInt(paramMap.get("id" + i), -1) != -1) {
					lists.add(Convert.strToLong(paramMap.get("id" + i), -1));
				}
			}
		}

		List<String> imglistsy = new ArrayList<String>();
		List<String> imgListsn = new ArrayList<String>();
		if (len != -1 && user != null) {
			for (int i = 1; i <= len; i++) {// 将要上传图片图片先保存在一个数组里面
				if (Convert.strToStr(paramMap.get("ids" + i), null) != null) {
					// 处理传来的图片值
					String temppicture = Convert.strToStr(paramMap.get("ids" + i), "");
					int v = temppicture.indexOf(".v");
					if (v == -1) {
						imgListsn.add(temppicture);
					} else {
						// 这个是保存可见的picture
						imglistsy.add(temppicture.substring(0, v));
					}
				}
			}
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String uploadingTime = format.format(new Date());// 当前时间上传图片时间
		if (user != null && tmid != -1L && materAuthTypeId != -1L) {
			Long userId = user.getId();
			// 遍历将image查到数据库中 1 表示向t_materialsauth 插入图片类型 表示等待审核
			result = userService.addUserImage(1, uploadingTime, lists, imglistsy, imgListsn, tmid, userId, materAuthTypeId, tmids, len);
//			operationLogService.addOperationLog("t_materialimagedetal", user.getUserName(), IConstants.UPDATE, user.getLastIP(), 0, "用户提交图片审核", 2);
			if (result > 0) {
				// 更新User的状态
				try {
					Map<String, String> newstatusmap = null;
					newstatusmap = userService.querynewStatus(userId);// 查询放到session中去
					if (newstatusmap != null && newstatusmap.size() > 0) {
						// user.setAuthStep(Convert.strToInt(newstatusmap.get("authStep"),
						// -1));
						// user.setEmail(Convert.strToStr(newstatusmap.get("email"),
						// null));
						// user.setPassword(Convert.strToStr(newstatusmap.get("password"),
						// null));
						// user.setId(Convert.strToLong(newstatusmap.get("id"),
						// -1L));
						user.setRealName(Convert.strToStr(newstatusmap.get("realName"), null));
						user.setKefuname(Convert.strToStr(newstatusmap.get("kefuname"), null));
						user.setIdNo(Convert.strToStr(newstatusmap.get("idNo"), null));
						user.setVipStatus(Convert.strToInt(newstatusmap.get("vipStatus"), -1));
						user.setKefuid(Convert.strToInt(newstatusmap.get("tukid"), -1));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				JSONUtils.printStr("123");
				return null;
			} else {
				JSONUtils.printStr("321");
				return null;
			}

		}
		return null;
	}*/

	/**
	 * 更新用户的图片是否可见
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String updatevisiable() throws SQLException, DataException {
		int len = Convert.strToInt(paramMap.get("len"), -1);
		Long tmid = Convert.strToLong(paramMap.get("tmidStr"), -1L);
		List<Long> lists = new ArrayList<Long>();
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (len != -1 && user != null) {
			for (int i = 1; i <= len; i++) {
				if (Convert.strToInt(paramMap.get("id" + i), -1) != -1) {
					lists.add(Convert.strToLong(paramMap.get("id" + i), -1));
				}
			}
		}
		userService.updatevisiable(tmid, lists);// 将传来的明细id设置为可见
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_materialimagedetal", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "更新用户的图片的可见性", 2);

		return null;
	}

	/**
	 * 添加用户初始化
	 * 
	 * @return String
	 * @throws
	 */
	public String addUserInit() {
		paramMap.put("balances", "0");
		paramMap.put("enable", "1");
		paramMap.put("gender", "1");
		return SUCCESS;
	}

	/**
	 * 添加用户
	 * 
	 * @throws SQLException
	 * @throws DataException
	 * @return String
	 */
	public String addUser() throws SQLException, DataException {
		try {
			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			@SuppressWarnings("unused")
			String addDate = dateformat.format(new Date()); // 创建时间

			String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email")); // 电子邮箱
			String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName")); // 用户名
			@SuppressWarnings("unused")
			String password = SqlInfusion.FilteSqlInfusion(paramMap.get("password")); // 用户密码
			@SuppressWarnings("unused")
			String name = SqlInfusion.FilteSqlInfusion(paramMap.get("name")); // 真实姓名
			@SuppressWarnings("unused")
			String gender = SqlInfusion.FilteSqlInfusion(paramMap.get("gender")); // 性别
			@SuppressWarnings("unused")
			String mobilePhone = SqlInfusion.FilteSqlInfusion(paramMap.get("mobilePhone")); // 手机号码
			@SuppressWarnings("unused")
			String qq = SqlInfusion.FilteSqlInfusion(paramMap.get("qq"));
			@SuppressWarnings("unused")
			Long provinceId = Convert.strToLong(paramMap.get("provinceId"), -1); // 省Id
			@SuppressWarnings("unused")
			Long cityId = Convert.strToLong(paramMap.get("cityId"), -1); // 城市id
			@SuppressWarnings("unused")
			Long areaId = Convert.strToLong(paramMap.get("areaId"), -1); // 区/镇/县id
			@SuppressWarnings("unused")
			String postalcode = SqlInfusion.FilteSqlInfusion(paramMap.get("postalcode")); // 邮政编码
			@SuppressWarnings("unused")
			String headImg = SqlInfusion.FilteSqlInfusion(paramMap.get("headImg")); // 头像
			@SuppressWarnings("unused")
			Integer status = 0; // Convert.strToInt(paramMap.get("status"), 2);
			// //邮箱是否验证通过 (0:未通过1:通过)
			@SuppressWarnings("unused")
			String balances = paramMap.get("balances"); // E币账户余额
			@SuppressWarnings("unused")
			Integer enable = Convert.strToInt(paramMap.get("enable"), 1); // 是否禁用
			// 1、启用
			// 2、禁用
			@SuppressWarnings("unused")
			Integer rating = Convert.strToInt(paramMap.get("rating"), 1); // 会员等级(1:普通会员2:铜牌会员3:银牌会员4:金牌会员)
			@SuppressWarnings("unused")
			String lastDate = SqlInfusion.FilteSqlInfusion(paramMap.get("lastDate")); // 最后登录时间
			@SuppressWarnings("unused")
			String lastIP = SqlInfusion.FilteSqlInfusion(paramMap.get("lastIP")); // 最后登录ip

			Long result = userService.isExistEmailORUserName(null, userName);
			if (result > 0) {
				this.addFieldError("paramMap['userName']", "该用户已注册！");
				return INPUT;
			}
			result = userService.isExistEmailORUserName(email, null);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_user", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, "添加新用户", 2);
			if (result > 0) {
				this.addFieldError("paramMap['email']", "该邮箱已注册！");
				return INPUT;
			}
			// userService.addUser(email, userName, password, name, gender);
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
	 * 添加认证图片
	 * 
	 * @return
	 * @throws Exception
	 */
	/*public String addImage() throws Exception {
		Map<String, String> Apcmap = null;// 五大基本资料的计数存放map
		long materAuthTypeId = Convert.strToLong(paramMap.get("materAuthTypeId").toString(), -1L);
		String imgPath = SqlInfusion.FilteSqlInfusion(paramMap.get("userHeadImg"));
		if (StringUtils.isBlank(imgPath)) {
			JSONUtils.printStr("-1");
			return null;
		}

		long imageId = -1L;
		long userId = -1L;
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		// 认证状态
		if (null != user) {
			userId = user.getId();

			imageId = userService.addImage(materAuthTypeId, imgPath, userId);
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_materialsauth", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, "添加认证图片", 2);

			user = userService.jumpToWorkData(userId);// 2表示工作认证步骤 2 工作认证 1
			// 基础资料认证 3 vip认证 //5表示通过资料上传验证
			session().removeAttribute("user");
			session().setAttribute("user", user);
		}
		if (imageId < 0) {
			JSONUtils.printStr("0");
			return null;
		} else {
			Integer allcount = 0;
			Apcmap = userService.queryPicturStatuCount(user.getId());
			if (Apcmap != null && Apcmap.size() > 0) {
				allcount = Convert.strToInt(Apcmap.get("ccc"), 0);
			}
			if (allcount != 0 && allcount >= 5) {
				response().setCharacterEncoding("UTF-8");
				response().setContentType("text/html; charset=UTF-8");
				PrintWriter out = response().getWriter();
				out.print("addBorrowInit.do?t=" + session().getAttribute("borrowWay"));
				out.flush();
				out.close();
				return null;
			} else {
				JSONUtils.printStr("1");// 不给跳转
				return null;
			}
		}
	}*/

	/**
	 * 添加用户基础资料
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String addUserBaseData() throws SQLException, IOException {
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

		String cellPhone = SqlInfusion.FilteSqlInfusion(paramMap.get("cellPhone"));// 手机号码
		if (StringUtils.isBlank(cellPhone)) {
			json.put("msg", "请正确填写手机号码");
			JSONUtils.printObject(json);
			return null;
		} else if (cellPhone.length() < 9 || cellPhone.length() > 15) {
			json.put("msg", "手机号码长度不对");
			JSONUtils.printObject(json);
			return null;
		}

		String phonecode = null;
		Object objcet = session().getAttribute("phone");
		// 测试--跳过验证码
		if (IConstants.ISDEMO.equals("1")) {

		} else {
			if (objcet != null) {
				phonecode = objcet.toString();
			} else {
				json.put("msg", "请输入正确的验证码");
				JSONUtils.printObject(json);
				return null;
			}
			if (phonecode != null) {
				if (!phonecode.trim().equals(cellPhone.trim())) {
					json.put("msg", "与获取验证码手机号不一致");
					JSONUtils.printObject(json);
					return null;
				}
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
		Object obje = session().getAttribute("randomCode");
		// 测试--跳过验证码
		if (IConstants.ISDEMO.equals("1")) {

		} else {
			if (obje != null) {
				randomCode = obje.toString();
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
		Long nativePlacePro = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("nativePlacePro")), -1);// 籍贯省份(默认为-1)
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

		Long registedPlacePro = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("registedPlacePro")), -1);// 户口所在地省份(默认为-1)
		if (StringUtils.isBlank(registedPlacePro.toString())) {
			json.put("msg", "请正确填写入户口所在地省份");
			JSONUtils.printObject(json);
			return null;
		}

		Long registedPlaceCity = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("registedPlaceCity")), -1);// 户口所在地城市(默认为-1)

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

		/* 用户头像 */
		String personalHead = SqlInfusion.FilteSqlInfusion(paramMap.get("personalHead"));// 个人头像 (默认系统头像)
		if (StringUtils.isBlank(personalHead)) {
			/*
			 * json.put("msg", "请正确填写入所在地"); JSONUtils.printObject(json); return
			 * null;
			 */
			personalHead = null;
		}

		// 用户Id
		// String userId = session("");//用户Id
		AccountUserDo user = (AccountUserDo) session("user");
		/*
		 * 测试 long userId = -1L; userId = user.getId(); if(userId < 0){
		 * json.put("msg", "超时，请重新登录"); JSONUtils.printObject(json); return
		 * null; }
		 */
		String idNo = SqlInfusion.FilteSqlInfusion(paramMap.get("idNo"));// 身份证号码
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

		long personId = -1L;

		personId = userService.userBaseData(realName, cellPhone, sex, birthday, highestEdu, eduStartDay, school, maritalStatus, hasChild, hasHourse,
				hasHousrseLoan, hasCar, hasCarLoan, nativePlacePro, nativePlaceCity, registedPlacePro, registedPlaceCity, address, telephone,
				personalHead, user.getId(), idNo);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (personId > 0) {
			session().removeAttribute("randomCode");
			operationLogService.addOperationLog("t_person", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, "添加用户基础资料成功", 2);
			json.put("msg", "保存成功");
			JSONUtils.printObject(json);
			return null;
			// 成功
		} else {
			operationLogService.addOperationLog("t_person", admin.getUserName(), IConstants.INSERT, admin.getLastIP(), 0, "添加用户基础资料失败", 2);
			json.put("msg", "保存失败");
			// 失败
			JSONUtils.printObject(json);
			return null;
		}
	}

	/**
	 * 审核基础资料
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 */
	public String updateUserBaseDataCheck() throws SQLException, IOException, DataException {
		JSONObject json = new JSONObject();

		long personId = -1L;
		int auditStatus = 1;// 默认不通过审核
		long userId = Convert.strToLong(paramMap.get("userId"), -1l);
		long flag = -1L;
		String statuss = null;
		if (StringUtils.isNotBlank(paramMap.get("flag"))) {
			flag = Long.parseLong(paramMap.get("flag"));
		}
		if (flag == 1) {
			auditStatus = 3;// 通过审核
			statuss = "通过审核";
		} else if (flag == 0) {
			auditStatus = 2;// 审核不通过
			statuss = "审核不通过";
		} else {
			return null;
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		long adminId = admin.getId();
		if (admin != null) {
			personId = userService.updateUserBaseDataCheck(userId, auditStatus, adminId);
			operationLogService.addOperationLog("t_person", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "审核基础资料，" + statuss, 2);

		}
		// 测试---跳过
		if (IConstants.ISDEMO.equals("1"))
			json.put("msg", "保存成功");
		// 成功
		if (personId > 0)
			json.put("msg", "保存成功");
		// 失败
		else
			json.put("msg", "保存失败");
		JSONUtils.printObject(json);
		return null;
	}

	/**
	 * 前台 更新基本信息
	 */
	public String updateUserBaseData() throws Exception {
		AccountUserDo user = (AccountUserDo) session("user");
        Map<String, String> map = userService.queryPersonById(user.getId() );
        JSONObject json = new JSONObject();
        if (map!=null&&map.get("idNo")!=null&&StringUtils.isNotBlank(map.get("idNo").toString())){
            //已经保存过身份证了
            json.put("msg", "修改失败");
            JSONUtils.printObject(json);
            return null;
        }

		String realName = SqlInfusion.FilteSqlInfusion(paramMap.get("realName"));// 真实姓名
		if (StringUtils.isBlank(realName)) {
			json.put("msg", "请填写真实名字");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > realName.length() || 20 < realName.length()) {
			json.put("msg", "真实姓名的长度为不小于2和大于20");
			JSONUtils.printObject(json);
			return null;
		}

		String idNo = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("idNo")), "");// 身份证号码
		long len = idNo.length();
		if (StringUtils.isBlank(idNo)) {
			json.put("msg", "请填写身份证号码");
			JSONUtils.printObject(json);
			return null;
		} else if (15 != len && 18 != len) {
			json.put("msg", "请填写真实的身份证号码");
			JSONUtils.printObject(json);
			return null;
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
			json.put("msg", "身份证不合法");
			JSONUtils.printObject(json);
			return null;
		}
		String iDresutl = IDCardValidateService.chekIdCard(MAN_SEX, idNo);
		if (iDresutl != "") {
			json.put("msg", "身份证不合法");
			JSONUtils.printObject(json);
			return null;
		}

		Map<String, String> pMap = null;
		pMap = beVipService.queryPUser(user.getId());
		if (pMap == null) {
			pMap = new HashMap<String, String>();
		}
		String isno = Convert.strToStr(pMap.get("idNo"), "");
		// 测试--跳过验证
		if (!IConstants.ISDEMO.equals("1")) {
			// add by houli 判断身份证的唯一性
			if (StringUtils.isBlank(isno)) {
				Map<String, String> idNoMap = beVipService.queryIDCard(idNo);
				if (idNoMap != null && !idNoMap.isEmpty()) {
					json.put("msg", "身份证已注册");
					JSONUtils.printObject(json);
					return null;
				}
			}
		}

		String sex = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("sex")), null);// 性别(男 女)
		String birthday = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("birthday")), null);// 出生日期
		String highestEdu = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("highestEdu")), null);// 最高学历
		String eduStartDay = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("eduStartDay")), null);// 入学年份
		String school = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("school")), null);// 毕业院校
		Long nativePlacePro = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("nativePlacePro")), -1);// 籍贯省份(默认-1)
		Long nativePlaceCity = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("nativePlaceCity")), -1);// 籍贯城市(默认-1)
		Long registedPlacePro = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("registedPlacePro")), -1);// 户口所在地省份(默认-1)
		Long registedPlaceCity = Convert.strToLong(SqlInfusion.FilteSqlInfusion(paramMap.get("registedPlaceCity")), -1);// 户口所在地城市(默认-1)
		String address = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("address")), null);// 所在地
		String email = Convert.strToStr(SqlInfusion.FilteSqlInfusion(paramMap.get("email")), null);// 邮件

		
//		
		boolean result = userService.updateUserBaseData2(realName, sex, birthday, highestEdu, eduStartDay, school, nativePlacePro, nativePlaceCity,
				registedPlacePro, registedPlaceCity, address, user.getId(), idNo,email);

		if (result) {
			if (user.getAuthStep() == 1) {
				user.setAuthStep(2);
			}
			//资本资料直接通过
//			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
//	        long adminId = admin.getId();
//	        if (admin != null) {
	            userService.updateUserBaseDataCheck(user.getId(), 3, user.getId());
	            operationLogService.addOperationLog("t_person", user.getUsername(), IConstants.UPDATE, user.getLastIP(), 0, "审核基础资料，" + "审核通过", 2);
//	        }
			session().removeAttribute("randomCode");
			json.put("msg", "保存成功");
			request().setAttribute("person", "1");
//			user.setRealName(realName);
//			user.setIdNo(idNo);
			user.setEmail(email);
			session().setAttribute("user", user);

            //将姓名和身份证号码放入session 定期理财那边需要
            session().setAttribute("realName",realName);
            session().setAttribute("idNo",idNo);
		} else {
			json.put("msg", "保存失败,请重试");
		}
		JSONUtils.printObject(json);
		return null;
	}

	/**
	 * 基本信息提交后再次跳转到user_information这个jsp页面
	 * 
	 * @return
	 * @throws Exception
	 */
	/*public String againUserInformation() throws Exception {
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");

		long userId = user.getId();
		user = userService.jumpToWorkData(userId);// 2表示工作认证步骤 2 工作认证 1
		// 基础资料认证 3 vip认证
		if (user != null) {
			session().removeAttribute("user");
			session().setAttribute("user", user);
			ServletActionContext.getRequest().setAttribute("authStep", user.getAuthStep());
			request().setAttribute("provinceList", provinceList);
			return SUCCESS;
		} else {
			return LOGIN;
		}
	}*/

	/**
	 * 工作信息认证后再次跳user_information这个jsp页面
	 * 
	 * @throws Exception
	 */
	/*public String againUserInformationTwo() throws Exception {

		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (user != null) {
			long userId = user.getId();
//			user = userService.jumpToWorkData(userId);// 表示工作认证步骤 2 工作认证 1
			// 基础资料认证 3 vip认证
			session().removeAttribute("user");
			session().setAttribute("user", user);
			ServletActionContext.getRequest().setAttribute("authStep", user.getAuthStep());
//			ServletActionContext.getRequest().setAttribute("realname", user.getRealName());
			int vipStatus = user.getVipStatus();
			if (vipStatus == 1) {
				ServletActionContext.getRequest().setAttribute("msg", "普通会员");
			} else {
				ServletActionContext.getRequest().setAttribute("msg", "尊敬的vip会员");
			}

			ServletActionContext.getRequest().setAttribute("email", user.getEmail());
//			ServletActionContext.getRequest().setAttribute("username", user.getUserName());
			request().setAttribute("provinceList", provinceList);
			return SUCCESS;
		} else {
			return LOGIN;
		}

	}
*/
	/**
	 * 前台 更新的工作认证资料添加
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
			json.put("msg", "公司名字不正确");
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
			json.put("msg", "请正确填写公司电话");
			JSONUtils.printObject(json);
			return null;
		}

		Pattern pattern = Pattern.compile("^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$");
		Matcher m = pattern.matcher(companyTel);
		if (!m.matches()) {
			json.put("msg", "请正确填写公司电话");
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
		Map<String, String> resultMap = userService.updateUserWorkData1(orgName, occStatus, workPro, workCity, companyType, companyLine,
				companyScale, job, monthlyIncome, workYear, companyTel, workEmail, companyAddress, "", "", "", "", "", "", "", "", "", userId,
				vipStatus, newutostept);
		result = Convert.strToLong(resultMap.get("ret") + "", -1);
		if (result > 0) {
			// 保存成功更新认证步骤
			if (user.getAuthStep() == 2) {
				user.setAuthStep(3);
			}
			json.put("msg", "保存成功");
			JSONUtils.printObject(json);
			return null;
		} else {
			json.put("msg", "保存失败");
			JSONUtils.printObject(json);
			return null;
		}

	}

	private String from;
	private String btype;

	public String getBtype() {
		return btype;
	}

	public void setBtype(String btype) {
		this.btype = btype;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	// public String execute(){
	// // System.out.println("from = " + from);
	// return from;
	// }
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
		// --------modify by houli
		String from = getFrom();
		if (from == null) {
			from = request("from");
		}
		String btype = getBtype();

		if (from != null) {
			request().setAttribute("from", from);
		}
		if (btype != null) {
			request().setAttribute("btype", btype);
		}
		// -----------
		if (user != null) {
			map = userService.queryPersonById(user.getId());
			if (map != null && map.size() > 0) {

				workPro = Convert.strToLong(map.get("nativePlacePro") + "", -1L);
				cityId = Convert.strToLong(map.get("nativePlaceCity") + "", -1L);
				regPro = Convert.strToLong(map.get("registedPlacePro") + "", -1L);
				regCity = Convert.strToLong(map.get("registedPlaceCity") + "", -1L);

				workPro = Convert.strToLong(map.get("nativePlacePro") + "", -1L);
				cityId = Convert.strToLong(map.get("nativePlaceCity") + "", -1L);
				regPro = Convert.strToLong(map.get("registedPlacePro") + "", -1L);
				regCity = Convert.strToLong(map.get("registedPlaceCity") + "", -1L);
				@SuppressWarnings("unused")
				String birthd = map.get("birthday");
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
	 * 查询后台的审核用户的详细信息
	 */
	/**
	 * 查询baseData
	 * 
	 * @return
	 * @throws Exception
	 */

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

	/**
	 * 查询用户基本资料
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAdminBasecMessage() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		long id = -1l;
		String birth = null;
		String rxedate = null;
		if (StringUtils.isNotBlank(request("uid")))
			id = Long.parseLong(request("uid"));

		map = userService.queryPersonBasecMessageById(id);
		if (map != null && map.size() > 0) {
			workPro = Convert.strToLong(map.get("nativePlacePro") + "", -1L);
			cityId = Convert.strToLong(map.get("nativePlaceCity") + "", -1L);
			regPro = Convert.strToLong(map.get("registedPlacePro") + "", -1L);
			regCity = Convert.strToLong(map.get("registedPlaceCity") + "", -1L);

			birth = Convert.strToStr(map.get("birthday"), null);
			rxedate = Convert.strToStr(map.get("eduStartDay"), null);
			if (birth != null)
				birth = birth.substring(0, 10);
			if (rxedate != null)
				rxedate = rxedate.substring(0, 10);
		}
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		regcityList = regionService.queryRegionList(-1L, regPro, 2);
		request().setAttribute("provinceList", provinceList);
		request().setAttribute("cityList", cityList);
		request().setAttribute("regcityList", regcityList);
		request().setAttribute("birth", birth);
		request().setAttribute("rxedate", rxedate);
		request().setAttribute("map", map);
		if (map == null) // 如果map是空的话 那么用户没有填写基本信息
			request().setAttribute("flag", "1");
		return SUCCESS;
	}

	/**
	 * vip页面跳转到workdata页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryVipToWork() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> allworkmap = new HashMap<String, String>();
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		request().setAttribute("provinceList", provinceList);
		String btype = request("btype");
		if (btype != null) {
			request().setAttribute("btype", btype);
		}
		String from = request("from");
		if (from != null && !from.equals("")) {
			request().setAttribute("from", from);
		}
		if (user != null) {
			// 获取用户认证进行的步骤
			if (user.getAuthStep() == 1) {
				// 个人信息认证步骤
				response().setContentType("text/html; charset=UTF-8");
				PrintWriter out = response().getWriter();
				out.print("<script>alert('基本资料尚未填写或未审核');window.location.href='owerInformationInit.do';</script>");
				return null;
			}

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
			@SuppressWarnings("unused")
			int authStep = user.getAuthStep();
			return SUCCESS;
		} else {
			return LOGIN;
		}
	}

	/**
	 * 基本信息跳转到工作信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryWorkDatabaseAgain() throws Exception {
		@SuppressWarnings("unused")
		Map<String, String> map = new HashMap<String, String>();
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		request().setAttribute("provinceList", provinceList);
		int authStep = user.getAuthStep();
		if (authStep == 3 || authStep == 4) {
			return "upWorkData";
		}
		return SUCCESS;
	}

	/**
	 * 在基本资料修改 和 工作认证资料修改 和 vip申请修改中的 资料上传的连接
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String upDataMethod() throws SQLException, DataException {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		int authStep = user.getAuthStep();
		if (authStep == 4) {
			return SUCCESS;
		}
		if (authStep == 2) {
			provinceList = regionService.queryRegionList(-1L, 1L, 1);
			request().setAttribute("provinceList", provinceList);
			return "jibenjiliao";
		}
		if (authStep == 3) {
			provinceList = regionService.queryRegionList(-1L, 1L, 1);
			request().setAttribute("provinceList", provinceList);
			return "workData";
		} else if (authStep == 5) {
			return SUCCESS;
		}
		return LOGIN;

	}

	/**
	 * 基本资料跳转到vip页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryBaseToVip() throws Exception {
		@SuppressWarnings("unused")
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> vippagemap = null;
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		// ----------modify houli
		String from = getFrom();// execute();
		if (from == null) {
			request().setAttribute("from", from);
		}
		String btype = getBtype();// request("btype");
		if (btype == null) {
			btype = request("btype");
			if (btype != null && !btype.equals("")) {
				request().setAttribute("btype", btype);
			}
		} else {
			request().setAttribute("btype", btype);
		}
		// ------------

		if (user != null) {
			if (from == null || from.equals("")) {
				if (user.getAuthStep() == 1) {
					// 个人信息认证步骤
					return "querBaseData";
				} else if (user.getAuthStep() == 2) {
					// 工作信息认证步骤
					return "querWorkData";
				}
			}

			vippagemap = userService.queryVipParamList(user.getId());
			request().setAttribute("vippagemap", vippagemap);

			// ----------add by houli 进行vip申请判断，如果该用户已经申请了vip，则不能再次申请
			if (user.getVipStatus() == IConstants.VIP_STATUS) {
				request().setAttribute("isVip", "true");
			}
			// --------------

			return SUCCESS;
		} else {
			return LOGIN;
		}

	}

	/**
	 * 更新用户vip状态和认证步骤
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updataVipStatus() throws Exception {
		JSONObject obj = new JSONObject();
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		// ----------------modify by houli
		String from = SqlInfusion.FilteSqlInfusion(paramMap.get("from"));

		if (from == null || from.equals("")) {
			if (user.getAuthStep() == 1) {
				// 个人信息认证步骤
				obj.put("msg", "13");
				JSONUtils.printObject(obj);
				return null;
			} else if (user.getAuthStep() == 2) {
				// 工作信息认证步骤
				obj.put("msg", "14");
				JSONUtils.printObject(obj);
				return null;
			}
		}

		String pageId = SqlInfusion.FilteSqlInfusion(paramMap.get("pageId"));
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = SqlInfusion.FilteSqlInfusion(paramMap.get("code").toString().trim());
		String servicePersonId = SqlInfusion.FilteSqlInfusion(paramMap.get("kefu"));// 客服跟踪人
		Long server = Convert.strToLong(servicePersonId, -1);
		if (StringUtils.isBlank(servicePersonId)) {
			obj.put("msg", "2");
			JSONUtils.printObject(obj);
			return null;
		}

		if (code == null || !_code.equals(code)) {
			obj.put("msg", "5");
			JSONUtils.printObject(obj);
			return null;
		}
		Long userId = user.getId();

		Map<String, Object> platformCostMap = getPlatformCost();
		double vipFee = Convert.strToDouble(platformCostMap.get(IAmountConstants.VIP_FEE_RATE) + "", 0);
		double money = Convert.strToDouble(platformCostMap.get(IAmountConstants.ORDINARY_FRIENDS_FEE) + "", 0);
		Map<String, String> map = beVipService.beVip(userId, server, vipFee, money);
		Map<String, String> userMap = userService.queryUserById(userId);
		if (userMap != null) {
			user.setAuthStep(Convert.strToInt(map.get("authStep"), user.getAuthStep()));
			user.setVipStatus(Convert.strToInt(map.get("vipStatus"), user.getVipStatus()));
		}
		int ret = Convert.strToInt(map.get("ret") + "", -1);
		if (ret < 1) {
			obj.put("msg", map.get("ret_desc") + "");
			JSONUtils.printObject(obj);
			return null;
		} else {
			// (如果用户已经申请完vip，重新设置user里面的vip状态，因为接下来跳转的地方是从user里面读取vip状态)
			user.setVipStatus(IConstants.VIP_STATUS);
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * 查询客服列表
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String querykefylist() throws DataException, SQLException {
		List<Map<String, Object>> map = null;
		map = userService.querykefylist();
		if (map != null && map.size() > 0) {
			request().setAttribute("map", map);
		}

		return SUCCESS;
	}

	public String jumpTopage() {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		if (user != null) {
			return SUCCESS;
		} else {
			return LOGIN;
		}

	}

	/**
	 * 跳转到上传页面
	 * 
	 * @throws SQLException
	 * @throws DataException
	 */
	public String jumpPassDatapage() throws Exception {
		AccountUserDo user = (AccountUserDo) session().getAttribute("user");
		Long userId = -1l;
		// Map<String,String> pictruemap = null;
		List<Map<String, Object>> basepictur = null;
		List<Map<String, Object>> selectpictur = null;
		// -----------modify by houli
		String from = SqlInfusion.FilteSqlInfusion(request("from"));
		String btype = SqlInfusion.FilteSqlInfusion(request("btype"));
		// -------------
		if (user != null) {
			if (from == null || from.equals("")) {
				// 获取用户认证进行的步骤
				if (user.getAuthStep() == 1) {
					// 个人信息认证步骤
					response().setContentType("text/html; charset=UTF-8");
					PrintWriter out = response().getWriter();
					out.print("<script>alert('基本资料尚未填写或未审核');window.location.href='owerInformationInit.do';</script>");
					return null;
				} else if (user.getAuthStep() == 2) {
					// 工作信息认证步骤
					response().setContentType("text/html; charset=UTF-8");
					PrintWriter out = response().getWriter();
					out.print("<script>alert('工作资料尚未填写或未审核');window.location.href='querWorkData.do';</script>");
					return null;
				} else if (user.getAuthStep() == 3) {
					// VIP申请认证步骤 合和年暂无
					// return "quervipData";
				}
				// ---------add by houli
				else if (user.getAuthStep() == 5 && (btype != null && !btype.equals(""))) {
					return "jumpOther";
				}
			} else {// 薪金贷跟生意贷操作步骤
				// 获取用户认证进行的步骤
				if (user.getAuthStep() == 1) {
					// 个人信息认证步骤
					response().setContentType("text/html; charset=UTF-8");
					PrintWriter out = response().getWriter();
					out.print("<script>alert('基本资料尚未填写或未审核');window.location.href='owerInformationInit.do';</script>");
					return null;
				}

				if (user.getVipStatus() == IConstants.UNVIP_STATUS) {
					return "quervipData";
				}

				// -------add by houli
				// return jumpToBorrow(btype);
				if (IConstants.BORROW_TYPE_NET_VALUE.equals(btype)) {
					return "jumpNet";
				} else if (IConstants.BORROW_TYPE_SECONDS.equals(btype)) {
					return "jumpSeconds";
				}
				// -----------------
			}

			userId = user.getId();
			// 获取到图片的地址和图片的状态值
			// pictruemap = userService.queryUserPictureStatus(userId);
			// request().setAttribute("pictruemap", pictruemap);
			basepictur = userService.queryBasePicture(userId);// 五大基本
			selectpictur = userService.querySelectPicture(userId);// 可选
			request().setAttribute("basepictur", basepictur);
			request().setAttribute("selectpictur", selectpictur);

			return SUCCESS;

		} else {
			return LOGIN;
		}

	}

	/**
	 * 注册页面跳转使用条款
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String querytipM() throws SQLException, DataException {
		paramMap = agreementService.getAgreementDetail(1, 2);

		return SUCCESS;
	}

	/**
	 * 注册页面跳转隐私条款
	 * 
	 * @MethodName: querytipsM
	 * @Param: UserAction
	 * @Author: gang.lv
	 * @Date: 2013-5-11 下午02:38:52
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public String querytipsM() throws SQLException, DataException {
		paramMap = agreementService.getAgreementDetail(1, 1);
		return SUCCESS;
	}

	/**
	 * @throws SQLException
	 *             删除用户
	 * 
	 * @return String
	 * @throws
	 */
	public String deleteUser() throws SQLException {
		String userIds = SqlInfusion.FilteSqlInfusion(request("userId"));
		String[] userids = userIds.split(",");
		if (userids.length > 0) {
			long tempId = 0;
			for (String str : userids) {
				tempId = Convert.strToLong(str, -1);
				if (tempId == -1) {
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}

		/*
		 * try { // userService.deleteUser(userIds, ","); } catch (DataException
		 * e) { e.printStackTrace(); } catch (SQLException e) {
		 * e.printStackTrace(); }
		 */
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_user", admin.getUserName(), IConstants.DELETE, admin.getLastIP(), 0, "删除用户", 2);
		return SUCCESS;
	}

	/**
	 * 分页查询用户信息初始化
	 * 
	 * @return String
	 * @throws
	 */
	public String queryUserListInfoInit() {
		return SUCCESS;
	}

	/**
	 * 分页查询用户
	 * 
	 * @throws SQLException
	 * @throws DataException
	 * @return String
	 * @throws
	 */
	public String queryUserListInfo() throws SQLException, DataException {
		@SuppressWarnings("unused")
		String email = SqlInfusion.FilteSqlInfusion(paramMap.get("email")); // 电子邮箱
		@SuppressWarnings("unused")
		String userName = SqlInfusion.FilteSqlInfusion(paramMap.get("userName")); // 用户名
		@SuppressWarnings("unused")
		String name = SqlInfusion.FilteSqlInfusion(paramMap.get("name")); // 真实姓名
		@SuppressWarnings("unused")
		Integer gender = Convert.strToInt(paramMap.get("gender"), -1); // 性别
		@SuppressWarnings("unused")
		String mobilePhone = SqlInfusion.FilteSqlInfusion(paramMap.get("mobilePhone")); // 手机号码
		@SuppressWarnings("unused")
		String qq = SqlInfusion.FilteSqlInfusion(paramMap.get("qq"));
		@SuppressWarnings("unused")
		Long provinceId = Convert.strToLong(paramMap.get("provinceId"), -1); // 省Id
		@SuppressWarnings("unused")
		Long cityId = Convert.strToLong(paramMap.get("cityId"), -1); // 城市id
		@SuppressWarnings("unused")
		Long areaId = Convert.strToLong(paramMap.get("areaId"), -1); // 区/镇/县id
		@SuppressWarnings("unused")
		Integer status = Convert.strToInt(paramMap.get("status"), 2); // 邮箱是否验证通过
		// (0:未通过1:通过)
		@SuppressWarnings("unused")
		Integer enable = Convert.strToInt(paramMap.get("enable"), 2); // 是否禁用
		// 1、启用
		// 2、禁用
		@SuppressWarnings("unused")
		Integer rating = Convert.strToInt(paramMap.get("rating"), 1); // 会员等级(1:普通会员2:铜牌会员3:银牌会员4:金牌会员)

		/*
		 * try { //userService.queryUserList(pageBean, email, userName, name,
		 * gender, mobilePhone, qq, provinceId, cityId, areaId, status, enable,
		 * rating); } catch (SQLException e) { log.error(e);
		 * e.printStackTrace(); throw e; } catch (DataException e) {
		 * log.error(e); e.printStackTrace(); throw e; }
		 */
		return SUCCESS;
	}

	/**
	 * 发送短信验证
	 * 
	 * @param code
	 * @return
	 */
	public String SendSMS() throws SQLException, DataException {
		try {
			// 清空验证码
			session().removeAttribute("randomCode");
			session().removeAttribute("phone");

			String phone = SqlInfusion.FilteSqlInfusion(paramMap.get("phone")); 
			 
			String sign = phone.substring(0, 10);
			phone = phone.substring(10, phone.length());
			String signNew = Encrypt.MD5(phone + IConstants.PASS_KEY).substring(0, 10);
			if (!signNew.equals(sign)) {// 签名验证
			JSONUtils.printStr("2");
			    return null;
			}
			 
			phone = Encrypt.decryptSES(phone, IConstants.PASS_KEY);
			String[] t = phone.split("/");
			phone = t[0].toString();
			String time = new String();
			String uid = new String();
			if (t[1] != null) {
			time = t[1];
			}
			if (t[3] != null) {
			uid = t[3];
			}
			AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
			if (!uid.equals(user.getId() + "")) {
			JSONUtils.printStr("2");
			return null;
			}
			long currTime = new Date().getTime();
			long sendTime = Long.valueOf(time);
			if (currTime - sendTime > 30 * 1000) {
			JSONUtils.printStr("2");
			return null;
			}
			// 随机产生4位数字
			int intCount = 0;
			intCount = (new Random()).nextInt(9999);// 最大值位9999
			if (intCount < 1000)
				intCount += 1000; // 最小值位1001

			String randomCode = intCount + "";
			// 测试--跳过验证
			if (IConstants.ISDEMO.equals("1")) {
				JSONUtils.printStr("1");
				session().setAttribute("randomCode", randomCode);
				session().setAttribute("phone", phone);
				return null;
			}
			// 发送短信
			Map<String, String> map = sMsService.getSMSById(1);

			String content = "尊敬的客户您好,欢迎使用桂林市合和年信贷,手机验证码为:";
			// //获取短信接口url
			// String url=SMSUtil.getSMSPort(map.get("url"), map.get("UserID"),
			// map.get("Account"), map.get("Password"), randomCode, content,
			// phone, null);
			// //发送短信
			// String retCode = SMSUtil.sendSMS(url);
			String retCode = SMSUtil.sendSMS(map.get("Account"), map.get("Password"), content, phone, randomCode);
			if ("Sucess".equals(retCode)) {
				JSONUtils.printStr("1");
				session().setAttribute("randomCode", randomCode);
				session().setAttribute("phone", phone);
				return null;
			} else {
				JSONUtils.printStr("2");
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;

	}
	/**
	* 发送短信验证
	* 
	* @param code
	* @return
	*/
	public String sendSMSNew() throws SQLException, DataException {
	try {
	// 清空验证码
	session().removeAttribute("randomCode");
	session().removeAttribute("phone");
	String phone = SqlInfusion.FilteSqlInfusion(paramMap.get("phone")); 
	 
	String sign = phone.substring(0, 10);
	phone = phone.substring(10, phone.length());
	String signNew = Encrypt.MD5(phone + IConstants.PASS_KEY).substring(0, 10);
	if (!signNew.equals(sign)) {// 签名验证
	JSONUtils.printStr("2");
	    return null;
	}
	 
	phone = Encrypt.decryptSES(phone, IConstants.PASS_KEY);
	String pageId = paramMap.get("pageId");
	String code = (String) session().getAttribute(pageId + "_checkCode");
	String[] t = phone.split("/");
	phone = t[0].toString();
	String time = new String();
	String codeBak = new String();
	if (t[1] != null) {
	time = t[1];
	}
	if (t[2] != null) {
	    codeBak = t[2];
	}
	if (!codeBak.equals(code)) {
	    JSONUtils.printStr("2");
	    return null;
	}
	long currTime = new Date().getTime();
	long sendTime = Long.valueOf(time);
	if (currTime - sendTime > 30 * 1000) {
	JSONUtils.printStr("2");
	return null;
	}
	// 随机产生4位数字
	int intCount = 0;
	intCount = (new Random()).nextInt(9999);// 最大值位9999
	if (intCount < 1000)
	intCount += 1000; // 最小值位1001
	String randomCode = intCount + "";
	// 测试--跳过验证
	if (IConstants.ISDEMO.equals("1")) {
	JSONUtils.printStr("1");
	session().setAttribute("randomCode", randomCode);
	session().setAttribute("phone", phone);
	        return null;
	}
	// 发送短信
	Map<String, String> map = sMsService.getSMSById(1);
	String content = "尊敬的客户您好,欢迎使用" + IConstants.PRO_GLOBLE_NAME + ",手机验证码为:";
	// //获取短信接口url
	// String url=SMSUtil.getSMSPort(map.get("url"), map.get("UserID"),
	// map.get("Account"), map.get("Password"), randomCode, content,
	// phone, null);
	// //发送短信
	// String retCode = SMSUtil.sendSMS(url);
	String retCode = SMSUtil.sendSMS(map.get("Account"), map.get("Password"), content, phone, randomCode);
	if ("Sucess".equals(retCode)) {
	    JSONUtils.printStr("1");
	    session().setAttribute("randomCode", randomCode);
	    session().setAttribute("phone", phone);
	return null;
	} else {
	JSONUtils.printStr("2");
	    return null;
	}
	} catch (Exception e) {
	log.error(e);
	e.printStackTrace();
	}
	return null;
	}
	public String phoneCheck() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("ret", -1 + "");
		String phone = Convert.strToStr(request("phone"), "");
		String code = Convert.strToStr(request("code"), "");
		String uid = "-1";
		AccountUserDo user = (AccountUserDo) session().getAttribute(IConstants.SESSION_USER);
		if (user != null) {
		uid = user.getId() + "";
		}
		if (StringUtils.isBlank(phone)) {
		obj.put("msg", "手机号码不能为空");
		JSONUtils.printObject(obj);
		return null;
		}
		// 手机号码验证
		Pattern p = Pattern.compile("^1[3,5,8]\\d{9}$");
		Matcher m = p.matcher(phone);
		if (!m.matches()) {
		obj.put("msg", "请输入正确的手机号码进行查询");
		JSONUtils.printObject(obj);
		return null;
		}
		String time = DateUtil.getTimeCurS("yyyy:MM:dd HH:mm:ss", new Date());
		try {
		phone = Encrypt.encryptSES(phone + "/" + time + "/" + code + "/" + uid, IConstants.PASS_KEY); 
		phone = Encrypt.MD5(phone + IConstants.PASS_KEY).substring(0, 10) + phone;
		obj.put("ret", 1 + "");
		obj.put("phone", phone);
		} catch (Exception e) {
		e.printStackTrace();
		}
		JSONUtils.printObject(obj);
		return null;
		}

	/**
	 * 随机生成账户密码
	 * */
	public String randomUserNamePassword() {
		String randomStr = UUID.randomUUID().toString();
		return randomStr.substring(randomStr.length() - 8, randomStr.length());
	}

	/**
	 * 验证身份证(已存在返回"exit" 通过返回"" 其他返回错误信息)
	 */
	public String checkIdNo(String idNo) {
		long len = idNo.length();
		// 验证身份证
		int sortCode = 0;
		int MAN_SEX = 0;
		if (len == 15) {
			sortCode = Integer.parseInt(idNo.substring(12, 15));
		} else if (len == 18) {
			sortCode = Integer.parseInt(idNo.substring(14, 17));
		} else {
			return "身份证格式错误";
		}
		if (sortCode % 2 == 0) {
			MAN_SEX = 2;// 女性身份证
		} else if (sortCode % 2 != 0) {
			MAN_SEX = 1;// 男性身份证
		} else {
			return "身份证验证错误";
		}
		String iDresutl = IDCardValidateService.chekIdCard(MAN_SEX, idNo);
		if (iDresutl.equals("")) {
			// 验证身份证唯一
			Map<String, String> map = null;
			try {
				map = beVipService.queryIDCard(idNo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (map != null && map.size() > 0) {
				return "exit";
			} else {
				return "";
			}
		}
		return "身份证验证错误";
	}

	/**
	 * 验证手机号码正确 和 唯一
	 */
	public String checkCellPhone(String cellPhone) {
		String regex = "^[1][3,5,4,8][0-9]{9}$";
		if (Pattern.matches(regex, cellPhone)) {
			// 验证手机的唯一性
			try {
				Map<String, String> phonemap = beVipService.queryIsPhone(cellPhone);
				if (phonemap != null) {
					return "手机号已存在";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}
		return "手机号码错误";
	}

	/**
	 * 导入用户资料 add by dong 10-15
	 * */
	public String importUserInfo() throws SQLException {
		if (userFile == null) {
			this.addFieldError("userFile", "数据上传错误,请选择文件!");
			return INPUT;
		}

		String[][] datas = null;
		try {
			datas = ExcelUtils.getData(userFile, 2);
		} catch (Exception e) {
			e.printStackTrace();
			this.addFieldError("userFile", "数据文件格式错误,请选择正确的文件!");
			return INPUT;
		}
		if (datas == null || datas.length == 0) {
			this.addFieldError("userFile", "数据导入为空,请按照模板填写资料后再导入!");
			return INPUT;
		}
		// getNativePlaceCity();// 查询地区列表
		DateFormat format = DateUtil.YYYY_MM_DD_MM_HH_SS;
		List<Map<String, String>> persons = new ArrayList<Map<String, String>>();
		List<Map<String, String>> works = new ArrayList<Map<String, String>>();
		List<Map<String, String>> borrows = new ArrayList<Map<String, String>>();
		List<Map<String, String>> banks = new ArrayList<Map<String, String>>();
		List<Map<String, String>> users = new ArrayList<Map<String, String>>();
		for (int i = 0; i < datas.length; i++) {
			if (datas[i].length < 47) {
				this.addFieldError("userFile", "文件第" + (i + 3) + "条数据存在错误,请严格按照模板填写资料!");
				return INPUT;
			}
			String cellPhone = datas[i][15];// 手机号码
			String idNo = datas[i][14];// 身份证号码
			String idCheck = checkIdNo(idNo);// 已存在返回"exit" 通过返回"" 其他返回错误信息
			if (idCheck.equals("")) {
				// 身份证通过,验证手机号
				String checkRet = checkCellPhone(cellPhone);
				if (checkRet.length() > 0) {
					this.addFieldError("userFile", "导入错误,第" + (i + 3) + "行," + checkRet);
					return INPUT;
				}
			} else if (idCheck.equals("exit")) {
				// 已存在身份证,不验证手机号码
			} else {
				this.addFieldError("userFile", "导入错误,第" + (i + 3) + "行,身份证号码不正确");
				return INPUT;
			}
			String OpenBankId = datas[i][9];// 开户银行
			if (!bankIdMap.containsKey(OpenBankId)) {
				this.addFieldError("userFile", "导入错误,第" + (i + 3) + "行,开户银行不正确");
				return INPUT;
			}
			OpenBankId = bankIdMap.get(OpenBankId);
			String OpenProvId = datas[i][10];// 银行省份
			if (!prov.containsKey(OpenProvId)) {
				this.addFieldError("userFile", "导入错误,第" + (i + 3) + "行,开户银行省份不正确");
				return INPUT;
			}
			OpenProvId = prov.get(OpenProvId);
			String OpenAreaId = datas[i][11];// 银行城市
			if (!city.containsKey(OpenAreaId)) {
				this.addFieldError("userFile", "导入错误,第" + (i + 3) + "行,开户银行城市地区不正确");
				return INPUT;
			}
			OpenAreaId = city.get(OpenAreaId);

			Date now = new Date();
			Map<String, String> personMap = new HashMap<String, String>();
			String realName = datas[i][1]; // 客户名称
			String workPlace = datas[i][20];
			// 账户名hhn_加身份证后6位 密码为身份证后8位
			String password = "123456";
			String userName = "hhn_" + idNo.substring(idNo.length() - 8);
			String oldPass = password;
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim() + IConstants.PASS_KEY);
			}

			// t_user
			personMap.put("userName", userName);
			personMap.put("password", password);
			personMap.put("dealpwd", password); // 交易密码
			personMap.put("createTime", format.format(now)); // 创建时间
			personMap.put("authStep", "3"); // 认证步骤
			personMap.put("enable", "1"); // 启用
			personMap.put("mobilePhone", cellPhone);
			personMap.put("source", "0"); // 用户来源 0小贷公司导入
			personMap.put("oldPass", oldPass); // 原始密码,汇付注册时传过去
			users.add(personMap);

			// t_person
			personMap = new HashMap<String, String>();
			personMap.put("realName", realName);
			personMap.put("sex", datas[i][2]); // 性别
			personMap.put("age", datas[i][3]); // 年龄
			personMap.put("maritalStatus", datas[i][4]); // 婚姻状况
			personMap.put("dwell", datas[i][5]); // 居住情况
			personMap.put("highestEdu", datas[i][6]); // 学历
			personMap.put("creditCardSum", datas[i][7]); // 信用卡张数
			personMap.put("creditCardAmount", datas[i][8]); // 信用卡额度
			personMap.put("hasHousrseLoan", datas[i][31]); // 房贷
			personMap.put("hasCarLoan", datas[i][32]); // 车贷
			personMap.put("cellPhone", cellPhone); // 手机号码
			personMap.put("bankCard", datas[i][13]); // 银行卡号
			personMap.put("idNo", idNo); // 身份证号
			personMap.put("auditStatus", "3"); // 审核状态 3 审核通过
			personMap.put("personalHead", "images/index_46.jpg"); // 头像
			personMap.put("workCity", getNativePlaceId(workPlace).get("CityCode"));// 工作地
			personMap.put("workPro", getNativePlaceId(workPlace).get("ProCode"));// 工作地
			persons.add(personMap);

			// t_bankcard
			personMap = new HashMap<String, String>();
			personMap.put("OpenBankId", OpenBankId); // 开户银行代号
			personMap.put("OpenProvId", OpenProvId); // 银行省份
			personMap.put("OpenAreaId", OpenAreaId); // 银行城市
			personMap.put("cardUserName", realName); // 用户名
			personMap.put("bankName", datas[i][12]); // 开户行
			personMap.put("bankNo", datas[i][13]); // 卡号
			personMap.put("idNo", idNo); // 身份证
			personMap.put("cellPhone", cellPhone); // 手机号码
			banks.add(personMap);

			personMap.put("contactsPhone", datas[i][16]); // 联系人电话
			personMap.put("houseSum", datas[i][17]); // 房产总量
			personMap.put("carSum", datas[i][18]); // 车辆总量

			// t_workth
			personMap = new HashMap<String, String>();
			personMap.put("orgName", datas[i][19]); // 公司名称
			personMap.put("companyAddress", datas[i][20]); // 公司地址
			personMap.put("job", datas[i][21]); // 职位
			personMap.put("companyType", datas[i][22]); // 公司性质
			personMap.put("workYear", datas[i][23]); // 现单位工作年限
			personMap.put("companyLine", datas[i][24]); // 现单位工作年限
			personMap.put("monthlyIncome", datas[i][25]); // 月收入
			personMap.put("allowance", datas[i][26]); // 奖金/津贴
			personMap.put("otherIncome", datas[i][27]); // 其他收入
			personMap.put("incomeSum", datas[i][28]); // 收入合计
			personMap.put("alimony", datas[i][29]); // 生活费
			personMap.put("creditCardRepayment", datas[i][30]); // 信用卡还款
			personMap.put("paySum", datas[i][33]); // 支出合计
			personMap.put("informationSearch", datas[i][34]); // 信息查询结果
			personMap.put("auditStatus", "3"); // 工作信息认证状态 3 审核通过
			personMap.put("workCity", getNativePlaceId(workPlace).get("CityCode"));// 工作地
			personMap.put("workPro", getNativePlaceId(workPlace).get("ProCode"));// 工作地
			// 工作信息
			works.add(personMap);

			// t_borrow
			personMap = new HashMap<String, String>();
			personMap.put("purpose", "2"); // 借款目的 默认是2 资金周转
			personMap.put("imgPath", randomImg()); // 借款图片
			personMap.put("borrowAmount", datas[i][35]); // 借款金额
			personMap.put("deadline", datas[i][36]); // 借款期限
			personMap.put("borrowWay", IPersonListsConstants.getCUSTOMSTRValue(datas[i][43]) + ""); // 借款类型
			personMap.put("loanClerk", datas[i][38]); // 信贷员
			personMap.put("borrowTitle", datas[i][39]); // 借款标题
			personMap.put("paymentMode", IPersonListsConstants.getCUSTOMSTRValue(datas[i][40]) + ""); // 还款方式
			personMap.put("annualRate", datas[i][41]); // 年利率
			personMap.put("productType", datas[i][37]); // 产品类型
			personMap.put("moneyPurposes", datas[i][44]); // 资金用途
			personMap.put("dateOfAccepted", datas[i][45]); // 受理日期
			personMap.put("borrowStatus", 8 + "");// 借款导入之后进去复审复审中
			personMap.put("otherReoaymentAmount", datas[i][46]); // 其他贷款月还款
			personMap.put("opinion", datas[i][47]); // 业务初审意见

			// 筹款日期
			String RAISETERM = datas[i][42];
			if (RAISETERM.equals("") || RAISETERM == null) {
				RAISETERM = 7 + "";// 默认筹标期限7天
			}
			int raiseTerm = Convert.strToInt(RAISETERM, 7);
			personMap.put("raiseTerm", raiseTerm + ""); // 筹款期限 天
			personMap.put("excitationType", 1 + "");// 不设置奖励
			personMap.put("remainTimeStart", format.format(now));
			personMap.put("remainTimeEnd", format.format(DateUtil.dateAddDay(now, raiseTerm)));
			personMap.put("MINTENDEREDSUM", 50 + "");// 默认最低投标金额
			personMap.put("PUBLISHTIME", format.format(now)); // 发布日期
			personMap.get("number");

			// 查询标种类型
			Map<String, String> borrowTypeMap = getBorrowTypeMap(IPersonListsConstants.getCUSTOMSTRValue(datas[i][43]) + "");
			double frozenMargin = 0;
			frozenMargin = Convert.strToDouble(datas[i][35], 0) * Double.parseDouble(borrowTypeMap.get("all_frost_scale")) / 100;
			personMap.put("frozenMargin", frozenMargin + ""); // 冻结保证金
			personMap.put("nid_log", borrowTypeMap.get("identifier")); // 发布日期
			borrows.add(personMap);

			// personMap = new HashMap<String, String>();
			// //担保公司加载
			// personMap.put("COMPANYNAME", datas[i][42]);
			// personMap.put("ISSHOW", "1");
			// System.out.println(datas[i][42]);
			// companyList.add(personMap);
		}
		List<String> resultList = null;
		try {
			resultList = userService.addImportUser(users, persons, works, banks, borrows);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (resultList != null && resultList.size() > 0) {
			request().setAttribute("resultList", resultList);
			return INPUT;
		} else {
			this.addActionMessage("导入成功!");
			return SUCCESS;
		}
	}

	/** 查询标种类型 **/
	private Map<String, String> getBorrowTypeMap(String type) {
		String nid = "";
		if (IConstants.BORROW_TYPE_NET_VALUE.equals(type)) {
			// 薪金贷
			nid = BorrowType.WORTH.getValue();
		} else if (IConstants.BORROW_TYPE_SECONDS.equals(type)) {
			// 彩生活
			nid = BorrowType.SECONDS.getValue();
		} else if (IConstants.BORROW_TYPE_GENERAL.equals(type)) {
			// 业主贷
			nid = BorrowType.ORDINARY.getValue();
		}
		session().setAttribute("nid", nid);
		try {
			return shoveBorrowTypeService.queryShoveBorrowTypeByNid(nid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HashMap<String, String>();
	}

	/**
	 * 查询地区列表
	 * */
	public void getNativePlaceCity() {
		if (city == null || prov == null || city.size() == 0 || prov.size() == 0) {
			try {
				regcityList = regionService.queryRegionListHHN(-1L, -1L, 0);
				for (Map<String, Object> map : regcityList) {
					if ("2".equals(map.get("regionType") + "")) {// 市
						city.put(map.get("regionName") + "", map.get("regionId") + "");
					} else {// 省
						prov.put(map.get("regionName") + "", map.get("regionId") + "");
					}
				}
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取城市 和省份 对应的ID 匹配不到 返回值为 1 中国
	 * */
	public Map<String, String> getNativePlaceId(String key) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("ProCode", "1");
		resultMap.put("CityCode", "1");
		if (regioncity == null) {
			return resultMap;
		}
		for (Map<String, Object> map : regioncity) {
			String city = (String) map.get("regionName");
			if (key.equals(city)) {
				resultMap.put("ProCode", map.get("parentId").toString());
				resultMap.put("CityCode", map.get("regionId").toString());
				break;
			}
		}
		return resultMap;
	}

	/**
	 * 随机取得借款默认图片
	 * 
	 * @return
	 */
	public String randomImg() {
		String[] imgArr = { "images/random/1.jpg", "images/random/2.jpg", "images/random/3.jpg", "images/random/4.jpg", "images/random/5.jpg",
				"images/random/6.jpg" };
		int index = (int) (Math.random() * imgArr.length);
		return imgArr[index];
	}

	/**
	 * 現下充值跳转委托書条款
	 * 
	 * @MethodName: querytipsM
	 * @Param: UserAction
	 * @Author: gang.lv
	 * @Date: 2013-5-11 下午02:38:52
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public String queryRecharTips() throws SQLException, DataException {
		paramMap = agreementService.getAgreementDetail(1, 1);
		return SUCCESS;
	}

	/**
	 * 清空session中的验证码
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String removeSessionCode() throws SQLException, DataException {
		session().removeAttribute("randomCode");
		session().removeAttribute("phone");
		log.info("注册手机验证码已清除");
		return null;
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

	public RegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Map<String, Object>> getRegcityList() {
		return regcityList;
	}

	public void setRegcityList(List<Map<String, Object>> regcityList) {
		this.regcityList = regcityList;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public JobTaskService getJobTaskService() {
		return jobTaskService;
	}

	public void setJobTaskService(JobTaskService jobTaskService) {
		this.jobTaskService = jobTaskService;
	}

	public File getUserFile() {
		return userFile;
	}

	public void setUserFile(File userFile) {
		this.userFile = userFile;
	}

	public String getUserFileName() {
		return userFileName;
	}

	public void setUserFileName(String userFileName) {
		this.userFileName = userFileName;
	}

	public String getUserFileContentName() {
		return userFileContentName;
	}

	public void setUserFileContentName(String userFileContentName) {
		this.userFileContentName = userFileContentName;
	}

	public String queryBaseMsgInit() {
		request().setAttribute("userId", request("uid"));
		return SUCCESS;
	}

}
