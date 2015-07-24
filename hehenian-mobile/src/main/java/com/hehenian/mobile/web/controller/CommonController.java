package com.hehenian.mobile.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.hehenian.biz.common.account.IUserService;
import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.identifycode.IIdentifyCodeService;
import com.hehenian.biz.common.wygj.IPropertyManagementFeeService;
import com.hehenian.biz.common.wygj.SysCodeService;
import com.hehenian.biz.common.wygj.dataobject.ParkingFeeDo;
import com.hehenian.biz.common.wygj.dataobject.PropertyManagementFeeDo;
import com.hehenian.biz.common.wygj.dataobject.SysCodeDo;
import com.hehenian.common.utils.IDCardUtil;
import com.hehenian.common.utils.ResponseUtils;
import com.hhn.hessian.query.IQueryService;
import com.hhn.util.BaseReturn;


@Controller
@RequestMapping(value = "/common")
public class CommonController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(CommonController.class);
    @Autowired
    private IIdentifyCodeService identifyCodeService;
	@Autowired
    private IQueryService queryService;
	@Autowired
	private IUserService userService;
	@Autowired
	private SysCodeService sysCodeService;
	@Autowired
	private IPropertyManagementFeeService propertyManagementFeeService;

	/**
	 * 生成验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "imageCode", method = RequestMethod.GET)
	public String imageCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// 在内存中创建图象
		int width = 65, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(230, 255));
		g.fillRect(0, 0, 100, 25);
		// 设定字体
		g.setFont(new Font("Arial", Font.CENTER_BASELINE | Font.ITALIC, 18));
		// 产生0条干扰线，
		g.drawLine(0, 0, 0, 0);
		// 取随机产生的认证码(4位数字)
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			// 将认证码显示到图象中
			g.setColor(getRandColor(100, 150));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 15 * i + 6, 16);
		}
		  for(int i=0;i<(random.nextInt(5)+5);i++){  
		        g.setColor(new Color(random.nextInt(255)+1,random.nextInt(255)+1,random.nextInt(255)+1));  
		        g.drawLine(random.nextInt(100),random.nextInt(30),random.nextInt(100),random.nextInt(30));  
		}
		String pageId = request.getParameter("pageId");
		String key = pageId + "_checkCode";
		// 将验证码存入页面KEY值的SESSION里面
		request.getSession().setAttribute(key, sRand);
		request.getSession().setAttribute("keys", sRand);
		// 图象生效
		g.dispose();
		ServletOutputStream responseOutputStream = response.getOutputStream();
		// 输出图象到页面
		ImageIO.write(image, "JPEG", responseOutputStream);
		// 以下关闭输入流！
		responseOutputStream.flush();
		responseOutputStream.close();
		// 获得页面key值
		return null;
	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * 发送短信验证码
	 * @param request
	 * @param response
	 * @return
	 * @author: chenzhpmf
	 * @date 2015-4-1 下午11:38:05
	 */
	@RequestMapping(value = "sendPhoneVirifyCode")
    public void sendPhoneVirifyCode(HttpServletRequest request,HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		String mobilePhone = request.getParameter("mobilePhone");
		boolean checkPhone = Boolean.valueOf(request.getParameter("checkPhone"));

		if(StringUtils.isBlank(mobilePhone)){
			Integer userId = getCurrentUserId();
			if(userId==null){
				jsonObject.put("ret","1");
		        ResponseUtils.renderText(response, null,jsonObject.toString());
		        return;
			}
	        BaseReturn userPhone = queryService.queryPhone(userId);
	        Map<String,Object> userMap = (HashMap<String,Object>)userPhone.getData();
	        mobilePhone = (String)userMap.get("mobilePhone");
	        if (mobilePhone.startsWith("-")){
	            mobilePhone = mobilePhone.substring(1);
	        }
		}else{
			if(checkPhone){
				AccountUserDo userDo = userService.findUserByPhone(mobilePhone);
			    if (userDo!=null){
			    	jsonObject.put("ret","3");
			        ResponseUtils.renderText(response, null,jsonObject.toString());
			        return;
			    }
			}
		}
	    
		if(StringUtils.isBlank(mobilePhone)){
			jsonObject.put("ret","2");
			ResponseUtils.renderText(response, null,jsonObject.toString());
			return;
		}
		
        String s = identifyCodeService.sendIdentifyCode(mobilePhone);
        if (StringUtils.isNotBlank(s)) {
            //发送成功
        	logger.info("向手机号:"+mobilePhone+"发送验证码成功,验证码为:"+s);
            jsonObject.put("ret","0");
        } else {
            //发送失败
        	logger.info("向手机号:"+mobilePhone+"发送验证码失败");
            jsonObject.put("ret","1");
        }
        ResponseUtils.renderText(response, null,jsonObject.toString());
    }
	
	
	/**
	 * 验证身份证号码
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ParseException
	 * @author: chenzhpmf
	 * @date 2015-4-4 上午12:02:52
	 */
	@RequestMapping(value = "isIDNO")
	public void isIDNO(HttpServletRequest request,HttpServletResponse response) throws IOException, NumberFormatException, ParseException {
		JSONObject jsonObject = new JSONObject();
		String IDNO = request.getParameter("idNo");
		if (StringUtils.isBlank(IDNO)) {
			jsonObject.put("putStr", "0");
			ResponseUtils.renderText(response, null,jsonObject.toString());
			return;
		}
		long len = IDNO.length();
		// 验证身份证
		int sortCode = 0;
		int MAN_SEX = 0;
		if (len == 15) {
			sortCode = Integer.parseInt(IDNO.substring(12, 15));
		} else if(len == 18) {
			sortCode = Integer.parseInt(IDNO.substring(14, 17));
		}else{
			jsonObject.put("putStr", "0");
			ResponseUtils.renderText(response, null,jsonObject.toString());
			return;
		}
		if (sortCode % 2 == 0) {
			MAN_SEX = 2;// 女性身份证
		} else if (sortCode % 2 != 0) {
			MAN_SEX = 1;// 男性身份证
		} else {
			jsonObject.put("putStr", "1");// 身份证不合法
			ResponseUtils.renderText(response, null,jsonObject.toString());
			return;
		}
		String iDresutl = "";
		iDresutl = IDCardUtil.chekIdCard(MAN_SEX, IDNO);
		if (iDresutl != "") {
			jsonObject.put("putStr", "1");// 身份证不合法
			ResponseUtils.renderText(response, null,jsonObject.toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "initSysCode")
    public void initSysCode(HttpServletRequest request,HttpServletResponse response) {
		//省/直辖市/特别行政区
		Integer type[] = {2};
		List<SysCodeDo> provinceList = sysCodeService.queryAllSysCodeByType(type);
		Map<String,Object> provCodeMap = new HashMap<String, Object>();
		for(SysCodeDo provCode : provinceList){
			Map<String,Object> remarKMap = new HashMap<String, Object>();
			String proName = provCode.getRemarkForShow(); 
			//直辖市： 北京 天津 上海 重庆
			if(proName.indexOf("北京")>-1 || proName.indexOf("天津市")>-1 || proName.indexOf("上海")>-1 || proName.indexOf("重庆")>-1 ){
				remarKMap.put("d",1);
			}
			remarKMap.put("n",proName);
			provCodeMap.put("c"+provCode.getId().toString(), remarKMap);
		}
		Integer types[] = {3,4};
		List<SysCodeDo> cityList = sysCodeService.queryAllSysCodeByType(types);
		Map<String,Object> cityCodeMap = new HashMap<String, Object>();
		for(SysCodeDo cityCode : cityList){
			String cityName = cityCode.getRemarkForShow(); 
			Map<String,Object> remarKMap = new HashMap<String, Object>();
			remarKMap.put("n", cityName);
			cityCodeMap.put("c"+cityCode.getParentId()+"_"+cityCode.getId(),remarKMap);
		}
		
		for (Map.Entry<String, Object> entry : provCodeMap.entrySet()) {  
			String key = entry.getKey();
			Map<String,Object> value = (Map<String,Object>)entry.getValue();
			for (Map.Entry<String, Object> cityEntry : cityCodeMap.entrySet()) {  
				String cityKey = cityEntry.getKey();
				if(cityKey.contains(key+"_")){
					String code = cityKey.split("_")[1];
					Map<String,Object> object = (Map<String,Object>)cityEntry.getValue();
					recursiveCode("c"+code,object,cityCodeMap);
					value.put("c"+code, object);
					entry.setValue(value);
				}
			}
		}
		ResponseUtils.renderText(response, null,JSONObject.fromObject(provCodeMap).toString());
	}
	
	public static void recursiveCode(String key,Map<String,Object> object,Map<String,Object> cityCodeMap){
		for (Map.Entry<String, Object> cityEntry : cityCodeMap.entrySet()) {  
			String cityKey = cityEntry.getKey();
			if(cityKey.contains(key+"_")){
				String code = cityKey.split("_")[1];
				object.put("c"+code, cityEntry.getValue());
			}
		}
	}
	
	@RequestMapping(value = "loadCommunity")
	public void loadCommunity(HttpServletRequest request,HttpServletResponse response) {
		Long id = NumberUtils.toLong(request.getParameter("id"));
		int queryFlag = NumberUtils.toInt(request.getParameter("flag"));
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		switch(queryFlag){
		case 1:
			SysCodeDo codeDo = new SysCodeDo(5,id);
			List<SysCodeDo> syCodeList = sysCodeService.querySysCodeListByDo(codeDo);
			for(SysCodeDo scd : syCodeList){
				jsonMap.put(scd.getId().toString(), scd.getRemarkForShow());
			}
			break;
		case 2:
			List<PropertyManagementFeeDo> pmfList = propertyManagementFeeService.queryBuildingByAddressId(id,null);
			for(int i=0;i<pmfList.size();i++){
				PropertyManagementFeeDo pmf = pmfList.get(i);
				jsonMap.put(pmf.getBuilding(),pmf.getBuilding());
			}
			break;
		case 3:
			String building = request.getParameter("building");
			List<PropertyManagementFeeDo> roomList = propertyManagementFeeService.queryBuildingByAddressId(id,building);
			for(int i=0;i<roomList.size();i++){
				PropertyManagementFeeDo pmf = roomList.get(i);
				jsonMap.put(pmf.getRoomnum(),pmf.getRoomnum());
			}
			break;
		}
		ResponseUtils.renderText(response, null,JSONObject.fromObject(jsonMap).toString());
	}
}
