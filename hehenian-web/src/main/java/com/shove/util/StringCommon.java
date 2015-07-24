package com.shove.util;

import java.util.Random;

public class StringCommon {

	/**
	 * 字符补位
	 * @param character 需要进行补位的字符 
	 * @param size 字符位数
	 * @return
	 */
	public static String charactersFillTheSeats(Long character,int size){
		StringBuffer sb = new StringBuffer();  
		int num = Integer.parseInt(character+"");   
	    sb.append(num);   
	    for(int i = String.valueOf(num).length() ; i <size;i++){   
	       sb.insert(0, "0");   
	    }
		return sb.toString();
	}
	
	/**
	 * 随机获取字符(字母)
	 * @param size 字符位数
	 * @return
	 */
	public static String randomCharacters(int size){
		StringBuffer sb = new StringBuffer();  
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			int j = random.nextInt(26)+1;//获得随机数
			sb.append(getChar(j));
		}
		return sb.toString();
	}
	
	/**
	 * 随机获取字符(字母+数字)
	 * @param size 字符位数
	 * @return
	 */
	public static String randomCharactersInt(int size){
		StringBuffer sb = new StringBuffer();  
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			int j = random.nextInt(35)+1;//获得随机数
			if(j>26){
				sb.append(j);
				continue;
			}
			sb.append(getChar(j));
		}
		return sb.toString();
	}
	
	/**
	 * 数字转换成字母
	 * @param i
	 * @return
	 */
	public static char getChar(int i){
		return (char) (64 + i);
	}
	
	
}
