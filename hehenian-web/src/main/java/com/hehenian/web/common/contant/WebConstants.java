package com.hehenian.web.common.contant;

public class WebConstants {

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

    /** 消息key */
    public static final String MESSAGE_KEY = "message";

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
     * 小贷MD5加密附加串
     */
    public static String XD_PASS_KEY = "HHN&XD#$%CD%des$";

    //e理财
    public static final String COLOUR_LIFE_Flag_WYF         = "1";

}
