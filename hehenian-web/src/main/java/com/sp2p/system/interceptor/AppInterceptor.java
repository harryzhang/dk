package com.sp2p.system.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.renren.api.client.utils.Md5Utils;
import com.shove.security.Encrypt;
import com.shove.security.License;
import com.shove.web.util.JSONUtils;

public class AppInterceptor implements Interceptor {

	private final static String APP_KEY = "wDwdKd27d0Qj1w%$Ea536yiuPE96O!3L";
	public void destroy() {

	}

	public void init() {

	}
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String auth = (String) request.getParameter("auth");
		System.out.println("auth===========>" + auth);
		String info = (String)request.getParameter("info");
		System.out.println("info===========>" + info);
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (StringUtils.isBlank(auth)) {
		jsonMap.put("error", "-2");
		jsonMap.put("msg", "验证签名不正确");
		JSONUtils.printObject(jsonMap);
		return null;
		}
		Map<String, String> map = (Map<String, String>) JSONObject.toBean(
		JSONObject.fromObject(auth), HashMap.class);
		String crc = map.get("crc");
		System.out.println("crc==>"+crc);
		if (StringUtils.isBlank(crc)) {
		jsonMap.put("error", "-2");
		jsonMap.put("msg", "验证签名不正确");
		JSONUtils.printObject(jsonMap);
		return null;
		}
		if(StringUtils.isBlank(map.get("time_stamp"))){
		jsonMap.put("error", "-2");
		jsonMap.put("msg", "时间戳不能为空");
		JSONUtils.printObject(jsonMap);
		return null;
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		long curTime = new Date().getTime();
		long client = sDateFormat.parse(map.get("time_stamp")).getTime();
		if (curTime - client >= 1 * 60 * 1000) {
		jsonMap.put("error", "-2");
		jsonMap.put("msg", "请求超时");
		JSONUtils.printObject(jsonMap);
		return null;
		}
		if(StringUtils.isBlank(map.get("imei"))){
		jsonMap.put("error", "-2");
		jsonMap.put("msg", "imei不能为空");
		JSONUtils.printObject(jsonMap);
		return null;
		}
		if(StringUtils.isBlank(map.get("uid"))){
		jsonMap.put("error", "-2");
		jsonMap.put("msg", "uid不能为空");
		JSONUtils.printObject(jsonMap);
		return null;
		}
		if(StringUtils.isBlank(map.get("uid"))){
		jsonMap.put("error", "-2");
		jsonMap.put("msg", "uid不能为空");
		JSONUtils.printObject(jsonMap);
		return null;
		}
		StringBuilder keys = new StringBuilder();
		keys.append(map.get("time_stamp"));
		keys.append(map.get("imei"));
		keys.append(map.get("uid"));
		keys.append(Encrypt.MD5(map.get("uid")+"").substring(9, 20));
		keys.append(info);
		keys.append(APP_KEY);
		System.out.println("keys==>"+keys.toString());
		String md5Crc = Md5Utils.md5(keys.toString());
		System.out.println("MD5CRC==>"+md5Crc);
		if(!crc.equals(md5Crc)){
		jsonMap.put("error", "-2");
		jsonMap.put("msg", "验证签名不正确");
		JSONUtils.printObject(jsonMap);
		return null;
		}
////		License.update(request, IConstants.LICENSE);
//		if (!License.getAndoridAllow(request)&&!License.getiOSAllow(request)) {
//		jsonMap.put("error", "1");
//		jsonMap.put("msg", "");
//		JSONUtils.printObject(jsonMap);
//		return null;
//		}
		return invocation.invoke();
		}

}
