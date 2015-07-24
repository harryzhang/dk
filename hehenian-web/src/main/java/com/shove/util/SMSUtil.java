package com.shove.util;

import java.rmi.RemoteException;

import javax.swing.Icon;
import javax.xml.rpc.ServiceException;


import com.shovesoft.SMS;
import com.sp2p.constants.IConstants;

/**
 * 短信接口，对短信接口地址进行拼接，提供公用
 * 
 * @author Administrator
 * 
 */
public class SMSUtil {


	/**   
	 * @MethodName: sendSMS  
	 * @Param: SMSUtil  
	 * @Author: gang.lv
	 * @Date: 2013-5-30 下午04:04:13
	 * @Return:    
	 * @Descb: 发送短信
	 * @Throws:
	*/
	public static String sendSMS(String userName, String password, String content,
			String phone,String randomCode) {
		try {
			if(randomCode != null){
				content += randomCode;
			}
			if (!IConstants.ISDEMO.equals("1")) {
				SMS.sendMSM(userName, password, content, phone);
			}
			return "Sucess";
		} catch (RemoteException e) {
			e.printStackTrace();
			return "Fail";
		} catch (ServiceException e) {
			e.printStackTrace();
			return "Fail";
		}
	}
}
