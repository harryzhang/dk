package com.hehenian.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Project: hehenian-barcode
 * @Package com.hehenian.barcode.common.util
 * @Title: PropertiesUtils
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年5月14日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
public abstract class PropertiesUtils {

	public static Properties loadProperties(final String propertieFileName) throws IOException {
		InputStream inStream = null;
		try {
			inStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertieFileName);

			Properties properties = new Properties();
			properties.load(inStream);
			return properties;
		} finally {
			if (inStream != null) {
				inStream.close();
			}
		}
	}

	public static Properties loadAbsolutePathProperties(final String propertieFileName) throws IOException {
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(propertieFileName);

			Properties properties = new Properties();
			properties.load(inStream);
			return properties;
		} finally {
			if (inStream != null) {
				inStream.close();
			}
		}
	}
	
	public static Properties loadSystemProperty(String evn, String fileName) {
		String tomcatHome = System.getProperty(evn);
		System.out.println("tomcat home:[" + tomcatHome + "]");
		String setupFile = tomcatHome + File.separatorChar + "conf" + File.separatorChar + fileName;
		System.out.println("system config file:" + setupFile);
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(new File(setupFile));
			prop.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	public static void main(String[] args) throws Exception {
		try {
			Properties pro = loadProperties("barcode.properties");
			String mappingClassName = pro.getProperty("image.save.path");
			System.out.println(mappingClassName);

			//Object mapping = Class.forName(mappingClassName).newInstance();
			//System.out.println(mapping);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
