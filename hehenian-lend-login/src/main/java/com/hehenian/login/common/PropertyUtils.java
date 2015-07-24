package com.hehenian.login.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * @Description 描述方法作用
 * @author huangzl QQ: 272950754
 * @date 2015年5月27日 下午5:34:09
 * @Project hehenian-lend-login
 * @Package com.hehenian.login.common 
 * @File PropertyUtils.java
*/
public class PropertyUtils {

    private final Logger logger = Logger.getLogger(this.getClass());

	static Properties prop = new Properties();
	static {
		try {
			 prop.load(new FileInputStream(System.getProperty("catalina.home")+File.separator+"conf"+File.separator+"hehenian.properties")); 
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	public static String getProperty(String key){
		return (String) prop.get(key);
		
	}
	
	
}
