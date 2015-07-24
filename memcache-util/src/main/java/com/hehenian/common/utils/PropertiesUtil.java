/*package com.hehenian.common.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertiesUtil {
	public final static int BY_PROPERTIES = 1;
	public final static int BY_RESOURCEBUNDLE = 2;
	public final static int BY_PROPERTYRESOURCEBUNDLE = 3;
	public final static int BY_CLASS = 4;
	public final static int BY_CLASSLOADER = 5;
	public final static int BY_SYSTEM_CLASSLOADER = 6;

	public final static Properties loadProperties(final String name,
			final int type) throws IOException {
		Properties p = new Properties();
		InputStream in = null;
		if (type == BY_PROPERTIES) {
			in = new BufferedInputStream(new FileInputStream(name));
			assert (in != null);
			p.load(in);
		} else if (type == BY_RESOURCEBUNDLE) {
			ResourceBundle rb = ResourceBundle.getBundle(name, Locale
					.getDefault());
			assert (rb != null);
			p = new ResourceBundleAdapter(rb);
		} else if (type == BY_PROPERTYRESOURCEBUNDLE) {
			in = new BufferedInputStream(new FileInputStream(name));
			assert (in != null);
			ResourceBundle rb = new PropertyResourceBundle(in);
			p = new ResourceBundleAdapter(rb);
		} else if (type == BY_CLASS) {
			assert (PropertiesUtil.class.equals(new PropertiesUtil().getClass()));
			in = PropertiesUtil.class.getResourceAsStream(name);
			assert (in != null);
			p.load(in);
		} else if (type == BY_CLASSLOADER) {
			assert (PropertiesUtil.class.getClassLoader().equals(new PropertiesUtil()
					.getClass().getClassLoader()));
			in = PropertiesUtil.class.getClassLoader().getResourceAsStream(name);
			assert (in != null);
			p.load(in);

		} else if (type == BY_SYSTEM_CLASSLOADER) {
			in = ClassLoader.getSystemResourceAsStream(name);
			assert (in != null);
			p.load(in);
		}
		if (in != null) {
			in.close();
		}
		return p;
	}
	
	public static void main(String[] args){
		try{
			Properties properties = loadProperties("userlevel.properties", PropertiesUtil.BY_CLASSLOADER);
			String value = properties.getProperty("level1");
			
			System.out.println("values:" + new String(value.getBytes("ISO8859-1"),"UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> getPropertiesForMap(String src, int srcType){
		try{
			Properties properties = loadProperties(src, srcType);
			if(properties != null){
				return new HashMap<String, String>((Map) properties); 
			}else{
				return null;
			}
		}catch(Exception e){
			return null;
		}
	}
}*/