package com.hehenian.biz.common.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.lang.math.NumberUtils;

public class StringUtil {
	
	/**
	 * 数字转成double类型
	 * @param v1
	 * @return
	 */
	public static double strToDouble(String v1) {
		return strToDouble(v1,0);
	}
	
	/**
	 * 
	 * @param v1 字符的数字转double
	 * @param defaultVal 默认值
	 * @return
	 */
	public static double strToDouble(String v1,double defaultVal) {
		if( null == v1 || "".equals(v1)){
			return  defaultVal;
		}
		
		BigDecimal b1 = new BigDecimal(v1);
		return b1.doubleValue();
	}
	
	/**
	 * 数字转成long类型
	 * @param v1
	 * @return
	 */
	public static long strToLong(String v1) {
		return strToLong(v1,0);
	}
	
	/**
	 * 
	 * @param v1 字符的数字转long
	 * @param defaultVal 默认值
	 * @return
	 */
	public static long strToLong(String v1,long defaultVal) {
		if( null == v1 || "".equals(v1)){
			return  defaultVal;
		}
		
		BigDecimal b1 = new BigDecimal(v1);
		return b1.longValue();
	}
	
	
	/**
	 * 数字转成long类型
	 * @param v1
	 * @return
	 */
	public static int strToInt(String v1) {
		return strToInt(v1,0);
	}
	
	/**
	 * 
	 * @param v1 字符的数字转long
	 * @param defaultVal 默认值
	 * @return
	 */
	public static int strToInt(String v1,int defaultVal) {
		if( null == v1 || "".equals(v1)){
			return  defaultVal;
		}
		
		BigDecimal b1 = new BigDecimal(v1);
		return b1.intValue();
	}
	
	/**
	 * 处理字符为空的时候返回默认值
	 * @param str
	 * @param defaultValue
	 * @return  
	 * @author: zhangyunhmf
	 * @date: 2014年9月23日下午2:21:31
	 */
	public static String strToStr(String str, String defaultValue) {
        String Result = defaultValue;

        if ((str != null) && (!str.isEmpty())) {
            Result = str;
        }

        return Result;
    }
	
	
	public static String FilteSqlInfusion(String input) {
		if ((input == null) || (input.trim() == "")) {
			return "";
		}
		if (!StringUtils.isNumeric(input)) {
			return input.replace("'", "’").replace("update", "ｕｐｄａｔｅ").replace(
					"drop", "ｄｒｏｐ").replace("delete", "ｄｅｌｅｔｅ").replace("exec",
					"ｅｘｅｃ").replace("create", "ｃｒｅａｔｅ").replace("execute",
					"ｅｘｅｃｕｔｅ").replace("where", "ｗｈｅｒｅ").replace("truncate",
					"ｔｒｕｎｃａｔｅ").replace("insert", "ｉｎｓｅｒｔ");
		} else {
			return input;
		}
	}
	
	public static String filterDangerString(String value) {  
        if (value == null) {  
            return null;  
        } 
        value = value.replaceAll("'", "");  
        value = value.replaceAll(">", "&gt;");  
        value = value.replaceAll("<", "&lt;"); 
        value = value.replaceAll("<script>", "&lt;script&gt;");  
        value = value.replaceAll("</script>", "&lt;/script&gt;"); 
        
        return value;  
    } 

	public static String fullString(long i, int length){
		String result = String.valueOf(i);
		while (result.length()<length){
			result="0"+result;
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(fullString(100, 4));
	}
	/**
	 * @Description: WML 编码
	 * @param str
	 * @return
	 * @author: zhanbmf
	 * @date 2015-3-29 下午5:42:22
	 */
    public static String encodeWML(String str)
    {
        if(str == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            switch(c)
            {
            case 36: // '$'
            case 255: 
                break;

            case 38: // '&'
                sb.append("&amp;");
                break;

            case 9: // '\t'
                sb.append("  ");
                break;

            case 60: // '<'
                sb.append("&lt;");
                break;

            case 62: // '>'
                sb.append("&gt;");
                break;

            case 34: // '"'
                sb.append("&quot;");
                break;

            case 10: // '\n'
                sb.append("<br/>");
                break;

            default:
                if(c < 0 || c > '\037')
                    sb.append(c);
                break;
            }
        }

        return sb.toString();
    }

    /**
     * @Description: net unicode解码
     * @param str
     * @return
     * @author: zhanbmf
     * @date 2015-3-29 下午5:43:12
     */
    public static String decodeNetUnicode(String str)
    {
        String pStr = "&#(\\d+);";
        Pattern p = Pattern.compile(pStr);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        String s;
        for(; m.find(); m.appendReplacement(sb, Matcher.quoteReplacement(s)))
        {
            String mcStr = m.group(1);
            int charValue = NumberUtils.toInt(mcStr, -1);
            s = charValue <= 0 ? "" : (new StringBuilder(String.valueOf((char)charValue))).toString();
        }

        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * @Description: sql编码
     * @param sql
     * @return
     * @author: zhanbmf
     * @date 2015-3-29 下午5:44:01
     */
    public static String encodeSQL(String sql)
    {
        if(sql == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < sql.length(); i++)
        {
            char c = sql.charAt(i);
            switch(c)
            {
            case 92: // '\\'
                sb.append("\\\\");
                break;

            case 13: // '\r'
                sb.append("\\r");
                break;

            case 10: // '\n'
                sb.append("\\n");
                break;

            case 9: // '\t'
                sb.append("\\t");
                break;

            case 8: // '\b'
                sb.append("\\b");
                break;

            case 39: // '\''
                sb.append("''");
                break;

            case 34: // '"'
                sb.append("\\\"");
                break;

            default:
                sb.append(c);
                break;
            }
        }

        return sb.toString();
    }
}
