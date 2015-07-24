package com.hehenian.biz.facade.notify.montnets.mwgate.common;

import java.util.List;

public class SmsTool {
	
	/**
	 * 打印上行信息
	 * @param moPackList
	 */
	public void printMoPack(List<MO_PACK> moPackList){
		if(moPackList!=null&&moPackList.size()>0){
			System.out.println("上行总条数："+moPackList.size());
			MO_PACK moPack=null;
			for (int i = 0; i < moPackList.size(); i++)
			{
				moPack=moPackList.get(i);
				System.out.println(moPack.getStrMoTime()+","+moPack.getStrMobile()+","+moPack.getStrSpNumber()+","
						+moPack.getStrExNo()+","+moPack.getStrReserve()+","+moPack.getStrMessage());
			}
		}else{
			System.out.println("无上行");
		}
	}
	
	/**
	 * 打印状态报告信息
	 * @param rptPackList
	 */
	public void printRptPack(List<RPT_PACK> rptPackList){
		if(rptPackList!=null&&rptPackList.size()>0){
			System.out.println("状态报告总条数："+rptPackList.size());
			RPT_PACK rptPack=null;
			for (int i = 0; i < rptPackList.size(); i++)
			{
				rptPack=rptPackList.get(i);
				System.out.println(rptPack.getStrMoTime()+","+rptPack.getStrPtMsgId()+","+rptPack.getStrSpNumber()+","+rptPack.getStrMobile()+","
						+rptPack.getStrUserMsgId()+","+rptPack.getStrReserve()+","+rptPack.getnStatus()+","+rptPack.getStrErCode());
			}
		}else{
			System.out.println("无状态报告");
		}
	}

	/**
	 * 无符号数字
	 * @param str
	 * @return
	 */
	public static boolean  isUnSignDigit(String str){
		char[] num=str.toCharArray();
		for (int i = 0; i < num.length; i++)
		{
			if(!Character.isDigit(num[i])){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 有符号数字
	 * @param str
	 * @return
	 */
	public static boolean  isSignDigit(String str){
		String firstChar=str.substring(0,1);
		if(firstChar.equals("-")){
			str=str.substring(1);
		}
		char[] num=str.toCharArray();
		for (int i = 0; i < num.length; i++)
		{
			if(!Character.isDigit(num[i])){
				return false;
			}
		}
		return true;
	}
}
