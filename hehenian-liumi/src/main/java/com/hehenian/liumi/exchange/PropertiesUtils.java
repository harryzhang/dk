package com.hehenian.liumi.exchange;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	
	private static Properties props = null;
	private static Object lock = new Object();
	
	private static final String DB_FILE="/opt/hehenian/order.properties";
	
	
	/**
	 * 写入
	 * @param filePath
	 * @param parameterName
	 * @param parameterValue
	 */
	 public static void writeProperties(String filePath,String parameterName,String parameterValue) {
	     try {
             if (StringUtils.isBlank(readValue(DB_FILE, parameterName )) ){
	            if(props == null) {
	            	synchronized (lock) {
                        if(props != null) {
                            return;
                        }
	            		props = new Properties();
	            		props.put(parameterName, parameterValue);
					}
	            }else{
	            	synchronized (lock) {
	            		if(!props.containsKey(parameterName)) {
	            			props.put(parameterName, parameterValue);
	            		}
					}
	            }
                    //FileWriter fw = new FileWriter(new File(System.getProperty("user.dir")+File.separator+DB_FILE), true);
                    FileWriter fw = new FileWriter(DB_FILE, true);
                    fw.write("\r\n"+parameterName+"="+parameterValue);
                    fw.flush();
                    fw.close();
                }
	        } catch (IOException e) {
	        	System.err.println("Visit order for updating "+parameterName+" value error");
                e.printStackTrace();
	        }
	    }
	 
	 	/**
	    * 根据key读取value
	    * @param filePath
	    * @param key
	    * @return
	    */
	   public static String readValue(String filePath,String key) {
		    if(props == null) {
		    	synchronized (lock) {
                    FileReader fr = null;
                    props = new Properties();
			         try {
			        	 //FileReader fr = new FileReader(new File(System.getProperty("user.dir")+File.separator+DB_FILE));
                         fr = new FileReader(DB_FILE);
				         props.load(fr);
				         return props.getProperty (key);
			        } catch (Exception e) {
				         e.printStackTrace();
				         return null;
			        } finally {
                         IOUtils.closeQuietly(fr);
                     }
				}
		    }else{
		    	return props.getProperty (key);
		    }
		    
	   }
}
