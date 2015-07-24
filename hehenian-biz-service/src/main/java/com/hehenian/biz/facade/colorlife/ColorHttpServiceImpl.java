package com.hehenian.biz.facade.colorlife;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.hehenian.biz.common.colorlife.IColorHttpService;

@Service("colorResourceService")
public class ColorHttpServiceImpl implements IColorHttpService {

	/**
	 * 调用彩色
	 */
	@Override
	public String callColorResouce(String baseUrl, String businessURL,
			String key, String secret, Map<String, String> parameterMap)
			throws Exception {
		return ColorLifeManager.callColorResouce(baseUrl,businessURL,key,secret, parameterMap);
	}

}
