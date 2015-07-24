/**  
 * @Project: hehenian-mobile
 * @Package com.hehenian.mobile.web.controller.product
 * @Title: ProductController.java
 * @Description: 产品
 *
 * @author: zhanbmf
 * @date 2015-3-30 下午3:58:19
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.mobile.web.controller.product;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hehenian.agreement.common.share.FileClient;
import com.hehenian.biz.common.account.IPersonService;
import com.hehenian.biz.common.account.IUserInfoService;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.IUserThirdPartyService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.InviteCodeDo;
import com.hehenian.biz.common.account.dataobject.LoginInfoRelate;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.account.dataobject.UserThirdPartyDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.colorlife.ColorLifeBuyService;
import com.hehenian.biz.common.identifycode.IIdentifyCodeService;
import com.hehenian.biz.common.wygj.ILotteryService;
import com.hehenian.biz.common.wygj.IOffsetService;
import com.hehenian.biz.common.wygj.IParkingFeeService;
import com.hehenian.biz.common.wygj.IPropertyManagementFeeService;
import com.hehenian.biz.common.wygj.SysCodeService;
import com.hehenian.biz.common.wygj.dataobject.LotteryInfo;
import com.hehenian.biz.common.wygj.dataobject.LotteryPrize;
import com.hehenian.biz.common.wygj.dataobject.OffsetDetailVo;
import com.hehenian.biz.common.wygj.dataobject.OffsetRecordsDo;
import com.hehenian.biz.common.wygj.dataobject.ParkingDetailDo;
import com.hehenian.biz.common.wygj.dataobject.ParkingFeeDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementDetailDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementFeeDo;
import com.hehenian.common.annotations.RequireLogin;
import com.hehenian.common.constants.HHNConstants;
import com.hehenian.common.session.SessionProvider;
import com.hehenian.common.session.cache.SessionCache;
import com.hehenian.common.utils.CommonUtil;
import com.hehenian.common.utils.ResponseUtils;
import com.hehenian.mobile.common.constants.WebConstants;
import com.hehenian.mobile.common.utils.ColorLifeUtils;
import com.hehenian.mobile.common.utils.CommonUtils;
import com.hehenian.mobile.web.controller.BaseController;
import com.hhn.hessian.invest.IInvestProductService;
import com.hhn.pojo.ProductRate;
import com.hhn.util.BaseReturn;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private IInvestProductService investProductService;
	@Autowired
	private IUserService userService;
	@Resource
	private SessionCache sessionCache;
	@Resource
	private SessionProvider session;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IUserThirdPartyService userThirdPartyService;
	@Autowired
	private ColorLifeBuyService colorLifeBuyService;
	@Autowired
	private IIdentifyCodeService identifyCodeService;
	@Autowired
	private ILotteryService lotteryService;
	
	@Autowired
	private IParkingFeeService parkingFeeService;
	@Autowired
	private IPropertyManagementFeeService manageFeeService;
	@Autowired
	private SysCodeService sysCodeService;
	
    @Autowired
    private IOffsetService offsetService;
	
    /**
	 * 落地页channel==1国际物业落地页 : 2表示彩管家落地页
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "index")
	public String gj(HttpServletRequest request, HttpServletResponse response) {
		int channel = CommonUtils.getChannel(request);
		String root = session.getSessionId(request, response);
		sessionCache.setAttribute(root, HHNConstants.CHANNEL, channel,
				HHNConstants.SESSION_CACHE_TIME);
		request.getSession().setAttribute(HHNConstants.CHANNEL, channel);
		// 存入cookie
		Cookie sessionCookie = new Cookie(HHNConstants.CHANNEL,
				String.valueOf(channel));
		sessionCookie.setPath("/");
		response.addCookie(sessionCookie);
		request.setAttribute(WebConstants.CHANNEL_NAME,
				CommonUtils.getChannelName(request));
		// CookieUtils.addCookie(response, HHNConstants.CHANNEL,
		// String.valueOf(channel), "/", ".hehenian.com", -1);
		String mobilePhone = request.getParameter("mobilePhone");
		request.getSession().setAttribute("mobilePhone", mobilePhone);
		if (1 == channel) {
			return "product/productGJWY";
		} else if (2 == channel) {
			Integer loginUserId = super.getCurrentUserId();
			AccountUserDo user = null;
			PersonDo person = null;
			String sign = request.getParameter("sign");
			String oa = request.getParameter("oa");
			UserThirdPartyDo utp = null;
			if (StringUtils.isNotBlank(sign) && StringUtils.isNotBlank(oa)) {
				String realName = null;
				try {
					// realName = new
					// String(ObjectUtils.toString(request.getParameter("realName")).getBytes("iso8859-1"),"utf-8");
					realName = URLDecoder.decode(ObjectUtils.toString(request
							.getParameter("realName")), "utf-8");
					// oa = new
					// String(ObjectUtils.toString(request.getParameter("oa")).getBytes("iso8859-1"),"utf-8");
					oa = URLDecoder.decode(
							ObjectUtils.toString(request.getParameter("oa")),
							"utf-8");

					System.out.println("realName1::"
							+ request.getParameter("realName"));
					System.out.println("realName2::"
							+ URLDecoder.decode(ObjectUtils.toString(request
									.getParameter("realName")), "utf-8"));
					System.out.println("realName3::" + realName);
					System.out.println("realName4::"
							+ new String(ObjectUtils.toString(request
									.getParameter("realName"))));
					System.out.println("realName::" + realName);
					System.out.println("oa:::" + oa);

					request.getSession().setAttribute("sign", sign);
					request.getSession().setAttribute("oa", oa);
					sessionCache.setAttribute(root, "oa", oa,
							HHNConstants.SESSION_CACHE_TIME);
					sessionCache.setAttribute(root, "mobilePhone", mobilePhone,
							HHNConstants.SESSION_CACHE_TIME);
					String appkey = request.getParameter("appkey");
					if (WebConstants.CGJ_APP_KEY.equals(appkey)) {
						String MD5Parm = DigestUtils
								.md5Hex(WebConstants.CGJ_PASS_KEY + channel
										+ mobilePhone + oa + realName);
						if (!MD5Parm.equals(sign)) {
							return "redirect:/login/index.do";
						}
					}
				} catch (UnsupportedEncodingException e1) {
					logger.error("oa账号转码出错");
					e1.printStackTrace();
				}
				utp = userThirdPartyService.getByTheThirdUserName(oa);
				request.getSession().setAttribute("utp", utp);
				sessionCache.setAttribute(root, "utp", utp,
						HHNConstants.SESSION_CACHE_TIME);
				if (utp != null) {
					user = userService.getById(utp.getUserId().longValue());
					person = personService.getByUserId(utp.getUserId()
							.longValue());
					request.getSession().setAttribute("user", user);
					sessionCache.setAttribute(root, "user", user,
							HHNConstants.SESSION_CACHE_TIME);
					if (person.getAuditStatus() == 3) {
						return "redirect:/product/plist.do";
					} else {
						// 如果用户存在但是没有实名认证成功
						return "redirect:/account/realAuth.do";
					}
				} else {
					// 第一步通过手机号验证用户是否已经在合和年注册,如果没有注册转到实名认证
					if (!StringUtils.isNotBlank(mobilePhone)) {
						return "redirect:/login/index.do";
					}
					user = userService.findUserByPhone(mobilePhone);
					person = personService.getByUserCellPhone(mobilePhone);
					if (user == null) {
						return "redirect:/product/linkIdentifyCode.do";
					} else {
						// request.getSession().setAttribute(HHNConstants.SESSION_INFO,
						// user);
						// // 取缓存登录信息
						// sessionCache.setAttribute(root,
						// HHNConstants.SESSION_INFO, user,
						// HHNConstants.SESSION_CACHE_TIME);
						return "redirect:/product/linkPassWord.do";
					}
				}

			}
			// to do...
			return "redirect:/product/plist.do";
		}else if(channel == 0){//官网
			return "product/productGW";
		}
		return "product/productGJWY";
		// return "redirect:/login/index.do";
	}

	/**
	 * +多宝
	 */
	@RequestMapping(value = "phb")
	public String phb() {
		return "product/productHB";
	}

	/**
	 * +多宝
	 * @Description: 产品列表页
	 * @param request
	 * @param response
	 * @author: jiangwmf
	 * @date 2015-4-23 下午09:02:27
	 */
	@RequestMapping(value = "pjd")
	public String pjd(HttpServletRequest request,HttpServletResponse response){
		int channel = CommonUtils.getChannel(request);
		int subChannel = NumberUtils.toInt(request.getParameter("subChannel"), 0);
		
		Map<String,Object> channelMap = new HashMap<String, Object>();
		channelMap.put("channel", channel);
		channelMap.put("sub_channel", subChannel);
		
		
		BaseReturn baseReturn = investProductService.getProductRateListByChannel(channelMap);
		if (baseReturn.getReturnCode() == 0 && baseReturn.getData() != null) {
			request.setAttribute("result", baseReturn.getData());
		}

		return "product/productJD";
	}

	/**
	 * +车宝
	 * @Description: 产品列表页
	 * @param request
	 * @param response
	 * @author: jiangwmf
	 * @date 2015-4-27 下午09:02:27
	 */
	@RequestMapping(value = "pjc")
	public String pjc(HttpServletRequest request,HttpServletResponse response){
		return "product/productJC";
	}
	
	/**
	 * @Description: 产品列表页
	 * @param request
	 * @param response
	 * @author: zhanbmf
	 * @date 2015-3-30 下午4:32:27
	 */
	@RequestMapping(value = "plist")
	public String plist(HttpServletRequest request, HttpServletResponse response) {
		// 是否是彩生活渠道channel=1彩生活渠道|0官方渠道 物业国际普通通道 员工通道
		// 根据渠道判断是国际物业还是彩管家
		int channel = CommonUtils.getChannel(request);
		int subChannel = NumberUtils.toInt(request.getParameter("subChannel"),
				0);

		long userId = super.getUserId();
		Map<String,Object> channelMap = new HashMap<String, Object>();
		channelMap.put("channel", channel);
		channelMap.put("sub_channel", subChannel);

		// 如果是物业国际员工通道channel = 1(物业国际) | subChannel = 1(员工通道)，则要检查验证码 to do...
		if (channel == 1 && subChannel == 1 && userId > 0) {
			InviteCodeDo inviteDO = userService
					.findInviteCodeByDO(new InviteCodeDo(userId));
			// 如果不是员工
			if (inviteDO == null) {
				return "redirect:/account/invite.do";
			}
		}

		if(2 == channel){//红包理财

			channelMap.put("channel", 2);
			channelMap.put("sub_channel", 0);
		}

		BaseReturn baseReturn = investProductService
				.getProductRateListByChannel(channelMap);
		if (baseReturn.getReturnCode() == 0 && baseReturn.getData() != null) {
			request.setAttribute("result", baseReturn.getData());
		}

		if(2 == channel){//红包理财
			Integer userid = new Long(userId).intValue();
			UserThirdPartyDo upo = userThirdPartyService.getByUserId(userid);
			if (upo != null) {
				String oauser = upo.getThethirdusername();
				System.out.println("oauser::::" + oauser);
				// 余额
				Float balance = ColorLifeUtils.getEmpBalance(oauser);
				request.setAttribute("balance", new BigDecimal(balance));
				// 投资金额
				BigDecimal principal = colorLifeBuyService
						.queryInvestment(userid);
				// 待收收益
				BigDecimal interested = colorLifeBuyService
						.queryInterested(userid);
				// 累计收益
				BigDecimal interest = colorLifeBuyService.queryInterest(userid);
				request.setAttribute("principal", principal);
				request.setAttribute("interested", interested);
				request.setAttribute("interest", interest);
			}

			return "product/productHB";
		}
		
		if(channel == 1 && subChannel == 2){//多宝
			return "product/productJD";
		}else if(channel == 1 && subChannel == 3){//车宝
			return "product/productJC";
		}

		request.setAttribute(HHNConstants.CHANNEL, channel);
		return "product/productList";//薪宝
	}
	
	/**
	 * 产品详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author: zhanbmf
	 * @date 2015-3-30 下午4:37:58
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String pdetail(HttpServletRequest request,
			HttpServletResponse response) {
		int channel = CommonUtils.getChannel(request);
		int pid = NumberUtils.toInt(request.getParameter("pid"), -1);
		if (pid < 0) {
			return "redirect:/product/plist.do";
		}

		// 获取登录情况
		request.setAttribute("isLogin", super.getUserId() > 0);
		BaseReturn baseReturn = investProductService.getProductRateById(pid);
		if (baseReturn.getReturnCode() == 0 && baseReturn.getData() != null) {
			request.setAttribute("result", baseReturn.getData());
		} else {
			return "redirect:/product/plist.do";
		}

		// 获取近期交易记录
		Map<String,Object> qMap = new HashMap<String,Object>();
		qMap.put("rate_id", pid);
		qMap.put("beginDate", DateFormatUtils.format(
				DateUtils.addDays(new Date(), -3), "yyyyMMdd"));
		qMap.put("trade_type", "INVESTMENT");
		qMap.put("channel", channel);
		qMap.put("numNow", 0);
		qMap.put("pageSize", 10);
		BaseReturn recentData = investProductService.getRecentList(qMap);
		if (recentData.getReturnCode() == 0 && recentData.getData() != null) {
			List list = (List) recentData.getData();
			for (int i = 0; i < list.size(); i++) {
				Map item = (Map) list.get(i);
				item.put("cellPhone", CommonUtil.encodeM(ObjectUtils
						.toString(item.get("cellPhone"))));
				item.put("trade_time", DateFormatUtils.format(
						(Date) item.get("trade_time"), "yyyy-MM-dd HH:mm"));
			}

			request.setAttribute("recentList", list);
		}

		return "product/productDetail";
	}

	/**
	 * 查询默认冲抵物业费地址和停车费车牌信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequireLogin
	@RequestMapping(value = "queryDefaultOffset")
	public String queryDefaultOffset(HttpServletRequest request,HttpServletResponse response) {
		long userId = super.getUserId();
		int pid = NumberUtils.toInt(request.getParameter("pid"), -1);
		//检查是否存在冲抵物业费地址和停车费车牌信息
		//通过用户id和产品pid 查询
		BaseReturn baseReturn = investProductService.getProductRateById(pid);
		if(baseReturn.getReturnCode() != 0 || baseReturn.getData() == null) {
			return "redirect:/product/plist.do";
		}
		
		ProductRate pr = (ProductRate)baseReturn.getData();
		request.setAttribute("product", pr);
		if(pr.getSub_channel()==2){//多宝
			PropertyManagementDetailDo offset=  manageFeeService.getDefaultByUserId(new Long(userId).intValue());
			if(offset !=null) {
				//1.第一种方案，选择默认的冲抵信息，跳转到购买页 
				try {
					//购买的产品id、冲抵信息id
					response.sendRedirect("/finance/prepayCB.do?pid="+pid+"&offsetId="+offset.getId());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}else{
				try {
					//购买的产品id、冲抵信息id
					response.sendRedirect("/product/setOffset.do?from=0&pid="+pid);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		
		}else if(pr.getSub_channel()==3){//车宝
			//选择默认地址
			ParkingDetailDo offset = parkingFeeService.getDefaultByUserId(new Long(userId).intValue());
			if(offset !=null) {
				//1.第一种方案，选择默认的冲抵信息，跳转到购买页 
				try {
					//购买的产品id、冲抵信息id
					response.sendRedirect("/finance/prepayCB.do?pid="+pid+"&offsetId="+offset.getId());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}else{
				try {
					//购买的产品id、冲抵信息id
					response.sendRedirect("/product/setOffset.do?from=0&pid="+pid);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		}
		return null;
	}

	/**
	 * 添加冲抵信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequireLogin
	@RequestMapping(value = "setOffset")
	public String setOffset(HttpServletRequest request,HttpServletResponse response) {
		//from: 
		//0:立即购买-设置默认冲抵信息    
		//1：我的账户-增加冲抵信息
		//2: 购买修改-增加冲抵信息
		int pid = NumberUtils.toInt(request.getParameter("pid"), -1);
		BaseReturn baseReturn = investProductService.getProductRateById(pid);
		if(baseReturn.getReturnCode() != 0 || baseReturn.getData() == null) {
			return "redirect:/product/plist.do";
		}
		
		ProductRate pr = (ProductRate)baseReturn.getData();
		request.setAttribute("product", pr);
		String from = request.getParameter("from");
		request.setAttribute("pid", pid);
		request.setAttribute("from", from);
		 return "product/productOffsetInfoSet";
	}
	
	/**
	 * 设置默认冲抵停车费信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequireLogin
	@RequestMapping(value = "addOffset")
	public void addOffset(HttpServletRequest request,HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		int pid = NumberUtils.toInt(request.getParameter("pid"), -1);
		long userId = super.getUserId();
		request.getSession().setAttribute("pid", pid);
		long community = request.getParameter("community")==null?-1L:Long.parseLong(request.getParameter("community"));//小区编号
		String plateNo= request.getParameter("plateNo");//停车卡编号
		
		String building = request.getParameter("building");
		String roomno = request.getParameter("roomno");
		String theowner = request.getParameter("theOwner");
		
		BaseReturn baseReturn = investProductService.getProductRateById(pid);
		if(baseReturn.getReturnCode() != 0 || baseReturn.getData() == null) {
			jsonObject.put("result", -1);
			ResponseUtils.renderText(response, null, jsonObject.toString());
			return;
		}
		
		ProductRate pr = (ProductRate)baseReturn.getData();
		request.setAttribute("product", pr);
		
		if(pr.getSub_channel()==3){//车宝
			//通过小区编号和停车卡号去基础表 查询
			//检查冲抵信息基础表中是否存在设置的信息，
			ParkingFeeDo  baseData = parkingFeeService.getByParams(community,plateNo);
			if(baseData!=null){
				//校验改地址物业费是否为0
				if(baseData.getParking_fee()==0){
					jsonObject.put("result", 3);
					ResponseUtils.renderText(response, null, jsonObject.toString());
					return;
				}
				ParkingDetailDo parkingFeeDetail = new ParkingDetailDo();
				//查询该用户下是否存在该产品的冲抵信息
				parkingFeeDetail.setPlate_number(plateNo);
				parkingFeeDetail.setMainaddressid(community);
				
				//校验是否已经设置过该地址
				ParkingDetailDo p = parkingFeeService.getDetailByParams(community, plateNo,new Long(userId).intValue());
				if(p!=null){
					jsonObject.put("result", 4);
					ResponseUtils.renderText(response, null, jsonObject.toString());
					return;
				}
				//校验改地址是否已经参加活动
				OffsetRecordsDo offset = offsetService.getParkingOffsetJoinEndDate(community, plateNo);
				if(offset!=null){//存在则通过detailid查询offsetrecords
					jsonObject.put("result", 2);
					jsonObject.put("enddate", offset.getEnddate());
					ResponseUtils.renderText(response, null, jsonObject.toString());
					return;
				}
				
				parkingFeeDetail.setTheowner(baseData.getTheowner());
				parkingFeeDetail.setUser_id(new Long(userId).intValue());
				parkingFeeDetail.setThe_arage_type(baseData.getThe_arage_type());
				parkingFeeDetail.setCar_emissions(baseData.getCar_emissions());
				parkingFeeDetail.setInfotype(0);//默认未冲抵
				
				int count = parkingFeeService.getParkingDetailCounts(new Long(userId).intValue());
				//校验通过则增加记录
				if(count > 0){
					parkingFeeDetail.setDefaultset(0);
				}else{//默认
					parkingFeeDetail.setDefaultset(1);
				}
				int num = parkingFeeService.insertParkingDetail(parkingFeeDetail); 
				if(num>0){
					jsonObject.put("result", 0);//插入成功
					ResponseUtils.renderText(response, null, jsonObject.toString());
					return;
				}else{
					jsonObject.put("result", -1);//添加冲抵停车费信息失败
					ResponseUtils.renderText(response, null, jsonObject.toString());
					return;
				}
			}else{
				jsonObject.put("result", 1);//冲抵信息错误，在基础表中找不到
				ResponseUtils.renderText(response, null, jsonObject.toString());
				return;
			}
			
		}else if(pr.getSub_channel()==2){
			//+多宝
			PropertyManagementFeeDo  baseData = manageFeeService.getByParams(community,building,roomno,theowner);
			if(baseData!=null){
				//校验改地址物业费是否为0
				if(baseData.getProperty_fee()==0){
					jsonObject.put("result", 3);
					ResponseUtils.renderText(response, null, jsonObject.toString());
					return;
				}
				
				PropertyManagementDetailDo propertyManageFeeDetail = new PropertyManagementDetailDo();
				
				propertyManageFeeDetail.setMainaddressid(community);
				propertyManageFeeDetail.setBuilding(building);
				propertyManageFeeDetail.setRoomnum(roomno);
				
				//校验是否已经设置过该地址
				PropertyManagementDetailDo p = manageFeeService.getDetailByParams(community,roomno ,new Long(userId).intValue());
				if(p!=null){
					jsonObject.put("result", 4);
					ResponseUtils.renderText(response, null, jsonObject.toString());
					return;
				}
				
				//校验改地址是否已经参加活动
				OffsetRecordsDo offset = offsetService.getManageOffsetJoinEndDate(community, roomno);
				if(offset!=null){//存在则通过detailid查询offsetrecords
					jsonObject.put("result", 2);
					jsonObject.put("enddate", offset.getEnddate());
					ResponseUtils.renderText(response, null, jsonObject.toString());
					return;
				}
				propertyManageFeeDetail.setTheowner(baseData.getTheowner());
				propertyManageFeeDetail.setUser_id(new Long(userId).intValue());
				propertyManageFeeDetail.setInfotype(0);//默认未冲抵
				
				//查询该用户下是否存在该产品的冲抵信息
				int count = manageFeeService.getPropertyManagementCounts(new Long(userId).intValue());
				//校验通过则增加记录
				if(count > 0){
					propertyManageFeeDetail.setDefaultset(0);
				}else{//默认
					propertyManageFeeDetail.setDefaultset(1);
				}
				int num = manageFeeService.insertManageDetail(propertyManageFeeDetail); 
				if(num>0){
					jsonObject.put("result", 0);//插入成功
					ResponseUtils.renderText(response, null, jsonObject.toString());
					return;
				}else{
					jsonObject.put("result", 2);//添加冲抵物业费信息失败
					ResponseUtils.renderText(response, null, jsonObject.toString());
					return;
				}
			}else{
				jsonObject.put("result", 1);//冲抵信息错误，在基础表中找不到
				ResponseUtils.renderText(response, null, jsonObject.toString());
				return;
			}
			
		}
		
	}
		
	/**
	 * 购买-修改-冲抵信息管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequireLogin
	@RequestMapping(value = "managerOffset")
	public String managerOffset(HttpServletRequest request,HttpServletResponse response) {
		long userId = super.getUserId();
		int pid = NumberUtils.toInt(request.getParameter("pid"), -1);
		BaseReturn baseReturn = investProductService.getProductRateById(pid);
		if(baseReturn.getReturnCode() != 0 || baseReturn.getData() == null) {
			return "redirect:/product/plist.do";
		}
		
		ProductRate pr = (ProductRate)baseReturn.getData();
		request.setAttribute("product", pr);
		if(pr.getSub_channel()==3){
			//根据用户id、产品id查询冲抵信息
			List<ParkingDetailDo>  offsetList = parkingFeeService.listParkingDetailsByUserId(new Long(userId).intValue());
			List<OffsetDetailVo> l = new ArrayList<OffsetDetailVo>();
			for (ParkingDetailDo p:offsetList) {
				OffsetDetailVo pdv = new OffsetDetailVo();
				pdv.setId(p.getId());
				pdv.setMainaddressid(p.getMainaddressid());
				pdv.setPlate_number(p.getPlate_number());
				pdv.setThe_arage_type(p.getThe_arage_type());
				pdv.setInfotype(p.getInfotype());
				pdv.setUser_id(p.getUser_id());
				pdv.setDefaultset(p.getDefaultset());
				pdv.setCar_emissions(p.getCar_emissions());
				pdv.setTheowner(p.getTheowner());
				pdv.setAddressDesc(sysCodeService.getByCommunityCode(p.getMainaddressid().toString()).replace(",",""));
				l.add(pdv);
			}
			request.setAttribute("pid", pid);
	        request.setAttribute("offsetList", l);
	        request.setAttribute("referer", request.getHeader("referer"));
			//return "product/buyOffset";
	        return "product/productOffsetManage";
		}else if(pr.getSub_channel()==2){
			//根据用户id、产品id查询冲抵信息
			List<PropertyManagementDetailDo>  offsetList = manageFeeService.listPropertyManagementsByUserId(new Long(userId).intValue());
			List<OffsetDetailVo> l = new ArrayList<OffsetDetailVo>();
			for (PropertyManagementDetailDo p:offsetList) {
				OffsetDetailVo pdv = new OffsetDetailVo();
				pdv.setId(p.getId());
				pdv.setMainaddressid(p.getMainaddressid());
				pdv.setBuilding(p.getBuilding());
				pdv.setRoomnum(p.getRoomnum());
				pdv.setInfotype(p.getInfotype());
				pdv.setUser_id(p.getUser_id());
				pdv.setDefaultset(p.getDefaultset());
				pdv.setTheowner(p.getTheowner());
				pdv.setAddressDesc(sysCodeService.getByCommunityCode(p.getMainaddressid().toString()).replace(",",""));
				l.add(pdv);
			}
			request.setAttribute("pid", pid);
	        request.setAttribute("offsetList", l);
	        request.setAttribute("referer", request.getHeader("referer"));
			//return "product/buyOffset";
	        return "product/productOffsetManage";
		}else
			return "redirect:/product/plist.do";
	}
	
	/**
	 * 设置默认冲抵信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "changeDefaultOffset")
	@Deprecated
	public void changeDefaultOffset(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonObject = new JSONObject();
		long userId = super.getUserId();
        String plateNo = request.getParameter("plateNo");
        
        map.put("userId", String.valueOf(userId));
        map.put("plateNo", plateNo);
		//根据用户id、产品id查询冲抵信息
        int count = parkingFeeService.updateDefaultByPlateNo(new Long(userId).intValue(),plateNo);
        if(count > 0){
        	jsonObject.put(0, "success");//成功
        }else{
        	jsonObject.put(1, "fail");//
        }
		ResponseUtils.renderJson(response, ResponseUtils.UTF8, jsonObject.toString());
	}
	
	@RequestMapping(value = "checkOffsetJoin")
	public void checkOffsetJoin(HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		int pid = NumberUtils.toInt(request.getParameter("pid"), -1);
		BaseReturn baseReturn = investProductService.getProductRateById(pid);
		if(baseReturn.getReturnCode() != 0 || baseReturn.getData() == null) {
			jsonObject.put("result", -1);
		}
		
		ProductRate pr = (ProductRate)baseReturn.getData();
		request.setAttribute("product", pr);
		
		String roomOrPlateNo = request.getParameter("roomOrPlateNo");
		long community = request.getParameter("community")==null?-1L:Long.parseLong(request.getParameter("community"));//小区编号
		
		
		if(pr.getChannel()==1&&pr.getSub_channel()==3){
			OffsetRecordsDo offset = offsetService.getParkingOffsetJoinEndDate(community, roomOrPlateNo);
			if(offset!=null){//存在则通过detailid查询offsetrecords
				jsonObject.put("result", 1);
				jsonObject.put("enddate", offset.getEnddate());
				ResponseUtils.renderText(response, null, jsonObject.toString());
				return;
			}
		}else if(pr.getChannel()==1&&pr.getSub_channel()==2){
			//校验改地址是否已经参加活动
			OffsetRecordsDo offset = offsetService.getManageOffsetJoinEndDate(community, roomOrPlateNo);
			if(offset!=null){//存在则通过detailid查询offsetrecords
				jsonObject.put("result", 1);
				jsonObject.put("enddate", offset.getEnddate());
				ResponseUtils.renderText(response, null, jsonObject.toString());
				return;
			}
		}
		jsonObject.put("result", 0);
		ResponseUtils.renderText(response, null, jsonObject.toString());
		return;
	}
	
	/**
	 * 我的账户-查询冲抵信息列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequireLogin
	@RequestMapping(value = "queryOffsetList")
	public String queryOffsetList(HttpServletRequest request,HttpServletResponse response) {
		long userId = super.getUserId();
		int pid = NumberUtils.toInt(request.getParameter("pid"), -1);
		//区分渠道：我的账户-列表页发起：1，默认列表页发起：0
		BaseReturn baseReturn = investProductService.getProductRateById(pid);
		if(baseReturn.getReturnCode() != 0 || baseReturn.getData() == null) {
			return "redirect:/product/plist.do";
		}
		
		ProductRate pr = (ProductRate)baseReturn.getData();
		request.setAttribute("product", pr);
		if(pr.getSub_channel()==3){
			//通过用户id，产品id查询所有的冲抵信息
			List<ParkingDetailDo>  offsetData = parkingFeeService.listParkingDetailsByUserId(new Long(userId).intValue());
			List<OffsetDetailVo> l = new ArrayList<OffsetDetailVo>();
			for (ParkingDetailDo p:offsetData) {
				OffsetDetailVo pdv = new OffsetDetailVo();
				pdv.setId(p.getId());
				pdv.setMainaddressid(p.getMainaddressid());
				pdv.setPlate_number(p.getPlate_number());
				pdv.setThe_arage_type(p.getThe_arage_type());
				pdv.setInfotype(p.getInfotype());
				pdv.setUser_id(p.getUser_id());
				pdv.setDefaultset(p.getDefaultset());
				pdv.setCar_emissions(p.getCar_emissions());
				pdv.setTheowner(p.getTheowner());
				pdv.setAddressDesc(sysCodeService.getByCommunityCode(p.getMainaddressid().toString()).replace(",",""));
				l.add(pdv);
			}
			request.setAttribute("data", l);
			request.setAttribute("pid",pid);
			 return "product/productOffsetList";
		}else if(pr.getSub_channel()==2){
			//通过用户id，产品id查询所有的冲抵信息
			List<PropertyManagementDetailDo>  offsetData = manageFeeService.listPropertyManagementsByUserId(new Long(userId).intValue());
			List<OffsetDetailVo> l = new ArrayList<OffsetDetailVo>();
			for (PropertyManagementDetailDo p:offsetData) {
				OffsetDetailVo pdv = new OffsetDetailVo();
				pdv.setId(p.getId());
				pdv.setMainaddressid(p.getMainaddressid());
				pdv.setInfotype(p.getInfotype());
				pdv.setUser_id(p.getUser_id());
				pdv.setDefaultset(p.getDefaultset());
				pdv.setTheowner(p.getTheowner());
				pdv.setBuilding(p.getBuilding());
				pdv.setRoomnum(p.getRoomnum());
				pdv.setAddressDesc(sysCodeService.getByCommunityCode(p.getMainaddressid().toString()).replace(",",""));
				l.add(pdv);
			}
			request.setAttribute("data", l);
			request.setAttribute("pid",pid);
			 return "product/productOffsetList";
		}else{
			return null;
		}
	}
	
	/**
	 * 删除冲抵信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequireLogin
	@RequestMapping(value = "deleteOffset")
	public void deleteOffset(HttpServletRequest request,HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		int id = Integer.parseInt(request.getParameter("id"));//冲抵id
		int pid = NumberUtils.toInt(request.getParameter("pid"), -1);
		BaseReturn baseReturn = investProductService.getProductRateById(pid);
		if(baseReturn.getReturnCode() != 0 || baseReturn.getData() == null) {
			jsonObject.put("result", "1");//
		}
		
		ProductRate pr = (ProductRate)baseReturn.getData();
		request.setAttribute("product", pr);
		//根据用户id、产品id、小区编号、房间编号删除冲抵信息
		int count = -1;
		if(pr.getChannel()==1 && pr.getSub_channel()==3){
			count = parkingFeeService.deleteParkingDetailById(id);
		}else if(pr.getChannel()==1 && pr.getSub_channel()==2){
			count = manageFeeService.deleteManagerDetailById(id);
		}
		if(count>0){
			jsonObject.put("result", "0");//成功
		}else{
			jsonObject.put("result", "1");//
		}
		ResponseUtils.renderText(response, null, jsonObject.toString());
	}
	
	@RequireLogin
	@RequestMapping(value = "queryAgreement")
	@ResponseBody
	public void getTradeList(HttpServletRequest request,
			HttpServletResponse respones) {
		long userId = getUserId();
		String fileName = request.getParameter("fileName");

		if (StringUtils.isBlank(fileName)) {
			return;
		}

		FileClient fc = new FileClient();
		byte[] file = fc.getAgreement(userId, fileName);
		// String downFileName = "hhn_" +
		// RandomStringUtils.randomAlphanumeric(10) + ".pdf";
		try {
			respones.setContentType("application/pdf");
			// respones.setHeader("content-disposition", "attachment; filename="
			// + downFileName);
			respones.getOutputStream().write(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String MD5Parm = DigestUtils.md5Hex(WebConstants.CGJ_PASS_KEY
				+ "213800138001test2张张");
		System.out.println(MD5Parm);
	}

	@RequestMapping(value = "linkPassWord")
	public String linkPassWord(HttpServletRequest request,
			HttpServletResponse response) {
		// String oa = (String) request.getSession().getAttribute("oa");
		// UserThirdPartyDo utd =
		// userThirdPartyService.getByTheThirdUserName(oa);
		// if(utd == null){
		//
		// }
		String root = session.getSessionId(request, response);
		/*
		 * request.getSession().setAttribute("showMenu", 0);
		 * sessionCache.setAttribute(root, "showMenu", 0,
		 * HHNConstants.SESSION_CACHE_TIME);
		 */

		request.getSession().removeAttribute("utp");
		sessionCache.setAttribute(root, "utp", null,
				HHNConstants.SESSION_CACHE_TIME);
		return "login/checkpassword";
	}

	@RequestMapping(value = "checkPassWord")
	public String checkPassWord(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		String root = session.getSessionId(request, response);
		String password = request.getParameter("password");
		String oa = (String) request.getSession().getAttribute("oa");
		String mobilePhone = (String) request.getSession().getAttribute(
				"mobilePhone");
		PersonDo person = personService.getByUserCellPhone(mobilePhone);
		AccountUserDo user = userService.findUserByPhone(mobilePhone);
		String pwdMd5 = DigestUtils.md5Hex(password + WebConstants.PASS_KEY);
		if (pwdMd5.equals(user.getPassword())) {
			UserThirdPartyDo upo = null;
			upo = userThirdPartyService.getByUserId(user.getId().intValue());
			if (upo != null) {
				request.getSession().removeAttribute("utp");
				sessionCache.setAttribute(root, "utp", null,
						HHNConstants.SESSION_CACHE_TIME);
				jsonObject.put("msg", "该手机号已经绑定其他OA！");
				ResponseUtils.renderText(response, null, jsonObject.toString());
				return null;
			}
			upo = new UserThirdPartyDo();
			upo.setThethirdusername(oa);
			upo.setUserId(new Long(user.getId()).intValue());
			// userThirdPartyService.deleteUserThirdPartyByUserId(new
			// Long(user.getId()).intValue());
			userThirdPartyService.saveUserThirdParty(upo);
			request.getSession().setAttribute("utp", upo);
			sessionCache.setAttribute(root, "utp", upo,
					HHNConstants.SESSION_CACHE_TIME);
			request.getSession().setAttribute("user", user);
			// 取缓存登录信息
			sessionCache.setAttribute(root, HHNConstants.SESSION_INFO,
					user, HHNConstants.SESSION_CACHE_TIME);
			if (person.getAuditStatus() == 3) {
				jsonObject.put("status", "3");
				jsonObject.put("msg", "Y");
				ResponseUtils.renderText(response, null, jsonObject.toString());
				// return "redirect:/product/plist.do";
			} else {
				jsonObject.put("status", "2");
				jsonObject.put("msg", "Y");
				ResponseUtils.renderText(response, null, jsonObject.toString());
				// return "redirect:/account/realAuth.do";
			}
		} else {
			jsonObject.put("msg", "密码错误，请重新输入！");
			ResponseUtils.renderText(response, null, jsonObject.toString());
		}
		return null;
	}

	@RequestMapping(value = "linkIdentifyCode")
	public String linkIdentifyCode(HttpServletRequest request,
			HttpServletResponse response) {
		return "login/identifycode";
	}

	@RequestMapping(value = "sendIdentifyCode")
	public String sendIdentifyCode(HttpServletRequest request,
			HttpServletResponse response) {
		// 手机验证码校验
		JSONObject jsonObject = new JSONObject();
		String root = session.getSessionId(request, response);
		AccountUserDo user = this.getAccountUser();
		String mobilePhone = (String) sessionCache.getSession(root).get(
				"mobilePhone");
		String s = identifyCodeService.sendIdentifyCode(mobilePhone);
		if (StringUtils.isNotBlank(s)) {
			// 发送成功
			logger.info("向手机号:" + mobilePhone + "发送验证码成功,验证码为:" + s);
			jsonObject.put("ret", "0");
		} else {
			// 发送失败
			logger.info("向手机号:" + mobilePhone + "发送验证码失败");
			jsonObject.put("ret", "1");
		}
		ResponseUtils.renderText(response, null, jsonObject.toString());
		return null;
	}

	@RequestMapping(value = "checkIdentifyCode")
	public String checkIdentifyCode(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		// 手机验证码校验
		String identifyCode = request.getParameter("identifyCode");
		// AccountUserDo user = this.getAccountUser();
		String root = session.getSessionId(request, response);
		String mobilePhone = (String) sessionCache.getSession(root).get(
				"mobilePhone");
		String oa = (String) sessionCache.getSession(root).get("oa");
		boolean flag = identifyCodeService.checkIdentifyCode(mobilePhone,
				identifyCode);
		if (!flag) {
			// 手机验证码校验不通过
			jsonObject.put("msg", "手机验证码不正确");
			jsonObject.put("result", "N");
			ResponseUtils.renderText(response, "UTF-8",
					JSONObject.fromObject(jsonObject).toString());
		} else {
			// 手机验证码校验通过
			// jsonObject.put("message", "手机验证码不正确");
			UserThirdPartyDo upo = new UserThirdPartyDo();
			try {
				AccountUserDo aud = new AccountUserDo();
				String loginInfo = mobilePhone;
				String pwd = "user" + mobilePhone;
				request.setAttribute("mobilePhone", mobilePhone);
				aud.setUsername(oa);
				aud.setMobilePhone(mobilePhone);
				String pwdMd5 = DigestUtils.md5Hex(pwd + WebConstants.PASS_KEY);
				aud.setPassword(pwdMd5);
				Date now = new Date();
				aud.setCreateTime(now);
				aud.setSource(13); // 彩管家
				IResult result = userService.registerUser(aud);
				if (result.isSuccess()) {
					Long userId = (Long) result.getModel();
					// 获取登录用户userId
					LoginInfoRelate loginir = userInfoService.getByLoginInfo(
							mobilePhone, LoginInfoRelate.class, true);
					if (loginir == null) {
						// 如果没有处理老数据，兼容老版本，取一次t_user信息
						aud = userService.loginWithPwd(loginInfo, pwdMd5);
					} else {
						aud = userService.getById(userId);
					}
					request.getSession().setAttribute("user", aud);
					// 取缓存登录信息
					sessionCache.setAttribute(root, HHNConstants.SESSION_INFO,
							aud, HHNConstants.SESSION_CACHE_TIME);
					upo.setUserId(new Long(aud.getId()).intValue());
				} else {
					logger.error("注册失败");
					// 手机验证码校验不通过
					jsonObject.put("msg", result.getErrorMessage());
					jsonObject.put("result", "N");
					ResponseUtils.renderText(response, "UTF-8", JSONObject
							.fromObject(jsonObject).toString());
					return null;
				}
				upo.setThethirdusername(oa);
				// upo.setUserId(new Long(user.getId()).intValue());
				// 待讨论决定
				// userThirdPartyService.deleteUserThirdPartyByUserId(new
				// Long(userId).intValue());//?????
				userThirdPartyService.saveUserThirdParty(upo);
				request.getSession().setAttribute("utp", upo);
				sessionCache.setAttribute(root, "utp", upo,
						HHNConstants.SESSION_CACHE_TIME);
			} catch (Exception e) {
				logger.error(e.getMessage());
				return "redirect:/login/index.do";
			}
			jsonObject.put("result", "Y");
			ResponseUtils.renderText(response, "UTF-8",
					JSONObject.fromObject(jsonObject).toString());
		}

		return null;
	}
	
	/**
	 * 物业国际抽奖页
	 * @param request
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-5-14 下午9:33:50
	 */
	@RequestMapping(value = "lottery")
	public String lottery(HttpServletRequest request){
//		Integer userId = getCurrentUserId();
//		Integer number = lotteryService.getLotteryNumber(userId);
//		request.setAttribute("number", number);
		request.setAttribute("topList",lotteryService.queryTopLotteryList());
		return "product/lottery";
	}
	
	/**
	 * 抽奖
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @throws ParseException
	 * @author: chenzhpmf
	 * @date 2015-5-14 下午9:34:03
	 */
	@RequestMapping(value = "ajaxLuckyDraw")
	public void ajaxLuckyDraw(HttpServletRequest request,
			HttpServletResponse response) throws ParseException{
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		String startTime = "2015-05-15 00:00:00"; //活动开始时间
		String endTime = "2015-05-30 23:59:59"; //结束时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long currTime = new Date().getTime();
		if(currTime<df.parse(startTime).getTime()){
			jsonMap.put("status", 0);
			jsonMap.put("message", "活动还未开始");
			ResponseUtils.renderText(response, "UTF-8",JSONObject.fromObject(jsonMap).toString());
			return;
		}
		if(currTime>df.parse(endTime).getTime()){
			jsonMap.put("status", 0);
			jsonMap.put("message", "抽奖活动已结束，谢谢参与！");
			ResponseUtils.renderText(response, "UTF-8",JSONObject.fromObject(jsonMap).toString());
			return;
		}
		AccountUserDo userDo  = getAccountUser();
		if(userDo==null || StringUtils.isBlank(ObjectUtils.toString(userDo.getId()))){
			jsonMap.put("status", 0);
			jsonMap.put("message", "立即登录！参与抽奖");
			ResponseUtils.renderText(response, "UTF-8",JSONObject.fromObject(jsonMap).toString());
			return;
		}
		//可抽奖次数
		Integer number = lotteryService.getLotteryNumber(userDo.getId().intValue());
		if(number==null || number==0){
			jsonMap.put("status", 0);
			jsonMap.put("message", "您没有抽奖机会了！");
			ResponseUtils.renderText(response, "UTF-8",JSONObject.fromObject(jsonMap).toString());
			return;
		}
		LotteryInfo info = new LotteryInfo();
		info.setUserId(userDo.getId().intValue());
		info.setUserName(userDo.getUsername());
		LotteryPrize prize = lotteryService.addLotteryInfo(info);
		jsonMap.put("status", 1);
		jsonMap.put("id", prize.getId());
		jsonMap.put("amount", prize.getPrizeName());
		jsonMap.put("done",number-1);
		jsonMap.put("userName", userDo.getUsername());
		ResponseUtils.renderText(response, "UTF-8",	JSONObject.fromObject(jsonMap).toString());
	}
	
	@RequestMapping(value = "initLotteryPrize")
	public void initLotteryPrize(HttpServletRequest request,
			HttpServletResponse response){
		String key = request.getParameter("key");
		if(StringUtils.isNotBlank(key) && key.equals("wygjadmin")) {
			List<LotteryPrize> prizeList = lotteryService.initLotteryPrize();
			ResponseUtils.renderText(response, "UTF-8",JSONArray.fromObject(prizeList).toString());
			return;
		}
		ResponseUtils.renderText(response, "UTF-8","{\"ok\"}");
	}
	
	@RequireLogin
	@RequestMapping(value = "getTopLottery")
	public void getTopLottery(HttpServletRequest request,
			HttpServletResponse response){
		List<LotteryInfo> topList = lotteryService.queryTopLotteryList();
		ResponseUtils.renderText(response, "UTF-8",JSONArray.fromObject(topList).toString());
	}
}
