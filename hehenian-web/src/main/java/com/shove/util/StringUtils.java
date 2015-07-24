package com.shove.util;




public class StringUtils {

	public static final String[] number = new String[]{"十", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
	/**
	 * 数字转换成字母
	 * @param i
	 * @return
	 */
	public static String getChar(int i){
		return (char) (64 + i) + "";
	}
	
	
	
	/**
	 * 将此字符串转换为一个新的字符数组
	 * @param answer
	 * @return
	 */
	public static String[] strToArray(String answer){
		if(org.apache.commons.lang.StringUtils.isNotBlank(answer)){
			return answer.split("");
		}
		return null;
	}
	
	/**
	 * 返回栏目题号
	 * @return
	 */
	public static String getColumnNum(String num){
		char[] cr = num.toCharArray();
		int length = cr.length;
		int[] arr = new int[length];
		for(int i = 0; i < cr.length; i ++){
			arr[i] = Integer.parseInt(cr[i]+"");
		}
		StringBuffer strBuffer = new StringBuffer();
		switch (length) {
			case 1:
				strBuffer.append(number[arr[0]]);
				break;
			case 2:
				if("10".equals(num)){
					strBuffer.append(number[arr[1]]);
					break;
				}else if(arr[0] == 1){
					strBuffer.append(number[0]).append(number[arr[1]]);
					break;
				}else if(arr[1] == 0){
					strBuffer.append(number[arr[0]]).append(number[arr[1]]);
				}else{
					strBuffer.append(number[arr[0]]).append(number[0]).append(number[arr[1]]);
				}
				break;
			default:
				break;
		}
		return strBuffer.toString();
	}
}
