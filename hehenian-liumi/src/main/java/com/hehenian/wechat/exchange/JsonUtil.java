package com.hehenian.wechat.exchange;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

public class JsonUtil {

	private static JsonConfig config = new JsonConfig();

	static{
		config.setJsonPropertyFilter(new PropertyFilter(){
			@Override
			public boolean apply(Object source, String name, Object value) {
				return value == null;
			}
		});
	}
	
	public static WechatResult translate(String json){
		return (WechatResult) JSONObject.toBean(JSONObject.fromObject(json), WechatResult.class);
	}
}
