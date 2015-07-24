package com.sp2p.util;

import java.text.FieldPosition;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class generateSequenceNo {

	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
	
	private final static Format dateFormat = new SimpleDateFormat("yyMMddHHmmssS");

	private static int seq = 0;

	private static final int MAX = 9999;

	/**
	 * 时间格式生成序列
	 * 
	 * @return String
	 */
	public static synchronized String generateSequenceNo() {

		Calendar rightNow = Calendar.getInstance();

		StringBuffer sb = new StringBuffer();

		dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);

		if (seq == MAX) {
			seq = 0;
		} else {
			seq++;
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		
		String str=generateSequenceNo.generateSequenceNo();
		
		System.out.println(str);
	}
}