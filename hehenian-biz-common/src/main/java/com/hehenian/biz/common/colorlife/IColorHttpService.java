package com.hehenian.biz.common.colorlife;

import java.util.Map;

/**
 * 调用彩色活http服务接口
 * @author zhangyunhmf
 *
 */
public interface IColorHttpService {
	
	/**
	 * 调用彩色活的服务, 获取客户经理绑定的小区
	 * 
	 * @param baseUrl
	 *            彩生活地址
	 * @param businessURL
	 *            业务操作的相对地址
	 * @return  返回JS字符串
	 * @throws Exception
	 */
	public  String callColorResouce(String baseUrl, String businessURL,
			String key, String secret, Map<String, String> parameterMap)
			throws Exception;

}
