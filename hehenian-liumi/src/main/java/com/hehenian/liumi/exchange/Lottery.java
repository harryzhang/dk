package com.hehenian.liumi.exchange;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lottery {
	
	private static Map<Integer,Integer> YD;
	private static Map<Integer,Integer> DX;
	private static Map<Integer,Integer> LT;
	private static Random random = new Random();
	public static String Flag ="";
	
	static{
		YD=new LinkedHashMap<Integer,Integer>();
		YD.put(10,95);
		YD.put(30,100);
		DX=new LinkedHashMap<Integer,Integer>();
		DX.put(5,45);
		DX.put(10,90);
		DX.put(30,95);
		DX.put(50,100);
		LT=new LinkedHashMap<Integer,Integer>();
		LT.put(50,100);
		
	}
	
	public static int lottery(String mobile){
		Map<Integer,Integer> carrier=lookup(mobile);
		int r = Math.abs(random.nextInt())%100;
		for(Entry<Integer,Integer> i : carrier.entrySet()){
			if(i.getValue()>=r){
				return i.getKey();
			}
		}
		return 0;
	}
	public static Map<Integer,Integer> lookup(String mobile){
		Pattern pattern=Pattern.compile("^1(34[0-8]|(3[5-9]|47|5[0-2]|57[124]|5[89]|8[2378])\\d)\\d{7}$");
		Matcher matcher=pattern.matcher(mobile);
		boolean b= matcher.matches();
		if(b){
			Flag = "YD";
			return YD;
		}
		
		pattern=Pattern.compile("^1(3[0-2]|45|5[56]|8[56])\\d{8}$");
		matcher=pattern.matcher(mobile);
		b= matcher.matches();
		if(b){
			Flag = "LT";
			return LT;
		}
		
		pattern=Pattern.compile("^1(33|53|8[09])\\d{8}$");
		matcher=pattern.matcher(mobile);
		b= matcher.matches();
		if(b){
			Flag = "DX";
			return DX;
		}
		return null;
	}
}
