package com.shove.web.util;

import java.io.File;

public class FileFormatUtil {

	/**
	 * 判断文件是否正确
	 * @param file 上传文件
	 * @param fileName 文件名称
	 * @return
	 * @throws Exception
	 */
	public static String fileIsTrue(File file, String fileName) throws Exception {
		if(file == null){
			return "请上传文件!";
		}
		
		if(!file.exists()){
			return "文件不存在";
		}
		
		int lastIndex = fileName.lastIndexOf(".");//从后面查找
		String suffix = fileName.substring(lastIndex + 1);//截取
		if(!"XLS".equalsIgnoreCase(suffix)){
			return "文件格式不正确";
		}
		return null;
	}
}
