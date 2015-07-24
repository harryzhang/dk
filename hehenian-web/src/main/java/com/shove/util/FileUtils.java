package com.shove.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**   
*    
* @Description 用户文件操作
* @Author Yang Cheng
* @Date: Feb 9, 2012 2:42:48 AM   
* @Version    
*    
*/ 
public class FileUtils {
	
	private static Log log = LogFactory.getLog(FileUtils.class);
	
	/** 
	* @Description: 创建目录
	* @Author Yang Cheng
	* @Date: Feb 9, 2012 3:20:47 AM  
	* @param path  
	* @return void    
	*/ 
	public static void mkdirs(String path){
	    File uploadFilePath = new File(path);
        // 如果该目录不存在,则创建
        if(!uploadFilePath.exists()) {
            uploadFilePath.mkdirs();
            log.info("目录不存在已创建");
        }else{
        	log.info("目录已存在");
        }
	}
	
	/**
	 * 自定义文件名称
	 * @return
	 */
	public static String getFileName(){
		SimpleDateFormat simpledateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Random random = new Random();
		return simpledateFormat.format(new Date())+random.nextInt(10000);
	}
	
	/**
	 * 返回指定路径下的所有文件
	 * @param path 路径
	 * @return
	 */
	public static File[] getFiles(String path){
		File folder = new File(path);
		if(!folder.exists()){
			folder.mkdirs();
		}
		File[] files = folder.listFiles();//得到当前文件和子文件
		return files;
	}
	
	/**
	 * 删除文件
	 * @param file
	 */
	public static void removeFile(File file){
		if(file.exists()){//判断一个文件是否存在
			file.delete();
		}
	}
}
