package com.hehenian.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 联系方式过滤，如QQ
 * @author vp.song
 *
 */
public class SensitiveFilter {
	private static final String[] rules = new String[] {
			"\\w{1,}@\\w{2,7}[.]\\w{1,3}",
			//"(\\d)|([零〇领灵令玲凌岭铃ⅰⅠ㈠①⑴壹亿咦伊意易衣二贰儿而ⅱⅡ㈡②⑵尔耳饵洱三伞散馓山闪潵叁ⅲⅢ㈢③⑶四丝死肆ⅳⅣ㈣④⑷思斯似私司五伍ⅴⅤ㈤⑤⑸吴乌屋午武六陆录溜留刘ⅵⅥ㈥⑥⑹遛柳七柒其器齐ⅶⅦ㈦⑦⑺八霸捌ⅷ㈧Ⅷ⑧⑻巴拔罢九久就玖酒ⅸⅨ㈨⑨⑼旧救舅究qQ：:号微信聊莫陌加球腾讯鹅扣抠])|(做爱)|(联系方式)|(手机)|(抠抠)|(扣扣)|(口口)",
			"(\\d)|([零〇ⅰⅠ㈠①⑴壹二贰ⅱⅡ㈡②⑵三叁ⅲⅢ㈢③⑶四肆ⅳⅣ㈣④⑷五伍ⅴⅤ㈤⑤⑸六ⅵⅥ㈥⑥⑹七柒ⅶⅦ㈦⑦⑺八捌ⅷ㈧Ⅷ⑧⑻九玖ⅸⅨ㈨⑨⑼qQ号微聊莫陌加腾讯鹅扣抠])|(做爱)|(联系方式)|(手机)|(抠抠)|(扣扣)|(口口)",
			"^http[s]?://[^\\f\\n\\r\\t\\v\\\\?]*[.]?((\\w+).(\\w{3}))([\\/]*[\\?]*[\\S]*)?"
	};
	private static final Pattern[] ptns;

	static {
		ptns = new Pattern[rules.length];
		for (int i = 0; i < rules.length; ++i) {
			ptns[i] = Pattern.compile(rules[i]);
		}
	}
	
	/**
	 * 是否含有敏感信息，例如QQ号
	 * @param text
	 * @return
	 */
	public static boolean isSensitive(String text) {
		for(Pattern ptn : ptns) {
			if(ptn.matcher(text).matches()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 过滤敏感信息，例如QQ号
	 * @param text
	 * @return
	 */
	public static String filter(String text) {
		if(StringUtils.isBlank(text)){
			return null;
		}
		//text = text.replaceAll("\\|", "").replaceAll("\\=", "").replaceAll("\\+", "").replaceAll("\\-", "").replaceAll("，", "").replaceAll(",", "");
		for(Pattern ptn : ptns) {
			text = ptn.matcher(text).replaceAll("*");
		}
		return text;//.replaceAll("\\*\\*","*");
	}

	public static boolean hasSensitive(String text){
		if(StringUtils.isBlank(text)){
			return true;
		}
		String target = StringUtils.replace(text, " ", "");
		target = StringUtils.replace(target, "	", "");
		for(Pattern ptn : ptns) {
			if(ptn.matcher(target).matches()) {
				return true;
			}
		}
		return false;
	}
	
	public static String trimAllBlank(String text){
		if(StringUtils.isBlank(text)){
			return null;
		}
		String target = StringUtils.replace(text, " ", "");
		return StringUtils.replace(target, "	", "");
	}
	
	public static void main(String[] args) {
		Pattern p = Pattern.compile(".*c.*t.*");
		//Matcher m = p.matcher("c awdasda t");
		Matcher m = p.matcher("asd1a c awdasda t  3s2d13sa");
		//System.out.println(text.matches("cat"));
		if(m.matches()){
			System.out.println(m.replaceFirst("王尼玛2"));
		}else{
			System.out.println("no matches");
		}
		
	}
}
