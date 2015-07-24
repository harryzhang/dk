package com.sp2p.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class IPersonListsConstants {

	public static List<Map<String,Object>> HIGHESTEDU;//最高学历
	
	public static List<Map<String,Object>> MARRIAGESTATUS;//婚姻状况
	
	public static List<Map<String,Object>> HAS_NO;//有无
	
	public static List<Map<String,Object>> ORGSCALE;//公司规模
	
	public static List<Map<String,Object>> ORGINDUSTRY;//公司行业
	
	public static List<Map<String,Object>> ORGTYPE;//单位性质
	
	public static List<Map<String,Object>> REPAYMENTWAY;//还款方式
	
	public static List<Map<String,Object>> BORROWWAY;//借款类型
	
	public static Map<String,Object> CUSTOMSTR = new HashMap<String,Object>();
	
	public IPersonListsConstants(){
		IPersonListsConstants.getHIGHESTEDU();
		IPersonListsConstants.getHAS_NO();
		IPersonListsConstants.getMARRIAGESTATUS();
		IPersonListsConstants.getORGINDUSTRY();
		IPersonListsConstants.getORGSCALE();
		IPersonListsConstants.getORGTYPE();
		IPersonListsConstants.getREPAYMENTWAY();
		IPersonListsConstants.getBORROWWAY();
	}
	//静态初始块
	static {
		IPersonListsConstants.getHIGHESTEDU();
		IPersonListsConstants.getHAS_NO();
		IPersonListsConstants.getMARRIAGESTATUS();
		IPersonListsConstants.getORGINDUSTRY();
		IPersonListsConstants.getORGSCALE();
		IPersonListsConstants.getORGTYPE();
		IPersonListsConstants.getREPAYMENTWAY();
		IPersonListsConstants.getBORROWWAY();
	}
	
	public static List<Map<String, Object>> getMARRIAGESTATUS() {
		if(MARRIAGESTATUS == null){
			MARRIAGESTATUS = new ArrayList<Map<String,Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", -1);
			mp.put("name", "请选择");
			MARRIAGESTATUS.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("name", "已婚");
			MARRIAGESTATUS.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("name", "未婚");
			MARRIAGESTATUS.add(mp);
			
			CUSTOMSTR.put("请选择", -1);
			CUSTOMSTR.put("已婚", 1);
			CUSTOMSTR.put("未婚", 2);
		}
		return MARRIAGESTATUS;
	}

	public static void setMARRIAGESTATUS(List<Map<String, Object>> marriagestatus) {
		MARRIAGESTATUS = marriagestatus;
	}

	public static List<Map<String, Object>> getHAS_NO() {
		if(HAS_NO == null){
			HAS_NO = new ArrayList<Map<String,Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("name", "有");
			HAS_NO.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("name", "无");
			HAS_NO.add(mp);
			
			CUSTOMSTR.put("有", 1);
			CUSTOMSTR.put("无", 2);
		}
		return HAS_NO;
	}

	public static void setHAS_NO(List<Map<String, Object>> has_no) {
		HAS_NO = has_no;
	}
	
	public static String getCUSTOMSTRValue(String key){
		Object value = IPersonListsConstants.CUSTOMSTR.get(key);
		if(value == null){
			value = "";
		}
		return value.toString();
	}

	public static List<Map<String, Object>> getORGSCALE() {
		if(ORGSCALE == null){
			ORGSCALE = new ArrayList<Map<String,Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", -1);
			mp.put("name", "请选择");
			ORGSCALE.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("name", "50或以下");
			ORGSCALE.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("name", "50-100人");
			ORGSCALE.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("name", "100-500人");
			ORGSCALE.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 4);
			mp.put("name", "500人以上");
			ORGSCALE.add(mp);
			
			CUSTOMSTR.put("50或以下", 1);
			CUSTOMSTR.put("50-100人", 2);
			CUSTOMSTR.put("100-500人", 3);
			CUSTOMSTR.put("500人以上", 4);
		}
		return ORGSCALE;
	}

	public static void setORGSCALE(List<Map<String, Object>> orgscale) {
		ORGSCALE = orgscale;
	}

	public static List<Map<String, Object>> getORGINDUSTRY() {
		if(ORGINDUSTRY == null){
			ORGINDUSTRY = new ArrayList<Map<String,Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", -1);
			mp.put("name", "请选择");
			ORGINDUSTRY.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("name", "金融");
			ORGINDUSTRY.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("name", "电子");
			ORGINDUSTRY.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("name", "服务行业");
			ORGINDUSTRY.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 4);
			mp.put("name", "服装");
			ORGINDUSTRY.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 5);
			mp.put("name", "其他");
			ORGINDUSTRY.add(mp);
			
			CUSTOMSTR.put("金融", 1);
			CUSTOMSTR.put("电子", 2);
			CUSTOMSTR.put("服务行业", 3);
			CUSTOMSTR.put("服装", 4);
			CUSTOMSTR.put("其他", 5);
		}
		return ORGINDUSTRY;
	}

	public static void setORGINDUSTRY(List<Map<String, Object>> orgindustry) {
		ORGINDUSTRY = orgindustry;
	}

	public static List<Map<String, Object>> getORGTYPE() {
		if(ORGTYPE == null){
			ORGTYPE = new ArrayList<Map<String,Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", -1);
			mp.put("name", "请选择");
			ORGTYPE.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("name", "私营企业");
			ORGTYPE.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("name", "事业单位");
			ORGTYPE.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("name", "中外合资企业");
			ORGTYPE.add(mp);
			
			CUSTOMSTR.put("私营企业", 1);
			CUSTOMSTR.put("事业单位", 2);
			CUSTOMSTR.put("中外合资企业", 3);
		}
		return ORGTYPE;
	}

	public static void setORGTYPE(List<Map<String, Object>> orgtype) {
		ORGTYPE = orgtype;
	}

	public static List<Map<String, Object>> getREPAYMENTWAY() {
		if(REPAYMENTWAY == null){
			REPAYMENTWAY = new ArrayList<Map<String,Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", -1);
			mp.put("name", "请选择");
			REPAYMENTWAY.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("name", "按月等额本息还款");
			REPAYMENTWAY.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("name", "按月还息、到期还本");
			REPAYMENTWAY.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("name", "等本等息还款");
			REPAYMENTWAY.add(mp);
			
			CUSTOMSTR.put("按月等额本息还款", 1);
			CUSTOMSTR.put("按月还息、到期还本", 2);
			CUSTOMSTR.put("等本等息还款", 3);
		}
		return REPAYMENTWAY;
	}

	public static void setREPAYMENTWAY(List<Map<String, Object>> repaymentway) {
		REPAYMENTWAY = repaymentway;
	}

	public static List<Map<String, Object>> getHIGHESTEDU() {
		if(HIGHESTEDU == null){
			HIGHESTEDU = new ArrayList<Map<String,Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", -1);
			mp.put("name", "请选择");
			HIGHESTEDU.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("name", "初中及以下");
			HIGHESTEDU.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("name", "高中或中专");
			HIGHESTEDU.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("name", "大专");
			HIGHESTEDU.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 4);
			mp.put("name", "本科及以上");
			HIGHESTEDU.add(mp);
			
			CUSTOMSTR.put("初中及以下", 1);
			CUSTOMSTR.put("高中或中专", 2);
			CUSTOMSTR.put("大专", 3);
			CUSTOMSTR.put("本科及以上", 4);
		}
		return HIGHESTEDU;
	}

	public static void setHIGHESTEDU(List<Map<String, Object>> highestedu) {
		HIGHESTEDU = highestedu;
	}

	public static List<Map<String, Object>> getBORROWWAY() {
		if(BORROWWAY == null){
			BORROWWAY = new ArrayList<Map<String,Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", -1);
			mp.put("name", "请选择");
			BORROWWAY.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("name", "薪金贷");
			BORROWWAY.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("name", "生意贷");
			BORROWWAY.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("name", "业主贷");
			BORROWWAY.add(mp);
			
			CUSTOMSTR.put("业主贷", 3);
			CUSTOMSTR.put("生意贷", 2);
			CUSTOMSTR.put("薪金贷", 1);
		}
		return BORROWWAY;
	}

	public static void setBORROWWAY(List<Map<String, Object>> borrowway) {
		BORROWWAY = borrowway;
	}
}
