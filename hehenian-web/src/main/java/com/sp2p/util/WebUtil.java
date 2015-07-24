package com.sp2p.util;

import com.sp2p.constants.IConstants;

public class WebUtil {
	
	public static String getBasePath(){
	    return getWebPath();
	    
	}
	
	
	/**   
	 * @MethodName: getWebPath  
	 * @Param: WebUtil  
	 * @Author: gang.lv
	 * @Date: 2013-5-12 下午10:57:47
	 * @Return:    
	 * @Descb: 获取web路径
	 * @Throws:
	*/
	public static String getWebPath(){
		return System.getProperty("web.root",IConstants.WEB_URL);
	}
}
