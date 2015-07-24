/**  
 * @Project: hehenian-lend
 * @Package com.hehenian.web.common.constant
 * @Title: WebConstants.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月22日 上午11:52:02
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.login.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量类
 * 
 * @author: liuzgmf
 * @date 2015年1月22日 上午11:52:02
 * @Description 描述方法作用
 * @author huangzl QQ: 272950754
 * @date 2015年5月27日 下午5:34:57
 * @Project hehenian-lend-login
 * @Package com.hehenian.login.common.constant 
 * @File WebConstants.java
*/
public class WebConstants {

    /** 消息提示KEY */
    public static final String MESSAGE_KEY  = "message";
    
    /** session用户信息KEY */
    public static final String SESSION_USER  = "user";
    /**
     * session内容：admin管理员
     */
    public static final String SESSION_ADMIN = "admin";
    
    /**
     * session内容：彩生活管理员
     */
    public static final String COLOURLIFE_ADMIN_USER = "cl_admin";

    /** 验证码 */
    public static final String VERIFICATION_CODE = "randomCode";


    /** 应答返回码 000-代表交易成功 */
    public static final String RESP_CODE_SUCCESS = "000";

    /**
     * 全局费用集合
     *
     * @since 1.0.0
     */
    public static final String FEE_APPLICATION = "feeApplication";

    /**
     * 用户来源
     */
    public static final String SOURCEFROM_WEB            = "1";
    public static final String SOURCEFROM_COLOURLIFE     = "2";
    public static final String SOURCEFROM_COLOURLIFE_APP = "4";
    public static final String SOURCEFROM_WEIXIN         = "10";

    /**
     * 密码加密串
     */
    public static String PASS_KEY = "GDgLwwdK270Qj155xho8lyTp";
    
    /**
     * 彩管家密码加密串
     */
    public static String CGJ_PASS_KEY = "GnKPe2BMT7PV";
    /**
     * 彩管家APPKEY
     */
    public static String CGJ_APP_KEY = "0001";

    //e理财
    public static final String COLOUR_LIFE_Flag_WYF         = "1";
    
    public static final String USER_ID = "";
    
    public static final String CHANNEL_NAME = "channel_name";
    

	public static final String ERR_CODE= "验证码错误";
	
	public static final String ERR_NAME="用户名错误";
	

	public static final String NOT_NULL="用户名密码不能为空";
	
    
    public static final Map<Integer, String> channelMap = new HashMap<Integer, String>();
    
    static {
    	channelMap.put(0, "合和年");
    	channelMap.put(1, "国际物业");
    	channelMap.put(2, "红包理财");
    }
}
