/**
 * 
 */
package com.hehenian.biz.common.base.constant;

/**
 * @author zhangyunhmf
 *
 */
public class Constants {
    /**
     * 字符串：空
     */
    public final static String EMPTY_STR  = "";

    /**
     * 半角字符：空格
     */
    public final static String BLANK_STR  = " ";

    /**
     * 半角字符：斜杠
     */
    public final static String SLASH      = "/";

    /**
     * 半角字符：反斜杠
     */
    public final static String BACKSLASH  = "\\";

    /**
     * 半角字符：点
     */
    public final static String POINT      = ".";

    /**
     * 半角字符：逗号
     */
    public final static String COMMA      = ",";

    /**
     * 半角字符：下划线
     */
    public final static String UNDERLINE  = "_";

    /**
     * 半角字符：中划线
     */
    public final static String MIDDLELINE = "-";

    /**
     * 数字：0
     */
    public final static byte   ZERO       = 0;

    /**
     * 数字：1
     */
    public final static byte   ONE        = 1;

    /**
     * 数字：-1
     */
    public final static byte   MINUS_ONE  = -1;

    /**
     * 字母：x
     */
    public final static String X          = "x";

    /**
     * 提前结清手续费率
     */
    public static final double PRE_REPAY_RATE          = 0.03d;
    // 按月付息到期还本
    public static final double PRE_REPAY_RATE2         = 0.02d;
    // 一次付息到期还本
    public static final double PRE_REPAY_RATE3         = 0d;

    /**
     * 提前结清手续费编码
     */
    public static final String FEE_CODE_PRE            = "901";
    // 咨询费
    public static final String FEE_CODE_CONSULT        = "902";
    // 放款手续费
    public static final String FEE_CODE_SERVICE_CHARGE = "903";
    // 罚息
    public static final String FEE_CODE_FX             = "603";
    // 扣除还款金额
    public static final String FEE_CODE_REPAY          = "604";

    /**
     * 投资信息管理费率
     * 
     * @since 1.0.0
     */
    public static final String INVEST_FEE_RATE         = "investFeeRate";

}
