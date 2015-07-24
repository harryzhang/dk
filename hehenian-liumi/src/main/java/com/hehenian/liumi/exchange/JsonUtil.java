package com.hehenian.liumi.exchange;

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
	
	public static String parse(Parameter p){
		String result = JSONObject.fromObject(p,config).toString();
		return result;
	}
	public static Result translate(String json){
		return (Result) JSONObject.toBean(JSONObject.fromObject(json), Result.class);
	}
}
