package com.hehenian.common.utils;

public class CNNumberUtil {

	/*
	 * 基本数字单位;
	 */
	private static final String[] units = { "千", "百", "十", "" };// 个位

	/*
	 * 大数字单位;
	 */
	private static final String[] bigUnits = { "万", "亿" };

	/*
	 * 中文数字;
	 */
	private static final char[] numChars = { '一', '二', '三', '四', '五', '六', '七',
			'八', '九' };

	// private static final char[] numMouneyChars =
	// { '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };

	private static char numZero = '零';

	/**
	 * 将中文数字转换为阿拉伯数字;
	 * 
	 * @param numberCN
	 * @return
	 */
	public static int numberCN2Arab(String numberCN) {

		String tempNumberCN = numberCN;

		// 异常数据处理;
		if (tempNumberCN == null) {
			return 0;
		}

		/*
		 * nums[0] 保存以千单位; nums[1] 保存以万单位; nums[2] 保存以亿单位;
		 */
		String[] nums = new String[bigUnits.length + 1];

		// 千位以内,直接处理;
		nums[0] = tempNumberCN;

		/*
		 * 分割大数字,以千为单位进行运算;
		 */
		for (int i = (bigUnits.length - 1); i >= 0; i--) {

			// 是否存在大单位(万,亿...);
			int find = tempNumberCN.indexOf(bigUnits[i]);

			if (find != -1) {
				String[] tempStrs = tempNumberCN.split(bigUnits[i]);

				// 清空千位内容;
				if (nums[0] != null) {
					nums[0] = null;
				}

				if (tempStrs[0] != null) {
					nums[i + 1] = tempStrs[0];
				}

				if (tempStrs.length > 1) {
					tempNumberCN = tempStrs[1];

					if (i == 0) {
						nums[0] = tempStrs[1];
					}

				} else {
					tempNumberCN = null;

					break;
				}
			}
		}

		String tempResultNum = "";

		for (int i = nums.length - 1; i >= 0; i--) {
			if (nums[i] != null) {
				tempResultNum += numberKCN2Arab(nums[i]);
			} else {
				tempResultNum += "0000";
			}
		}

		return Integer.parseInt(tempResultNum);
	}

	/**
	 * 将一位中文数字转换为一位数字; eg: 一 返回 1;
	 * 
	 * @param onlyCNNumber
	 * @return
	 */
	public static int numberCharCN2Arab(char onlyCNNumber) {

		if (numChars[0] == onlyCNNumber) {
			return 1;
		} else if (numChars[1] == onlyCNNumber || onlyCNNumber == '两') {// 处理中文习惯用法(二,两)
			return 2;
		} else if (numChars[2] == onlyCNNumber) {
			return 3;
		} else if (numChars[3] == onlyCNNumber) {
			return 4;
		} else if (numChars[4] == onlyCNNumber) {
			return 5;
		} else if (numChars[5] == onlyCNNumber) {
			return 6;
		} else if (numChars[6] == onlyCNNumber) {
			return 7;
		} else if (numChars[7] == onlyCNNumber) {
			return 8;
		} else if (numChars[8] == onlyCNNumber) {
			return 9;
		}

		return 0;
	}

	/**
	 * 将一位数字转换为一位中文数字; eg: 1 返回 一;
	 * 
	 * @param onlyArabNumber
	 * @return
	 */
	public static char numberCharArab2CN(char onlyArabNumber) {

		if (onlyArabNumber == '0') {
			return numZero;
		}

		if (onlyArabNumber > '0' && onlyArabNumber <= '9') {
			return numChars[onlyArabNumber - '0' - 1];
		}

		return onlyArabNumber;
	}

	/**
	 * 
	 * @param num
	 * @return
	 */
	public static String numberArab2CN(Integer num) {

		String tempNum = num + "";

		// 传说中的分页算法;
		int numLen = tempNum.length();
		int start = 0;
		int end = 0;
		int per = 4;
		int total = (int) ((numLen + per - 1) / per);
		int inc = numLen % per;

		/*
		 * 123,1234,1234 四位一段,进行处理;
		 */
		String[] numStrs = new String[total];

		for (int i = total - 1; i >= 0; i--) {
			start = (i - 1) * per + inc;

			if (start < 0) {
				start = 0;
			}

			end = i * per + inc;

			numStrs[i] = tempNum.substring(start, end);
		}

		String tempResultNum = "";

		int rempNumsLen = numStrs.length;
		for (int i = 0; i < rempNumsLen; i++) {

			// 小于1000补零处理;
			if (i > 0 && Integer.parseInt(numStrs[i]) < 1000) {
				tempResultNum += numZero
						+ numberKArab2CN(Integer.parseInt(numStrs[i]));
			} else {
				tempResultNum += numberKArab2CN(Integer.parseInt(numStrs[i]));
			}

			// 加上单位(万,亿....)
			if (i < rempNumsLen - 1) {
				tempResultNum += bigUnits[rempNumsLen - i - 2];
			}

		}

		// 去掉未位的零
		tempResultNum = tempResultNum.replaceAll(numZero + "$", "");

		return tempResultNum;
	}

	/**
	 * 将千以内的数字转换为中文数字;
	 * 
	 * @param num
	 * @return
	 */
	private static String numberKArab2CN(Integer num) {

		char[] numChars = (num + "").toCharArray();

		String tempStr = "";

		int inc = units.length - numChars.length;

		for (int i = 0; i < numChars.length; i++) {
			if (numChars[i] != '0') {
				tempStr += numberCharArab2CN(numChars[i]) + units[i + inc];
			} else {
				tempStr += numberCharArab2CN(numChars[i]);
			}
		}

		// 将连续的零保留一个
		tempStr = tempStr.replaceAll(numZero + "+", numZero + "");

		// 去掉未位的零
		tempStr = tempStr.replaceAll(numZero + "$", "");

		return tempStr;

	}

	/**
	 * 处理千以内中文数字,返回4位数字字符串,位数不够以"0"补齐;
	 * 
	 * @param numberCN
	 * @return
	 */
	private static String numberKCN2Arab(String numberCN) {

		if ("".equals(numberCN)) {
			return "";
		}

		int[] nums = new int[4];

		if (numberCN != null) {

			for (int i = 0; i < units.length; i++) {
				int idx = numberCN.indexOf(units[i]);

				if (idx > 0) {
					char tempNumChar = numberCN.charAt(idx - 1);

					int tempNumInt = numberCharCN2Arab(tempNumChar);

					nums[i] = tempNumInt;
				}
			}

			// 处理十位
			char ones = numberCN.charAt(numberCN.length() - 1);
			nums[nums.length - 1] = numberCharCN2Arab(ones);

			// 处理个位
			if ((numberCN.length() == 2 || numberCN.length() == 1)
					&& numberCN.charAt(0) == '十') {
				nums[nums.length - 2] = 1;
			}
		}

		// 返回结果
		String tempNum = "";
		for (int i = 0; i < nums.length; i++) {
			tempNum += nums[i];
		}

		return (tempNum);
	}
	
	public  static void main(String[] args){
		System.out.println(numberCN2Arab("三亿零五千七百六十二"));
	}
}
